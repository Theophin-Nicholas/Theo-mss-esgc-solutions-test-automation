package com.esgc.Tests.DataValidation.RegulatoryReportingPage;

import com.esgc.Pages.DashboardPage;
import com.esgc.Pages.PortfolioAnalysisPage.PhysicalRiskPages.PhysicalRiskManagementPages.PhysicalRiskManagementPage;
import com.esgc.Pages.RegulatoryReportingPage;
import com.esgc.Tests.TestBases.DataValidationTestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.List;

public class RegulatoryReportingPageExcelValidation extends DataValidationTestBase {

    RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
    @Test(groups = {"regression", "DataValidation", "regulatoryReporting"}, description = "Data Validation | Regulatory Reporting | Check the Data available on User Input History Tab of Annual Report")
    @Xray(test = {11112})
    public void verifyDataAvailableForUserInputHistoryTabForAnnualReportTest() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Regulatory Reporting");
        test.info("Navigated to Regulatory Reporting Page");
        //verify portfolio upload modal
        assertTestCase.assertTrue(reportingPage.uploadAnotherPortfolioLink.isDisplayed(), "Portfolio Upload button is displayed");

        //upload a portfolio
        String newPortfolioName = "PortfolioWithValidData2";
        assertTestCase.assertTrue(reportingPage.uploadPortfolio(newPortfolioName), "New Portfolio is added to the list");
        System.out.println(newPortfolioName + " uploaded");

        String currentWindow = BrowserUtils.getCurrentWindowHandle();
        newPortfolioName = "Portfolio Upload updated_good2";
        reportingPage.selectPortfolioOptionByName(newPortfolioName);
        reportingPage.selectAllPortfolioOptions();
        reportingPage.selectAnnualReports();
        List<String> selectedPortfolios = reportingPage.getSelectedPortfolioOptions();

        //verify create reports button before clicking
        assertTestCase.assertTrue(reportingPage.createReportsButton.isEnabled(), "Create report button is enabled");
        reportingPage.createReportsButton.click();
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
            assertTestCase.assertTrue(reportingPage.verifyReportsReadyToDownload(selectedPortfolios), "Reports are ready to download");
            System.out.println("Reports are ready to download");
            reportingPage.deleteFilesInDownloadsFolder();
            assertTestCase.assertTrue(reportingPage.verifyIfReportsDownloaded(), "Reports are downloaded");
            System.out.println("Reports are downloaded");
            assertTestCase.assertTrue(reportingPage.unzipReports(), "Reports are extracted");
            System.out.println("Reports are extracted");
            assertTestCase.assertTrue(reportingPage.verifyReportsContentForData(selectedPortfolios, selectedPortfolios.size()+1, "Exposure Amount in EUR"),
                    "Reports content is verified");
        } catch (Exception e) {
            e.printStackTrace();
            assertTestCase.assertTrue(false, "New tab verification failed");
        } finally {
            BrowserUtils.switchWindowsTo(currentWindow);
            System.out.println("=============================");
        }
        reportingPage.navigateToPageFromMenu("Portfolio Analysis");
        PhysicalRiskManagementPage portfolioAnalysisPage = new PhysicalRiskManagementPage();
        portfolioAnalysisPage.deletePortfolio(newPortfolioName);
    }

    @Test(groups = {"regression", "DataValidation", "regulatoryReporting"},
            description = "Check the Data available on the Report with SF when Use latest data is selected (Company Level Output Tab)")
    @Xray(test = {11111, 11231})
    public void downloadAndVerifyExcelReportsTest() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Regulatory Reporting");
        test.info("Navigated to Regulatory Reporting Page");
        assertTestCase.assertTrue(reportingPage.interimReportsOption.isDisplayed(), "Interim reports option is displayed");
        assertTestCase.assertTrue(reportingPage.useLatestDataOption.isDisplayed(), "Use latest data option is displayed");
        String currentWindow = BrowserUtils.getCurrentWindowHandle();
        reportingPage.selectAllPortfolioOptions();
        reportingPage.selectInterimReports();
        List<String> selectedPortfolios = reportingPage.getSelectedPortfolioOptions();

        //verify create reports button before clicking
        assertTestCase.assertTrue(reportingPage.createReportsButton.isEnabled(), "Create report button is enabled");
        reportingPage.createReportsButton.click();
        BrowserUtils.wait(2);
        String newTab1 = "";
        for (String tab : BrowserUtils.getWindowHandles()) {
            if (!tab.equals(currentWindow)) {
                newTab1 = tab;
            }
        }
        System.out.println("newTab = " + newTab1);
        System.out.println("currentWindow = " + currentWindow);
        try {
            //New tab should be opened and empty state message should be displayed as in the screenshot
            assertTestCase.assertTrue(reportingPage.verifyNewTabOpened(newTab1), "New tab is opened");
            System.out.println("New tab is opened");
            assertTestCase.assertTrue(reportingPage.verifyReportsReadyToDownload(selectedPortfolios), "Reports are ready to download");
            System.out.println("Reports are ready to download");
            reportingPage.deleteFilesInDownloadsFolder();
            assertTestCase.assertTrue(reportingPage.verifyIfReportsDownloaded(), "Reports are downloaded");
            System.out.println("Reports are downloaded");
            assertTestCase.assertTrue(reportingPage.unzipReports(), "Reports are extracted");
            System.out.println("Reports are extracted");
            assertTestCase.assertTrue(reportingPage.verifyReportsContent(selectedPortfolios), "Reports content is verified");
        } catch (Exception e) {
            e.printStackTrace();
            assertTestCase.assertTrue(false, "New tab verification failed");
        } finally {
            BrowserUtils.switchWindowsTo(currentWindow);
            System.out.println("=============================");
        }

        reportingPage.selectAnnualReports();
        assertTestCase.assertTrue(reportingPage.createReportsButton.isEnabled(), "Create report button is enabled");
        reportingPage.createReportsButton.click();
        BrowserUtils.wait(2);
        String newTab2 = "";
        for (String tab : BrowserUtils.getWindowHandles()) {
            if (!tab.equals(currentWindow) && !tab.equals(newTab1)) {
                newTab2 = tab;
            }
        }
        System.out.println("newTab2 = " + newTab2);
        try {
            //New tab should be opened and empty state message should be displayed as in the screenshot
            assertTestCase.assertTrue(reportingPage.verifyNewTabOpened(newTab2), "New tab is opened");
            System.out.println("New tab is opened");
            assertTestCase.assertTrue(reportingPage.verifyReportsReadyToDownload(), "Reports are ready to download");
            System.out.println("Reports are ready to download");
            reportingPage.deleteFilesInDownloadsFolder();
            assertTestCase.assertTrue(reportingPage.verifyIfReportsDownloaded(), "Reports are downloaded");
            System.out.println("Reports are downloaded");
            assertTestCase.assertTrue(reportingPage.unzipReports(), "Reports are extracted");
            System.out.println("Reports are extracted");
            assertTestCase.assertTrue(reportingPage.verifyReportsContentForAnnualReports(selectedPortfolios), "Reports content is verified");
        } catch (Exception e) {
            e.printStackTrace();
            assertTestCase.assertTrue(false, "New tab verification failed");
        } finally {
            BrowserUtils.switchWindowsTo(currentWindow);
        }
    }
}
