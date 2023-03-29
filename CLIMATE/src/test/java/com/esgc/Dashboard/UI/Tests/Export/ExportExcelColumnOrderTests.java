package com.esgc.Dashboard.UI.Tests.Export;

import com.esgc.Base.API.Controllers.APIController;
import com.esgc.Base.TestBases.APITestBase;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.Utilities.ExcelUtil;
import com.esgc.Utilities.Xray;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static com.esgc.Utilities.Groups.*;

public class ExportExcelColumnOrderTests extends APITestBase {

    @Test(groups = {DASHBOARD, REGRESSION, UI})
    @Xray(test = {9794})
    public void checkColumnsOrderIsBasedOnEntitlementsOrder() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.downloadDashboardExportFile();

        // Read the data from Excel File
        String filePath = dashboardPage.getDownloadedCompaniesExcelFilePath();
        ExcelUtil excel = new ExcelUtil(filePath, "Data - All research lines");
        List<String> excelColumns = excel.getColumnsNames();

        APIController controller = new APIController();
        Response response = controller.getEntitlementHandlerResponse();
        response.then().assertThat().statusCode(200);

        JsonPath jsonPathEvaluator = response.jsonPath();
        List<String> entitlements = jsonPathEvaluator.getList("entitlements.name");
        System.out.println(entitlements);

        verifyColumnsOrderWithEntitlements(excelColumns, entitlements);

        dashboardPage.deleteDownloadFolder();
    }

    public void verifyColumnsOrderWithEntitlements(List<String> excelColumns, List<String> entitlements){

        for(int i=0; i<entitlements.size()-1; ){

            while(i<=(entitlements.size()-2) && getColumnName(entitlements.get(i)).isEmpty()){
                i++;
            }
            if(i>entitlements.size()-2){break;}
            String entColumnName1 = getColumnName(entitlements.get(i));
            i++;

            while(i<=(entitlements.size()-1) && getColumnName(entitlements.get(i)).isEmpty() && i<entitlements.size()-1){
                i++;
            }
            if(i>entitlements.size()-1){break;}
            String entColumnName2 = getColumnName(entitlements.get(i));

            int entColumnIndex1 = excelColumns.indexOf(entColumnName1);
            int entColumnIndex2 = excelColumns.indexOf(entColumnName2);

            assertTestCase.assertTrue(entColumnIndex1<entColumnIndex2, "Columns order is based on Entitlements Order");
        }
    }

    public String getColumnName(String entitlementName){
        String columnName = "";
        switch (entitlementName){
            case "Export":
                columnName = "";
                break;
            case "Physical Risk":
                columnName = "Physical Climate Risk: As Of Date";
                break;
            case "Transition Risk":
                columnName = "Temperature Alignment";
                break;
            case "Controversies Data":
                columnName = "";
                break;
            case "Corporates ESG Data and Scores":
                columnName = "Overall Score";
                break;
        }
        return columnName;
    }


}
