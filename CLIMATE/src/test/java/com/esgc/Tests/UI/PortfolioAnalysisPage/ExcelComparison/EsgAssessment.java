package com.esgc.Tests.UI.PortfolioAnalysisPage.ExcelComparison;

import com.esgc.Pages.DashboardPage;
import com.esgc.Tests.TestBases.DataValidationTestBase;
import com.esgc.Tests.UI.DashboardPage.Export.ExportUtils;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Database.DashboardQueries;
import com.esgc.Utilities.Database.DatabaseDriver;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class EsgAssessment extends DataValidationTestBase {

    ExportUtils utils = new ExportUtils();

    public void verifyEsgAssessment(String researchLine, String portfolioId, String year, String month) {

        DashboardPage dashboardPage = new DashboardPage();
        List<Map<String,String>> excelResults = utils.convertExcelToNestedMap(BrowserUtils.exportPath(researchLine));

        // Compare the data from Data Base with Excel File
        DatabaseDriver.createDBConnection();
        DashboardQueries dashboardQueries = new DashboardQueries();
        List<Map<String, Object>> dbEsgScoresInfo = dashboardQueries.getEsgAssessmentInfo(portfolioId, year, month);

        int i=0;
        for(Map<String, String> excelResult:excelResults){
            String excelOrbisId = String.valueOf(excelResult.get("Orbis ID"));
            System.out.println("**** Record: "+(++i)+" ESG Scores Info Verification of Orbis Id: "+excelOrbisId);
            boolean found = false;
            for(int j=0; j<dbEsgScoresInfo.size(); j++){
                if(dbEsgScoresInfo.get(j).get("Orbis ID").toString().equals(excelOrbisId)){
                    found = true;
                    Assert.assertTrue(verifyEsgScoresInfo(excelResult, dbEsgScoresInfo.get(j)),excelOrbisId+" ESG Scores Info Verification");
                    break;
                }
            }
            if(!found){
                Assert.assertTrue(false,excelOrbisId+" Company ESG Scores Info is not found in DB");
            }
        }
        dashboardPage.deleteDownloadFolder();

    }

    public boolean verifyEsgScoresInfo(Map<String,String> excelResult, Map<String,Object> dbResult) {

        boolean result = utils.compare(dbResult.get("Scored Orbis ID"),excelResult.get("Scored Orbis ID")) &&
                utils.compare(dbResult.get("LEI"),excelResult.get("LEI")) &&
                utils.compare(dbResult.get("Entity Name"),excelResult.get("Entity Name")) &&
                utils.compare(dbResult.get("Sector"),excelResult.get("Sector")) &&
                utils.compare(dbResult.get("Methodology Model Version"),excelResult.get("Methodology Model Version")) &&
                utils.compare(dbResult.get("Scored Date"),excelResult.get("Scored Date")) &&
                utils.compare(dbResult.get("Evaluation Year"),excelResult.get("Evaluation Year")) &&
                utils.compare(dbResult.get("Score Type"),excelResult.get("Score Type")) &&
                utils.compare(dbResult.get("Overall Score"),excelResult.get("Overall Score")) &&
                utils.compare(dbResult.get("Environment Qualifier"),excelResult.get("Environment Qualifier")) &&
                utils.compare(dbResult.get("Social Qualifier"),excelResult.get("Social Qualifier")) &&
                utils.compare(dbResult.get("Governance Qualifier"),excelResult.get("Governance Qualifier")) &&
                utils.compare(dbResult.get("Environment Score"),excelResult.get("Environment Score")) &&
                utils.compare(dbResult.get("Social Score"),excelResult.get("Social Score")) &&
                utils.compare(dbResult.get("Governance Score"),excelResult.get("Governance Score"));

        return result;
    }

}
