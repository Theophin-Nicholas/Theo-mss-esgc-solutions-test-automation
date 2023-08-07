package com.esgc.Dashboard.UI.Tests;

import com.esgc.Base.TestBases.DashboardUITestBase;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class PerfomanceWidget extends DashboardUITestBase {

    @Test(groups = {DASHBOARD, REGRESSION, UI, SMOKE})
    @Xray(test = {3521})
    public void verifyPerformanceChartTotalControversies() {

        DashboardPage dashboardPage = new DashboardPage();
        try {
            dashboardPage.navigateToPageFromMenu("Climate Dashboard");
            BrowserUtils.wait(4);
            dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();

            dashboardPage.clickAndSelectAPerformanceChart("Largest Holding");

            dashboardPage.verifyTotalControversies();

            dashboardPage.clickAndSelectAPerformanceChart("Leaders");
            dashboardPage.verifyTotalControversies();

            dashboardPage.clickAndSelectAPerformanceChart("Laggards");
            dashboardPage.verifyTotalControversies();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
