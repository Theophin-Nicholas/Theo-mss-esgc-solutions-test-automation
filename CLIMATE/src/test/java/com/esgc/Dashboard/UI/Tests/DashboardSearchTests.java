package com.esgc.Dashboard.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Dashboard.TestDataProviders.EntityWithEsgDataOnlyDataProviders;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.Utilities.Environment;
import com.esgc.Utilities.Xray;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.REGRESSION;
import static com.esgc.Utilities.Groups.UI;

public class DashboardSearchTests extends UITestBase {

    @Test(groups = {UI, REGRESSION}, dataProvider = "entityWithEsgDataOnly-DP", dataProviderClass = EntityWithEsgDataOnlyDataProviders.class)
    @Xray(test = {2796})
    public void validateNoDataForEntitiesWithEsgDataOnly(String... entity) {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.clickSearchIcon();
        dashboardPage.searchEntity(entity[0]);
        dashboardPage.validateEntitiesWithOnlyEsgDataDontShowInSearch(entity[0]);
    }

    @Test(groups = {UI, REGRESSION})
    @Xray(test = {2841})
    public void ValidateRemovalOfCalculationsFromGlobalMenu() {
        if(Environment.environment.equalsIgnoreCase("qa"))
            throw new SkipException("Calculations option check is ignored for QA environment");
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.clickOnMenuButton();
        dashboardPage.validateCalculationsFromGlobalMenuIsHidden();
    }

    @Test(groups = {UI, REGRESSION})
    @Xray(test = {2813})
    public void ValidateNewNameInGlobalMenu() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.clickOnMenuButton();
        dashboardPage.validateNewDashboardMenuName();
        dashboardPage.validateNewPortfolioAnalysisMenuName();
        dashboardPage.validateNewReportingMenuName();
        dashboardPage.clickOnClimateDashboardMenuOption();
        dashboardPage.validateClimateDashboardPageHeaders();
        dashboardPage.clickOnMenuButton();
        dashboardPage.clickOnPortfolioAnalysisMenuOption();
        dashboardPage.validateClimatePortfolioAnalysisPageHeaders();
        dashboardPage.clickOnMenuButton();
        dashboardPage.clickOnReportingPortalMenuOption();
        dashboardPage.validateReportingPortalPageHeaders();

    }
}
