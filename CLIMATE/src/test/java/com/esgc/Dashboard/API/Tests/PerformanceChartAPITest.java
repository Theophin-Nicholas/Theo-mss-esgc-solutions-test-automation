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
}
