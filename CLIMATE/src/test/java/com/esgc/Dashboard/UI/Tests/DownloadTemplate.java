package com.esgc.Dashboard.UI.Tests;

import com.esgc.Base.TestBases.DashboardUITestBase;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.ExcelUtil;
import com.esgc.Utilities.Xray;
import org.testng.Assert;
import org.testng.annotations.Test;


public class DownloadTemplate extends DashboardUITestBase {


    @Test(groups = {"dashboard", "regression", "ui", "smoke"})
    @Xray(test = {192, 188, 287})
    public void downloadTemplateLinkTest() {
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");
        test.info("Clicked on Upload button");

        dashboardPage.clickUploadPortfolioButton();
        test.info("Navigated to Upload Page");

        test.info("Clicked on \"Download Template\" link");
        BrowserUtils.waitForVisibility(dashboardPage.downloadTemplateLink, 2);

        Assert.assertEquals(dashboardPage.downloadTemplateLink.getText(), "Download Template");
        Assert.assertTrue(dashboardPage.downloadTemplateLink.isEnabled());

        assertTestCase.assertEquals(dashboardPage.downloadTemplateLink.getText(), "Download Template", "Download Template is accessible", 188, 287);
        assertTestCase.assertTrue(dashboardPage.downloadTemplateLink.isEnabled(), "Template is visible", 192);
        test.pass("\"Download Template\" button's visibility verified.");
        test.pass("Verified: \"Download Template\" link is clickable.");

        BrowserUtils.wait(1);
    }


    @Test(description = "Verify that the downloaded template, when clicked will launch Excel",
            groups = {"dashboard", "regression", "ui", "smoke"})
    public void verifyTemplateIsAccessibleTest() {
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");
        test.info("Clicked on Upload button");

        dashboardPage.clickUploadPortfolioButton();
        test.info("Navigated to Upload Page");

        test.info("Clicked on \"Download Template\" link");
        dashboardPage.clickDownloadTemplate();
        BrowserUtils.wait(4);
        assertTestCase.assertTrue(dashboardPage.isTemplateDownloaded(), "Template is saved to user machine", 248);

        assertTestCase.assertTrue(ExcelUtil.checkIf2CSVFilesAreSame(BrowserUtils.templatePath(), BrowserUtils.expectedPortfolioTemplateDocumentPath())
                , "Download Template content check", 299, 317, 11079);

        Assert.assertEquals(dashboardPage.getDataFromCSV(0, 0), "Currency: Please only enter in values of USD or GBP or EUR. Platform values will be converted into USD. Only one currency is allowed per portfolio");
        test.pass("Excel was launched and this action was verified by reading a data from excel");
        test.info("Template was deleted");
        dashboardPage.deleteDownloadFolder();
    }

    @Test(description = "Verify that the downloaded template, when clicked will launch Excel",
            groups = {"dashboard", "regression", "ui", "smoke"})
    public void verifyTemplateFromPortfolioManagement() {
        DashboardPage dashboardPage = new DashboardPage();
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.clickMenu();
        researchLinePage.portfolioSettings.click();
        BrowserUtils.wait(4);
        researchLinePage.link_UploadNew.click();

        test.info("Clicked on \"Download Template\" link");
        dashboardPage.clickDownloadTemplate();
        BrowserUtils.wait(4);
        assertTestCase.assertTrue(dashboardPage.isTemplateDownloaded(), "Template is saved to user machine", 248);

        assertTestCase.assertTrue(ExcelUtil.checkIf2CSVFilesAreSame(BrowserUtils.templatePath(), BrowserUtils.expectedPortfolioTemplateDocumentPath())
                , "Download Template content check", 11079);

        Assert.assertEquals(dashboardPage.getDataFromCSV(0, 0), "Currency: Please only enter in values of USD or GBP or EUR. Platform values will be converted into USD. Only one currency is allowed per portfolio");
        test.pass("Excel was launched and this action was verified by reading a data from excel");
        test.info("Template was deleted");
        dashboardPage.deleteDownloadFolder();
    }
}
