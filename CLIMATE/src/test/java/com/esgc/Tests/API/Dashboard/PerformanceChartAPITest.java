package com.esgc.Tests.API.Dashboard;

import com.esgc.APIModels.APIFilterPayload;
import com.esgc.APIModels.Dashboard.PerformanceChartCompany;
import com.esgc.Controllers.APIController;
import com.esgc.Tests.TestBases.APITestBase;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;


public class PerformanceChartAPITest extends APITestBase {
    @Test(groups = {"api", "regression"}, dataProvider = "API Research Lines2")
    @Xray(test = {8044})
    public void PerformanceChartAPI_Success(@Optional String researchLine) {
        APIController apiController = new APIController();

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

    @Test(groups = {"api", "regression"}, dataProvider = "API Research Lines2")
    @Xray(test = {8045})
    public void PerformanceChartAPI_InvalidPayload(@Optional String researchLine) {
        APIController apiController = new APIController();

        test.info("POST Request sending for Performance Chart");
        APIFilterPayload apiFilterPayload = new APIFilterPayload(null, null, "03", "2021", "");

        Response response = apiController
                .getPerformanceChartList(portfolioID, researchLine, apiFilterPayload, "largest_holdings", "10");
        response.then().assertThat().body(".", is(empty()));

        response = apiController
                .getPerformanceChartList(portfolioID, researchLine, apiFilterPayload, "leaders", "10");
        response.then().assertThat().body(".", is(empty()));

        response = apiController
                .getPerformanceChartList(portfolioID, researchLine, apiFilterPayload, "laggards", "10");
        response.then().assertThat().body(".", is(empty()));


        test.pass("Response received for Performance Chart");
        test.pass("Performance Chart Call Completed Successfully");
    }

    @Test(groups = {"api", "regression"}, dataProvider = "API Research Lines2")
    @Xray(test = {8045})
    public void PerformanceChart_UnauthorisedAccess(@Optional String researchLine) {

        APIController apiController = new APIController();

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
