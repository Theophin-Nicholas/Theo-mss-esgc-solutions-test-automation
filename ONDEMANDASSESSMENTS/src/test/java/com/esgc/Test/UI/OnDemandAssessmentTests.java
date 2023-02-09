package com.esgc.Test.UI;

import com.esgc.Base.TestBases.UITestBase;

import com.esgc.Pages.LoginPage;
import com.esgc.Pages.OnDemandAssessmentPage;


import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class OnDemandAssessmentTests extends UITestBase {

    @Test(groups = {"regression", "ui"})
    @Xray(test = {12001,12002,12011})
    public void validateOnDemandAssessmentRequest() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);

        onDemandAssessmentPage.clickMenu();
        onDemandAssessmentPage.onDemandAssessmentRequest.click();
        assertTestCase.assertEquals(onDemandAssessmentPage.menuOptionPageHeader.getText(), "Moody's ESG360: Request On-Demand Assessment", "Moody's ESG360: Request On-Demand Assessment page verified");

        onDemandAssessmentPage.clickReviewAndSendRequestButton();
        onDemandAssessmentPage.confirmRequest();
        onDemandAssessmentPage.clickProceedOnConfirmRequestPopup();

        assertTestCase.assertEquals(onDemandAssessmentPage.menuOptionPageHeader.getText(), "Moody's ESG360: Request On-Demand Assessment", "Moody's ESG360: Request On-Demand Assessment page verified");

    }

    @Test(groups = {"regression", "ui"})
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

    @Test(groups = {"regression", "ui", "smoke"})
    @Xray(test = {12440,12456})
    public void verifyHeaderAndFilterFunctionality() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);

        onDemandAssessmentPage.clickMenu();

        BrowserUtils.waitForVisibility(onDemandAssessmentPage.onDemandAssessmentRequest,60).click();
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

    @Test(groups = {"regression", "ui", "smoke"})
    @Xray(test = {12455})
    public void verifyPageNavigation() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);

        onDemandAssessmentPage.clickMenu();
        BrowserUtils.waitForVisibility(onDemandAssessmentPage.onDemandAssessmentRequest,60).click();
        assertTestCase.assertEquals(onDemandAssessmentPage.menuOptionPageHeader.getText(), "Moody's ESG360: Request On-Demand Assessment", "Moody's ESG360: Request On-Demand Assessment page verified");

        onDemandAssessmentPage.clickOnESCButton();
        onDemandAssessmentPage.clickOnDemagPagelinkFromDashboardPage();
        assertTestCase.assertEquals(onDemandAssessmentPage.menuOptionPageHeader.getText(), "Moody's ESG360: Request On-Demand Assessment", "Moody's ESG360: Request On-Demand Assessment page verified");

    }



    @Test(groups = {"regression", "ui", "smoke"})
    @Xray(test = {12703})
    public void verifyFilterCriteriaWithAndORLogic() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);

        onDemandAssessmentPage.clickMenu();

        BrowserUtils.waitForVisibility(onDemandAssessmentPage.onDemandAssessmentRequest,60).click();
        assertTestCase.assertEquals(onDemandAssessmentPage.menuOptionPageHeader.getText(), "Moody's ESG360: Request On-Demand Assessment", "Moody's ESG360: Request On-Demand Assessment page verified");


        onDemandAssessmentPage.validateAndORLogic("And");



    }





}
