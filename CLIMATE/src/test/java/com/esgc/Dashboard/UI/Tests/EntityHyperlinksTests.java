package com.esgc.Dashboard.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class EntityHyperlinksTests extends UITestBase {
    @Test(groups = {REGRESSION, DASHBOARD, UI})
    @Xray(test = {4329, 4922, 3829})
    public void verifyDashboardHyperlinks() {

        test.info("Navigate to Dashboard page");
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Climate Dashboard");

        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();

        test.info("Verify view companies link");
        dashboardPage.viewAllCompaniesButton.click();
        String expPanelTitle = "Companies in " + dashboardPage.selectPortfolioButton.getText();
        String actPanelTitle = dashboardPage.summaryCompaniesPanelTitle.getText();
        assertTestCase.assertEquals(actPanelTitle, expPanelTitle,
                "Panel title is verified as Companies in <portfolio name>");
        dashboardPage.closePortfolioExportDrawer();
        BrowserUtils.wait(10);

        assertTestCase.assertTrue(dashboardPage.verifyDashboardHyperlinks("Portfolio Monitoring", 3),
                "Portfolio Monitoring - Verify Controversy Link");

        assertTestCase.assertTrue(dashboardPage.verifyDashboardHyperlinks("Portfolio Monitoring", 4),
                "Portfolio Monitoring - Verify Entity Hyperlink");

        assertTestCase.assertTrue(dashboardPage.verifyDashboardHyperlinks("Performance: Largest Holdings, Leaders, Laggards", 1),
                "Portfolio Monitoring - Verify Largest Holdings Company Hyperlink");

        dashboardPage.tabPerformanceLeaders.click();

        assertTestCase.assertTrue(dashboardPage.verifyDashboardHyperlinks("Performance: Largest Holdings, Leaders, Laggards", 1),
                "Portfolio Monitoring - Verify Performance Leaders Company Hyperlink");

        dashboardPage.tabPerformanceLaggards.click();

        assertTestCase.assertTrue(dashboardPage.verifyDashboardHyperlinks("Performance: Largest Holdings, Leaders, Laggards", 1),
                "Portfolio Monitoring - Verify Performance Laggards Company Hyperlink");

        //TODO Geomap is de-scoped until an update from business
//        ArrayList<String> GeographicRisks = new ArrayList<>();
//        GeographicRisks.add("Market Risk");
//        GeographicRisks.add("Operations Risk");
//        GeographicRisks.add("Supply Chain Risk");
//        GeographicRisks.add("Temperature Alignment");
//        GeographicRisks.add("Carbon Footprint");
//        GeographicRisks.add("Green Share Assessment");
//        GeographicRisks.add("Brown Share Assessment");
//
//        for (String risk : GeographicRisks) {
//            dashboardPage.selectResearchLineFromGeographicRiskMapDropDown(risk);
//            System.out.println("Geographic Risk = " + risk);
//            assertTestCase.assertTrue(dashboardPage.verifyGeographicRiskHyperlink(),
//                    "Geographic Risk Distribution - " + risk + ": Verify Entity Hyperlink");
//        }

        //TODO webelement is failing
        assertTestCase.assertTrue(dashboardPage.verifyHeatMapHyperlink(),
                "Heatmap: Verify Entity Hyperlink");

    }

    //TODO all researchline data provider
    @Test(groups = {REGRESSION, DASHBOARD, UI})
    @Xray(test = {4494, 3829})
    public void verifyPortfolioAnalysisHyperlinks() {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");

        DashboardPage dashboardPage = new DashboardPage();
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        researchLinePage.waitForDataLoadCompletion();

        assertTestCase.assertTrue(dashboardPage.verifyPortfolioAnalysisHyperlinks("Updates", 1),
                "Portfolio Analysis - Verify Updates Hyperlink");

        assertTestCase.assertTrue(dashboardPage.verifyPortfolioAnalysisHyperlinks("Positive impact based on investment and score", 1),
                "Portfolio Analysis - Verify Positive impact based on investment and score Hyperlink");

        assertTestCase.assertTrue(dashboardPage.verifyPortfolioAnalysisHyperlinks("Negative impact based on investment and score", 1),
                "Portfolio Analysis - Verify Negative impact based on investment and score Hyperlink");
        //TODO webelement is failing
        assertTestCase.assertTrue(dashboardPage.verifyPortfolioAnalysisHyperlinks("Leaders by Score", 2),
                "Portfolio Analysis - Verify Leaders by Score Hyperlink");

        assertTestCase.assertTrue(dashboardPage.verifyPortfolioAnalysisHyperlinks("Laggards by Score", 2),
                "Portfolio Analysis - Verify Laggards by Score Hyperlink");

        assertTestCase.assertTrue(dashboardPage.verifyRegionAndCountryHyperlinks("Europe, Middle East & Africa", 1),
                "Portfolio Analysis - Verify Europe, Middle East & Africa Entity Hyperlink");

        BrowserUtils.wait(10);
        assertTestCase.assertTrue(dashboardPage.verifyRegionAndCountryHyperlinks("Basic Materials", 1),
                "Portfolio Analysis - Verify Basic Materials Entity Hyperlink");

    }

}
