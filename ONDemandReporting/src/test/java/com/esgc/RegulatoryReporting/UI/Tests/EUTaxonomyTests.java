package com.esgc.RegulatoryReporting.UI.Tests;


import com.esgc.Common.UI.TestBases.UITestBase;
import com.esgc.RegulatoryReporting.UI.Pages.EUTaxonomyPage;
import com.esgc.RegulatoryReporting.UI.Pages.SFDRPage;
import com.esgc.TestBase.TestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;

import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.List;

import static com.esgc.Utilities.Groups.*;
import static com.esgc.Utilities.CommonUtility.randomBetween;

public class EUTaxonomyTests extends UITestBase {

    @Test(groups = {REGRESSION, UI, REGULATORY_REPORTING, SMOKE}, description = "Verify that user can navigate to Eu Taxonomy Reporting page")
    @Xray(test = {4271})
    public void verifyEUTaxonomyPageTest() {
        EUTaxonomyPage euTaxonomyPage = new EUTaxonomyPage();
        euTaxonomyPage.navigateToReportingService("EU");

        assertTestCase.assertTrue(euTaxonomyPage.isEUTaxonomyOptionIsAvailable(),
                "EU Taxonomy option verification", 4271, 3795);

        //euTaxonomyPage.clickOnEUTaxonomyOption();
        euTaxonomyPage.verifyEUTaxonomyHeaders();
        euTaxonomyPage.verifyEUTaxonomyTableContent();
        euTaxonomyPage.verifyDefaultReportingOptionsForEUTaxonomy();
        euTaxonomyPage.verifyLatestDataOptionCannotTurnedOffForEUTaxonomy();

        assertTestCase.assertTrue(euTaxonomyPage.verifyPreviouslyDownloadedButton(),
                "EU Taxonomy option verification", 4272);

        euTaxonomyPage.clickOnSFDRPAIsOption();

        SFDRPage sfdrPage = new SFDRPage();
        sfdrPage.verifySFDRHeaders();
        sfdrPage.verifyDefaultReportingOptionsForSFDR();

    }

    @Test(groups = {REGRESSION, UI, REGULATORY_REPORTING, SMOKE},
            description = "Verify  \"Non-Sovereign Derivatives\" and \"Cash and liquidities\" values remain when user navigated back to EU Taxonomy page")
    @Xray(test = {4270})
    public void verifyValuesAreRemainingForEUTaxonomy() {
        EUTaxonomyPage euTaxonomyPage = new EUTaxonomyPage();
        euTaxonomyPage.navigateToPageFromMenu("ESG Reporting Portal");
        TestBase.test.info("Navigated to Regulatory Reporting Page");

        euTaxonomyPage.clickOnEUTaxonomyOption();
        euTaxonomyPage.waitForPortfolioTableToLoad();
        boolean isSamplePortfolioInPortfolioNames = euTaxonomyPage.getPortfolioList().contains("Sample Portfolio");

        assertTestCase.assertFalse(isSamplePortfolioInPortfolioNames, "Sample Portfolio should not be in the list", 4262);

        euTaxonomyPage.selectAllPortfolioOptions();
        List<String> selectedPortfoliosNames = euTaxonomyPage.getSelectedPortfolioOptions();
        euTaxonomyPage.deselectAllPortfolioOptions();
        int index = 1;
        for (String portfolioName : selectedPortfoliosNames) {
            euTaxonomyPage.selectPortfolio( portfolioName);
            if(index == 1)
                assertTestCase.assertEquals(euTaxonomyPage.getCreateReportsButtonText(), "Create "+index+" Report", "Create Reports button is verified for 1 portfolio selected", 3895);
            else
                assertTestCase.assertEquals(euTaxonomyPage.getCreateReportsButtonText(), "Create "+index+" Reports", "Create Reports button is verified for 2 portfolio selected", 3895);
            index++;
        }

        //List<String> selectedPortfoliosNames = euTaxonomyPage.getSelectedPortfolioOptions();

        double random1 = randomBetween(100, 1000) / 10d;
        double random2 = randomBetween(100, 1000) / 10d;

        for (String portfolioName : selectedPortfoliosNames) {
            euTaxonomyPage.enterEUTaxonomyValues(portfolioName, String.valueOf(random1), String.valueOf(random2));
        }

        euTaxonomyPage.clickOnCreateReportsButton();
        BrowserUtils.wait(5);
        String currentWindow = BrowserUtils.getCurrentWindowHandle();
        String generateReportWindow = BrowserUtils.getWindowHandles().stream().filter(e -> !e.equals(currentWindow)).findFirst().get();
        BrowserUtils.switchWindowsTo(generateReportWindow);

        Driver.closeBrowserTab();
        BrowserUtils.switchWindow(currentWindow);

        euTaxonomyPage.navigateToPageFromMenu("Climate Dashboard");
        euTaxonomyPage.navigateToPageFromMenu("ESG Reporting Portal");
        TestBase.test.info("User goes back to EU taxonomy");

        euTaxonomyPage.clickOnEUTaxonomyOption();

        for (String portfolioName : selectedPortfoliosNames) {
            double actual1 = euTaxonomyPage.getNonSovereignDerivativesValueByPortfolioName(portfolioName);
            double actual2 = euTaxonomyPage.getCashAndLiquiditiesValueByPortfolioName(portfolioName);

            assertTestCase.assertEquals(actual1, random1);
            assertTestCase.assertEquals(actual2, random2);

        }
    }

}
