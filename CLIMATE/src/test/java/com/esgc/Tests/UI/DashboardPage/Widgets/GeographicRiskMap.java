package com.esgc.Tests.UI.DashboardPage.Widgets;

import com.esgc.Pages.DashboardPage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Tests.TestBases.DashboardUITestBase;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.List;

public class GeographicRiskMap extends DashboardUITestBase {
   //Todo there is random fails due to clicking on the country."Holdings Title Verification"
    @Test(groups = {"ui", "dashboard", "smoke", "regression"},
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {1932, 4548, 7565, })
    public void verifyGeographicRiskMapIsDisplayed(String researchLine) {
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");

        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();

        test.info("Check if Geographic Risk Map displayed");

            List<String> getAvailableResearchLinesFromMap =
                    dashboardPage.getAvailableResearchLinesFromGeographicRiskDistribution();
            List<String> expectedGeoMapResearchLines = dashboardPage.getExpectedListOfGeoMapResearchLines(EntitlementsBundles.ALL);
            assertTestCase.assertEquals(getAvailableResearchLinesFromMap,
                    expectedGeoMapResearchLines, "Validating Geographic Map Research Lines", 4533, 4548);

        dashboardPage.selectResearchLineFromGeographicRiskMapDropDown(researchLine);
        test.info("Check if Country List displayed");
        dashboardPage.clickRandomCountryOnGeographicRiskMap();//4613

        assertTestCase.assertTrue(dashboardPage.isCountryDistributionListDisplayed(),"Country List is Displayed", 4602, 4609);//4602, 4609

        dashboardPage.clickRandomCountryInCountryDistributionList();
        test.info("Check if Entity List displayed");
        assertTestCase.assertTrue(dashboardPage.isHoldingsTitleDisplayedOnEntityList(), "Holdings Title Verification", 4612);

        assertTestCase.assertTrue(dashboardPage.isGeographicRiskMapDisplayed(),"Geographic Risk Map is Displayed");


    }

    @Test(groups = {"ui", "dashboard", "smoke", "regression"},
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {4559, 6309, 6310, 6311})
    public void verifyGeographicRiskMapRedirectLink(String researchLine) {
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Dashboard");
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
