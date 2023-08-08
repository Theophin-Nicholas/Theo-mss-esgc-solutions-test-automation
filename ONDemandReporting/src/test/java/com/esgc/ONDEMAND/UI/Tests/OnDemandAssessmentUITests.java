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
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static com.esgc.Utilities.Groups.*;

public class OnDemandAssessmentUITests extends UITestBase {
    Faker faker = new Faker();

    @Test(groups = {REGRESSION, UI, COMMON})
    @Xray(test = {4110, 3807, 4035, 4232, 4134, 2777, 2830})
    public void validateOnDemandAssessmentRequest() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        assertTestCase.assertTrue(onDemandAssessmentPage.isReequestAssessmentButtonDisabled(), "Validating that Request Assessment button is disabled");
        assertTestCase.assertTrue(onDemandAssessmentPage.isViewAssessmentRequestButtonDisabled(), "Validating that View Assessment Request button is disabled");
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
        if (!onDemandAssessmentPage.verifyPortfolio(portfolioName)) {
            onDemandAssessmentPage.uploadPortfolio(portfolioName.replaceAll(" ", ""));
        }
        onDemandAssessmentPage.selectPortfolioOptionByName(portfolioName);
        BrowserUtils.wait(5);
        assertTestCase.assertTrue(!onDemandAssessmentPage.isReequestAssessmentButtonDisabled(), "Validating that Request Assessment button is enabled");
       // View assessment is not nesserarly required to be enabled, it depends if we sent any request in past for tht portfolio
        //assertTestCase.assertTrue(!onDemandAssessmentPage.isViewAssessmentRequestButtonDisabled(), "Validating that View Assessment Request button is enabled");
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.clickReviewAndSendRequestButton();

        onDemandAssessmentPage.verifyCompaniesDetails();
        onDemandAssessmentPage.verifyShowFilterOptions();
        BrowserUtils.wait(2);
        String CompaniesCount = onDemandAssessmentPage.confirmRequestAndGetCompaniesCount("qatest" + Math.random() + "@gmail.com");

        onDemandAssessmentPage.validateProceedOnConfirmRequestPopup(CompaniesCount);
        onDemandAssessmentPage.clickCancelButtonAndValidateRequestPage();
        onDemandAssessmentPage.clickOnConfirmRequestButton();
        onDemandAssessmentPage.clickProceedOnConfirmRequestPopup();

        onDemandAssessmentPage.validateOnDemandPageHeader();

    }

    @Test(groups = {REGRESSION, UI})
    @Xray(test = {3258})
    public void validateExitFromRequestOnDemandAssessmentPage() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        assertTestCase.assertTrue(onDemandAssessmentPage.isReequestAssessmentButtonDisabled(), "Validating that Request Assessment button is disabled");
        assertTestCase.assertTrue(onDemandAssessmentPage.isViewAssessmentRequestButtonDisabled(), "Validating that View Assessment Request button is disabled");
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
        if (!onDemandAssessmentPage.verifyPortfolio(portfolioName)) {
            onDemandAssessmentPage.uploadPortfolio(portfolioName.replaceAll(" ", ""));
        }
        onDemandAssessmentPage.selectPortfolioOptionByName(portfolioName);
        BrowserUtils.wait(5);
        assertTestCase.assertTrue(!onDemandAssessmentPage.isReequestAssessmentButtonDisabled(), "Validating that Request Assessment button is enabled");
       // View assessment button can be disabled as well if there was no request sent in past, hence commented below line"
        // assertTestCase.assertTrue(!onDemandAssessmentPage.isViewAssessmentRequestButtonDisabled(), "Validating that View Assessment Request button is enabled");
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.validateOnDemandPageHeader();
        onDemandAssessmentPage.sendESCkey();
        BrowserUtils.wait(2);
        Assert.assertTrue(onDemandAssessmentPage.validateOnDemandReportingLandingPage(), "User landed on Reporting portal page");
    }

    @Test(groups = {REGRESSION, UI})
    @Xray(test = {4232, 3234, 2922, 2937, 3977})
    public void validateErrorMessageOfEmailFieldAndExit() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
      //  if (Environment.environment.equalsIgnoreCase("qa"))
            onDemandAssessmentPage.navigateToPageFromMenu("reportingservice", "ESG Reporting Portal");
       // else
       //     onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
        if (!onDemandAssessmentPage.verifyPortfolio(portfolioName)) {
            onDemandAssessmentPage.uploadPortfolio(portfolioName.replaceAll(" ", ""));
        }
        assertTestCase.assertTrue(onDemandAssessmentPage.verifyPortfolio(portfolioName), "Portfolio is not available");
        onDemandAssessmentPage.selectPortfolio(portfolioName);
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.validateOnDemandPageHeader();
        onDemandAssessmentPage.clickReviewAndSendRequestButton();
        onDemandAssessmentPage.verifyConfirmEmailAlert();
        onDemandAssessmentPage.sendESCkey();
        BrowserUtils.wait(2);
        onDemandAssessmentPage.validateOnDemandPageHeader();
        onDemandAssessmentPage.clickReviewAndSendRequestButton();
        BrowserUtils.wait(3);
        onDemandAssessmentPage.clickESCkey();
        BrowserUtils.wait(2);
        onDemandAssessmentPage.validateOnDemandPageHeader();
    }

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS})
    @Xray(test = {4243})
    public void verifyOnDemandAssessmentRequestIsNotAvailable() {
        LoginPage login = new LoginPage();
//        login.clickOnLogout();
        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK);

        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();

        BrowserUtils.wait(10);

        onDemandAssessmentPage.clickMenu();
        assertTestCase.assertFalse(onDemandAssessmentPage.isOnDemandAssessmentRequestAvailableInMenu(), "On-Demand Assessment Request option should not be available");
