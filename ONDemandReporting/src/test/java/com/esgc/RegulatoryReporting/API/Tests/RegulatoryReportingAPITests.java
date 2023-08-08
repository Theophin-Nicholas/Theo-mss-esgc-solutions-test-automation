package com.esgc.RegulatoryReporting.API.Tests ;


import com.esgc.Common.API.APIModels.Portfolio;
import com.esgc.Common.API.APIModels.PortfolioDetails;
import com.esgc.Common.API.Controllers.CommonAPIController;
import com.esgc.Common.API.TestBase.CommonTestBase;
import com.esgc.Common.UI.Pages.LoginPage;
import com.esgc.RegulatoryReporting.API.Controllers.RegulatoryReportingAPIController;
import com.esgc.RegulatoryReporting.UI.Pages.RegulatoryReportingPage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.DateTimeUtilities;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.*;

import static com.esgc.Utilities.Groups.*;
import static org.hamcrest.Matchers.equalTo;

public class RegulatoryReportingAPITests extends CommonTestBase {

    @Test(groups = {REGRESSION, REGULATORY_REPORTING, API}, description = "Data Validation| MT | Regulatory Reporting | Validate Portfolio list and portfolio-details")
    @Xray(test = {4028})
    public void verifyPortfolioPortfolioDetailsListTest() {
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        
        //Verify all the portfolios that the user has uploaded, are listed in the "select portfolio" section.
        reportingPage.navigateToPageFromMenu("Climate Dashboard");
        test.info("Navigated to Dashboard Page");
        reportingPage.clickPortfolioSelectionButton();
        List<String> expectedPortfoliosList = BrowserUtils.getElementsText(reportingPage.dashboardPortfolioNamesList);
        reportingPage.navigateToReportingService("SFDR");
        reportingPage.waitForPortfolioTableToLoad();
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

        //subtract sample portfolio
        assertTestCase.assertEquals(apiPortfoliosList.size()-1, actualPortfoliosList.size(), "API Portfolio list size is verified");
        //print the list
        expectedPortfoliosList.forEach(System.out::println);
        for (String portfolioName : actualPortfoliosList) {
            System.out.println("portfolioName = " + portfolioName);
            assertTestCase.assertTrue(apiPortfoliosList.contains(portfolioName.trim()), "Portfolio name from API is verified in UI");
        }
    }

    @Test(groups = {REGRESSION, REGULATORY_REPORTING, API})
    @Xray(test = {3900})
    public void verifyDownloadHistory() {
        RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
        List<String> apiReportsList = apiController.getDownloadHistory().jsonPath().getList("report_name");
        assertTestCase.assertTrue(apiReportsList.size() > 0, "Verify downloaded reports available in Previously Downloaded reports list");

    }

    @Test(groups = {REGRESSION, REGULATORY_REPORTING, API, ENTITLEMENTS})
    @Xray(test = {3900})
    public void verifyDownloadHistoryWhenNoDownloadReports() {
        LoginPage login = new LoginPage();
        login.clickOnLogout();
        login.entitlementsLogin(EntitlementsBundles.NO_PREVIOUSLY_DOWNLOADED_REGULATORY_REPORTS);

        RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
        List<String> apiReportsList1 = apiController.getDownloadHistory().jsonPath().getList("report_name");
        assertTestCase.assertTrue(apiReportsList1.size() == 0, "Verify downloaded reports not available in Previously Downloaded reports list");
        login.clickOnLogout();
        login.login();
    }

    @Test(groups = {REGRESSION, REGULATORY_REPORTING, API}, description = "Data Validation| MT | Regulatory Reporting | Validate Portfolio list and portfolio-details")
    @Xray(test = {3794})
    public void ValidatePortfolioListAndPortfolioDetails() {
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        
        reportingPage.navigateToReportingService("SFDR");
        test.info("Navigated to Regulatory Reporting Page");
        getExistingUsersAccessTokenFromUI();
        Response response = CommonAPIController.getPortfolioDetails();
        List<PortfolioDetails> portfolios = Collections.singletonList(response.as(Portfolio.class)).get(0).getPortfolios();

        for (PortfolioDetails portfolio : portfolios) {
            String portfolioName = portfolio.getPortfolio_name();
            System.out.println("portfolioName = " + portfolioName);
            int index = reportingPage.selectPortfolioOptionByName(portfolioName);
            if (index == -1) continue;
            if (portfolio.getReporting_years() != null && portfolio.getReporting_years().size() > 0
                    && BrowserUtils.convertStringListToIntList(portfolio.getReporting_years(), Integer::parseInt).stream().min(Integer::compare).get() < 2019) {
                List<Integer> UIYears = BrowserUtils.convertStringListToIntList(reportingPage.getReportingFor_YearList(portfolioName, index), Integer::parseInt);
                assertTestCase.assertTrue(UIYears.stream().min(Integer::compare).get() > 2018, "Validating that years are not showing less than 2019");
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
                CommonAPIController.deletePortfolio(apiController.getPortfolioId(portfolioName));
                System.out.println("Remaining portfolios = " + apiController.getPortfolioNames().size());
            }
        }
    }

    @Test
    public void verifyBenchmarkFieldDashboard(){
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        
        reportingPage.navigateToPageFromMenu("Dashboard");

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
    @Xray(test = {3716})
    public void VerifyAPIsResponseWithValidParameters() {

        RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
        Response response = CommonAPIController.getPortfolioDetails();
        List<PortfolioDetails> portfolio = Collections.singletonList(response.as(Portfolio.class)).get(0).getPortfolios();
        String portfolioId = portfolio.stream().filter(i -> !(i.getPortfolio_name().contains("Sample"))).findFirst().get().getPortfolio_id();
        response = apiController.getAysncGenerationAPIReposnse(portfolioId, DateTimeUtilities.getCurrentYear(), "Valid");
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
    @Xray(test = {3413})
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
    @Xray(test = {3700})
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
    @Xray(test = {3396})
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
