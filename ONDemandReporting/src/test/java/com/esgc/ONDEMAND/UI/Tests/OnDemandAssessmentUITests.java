package com.esgc.ONDEMAND.UI.Tests;

import com.esgc.Common.UI.Pages.LoginPage;
import com.esgc.Common.UI.TestBases.UITestBase;
import com.esgc.ONDEMAND.API.Controllers.OnDemandFilterAPIController;
import com.esgc.ONDEMAND.UI.Pages.OnDemandAssessmentPage;
import com.esgc.Utilities.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class OnDemandAssessmentUITests extends UITestBase {
    @Test(groups = {REGRESSION, UI, COMMON})
    @Xray(test = {11985, 12001, 12002, 12011, 12054, 12092, 12822, 12824})
    public void validateOnDemandAssessmentRequest() {

        String portfolioName = "500 predicted p";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        onDemandAssessmentPage.selectPortfolioOptionByName(portfolioName);
        BrowserUtils.wait(5);
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.clickReviewAndSendRequestButton();

        onDemandAssessmentPage.verifyCompaniesDetails();
        onDemandAssessmentPage.verifyShowFilterOptions();

        String CompaniesCount = onDemandAssessmentPage.confirmRequestAndGetCompaniesCount("qatest" + Math.random() + "@gmail.com");

        onDemandAssessmentPage.validateProceedOnConfirmRequestPopup(CompaniesCount);
        onDemandAssessmentPage.clickCancelButtonAndValidateRequestPage();

        //onDemandAssessmentPage.confirmRequestAndGetCompaniesCount("qatest" + Math.random() + "@gmail.com");
        onDemandAssessmentPage.clickProceedOnConfirmRequestPopup();

        onDemandAssessmentPage.validateOnDemandPageHeader();

    }

    @Test(groups = {REGRESSION, UI})
    @Xray(test = {12808})
    public void validateExitFromRequestOnDemandAssessmentPage() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        //onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);
        onDemandAssessmentPage.clickMenu();
        BrowserUtils.wait(5);
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.validateOnDemandPageHeader();

        onDemandAssessmentPage.sendESCkey();
        BrowserUtils.wait(2);
        Assert.assertTrue(onDemandAssessmentPage.verifyMainPageHeader("Climate Dashboard"), "Global Header Title is not matched for Dashboard");

        onDemandAssessmentPage.clickMenu();
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.validateOnDemandPageHeader();

        onDemandAssessmentPage.clickESCkey();
        BrowserUtils.wait(2);
        Assert.assertTrue(onDemandAssessmentPage.verifyMainPageHeader("Climate Dashboard"), "Global Header Title is not matched for Dashboard");
    }

    @Test(groups = {REGRESSION, UI})
    @Xray(test = {12054, 12810, 12811, 12812})
    public void validateErrorMessageOfEmailFieldAndExit() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        //onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);
        onDemandAssessmentPage.clickMenu();
        BrowserUtils.wait(5);
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.validateOnDemandPageHeader();

        onDemandAssessmentPage.goToSendRequestPage(portfolioName);
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

    @Test(groups = {REGRESSION, UI})
    @Xray(test = {12010, 12827})
    public void verifyOnDemandAssessmentRequestIsNotAvailable() {
        LoginPage login = new LoginPage();
        login.clickOnLogout();
        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK);

        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();

        BrowserUtils.wait(10);

        onDemandAssessmentPage.clickMenu();
        assertTestCase.assertFalse(onDemandAssessmentPage.isOnDemandAssessmentRequestAvailableInMenu(), "On-Demand Assessment Request option should not be available");
        login.clickOnLogout();
    }

    @Test(groups = {REGRESSION, UI, SMOKE})
    @Xray(test = {12440, 12456})
    public void verifyHeaderAndFilterFunctionality() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        //onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);
        onDemandAssessmentPage.clickMenu();
        BrowserUtils.wait(5);
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.validateOnDemandPageHeader();

        onDemandAssessmentPage.goToSendRequestPage(portfolioName);
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
    @Xray(test = {12455})
    public void verifyPageNavigation() {


        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        //onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);

        onDemandAssessmentPage.clickMenu();
        BrowserUtils.wait(5);
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.validateOnDemandPageHeader();

        onDemandAssessmentPage.clickOnESCButton();
        onDemandAssessmentPage.clickOnDemandPagelinkFromDashboardPage();
        onDemandAssessmentPage.validateOnDemandPageHeader();

    }


    @Test(groups = {REGRESSION, UI, SMOKE})
    @Xray(test = {12703})
    public void verifyFilterCriteriaWithAndORLogic() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        //onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);
        onDemandAssessmentPage.navigateToReportingService("On-Demand");
        onDemandAssessmentPage.validateOnDemandPageHeader();

        onDemandAssessmentPage.goToSendRequestPage(portfolioName);

        onDemandAssessmentPage.validateAndORLogic();


    }

    @Test(groups = {REGRESSION, UI})
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

    }

    @Test(groups = {REGRESSION, UI, SMOKE})
    @Xray(test = {})
    public void testOnDemandDetailPanel() {

        String portfolioName = "500 predicted p";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        onDemandAssessmentPage.selectPortfolioOptionByName(portfolioName);
        BrowserUtils.wait(5);
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.clickReviewAndSendRequestButton();
    }


}
