package com.esgc.Base.API.Tests;

import com.esgc.Base.API.APIModels.APIFilterPayload;
import com.esgc.Base.API.APIModels.APIFilterPayloadWithImpactFilter;
import com.esgc.Base.API.Controllers.APIController;
import com.esgc.Base.TestBases.APITestBase;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.API;
import static com.esgc.Utilities.Groups.REGRESSION;

public class InvalidAuthorizationTests extends APITestBase {

    @Test(groups = {API, REGRESSION}, dataProvider = "API Research Lines")
    @Xray(test = {3324, 3325, 3326, 3327, 3328, 3329, 3330,  3332})
    //3331 TCFD
    public void invalidTokenAuthorizationTest(String researchLine) {
        APIController apiController = new APIController();
        APIFilterPayload apiFilterPayload = new APIFilterPayload("all", "all", "03", "2021", "");

        String portfolio_id = "002e85b1-4cdc-4bd9-8314-c51bd48ddf61";

        apiController.getPortfolioScoreResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(403);
        apiController.getPortfolioDistributionResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(403);
        apiController.getPortfolioCoverageResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(403);
        apiController.getPortfolioUpdatesResponse(portfolio_id, researchLine, apiFilterPayload).prettyPeek().then().assertThat().statusCode(403);
        apiController.getPortfolioLeadersAndLaggardsResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(403);
        apiController.getPortfolioRegionSummaryResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(403);
        apiController.getPortfolioRegionDetailsResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(403);
        apiController.getPortfolioRegionMapResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(403);
        apiController.getPortfolioSectorSummaryResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(403);
        apiController.getPortfolioSectorDetailsResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(403);
        if (!researchLine.equals("physicalriskmgmt"))
            apiController.getPortfolioUnderlyingDataMetricsResponse(portfolio_id, researchLine, apiFilterPayload).prettyPeek().then().assertThat().statusCode(403);
        if (researchLine.equals("carbonfootprint"))
            apiController.getPortfolioEmissionsResponse(portfolio_id, researchLine, apiFilterPayload).prettyPeek().then().assertThat().statusCode(403);

        APIFilterPayloadWithImpactFilter apiImpactFilterPayload = new APIFilterPayloadWithImpactFilter("all", "all", "03", "2021", "");
        apiController.getImpactDistributionResponse(portfolio_id, researchLine, apiImpactFilterPayload).then().assertThat().statusCode(403);
        apiController.getHistoryTablesResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(403);
    }

    @Test(groups = {API, REGRESSION}, dataProvider = "API Research Lines")
    @Xray(test = {3308, 3309, 3310, 3311, 3312, 3313, 3314,  3316})
    //3315 TCFD
    public void noTokenAuthorizationTest(String researchLine) {
        APIController apiController = new APIController();
        apiController.setInvalid();
        APIFilterPayload apiFilterPayload = new APIFilterPayload("all", "all", "03", "2021", "");

        String portfolio_id = "002e85b1-4cdc-4bd9-8314-c51bd48ddf61";

        apiController.getPortfolioScoreResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(401);
        apiController.getPortfolioDistributionResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(401);
        apiController.getPortfolioCoverageResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(401);
        apiController.getPortfolioLeadersAndLaggardsResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(401);
        if (!researchLine.equals("ESG Assessments")) {
            apiController.getPortfolioUpdatesResponse(portfolio_id, researchLine, apiFilterPayload).prettyPeek().then().assertThat().statusCode(401);
            apiController.getPortfolioRegionSummaryResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(401);
            apiController.getPortfolioRegionDetailsResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(401);
            apiController.getPortfolioRegionMapResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(401);
            apiController.getPortfolioSectorSummaryResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(401);
            apiController.getPortfolioSectorDetailsResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(401);
            if (!researchLine.equals("physicalriskmgmt"))
                apiController.getPortfolioUnderlyingDataMetricsResponse(portfolio_id, researchLine, apiFilterPayload).prettyPeek().then().assertThat().statusCode(401);
            if (researchLine.equals("carbonfootprint"))
                apiController.getPortfolioEmissionsResponse(portfolio_id, researchLine, apiFilterPayload).prettyPeek().then().assertThat().statusCode(401);
            if (researchLine.equals("marketrisk") || researchLine.equals("supplychainrisk") )
                apiController.getPhysicalRiskUnderlyingDataMetricsResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(401);
            APIFilterPayloadWithImpactFilter apiImpactFilterPayload = new APIFilterPayloadWithImpactFilter("all", "all", "03", "2021", "");
            apiController.getImpactDistributionResponse(portfolio_id, researchLine, apiImpactFilterPayload).then().assertThat().statusCode(401);
            apiController.getHistoryTablesResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(401);
        }
        apiController.resetInvalid();
    }
}
