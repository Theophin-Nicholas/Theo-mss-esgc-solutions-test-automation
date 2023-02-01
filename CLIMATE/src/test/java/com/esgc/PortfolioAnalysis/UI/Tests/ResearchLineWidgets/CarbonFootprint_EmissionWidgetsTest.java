package com.esgc.PortfolioAnalysis.UI.Tests.ResearchLineWidgets;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

/**
 * Created by ChaudhS2 on 12/9/2021..
 */
public class CarbonFootprint_EmissionWidgetsTest extends UITestBase {

    @Test(groups = {REGRESSION, SMOKE, UI},
            description = "ESGCA-3011 - Verify Emissions Section is Displayed in Carbon Footprint")
    @Xray(test = {3011, 3079})
    public void verifyEmissionsWidgetsAreDisplayedOnCarbonFootPrintPage() {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        BrowserUtils.wait(5);
        researchLinePage.navigateToResearchLine("Carbon Footprint");
        Assert.assertTrue(researchLinePage.checkIfPageTitleIsDisplayed("Carbon Footprint"));
        test.pass("Navigated to Carbon Footprint");
        researchLinePage.navigateToParticluarWidget("Emissions");
        Assert.assertTrue(researchLinePage.IsEmissionWidgetIsPresent(), "Emissions Widget was not present on Carbon FootPrint");
        Assert.assertTrue(researchLinePage.IsTotalFinancialEmissionSubsectionPresentUnderEmission(), "Total Financed Emissions title is not present under Emission.");
        Assert.assertTrue(researchLinePage.IsTotalFinancedEmissionPerMillionSubsectionPresentUnderEmission(), "Financed Emissions per Millions Invested title is not present under Emission.");
        Assert.assertTrue(researchLinePage.IsCarbon_Intensity_per_SalesSubsectionPresentUnderEmission(), "Carbon Intensity per Sales title is not present under Emission.");

    }

    @Test(groups = {REGRESSION, UI})
    @Xray(test = {5506, 5507})
    public void verifyCarbonFootprintPageLabels_tCO2eq() {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        BrowserUtils.wait(5);
        researchLinePage.navigateToResearchLine("Carbon Footprint");
        Assert.assertTrue(researchLinePage.checkIfPageTitleIsDisplayed("Carbon Footprint"));
        test.pass("Navigated to Carbon Footprint");
        researchLinePage.waitForDataLoadCompletion();
        test.info("Check (tCO2eq) displayed in each widget");
        researchLinePage.clickOnBenchmarkDropdown()
                .SelectAPortfolioFromBenchmark("Sample Portfolio");
        researchLinePage.validateCarbonFootPrint();
    }

    @Test(groups = {REGRESSION, UI}, dataProvider = "Entity Names")
    @Xray(test = {6028})
    public void verifyCarbonFootprintWidget(String entity) {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.goToEntity(entity);
        assertTestCase.assertTrue(researchLinePage.checkIfUserIsOnEntityPageBySearchedKeyword(entity));
        assertTestCase.assertTrue(researchLinePage.validateCarbonFootPrintAndSubLabels(), "Carbon Footprint label and sub-labels are correct");
        //researchLinePage.verifyBackGroundColor();
    }

    @DataProvider(name = "Entity Names")
    public Object[][] dpMethod() {

        return new Object[][]{
                {"Amazon.com, Inc."},
                {"Goodman Group"}
        };
    }
}
