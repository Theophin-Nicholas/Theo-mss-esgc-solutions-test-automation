package com.esgc.RegulatoryReporting.DB.Tests;


import com.esgc.Common.UI.TestBases.UITestBase;
import com.esgc.RegulatoryReporting.API.Controllers.RegulatoryReportingAPIController;
import com.esgc.RegulatoryReporting.UI.Pages.RegulatoryReportingPage;
import com.esgc.TestBase.TestBase;

import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.DateTimeUtilities;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

import static com.esgc.Utilities.Groups.*;

public class RegulatoryReportingPageExcelValidation extends UITestBase {

    @Test(groups = {REGRESSION, DATA_VALIDATION, REGULATORY_REPORTING, ROBOT_DEPENDENCY, UI}, description = "Data Validation | Regulatory Reporting | Check the Data available on User Input History Tab of Annual Report")
    @Xray(test = {11112})
    public void verifyDataAvailableForUserInputHistoryTabForAnnualReportTest() {
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        
        reportingPage.navigateToReportingService("SFDR");
        TestBase.test.info("Navigated to Regulatory Reporting Page");
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
        Set<String> currentWindows = BrowserUtils.getWindowHandles();
        //verify create reports button before clicking
        assertTestCase.assertTrue(reportingPage.createReportsButton.isEnabled(), "Create report button is enabled");
        reportingPage.clickOnCreateReportsButton();

        try {
            //New tab should be opened and empty state message should be displayed as in the screenshot
            assertTestCase.assertTrue(reportingPage.verifyNewTabOpened(currentWindows), "New tab is opened");
            System.out.println("New tab is opened");
            assertTestCase.assertTrue(reportingPage.verifyReportsReadyToDownload(selectedPortfolios), "Reports are ready to download");
            System.out.println("Reports are ready to download");
            reportingPage.deleteFilesInDownloadsFolder();
            reportingPage.deleteFilesInDownloadsFolder();
            assertTestCase.assertTrue(reportingPage.verifyIfReportsDownloaded(), "Reports are downloaded");
            System.out.println("Reports are downloaded");
            assertTestCase.assertTrue(reportingPage.unzipReports(), "Reports are extracted");
            System.out.println("Reports are extracted");
            assertTestCase.assertTrue(reportingPage.verifyReportsContentForData(selectedPortfolios, "User Input History", "Exposure Amount in EUR"),
                    "Reports Data is verified with snowflake");
        } catch (Exception e) {
            e.printStackTrace();
            assertTestCase.assertTrue(false, "New tab verification failed");
        } finally {
            Driver.closeBrowserTab();
            BrowserUtils.switchWindowsTo(currentWindow);
            System.out.println("=============================");
        }
        RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
        apiController.deletePortfolio(apiController.getPortfolioId(newPortfolioName));
    }

