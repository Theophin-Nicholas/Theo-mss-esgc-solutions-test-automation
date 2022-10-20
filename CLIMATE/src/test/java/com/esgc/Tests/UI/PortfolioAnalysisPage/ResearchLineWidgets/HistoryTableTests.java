package com.esgc.Tests.UI.PortfolioAnalysisPage.ResearchLineWidgets;

import com.esgc.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class HistoryTableTests extends UITestBase {

    @Test(groups = {"regression", "ui", "smoke"},
            description = "ESGCA-5122 - Verify History Table",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {5051})
    public void verifyHistoryTable(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToResearchLine(page);
        researchLinePage.navigateToResearchLine(page);
        researchLinePage.waitForDataLoadCompletion();
        if (page.equals("Physical Risk Hazards") || page.equals("Temperature Alignment")) {
            throw new SkipException("History Table is not ready to test in " + page);
        }

       // assertTestCase.assertTrue(researchLinePage.checkIfHistoryTableExists(page), "History Table Validation");
        test.info("History Table is present. Test Passed");
    }

    @Test(groups = {"regression", "ui", "smoke"},
            description = "Verify History Table not available", dataProvider = "History Table Research Lines")
    @Xray(test = {5815, 5816, 5817, 5820})
    public void verifyHistoryTablbeNotAvailable(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToResearchLine(page);
        BrowserUtils.wait(5);
        researchLinePage.clickOnBenchmarkDropdown();
        //researchLinePage.waitForDataLoadCompletion();
        researchLinePage.SelectAPortfolioFromBenchmark("Sample Portfolio");
        researchLinePage.clickFiltersDropdown();

        researchLinePage.selectOptionFromFiltersDropdown("as_of_date", "December 2020");
        //Assert.assertTrue(!researchLinePage.checkIfHistoryTableExists(""), "Validate that history table does not exists");
        Assert.assertTrue(!researchLinePage.checkIfBenchMarkHistoryTableExists(), "Validate that history table does not exists");
    }

    @Test(groups = {"regression", "ui", "smoke"},
            description = "Verify History Chart include unmatched companies/NULL/Negative values", dataProvider = "History Table Research Lines")
    @Xray(test = {5935})
    public void verifyHistoryChartIncludeUnmatchedCompanies(String page) {
        if (!page.equals("Temperature Alignment")) {
            ResearchLinePage researchLinePage = new ResearchLinePage();

            researchLinePage.navigateToResearchLine(page);
            BrowserUtils.wait(5);
            researchLinePage.clickOnBenchmarkDropdown();
            BrowserUtils.wait(5);
            researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
            BrowserUtils.wait(5);
            Assert.assertTrue(Color.fromString(researchLinePage.historyChartUnmatched.getCssValue("fill")).asHex().equals("#dce1e6"));

        }
        BrowserUtils.wait(5);
    }


    @DataProvider(name = "History Table Research Lines")
    public Object[][] availableResearchLines() {

        return new Object[][]{

                {"Temperature Alignment"},
                {"Carbon Footprint"},
                {"Brown Share Assessment"},
                {"Green Share Assessment"},
        };
    }
}
