package com.esgc.Dashboard.UI.Tests;

import com.esgc.Base.TestBases.DashboardUITestBase;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.ExcelUtil;
import com.esgc.Utilities.Xray;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class DownloadTemplate extends DashboardUITestBase {


    @Test(description = "Verify that the downloaded template, when clicked will launch Excel",
            groups = {DASHBOARD, REGRESSION, UI, SMOKE})
    @Xray(test = {4313, 4371, 4489})
    public void downloadTemplateLinkTest() {
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Climate Dashboard");
        test.info("Navigated to Dashboard Page");
        test.info("Clicked on Upload button");

        dashboardPage.clickUploadPortfolioButton();
        test.info("Navigated to Upload Page");

        test.info("Clicked on \"Download Template\" link");
        BrowserUtils.waitForVisibility(dashboardPage.downloadTemplateLink, 2);

        Assert.assertEquals(dashboardPage.downloadTemplateLink.getText(), "Download Template");
        Assert.assertTrue(dashboardPage.downloadTemplateLink.isEnabled());

        assertTestCase.assertEquals(dashboardPage.downloadTemplateLink.getText(), "Download Template", "Download Template is accessible", 4371, 4489);
        assertTestCase.assertTrue(dashboardPage.downloadTemplateLink.isEnabled(), "Template is visible", 4313);
        test.pass("\"Download Template\" button's visibility verified.");
        test.pass("Verified: \"Download Template\" link is clickable.");

        dashboardPage.clickDownloadTemplate();
        test.info("Clicked on \"Download Template\" link");
        BrowserUtils.wait(4);
        assertTestCase.assertTrue(dashboardPage.isTemplateDownloaded(), "Template is saved to user machine", 4631);

        assertTestCase.assertTrue(ExcelUtil.checkIf2CSVFilesAreSame(BrowserUtils.templatePath(), BrowserUtils.expectedPortfolioTemplateDocumentPath())
                , "Download Template content check", 4493, 5024, 3442);

        Assert.assertEquals(dashboardPage.getDataFromCSV(0, 0), "Currency: Please only enter in values of USD or GBP or EUR. Platform values will be converted into USD. Only one currency is allowed per portfolio");
        test.pass("Excel was launched and this action was verified by reading a data from excel");
        test.info("Template was deleted");
        dashboardPage.deleteDownloadFolder();
    }

    @Test(description = "Verify that the downloaded template, when clicked will launch Excel",
            groups = {DASHBOARD, REGRESSION, UI, SMOKE})
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
        assertTestCase.assertTrue(dashboardPage.isTemplateDownloaded(), "Template is saved to user machine", 4631);

        assertTestCase.assertTrue(ExcelUtil.checkIf2CSVFilesAreSame(BrowserUtils.templatePath(), BrowserUtils.expectedPortfolioTemplateDocumentPath())
                , "Download Template content check", 3442);

        Assert.assertEquals(dashboardPage.getDataFromCSV(0, 0), "Currency: Please only enter in values of USD or GBP or EUR. Platform values will be converted into USD. Only one currency is allowed per portfolio");
        test.pass("Excel was launched and this action was verified by reading a data from excel");
        test.info("Template was deleted");
        dashboardPage.deleteDownloadFolder();
    }
}
