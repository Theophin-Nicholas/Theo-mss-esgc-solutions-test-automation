package com.esgc.Tests.UI.DashboardPage;

import com.esgc.Pages.DashboardPage;
import com.esgc.Tests.TestBases.DashboardUITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class DashboardSummaryHeader extends DashboardUITestBase {

    @Test(groups = {"dashboard", "regression", "ui", "smoke"})
    @Xray(test = {3632, 5067, 6313, 7707, 8313, 8343})
    public void validateDashboardSummaryHeader(){
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");

        // ESGCA-5067: Verify the selected Portfolio widget details on the Climate Tile
        assertTestCase.assertTrue(dashboardPage.validateSelectedPortfolio(),"Verify selected portfolio name in summary");

        // ESGCA-3632: Verify Coverage is Displayed on Dashboard
        assertTestCase.assertTrue(dashboardPage.verifyCoverage(),"Verify coverage information in summary");

        // ESGCA-8313: Verify Weighted Average ESG Score widget is Displayed
        assertTestCase.assertTrue(dashboardPage.verifyAverageEsgScoreWidget(),"Verify Investment weighted average score in summary");

        // ESGCA-8322: Verify ESG Score is valid
        assertTestCase.assertTrue(dashboardPage.verifyEsgScoreValue(),"Verify ESG Score is valid");

        //ESGCA-7707: UI Checks on Physical Risk Management card
        assertTestCase.assertTrue(dashboardPage.verifyPhysicalRiskWidget(),"Verify Physical Risk Widget in summary");

        // ESGCA-6313: Verify Facilities Exposed widget
        assertTestCase.assertTrue(dashboardPage.verifyFacilitiesExposedWidget(),"Verify Facilities Exposed Widget in summary");

    }

    @Test(groups = {"dashboard", "regression", "ui", "smoke"})
    @Xray(test = {6082,4324})
    public void verifyStickyHeader(){
        DashboardPage dashboardPage = new DashboardPage();

        test.info("Navigate to Dashboard Page");
        dashboardPage.navigateToPageFromMenu("Dashboard");

        test.info("Verify sticky header information");
        BrowserUtils.wait(5);
        assertTestCase.assertTrue(dashboardPage.verifyStickyHeaderInfo(),"Verify Sticky Header Info");
    }

    @Test(enabled = false,groups = {"dashboard", "regression", "ui", "smoke"})
    @Xray(test = {4260, 5074,5076})
    public void validateClimateTiles(){
        DashboardPage dashboardPage = new DashboardPage();

        test.info("Navigate to Dashboard Page");
        dashboardPage.navigateToPageFromMenu("Dashboard");
        BrowserUtils.wait(3);
        test.info("Verify physical risk climate tile information");
        assertTestCase.assertTrue(dashboardPage.verifyPhysicalRiskClimateTile(),"Verify Physical Risk Climate Tile");

        test.info("Verify transition risk climate tile information");
        assertTestCase.assertTrue(dashboardPage.verifyTransitionRiskClimateTile(),"Verify Transition Risk Climate Tile");

        test.info("Verify View Companies and Investments link");
        dashboardPage.clickViewCompaniesAndInvestments();
        dashboardPage.selectViewByRegion();

    }

    @Test(groups = {"dashboard", "regression", "ui", "smoke"})
    @Xray(test = 8334)
    public void verifyViewMethodologies(){
        // ESGCA-8334: General UI Checks for Methodology Drawer
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.selectViewMethodologies();
        assertTestCase.assertTrue(dashboardPage.verifyMethodologiesPopup(),"Verify Methodologies popup");

        dashboardPage.clickHideLink();
    }

}
