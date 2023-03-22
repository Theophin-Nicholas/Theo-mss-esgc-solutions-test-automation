package com.esgc.Dashboard.UI.Tests;

import com.esgc.Base.TestBases.DashboardUITestBase;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.apache.commons.text.CaseUtils;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.time.Month;

import static com.esgc.Utilities.Groups.*;

public class DashboardSummaryHeader extends DashboardUITestBase {

    @Test(groups = {DASHBOARD, REGRESSION, UI, SMOKE}, dataProvider = "filters")
    @Xray(test = {3632, 5067, 6313, 6277, 6278, 7707, 8343, 4267})
    public void validateDashboardSummaryHeader(@Optional String sector, @Optional String region, @Optional String month, @Optional String year) {
        DashboardPage dashboardPage = new DashboardPage();
        ResearchLinePage researchLinePage = new ResearchLinePage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");

        String dateFilter = CaseUtils.toCamelCase(Month.of(Integer.valueOf(month)).name(), true, ' ') + " " + year;

        researchLinePage.selectOptionFromFiltersDropdown("regions", region);
        researchLinePage.selectOptionFromFiltersDropdown("as_of_date", dateFilter);
        BrowserUtils.wait(5);


        // ESGCA-5067: Verify the selected Portfolio widget details on the Climate Tile
        assertTestCase.assertTrue(dashboardPage.validateSelectedPortfolio(), "Verify selected portfolio name in summary");

        // ESGCA-3632: Verify Coverage is Displayed on Dashboard
        assertTestCase.assertTrue(dashboardPage.verifyCoverage(), "Verify coverage information in summary");

        //ESGCA-7707: UI Checks on Physical Risk Management card
        assertTestCase.assertTrue(dashboardPage.verifyPhysicalRiskWidget(), "Verify Physical Risk Widget in summary");

        // ESGCA-6313: Verify Facilities Exposed widget
        assertTestCase.assertTrue(dashboardPage.verifyFacilitiesExposedWidget(), "Verify Facilities Exposed Widget in summary");

    }

    @Test(groups = {DASHBOARD, REGRESSION, UI, SMOKE})
    @Xray(test = {6082, 4324, 7391})
    public void verifyStickyHeader() {
        DashboardPage dashboardPage = new DashboardPage();

        test.info("Navigate to Dashboard Page");
        dashboardPage.navigateToPageFromMenu("Dashboard");

        test.info("Verify sticky header information");
        BrowserUtils.wait(5);
        assertTestCase.assertTrue(dashboardPage.verifyStickyHeaderInfo(), "Verify Sticky Header Info");
    }

    @Test(groups = {DASHBOARD, REGRESSION, UI, SMOKE})
    @Xray(test = {4260, 5074, 5076, 7892})
    public void validateClimateTiles() {
        DashboardPage dashboardPage = new DashboardPage();
        test.info("Navigate to Dashboard Page");
        dashboardPage.navigateToPageFromMenu("Dashboard");
        BrowserUtils.wait(3);
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();
        System.out.println("Sample Portfolio Selected");
        dashboardPage.waitForDataLoadCompletion();
        test.info("Verify physical risk climate tile information");
        assertTestCase.assertTrue(dashboardPage.verifyPhysicalRiskClimateTile(), "Verify Physical Risk Climate Tile");

        test.info("Verify transition risk climate tile information");
        assertTestCase.assertTrue(dashboardPage.verifyTransitionRiskClimateTile(), "Verify Transition Risk Climate Tile");

        test.info("Verify View Companies and Investments link");
        dashboardPage.clickViewCompaniesAndInvestments();
        dashboardPage.verifyDrawerRegionCountryColumn();
        dashboardPage.selectViewByRegion();

        dashboardPage.closePortfolioExportDrawer();

    }

}
