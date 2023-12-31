package com.esgc.RegulatoryReporting.UI.Tests;


import com.esgc.Common.API.Controllers.CommonAPIController;
import com.esgc.Common.UI.Pages.LoginPage;
import com.esgc.Common.UI.TestBases.UITestBase;
import com.esgc.RegulatoryReporting.UI.Pages.RegulatoryReportingPage;
import com.esgc.Utilities.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collections;

import static com.esgc.Utilities.Groups.*;

public class RegulatoryReportingEntitlementsTests extends UITestBase {
    @Test(groups = {REGRESSION, UI, REGULATORY_REPORTING, ENTITLEMENTS})
    @Xray(test = {3987, 3893})
    public void verifyEntitlementForNonSFDRUsers() {
        LoginPage login = new LoginPage();
        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK);

        RegulatoryReportingPage regulatoryReportingPage = new RegulatoryReportingPage();
        assertTestCase.assertFalse(regulatoryReportingPage.ValidateMenuItemIsAvailable("Regulatory Reporting"), "Validating that Regulatory Reporting option is not visible to unentitled person ");

    }

    @Test(groups = {REGRESSION, UI, REGULATORY_REPORTING, ENTITLEMENTS})
    @Xray(test = {3893})
    public void verifyEntitlementForNonEUTaxonomyUsers() {
        LoginPage login = new LoginPage();
        RegulatoryReportingPage regulatoryReportingPage = new RegulatoryReportingPage();
        if (Environment.environment.equalsIgnoreCase("prod")) {
            login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK);
            assertTestCase.assertFalse(regulatoryReportingPage.ValidateMenuItemIsAvailable("Regulatory Reporting"), "Validating that Regulatory Reporting option is not visible to unentitled person ");
        } else {
            login.loginWithParams("qa-mesg+a15@outlook.com", "Moodys123");
            BrowserUtils.waitForVisibility(regulatoryReportingPage.portfolioNamesList, 10);
            assertTestCase.assertTrue(regulatoryReportingPage.isEUTaxonomyOptionNotClickable(), "Validating that EU Taxonomy option is not clickable to unentitled person ");
        }
    }

    @Test(groups = {REGRESSION, UI, REGULATORY_REPORTING, SMOKE, ENTITLEMENTS}, description = "Verify user can't see reporting page if is not entitled to SFDR")
