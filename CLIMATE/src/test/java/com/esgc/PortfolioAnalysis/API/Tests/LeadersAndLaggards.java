package com.esgc.PortfolioAnalysis.API.Tests;

import com.esgc.Base.API.APIModels.APIFilterPayload;
import com.esgc.Base.API.Controllers.APIController;
import com.esgc.Base.TestBases.APITestBase;
import com.esgc.PortfolioAnalysis.API.APIModels.LeadersAndLaggardsWrapper;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class LeadersAndLaggards extends APITestBase {

    @Test(groups = {"api", "regression"}, dataProvider = "API Research Lines")
    @Xray(test = {645, 646,9875})
    public void leadersAndLaggards_Success(@Optional String researchLine) {
        APIController apiController = new APIController();

        test.info("POST Request sending for Leaders and Laggards");

        APIFilterPayload apiFilterPayload = new APIFilterPayload("all", "all", "03", "2021", "");

        Response response = apiController
                .getPortfolioLeadersAndLaggardsResponse(portfolioID, researchLine, apiFilterPayload);
        response.then().assertThat().statusCode(200);
        response.as(LeadersAndLaggardsWrapper.class);
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("leaders", everyItem(is(notNullValue())))
                .body("laggards", everyItem(is(notNullValue())));

        test.pass("Leaders and Laggards Call Completed Successfully");
    }

    @Test(groups = {"api", "regression"}, dataProvider = "API Research Lines")
    @Xray(test = {1168, 734, 1169, 2440,  2877})
    //3093 Energy Transition
    public void leadersAndLaggards_InvalidPayload(@Optional String researchLine) {
        APIController apiController = new APIController();

        test.info("POST Request sending for Leaders and Laggards");
        APIFilterPayload apiFilterPayload = new APIFilterPayload(null, null, "03", "2021", "");
        Response response = apiController
                .getPortfolioLeadersAndLaggardsResponse(portfolioID, researchLine, apiFilterPayload);
        response.then().assertThat().statusCode(APIUtilities.invalidPayloadStatusCode);

        test.pass("Response received for Leaders and Laggards");
        test.pass("Leaders and Laggards Call Completed Successfully");
    }
}
