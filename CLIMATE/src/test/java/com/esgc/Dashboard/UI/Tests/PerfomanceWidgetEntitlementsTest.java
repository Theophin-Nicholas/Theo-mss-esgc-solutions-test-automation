package com.esgc.Dashboard.UI.Tests;

import com.esgc.Base.TestBases.DashboardUITestBase;
import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.esgc.Utilities.Groups.*;

public class PerfomanceWidgetEntitlementsTest extends DashboardUITestBase {

    // to be de-scoped remove. validate with furkan ???
    @Test(groups = {"regression", "ui", "smoke","entitlements"})
    @Xray(test = {8691})
    public void validateEsgScoreEntitlementNotVailableBundle(){
        DashboardPage dashboardPage = new DashboardPage();
        LoginPage login = new LoginPage();

        test.info("Login with user is not having ESG Entitlement");
        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK);

        test.info("ESG Score widget in Dashboard Page");
        List<String> performanceChartTypes = Arrays.asList("Leaders", "Laggards", "Largest Holding");

        for (String performanceChartType : performanceChartTypes) {
            dashboardPage.clickAndSelectAPerformanceChart(performanceChartType);
            // to be de-scoped remove. validate with furkan ???
            assertTestCase.assertTrue(!dashboardPage.isOverAllESGColumAvailable(), "Verify Overall ESG Score coloumn is not avaialble in performance widget");
        }
    }

    @Test(groups = {REGRESSION, UI, SMOKE, ENTITLEMENTS})
    @Xray(test = {8692})
    public void validateTotalControversiesNotAvailableBundle(){
        DashboardPage dashboardPage = new DashboardPage();
        LoginPage login = new LoginPage();

        test.info("Login with user is not having ESG Entitlement");
        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK_TRANSITION_RISK);

        test.info("ESG Score widget in Dashboard Page");
        List<String> performanceChartTypes = Arrays.asList("Leaders", "Laggards", "Largest Holding");
        for (String performanceChartType : performanceChartTypes) {
            dashboardPage.clickAndSelectAPerformanceChart(performanceChartType);
            assertTestCase.assertTrue(!dashboardPage.isTotalControversiesColumAvailable(), "Verify Total Controversies coloumn is not avaialble in performance widget");
        }
    }




}
