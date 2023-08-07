package com.esgc.PortfolioAnalysis.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Utilities.Xray;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.esgc.Base.TestBases.Descriptions.*;
import static com.esgc.Utilities.Groups.*;

public class ResearchLinesDescriptionsTest extends UITestBase {


    /*
    US JIRA # - ESGCA-490
    Acceptance Criteria:
    when a user navigates to physical risk management page
    on the description in the overview section of the page should match the attached document.
 */

    @Test(groups = {REGRESSION, UI, SMOKE, ESG}, dataProvider = "Descriptions")
    @Xray(test = {4425,5058,4686})
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
                {"Physical Risk Management", PHYSICAL_RISK_MANAGEMENT_DESCRIPTION, 5082},
                //{"Operations Risk", OPERATIONS_RISK_DESCRIPTION, 5073},
                //{"Market Risk", MARKET_RISK_DESCRIPTION, 725},
                //{"Supply Chain Risk", SUPPLY_CHAIN_RISK_DESCRIPTION, 727},
                {"Physical Risk Hazards", PHYSICAL_RISK_HAZARDS_DESCRIPTION, 5073},
                {"Temperature Alignment", TEMPERATURE_ALIGNMENTS_DESCRIPTION, 5058},
                {"Carbon Footprint", CARBON_FOOTPRINT_DESCRIPTION, 4853, 4613},
                {"Brown Share Assessment", BROWN_SHARE_ASSESSMENT_DESCRIPTION, 5034},
                // {"Energy Transition Management",ENERGY_TRANSITION_MANAGEMENT_DESCRIPTION},
                // {"TCFD Strategy",TCFD_CLIMATE_STRATEGY_DESCRIPTION},
                {"Green Share Assessment", GREEN_SHARE_ASSESSMENT_DESCRIPTION, 5078},

        };
    }

    @Test(groups = {REGRESSION, UI, ESG})
    @Xray(test = {4401, 4377, 4407, 4702, 4401, 4292, 4917, 4896, 4788, 3916})
    public void validateDescriptionsForAdditionalPhysicalRiskHazards() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");

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



}