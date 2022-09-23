package com.esgc.Tests.UI.DashboardPage.Export;

import com.esgc.Pages.DashboardPage;
import com.esgc.Tests.TestBases.DataValidationTestBase;
import com.esgc.Utilities.Database.DashboardQueries;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;


public class PercentExposedInfoTests extends DataValidationTestBase {

    String portfolioId = "00000000-0000-0000-0000-000000000000";
    ExportUtils utils = new ExportUtils();

    @Test(groups = {"dashboard", "regression", "ui"})
    @Xray(test = {6417})
    public void comparePercentExposedInfoFromExcelToDB() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.downloadDashboardExportFile();

        // Read the data from Excel File
        String filePath = dashboardPage.getDownloadedCompaniesExcelFilePath();
        List<Map<String,String>> excelResults = utils.convertExcelToNestedMap(filePath);

        // Compare the data from Data Base with Excel File
        DashboardQueries dashboardQueries = new DashboardQueries();
        int i=0;
        for(Map<String, String> excelResult:excelResults){
            String excelOrbisId = String.valueOf(excelResult.get("ORBIS_ID"));
            System.out.println("**** Record: "+(++i)+" Physical Risk Info Verification of Orbis Id: "+excelOrbisId);

            List<Map<String, Object>> dbPhysicalRiskInfo = dashboardQueries.getPhysicalRiskInfo(portfolioId, excelOrbisId);
            assertTestCase.assertEquals(dbPhysicalRiskInfo.size(),1,excelOrbisId+" No company physical risk details found");
            assertTestCase.assertTrue(verifyPercentExposedInfo(excelResult),excelOrbisId+" Company Percentage Facilities Info Verification");
        }
        dashboardPage.deleteDownloadFolder();
    }

    public boolean verifyPercentExposedInfo(Map<String,String> excelResult) {
        String bvd9Number = excelResult.get("ORBIS_ID");
        boolean result = utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"1","0"),excelResult.get("% Exposed For Heat Stress: No Risk")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"1","1"),excelResult.get("% Exposed For Heat Stress: Low Risk")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"1","2"),excelResult.get("% Exposed For Heat Stress: Medium Risk")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"1","3"),excelResult.get("% Exposed For Heat Stress: High Risk")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"1","4"),excelResult.get("% Exposed For Heat Stress: Red Flag")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"2","0"),excelResult.get("% Exposed For Water Stress: No Risk")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"2","1"),excelResult.get("% Exposed For Water Stress: Low Risk")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"2","2"),excelResult.get("% Exposed For Water Stress: Medium Risk")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"2","3"),excelResult.get("% Exposed For Water Stress: High Risk")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"2","4"),excelResult.get("% Exposed For Water Stress: Red Flag")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"4","0"),excelResult.get("% Exposed For Sea Level Rise: No Risk")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"4","1"),excelResult.get("% Exposed For Sea Level Rise: Low Risk")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"4","2"),excelResult.get("% Exposed For Sea Level Rise: Medium Risk")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"4","3"),excelResult.get("% Exposed For Sea Level Rise: High Risk")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"4","4"),excelResult.get("% Exposed For Sea Level Rise: Red Flag")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"14","0"),excelResult.get("% Exposed For Floods: No Risk")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"14","1"),excelResult.get("% Exposed For Floods: Low Risk")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"14","2"),excelResult.get("% Exposed For Floods: Medium Risk")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"14","3"),excelResult.get("% Exposed For Floods: High Risk")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"14","4"),excelResult.get("% Exposed For Floods: Red Flag")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"12","0"),excelResult.get("% Exposed For Hurricanes and Typhoons: No Risk")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"12","1"),excelResult.get("% Exposed For Hurricanes and Typhoons: Low Risk")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"12","2"),excelResult.get("% Exposed For Hurricanes and Typhoons: Medium Risk")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"12","3"),excelResult.get("% Exposed For Hurricanes and Typhoons: High Risk")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"12","4"),excelResult.get("% Exposed For Hurricanes and Typhoons: Red Flag")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"16","0"),excelResult.get("% Exposed For Wildfires: No Risk")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"16","1"),excelResult.get("% Exposed For Wildfires: Low Risk")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"16","2"),excelResult.get("% Exposed For Wildfires: Medium Risk")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"16","3"),excelResult.get("% Exposed For Wildfires: High Risk")) &&
                utils.compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"16","4"),excelResult.get("% Exposed For Wildfires: Red Flag"));

        return result;
    }
}
