package com.esgc.EntityProfile.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.EntityProfile.UI.Pages.EntityClimateProfilePage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class EntityClimateProfileMethodologyLinks extends UITestBase {

   /* @Test(groups = {ENTITY_PROFILE, REGRESSION, UI, SMOKE, ENTITLEMENTS})
    @Xray(test = {6159})
    public void validatePhysicalRiskMethodologyLink() {
        //Trying to log in with only Physical Risk Entitilment User
     *//*   Driver.getDriver().manage().deleteAllCookies();
        Driver.getDriver().navigate().refresh();*//*
        LoginPage login = new LoginPage();
//        login.clickOnLogout();
        ResearchLinePage researchLinePage = new ResearchLinePage();
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_EXPORT_ENTITLEMENT);

        test.info("Navigating to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        BrowserUtils.wait(5);

        String company = "Apple, Inc.";
        test.info("Searching and Selecting the company");
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName), companyName + " Header Verification");
        test.info("Searched and selected the company: " + companyName);

        test.info("Verifying Physical Risk Items");
        assertTestCase.assertTrue(entityProfilePage.verifyPhysicalRiskItems(), "Verification of Market Risk, Supply Chain Risk, Operations Risk labels");

        entityProfilePage.ReferenceAndMethodology_Button.click();
      //  test.info("Verifying Physical Risk - Methodology Link");
     //   assertTestCase.assertTrue(entityProfilePage.clickAndVerifyMethodologyLink("Physical Risk"), "Verification of Physical Risk - Methodology Link");

    }*/

    @Test(groups = {ENTITY_PROFILE, REGRESSION, UI, SMOKE, ENTITLEMENTS})
    @Xray(test = {6326, 7872, 6159})
    public void validateTransitionRiskMethodologyLink() {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        LoginPage login = new LoginPage();

        login.entitlementsLogin(EntitlementsBundles.USER_WITH_EXPORT_ENTITLEMENT);
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        BrowserUtils.wait(5);
        test.info("Navigated to Portfolio Analysis page");

        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage("Apple, Inc.");
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName), companyName + " Header Verification");
        test.info("Searched and selected the company: " + companyName);

        test.info("Verifying Transition Risk Widgets");
        assertTestCase.assertTrue(entityProfilePage.verifyTransitionRiskItems(), "Transition Risk:Verification of Temperature Alignment, Carbon Footprint, Green Share and Brown Share Cards");

        test.info("Verifying Physical Risk Items");
        assertTestCase.assertTrue(entityProfilePage.verifyPhysicalRiskItems(), "Verification of Market Risk, Supply Chain Risk, Operations Risk labels");

        entityProfilePage.ReferenceAndMethodology_Button.click();
        test.info("Verifying Methodology Links");
        assertTestCase.assertTrue(entityProfilePage.clickAndVerifyMethodologyLink("Controversy Risk Assessment Methodology"), "Verification of Controversy Risk Assessment - Methodology Link");
        assertTestCase.assertTrue(entityProfilePage.clickAndVerifyMethodologyLink("Physical Risk methodology"), "Verification of Physical Risk - Methodology Link");
        assertTestCase.assertTrue(entityProfilePage.clickAndVerifyMethodologyLink("Physical Risk Management methodology"), "Verification of Physical Risk Management - Methodology Link");
        assertTestCase.assertTrue(entityProfilePage.clickAndVerifyMethodologyLink("Temperature Alignment methodology"), "Verification of Temperature Alignment - Methodology Link");
        assertTestCase.assertTrue(entityProfilePage.clickAndVerifyMethodologyLink("Transition Risk methodology"), "Verification of Transition Risk - Methodology Link");
        BrowserUtils.ActionKeyPress(Keys.ESCAPE);
    }
}
