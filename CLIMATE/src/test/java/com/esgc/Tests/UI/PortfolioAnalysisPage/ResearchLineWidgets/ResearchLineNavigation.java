package com.esgc.Tests.UI.PortfolioAnalysisPage.ResearchLineWidgets;

import com.esgc.Pages.ResearchLinePage;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class ResearchLineNavigation extends UITestBase {
    @Xray(test = {547, 3265, 3266, 3267, 3268})
    @Test(groups = {"regression", "ui", "smoke"},
            description = "Verify research line navigation")
    public void verifyResearchLineNavigation() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        BrowserUtils.wait(5);

        List<String> actualResearchLines = researchLinePage.getAvailableResearchLines();
        assertTestCase.assertEquals(actualResearchLines.size(), 7, "Existing Research Lines Validation ");
        assertTestCase.assertEquals(actualResearchLines.get(0), "Physical Risk Hazards", "Default Research Line Validation");
        List<String> expectedResearchLines = Arrays.asList("Physical Risk Hazards", "Physical Risk Management",
                "Temperature Alignment", "Carbon Footprint", "Green Share Assessment", "Brown Share Assessment","ESG Assessments");
        assertTestCase.assertEquals(actualResearchLines, expectedResearchLines, "Research Line dropdown list verification");

    }
}
