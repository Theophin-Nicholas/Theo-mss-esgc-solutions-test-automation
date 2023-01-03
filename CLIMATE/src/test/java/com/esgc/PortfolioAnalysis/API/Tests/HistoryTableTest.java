package com.esgc.PortfolioAnalysis.API.Tests;

import com.esgc.Base.API.APIModels.APIFilterPayload;
import com.esgc.Base.API.Controllers.APIController;
import com.esgc.Base.TestBases.APITestBase;
import com.esgc.PortfolioAnalysis.API.APIModels.HistoryTableWrapper;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.API;
import static com.esgc.Utilities.Groups.REGRESSION;
import static org.hamcrest.Matchers.*;

public class HistoryTableTest extends APITestBase{

        @Test(groups = {API, REGRESSION}, dataProvider = "API Research Lines")
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

        @Test(groups = {API, REGRESSION}, dataProvider = "No ESG API Research Lines")
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

        @Test(groups = {API, REGRESSION}, dataProvider = "API Research Lines")
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
