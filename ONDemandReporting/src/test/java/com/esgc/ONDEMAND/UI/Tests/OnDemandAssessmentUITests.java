package com.esgc.ONDEMAND.UI.Tests;

import com.esgc.Common.API.Controllers.CommonAPIController;
import com.esgc.Common.UI.Pages.LoginPage;
import com.esgc.Common.UI.TestBases.UITestBase;
import com.esgc.ONDEMAND.API.Controllers.OnDemandFilterAPIController;
import com.esgc.ONDEMAND.DB.DBQueries.OnDemandAssessmentQueries;
import com.esgc.ONDEMAND.UI.Pages.OnDemandAssessmentPage;
import com.esgc.Pages.Page404;
import com.esgc.Utilities.*;
import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.Groups.*;

public class OnDemandAssessmentUITests extends UITestBase {
Faker faker = new Faker();
    @Test(groups = {REGRESSION, UI, COMMON})
    @Xray(test = {11985, 12001, 12002, 12011, 12054, 12092, 12822, 12824,14103,14105})
    public void validateOnDemandAssessmentRequest() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        assertTestCase.assertTrue(onDemandAssessmentPage.isReequestAssessmentButtonDisabled(), "Validating that Request Assessment button is disabled");
        assertTestCase.assertTrue(onDemandAssessmentPage.isViewAssessmentRequestButtonDisabled(), "Validating that View Assessment Request button is disabled");
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
        if(!onDemandAssessmentPage.verifyPortfolio(portfolioName)) {
            onDemandAssessmentPage.uploadPortfolio(portfolioName.replaceAll(" ", ""));
        }
        onDemandAssessmentPage.selectPortfolioOptionByName(portfolioName);
        BrowserUtils.wait(5);
        assertTestCase.assertTrue(!onDemandAssessmentPage.isReequestAssessmentButtonDisabled(), "Validating that Request Assessment button is enabled");
        assertTestCase.assertTrue(!onDemandAssessmentPage.isViewAssessmentRequestButtonDisabled(), "Validating that View Assessment Request button is enabled");
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
    @Xray(test = {12808})
    public void validateExitFromRequestOnDemandAssessmentPage() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        assertTestCase.assertTrue(onDemandAssessmentPage.isReequestAssessmentButtonDisabled(), "Validating that Request Assessment button is disabled");
        assertTestCase.assertTrue(onDemandAssessmentPage.isViewAssessmentRequestButtonDisabled(), "Validating that View Assessment Request button is disabled");
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
        if(!onDemandAssessmentPage.verifyPortfolio(portfolioName)) {
            onDemandAssessmentPage.uploadPortfolio(portfolioName.replaceAll(" ", ""));
        }
        onDemandAssessmentPage.selectPortfolioOptionByName(portfolioName);
        BrowserUtils.wait(5);
        assertTestCase.assertTrue(!onDemandAssessmentPage.isReequestAssessmentButtonDisabled(), "Validating that Request Assessment button is enabled");
        assertTestCase.assertTrue(!onDemandAssessmentPage.isViewAssessmentRequestButtonDisabled(), "Validating that View Assessment Request button is enabled");
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.validateOnDemandPageHeader();
        onDemandAssessmentPage.sendESCkey();
        BrowserUtils.wait(2);
        Assert.assertTrue(onDemandAssessmentPage.validateOnDemandReportingLandingPage(), "User landed on Reporting portal page");
    }

    @Test(groups = {REGRESSION, UI})
    @Xray(test = {12054, 12810, 12811, 12812,12455})
    public void validateErrorMessageOfEmailFieldAndExit() {

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

    @Test(groups = {REGRESSION, UI, SMOKE})
    @Xray(test = {12440, 12456})
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

       // onDemandAssessmentPage.goToSendRequestPage(portfolioName);
        onDemandAssessmentPage.onDemandCoverageHeaderValidation(portfolioName);
        assertTestCase.assertTrue(onDemandAssessmentPage.isCancelButtonAvailable(), "Validate if Cancel button is available");
        assertTestCase.assertTrue(onDemandAssessmentPage.isReviewButtonAvailable(), "Validate if Review button is available");

        //Validating Predicted Score Graph Slider
        onDemandAssessmentPage.validatePredictedScore();
        onDemandAssessmentPage.validateLocation();
        onDemandAssessmentPage.validateSector();
        onDemandAssessmentPage.validateSize();


    }

    /*@Test(groups = {REGRESSION, UI, SMOKE})
    @Xray(test = {12455})
    public void verifyPageNavigation() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        onDemandAssessmentPage.selectPortfolioOptionByName(portfolioName);
        BrowserUtils.wait(5);
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.validateOnDemandPageHeader();
        onDemandAssessmentPage.sendESCkey();
        onDemandAssessmentPage.clickOnDemandPagelinkFromDashboardPage();
        onDemandAssessmentPage.validateOnDemandPageHeader();

    }*/


    @Test(groups = {REGRESSION, UI, SMOKE})
    @Xray(test = {12703})
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

    // Disabling below method as all three test cases have been cancelled
   /* @Test(groups = {REGRESSION, UI})
    @Xray(test = {12826, 12974, 12828})
    public void validateFirstTimeUser() {

        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        String inputFile = ConfigurationReader.getProperty("OnDemandPortfolio");
        OnDemandFilterAPIController apiController = new OnDemandFilterAPIController();
        String PortfolioId = apiController.uploadPortfolio(inputFile);
        Driver.getDriver().navigate().refresh();

        onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(inputFile);
        onDemandAssessmentPage.validateDashboardPageButtonForOnDemand();
        onDemandAssessmentPage.validateDashboardPageButtonCoverage(PortfolioId);

        onDemandAssessmentPage.clickMenu();
        BrowserUtils.wait(5);
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.validateOnDemandPageHeader();
        assertTestCase.assertTrue(onDemandAssessmentPage.isReviewButtonAvailable(), " Validating first time user landed on Filter page");

        onDemandAssessmentPage.clickReviewAndSendRequestButton();

        onDemandAssessmentPage.confirmRequestAndGetCompaniesCount(Environment.DATA_USERNAME);
        onDemandAssessmentPage.clickProceedOnConfirmRequestPopup();

        onDemandAssessmentPage.validateErrormessage();

        onDemandAssessmentPage.clickESCkey();

        test.info("Validating second time user is going back to filter screen when there was an error in first time submission");
        System.out.println("Validating second time user is going back to filter screen when there was an error in first time submission");
        Driver.getDriver().navigate().refresh();

        onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(inputFile);
        onDemandAssessmentPage.validateDashboardPageButtonForOnDemand();

        onDemandAssessmentPage.clickMenu();
        BrowserUtils.wait(5);
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.validateOnDemandPageHeader();
        assertTestCase.assertTrue(onDemandAssessmentPage.isReviewButtonAvailable(), " Validating first time user landed on Filter page");
        System.out.println("Validated all first time user test");
        apiController.deletePortfolio(PortfolioId);

    }*/

    @Test(groups = {REGRESSION, UI, SMOKE})
    @Xray(test = {13727, 13763, 13787, 13788, 14201, 14202})
    public void verifyGeneratingOnDemandAssessmentReportWithUniqueEmailAddress() {

        CommonAPIController apiController = new CommonAPIController();
        System.clearProperty("token");
        Response response = apiController.getEntitlementHandlerResponse();
        response.then().assertThat().statusCode(200);
        JsonPath jsonPathEvaluator = response.jsonPath();
        List<String> entitlements = jsonPathEvaluator.getList("entitlements.name");
        System.out.println(entitlements);
        List<String> entitlementDataRoles = Arrays.asList("ESG On-Demand Assessment", "Corporates ESG Data and Scores", "Score Predictor: ESG");
        assertTestCase.assertTrue(entitlements.containsAll(entitlementDataRoles), "Entitlements are not available");

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        //onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);
        BrowserUtils.wait(2);
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
       // onDemandAssessmentPage.navigateToPageFromMenu("reportingservice","On-Demand Reporting");
        //BrowserUtils.waitForVisibility(onDemandAssessmentPage.portfolioNamesList, 15);
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
        onDemandAssessmentPage.verifyMethodologies();

        if(!onDemandAssessmentPage.verifyPortfolio(portfolioName)) {
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
        assertTestCase.assertTrue(onDemandAssessmentPage.getNumberOfEmailInputs()==2, "Number of email inputs is verified");
        String email = "qatest_"+faker.number().digits(4)+"@moodystest.com";
        onDemandAssessmentPage.enterEmail(email,0);
        onDemandAssessmentPage.enterEmail(email,1);
        onDemandAssessmentPage.verifyDuplicateEmailNotification();
        assertTestCase.assertFalse(onDemandAssessmentPage.btnConfirmRequest.isEnabled(), "Confirm request button is disabled");
        onDemandAssessmentPage.enterEmail("qatest"+faker.number().digits(4)+"@moodystest.com",1);
        assertTestCase.assertTrue(onDemandAssessmentPage.btnConfirmRequest.isEnabled(), "Confirm request button is enabled");
        onDemandAssessmentPage.clickConfirmRequest();
        onDemandAssessmentPage.verifyConfirmRequestPopup("Cancel");
    }

    @Test(groups = {REGRESSION, UI, COMMON})
    @Xray(test = {13987, 14002})
    public void OnDemandAssessmentPortfolioTableValidations() throws ParseException {
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
        // ESGCA - 13987 Verify that for a portfolio having 0% On Demand Assessment eligible coverage , request assessment button is disabled
        onDemandAssessmentPage.SelectPortfolioWithZeroOnDemandAssessmentEligibility();
        assertTestCase.assertTrue(onDemandAssessmentPage.isViewAssessmentRequestButtonDisabled(), "Validating that View Assessment Request button is disabled");
        // ESGCA - 14002 - Verify the sorting of the Portfolios in the portfolio table
        onDemandAssessmentPage.ValidateSortingOnLastUpdateColumn();
    }


    @Test(groups = {REGRESSION, UI, COMMON, SMOKE}, description = "UI | On-Demand Reporting | On-Demand Assessments | Verify Different ways to download the portfolio/export file")
    @Xray(test = {13691})
    public void verifyDifferentWaysToDownloadThePortfolioTest() {
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
        // ESGCA - 13987 Verify that for a portfolio having 0% On Demand Assessment eligible coverage , request assessment button is disabled
        System.out.println(BrowserUtils.getElementsText(onDemandAssessmentPage.portfolioNamesList));
        onDemandAssessmentPage.verifyDetailsPanel();
    }

}
