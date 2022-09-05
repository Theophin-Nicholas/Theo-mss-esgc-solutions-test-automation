package com.esgc.Tests.UI.DashboardPage.Widgets;

import com.esgc.Pages.DashboardPage;
import com.esgc.Tests.TestBases.DashboardUITestBase;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class ControversiesTable extends DashboardUITestBase {

    @Test(groups = {"ui", "dashboard", "smoke", "regression"})
    @Xray(test = 3931)
    public void verifyPortfolioMonitoringIsDisplayed() {
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");

        dashboardPage.clickFiltersDropdown();
        dashboardPage.selectRandomOptionFromFiltersDropdown("as_of_date");
        dashboardPage.closeFilterByKeyboard();

        dashboardPage.waitForDataLoadCompletion();

        test.info("Check if Portfolio Monitoring Table / Controversies Table is displayed");

        String filters = dashboardPage.getRegionsSectionAndAsOfDateDropdownSelectedValue();

        String expectedTitle = "Portfolio Monitoring";
        String actualTitle = dashboardPage.getPortfolioMonitoringTableTitle();

        assertTestCase.assertEquals(actualTitle, expectedTitle, "Portfolio Monitoring Check", 4063);
        assertTestCase.assertTrue(dashboardPage.isControversiesTableDisplayed(), "Controversies table is Displayed");


    }
}
