package com.esgc.Dashboard.UI.Tests.Export;

import com.esgc.Base.TestBases.DashboardUITestBase;
import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.ExcelUtil;
import com.esgc.Utilities.Xray;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import static com.esgc.Utilities.Groups.*;

public class ExportDashboardDataDictionary extends DashboardUITestBase {

    @Test(groups = {DASHBOARD, REGRESSION, UI})
    @Xray(test = {9185})
    public void verifyDataDictionary() {
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
        String tabName = "Data Dictionary";

        // ESGCA-9185: Validate Export Excel has Physical Risk Management Rows in 'Data Mappings' Sheet
        Map<String,String> dataDictionary = new HashMap<>();
        dataDictionary.put("Score Type","Type of score produced - Analyst-verified or Predicted Score ");
        dataDictionary.put("Physical Risk Management Produced Date","Year, month, day at which Physical Risk Management research was published in the dataset");
        dataDictionary.put("Physical Risk Management Score Category","Physical Risk Management Category mapping to the overall Physical Risk Management Global Score. The Categories are Advanced (60-100), Robust (50-59), Limited (30-49), and Weak (0-29)");
        dataDictionary.put("Physical Risk Management Score","The Global Score - Physical Risk Management assesses how issuers are acting to anticipate, prevent and manage the risks associated with the physical impacts of climate change");
        dataDictionary.put("Physical Risk Management - Leadership Score","Physical Risk Management Leadership assesses assesses the issuer’s vulnerability to the physical impacts of climate change");
        dataDictionary.put("Physical Risk Management - Implementation Score","Physical Risk Management Implementation assesses how the issuer takes adequate measures to mitigate the potential consequences the physical impacts of climate change could have on its activities");
        dataDictionary.put("Physical Risk Management - Results Score","Physical Risks Management Results assesses how the issuer reinforce its capacity to adapt and reduce its exposure to such changes");

        assertTestCase.assertTrue(verifyDataDictionaryData(filePath, tabName, dataDictionary),"Data dictionary details verification");

        dashboardPage.deleteDownloadFolder();
    }

    @Test(groups = {DASHBOARD, REGRESSION, UI})
    @Xray(test = {9188, 9786, 9817})
    public void verifyAllResearchLinesTabInfo() {
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
        String tabName = "Data - All research lines";

        // ESGCA-9188: Validate Physical Risk Management Columns in 'Data - All research lines' Sheet
        ExcelUtil excel = new ExcelUtil(filePath,tabName);
        List<String> actualColumnsList = excel.getColumnsNames();
        List<String> expectedPhysicalRiskColumnsList = new ArrayList<>();
        expectedPhysicalRiskColumnsList.add("Physical Risk Management Produced Date");
        expectedPhysicalRiskColumnsList.add("Physical Risk Management Score Category");
        expectedPhysicalRiskColumnsList.add("Physical Risk Management Score");
        expectedPhysicalRiskColumnsList.add("Physical Risk Management - Leadership Score");
        expectedPhysicalRiskColumnsList.add("Physical Risk Management - Implementation Score");
        expectedPhysicalRiskColumnsList.add("Physical Risk Management - Results Score");
        assertTestCase.assertTrue(actualColumnsList.containsAll(expectedPhysicalRiskColumnsList),"Data dictionary details verification");

        // ESGCA-9786: Validate newly added Columns in 'Data - All research lines' Sheet
        List<String> expectedNewColumnsList = new ArrayList<>();
        expectedNewColumnsList.add("Scored Orbis ID");
        expectedNewColumnsList.add("LEI");
        expectedNewColumnsList.add("Methodology Model Version");
        expectedNewColumnsList.add("Scored Date");
        expectedNewColumnsList.add("Evaluation Year");
        expectedNewColumnsList.add("Overall Score");
        assertTestCase.assertTrue(actualColumnsList.containsAll(expectedNewColumnsList),"Data dictionary details verification");

        //ESGCA-9817: Verify the columns Alphanumeric Score and Overall Qualifier is not present
        List<String> deletedColumnsList = new ArrayList<>();
        deletedColumnsList.add("Alphanumeric Score");
        deletedColumnsList.add("Overall Qualifier");
        for(String columnName:deletedColumnsList) {
            assertTestCase.assertTrue(!actualColumnsList.contains(columnName), "Data dictionary details verification");
        }

        dashboardPage.deleteDownloadFolder();
    }

    @Test(groups = {DASHBOARD, REGRESSION, UI})
    @Xray(test = {9787, 9789})
    public void verifyESGInfoInExcelWhenNoESGEntitlement_Bundle() {
        LoginPage login = new LoginPage();
        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK_TRANSITION_RISK);

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
        String tabName = "Data - All research lines";

        // ESGCA-9787: Validate ESG Columns in 'Data - All research lines' Sheet when no ESG Entitlement
        ExcelUtil excel = new ExcelUtil(filePath,tabName);
        List<String> actualColumnsList = excel.getColumnsNames();
        List<String> expectedNewColumnsList = new ArrayList<>();
        // expectedNewColumnsList.add("Orbis ID");
        expectedNewColumnsList.add("Scored Orbis ID");
        expectedNewColumnsList.add("LEI");
        expectedNewColumnsList.add("Methodology Model Version");
        expectedNewColumnsList.add("Scored Date");
        expectedNewColumnsList.add("Evaluation Year");
        expectedNewColumnsList.add("Overall Score");
        for(String columnName:expectedNewColumnsList) {
            assertTestCase.assertTrue(!actualColumnsList.contains(columnName), "Data dictionary details verification");
        }

        String secondTabName =  "Data Dictionary";
        ExcelUtil dictionarySheet = new ExcelUtil(filePath,secondTabName);
        for(String columnName:expectedNewColumnsList){
            for(int i=0; i<dictionarySheet.rowCount();i++){
                assertTestCase.assertTrue(!dictionarySheet.getCellData(i,0).equals(columnName), "Verify ESG Info in Data Dictionary tab");
            }
        }

        dashboardPage.deleteDownloadFolder();
    }

    public boolean verifyDataDictionaryData(String filePath, String tabName, Map<String,String> dataDictionary) {

        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(tabName);

            for(int i=0; i<dataDictionary.size();i++) {
                String expDataType = dataDictionary.keySet().toArray()[i].toString();
                String expDefinition = dataDictionary.get(expDataType);
                System.out.println("Verification of Data Type: "+expDataType+" , Definition: "+expDefinition);

                boolean found = false;
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    String dataType = row.getCell(0).toString();
                    String definition = row.getCell(1).toString();
                    if(expDataType.equals(dataType) && expDefinition.equals(definition)){
                        found = true;
                        break;
                    }
                }
                if(!found){
                    System.out.println(" -- Failed");
                    return false;
                }
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
