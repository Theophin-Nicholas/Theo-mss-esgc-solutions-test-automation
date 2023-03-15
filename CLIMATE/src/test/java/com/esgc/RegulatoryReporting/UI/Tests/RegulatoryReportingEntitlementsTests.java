package com.esgc.RegulatoryReporting.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.RegulatoryReporting.UI.Pages.RegulatoryReportingPage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Environment;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class RegulatoryReportingEntitlementsTests extends UITestBase {
    @Test(groups = {REGRESSION, UI, REGULATORY_REPORTING, ENTITLEMENTS})
    @Xray(test = {10858, 11563})
    public void verifyEntitlementForNonSFDRusers() {
        LoginPage login = new LoginPage();
        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK);

        RegulatoryReportingPage regulatoryReportingPage = new RegulatoryReportingPage();
        assertTestCase.assertFalse(regulatoryReportingPage.ValidateMenuItemIsAvailable("Regulatory Reporting"), "Validating that Regulatory Reporting option is not visible to unentitled person ");

    }

    @Test(groups = {REGRESSION, UI, REGULATORY_REPORTING, ENTITLEMENTS})
    @Xray(test = {11563})
    public void verifyEntitlementForNonEUTaxonomyUsers() {
        LoginPage login = new LoginPage();
        RegulatoryReportingPage regulatoryReportingPage = new RegulatoryReportingPage();
        if (Environment.environment.equalsIgnoreCase("prod")) {
            login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK);
            assertTestCase.assertFalse(regulatoryReportingPage.ValidateMenuItemIsAvailable("Regulatory Reporting"), "Validating that Regulatory Reporting option is not visible to unentitled person ");
        } else {
            login.loginWithParams("qa-mesg+a15@outlook.com", "Moodys123");
            assertTestCase.assertTrue(regulatoryReportingPage.isEUTaxonomyOptionNotClickable(), "Validating that EU Taxonomy option is not clickable to unentitled person ");
        }
    }

    @Test(groups = {REGRESSION, UI, REGULATORY_REPORTING}, description = "Verify user can't see reporting page if is not entitled to SFDR")
//, "smoke"
    @Xray(test = {10867})
    public void verifyReportingPageWithoutSFDRUserTest() {
        RegulatoryReportingPage regulatoryReportingPage = new RegulatoryReportingPage();
        regulatoryReportingPage.clickMenu();
        assertTestCase.assertTrue(regulatoryReportingPage.isRegulatoryReportingOptionIsAvailableInSidePanel(), "Reporting page option is displayed");
        LoginPage loginPage = new LoginPage();
        loginPage.clickOnLogout();
        loginPage.loginWithParams(Environment.PHYSICAL_RISK_USERNAME, Environment.PHYSICAL_RISK_PASSWORD);
        regulatoryReportingPage.clickMenu();
        assertTestCase.assertFalse(BrowserUtils.getElementsText(regulatoryReportingPage.menuList).contains("Regulatory Reporting"), "Reporting page is not displayed as expected");
        loginPage.clickOnLogout();
    }

}
