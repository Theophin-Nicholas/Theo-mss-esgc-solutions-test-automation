package com.esgc.Dashboard.API.Tests;

import com.esgc.Base.API.APIModels.APIFilterPayload;
import com.esgc.Base.TestBases.APITestBase;
import com.esgc.Dashboard.API.APIModels.PerformanceChartCompany;
import com.esgc.Dashboard.API.Controllers.DashboardAPIController;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.Groups.*;
import static com.esgc.Utilities.Groups.SMOKE;
import static org.hamcrest.Matchers.*;


public class PerformanceChartAPITest extends APITestBase {
    @Test(groups = {API, REGRESSION}, dataProvider = "API Research Lines2")
    @Xray(test = {8044})
    public void PerformanceChartAPI_Success(@Optional String researchLine) {
        DashboardAPIController apiController = new DashboardAPIController();

        test.info("POST Request sending for Performance Chart");

        APIFilterPayload apiFilterPayload = new APIFilterPayload("all", "all", "03", "2021", "");

        Response response = apiController
                .getPerformanceChartList(portfolioID, researchLine, apiFilterPayload, "largest_holdings", "10");
        response.then().assertThat().statusCode(200);
        response.as(PerformanceChartCompany[].class);
        response.then().log().all();
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(".", everyItem(is(notNullValue())));

        response = apiController
                .getPerformanceChartList(portfolioID, researchLine, apiFilterPayload, "leaders", "10");
        response.then().assertThat().statusCode(200);
        response.as(PerformanceChartCompany[].class);
        response.then().log().all();
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(".", everyItem(is(notNullValue())));

        response = apiController
                .getPerformanceChartList(portfolioID, researchLine, apiFilterPayload, "laggards", "10");
        response.then().assertThat().statusCode(200);
        response.as(PerformanceChartCompany[].class);
        response.then().log().all();
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(".", everyItem(is(notNullValue())));

        test.pass("Performance Chart Call Completed Successfully");
    }

    @Test(groups = {API, REGRESSION}, dataProvider = "API Research Lines2")
    @Xray(test = {8045})
    public void PerformanceChartAPI_InvalidPayload(@Optional String researchLine) {
        DashboardAPIController apiController = new DashboardAPIController();

        test.info("POST Request sending for Performance Chart");
        APIFilterPayload apiFilterPayload = new APIFilterPayload(null, null, "03", "2021", "");

        Response response = apiController
                .getPerformanceChartList(portfolioID, researchLine, apiFilterPayload, "largest_holdings", "10");
        response.then().assertThat().statusCode(400).and().body("errorType", is("ValueError"))
                .and().body("errorMessage", is("Request Invalid. "));

        response = apiController
                .getPerformanceChartList(portfolioID, researchLine, apiFilterPayload, "leaders", "10");
        response.then().assertThat().statusCode(400).and().body("errorType", is("ValueError"))
                .and().body("errorMessage", is("Request Invalid. "));

        response = apiController
                .getPerformanceChartList(portfolioID, researchLine, apiFilterPayload, "laggards", "10");
        response.then().assertThat().statusCode(400).and().body("errorType", is("ValueError"))
                .and().body("errorMessage", is("Request Invalid. "));


        test.pass("Response received for Performance Chart");
        test.pass("Performance Chart Call Completed Successfully");
    }

    @Test(groups = {API, REGRESSION}, dataProvider = "API Research Lines2")
    @Xray(test = {8045})
    public void PerformanceChart_UnauthorisedAccess(@Optional String researchLine) {

        DashboardAPIController apiController = new DashboardAPIController();

        String portfolioID = RandomStringUtils.randomAlphanumeric(20);

        test.info("POST Request sending for Performance Chart");
        APIFilterPayload apiFilterPayload = new APIFilterPayload("all", "all", "03", "2021", "");

        Response response = apiController
                .getPerformanceChartList(portfolioID, researchLine, apiFilterPayload, "largest_holdings", "10");
        response.then().assertThat().statusCode(403);

        response = apiController
                .getPerformanceChartList(portfolioID, researchLine, apiFilterPayload, "leaders", "10");
        response.then().assertThat().statusCode(403);

        response = apiController
                .getPerformanceChartList(portfolioID, researchLine, apiFilterPayload, "laggards", "10");

        response.then().assertThat().statusCode(403);

        test.pass("Response received for Performance Chart");
        test.pass("Performance Chart Call Completed Successfully");
    }

    @Test(groups = {REGRESSION, UI, DASHBOARD})
    @Xray(test = {12322})
    public void CompareUIandApiHeatMapBrownShareData() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();
        BrowserUtils.wait(5);
        dashboardPage.selectOrDeselectHeatMapSection("Brown Share Assessment");
        BrowserUtils.wait(5);
        dashboardPage.selectOrDeselectHeatMapSection("Overall ESG Score");
        BrowserUtils.wait(5);
        dashboardPage.selectOrDeselectHeatMapSection("Overall ESG Score");
        BrowserUtils.wait(5);

        DashboardAPIController controller = new DashboardAPIController();
        List<Map<String, String>> apiBrownShareHeatMapData = controller.getPortfolioBrownShareData("00000000-0000-0000-0000-000000000000","2022","12");
        List<Map<String, String>> uiBrownShareHeatMapData = dashboardPage.getBrownShareDataFromHeatMapDrawer();
        assertTestCase.assertEquals(apiBrownShareHeatMapData.size(),uiBrownShareHeatMapData.size(),"Company Records Count from both DB and UI are not matching");
        for(int i=0;i<apiBrownShareHeatMapData.size();i++){
            String apiCompanyName = apiBrownShareHeatMapData.get(i).get("COMPANY_NAME");
            System.out.println("Verification of Record: "+i+" - "+apiCompanyName);
            for(int j=0;j<uiBrownShareHeatMapData.size();j++){
                String uiCompanyName = uiBrownShareHeatMapData.get(j).get("COMPANY_NAME");
                if(apiCompanyName.equals(uiCompanyName)){
                    String apiScoreRange = apiBrownShareHeatMapData.get(i).get("SCORE_RANGE");
                    String uiScoreRange = uiBrownShareHeatMapData.get(j).get("SCORE_RANGE");
                    System.out.println("API Score Range:"+apiScoreRange+", UI Score Range:"+uiScoreRange);
                    assertTestCase.assertEquals(apiScoreRange,uiScoreRange,apiCompanyName+" Score Ranges are not matching");

                    String apiScoreCategory = apiBrownShareHeatMapData.get(i).get("SCORE_CATEGORY");
                    String uiScoreCategory = uiBrownShareHeatMapData.get(j).get("SCORE_CATEGORY");
                    System.out.println("API Score Category:"+apiScoreCategory+", UI Score Category:"+uiScoreCategory);
                    assertTestCase.assertEquals(apiScoreCategory,uiScoreCategory,apiCompanyName+" Score Category are not matching");
                    break;
                }
                if(j==uiBrownShareHeatMapData.size())
                    assertTestCase.assertTrue(false,apiCompanyName+" Company Name is not available in UIHeatMap Drawer");
            }
        }
    }
}
