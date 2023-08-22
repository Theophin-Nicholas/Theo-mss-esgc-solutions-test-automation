package com.esgc.ONDEMAND.UI.Tests;

import com.esgc.Common.UI.TestBases.UITestBase;
import com.esgc.ONDEMAND.UI.Pages.OnDemandAssessmentPage;
import com.esgc.ONDEMAND.UI.Pages.ViewDetailPage;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

// test class

public class OnDemandViewDetailUITests extends UITestBase {

    @Test(groups = {UI, REGRESSION, SMOKE})
    @Xray(test = {2994, 2739, 2969, 2848, 2869, 2871, 2954})
    public void verifyViewDetailPageByScoreType() {
        ViewDetailPage detail = new ViewDetailPage();
        String portfolioName = "500 predicted portfolio";
        // String portfolioName1 = "SamplePortfolioToDelete";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
       /* if(!onDemandAssessmentPage.verifyPortfolio(portfolioName1)) {
            onDemandAssessmentPage.uploadPortfolio(portfolioName1.replaceAll(" ", ""));
        }*/
        if (!onDemandAssessmentPage.verifyPortfolio(portfolioName)) {
            onDemandAssessmentPage.uploadPortfolio(portfolioName.replaceAll(" ", ""));
        }
        /*onDemandAssessmentPage.selectPortfolioOptionByName(portfolioName1);*/
        onDemandAssessmentPage.SelectAndGetNonOnDemandEligiblePortfolioName();
        assertTestCase.assertFalse(onDemandAssessmentPage.isRequestAssessmentButtonEnabled(), "Verify that the request assessment button is disabled for a portfolio not on-Demand eligible ");
        System.out.println("the request assessment button is disabled for non on-demand assessment eligible portfolio ");

        onDemandAssessmentPage.selectPortfolioOptionByName(portfolioName);

        assertTestCase.assertTrue(onDemandAssessmentPage.isRequestAssessmentButtonEnabled(), "Verify that the request assessment button is enabled for a on-Demand eligible Portfolio");
        System.out.println("the request assessment button is enabled for On-Demand assessment eligible portfolio");
        //assertTestCase.assertTrue(onDemandAssessmentPage.isViewDetailButtonEnabled(portfolioName), "verify view detail button is enabled");
        System.out.println("the view detail button is displayed and enabled ");
        onDemandAssessmentPage.clickOnViewDetailButton(portfolioName);

        detail.clickOnGroupByOption(detail, "Score Type");

        detail.verifyHeaderDetailsInViewDetailPage(detail, portfolioName);
        detail.verifyViewDetailPageFooter(detail, portfolioName);
        System.out.println("linkedhashMap of entities + investment" + detail.getEntityInvestmentMap());
        detail.sortByValue(detail.getEntityInvestmentMap());
        detail.convertListOfInvestmentCellsTextToDouble(detail.getListOfInvestmentCellsText());
        detail.verifyViewDetailTables(detail);

        //  System.out.println("the number of companies not listed in the view detail page are : " + detail.getTheDigitsFromNumberString());
        //  detail.getCountOfCompaniesInViewDetailPage();
        //  detail.getColorOfElements();
        // detail.verifyTheColorOfPredictedScoreEntities();
        //detail.verifyDifferentWayToCloseViewDetailPage(detail, portfolioName);
        detail.verifyInvestmentCellsAreSortedFrom();
        //todo: Sorting verification logic is wrong. Compares all entities with each other. Should compare for each category table. Not all.
        detail.isEntitiesListSorted();
        detail.verifyEntitiesAreNotClickable();
        detail.verifyEntitiesNotClickableInViewDetailPage(detail);
    }