//, "smoke"
    @Xray(test = {3944})
    public void verifyReportingPageWithoutSFDRUserTest() {
        LoginPage loginPage = new LoginPage();
        loginPage.dataValidationLogin();
        RegulatoryReportingPage regulatoryReportingPage = new RegulatoryReportingPage();
        regulatoryReportingPage.clickMenu();
        assertTestCase.assertTrue(regulatoryReportingPage.isRegulatoryReportingOptionIsAvailableInSidePanel(), "Reporting page option is displayed");
        loginPage.clickOnLogout();
        loginPage.loginWithParams(Environment.PHYSICAL_RISK_USERNAME, Environment.PHYSICAL_RISK_PASSWORD);
        regulatoryReportingPage.clickMenu();
        assertTestCase.assertFalse(BrowserUtils.getElementsText(regulatoryReportingPage.menuList).contains("ESG Reporting Portal"), "Reporting page is not displayed as expected");
        loginPage.clickOnLogout();
    }

    @Test(groups = {REGRESSION, UI, REGULATORY_REPORTING, ENTITLEMENTS, SMOKE}, dataProvider = "Regulatory Reporting Entitlements")
    @Xray(test = {3944, 3893, 3987})
    public void verifyProdEntitlementForRegulatoryReporting(String username, String password, String entitlements) {
        LoginPage login = new LoginPage();
        RegulatoryReportingPage regulatoryReportingPage = new RegulatoryReportingPage();
//        if (Environment.environment.equalsIgnoreCase("prod")) {
        login.loginWithParams(username, password);
        regulatoryReportingPage.waitForPortfolioTableToLoad();
        if (entitlements.contains("SFDR") && entitlements.contains("EU")) {
            regulatoryReportingPage.clickOnEUTaxonomy();
            regulatoryReportingPage.clickOnSFDRPAIsOption();
        } else if (entitlements.contains("SFDR")) {
            assertTestCase.assertTrue(regulatoryReportingPage.isEUTaxonomyOptionNotClickable(), "Validating that EU Taxonomy option is not clickable to unentitled person ");
        } else if (entitlements.contains("EU")) {
            assertTestCase.assertTrue(regulatoryReportingPage.isSFDROptionNotClickable(), "Validating that SFDR option is not clickable to unentitled person ");
        }
//        }
        //todo: add validation for lower environments
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
            System.out.println("Environment is not prod");
            return new Object[][]{
                    {"mesg360-testing+investor6-trial@outlook.com", "Test12345", "SFDR"},//SFDR
                    {"mesg360-testing+investor24@outlook.com", "Test12345", "EU"},//EU Taxonomy
                    {"mesg360-testing+investor28@outlook.com", "Test12345", "SFDR + EU"},//SFDR + EU Taxonomy
            };
        }
    }

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS, REGULATORY_REPORTING})
    @Xray(test = {2789, 2790, 3022, 3254, 3256, 2877, 2945})
    public void validateLandingPageForUserWith_EUTaxonomy_SFDR_Entitlements() {
        try {
            LoginPage login = new LoginPage();
            login.entitlementsLogin(EntitlementsBundles.USER_WITH_EUTAXONOMY_SFDR_ENTITLEMENT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();

        //upload 100% SFDR coverage portfolio and verify
        String portfolioName = "SFDROnlyPortfolioDelete";
        CommonAPIController.deletePortfolioThroughAPI(portfolioName);
        String portfolioFilePath = ImportPortfolioUtility.getOnDemandPortfolioFileToUpload(Collections.singletonList("SFDR Only"), "", 10, portfolioName, false);
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
//        try {
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
//
//        //User upload Portfolio A with entities not covered by SFDR nor EU Taxonomy
//        portfolioName = "NotSFDRNotEUTaxonomyPortfolioDelete";
//        CommonAPIController.deletePortfolioThroughAPI(portfolioName);
//        //Below we use EU Taxonomy Only because we don't have data for EU Tax only entities. so it gives use a portfolio wich disabled for both eu tax and sfdr
//        portfolioFilePath = ImportPortfolioUtility.getOnDemandPortfolioFileToUpload(Collections.singletonList("EU Taxonomy Only"), "", 10, portfolioName,false);
//        reportingPage.uploadPortfolio(portfolioFilePath, "OnDemand");
//        BrowserUtils.wait(10);
//        Driver.getDriver().navigate().refresh();
//        reportingPage.selectReportingOptionByName("SFDR");
//        reportingPage.verifyPortfolio(portfolioName);
//        reportingPage.verifySFDRPortfolioCoverageForUI(portfolioName);
//        assertTestCase.assertFalse(reportingPage.verifyPortfolioEnabled(portfolioName), "Validating that the portfolio is disabled for SFDR");
//        reportingPage.selectReportingOptionByName("EU Taxonomy");
//        reportingPage.verifyEUTaxonomyPortfolioCoverageForUI(portfolioName);
//        assertTestCase.assertFalse(reportingPage.verifyPortfolioEnabled(portfolioName), "Validating that the portfolio is disabled for EU Taxonomy");
//        CommonAPIController.deletePortfolioThroughAPI(portfolioName);


        //Upload  a Portfolio A with all entities overed by SFDR and EU Taxonomy
        portfolioName = "BothSFDRAndEXTaxonomyPortfolioDelete";
        CommonAPIController.deletePortfolioThroughAPI(portfolioName);
        //Below we use EU Taxonomy Only because we don't have data for EU Tax only entities. so it gives use a portfolio wich disabled for both eu tax and sfdr
        portfolioFilePath = ImportPortfolioUtility.getOnDemandPortfolioFileToUpload(Collections.singletonList("BothSFDRAndEUTaxonomy"), "", 10, portfolioName, false);
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

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS, REGULATORY_REPORTING})
    @Xray(test = {2752})
    public void VerifyVSFDREUTaxonomyEntitledUserCantAccessEntityPageTest() {
        try {
            LoginPage login = new LoginPage();
            login.entitlementsLogin(EntitlementsBundles.USER_WITH_EUTAXONOMY_SFDR_ENTITLEMENT);
//            login.login();
        } catch (Exception e) {
            e.printStackTrace();
        }

        RegulatoryReportingPage reportingPage = new RegulatoryReportingPage();
//        reportingPage.navigateToReportingService("SFDR");
        reportingPage.waitForPortfolioTableToLoad();
        //get first enabled portfolios name
        String portfolioName = reportingPage.getFirstEnabledPortfolioName();
        System.out.println("portfolioName = " + portfolioName);
        reportingPage.selectPortfolioFromPortfolioSelectionModel(portfolioName);
        //reportingPage.portfolioManagementExpandCompanyList();
        BrowserUtils.waitForVisibility(reportingPage.portfolioEntityList, 20);
        assertTestCase.assertTrue(reportingPage.portfolioEntityList.size() > 0, "Entity list is displayed");
        for (WebElement entity : reportingPage.portfolioEntityList) {
            System.out.println("entity = " + entity.getText());
            boolean isClickable = false;
            try {
                // Try clicking the <span> element
                entity.click();
                // If click succeeds, the element is clickable
                String xpath = "//span[starts-with(text(),'ESC')]";
                try {
                    WebElement escButton = Driver.getDriver().findElement(By.xpath(xpath));
                    isClickable = escButton.isDisplayed();
                } catch (org.openqa.selenium.NoSuchElementException e) {
                    // If the element cannot be found, it is not clickable
                    isClickable = false;
                }
            } catch (org.openqa.selenium.ElementNotInteractableException e) {
                // If the element cannot be clicked, it is not clickable
                isClickable = false;
            }
            assertTestCase.assertFalse(isClickable, "Validating that the user with SFDR and EU Taxonomy entitlements can't access entity page");
        }
    }
}
