package com.esgc.RegulatoryReporting.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.PortfolioAnalysis.UI.Pages.PhysicalRiskPages.PhysicalRiskManagementPages.PhysicalRiskManagementPage;
import com.esgc.RegulatoryReporting.UI.Pages.RegulatoryReportingPage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.DateTimeUtilities;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.esgc.Utilities.Groups.*;

public class RegulatoryReportingPageTests extends UITestBase {

    @Test(groups = {REGRESSION, UI, REGULATORY_REPORTING, SMOKE}, description = "Verify that user can navigate to Regulatory Reporting page")
    @Xray(test = {10693, 10694, 10709, 10710, 10743, 10744, 10745, 10851, 10865})
    public void verifyReportingListTest() {
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.clickMenu();
        // Dynamic xpath - Helps us to pass page names "Dashboard", "Portfolio Analysis"
        assertTestCase.assertTrue(dashboardPage.isRegulatoryReportingDisplayed(),
                "Verify that user can navigate to Regulatory Reporting page");
        dashboardPage.clickOnRegulatoryReporting();
        //dashboardPage.navigateToPageFromMenu("Regulatory Reporting");
        test.info("Navigated to Regulatory Reporting Page");
        assertTestCase.assertTrue(reportingPage.isPageTitleDisplayed(),
                "Regulatory Reporting page title is verified");
        assertTestCase.assertEquals(reportingPage.getReportingSubtitleText(), "Reporting",
                "Regulatory Reporting Page - Reporting SubTitle is verified");
        assertTestCase.assertTrue(reportingPage.getReportingList().size() > 0,
                "Regulatory Reporting Page - Reporting list is verified");
        assertTestCase.assertTrue(reportingPage.rrPage_portfolioNamesList.size() > 0,
                "Regulatory Reporting Page - Portfolio list is verified");
        assertTestCase.assertTrue(reportingPage.getReportingList().contains("SFDR PAIs"),
                "Regulatory Reporting Page - Reporting list is verified");
        assertTestCase.assertTrue(reportingPage.isSelectedReportingOptionByName("SFDR PAIs"),
                "SFDR PAIs is selected by default");
        reportingPage.selectReportingOptionByName("EU Taxonomy");
        assertTestCase.assertFalse(reportingPage.isSelectedReportingOptionByName("SFDR PAIs"),
                "SFDR PAIs is not selected");
        assertTestCase.assertTrue(reportingPage.isSelectedReportingOptionByName("EU Taxonomy"),
                "EU Taxonomy is selected");
        assertTestCase.assertTrue(reportingPage.isInterimReportsOptionDisplayed(),"Interim Reports option is displayed");
        assertTestCase.assertTrue(reportingPage.isAnnualReportsOptionEnabled(),"Annual Reports option is enabled");
        assertTestCase.assertTrue(reportingPage.isUseLatestDataOptionDisplayed(),"Use Latest Data option is displayed");
    }

