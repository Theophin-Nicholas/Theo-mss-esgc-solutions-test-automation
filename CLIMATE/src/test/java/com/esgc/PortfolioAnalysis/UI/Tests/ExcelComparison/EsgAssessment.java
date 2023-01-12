package com.esgc.PortfolioAnalysis.UI.Tests.ExcelComparison;

import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.Dashboard.DB.DBQueries.DashboardQueries;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.Dashboard.UI.Tests.Export.ExportUtils;
import com.esgc.Reporting.CustomAssertion;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Database.DatabaseDriver;
import com.esgc.Utilities.ExcelUtil;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class EsgAssessment extends DataValidationTestBase {

    public CustomAssertion assertTestCase = new CustomAssertion();
    ExportUtils utils = new ExportUtils();

    public void verifyEsgAssessment(String researchLine, String portfolioId, String year, String month) {

        DashboardPage dashboardPage = new DashboardPage();
        String filePath = BrowserUtils.exportPath(researchLine);
        //TODO: Format is not correct
        //assertTestCase.assertTrue(validateEsgAssessmentExportedFileName(filePath), "File name should be in the format of ESG Assessment_26-Jul-2022_T12_24_53.xls");
        List<Map<String,String>> excelResults = utils.convertExcelToNestedMap(filePath);

        // Compare the data from Data Base with Excel File
        // DatabaseDriver.createDBConnection();
        DashboardQueries dashboardQueries = new DashboardQueries();
        DatabaseDriver.createDBConnection();
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

    public boolean validateEsgAssessmentExportedFileName(String filePath){
        return filePath.substring(filePath.indexOf("_T")).length()==14;
    }

    public void verifyEsgAssessmentColumnsInExcel(ExcelUtil exportedDocument) {
        List<String> columns = exportedDocument.getColumnsNames();

        boolean keyCheck = !(columns.contains("Alphanumeric Score") ||
                columns.contains("Overall Qualifier"));

        assertTestCase.assertTrue(keyCheck,"Columns should not be available");

    }

}
