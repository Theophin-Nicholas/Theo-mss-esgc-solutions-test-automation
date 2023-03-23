package com.esgc.ONDEMAND.DB.Tests;

import com.esgc.ONDEMAND.API.Controllers.OnDemandFilterAPIController;
import com.esgc.ONDEMAND.DB.DBQueries.OnDemandAssessmentQueries;
import com.esgc.Common.DB.TestBases.DataValidationTestBase;

import com.esgc.ONDEMAND.UI.Pages.OnDemandAssessmentPage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.Groups.DATA_VALIDATION;
import static com.esgc.Utilities.Groups.REGRESSION;

public class OnDemandAssessmentDBTests extends DataValidationTestBase {

    @Xray(test = {12066})
    @Test(groups = {REGRESSION, DATA_VALIDATION})
    public void verifyPredictedEsgInfo() {

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();

        //onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);
        onDemandAssessmentPage.clickMenu();
        BrowserUtils.wait(5);
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.validateOnDemandPageHeader();

        onDemandAssessmentPage.goToSendRequestPage(portfolioName);

        String uiEligibleAssessment = onDemandAssessmentPage.getEligibleAssessment();
        String uiHighestInvestment = onDemandAssessmentPage.getHighestInvestment();

        OnDemandFilterAPIController controller = new OnDemandFilterAPIController();
        String portfolioId = controller.getPortfolioId(portfolioName);
        OnDemandAssessmentQueries queries = new OnDemandAssessmentQueries();
        String dbEligibleAssessment = queries.getEligibleAssessment(portfolioId);
        String dbHighestInvestment = queries.getHighestInvestment(portfolioId);

        assertTestCase.assertEquals(uiEligibleAssessment,dbEligibleAssessment,"Eligible Assessments Percentage is not matching");
        //TODO : to check with Satish
        //assertTestCase.assertEquals(uiHighestInvestment,dbHighestInvestment,"Eligible Assessments Percentage is not matching");

    }

    // TODO : There is an error in UI values and bug has been created
    @Xray(test = {12068})
    @Test(enabled = false , groups = {REGRESSION, DATA_VALIDATION})
    public void verifyCompaniesInvestmentInfo() {
        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        //onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);
        onDemandAssessmentPage.clickMenu();
        BrowserUtils.wait(5);
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.validateOnDemandPageHeader();

        onDemandAssessmentPage.goToSendRequestPage(portfolioName);
        onDemandAssessmentPage.clickReviewAndSendRequestButton();

        ArrayList<HashMap<String, String>> uiCompaniesInfo = onDemandAssessmentPage.getCompaniesInvestmentInfo();

        OnDemandFilterAPIController controller = new OnDemandFilterAPIController();
        String portfolioId = controller.getPortfolioId(portfolioName);
        OnDemandAssessmentQueries queries = new OnDemandAssessmentQueries();
        List<Map<String, Object>> dbCompaniesInfo = queries.getCompaniesInvestmentInfo(portfolioId);

        //assertTestCase.assertEquals(uiCompaniesInfo.size(),dbCompaniesInfo.size(),"Companies count is not matching");
        for(int i=0; i<=uiCompaniesInfo.size(); i++){
            assertTestCase.assertEquals(uiCompaniesInfo.get(i).get("COMPANY_NAME"),dbCompaniesInfo.get(i).get("COMPANY_NAME"),"Record-"+i+": Company Name is not matching");
            //TODO : to check with Satish
          //  assertTestCase.assertEquals(uiCompaniesInfo.get(i).get("InvPerc"),dbCompaniesInfo.get(i).get("InvPerc"),"Record-"+i+": InvPerc is not matching");
            assertTestCase.assertEquals(uiCompaniesInfo.get(i).get("VALUE"),dbCompaniesInfo.get(i).get("VALUE").toString(),"Record-"+i+": Company Name is not matching");
        }

    }

    @Xray(test = {12457})
    @Test(groups = {REGRESSION, DATA_VALIDATION})
    public void verifyRegionsSectorsInvestmentInfo() {
        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        //onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);
        onDemandAssessmentPage.clickMenu();
        BrowserUtils.wait(5);
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.validateOnDemandPageHeader();

        ArrayList<String> uiLocationWiseCompaniesInfo = onDemandAssessmentPage.getLocationWiseInvestmentInfo();
        ArrayList<String> uiSectorWiseCompaniesInfo = onDemandAssessmentPage.getSectorWiseInvestmentInfo();

        OnDemandFilterAPIController controller = new OnDemandFilterAPIController();
        String portfolioId = controller.getPortfolioId(portfolioName);
        OnDemandAssessmentQueries queries = new OnDemandAssessmentQueries();
        List<String> dbLocationInfo = queries.getLocationWiseInvestmentInfo(portfolioId);
        List<String> dbSectorInfo = queries.getSectorWiseInvestmentInfo(portfolioId);

        for(int i=0; i<uiLocationWiseCompaniesInfo.size(); i++){
            assertTestCase.assertTrue(dbLocationInfo.contains(uiLocationWiseCompaniesInfo.get(i)),uiLocationWiseCompaniesInfo.get(i)+" Location Info is not matching");
        }

        for(int i=0; i<uiSectorWiseCompaniesInfo.size(); i++){
            assertTestCase.assertTrue(dbSectorInfo.contains(uiSectorWiseCompaniesInfo.get(i)),uiSectorWiseCompaniesInfo.get(i)+" Sector Info is not matching");
        }

    }
}
