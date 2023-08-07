package com.esgc.PortfolioAnalysis.UI.Tests.ResearchLineWidgets;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class RegionMapTests extends UITestBase {
//This is de-scoped
    @Test(enabled = false,groups = {REGRESSION, UI, SMOKE},
            description = "Verify if World Map is Displayed as Expected",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {1010,  2385, 2448, 2912, 2903, 1831})
    public void verifyWorldMapWidgetAndToolTip(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToResearchLine(page);
        BrowserUtils.wait(5);

        if (page.equals("Temperature Alignment")) {
            throw new SkipException("Region Map is not ready to test in " + page);
        }

        test.info("Navigated to " + page + " Page");

        assertTestCase.assertTrue(researchLinePage.checkIfRegionMapIsDisplayed(), "Region map displayed");

        researchLinePage.hoverOverRandomCountryOnMapAndCheckIfTooltipDisplayed();

        assertTestCase.assertTrue(researchLinePage.checkIfRegionMapLabelIsCorrect(), "Validating map label", 4832);
        test.pass("World Map label is displayed correctly");
    }


}