    @Test(groups = {REGRESSION, UI, REGULATORY_REPORTING}, description = "Verify user portfolio list on regulatory reporting page")
    @Xray(test = {11063, 11064, 11093, 11332})
    public void verifyPortfolioListForUserTest() {
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        DashboardPage dashboardPage = new DashboardPage();
        //get portfolios' list on dashboard page
        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");
        dashboardPage.clickPortfolioSelectionButton();
        List<String> expectedPortfoliosList = BrowserUtils.getElementsText(dashboardPage.portfolioNameList);
        assertTestCase.assertTrue(expectedPortfoliosList.size() > 0, "Dashboard Page - Portfolio list is verified");
        dashboardPage.navigateToPageFromMenu("Regulatory Reporting");
        test.info("Navigated to Regulatory Reporting Page");
        //get portfolios' list on regulatory reporting page
        List<String> actualPortfoliosList = reportingPage.getPortfolioList();
        assertTestCase.assertTrue(actualPortfoliosList.size() > 4, "Regulatory Reporting Page - Portfolio list is verified");
        assertTestCase.assertTrue(expectedPortfoliosList.containsAll(actualPortfoliosList), "Regulatory Reporting Page - Portfolio list is verified");

        //verify user selects only 4 portfolios at a time
        assertTestCase.assertFalse(reportingPage.isCreateReportsButtonEnabled(), "Create Reports button is disabled");
        assertTestCase.assertEquals(reportingPage.getCreateReportsButtonText(), "Create 0 Report", "Create Reports button is verified");
        String color = Color.fromString(reportingPage.createReportsButton.getCssValue("background-color")).asHex();
        assertTestCase.assertEquals(color.toLowerCase(), "#c1c6cc", "Create Reports button color is verified");

        reportingPage.selectAllPortfolioOptions();
        assertTestCase.assertEquals(reportingPage.numberOfSelectedPortfolioOptions(), 4, "All Portfolio options are selected");
        assertTestCase.assertEquals(reportingPage.getCreateReportsButtonText(), "Create 4 Reports", "All Portfolio options are selected");
        assertTestCase.assertTrue(reportingPage.isCreateReportsButtonEnabled(), "Create Reports button is enabled");
        assertTestCase.assertFalse(reportingPage.portfolioRadioButtonList.get(4).isEnabled(), "Radio button is disabled if already 4 button selected");
        color = Color.fromString(reportingPage.createReportsButton.getCssValue("background-color")).asHex();
        assertTestCase.assertEquals(color.toUpperCase(), "#1F8CFF", "Create Reports button color is verified");
        System.out.println("User can select only 4 portfolios at a time is verified");

        //Verify Portfolio column is displayed with portfolio list sorted in alphabetical order.

        List<String> sortedPortfolioList = reportingPage.getPortfolioList();

        sortedPortfolioList = sortedPortfolioList.stream().filter(e -> !Character.isDigit(e.charAt(0))).collect(Collectors.toList());
        sortedPortfolioList.sort(String::compareToIgnoreCase);

        assertTestCase.assertEquals(sortedPortfolioList, actualPortfoliosList, "Portfolio list is sorted in alphabetical order");
        System.out.println("Portfolio column is displayed with portfolio list sorted in alphabetical order is verified");

        //Verify "Last Uploaded" column is present with date of upload as its value.
        assertTestCase.assertEquals(actualPortfoliosList.size(), reportingPage.lastUploadedList.size(), "Last Uploaded column is present with date of upload as its value");
        for (WebElement date : reportingPage.lastUploadedList) {
            assertTestCase.assertTrue(DateTimeUtilities.isValidFormat("MMMM d, yyyy", date.getText()), "Last Uploaded column is present with date of upload as its value");
        }
        System.out.println("Last Uploaded column is present with date of upload as its value is verified");

        //Verify "Coverage" column is present with "% of coverage" as its value.
        assertTestCase.assertEquals(actualPortfoliosList.size(), reportingPage.coverageList.size(), "Coverage column is present with % of coverage as its value");
        for (WebElement coverage : reportingPage.coverageList) {
            //System.out.println("coverage.getText() = " + coverage.getText());
            assertTestCase.assertTrue(coverage.getText().matches("\\d+.*") || coverage.getText().equals("NA"),
                    "Coverage column is present with % of coverage as its value");
        }
        System.out.println("Coverage column is present with % of coverage as its value is verified");

        //Verify "Reporting for" column is present as a dropdown, to allow user to select the reporting year they want in the report.
        assertTestCase.assertEquals(actualPortfoliosList.size(), reportingPage.reportingForList.size(), "Reporting for column year for portfolio is verified");
        for (WebElement dropdown : reportingPage.reportingForList) {
            assertTestCase.assertTrue(dropdown.isEnabled(), "Reporting for column dropdown for portfolio is verified");
        }
        System.out.println("Reporting for column is present as a dropdown, to allow user to select the reporting year they want in the report is verified");

        //select portfolio 1 and verify it is bolded
        if (!reportingPage.portfolioRadioButtonList.get(0).isSelected()) {
            System.out.println("Portfolio 1 is not selected");
            reportingPage.deselectAllPortfolioOptions();
            reportingPage.selectPortfolioOptionByIndex(1);
        }
        assertTestCase.assertTrue(reportingPage.portfolioRadioButtonList.get(0).isSelected(), "Portfolio 1 is selected");
        System.out.println("Font-Family = " + reportingPage.rrPage_portfolioNamesList.get(0).getCssValue("font-family"));
        assertTestCase.assertTrue(reportingPage.rrPage_portfolioNamesList.get(0).getCssValue("font-family").contains("WhitneyNarrSemiBold"), "Portfolio is bolded");
        assertTestCase.assertTrue(reportingPage.lastUploadedList.get(0).getCssValue("font-family").contains("WhitneyNarrSemiBold"), "Upload date is bolded");
        assertTestCase.assertTrue(reportingPage.coverageList.get(0).getCssValue("font-family").contains("WhitneyNarrSemiBold"), "Coverage is bolded");
        assertTestCase.assertTrue(reportingPage.reportingForList.get(0).getCssValue("font-family").contains("WhitneyNarrSemiBold"), "Reporting for year is bolded");

        //-Button should have download icon and 'Create 1 Report' by the number of selected portfolio. (If there are more than 1 portfolio selected, then should be 'Create 2 Reports')
        reportingPage.deselectAllPortfolioOptions();
        reportingPage.selectPortfolioOptionByIndex(1);
        assertTestCase.assertEquals(reportingPage.getCreateReportsButtonText(), "Create 1 Report", "Create Reports button is verified for 1 portfolio selected");
        reportingPage.selectPortfolioOptionByIndex(2);
        assertTestCase.assertEquals(reportingPage.getCreateReportsButtonText(), "Create 2 Reports", "Create Reports button is verified for 2 portfolio selected");
        reportingPage.selectPortfolioOptionByIndex(3);
        assertTestCase.assertEquals(reportingPage.getCreateReportsButtonText(), "Create 3 Reports", "Create Reports button is verified for 3 portfolio selected");
        reportingPage.selectPortfolioOptionByIndex(4);
        assertTestCase.assertEquals(reportingPage.getCreateReportsButtonText(), "Create 4 Reports", "Create Reports button is verified for 4 portfolio selected");
    }

