package com.esgc.Tests.API.PortfolioAnalysis;

import com.esgc.APIModels.APIFilterPayload;
import com.esgc.APIModels.RegionSummary;
import com.esgc.Controllers.APIController;
import com.esgc.Tests.TestBases.APITestBase;
import com.esgc.Utilities.Xray;
import com.esgc.Utilities.APIUtilities;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class RegionSummaryTest extends APITestBase {
//868,1190

    @Test(groups = {"api", "regression"}, dataProvider = "No ESG API Research Lines")
    @Xray(test = {868, 2147, 1710, 1190,})
    public void RegionSummary(@Optional String researchLine) {

        APIController apiController = new APIController();
        test.info("POST Request sending for Region Summary");

        APIFilterPayload apiFilterPayload = new APIFilterPayload("all", "all", "03", "2021", "");
        Response response = apiController.getPortfolioRegionSummaryResponse(portfolioID, researchLine, apiFilterPayload);
        response.as(RegionSummary[].class);
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
        test.pass("Region Summary Call Completed Successfully");
    }

    //872//1209
    @Test(groups = {"api", "regression"}, dataProvider = "API Research Lines")
    @Xray(test = {872, 1209})
    public void RegionSummary_InvalidPayload(@Optional String researchLine) {

        APIController apiController = new APIController();
        test.info("POST Request sending for Region Summary");
        APIFilterPayload apiFilterPayload = new APIFilterPayload(null, null, "03", "2021", "");
        Response response = apiController.getPortfolioRegionSummaryResponse(portfolioID, researchLine, apiFilterPayload);
        response.then().assertThat().statusCode(APIUtilities.invalidPayloadStatusCode);
        test.pass("Region Summary Call Completed Successfully");
    }
}