    @Test(groups = {UI, REGRESSION, SMOKE})
    @Xray(test = { 3033, 3113, 3124, 2865, 2845, 2837})
    public void verifyViewDetailPageBySector() {
        ViewDetailPage detail = new ViewDetailPage();
        String portfolioName = "500 predicted portfolio";
        // String portfolioName1 = "Portfolio Upload updated_good2";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
       /* if(!onDemandAssessmentPage.verifyPortfolio(portfolioName1)) {
            onDemandAssessmentPage.uploadPortfolio("PortfolioWithValidData2");
        }*/
        if (!onDemandAssessmentPage.verifyPortfolio(portfolioName)) {
            onDemandAssessmentPage.uploadPortfolio(portfolioName.replaceAll(" ", ""));
        }
        //onDemandAssessmentPage.selectPortfolioOptionByName(portfolioName1);
        onDemandAssessmentPage.SelectAndGetNonOnDemandEligiblePortfolioName();

        assertTestCase.assertFalse(onDemandAssessmentPage.isRequestAssessmentButtonEnabled(), "Verify that the request assessment button is disabled for a portfolio not on-Demand eligible ");
        System.out.println("the request assessment button is disabled for non on-demand assessment eligible portfolio ");

        onDemandAssessmentPage.selectPortfolioOptionByName(portfolioName);
        assertTestCase.assertTrue(onDemandAssessmentPage.isRequestAssessmentButtonEnabled(), "Verify that the request assessment button is enabled for a on-Demand eligible Portfolio");
        System.out.println("the request assessment button is enabled for On-Demand assessment eligible portfolio");
        //assertTestCase.assertTrue(onDemandAssessmentPage.isViewDetailButtonEnabled(portfolioName), "verify view detail button is enabled");
        System.out.println("the view detail button is displayed and enabled ");
        onDemandAssessmentPage.clickOnViewDetailButton(portfolioName);

        detail.clickOnGroupByOption(detail, "Sector");
        detail.verifyTablesInSectorTab();

        detail.closeViewDetailPanel(detail);
    }

    @Test(groups = {UI, REGRESSION, SMOKE})
    @Xray(test = {2855,2823,2829,2933,2814})
    public void verifyViewDetailPageByRegion() {

        ViewDetailPage detail = new ViewDetailPage();
        String portfolioName = "500 predicted portfolio";
        // String portfolioName1 = "SamplePortfolioToDelete";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
       /* if(!onDemandAssessmentPage.verifyPortfolio(portfolioName1)) {
            onDemandAssessmentPage.uploadPortfolio(portfolioName1.replaceAll(" ", ""));
        }*/
        if (!onDemandAssessmentPage.verifyPortfolio(portfolioName)) {
            onDemandAssessmentPage.uploadPortfolio(portfolioName.replaceAll(" ", ""));
        }

        // onDemandAssessmentPage.selectPortfolioOptionByName(portfolioName1);
        onDemandAssessmentPage.SelectAndGetNonOnDemandEligiblePortfolioName();
        assertTestCase.assertFalse(onDemandAssessmentPage.isRequestAssessmentButtonEnabled(), "Verify that the request assessment button is disabled for a portfolio not on-Demand eligible ");
        System.out.println("the request assessment button is disabled for non on-demand assessment eligible portfolio ");

        onDemandAssessmentPage.selectPortfolioOptionByName(portfolioName);

        assertTestCase.assertTrue(onDemandAssessmentPage.isRequestAssessmentButtonEnabled(), "Verify that the request assessment button is enabled for a on-Demand eligible Portfolio");
        System.out.println("the request assessment button is enabled for On-Demand assessment eligible portfolio");
        //assertTestCase.assertTrue(onDemandAssessmentPage.isViewDetailButtonEnabled(portfolioName), "verify view detail button is enabled");
        //System.out.println("the view detail button is displayed and enabled ");
        onDemandAssessmentPage.clickOnViewDetailButton(portfolioName);

        detail.clickOnGroupByOption(detail, "Region");
        detail.verifyOnlyRegionsAreInRegionTab();
        detail.verifyTablesInRegionTab();
    }
}