    @Test(groups = {REGRESSION, UI, REGULATORY_REPORTING, ROBOT_DEPENDENCY}, description = "Verify user portfolio list on regulatory reporting page")
    @Xray(test = {11091, 11092})
    public void verifyPortfolioUploadTest() {
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Regulatory Reporting");
        test.info("Navigated to Regulatory Reporting Page");
        //verify portfolio upload modal
        assertTestCase.assertTrue(reportingPage.isUploadAnotherPortfolioLinkDisplayed(), "Portfolio Upload button is displayed");
        reportingPage.openPortfolioUploadModal();
        assertTestCase.assertTrue(reportingPage.isImportPortfolioPopUpDisplayed(), "Portfolio Upload Modal is displayed");
        reportingPage.closeUploadModal();

        //upload a portfolio
        String newPortfolioName = "SamplePortfolioToDelete";
        assertTestCase.assertTrue(reportingPage.uploadPortfolio(newPortfolioName), "New Portfolio is added to the list");
        System.out.println(newPortfolioName + " uploaded");

        //verify portfolio on regulatory reporting page based on sort order
        BrowserUtils.wait(3);
        assertTestCase.assertTrue(reportingPage.getPortfolioList().contains(newPortfolioName), "New Portfolio is added to the list");
        System.out.println(newPortfolioName + " is verified on regulatory reporting page");
        List<String> sortedPortfolioList = reportingPage.getPortfolioList();

        sortedPortfolioList = sortedPortfolioList.stream().filter(e -> !Character.isDigit(e.charAt(0))).collect(Collectors.toList());
        sortedPortfolioList.sort(String::compareToIgnoreCase);
        assertTestCase.assertEquals(reportingPage.getPortfolioList(), sortedPortfolioList, "Portfolio list is sorted alphabetically");

        //verify portfolio on dashboard
        dashboardPage.navigateToPageFromMenu("Dashboard");
        BrowserUtils.waitFor(10);
        dashboardPage.clickPortfolioSelectionButton();
        List<String> actualPortfolioList = BrowserUtils.getElementsText(dashboardPage.portfolioNameList);
        assertTestCase.assertTrue(actualPortfolioList.contains(newPortfolioName), "New Portfolio is verified on dashboard page");
        dashboardPage.clickAwayinBlankArea();

        //verify portfolio on portfolio analysis page
        dashboardPage.navigateToPageFromMenu("Portfolio Analysis");
        PhysicalRiskManagementPage portfolioAnalysisPage = new PhysicalRiskManagementPage();
        BrowserUtils.waitFor(10);
        portfolioAnalysisPage.clickPortfolioSelectionButton();
        actualPortfolioList = BrowserUtils.getElementsText(portfolioAnalysisPage.portfolioNameList);
        assertTestCase.assertTrue(actualPortfolioList.contains(newPortfolioName), "New Portfolio is verified on portfolio analysis page");

        //verify portfolio on portfolio selection modal
        assertTestCase.assertTrue(portfolioAnalysisPage.verifyPortfolio(newPortfolioName), "New Portfolio is verified on portfolio upload page");

        //delete portfolio
        portfolioAnalysisPage.deletePortfolio(newPortfolioName);
        //note:LINES BELOW DISABLED BECAUSE OF there might be multiople portfolios with the same name
//        assertTestCase.assertFalse(portfolioAnalysisPage.verifyPortfolio(newPortfolioName), "New Portfolio is deleted");
//
//        //verify portfolio on regulatory reporting page
//        dashboardPage.navigateToPageFromMenu("Regulatory Reporting");
//        assertTestCase.assertFalse(regulatoryReportingPage.getPortfolioList().contains(newPortfolioName),"New Portfolio is deleted from the list");
    }

