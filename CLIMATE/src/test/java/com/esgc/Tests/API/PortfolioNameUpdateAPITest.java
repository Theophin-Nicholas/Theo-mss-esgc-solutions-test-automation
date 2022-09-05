package com.esgc.Tests.API;

import com.esgc.Controllers.APIController;
import com.esgc.Tests.TestBases.APITestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class PortfolioNameUpdateAPITest extends APITestBase {

    @Test(groups = {"api", "regression"})
    public void PortfolioNameChangeAPIValidation() {
        importPortfolioBeforeAPITests();
        APIController apiController = new APIController();

        Response response = apiController.getPortfolioNameUpdateAPIResponse(portfolioID,portfolioName+"Changed");
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }
}