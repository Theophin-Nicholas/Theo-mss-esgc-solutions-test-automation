package com.esgc.Tests.API.PortfolioAnalysis;

import com.esgc.APIModels.APIFilterPayload;
import com.esgc.APIModels.HistoryTableWrapper;
import com.esgc.Controllers.APIController;
import com.esgc.Tests.TestBases.APITestBase;
import com.esgc.Utilities.Xray;
import com.esgc.Utilities.APIUtilities;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class HistoryTableTest extends APITestBase{

        @Test(groups = {"api", "regression"}, dataProvider = "API Research Lines")
        @Xray(test = {5107})
        public void getHistoryTableSuccessAPIResponse(String researchLine) {
            APIController apiController = new APIController();

            test.info("Success : History Table Request for " + researchLine + "research line");
            APIFilterPayload apiFilterPayload = new APIFilterPayload("all", "all", "03", "2021", "");
            Response response = apiController
                    .getHistoryTablesResponse(portfolioID, researchLine, apiFilterPayload);
            response.as(HistoryTableWrapper[].class);
            response.then().log().all();
            response.then().assertThat()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body(".", everyItem(is(notNullValue())));

            test.pass("Successfully validated for " + researchLine + "research line");
        }

        @Test(groups = {"api", "regression"}, dataProvider = "No ESG API Research Lines")
        @Xray(test = {5097})
        public void getHistoryTableInvalidPayload(String researchLine) {
            APIController apiController = new APIController();

            test.info("Invalid Payload : History Table API POST request for " + researchLine + "research line");
            APIFilterPayload apiFilterPayload = new APIFilterPayload(null, null, "03", "2021", "");
            Response response = apiController
                    .getHistoryTablesResponse(portfolioID, researchLine, apiFilterPayload);
            response.then().assertThat().statusCode(APIUtilities.invalidPayloadStatusCode);

            test.pass("Successfully validated for " + researchLine + "research line");
        }

        @Test(groups = {"api", "regression"}, dataProvider = "API Research Lines")
        @Xray(test = {5097})
        public void getHistoryTableInvalidToken(String researchLine) {
            APIController apiController = new APIController();

            String portfolioID = RandomStringUtils.randomAlphanumeric(20);
            test.info("Unauthorised Access : History Table API POST request for " + researchLine + "research line");
            APIFilterPayload apiFilterPayload = new APIFilterPayload("all", "all", "03", "2021", "");
            Response response = apiController
                    .getHistoryTablesResponse(portfolioID, researchLine, apiFilterPayload);
            response.then().assertThat().statusCode(403);
            test.pass("Successfully validated for " + researchLine + "research line");   }


    }