    @Test(groups = {REGRESSION, UI, REGULATORY_REPORTING}, description = "Validate 'Select Reporting' and 'Select Portfolios' columns")
    @Xray(test = {11137, 11138, 11139})
    public void verifySelectReportingAndPortfoliosColumnsTest() {
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        DashboardPage dashboardPage = new DashboardPage();
        //Verify all the portfolios that the user has uploaded, are listed in the "select portfolio" section.
        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");
        dashboardPage.clickPortfolioSelectionButton();
        List<String> expectedPortfoliosList = BrowserUtils.getElementsText(dashboardPage.portfolioNameList);
        assertTestCase.assertTrue(expectedPortfoliosList.size() > 0, "Dashboard Page - Portfolio list is verified");
        dashboardPage.navigateToPageFromMenu("Regulatory Reporting");
        test.info("Navigated to Regulatory Reporting Page");
        //get portfolios' list on regulatory reporting page
        List<String> actualPortfoliosList = reportingPage.getPortfolioList();
        assertTestCase.assertTrue(actualPortfoliosList.size() > 4, "Regulatory Reporting Page - Portfolio list is verified");
        assertTestCase.assertTrue(expectedPortfoliosList.containsAll(actualPortfoliosList), "Regulatory Reporting Page - Portfolio list is verified");
        //verify "Select Reporting" section is displayed on the left with options as "SFDR PAIs"
        assertTestCase.assertTrue(reportingPage.getReportingList().contains("SFDR PAIs"), "Select Reporting list contains SFDR PAIs");

        //Verify "SFDR PAIs" is selected by default
        System.out.println("Selected reporting option: " + reportingPage.getSelectedReportingOption());
        assertTestCase.assertEquals(reportingPage.getSelectedReportingOption(), "SFDR PAIs", "SFDR PAIs is selected by default");

        //Select "SFDR PAIs" and select any 1 portfolio
        reportingPage.selectPortfolioOptionByIndex(1);

        //verify "Create 1 report" button should be enabled
        assertTestCase.assertTrue(reportingPage.isCreateReportsButtonEnabled(), "Create 1 report button is enabled");

        //Uncheck "SFDR PAI" reporting option and select multiple portfolios
        reportingPage.deselectAllPortfolioOptions();
        assertTestCase.assertFalse(reportingPage.isCreateReportsButtonEnabled(), "Create 1 report button is disabled");

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
        BrowserUtils.wait(5);
        reportingPage.reportingForDropdownOptionsList.get(0).click();
        assertTestCase.assertTrue(reportingPage.isCreateReportsButtonEnabled(), "Create 1 report button is enabled");

        //verify reporting dropdown options are not listed if portfolio is not selected
        reportingPage.pressESCKey();
        reportingPage.deselectAllPortfolioOptions();
        reportingPage.deselectInterimReports();
        reportingPage.deselectAnnualReports();
        reportingPage.deselectUseLatestData();
        assertTestCase.assertFalse(reportingPage.reportingForListButtons.get(0).isEnabled(), "Reporting year dropdown is enabled");
        BrowserUtils.waitForClickablility(reportingPage.reportingForList.get(0), 10).click();
        assertTestCase.assertTrue(reportingPage.reportingForDropdownOptionsList.size() == 0, "reporting for dropdown options are not listed if portfolio is not selected");

        //verify reporting dropdown options are not listed if use latest data toggle is selected
        reportingPage.selectPortfolioOptionByIndex(1);
        assertTestCase.assertTrue(reportingPage.isInterimReportsOptionDisplayed(), "Interim reports toggle is displayed");
        assertTestCase.assertTrue(reportingPage.isAnnualReportsOptionDisplayed(), "Annual reports toggle is displayed");
        assertTestCase.assertTrue(reportingPage.iaUseLatestDataOptionDisplayed(), "Use latest data toggle is displayed");
        reportingPage.selectUseLatestData();
        assertTestCase.assertTrue(reportingPage.isUseLatestDataSelected(), "Use latest data toggle is selected");
        assertTestCase.assertFalse(reportingPage.reportingForListButtons.get(0).isEnabled(), "Reporting year dropdown is enabled");
        BrowserUtils.waitForClickablility(reportingPage.reportingForList.get(0), 10).click();
        assertTestCase.assertTrue(reportingPage.reportingForDropdownOptionsList.size() == 0, "reporting for dropdown options are not listed if portfolio is not selected");

        //Validate only one of the option can be turned on at a time "Interim Reporting" or "Annual Reporting".
        //Cannot turn on both the options at the same time.
        reportingPage.selectInterimReports();
        assertTestCase.assertTrue(reportingPage.isInterimReportsSelected(), "Interim reports toggle is selected");
        assertTestCase.assertFalse(reportingPage.isAnnualReportsSelected(), "Annual reports toggle is not selected");

        reportingPage.selectAnnualReports();
        assertTestCase.assertFalse(reportingPage.isInterimReportsSelected(), "Interim reports toggle is not selected");
        assertTestCase.assertTrue(reportingPage.isAnnualReportsSelected(), "Annual reports toggle is selected");
    }

