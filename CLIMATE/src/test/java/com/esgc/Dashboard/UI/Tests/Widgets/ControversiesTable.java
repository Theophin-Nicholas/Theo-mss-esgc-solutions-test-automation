package com.esgc.Dashboard.UI.Tests.Widgets;

import com.esgc.Base.TestBases.DashboardUITestBase;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class ControversiesTable extends DashboardUITestBase {

    @Test(groups = {UI, DASHBOARD, SMOKE, REGRESSION})
    @Xray(test = 3931)
    public void verifyPortfolioMonitoringIsDisplayed() {
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Climate Dashboard");
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
