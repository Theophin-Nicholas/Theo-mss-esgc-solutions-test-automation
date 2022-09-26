package com.esgc.Tests.UI.PortfolioAnalysisPage.ResearchLineWidgets;

import com.esgc.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class PortfolioUpdatesTest extends UITestBase {

    @Test(groups = {"regression", "ui", "smoke"},
            description = "Verify if Portfolio Updates Table is Displayed as Expected",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {1243, 2027})
    public void verifyPortfolioUpdates(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        if (page.equals("Temperature Alignment") ) {
            throw new SkipException(page +" - has no updates");
        }
        if ( page.equals("ESG Assessments")) {
            throw new SkipException("not ready to test in " + page);
        }
        researchLinePage.navigateToResearchLine(page);
        test.info("Navigated to " + page + " Page");

        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        test.info("Selected Sample Portfolio");

        researchLinePage.clickFiltersDropdown();
        test.info("Selecting the As of Date value");

        List<WebElement> updatesTableColumns = researchLinePage.selectAnAsOfDateWhereUpdatesAreAvailable("April 2021");
        test.info("Validate the column names in the Updates Table");
        System.out.println("updatesTableColumns = " + updatesTableColumns);

        assertTestCase.assertTrue(updatesTableColumns.get(0).getText().equalsIgnoreCase("Company"), "Column name verified: Company", 473, 1017, 1253, 1764);
        assertTestCase.assertTrue(updatesTableColumns.get(1).getText().equalsIgnoreCase("Updated"), "Column name verified: Updated", 473, 1017, 1253, 1764);
        assertTestCase.assertTrue(updatesTableColumns.get(2).getText().equalsIgnoreCase("Score")
                || updatesTableColumns.get(2).getText().equalsIgnoreCase("Score Range"), "Column name verified: Score", 473, 1017, 1253, 1764);
        assertTestCase.assertTrue(updatesTableColumns.get(3).getText().equalsIgnoreCase("Previous"), "Column name verified: Previous", 473, 1017, 1253, 1764);
        assertTestCase.assertTrue(updatesTableColumns.get(4).getText().equalsIgnoreCase("Previous Score")
                || updatesTableColumns.get(4).getText().equalsIgnoreCase("Previous Score Range"), "Column name verified: Previous Score", 473, 1017, 1253, 1764);
        assertTestCase.assertTrue(updatesTableColumns.get(5).getText().equalsIgnoreCase("% Investment"), "Column name verified: % Investment", 473, 1017, 1253, 1764);
        assertTestCase.assertTrue(updatesTableColumns.get(6).getText().equalsIgnoreCase("Sector"), "Column name verified: Sector", 473, 1017, 1253, 1764);
        assertTestCase.assertTrue(updatesTableColumns.get(7).getText().equalsIgnoreCase("Country"), "Column name verified: Country", 473, 1017, 1253, 1764);
        assertTestCase.assertTrue(updatesTableColumns.get(8).getText().equalsIgnoreCase("Region"), "Column name verified: Region", 473, 1017, 1253, 1764);

        assertTestCase.assertTrue(researchLinePage.verifyUpdatesHasMax10Companies(), "Updates has Max 10 companies", 476, 1518);

    }

    @Test(groups = {"regression", "ui"},
            description = "Verify if Updates As Of Modal Window is Displayed as Expected",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {1776, 2035, 2289})
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

        assertTestCase.assertTrue(researchLinePage.checkIfUpdatesAsOfModalWindowPresent(), "Updates Expand verified", 1776);
        if (Arrays.asList(new String[]{"Physical Risk Hazards", "Operations Risk", "Market Risk", "Supply Chain Risk"}).contains(page)) {
            assertTestCase.assertEquals(researchLinePage.drillDownTitle.getText(), "Updates as of April 2021", "Updates Drilldown Title Verification", 2034);
        } else {
            assertTestCase.assertEquals(researchLinePage.drillDownTitle.getText(), "Updates in April 2021", "Updates Drilldown Title Verification", 2034);
        }
        test.info("Found Updates as of April 2021");

//TODO updates drilldown headers and rows + drilldown should be closed.

    }

    @Test(groups = {"regression", "ui", "smoke"},
            description = "Verify if Updates Header is displayed as expected when filter is changed",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {477, 1951})
    public void verifyPortfolioUpdatesHeader(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine(page);
        test.info("Navigated to Research Line page");

        test.info("Navigated to " + page + " Page");
        researchLinePage.isUpdatesAndLeadersAndLaggardsHeaderDisplayUpdatedHeader(page, assertTestCase);
        test.info("Header is correctly updated");
    }
}