package com.esgc.Dashboard.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static com.esgc.Utilities.Groups.*;

public class DashboardUiFilterTests extends UITestBase {
    @Test(groups = {REGRESSION, UI, SMOKE, DASHBOARD})
    @Xray(test = {1301, 1305, 3286})
    public void verifySectorsSorting() {

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Climate Dashboard");
        test.info("Navigated to Dashboard Page");

        dashboardPage.clickFiltersDropdown();

        assertTestCase.assertTrue(dashboardPage.isSectorFilterPresent(), "Sectors filter is not available");
        assertTestCase.assertTrue(dashboardPage.verifySectorsSort(), "Verify list of sectors are sorted");
        assertTestCase.assertTrue(dashboardPage.verifyDefaultSelectedSectorValue("All Sectors"), "Verify default selected sector value is 'All Sectors'");

    }

    @Test(groups = {REGRESSION, UI, SMOKE, DASHBOARD})
    @Xray(test = {1302, 1304, 3285})
    public void verifyRegions() {

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Climate Dashboard");
        test.info("Navigated to Dashboard Page");

        dashboardPage.clickFiltersDropdown();

        assertTestCase.assertTrue(dashboardPage.isRegionsFilterPresent(), "Regions filter is not available");

        ArrayList<String> expRegionsList = new ArrayList<>();
        expRegionsList.add("All Regions");
        expRegionsList.add("Americas");
        expRegionsList.add("Asia Pacific");
        expRegionsList.add("Europe, Middle East & Africa");

        assertTestCase.assertTrue(dashboardPage.verifyRegionValuesAndSort(expRegionsList), "Verify list of regions are sorted");
        assertTestCase.assertTrue(dashboardPage.verifyDefaultSelectedRegionValue("All Regions"), "Verify default selected region value is 'All Regions'");

    }

    @Test(groups = {REGRESSION, UI, SMOKE, DASHBOARD})
    @Xray(test = {1306, 3284})
    public void verifyAsOfDates() {

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Climate Dashboard");
        test.info("Navigated to Dashboard Page");

        dashboardPage.clickFiltersDropdown();
        dashboardPage.waitForDataLoadCompletion();
        assertTestCase.assertTrue(dashboardPage.isAsOfDateFilterPresent(), "As Of Date filter is not available");
        assertTestCase.assertTrue(dashboardPage.verifyAsOfDatesSort(), "Verify As Of Dates are sorted");
        assertTestCase.assertTrue(dashboardPage.verifyDefaultSelectedAsOfDateValue(), "Verify default selected as of date value is Current Month and Year");

    }

    @Test(groups = {REGRESSION, UI, SMOKE, DASHBOARD})
    @Xray(test = {7727})
    public void verifyDataAfterFilter() {

        DashboardPage dashboardPage = new DashboardPage();
        ResearchLinePage researchLine = new ResearchLinePage();
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();

        // Verify filter by using sector
        dashboardPage.navigateToPageFromMenu("Climate Dashboard");
        int initControversiesCount = dashboardPage.controversies.size();
        System.out.println("initControversiesCount = " + initControversiesCount);
        dashboardPage.clickFiltersDropdown();
        dashboardPage.selectOptionFromFiltersDropdown("sectors", "Financials");
        dashboardPage.waitForDataLoadCompletion();
        assertTestCase.assertTrue(!researchLine.isFiltersDropdownPopupDisplayed(), "Verify filter popup is closed");
        assertTestCase.assertTrue(dashboardPage.regionsDropdown.getText().contains("Financials"), "Verify the filter text");
        int afterControversiesCount = dashboardPage.controversies.size();
        System.out.println("afterControversiesCount = " + afterControversiesCount);
        assertTestCase.assertTrue(initControversiesCount>=afterControversiesCount);

        // Verify filter by using region
        dashboardPage.navigateToPageFromMenu("Climate Dashboard");
        initControversiesCount = dashboardPage.controversies.size();
        dashboardPage.clickFiltersDropdown();
        dashboardPage.selectOptionFromFiltersDropdown("regions","Europe, Middle East & Africa");
        dashboardPage.waitForDataLoadCompletion();
        System.out.println("Selected Region = " + dashboardPage.regionsDropdown.getText());
        assertTestCase.assertTrue(dashboardPage.regionsDropdown.getText().contains("Europe, Middle East & Africa"), "Verify the filter text");
        afterControversiesCount = dashboardPage.controversies.size();
        assertTestCase.assertTrue(initControversiesCount>=afterControversiesCount);

        dashboardPage.clickFiltersDropdown();
        researchLine.clickOutsideOfDrillDownPanel();
        BrowserUtils.wait(5);
        assertTestCase.assertTrue(!researchLine.isFiltersDropdownPopupDisplayed(), "Verify filter popup is closed");
    }
}
