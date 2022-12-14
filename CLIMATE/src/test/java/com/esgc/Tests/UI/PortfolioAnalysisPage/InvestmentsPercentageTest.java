package com.esgc.Tests.UI.PortfolioAnalysisPage;

import com.esgc.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class InvestmentsPercentageTest extends UITestBase {
//TODO this test case failing in UAT, needs attention
    @Test(groups = {"regression", "ui", "smoke"},
    dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines Investments")
    @Xray(test = 5719)
    public void verifyInvestmentPercentageLessThanOne(String... dataProvider) {

        String portfolio = dataProvider[0];
        String researchLine = dataProvider[1];
        String region = dataProvider[2];
        String sector = dataProvider[3];
        String month = dataProvider[4];
        String benchmark = dataProvider[5];

        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");

        researchLinePage.selectPortfolioByNameFromPortfolioSelectionModal(portfolio);
        researchLinePage.selectResearchLineFromDropdown(researchLine);

     /*   researchLinePage.clickRegionsSectionAndAsOfDateDropdown();
        researchLinePage.selectRegion(region);
        BrowserUtils.wait(5);

        researchLinePage.clickRegionsSectionAndAsOfDateDropdown();
        researchLinePage.selectSector(sector);
        BrowserUtils.wait(5);*/

        researchLinePage.clickRegionsSectionAndAsOfDateDropdown();
        researchLinePage.selectAsOfDate(month);
        BrowserUtils.wait(3);

        researchLinePage.clickOnBenchmarkDropdown();
        researchLinePage.SelectAPortfolioFromBenchmark(benchmark);
        BrowserUtils.wait(2);

        test.info("Verify Portfolio & Benchmark Summary section for Investments less than 1%");
        assertTestCase.assertTrue(researchLinePage.verifyInvestmentsLessthanOnePercentInPortfolioAndBenchmark(), "Portfolio & Benchmark Summary - Verify investment percentages which are <1%");

        test.info("Verify Portfolio Distribution section for Investments less than 1%");
        assertTestCase.assertTrue(researchLinePage.verifyInvestmentsLessthanOnePercentInPortfolioDistribution(), "Portfolio Distribution - Verify investment percentages which are <1%");

        test.info("Verify Benchmark Distribution section for Investments less than 1%");
        assertTestCase.assertTrue(researchLinePage.verifyInvestmentsLessthanOnePercentInBenchmarkDistribution(), "Benchmark Distribution - Verify investment percentages which are <1%");

        test.info("Verify Updates section for Investments less than 1%");
        assertTestCase.assertTrue(researchLinePage.verifyInvestmentsLessthanOnePercentInUpdatesSection(), "Updates Section - Verify investment percentages which are <1%");

        test.info("Verify Updates section Drawers for Investments less than 1%");
        assertTestCase.assertTrue(researchLinePage.verifyInvestmentsLessthanOnePercentInUpdatesDrawerSection(), "Updates Drawers Section - Verify investment percentages which are <1%");

        test.info("Verify Positive & Negative Impact sections - Investments less than 1%");
        assertTestCase.assertTrue(researchLinePage.verifyInvestmentsLessthanOnePercentInImpactSection(), "Positive & Negative Impact sections - Verify investment percentages which are <1%");

        test.info("Verify Positive & Negative Impact Drawers - Investments less than 1%");
        assertTestCase.assertTrue(researchLinePage.verifyInvestmentsLessthanOnePercentInImpactSectionDrawers(), "Positive & Negative Impact Drawers - Verify investment percentages which are <1%");

        test.info("Verify Leaders & Laggards sections - Investments less than 1%");
        assertTestCase.assertTrue(researchLinePage.verifyInvestmentsLessthanOnePercentInLeadersAndLaggards(), "Leaders & Laggards sections - Verify investment percentages which are <1%");

        test.info("Verify Leaders & Laggards Drawers - Investments less than 1%");
        assertTestCase.assertTrue(researchLinePage.verifyInvestmentsLessthanOnePercentInLeadersAndLaggardsDrawers(), "Leaders & Laggards Drawers - Verify investment percentages which are <1%");

        test.info("Verify Regions & Sectors - Investments less than 1%");
        assertTestCase.assertTrue(researchLinePage.verifyInvestmentsLessthanOnePercentInRegionsAndSectors(), "Regions & Sectors sections - Verify investment percentages which are <1%");

        test.info("Verify Regions & Sectors Drawers- Investments less than 1%");
        assertTestCase.assertTrue(researchLinePage.verifyInvestmentsLessthanOnePercentInRegionsAndSectorsDrawers(researchLine), "Regions & Sectors sections - Verify investment percentages which are <1%");

    }



}