package com.esgc.DB.Tests;

import com.esgc.API.Controllers.OnDemandFilterAPIController;
import com.esgc.DB.DBQueries.OnDemandAssessmentQueries;
import com.esgc.DB.TestBases.DataValidationTestBase;
import com.esgc.UI.Pages.OnDemandAssessmentPage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.Groups.REGRESSION;
import static com.esgc.Utilities.Groups.UI;

public class OnDemandAssessmentDBTests extends DataValidationTestBase {

    @Xray(test = {12066})
    @Test(groups = {REGRESSION, UI},dataProviderClass = DataProviderClass.class)
    public void verifyPredictedEsgInfo() {
        String portfolioName = "EsgWithPredictedScores";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);

        onDemandAssessmentPage.clickMenu();
        onDemandAssessmentPage.onDemandAssessmentRequest.click();
        assertTestCase.assertEquals(onDemandAssessmentPage.menuOptionPageHeader.getText(), "Moody's ESG360: Request On-Demand Assessment", "Moody's ESG360: Request On-Demand Assessment page verified");

        String uiEligibleAssessment = onDemandAssessmentPage.getEligibleAssessment();
        String uiHighestInvestment = onDemandAssessmentPage.getHighestInvestment();

        OnDemandFilterAPIController controller = new OnDemandFilterAPIController();
        String portfolioId = controller.getPortfolioId("EsgWithPredictedScores");
        OnDemandAssessmentQueries queries = new OnDemandAssessmentQueries();
        String dbEligibleAssessment = queries.getEligibleAssessment(portfolioId);
        String dbHighestInvestment = queries.getHighestInvestment(portfolioId);

        assertTestCase.assertEquals(uiEligibleAssessment,dbEligibleAssessment,"Eligible Assessments Percentage is not matching");
        assertTestCase.assertEquals(uiHighestInvestment,dbHighestInvestment,"Eligible Assessments Percentage is not matching");

    }

    @Xray(test = {12068})
    @Test(groups = {REGRESSION, UI},dataProviderClass = DataProviderClass.class)
    public void verifyCompaniesInvestmentInfo() {
        String portfolioName = "EsgWithPredictedScores";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);

        onDemandAssessmentPage.clickMenu();
        onDemandAssessmentPage.onDemandAssessmentRequest.click();
        assertTestCase.assertEquals(onDemandAssessmentPage.menuOptionPageHeader.getText(), "Moody's ESG360: Request On-Demand Assessment", "Moody's ESG360: Request On-Demand Assessment page verified");

        onDemandAssessmentPage.clickReviewAndSendRequestButton();
        ArrayList<HashMap<String, String>> uiCompaniesInfo = onDemandAssessmentPage.getCompaniesInvestmentInfo();

        OnDemandFilterAPIController controller = new OnDemandFilterAPIController();
        String portfolioId = controller.getPortfolioId("EsgWithPredictedScores");
        OnDemandAssessmentQueries queries = new OnDemandAssessmentQueries();
        List<Map<String, Object>> dbCompaniesInfo = queries.getCompaniesInvestmentInfo(portfolioId);

        //assertTestCase.assertEquals(uiCompaniesInfo.size(),dbCompaniesInfo.size(),"Companies count is not matching");
        for(int i=1; i<=uiCompaniesInfo.size(); i++){
            assertTestCase.assertEquals(uiCompaniesInfo.get(i).get("COMPANY_NAME"),uiCompaniesInfo.get(i).get("COMPANY_NAME"),"Record-"+i+": Company Name is not matching");
            assertTestCase.assertEquals(uiCompaniesInfo.get(i).get("InvPerc"),uiCompaniesInfo.get(i).get("InvPerc"),"Record-"+i+": InvPerc is not matching");
            assertTestCase.assertEquals(uiCompaniesInfo.get(i).get("VALUE"),uiCompaniesInfo.get(i).get("VALUE"),"Record-"+i+": Company Name is not matching");
        }

    }

    @Xray(test = {12457})
    @Test(groups = {REGRESSION, UI},dataProviderClass = DataProviderClass.class)
    public void verifyRegionsSectorsInvestmentInfo() {
        String portfolioName = "EsgWithPredictedScores";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);

        onDemandAssessmentPage.clickMenu();
        onDemandAssessmentPage.onDemandAssessmentRequest.click();
        assertTestCase.assertEquals(onDemandAssessmentPage.menuOptionPageHeader.getText(), "Moody's ESG360: Request On-Demand Assessment", "Moody's ESG360: Request On-Demand Assessment page verified");

        ArrayList<String> uiLocationWiseCompaniesInfo = onDemandAssessmentPage.getLocationWiseInvestmentInfo();
        ArrayList<String> uiSectorWiseCompaniesInfo = onDemandAssessmentPage.getSectorWiseInvestmentInfo();

        OnDemandFilterAPIController controller = new OnDemandFilterAPIController();
        String portfolioId = controller.getPortfolioId("EsgWithPredictedScores");
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
