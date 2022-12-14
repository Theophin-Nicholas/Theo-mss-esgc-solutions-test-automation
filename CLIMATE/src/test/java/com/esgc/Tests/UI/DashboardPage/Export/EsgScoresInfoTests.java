package com.esgc.Tests.UI.DashboardPage.Export;

import com.esgc.Pages.DashboardPage;
import com.esgc.Pages.LoginPage;
import com.esgc.Tests.TestBases.DataValidationTestBase;
import com.esgc.Utilities.*;
import com.esgc.Utilities.Database.DashboardQueries;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class EsgScoresInfoTests extends DataValidationTestBase {

    String portfolioId = "00000000-0000-0000-0000-000000000000";
    ExportUtils utils = new ExportUtils();

    @Test(groups = {"dashboard", "regression", "ui"})
    @Xray(test = {9788, 9820})
    public void compareEsgScoresInfoFromExcelToDB() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.downloadDashboardExportFile();

        // Read the data from Excel File
        String filePath = dashboardPage.getDownloadedCompaniesExcelFilePath();
        List<Map<String,String>> excelResults = utils.convertExcelToNestedMap(filePath);

        // Compare the data from Data Base with Excel File
        DashboardQueries dashboardQueries = new DashboardQueries();
        List<Map<String, Object>> dbEsgScoresInfo = dashboardQueries.getEsgScoresInfo(portfolioId);

        int i=0;
        for(Map<String, String> excelResult:excelResults){
            String excelOrbisId = String.valueOf(excelResult.get("ORBIS_ID"));
            System.out.println("**** Record: "+(++i)+" ESG Scores Info Verification of Orbis Id: "+excelOrbisId);
            boolean found = false;
            for(int j=0; j<dbEsgScoresInfo.size(); j++){
                if(dbEsgScoresInfo.get(j).get("ORBIS_ID").toString().equals(excelOrbisId)){
                    found = true;
                    assertTestCase.assertTrue(verifyEsgScoresInfo(excelResult, dbEsgScoresInfo.get(j)),excelOrbisId+" ESG Scores Info Verification");
                }
            }
            if(!found){
                assertTestCase.assertTrue(false,excelOrbisId+" Company ESG Scores Info is not found in DB");
            }
        }
        dashboardPage.deleteDownloadFolder();
    }

    @Test(groups = {"dashboard", "regression", "ui"})
    @Xray(test = {11266, 11315})
    public void verifyScoreTypeOfEntitiesWhenEsgPredEntitlement_Bundle() {
        LoginPage login = new LoginPage();
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_ESG_PS_ENTITLEMENT);

        DashboardPage dashboardPage = new DashboardPage();

        BrowserUtils.wait(5);
        dashboardPage.clickUploadPortfolioButton();
        dashboardPage.clickBrowseFile();
        BrowserUtils.wait(2);
        String inputFile = PortfolioFilePaths.portfolioWithPredictedScores();
        RobotRunner.selectFileToUpload(inputFile);
        BrowserUtils.wait(4);
        dashboardPage.clickUploadButton();
        dashboardPage.waitForDataLoadCompletion();
        BrowserUtils.wait(10);
        dashboardPage.closePopUp();

        dashboardPage.downloadDashboardExportFile();

        // Read the data from Excel File
        String filePath = dashboardPage.getDownloadedCompaniesExcelFilePath();
        List<Map<String,String>> excelResults = utils.convertExcelToNestedMap(filePath);
        ArrayList<String> expScoreTypes = new ArrayList<>();
        expScoreTypes.add("Analyst Driven");
        expScoreTypes.add("Subsidiary");
        expScoreTypes.add("Predicted");
        int i=0;
        for(Map<String, String> excelResult:excelResults){
            String actualScoreType = String.valueOf(excelResult.get("Score Type"));
            System.out.println("**** Record: "+(++i)+" -- Orbis Id: "+excelResult.get("ORBIS_ID")+" -- Score Type: "+actualScoreType);
            if(!actualScoreType.isEmpty())
                assertTestCase.assertTrue(expScoreTypes.contains(actualScoreType),"Score Type Verification");
        }
        dashboardPage.deleteDownloadFolder();
    }

    @Test(groups = {"dashboard", "regression", "ui"})
    @Xray(test = {11267, 11317})
    public void compareEsgScoresWhenNoEsgPredEntitlement_Bundle() {
        LoginPage login = new LoginPage();
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_ESG_ENTITLEMENT);

        DashboardPage dashboardPage = new DashboardPage();

        BrowserUtils.wait(5);
        dashboardPage.clickUploadPortfolioButton();
        dashboardPage.clickBrowseFile();
        BrowserUtils.wait(2);
        String inputFile = PortfolioFilePaths.portfolioWithPredictedScores();
        RobotRunner.selectFileToUpload(inputFile);
        BrowserUtils.wait(4);
        dashboardPage.clickUploadButton();
        dashboardPage.waitForDataLoadCompletion();
        BrowserUtils.wait(10);
        dashboardPage.closePopUp();

        dashboardPage.downloadDashboardExportFile();

        // Read the data from Excel File
        String filePath = dashboardPage.getDownloadedCompaniesExcelFilePath();
        List<Map<String,String>> excelResults = utils.convertExcelToNestedMap(filePath);
        ArrayList<String> expScoreTypes = new ArrayList<>();
        expScoreTypes.add("Analyst Driven");
        expScoreTypes.add("Subsidiary");
        int i=0;
        for(Map<String, String> excelResult:excelResults){
            System.out.println("**** Record: "+(++i)+" ESG Scores Info Verification of Orbis Id: "+excelResult.get("ORBIS_ID"));
            String actualScoreType = String.valueOf(excelResult.get("Score Type"));
            if(!actualScoreType.isEmpty())
                assertTestCase.assertTrue(expScoreTypes.contains(actualScoreType),actualScoreType+" is not an expected Score Type, expected from list: "+expScoreTypes);
        }
        dashboardPage.deleteDownloadFolder();
    }

    @Test(groups = {"dashboard", "regression", "ui"})
    @Xray(test = {11268})
    public void compareEsgScoresWhenNoEsgEntitlement_Bundle() {
        LoginPage login = new LoginPage();
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_EXPORT_ENTITLEMENT);

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();
        dashboardPage.downloadDashboardExportFile();

        // Read the data from Excel File
        String filePath = dashboardPage.getDownloadedCompaniesExcelFilePath();
        List<Map<String,String>> excelResults = utils.convertExcelToNestedMap(filePath);

        Map<String, String> excelResult = excelResults.get(0);
        boolean keyCheck = !(excelResult.containsKey("Scored Orbis ID") ||
                excelResult.containsKey("LEI") ||
                excelResult.containsKey("Methodology Model Version") ||
                excelResult.containsKey("Scored Date") ||
                excelResult.containsKey("Score Type") ||
                excelResult.containsKey("Evaluation Year") ||
                excelResult.containsKey("Overall Score"));

        assertTestCase.assertTrue(keyCheck,"ESG Score columns should not be available");
        dashboardPage.deleteDownloadFolder();
    }

    public boolean verifyEsgScoresInfo(Map<String,String> excelResult, Map<String,Object> dbResult) {

        boolean result = utils.compare(dbResult.get("Scored Orbis ID"),excelResult.get("Scored Orbis ID")) &&
                utils.compare(dbResult.get("LEI"),excelResult.get("LEI")) &&
                utils.compare(dbResult.get("Methodology Model Version"),excelResult.get("Methodology Model Version")) &&
                utils.compare(dbResult.get("Scored Date"),excelResult.get("Scored Date")) &&
                utils.compare(dbResult.get("Score Type"),excelResult.get("Score Type")) &&
                utils.compare(dbResult.get("Evaluation Year"),excelResult.get("Evaluation Year")) &&
                utils.compare(dbResult.get("Overall Score"),excelResult.get("Overall Score"));

        return result;
    }
}
