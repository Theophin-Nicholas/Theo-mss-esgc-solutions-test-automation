package com.esgc.Tests.UI.PortfolioAnalysisPage;

import com.esgc.Pages.ResearchLinePage;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class EsgAssessmentUITests extends UITestBase {

     @Test(groups = {"regression", "ui", "smoke", "esg"})
    @Xray(test = {8704, 9969})
    public void verifyESGGradeDistributionIsDisplayed() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine("ESG Assessments");
        test.info("Navigated to ESG Assessments Page");
        researchLinePage.validateEsgGradeDistribution();
    }

    @Test(enabled = false,groups = {"regression", "ui", "smoke", "esg"})
    @Xray(test = {9133})
    public void verifyESGGRegionMapAndCountryTableDrawer() {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine("ESG Assessments");
        test.info("Navigated to ESG Assessments Page");
        researchLinePage.validateCountry();
    }

    @Test(groups = {"regression", "ui", "esg"})
    @Xray(test = {9967, 9898})
    public void verifyEsgAssessmentScoreLegend() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine("ESG Assessments");
        test.info("Navigated to ESG Assessments Page");
        researchLinePage.validateEsgAssessmentLegends();
    }

    @Test(groups = {"regression", "ui", "esg"})
    @Xray(test = {8291})
    public void verifyEsgAssessmentWithBenchmark() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine("ESG Assessments");

        researchLinePage.clickOnBenchmarkDropdown();
        researchLinePage.waitForDataLoadCompletion();
        researchLinePage.SelectAPortfolioFromBenchmark("Sample Portfolio");


    }
}