    @Test(groups = {REGRESSION, DATA_VALIDATION, REGULATORY_REPORTING, UI},
            description = "Check the Data available on the Report with SF when Use latest data is selected (Company Level Output Tab)")
    @Xray(test = {11111, 11231})
    public void downloadAndVerifyExcelReportsTest() {
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        
        reportingPage.navigateToPageFromMenu("ESG Reporting Portal");
        TestBase.test.info("Navigated to Regulatory Reporting Page");
        reportingPage.selectReportingOptionByName("SFDR PAIs");
        assertTestCase.assertTrue(reportingPage.isInterimReportsOptionDisplayed(), "Interim reports option is displayed");
        assertTestCase.assertTrue(reportingPage.isUseLatestDataOptionDisplayed(), "Use latest data option is displayed");
        reportingPage.selectUseLatestData();
        String currentWindow = BrowserUtils.getCurrentWindowHandle();
        reportingPage.selectAllPortfolioOptions();
        reportingPage.selectInterimReports();
        List<String> selectedPortfolios = reportingPage.getSelectedPortfolioOptions();
        Set<String> currentWindows = BrowserUtils.getWindowHandles();
        //verify create reports button before clicking
        assertTestCase.assertTrue(reportingPage.isCreateReportsButtonEnabled(), "Create report button is enabled");

        reportingPage.clickOnCreateReportsButton();
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
            assertTestCase.assertTrue(reportingPage.verifySFDRCompanyOutput(selectedPortfolios, "latest"), "Reports content is verified");
        } catch (Exception e) {
            e.printStackTrace();
            assertTestCase.assertTrue(false, "New tab verification failed");
        } finally {
            Driver.closeBrowserTab();
            BrowserUtils.switchWindowsTo(currentWindow);
            System.out.println("=============================");
        }
        reportingPage.selectAnnualReports();
        assertTestCase.assertTrue(reportingPage.isCreateReportsButtonEnabled(), "Create report button is enabled");
        currentWindows = BrowserUtils.getWindowHandles();
        reportingPage.clickOnCreateReportsButton();
        BrowserUtils.wait(2);
        try {
            //New tab should be opened and empty state message should be displayed as in the screenshot
            assertTestCase.assertTrue(reportingPage.verifyNewTabOpened(currentWindows), "New tab is opened");
            System.out.println("New tab is opened");
            assertTestCase.assertTrue(reportingPage.verifyReportsReadyToDownload(), "Reports are ready to download");
            System.out.println("Reports are ready to download");
            reportingPage.deleteFilesInDownloadsFolder();
            assertTestCase.assertTrue(reportingPage.verifyIfReportsDownloaded(), "Reports are downloaded");
            System.out.println("Reports are downloaded");
            assertTestCase.assertTrue(reportingPage.unzipReports(), "Reports are extracted");
            System.out.println("Reports are extracted");
            assertTestCase.assertTrue(reportingPage.verifySFDRCompanyOutput(selectedPortfolios,"latest"), "Reports content is verified");
        } catch (Exception e) {
            e.printStackTrace();
            assertTestCase.assertTrue(false, "New tab verification failed");
        } finally {
            Driver.closeBrowserTab();
            BrowserUtils.switchWindowsTo(currentWindow);
        }
    }

    @Test(groups = {REGRESSION, DATA_VALIDATION, REGULATORY_REPORTING, UI}, description = "Data Validation | SFDR | Regulatory Reporting | Verify the portfolio coverage for the portfolio when SFDR reporting is selected")
    @Xray(test = {11730})
    public void verifyPortfolioCoverageForSFDRReportingTest() {
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        
        reportingPage.navigateToPageFromMenu("ESG Reporting Portal");
        TestBase.test.info("Navigated to Regulatory Reporting Page");
        //verify portfolio upload modal
        assertTestCase.assertTrue(reportingPage.uploadAnotherPortfolioLink.isDisplayed(), "Portfolio Upload button is displayed");
        reportingPage.selectReportingOptionByName("SFDR PAIs");

        //upload a portfolio
        String portfolioName = "SFDRPortfolio";
        if (!reportingPage.verifyPortfolio(portfolioName)) reportingPage.uploadPortfolio(portfolioName);

        String currentWindow = BrowserUtils.getCurrentWindowHandle();
        reportingPage.selectPortfolioOptionByName(portfolioName);

        assertTestCase.assertTrue(reportingPage.verifySFDRPortfolioCoverageForUI(portfolioName),
                "SFDR portfolio coverage is verified for UI vs DB");
        List<String> selectedPortfolios = reportingPage.getSelectedPortfolioOptions();
        Set<String> currentWindows = BrowserUtils.getWindowHandles();
        reportingPage.selectReportingFor(portfolioName,"2021");
        //verify create reports button before clicking
        assertTestCase.assertTrue(reportingPage.createReportsButton.isEnabled(), "Create report button is enabled");
        reportingPage.clickOnCreateReportsButton();

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
            assertTestCase.assertTrue(reportingPage.verifyPortfolioCoverageForExcel(portfolioName,"SFDR_COVERAGE"),
                    "SFDR portfolio coverage is verified for Excel vs DB");
            System.out.println("SFDR portfolio coverage is verified for Excel vs DB");
        } catch (Exception e) {
            e.printStackTrace();
            assertTestCase.assertTrue(false, "New tab verification failed");
        } finally {
            Driver.closeBrowserTab();
            BrowserUtils.switchWindowsTo(currentWindow);
            System.out.println("=============================");
        }
    }

    @Test(groups = {REGRESSION, DATA_VALIDATION, REGULATORY_REPORTING, UI},
            description = "Data Validation | EU Taxonomy | Regulatory Reporting | Verify the portfolio coverage for the portfolio when EU Taxonomy reporting is selected")
    @Xray(test = {11731})
    public void verifyPortfolioCoverageForEUTaxonomyReportingTest() {
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        
        reportingPage.navigateToReportingService("EU");
        TestBase.test.info("Navigated to Regulatory Reporting Page");
        //verify portfolio upload modal
        assertTestCase.assertTrue(reportingPage.verifyPortfolioUploadLink(), "Portfolio Upload button is displayed");

        //upload a portfolio
        String portfolioName = "SFDRPortfolio";
        if (!reportingPage.verifyPortfolio(portfolioName)) reportingPage.uploadPortfolio(portfolioName);

        String currentWindow = BrowserUtils.getCurrentWindowHandle();
        //reportingPage.selectReportingOptionByName("EU Taxonomy");
        assertTestCase.assertEquals(reportingPage.getSelectedReportingOption(),"EU Taxonomy",
                "EU Taxonomy is selected");
        reportingPage.selectPortfolioOptionByName(portfolioName);

        assertTestCase.assertTrue(reportingPage.verifyEUTaxonomyPortfolioCoverageForUI(portfolioName),
                "EU Taxonomy portfolio coverage is verified for UI vs DB");
        List<String> selectedPortfolios = reportingPage.getSelectedPortfolioOptions();
        //verify create reports button before clicking
        assertTestCase.assertTrue(reportingPage.isCreateReportsButtonEnabled(), "Create report button is enabled");
        Set<String> currentWindows = BrowserUtils.getWindowHandles();
        reportingPage.clickOnCreateReportsButton();
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
            assertTestCase.assertTrue(reportingPage.verifyPortfolioCoverageForExcel(portfolioName, "TAXONOMY_COVERAGE"),
                    "EU Taxonomy portfolio coverage is verified for Excel vs DB");
            System.out.println("EU Taxonomy portfolio coverage is verified for Excel vs DB");
        } catch (Exception e) {
            e.printStackTrace();
            assertTestCase.assertTrue(false, "New tab verification failed");
        } finally {
            Driver.closeBrowserTab();
            BrowserUtils.switchWindowsTo(currentWindow);
            System.out.println("=============================");
        }
    }

    @Test(groups = {REGRESSION, DATA_VALIDATION, REGULATORY_REPORTING, UI},
            description = "Data Validation | Regulatory Reporting | Verify the data downloaded in the generated annual report excel is for the latest data available for the portfolios irrespective of reporting year when use latest data filter is selected")
    @Xray(test = {10855})
    public void verifyLatestDataAvailableForPortfoliosWithLatestDataTest() {
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        
        reportingPage.navigateToPageFromMenu("ESG Reporting Portal");
        String year = "2021";
        TestBase.test.info("Navigated to Regulatory Reporting Page");
        //verify portfolio upload modal
        assertTestCase.assertTrue(reportingPage.uploadAnotherPortfolioLink.isDisplayed(), "Portfolio Upload button is displayed");
        reportingPage.selectReportingOptionByName("SFDR PAIs");

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
        assertTestCase.assertTrue(reportingPage.isCreateReportsButtonEnabled(), "Create report button is enabled");
        Set<String> currentWindows = BrowserUtils.getWindowHandles();
        reportingPage.clickOnCreateReportsButton();
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
            assertTestCase.assertTrue(reportingPage.verifySFDRCompanyOutput(selectedPortfolios,year),
                    "SFDR Company output for portfolio coverage is verified for Excel vs DB");
            assertTestCase.assertTrue(reportingPage.verifyUserInputHistory(selectedPortfolios),
                    "User input history for portfolio coverage is verified for Excel vs DB");
            System.out.println("SFDR Company output and User input history for portfolio coverage is verified for Excel vs DB");
            assertTestCase.assertTrue(reportingPage.verifyPortfolioLevelOutput(selectedPortfolios, year,"Annual","Yes"),
                    "Portfolio level output for portfolio coverage is verified for Excel vs DB");
            System.out.println("Portfolio level output for portfolio coverage is verified for Excel vs DB");
        } catch (Exception e) {
            e.printStackTrace();
            assertTestCase.assertTrue(false, "New tab verification failed");
        } finally {
            Driver.closeBrowserTab();
            BrowserUtils.switchWindowsTo(currentWindow);
            System.out.println("=============================");
        }
    }

    @Test(groups = {REGRESSION, DATA_VALIDATION, REGULATORY_REPORTING, UI},
            description = "Data Validation | Regulatory Reporting | Verify the data downloaded in the generated annual report excel is for the latest data available for the portfolios irrespective of reporting year when use latest data filter is selected")
    @Xray(test = {10852,10853, 11200,11388})
    public void verifyAnnualReportWithoutLatestDataTest() {
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        
        reportingPage.navigateToPageFromMenu("ESG Reporting Portal");
        String year = "2021";
        TestBase.test.info("Navigated to Regulatory Reporting Page");
        //verify portfolio upload modal
        assertTestCase.assertTrue(reportingPage.verifyPortfolioUploadLink(), "Portfolio Upload button is displayed");
        reportingPage.selectReportingOptionByName("SFDR PAIs");

        //upload a portfolio
        String portfolioName = "SFDRPortfolio";
        if (!reportingPage.verifyPortfolio(portfolioName)) reportingPage.uploadPortfolio(portfolioName);

        String currentWindow = BrowserUtils.getCurrentWindowHandle();
        reportingPage.selectPortfolioOptionByName(portfolioName);
       // reportingPage.selectUseLatestData();
       // assertTestCase.assertTrue(reportingPage.isUseLatestDataSelected(), "Use latest data is selected");
        reportingPage.selectReportingFor(portfolioName, year);
        reportingPage.selectAnnualReports();
        assertTestCase.assertTrue(reportingPage.isAnnualReportsSelected(), "Annual reports is selected");

        List<String> selectedPortfolios = reportingPage.getSelectedPortfolioOptions();
        //verify create reports button before clicking
        assertTestCase.assertTrue(reportingPage.isCreateReportsButtonEnabled(), "Create report button is enabled");
        Set<String> currentWindows = BrowserUtils.getWindowHandles();
        reportingPage.clickOnCreateReportsButton();
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
            assertTestCase.assertTrue(reportingPage.verifySFDRCompanyOutput(selectedPortfolios,year),
                    "SFDR Company output for portfolio coverage is verified for Excel vs DB");
            assertTestCase.assertTrue(reportingPage.verifyUserInputHistory(selectedPortfolios),
                    "User input history for portfolio coverage is verified for Excel vs DB");
            System.out.println("SFDR Company output and User input history for portfolio coverage is verified for Excel vs DB");
            assertTestCase.assertTrue(reportingPage.verifyPortfolioLevelOutput(selectedPortfolios, year,"Annual","No"),
                    "Portfolio level output for portfolio coverage is verified for Excel vs DB");
            System.out.println("Portfolio level output for portfolio coverage is verified for Excel vs DB");
        } catch (Exception e) {
            e.printStackTrace();
            assertTestCase.assertTrue(false, "New tab verification failed");
        } finally {
            Driver.closeBrowserTab();
            BrowserUtils.switchWindowsTo(currentWindow);
            System.out.println("=============================");
        }
    }


    @Test(groups = {REGRESSION, DATA_VALIDATION, REGULATORY_REPORTING, UI},
            description = "Data Validation | Regulatory Reporting | Verify the data downloaded in the generated Interim report excel when the portfolios has data for the selected year")
    @Xray(test = {11352, 11367,11200,11388})
    public void verifyInterimReportForSelectedYearTest() {
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        
        reportingPage.navigateToPageFromMenu("ESG Reporting Portal");
        String year = "2020";
        TestBase.test.info("Navigated to Regulatory Reporting Page");
        //verify portfolio upload modal
        assertTestCase.assertTrue(reportingPage.uploadAnotherPortfolioLink.isDisplayed(), "Portfolio Upload button is displayed");
        reportingPage.selectReportingOptionByName("SFDR PAIs");

        //upload a portfolio
        String portfolioName = "SFDRPortfolio";
        if (!reportingPage.verifyPortfolio(portfolioName)) reportingPage.uploadPortfolio(portfolioName);

        String currentWindow = BrowserUtils.getCurrentWindowHandle();
        reportingPage.selectPortfolioOptionByName(portfolioName);
        reportingPage.selectReportingFor(portfolioName, year);

        List<String> selectedPortfolios = reportingPage.getSelectedPortfolioOptions();
        //verify create reports button before clicking
        assertTestCase.assertTrue(reportingPage.isCreateReportsButtonEnabled(), "Create report button is enabled");
        Set<String> currentWindows = BrowserUtils.getWindowHandles();
        reportingPage.clickOnCreateReportsButton();
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
            assertTestCase.assertTrue(reportingPage.verifySFDRCompanyOutput(selectedPortfolios,year),
                    "SFDR Company output for portfolio coverage is verified for Excel vs DB");
            assertTestCase.assertTrue(reportingPage.verifyUserInputHistory(selectedPortfolios),
                    "User input history for portfolio coverage is verified for Excel vs DB");
            System.out.println("SFDR Company output and User input history for portfolio coverage is verified for Excel vs DB");
            assertTestCase.assertTrue(reportingPage.verifyPortfolioLevelOutput(selectedPortfolios, year,"Interim","No"),
                    "Portfolio level output for portfolio coverage is verified for Excel vs DB");
            System.out.println("Portfolio level output for portfolio coverage is verified for Excel vs DB");
        } catch (Exception e) {
            e.printStackTrace();
            assertTestCase.assertTrue(false, "New tab verification failed");
        } finally {
            Driver.closeBrowserTab();
            BrowserUtils.switchWindowsTo(currentWindow);
            System.out.println("=============================");
        }
    }

    @Test(groups = {REGRESSION, DATA_VALIDATION, REGULATORY_REPORTING, UI},
            description = "Data Validation | Regulatory Reporting | Verify the data downloaded in the generated Interim report excel when the portfolios has data for the selected year")
    @Xray(test = {11368, 11717})
    public void verifyInterimReportForLatestDataTest() {
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        
        reportingPage.navigateToReportingService("SFDR");
        String year = "2021";
        TestBase.test.info("Navigated to Regulatory Reporting Page");
        //verify portfolio upload modal
        assertTestCase.assertTrue(reportingPage.uploadAnotherPortfolioLink.isDisplayed(), "Portfolio Upload button is displayed");

        //upload a portfolio
        String portfolioName = "SFDRPortfolio";
        if (!reportingPage.verifyPortfolio(portfolioName)) reportingPage.uploadPortfolio(portfolioName);

        String currentWindow = BrowserUtils.getCurrentWindowHandle();
        reportingPage.selectPortfolioOptionByName(portfolioName);
        reportingPage.selectUseLatestData();

        List<String> selectedPortfolios = reportingPage.getSelectedPortfolioOptions();
        //verify create reports button before clicking
        assertTestCase.assertTrue(reportingPage.isCreateReportsButtonEnabled(), "Create report button is enabled");
        Set<String> currentWindows = BrowserUtils.getWindowHandles();
        reportingPage.clickOnCreateReportsButton();
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
            assertTestCase.assertTrue(reportingPage.verifySFDRCompanyOutput(selectedPortfolios,year),
                    "SFDR Company output for portfolio coverage is verified for Excel vs DB");
            assertTestCase.assertTrue(reportingPage.verifyUserInputHistory(selectedPortfolios),
                    "User input history for portfolio coverage is verified for Excel vs DB");
            System.out.println("SFDR Company output and User input history for portfolio coverage is verified for Excel vs DB");
            assertTestCase.assertTrue(reportingPage.verifyPortfolioLevelOutput(selectedPortfolios, year,"Interim","Yes"),
                    "Portfolio level output for portfolio coverage is verified for Excel vs DB");
            System.out.println("Portfolio level output for portfolio coverage is verified for Excel vs DB");
        } catch (Exception e) {
            e.printStackTrace();
            assertTestCase.assertTrue(false, "New tab verification failed");
        } finally {
            Driver.closeBrowserTab();
            BrowserUtils.switchWindowsTo(currentWindow);
            System.out.println("=============================");
        }
    }

    @Test(groups = {REGRESSION, DATA_VALIDATION, REGULATORY_REPORTING, UI},
            description = "Data Validation | UI | Regulatory Reporting | Verify data available in Company level Output across different reports")
    @Xray(test = {11716})
    public void verifyCompanyLevelOutputTest() {
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        
        reportingPage.navigateToReportingService("SFDR");
        TestBase.test.info("Navigated to Regulatory Reporting Page");
        //verify portfolio upload modal
        assertTestCase.assertTrue(reportingPage.verifyPortfolioUploadLink(), "Portfolio Upload button is displayed");

        //upload a portfolio
        String portfolioName = "SFDRPortfolio";
        if (!reportingPage.verifyPortfolio(portfolioName)) reportingPage.uploadPortfolio(portfolioName);

        String currentWindow = BrowserUtils.getCurrentWindowHandle();
        reportingPage.selectPortfolioOptionByName(portfolioName);

        List<String> selectedPortfolios = reportingPage.getSelectedPortfolioOptions();
        //verify create reports button before clicking
        assertTestCase.assertTrue(reportingPage.isCreateReportsButtonEnabled(), "Create report button is enabled");
        Set<String> currentWindows = BrowserUtils.getWindowHandles();
        reportingPage.clickOnCreateReportsButton();
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
            assertTestCase.assertTrue(reportingPage.verifySFDRCompanyOutput(selectedPortfolios,DateTimeUtilities.getCurrentYear(-1)),
                    "SFDR Company output for portfolio coverage is verified for Excel vs DB");
        } catch (Exception e) {
            e.printStackTrace();
            assertTestCase.assertTrue(false, "New tab verification failed");
        } finally {
            Driver.closeBrowserTab();
            BrowserUtils.switchWindowsTo(currentWindow);
            System.out.println("=============================");
        }
        reportingPage.selectUseLatestData();
        currentWindows = BrowserUtils.getWindowHandles();
        reportingPage.clickOnCreateReportsButton();
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
            assertTestCase.assertTrue(reportingPage.verifySFDRCompanyOutput(selectedPortfolios,"latest"),
                    "SFDR Company output for portfolio coverage is verified for Excel vs DB");
        } catch (Exception e) {
            e.printStackTrace();
            assertTestCase.assertTrue(false, "New tab verification failed");
        } finally {
            Driver.closeBrowserTab();
            BrowserUtils.switchWindowsTo(currentWindow);
            System.out.println("=============================");
        }
    }

    @Test(groups = {REGRESSION, DATA_VALIDATION, REGULATORY_REPORTING, UI},
            description = "Data Validation | UI | Regulatory Reporting | SFDR | Company Level Outputs | Verify Scope 3 GHG Emissions Column is Reflected to report")
    @Xray(test = {12892, 12893, 12895,11276})
    public void verifyScope3GHGEmissionsColumnTest() {
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        
        reportingPage.navigateToPageFromMenu("ESG Reporting Portal");
        TestBase.test.info("Navigated to Regulatory Reporting Page");
        //verify portfolio upload modal
        assertTestCase.assertTrue(reportingPage.uploadAnotherPortfolioLink.isDisplayed(), "Portfolio Upload button is displayed");
        reportingPage.selectReportingOptionByName("SFDR PAIs");
        String currentWindow = BrowserUtils.getCurrentWindowHandle();
        reportingPage.selectInterimReports();
        reportingPage.selectAllPortfolioOptions();
        reportingPage.selectUseLatestData();
        List<String> selectedPortfolios = reportingPage.getSelectedPortfolioOptions();
        //verify create reports button before clicking
        assertTestCase.assertTrue(reportingPage.createReportsButton.isEnabled(), "Create report button is enabled");
        Set<String> currentWindows = BrowserUtils.getWindowHandles();
        reportingPage.clickOnCreateReportsButton();
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
            assertTestCase.assertTrue(reportingPage.verifyScope3GHGEmissionsForCompanyOutput(selectedPortfolios,"latest"),
                    "SFDR Company output for portfolio coverage is verified for Excel vs DB");
            System.out.println("SFDR Scope 3 GHG Emissions output for portfolio coverage is verified for Excel vs DB");
            assertTestCase.assertTrue(reportingPage.verifyScope3GHGEmissionsForPortfolioLevelOutput(selectedPortfolios,"latest", "Interim"),
                    "SFDR Portfolio Level output for portfolio coverage is verified for Excel vs DB");
            System.out.println("SFDR Scope 3 GHG Emissions output for portfolio coverage is verified for Excel vs DB");

            assertTestCase.assertTrue(reportingPage.verifyPortfolioLevelOutput(selectedPortfolios,"2020", "Interim", "Yes"),
                    "SFDR Portfolio Level Output is verified for Excel vs DB");
            System.out.println("SFDR Portfolio Level Output is verified for Excel vs DB");
        } catch (Exception e) {
            e.printStackTrace();
            assertTestCase.assertTrue(false, "New tab verification failed");
        } finally {
            Driver.closeBrowserTab();
            BrowserUtils.switchWindowsTo(currentWindow);
            System.out.println("=============================");
        }

        reportingPage.selectAnnualReports();
        selectedPortfolios = reportingPage.getSelectedPortfolioOptions();
        //verify create reports button before clicking
        assertTestCase.assertTrue(reportingPage.createReportsButton.isEnabled(), "Create report button is enabled");
        currentWindows = BrowserUtils.getWindowHandles();
        reportingPage.clickOnCreateReportsButton();
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
            assertTestCase.assertTrue(reportingPage.verifyScope3GHGEmissionsForCompanyOutput(selectedPortfolios,"latest"),
                    "SFDR Company output for portfolio coverage is verified for Excel vs DB");
            System.out.println("SFDR Scope 3 GHG Emissions output for portfolio coverage is verified for Excel vs DB");
            assertTestCase.assertTrue(reportingPage.verifyScope3GHGEmissionsForPortfolioLevelOutput(selectedPortfolios,"latest", "Annual"),
                    "SFDR Portfolio Level output for portfolio coverage is verified for Excel vs DB");
            System.out.println("SFDR Scope 3 GHG Emissions output for portfolio coverage is verified for Excel vs DB");

            assertTestCase.assertTrue(reportingPage.verifyPortfolioLevelOutput(selectedPortfolios,"2020", "Annual", "Yes"),
                    "SFDR Portfolio Level Output is verified for Excel vs DB");
            System.out.println("SFDR Portfolio Level Output is verified for Excel vs DB");
        } catch (Exception e) {
            e.printStackTrace();
            assertTestCase.assertTrue(false, "New tab verification failed");
        } finally {
            Driver.closeBrowserTab();
            BrowserUtils.switchWindowsTo(currentWindow);
            System.out.println("=============================");
        }
    }

    @Test(groups = {REGRESSION, DATA_VALIDATION, REGULATORY_REPORTING, UI},
            description = "Data Validation | SFDR - Regulatory reporting | Verify the BVD9 ID for the entities is displayed correctly in the Portfolio Level Output Sheet of generated Interim report without Use latest data filter")
    @Xray(test = {13566})
    public void verifyBVD9IDForInterimPortfolioLevelOutputWithoutLatestDataTest() {
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        
        reportingPage.navigateToReportingService("SFDR");
        TestBase.test.info("Navigated to Regulatory Reporting Page");
        //verify portfolio upload modal
        assertTestCase.assertTrue(reportingPage.verifyPortfolioUploadLink(), "Portfolio Upload button is displayed");
        //reportingPage.selectReportingOptionByName("SFDR PAIs");
        String currentWindow = BrowserUtils.getCurrentWindowHandle();
        reportingPage.selectInterimReports();//reportingPage.selectAnnualReports();
        reportingPage.selectAllPortfolioOptions();
        //reportingPage.selectUseLatestData();
        List<String> selectedPortfolios = reportingPage.getSelectedPortfolioOptions();
        //verify create reports button before clicking
        assertTestCase.assertTrue(reportingPage.createReportsButton.isEnabled(), "Create report button is enabled");
        Set<String> currentWindows = BrowserUtils.getWindowHandles();
        reportingPage.clickOnCreateReportsButton();
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
            assertTestCase.assertTrue(reportingPage.verifyBVD9IDInCompanyLevelOutput(selectedPortfolios,"Interim", "2022"),
                    "BVD9 ID for Portfolio Level output is verified for Excel vs DB");
            System.out.println("BVD9 ID for Portfolio Level output is verified for Excel vs DB");
        } catch (Exception e) {
            e.printStackTrace();
            assertTestCase.assertTrue(false, "Report verification failed");
        } finally {
            Driver.closeBrowserTab();
            BrowserUtils.switchWindowsTo(currentWindow);
            System.out.println("=============================");
        }
    }
}
