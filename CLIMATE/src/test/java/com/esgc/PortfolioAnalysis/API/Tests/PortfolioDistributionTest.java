package com.esgc.PortfolioAnalysis.API.Tests;

import com.esgc.Base.API.APIModels.APIFilterPayload;
import com.esgc.Base.API.Controllers.APIController;
import com.esgc.Base.TestBases.APITestBase;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.API;
import static com.esgc.Utilities.Groups.REGRESSION;
import static io.restassured.RestAssured.requestSpecification;

public class PortfolioDistributionTest extends APITestBase {
    @Test(groups = {API, REGRESSION}, dataProvider = "API Research Lines")
    @Xray(test = { 1523, 2161, 2045, 1484})
    //3106 Energy Transition
    public void APIPortfolioDistributionTest(@Optional String researchLine) {
        String user_id = APIUtilities.userID();
        APIController apiController = new APIController();

        test.info("Import portfolio for user " + user_id);

        APIFilterPayload apiFilterPayload = new APIFilterPayload("all", "all", "03", "2021", "");

        Response response = apiController
                .getPortfolioDistributionResponse(portfolioID, researchLine, apiFilterPayload);
        response.then().assertThat().statusCode(200);
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);

        test.pass("Portfolio Distribution Call Completed Successfully");
    }

    @Test(groups = {API, REGRESSION}, dataProvider = "API Research Lines")
    @Xray(test = {2060,  2014, 1565, 1402})
    //3108 Energy Transition
    public void APIPortfolioDistributionNegativeTest(@Optional String researchLine) {
        String user_id = APIUtilities.userID();
        APIController apiController = new APIController();

        test.info("POST Request sending for Leaders and Laggards");

        APIFilterPayload apiFilterPayload = new APIFilterPayload(null, null, null, null, null);

        Response response = apiController
                .getPortfolioDistributionResponse(portfolioID, researchLine, apiFilterPayload);
        response.then().assertThat().statusCode(APIUtilities.invalidPayloadStatusCode);

        test.pass("Portfolio Distribution Call Completed with invalid body Successfully");
    }

    @Test(groups = {API, REGRESSION}, dataProvider = "API Research Lines")
    public void APIPortfolioDistributionWithInvalidToken(@Optional String researchLine) {
        String user_id = APIUtilities.userID();
        APIController apiController = new APIController();

        test.info("POST Request sending for Leaders and Laggards");

        APIFilterPayload apiFilterPayload = new APIFilterPayload("all", "all", "03", "2021", "");

        apiController.setInvalid();

        requestSpecification = null;
        requestSpecification = new RequestSpecBuilder()
                .build().header("Authorization", "Bearer invalid token");

        Response response = apiController
                .getPortfolioDistributionResponse(portfolioID, researchLine, apiFilterPayload);
        response.then().assertThat().statusCode(500);

        test.pass("Portfolio Distribution Call Completed with invalid Token Successfully");

        apiController.resetInvalid();
    }
}
