package com.esgc.EntityProfile.UI.Tests;

import com.esgc.EntityProfile.UI.Pages.EntityClimateProfilePage;
import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class EntityClimateProfileEntitlementsTests extends UITestBase {

    @Test(groups = {"entity_climate_profile", "regression", "ui", "smoke", "entitlements"})
    @Xray(test = {6110})
    public void validatePhysicalRiskEntitlements(){
        ResearchLinePage researchLinePage = new ResearchLinePage();
        LoginPage login = new LoginPage();

        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK);

        test.info("Navigating to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        BrowserUtils.wait(5);

        String company = "Apple, Inc.";
        test.info("Searching and Selecting the company");
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName),companyName+" Header Verification");
        test.info("Searched and selected the company: "+ companyName);

        test.info("Verifying Physical Climate Hazards Details");
        assertTestCase.assertTrue(entityProfilePage.isPhysicalClimateHazardCardDisplayed(), "Physical Climate Hazard Card Verification");

        test.info("Verifying Physical Risk Items");
        assertTestCase.assertTrue(entityProfilePage.verifyPhysicalRiskItems(),"Verification of Market Risk, Supply Chain Risk, Operations Risk labels");

    }

    @Test(groups = {"entity_climate_profile", "regression", "ui", "smoke", "entitlements"})
    @Xray(test = {6110})
    public void validateTransitionRiskEntitlements(){
        ResearchLinePage researchLinePage = new ResearchLinePage();

        LoginPage login = new LoginPage();

        login.entitlementsLogin(EntitlementsBundles.TRANSITION_RISK);
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        BrowserUtils.wait(5);
        test.info("Navigated to Portfolio Analysis page");

        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage("Apple, Inc.");
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName),companyName+" Header Verification");
        test.info("Searched and selected the company: "+ companyName);

        test.info("Verifying Common Widgets");
        assertTestCase.assertTrue(entityProfilePage.checkIfTempratureAlignmentWidgetISAvailable(), "Temperature Alignment Widget Verification");
        assertTestCase.assertTrue(entityProfilePage.checkIfCarbonFootprintWidgetISAvailable(), "Carbon Footprint Widget Verification");
        assertTestCase.assertTrue(entityProfilePage.checkIfGreenShareCardISAvailable(), "Green Share Card Verification");
        assertTestCase.assertTrue(entityProfilePage.checkIfBrownShareCardISAvailable(), "Brown Share Card Verification");

        test.info("Verifying Transition Risk Widgets");
        assertTestCase.assertTrue(entityProfilePage.verifyTransitionRiskItems(), "Transition Risk:Verification of Temperature Alignment, Carbon Footprint, Green Share and Brown Share Cards");

    }

    @Test(groups = {"entity_climate_profile", "regression", "ui", "smoke", "entitlements"})
    @Xray(test = {6110})
    public void validatePhysicalAndTransitionRiskEntitlements(){
        ResearchLinePage researchLinePage = new ResearchLinePage();

        LoginPage login = new LoginPage();

        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK_TRANSITION_RISK);
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        BrowserUtils.wait(5);
        test.info("Navigated to Portfolio Analysis page");

        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage("Apple, Inc.");
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName),companyName+" Header Verification");
        test.info("Searched and selected the company: "+ companyName);

        test.info("Verifying Physical Climate Hazards Details");
        assertTestCase.assertTrue(entityProfilePage.isPhysicalClimateHazardCardDisplayed(), "Physical Climate Hazard Card Verification");

        test.info("Verifying Physical Risk Items");
        assertTestCase.assertTrue(entityProfilePage.verifyPhysicalRiskItems(),"Verification of Market Risk, Supply Chain Risk, Operations Risk labels");

        test.info("Verifying Common Widgets");
        assertTestCase.assertTrue(entityProfilePage.checkIfTempratureAlignmentWidgetISAvailable(), "Temperature Alignment Widget Verification");
        assertTestCase.assertTrue(entityProfilePage.checkIfCarbonFootprintWidgetISAvailable(), "Carbon Footprint Widget Verification");
        assertTestCase.assertTrue(entityProfilePage.checkIfGreenShareCardISAvailable(), "Green Share Card Verification");
        assertTestCase.assertTrue(entityProfilePage.checkIfBrownShareCardISAvailable(), "Brown Share Card Verification");

        test.info("Verifying Transition Risk Widgets");
        assertTestCase.assertTrue(entityProfilePage.verifyTransitionRiskItems(), "Transition Risk:Verification of Temperature Alignment, Carbon Footprint, Green Share and Brown Share Cards");

    }

    @Test(enabled = false,groups = {"entity_climate_profile", "regression", "ui", "smoke", "entitlements"})
    @Xray(test = {8982})//TODO #access only Climate Governance bundle cg_bundle_username=esg-test33@outlook.com not working
                        // Portfolio Analysis link is not available in UI
    public void validatePhysicalRiskManagementIsUnavailable() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        LoginPage login = new LoginPage();
        login.entitlementsLogin(EntitlementsBundles.CLIMATE_GOVERNANCE);
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        BrowserUtils.wait(5);
        test.info("Navigated to Portfolio Analysis page");

        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        entityProfilePage.searchAndLoadClimateProfilePage("Apple, Inc.");

        assertTestCase.assertTrue(!entityProfilePage.validatePhysicalRiskMananagementTableIsAvailable(),"Validate physical risk management table should not be available");


    }

    @Test(groups = {"entity_climate_profile", "regression", "ui", "entitlements"})
    @Xray(test = {8890})
    public void validateMethodologiesTabWithControversiesEntitlement() {

        // User with Controversies Entitlement and No Esg Entitlement
        LoginPage login = new LoginPage();
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_CONTROVERSIES_ENTITLEMENT);

        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        entityProfilePage.searchAndLoadClimateProfilePage("Apple, Inc.");

        assertTestCase.assertTrue(entityProfilePage.verifyMethodologiesTabIsDisplayed(),"Validate methodologies tab is available");
    }

    @Test(groups = {"entity_climate_profile", "regression", "ui", "entitlements"})
    @Xray(test = {8890})
    public void validateMethodologiesTabWithEsgEntitlement() {

        // User with ESG Entitlement and No Controversies Entitlement
        LoginPage login = new LoginPage();
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_OUT_CONTROVERSIES_ENTITLEMENT);

        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        entityProfilePage.searchAndLoadClimateProfilePage("Apple, Inc.");

        assertTestCase.assertTrue(entityProfilePage.verifyMethodologiesTabIsDisplayed(),"Validate methodologies tab is available");
    }

    @Test(groups = {"entity_climate_profile", "regression", "ui", "entitlements"})
    @Xray(test = {8890})
    public void validateMethodologiesTabWithOutControversiesAndEsgEntitlements() {

        // User without ESG & Controversies Entitlements
        LoginPage login = new LoginPage();
        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK);

        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        entityProfilePage.searchAndLoadClimateProfilePage("Apple, Inc.");

        assertTestCase.assertTrue(!entityProfilePage.verifyMethodologiesTabIsDisplayed(),"Validate methodologies tab is available");
    }

}
