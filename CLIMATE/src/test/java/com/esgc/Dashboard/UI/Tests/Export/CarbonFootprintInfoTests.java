package com.esgc.Dashboard.UI.Tests.Export;

import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.Dashboard.DB.DBQueries.DashboardQueries;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.Groups.*;


public class CarbonFootprintInfoTests extends DataValidationTestBase {

    String portfolioId = "00000000-0000-0000-0000-000000000000";
    ExportUtils utils = new ExportUtils();

    @Test(groups = {DASHBOARD, REGRESSION, UI})
    @Xray(test = {7220, 7221})
    public void compareCarbonFootPrintFromExcelToDB() {
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
        System.out.println("year last = " + year);
        System.out.println("month last= " + month);

        // Compare the data from Data Base with Excel File
        int i=0;
        for(Map<String, String> excelResult:excelResults){
            String excelOrbisId = String.valueOf(excelResult.get("ORBIS_ID"));
            System.out.println("**** Record: "+(++i)+" Carbon Footprint Details Verification of Orbis Id: "+excelOrbisId);

            List<Map<String, Object>> dbCompanyCarbonFootPrintInfo = dashboardQueries.getCompanyCarbonFootprintInfo(portfolioId, excelOrbisId, month, year);
            assertTestCase.assertEquals(dbCompanyCarbonFootPrintInfo.size(),1,excelOrbisId+" No Carbon Footprint details found");
            assertTestCase.assertTrue(verifyCarbonFootprintInfo(excelResult, dbCompanyCarbonFootPrintInfo.get(0)),excelOrbisId+" Carbon Footprint Verification");

        }

        dashboardPage.deleteDownloadFolder();
    }

    public boolean verifyCarbonFootprintInfo(Map<String,String> excelResult, Map<String,Object> dbResult) {

        boolean result =//TODO Below line disabled due to date issue ( check https://esjira/browse/ESGCA-8761
               //utils.compare(dbResult.get("PRODUCED_DATE"),excelResult.get("Carbon Footprint Produced Date")) &&
                utils.compare(dbResult.get("SCORE_CATEGORY"),excelResult.get("Carbon Footprint Grade")) &&
                utils.compare(dbResult.get("CARBON_FOOTPRINT_VALUE_TOTAL"),excelResult.get("Carbon Footprint Score (scope 1 & 2) (t CO2 eq)")) &&
                utils.compare(dbResult.get("CARBON_FOOTPRINT_VALUE_SCOPE_1"),excelResult.get("Scope 1 (t CO2 eq)")) &&
                utils.compare(dbResult.get("CARBON_FOOTPRINT_VALUE_SCOPE_2"),excelResult.get("Scope 2 (t CO2 eq)")) &&
                utils.compare(dbResult.get("CARBON_FOOTPRINT_VALUE_SCOPE_3"),excelResult.get("Scope 3 (t CO2 eq)"));

        return result;
    }

}
