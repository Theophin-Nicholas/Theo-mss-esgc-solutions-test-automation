package com.esgc.Tests.API.PortfolioAnalysis;

import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.esgc.APIModels.APIFilterPayload;
import com.esgc.APIModels.PortfolioCoverageWrapper;
import com.esgc.Controllers.APIController;
import com.esgc.Tests.TestBases.APITestBase;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class PortfolioCoverage extends APITestBase {

    @Test(groups = {"api", "regression"}, dataProvider = "API Research Lines")
    @Xray(test = {1723, 1691, 621, 1221, 652, 2100, 2587, 2422})
    public void portfolioCoverage(@Optional String researchLine) {

        String user_id = APIUtilities.userID();
        APIController apiController = new APIController();

        test.info("Import portfolio for user " + user_id);

        Response response = APIUtilities.importScorePortfolio(user_id);

        test.pass("Response received");

        String portfolioID = response.jsonPath().getString("portfolio_id");
        String portfolioName = response.jsonPath().getString("portfolio_name");

        test.info(MarkupHelper.createCodeBlock(response.statusLine()));
        test.info(MarkupHelper.createCodeBlock(response.body().jsonPath().prettyPrint(), CodeLanguage.JSON));

        test.info("POST Request sending for portfolio coverage data");

        APIFilterPayload apiFilterPayload = new APIFilterPayload("all", "all", "03", "2021", "");

        response = apiController
                .getPortfolioCoverageResponse(portfolioID, researchLine, apiFilterPayload).prettyPeek();
        response.then().assertThat().statusCode(200);

        response.as(PortfolioCoverageWrapper[].class);

        test.pass("Response received");

        test.pass("companies property validated");

        test.pass("Companies parameter is presented in response");

        test.pass("Portfolio Coverage Generated Successfully");
    }
}
