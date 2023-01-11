package com.esgc.PortfolioAnalysis.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class MethodologyLinksTest extends UITestBase {

    @Test(groups = {REGRESSION, UI, SMOKE},
            description = "Verify Methodology link works as Expected",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {1929, 1939, 1940, 1943, 2296, 1944, 1945, 5504, 7867, 7995})
    public void verifyMethodologyLink(String page){
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine(page);
        test.info("Navigated to " + page);

        boolean result = researchLinePage.clickAndVerifyMethodologyLink(page);
        assertTestCase.assertTrue(result, "Methodology link verification");
    }

    @Test(enabled = false, groups = {REGRESSION, UI, SMOKE, ESG},
            description = "Verify  ESG Summary Section links")
    @Xray(test = {8284}) //TODO de-scopped as of now. Enable it when scopped
    public void verifyPortfolioCoverage() {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToResearchLine("ESG Assessments");
        test.info("Navigated to ESG Assessments Page");
        researchLinePage.ValidateSummaryBoxTextLinks();

    }
}
