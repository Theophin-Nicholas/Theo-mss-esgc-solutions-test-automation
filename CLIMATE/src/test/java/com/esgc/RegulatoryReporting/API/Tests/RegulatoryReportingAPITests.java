package com.esgc.RegulatoryReporting.API.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.RegulatoryReporting.API.APIModels.PortfolioDetails;
import com.esgc.RegulatoryReporting.API.Controllers.RegulatoryReportingAPIController;
import com.esgc.RegulatoryReporting.UI.Pages.RegulatoryReportingPage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import com.google.common.collect.Lists;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import java.util.*;
//import java.util.function.Function;
import com.google.common.base.Function;
//import com.google.common.collect.Lists;

import static com.esgc.Utilities.Groups.*;

public class RegulatoryReportingAPITests extends UITestBase {


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

    //TODO should be in API package
    @Test(groups = {"regression", "regulatoryReporting", "api"})
    @Xray(test = {11681})
    public void verifyDownloadHistory() {
        RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
        List<String> apiReportsList = apiController.getDownloadHistory().jsonPath().getList("report_name");
        assertTestCase.assertTrue(apiReportsList.size() > 0, "Verify downloaded reports available in Previously Downloaded reports list");

    }

    @Test(groups = {"regression", "regulatoryReporting", "api"})
    @Xray(test = {11681})
    public void verifyDownloadHistoryWhenNoDownloadReports() {
        LoginPage login = new LoginPage();
        login.clickOnLogout();
        login.entitlementsLogin(EntitlementsBundles.NO_PREVIOUSLY_DOWNLOADED_REGULATORY_REPORTS);
        BrowserUtils.wait(10);

        String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.accessToken";
        String accessToken = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
        System.setProperty("token", accessToken);

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
        RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
        PortfolioDetails[] apiResponse = apiController.getPortfolioDetails().as(PortfolioDetails[].class);
        for (PortfolioDetails portfolio : apiResponse) {
            String portfolioName = portfolio.getPortfolio_name();
            int index = reportingPage.selectPortfolioOptionByName(portfolioName);
            List<Integer> UIYears = BrowserUtils.convertStringListToIntList(reportingPage.getReportingFor_YearList(portfolioName, index), Integer::parseInt);
            if (BrowserUtils.convertStringListToIntList(portfolio.getReporting_years(), Integer::parseInt).stream().min(Integer::compare).get() < 2019) {
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
        portfolioNumbers.forEach((key, value) -> {if (value > 1) System.out.println(key+" = "+value);});
        for (String portfolioName : portfolioNumbers.keySet()) {
            for(int i = 1; i < portfolioNumbers.get(portfolioName); i++) {
                apiController.deletePortfolio(apiController.getPortfolioId(portfolioName));
            }
        }
    }
}

