package com.esgc.RegulatoryReporting.API.Tests;

import com.esgc.Base.TestBases.APITestBase;
import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.Dashboard.API.Controllers.DashboardAPIController;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.RegulatoryReporting.API.APIModels.PortfolioDetails;
import com.esgc.RegulatoryReporting.API.Controllers.RegulatoryReportingAPIController;
import com.esgc.RegulatoryReporting.UI.Pages.RegulatoryReportingPage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.DateTimeUtilities;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.Groups.*;
import static org.hamcrest.Matchers.equalTo;

public class RegulatoryReportingAPITests extends APITestBase {


    @Test(groups = {REGRESSION, REGULATORY_REPORTING, API}, description = "Data Validation| MT | Regulatory Reporting | Validate Portfolio list and portfolio-details")
    @Xray(test = {11140})
    public void verifyPortfolioPortfolioDetailsListTest() {
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        DashboardPage dashboardPage = new DashboardPage();
        //Verify all the portfolios that the user has uploaded, are listed in the "select portfolio" section.
        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");
        dashboardPage.clickPortfolioSelectionButton();
        List<String> expectedPortfoliosList = BrowserUtils.getElementsText(dashboardPage.portfolioNameList);
        dashboardPage.navigateToPageFromMenu("Regulatory Reporting");
        List<String> actualPortfoliosList = reportingPage.getPortfolioList();
        assertTestCase.assertTrue(expectedPortfoliosList.containsAll(actualPortfoliosList), "Regulatory Reporting Page - Portfolio list is verified");

        //Select a portfolio and validate the reporting for column is listed with year option dropdown.
        // The oldest available option should be 2019 and should not list any year before that
        reportingPage.selectPortfolioOptionByIndex(1);
        assertTestCase.assertTrue(reportingPage.reportingForListButtons.get(0).isEnabled(), "Reporting year dropdown is enabled");
        BrowserUtils.wait(2);
        reportingPage.reportingForList.get(0).click();
        BrowserUtils.wait(2);
        List<String> dropDownOptions = BrowserUtils.getElementsText(reportingPage.reportingForDropdownOptionsList);
        assertTestCase.assertTrue(dropDownOptions.contains("2019"), "Reporting for option oldest year is 2019 is verified");
        assertTestCase.assertFalse(dropDownOptions.contains("2018"), "Reporting for option oldest year is 2019 is verified");
        reportingPage.reportingForDropdownOptionsList.get(0).click();
        getExistingUsersAccessTokenFromUI();
        RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
        List<String> apiPortfoliosList = apiController.getPortfolioNames();
        //sort the list
        apiPortfoliosList.sort(String::compareToIgnoreCase);
        assertTestCase.assertEquals(apiPortfoliosList.size(), actualPortfoliosList.size(), "API Portfolio list size is verified");
        //print the list
        expectedPortfoliosList.forEach(System.out::println);
        for (String portfolioName : apiPortfoliosList) {
            System.out.println("portfolioName = " + portfolioName);
            assertTestCase.assertTrue(actualPortfoliosList.contains(portfolioName.trim()), "Portfolio name from API is verified in UI");
        }
    }

    @Test(groups = {REGRESSION, REGULATORY_REPORTING, API})
    @Xray(test = {11681})
    public void verifyDownloadHistory() {
        RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
        List<String> apiReportsList = apiController.getDownloadHistory().jsonPath().getList("report_name");
        assertTestCase.assertTrue(apiReportsList.size() > 0, "Verify downloaded reports available in Previously Downloaded reports list");

    }

    @Test(groups = {REGRESSION, REGULATORY_REPORTING, API, ENTITLEMENTS})
    @Xray(test = {11681})
    public void verifyDownloadHistoryWhenNoDownloadReports() {
        LoginPage login = new LoginPage();
        login.entitlementsLogin(EntitlementsBundles.NO_PREVIOUSLY_DOWNLOADED_REGULATORY_REPORTS);
        BrowserUtils.wait(20);
        getExistingUsersAccessTokenFromUI();

        RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
        List<String> apiReportsList1 = apiController.getDownloadHistory().jsonPath().getList("report_name");
        assertTestCase.assertTrue(apiReportsList1.size() == 0, "Verify downloaded reports not available in Previously Downloaded reports list");

    }

