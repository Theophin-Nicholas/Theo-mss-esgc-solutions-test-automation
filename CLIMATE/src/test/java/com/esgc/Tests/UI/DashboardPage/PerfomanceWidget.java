package com.esgc.Tests.UI.DashboardPage;

import com.esgc.Pages.DashboardPage;
import com.esgc.Pages.LoginPage;
import com.esgc.Pages.ResearchLinePage;
import com.esgc.Tests.TestBases.DashboardUITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.List;

public class PerfomanceWidget extends DashboardUITestBase {

    @Test(groups = {"dashboard", "regression", "ui", "smoke"})
    @Xray(test = {8685})
    public void verifyPerformanceChartESGCatogories () {

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        BrowserUtils.wait(4);
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();
        dashboardPage.clickAndSelectAPerformanceChart("Largest Holding");
        dashboardPage.verifyOverallESGScoreCatgories();

        dashboardPage.clickAndSelectAPerformanceChart("Leaders");
        dashboardPage.verifyOverallESGScoreCatgories();

        dashboardPage.clickAndSelectAPerformanceChart("Laggards");
        dashboardPage.verifyOverallESGScoreCatgories();
    }

    @Test(groups = {"dashboard", "regression", "ui", "smoke"})
    @Xray(test = {8686})
    public void verifyPerformanceChartTotalControversies() {

        DashboardPage dashboardPage = new DashboardPage();
        try {
            dashboardPage.navigateToPageFromMenu("Dashboard");
            BrowserUtils.wait(4);
            dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();

            dashboardPage.clickAndSelectAPerformanceChart("Largest Holding");

            dashboardPage.verifyOverallESGTotalControversies();

            dashboardPage.clickAndSelectAPerformanceChart("Leaders");
            dashboardPage.verifyOverallESGTotalControversies();

            dashboardPage.clickAndSelectAPerformanceChart("Laggards");
            dashboardPage.verifyOverallESGTotalControversies();
        }catch(Exception e){
            e.printStackTrace();
        }
    }



}
