package com.esgc.Tests.API.PortfolioAnalysis;

import com.esgc.APIModels.APIFilterPayload;
import com.esgc.APIModels.RegionMap;
import com.esgc.Controllers.APIController;
import com.esgc.Tests.TestBases.APITestBase;
import com.esgc.Utilities.Xray;
import com.esgc.Utilities.APIUtilities;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class RegionMapAPITest extends APITestBase {

    //1062
    @Test(groups = {"api", "regression"}, dataProvider = "No ESG API Research Lines")
    @Xray(test = {1062, 2387, 1824, 1802, 2450, 2905, 1267})
    public void RegionMapAPI_Success(@Optional String researchLine) {
        APIController apiController = new APIController();

        test.info("Success : Region Map API POST request for " + researchLine + "research line");
        APIFilterPayload apiFilterPayload = new APIFilterPayload("all", "all", "03", "2021", "");
        Response response = apiController
                .getPortfolioRegionMapResponse(portfolioID, researchLine, apiFilterPayload);
        response.as(RegionMap[].class);
        response.then().log().all();
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(".", everyItem(is(notNullValue())));

        test.pass("Successfully validated for " + researchLine + "research line");
    }


    @Test(groups = {"api", "regression"}, dataProvider = "API Research Lines")
    @Xray(test = {1064,1247})
    public void RegionMap_InvalidPayload(@Optional String researchLine) {
        APIController apiController = new APIController();

        test.info("Invalid Payload : Region Map API POST request for " + researchLine + "research line");
        APIFilterPayload apiFilterPayload = new APIFilterPayload(null, null, "03", "2021", "");
        Response response = apiController
                .getPortfolioRegionMapResponse(portfolioID, researchLine, apiFilterPayload);
        response.then().assertThat().statusCode(APIUtilities.invalidPayloadStatusCode);

        test.pass("Successfully validated for " + researchLine + "research line");
    }

    @Test(groups = {"api", "regression"}, dataProvider = "API Research Lines")
    public void RegionMap_UnauthorisedAccess(@Optional String researchLine) {
        APIController apiController = new APIController();

        String portfolioID = RandomStringUtils.randomAlphanumeric(20);
        test.info("Unauthorised Access : Region Map API POST request for " + researchLine + "research line");
        APIFilterPayload apiFilterPayload = new APIFilterPayload("all", "all", "03", "2021", "");
        Response response = apiController
                .getPortfolioRegionMapResponse(portfolioID, researchLine, apiFilterPayload);
        response.then().assertThat().statusCode(403);
        test.pass("Successfully validated for " + researchLine + "research line");

    }
}