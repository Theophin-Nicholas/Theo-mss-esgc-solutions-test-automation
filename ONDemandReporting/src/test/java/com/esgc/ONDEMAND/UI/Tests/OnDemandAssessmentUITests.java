package com.esgc.ONDEMAND.UI.Tests;

import com.esgc.Common.UI.Pages.LoginPage;
import com.esgc.Common.UI.TestBases.UITestBase;
import com.esgc.ONDEMAND.API.Controllers.OnDemandFilterAPIController;
import com.esgc.ONDEMAND.UI.Pages.OnDemandAssessmentPage;

import com.esgc.ONDEMAND.UI.Pages.viewDetailPage;
import com.esgc.Utilities.*;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class OnDemandAssessmentUITests extends UITestBase {


    @Test()
    @Xray()

    public void issamTest(){


        String portfolioName = "500 predicted p";
        String portfolioName1 = "SFDRPortfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        onDemandAssessmentPage.selectPortfolioOptionByName(portfolioName1);
        BrowserUtils.wait(2);
        assertTestCase.assertTrue(!onDemandAssessmentPage.buttonRequestAssessment.isEnabled(), "Verify that the request assessment button is disabled for a portfolio not on-Demand eligible ");
        System.out.println("the request assessment button is disabled for non on-demand assessment eligible portfolio ");

        onDemandAssessmentPage.selectPortfolioOptionByName(portfolioName);
        BrowserUtils.wait(5);
        assertTestCase.assertTrue(onDemandAssessmentPage.buttonRequestAssessment.isEnabled(), "Verify that the request assessment button is enabled for a on-Demand eligible Portfolio");
        System.out.println("the request assessment button is enabled for On-Demand assessment eligible portfolio");
        //assertTestCase.assertTrue(onDemandAssessmentPage.isViewDetailButtonEnabled(portfolioName), "verify view detail button is enabled");
        System.out.println("the view detail button is displayed and enabled ");
        onDemandAssessmentPage.clickOnViewDetailButton(portfolioName);
        BrowserUtils.wait(15);
        viewDetailPage detail = new viewDetailPage();
        detail.verifyHeaderDetailsInViewDetailPage(detail);
        detail.verifyViewDetailPageFooter(detail, portfolioName );
        detail.verifyViewDetailTables(detail);
        detail.verifyClickingOnEscButtonFromViewDetailPage(detail);



     /*
        onDemandAssessmentPage.clickOnMenuButton();
        onDemandAssessmentPage.clickOnLogOutButton();
        LoginPage login = new LoginPage();
        login.loginWithParams("onemodyt+001@gmail.com", "Moodys123");
        System.out.println("logged In to check portfolio with 0% coverage");
        onDemandAssessmentPage.getListOfPortfolios();
        int index = onDemandAssessmentPage.checkPortfolioWithZeroCoverage();
        System.out.println("index for portfolio with 0 coverage is : " + index);
        String portfolio =onDemandAssessmentPage.getPortfolioNameByIndex(index);
        System.out.println("the name of portfolio with 0 coverage is" + portfolio);

        BrowserUtils.wait(20);
*/




      //  onDemandAssessmentPage.clickonOnRequestAssessmentButton();
       // onDemandAssessmentPage.clickReviewAndSendRequestButton();


    }
    @Test(groups = {REGRESSION,UI,COMMON })
    @Xray(test = {11985,12001,12002,12011,12054,12092,12822,12824})
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

    @Test(groups = {"regression", "ui"})
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
        Assert.assertTrue(onDemandAssessmentPage.verifyMainPageHeader("Moody's ESG360: Dashboard"), "Global Header Title is not matched for Dashboard");

        onDemandAssessmentPage.clickMenu();
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
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

        onDemandAssessmentPage.clickReviewAndSendRequestButton();;
        BrowserUtils.wait(3);
        onDemandAssessmentPage.clickESCkey();
        BrowserUtils.wait(2);
        onDemandAssessmentPage.validateOnDemandPageHeader();
    }

    @Test(groups = {REGRESSION,UI })
    @Xray(test = {12010,12827})
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
        //onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);
        onDemandAssessmentPage.clickMenu();
        BrowserUtils.wait(5);
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
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
        //onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);

        onDemandAssessmentPage.clickMenu();
        BrowserUtils.wait(5);
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
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
        //onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);
        onDemandAssessmentPage.clickMenu();
        BrowserUtils.wait(5);
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.validateOnDemandPageHeader();

        onDemandAssessmentPage.goToSendRequestPage(portfolioName);

        onDemandAssessmentPage.validateAndORLogic();



    }

    @Test(groups = {"regression", "ui"})
    @Xray(test = {12826, 12974,12828})
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

    @Test(groups = {REGRESSION,UI , SMOKE})
    @Xray(test = {})
public void testOnDemandDetailPanel(){

    String portfolioName = "500 predicted p";
    OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
    onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
    onDemandAssessmentPage.selectPortfolioOptionByName(portfolioName);
    BrowserUtils.wait(5);
    onDemandAssessmentPage.clickonOnRequestAssessmentButton();
    onDemandAssessmentPage.clickReviewAndSendRequestButton();
}





}
