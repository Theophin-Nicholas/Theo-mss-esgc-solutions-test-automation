package com.esgc.Base.API.Tests;

import com.esgc.Base.API.APIModels.APIFilterPayload;
import com.esgc.Base.API.APIModels.APIFilterPayloadWithImpactFilter;
import com.esgc.Base.API.Controllers.APIController;
import com.esgc.Base.TestBases.APITestBase;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.API;
import static com.esgc.Utilities.Groups.REGRESSION;

public class AuthorizationTest extends APITestBase {

    @Test(groups = {API, REGRESSION}, dataProvider = "API Research Lines")
    public void APIAuthorization(String researchLine) {
        APIController apiController = new APIController();
        APIFilterPayload apiFilterPayload = new APIFilterPayload("all", "all", "03", "2021", "");
        APIFilterPayloadWithImpactFilter apiImpactFilterPayload = new APIFilterPayloadWithImpactFilter("all", "all", "03", "2021", "top5");

        String portfolio_id = APIUtilities.importScorePortfolio(APIUtilities.userID()).getBody().jsonPath().get("portfolio_id");
        test.info("User is in " + researchLine);
        test.info("portfolio_id = " + portfolio_id);
        apiController.getPortfolioScoreResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(200);
        apiController.getPortfolioDistributionResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(200);
        apiController.getPortfolioCoverageResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(200);
        apiController.getPortfolioLeadersAndLaggardsResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(200);
        if (!researchLine.equals("ESG Assessments")) {
            apiController.getPortfolioUpdatesResponse(portfolio_id, researchLine, apiFilterPayload).prettyPeek().then().assertThat().statusCode(200);
            apiController.getPortfolioRegionSummaryResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(200);
            apiController.getPortfolioRegionDetailsResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(200);
            apiController.getPortfolioRegionMapResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(200);
            apiController.getPortfolioSectorSummaryResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(200);
            apiController.getPortfolioSectorDetailsResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(200);
            if (researchLine.equals("marketrisk") || researchLine.equals("supplychainrisk"))
                apiController.getPhysicalRiskUnderlyingDataMetricsResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(200);
            else if (!researchLine.equals("physicalriskmgmt") && !researchLine.equals("operationsrisk"))
                apiController.getPortfolioUnderlyingDataMetricsResponse(portfolio_id, researchLine, apiFilterPayload).prettyPeek().then().assertThat().statusCode(200);
            if (researchLine.equals("carbonfootprint"))
                apiController.getPortfolioEmissionsResponse(portfolio_id, researchLine, apiFilterPayload).prettyPeek().then().assertThat().statusCode(200);
            if (!researchLine.equals("marketrisk") && !researchLine.equals("supplychainrisk"))
            apiController.getImpactDistributionResponse(portfolio_id, researchLine, apiImpactFilterPayload).then().assertThat().statusCode(200);
            apiController.getHistoryTablesResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(200);
        }

    }

    //3340 TCFD
    @Xray(test = {3333, 3334, 3335, 3336, 3337, 3338, 3339, 3341})
    @Test(groups = {API, REGRESSION}, dataProvider = "API Research Lines")
    public void invalidPortfolioAuthorizationTest(String researchLine) {
        APIController apiController = new APIController();
        APIFilterPayload apiFilterPayload = new APIFilterPayload("all", "all", "03", "2021", "");

        String portfolio_id = "11c483d4-9f1e-4963-beb7-d0444bc646d1";

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
        APIFilterPayloadWithImpactFilter apiImpactFilterPayload = new APIFilterPayloadWithImpactFilter("all", "all", "03", "2021", "top5");
        apiController.getImpactDistributionResponse(portfolio_id, researchLine, apiImpactFilterPayload).then().assertThat().statusCode(403);
        apiController.getHistoryTablesResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(403);
    }
}
