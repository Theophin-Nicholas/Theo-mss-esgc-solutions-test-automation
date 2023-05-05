package com.esgc.ONDEMAND.DB.Tests;

import com.esgc.Common.API.Controllers.CommonAPIController;
import com.esgc.ONDEMAND.API.Controllers.OnDemandFilterAPIController;
import com.esgc.ONDEMAND.DB.DBModels.OnDemandPortfolioTable;
import com.esgc.ONDEMAND.DB.DBQueries.OnDemandAssessmentQueries;
import com.esgc.Common.DB.TestBases.DataValidationTestBase;

import com.esgc.ONDEMAND.UI.Pages.OnDemandAssessmentPage;
import com.esgc.Utilities.*;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static com.esgc.Utilities.Groups.*;
import static com.esgc.Utilities.Groups.COMMON;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.collect;

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

    @Test(groups = {REGRESSION, UI, COMMON})
    @Xray(test = {13989})
    public void OnDemandAssessmentPortfolioTableDataValidations() throws ParseException {
        String portfolioName = "OnDemandEntities";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
        if (onDemandAssessmentPage.getPortfolioList().stream().filter(i-> i.equals(portfolioName)).count()>0){
            CommonAPIController.deletePortfolioThroughAPI(portfolioName);
            Driver.getDriver().navigate().refresh();
        }
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
        String portfolioFilePath = ImportPortfolioUtility.getOnDemandPortfolioFileToUpload(Arrays.asList(new String[]{"Self-Assessed","Predicted","Analytical"}),"" ,10, portfolioName,false);
        onDemandAssessmentPage.uploadPortfolio(portfolioFilePath, "OnDemand");
        onDemandAssessmentPage.closePopUp();
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
        String portfolioID = CommonAPIController.getPortfolioIds(portfolioName).get(0);
        OnDemandAssessmentQueries onDemandQueries = new OnDemandAssessmentQueries();
        List<OnDemandPortfolioTable> dbData = onDemandQueries.getDataForPortfolioTable(portfolioID,"'Self-Assessed','Predicted','Analytical'");
        int sumOfCoveredEntities =  dbData.stream().filter(i -> !(i.getSCORE_QUALITY().equals("NA"))).mapToInt(OnDemandPortfolioTable::getVALUE).sum();
        int sumOfPredictedEntities =  dbData.stream().filter(i -> (i.getSCORE_QUALITY().equals("Predicted"))).mapToInt(OnDemandPortfolioTable::getVALUE).sum();
        Map<String,String> UIValues = onDemandAssessmentPage.getPortfolioCoverageAndOnDemadEligibilityValues(portfolioName);
        assertTestCase.assertTrue(UIValues.get("Coverage").equals(String.format("%.2f",(float) sumOfCoveredEntities/dbData.get(0).getTOTAL_VALUE()*100)+"%"),"Validating Coverage Value");
        assertTestCase.assertTrue(UIValues.get("Coverage").equals(String.format("%.2f",(float) sumOfPredictedEntities/dbData.get(0).getTOTAL_VALUE()*100)+"%"),"Validating On Demand Eligibility Value");
        //assertTestCase.assertTrue(UIValues.get("ONDemandEligibility").equals(CommonUtility.round((float) sumOfPredictedEntities/dbData.get(0).getTOTAL_VALUE()*100,2)+"%"),"");
    }

    @Test(groups = {REGRESSION, UI, COMMON})
    @Xray(test = {13991})
    public void OnDemandAssessmentPortfolioTableDataValidationsWithUnmatchedEntities() throws ParseException {
        String portfolioName = "OnDemandEntities";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
        if (onDemandAssessmentPage.getPortfolioList().stream().filter(i-> i.equals(portfolioName)).count()>0){
            CommonAPIController.deletePortfolioThroughAPI(portfolioName);
            Driver.getDriver().navigate().refresh();
        }
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
        String portfolioFilePath = ImportPortfolioUtility.getOnDemandPortfolioFileToUpload(Arrays.asList(new String[]{"Self-Assessed","Predicted","Analytical"}),"" ,10, portfolioName,true);
        onDemandAssessmentPage.uploadPortfolio(portfolioFilePath, "OnDemand");
        onDemandAssessmentPage.closePopUp();
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
        String portfolioID = CommonAPIController.getPortfolioIds(portfolioName).get(0);
        OnDemandAssessmentQueries onDemandQueries = new OnDemandAssessmentQueries();
        List<OnDemandPortfolioTable> dbData = onDemandQueries.getDataForPortfolioTable(portfolioID,"'Self-Assessed','Predicted','Analytical'");
        int sumOfCoveredEntities =  dbData.stream().filter(i -> !(i.getSCORE_QUALITY().equals("NA"))).mapToInt(OnDemandPortfolioTable::getVALUE).sum();
        int sumOfPredictedEntities =  dbData.stream().filter(i -> (i.getSCORE_QUALITY().equals("Predicted"))).mapToInt(OnDemandPortfolioTable::getVALUE).sum();
        Map<String,String> UIValues = onDemandAssessmentPage.getPortfolioCoverageAndOnDemadEligibilityValues(portfolioName);
        assertTestCase.assertTrue(UIValues.get("Coverage").equals(CommonUtility.round((float) sumOfCoveredEntities/dbData.get(0).getTOTAL_VALUE()*100,2)+"%"),"Validating Coverage Value");
        assertTestCase.assertTrue(UIValues.get("ONDemandEligibility").equals(CommonUtility.round((float) sumOfPredictedEntities/dbData.get(0).getTOTAL_VALUE()*100,2)+"%"),"Validating On Demand Eligibility Value");
    }

    @Test(groups = {REGRESSION, UI, COMMON})
    @Xray(test = {13988,13766,13767,13768})
    public void OnDemandAssessmentPortfolioTableDataValidationsWithOnyPredictedAndAnalyticalEntities() throws ParseException {
        String portfolioName = "OnDemandEntities";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
        if (onDemandAssessmentPage.getPortfolioList().stream().filter(i-> i.equals(portfolioName)).count()>0){
            CommonAPIController.deletePortfolioThroughAPI(portfolioName);
            Driver.getDriver().navigate().refresh();
        }
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
        String portfolioFilePath = ImportPortfolioUtility.getOnDemandPortfolioFileToUpload(Arrays.asList(new String[]{"Predicted","Analytical"}),"" ,10, portfolioName,false);
        onDemandAssessmentPage.uploadPortfolio(portfolioFilePath, "OnDemand");
        onDemandAssessmentPage.closePopUp();
        onDemandAssessmentPage.waitForPortfolioTableToLoad();
        String portfolioID = CommonAPIController.getPortfolioIds(portfolioName).get(0);
        OnDemandAssessmentQueries onDemandQueries = new OnDemandAssessmentQueries();
        List<OnDemandPortfolioTable> dbData = onDemandQueries.getDataForPortfolioTable(portfolioID,"'Self-Assessed','Predicted','Analytical'");
        int sumOfCoveredEntities =  dbData.stream().filter(i -> !(i.getSCORE_QUALITY().equals("NA"))).mapToInt(OnDemandPortfolioTable::getVALUE).sum();
        int sumOfPredictedEntities =  dbData.stream().filter(i -> (i.getSCORE_QUALITY().equals("Predicted"))).mapToInt(OnDemandPortfolioTable::getVALUE).sum();
        Map<String,String> UIValues = onDemandAssessmentPage.getPortfolioCoverageAndOnDemadEligibilityValues(portfolioName);
        assertTestCase.assertTrue(UIValues.get("Coverage").equals(CommonUtility.round((float) sumOfCoveredEntities/dbData.get(0).getTOTAL_VALUE()*100,2)+"%"),"Validating Coverage Value");
        assertTestCase.assertTrue(UIValues.get("ONDemandEligibility").equals(CommonUtility.round((float) sumOfPredictedEntities/dbData.get(0).getTOTAL_VALUE()*100,2)+"%"),"Validating On Demand Eligibility Value");
       onDemandAssessmentPage.selectPortfolio(portfolioName);
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.getEligibleAssessment();
    }
}
