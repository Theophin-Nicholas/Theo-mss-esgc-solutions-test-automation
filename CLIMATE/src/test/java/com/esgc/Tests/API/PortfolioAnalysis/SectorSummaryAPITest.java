package com.esgc.Tests.API.PortfolioAnalysis;

import com.esgc.APIModels.APIFilterPayload;
import com.esgc.APIModels.SectorSummary;
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

/**
 * Created by ChaudhS2 on 11/9/2021.
 */
public class SectorSummaryAPITest extends APITestBase {//870
    @Test(groups = {"api", "regression"}, dataProvider = "API Research Lines")
    @Xray(test = {870, 1923, 1751, 2215, 1184, 2571, 2666})
    public void SectorSummaryAPI_Success(@Optional String researchLine) {
        APIController apiController = new APIController();

        test.info("POST Request sending for Sector Summary");

        APIFilterPayload apiFilterPayload = new APIFilterPayload("all","all","03","2021", "");

        Response response = apiController
                .getPortfolioSectorSummaryResponse(portfolioID, researchLine, apiFilterPayload);
        response.then().assertThat().statusCode(200);
        response.as(SectorSummary[].class);
        response.then().log().all();
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(".", everyItem(is(notNullValue())));

        test.pass("Sector Summary Call Completed Successfully");
    }

    @Test(groups = {"api", "regression"}, dataProvider = "API Research Lines")
    @Xray(test = {875, 1231, })
    public void SectorSummary_InvalidPayload(@Optional String researchLine) {
        APIController apiController = new APIController();

        test.info("POST Request sending for Sector Summary");
        APIFilterPayload apiFilterPayload = new APIFilterPayload(null, null, "03", "2021", "");
        Response response = apiController
                .getPortfolioSectorSummaryResponse(portfolioID, researchLine, apiFilterPayload);
        response.then().assertThat().statusCode(APIUtilities.invalidPayloadStatusCode);

        test.pass("Response received for Sector Summary");
        test.pass("Sector Summary Call Completed Successfully");
    }

    @Test(groups = {"api", "regression"}, dataProvider = "API Research Lines")
    public void SectorSummary_UnauthorisedAccess(@Optional String researchLine) {

        APIController apiController = new APIController();

        String portfolioID = RandomStringUtils.randomAlphanumeric(20);

        test.info("POST Request sending for Sector Summary");
        APIFilterPayload apiFilterPayload = new APIFilterPayload("all", "all", "03", "2021", "");
        Response response = apiController
                .getPortfolioSectorSummaryResponse(portfolioID, researchLine, apiFilterPayload);
        response.then().assertThat().statusCode(403);

        test.pass("Response received for Sector Summary");
        test.pass("Sector Summary Call Completed Successfully");
    }
}
