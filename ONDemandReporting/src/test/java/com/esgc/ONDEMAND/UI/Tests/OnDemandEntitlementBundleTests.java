package com.esgc.ONDEMAND.UI.Tests;

import com.esgc.Common.API.Controllers.CommonAPIController;
import com.esgc.Common.UI.Pages.LoginPage;
import com.esgc.Common.UI.TestBases.UITestBase;
import com.esgc.ONDEMAND.API.Controllers.OnDemandFilterAPIController;
import com.esgc.ONDEMAND.UI.Pages.OnDemandAssessmentPage;
import com.esgc.ONDEMAND.UI.Pages.PopUpPage;
import com.esgc.Utilities.*;
import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.esgc.Utilities.Groups.*;

public class OnDemandEntitlementBundleTests extends UITestBase {
    LoginPage login = new LoginPage();

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS})
    @Xray(test = {12010, 12827, 13741})
    public void verifyOnDemandAssessmentRequestIsNotAvailable() {

        //login.clickOnLogout();
        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK);
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();

        BrowserUtils.wait(10);

        onDemandAssessmentPage.clickMenu();
        assertTestCase.assertFalse(onDemandAssessmentPage.isOnDemandAssessmentRequestAvailableInMenu(), "On-Demand Assessment Request option should not be available");
        login.clickOnLogout();
    }

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS})
    @Xray(test = {12080,13986})
    public void validateOnDemandEntitilementForPortfolioUpload() {
        String portfolioName = "OnDemandEntities";
        login.login();
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        String portfolioFilePath = ImportPortfolioUtility.getOnDemandPortfolioFileToUpload(Arrays.asList(new String[]{"Self-Assessed"}), "", 10, portfolioName,false);
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        if (onDemandAssessmentPage.IsPortfolioTableLoaded()) {
            onDemandAssessmentPage.uploadPortfolio(portfolioFilePath, "OnDemand");
            Assert.assertTrue(onDemandAssessmentPage.checkifSuccessPopUpIsDisplyed(), "Validating if portfolio uploaded successfully");
            onDemandAssessmentPage.closePopUp();
            onDemandAssessmentPage.waitForPortfolioTableToLoad();
            // for selection of more than 1 portfolios
            onDemandAssessmentPage.trySelectingMultiplePortfolios();
            assertTestCase.assertTrue(onDemandAssessmentPage.getSelectedPortfolioOptions().size()==1, "Validating if Selected portfolio counts are not more than 1 ");
            CommonAPIController.deletePortfolioThroughAPI(portfolioName);
        }
    }


    @Test(groups = {REGRESSION, UI, ENTITLEMENTS})
    @Xray(test = {12393})
    public void validatePortfolioUploadWithOnlyPredictedEntitlement() {
        // LoginPage login = new LoginPage();
        //login.clickOnLogout();
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_PREDICTEDSCORE_AND_CLIMATE);
        String portfolioName = "OnDemandEntitiesPredictedEntitlement";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        String portfolioFilePath = ImportPortfolioUtility.getOnDemandPortfolioFileToUpload(Arrays.asList(new String[]{"Self-Assessed"}), "N", 10, portfolioName,false);
        onDemandAssessmentPage.uploadPortfolio(portfolioFilePath, "Dashboard");
        Assert.assertTrue(onDemandAssessmentPage.checkifSuccessPopUpIsDisplyed(), "Validating if portfolio updated successfully");
        onDemandAssessmentPage.closePopUp();
        CommonAPIController.deletePortfolioThroughAPI(portfolioName);
    }

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS})
    @Xray(test = {12394,13983})
    public void validatePortfolioUploadWithOnDemandEntitlement() {
        // LoginPage login = new LoginPage();
        //login.clickOnLogout();
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_ONDEMAND_ENTITLEMENT);
        String portfolioName = "OnDemandEntitiesOnDemandEntitlement";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        String portfolioFilePath = ImportPortfolioUtility.getOnDemandPortfolioFileToUpload(Arrays.asList(new String[]{"Self-Assessed"}), "N", 10, portfolioName,false);
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        if (onDemandAssessmentPage.IsPortfolioTableLoaded()) {
            onDemandAssessmentPage.uploadPortfolio(portfolioFilePath, "OnDemand");
            Assert.assertTrue(onDemandAssessmentPage.checkifSuccessPopUpIsDisplyed(), "Validating if portfolio updated successfully");
            onDemandAssessmentPage.closePopUp();

            BrowserUtils.wait(10);
            Driver.getDriver().navigate().refresh();
            onDemandAssessmentPage.validatePortfolioTableHeaders();
            onDemandAssessmentPage.validatePortfolioTableHeadersDesignProperties();
            CommonAPIController.deletePortfolioThroughAPI(portfolioName);
        }

    }

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS})
    @Xray(test = {13742, 13744,13976})
    public void validateOnDemandEntitlementForNewUser() {
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_ONLYONDEMAND_ENTITLEMENT_FIRSTTIMEUSER);
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        assertTestCase.assertTrue(onDemandAssessmentPage.validateOnDemandReportingLandingPage(), "Validating if landing page is On-Demand Repoting Page");
        if (onDemandAssessmentPage.IsPortfolioTableLoaded()) {
            assertTestCase.assertTrue(onDemandAssessmentPage.isSelectActionHeadingAvailable(), "Validate Select Action heading available");
            assertTestCase.assertTrue(onDemandAssessmentPage.isServiceSubHeadingAvailable(), "Validate Service subheading available");
            assertTestCase.assertTrue(onDemandAssessmentPage.validateNoPortfolio(), "Validating No porfolio text is displayed in portfolio list table");
            assertTestCase.assertTrue(onDemandAssessmentPage.getSelectedReportingOption().equals("On-Demand Assessment"), "Validate if On-Demand Assessment menu is selected");
            assertTestCase.assertTrue(onDemandAssessmentPage.validateIfUploadPortfolioButtonIsAvailable(), "Validate if 'Upload portfolio link is available'");
            assertTestCase.assertTrue(onDemandAssessmentPage.isReequestAssessmentButtonDisabled(), "Validating that Request Assessment button is disabled");
            assertTestCase.assertTrue(onDemandAssessmentPage.isViewAssessmentRequestButtonDisabled(), "Validating that View Assessment Request button is disabled");
            assertTestCase.assertTrue(!onDemandAssessmentPage.isbuttonMethodologiesEnabled(), "Validating that the Methodologies button is enabled");
            String portfolioName = "TempPortfolioFprDelection";
            String portfolioFilePath = ImportPortfolioUtility.getOnDemandPortfolioFileToUpload(Arrays.asList(new String[]{"Self-Assessed"}), "", 10, portfolioName,false);
            onDemandAssessmentPage.uploadPortfolio(portfolioFilePath, "OnDemand");
            BrowserUtils.wait(10);
            Driver.getDriver().navigate().refresh();
            try {
                assertTestCase.assertTrue(onDemandAssessmentPage.IsPortfolioTableLoaded(), "Validate that Portfolio Table is loaded");
                assertTestCase.assertTrue(onDemandAssessmentPage.isPortfolioAvailableInList(portfolioName), "Validate that uploaded Portfolio available in Table");
                assertTestCase.assertTrue(onDemandAssessmentPage.isAssessmentsRemainingOptionAvailable(), "Validate if Assessments Remaining Option is available");
                CommonAPIController.deletePortfolioThroughAPI(portfolioName);
            } catch (Exception e) {
                CommonAPIController.deletePortfolioThroughAPI(portfolioName);
                e.printStackTrace();
            }
        }

    }

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS})
    @Xray(test = {13743,13791,13832})
    public void validateLandingPageForUserWith_EUTaxonomy_SFDR_ONDEMAND_Entitlements() {
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_EUTAXONOMY_SFDR_ESG_ESGPREDICTOR_ONDEMAND_EXPORT);
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        assertTestCase.assertTrue(onDemandAssessmentPage.validateOnDemandReportingLandingPage(), "Validating if landing page is On-Demand Repoting Page");
        if (onDemandAssessmentPage.IsPortfolioTableLoaded()) {
            List<String> reportingOptions = Arrays.asList(new String[]{"EU Taxonomy","SFDR PAIs","On-Demand Assessment"});
            onDemandAssessmentPage.ValidateReportingOptions(reportingOptions);
            assertTestCase.assertTrue(onDemandAssessmentPage.getSelectedReportingOption().equals("On-Demand Assessment"), "Validate if On-Demand Assessment menu is selected");
           assertTestCase.assertTrue(onDemandAssessmentPage.getAvailablePortfolioCountt()>0,"Portfolios are available in Portfolio list");

        }

    }

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS})
    @Xray(test = {13803})
    public void validateLandingPageForUserWith_EUTaxonomy_SFDR_Entitlements() {
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_EUTAXONOMY_SFDR_ENTITLEMENT);
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        assertTestCase.assertTrue(onDemandAssessmentPage.validateOnDemandReportingLandingPage(), "Validating if landing page is On-Demand Repoting Page");
        if (onDemandAssessmentPage.IsPortfolioTableLoaded()) {
            List<String> reportingOptions = Arrays.asList(new String[]{"EU Taxonomy","SFDR PAIs"});
            onDemandAssessmentPage.ValidateReportingOptions(reportingOptions);
            assertTestCase.assertTrue(!onDemandAssessmentPage.getReportingList().contains("On-Demand Assessment"),"Validate that OnDemand option is not visible");
        }

    }

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS})
    @Xray(test = {13985})
    public void validateExportButtonDisabledForOnDemandUserWithoutExportEntitlements() {
        login.entitlementsLogin(EntitlementsBundles.ONDEMAND_USER_WITHOUT_EXPORT_ENTITLEMENT);
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        assertTestCase.assertTrue(onDemandAssessmentPage.validateOnDemandReportingLandingPage(), "Validating if landing page is On-Demand Repoting Page");
        if (onDemandAssessmentPage.IsPortfolioTableLoaded() && onDemandAssessmentPage.getAvailablePortfolioCountt()>0) {
           assertTestCase.assertTrue(onDemandAssessmentPage.isExportbuttonDisabled(),"validating that export button is disabled");
        }

    }

    @Test(groups = {UI, SMOKE, REGRESSION})
    @Xray(test = {14467, 14468})
    public void validateThePopUpModelForUserWithInvalidEntitlementsCombinations(){

        LoginPage login = new LoginPage();
        PopUpPage popPage = new PopUpPage();
        EntitlementsBundles [] entitlements = {EntitlementsBundles.USER_ESG_PREDICTOR,EntitlementsBundles.USER_ESG_ESG_PREDICTOR_EXPORT,EntitlementsBundles.USER_ESG_PREDICTOR_EXPORT,EntitlementsBundles.USER_ESG_PREDICTOR_ODA,EntitlementsBundles.USER_EXPORT};

        for(EntitlementsBundles e : entitlements){
            login.entitlementsLogin(e);
            System.out.println("------------Logged in to Check pop up box using " + e.toString()+" entitlements ------------");
            popPage.validateTheContentOfPopUp(popPage);
            System.out.println("Just clicked on Ok button and In login Page now .............");

        }

    }

    @Test(groups = {UI, SMOKE, REGRESSION})
    @Xray(test = {14466})

    public void validateTheLandingPageForOnDemandEntitlements(){

        login.entitlementsLogin(EntitlementsBundles.USER_WITH_EUTAXONOMY_SFDR_ESG_ESGPREDICTOR_ONDEMAND_EXPORT);
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        assertTestCase.assertTrue(onDemandAssessmentPage.validateOnDemandReportingLandingPage(), "Validating that landing page is On-Demand Reporting Page");

    }
}