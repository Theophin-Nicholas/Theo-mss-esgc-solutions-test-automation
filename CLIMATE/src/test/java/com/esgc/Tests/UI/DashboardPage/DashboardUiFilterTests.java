package com.esgc.Tests.UI.DashboardPage;

import com.esgc.Pages.DashboardPage;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class DashboardUiFilterTests extends UITestBase {
    @Test(groups = {"regression", "ui", "smoke", "dashboard"})
    @Xray(test = {1301, 1305, 3286})
    public void verifySectorsSorting() {

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");

        dashboardPage.clickFiltersDropdown();

        assertTestCase.assertTrue(dashboardPage.isSectorFilterPresent(),"Sectors filter is not available");
        assertTestCase.assertTrue(dashboardPage.verifySectorsSort(), "Verify list of sectors are sorted");
        assertTestCase.assertTrue(dashboardPage.verifyDefaultSelectedSectorValue("All Sectors"), "Verify default selected sector value is 'All Sectors'");

    }

    @Test(groups = {"regression", "ui", "smoke", "dashboard"})
    @Xray(test = {1302, 1304, 3285})
    public void verifyRegions() {

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");

        dashboardPage.clickFiltersDropdown();

        assertTestCase.assertTrue(dashboardPage.isRegionsFilterPresent(),"Regions filter is not available");

        ArrayList<String> expRegionsList = new ArrayList<>();
        expRegionsList.add("All Regions");
        expRegionsList.add("Americas");
        expRegionsList.add("Asia Pacific");
        expRegionsList.add("Europe, Middle East & Africa");

        assertTestCase.assertTrue(dashboardPage.verifyRegionValuesAndSort(expRegionsList), "Verify list of regions are sorted");
        assertTestCase.assertTrue(dashboardPage.verifyDefaultSelectedRegionValue("All Regions"), "Verify default selected region value is 'All Regions'");

    }

    @Test(groups = {"regression", "ui", "smoke", "dashboard"})
    @Xray(test = {1306,3284})
    public void verifyAsOfDates() {

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");

        dashboardPage.clickFiltersDropdown();

        assertTestCase.assertTrue(dashboardPage.isAsOfDateFilterPresent(),"As Of Date filter is not available");
        assertTestCase.assertTrue(dashboardPage.verifyAsOfDatesSort(), "Verify As Of Dates are sorted");
        assertTestCase.assertTrue(dashboardPage.verifyDefaultSelectedAsOfDateValue(), "Verify default selected as of date value is Current Month and Year");

    }
}
