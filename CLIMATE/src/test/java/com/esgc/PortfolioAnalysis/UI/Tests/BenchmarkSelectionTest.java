package com.esgc.PortfolioAnalysis.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

/**
 * Created by ChaudhS2 on 12/6/2021.
 */
public class BenchmarkSelectionTest extends UITestBase {

    @Test(groups = {REGRESSION, UI, SMOKE},
            description = "ESGT-4698 - Verify User is Able to Select the Benchmark",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = 4698)
    public void verifyUserIsAbleToSelectBenchmarkPortfolio(String researchLine) {
        BrowserUtils.wait(2);
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToResearchLine(researchLine);
        BrowserUtils.wait(5);

        assertTestCase.assertEquals(researchLinePage.benchmarkDropdown.getText(), "Select Benchmark", "Benchmark dropdown verified", 5027, 5006);
        test.info("Select Benchmark is in dropdown");

        researchLinePage.clickOnBenchmarkDropdown();
        researchLinePage.waitForDataLoadCompletion();
        researchLinePage.SelectAPortfolioFromBenchmark("Sample Portfolio");

        assertTestCase.assertTrue(researchLinePage.IsBenchmarkListBoxDisplayed(), "Portfolio Analysis page was loaded successfully after Benchmark selection.", 4698, 4598);
        BrowserUtils.wait(3);
        assertTestCase.assertTrue(researchLinePage.isBenchmarkPortfolioScoreWidgetDisplayed(), "Benchmark Score validation", 4811, 1437);
        assertTestCase.assertTrue(researchLinePage.isBenchmarkPortfolioCoverageWidgetDisplayed(), "Benchmark Coverage validation", 4339);
        assertTestCase.assertTrue(researchLinePage.isBenchmarkColumnsAreDisplayedOnRegionAndSectorWidgets(), "Benchmark Columns validation", 3520, 4547);
        assertTestCase.assertTrue(researchLinePage.isBenchmarkMarksAreDisplayedOnRegionAndSectorWidgets(), "Benchmark Marks validation", 4988, 5044);

        researchLinePage.clickOnBenchmarkDropdown()
                .SelectAPortfolioFromBenchmark("No Benchmark");
        //researchLinePage.waitForDataLoadCompletion();

        assertTestCase.assertTrue(researchLinePage.isNoBenchmarkMessageDisplayed(), " No Benchmark message is displayed", 4659, 1434);
        assertTestCase.assertEquals(researchLinePage.benchmarkDropdown.getText(), "Select Benchmark", "Benchmark dropdown verified", 5027);
        test.info("Select Benchmark is in dropdown");

    }

    @Test(groups = {REGRESSION, UI, SMOKE},
            description = "ESGT-1768 - Verify the Benchmark coverage section is displayed when a Benchmark portfolio selected")
    @Xray(test = {5027, 1768, 4315, 4845})
    public void verifyBenchmarkCoverageSectionIsDisplayedAfterBenchmarkPortfolio() {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        BrowserUtils.wait(5);

        researchLinePage.clickOnBenchmarkDropdown()
                .SelectAPortfolioFromBenchmark("Sample Portfolio");
        researchLinePage.waitForDataLoadCompletion();

        assertTestCase.assertTrue(researchLinePage.IsBenchmarkCoverageSectionDisplayed(), "The Benchmark coverage section is displayed when a Benchmark portfolio selected");
        assertTestCase.assertTrue(researchLinePage.IsBenchmarkCoverageCompaniesSectionDisplayed(), "The Companies Label for the Benchmark coverage section is displayed when a Benchmark portfolio selected");
        assertTestCase.assertTrue(researchLinePage.IsBenchmarkCoverageInvestmentSectionDisplayed(), "The Investment Label for the Benchmark coverage section is displayed when a Benchmark portfolio selected");

    }

