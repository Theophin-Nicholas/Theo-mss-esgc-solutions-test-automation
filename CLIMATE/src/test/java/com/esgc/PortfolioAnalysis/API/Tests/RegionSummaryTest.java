package com.esgc.PortfolioAnalysis.API.Tests;

import com.esgc.Base.API.APIModels.APIFilterPayload;
import com.esgc.Base.API.Controllers.APIController;
import com.esgc.Base.TestBases.APITestBase;
import com.esgc.PortfolioAnalysis.API.APIModels.RegionSummary;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.API;
import static com.esgc.Utilities.Groups.REGRESSION;

public class RegionSummaryTest extends APITestBase {
//1617,1684

    @Test(groups = {API, REGRESSION}, dataProvider = "No ESG API Research Lines")
    @Xray(test = {1617, 1424,  1684,})
    //1710 Energy Transition
    public void RegionSummary(@Optional String researchLine) {

        APIController apiController = new APIController();
        test.info("POST Request sending for Region Summary");

        APIFilterPayload apiFilterPayload = new APIFilterPayload("all", "all", "03", "2023", "");
        Response response = apiController.getPortfolioRegionSummaryResponse(portfolioID, researchLine, apiFilterPayload);
        response.as(RegionSummary[].class);
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
        test.pass("Region Summary Call Completed Successfully");
    }

    //1241//1911
    @Test(groups = {API, REGRESSION}, dataProvider = "API Research Lines")
    @Xray(test = {1241, 1911})
    public void RegionSummary_InvalidPayload(@Optional String researchLine) {

        APIController apiController = new APIController();
        test.info("POST Request sending for Region Summary");
        APIFilterPayload apiFilterPayload = new APIFilterPayload(null, null, "03", "2021", "");
        Response response = apiController.getPortfolioRegionSummaryResponse(portfolioID, researchLine, apiFilterPayload);
        response.then().assertThat().statusCode(APIUtilities.invalidPayloadStatusCode);
        test.pass("Region Summary Call Completed Successfully");
    }
}
