package com.esgc.Tests.UI.DashboardPage;

import com.esgc.Pages.DashboardPage;
import com.esgc.Tests.TestBases.DataValidationTestBase;
import com.esgc.Utilities.Database.DashboardQueries;
import com.esgc.Utilities.Xray;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;


public class ExportDashboardForAllRLs extends DataValidationTestBase {
//TODO All test cases are failing. need attention
    @Test(groups = {"dashboard", "regression", "ui"})
    @Xray(test = {6417, 6418})
    public void compareCompanyInfoFromExcelToDB() {
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");

        dashboardPage.clickViewCompaniesAndInvestments();
        test.info("Navigated to Companies Page");

        test.info("Verification of Companies Export Based on Sector");
        dashboardPage.selectViewBySector();

        dashboardPage.deleteDownloadFolder();
        dashboardPage.clickExportCompaniesButton();
        test.info("Exported All Companies and Investments Details in excel format");
        dashboardPage.closePortfolioExportDrawer();

        assertTestCase.assertTrue(dashboardPage.isCompaniesAndInvestmentsExcelDownloaded(), "Verify Download of Excel file with Companies and Investments details", 6080);

        // Read the data from Excel File
        String filePath = dashboardPage.getDownloadedCompaniesExcelFilePath();
        List<Map<String,String>> excelResults = convertExcelToNestedMap(filePath);

        // Read the data from Data Base
        DashboardQueries dashboardQueries = new DashboardQueries();
        String portfolioId = "00000000-0000-0000-0000-000000000000";

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

    @Test(groups = {"dashboard", "regression", "ui"})
    @Xray(test = {6417, 6418})
    public void comparePhysicalRiskDataFromExcelToDB() {
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");

        dashboardPage.clickViewCompaniesAndInvestments();
        test.info("Navigated to Companies Page");

        test.info("Verification of Companies Export Based on Sector");
        dashboardPage.selectViewBySector();

        dashboardPage.deleteDownloadFolder();
        dashboardPage.clickExportCompaniesButton();
        test.info("Exported All Companies and Investments Details in excel format");
        dashboardPage.closePortfolioExportDrawer();

        assertTestCase.assertTrue(dashboardPage.isCompaniesAndInvestmentsExcelDownloaded(), "Verify Download of Excel file with Companies and Investments details", 6080);

        // Read the data from Excel File
        String filePath = dashboardPage.getDownloadedCompaniesExcelFilePath();
        List<Map<String,String>> excelResults = convertExcelToNestedMap(filePath);

        // Compare the data from Data Base with Excel File
        DashboardQueries dashboardQueries = new DashboardQueries();
        String portfolioId = "00000000-0000-0000-0000-000000000000";
        int i=0;
        for(Map<String, String> excelResult:excelResults){
            String excelOrbisId = String.valueOf(excelResult.get("ORBIS_ID"));
            System.out.println("**** Record: "+(++i)+" Physical Risk Info Verification of Orbis Id: "+excelOrbisId);

            List<Map<String, Object>> dbPhysicalRiskInfo = dashboardQueries.getPhysicalRiskInfo(portfolioId, excelOrbisId);
            assertTestCase.assertEquals(dbPhysicalRiskInfo.size(),1,excelOrbisId+" No company physical risk details found");
            assertTestCase.assertTrue(verifyPhysicalRiskInfo(excelResult, dbPhysicalRiskInfo.get(0)),excelOrbisId+" Company Physical Risk Info Verification");
            assertTestCase.assertTrue(verifyPercentExposedInfo(excelResult),excelOrbisId+" Company Percentage Facilities Info Verification");
        }
        dashboardPage.deleteDownloadFolder();
    }

    @Test(groups = {"dashboard", "regression", "ui"})
    @Xray(test = {7220, 7221})
    public void compareCarbonFootPrintFromExcelToDB() {
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");

        dashboardPage.clickViewCompaniesAndInvestments();
        test.info("Navigated to Companies Page");

        test.info("Verification of Companies Export Based on Sector");
        dashboardPage.selectViewBySector();

        dashboardPage.deleteDownloadFolder();
        dashboardPage.clickExportCompaniesButton();
        test.info("Exported All Companies and Investments Details in excel format");
        dashboardPage.closePortfolioExportDrawer();

        assertTestCase.assertTrue(dashboardPage.isCompaniesAndInvestmentsExcelDownloaded(), "Verify Download of Excel file with Companies and Investments details", 6080);

        // Read the data from Excel File
        String filePath = dashboardPage.getDownloadedCompaniesExcelFilePath();
        List<Map<String,String>> excelResults = convertExcelToNestedMap(filePath);

        // Read the data from Data Base
        DashboardQueries dashboardQueries = new DashboardQueries();
        String portfolioId = "00000000-0000-0000-0000-000000000000";
        String latestMonthAndYearWithData = dashboardQueries.getLatestMonthAndYearWithData(portfolioId);
        String month = latestMonthAndYearWithData.split(":")[0];
        String year = latestMonthAndYearWithData.split(":")[1];

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


    @Test(groups = {"dashboard", "regression", "ui"})
    @Xray(test = {7166})
    public void compareTempAlignInfoFromExcelToDB() {
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");

        dashboardPage.clickViewCompaniesAndInvestments();
        test.info("Navigated to Companies Page");

        test.info("Verification of Companies Export Based on Sector");
        dashboardPage.selectViewBySector();

        dashboardPage.deleteDownloadFolder();
        dashboardPage.clickExportCompaniesButton();
        test.info("Exported All Companies and Investments Details in excel format");
        dashboardPage.closePortfolioExportDrawer();
        assertTestCase.assertTrue(dashboardPage.isCompaniesAndInvestmentsExcelDownloaded(), "Verify Download of Excel file with Companies and Investments details", 6080);

        // Read the data from Excel File
        String filePath = dashboardPage.getDownloadedCompaniesExcelFilePath();
        List<Map<String,String>> excelResults = convertExcelToNestedMap(filePath);

        // Read the data from Data Base
        DashboardQueries dashboardQueries = new DashboardQueries();
        String portfolioId = "00000000-0000-0000-0000-000000000000";
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

//    @Test(groups = {"dashboard", "regression", "ui"})
//    @Xray(test = {7222, 7223, 7239})
//    public void compareGreenShareInfoFromExcelToDB() {
//        DashboardPage dashboardPage = new DashboardPage();
//
//        dashboardPage.navigateToPageFromMenu("Dashboard");
//        test.info("Navigated to Dashboard Page");
//
//        dashboardPage.clickViewCompaniesAndInvestments();
//        test.info("Navigated to Companies Page");
//
//        test.info("Verification of Companies Export Based on Sector");
//        dashboardPage.selectViewBySector();
//
//        dashboardPage.deleteDownloadFolder();
//        dashboardPage.clickExportCompaniesButton();
//        dashboardPage.closePortfolioExportDrawer();
//        test.info("Exported All Companies and Investments Details in excel format");
//
//        assertTestCase.assertTrue(dashboardPage.isCompaniesAndInvestmentsExcelDownloaded(), "Verify Download of Excel file with Companies and Investments details", 6080);
//
//        // Read the data from Excel File
//        String filePath = dashboardPage.getDownloadedCompaniesExcelFilePath();
//        List<Map<String,String>> excelResults = convertExcelToNestedMap(filePath);
//
//        // Read the data from Data Base
//        DashboardQueries dashboardQueries = new DashboardQueries();
//        String portfolioId = "00000000-0000-0000-0000-000000000000";
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
//            System.out.println("Green Share Details Verification");
//            List<Map<String, Object>> dbCompanyGreenShareInfo = dashboardQueries.getCompanyGreenShareInfo(portfolioId, excelOrbisId, month, year);
//            assertTestCase.assertEquals(dbCompanyGreenShareInfo.size(),1,excelOrbisId+" No Green Share details found");
//            assertTestCase.assertTrue(verifyGreenShareInfo(excelResult, dbCompanyGreenShareInfo.get(0)),excelOrbisId+" Green Share Info Verification");
//
//        }
//
//        dashboardPage.deleteDownloadFolder();
//    }
//
//    @Test(groups = {"dashboard", "regression", "ui"})
//    @Xray(test = {7224, 7225})
//    public void compareBrownShareInfoFromExcelToDB() {
//        DashboardPage dashboardPage = new DashboardPage();
//
//        dashboardPage.navigateToPageFromMenu("Dashboard");
//        test.info("Navigated to Dashboard Page");
//
//        dashboardPage.clickViewCompaniesAndInvestments();
//        test.info("Navigated to Companies Page");
//
//        test.info("Verification of Companies Export Based on Sector");
//        dashboardPage.selectViewBySector();
//
//        dashboardPage.deleteDownloadFolder();
//        dashboardPage.clickExportCompaniesButton();
//        test.info("Exported All Companies and Investments Details in excel format");
//        dashboardPage.closePortfolioExportDrawer();
//
//        assertTestCase.assertTrue(dashboardPage.isCompaniesAndInvestmentsExcelDownloaded(), "Verify Download of Excel file with Companies and Investments details", 6080);
//
//        // Read the data from Excel File
//        String filePath = dashboardPage.getDownloadedCompaniesExcelFilePath();
//        List<Map<String,String>> excelResults = convertExcelToNestedMap(filePath);
//
//        // Read the data from Data Base
//        DashboardQueries dashboardQueries = new DashboardQueries();
//        String portfolioId = "00000000-0000-0000-0000-000000000000";
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

    // ************************* Re-Usable Methods ******************************

    public boolean verifyCompanyGeneralInfo(Map<String,String> excelResult, Map<String,Object> dbResult) {

        boolean result = compare(dbResult.get("COMPANY_NAME"), excelResult.get("ENTITY")) &&
                compare(dbResult.get("ISIN"), excelResult.get("ISIN(PRIMARY)")) &&
                compare(dbResult.get("SEC_ID"), excelResult.get("ISIN(USER_INPUT)")) &&
                compare(dbResult.get("BVD9_NUMBER"), excelResult.get("ORBIS_ID")) &&
                compare(dbResult.get("SECTOR"), excelResult.get("SECTOR")) &&
                compare(dbResult.get("REGION"), excelResult.get("REGION")) &&
                compare(dbResult.get("AS_OF_DATE"),excelResult.get("Portfolio Upload Date"));

        return result;
    }

    public boolean verifyPhysicalRiskInfo(Map<String,String> excelResult, Map<String,Object> dbResult) {

        boolean result = compare(dbResult.get("RELEASE_DATE").toString().substring(0,10), excelResult.get("Physical Climate Risk: As Of Date")) &&
                compareDoubleValue(dbResult.get("% Facilities Exposed to High Risk and Red Flag").toString(), excelResult.get("% Facilities Exposed to High Risk and Red Flag")) &&
                compare(dbResult.get("Highest Risk Hazard"), excelResult.get("Highest Risk Hazard")) &&
                compareDoubleValue(dbResult.get("ENTITY_SCORE_TOTAL").toString(), excelResult.get("Corporate Physical Climate Risk Score")) &&
                compareDoubleValue(dbResult.get("OPERATIONS_RISK_SCORE").toString(), excelResult.get("Physical Operation Risk Score")) &&
                compareDoubleValue(dbResult.get("SUPPLY_CHAIN_RISK_SCORE").toString(), excelResult.get("Physical Supply Chain Risk Score")) &&
                compareDoubleValue(dbResult.get("MARKET_RISK_SCORE").toString(), excelResult.get("Physical Market Risk Score")) &&
                compareDoubleValue(dbResult.get("Physical Supply Chain Risk:Country of Origin Score").toString(), excelResult.get("Physical Supply Chain Risk:Country of Origin Score")) &&
                compareDoubleValue(dbResult.get("Physical Supply Chain Risk:Resource Demand Score").toString(), excelResult.get("Physical Supply Chain Risk:Resource Demand Score")) &&
                compareDoubleValue(dbResult.get("Physical Market Risk:Weather Sensitivity Score").toString(),excelResult.get("Physical Market Risk:Weather Sensitivity Score")) &&
                compareDoubleValue(dbResult.get("Physical Market Risk:Country of Sales Score").toString(),excelResult.get("Physical Market Risk:Country of Sales Score"));

        return result;
    }

    public boolean verifyPercentExposedInfo(Map<String,String> excelResult) {
        String bvd9Number = excelResult.get("ORBIS_ID");
        boolean result = compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"1","0"),excelResult.get("% Exposed For Heat Stress: No Risk")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"1","1"),excelResult.get("% Exposed For Heat Stress: Low Risk")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"1","2"),excelResult.get("% Exposed For Heat Stress: Medium Risk")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"1","3"),excelResult.get("% Exposed For Heat Stress: High Risk")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"1","4"),excelResult.get("% Exposed For Heat Stress: Red Flag")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"2","0"),excelResult.get("% Exposed For Water Stress: No Risk")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"2","1"),excelResult.get("% Exposed For Water Stress: Low Risk")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"2","2"),excelResult.get("% Exposed For Water Stress: Medium Risk")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"2","3"),excelResult.get("% Exposed For Water Stress: High Risk")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"2","4"),excelResult.get("% Exposed For Water Stress: Red Flag")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"4","0"),excelResult.get("% Exposed For Sea Level Rise: No Risk")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"4","1"),excelResult.get("% Exposed For Sea Level Rise: Low Risk")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"4","2"),excelResult.get("% Exposed For Sea Level Rise: Medium Risk")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"4","3"),excelResult.get("% Exposed For Sea Level Rise: High Risk")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"4","4"),excelResult.get("% Exposed For Sea Level Rise: Red Flag")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"14","0"),excelResult.get("% Exposed For Floods: No Risk")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"14","1"),excelResult.get("% Exposed For Floods: Low Risk")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"14","2"),excelResult.get("% Exposed For Floods: Medium Risk")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"14","3"),excelResult.get("% Exposed For Floods: High Risk")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"14","4"),excelResult.get("% Exposed For Floods: Red Flag")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"12","0"),excelResult.get("% Exposed For Hurricanes and Typhoons: No Risk")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"12","1"),excelResult.get("% Exposed For Hurricanes and Typhoons: Low Risk")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"12","2"),excelResult.get("% Exposed For Hurricanes and Typhoons: Medium Risk")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"12","3"),excelResult.get("% Exposed For Hurricanes and Typhoons: High Risk")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"12","4"),excelResult.get("% Exposed For Hurricanes and Typhoons: Red Flag")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"16","0"),excelResult.get("% Exposed For Wildfires: No Risk")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"16","1"),excelResult.get("% Exposed For Wildfires: Low Risk")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"16","2"),excelResult.get("% Exposed For Wildfires: Medium Risk")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"16","3"),excelResult.get("% Exposed For Wildfires: High Risk")) &&
                compareDoubleValue(dashboardQueries.getPercentExposedData(bvd9Number,"16","4"),excelResult.get("% Exposed For Wildfires: Red Flag"));

        return result;
    }

    public boolean verifyCarbonFootprintInfo(Map<String,String> excelResult, Map<String,Object> dbResult) {

        boolean result = compare(dbResult.get("PRODUCED_DATE"),excelResult.get("Carbon Footprint Produced Date")) &&
                compare(dbResult.get("SCORE_CATEGORY"),excelResult.get("Carbon Footprint Grade")) &&
                compare(dbResult.get("CARBON_FOOTPRINT_VALUE_TOTAL"),excelResult.get("Carbon Footprint Score (scope 1 & 2) (t CO2 eq)")) &&
                compare(dbResult.get("CARBON_FOOTPRINT_VALUE_SCOPE_1"),excelResult.get("Scope 1 (t CO2 eq)")) &&
                compare(dbResult.get("CARBON_FOOTPRINT_VALUE_SCOPE_2"),excelResult.get("Scope 2 (t CO2 eq)")) &&
                compare(dbResult.get("CARBON_FOOTPRINT_VALUE_SCOPE_3"),excelResult.get("Scope 3 (t CO2 eq)"));

        return result;
    }

    public boolean verifyGreenShareInfo(Map<String,String> excelResult, Map<String,Object> dbResult) {
        String bvd9Number = dbResult.get("BVD9_NUMBER").toString();
        String year = dbResult.get("YEAR").toString();
        String month = dbResult.get("MONTH").toString();
        boolean result =
                compare(dbResult.get("PRODUCED_DATE"),excelResult.get("Green Share Assessment Produced Date")) &&
                compare(dbResult.get("GS_OVERALL_ASSESSMENT_SCALE_OF_INCORPORATION"),excelResult.get("Green Share Assessment Score")) &&
                compare(dbResult.get("GS_OVERALL_ASSESSMENT_ESTIMATE_OF_INCORPORATION"),excelResult.get("% of Investments in Commercial Activities linked to Green Solutions")) &&
                compare(dbResult.get("GS_AFFORESTATION_ESTIMATE_OF_INCORPORATION"),excelResult.get("Afforestation : Reasonable Estimate of Incorporation")) &&
                compare(dbResult.get("GS_BUILDING_MATERIALS_WATER_EFFICIENCY_ESTIMATE_OF_INCORPORATION"),excelResult.get("Building materials allowing water efficiency : Reasonable Estimate of Incorporation")) &&
                compare(dbResult.get("GS_BUILDING_MATERIALS_WOOD_ESTIMATE_OF_INCORPORATION"),excelResult.get("Building materials from wood : Reasonable Estimate of Incorporation")) &&
                compare(dbResult.get("GS_ELECTRIC_ENGINE_ESTIMATE_OF_INCORPORATION"),excelResult.get("Electric engine : Reasonable Estimate of Incorporation")) &&
                compare(dbResult.get("GS_ELECTRIC_VEHICLES_ESTIMATE_OF_INCORPORATION"),excelResult.get("Electric vehicles : Reasonable Estimate of Incorporation")) &&
                compare(dbResult.get("GS_ENERGY_DEMAND_SIDE_ESTIMATE_OF_INCORPORATION"),excelResult.get("Energy demand-side management : Reasonable Estimate of Incorporation")) &&
                compare(dbResult.get("GS_ENERGY_STORAGE_ESTIMATE_OF_INCORPORATION"),excelResult.get("Energy storage : Reasonable Estimate of Incorporation")) &&
                compare(dbResult.get("GS_GREEN_BUILDINGS_ESTIMATE_OF_INCORPORATION"),excelResult.get("Green buildings : Reasonable Estimate of Incorporation")) &&
                compare(dbResult.get("GS_GREEN_LENDING_ESTIMATE_OF_INCORPORATION"),excelResult.get("Green lending : Reasonable Estimate of Incorporation")) &&
                compare(dbResult.get("GS_HYBRID_ENGINE_ESTIMATE_OF_INCORPORATION"),excelResult.get("Hybrid engine : Reasonable Estimate of Incorporation")) &&
                compare(dbResult.get("GS_HYBRID_VEHICLES_ESTIMATE_OF_INCORPORATION"),excelResult.get("Hybrid vehicles : Reasonable Estimate of Incorporation")) &&
                compare(dbResult.get("GS_RENEWABLE_ENERGY_ESTIMATE_OF_INCORPORATION"),excelResult.get("Renewable energy : Reasonable Estimate of Incorporation")) &&

                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","183"),excelResult.get("Bicycles : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","184"),excelResult.get("Bio-based chemicals : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","187"),excelResult.get("Contaminated site rehabilitation : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","189"),excelResult.get("Electric vehicle technology : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","192"),excelResult.get("Energy from waste : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","194"),excelResult.get("Fuel cell engine : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","199"),excelResult.get("Impact investing : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","200"),excelResult.get("Insulation materials : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","201"),excelResult.get("LED : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","202"),excelResult.get("Materials allowing energy efficiency : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","202"),excelResult.get("Organic fertilizers : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","203"),excelResult.get("Photocatalytic materials : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","204"),excelResult.get("Pollution abatement technology : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","205"),excelResult.get("Rainwater harvesting : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","206"),excelResult.get("Recycling services : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","209"),excelResult.get("Renewable energy technology : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","210"),excelResult.get("Sea water desalination : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","211"),excelResult.get("Services facilitating environmental progress : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","212"),excelResult.get("Smart grid : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","213"),excelResult.get("Smart grid technology : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","214"),excelResult.get("Smart meters : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","215"),excelResult.get("Solar airplane : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","216"),excelResult.get("SRI : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","217"),excelResult.get("Sustainable farming : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","218"),excelResult.get("Sustainable transportation : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","219"),excelResult.get("Sustainably-sourced biofuel : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","220"),excelResult.get("Transportation-sharing services : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","221"),excelResult.get("Waste collection : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","222"),excelResult.get("Waste treatment : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","223"),excelResult.get("Waste-water treatment : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","224"),excelResult.get("Water demand-side management : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","225"),excelResult.get("Water distribution : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","226"),excelResult.get("Water quality preservation : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","227"),excelResult.get("Water treatment : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","228"),excelResult.get("Water treatment chemicals : Reasonable Estimate of Incorporation")) &&
                compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","229"),excelResult.get("Water treatment technology : Reasonable Estimate of Incorporation"));

        return result;
    }

    public boolean verifyBrownShareInfo(Map<String,String> excelResult, Map<String,Object> dbResult) {

        boolean result = compare(dbResult.get("PRODUCED_DATE"),excelResult.get("Brown Share Assessment Produced Date")) &&
                compare(dbResult.get("SCORE_RANGE"),excelResult.get("Brown Share Assessment Score")) &&
                compare(dbResult.get("BS_FOSF_INDUSTRY_REVENUES_ACCURATE_SOURCE"),excelResult.get("% of Overall Revenues derived from Fossil Fuel Activities")) &&
                compareFFI(dbResult.get("BS_FOSF_INDUSTRY_UPSTREAM_THRESHOLD"),dbResult.get("BS_FOSF_INDUSTRY_UPSTREAM"),excelResult.get("Fossil fuels industry - Upstream")) &&
                compareFFI(dbResult.get("BS_FOSF_INDUSTRY_MIDSTREAM_THRESHOLD"),dbResult.get("BS_FOSF_INDUSTRY_MIDSTREAM"),excelResult.get("Fossil fuels industry - Midstream")) &&
                compareFFI(dbResult.get("BS_FOSF_INDUSTRY_GENERATION_THRESHOLD"),dbResult.get("BS_FOSF_INDUSTRY_GENERATION"),excelResult.get("Fossil fuels industry - Generation")) &&
                compare(dbResult.get("BS_FOSF_INDUSTRY_REVENUES"),excelResult.get("Fossil fuels industry revenues")) &&
                compare(dbResult.get("BS_FOSF_INDUSTRY_REVENUES_COAL_RESERVES"),excelResult.get("Fossil fuels reserves - Coal reserves")) &&
                compare(dbResult.get("BS_FOSF_INDUSTRY_REVENUES_OIL_RESERVES"),convertToDecimal(excelResult.get("Fossil fuels reserves - Oil reserves"))) &&
                compare(dbResult.get("BS_FOSF_INDUSTRY_REVENUES_NATURAL_GAS_RESERVES"),convertToDecimal(excelResult.get("Fossil fuels reserves - Natural Gas reserves"))) &&
                compare(dbResult.get("BS_FOSF_COAL_MINING"),convertToDecimal(excelResult.get("Coal mining"))) &&
                compare(dbResult.get("BS_FOSF_THERMAL_COAL_MINING"),excelResult.get("Thermal coal mining")) &&
                compare(dbResult.get("BS_FOSF_COAL_FUELLED_POWER_GEN"),excelResult.get("Coal-fuelled power generation")) &&
                compare(dbResult.get("BS_FOSF_TAR_SAND_N_OIL_SHALE_EXTRACTION_SERVICES"),excelResult.get("Tar sands and oil shale extraction or services")) &&
                compare(dbResult.get("BS_FOSF_OFFSHORE_ARCTIC_DRILLING"),excelResult.get("Offshore arctic drilling")) &&
                compare(dbResult.get("BS_FOSF_ULTRA_DEEP_OFFSHORE"),excelResult.get("Ultra-deep offshore")) &&
                compare(dbResult.get("BS_FOSF_COAL_BED_METHANE"),excelResult.get("Coal-bed methane (coal seam gas)")) &&
                compare(dbResult.get("BS_FOSF_METHANE_HYDRATES"),excelResult.get("Methane hydrates")) &&
                compare(dbResult.get("BS_FOSF_HYDRAULIC_FRACTURING"),excelResult.get("Hydraulic fracturing")) &&
                compare(dbResult.get("BS_FOSF_LIQUEFIED_NATURAL_GAS"),excelResult.get("Liquefied Natural Gas (LNG)")) &&
                compare(dbResult.get("BS_PEM_TOTAL_POTENTIAL_EMISSIONS"),excelResult.get("Total Potential Emissions")) &&
                compare(dbResult.get("BS_PEM_TOTAL_COAL_POTENTIAL_EMISSIONS"),excelResult.get("Total Coal Potential Emissions")) &&
                compare(dbResult.get("BS_PEM_TOTAL_NATURAL_GAS_POTENTIAL_EMISSIONS"),excelResult.get("Total Natural Gas Potential Emissions")) &&
                compare(dbResult.get("BS_PEM_TOTAL_OIL_POTENTIAL_EMISSIONS"),excelResult.get("Total Oil Potential Emissions"));

        return result;
    }

    public boolean verifyTemperatureAlignmentInfo(Map<String,String> excelResult, Map<String,Object> dbResult) {

        boolean result = compare(dbResult.get("PRODUCED_DATE"),excelResult.get("Temperature Alignment Produced Date")) &&
                compare(dbResult.get("APPROACH"),excelResult.get("Approach")) &&
                compare(dbResult.get("DATA_TYPE"),excelResult.get("Data Type")) &&
                compare(dbResult.get("SCORE_CATEGORY"),excelResult.get("Temperature Alignment")) &&
                compare(dbResult.get("TEMPERATURE_SCORE"),excelResult.get("Implied Temperature Rise")) &&
                compare(dbResult.get("TEMPERATURE_TARGET"),excelResult.get("Implied Temperature Rise Target Year")) &&
                compare(dbResult.get("BASE_YEAR"),excelResult.get("Emissions Reduction Base Year")) &&
                compare(dbResult.get("TARGET_YEAR"),excelResult.get("Emissions Reduction Target Year")) &&
                compare(dbResult.get("TARGET_DISCLOSURE"),excelResult.get("Target Disclosure")) &&
                compare(dbResult.get("TARGET_DESCRIPTION"),excelResult.get("Target Description")) &&
                compare(dbResult.get("SOURCE"),excelResult.get("Source")) &&
                compare(dbResult.get("SCOPE"),excelResult.get("Scope")) &&
                compare(dbResult.get("UNIT"),excelResult.get("Units"));

        return result;
    }

    public List<Map<String,String>> convertExcelToNestedMap(String ExcelFileName) {

        List<Map<String,String>> parentMap = new ArrayList<Map<String,String>>();

        try {
            File file = new File(ExcelFileName);
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            Row headerRow = rowIterator.next();

            while( rowIterator.hasNext() )
            {
                Map<String, String> childMap = new HashMap<String, String>();
                Row row = rowIterator.next();
                for(int i=0; i<headerRow.getLastCellNum(); i++) {
                    String propertyName = headerRow.getCell(i).getStringCellValue();
                    String propertyValue = "";
                    if(row.getCell(i)!=null)
                        propertyValue = new DataFormatter().formatCellValue(row.getCell(i));
                    childMap.put(propertyName, propertyValue);
                }
                parentMap.add(childMap);
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parentMap;
    }

    public boolean compare(Object dbValue, String excelValue){
        String strdbValue = "";
        String strExcelValue = "";
        if(dbValue!=null) {
            strdbValue = dbValue.toString()
                    .replace("_x000D_", "");
            if(strdbValue.equals("-1")) strdbValue="NI";
        }
        if(excelValue!=null) {
            strExcelValue = excelValue.replace("_x000D_", "");
        }
        boolean result = strdbValue.equals(strExcelValue);
        System.out.println("Compare: "+dbValue+" --> "+excelValue+" : "+result);
        return result;
    }

    public boolean compareResearchData(Object dbValue, String excelValue){
        String strdbValue = "";
        String strExcelValue = "";
        if(dbValue==null) {
            strdbValue = "0";
        }else{
            strdbValue = dbValue.toString();
        }
        if(excelValue==null) {
            strExcelValue = "0";
        }else if(excelValue.equals("None")){
            strExcelValue = "0";
        }else{
            strExcelValue = excelValue;
        }
        boolean result = strdbValue.equals(strExcelValue);
        System.out.println("Compare: "+dbValue+" --> "+excelValue+" : "+result);
        return result;
    }

    public boolean compareDoubleValue(String dbValue, String excelValue){
        boolean result = Double.valueOf(dbValue)-Double.valueOf(excelValue)==0;
        System.out.println("Compare: "+dbValue+" --> "+excelValue+" : "+result);
        return result;
    }

    public boolean compareFFI(Object dbThresholdValue, Object dbActualValue, String excelValue){
        String dbValue = "";
        if(dbThresholdValue == null){
            dbValue = "Y";
        }else if(dbThresholdValue != null && String.valueOf(dbThresholdValue).equals("0%")){
            dbValue = "N";
        }else{
            dbValue = "Y";
        }
        boolean result = excelValue.equals(dbValue);
        System.out.println("Compare: "+dbValue+" --> "+excelValue+" : "+result);
        return result;
    }

    public String convertToDecimal(String value){
        //if(value != null && value.length() != 0 && !value.contains(".")) value = value+".00";
        if(value.equals("0")) value = value+".00";
        return value;
    }

}