    @Test(groups = {REGRESSION, UI},
            description = "ESGT-1360 - Validate Score Title's for Portfolio and Benchmark Score")
    @Xray(test = 1360)
    public void verifyBenchmarkAndPortfolioScoreTitleAfterBenchmarkPortfolio() {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        BrowserUtils.wait(5);

        researchLinePage.clickOnBenchmarkDropdown()
                .SelectAPortfolioFromBenchmark("Sample Portfolio");
        researchLinePage.waitForDataLoadCompletion();

        assertTestCase.assertEquals(researchLinePage.getPortfolioScoreTitle(), "Portfolio", "The Benchmark coverage section is displayed when a Benchmark portfolio selected");
        assertTestCase.assertEquals(researchLinePage.getBenchmarkScoreTitle(), "Benchmark", "The Companies Label for the Benchmark coverage section is displayed when a Benchmark portfolio selected");

    }

    @Test(groups = {REGRESSION, UI},
            description = "ESGT-4659 - Validate No Benchmark option is present")
    @Xray(test = 4659)
    public void verifyNoBenchMark() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        test.info("Navigated to Portfolio Analysis page");
        BrowserUtils.wait(5);

        researchLinePage.clickOnBenchmarkDropdown()
                .SelectAPortfolioFromBenchmark("No Benchmark");
        researchLinePage.waitForDataLoadCompletion();

        test.info("No Benchmark option is present in the dropdown as expected");

        assertTestCase.assertTrue(researchLinePage.isNoBenchmarkMessageDisplayed(), " No Benchmark message is not displayed", 4659);
    }

    @Test(groups = {REGRESSION, UI},
            description = "ESGT-5185 - Verify changes persist after benchmark is selected")
    @Xray(test = 5185)
    public void verifyBenchmarkChangesPersistence() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        test.info("Navigated to Portfolio Analysis page");
        BrowserUtils.wait(10);

        BrowserUtils.wait(5);
        researchLinePage.clickOnBenchmarkDropdown();
        WebElement option = Driver.getDriver().findElement(By.xpath("//ul[@role='listbox']//..//li[text()='Sample Portfolio']"));
        BrowserUtils.wait(5);
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].click()", option);

        researchLinePage.navigateToResearchLine("Green Share Assessment");
        BrowserUtils.wait(10);
        assertTestCase.assertEquals(researchLinePage.benchmarkDropdown.getText(), "Sample Portfolio", "Sample portfolio selected as benchmark");

        test.info("Benchmark Changes are persisted across different research lines");
    }


    @Test(groups = {REGRESSION, UI},
            description = "ESGT-4598 - Verify carbon footprint benchmark score")
    @Xray(test = 4598)
    public void verifyCarbonFootprint() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        researchLinePage.navigateToResearchLine("Carbon Footprint");
        test.info("Navigated to Carbon Footprint page");
        BrowserUtils.wait(5);
        researchLinePage.clickOnBenchmarkDropdown();
        WebElement option = Driver.getDriver().findElement(By.xpath("//ul[@role='listbox']//..//li[text()='Sample Portfolio']"));
        BrowserUtils.wait(5);
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].click()", option);

        researchLinePage.clickFiltersDropdown();
        researchLinePage.selectOptionFromFiltersDropdown("as_of_date", "April 2021");
        researchLinePage.closeFilterByKeyboard();
        researchLinePage.checkScoreWidget("Carbon Footprint");

        test.info("Carbon Footprint score widget present");
    }

    @Test(groups = {REGRESSION, UI},
            description = "ESGT-4598 - Verify carbon footprint benchmark score")
    @Xray(test = 4598)
    public void verifyShadowedSelectBenchmark() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        test.info("Navigated to Portfolio Analysis page");
        BrowserUtils.wait(5);

        Assert.assertEquals(researchLinePage.benchmarkDropdown.getText(), "Select Benchmark");
        test.info("Select Benchmark is shadowed as expected");
    }
}