    @Test(groups = {REGRESSION, REGULATORY_REPORTING, API}, description = "Data Validation| MT | Regulatory Reporting | Validate Portfolio list and portfolio-details")
    @Xray(test = {11300})
    public void ValidatePortfolioListAndPortfolioDetails() {
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Regulatory Reporting");
        test.info("Navigated to Regulatory Reporting Page");
        getExistingUsersAccessTokenFromUI();
        RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
        PortfolioDetails[] apiResponse = apiController.getPortfolioDetails().as(PortfolioDetails[].class);
        for (PortfolioDetails portfolio : apiResponse) {
            String portfolioName = portfolio.getPortfolio_name();
            int index = reportingPage.selectPortfolioOptionByName(portfolioName);
            List<Integer> UIYears = BrowserUtils.convertStringListToIntList(reportingPage.getReportingFor_YearList(portfolioName, index), Integer::parseInt);
            if (portfolio.getReporting_years().size() > 0 && BrowserUtils.convertStringListToIntList(portfolio.getReporting_years(), Integer::parseInt).stream().min(Integer::compare).get() < 2019) {
                assertTestCase.assertTrue(UIYears.stream().min(Integer::compare).get() > 2018, "Validating that years are not showing less tyhan 2019");
            }
            reportingPage.deSelectPortfolioOptionByName(portfolioName);
        }
    }

    @Test(groups = {REGULATORY_REPORTING, API}, description = "Delete duplicate portfolios")
    public void deleteDuplicatePortfolios() {
        RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
        List<String> portfolioNames = apiController.getPortfolioNames();
        //sort the list
        portfolioNames.sort(String::compareToIgnoreCase);
        Map<String, Integer> portfolioNumbers = new HashMap<>();
        for (String portfolioName : portfolioNames) {
            if (portfolioNumbers.containsKey(portfolioName)) {
                portfolioNumbers.put(portfolioName, portfolioNumbers.get(portfolioName) + 1);
            } else {
                portfolioNumbers.put(portfolioName, 1);
            }
        }
        portfolioNumbers.forEach((key, value) -> {
            if (value > 1) System.out.println(key + " = " + value);
        });
        for (String portfolioName : portfolioNumbers.keySet()) {
            for (int i = 1; i < portfolioNumbers.get(portfolioName); i++) {
                apiController.deletePortfolio(apiController.getPortfolioId(portfolioName));
            }
        }
    }
@Test
    public void verifyBenchmarkFieldDashboard(){
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        DashboardPage dashboardPage = new DashboardPage();
    dashboardPage.navigateToPageFromMenu("Dashboard");

    RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
    List<String> portfolioFields = apiController.getPortfolioFields();

    BrowserUtils.wait(10);

    assertTestCase.assertFalse(portfolioFields.contains("benchmark"), "the benchmark field is not present");
    for (String name : portfolioFields ){
        System.out.println(name);
    }

    BrowserUtils.wait(10);

    //JsonPath benchmarkTest = apiController.getDashboardPortfolioDetails().jsonPath();
   // assertTestCase.assertEquals(benchmarkTest.getString("benchmark"), "benchmark", "benchmark field is present");



    }

    @Test(groups = {REGRESSION, REGULATORY_REPORTING, API}, description = "Data Validation| MT | Regulatory Reporting | Verify API Response with Valid Parameters")
    @Xray(test = {11408})
    public void VerifyAPIsResponseWithValidParameters() {

        RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();

        List<PortfolioDetails> portfolio = Arrays.asList(apiController.getPortfolioDetails().as(PortfolioDetails[].class));
        String portfolioId = portfolio.stream().filter(i -> !(i.getPortfolio_name().contains("Sample"))).findFirst().get().getPortfolio_id();
        Response response = apiController.getAysncGenerationAPIReposnse(portfolioId, DateTimeUtilities.getCurrentYear(), "Valid");
        response.prettyPrint();
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
        String reqID = response.jsonPath().getString("request_id");
        System.out.println(reqID);

        Response statusAPIResponse = apiController.getStatusAPIReposnse(reqID, "Valid");
        statusAPIResponse.prettyPrint();
        statusAPIResponse.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);

