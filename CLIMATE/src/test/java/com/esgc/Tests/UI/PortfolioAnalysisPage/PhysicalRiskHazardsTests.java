package com.esgc.Tests.UI.PortfolioAnalysisPage;

import com.esgc.Pages.ResearchLinePage;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.ResearchLineColors;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.support.Color;
import org.testng.annotations.Test;

public class PhysicalRiskHazardsTests extends UITestBase {

    @Test(groups = {"regression", "ui"})
    @Xray(test = {6068, 6069,6070,6071,6072})
    public void validatePhysicalRiskPage() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        researchLinePage.selectResearchLineFromDropdown("Physical Risk Hazards");
        assertTestCase.assertTrue(researchLinePage.isPhysicalRiskHazardTextsDisplayed(),
                "Underlying Data Operations Risk Facilities Exposed Widget is displayed under Portfolio Summary section");
        assertTestCase.assertTrue(researchLinePage.showTheExactNumberInsteadOfLessSign(),
                "Percentages on the widget show the exact number instead of <1% " );

    }

    @Test(groups = {"regression", "ui"})
    @Xray(test = {6362,6364,6365,6366,7102})
    public void verifyPortfolioScoreSectionTest() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        researchLinePage.selectResearchLineFromDropdown("Physical Risk Hazards");
        assertTestCase.assertTrue(researchLinePage.isPhysicalRiskHazardTextsDisplayed(),
                "Underlying Data Operations Risk Facilities Exposed Widget is displayed under Portfolio Summary section");
        //-Title must say: Portfolio(vertically)
        assertTestCase.assertEquals(researchLinePage.PortfolioScoreTitle.getText().trim(),"Portfolio","Portfolio Score title is verified");
        assertTestCase.assertEquals(researchLinePage.PortfolioScoreTitle.getCssValue("transform"), "matrix(-1, 1.22465e-16, -1.22465e-16, -1, 0, 0)",
                        "Portfolio Score title is rotated");
        //Highest Risk Hazard: The highest Physical risk hazard score exposed to your portfolio or company | Highest Risk Hazard: Water Stress
        System.out.println(researchLinePage.physicalRiskHazardsScoreWidget.getText());
        assertTestCase.assertTrue(researchLinePage.physicalRiskHazardsScoreWidget.isDisplayed(),
                "Highest Risk Hazard: Water Stress is displayed");
        System.out.println(researchLinePage.facilitiesExposedWidget.getText());
        assertTestCase.assertTrue(researchLinePage.facilitiesExposedWidget.isDisplayed(),
                "Facilities Exposed Widget is displayed");
        //Highest Risk Hazard Widget design
        //Color of the box - this should be consistent with the color legend we have for risk scores. If it falls in High Risk, then background should show the dark brown color
        // while if it falls in Red Flag risk, the background should be Red.
        String actBackgroundColor = Color.fromString(researchLinePage.physicalRiskHazardsScoreWidget.getCssValue("background-color")).asHex().toUpperCase();

        ResearchLineColors riskColor = new ResearchLineColors();
        System.out.println("getScore(researchLinePage.facilitiesExposedWidget.getText()) = " + getScore(researchLinePage.facilitiesExposedWidget.getText()));
        assertTestCase.assertEquals(riskColor.getColorForScoreCategory(getScore(researchLinePage.facilitiesExposedWidget.getText())), actBackgroundColor,
                        "Highest Risk Hazard Widget background color is verified");
        actBackgroundColor = Color.fromString(researchLinePage.physicalRiskHazardsScoreWidgetText.getCssValue("color")).asHex();
        assertTestCase.assertEquals(actBackgroundColor,"#ffffff", "Highest Risk Hazard Widget text background color is verified");
        actBackgroundColor = Color.fromString(researchLinePage.facilitiesExposedWidget.getCssValue("color")).asHex();
        assertTestCase.assertEquals(actBackgroundColor,"#333333", "Facilities Exposed Widget background color is verified");

        assertTestCase.assertTrue(researchLinePage.checkIfPortfolioCoverageChartTitlesAreDisplayed(), "Portfolio Coverage chart titles are displayed");
        assertTestCase.assertTrue(researchLinePage.portfolioCoverageTable.getText().matches("Coverage\\n\\d+\\/\\d+\\nCompanies\\n.\\n\\d+%\\nInvestment"),
                "Portfolio Coverage formatted is verified");
        assertTestCase.assertTrue(researchLinePage.impactTableMainTitle.isDisplayed(), "Legend section is displayed");

        String description = "We evaluate your portfolio’s percentage of corporate facilities exposed to high risk/red flag for the physical climate hazard that has the highest average score in your portfolio. We leverage the company’s Operations Risk to calculate your highest physical climate hazard exposure. We score companies for Operations Risk by aggregating site-level climate hazard exposure across all of their known facilities, which can range from manufacturing sites and warehouses to offices and retail stores. We assess exposure to six climate hazards, including floods, heat stress, hurricanes & typhoons, sea level rise, water stress, and wildfires.\n" +
                "\n" +
                "Portfolio securities that are not covered within the dataset are excluded from the weighted average calculations.";
        assertTestCase.assertTrue(researchLinePage.impactTableDescription.isDisplayed(), "Description is is displayed");
        System.out.println("researchLinePage = " + researchLinePage);
        assertTestCase.assertEquals(researchLinePage.impactTableDescription.getText(),description, "Description is verified");

    }

    public String getScore(String category) {
        int level = Integer.parseInt(category.replaceAll("\\D+",""));
        System.out.println("level = " + level);
        if(level <20) return "NO RISK";
        else if(level <40) return "LOW RISK";
        else if(level <60) return "MEDIUM RISK";
        else if(level <80) return "HIGH RISK";
        else return "RED FLAG RISK";
    }

    @Test(groups = {"regression", "ui"})
    @Xray(test = {8063, 8064})
    public void verifyDrawersAreDisplayed() {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        researchLinePage.selectResearchLineFromDropdown("Physical Risk Hazards");

        BrowserUtils.wait(10);

        assertTestCase.assertTrue(researchLinePage.verifyPhysicalRiskHazardsDrawers("Market Risk", "Country of Sales"), "Market Risk:Country of Sales - Verify drawers");
        assertTestCase.assertTrue(researchLinePage.verifyPhysicalRiskHazardsDrawers("Market Risk", "Weather Sensitivity"), "Market Risk:Weather Sensitivity - Verify drawers");
        assertTestCase.assertTrue(researchLinePage.verifyPhysicalRiskHazardsDrawers("Supply Chain Risk", "Country of Origin"), "Supply Chain Risk:Country of Origin - Verify drawers");
        assertTestCase.assertTrue(researchLinePage.verifyPhysicalRiskHazardsDrawers("Supply Chain Risk", "Resource Demand"), "Supply Chain Risk:Resource Demand - Verify drawers");

    }

    @Test(groups = {"regression", "ui"})
    @Xray(test = {7103})
    public void verifyScoreRangeColors() {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        researchLinePage.selectResearchLineFromDropdown("Physical Risk Hazards");

        BrowserUtils.wait(5);

        researchLinePage.verifyColorLegendOfScoreCategory();
    }

    @Test(groups = {"regression", "ui"})
    @Xray(test = {8155, 8157})
    public void verifyInvestmentSectioninSummarySection() {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        researchLinePage.selectResearchLineFromDropdown("Physical Risk Hazards");

        BrowserUtils.wait(10);

        assertTestCase.assertTrue(researchLinePage.isInvestmentSectionAvailable());
        researchLinePage.validateInvestmentSectionRiskLevels();
    }

}
