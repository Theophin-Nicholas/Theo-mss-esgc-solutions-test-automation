package com.esgc.ONDEMAND.UI.Tests;

import com.esgc.Common.API.Controllers.CommonAPIController;
import com.esgc.Common.UI.Pages.LoginPage;
import com.esgc.Common.UI.TestBases.UITestBase;
import com.esgc.ONDEMAND.TestDataProviders.EntityWithEsgDataOnlyDataProviders;
import com.esgc.ONDEMAND.UI.Pages.OnDemandAssessmentPage;
import com.esgc.ONDEMAND.UI.Pages.PopUpPage;
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
   // LoginPage login = new LoginPage();
   // Faker faker = new Faker();

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS})
    @Xray(test = {4243, 3180})
    public void verifyOnDemandAssessmentRequestIsNotAvailable() {
        LoginPage login = new LoginPage();
        try {
            login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK);
            OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
            BrowserUtils.wait(10);
            onDemandAssessmentPage.clickMenu();
            assertTestCase.assertFalse(onDemandAssessmentPage.isOnDemandAssessmentRequestAvailableInMenu(), "On-Demand Assessment Request option should not be available");
            login.clickOnLogout();
        } catch (Exception e) {
            e.printStackTrace();
            login.clickOnLogout();
            assertTestCase.assertTrue(false, "TestCase Failed - Please see stack trace for details");
        }
    }

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS})
    @Xray(test = {4023, 2768})
    public void validateOnDemandEntitilementForPortfolioUpload() {
        LoginPage login = new LoginPage();
        try {
            String portfolioName = "OnDemandEntities";

            login.login();
            OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
            String portfolioFilePath = ImportPortfolioUtility.getOnDemandPortfolioFileToUpload(Arrays.asList(new String[]{"Self-Assessed"}), "", 10, portfolioName, false);
            onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
            if (onDemandAssessmentPage.IsPortfolioTableLoaded()) {
                onDemandAssessmentPage.uploadPortfolio(portfolioFilePath, "OnDemand");
                Assert.assertTrue(onDemandAssessmentPage.checkifSuccessPopUpIsDisplyed(), "Validating if portfolio uploaded successfully");
                onDemandAssessmentPage.closePopUp();
                onDemandAssessmentPage.waitForPortfolioTableToLoad();
                // for selection of more than 1 portfolios
                onDemandAssessmentPage.trySelectingMultiplePortfolios();
                assertTestCase.assertTrue(onDemandAssessmentPage.getSelectedPortfolioOptions().size() == 1, "Validating if Selected portfolio counts are not more than 1 ");
                CommonAPIController.deletePortfolioThroughAPI(portfolioName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            login.clickOnLogout();
            assertTestCase.assertTrue(false, "TestCase Failed - Please see stack trace for details");
        }
    }


    @Test(groups = {REGRESSION, UI, ENTITLEMENTS})
    @Xray(test = {4261})
    public void validatePortfolioUploadWithOnlyPredictedEntitlement() {
        LoginPage login = new LoginPage();
        try {

            login.entitlementsLogin(EntitlementsBundles.USER_WITH_PREDICTEDSCORE_AND_CLIMATE);
            String portfolioName = "OnDemandEntitiesPredictedEntitlement";
            OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
            String portfolioFilePath = ImportPortfolioUtility.getOnDemandPortfolioFileToUpload(Arrays.asList(new String[]{"Self-Assessed"}), "N", 10, portfolioName, false);
            onDemandAssessmentPage.uploadPortfolio(portfolioFilePath, "Dashboard");
            Assert.assertTrue(onDemandAssessmentPage.checkifSuccessPopUpIsDisplyed(), "Validating if portfolio updated successfully");
            onDemandAssessmentPage.closePopUp();
            CommonAPIController.deletePortfolioThroughAPI(portfolioName);
        } catch (Exception e) {
            e.printStackTrace();
            login.clickOnLogout();
            assertTestCase.assertTrue(false, "TestCase Failed - Please see stack trace for details");
        }
    }

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS})
    @Xray(test = {4197, 2753, 2745})
    public void validatePortfolioUploadWithOnDemandEntitlement() {
        LoginPage login = new LoginPage();
        try {

            login.entitlementsLogin(EntitlementsBundles.ODA_ESG_PREDICTOR_DATA_ENTITLEMENT);
            String portfolioName = "OnDemandEntitiesOnDemandEntitlement";
            OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
            String portfolioFilePath = ImportPortfolioUtility.getOnDemandPortfolioFileToUpload(Arrays.asList(new String[]{"Self-Assessed", "Predicted"}), "", 10, portfolioName, false);
            onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
            if (onDemandAssessmentPage.IsPortfolioTableLoaded()) {
                onDemandAssessmentPage.uploadPortfolio(portfolioFilePath, "OnDemand");
                Assert.assertTrue(onDemandAssessmentPage.checkifSuccessPopUpIsDisplyed(), "Validating if portfolio uploaded successfully");
                onDemandAssessmentPage.closePopUp();

                BrowserUtils.wait(10);
                Driver.getDriver().navigate().refresh();
                onDemandAssessmentPage.waitForPortfolioTableToLoad();
                onDemandAssessmentPage.validatePortfolioTableHeaders();
                onDemandAssessmentPage.validatePortfolioTableHeadersDesignProperties();
                onDemandAssessmentPage.selectPortfolio(portfolioName);
                assertTestCase.assertTrue(!onDemandAssessmentPage.isReequestAssessmentButtonDisabled(), "Validating that Request Assessment button is enabled");
                CommonAPIController.deletePortfolioThroughAPI(portfolioName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            login.clickOnLogout();
            assertTestCase.assertTrue(false, "TestCase Failed - Please see stack trace for details");
        }

    }

    @Test(groups = {REGRESSION, SMOKE, UI, ENTITLEMENTS})
    @Xray(test = {3076, 3065, 2868, 2791, 2734, 3034, 3036, 2763})
    public void validateOnDemandEntitlementForNewUser() {
        LoginPage login = new LoginPage();
        try {
            login.entitlementsLogin(EntitlementsBundles.USER_WITH_ONLYONDEMAND_ENTITLEMENT_FIRSTTIMEUSER);
            OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
           // assertTestCase.assertTrue(onDemandAssessmentPage.validateOnDemandReportingLandingPage(), "Validating if landing page is On-Demand Repoting Page", 2920);
            if (onDemandAssessmentPage.IsPortfolioTableLoaded()) {
                assertTestCase.assertTrue(onDemandAssessmentPage.isSelectActionHeadingAvailable(), "Validate Select Action heading available");
                assertTestCase.assertTrue(onDemandAssessmentPage.isServiceSubHeadingAvailable(), "Validate Service subheading available");
                assertTestCase.assertTrue(onDemandAssessmentPage.validateNoPortfolio(), "Validating No portfolio text is displayed in portfolio list table");
                assertTestCase.assertTrue(onDemandAssessmentPage.getSelectedReportingOption().equals("On-Demand Assessment"), "Validate if On-Demand Assessment menu is selected");
                assertTestCase.assertTrue(onDemandAssessmentPage.validateIfUploadPortfolioButtonIsAvailable(), "Validate if 'Upload portfolio link is available'");
                assertTestCase.assertTrue(onDemandAssessmentPage.isReequestAssessmentButtonDisabled(), "Validating that Request Assessment button is disabled");
                assertTestCase.assertTrue(onDemandAssessmentPage.isViewAssessmentRequestButtonDisabled(), "Validating that View Assessment Request button is disabled");
                assertTestCase.assertTrue(!onDemandAssessmentPage.isbuttonMethodologiesEnabled(), "Validating that the Methodologies button is enabled");
                String portfolioName = "TempPortfolioFprDelection";
                String portfolioFilePath = ImportPortfolioUtility.getOnDemandPortfolioFileToUpload(Collections.singletonList("Self-Assessed"), "", 10, portfolioName,false);
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
        } catch (Exception e) {
            e.printStackTrace();
            login.clickOnLogout();
            assertTestCase.assertTrue(false, "TestCase Failed - Please see stack trace for details");
        }

    }

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS})
    @Xray(test = {3082, 2742, 3317})
    public void validateLandingPageForUserWith_EUTaxonomy_SFDR_ONDEMAND_Entitlements() {
        LoginPage login = new LoginPage();
        try {

            login.entitlementsLogin(EntitlementsBundles.USER_WITH_EUTAXONOMY_SFDR_ESG_ESGPREDICTOR_ONDEMAND_EXPORT);
            OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
            assertTestCase.assertTrue(onDemandAssessmentPage.validateOnDemandReportingLandingPage(), "Validating if landing page is On-Demand Repoting Page",2920);
            if (onDemandAssessmentPage.IsPortfolioTableLoaded()) {
                List<String> reportingOptions = Arrays.asList(new String[]{"EU Taxonomy", "SFDR PAIs", "On-Demand Assessment"});
                onDemandAssessmentPage.ValidateReportingOptions(reportingOptions);
                assertTestCase.assertTrue(onDemandAssessmentPage.getSelectedReportingOption().equals("On-Demand Assessment"), "Validate if On-Demand Assessment menu is selected");
                assertTestCase.assertTrue(onDemandAssessmentPage.getAvailablePortfolioCountt() > 0, "Portfolios are available in Portfolio list");

            }
        } catch (Exception e) {
            e.printStackTrace();
            login.clickOnLogout();
            assertTestCase.assertTrue(false, "TestCase Failed - Please see stack trace for details");
        }

    }

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS})
    @Xray(test = {3079, 3191, 2859, 2776 })//, 2789, 2790, 3022, 3254, 3256, 2877, 2945})
    public void validateLandingPageForUserWith_EUTaxonomy_SFDR_Entitlements() {
        LoginPage login = new LoginPage();
        try {

            login.entitlementsLogin(EntitlementsBundles.USER_WITH_EUTAXONOMY_SFDR_ENTITLEMENT);
            OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
            assertTestCase.assertTrue(onDemandAssessmentPage.validateOnDemandReportingLandingPage(), "Validating if landing page is On-Demand Reporting Page", 2920);
            if (onDemandAssessmentPage.IsPortfolioTableLoaded()) {
                List<String> reportingOptions = Arrays.asList("EU Taxonomy", "SFDR PAIs");
                onDemandAssessmentPage.ValidateReportingOptions(reportingOptions);
                assertTestCase.assertTrue(!onDemandAssessmentPage.getReportingList().contains("On-Demand Assessment"), "Validate that OnDemand option is not visible");
            }
        } catch (Exception e) {
            e.printStackTrace();
            login.clickOnLogout();
            assertTestCase.assertTrue(false, "TestCase Failed - Please see stack trace for details");
        }
    }

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS})
    @Xray(test = {2681})
    public void validateExportButtonDisabledForOnDemandUserWithoutExportEntitlements() {
        LoginPage login = new LoginPage();
        try {

            login.entitlementsLogin(EntitlementsBundles.ONDEMAND_USER_WITHOUT_EXPORT_ENTITLEMENT);
            OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
            assertTestCase.assertTrue(onDemandAssessmentPage.validateOnDemandReportingLandingPage(), "Validating if landing page is On-Demand Repoting Page",2920);
            if (onDemandAssessmentPage.IsPortfolioTableLoaded() && onDemandAssessmentPage.getAvailablePortfolioCountt() > 0) {
                assertTestCase.assertTrue(onDemandAssessmentPage.isExportbuttonDisabled(), "validating that export button is disabled");
            }
        } catch (Exception e) {
            e.printStackTrace();
            login.clickOnLogout();
            assertTestCase.assertTrue(false, "TestCase Failed - Please see stack trace for details");
        }

    }

    @Test(groups = {UI, REGRESSION, ENTITLEMENTS})
    @Xray(test = {3019, 3128})
    public void validateThePopUpModelForUserWithInvalidEntitlementsCombinations() {
        LoginPage login = new LoginPage();
        try {

            PopUpPage popPage = new PopUpPage();
            EntitlementsBundles[] entitlements = {EntitlementsBundles.USER_ESG_PREDICTOR, EntitlementsBundles.USER_ESG_ESG_PREDICTOR_EXPORT, EntitlementsBundles.USER_ESG_PREDICTOR_EXPORT, EntitlementsBundles.USER_ESG_PREDICTOR_ODA, EntitlementsBundles.USER_EXPORT};

            for (EntitlementsBundles e : entitlements) {
                login.entitlementsLogin(e);
                System.out.println("------------Logged in to Check pop up box using " + e.toString() + " entitlements ------------");
                popPage.validateTheContentOfPopUp();
                System.out.println("Just clicked on Ok button and In login Page now .............");

            }
        } catch (Exception e) {
            e.printStackTrace();
            login.clickOnLogout();
            assertTestCase.assertTrue(false, "TestCase Failed - Please see stack trace for details");
        }

    }

    @Test(groups = {UI, SMOKE, REGRESSION, ENTITLEMENTS})
    @Xray(test = {3190})
    public void validateTheLandingPageForOnDemandEntitlements() {
        LoginPage login = new LoginPage();
        try {

            login.entitlementsLogin(EntitlementsBundles.USER_WITH_EUTAXONOMY_SFDR_ESG_ESGPREDICTOR_ONDEMAND_EXPORT);
            OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
            assertTestCase.assertTrue(onDemandAssessmentPage.validateOnDemandReportingLandingPage(), "Validating that landing page is On-Demand Reporting Page", 2920);
        } catch (Exception e) {
            e.printStackTrace();
            login.clickOnLogout();
            assertTestCase.assertTrue(false, "TestCase Failed - Please see stack trace for details");
        }

    }

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS})
    @Xray(test = {2923})
    public void validateViewAssessmentButtonIsEnabledForUSERWithExaustedAssessmentLimit() {
        LoginPage login = new LoginPage();
        try {
            login.entitlementsLogin(EntitlementsBundles.ODA_USER_WITH_EXHAUSTED_ASSESSMENT_LIMIT);
            OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
            assertTestCase.assertTrue(onDemandAssessmentPage.validateOnDemandReportingLandingPage(), "Validating if landing page is On-Demand Repoting Page", 2920);
            onDemandAssessmentPage.waitForPortfolioTableToLoad();
            String PortfolioName = onDemandAssessmentPage.SelectAndGetOnDemandEligiblePortfolioName();
            assertTestCase.assertTrue(onDemandAssessmentPage.isReequestAssessmentButtonDisabled(), "Validating that Request Assessment button is disabled");
           //TODO : Need to add a request in this account
            assertTestCase.assertTrue(onDemandAssessmentPage.isViewAssessmentRequestButtonDisabled(), "Validating that View Assessment Request button is enabled");
        } catch (Exception e) {
            e.printStackTrace();
            login.clickOnLogout();
            assertTestCase.assertTrue(false, "TestCase Failed - Please see stack trace for details");
        }
    }

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS, INCLUDEDAPITEST}, description = "UI | Dashboard | On-Demand | Verify if only have On-demand Entitlements")
    @Xray(test = {2694})
    public void verifyUserWithOnlyOnDemandEntitlementsTest() {
        LoginPage login = new LoginPage();
        try {
            //System.out.println("Logged out");

            login.entitlementsLogin(EntitlementsBundles.USER_WITH_ON_DEMAND_ENTITLEMENT);
            System.out.println("Logged in with only On-Demand entitlements");
            System.clearProperty("token");
            PopUpPage popUpPage = new PopUpPage();
            BrowserUtils.waitForVisibility(popUpPage.popUpHeader).isDisplayed();
            CommonAPIController apiController = new CommonAPIController();
            Response response = apiController.getEntitlementHandlerResponse();
            BrowserUtils.wait(5);
            response.then().assertThat().statusCode(200);
            JsonPath jsonPathEvaluator = response.jsonPath();
            List<String> entitlements = jsonPathEvaluator.getList("entitlements.name");
            System.out.println(entitlements);
            assertTestCase.assertTrue(entitlements.contains("ESG On-Demand Assessment"), "User with only On-Demand entitlements is verified");
           /* if (Environment.environment.equalsIgnoreCase("qa")) {
                assertTestCase.assertTrue(entitlements.contains("ESG On-Demand Assessment"), "User with only On-Demand entitlements is verified");
            } else {
                assertTestCase.assertTrue(entitlements.contains("On-Demand Reporting"), "User with only On-Demand entitlements is verified");
            }*/

            //PopUpPage popUpPage = new PopUpPage();
            popUpPage.validateTheContentOfPopUp();
        } catch (Exception e) {
            e.printStackTrace();
            login.clickOnLogout();
            assertTestCase.assertTrue(false, "TestCase Failed - Please see stack trace for details");
        }

    }

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS, INCLUDEDAPITEST}, description = "UI | Dashboard | On-Demand | Verify if user only have Predicted Entitlement")
    @Xray(test = {3004})
    public void verifyUserWithOnlyPredictedEntitlementTest() {
        LoginPage login = new LoginPage();
        try {

            login.entitlementsLogin(EntitlementsBundles.USER_WITH_SCORE_PREDICTOR_ENTITLEMENT);
            System.out.println("Logged in with only Predicted Score entitlements");
            System.clearProperty("token");
            PopUpPage popUpPage = new PopUpPage();
            BrowserUtils.waitForVisibility(popUpPage.popUpHeader).isDisplayed();
            CommonAPIController apiController = new CommonAPIController();
            Response response = apiController.getEntitlementHandlerResponse();
            BrowserUtils.wait(5);
            response.then().assertThat().statusCode(200);
            JsonPath jsonPathEvaluator = response.jsonPath();
            List<String> entitlements = jsonPathEvaluator.getList("entitlements.name");
            System.out.println(entitlements);
            assertTestCase.assertTrue(entitlements.contains("Score Predictor: ESG"), "User with only Predicted entitlements is verified");
            // PopUpPage popUpPage = new PopUpPage();
            popUpPage.validateTheContentOfPopUp();
        } catch (Exception e) {
            e.printStackTrace();
            login.clickOnLogout();
            assertTestCase.assertTrue(false, "TestCase Failed - Please see stack trace for details");
        }

    }

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS, INCLUDEDAPITEST}, description = "UI | Dashboard | On-Demand | Verify if user only have 'Corporates ESG Data and Scores' Entitlement")
    @Xray(test = {3116})
    public void verifyUserWithOnlyCorporatesESGDataAndScoresEntitlementTest() {
        LoginPage login = new LoginPage();
        try {

            login.entitlementsLogin(EntitlementsBundles.USER_WITH_CORPORATES_ESG_DATA_AND_SCORES_ENTITLEMENT);
            System.out.println("Logged in with only Corporates ESG Data and Scores entitlements");
            System.clearProperty("token");
            PopUpPage popUpPage = new PopUpPage();
            BrowserUtils.waitForVisibility(popUpPage.popUpHeader).isDisplayed();
            CommonAPIController apiController = new CommonAPIController();
            Response response = apiController.getEntitlementHandlerResponse();
            BrowserUtils.wait(5);
            response.then().assertThat().statusCode(200);
            JsonPath jsonPathEvaluator = response.jsonPath();
            List<String> entitlements = jsonPathEvaluator.getList("entitlements.name");
            System.out.println(entitlements);
            assertTestCase.assertTrue(entitlements.contains("Corporates ESG Data and Scores"),
                    "User with only Corporates ESG Data and Scores entitlements is verified");
            // PopUpPage popUpPage = new PopUpPage();
            popUpPage.validateTheContentOfPopUp();
        } catch (Exception e) {
            e.printStackTrace();
            login.clickOnLogout();
            assertTestCase.assertTrue(false, "TestCase Failed - Please see stack trace for details");
        }

    }

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS}, description = "UI | On-Demand Assessment | Verify User is able to Submit/Un-submit Assessment Based on the Limit")
    @Xray(test = {3073, 2944, 2662})
    public void verifyAssessmentSubmissionBasedOnTheLimit() {
        LoginPage login = new LoginPage();
        try {

            login.entitlementsLogin(EntitlementsBundles.ODA_ESG_PREDICTOR_DATA_ENTITLEMENT);

            System.out.println("Logged in with On-Demand Assessment, ESG Predictor and Data entitlements");

            OnDemandAssessmentPage ODAPage = new OnDemandAssessmentPage();
            //onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);
            //ODAPage.navigateToPageFromMenu("reportingservice","On-Demand Reporting");
            //ODAPage.navigateToReportingService("On-Demand Assessment");
            BrowserUtils.waitForVisibility(ODAPage.portfolioNamesList, 30);

            String portfolioName = "500 predicted portfolio";
            if (!ODAPage.verifyPortfolio(portfolioName)) {
                ODAPage.uploadPortfolio(portfolioName.replaceAll(" ", ""));
            }
            BrowserUtils.waitForVisibility(ODAPage.portfolioNamesList, 15);
            assertTestCase.assertTrue(ODAPage.verifyPortfolio(portfolioName), "Portfolio is available");
            ODAPage.selectPortfolio(portfolioName);
            ODAPage.clickonOnRequestAssessmentButton();
            int remainingAssessmentLimit = ODAPage.getRemainingAssessmentLimit();
            assertTestCase.assertTrue(remainingAssessmentLimit > 1, "Remaining assessment limit is verified");
            //onDemandAssessmentPage.goToSendRequestPage(portfolioName);
            ODAPage.clickReviewAndSendRequestButton();
            ODAPage.selectFilter("No Request Sent");
            if (ODAPage.getNumberOfEmailInputs() <= remainingAssessmentLimit) {
                System.out.println("There is not enough assessments to remove. There should be at least" + (remainingAssessmentLimit + 1) + " assessments");
            }
            assertTestCase.assertTrue(ODAPage.getNumberOfEmailInputs() >= remainingAssessmentLimit + 1, "Number of email inputs is verified");
            ODAPage.RemoveRequests(remainingAssessmentLimit + 1);
            Faker faker = new Faker();
            for (int i = 0; i < remainingAssessmentLimit + 1; i++) {
                String email = "qatest" + faker.number().digits(4) + "@moodystest.com";
                ODAPage.enterEmail(email, i);
            }
            assertTestCase.assertFalse(ODAPage.btnConfirmRequest.isEnabled(), "Confirm request button is disabled");

            ODAPage.RemoveRequests(remainingAssessmentLimit);
            assertTestCase.assertTrue(ODAPage.btnConfirmRequest.isEnabled(), "Confirm request button is enabled");
            ODAPage.clickConfirmRequest();
            ODAPage.verifyConfirmRequestPopup("Cancel");

            ODAPage.RemoveRequests(remainingAssessmentLimit - 1);
            assertTestCase.assertTrue(ODAPage.btnConfirmRequest.isEnabled(), "Confirm request button is enabled");
            ODAPage.clickConfirmRequest();
            ODAPage.verifyConfirmRequestPopup("Cancel");
        } catch (Exception e) {
            e.printStackTrace();
            login.clickOnLogout();
            assertTestCase.assertTrue(false, "TestCase Failed - Please see stack trace for details");
        }
    }

    @Test(groups = {UI, REGRESSION,ENTITLEMENTS})
    @Xray(test = {2819, 2766,2879})
    public void verifyZeroAssessmentRemaining() {
        LoginPage login = new LoginPage();
        try {
            login.entitlementsLogin(EntitlementsBundles.USER_WITH_ZERO_ASSESSMENT_AVAILABLE);
            OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
            String portfolioName = "500 predicted portfolio";
            onDemandAssessmentPage.waitForPortfolioTableToLoad();
            if (!onDemandAssessmentPage.verifyPortfolio(portfolioName)) {
                onDemandAssessmentPage.uploadPortfolio(portfolioName.replaceAll(" ", ""));
            }
            int remainingAssessmentLimit = onDemandAssessmentPage.getRemainingAssessmentLimit();
            if (remainingAssessmentLimit>0){
                onDemandAssessmentPage.selectPortfolio(portfolioName);
                onDemandAssessmentPage.clickonOnRequestAssessmentButton();
                onDemandAssessmentPage.clickReviewAndSendRequestButton();
                onDemandAssessmentPage.selectFilter("No Request Sent");
                onDemandAssessmentPage.RemoveRequests(remainingAssessmentLimit);
                Faker faker = new Faker();
                for (int i = 0; i < remainingAssessmentLimit; i++) {
                    String email = "qatest" + faker.number().digits(4) + "@moodystest.com";
                    onDemandAssessmentPage.enterEmail(email, i);
                }
                onDemandAssessmentPage.clickConfirmRequest();
                onDemandAssessmentPage.verifyConfirmRequestPopup("Proceed");
                onDemandAssessmentPage.clickESCkey();
            }
            onDemandAssessmentPage.waitForPortfolioTableToLoad();
            System.out.println("Logged in to Check 0 Remaining Assessment....");
            assertTestCase.assertTrue(!onDemandAssessmentPage.isRequestAssessmentButtonEnabled(), "The request assessment button is disabled for accounts with 0 remaining requests.");
            onDemandAssessmentPage.verifyZeroAssessmentRemainingForOnDemand();
           onDemandAssessmentPage.selectPortfolio(portfolioName);
           onDemandAssessmentPage.clickonViewAssessmentRequestButton();

        } catch (Exception e) {
            e.printStackTrace();
            login.clickOnLogout();
            assertTestCase.assertTrue(false, "TestCase Failed - Please see stack trace for details");
        }
    }

    @Test(groups ={REGRESSION, UI, SMOKE, ENTITLEMENTS})
    @Xray(test = {2918, 2930})
    public void validateDashboardAndPortfolioAnalysisNotPresentInGlobalMenu() {
        LoginPage login = new LoginPage();
        try {

            login.entitlementsLogin(EntitlementsBundles.USER_WITH_ONLYONDEMAND_ENTITLEMENT_FIRSTTIMEUSER);
            OnDemandAssessmentPage odaPage = new OnDemandAssessmentPage();
            BrowserUtils.wait(5);
            odaPage.clickOnMenuButton();
            odaPage.validateDashboardTabNotPresentFromGlobalMenu("Climate Dashboard");
            odaPage.validateDashboardTabNotPresentFromGlobalMenu("Climate Portfolio Analysis");
            odaPage.validateSearchButtonNotDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            login.clickOnLogout();
            assertTestCase.assertTrue(false, "TestCase Failed - Please see stack trace for details");


        }
    }

    @Test(groups ={REGRESSION, UI,ENTITLEMENTS})
    @Xray(test = {2677})
    public void validateCalculationsIsVisibleForEntitlements() {
        LoginPage login = new LoginPage();
        try {

            OnDemandAssessmentPage odaPage = new OnDemandAssessmentPage();
            EntitlementsBundles[] entitlements = {EntitlementsBundles.USER_CLIMATE_ESG_ESG_PREDICTOR_EXPORT, EntitlementsBundles.USER_CLIMATE_ESG};

            for (EntitlementsBundles e : entitlements) {
                login.entitlementsLogin(e);
                System.out.println("------------Logged in to Check Calculations tab in Global Menu using " + e.toString() + " entitlements ------------");
                odaPage.clickOnMenuButton();
                odaPage.validateAnyTabPresentInGlobalMenu("Calculations");
                odaPage.clickOnLogOutButton();

            }
        } catch (Exception e) {
            e.printStackTrace();
            login.clickOnLogout();
            assertTestCase.assertTrue(false, "TestCase Failed - Please see stack trace for details");
        }
    }

    @Test(groups ={REGRESSION, UI, SMOKE,ENTITLEMENTS})
    @Xray(test = {3133})
    public void validateOnDemandSfdrEuTaxonomyForEntitlements() {
        LoginPage login = new LoginPage();
        try {

            OnDemandAssessmentPage odaPage = new OnDemandAssessmentPage();
            EntitlementsBundles[] entitlements ;
            if (Environment.environment.equalsIgnoreCase("prod")){
                entitlements = new EntitlementsBundles[]{EntitlementsBundles.USER_SFDR_ESG_ESG_PREDICTOR_ODA};
            }
            else {
                entitlements = new EntitlementsBundles[]{EntitlementsBundles.USER_SFDR_ESG_ESG_PREDICTOR_ODA,
                        EntitlementsBundles.USER_EUTAXONOMY_SFDR_ESG_ESG_PREDICTOR_ODA_EXCEL,
                        EntitlementsBundles.USER_SFDR_ESG_ESG_PREDICTOR_ODA_EXCEL,
                        EntitlementsBundles.USER_EUTAXONOMY_ESG_ESG_PREDICTOR_ODA,
                        EntitlementsBundles.USER_EUTAXONOMY_ESG_ESG_PREDICTOR_ODA_EXCEL,
                        EntitlementsBundles.USER_CLIMATE_ESG_ESG_PREDICTOR_EXPORT,
                        EntitlementsBundles.USER_CLIMATE_ESG,
                        EntitlementsBundles.USER_ESG_ESG_PREDICTOR_ODA};
            }
            for (EntitlementsBundles e : entitlements) {
                System.out.println("------------Logged in to Check OnDemand Reporting tab in Global Menu using " + e.toString() + " entitlements ------------");
                odaPage.validateReportingOptionsInReportingPage(login, e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            login.clickOnLogout();
            assertTestCase.assertTrue(false, "TestCase Failed - Please see stack trace for details");

        }
    }

    @Test(groups = {UI, REGRESSION,ENTITLEMENTS}, dataProvider = "entityWithEsgDataOnly-DP", dataProviderClass = EntityWithEsgDataOnlyDataProviders.class)
    @Xray(test = {2796})
    public void ValidateNoDataForEntitiesWithEsgDataOnly(String... entity) {
        LoginPage login = new LoginPage();
        try{

        login.entitlementsLogin(EntitlementsBundles.USER_CLIMATE_ESG);
        System.out.println("---------------Logged back in using climate and esg entitlements--------------------");
        OnDemandAssessmentPage odaPage = new OnDemandAssessmentPage();
        odaPage.clickOnSearchButton();
        odaPage.searchForEntitities(entity[0]);
        //assertTestCase.assertTrue(!entity.equals(odaPage.searchResultLineOne.getText()), "Validating that " + entity + " which is an Entity with ESG data only is not returned or suggested in search option : Status Done");

        odaPage.validateEntitiesWithOnlyEsgDataDontShowInSearch(entity[0]);
        } catch (Exception e) {
            e.printStackTrace();
            login.clickOnLogout();
            assertTestCase.assertTrue(false, "TestCase Failed - Please see stack trace for details");
        }

        }

    @Test(groups = {UI, REGRESSION,ENTITLEMENTS}, dataProvider = "entityWithEsgDataOnly-DP", dataProviderClass = EntityWithEsgDataOnlyDataProviders.class)
    @Xray(test = {2796})
    public void validateOnlyESGEntitiesAreNotPopulatingUnderSearch(String data) {
        LoginPage login = new LoginPage();
        try {

            login.entitlementsLogin(EntitlementsBundles.USER_CLIMATE_ESG_ESG_PREDICTOR_EXPORT);
            System.out.println("---------------Logged back in using climate esg- esg predictor and export entitlements--------------------");
            OnDemandAssessmentPage odaPage = new OnDemandAssessmentPage();

            odaPage.clickOnSearchButton();

            odaPage.searchForEntitities(data);
            odaPage.validateEntitiesWithOnlyEsgDataDontShowInSearch(data);


        } catch (Exception e) {
            e.printStackTrace();
            login.clickOnLogout();
            assertTestCase.assertTrue(false, "TestCase Failed - Please see stack trace for details");

        }
    }

    @Test(groups = {REGRESSION, UI, COMMON}, description = "UI | On-Demand Reporting | On-Demand Assessment | Verify Download button is not displayed if Export entitlement is disabled")
    @Xray(test = {2917})
    public void verifyDownloadButtonNotDisplayedTest() {
        LoginPage login = new LoginPage();
        try {

            login.entitlementsLogin(EntitlementsBundles.ODA_ESG_PREDICTOR_DATA_ENTITLEMENT);
            System.out.println("---------------Logged back in using ODA ESG Predictor entitlements--------------------");
            OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
            //onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
            onDemandAssessmentPage.waitForPortfolioTableToLoad();
            String portfolioName = "500 predicted portfolio";
            if(!onDemandAssessmentPage.verifyPortfolio(portfolioName)){
                onDemandAssessmentPage.uploadPortfolio(portfolioName.replaceAll(" ",""));
            }
            //onDemandAssessmentPage.viewDetailForPortfolio(portfolioName);

            onDemandAssessmentPage.verifyDetailsPanel(false);

        } catch (Exception e) {
            e.printStackTrace();
            login.clickOnLogout();
            assertTestCase.assertTrue(false, "TestCase Failed - Please see stack trace for details");

        }

    }

}