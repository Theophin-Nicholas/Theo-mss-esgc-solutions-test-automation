package com.esgc.Dashboard.UI.Tests;

import com.esgc.Base.TestBases.DashboardUITestBase;
import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Environment;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class ControversiesModalTests extends DashboardUITestBase {

    @Test(enabled = false, groups = {REGRESSION, DASHBOARD, UI})
    @Xray(test = {1472, 4290, 1352, 1679, 4844, 4787, 4807, 4354, 1817, 4548, 3894})
    public void controversiesModalUIAutomationTest() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();
        dashboardPage.clickFiltersDropdown();

        dashboardPage.selectRandomOptionFromFiltersDropdown("as_of_date");
        dashboardPage.checkControversiesAreInLast60Days();
        System.out.println("dashboardPage.portfolioPanelTitle.getText() = " + dashboardPage.portfolioMonitoringTitle.getText());
        assertTestCase.assertEquals(dashboardPage.portfolioMonitoringTitle.getText(), "Portfolio Monitoring",
                "Portfolio Monitoring Title is verified");
        //for each controversy validate cells and modal once you click controversy

        dashboardPage.verifyControversiesAndValidateContentOfEachControversy();

        dashboardPage.clickMonthButtonOnPortfolioMonitoring();
        dashboardPage.checkControversiesAreInSelectedMonthAndYear();
        dashboardPage.verifyControversiesAndValidateContentOfEachControversy();

    }

    @Test(groups = {REGRESSION, DASHBOARD, UI})
    @Xray(test = 4890)
    public void controversiesFilterTest() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();

        dashboardPage.clickLast60DaysFilterButton();
        assertTestCase.assertTrue(dashboardPage.verifyLast60DaysControversies(), "Event is not falling in last 60 days");

        dashboardPage.clickMonthButtonOnPortfolioMonitoring();
        assertTestCase.assertTrue(dashboardPage.verifyLastMonthControversies(), "Event is not falling in last 30 days");

    }

    @Test(groups = {REGRESSION, DASHBOARD, UI, SMOKE, ENTITLEMENTS})
    @Xray(test = 3408)
    public void verifyControversiesAreVisible_Bundle() {

        LoginPage login = new LoginPage();
        login.login();

        DashboardPage dashboardPage = new DashboardPage();
        BrowserUtils.wait(10);
        assertTestCase.assertTrue(dashboardPage.isPortfolioMonitoringTableDisplayed(),
                "Verification of Portfolio Monitoring table");

        dashboardPage.clickViewCompaniesAndInvestments();

        String expPanelTitle = "Companies in " + dashboardPage.selectPortfolioButton.getText();
        String actPanelTitle = dashboardPage.summaryCompaniesPanelTitle.getText();
        assertTestCase.assertEquals(actPanelTitle, expPanelTitle,
                "Panel title is verified as Companies in <portfolio name>");

        assertTestCase.assertTrue(dashboardPage.isControversiesColumnDisplayed(),
                "Verification of Controversies column");

    }

    @Test(groups = {REGRESSION, DASHBOARD, UI, SMOKE, ENTITLEMENTS})
    @Xray(test = 3710)
    public void verifyControversiesAreNotVisible_Bundle() {

        LoginPage login = new LoginPage();
        if (Environment.environment.equalsIgnoreCase("prod")) {
            login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK);
        } else {
            login.entitlementsLogin(EntitlementsBundles.USER_WITH_OUT_CONTROVERSIES_ENTITLEMENT);
        }

        DashboardPage dashboardPage = new DashboardPage();
        BrowserUtils.wait(10);
        assertTestCase.assertTrue(!dashboardPage.isPortfolioMonitoringTableDisplayed(),
                "Verification of Portfolio Monitoring table");

        dashboardPage.clickViewCompaniesAndInvestments();

        String expPanelTitle = "Companies in " + dashboardPage.selectPortfolioButton.getText();
        String actPanelTitle = dashboardPage.summaryCompaniesPanelTitle.getText();
        assertTestCase.assertEquals(actPanelTitle, expPanelTitle,
                "Panel title is verified as Companies in <portfolio name>");

        assertTestCase.assertTrue(!dashboardPage.isControversiesColumnDisplayed(),
                "Verification of Controversies column");
    }

    @Test(groups = {REGRESSION, DASHBOARD, UI})
    @Xray(test = {1591, 1679, 3515, 3581, 3429, 3459, 3620})
    public void verifyCriticalAndNonCriticalControversies() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();

        dashboardPage.clickMonthButtonOnPortfolioMonitoring();
        BrowserUtils.wait(5);

        dashboardPage.checkControversiesAreInSelectedMonthAndYear();

        dashboardPage.clickPortfolioMonitoringCriticalTab();
        BrowserUtils.wait(5);
        String criticalControversies[][] = dashboardPage.getControversies();

        assertTestCase.assertTrue(dashboardPage.checkIfAllControversiesAreCritical(), "Verify Critical Controversies");

        dashboardPage.clickPortfolioMonitoringNotCriticalTab();
        BrowserUtils.wait(5);
        String notCriticalControversies[][] = dashboardPage.getControversies();

        assertTestCase.assertFalse(dashboardPage.checkIfAllControversiesAreCritical(), "Verify Non-Critical Controversies");

        dashboardPage.clickPortfolioMonitoringAllTab();
        BrowserUtils.wait(5);
        String allControversies[][] = dashboardPage.getControversies();

        System.out.println("All Controversies Count: " + allControversies.length);
        System.out.println("Critical Controversies Count: " + criticalControversies.length);
        System.out.println("Not Critical Controversies Count: " + notCriticalControversies.length);

        assertTestCase.assertTrue(allControversies.length == notCriticalControversies.length + criticalControversies.length, "Verify Sum of Critical and Non Critical Controversies is All Controversies count");
        assertTestCase.assertTrue(dashboardPage.compareArrays(allControversies, criticalControversies), "Verify all critical controversies are in All controversies ");
        assertTestCase.assertTrue(dashboardPage.compareArrays(allControversies, notCriticalControversies), "Verify all not critical controversies are in All controversies ");

    }

}
