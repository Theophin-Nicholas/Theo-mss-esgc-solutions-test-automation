package com.esgc.ONDEMAND.UI.Tests;

import com.esgc.Common.UI.Pages.LoginPage;
import com.esgc.Common.UI.TestBases.UITestBase;
import com.esgc.ONDEMAND.API.Controllers.OnDemandFilterAPIController;
import com.esgc.ONDEMAND.UI.Pages.OnDemandAssessmentPage;
import com.esgc.ONDEMAND.UI.Pages.ViewDetailPage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

// test class

public class OnDemandViewDetailUITests extends UITestBase {

    // test methods for on demand view detail pages

    @Test(groups = {UI, REGRESSION, SMOKE})
    @Xray(test = {13786, 14139, 13972, 14164, 14168, 14169, 14174})
    public void verifyViewDetailPageByScoreType(){
        ViewDetailPage detail = new ViewDetailPage();
        String portfolioName = "500 predicted p";
        String portfolioName1 = "SFDRPortfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        onDemandAssessmentPage.selectPortfolioOptionByName(portfolioName1);

        assertTestCase.assertTrue(!onDemandAssessmentPage.isRequestAssessmentButtonEnabled(), "Verify that the request assessment button is disabled for a portfolio not on-Demand eligible ");
        System.out.println("the request assessment button is disabled for non on-demand assessment eligible portfolio ");

        onDemandAssessmentPage.selectPortfolioOptionByName(portfolioName);

        assertTestCase.assertTrue(onDemandAssessmentPage.isRequestAssessmentButtonEnabled(), "Verify that the request assessment button is enabled for a on-Demand eligible Portfolio");
        System.out.println("the request assessment button is enabled for On-Demand assessment eligible portfolio");
        //assertTestCase.assertTrue(onDemandAssessmentPage.isViewDetailButtonEnabled(portfolioName), "verify view detail button is enabled");
        System.out.println("the view detail button is displayed and enabled ");
        onDemandAssessmentPage.clickOnViewDetailButton(portfolioName);

        detail.clickOnGroupByOption(detail, "Score Type");

        detail.verifyHeaderDetailsInViewDetailPage(detail);
        detail.verifyViewDetailPageFooter(detail, portfolioName );
        System.out.println("linkedhashMap of entities + investment"+ detail.getEntityInvestmentMap());
        detail.sortByValue(detail.getEntityInvestmentMap());
        detail.convertListOfInvestmentCellsTextToDouble(detail.getListOfInvestmentCellsText());
        detail.verifyViewDetailTables(detail);

        //  System.out.println("the number of companies not listed in the view detail page are : " + detail.getTheDigitsFromNumberString());
        //  detail.getCountOfCompaniesInViewDetailPage();
        //  detail.getColorOfElements();
        // detail.verifyTheColorOfPredictedScoreEntities();
        //detail.verifyDifferentWayToCloseViewDetailPage(detail, portfolioName);
        detail.verifyInvestmentCellsAreSortedFrom();
        detail.isEntitiesListSorted();
        detail.verifyEntitiesAreNotClickable();
        detail.verifyEntitiesNotClickableInViewDetailPage(detail);



    }
    @Test(groups = {UI, REGRESSION, SMOKE})
    @Xray(test = { 14212, 13795, 13796, 13797, 13800, 14138 })
    public void verifyViewDetailPageBySector(){
        ViewDetailPage detail = new ViewDetailPage();
        String portfolioName = "500 predicted p";
        String portfolioName1 = "SFDRPortfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        onDemandAssessmentPage.selectPortfolioOptionByName(portfolioName1);
        assertTestCase.assertTrue(!onDemandAssessmentPage.isRequestAssessmentButtonEnabled(), "Verify that the request assessment button is disabled for a portfolio not on-Demand eligible ");
        System.out.println("the request assessment button is disabled for non on-demand assessment eligible portfolio ");

        onDemandAssessmentPage.selectPortfolioOptionByName(portfolioName);
        assertTestCase.assertTrue(onDemandAssessmentPage.isRequestAssessmentButtonEnabled(), "Verify that the request assessment button is enabled for a on-Demand eligible Portfolio");
        System.out.println("the request assessment button is enabled for On-Demand assessment eligible portfolio");
        //assertTestCase.assertTrue(onDemandAssessmentPage.isViewDetailButtonEnabled(portfolioName), "verify view detail button is enabled");
        System.out.println("the view detail button is displayed and enabled ");
        onDemandAssessmentPage.clickOnViewDetailButton(portfolioName);

        detail.clickOnGroupByOption(detail, "Sector");
        detail.verifyTablesInSectorTab();

        detail.verifyDifferentWayToCloseViewDetailPage(detail, portfolioName);

    }
    @Test(groups = {UI, REGRESSION, SMOKE})
    @Xray(test = {14145,14146,14150,14154,14158})
    public void verifyViewDetailPageByRegion(){

        ViewDetailPage detail = new ViewDetailPage();
        String portfolioName = "500 predicted p";
        String portfolioName1 = "SFDRPortfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        onDemandAssessmentPage.selectPortfolioOptionByName(portfolioName1);

        assertTestCase.assertTrue(!onDemandAssessmentPage.isRequestAssessmentButtonEnabled(), "Verify that the request assessment button is disabled for a portfolio not on-Demand eligible ");
        System.out.println("the request assessment button is disabled for non on-demand assessment eligible portfolio ");

        onDemandAssessmentPage.selectPortfolioOptionByName(portfolioName);

        assertTestCase.assertTrue(onDemandAssessmentPage.isRequestAssessmentButtonEnabled(), "Verify that the request assessment button is enabled for a on-Demand eligible Portfolio");
        System.out.println("the request assessment button is enabled for On-Demand assessment eligible portfolio");
        //assertTestCase.assertTrue(onDemandAssessmentPage.isViewDetailButtonEnabled(portfolioName), "verify view detail button is enabled");
        System.out.println("the view detail button is displayed and enabled ");
        onDemandAssessmentPage.clickOnViewDetailButton(portfolioName);

        detail.clickOnGroupByOption(detail, "Region");
        detail.verifyOnlyRegionsAreInRegionTab();
        detail.verifyTablesInRegionTab();


    }
    @Test(groups={UI, REGRESSION, SMOKE})
    @Xray(test={13971, 13993})
    public void verifyZeroAssessmentRemaining(){
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        OnDemandFilterAPIController onD = new OnDemandFilterAPIController();
        LoginPage login = new LoginPage();
        String portfolioName = "PortfolioWithZeroCoverageEntities";
        onDemandAssessmentPage.clickOnMenuButton();
        onDemandAssessmentPage.clickOnLogOutButton();
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_ZERO_ASSESSMENT_AVAILABLE);
        System.out.println("Logged in to Check 0 Remaining Assessment....");
        assertTestCase.assertTrue(!onDemandAssessmentPage.isRequestAssessmentButtonEnabled(), "The request assessment button is disabled for accounts with 0 remaining requests.");

        onDemandAssessmentPage.verifyZeroAssessmentRemainingForOnDemand();
        onDemandAssessmentPage.checkViewDetailButtonDisabled();
        //onDemandAssessmentPage.validateViewDetailButtonAndDownloadButtonDisabledForZeroCoveragePortfolios(portfolioName);

    }


}