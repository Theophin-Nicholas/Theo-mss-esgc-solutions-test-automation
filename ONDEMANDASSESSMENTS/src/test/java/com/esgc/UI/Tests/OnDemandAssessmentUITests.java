package com.esgc.UI.Tests;

import com.esgc.UI.TestBases.UITestBase;

import com.esgc.UI.Pages.LoginPage;
import com.esgc.UI.Pages.OnDemandAssessmentPage;


import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import static com.esgc.Utilities.Groups.*;

public class OnDemandAssessmentUITests extends UITestBase {

    @Test(groups = {REGRESSION,UI })
    @Xray(test = {11985,12001,12002,12011,12054})
    public void validateOnDemandAssessmentRequest() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();

        onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);

        onDemandAssessmentPage.clickMenu();
        BrowserUtils.wait(5);
        BrowserUtils.waitForVisibility(onDemandAssessmentPage.onDemandAssessmentRequest,60).click();
        assertTestCase.assertEquals(onDemandAssessmentPage.menuOptionPageHeader.getText(), "Moody's ESG360: Request On-Demand Assessment", "Moody's ESG360: Request On-Demand Assessment page verified");

        onDemandAssessmentPage.clickReviewAndSendRequestButton();
        onDemandAssessmentPage.verifyCompaniesDetails();
        onDemandAssessmentPage.verifyShowFilterOptions();


        onDemandAssessmentPage.confirmRequest();
        onDemandAssessmentPage.clickProceedOnConfirmRequestPopup();

        assertTestCase.assertEquals(onDemandAssessmentPage.menuOptionPageHeader.getText(), "Moody's ESG360: Request On-Demand Assessment", "Moody's ESG360: Request On-Demand Assessment page verified");

    }

    @Test(groups = {"regression", "ui"})
    @Xray(test = {12808})
    public void validateExitFromRequestOnDemandAssessmentPage() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);
        BrowserUtils.wait(10);
        BrowserUtils.waitForPageToLoad(50);

        onDemandAssessmentPage.clickMenu();
        onDemandAssessmentPage.onDemandAssessmentRequest.click();
        assertTestCase.assertEquals(onDemandAssessmentPage.menuOptionPageHeader.getText(), "Moody's ESG360: Request On-Demand Assessment", "Moody's ESG360: Request On-Demand Assessment page verified");

        onDemandAssessmentPage.sendESCkey();
        BrowserUtils.wait(2);
        Assert.assertTrue(onDemandAssessmentPage.verifyMainPageHeader("Moody's ESG360: Dashboard"), "Global Header Title is not matched for Dashboard");

        onDemandAssessmentPage.clickMenu();
        onDemandAssessmentPage.onDemandAssessmentRequest.click();
        assertTestCase.assertEquals(onDemandAssessmentPage.menuOptionPageHeader.getText(), "Moody's ESG360: Request On-Demand Assessment", "Moody's ESG360: Request On-Demand Assessment page verified");

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
       // BrowserUtils.wait(5);
        BrowserUtils.wait(10);
        BrowserUtils.waitForPageToLoad(50);

        onDemandAssessmentPage.clickMenu();
        BrowserUtils.waitForClickablility(onDemandAssessmentPage.onDemandAssessmentRequest, 60).click();
        assertTestCase.assertEquals(onDemandAssessmentPage.menuOptionPageHeader.getText(), "Moody's ESG360: Request On-Demand Assessment", "Moody's ESG360: Request On-Demand Assessment page verified");

        onDemandAssessmentPage.clickReviewAndSendRequestButton();
        String emailConfirmMessage = "Please confirm email addresses. The listed contacts will receive an email prompting them to complete an ESG assessment questionnaire.";
        onDemandAssessmentPage.verifyConfirmEmailAlert(emailConfirmMessage);

        //onDemandAssessmentPage.sendESCkey();
        BrowserUtils.ActionKeyPress(Keys.ESCAPE);
        BrowserUtils.wait(2);
        assertTestCase.assertEquals(onDemandAssessmentPage.menuOptionPageHeader.getText(), "Moody's ESG360: Request On-Demand Assessment", "Moody's ESG360: Request On-Demand Assessment page verified")

        onDemandAssessmentPage.clickReviewAndSendRequestButton();
        onDemandAssessmentPage.clickESCkey();
        BrowserUtils.wait(2);
        assertTestCase.assertEquals(onDemandAssessmentPage.menuOptionPageHeader.getText(), "Moody's ESG360: Request On-Demand Assessment", "Moody's ESG360: Request On-Demand Assessment page verified");

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
    }

    @Test(groups = {REGRESSION,UI , SMOKE})
    @Xray(test = {12440,12456})
    public void verifyHeaderAndFilterFunctionality() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);

        onDemandAssessmentPage.clickMenu();

        BrowserUtils.waitForClickablility(onDemandAssessmentPage.onDemandAssessmentRequest,60).click();
        assertTestCase.assertEquals(onDemandAssessmentPage.menuOptionPageHeader.getText(), "Moody's ESG360: Request On-Demand Assessment", "Moody's ESG360: Request On-Demand Assessment page verified");
        onDemandAssessmentPage.onDemandCoverageHeaderValidation(portfolioName);
        onDemandAssessmentPage.isCancelButtonAvailable();
        onDemandAssessmentPage.isReviewButtonAvailable();

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
        BrowserUtils.waitForClickablility(onDemandAssessmentPage.onDemandAssessmentRequest,60).click();
        assertTestCase.assertEquals(onDemandAssessmentPage.menuOptionPageHeader.getText(), "Moody's ESG360: Request On-Demand Assessment", "Moody's ESG360: Request On-Demand Assessment page verified");

        onDemandAssessmentPage.clickOnESCButton();
        onDemandAssessmentPage.clickOnDemagPagelinkFromDashboardPage();
        assertTestCase.assertEquals(onDemandAssessmentPage.menuOptionPageHeader.getText(), "Moody's ESG360: Request On-Demand Assessment", "Moody's ESG360: Request On-Demand Assessment page verified");

    }



    @Test(groups = {REGRESSION,UI , SMOKE})
    @Xray(test = {12703})
    public void verifyFilterCriteriaWithAndORLogic() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);

        onDemandAssessmentPage.clickMenu();

        BrowserUtils.waitForClickablility(onDemandAssessmentPage.onDemandAssessmentRequest,60).click();
        assertTestCase.assertEquals(onDemandAssessmentPage.menuOptionPageHeader.getText(), "Moody's ESG360: Request On-Demand Assessment", "Moody's ESG360: Request On-Demand Assessment page verified");


        onDemandAssessmentPage.validateAndORLogic("And");



    }





}
