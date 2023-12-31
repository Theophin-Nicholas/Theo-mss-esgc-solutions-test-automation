package com.esgc.PortfolioAnalysis.API.Tests;

import com.esgc.Base.API.APIModels.APIFilterPayload;
import com.esgc.Base.API.Controllers.APIController;
import com.esgc.Base.TestBases.APITestBase;
import com.esgc.PortfolioAnalysis.API.APIModels.PhysicalRiskEntities;
import com.esgc.PortfolioAnalysis.API.APIModels.UnderlyingDataMetricsWrapper;
import com.esgc.PortfolioAnalysis.API.APIModels.UnderlyingDataMetricsWrapperNew;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.API;
import static com.esgc.Utilities.Groups.REGRESSION;
import static org.hamcrest.Matchers.*;

/**
 * Created by ChaudhS2 on 11/12/2021.
 */
public class UnderlyingDataMetricsAPITest extends APITestBase {
    //1278
    @Test(groups = {API, REGRESSION}, dataProvider = "No ESG API Research Lines")
    @Xray(test = {1278, 2091})
    // 3852 TCFD
    // 3117 Energy Transition
    public void UnderlyingDataMetricsAPI_Success(@Optional String researchLine) {
        APIController apiController = new APIController();

        test.info("POST Request sending for UnderLying Data Metrics");

        APIFilterPayload apiFilterPayload = new APIFilterPayload("all", "all", "03", "2021", "");

        if (!researchLine.equals("physicalriskmgmt")) {
            Response response = apiController
                    .getPortfolioUnderlyingDataMetricsResponse(portfolioID, researchLine, apiFilterPayload);
            response.prettyPeek().then().assertThat().statusCode(200);

            response.then().log().all();
            response.then().assertThat()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body(".", everyItem(is(notNullValue())));

            switch (researchLine) {
                case "greenshareasmt":
                case "operationsrisk":
                case "energytransmgmt":
                case "brownshareasmt":
                    response.as(UnderlyingDataMetricsWrapperNew[].class);
                    break;
                default:
                    response.as(UnderlyingDataMetricsWrapper[].class);
                    break;
            }

        }

        test.pass("UnderLying Data Metrics Call Completed Successfully");
    }

    //3118 Energy Transition
    @Test(groups = {API, REGRESSION}, dataProvider = "API Research Lines")
    @Xray(test = { 2077, 1671, 1439, 2141})
    public void UnderlyingDataMetricsAPI_InvalidPayload(@Optional String researchLine) {
        APIController apiController = new APIController();

        test.info("POST Request sending for UnderLying Data Metrics");
        APIFilterPayload apiFilterPayload = new APIFilterPayload(null, null, "03", "2021", "");
        Response response = apiController
                .getPortfolioUnderlyingDataMetricsResponse(portfolioID, researchLine, apiFilterPayload);
        response.then().assertThat().statusCode(APIUtilities.invalidPayloadStatusCode);

        test.pass("Response received for UnderLying Data Metrics");
        test.pass("UnderLying Data Metrics Call Completed Successfully");
    }

    @Test(groups = {API, REGRESSION}, dataProvider = "API Research Lines")
    public void UnderlyingDataMetricsAPI_UnauthorisedAccess(@Optional String researchLine) {

        APIController apiController = new APIController();

        String portfolioID = RandomStringUtils.randomAlphanumeric(20);

        test.info("POST Request sending for UnderLying Data Metrics");
        APIFilterPayload apiFilterPayload = new APIFilterPayload("all", "all", "03", "2021", "");
        Response response = apiController
                .getPortfolioUnderlyingDataMetricsResponse(portfolioID, researchLine, apiFilterPayload);
        response.then().assertThat().statusCode(403);

        test.pass("Response received for UnderLying Data Metrics");
        test.pass("UnderLying Data Metrics Call Completed Successfully");
    }

    @Test(groups = {API, REGRESSION})
    @Xray(test = {3882})
    public void PhysicalRiskUnderlyingDataMetricsAPI() {

        APIController apiController = new APIController();

        // Market Risk - Underlying data data metrics details
        Response marketRiskRresponse = apiController.getPhysicalRiskUnderlyingDataMetricsResponse(APITestBase.portfolioID,"marketrisk");

        marketRiskRresponse.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(".", everyItem(is(notNullValue())));

        marketRiskRresponse.as(PhysicalRiskEntities[].class);

        // Supply Chain Risk - Underlying data data metrics details
        Response supplyRiskRresponse = apiController.getPhysicalRiskUnderlyingDataMetricsResponse(APITestBase.portfolioID,"supplychainrisk");
        supplyRiskRresponse.prettyPeek().then().assertThat().statusCode(200);

        supplyRiskRresponse.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(".", everyItem(is(notNullValue())));

        supplyRiskRresponse.as(PhysicalRiskEntities[].class);

    }

}
