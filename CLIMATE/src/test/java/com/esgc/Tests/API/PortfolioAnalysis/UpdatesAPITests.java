package com.esgc.Tests.API.PortfolioAnalysis;

import com.esgc.APIModels.APIFilterPayload;
import com.esgc.APIModels.PortoflioAnalysisModels.UpdatesModel;
import com.esgc.Controllers.APIController;
import com.esgc.Tests.TestBases.APITestBase;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class UpdatesAPITests extends APITestBase {

    @Test(groups = {"api", "regression"}, dataProvider = "API Research Lines")
    @Xray(test = {1033, 2602, 2609})
    public void updates_Invalid_Payload(@Optional String researchLine) {

        APIController con = new APIController();
        APIFilterPayload apiFilterPayload = new APIFilterPayload("all", "all", "03", null, "");

        Response response = con.getPortfolioUpdatesResponse(portfolioID, researchLine, apiFilterPayload);

        response.then().assertThat().statusCode(APIUtilities.invalidPayloadStatusCode);

        test.pass("Response received");

        test.pass("Response Invalid Payload Verified Successfully");
    }

    @Test(groups = {"api", "regression"}, dataProvider = "No ESG API Research Lines")
    @Xray(test = {2283})
    //2024 TCFD
    //2021 Energy Transition
    public void updates_Success(@Optional String researchLine) {
        APIController con = new APIController();
        APIFilterPayload apiFilterPayload = new APIFilterPayload("all", "all", "03", "2021", "");

        Response response = con.getPortfolioUpdatesResponse(portfolioID, researchLine, apiFilterPayload);
        response.as(UpdatesModel[].class);
        response.then().assertThat().statusCode(200);

        test.pass("Response received");

        test.pass("Updates API Verified Successfully");
    }
}
