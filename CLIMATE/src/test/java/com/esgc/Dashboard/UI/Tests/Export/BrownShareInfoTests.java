package com.esgc.Dashboard.UI.Tests.Export;

import com.esgc.Base.TestBases.DataValidationTestBase;

import java.util.Map;


public class BrownShareInfoTests extends DataValidationTestBase {

    String portfolioId = "00000000-0000-0000-0000-000000000000";
    ExportUtils utils = new ExportUtils();

//    @Test(groups = {"dashboard", "regression", "ui"})
//    @Xray(test = {7224, 7225})
//    public void compareBrownShareInfoFromExcelToDB() {
//        DashboardPage dashboardPage = new DashboardPage();
//        dashboardPage.downloadDashboardExportFile();
//
//        // Read the data from Excel File
//        String filePath = dashboardPage.getDownloadedCompaniesExcelFilePath();
//        List<Map<String,String>> excelResults = utils.convertExcelToNestedMap(filePath);
//
//        // Read the data from Data Base
//        DashboardQueries dashboardQueries = new DashboardQueries();
//        String latestMonthAndYearWithData = dashboardQueries.getLatestMonthAndYearWithData(portfolioId);
//        String month = latestMonthAndYearWithData.split(":")[0];
//        String year = latestMonthAndYearWithData.split(":")[1];
//
//        // Compare the data from Data Base with Excel File
//        int i=0;
//        for(Map<String, String> excelResult:excelResults){
//            String excelOrbisId = String.valueOf(excelResult.get("ORBIS_ID"));
//            System.out.println("**** Record: "+(++i)+" Verification of Orbis Id: "+excelOrbisId);
//
//            System.out.println("Brown Share Details Verification");
//            List<Map<String, Object>> dbCompanyBrownShareInfo = dashboardQueries.getCompanyBrownShareInfo(portfolioId, excelOrbisId, month, year);
//            assertTestCase.assertEquals(dbCompanyBrownShareInfo.size(),1,excelOrbisId+" No Brown Share details found");
//            assertTestCase.assertTrue(verifyBrownShareInfo(excelResult, dbCompanyBrownShareInfo.get(0)),excelOrbisId+" Brown Share Info Verification");
//
//        }
//
//        dashboardPage.deleteDownloadFolder();
//    }


    public boolean verifyBrownShareInfo(Map<String,String> excelResult, Map<String,Object> dbResult) {

        boolean result = utils.compare(dbResult.get("PRODUCED_DATE"),excelResult.get("Brown Share Assessment Produced Date")) &&
                utils.compare(dbResult.get("SCORE_RANGE"),excelResult.get("Brown Share Assessment Score")) &&
                utils.compare(dbResult.get("BS_FOSF_INDUSTRY_REVENUES_ACCURATE_SOURCE"),excelResult.get("% of Overall Revenues derived from Fossil Fuel Activities")) &&
                utils.compareFFI(dbResult.get("BS_FOSF_INDUSTRY_UPSTREAM_THRESHOLD"),dbResult.get("BS_FOSF_INDUSTRY_UPSTREAM"),excelResult.get("Fossil fuels industry - Upstream")) &&
                utils.compareFFI(dbResult.get("BS_FOSF_INDUSTRY_MIDSTREAM_THRESHOLD"),dbResult.get("BS_FOSF_INDUSTRY_MIDSTREAM"),excelResult.get("Fossil fuels industry - Midstream")) &&
                utils.compareFFI(dbResult.get("BS_FOSF_INDUSTRY_GENERATION_THRESHOLD"),dbResult.get("BS_FOSF_INDUSTRY_GENERATION"),excelResult.get("Fossil fuels industry - Generation")) &&
                utils.compare(dbResult.get("BS_FOSF_INDUSTRY_REVENUES"),excelResult.get("Fossil fuels industry revenues")) &&
                utils.compare(dbResult.get("BS_FOSF_INDUSTRY_REVENUES_COAL_RESERVES"),excelResult.get("Fossil fuels reserves - Coal reserves")) &&
                utils.compare(dbResult.get("BS_FOSF_INDUSTRY_REVENUES_OIL_RESERVES"),utils.convertToDecimal(excelResult.get("Fossil fuels reserves - Oil reserves"))) &&
                utils.compare(dbResult.get("BS_FOSF_INDUSTRY_REVENUES_NATURAL_GAS_RESERVES"),utils.convertToDecimal(excelResult.get("Fossil fuels reserves - Natural Gas reserves"))) &&
                utils.compare(dbResult.get("BS_FOSF_COAL_MINING"),utils.convertToDecimal(excelResult.get("Coal mining"))) &&
                utils.compare(dbResult.get("BS_FOSF_THERMAL_COAL_MINING"),excelResult.get("Thermal coal mining")) &&
                utils.compare(dbResult.get("BS_FOSF_COAL_FUELLED_POWER_GEN"),excelResult.get("Coal-fuelled power generation")) &&
                utils.compare(dbResult.get("BS_FOSF_TAR_SAND_N_OIL_SHALE_EXTRACTION_SERVICES"),excelResult.get("Tar sands and oil shale extraction or services")) &&
                utils.compare(dbResult.get("BS_FOSF_OFFSHORE_ARCTIC_DRILLING"),excelResult.get("Offshore arctic drilling")) &&
                utils.compare(dbResult.get("BS_FOSF_ULTRA_DEEP_OFFSHORE"),excelResult.get("Ultra-deep offshore")) &&
                utils.compare(dbResult.get("BS_FOSF_COAL_BED_METHANE"),excelResult.get("Coal-bed methane (coal seam gas)")) &&
                utils.compare(dbResult.get("BS_FOSF_METHANE_HYDRATES"),excelResult.get("Methane hydrates")) &&
                utils.compare(dbResult.get("BS_FOSF_HYDRAULIC_FRACTURING"),excelResult.get("Hydraulic fracturing")) &&
                utils.compare(dbResult.get("BS_FOSF_LIQUEFIED_NATURAL_GAS"),excelResult.get("Liquefied Natural Gas (LNG)")) &&
                utils.compare(dbResult.get("BS_PEM_TOTAL_POTENTIAL_EMISSIONS"),excelResult.get("Total Potential Emissions")) &&
                utils.compare(dbResult.get("BS_PEM_TOTAL_COAL_POTENTIAL_EMISSIONS"),excelResult.get("Total Coal Potential Emissions")) &&
                utils.compare(dbResult.get("BS_PEM_TOTAL_NATURAL_GAS_POTENTIAL_EMISSIONS"),excelResult.get("Total Natural Gas Potential Emissions")) &&
                utils.compare(dbResult.get("BS_PEM_TOTAL_OIL_POTENTIAL_EMISSIONS"),excelResult.get("Total Oil Potential Emissions"));

        return result;
    }

}