        //Wait until report is generated in db
        for (int i = 0; i < 5; i++) {
            BrowserUtils.wait(50);
            statusAPIResponse = apiController.getStatusAPIReposnse(reqID, "Valid");
            String status = statusAPIResponse.jsonPath().get("[0].request_status");
            if(!status.equalsIgnoreCase("pending")) break;
        }

        Response DownloadAPIResponse = apiController.getDownload(reqID, "Valid");
        DownloadAPIResponse.prettyPrint();
        DownloadAPIResponse.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);

    }

    @Test(groups = {REGRESSION, REGULATORY_REPORTING, API}, description = "Data Validation| MT | Regulatory Reporting | Verify API Response with InValid Parameters")
    @Xray(test = {11411})
    public void VerifyAPIsResponseWithInvalidParameters() {
        RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
        Response response = apiController.getAysncGenerationAPIReposnse("", DateTimeUtilities.getCurrentYear(), "Invalid");
        response.prettyPrint();
        response.then().assertThat()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .body("errorType", equalTo("ValueError"))
                .body("errorMessage", equalTo("Request Invalid. "));

        Response statusAPIResponse = apiController.getStatusAPIReposnse("", "Invalid");
        statusAPIResponse.prettyPrint();
        statusAPIResponse.then().assertThat()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .body("errorType", equalTo("ValueError"))
                .body("errorMessage", equalTo("Request Invalid. "));

        Response DownloadAPIResponse = apiController.getDownload("", "Invalid");
        DownloadAPIResponse.prettyPrint();
        DownloadAPIResponse.then().assertThat()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .body("errorType", equalTo("ValueError"))
                .body("errorMessage", equalTo("Request Invalid. "));
    }

    @Test(groups = {REGRESSION, REGULATORY_REPORTING, API}, description = "Data Validation| MT | Regulatory Reporting | Verify API Response with InValid Token")
    @Xray(test = {11412})
    public void VerifyAPIsResponseWithinvalidToken() {
        RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
        apiController.setInvalid();
        Response response = apiController.getAysncGenerationAPIReposnse("123456", DateTimeUtilities.getCurrentYear(), "Valid");
        response.prettyPrint();
        response.then().assertThat()
                .statusCode(401)
                .contentType(ContentType.JSON)
                .body("message", equalTo("Forbidden"));

        Response statusAPIResponse = apiController.getStatusAPIReposnse("11111", "Valid");
        statusAPIResponse.prettyPrint();
        statusAPIResponse.then().assertThat()
                .statusCode(401)
                .contentType(ContentType.JSON)
                .body("message", equalTo("Forbidden"));

        Response DownloadAPIResponse = apiController.getDownload("11111", "Valid");
        DownloadAPIResponse.prettyPrint();
        DownloadAPIResponse.then().assertThat()
                .statusCode(401)
                .contentType(ContentType.JSON)
                .body("message", equalTo("Forbidden"));
    }

    //TODO : Need to check with dev team to revise Status code and error messange in this method
    @Test(groups = {REGRESSION, REGULATORY_REPORTING, API}, description = "Data Validation| MT | Regulatory Reporting | Verify API Response in case of Different User's Portfolios and Request Ids")
    @Xray(test = {11424})
    public void VerifyAPIsResponseWithDifferentUserPortfolioAndRequestID() {

        RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
        String portfolioId = "0ba40cc2-4268-449d-91cc-98d1ae67a9e9";
        Response response = apiController.getAysncGenerationAPIReposnse(portfolioId, DateTimeUtilities.getCurrentYear(), "Valid");
        response.prettyPrint();
        response.then().assertThat()
                .statusCode(403)
                .contentType(ContentType.JSON);
        //.body("message", equalTo("Forbidden"));
        String reqID = "r-7d901735-839a-449e-abb9-68ee7587c2a5";
        System.out.println(reqID);

        Response statusAPIResponse = apiController.getStatusAPIReposnse(reqID, "Valid");
        statusAPIResponse.prettyPrint();
        statusAPIResponse.then().assertThat()
                .statusCode(401)
                .contentType(ContentType.JSON);
        //.body("message", equalTo("Forbidden"))

        Response DownloadAPIResponse = apiController.getDownload(reqID, "Valid");
        DownloadAPIResponse.prettyPrint();
        DownloadAPIResponse.then().assertThat()
                .statusCode(500)
                .contentType(ContentType.JSON);
        //.body("message", equalTo("Forbidden"));

    }


}

