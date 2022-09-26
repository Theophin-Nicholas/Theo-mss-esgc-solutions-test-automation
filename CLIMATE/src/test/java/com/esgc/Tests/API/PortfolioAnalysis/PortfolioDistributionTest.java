package com.esgc.Tests.API.PortfolioAnalysis;

import com.esgc.APIModels.APIFilterPayload;
import com.esgc.Controllers.APIController;
import com.esgc.Tests.TestBases.APITestBase;
import com.esgc.Utilities.Xray;
import com.esgc.Utilities.APIUtilities;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.requestSpecification;

public class PortfolioDistributionTest extends APITestBase {
    @Test(groups = {"api", "regression"}, dataProvider = "API Research Lines")
    @Xray(test = {3106, 2539, 2697, 2303, 570})
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

    @Test(groups = {"api", "regression"}, dataProvider = "API Research Lines")
    @Xray(test = {2698, 3108, 2540, 2304, 572})
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

    @Test(groups = {"api", "regression"}, dataProvider = "API Research Lines")
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
