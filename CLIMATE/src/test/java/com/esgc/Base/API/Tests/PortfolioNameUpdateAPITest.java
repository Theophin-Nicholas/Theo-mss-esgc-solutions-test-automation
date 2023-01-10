package com.esgc.Base.API.Tests;

import com.esgc.Base.API.Controllers.APIController;
import com.esgc.Base.TestBases.APITestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.API;
import static com.esgc.Utilities.Groups.REGRESSION;

public class PortfolioNameUpdateAPITest extends APITestBase {

    @Test(groups = {API, REGRESSION})
    public void PortfolioNameChangeAPIValidation() {
        importPortfolioBeforeAPITests();
        APIController apiController = new APIController();

        Response response = apiController.getPortfolioNameUpdateAPIResponse(portfolioID,portfolioName+"Changed");
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }
}