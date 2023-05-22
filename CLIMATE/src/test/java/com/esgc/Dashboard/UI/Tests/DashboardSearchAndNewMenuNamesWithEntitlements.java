package com.esgc.Dashboard.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Base.UI.Pages.LoginPage;

import com.esgc.Dashboard.TestDataProviders.EntityWithEsgDataOnlyDataProviders;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.esgc.Utilities.Groups.*;

public class DashboardSearchAndNewMenuNamesWithEntitlements extends UITestBase {

    @BeforeMethod
    public void switchLoginSetup(Method method){
        LoginPage login = new LoginPage();
        if (method.getName().equals("validateNoDataForEntitiesWithEsgDataOnly")){
            login.entitlementsLogin(EntitlementsBundles.USER_CLIMATE_ESG);
        } else if (method.getName().equals("validateNoDataForEntitiesWithEsgDataOnlyWithPredictorExport")){
            login.entitlementsLogin(EntitlementsBundles.USER_CLIMATE_ESG_ESG_PREDICTOR_EXPORT);

        }
    }

    @Test(groups = {UI, REGRESSION, SMOKE}, dataProvider = "entityWithEsgDataOnly-DP", dataProviderClass = EntityWithEsgDataOnlyDataProviders.class)
    @Xray(test = {14094})
    public void validateNoDataForEntitiesWithEsgDataOnly(String... entity) {


        DashboardPage dashboardPage = new DashboardPage();
        System.out.println("---------------Logged back in using climate and esg entitlements--------------------");
        dashboardPage.clickSearchIcon();
        dashboardPage.searchEntity(entity[0]);
        dashboardPage.validateEntitiesWithOnlyEsgDataDontShowInSearch(entity[0]);

    }

    @Test(groups = {UI, REGRESSION, SMOKE}, dataProvider = "entityWithEsgDataOnly-DP", dataProviderClass = EntityWithEsgDataOnlyDataProviders.class)
    @Xray(test = {14094})
    public void validateNoDataForEntitiesWithEsgDataOnlyWithPredictorExport(String... entity) {
        DashboardPage dashboardPage = new DashboardPage();
        System.out.println("---------------Logged back in using climate esg- esg predictor and export entitlements--------------------");

        dashboardPage.clickSearchIcon();
        dashboardPage.searchEntity(entity[0]);
        dashboardPage.validateEntitiesWithOnlyEsgDataDontShowInSearch(entity[0]);
    }

    @Test(groups = {UI, REGRESSION})
    @Xray(test = {14017})
    public void ValidateRemovalOfCalculationsFromGlobalMenu(){
        LoginPage login = new LoginPage();
        DashboardPage dashboardPage = new DashboardPage();
        login.entitlementsLogin(EntitlementsBundles.TRANSITION_RISK_CLIMATE_GOVERNANCE);
        dashboardPage.clickOnMenuButton();
        dashboardPage.validateCalculationsFromGlobalMenuIsHidden();
    }

    @Test(groups = {UI, REGRESSION })
    @Xray(test = {14015})
    public void ValidateNewNameInGlobalMenu(){
        LoginPage login = new LoginPage();
        DashboardPage dashboardPage = new DashboardPage();

        login.entitlementsLogin(EntitlementsBundles.USER_WITH_PREDICTEDSCORE_AND_CLIMATE);
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
