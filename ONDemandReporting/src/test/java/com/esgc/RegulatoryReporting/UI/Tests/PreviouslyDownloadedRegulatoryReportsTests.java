package com.esgc.RegulatoryReporting.UI.Tests;


import com.esgc.Common.UI.Pages.LoginPage;
import com.esgc.Common.UI.TestBases.UITestBase;
import com.esgc.RegulatoryReporting.UI.Pages.RegulatoryReportingPage;
import com.esgc.TestBase.TestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class PreviouslyDownloadedRegulatoryReportsTests extends UITestBase {

    @Test(groups = {REGRESSION, UI, REGULATORY_REPORTING}, dataProvider = "Reports")
    @Xray(test = {3857, 3854, 4252})
    public void verifyPreviouslyDownloadedFeature(String report) {
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        reportingPage.navigateToReportingService(report);
        TestBase.test.info("Navigated to Regulatory Reporting Page for " + report + " Report");

        assertTestCase.assertTrue(reportingPage.verifyPreviouslyDownloadedButton(), "Verify Previously Downloaded button");

        reportingPage.clickPreviouslyDownloadedButton();
        BrowserUtils.wait(5);
        String currentWindow = BrowserUtils.getCurrentWindowHandle();
        String previousDownloadsWindow = BrowserUtils.getWindowHandles().stream().filter(e -> !e.equals(currentWindow)).findFirst().get();
        BrowserUtils.switchWindowsTo(previousDownloadsWindow);

        reportingPage.verifyPreviouslyDownloadedScreen();
        reportingPage.verifyFileDownloadFromPreviouslyDownloadedScreen();

        Driver.closeBrowserTab();
        BrowserUtils.switchWindow(currentWindow);
    }

    @Test(groups = {REGRESSION, UI, REGULATORY_REPORTING, ENTITLEMENTS}, dataProvider = "Reports")
    @Xray(test = {4255})
    public void verifyNoFilesInPreviouslyDownloadedScreen(String report) {
        System.out.println("report = " + report);
        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
        LoginPage login = new LoginPage();
        login.entitlementsLogin(EntitlementsBundles.NO_PREVIOUSLY_DOWNLOADED_REGULATORY_REPORTS);

        reportingPage.navigateToReportingService(report);
        TestBase.test.info("Navigated to Regulatory Reporting Page");

        assertTestCase.assertTrue(reportingPage.verifyPreviouslyDownloadedButton(), "Verify Previously Downloaded button");

        reportingPage.clickPreviouslyDownloadedButton();
        BrowserUtils.wait(5);
        String currentWindow = BrowserUtils.getCurrentWindowHandle();
        String previousDownloadsWindow = BrowserUtils.getWindowHandles().stream().filter(e -> !e.equals(currentWindow)).findFirst().get();
        BrowserUtils.switchWindowsTo(previousDownloadsWindow);

        BrowserUtils.waitForVisibility(reportingPage.previouslyDownloadedErrorMessage, 30);
        assertTestCase.assertEquals(reportingPage.getPreviouslyDownloadedErrorMessageText(), "No previously downloaded reports to be displayed.", "Verify Error message in Previously Downloaded screen");

        Driver.closeBrowserTab();
        BrowserUtils.switchWindow(currentWindow);
    }

    @DataProvider(name = "Reports")
    public Object[][] dpMethodForReports() {

        return new Object[][]{
                {"SFDR"},
                {"EU Taxonomy"}
        };
    }
}
