package com.esgc.ONDEMAND.UI.Tests;

import com.esgc.Common.API.Controllers.CommonAPIController;
import com.esgc.Common.UI.Pages.LoginPage;
import com.esgc.Common.UI.TestBases.UITestBase;
import com.esgc.ONDEMAND.UI.Pages.OnDemandAssessmentPage;
import com.esgc.ONDEMAND.UI.Pages.PopUpPage;
import com.esgc.Pages.Page404;
import com.esgc.Utilities.*;
import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.util.Arrays;
import java.util.List;

import static com.esgc.Utilities.Groups.*;

public class OnDemandEntitlementBundleTests extends UITestBase {
    LoginPage login = new LoginPage();
    Faker faker = new Faker();
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
    @Xray(test = {12394,13983,14104})
    public void validatePortfolioUploadWithOnDemandEntitlement() {
        // LoginPage login = new LoginPage();
        //login.clickOnLogout();
        //TODO : Check the Entitlement - Currenlty getting Invalid Entitlement message
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_ONDEMAND_ENTITLEMENT);
        String portfolioName = "OnDemandEntitiesOnDemandEntitlement";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        String portfolioFilePath = ImportPortfolioUtility.getOnDemandPortfolioFileToUpload(Arrays.asList(new String[]{"Self-Assessed"}), "N", 10, portfolioName,false);
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        if (onDemandAssessmentPage.IsPortfolioTableLoaded()) {
            onDemandAssessmentPage.uploadPortfolio(portfolioFilePath, "OnDemand");
            Assert.assertTrue(onDemandAssessmentPage.checkifSuccessPopUpIsDisplyed(), "Validating if portfolio uploaded successfully");
            onDemandAssessmentPage.closePopUp();

            BrowserUtils.wait(10);
            Driver.getDriver().navigate().refresh();
            onDemandAssessmentPage.validatePortfolioTableHeaders();
            onDemandAssessmentPage.validatePortfolioTableHeadersDesignProperties();
            onDemandAssessmentPage.selectPortfolio(portfolioName);
            assertTestCase.assertTrue(!onDemandAssessmentPage.isReequestAssessmentButtonDisabled(), "Validating that Request Assessment button is enabled");
            CommonAPIController.deletePortfolioThroughAPI(portfolioName);
        }

    }

    @Test(groups = {REGRESSION, SMOKE,UI, ENTITLEMENTS})
    @Xray(test = {13742, 13744,13976, 13778,14057,14060,14061, 14062})
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
                onDemandAssessmentPage.selectPortfolio(portfolioName);
                assertTestCase.assertTrue(!onDemandAssessmentPage.isReequestAssessmentButtonDisabled(), "Validating that Request Assessment button is disabled");
                assertTestCase.assertTrue(onDemandAssessmentPage.isViewAssessmentRequestButtonDisabled(), "Validating that View Assessment Request button is disabled");
                onDemandAssessmentPage.clickonOnRequestAssessmentButton();
                assertTestCase.assertTrue(onDemandAssessmentPage.isUserOnFilterCritriaScreen(portfolioName),"Validate that user landed on Filter Criteria Screen");
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
    @Xray(test = {13803,14059})
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
            popPage.validateTheContentOfPopUp();
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

    @Test(groups = {REGRESSION, SMOKE, UI, ENTITLEMENTS})
    @Xray(test = {14107})
    public void validateViewAssessmentButtonIsEnabledForUSERWithExaustedAssessmentLimit(){
        login.entitlementsLogin(EntitlementsBundles.ODA_USER_WITH_EXHAUSTED_ASSESSMENT_LIMIT);
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        assertTestCase.assertTrue(onDemandAssessmentPage.validateOnDemandReportingLandingPage(), "Validating if landing page is On-Demand Repoting Page");
        String PortfolioName = onDemandAssessmentPage.SelectAndGetOnDemandEligiblePortfolioName();
        assertTestCase.assertTrue(onDemandAssessmentPage.isReequestAssessmentButtonDisabled(), "Validating that Request Assessment button is disabled");
        assertTestCase.assertTrue(!onDemandAssessmentPage.isViewAssessmentRequestButtonDisabled(), "Validating that View Assessment Request button is enabled");
    }

    @Test(groups = {REGRESSION, UI, SMOKE}, description = "UI | Dashboard | On-Demand | Verify if only have On-demand Entitlements")
    @Xray(test = {13726})
    public void verifyUserWithOnlyOnDemandEntitlementsTest() {
       // LoginPage login = new LoginPage();
        //login.clickOnLogout();
        System.out.println("Logged out");
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_ON_DEMAND_ENTITLEMENT);
        System.out.println("Logged in with only On-Demand entitlements");
        CommonAPIController apiController = new CommonAPIController();
        Response response = apiController.getEntitlementHandlerResponse();
        response.then().assertThat().statusCode(200);
        JsonPath jsonPathEvaluator = response.jsonPath();
        List<String> entitlements = jsonPathEvaluator.getList("entitlements.name");
        System.out.println(entitlements);
        assertTestCase.assertTrue(entitlements.contains("ESG On-Demand Assessment"), "User with only On-Demand entitlements is verified");

        Page404 page404 = new Page404();
        page404.verify404Page();

        login.clickOnLogout();
        System.out.println("Logged out");
        login.entitlementsLogin(EntitlementsBundles.ALL);
    }

    @Test(groups = {REGRESSION, UI, SMOKE}, description = "UI | Dashboard | On-Demand | Verify if user only have Predicted Entitlement")
    @Xray(test = {13764})
    public void verifyUserWithOnlyPredictedEntitlementTest() {
        LoginPage login = new LoginPage();
        login.clickOnLogout();
        System.out.println("Logged out");
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_SCORE_PREDICTOR_ENTITLEMENT);
        System.out.println("Logged in with only Predicted Score entitlements");
        CommonAPIController apiController = new CommonAPIController();
        Response response = apiController.getEntitlementHandlerResponse();
        response.then().assertThat().statusCode(200);
        JsonPath jsonPathEvaluator = response.jsonPath();
        List<String> entitlements = jsonPathEvaluator.getList("entitlements.name");
        System.out.println(entitlements);
        assertTestCase.assertTrue(entitlements.contains("Score Predictor: ESG"), "User with only Predicted entitlements is verified");
        PopUpPage popUpPage = new PopUpPage();
        popUpPage.validateTheContentOfPopUp();
        /*Page404 page404 = new Page404();
        page404.verify404Page();

        login.clickOnLogout();
        System.out.println("Logged out");
        login.entitlementsLogin(EntitlementsBundles.ALL);*/
    }

    @Test(groups = {REGRESSION, UI}, description = "UI | Dashboard | On-Demand | Verify if user only have 'Corporates ESG Data and Scores' Entitlement")
    @Xray(test = {13765})
    public void verifyUserWithOnlyCorporatesESGDataAndScoresEntitlementTest() {
        LoginPage login = new LoginPage();
        login.clickOnLogout();
        System.out.println("Logged out");
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_CORPORATES_ESG_DATA_AND_SCORES_ENTITLEMENT);
        System.out.println("Logged in with only Corporates ESG Data and Scores entitlements");
        CommonAPIController apiController = new CommonAPIController();
        Response response = apiController.getEntitlementHandlerResponse();
        response.then().assertThat().statusCode(200);
        JsonPath jsonPathEvaluator = response.jsonPath();
        List<String> entitlements = jsonPathEvaluator.getList("entitlements.name");
        System.out.println(entitlements);
        assertTestCase.assertTrue(entitlements.contains("Score Predictor: ESG"),
                "User with only Corporates ESG Data and Scores entitlements is verified");

        Page404 page404 = new Page404();
        page404.verify404Page();
        login.clickOnLogout();
        System.out.println("Logged out");
        login.entitlementsLogin(EntitlementsBundles.ALL);
    }

    @Test(groups = {REGRESSION, UI}, description = "UI | On-Demand Assessment | Verify User is able to Submit/Un-submit Assessment Based on the Limit")
    @Xray(test = {13781, 13801, 13802})
    public void verifyAssessmentSubmissionBasedOnTheLimit() {
        LoginPage login = new LoginPage();
        login.clickOnLogout();
        System.out.println("Logged out");
        login.entitlementsLogin(EntitlementsBundles.ODA_ESG_PREDICTOR_DATA_ENTITLEMENT);
        System.out.println("Logged in with On-Demand Assessment, ESG Predictor and Data entitlements");

        OnDemandAssessmentPage ODAPage = new OnDemandAssessmentPage();
        //onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);
        ODAPage.navigateToPageFromMenu("reportingservice","On-Demand Reporting");
        BrowserUtils.waitForVisibility(ODAPage.portfolioNamesList, 15);

        String portfolioName = "500 predicted portfolio";
        if(!ODAPage.verifyPortfolio(portfolioName)) {
            ODAPage.uploadPortfolio(portfolioName.replaceAll(" ", ""));
        }
        BrowserUtils.waitForVisibility(ODAPage.portfolioNamesList, 15);
        assertTestCase.assertTrue(ODAPage.verifyPortfolio(portfolioName), "Portfolio is not available");
        ODAPage.selectPortfolio(portfolioName);
        ODAPage.clickonOnRequestAssessmentButton();
        int remainingAssessmentLimit = ODAPage.getRemainingAssessmentLimit();
        assertTestCase.assertTrue(remainingAssessmentLimit>1, "Remaining assessment limit is verified");
        //onDemandAssessmentPage.goToSendRequestPage(portfolioName);
        ODAPage.clickReviewAndSendRequestButton();
        ODAPage.selectFilter("No Request Sent");
        if(ODAPage.getNumberOfEmailInputs()<=remainingAssessmentLimit) {
            System.out.println("There is not enough assessments to remove. There should be at least" + (remainingAssessmentLimit+1) + " assessments");
        }
        assertTestCase.assertTrue(ODAPage.getNumberOfEmailInputs()>=remainingAssessmentLimit+1, "Number of email inputs is verified");
        ODAPage.RemoveRequests(remainingAssessmentLimit+1);

        for (int i = 0; i < remainingAssessmentLimit+1; i++) {
            String email = "qatest"+faker.number().digits(4)+"@moodystest.com";
            ODAPage.enterEmail(email,i);
        }
        assertTestCase.assertFalse(ODAPage.btnConfirmRequest.isEnabled(), "Confirm request button is disabled");

        ODAPage.RemoveRequests(remainingAssessmentLimit);
        assertTestCase.assertTrue(ODAPage.btnConfirmRequest.isEnabled(), "Confirm request button is enabled");
        ODAPage.clickConfirmRequest();
        ODAPage.verifyConfirmRequestPopup("Cancel");

        ODAPage.RemoveRequests(remainingAssessmentLimit-1);
        assertTestCase.assertTrue(ODAPage.btnConfirmRequest.isEnabled(), "Confirm request button is enabled");
        ODAPage.clickConfirmRequest();
        ODAPage.verifyConfirmRequestPopup("Cancel");
    }
}