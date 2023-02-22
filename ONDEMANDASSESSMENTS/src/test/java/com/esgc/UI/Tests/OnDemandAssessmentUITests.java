package com.esgc.UI.Tests;

import com.esgc.UI.TestBases.UITestBase;

import com.esgc.UI.Pages.LoginPage;
import com.esgc.UI.Pages.OnDemandAssessmentPage;


import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.testng.Assert;
import org.testng.annotations.Test;
import static com.esgc.Utilities.Groups.*;

public class OnDemandAssessmentUITests extends UITestBase {

    @Test(groups = {REGRESSION,UI,COMMON })
    @Xray(test = {11985,12001,12002,12011,12054,12092})
    public void validateOnDemandAssessmentRequest() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);
        onDemandAssessmentPage.clickMenu();
        BrowserUtils.wait(5);
        onDemandAssessmentPage.clickonOnOndemandMenuOption();
        onDemandAssessmentPage.validateOnDemandPageHeader();
        onDemandAssessmentPage.goToSendRequestPage(portfolioName);
        onDemandAssessmentPage.clickReviewAndSendRequestButton();

        onDemandAssessmentPage.verifyCompaniesDetails();
        onDemandAssessmentPage.verifyShowFilterOptions();

        String CompaniesCount = onDemandAssessmentPage.confirmRequestAndGetCompaniesCount();

        onDemandAssessmentPage.validateProceedOnConfirmRequestPopup(CompaniesCount);
        onDemandAssessmentPage.clickCancelButtonAndValidateRequestPage();

        onDemandAssessmentPage.confirmRequestAndGetCompaniesCount();
        onDemandAssessmentPage.clickProceedOnConfirmRequestPopup();

        onDemandAssessmentPage.validateOnDemandPageHeader();

    }

    @Test(groups = {"regression", "ui"})
    @Xray(test = {12808})
    public void validateExitFromRequestOnDemandAssessmentPage() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);
        onDemandAssessmentPage.clickMenu();
        BrowserUtils.wait(5);
        onDemandAssessmentPage.clickonOnOndemandMenuOption();
        onDemandAssessmentPage.validateOnDemandPageHeader();

        onDemandAssessmentPage.sendESCkey();
        BrowserUtils.wait(2);
        Assert.assertTrue(onDemandAssessmentPage.verifyMainPageHeader("Moody's ESG360: Dashboard"), "Global Header Title is not matched for Dashboard");

        onDemandAssessmentPage.clickMenu();
        onDemandAssessmentPage.clickonOnOndemandMenuOption();
        onDemandAssessmentPage.validateOnDemandPageHeader();

        onDemandAssessmentPage.clickESCkey();
        BrowserUtils.wait(2);
        Assert.assertTrue(onDemandAssessmentPage.verifyMainPageHeader("Moody's ESG360: Dashboard"), "Global Header Title is not matched for Dashboard");
    }

    @Test(groups = {REGRESSION,UI })
    @Xray(test = {12054,12810,12811,12812})
    public void validateErrorMessageOfEmailFieldAndExit() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);
        onDemandAssessmentPage.clickMenu();
        BrowserUtils.wait(5);
        onDemandAssessmentPage.clickonOnOndemandMenuOption();
        onDemandAssessmentPage.validateOnDemandPageHeader();

        onDemandAssessmentPage.goToSendRequestPage(portfolioName);
        onDemandAssessmentPage.clickReviewAndSendRequestButton();

        onDemandAssessmentPage.verifyConfirmEmailAlert();

        onDemandAssessmentPage.sendESCkey();
        BrowserUtils.wait(2);
        onDemandAssessmentPage.validateOnDemandPageHeader();

        onDemandAssessmentPage.clickReviewAndSendRequestButton();;
        BrowserUtils.wait(3);
        onDemandAssessmentPage.clickESCkey();
        BrowserUtils.wait(2);
        onDemandAssessmentPage.validateOnDemandPageHeader();
    }

    @Test(groups = {REGRESSION,UI })
    @Xray(test = {12010})
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

    @Test(groups = {REGRESSION,UI , SMOKE})
    @Xray(test = {12440,12456})
    public void verifyHeaderAndFilterFunctionality() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);
        onDemandAssessmentPage.clickMenu();
        BrowserUtils.wait(5);
        onDemandAssessmentPage.clickonOnOndemandMenuOption();
        onDemandAssessmentPage.validateOnDemandPageHeader();

        onDemandAssessmentPage.goToSendRequestPage(portfolioName);
        onDemandAssessmentPage.onDemandCoverageHeaderValidation(portfolioName);
        assertTestCase.assertTrue(onDemandAssessmentPage.isCancelButtonAvailable(),"Validate if Cancel button is available");
        assertTestCase.assertTrue(onDemandAssessmentPage.isReviewButtonAvailable(),"Validate if Review button is available");

        //Validating Predicted Score Graph Slider
        onDemandAssessmentPage.validatePredictedScore();
        onDemandAssessmentPage.validateLocation();
        onDemandAssessmentPage.validateSector();
        onDemandAssessmentPage.validateSize();


    }

    @Test(groups = {REGRESSION,UI , SMOKE})
    @Xray(test = {12455})
    public void verifyPageNavigation() {


        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);

        onDemandAssessmentPage.clickMenu();
        BrowserUtils.wait(5);
        onDemandAssessmentPage.clickonOnOndemandMenuOption();
        onDemandAssessmentPage.validateOnDemandPageHeader();

        onDemandAssessmentPage.clickOnESCButton();
        onDemandAssessmentPage.clickOnDemandPagelinkFromDashboardPage();
        onDemandAssessmentPage.validateOnDemandPageHeader();

    }



    @Test(groups = {REGRESSION,UI , SMOKE})
    @Xray(test = {12703})
    public void verifyFilterCriteriaWithAndORLogic() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);
        onDemandAssessmentPage.clickMenu();
        BrowserUtils.wait(5);
        onDemandAssessmentPage.clickonOnOndemandMenuOption();
        onDemandAssessmentPage.validateOnDemandPageHeader();

        onDemandAssessmentPage.goToSendRequestPage(portfolioName);

        onDemandAssessmentPage.validateAndORLogic();



    }





}
