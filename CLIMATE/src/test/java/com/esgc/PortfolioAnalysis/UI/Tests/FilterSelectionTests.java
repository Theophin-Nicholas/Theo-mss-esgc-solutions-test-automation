package com.esgc.PortfolioAnalysis.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.support.Color;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

/**
 * Created by ChaudhS2 on 12/1/2021.
 */
public class FilterSelectionTests extends UITestBase {


    @Test(groups = {REGRESSION, UI, SMOKE},
            description = "ESGCA-1293 - Verify the Filters Selection When a User Refreshes  the Page")
    @Xray(test = {1293, 1300})
    public void verifyFiltersDefaultSelectionWithRefresh() {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        BrowserUtils.wait(5);
        researchLinePage.openSelectionModalPopUp();
        assertTestCase.assertTrue(researchLinePage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was opened");
        assertTestCase.assertTrue(researchLinePage.checkIfSearchBarIsDisplayed(), "Selection Modal Popup was opened");

        test.info("Select a Portfolio from the list.");
        researchLinePage.selectRandomPortfolioFromPortfolioSelectionModal();
        researchLinePage.waitForDataLoadCompletion();
        String portfolioNameBeforeRefresh = researchLinePage.getSelectedPortfolioNameFromDropdown();

        researchLinePage.clickFiltersDropdown();
        researchLinePage.selectRandomOptionFromFiltersDropdown("regions");
        researchLinePage.clickFiltersDropdown();
        researchLinePage.selectRandomOptionFromFiltersDropdown("sector");
        researchLinePage.clickFiltersDropdown();
        researchLinePage.selectRandomOptionFromFiltersDropdown("as_of_date");
            /*
            researchLinePage.selectOptionFromFiltersDropdown("regions","Americas");
            researchLinePage.selectOptionFromFiltersDropdown("sectors","Basic Materials");
            researchLinePage.selectOptionFromFiltersDropdown("as_of_date","March 2021");*/
        // researchLinePage.closeFilterByKeyboard();
        researchLinePage.waitForDataLoadCompletion();
        String filterDataBeforeRefresh = researchLinePage.getRegionsSectionAndAsOfDateDropdownSelectedValue();

        researchLinePage.refreshCurrentWindow();
        researchLinePage.waitForDataLoadCompletion();

        String portfolioNameAfterRefresh = researchLinePage.getSelectedPortfolioNameFromDropdown();
        String filterDataAfterRefresh = researchLinePage.getRegionsSectionAndAsOfDateDropdownSelectedValue();
        //TODO this might fail in parallel run due to same account. we might use different login test account to over come this issue
        assertTestCase.assertEquals(portfolioNameBeforeRefresh, portfolioNameAfterRefresh, "Default portfolio verified", 1293);
        assertTestCase.assertTrue(filterDataAfterRefresh.startsWith("Viewing data in All Regions, All Sectors, at the end of"), "Default filter verification");
        assertTestCase.assertNotEquals(filterDataBeforeRefresh, filterDataAfterRefresh, "After refreshing the Page , Filters are getting reset to default.");


    }


    @Test(groups = {REGRESSION, UI},
            description = "ESGCA-1292 - UI | Filters | Verify the filters selection for returning user")
    @Xray(test = 1292)
    public void verifyFiltersDefaultSelectionByNavigatingResearchLines() {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        BrowserUtils.wait(5);
        researchLinePage.openSelectionModalPopUp();
        assertTestCase.assertTrue(researchLinePage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was opened");
        assertTestCase.assertTrue(researchLinePage.checkIfSearchBarIsDisplayed(), "Selection Modal Popup was opened");

        test.info("Select a Portfolio from the list.");
        researchLinePage.selectRandomPortfolioFromPortfolioSelectionModal();

        researchLinePage.selectResearchLineFromDropdownByIndex(1);

        String portfolioNameBeforeRefresh = researchLinePage.getSelectedPortfolioNameFromDropdown();

        researchLinePage.clickFiltersDropdown();
        researchLinePage.selectRandomOptionFromFiltersDropdown("regions");
        researchLinePage.clickFiltersDropdown();
        researchLinePage.selectRandomOptionFromFiltersDropdown("sectors");
        researchLinePage.clickFiltersDropdown();
        researchLinePage.selectRandomOptionFromFiltersDropdown("as_of_date");
        // researchLinePage.closeFilterByKeyboard();
        researchLinePage.waitForDataLoadCompletion();
        String filterDataBeforeRefresh = researchLinePage.getRegionsSectionAndAsOfDateDropdownSelectedValue();

        researchLinePage.navigateToResearchLineByIndex(2);
        researchLinePage.waitForDataLoadCompletion();

        researchLinePage.navigateToResearchLineByIndex(1);

        String portfolioNameAfterRefresh = researchLinePage.getSelectedPortfolioNameFromDropdown();
        String filterDataAfterRefresh = researchLinePage.getRegionsSectionAndAsOfDateDropdownSelectedValue();
        System.out.println(filterDataAfterRefresh);
        assertTestCase.assertEquals(portfolioNameBeforeRefresh, portfolioNameAfterRefresh, "Default portfolio verified", 1292);
        assertTestCase.assertEquals(filterDataBeforeRefresh, filterDataAfterRefresh, "After navigating back to the Page , Filters are still same.");


    }

    @Test(groups = {REGRESSION, UI},
            description = "ESGCA-1294 - UI | Filters | Verify the Filters Selection once User Refresh the Page in case of Default Portfolio")
    @Xray(test = 1294)
    public void verifyFiltersDefaultSelectionWithBenchmarkByRefreshingPage() {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        BrowserUtils.wait(5);
        researchLinePage.openSelectionModalPopUp();
        assertTestCase.assertTrue(researchLinePage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was opened");
        assertTestCase.assertTrue(researchLinePage.checkIfSearchBarIsDisplayed(), "Selection Modal Popup was opened");

        test.info("Select a Portfolio from the list.");
        researchLinePage.selectRandomPortfolioFromPortfolioSelectionModal();

        researchLinePage.selectResearchLineFromDropdownByIndex(1);

        String portfolioNameBeforeRefresh = researchLinePage.getSelectedPortfolioNameFromDropdown();
        researchLinePage.clickFiltersDropdown();
        researchLinePage.selectRandomOptionFromFiltersDropdown("regions");
        researchLinePage.clickFiltersDropdown();
        researchLinePage.selectRandomOptionFromFiltersDropdown("sectors");
        researchLinePage.clickFiltersDropdown();
        researchLinePage.selectRandomOptionFromFiltersDropdown("as_of_date");
        //researchLinePage.closeFilterByKeyboard();
        researchLinePage.clickOnBenchmarkDropdown().selectRandomOptionFromFiltersDropdown("benchmark");

        researchLinePage.waitForDataLoadCompletion();
        String filterDataBeforeRefresh = researchLinePage.getRegionsSectionAndAsOfDateDropdownSelectedValue();
        String benchmarkBeforeRefresh = researchLinePage.getBenchmarkPortfolioName();

        researchLinePage.refreshCurrentWindow();
        researchLinePage.waitForDataLoadCompletion();

        String portfolioNameAfterRefresh = researchLinePage.getSelectedPortfolioNameFromDropdown();
        String filterDataAfterRefresh = researchLinePage.getRegionsSectionAndAsOfDateDropdownSelectedValue();
        String benchmarkAfterRefresh = researchLinePage.getBenchmarkPortfolioName();

        assertTestCase.assertEquals(portfolioNameBeforeRefresh, portfolioNameAfterRefresh, "Default portfolio verified", 1294);
        assertTestCase.assertTrue(filterDataAfterRefresh.startsWith("Viewing data in All Regions, All Sectors, at the end of"), "Default filter verification");
        System.out.println("filterDataBeforeRefresh = " + filterDataBeforeRefresh);
        System.out.println("filterDataAfterRefresh = " + filterDataAfterRefresh);
        assertTestCase.assertNotEquals(filterDataBeforeRefresh, filterDataAfterRefresh, "After refreshing the Page , Filters are getting reset to default.");
        assertTestCase.assertNotEquals(benchmarkBeforeRefresh, benchmarkAfterRefresh, "Benchmark dropdown verified");
        assertTestCase.assertEquals(benchmarkAfterRefresh, "Select Benchmark", "Benchmark dropdown verified");
        assertTestCase.assertTrue(researchLinePage.isNoBenchmarkMessageDisplayed(), " No Benchmark message is displayed", 1294);

    }

    @Test(groups = {REGRESSION, UI},
            description = "ESGCA-1308 - Verify the filter selection for 'As of date' in case of new updates when user refresh the page")
    @Xray(test = {1307, 1308})
    public void verifyFilterSelectionAsOfDateTest() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        BrowserUtils.wait(5);

        //Verify the 'As of date' filter is set to latest update date automatically
        researchLinePage.clickFiltersDropdown();
        String actBackgroundColor = Color.fromString(researchLinePage.asOfDateValues.get(0).getCssValue("background-color")).asHex();
        assertTestCase.assertEquals(actBackgroundColor, "#d7edfa", "'As of date' filter is set to latest update date automatically");

        //Refresh the page
        Driver.getDriver().navigate().refresh();

        //Verify the 'As of date' filter is set to latest update date automatically
        researchLinePage.clickFiltersDropdown();
        actBackgroundColor = Color.fromString(researchLinePage.asOfDateValues.get(0).getCssValue("background-color")).asHex();
        assertTestCase.assertEquals(actBackgroundColor, "#d7edfa", "'As of date' filter is set to latest update date automatically");
    }

    @Test(groups = {REGRESSION, UI},
            description = "ESGCA-1313 Verify the different sections are responding to the filter selections")
    @Xray(test = 1313)
    public void verifyDifferentSectionsRespondingSelectionsTest() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        BrowserUtils.waitForVisibility(researchLinePage.portfolioSelectionButton, 10);
        researchLinePage.openSelectionModalPopUp();
        assertTestCase.assertTrue(researchLinePage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was opened");
        assertTestCase.assertTrue(researchLinePage.checkIfSearchBarIsDisplayed(), "Selection Modal Popup was opened");

        test.info("Select a Portfolio from the list.");
        researchLinePage.selectRandomPortfolioFromPortfolioSelectionModal();

        //Verify the 'As of date' filter is set to latest update date automatically
        researchLinePage.clickFiltersDropdown();
        String regionName = researchLinePage.regionList.get(1).getText();
        researchLinePage.regionList.get(1).click();
        BrowserUtils.waitForVisibility(researchLinePage.asOfDateValues.get(0), 10);
        System.out.println("regionName = " + regionName);
        // researchLinePage.clickAwayinBlankArea();
        System.out.println(researchLinePage.filtersDropdown.getText());
        BrowserUtils.wait(5);
        //All the sections on page should respond to filters by displaying the rows/data in the portfolio that satisfy the criteria of the filter.

        assertTestCase.assertTrue(researchLinePage.filtersDropdown.getText().contains(regionName),
                "Region filter is set to " + regionName);
        researchLinePage.updatesCompaniesList.forEach(column -> assertTestCase.assertTrue(column.getText().endsWith(regionName),
                "Regions on update table is set to " + regionName));
        assertTestCase.assertEquals(researchLinePage.regionSectorChartNames.get(0).getText(), regionName,
                "Region on sector chart is set to " + regionName);
    }

    @Test(groups = {REGRESSION, UI},
            description = " ESGCA-1319 UI Verify User Changes Pages and Goes Back to Prior Page Without Updating the Portfolio Filter")
    @Xray(test = 1319)
    public void verifyUserChangesPagesPortfolioSelectionTest() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        BrowserUtils.waitForVisibility(researchLinePage.portfolioSelectionButton, 10);
        researchLinePage.openSelectionModalPopUp();
        assertTestCase.assertTrue(researchLinePage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was opened");
        assertTestCase.assertTrue(researchLinePage.checkIfSearchBarIsDisplayed(), "Selection Modal Popup was opened");

        // test.info("Select a Portfolio from the list.");
        researchLinePage.selectRandomPortfolioFromPortfolioSelectionModal();
        BrowserUtils.wait(10);
        //Verify the 'As of date' filter is set to latest update date automatically
        BrowserUtils.wait(4);
        researchLinePage.clickFiltersDropdown();
        researchLinePage.sectorList.get(1).click();
        BrowserUtils.wait(4);
        researchLinePage.clickFiltersDropdown();
        researchLinePage.regionList.get(1).click();
        BrowserUtils.wait(4);
        researchLinePage.clickFiltersDropdown();

        BrowserUtils.waitForVisibility(researchLinePage.asOfDateValues.get(0), 10);
        researchLinePage.asOfDateValues.get(1).click();
        // BrowserUtils.waitForVisibility(researchLinePage.asOfDateValues.get(0), 10);
        // researchLinePage.clickAwayinBlankArea();
        String expSelectedFilters = researchLinePage.filtersDropdown.getText();
        System.out.println("expSelectedFilters = " + expSelectedFilters);
        researchLinePage.selectResearchLineFromDropdownByIndex(1);
        String actSelectedFilters = researchLinePage.filtersDropdown.getText();
        assertTestCase.assertEquals(expSelectedFilters, actSelectedFilters,
                "Filters are verified for new research line ");
        researchLinePage.selectResearchLineFromDropdownByIndex(0);
        actSelectedFilters = researchLinePage.filtersDropdown.getText();
        assertTestCase.assertEquals(expSelectedFilters, actSelectedFilters,
                "Filters are verified for default research line ");
    }
}
