package com.esgc.Tests.UI.PortfolioAnalysisPage;

import com.esgc.Pages.ResearchLinePage;
import com.esgc.Tests.TestBases.DataValidationTestBase;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class EsgAssessmentUITests extends UITestBase {

     @Test(groups = {"regression", "ui", "smoke", "esg"})
    @Xray(test = {8704})
    public void verifyESGGradeDistributionIsDisplayed() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine("ESG Assessments");
        test.info("Navigated to ESG Assessments Page");
        researchLinePage.validateEsgGradeDistribution();
    }

    @Test(groups = {"regression", "ui", "smoke", "esg"})
    @Xray(test = {9133})
    public void verifyESGGRegionMapAndCountryTableDrawer() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine("ESG Assessments");
        test.info("Navigated to ESG Assessments Page");
        researchLinePage.validateCountry();
    }
}
