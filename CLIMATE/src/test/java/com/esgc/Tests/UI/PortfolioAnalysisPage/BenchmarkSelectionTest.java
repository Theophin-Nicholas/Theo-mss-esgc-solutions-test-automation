package com.esgc.Tests.UI.PortfolioAnalysisPage;

import com.esgc.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * Created by ChaudhS2 on 12/6/2021.
 */
public class BenchmarkSelectionTest extends UITestBase {

    @Test(groups = {"regression", "ui", "smoke"},
            description = "ESGCA-1433 - Verify User is Able to Select the Benchmark",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = 1433)
    public void verifyUserIsAbleToSelectBenchmarkPortfolio(String researchLine) {
        BrowserUtils.wait(2);
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToResearchLine(researchLine);
        BrowserUtils.wait(5);

        assertTestCase.assertEquals(researchLinePage.benchmarkDropdown.getText(), "Select Benchmark", "Benchmark dropdown verified", 671);
        test.info("Select Benchmark is in dropdown");

        researchLinePage.clickOnBenchmarkDropdown();
        researchLinePage.waitForDataLoadCompletion();
        researchLinePage.SelectAPortfolioFromBenchmark("Sample Portfolio");

        assertTestCase.assertTrue(researchLinePage.IsBenchmarkListBoxDisplayed(), "Portfolio Analysis page was loaded successfully after Benchmark selection.", 1433, 2254);
        BrowserUtils.wait(3);
        researchLinePage.clickOnBenchmarkDropdown()
                .SelectAPortfolioFromBenchmark("No Benchmark");
        //researchLinePage.waitForDataLoadCompletion();

        assertTestCase.assertTrue(researchLinePage.isNoBenchmarkMessageDisplayed(), " No Benchmark message is displayed", 1329);
        assertTestCase.assertEquals(researchLinePage.benchmarkDropdown.getText(), "Select Benchmark", "Benchmark dropdown verified", 671);
        test.info("Select Benchmark is in dropdown");

    }

    @Test(groups = {"regression", "ui", "smoke"},
            description = "ESGCA-2594 - Verify the Benchmark coverage section is displayed when a Benchmark portfolio selected")
    @Xray(test = {671, 2594, 4084, 6734})
    public void verifyBenchmarkCoverageSectionIsDisplayedAfterBenchmarkPortfolio() {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        BrowserUtils.wait(5);

        researchLinePage.clickOnBenchmarkDropdown()
                .SelectAPortfolioFromBenchmark("Sample Portfolio");
        researchLinePage.waitForDataLoadCompletion();

        assertTestCase.assertTrue(researchLinePage.IsBenchmarkCoverageSectionDisplayed(), "The Benchmark coverage section is displayed when a Benchmark portfolio selected");
        assertTestCase.assertTrue(researchLinePage.IsBenchmarkCoverageCompaniesSectionDisplayed(), "The Companies Label for the Benchmark coverage section is displayed when a Benchmark portfolio selected");
        assertTestCase.assertTrue(researchLinePage.IsBenchmarkCoverageInvestmentSectionDisplayed(), "The Investment Label for the Benchmark coverage section is displayed when a Benchmark portfolio selected");

    }

    @Test(groups = {"regression", "ui"},
            description = "ESGCA-3879 - Validate Score Title's for Portfolio and Benchmark Score")
    @Xray(test = 3879)
    public void verifyBenchmarkAndPortfolioScoreTitleAfterBenchmarkPortfolio() {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        BrowserUtils.wait(5);

        researchLinePage.clickOnBenchmarkDropdown()
                .SelectAPortfolioFromBenchmark("Sample Portfolio");
        researchLinePage.waitForDataLoadCompletion();

        assertTestCase.assertEquals(researchLinePage.getPortfolioScoreTitle(), "Portfolio", "The Benchmark coverage section is displayed when a Benchmark portfolio selected");
        assertTestCase.assertEquals(researchLinePage.getBenchmarkScoreTitle(), "Benchmark", "The Companies Label for the Benchmark coverage section is displayed when a Benchmark portfolio selected");

    }

    @Test(groups = {"regression", "ui"},
            description = "ESGCA-1329 - Validate No Benchmark option is present")
    @Xray(test = 1329)
    public void verifyNoBenchMark() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        test.info("Navigated to Portfolio Analysis page");
        BrowserUtils.wait(5);

        researchLinePage.clickOnBenchmarkDropdown()
                .SelectAPortfolioFromBenchmark("No Benchmark");
        researchLinePage.waitForDataLoadCompletion();

        test.info("No Benchmark option is present in the dropdown as expected");

        assertTestCase.assertTrue(researchLinePage.isNoBenchmarkMessageDisplayed(), " No Benchmark message is not displayed", 1329);
    }

    @Test(groups = {"regression", "ui"},
            description = "ESGCA-1436 - Verify changes persist after benchmark is selected")
    @Xray(test = 1436)
    public void verifyBenchmarkChangesPersistence() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
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


    @Test(groups = {"regression", "ui"},
            description = "ESGCA-2254 - Verify carbon footprint benchmark score")
    @Xray(test = 2254)
    public void verifyCarbonFootprint() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
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

    @Test(groups = {"regression", "ui"},
            description = "ESGCA-2254 - Verify carbon footprint benchmark score")
    @Xray(test = 2254)
    public void verifyShadowedSelectBenchmark() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        test.info("Navigated to Portfolio Analysis page");
        BrowserUtils.wait(5);

        Assert.assertEquals(researchLinePage.benchmarkDropdown.getText(), "Select Benchmark");
        test.info("Select Benchmark is shadowed as expected");
    }


    @Test(enabled = false, groups = {"regression", "ui"}, //TODO disabled till ESG assessment scoped back.
            description = "Verify that Benchmark can be selected and displayed on Summary Section")
    @Xray(test = {8387})
    public void verifyESGAssessmentsBenchmarkSection() {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        BrowserUtils.wait(3);
        researchLinePage.navigateToResearchLine("ESG Assessments");
        BrowserUtils.wait(3);
        researchLinePage.clickOnBenchmarkDropdown()
                .SelectAPortfolioFromBenchmark("Sample Portfolio");

        assertTestCase.assertTrue(researchLinePage.ValidateifEsgBenchmarkPortfolioBoxIsDisplayed(), "The Benchmark Box is displayed");
        assertTestCase.assertTrue(researchLinePage.IsBenchmarkLableAvailable(), "Benchmark Label for the Benchmark coverage section is displayed when a Benchmark portfolio selected");
        assertTestCase.assertTrue(researchLinePage.IsBenchmarkScoreCategoryAvailable(), "Benchmark Score category is available");
        assertTestCase.assertTrue(researchLinePage.IsBenchmarkCoverageSectionAvailableAndMatchesWithPattern(), "Validate that Bechnmark Coverage section is diplayed");
        researchLinePage.validateBenchMarkDistributionSection();
    }
}
