package com.esgc.PortfolioAnalysis.UI.Tests.ResearchLineWidgets;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.esgc.Utilities.Groups.*;

public class ResearchLineNavigation extends UITestBase {
    @Xray(test = {4888, 4687, 4505, 1996, 4495})
    @Test(groups = {REGRESSION, UI, SMOKE},
            description = "Verify research line navigation")
    public void verifyResearchLineNavigation() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        BrowserUtils.wait(5);

        List<String> actualResearchLines = researchLinePage.getAvailableResearchLines();
        assertTestCase.assertEquals(actualResearchLines.size(), 6, "Existing Research Lines Validation ");
        assertTestCase.assertEquals(actualResearchLines.get(0), "Physical Risk Hazards", "Default Research Line Validation");
        List<String> expectedResearchLines = Arrays.asList("Physical Risk Hazards", "Physical Risk Management",
                "Temperature Alignment", "Carbon Footprint", "Green Share Assessment", "Brown Share Assessment");
        assertTestCase.assertEquals(actualResearchLines, expectedResearchLines, "Research Line dropdown list verification");

    }
}
