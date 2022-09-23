package com.esgc.Tests.UI.DashboardPage.Export;

import com.esgc.Pages.DashboardPage;
import com.esgc.Tests.TestBases.DataValidationTestBase;
import com.esgc.Utilities.Database.DashboardQueries;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

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

    public boolean verifyEsgScoresInfo(Map<String,String> excelResult, Map<String,Object> dbResult) {

        boolean result = utils.compare(dbResult.get("Scored Orbis ID"),excelResult.get("Scored Orbis ID")) &&
                utils.compare(dbResult.get("LEI"),excelResult.get("LEI")) &&
                utils.compare(dbResult.get("Methodology Model Version"),excelResult.get("Methodology Model Version")) &&
                utils.compare(dbResult.get("Scored Date"),excelResult.get("Scored Date")) &&
                utils.compare(dbResult.get("Evaluation Year"),excelResult.get("Evaluation Year")) &&
                utils.compare(dbResult.get("Overall Score"),excelResult.get("Overall Score"));

        return result;
    }
}
