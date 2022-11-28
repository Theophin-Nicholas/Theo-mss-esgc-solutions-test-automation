package com.esgc.Utilities;

import com.esgc.APIModels.JiraTestCase;
import com.esgc.APIModels.TestCase;
import com.esgc.APIModels.TestEvidence;
import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class XrayFileImporter {

    //Responsible for loading properties fileand will provide access, based on keys we will read values.
    private static final Properties properties = new Properties();
    private static final String urlSlackWebHook = "https://hooks.slack.com/services/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";
    private static final String channelName = "#platform-ui-smoke-test-execution";

    private static final String url = "https://esjira/rest/";

    static {

        try {
            String current = new java.io.File(".").getCanonicalPath();
            System.out.println("current = " + current);
            current = current.substring(0,current.lastIndexOf(File.separator));
            System.out.println("current = " + current);

            //provides access to the file
            FileInputStream fileInputStream = new FileInputStream(current + File.separator + "configuration.properties");

            //loads properties file
            properties.load(fileInputStream);
            fileInputStream.close();

        } catch (IOException e) {
            System.out.println("XRAY Process cannot be started");
            e.printStackTrace();
        }
    }

    private XrayFileImporter() {

    }

  /*  public Response sendExecutionTestNGResultsToXray(String reportName) {
        Response response = createTestExecution(reportName).prettyPeek();
        response.then().statusCode(201);
        String testExecutionKey = response.jsonPath().getString("key");
        //For testing purposes> String testExecutionKey = "ESGCA-5194";
        return configSpec()
                .queryParam("testExecKey", testExecutionKey)
                .multiPart("file",
                        new File("target/surefire-reports/testng-results.xml"))
                .when()
                .post("raven/1.0/import/execution/testng");
    }*/


    public static void sendExecutionResultsToXray(String reportName, List<TestCase> testCaseList) {
        System.out.println("Xray Process Started");
        Set<String> allTestCases = findTestCases(testCaseList);
        Set<String> failedTestCases = findFailedTestCases(testCaseList);

        List<JiraTestCase> tclist = allTestCases.stream()
                .map(testCaseNumber -> {
                    String status = "PASS";
                    if (failedTestCases.contains(testCaseNumber)) {
                        status = "FAIL";
                        String screenshotLocation = "";
                        screenshotLocation = findFailedScreenshot(testCaseList, testCaseNumber);
                        if (screenshotLocation != null) {
                            String screenshot = screenshot = encodeFileToBase64Binary(screenshotLocation);
                            List<TestEvidence> evidences = new ArrayList<>();
                            TestEvidence testEvidence = new TestEvidence(screenshot);
                            evidences.add(testEvidence);
                            return new JiraTestCase(testCaseNumber, status, evidences);
                        }
                    }
                    return new JiraTestCase(testCaseNumber, status);
                }).collect(Collectors.toList());

        String tcs = new Gson().toJson(tclist);

        Response responseTestExecution = createTestExecution(reportName).prettyPeek();
        responseTestExecution.then().statusCode(201);
        String testExecutionKey = responseTestExecution.jsonPath().getString("key");
//        String testExecutionKey = "ESGCA-6281";

        String payload = "{" +

     /*       FOR DEMO
            If you want to use an existing Test Execution ticket, add this line to payload,
            "    \"testExecutionKey\": \"" + testExecutionKey + "\",\n" +
     */
                "    \"testExecutionKey\": \"" + testExecutionKey + "\",\n" +
                "    \"info\" : {\n" +
                "     \"project\" : \"ESGCA\"," +
                "       \"summary\": \"Automated " + reportName + "\",\n" +
                "       \"description\": \"Result of Automation Execution " + reportName + "\",\n" +
                "        \"description\" : \"This execution is automatically created by Moody's ESG+C Test Automation Framework\",\n" +
//                "        \"version\" : \"v2.5\",\n" +
                "        \"user\" : \"sys_e2e_esg_qa\",\n" +
//                "        \"testPlanKey\" : \"DEMO-100\",\n" +
                "        \"testEnvironments\": [\"" + ConfigurationReader.getProperty("environment").toUpperCase() + "\"]\n" +
                "    }," +
                " \"tests\" : " + tcs + "\n" +
                "}";

        //Create test execution ticket and map Test Cases with PASS and/or FAIL status
        Response response = configSpec()
                .contentType("application/json")
                .body(payload)
                .when()
                .post("raven/1.0/import/execution").prettyPeek();


        /*

        If there is any unrelated ticket mapped, remove it from list to complete mapping on Jira test execution ticket
        (once there is unrelated ticket number in the list, test execution is not mappping test cases)
        {
                "error": "Test issue with key ESGCA-11122343 not found.;Issue with key \"ESGCA-1111\" is not of type Test"
            }

        */

        Response response2 = null;
        if (response.statusCode() == 400) {
            String errorMessage = response.jsonPath().getString("error");
            System.out.println(errorMessage);
            List<String> removeTestCaseList = Arrays.stream(errorMessage.split(" "))
                    .filter(word -> word.contains("ESGCA-")).collect(Collectors.toList());

            for (int i = 0; i < removeTestCaseList.size(); i++) {
                String ticketNum = removeTestCaseList.get(i);
                if (ticketNum.contains("\"")) {
                    removeTestCaseList.set(i, ticketNum.substring(1, ticketNum.length() - 1));
                }
            }

            System.out.println("###################");
            System.out.println("SOME TICKET NUMBERS TYPE IS NOT TEST CASE");
            System.out.println("THOSE TICKET NUMBERS ARE REMOVED FROM THE LIST");
            System.out.println("REMOVED TICKET NUMBERS WHICH ARE NOT TEST CASES:");
            removeTestCaseList.forEach(System.out::println);
            System.out.println("###################");

            for (String testCaseTicketNumber : removeTestCaseList) {
                tclist.removeIf(e -> e.getTestKey().equals(testCaseTicketNumber));
            }

            // tclist.stream().filter(e -> !removeTestCaseList.contains(e.getTestKey())).collect(Collectors.toList());

            String tcs2 = new Gson().toJson(tclist);
            String payload2 = "{" +


                    "    \"testExecutionKey\": \"" + testExecutionKey + "\",\n" +
                    "    \"info\" : {\n" +
                    "     \"project\" : \"ESGCA\"," +
                    "       \"summary\": \"Automated " + reportName + "\",\n" +
                    "       \"description\": \"Result of Automation Execution " + reportName + "\",\n" +
                    "        \"description\" : \"This execution is automatically created by Moody's ESG+C Test Automation Framework\",\n" +
                    "        \"user\" : \"sys_e2e_esg_qa\",\n" +
                    "        \"testEnvironments\": [\"" + ConfigurationReader.getProperty("environment").toUpperCase() + "\"]\n" +
                    "    }," +
                    " \"tests\" : " + tcs2 + "\n" +
                    "}";

            //(without unrelated ticket numbers) Create test execution ticket and map Test Cases with PASS and/or FAIL status
            response2 = configSpec()
                    .contentType("application/json")
                    .body(payload2)
                    .when()
                    .post("raven/1.0/import/execution").prettyPeek();
        }


        //Update Test Execution Ticket Status from BACKLOG to Accepted
        String testExecutionID = response2 == null ?
                response.jsonPath().getString("testExecIssue.id") :
                response2.jsonPath().getString("testExecIssue.id");
        List<String> ticketStatuses = Arrays.asList("61", "71", "81", "131", "331", "141", "161", "181");
        for (String status : ticketStatuses) {
            String payloadToChangeTicketStatus = "{" +
                    "    \"transition\" : {\n" +
                    "     \"id\" : \"" + status + "\"" +
                    "    }" +
                    "}";
            configSpec()
                    .contentType("application/json")
                    .pathParam("id", testExecutionID)
                    .body(payloadToChangeTicketStatus)
                    .when()
                    .post("api/2/issue/{id}/transitions?expand=expand.fields")
                    .then()
                    .log().ifError();
        }

        int totalTCsCount = allTestCases.size();
        int failedTCsCount = failedTestCases.size();
        int passedTCsCount = totalTCsCount - failedTCsCount;
        double passRate = Math.ceil((((double) passedTCsCount) / ((double) totalTCsCount)) * 100);

        System.out.printf("%30s %6s \n", "Total Test Cases:", totalTCsCount);
        System.out.printf("%30s %6s \n", "Passed Test Cases:", passedTCsCount);
        System.out.printf("%30s %6s \n", "Failed Test Cases:", failedTCsCount);

        System.out.printf("%30s %6s \n", "Pass rate:", passRate + "%");


        String environment = ConfigurationReader.getProperty("environment");
        if (System.getProperty("environment") != null) {
            environment = System.getProperty("environment");
        }

        String browser = System.getProperty("browser") == null ? ConfigurationReader.getProperty("browser") : System.getProperty("browser");
        String platform = reportName.contains("EMC") ? "EMC " : reportName.contains("Issuer") ? "Issuer " : "Moody's ESG360 Platform ";
        String testType = reportName.contains("Smoke") ? "Smoke " : reportName.contains("Regression") ? "Regression " : "";
        String message = platform + testType + "Test Execution Results\n" +
                ":globe_with_meridians: Browser: " + browser.toUpperCase() + "\n" +
                ":computer: Environment: " + environment.toUpperCase() + "\n" +
                ":clipboard: Total Test Cases: " + totalTCsCount + "\n" +
                ":white_Check_mark: Passed Test Cases: " + passedTCsCount + "\n" +
                ":x: Failed Test Cases: " + failedTCsCount + "\n" +
                ":chart_with_upwards_trend:Pass rate: " + passRate + "%\n" +
                "<https://esjira/browse/" + testExecutionKey + "|View results in Jira>\n";
        try {
            sendTestExecutionStatusToSlack(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static Response createTestExecution(String reportName) {
        System.out.println("creating test execution");
        return configSpec()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"fields\": {\n" +
                        "       \"project\":\n" +
                        "       {\n" +
                        "          \"key\": \"ESGCA\"\n" +
                        "       },\n" +
                        "       \"summary\": \"Automated " + reportName + "\",\n" +
                        "       \"description\": \"Result of Automation Execution " + reportName + "\",\n" +
                        "       \"issuetype\": {\n" +
                        "          \"name\": \"Test Execution\"\n" +
                        "       },\n" +
                        "       \"assignee\": {\n" +
                        "      \"name\": \"sys_e2e_esg_qa\"\n" +
                        "    }\n" +
                        "   }\n" +
                        "}")
                .post("api/2/issue/").prettyPeek();
    }

    private static RequestSpecification configSpec() {
        String auth_token = Encryption.decrypt(ConfigurationReader.getProperty("auth_tokens"),
                Encryption.convertStringToSecretKeyTo(ConfigurationReader.getProperty("secretKey")));
        //properties.getProperty(auth_token)
        return given().header("Authorization", auth_token).log().all()
                .baseUri(url)
                .relaxedHTTPSValidation().accept(ContentType.JSON);
    }

    //To verify the token
    public synchronized void test() {

        String auth_token = Encryption.decrypt(ConfigurationReader.getProperty("auth_tokens"),
                Encryption.convertStringToSecretKeyTo(ConfigurationReader.getProperty("secretKey")));
        System.out.println(auth_token);
    }

    private static Set<String> findTestCases(List<TestCase> testCaseList) {
        return testCaseList.stream().map(TestCase::getTestCaseNumber).flatMap(List::stream).collect(Collectors.toSet());
    }

    private static Set<String> findFailedTestCases(List<TestCase> testCaseList) {
        return testCaseList.stream().filter(t -> !t.getPassOrFail()).map(TestCase::getTestCaseNumber).flatMap(List::stream).collect(Collectors.toSet());
    }

    private static String findFailedScreenshot(List<TestCase> testCaseList, String testCaseTicketNumber) {
        TestCase testCase = testCaseList.stream().filter(t -> !t.getPassOrFail())
                .filter(x -> x.getFailedScreenShot() != null)
                .filter(x -> x.getTestCaseNumber().contains(testCaseTicketNumber))
                .findFirst().orElse(null);
        if (testCase != null) {
            return testCase.getFailedScreenShot();
        }
        return null;
    }

    public static void sendTestExecutionStatusToSlack(String message) throws Exception {
        try {
            Payload payload = Payload.builder().channel(channelName).text(message).build();

            WebhookResponse webhookResponse = Slack.getInstance().send(urlSlackWebHook, payload);
            System.out.println("webhookResponse.getMessage() = " + webhookResponse.toString());
        } catch (
                IOException e) {
            System.out.println("Unexpected Error! WebHook:" + urlSlackWebHook);
        }
    }

    private static String encodeFileToBase64Binary(String fileName) {
        File file = new File(fileName);
        byte[] encoded = new byte[0];
        try {
            encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            System.out.println("SCREEN SHOT FILE NOT FOUND");
            e.printStackTrace();
        }
        return new String(encoded, StandardCharsets.US_ASCII);
    }

}
