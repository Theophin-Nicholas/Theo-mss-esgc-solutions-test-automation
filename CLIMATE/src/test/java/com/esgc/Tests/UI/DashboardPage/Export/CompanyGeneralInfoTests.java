package com.esgc.Tests.UI.DashboardPage.Export;

import com.esgc.Pages.DashboardPage;
import com.esgc.Tests.TestBases.DataValidationTestBase;
import com.esgc.Utilities.Database.DashboardQueries;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;


public class CompanyGeneralInfoTests extends DataValidationTestBase {

    String portfolioId = "00000000-0000-0000-0000-000000000000";
    ExportUtils utils = new ExportUtils();

    @Test(groups = {"dashboard", "regression", "ui"})
    @Xray(test = {6417, 6418})
    public void compareCompanyInfoFromExcelToDB() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.downloadDashboardExportFile();

        // Read the data from Excel File
        String filePath = dashboardPage.getDownloadedCompaniesExcelFilePath();
        List<Map<String,String>> excelResults = utils.convertExcelToNestedMap(filePath);

        // Read the data from Data Base
        DashboardQueries dashboardQueries = new DashboardQueries();

        // Compare the data from Data Base with Excel File
        int i=0;
        for(Map<String, String> excelResult:excelResults){
            String excelOrbisId = String.valueOf(excelResult.get("ORBIS_ID"));
            System.out.println("**** Record: "+(++i)+" Company General Info Verification of Orbis Id: "+excelOrbisId);

            List<Map<String, Object>> dbCompanyGeneralInfo = dashboardQueries.getCompanyGeneralInfo(portfolioId, excelOrbisId);
            assertTestCase.assertEquals(dbCompanyGeneralInfo.size(),1,excelOrbisId+" No company details found");
            assertTestCase.assertTrue(verifyCompanyGeneralInfo(excelResult, dbCompanyGeneralInfo.get(0)),excelOrbisId+" Company General Info Verification");

        }

        dashboardPage.deleteDownloadFolder();
    }

    public boolean verifyCompanyGeneralInfo(Map<String,String> excelResult, Map<String,Object> dbResult) {

        boolean result = utils.compare(dbResult.get("COMPANY_NAME"), excelResult.get("ENTITY")) &&
                utils.compare(dbResult.get("ISIN"), excelResult.get("ISIN(PRIMARY)")) &&
                utils.compare(dbResult.get("SEC_ID"), excelResult.get("ISIN(USER_INPUT)")) &&
                utils.compare(dbResult.get("BVD9_NUMBER"), excelResult.get("ORBIS_ID")) &&
                utils.compare(dbResult.get("SECTOR"), excelResult.get("SECTOR")) &&
                utils.compare(dbResult.get("REGION"), excelResult.get("REGION")) &&
                utils.compare(dbResult.get("AS_OF_DATE"),excelResult.get("Portfolio Upload Date"));

        return result;
    }

}
