package com.esgc.PortfolioAnalysis.UI.Tests.ResearchLineWidgets;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.Xray;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

/**
 * Created by Tarun
 */
public class PortfolioDistribution extends UITestBase {

    @Test(groups = {REGRESSION, UI, SMOKE},
            description = "Verify if Portfolio Distribution is available",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {1924, 4936, 4594, 3575})
    public void verifyIfPortfolioDistributionIsAvailable(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine(page);
        if (page.equals("Physical Risk Hazards")) {
            throw new SkipException(page +" - Portfolio Distribution is not ready to test");
        }
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        test.info("Navigated to " + page + " Page");
        assertTestCase.assertTrue(researchLinePage.validatePortfolioDistribution(page), "Portfolio Distribution UI verified", 4921);
        test.pass("Portfolio Distribution table is present on " + page + " Page");
    }

}


