package com.esgc.RegulatoryReporting.UI.Tests;


import com.esgc.Common.API.Controllers.CommonAPIController;
import com.esgc.Common.UI.Pages.LoginPage;
import com.esgc.Common.UI.TestBases.UITestBase;
import com.esgc.ONDEMAND.UI.Pages.OnDemandAssessmentPage;
import com.esgc.RegulatoryReporting.UI.Pages.RegulatoryReportingPage;
import com.esgc.Utilities.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    @Test(groups = {REGRESSION, UI, REGULATORY_REPORTING, SMOKE, ENTITLEMENTS}, description = "Verify user can't see reporting page if is not entitled to SFDR")
//, "smoke"
    @Xray(test = {10867})
    public void verifyReportingPageWithoutSFDRUserTest() {
        LoginPage loginPage = new LoginPage();
        loginPage.login();
        RegulatoryReportingPage regulatoryReportingPage = new RegulatoryReportingPage();
        regulatoryReportingPage.clickMenu();
        assertTestCase.assertTrue(regulatoryReportingPage.isRegulatoryReportingOptionIsAvailableInSidePanel(), "Reporting page option is displayed");
        loginPage.clickOnLogout();
        loginPage.loginWithParams(Environment.PHYSICAL_RISK_USERNAME, Environment.PHYSICAL_RISK_PASSWORD);
        regulatoryReportingPage.clickMenu();
        assertTestCase.assertFalse(BrowserUtils.getElementsText(regulatoryReportingPage.menuList).contains("Regulatory Reporting"), "Reporting page is not displayed as expected");
        loginPage.clickOnLogout();
    }

    @Test(groups = {REGRESSION, UI, REGULATORY_REPORTING, ENTITLEMENTS, SMOKE}, dataProvider = "Regulatory Reporting Entitlements")
    @Xray(test = {11563, 10867, 11563, 10858, 11563})
    public void verifyProdEntitlementForRegulatoryReporting(String username, String password, String entitlements) {
        LoginPage login = new LoginPage();
        RegulatoryReportingPage regulatoryReportingPage = new RegulatoryReportingPage();
        if (Environment.environment.equalsIgnoreCase("prod")) {
            login.loginWithParams(username, password);
            if (entitlements.contains("SFDR") && entitlements.contains("EU")) {
                regulatoryReportingPage.clickOnEUTaxonomy();
                regulatoryReportingPage.clickOnSFDRPAIsOption();
            } else if (entitlements.contains("SFDR")) {
                assertTestCase.assertTrue(regulatoryReportingPage.isEUTaxonomyOptionNotClickable(), "Validating that EU Taxonomy option is not clickable to unentitled person ");
            } else if (entitlements.contains("EU")) {
                assertTestCase.assertTrue(regulatoryReportingPage.isSFDROptionNotClickable(), "Validating that SFDR option is not clickable to unentitled person ");
            }
        } else {
            //TODO
        }
    }

    @DataProvider(name = "Regulatory Reporting Entitlements")
    public Object[][] dpMethodForReports() {
        if (Environment.environment.equalsIgnoreCase("prod")) {
            return new Object[][]{
                    {"mesg360-testing+investor6@outlook.com", "Test12345@@", "SFDR"},//SFDR
                    {"mesg360-testing+investor24@outlook.com", "Test12345@@", "EU"},//EU Taxonomy
                    {"mesg360-testing+investor28@outlook.com", "Test12345@@", "SFDR + EU"},//SFDR + EU Taxonomy
            };
        } else {
            return new Object[][]{
                    {"mesg360-testing+investor6@outlook.com", "Test12345@@"},//SFDR
                    {"mesg360-testing+investor24@outlook.com", "Test12345@@"},//EU Taxonomy
                    {"mesg360-testing+investor28@outlook.com", "Test12345@@"},//SFDR + EU Taxonomy
            };
        }
    }

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS, REGULATORY_REPORTING})
    @Xray(test = {14364, 14365, 14366, 14473, 14474, 14475, 14476})
    public void validateLandingPageForUserWith_EUTaxonomy_SFDR_Entitlements() {

        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();

        //upload 100% SFDR coverage portfolio and verify
        String portfolioName = "SFDROnlyPortfolioDelete";
        CommonAPIController.deletePortfolioThroughAPI(portfolioName);
        String portfolioFilePath = ImportPortfolioUtility.getOnDemandPortfolioFileToUpload(Collections.singletonList("SFDR Only"), "", 10, portfolioName,false);
        reportingPage.uploadPortfolio(portfolioFilePath, "OnDemand");
        BrowserUtils.wait(10);
        Driver.getDriver().navigate().refresh();
        reportingPage.selectReportingOptionByName("SFDR");
        reportingPage.verifyPortfolio(portfolioName);
        reportingPage.verifySFDRPortfolioCoverageForUI(portfolioName);
        assertTestCase.assertTrue(reportingPage.verifyPortfolioEnabled(portfolioName), "Validating that the portfolio is enabled for SFDR");
        reportingPage.selectReportingOptionByName("EU Taxonomy");
        reportingPage.verifyEUTaxonomyPortfolioCoverageForUI(portfolioName);
        assertTestCase.assertFalse(reportingPage.verifyPortfolioEnabled(portfolioName), "Validating that the portfolio is disabled for EU Taxonomy");
        CommonAPIController.deletePortfolioThroughAPI(portfolioName);

        /**
         * We don't have data for EU Taxonomy Only portfolio So Code below is commented
         */


//        //upload 100% EU Taxonomy coverage portfolio and verify
//        portfolioName = "EUTaxonomyOnlyPortfolioDelete";
//        portfolioFilePath = ImportPortfolioUtility.getOnDemandPortfolioFileToUpload(Collections.singletonList("EU Taxonomy Only"), "", 10, portfolioName,false);
//        reportingPage.uploadPortfolio(portfolioFilePath, "OnDemand");
//        BrowserUtils.wait(10);
//        Driver.getDriver().navigate().refresh();
////        try {
//            reportingPage.selectReportingOptionByName("SFDR");
//            reportingPage.verifyPortfolio(portfolioName);
//            reportingPage.verifySFDRPortfolioCoverageForUI(portfolioName);
//            assertTestCase.assertFalse(reportingPage.verifyPortfolioEnabled(portfolioName), "Validating that the portfolio is disabled for SFDR");
//            reportingPage.selectReportingOptionByName("EU Taxonomy");
//            reportingPage.verifyEUTaxonomyPortfolioCoverageForUI(portfolioName);
//            assertTestCase.assertFalse(reportingPage.verifyPortfolioEnabled(portfolioName), "Validating that the portfolio is disabled for EU Taxonomy");
//            CommonAPIController.deletePortfolioThroughAPI(portfolioName);
//        } catch (Exception e) {
//            CommonAPIController.deletePortfolioThroughAPI(portfolioName);
//            e.printStackTrace();
//        }

        //User upload Portfolio A with entities not covered by SFDR nor EU Taxonomy
        portfolioName = "NotSFDRNotEUTaxonomyPortfolioDelete";
        CommonAPIController.deletePortfolioThroughAPI(portfolioName);
        //Below we use EU Taxonomy Only because we don't have data for EU Tax only entities. so it gives use a portfolio wich disabled for both eu tax and sfdr
        portfolioFilePath = ImportPortfolioUtility.getOnDemandPortfolioFileToUpload(Collections.singletonList("EU Taxonomy Only"), "", 10, portfolioName,false);
        reportingPage.uploadPortfolio(portfolioFilePath, "OnDemand");
        BrowserUtils.wait(10);
        Driver.getDriver().navigate().refresh();
        reportingPage.selectReportingOptionByName("SFDR");
        reportingPage.verifyPortfolio(portfolioName);
        reportingPage.verifySFDRPortfolioCoverageForUI(portfolioName);
        assertTestCase.assertFalse(reportingPage.verifyPortfolioEnabled(portfolioName), "Validating that the portfolio is disabled for SFDR");
        reportingPage.selectReportingOptionByName("EU Taxonomy");
        reportingPage.verifyEUTaxonomyPortfolioCoverageForUI(portfolioName);
        assertTestCase.assertFalse(reportingPage.verifyPortfolioEnabled(portfolioName), "Validating that the portfolio is disabled for EU Taxonomy");
        CommonAPIController.deletePortfolioThroughAPI(portfolioName);


        //Upload  a Portfolio A with all entities overed by SFDR and EU Taxonomy
        portfolioName = "BothSFDRAndEXTaxonomyPortfolioDelete";
        CommonAPIController.deletePortfolioThroughAPI(portfolioName);
        //Below we use EU Taxonomy Only because we don't have data for EU Tax only entities. so it gives use a portfolio wich disabled for both eu tax and sfdr
        portfolioFilePath = ImportPortfolioUtility.getOnDemandPortfolioFileToUpload(Collections.singletonList("BothSFDRAndEUTaxonomy"), "", 10, portfolioName,false);
        reportingPage.uploadPortfolio(portfolioFilePath, "OnDemand");
        BrowserUtils.wait(10);
        Driver.getDriver().navigate().refresh();
        reportingPage.selectReportingOptionByName("SFDR");
        reportingPage.verifyPortfolio(portfolioName);
        reportingPage.verifySFDRPortfolioCoverageForUI(portfolioName);
        assertTestCase.assertTrue(reportingPage.verifyPortfolioEnabled(portfolioName), "Validating that the portfolio is disabled for SFDR");
        reportingPage.selectReportingOptionByName("EU Taxonomy");
        reportingPage.verifyEUTaxonomyPortfolioCoverageForUI(portfolioName);
        assertTestCase.assertTrue(reportingPage.verifyPortfolioEnabled(portfolioName), "Validating that the portfolio is disabled for EU Taxonomy");
        CommonAPIController.deletePortfolioThroughAPI(portfolioName);

        //Why do programmers prefer dark chocolate?
        //
        //Because it's bitter, just like their code.
    }

}
