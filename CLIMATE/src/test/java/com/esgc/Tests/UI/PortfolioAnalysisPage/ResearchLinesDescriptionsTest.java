package com.esgc.Tests.UI.PortfolioAnalysisPage;

import com.esgc.Pages.ResearchLinePage;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.Xray;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.esgc.Tests.TestBases.Descriptions.*;

public class ResearchLinesDescriptionsTest extends UITestBase {


    /*
    US JIRA # - ESGCA-490
    Acceptance Criteria:
    when a user navigates to physical risk management page
    on the description in the overview section of the page should match the attached document.
 */

    @Test(groups = {"regression", "ui", "smoke", "esg"}, dataProvider = "Descriptions")
    @Xray(test = {3918,6934})
    public void verifyDescriptions(String page, String description, Integer... testCases) {

        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToResearchLine(page);
        assertTestCase.assertTrue(researchLinePage.checkIfPageTitleIsDisplayed(page), "Page Title Displayed", testCases);
        test.pass("Navigated to " + page);

        test.info("Description validation for " + page);
        assertTestCase.assertTrue(researchLinePage.checkIfResearchLineTitleIsDisplayed(page), researchLinePage + " title displayed", testCases);
        assertTestCase.assertEquals(researchLinePage.checkIfDescriptionDisplayed(description), description, "description displayed", testCases);

        test.pass("Verified:" + page + " description is displayed successfully");
        test.pass("Verified:" + page + " description content is correct");

    }

    @DataProvider(name = "Descriptions")
    public Object[][] dpMethod() {

        return new Object[][]{
                //{"Physical Risk Management", PHYSICAL_RISK_MANAGEMENT_DESCRIPTION, 717},
                //{"Operations Risk", OPERATIONS_RISK_DESCRIPTION, 724},
                //{"Market Risk", MARKET_RISK_DESCRIPTION, 725},
                //{"Supply Chain Risk", SUPPLY_CHAIN_RISK_DESCRIPTION, 727},
                {"Physical Risk Hazards", PHYSICAL_RISK_HAZARDS_DESCRIPTION, 724},
                {"Temperature Alignment", TEMPERATURE_ALIGNMENTS_DESCRIPTION, 6934},
                {"Carbon Footprint", CARBON_FOOTPRINT_DESCRIPTION, 549, 726},
                {"Brown Share Assessment", BROWN_SHARE_ASSESSMENT_DESCRIPTION, 2266},
                // {"Energy Transition Management",ENERGY_TRANSITION_MANAGEMENT_DESCRIPTION},
                // {"TCFD Strategy",TCFD_CLIMATE_STRATEGY_DESCRIPTION},
                {"Green Share Assessment", GREEN_SHARE_ASSESSMENT_DESCRIPTION, 2262},

        };
    }

    @Test(groups = {"regression", "ui", "esg"})
    @Xray(test = {6517, 6522, 6519, 6516, 6517, 6477, 6520, 6521, 6523, 6561})
    public void validateDescriptionsForAdditionalPhysicalRiskHazards() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");

        test.info("Verify Market Risk -> Country of Sales Description");
        String countryOfSalesDescription = "Country of Sales measures climate risk in the countries in which a company generates its sales. Country of Sales is a percentile score between 0 (low risk) and 100 (high risk).";
        assertTestCase.assertTrue(researchLinePage.verifyPhysicalRiskHazardDescription("Market Risk", "Country of Sales", countryOfSalesDescription), "Market Risk -> Country Of Sales Description Verification");

        String weatherDescription = "Weather Sensitivity measures the sensitivity of economic output to climate variability based on the industries within which the company operates. Weather Sensitivity is a percentile score between 0 (low risk) and 100 (high risk).";
        assertTestCase.assertTrue(researchLinePage.verifyPhysicalRiskHazardDescription("Market Risk", "Weather Sensitivity", weatherDescription), "Market Risk -> Weather Sensitivity Description Verification");

        test.info("Verify Supply Chain Risk -> Country of Origin Description");
        String countryOfOriginDescription = "Country of Origin measures climate risk in countries that export commodities that a company depends on for the production and delivery of products and services. Country of Origin is a percentile score between 0 (low risk) and 100 (high risk).";
        assertTestCase.assertTrue(researchLinePage.verifyPhysicalRiskHazardDescription("Supply Chain Risk", "Country of Origin", countryOfOriginDescription), "Supply Chain Risk -> Country of Origin Description Verification");

        test.info("Verify Supply Chain Risk -> Resource Demand Description");
        String resourceDemandDescription = "Resource Demand measures a company’s dependence on resources affected by climate change. Resource Demand is a percentile score between 0 (low risk) and 100 (high risk).";
        assertTestCase.assertTrue(researchLinePage.verifyPhysicalRiskHazardDescription("Supply Chain Risk", "Resource Demand", resourceDemandDescription), "Supply Chain Risk -> Resource Demand Description Verification");

    }

    @Test(groups = {"regression", "ui", "smoke", "esg"},
            description = "Verify  ESG Summary Section")
    @Xray(test = {8283, 8285})
    public void verifyESGAssessmentBoxAndDescription() {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToResearchLine("ESG Assessments");
        test.info("Navigated to ESG Assessments Page");

        assertTestCase.assertTrue(researchLinePage.ValidateifEsgPortfolioBoxIsDisplayed(), "Portfolio Box is displayed");
        assertTestCase.assertTrue(researchLinePage.ValidateifEsgsummaryBoxIsDisplayed(), "ESG summary Box is displayed");
        researchLinePage.ValidateSummaryBoxText();
    }

}