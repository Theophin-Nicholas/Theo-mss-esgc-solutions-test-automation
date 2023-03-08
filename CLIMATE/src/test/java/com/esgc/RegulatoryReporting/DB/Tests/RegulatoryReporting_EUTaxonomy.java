package com.esgc.RegulatoryReporting.DB.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Dashboard.UI.Pages.*;
import com.esgc.PortfolioAnalysis.UI.Pages.PhysicalRiskPages.PhysicalRiskManagementPages.PhysicalRiskManagementPage;
import com.esgc.RegulatoryReporting.API.Controllers.RegulatoryReportingAPIController;
import com.esgc.RegulatoryReporting.UI.Pages.RegulatoryReportingPage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

import static com.esgc.Utilities.Groups.*;

public class RegulatoryReporting_EUTaxonomy extends UITestBase {


    RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
    @Test(groups = {REGRESSION, DATA_VALIDATION, REGULATORY_REPORTING}, description = "Data Validation | EU Taxonomy Regulatory Reporting")
    @Xray(test = {11993, 11994, 11995})
    public void verifyEUTaxonomyReport() {
        PhysicalRiskManagementPage portfolioAnalysisPage = new PhysicalRiskManagementPage();
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Regulatory Reporting");
        test.info("Navigated to Regulatory Reporting Page");
        //verify portfolio upload modal
        assertTestCase.assertTrue(reportingPage.isUploadAnotherPortfolioLinkDisplayed(), "Portfolio Upload button is displayed");

        //upload a portfolio
        String portfolioPropertyName = "PortfolioWithValidData2";
        String portfolioName = "Portfolio Upload updated_good2";
        if(!portfolioAnalysisPage.verifyPortfolio(portfolioName)) {
            BrowserUtils.ActionKeyPress(Keys.ESCAPE);
            assertTestCase.assertTrue(reportingPage.uploadPortfolio(portfolioPropertyName), "New Portfolio is added to the list");
            System.out.println(portfolioName + " uploaded");
        }

        String currentWindow = BrowserUtils.getCurrentWindowHandle();

        RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
        String portfolioId = apiController.getPortfolioId(portfolioName);
        reportingPage.clickOnEUTaxonomy();
        reportingPage.selectPortfolioOptionByName(portfolioName);
        reportingPage.enterEUTaxonomyValues(portfolioName, "100", "1000");
        //verify create reports button before clicking
        assertTestCase.assertTrue(reportingPage.isCreateReportsButtonEnabled(), "Create report button is enabled");
        reportingPage.clickOnCreateReportsButton();
        BrowserUtils.wait(2);
        String newTab1 = "";
        for (String tab : BrowserUtils.getWindowHandles()) {
            if (!tab.equals(currentWindow)) {
                newTab1 = tab;
            }
        }

        try {
            //New tab should be opened and empty state message should be displayed as in the screenshot
            assertTestCase.assertTrue(reportingPage.verifyNewTabOpened(newTab1), "New tab is opened");
            System.out.println("New tab is opened");
            System.out.println("Reports are ready to download");
            reportingPage.deleteFilesInDownloadsFolder();
            assertTestCase.assertTrue(reportingPage.verifyIfReportsDownloaded(), "Reports are downloaded");
            System.out.println("Reports are downloaded");
            assertTestCase.assertTrue(reportingPage.unzipReports(), "Reports are extracted");
            System.out.println("Reports are extracted");
            assertTestCase.assertTrue(reportingPage.verifyEUReportsUnderLyingDataOverview(portfolioId), "Validate Underlying Overview tab data");
            assertTestCase.assertTrue(reportingPage.verifyEUReportsUnderlyingDataActivities(portfolioId), "Validate Underlying Activity tab data");
            assertTestCase.assertTrue(reportingPage.verifyEUReportsGreenInvestmentRatio(portfolioId,"100","1000") , "Validate GreenInvestmentRatio tab data");
        } catch (Exception e) {
            e.printStackTrace();
            assertTestCase.assertTrue(false, "New tab verification failed");
        } finally {
            BrowserUtils.switchWindowsTo(currentWindow);
            System.out.println("=============================");
        }
    }
}
