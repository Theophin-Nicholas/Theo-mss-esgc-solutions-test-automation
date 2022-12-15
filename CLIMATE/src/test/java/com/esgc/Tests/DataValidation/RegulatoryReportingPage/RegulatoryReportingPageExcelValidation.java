package com.esgc.Tests.DataValidation.RegulatoryReportingPage;

import com.esgc.Pages.DashboardPage;
import com.esgc.Pages.PortfolioAnalysisPage.PhysicalRiskPages.PhysicalRiskManagementPages.PhysicalRiskManagementPage;
import com.esgc.Pages.RegulatoryReportingPage;
import com.esgc.Tests.TestBases.DataValidationTestBase;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

public class RegulatoryReportingPageExcelValidation extends UITestBase {

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
            assertTestCase.assertTrue(reportingPage.verifyReportsContentForData(selectedPortfolios, 2, "Exposure Amount in EUR"),
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

    @Test(groups = {"regression", "DataValidation", "regulatoryReporting"}, description = "Data Validation | SFDR | Regulatory Reporting | Verify the portfolio coverage for the portfolio when SFDR reporting is selected")
    @Xray(test = {11730})
    public void verifyPortfolioCoverageForSFDRReportingTest() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Regulatory Reporting");
        test.info("Navigated to Regulatory Reporting Page");
        //verify portfolio upload modal
        assertTestCase.assertTrue(reportingPage.uploadAnotherPortfolioLink.isDisplayed(), "Portfolio Upload button is displayed");

        //upload a portfolio
        String portfolioName = "SFDRPortfolio";
        if (!reportingPage.verifyPortfolio(portfolioName)) reportingPage.uploadPortfolio(portfolioName);

        String currentWindow = BrowserUtils.getCurrentWindowHandle();
        reportingPage.selectPortfolioOptionByName(portfolioName);

        assertTestCase.assertTrue(reportingPage.verifySFDRPortfolioCoverageForUI(portfolioName),
                "SFDR portfolio coverage is verified for UI vs DB");
        List<String> selectedPortfolios = reportingPage.getSelectedPortfolioOptions();
        reportingPage.selectReportingFor(portfolioName,"2021");
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
            assertTestCase.assertTrue(reportingPage.verifyPortfolioCoverageForExcel(portfolioName),
                    "SFDR portfolio coverage is verified for Excel vs DB");
            System.out.println("SFDR portfolio coverage is verified for Excel vs DB");
        } catch (Exception e) {
            e.printStackTrace();
            assertTestCase.assertTrue(false, "New tab verification failed");
        } finally {
            BrowserUtils.switchWindowsTo(currentWindow);
            System.out.println("=============================");
        }
    }

    @Test(groups = {"regression", "DataValidation", "regulatoryReporting"},
            description = "Data Validation | EU Taxonomy | Regulatory Reporting | Verify the portfolio coverage for the portfolio when EU Taxonomy reporting is selected")
    @Xray(test = {11731})
    public void verifyPortfolioCoverageForEUTaxonomyReportingTest() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Regulatory Reporting");
        test.info("Navigated to Regulatory Reporting Page");
        //verify portfolio upload modal
        assertTestCase.assertTrue(reportingPage.uploadAnotherPortfolioLink.isDisplayed(), "Portfolio Upload button is displayed");

        //upload a portfolio
        String portfolioName = "SFDRPortfolio";
        if (!reportingPage.verifyPortfolio(portfolioName)) reportingPage.uploadPortfolio(portfolioName);

        String currentWindow = BrowserUtils.getCurrentWindowHandle();
        reportingPage.selectReportingOptionByName("EU Taxonomy");
        assertTestCase.assertEquals(reportingPage.getSelectedReportingOption(),"EU Taxonomy",
                "EU Taxonomy is selected");
        reportingPage.selectPortfolioOptionByName(portfolioName);

        assertTestCase.assertTrue(reportingPage.verifyEUTaxonomyPortfolioCoverageForUI(portfolioName),
                "EU Taxonomy portfolio coverage is verified for UI vs DB");
        List<String> selectedPortfolios = reportingPage.getSelectedPortfolioOptions();
        //verify create reports button before clicking
        assertTestCase.assertTrue(reportingPage.createReportsButton.isEnabled(), "Create report button is enabled");
        Set<String> currentWindows = BrowserUtils.getWindowHandles();
        reportingPage.createReportsButton.click();
        try {
            //New tab should be opened and empty state message should be displayed as in the screenshot
            assertTestCase.assertTrue(reportingPage.verifyNewTabOpened(currentWindows), "New tab is opened");
            System.out.println("New tab is opened");
            assertTestCase.assertTrue(reportingPage.verifyReportsReadyToDownload(selectedPortfolios), "Reports are ready to download");
            System.out.println("Reports are ready to download");
            reportingPage.deleteFilesInDownloadsFolder();
            assertTestCase.assertTrue(reportingPage.verifyIfReportsDownloaded(), "Reports are downloaded");
            System.out.println("Reports are downloaded");
            assertTestCase.assertTrue(reportingPage.unzipReports(), "Reports are extracted");
            System.out.println("Reports are extracted");
            assertTestCase.assertTrue(reportingPage.verifyPortfolioCoverageForExcel(portfolioName),
                    "EU Taxonomy portfolio coverage is verified for Excel vs DB");
            System.out.println("EU Taxonomy portfolio coverage is verified for Excel vs DB");
        } catch (Exception e) {
            e.printStackTrace();
            assertTestCase.assertTrue(false, "New tab verification failed");
        } finally {
            BrowserUtils.switchWindowsTo(currentWindow);
            System.out.println("=============================");
        }
    }

    @Test(groups = {"regression", "DataValidation", "regulatoryReporting"},
            description = "Data Validation | Regulatory Reporting | Verify the data downloaded in the generated annual report excel is for the latest data available for the portfolios irrespective of reporting year when use latest data filter is selected")
    @Xray(test = {10855})
    public void verifyLatestDataAvailableForPortfoliosWithLatestDataTest() {
        //todo: Step 8, 9 and 10 are not completed yet
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Regulatory Reporting");
        test.info("Navigated to Regulatory Reporting Page");
        //verify portfolio upload modal
        assertTestCase.assertTrue(reportingPage.uploadAnotherPortfolioLink.isDisplayed(), "Portfolio Upload button is displayed");

        //upload a portfolio
        String portfolioName = "SFDRPortfolio";
        if (!reportingPage.verifyPortfolio(portfolioName)) reportingPage.uploadPortfolio(portfolioName);

        String currentWindow = BrowserUtils.getCurrentWindowHandle();
        reportingPage.selectPortfolioOptionByName(portfolioName);
        reportingPage.selectUseLatestData();
        assertTestCase.assertTrue(reportingPage.isUseLatestDataSelected(), "Use latest data is selected");
        reportingPage.selectAnnualReports();
        assertTestCase.assertTrue(reportingPage.isAnnualReportsSelected(), "Annual reports is selected");

        List<String> selectedPortfolios = reportingPage.getSelectedPortfolioOptions();
        //verify create reports button before clicking
        assertTestCase.assertTrue(reportingPage.createReportsButton.isEnabled(), "Create report button is enabled");
        Set<String> currentWindows = BrowserUtils.getWindowHandles();
        reportingPage.createReportsButton.click();
        try {
            //New tab should be opened and empty state message should be displayed as in the screenshot
            assertTestCase.assertTrue(reportingPage.verifyNewTabOpened(currentWindows), "New tab is opened");
            System.out.println("New tab is opened");
            assertTestCase.assertTrue(reportingPage.verifyReportsReadyToDownload(selectedPortfolios), "Reports are ready to download");
            System.out.println("Reports are ready to download");
            reportingPage.deleteFilesInDownloadsFolder();
            assertTestCase.assertTrue(reportingPage.verifyIfReportsDownloaded(), "Reports are downloaded");
            System.out.println("Reports are downloaded");
            assertTestCase.assertTrue(reportingPage.unzipReports(), "Reports are extracted");
            System.out.println("Reports are extracted");
            assertTestCase.assertTrue(reportingPage.verifySFDRCompanyOutput(selectedPortfolios),
                    "SFDR Company output for portfolio coverage is verified for Excel vs DB");
            System.out.println("SFDR Company output for portfolio coverage is verified for Excel vs DB");
        } catch (Exception e) {
            e.printStackTrace();
            assertTestCase.assertTrue(false, "New tab verification failed");
        } finally {
            BrowserUtils.switchWindowsTo(currentWindow);
            System.out.println("=============================");
        }
    }
}
