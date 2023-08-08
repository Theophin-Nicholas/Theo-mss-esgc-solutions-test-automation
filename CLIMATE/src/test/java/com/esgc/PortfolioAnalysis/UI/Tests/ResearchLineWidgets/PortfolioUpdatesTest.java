package com.esgc.PortfolioAnalysis.UI.Tests.ResearchLineWidgets;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.esgc.Utilities.Groups.*;

public class PortfolioUpdatesTest extends UITestBase {

    @Test(groups = {REGRESSION, UI, SMOKE}, description = "Verify if Updates Header is displayed as expected when filter is changed",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {4851, 5040, 4546, 5095, 5087, 4827, 3454, 4947})
    public void verifyPortfolioUpdatesHeader(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine(page);
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        test.info("Navigated to " + page + " Page");

        researchLinePage.isUpdatesAndLeadersAndLaggardsHeaderDisplayUpdatedHeader(page, assertTestCase);
        test.info("Header is correctly updated");
    }

    @Test(groups = {REGRESSION, UI, SMOKE},
            description = "Verify if Portfolio Updates Table is Displayed as Expected",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {4982, 4611, 3454, 3565, 3444})
    public void verifyPortfolioUpdates(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine(page);
        test.info("Navigated to " + page + " Page");

        if (page.equals("Temperature Alignment") || page.equals("ESG Assessments") ) {
            throw new SkipException(page +" - has no updates");
        }

        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        test.info("Selected Sample Portfolio");

        researchLinePage.clickFiltersDropdown();
        test.info("Selecting the As of Date value");

        List<WebElement> updatesTableColumns = researchLinePage.selectAnAsOfDateWhereUpdatesAreAvailable("April 2021");
        test.info("Validate the column names in the Updates Table");
        System.out.println("updatesTableColumns = ");
        updatesTableColumns.forEach( e -> System.out.println("e.getText() = " + e.getText()));

        assertTestCase.assertTrue(updatesTableColumns.get(0).getText().equalsIgnoreCase("Company"), "Column name verified: Company", 4989, 4621);
        assertTestCase.assertTrue(updatesTableColumns.get(1).getText().equalsIgnoreCase("Updated"), "Column name verified: Updated", 4989, 4621);
        assertTestCase.assertTrue(updatesTableColumns.get(2).getText().equalsIgnoreCase("Score")
                || updatesTableColumns.get(2).getText().equalsIgnoreCase("Score Range"), "Column name verified: Score", 4989, 4621);
        assertTestCase.assertTrue(updatesTableColumns.get(3).getText().equalsIgnoreCase("Previous"), "Column name verified: Previous", 4989, 4621);
        assertTestCase.assertTrue(updatesTableColumns.get(4).getText().equalsIgnoreCase("Previous Score")
                || updatesTableColumns.get(4).getText().equalsIgnoreCase("Previous Score Range"), "Column name verified: Previous Score", 4989, 4621);
        assertTestCase.assertTrue(updatesTableColumns.get(5).getText().equalsIgnoreCase("% Investment"), "Column name verified: % Investment", 4989, 4621);
        assertTestCase.assertTrue(updatesTableColumns.get(6).getText().equalsIgnoreCase("Sector"), "Column name verified: Sector", 4989, 4621);
        assertTestCase.assertTrue(updatesTableColumns.get(7).getText().equalsIgnoreCase("Country"), "Column name verified: Country", 4989, 4621);
        assertTestCase.assertTrue(updatesTableColumns.get(8).getText().equalsIgnoreCase("Region"), "Column name verified: Region", 4989, 4621);

        assertTestCase.assertTrue(researchLinePage.verifyUpdatesHasMax10Companies(), "Updates has Max 10 companies", 4530, 4596, 4578, 3467);
        assertTestCase.assertTrue(researchLinePage.verifyUpdatesSortingOrder(page), "Updates sorting order of companies", 4611,3565, 3457, 3412);

    }

    @Test(groups = {REGRESSION, UI},
            description = "Verify if Updates As Of Modal Window is Displayed as Expected",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {4648, 4433, 4835, 4539, 2215, 4639, 3658, 3417, 3638})
    public void verifyPortfolioUpdatesDrillDown(String page) {
        if (page.equals("Temperature Alignment") ) {
            throw new SkipException(page +" - Portfolio Distribution is not ready to test");
        }
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine(page);
        test.info("Navigated to " + page + " Page");

        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        test.info("Selected Sample Portfolio");


        test.info("Selecting the As of Date value");

        //researchLinePage.selectAnAsOfDateWhereUpdatesAreAvailable("April 2021");
        researchLinePage.clickFiltersDropdown();
        researchLinePage.selectOptionFromFiltersDropdown("as_of_date", "April 2021");
        //researchLinePage.closeFilterByKeyboard();
        researchLinePage.clickMoreCompaniesDrillDown(page);

        assertTestCase.assertTrue(researchLinePage.checkIfUpdatesAsOfModalWindowPresent(), "Updates Expand verified", 4648);
        if (Arrays.asList(new String[]{"Physical Risk Hazards", "Operations Risk", "Market Risk", "Supply Chain Risk","Brown Share Assessment"}).contains(page)) {
            assertTestCase.assertEquals(researchLinePage.drillDownTitle.getText(), "Updates as of April 2021", "Updates Drilldown Title Verification", 4538);
        } else {
            assertTestCase.assertEquals(researchLinePage.drillDownTitle.getText(), "Updates in April 2021", "Updates Drilldown Title Verification", 4538);
        }
        test.info("Found Updates as of April 2021");

        List<String> expectedColumnNames = new ArrayList<>();
        expectedColumnNames.add("Company");
        expectedColumnNames.add("Score");
        expectedColumnNames.add("% Investment");
        List<String> actualColumnNames = researchLinePage.getColumnNamesFromDrawer();
        Collections.sort(actualColumnNames);
        Collections.sort(expectedColumnNames);
        assertTestCase.assertTrue(actualColumnNames.equals(expectedColumnNames),"Column Names are not matching with Expected");

        researchLinePage.clickHideButtonInDrillDownPanel();

    }
}