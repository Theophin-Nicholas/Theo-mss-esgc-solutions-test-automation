package com.esgc.Tests.UI.DashboardPage;

import com.esgc.Pages.DashboardPage;
import com.esgc.Pages.ResearchLinePage;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class EntityHyperlinksTests extends UITestBase {
    @Test(groups = {"regression", "dashboard", "ui"})
    @Xray(test = {4040, 6814, 6865})
    public void verifyDashboardHyperlinks() {

        test.info("Navigate to Dashboard page");
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");

        dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("Sample Portfolio");

        test.info("Verify view companies link");
        dashboardPage.viewAllCompaniesButton.click();
        String expPanelTitle = "Companies in " + dashboardPage.selectPortfolioButton.getText();
        String actPanelTitle = dashboardPage.summaryCompaniesPanelTitle.getText();
        assertTestCase.assertEquals(actPanelTitle, expPanelTitle,
                "Panel title is verified as Companies in <portfolio name>");
        dashboardPage.closePanelBtn.click();
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

        ArrayList<String> GeographicRisks = new ArrayList<>();
        GeographicRisks.add("Market Risk");
        GeographicRisks.add("Operations Risk");
        GeographicRisks.add("Supply Chain Risk");
        GeographicRisks.add("Temperature Alignment");
        GeographicRisks.add("Carbon Footprint");
        GeographicRisks.add("Green Share Assessment");
        GeographicRisks.add("Brown Share Assessment");

        for (String risk : GeographicRisks) {
            dashboardPage.selectResearchLineFromGeographicRiskMapDropDown(risk);
            System.out.println("Geographic Risk = " + risk);
            assertTestCase.assertTrue(dashboardPage.verifyGeographicRiskHyperlink(),
                    "Geographic Risk Distribution - " + risk + ": Verify Entity Hyperlink");
        }

        assertTestCase.assertTrue(dashboardPage.verifyHeatMapHyperlink(),
                "Heatmap: Verify Entity Hyperlink");

    }

    @Test(groups = {"regression", "dashboard", "ui"})
    @Xray(test = {6566, 6865})
    public void verifyPortfolioAnalysisHyperlinks() {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("Sample Portfolio");

        assertTestCase.assertTrue(dashboardPage.verifyPortfolioAnalysisHyperlinks("Updates", 1),
                "Portfolio Analysis - Verify Updates Hyperlink");

        assertTestCase.assertTrue(dashboardPage.verifyPortfolioAnalysisHyperlinks("Positive impact based on investment and score", 1),
                "Portfolio Analysis - Verify Positive impact based on investment and score Hyperlink");

        assertTestCase.assertTrue(dashboardPage.verifyPortfolioAnalysisHyperlinks("Negative impact based on investment and score", 1),
                "Portfolio Analysis - Verify Negative impact based on investment and score Hyperlink");

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
