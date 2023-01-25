package com.esgc.Dashboard.UI.Tests.Export;

import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.Dashboard.DB.DBQueries.DashboardQueries;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.Groups.*;

public class TemperatureAlignInfoTests extends DataValidationTestBase {

    String portfolioId = "00000000-0000-0000-0000-000000000000";
    ExportUtils utils = new ExportUtils();

    @Test(groups = {DASHBOARD, REGRESSION, UI})
    @Xray(test = {7166})
    public void compareTempAlignInfoFromExcelToDB() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.downloadDashboardExportFile();

        // Read the data from Excel File
        String filePath = dashboardPage.getDownloadedCompaniesExcelFilePath();
        List<Map<String,String>> excelResults = utils.convertExcelToNestedMap(filePath);

        // Read the data from Data Base
        DashboardQueries dashboardQueries = new DashboardQueries();
        String latestMonthAndYearWithData = dashboardQueries.getLatestMonthAndYearWithData(portfolioId);
        String month = latestMonthAndYearWithData.split(":")[0];
        String year = latestMonthAndYearWithData.split(":")[1];

        // Compare the data from Data Base with Excel File
        int i=0;
        for(Map<String, String> excelResult:excelResults){
            String excelOrbisId = String.valueOf(excelResult.get("ORBIS_ID"));
            System.out.println("**** Record: "+(++i)+" Temperature Alignment Details Verification of Orbis Id: "+excelOrbisId);

            List<Map<String, Object>> dbCompanyTempAlignInfo = dashboardQueries.getCompanyTempAlignInfo(portfolioId, excelOrbisId, month, year);
            assertTestCase.assertEquals(dbCompanyTempAlignInfo.size(),1,excelOrbisId+" No Temperature Alignment details found");
            assertTestCase.assertTrue(verifyTemperatureAlignmentInfo(excelResult, dbCompanyTempAlignInfo.get(0)),excelOrbisId+" Temperature Alignment Info Verification");

        }

        dashboardPage.deleteDownloadFolder();
    }

    public boolean verifyTemperatureAlignmentInfo(Map<String,String> excelResult, Map<String,Object> dbResult) {

        boolean result = utils.compare(dbResult.get("PRODUCED_DATE"),excelResult.get("Temperature Alignment Produced Date")) &&
                utils.compare(dbResult.get("APPROACH"),excelResult.get("Approach")) &&
                utils.compare(dbResult.get("DATA_TYPE"),excelResult.get("Data Type")) &&
                utils.compare(dbResult.get("SCORE_CATEGORY"),excelResult.get("Temperature Alignment")) &&
                utils.compare(dbResult.get("TEMPERATURE_SCORE"),excelResult.get("Implied Temperature Rise")) &&
                utils.compare(dbResult.get("TEMPERATURE_TARGET"),excelResult.get("Implied Temperature Rise Target Year")) &&
                utils.compare(dbResult.get("BASE_YEAR"),excelResult.get("Emissions Reduction Base Year")) &&
                utils.compare(dbResult.get("TARGET_YEAR"),excelResult.get("Emissions Reduction Target Year")) &&
                utils.compare(dbResult.get("TARGET_DISCLOSURE"),excelResult.get("Target Disclosure")) &&
                utils.compare(dbResult.get("TARGET_DESCRIPTION"),excelResult.get("Target Description")) &&
                utils.compare(dbResult.get("SOURCE"),excelResult.get("Source")) &&
                utils.compare(dbResult.get("SCOPE"),excelResult.get("Scope")) &&
                utils.compare(dbResult.get("UNIT"),excelResult.get("Units"));

        return result;
    }
}
