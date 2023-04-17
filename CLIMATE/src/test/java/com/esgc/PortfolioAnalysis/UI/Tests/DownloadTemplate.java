package com.esgc.PortfolioAnalysis.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.ExcelUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class DownloadTemplate extends UITestBase {

      /*
    US JIRA # - ESGCA-224
    Acceptance Criteria:
    Download template link appears on the import modal
    Download template link is clickable on the import modal
 */

    @Test(groups = {REGRESSION, UI, SMOKE})
    public void downloadTemplateLinkTest() {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        test.info("Navigated to Portfolio Analysis Page");
        test.info("Clicked on Upload button");

        researchLinePage.clickUploadPortfolioButton();
        test.info("Navigated to Upload Page");

        test.info("Clicked on \"Download Template\" link");
        BrowserUtils.waitForVisibility(researchLinePage.downloadTemplateLink, 2);

        assertTestCase.assertEquals(researchLinePage.downloadTemplateLink.getText(), "Download Template", "Download Template is accessible", 188, 287);
        assertTestCase.assertTrue(researchLinePage.downloadTemplateLink.isEnabled(), "Template is visible", 192);
        test.pass("\"Download Template\" button's visibility verified.");
        test.pass("Verified: \"Download Template\" link is clickable.");

    }

    /**
     * The downloaded template, when clicked will launch Excel
     * and open the template
     */

    @Test(description = "Verify that the downloaded template, when clicked will launch Excel",
            groups = {REGRESSION, UI, SMOKE})
    public void verifyTemplateIsAccessibleTest() {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        test.info("Navigated to \"Portfolio Analysis Page\"");
        test.info("Clicked on Upload button");

        researchLinePage.clickUploadPortfolioButton();
        test.info("Navigated to Upload Page");

        test.info("Clicked on \"Download Template\" link");
        researchLinePage.clickDownloadTemplate();
        BrowserUtils.wait(4);
        assertTestCase.assertTrue(researchLinePage.isTemplateDownloaded(), "Template is saved to user machine", 248);

        assertTestCase.assertTrue(ExcelUtil.checkIf2CSVFilesAreSame(BrowserUtils.templatePath(), BrowserUtils.expectedPortfolioTemplateDocumentPath()),
                "Download template content check", 299, 317, 11079);

        Assert.assertEquals(researchLinePage.getDataFromCSV(0, 0), "Currency: Please only enter in values of USD or GBP or EUR. Platform values will be converted into USD. Only one currency is allowed per portfolio");
        test.pass("Excel was launched and this action was verified by reading a data from excel");
        test.info("Template was deleted");
        researchLinePage.deleteDownloadFolder();
    }


}
