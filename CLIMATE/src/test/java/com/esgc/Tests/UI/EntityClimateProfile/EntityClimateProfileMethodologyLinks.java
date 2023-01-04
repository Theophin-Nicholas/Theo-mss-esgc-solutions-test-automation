package com.esgc.Tests.UI.EntityClimateProfile;

import com.esgc.Pages.EntityClimateProfilePage;
import com.esgc.Pages.LoginPage;
import com.esgc.Pages.ResearchLinePage;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class EntityClimateProfileMethodologyLinks extends UITestBase {

    @Test(groups = {"entity_climate_profile", "regression", "ui", "smoke", "entitlements"})
    @Xray(test = {6159})
    public void validatePhysicalRiskMethodologyLink() {
        //Trying to log in with only Physical Risk Entitilment User
     /*   Driver.getDriver().manage().deleteAllCookies();
        Driver.getDriver().navigate().refresh();*/
        LoginPage login = new LoginPage();
        //login.clickOnLogout();
        ResearchLinePage researchLinePage = new ResearchLinePage();
        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK);

        test.info("Navigating to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        BrowserUtils.wait(5);

        String company = "Apple, Inc.";
        test.info("Searching and Selecting the company");
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName), companyName + " Header Verification");
        test.info("Searched and selected the company: " + companyName);

        test.info("Verifying Physical Risk Items");
        assertTestCase.assertTrue(entityProfilePage.verifyPhysicalRiskItems(), "Verification of Market Risk, Supply Chain Risk, Operations Risk labels");

        test.info("Verifying Physical Risk - Methodology Link");
        assertTestCase.assertTrue(entityProfilePage.clickAndVerifyMethodologyLink("Physical Risk"), "Verification of Physical Risk - Methodology Link");

    }

    @Test(groups = {"entity_climate_profile", "regression", "ui", "smoke", "entitlements"})
    @Xray(test = {6326, 7872})
    public void validateTransitionRiskMethodologyLink() {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        LoginPage login = new LoginPage();

        login.entitlementsLogin(EntitlementsBundles.TRANSITION_RISK);
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        BrowserUtils.wait(5);
        test.info("Navigated to Portfolio Analysis page");

        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage("Apple, Inc.");
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName), companyName + " Header Verification");
        test.info("Searched and selected the company: " + companyName);

        test.info("Verifying Transition Risk Widgets");
        assertTestCase.assertTrue(entityProfilePage.verifyTransitionRiskItems(), "Transition Risk:Verification of Temperature Alignment, Carbon Footprint, Green Share and Brown Share Cards");

        test.info("Verifying Transition Risk - Methodology Links");
        assertTestCase.assertTrue(entityProfilePage.clickAndVerifyMethodologyLink("Temperature Alignment"), "Verification of Temperature Alignment - Methodology Link");
        assertTestCase.assertTrue(entityProfilePage.clickAndVerifyMethodologyLink("Carbon Footprint"), "Verification of Carbon Footprint - Methodology Link");
        assertTestCase.assertTrue(entityProfilePage.clickAndVerifyMethodologyLink("Green Share Assessment"), "Verification of Green Share Assessment - Methodology Link");
        assertTestCase.assertTrue(entityProfilePage.clickAndVerifyMethodologyLink("Brown Share Assessment"), "Verification of Brown Share Assessment - Methodology Link");

    }
}
