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
    @Xray(test = {4617, 4869, 5033, 1347, 4476, 4622, 4813, 4485})
    public void verifyMethodologyLink(String page){
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine(page);
        test.info("Navigated to " + page);

        boolean result = researchLinePage.clickAndVerifyMethodologyLink(page);
        assertTestCase.assertTrue(result, "Methodology link verification");
    }


}