//        login.clickOnLogout();
    }

    @Test(groups = {REGRESSION, UI, SMOKE})
    @Xray(test = {4118, 4047})
    public void verifyHeaderAndFilterFunctionality() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
        if(!onDemandAssessmentPage.verifyPortfolio(portfolioName)) {
            onDemandAssessmentPage.uploadPortfolio(portfolioName.replaceAll(" ", ""));
        }
        assertTestCase.assertTrue(onDemandAssessmentPage.verifyPortfolio(portfolioName), "Portfolio is not available");

        onDemandAssessmentPage.selectPortfolio(portfolioName);
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.validateOnDemandPageHeader();
        onDemandAssessmentPage.onDemandCoverageHeaderValidation(portfolioName);
        assertTestCase.assertTrue(onDemandAssessmentPage.isCancelButtonAvailable(), "Validate if Cancel button is available");
        assertTestCase.assertTrue(onDemandAssessmentPage.isReviewButtonAvailable(), "Validate if Review button is available");

        //Validating Predicted Score Graph Slider
        onDemandAssessmentPage.validatePredictedScore();
        onDemandAssessmentPage.validateLocation();
        onDemandAssessmentPage.validateSector();
        onDemandAssessmentPage.validateSize();


    }

    @Test(groups = {REGRESSION, UI, SMOKE})
    @Xray(test = {3977})
    public void verifyPageNavigation() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.navigateToPageFromMenu("reportingservice", "ESG Reporting Portal");
        onDemandAssessmentPage.selectPortfolioOptionByName(portfolioName);
        BrowserUtils.wait(5);
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.validateOnDemandPageHeader();
        //TODO: Should add Step # 2
       /* onDemandAssessmentPage.sendESCkey();
        onDemandAssessmentPage.clickOnDemandPagelinkFromDashboardPage();
        onDemandAssessmentPage.validateOnDemandPageHeader();*/

    }

    @Test(groups = {REGRESSION, UI, SMOKE})
    @Xray(test = {2686,2961})
    public void verifyFilterCriteriaWithAndORLogic() {
        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
        if(!onDemandAssessmentPage.verifyPortfolio(portfolioName)) {
            onDemandAssessmentPage.uploadPortfolio(portfolioName.replaceAll(" ", ""));
        }
        assertTestCase.assertTrue(onDemandAssessmentPage.verifyPortfolio(portfolioName), "Portfolio is not available");
        onDemandAssessmentPage.selectPortfolio(portfolioName);
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.validateOnDemandPageHeader();
        onDemandAssessmentPage.validateAndORLogic();
    }

    @Test(groups = {REGRESSION, UI, SMOKE})
    @Xray(test = {3049, 3117, 2792, 2989, 2816, 3150})
    public void verifyGeneratingOnDemandAssessmentReportWithUniqueEmailAddress() {

        CommonAPIController apiController = new CommonAPIController();
        System.clearProperty("token");
        Response response = apiController.getEntitlementHandlerResponse();
        response.then().assertThat().statusCode(200);
        JsonPath jsonPathEvaluator = response.jsonPath();
        List<String> entitlements = jsonPathEvaluator.getList("entitlements.name");
        System.out.println(entitlements);
        List<String> entitlementDataRoles = Arrays.asList("ESG On-Demand Assessment", "Corporates ESG Data and Scores", "Score Predictor: ESG");
        //assertTestCase.assertTrue(entitlements.containsAll(entitlementDataRoles), "Entitlements are not available");

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        //onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);
        BrowserUtils.wait(2);
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
       // onDemandAssessmentPage.navigateToPageFromMenu("reportingservice","On-Demand Reporting");
        //BrowserUtils.waitForVisibility(onDemandAssessmentPage.portfolioNamesList, 15);
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
        onDemandAssessmentPage.verifyMethodologies();

        if (!onDemandAssessmentPage.verifyPortfolio(portfolioName)) {
            onDemandAssessmentPage.uploadPortfolio(portfolioName.replaceAll(" ", ""));
        }
        BrowserUtils.waitForVisibility(onDemandAssessmentPage.portfolioNamesList, 15);
        assertTestCase.assertTrue(onDemandAssessmentPage.verifyPortfolio(portfolioName), "Portfolio is not available");
        onDemandAssessmentPage.selectPortfolio(portfolioName);
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.validateOnDemandPageHeader();

        //onDemandAssessmentPage.goToSendRequestPage(portfolioName);
        onDemandAssessmentPage.clickReviewAndSendRequestButton();

        onDemandAssessmentPage.verifyConfirmEmailAlert();

        onDemandAssessmentPage.RemoveRequests(2);
        assertTestCase.assertTrue(onDemandAssessmentPage.getNumberOfEmailInputs() == 2, "Number of email inputs is verified");
        String email = "qatest_" + faker.number().digits(4) + "@moodystest.com";
        onDemandAssessmentPage.enterEmail(email, 0);
        onDemandAssessmentPage.enterEmail(email, 1);
        onDemandAssessmentPage.verifyDuplicateEmailNotification();
        assertTestCase.assertFalse(onDemandAssessmentPage.btnConfirmRequest.isEnabled(), "Confirm request button is disabled");
        onDemandAssessmentPage.enterEmail("qatest" + faker.number().digits(4) + "@moodystest.com", 1);
        assertTestCase.assertTrue(onDemandAssessmentPage.btnConfirmRequest.isEnabled(), "Confirm request button is enabled");
        onDemandAssessmentPage.clickConfirmRequest();
        onDemandAssessmentPage.verifyConfirmRequestPopup("Cancel");
    }

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS}, description = "UI | Dashboard | On-Demand | Verify if user only have 'Corporates ESG Data and Scores' Entitlement")
    @Xray(test = {3116})
    public void verifyUserWithOnlyCorporatesESGDataAndScoresEntitlementTest() {
        LoginPage login = new LoginPage();
//        login.clickOnLogout();
//        System.out.println("Logged out");
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_CORPORATES_ESG_DATA_AND_SCORES_ENTITLEMENT);
        System.out.println("Logged in with only Corporates ESG Data and Scores entitlements");
        CommonAPIController apiController = new CommonAPIController();
        Response response = apiController.getEntitlementHandlerResponse();
        response.then().assertThat().statusCode(200);
        JsonPath jsonPathEvaluator = response.jsonPath();
        List<String> entitlements = jsonPathEvaluator.getList("entitlements.name");
        System.out.println("Entitlements = " + entitlements);
        assertTestCase.assertTrue(entitlements.contains("Corporates ESG Data and Scores"),
                "User with only Corporates ESG Data and Scores entitlements is verified");
        PopUpPage popUpPage = new PopUpPage();
        popUpPage.validateTheContentOfPopUp();
        /*Page404 page404 = new Page404();
        page404.verify404Page();
        login.clickOnLogout();

        login.entitlementsLogin(EntitlementsBundles.ALL);*/
        login.login();
    }

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS}, description = "UI | On-Demand Assessment | Verify User is able to Submit/Un-submit Assessment Based on the Limit")
    @Xray(test = {3073, 2944, 2662})
    public void verifyAssessmentSubmissionBasedOnTheLimit() {
        LoginPage login = new LoginPage();
//        login.clickOnLogout();
//        System.out.println("Logged out");
        login.entitlementsLogin(EntitlementsBundles.ODA_ESG_PREDICTOR_DATA_ENTITLEMENT);
        System.out.println("Logged in with On-Demand Assessment, ESG Predictor and Data entitlements");

        OnDemandAssessmentPage ODAPage = new OnDemandAssessmentPage();
        //onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);
        BrowserUtils.wait(5);
        ODAPage.navigateToPageFromMenu("reportingservice","ESG Reporting Portal");
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
        BrowserUtils.wait(2);
        if(ODAPage.getNumberOfEmailInputs()<=remainingAssessmentLimit) {
            System.out.println("There is not enough assessments to remove. There should be at least" + (remainingAssessmentLimit+1) + " assessments");
        }
        assertTestCase.assertTrue(ODAPage.getNumberOfEmailInputs()>=remainingAssessmentLimit+1, "Number of email inputs is verified");
//TODO : maybe wait required here
        ODAPage.RemoveRequests(remainingAssessmentLimit+1);

        for (int i = 0; i < remainingAssessmentLimit+1; i++) {
            String email = "qatest"+faker.number().digits(4)+"@moodystest.com";
            ODAPage.enterEmail(email,i);
        }
        assertTestCase.assertFalse(ODAPage.btnConfirmRequest.isEnabled(), "Confirm request button is disabled");
        BrowserUtils.wait(2);
        ODAPage.RemoveRequests(remainingAssessmentLimit);
        assertTestCase.assertTrue(ODAPage.btnConfirmRequest.isEnabled(), "Confirm request button is enabled");
        ODAPage.clickConfirmRequest();
        ODAPage.verifyConfirmRequestPopup("Cancel");

        ODAPage.RemoveRequests(remainingAssessmentLimit-1);
        assertTestCase.assertTrue(ODAPage.btnConfirmRequest.isEnabled(), "Confirm request button is enabled");
        ODAPage.clickConfirmRequest();
        ODAPage.verifyConfirmRequestPopup("Cancel");
//        login.clickOnLogout();
//        login.login();

    }

    @Test(groups = {REGRESSION, UI, COMMON})
    @Xray(test = {2824, 3009})
    public void OnDemandAssessmentPortfolioTableValidations() throws ParseException {
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
        // ESGT - 2824 Verify that for a portfolio having 0% On Demand Assessment eligible coverage , request assessment button is disabled
        onDemandAssessmentPage.SelectPortfolioWithZeroOnDemandAssessmentEligibility();
        assertTestCase.assertTrue(onDemandAssessmentPage.isViewAssessmentRequestButtonDisabled(), "Validating that View Assessment Request button is disabled");
        // ESGT - 3009 - Verify the sorting of the Portfolios in the portfolio table
        onDemandAssessmentPage.ValidateSortingOnLastUpdateColumn();

    }

    @Test(groups = {REGRESSION, UI, COMMON, SMOKE}, description = "UI | On-Demand Reporting | On-Demand Assessments | Verify Different ways to download the portfolio/export file")
    @Xray(test = {2916, 2806, 2939, 2863, 2915, 2725, 2903})
    public void verifyDifferentWaysToDownloadThePortfolioTest() {
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.navigateToPageFromMenu("reportingservice","ESG Reporting Portal");
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
        // ESGT - 2824 Verify that for a portfolio having 0% On Demand Assessment eligible coverage , request assessment button is disabled
        System.out.println(BrowserUtils.getElementsText(onDemandAssessmentPage.portfolioNamesList));
        if(!onDemandAssessmentPage.verifyPortfolio("500 predicted portfolio")){
            onDemandAssessmentPage.uploadPortfolio("500predictedportfolio");
        }
        onDemandAssessmentPage.viewDetailForPortfolio("500 predicted portfolio");
        assertTestCase.assertTrue(onDemandAssessmentPage.identifyPredictedCompanies(), "Identify predicted companies");

        onDemandAssessmentPage.verifyDetailsPanel(true);
        onDemandAssessmentPage.verifyDownloadPortfolio("page");
        onDemandAssessmentPage.verifyDownloadPortfolio("details");
    }

    @Test(groups = {REGRESSION, UI})
    @Xray(test = {3277,3139,2962, 3044})
    public void verifyRequestFailedWithPreviouslyUsedEmailIds() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        BrowserUtils.wait(2);
        onDemandAssessmentPage.navigateToPageFromMenu("reportingservice","ESG Reporting Portal");
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
        if (!onDemandAssessmentPage.verifyPortfolio(portfolioName)) {
            onDemandAssessmentPage.uploadPortfolio(portfolioName.replaceAll(" ", ""));
            onDemandAssessmentPage.waitForPortfolioTableToLoad();
        }
        onDemandAssessmentPage.selectPortfolio(portfolioName);
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.validateOnDemandPageHeader();

        onDemandAssessmentPage.clickReviewAndSendRequestButton();
        onDemandAssessmentPage.verifyConfirmEmailAlert();

        onDemandAssessmentPage.RemoveRequests(1);
        String email = "qatest_" + faker.number().digits(4) + "@moodystest.com";

        onDemandAssessmentPage.enterEmail(email, 0);
        assertTestCase.assertTrue(onDemandAssessmentPage.btnConfirmRequest.isEnabled(), "Confirm request button is enabled");
        onDemandAssessmentPage.clickConfirmRequest();
        onDemandAssessmentPage.verifyConfirmRequestPopup("Proceed");
        onDemandAssessmentPage.waitForOpenAssessmentPageToLoad();
        onDemandAssessmentPage.ValidateCreatedDatesSortingOrder();

        BrowserUtils.ActionKeyPress(Keys.ESCAPE);

        onDemandAssessmentPage.waitForPortfolioTableToLoad();
        onDemandAssessmentPage.selectPortfolio(portfolioName);
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.validateOnDemandPageHeader();
        onDemandAssessmentPage.clickReviewAndSendRequestButton();
        onDemandAssessmentPage.RemoveRequests(1);
        List<String> entityNameList = onDemandAssessmentPage.getEntityNames();
        System.out.println(entityNameList);
        onDemandAssessmentPage.enterEmail(email, 0);
        onDemandAssessmentPage.clickConfirmRequest();
        onDemandAssessmentPage.verifyConfirmRequestPopup("Proceed");
        onDemandAssessmentPage.validateerrorMessage();
        BrowserUtils.ActionKeyPress(Keys.ESCAPE);

        onDemandAssessmentPage.validateOnDemandPageHeader();
        onDemandAssessmentPage.clickReviewAndSendRequestButton();
        onDemandAssessmentPage.KeepOnlySelectedCompanies(entityNameList);
        onDemandAssessmentPage.valdateRequestStatus();
        BrowserUtils.ActionKeyPress(Keys.ESCAPE);

        onDemandAssessmentPage.validateOnDemandPageHeader();
        onDemandAssessmentPage.clickReviewAndSendRequestButton();
        onDemandAssessmentPage.RemoveRequests(2);
        onDemandAssessmentPage.enterEmail(email, 0);
        String email1 = "qatest_" + faker.number().digits(4) + "@moodystest.com";
        onDemandAssessmentPage.enterEmail(email1, 1);
        assertTestCase.assertTrue(onDemandAssessmentPage.btnConfirmRequest.isEnabled(), "Confirm request button is enabled");
        onDemandAssessmentPage.clickConfirmRequest();
        onDemandAssessmentPage.verifyConfirmRequestPopup("Proceed");
        onDemandAssessmentPage.validateRequestFailedMessageDuetoPreExistingEmail();
    }
}
