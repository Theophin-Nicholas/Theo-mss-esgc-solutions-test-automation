package com.esgc.Dashboard.UI.Tests.Export;

import com.esgc.Base.TestBases.DataValidationTestBase;

import java.util.Map;


public class GreenShareInfoTests extends DataValidationTestBase {

    String portfolioId = "00000000-0000-0000-0000-000000000000";
    ExportUtils utils = new ExportUtils();

//    @Test(groups = {"dashboard", "regression", "ui"})
//    @Xray(test = {7222, 7223, 7239})
//    public void compareGreenShareInfoFromExcelToDB() {
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
//            System.out.println("Green Share Details Verification");
//            List<Map<String, Object>> dbCompanyGreenShareInfo = dashboardQueries.getCompanyGreenShareInfo(portfolioId, excelOrbisId, month, year);
//            assertTestCase.assertEquals(dbCompanyGreenShareInfo.size(),1,excelOrbisId+" No Green Share details found");
//            assertTestCase.assertTrue(verifyGreenShareInfo(excelResult, dbCompanyGreenShareInfo.get(0)),excelOrbisId+" Green Share Info Verification");
//
//        }
//
//        dashboardPage.deleteDownloadFolder();
//    }

    public boolean verifyGreenShareInfo(Map<String,String> excelResult, Map<String,Object> dbResult) {
        String bvd9Number = dbResult.get("BVD9_NUMBER").toString();
        String year = dbResult.get("YEAR").toString();
        String month = dbResult.get("MONTH").toString();
        boolean result =
                utils.compare(dbResult.get("PRODUCED_DATE"),excelResult.get("Green Share Assessment Produced Date")) &&
                        utils.compare(dbResult.get("GS_OVERALL_ASSESSMENT_SCALE_OF_INCORPORATION"),excelResult.get("Green Share Assessment Score")) &&
                        utils.compare(dbResult.get("GS_OVERALL_ASSESSMENT_ESTIMATE_OF_INCORPORATION"),excelResult.get("% of Investments in Commercial Activities linked to Green Solutions")) &&
                        utils.compare(dbResult.get("GS_AFFORESTATION_ESTIMATE_OF_INCORPORATION"),excelResult.get("Afforestation : Reasonable Estimate of Incorporation")) &&
                        utils.compare(dbResult.get("GS_BUILDING_MATERIALS_WATER_EFFICIENCY_ESTIMATE_OF_INCORPORATION"),excelResult.get("Building materials allowing water efficiency : Reasonable Estimate of Incorporation")) &&
                        utils.compare(dbResult.get("GS_BUILDING_MATERIALS_WOOD_ESTIMATE_OF_INCORPORATION"),excelResult.get("Building materials from wood : Reasonable Estimate of Incorporation")) &&
                        utils.compare(dbResult.get("GS_ELECTRIC_ENGINE_ESTIMATE_OF_INCORPORATION"),excelResult.get("Electric engine : Reasonable Estimate of Incorporation")) &&
                        utils.compare(dbResult.get("GS_ELECTRIC_VEHICLES_ESTIMATE_OF_INCORPORATION"),excelResult.get("Electric vehicles : Reasonable Estimate of Incorporation")) &&
                        utils.compare(dbResult.get("GS_ENERGY_DEMAND_SIDE_ESTIMATE_OF_INCORPORATION"),excelResult.get("Energy demand-side management : Reasonable Estimate of Incorporation")) &&
                        utils.compare(dbResult.get("GS_ENERGY_STORAGE_ESTIMATE_OF_INCORPORATION"),excelResult.get("Energy storage : Reasonable Estimate of Incorporation")) &&
                        utils.compare(dbResult.get("GS_GREEN_BUILDINGS_ESTIMATE_OF_INCORPORATION"),excelResult.get("Green buildings : Reasonable Estimate of Incorporation")) &&
                        utils.compare(dbResult.get("GS_GREEN_LENDING_ESTIMATE_OF_INCORPORATION"),excelResult.get("Green lending : Reasonable Estimate of Incorporation")) &&
                        utils.compare(dbResult.get("GS_HYBRID_ENGINE_ESTIMATE_OF_INCORPORATION"),excelResult.get("Hybrid engine : Reasonable Estimate of Incorporation")) &&
                        utils.compare(dbResult.get("GS_HYBRID_VEHICLES_ESTIMATE_OF_INCORPORATION"),excelResult.get("Hybrid vehicles : Reasonable Estimate of Incorporation")) &&
                        utils.compare(dbResult.get("GS_RENEWABLE_ENERGY_ESTIMATE_OF_INCORPORATION"),excelResult.get("Renewable energy : Reasonable Estimate of Incorporation")) &&

                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","183"),excelResult.get("Bicycles : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","184"),excelResult.get("Bio-based chemicals : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","187"),excelResult.get("Contaminated site rehabilitation : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","189"),excelResult.get("Electric vehicle technology : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","192"),excelResult.get("Energy from waste : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","194"),excelResult.get("Fuel cell engine : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","199"),excelResult.get("Impact investing : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","200"),excelResult.get("Insulation materials : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","201"),excelResult.get("LED : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","202"),excelResult.get("Materials allowing energy efficiency : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","202"),excelResult.get("Organic fertilizers : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","203"),excelResult.get("Photocatalytic materials : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","204"),excelResult.get("Pollution abatement technology : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","205"),excelResult.get("Rainwater harvesting : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","206"),excelResult.get("Recycling services : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","209"),excelResult.get("Renewable energy technology : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","210"),excelResult.get("Sea water desalination : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","211"),excelResult.get("Services facilitating environmental progress : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","212"),excelResult.get("Smart grid : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","213"),excelResult.get("Smart grid technology : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","214"),excelResult.get("Smart meters : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","215"),excelResult.get("Solar airplane : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","216"),excelResult.get("SRI : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","217"),excelResult.get("Sustainable farming : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","218"),excelResult.get("Sustainable transportation : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","219"),excelResult.get("Sustainably-sourced biofuel : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","220"),excelResult.get("Transportation-sharing services : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","221"),excelResult.get("Waste collection : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","222"),excelResult.get("Waste treatment : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","223"),excelResult.get("Waste-water treatment : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","224"),excelResult.get("Water demand-side management : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","225"),excelResult.get("Water distribution : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","226"),excelResult.get("Water quality preservation : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","227"),excelResult.get("Water treatment : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","228"),excelResult.get("Water treatment chemicals : Reasonable Estimate of Incorporation")) &&
                        utils.compareResearchData(dashboardQueries.getResearchData(bvd9Number,month,year,"6","229"),excelResult.get("Water treatment technology : Reasonable Estimate of Incorporation"));

        return result;
    }

}
