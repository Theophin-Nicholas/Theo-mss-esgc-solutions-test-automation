package com.esgc.Dashboard.UI.Tests.Export;

import com.esgc.Base.TestBases.DashboardUITestBase;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
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
    @Xray(test = {4420})
    public void verifyDataDictionary() {
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

        assertTestCase.assertTrue(dashboardPage.isCompaniesAndInvestmentsExcelDownloaded(), "Verify Download of Excel file with Companies and Investments details", 1867);

        // Read the data from Excel File
        String filePath = dashboardPage.getDownloadedCompaniesExcelFilePath();
        String tabName = "Data Dictionary";

        // ESGT-4420: Validate Export Excel has Physical Risk Management Rows in 'Data Mappings' Sheet
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
    @Xray(test = {4459, 4441})
    public void verifyAllResearchLinesTabInfo() {
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

        assertTestCase.assertTrue(dashboardPage.isCompaniesAndInvestmentsExcelDownloaded(), "Verify Download of Excel file with Companies and Investments details", 1867);

        // Read the data from Excel File
        String filePath = dashboardPage.getDownloadedCompaniesExcelFilePath();
        String tabName = "Data - All research lines";

        // ESGT-4459: Validate Physical Risk Management Columns in 'Data - All research lines' Sheet
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

        // ESGT-4441: Validate newly added Columns in 'Data - All research lines' Sheet

        //ESGCA-9817: Verify the columns Alphanumeric Score and Overall Qualifier is not present
        List<String> deletedColumnsList = new ArrayList<>();
        deletedColumnsList.add("Alphanumeric Score");
        deletedColumnsList.add("Overall Qualifier");
        for(String columnName:deletedColumnsList) {
            assertTestCase.assertTrue(!actualColumnsList.contains(columnName), "Data dictionary details verification");
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
