package com.esgc.Dashboard.UI.Tests.Export;

import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.Dashboard.DB.DBQueries.DashboardQueries;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.Groups.*;

public class PhysicalRiskMgmtInfoTests extends DataValidationTestBase {

    String portfolioId = "00000000-0000-0000-0000-000000000000";
    ExportUtils utils = new ExportUtils();

    @Test(groups = {DASHBOARD, REGRESSION, UI})
    @Xray(test = {6417, 6418, 7104, 9190})
    public void comparePhysicalRiskDataFromExcelToDB() {
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Climate Dashboard");
        test.info("Navigated to Dashboard Page");

        dashboardPage.clickViewCompaniesAndInvestments();
        test.info("Navigated to Companies Page");

        test.info("Verification of Companies Export Based on Sector");
        dashboardPage.selectViewBySector();

        dashboardPage.deleteDownloadFolder();
        dashboardPage.clickExportCompaniesButton();
        test.info("Exported All Companies and Investments Details in excel format");
        dashboardPage.closePortfolioExportDrawer();
//TODO date validation needs to be updated
        assertTestCase.assertTrue(dashboardPage.isCompaniesAndInvestmentsExcelDownloaded(), "Verify Download of Excel file with Companies and Investments details", 6080);

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
            assertTestCase.assertTrue(verifyPhysicalRiskInfo(excelResult, dbPhysicalRiskInfo.get(0)),excelOrbisId+" Company Physical Risk Info Verification");
            }
        dashboardPage.deleteDownloadFolder();
    }

    @Test(groups = {DASHBOARD, REGRESSION, UI})
    @Xray(test = {9190})
    public void comparePhysicalRiskManagementFromExcelToDB() {
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Climate Dashboard");
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
        List<Map<String,String>> excelResults = utils.convertExcelToNestedMap(filePath);

        // utils.Compare the data from Data Base with Excel File
        DashboardQueries dashboardQueries = new DashboardQueries();
        List<Map<String, Object>> dbPhysicalRiskManagementInfo = dashboardQueries.getPhysicalRiskManagementInfo(portfolioId);

        int i=0;
        for(Map<String, String> excelResult:excelResults){
            i++;
            if(excelResult.get("Physical Risk Management Score Category").isEmpty()){
                continue;
            }
            String excelOrbisId = String.valueOf(excelResult.get("ORBIS_ID"));
            System.out.println("**** Record: "+(i)+" Physical Risk Management Info Verification of Orbis Id: "+excelOrbisId);
            boolean found = false;
            for(int j=0; j<dbPhysicalRiskManagementInfo.size(); j++){
                if(dbPhysicalRiskManagementInfo.get(j).get("BVD9_NUMBER").toString().equals(excelOrbisId)){
                    found = true;
                    assertTestCase.assertTrue(verifyPhysicalRiskManagementInfo(excelResult, dbPhysicalRiskManagementInfo.get(j)),excelOrbisId+" Company Physical Risk Info Verification");
                }
            }
            if(!found){
                assertTestCase.assertTrue(false,excelOrbisId+" Company Physical Risk Management Info is not found in DB");
            }
        }
        dashboardPage.deleteDownloadFolder();
    }

    public boolean verifyPhysicalRiskInfo(Map<String,String> excelResult, Map<String,Object> dbResult) {

        if(excelResult.get("Physical Climate Risk: As Of Date") ==null || excelResult.get("Physical Climate Risk: As Of Date").isEmpty()){
            System.out.println("Physical Climate Risk: As Of Date is null or empty string");
            return false;
        }

        boolean result = utils.compare(dbResult.get("RELEASE_DATE").toString().substring(0,10), excelResult.get("Physical Climate Risk: As Of Date")) &&
                utils.compareDoubleValue(dbResult.get("% Facilities Exposed to High Risk and Red Flag").toString(), excelResult.get("% Facilities Exposed to High Risk and Red Flag")) &&
                utils.compare(dbResult.get("Highest Risk Hazard"), excelResult.get("Highest Risk Hazard")) &&
                utils.compareDoubleValue(dbResult.get("ENTITY_SCORE_TOTAL").toString(), excelResult.get("Corporate Physical Climate Risk Score")) &&
                utils.compareDoubleValue(dbResult.get("OPERATIONS_RISK_SCORE").toString(), excelResult.get("Physical Operation Risk Score")) &&
                utils.compareDoubleValue(dbResult.get("SUPPLY_CHAIN_RISK_SCORE").toString(), excelResult.get("Physical Supply Chain Risk Score")) &&
                utils.compareDoubleValue(dbResult.get("MARKET_RISK_SCORE").toString(), excelResult.get("Physical Market Risk Score")) &&
                utils.compareDoubleValue(dbResult.get("Physical Supply Chain Risk:Country of Origin Score").toString(), excelResult.get("Physical Supply Chain Risk:Country of Origin Score")) &&
                utils.compareDoubleValue(dbResult.get("Physical Supply Chain Risk:Resource Demand Score").toString(), excelResult.get("Physical Supply Chain Risk:Resource Demand Score")) &&
                utils.compareDoubleValue(dbResult.get("Physical Market Risk:Weather Sensitivity Score").toString(),excelResult.get("Physical Market Risk:Weather Sensitivity Score")) &&
                utils.compareDoubleValue(dbResult.get("Physical Market Risk:Country of Sales Score").toString(),excelResult.get("Physical Market Risk:Country of Sales Score"));

        return result;
    }

    public boolean verifyPhysicalRiskManagementInfo(Map<String,String> excelResult, Map<String,Object> dbResult) {

        boolean result = utils.compare(dbResult.get("PRODUCED_DATE"),excelResult.get("Physical Risk Management Produced Date")) &&
                utils.compare(dbResult.get("SCORE_CATEGORY"),excelResult.get("Physical Risk Management Score Category")) &&
                utils.compare(dbResult.get("GS_PH_RISK_MGT_TOTAL"),excelResult.get("Physical Risk Management Score")) &&
                utils.compare(dbResult.get("GS_PH_RISK_MGT_LEADERSHIP"),excelResult.get("Physical Risk Management - Leadership Score")) &&
                utils.compare(dbResult.get("GS_PH_RISK_MGT_IMPLEMENTATION"),excelResult.get("Physical Risk Management - Implementation Score")) &&
                utils.compare(dbResult.get("GS_PH_RISK_MGT_RESULTS"),excelResult.get("Physical Risk Management - Results Score"));

        return result;
    }

}
