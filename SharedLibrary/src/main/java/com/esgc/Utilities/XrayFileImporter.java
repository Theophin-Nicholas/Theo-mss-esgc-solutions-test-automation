package com.esgc.Utilities;

import com.esgc.APIModels.JiraTestCase;
import com.esgc.APIModels.JiraTestCasePayload;
import com.esgc.APIModels.TestCase;
import com.esgc.APIModels.TestEvidence;
import com.esgc.TestBase.TestBase;
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
import java.io.FileWriter;
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

    private static final String url = "https://ma-esg-and-data.atlassian.net/";

    static {

        try {
            String current = new java.io.File(".").getCanonicalPath();
            System.out.println("current = " + current);
            current = current.substring(0, current.lastIndexOf(File.separator));
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

        //STEP 1 get all test cases and failed test cases
        Set<String> allTestCases = findTestCases(testCaseList);
        System.out.println("allTestCases = " + allTestCases);
        Set<String> failedTestCases = findFailedTestCases(testCaseList);

        //If we run smoke suite then remove test cases which are not marked as smoke
//        if(reportName.contains("Smoke")) {
//            List<String> smokeTestList = getSmokeTestCases();
//            allTestCases = allTestCases.stream().filter(s -> smokeTestList.get(0).contains(s)).collect(Collectors.toSet());
//            failedTestCases = failedTestCases.stream().filter(s -> smokeTestList.get(0).contains(s)).collect(Collectors.toSet());
//        }

        //STEP 2
        //Some executions may cover same test case couple times, so we remove duplicated test cases
        //We mark test cases as PASSED and FAILED and for FAILED ones we include screenshots to see in jira
        Set<String> finalFailedTestCases = failedTestCases;
        List<JiraTestCase> tclist = allTestCases.stream()
                .map(testCaseNumber -> {
                    String status = "PASSED";
                    if (finalFailedTestCases.contains(testCaseNumber)) {
                        status = "FAILED";
                        String screenshotLocation = "";
                        screenshotLocation = findFailedScreenshot(testCaseList, testCaseNumber);
                        if (screenshotLocation != null) {
                            String screenshot = encodeFileToBase64Binary(screenshotLocation);
                            List<TestEvidence> evidences = new ArrayList<>();
                            TestEvidence testEvidence = new TestEvidence(screenshot);
                            evidences.add(testEvidence);
                            return new JiraTestCase(testCaseNumber, status, evidences);
                        }
                    }
                    return new JiraTestCase(testCaseNumber, status);
                }).collect(Collectors.toList());

        //When test cases are ready we convert the test case list to json format to send in API call to import in jira test execution ticket
        String tcs = new Gson().toJson(tclist);

        //STEP 3 create a test execution ticket in JIRA
        Response responseTestExecution = createTestExecution(reportName).prettyPeek();
        responseTestExecution.then().statusCode(201);
        String testExecutionKey = responseTestExecution.jsonPath().getString("key");

        //FOR TESTING PURPOSES you can use existing test execution: (comment out 3 lines above and uncomment the line below
        //String testExecutionKey = "ESGT-2495";

        String payload = "{" +
                "    \"testExecutionKey\": \"" + testExecutionKey + "\",\n" +
                " \"tests\" : " + tcs + "\n" +
                "}";

        //Just in case saving payload to file so that if something is failed we can send request manually
        savePayloadToFramework(payload);

        //STEP 4 get token to get access to import tests in JIRA
        String token = getXrayToken();

        //Since test cases are a lot, we send them partially (100 test case) in each iteration
        int chunkSize = 100;
        for (int startIndex = 0; startIndex < tclist.size(); startIndex += chunkSize) {

            int endIndex = Math.min(startIndex + chunkSize, tclist.size());
            List<JiraTestCase> tcSubList = tclist.subList(startIndex, endIndex);
            System.out.println("tcSubList = " + tcSubList);
            //STEP 5 Import tests to test execution ticket and map Test Cases with PASS and/or FAIL status in JIRA
            Response response = linkTestCasesToTestExecutionTicket(testExecutionKey, token, tcSubList);

        /*
        STEP 6 - If there is any unrelated ticket mapped, remove it from list to complete mapping on Jira test execution ticket
        (once there is unrelated ticket number in the list, test execution is not mapping test cases)
        {
            "error": "Issue with key ESGCA-111 is not of type Test."
        }
        */
            while (response.statusCode() == 400) {
                String errorMessage = response.jsonPath().getString("error");
                System.out.println("Error Message:" + errorMessage);

                if(!errorMessage.contains("ESGT-"))
                    break;

                List<String> removeTestCaseList = Arrays.stream(errorMessage.split(" "))
                        .filter(word -> word.contains("ESGT-")).collect(Collectors.toList());

                for (int i = 0; i < removeTestCaseList.size(); i++) {
                    String ticketNum = removeTestCaseList.get(i);
                    if (ticketNum.contains("\"")) {
                        removeTestCaseList.set(i, ticketNum.substring(1, ticketNum.length() - 1));
                    }
                }

                System.out.println("###################");
                System.out.println("THIS TICKET NUMBER TYPE IS NOT A TEST CASE:");
                removeTestCaseList.forEach(System.out::println);
                System.out.println("###################");

                for (String testCaseTicketNumber : removeTestCaseList) {
                    tclist.removeIf(e -> e.getTestKey().equals(testCaseTicketNumber));
                }

                // tclist.stream().filter(e -> !removeTestCaseList.contains(e.getTestKey())).collect(Collectors.toList());

                //(without unrelated ticket numbers) Create test execution ticket and map Test Cases with PASS and/or FAIL status
                response = linkTestCasesToTestExecutionTicket(testExecutionKey, token, tcSubList);
            }
        }

        //STEP 7 - Update Test Execution Ticket Status from TO-DO to DONE
        moveTestExecutionTicketToDoneStatus(testExecutionKey);

        //STEP 8
        attachHTMLReportToTestExecutionTicket(testExecutionKey, reportName);

        int totalTCsCount = allTestCases.size();
        int failedTCsCount = finalFailedTestCases.size();
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

        //STEP 9 - Send results to Slack Channel
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
                "<https://ma-esg-and-data.atlassian.net/browse/" + testExecutionKey + "|View results in Jira Cloud>\n";
        try {
            sendTestExecutionStatusToSlack(message);
        } catch (
                Exception e) {
            e.printStackTrace();
        }

    }

    private static Response xcreateTestExecution(String reportName) {
        System.out.println("creating test execution");
        return configSpec()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"fields\": {\n" +
                        "       \"project\":\n" +
                        "       {\n" +
                        "          \"key\": \"ESGT\"\n" +
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
                .post("rest/api/2/issue/").prettyPeek();
    }

    private static RequestSpecification configSpec() {
        return given().
                auth().preemptive().basic("sys_e2e_esg_qa@moodys.com",
                        "ATATT3xFfGF0vwjE0FaDBbnQHNjwy7YhidWPBz7W7ZfZE55PFWNfeaOOfoGyCJrHkN9-8I1Npk10C-sMSZvnqaI1z--pFKSs_Ql5tgzWniZw_uFfhRhEe1SyQQ4gu3Sxu3rcPwWSZdUsbR6YC4HStr6nF1uy5DqM-PlLjWkICMTZVPRG1lnn-sc=2759FD97")
                .baseUri(url)
                .relaxedHTTPSValidation().accept(ContentType.JSON);
    }

    //To verify the token
    public synchronized static void test() {

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

    private static String getXrayToken() {
        String body = "{ \"client_id\": \"64095568AFB44A98A04471DE45FCBAE6\",\"client_secret\": \"934a0ffc7ed4fdc7d7ffee0dcce4a72161ccfaf3518bbf299b28f2278f16df14\" }\n";

        return given()
                .body(body)
                .contentType("application/json")
                .relaxedHTTPSValidation().accept(ContentType.JSON).when()
                .post("https://xray.cloud.getxray.app/api/v2/authenticate").prettyPrint().replace("\"", "");
    }

    private static Response linkTestCasesToTestExecutionTicket(String testExecutionKey, String token, List<JiraTestCase> testCaseList) {
        System.out.println("Linking test cases");
        return given()
                .header("Authorization", "Bearer " + token)
                .relaxedHTTPSValidation().accept(ContentType.JSON)
                .contentType("application/json")
                .body(new JiraTestCasePayload(testExecutionKey, testCaseList))
                .when()
                .post("https://xray.cloud.getxray.app/api/v2/import/execution");
    }

    private static void moveTestExecutionTicketToDoneStatus(String testExecutionKey) {
        String payloadToChangeTicketStatus = "{" +
                "    \"transition\" : {\n" +
                "     \"id\" : \"41\"" +
                "    }" +
                "}";

        configSpec()
                .contentType("application/json")
                .pathParam("id", testExecutionKey)
                .body(payloadToChangeTicketStatus)
                .when()
                .post("rest/api/3/issue/{id}/transitions?expand=expand.fields")
                .then()
                .log()
                .ifError();
    }

    private static void sendTestExecutionStatusToSlack(String message) throws Exception {
        try {
            Payload payload = Payload.builder().channel(channelName).text(message).build();

            WebhookResponse webhookResponse = Slack.getInstance().send(urlSlackWebHook, payload);
            System.out.println("webhookResponse.getMessage() = " + webhookResponse.toString());
        } catch (
                IOException e) {
            System.out.println("Unexpected Error! WebHook:" + urlSlackWebHook);
        }
    }

    //Save screenshots as binary to send in API calls
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

    public static void attachHTMLReportToTestExecutionTicket(String tickedNumber, String reportName) {
        System.out.println("File Attachment Started");
        String filepath = TestBase.reportPath + File.separator + reportName + ".html";
        System.out.println("Report Path=" + filepath);
        configSpec()
                .header("Accept", "application/json")
                .header("X-Atlassian-Token", "no-check")
                .multiPart("file", new File(filepath))
                .when()
                .post("rest/api/3/issue/" + tickedNumber + "/attachments");
    }

    public static List<String> getSmokeTestCases() {
        Response response = configSpec()
                .contentType("application/json")
                .when()
                .log().all()
                .get("/rest/api/3/search?jql='Smoke/Sanity Test' = Yes").prettyPeek();

        return response.getBody().jsonPath().getList("issues.key");

    }

    public static void savePayloadToFramework(String payload) {
        String filePath = System.getProperty("user.dir") + File.separator + "SEND RESULTS TO JIRA PAYLOAD.txt";
        System.out.println("filePath = " + filePath);
        try (FileWriter fileWriter = new FileWriter(filePath, false)) {
            fileWriter.write(payload);
            System.out.println("Payload saved to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error occurred while saving the string to file.");
        }
    }

    private static Response createTestExecution(String reportName) {
        System.out.println("creating test execution");
        return configSpec()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"fields\": {\n" +
                        "    \"project\": {\n" +
                        "      \"id\": \"10016\"\n" +
                        "    },\n" +
                        "    \"issuetype\": {\n" +
                        "      \"id\": \"10004\"\n" +
                        "    },\n" +
                        "    \"assignee\": {\n" +
                        "      \"id\": \"712020:f052e693-11dd-47a1-90e6-666e6bb9c262\"\n" +
                        "    },\n" +
                        "    \"summary\": \"Automated " + reportName + "\",\n" +
                        "    \"customfield_10310\": {\n" +
                        "      \"id\": \"10875\",\n" +
                        "      \"value\": \"ESGT: RDP-Guardians of the Galaxy\"\n" +
                        "    }," +
                        "    \"environment\": {\n" +
                        "      \"version\": 1,\n" +
                        "      \"type\": \"doc\"," +
                        "       \"content\": [\n" +
                        "        {\n" +
                        "          \"content\": [\n" +
                        "            {\n" +
                        "              \"text\": \"" + ConfigurationReader.getProperty("environment").toUpperCase() + "\",\n" +
                        "              \"type\": \"text\"\n" +
                        "            }\n" +
                        "          ],\n" +
                        "          \"type\": \"paragraph\"\n" +
                        "        }\n" +
                        "      ]\n" +
                        "  }\n" +
                        "}}")
                .post("rest/api/3/issue/").prettyPeek();
    }

}
