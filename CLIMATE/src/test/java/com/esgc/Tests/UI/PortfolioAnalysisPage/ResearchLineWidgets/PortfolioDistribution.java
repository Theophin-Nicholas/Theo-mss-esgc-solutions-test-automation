package com.esgc.Tests.UI.PortfolioAnalysisPage.ResearchLineWidgets;

import com.esgc.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.SkipException;
import org.testng.annotations.Test;


/**
 * Created by Tarun
 */
public class PortfolioDistribution extends UITestBase {

    @Test(groups = {"regression", "ui", "smoke"},
            description = "Verify if Portfolio Distribution is available",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {3924, 6730,657})
    public void verifyIfPortfolioDistributionIsAvailable(String page) {
        if (page.equals("Physical Risk Hazards") || page.equals("Physical Risk Management")) {
            throw new SkipException(page +" - Portfolio Distribution is not ready to test");
        }
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine(page);
        BrowserUtils.wait(5);


        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        test.info("Navigated to " + page + " Page");
        assertTestCase.assertTrue(researchLinePage.validatePortfolioDistribution(page), "Portfolio Distribution UI verified", 552);
        test.pass("Portfolio Distribution table is present on " + page + " Page");
    }

}