    @Test(groups = {REGRESSION, UI, REGULATORY_REPORTING, SMOKE}, description = "UI | Regulatory Reporting | Download | Verify Create Reports Button is Clickable")
    @Xray(test = {10849, 11333, 11334, 11350, 11370, 11402})
    public void verifyCreateReportsButtonWorksTest() {
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Regulatory Reporting");
        test.info("Navigated to Regulatory Reporting Page");

        String currentWindow = BrowserUtils.getCurrentWindowHandle();
        System.out.println("currentWindow = " + currentWindow);
        reportingPage.selectAllPortfolioOptions();
        reportingPage.selectUseLatestData();
        List<String> selectedPortfolios = reportingPage.getSelectedPortfolioOptions();

        //verify create reports button before clicking
        assertTestCase.assertTrue(reportingPage.isCreateReportsButtonEnabled(), "Create report button is enabled");
        String color = Color.fromString(reportingPage.createReportsButton.getCssValue("background-color")).asHex();
        System.out.println("color = " + color);
        assertTestCase.assertEquals(color, "#1f8cff", "Create report button color is blue");
        Set<String> windows = BrowserUtils.getWindowHandles();
        reportingPage.createReportsButton.click();

        //verify create reports button after clicking
        BrowserUtils.switchWindowsTo(currentWindow);
        BrowserUtils.wait(1);
        color = Color.fromString(reportingPage.createReportsButton.getCssValue("background-color")).asHex();
        System.out.println("color = " + color);
        assertTestCase.assertEquals(color, "#046bd9", "Create report button color is blue");//#0971e0
        try {
            //New tab should be opened and empty state message should be displayed as in the screenshot
            assertTestCase.assertTrue(reportingPage.verifyNewTabOpened(windows), "New tab is opened");
            System.out.println("New tab is opened");
            assertTestCase.assertTrue(reportingPage.verifyReportsReadyToDownload(selectedPortfolios), "Reports are ready to download");
            System.out.println("Reports are ready to download");
            assertTestCase.assertTrue(reportingPage.verifyIfReportsDownloaded(), "Reports are downloaded");
            System.out.println("Reports are downloaded");

        } catch (Exception e) {
            assertTestCase.assertTrue(false, "New tab verification failed");
            e.printStackTrace();
        } finally {
            Driver.closeBrowserTab();
            BrowserUtils.switchWindowsTo(currentWindow);
        }
        BrowserUtils.wait(5);
        //Create Report button should not be clickable until progress is done, button should be greyed out
        //This part is not automated since button turn back to blue in a short time
    }

