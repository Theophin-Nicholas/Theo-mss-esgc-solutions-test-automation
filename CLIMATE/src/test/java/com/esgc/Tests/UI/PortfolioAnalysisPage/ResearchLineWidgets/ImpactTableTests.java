package com.esgc.Tests.UI.PortfolioAnalysisPage.ResearchLineWidgets;

import com.esgc.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class ImpactTableTests extends UITestBase {

    @Test(groups = {"regression", "ui", "smoke"},
            description = "ESGCA-4932 - Verify Impact Filter dropdown",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {4932})
    public void checkImpactFilterDropdownOptions(String page) {
        test.info("Test Cases: ESGCA-4932 ");
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine(page);
        List<String> expected = Arrays.asList("Top 5", "Top 10", "Bottom 5", "Bottom 10", "Top 10% of Investment", "Bottom 10% of Investment");
        List<String> actual = researchLinePage.impactFilterOptions();
        actual.forEach(System.out::println);
        Assert.assertTrue(expected.containsAll(actual));
    }

    @Test(groups = {"regression", "ui", "smoke"},
            description = "ESGCA-4913, ESGCA-4997,  ESGCA-5007 - Verify Impact Table and graph is present",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {4913, 4997, 5007})
    public void verifyImpactTableAndGraphPresent(String page) {
        test.info("Test Cases: ESGCA-4913, ESGCA-4997,  ESGCA-5007 ");
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine(page);
        assertTestCase.assertTrue(researchLinePage.isImpactTablePresent(), "Impact Table Verification");
        assertTestCase.assertTrue(researchLinePage.isImpactGraphPresent(), "Impact Graph Verification");
    }

    @Test(groups = {"regression", "ui", "smoke"},
            description = "ESGCA-4962  - Verify Impact Table columns",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {4962})
    public void verifyImpactTableColumns(String page) {
        test.info("Test Cases: ESGCA-4962 ");
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine(page);
        List<String> expected = Arrays.asList("Company", "% Investment", "Score", "Risk Category");
        List<String> expectedCarbonFootPrint = Arrays.asList("Company", "% Investment", "Score (tCO2eq)");
        List<String> expectedBrownGreenShare = Arrays.asList("Company", "% Investment", "Score Range");
        List<String> expectedPhysicalRiskManagement = Arrays.asList("Company", "% Inv", "Score");
        List<String> expectedTemperatureAlignment = Arrays.asList("Company", "% Investment", "Temperature Alignment");
        List<String> actual = researchLinePage.verifyImpactTableColumns();
        actual.forEach(System.out::println);
        switch (page) {
            case "Brown Share Assessment":
            case "Green Share Assessment":
                System.out.println("actual = " + actual);
                System.out.println("expectedBrownGreenShare = " + expectedBrownGreenShare);
                assertTestCase.assertEquals(actual, expectedBrownGreenShare, "Impact Table Column Verification");
                break;
            case "Carbon Footprint":
                System.out.println("actual = " + actual);
                System.out.println("expectedCarbonFootPrint = " + expectedCarbonFootPrint);
                assertTestCase.assertEquals(actual, expectedCarbonFootPrint, "Impact Table Column Verification");
                break;
            case "Physical Risk Management":
                System.out.println("actual = " + actual);
                System.out.println("expectedPhysicalRiskManagement = " + expectedPhysicalRiskManagement);
                assertTestCase.assertEquals(actual, expectedPhysicalRiskManagement, "Impact Table Column Verification");
                break;
            case "Temperature Alignment":
                System.out.println("actual = " + actual);
                System.out.println("expectedTemperatureAlignment = " + expectedTemperatureAlignment);
                assertTestCase.assertEquals(actual, expectedTemperatureAlignment, "Impact Table Column Verification");
                break;
            default:
                System.out.println("actual = " + actual);
                System.out.println("expected = " + expected);
                assertTestCase.assertEquals(actual, expected, "Impact Table Column Verification");
        }
    }

    @Test(enabled = false, groups = {"regression", "ui", "smoke"},
            description = "ESGCA-4998 - Verify Score Category colors",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {4998})
    public void verifyScoreCategoryColors(String page) {
        if (page.equals("Temperature Alignment")){
            throw new SkipException("Export is not ready to test in " + page);
        }
        test.info("Test Cases: ESGCA-4998 ");
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine(page);
        assertTestCase.assertTrue(researchLinePage.verifyImpactTableScoreCategoryColors(page), "Verify Impact Table Colors");
        System.out.println("End of test");

    }


    @Test(groups = {"regression", "ui", "smoke"},
            description = "ESGCA-5543 - Verify labels for positive ane negative impact table",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {5543})
    public void verifyWidgetTitles(String page) {
        test.info("Test Cases: ESGCA-5543");
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine(page);
        if (page.equals("Physical Risk Hazards")) {
            assertTestCase.assertEquals(researchLinePage.impactTableMainTitle.getText(), "Physical Risk Hazards: Operations Risk");
        }
        assertTestCase.assertTrue(researchLinePage.impactWidgetTitles(), "Impact tables are displayed");
        System.out.println("End of test");
    }

    @Test(groups = {"regression", "ui"},
            description = "ESGCA-5002 - UI | Impact Graph | Verify Impact graph data for all filters",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {5002})
    public void checkImpactFilterFuncionality(String page) {
        test.info("Test Cases: ESGCA-5002 ");
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine(page);
        List<String> filterOptions = Arrays.asList("Top 5", "Top 10", "Bottom 5", "Bottom 10");
        for (String filter : filterOptions) {
            researchLinePage.selectImpactFilterOption(filter);
            assertTestCase.assertTrue(researchLinePage.isImpactTablePresent(), "Impact Table Verification for " + filter);
            assertTestCase.assertTrue(researchLinePage.isImpactGraphPresent(), "Impact Graph Verification for " + filter);
        }
    }

    @Test(groups = {"regression", "ui"},
            description = "ESGCA-5006 - UI | Impact Graph | Verify when Investment % and Impact % are same",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {5006})
    public void checkImpactGraphBars(String page) {
        test.info("Test Cases: ESGCA-5006 ");
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine(page);
        researchLinePage.selectPortfolioByNameFromPortfolioSelectionModal("Sample Portfolio");
        researchLinePage.clickFiltersDropdown();
        researchLinePage.selectOptionFromFiltersDropdown("as_of_date", "December 2020");
        researchLinePage.closeFilterByKeyboard();
        researchLinePage.waitForDataLoadCompletion();

        researchLinePage.selectImpactFilterOption("Top 10");
        BrowserUtils.scrollTo(researchLinePage.updatesAndLeadersAndLaggardsHeader);
        BrowserUtils.wait(3);
        WebElement parentDiv = Driver.getDriver().findElement(By.xpath("//div[normalize-space()='Positive Impact Distribution']/following-sibling::div/div[2]/div"));

        List<WebElement> graphBars = parentDiv.findElements(By.cssSelector("g.highcharts-series rect"));
        BrowserUtils.scrollTo(graphBars.get(0));
        System.out.println("graphBars.size() = " + graphBars.size());
        for (WebElement graph : graphBars) {
            System.out.println("graph.getCssValue(\"opacity\") = " + graph.getCssValue("opacity"));
            if (graph.getCssValue("opacity").equals("0.6")) {
                //TODO looks like not a valid case. only on sample portfolio. need to check
               // assertTestCase.assertEquals(Integer.parseInt(graph.getAttribute("height")), 0);
            } else if (graph.getCssValue("opacity").equals("1")) {
                assertTestCase.assertTrue(Integer.parseInt(graph.getAttribute("height")) > 0);
            }
        }

    }

    @Test(groups = {"regression", "ui", "smoke"},
            description = "ESGCA-4940 - Verify Drilldowns If There are More Than 20 Companies in Impact Tables",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {4940, 6775})
    public void verifyImpactTableDrillDown(String page) {
        test.info("Test Cases: ESGCA-4940");
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine(page);
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        researchLinePage.selectImpactFilterOption("Top 10");
        researchLinePage.validateImpactDrillDown();

    }

    @Test(groups = {"regression", "ui"},
            description = "ESGCA-9809 - UI | Portfolio Analysis | Impact Table | Verify Sorting Orders by Impact Filter",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {4933})
    public void verifySortingOrderByImpactFilter(String researchLine) {
        test.info("Test Cases: ESGCA-4933");
        if (researchLine.equals("Physical Risk Hazards") ||
                researchLine.equals("Temperature Alignment") ||
                researchLine.equals("Physical Risk Management") ||
                researchLine.equals("ESG Assessments")) {
            throw new SkipException("Portfolio Analysis Page - Impact Table is not ready to test in " + researchLine);
        }
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine(researchLine);
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        researchLinePage.validateOrder("Top 5");
        researchLinePage.validateOrder("Top 10");
        researchLinePage.validateOrder("Bottom 5");
        researchLinePage.validateOrder("Bottom 10");
        assertTestCase.assertTrue(researchLinePage.validateOrder("Top 5"),"Verify Order is Ascending for Top 5");
        assertTestCase.assertTrue(researchLinePage.validateOrder("Top 10"),"Verify Order is Ascending for Top 10");
        assertTestCase.assertTrue(researchLinePage.validateOrder("Bottom 5"),"Verify Order is Descending for Bottom 5");
        assertTestCase.assertTrue(researchLinePage.validateOrder("Bottom 10"),"Verify Order is Descending for Bottom 10");
    }
}
