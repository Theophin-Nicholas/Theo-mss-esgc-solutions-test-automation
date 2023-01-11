package com.esgc.RegulatoryReporting.API.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.RegulatoryReporting.API.Controllers.RegulatoryReportingAPIController;
import com.esgc.RegulatoryReporting.UI.Pages.RegulatoryReportingPage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import java.util.List;

import static com.esgc.Utilities.Groups.*;

public class RegulatoryReportingTests extends UITestBase {


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
        assertTestCase.assertEquals(apiPortfoliosList.size(),actualPortfoliosList.size(), "API Portfolio list size is verified");
        //print the list
        expectedPortfoliosList.forEach(System.out::println);
        for(String portfolioName : apiPortfoliosList){
            System.out.println("portfolioName = " + portfolioName);
            assertTestCase.assertTrue(actualPortfoliosList.contains(portfolioName.trim()), "Portfolio name from API is verified in UI");
        }
    }

    //TODO should be in API package
    @Test(groups = {"regression", "regulatoryReporting", "api"})
    @Xray(test = {11681})
    public void verifyDownloadHistory() {
        RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
        List<String>  apiReportsList = apiController.getDownloadHistory().jsonPath().getList("report_name");
        assertTestCase.assertTrue(apiReportsList.size()>0, "Verify downloaded reports available in Previously Downloaded reports list");

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
        List<String>  apiReportsList1 = apiController.getDownloadHistory().jsonPath().getList("report_name");
        assertTestCase.assertTrue(apiReportsList1.size()==0, "Verify downloaded reports not available in Previously Downloaded reports list");

    }
}