    @Test(groups = {REGRESSION, UI, REGULATORY_REPORTING}, description = "Verify user cant get report for predicted scores portfolio")
    @Xray(test = {11403})
    public void verifyPredictedScorePortfolioTest() {
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Regulatory Reporting");
        test.info("Navigated to Regulatory Reporting Page");
        reportingPage.deselectAllPortfolioOptions();
        String portfolioName = "PredictedScoresPortfolio";
        if (!reportingPage.verifyPortfolio(portfolioName)) reportingPage.uploadPortfolio(portfolioName);
        assertTestCase.assertTrue(reportingPage.verifyPortfolio(portfolioName), "PredictedScoresPortfolio is uploaded");
        reportingPage.selectPortfolioOptionByName(portfolioName);
        assertTestCase.assertFalse(reportingPage.isPortfolioSelected(portfolioName), "PredictedScoresPortfolio is not selected");
        assertTestCase.assertFalse(reportingPage.isPortfolioSelectionEnabled(portfolioName), "PredictedScoresPortfolio is disabled");
        assertTestCase.assertFalse(reportingPage.isCreateReportsButtonEnabled(), "Create report button is disabled");
    }

    @Test(groups = {REGRESSION, UI, REGULATORY_REPORTING}, description = "UI | Regulatory Reporting | Download | Verify Create Reports Button is Clickable")
//"smoke",
    @Xray(test = {10854})
    public void verifyAnnualReportingDisabledTest() {
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Regulatory Reporting");
        test.info("Navigated to Regulatory Reporting Page");
        assertTestCase.assertTrue(reportingPage.isInterimReportsOptionDisplayed(), "Interim reports option is displayed");
        assertTestCase.assertTrue(reportingPage.isAnnualReportsOptionDisplayed(), "Annual reports option is displayed");
        assertTestCase.assertTrue(reportingPage.isUseLatestDataOptionDisplayed(), "Use latest data option is displayed");
        reportingPage.selectAllPortfolioOptions();
        List<String> selectedPortfolios = reportingPage.getSelectedPortfolioOptions();
        reportingPage.selectReportingFor(selectedPortfolios.get(0), "2019");
        reportingPage.selectReportingFor(selectedPortfolios.get(1), "2020");
        reportingPage.selectReportingFor(selectedPortfolios.get(2), "2021");
        reportingPage.selectAnnualReports();
        assertTestCase.assertFalse(reportingPage.isAnnualReportsSelected(), "Annual reports option is disabled as expected");
    }

