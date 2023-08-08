package com.esgc.Dashboard.UI.Tests.Widgets;

import com.esgc.Base.TestBases.DashboardUITestBase;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.List;

import static com.esgc.Utilities.Groups.*;

public class GeographicRiskMap extends DashboardUITestBase {
    //TODO Geomap is de-scoped until get an update from business
   //Todo there is random fails due to clicking on the country."Holdings Title Verification"
    @Test(enabled = false, groups = {UI, DASHBOARD, SMOKE, REGRESSION},
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {2030, 1863, 7565, 3675})
    public void verifyGeographicRiskMapIsDisplayed(String researchLine) {
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Climate Dashboard");
        test.info("Navigated to Dashboard Page");

        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();

        test.info("Check if Geographic Risk Map displayed");

            List<String> getAvailableResearchLinesFromMap =
                    dashboardPage.getAvailableResearchLinesFromGeographicRiskDistribution();
            List<String> expectedGeoMapResearchLines = dashboardPage.getExpectedListOfGeoMapResearchLines(EntitlementsBundles.ALL);
            assertTestCase.assertEquals(getAvailableResearchLinesFromMap,
                    expectedGeoMapResearchLines, "Validating Geographic Map Research Lines", 1694, 1863);

        dashboardPage.selectResearchLineFromGeographicRiskMapDropDown(researchLine);
        test.info("Check if Country List displayed");
        dashboardPage.clickRandomCountryOnGeographicRiskMap();//3675

        assertTestCase.assertTrue(dashboardPage.isCountryDistributionListDisplayed(),"Country List is Displayed", 3450, 1288);//3450, 1288

        dashboardPage.clickRandomCountryInCountryDistributionList();
        test.info("Check if Entity List displayed");
        assertTestCase.assertTrue(dashboardPage.isHoldingsTitleDisplayedOnEntityList(), "Holdings Title Verification", 1357);

        assertTestCase.assertTrue(dashboardPage.isGeographicRiskMapDisplayed(),"Geographic Risk Map is Displayed");


    }
    //TODO Geomap is de-scoped until get an update from business
    @Test(enabled = false, groups = {UI, DASHBOARD, SMOKE, REGRESSION},
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {1836, 4356, 4725, 4591})
    public void verifyGeographicRiskMapRedirectLink(String researchLine) {
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Climate Dashboard");
        test.info("Navigated to Dashboard Page");

        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();

        test.info("Check if Geographic Risk Map displayed");



        dashboardPage.selectResearchLineFromGeographicRiskMapDropDown(researchLine);
        dashboardPage.linkGoToPortfolioAnalysis.click();

        System.out.println("Link "+Driver.getDriver().getCurrentUrl().substring(Driver.getDriver().getCurrentUrl().lastIndexOf("/")+1));
        System.out.println("Link 2 "+ dashboardPage.getExpectedResearchlineName(researchLine));
        assertTestCase.assertTrue(Driver.getDriver().getCurrentUrl().substring(Driver.getDriver().getCurrentUrl().lastIndexOf("/")+1).
                equals(dashboardPage.getExpectedResearchlineName(researchLine)));
    }
}
