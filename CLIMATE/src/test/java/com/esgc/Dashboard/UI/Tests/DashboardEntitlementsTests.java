package com.esgc.Dashboard.UI.Tests;

import com.esgc.Base.TestBases.DashboardUITestBase;
import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.esgc.Utilities.Groups.*;

public class DashboardEntitlementsTests extends DashboardUITestBase {

    @Test(groups = {DASHBOARD, REGRESSION, UI, SMOKE, ENTITLEMENTS},
            dataProviderClass = DataProviderClass.class, dataProvider = "bundles")
    @Xray(test = {4773, 3998})
    public void validateEntitlementsBundleInDashboard(@Optional EntitlementsBundles bundleName, @Optional Integer... testCase) {

        DashboardPage dashboardPage = new DashboardPage();

        LoginPage login = new LoginPage();

        System.out.println("bundleName = " + bundleName);

        login.entitlementsLogin(bundleName);
        dashboardPage.navigateToPageFromMenu("Climate Dashboard");

        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();

        dashboardPage.clickFiltersDropdown();
        dashboardPage.selectOptionFromFiltersDropdown("as_of_date", "March 2022");


        List<String> expectedSummaryHeaderBundleTitles = dashboardPage.getExpectedListOfSummaryHeaderBundleNames(bundleName);
        List<String> actualSummaryHeaderBundleTitles = dashboardPage.getSummaryHeadersBundleNames();
        BrowserUtils.wait(3);
        assertTestCase.assertEquals(actualSummaryHeaderBundleTitles,
                expectedSummaryHeaderBundleTitles, "Validating Summary Header Bundle Titles", testCase);

        List<String> expectedSummaryTitles = dashboardPage.getExpectedListOfSummaryHeadersTileNames(bundleName);
        List<String> actualSummaryTitles = dashboardPage.getSummaryHeadersColumnNames();

        assertTestCase.assertEquals(actualSummaryTitles,
                expectedSummaryTitles, "Validating Summary Header Tile Titles", testCase);


        List<String> performanceCharts = Arrays.asList("March 2022 Leaders", "March 2022 Laggards", "Largest Holdings");

        for (String performanceChartName : performanceCharts) {
            dashboardPage.clickAndSelectAPerformanceChart(performanceChartName);

            //validate performance chart research line column names are expected
            List<String> actualPerformanceChartsColumnNames = dashboardPage.getPerformanceChartColumnNames();
            test.info("Available columns on performance chart " + actualPerformanceChartsColumnNames);

            System.out.println("actualPerformanceChartsColumnNames = " + actualPerformanceChartsColumnNames);
            System.out.println("dashboardPage.getExpectedListOfPerformanceChartColumnNames(bundleName) = " + dashboardPage.getExpectedListOfPerformanceChartColumnNames(bundleName));
            assertTestCase.assertEquals(actualPerformanceChartsColumnNames,
                    dashboardPage.getExpectedListOfPerformanceChartColumnNames(bundleName),
                    "Validating list of accessible research lines in performance charts", testCase);
        }

        //TODO Geomap is de-scoped until an update from business
//        List<String> getAvailableResearchLinesFromMap =
//                dashboardPage.getAvailableResearchLinesFromGeographicRiskDistribution();
//        List<String> expectedGeoMapResearchLines = dashboardPage.getExpectedListOfGeoMapResearchLines(bundleName);
//        System.out.println("dashboardPage.getExpectedListOfPerformanceChartColumnNames(bundleName) = " + dashboardPage.getExpectedListOfPerformanceChartColumnNames(bundleName));
//        System.out.println("expectedGeoMapResearchLines = " + expectedGeoMapResearchLines);
//        System.out.println("ActualResearchLinesFromMap = " + getAvailableResearchLinesFromMap);
//        assertTestCase.assertEquals(getAvailableResearchLinesFromMap,
//                expectedGeoMapResearchLines, "Validating Geographic Map Research Lines", 1331);

        List<String> actualHeatMapResearchLines = dashboardPage.getAvailableResearchLinesFromHeatMapResearchLineSelection();
        System.out.println("actualHeatMapResearchLines = " + actualHeatMapResearchLines);
        System.out.println("dashboardPage.getExpectedListOfHeatMapResearchLines(bundleName) = " + dashboardPage.getExpectedListOfHeatMapResearchLines(bundleName));
        assertTestCase.assertEquals(actualHeatMapResearchLines,
                dashboardPage.getExpectedListOfHeatMapResearchLines(bundleName),
                "Validating Heat Map Research Lines", testCase);
    }




}