    @Test(groups = {REGRESSION, UI, REGULATORY_REPORTING}, description = "UI | Regulatory Reporting | UI Checks for Reporting service Options")
    @Xray(test = {11066, 11067})
    public void verifyReportingServiceOptionsTest() {
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Regulatory Reporting");
        test.info("Navigated to Regulatory Reporting Page");
        //verify Title
        assertTestCase.assertTrue(reportingPage.isReportingOptionsTitleDisplayed(), "Reporting options title is displayed");
        assertTestCase.assertEquals(reportingPage.getReportingOptionsTitleText(), "Reporting Options", "Reporting options title is displayed as expected");

        //verify Interim Options
        assertTestCase.assertTrue(reportingPage.isInterimReportsOptionDisplayed(), "Interim reports option is displayed");
        assertTestCase.assertEquals(reportingPage.isInterimReportsOptionText(), "Interim Reports", "Interim reports option is displayed as expected");
        assertTestCase.assertTrue(reportingPage.isInterimOptionSubtitleDisplayed(), "Interim reports option subtitle is displayed");
        assertTestCase.assertEquals(reportingPage.isInterimOptionSubtitleText(), "Separate reports across selected portfolios",
                "Interim reports option subtitle is displayed as expected");
        assertTestCase.assertTrue(reportingPage.isInterimReportsSelected(), "Interim reports option selected by default");
        reportingPage.selectAllPortfolioOptions();
        assertTestCase.assertEquals(reportingPage.isCreateReportsButtonText(), "Create 4 Reports", "Interim reports option subtitle is displayed");

        //verify Annual Options
        assertTestCase.assertTrue(reportingPage.isAnnualReportsOptionDisplayed(), "Annual reports option is displayed");
        assertTestCase.assertEquals(reportingPage.iaAnnualReportsOptionText(), "Annual Reports", "Annual reports option is displayed as expected");
        assertTestCase.assertTrue(reportingPage.isAnnualOptionSubtitleDisplayed(), "Annual reports option subtitle is displayed");
        assertTestCase.assertEquals(reportingPage.isAnnualOptionSubtitleText(), "Annual report averaging across selected portfolios",
                "Annual reports option subtitle is displayed as expected");
        assertTestCase.assertFalse(reportingPage.isAnnualReportsSelected(), "Annual reports option is not selected by default");
        reportingPage.selectAnnualReports("2020");
        assertTestCase.assertEquals(reportingPage.isCreateReportsButtonText(), "Create 1 Report", "Annual reports option subtitle is displayed");
        assertTestCase.assertFalse(reportingPage.isInterimReportsSelected(), "Interim reports option is not selected when annual reports option is selected");


        //verify Use Latest Data Options
        assertTestCase.assertTrue(reportingPage.isUseLatestDataOptionDisplayed(), "Use latest data option is displayed");
        assertTestCase.assertEquals(reportingPage.isUseLatestDataOptionText(), "Use Latest Data", "Use latest data option is displayed as expected");
        assertTestCase.assertTrue(reportingPage.isUseLatestDataOptionSubtitleDisplayed(), "Use latest data option subtitle is displayed");
        assertTestCase.assertEquals(reportingPage.getUseLatestDataOptionSubtitleText(), "Only use latest data as of today. Data will not align to report date.",
                "Use latest data option subtitle is displayed as expected");
        assertTestCase.assertFalse(reportingPage.isUseLatestDataSelected(), "Use latest data option is not selected by default");
        reportingPage.selectUseLatestData();
        assertTestCase.assertTrue(reportingPage.isUseLatestDataSelected(), "Use latest data option is selected when selected");
        assertTestCase.assertFalse(reportingPage.reportingForListButtons.get(0).isEnabled(), "reporting for dropdown is disabled when use latest data option is selected");
        assertTestCase.assertTrue(reportingPage.isCreateReportsButtonEnabled(), "Create reports button is enabled when use latest data option is selected");
    }

    @Test(groups = {REGRESSION, UI, REGULATORY_REPORTING, SMOKE},
            description = "UI | Regulatory Reporting | EU Taxonomy | Verify EU Taxonomy Report Sheets")
    @Xray(test = {11987, 11988, 11989, 11990, 11991, 11992})
    public void verifyEUTaxonomyReportSheetsTest() {
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Regulatory Reporting");
        test.info("Navigated to Regulatory Reporting Page");
        String portfolioName = "SFDRPortfolio";
        if (!reportingPage.verifyPortfolio(portfolioName)) reportingPage.uploadPortfolio(portfolioName);
        String currentWindow = BrowserUtils.getCurrentWindowHandle();
        reportingPage.selectReportingOptionByName("EU Taxonomy");
        assertTestCase.assertEquals(reportingPage.getSelectedReportingOption(), "EU Taxonomy", "EU Taxonomy is selected");
        reportingPage.selectPortfolioOptionByName(portfolioName);
        List<String> selectedPortfolios = reportingPage.getSelectedPortfolioOptions();
        assertTestCase.assertTrue(selectedPortfolios.contains(portfolioName), "Portfolio is selected");
        Set<String> tabs = BrowserUtils.getWindowHandles();

        //verify create reports button before clicking
        assertTestCase.assertTrue(reportingPage.isCreateReportsButtonEnabled(), "Create report button is enabled");
        reportingPage.createReportsButton.click();

        //verify create reports button after clicking
        BrowserUtils.wait(1);
        try {
            //New tab should be opened and empty state message should be displayed as in the screenshot
            assertTestCase.assertTrue(reportingPage.verifyNewTabOpened(tabs), "New tab is opened");
            System.out.println("New tab is opened");
            assertTestCase.assertTrue(reportingPage.verifyReportsReadyToDownload(selectedPortfolios), "Reports are ready to download");
            System.out.println("Reports are ready to download");
            reportingPage.deleteFilesInDownloadsFolder();
            assertTestCase.assertTrue(reportingPage.verifyIfReportsDownloaded(), "Reports are downloaded");
            System.out.println("Reports are downloaded");
            assertTestCase.assertTrue(reportingPage.unzipReports(), "Reports are extracted");
            System.out.println("Reports are extracted");
            assertTestCase.assertTrue(reportingPage.verifyEUTaxonomySheets(), "EU Taxonomy sheets are verified");
            System.out.println("EU Taxonomy sheets are verified");
            assertTestCase.assertTrue(reportingPage.verifyGreenInvestmentRatioTemplate(), "Green Investment Ratio Template sheet is verified");
            System.out.println("Green Investment Ratio Template sheet is verified");
            assertTestCase.assertTrue(reportingPage.verifyUnderlyingDataOverview(), "Underlying Data Overview sheet is verified");
            System.out.println("Underlying Data Overview sheet is verified");
            assertTestCase.assertTrue(reportingPage.verifyUnderlyingDataActivities(), "Underlying Data Activities sheet is verified");
            System.out.println("Underlying Data Activities sheet is verified");
            assertTestCase.assertTrue(reportingPage.verifyDefinitions(), "Definitions sheet is verified");
            System.out.println("Definitions sheet is verified");
            assertTestCase.assertTrue(reportingPage.verifyDisclaimer(), "Definitions sheet is verified");
            System.out.println("Definitions sheet is verified");
        } catch (Exception e) {
            assertTestCase.assertTrue(false, "Report verification failed");
            e.printStackTrace();
        } finally {
            Driver.closeBrowserTab();
            BrowserUtils.switchWindowsTo(currentWindow);
        }
    }

}
