package com.esgc.Tests.API.PortfolioAnalysis;

import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.esgc.APIModels.APIFilterPayload;
import com.esgc.APIModels.PortfolioScore;
import com.esgc.APIModels.PortfolioScoreWrapper;
import com.esgc.Controllers.APIController;
import com.esgc.Tests.TestBases.APITestBase;
import com.esgc.Utilities.Xray;
import com.esgc.Utilities.APIUtilities;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;


public class PortfolioScoreTests extends APITestBase {

    @Test(groups = {"api", "regression"},dataProvider = "API Research Lines")
    @Xray(test = {2245, 1978, 2653, 3009, 2501, 1204})
    public void portfolioScoreSuccess(@Optional String researchLines) {

        String user_id = APIUtilities.userID();
        test.info("Import portfolio for user " + user_id);

        Response response = APIUtilities.importScorePortfolio(user_id);
        test.pass("Response received");

        String portfolioID = response.jsonPath().getString("portfolio_id");

        test.info(MarkupHelper.createCodeBlock(response.statusLine()));
        test.info(MarkupHelper.createCodeBlock(response.body().jsonPath().prettyPrint(), CodeLanguage.JSON));

        APIFilterPayload apiFilterPayload = new APIFilterPayload("all","all","03","2021",portfolioID);
        APIController apiController = new APIController();

        Response portfolioScoreResponse = apiController.getPortfolioScoreResponse(portfolioID,researchLines, apiFilterPayload);
        test.pass("Response received");

        Assert.assertEquals(portfolioScoreResponse.getStatusCode(),200);

        deserializePortfolioScore(researchLines, portfolioScoreResponse);
        test.info("Deserialization successful");
        test.pass("Portfolio score test passed");

    }

    public void deserializePortfolioScore(String researchLine, Response response){

        switch (researchLine){
            case "physicalriskmgmt":
            case "carbonfootprint":
            case "tcfdstrategy":
            case "brownshareasmt":
            case "energytransmgmt":
            case "greenshareasmt":
                response.as(PortfolioScoreWrapper[].class);
                break;


            case "supplychainrisk":
            case "marketrisk":
            case "operationsrisk":
                response.as(PortfolioScore[].class);
                break;

            default: Assert.fail();
        }
    }

    @Test(groups = {"api", "regression"})
    public void portfolioScoreInvalidPortfolioID() {

        String user_id = APIUtilities.userID();
        test.info("Import portfolio for user " + user_id);

        Response response = APIUtilities.importScorePortfolio(user_id);
        test.pass("Response received");

        String portfolioID = response.jsonPath().getString("portfolio_id");

        test.info(MarkupHelper.createCodeBlock(response.statusLine()));
        test.info(MarkupHelper.createCodeBlock(response.body().jsonPath().prettyPrint(), CodeLanguage.JSON));

        APIFilterPayload apiFilterPayload = new APIFilterPayload("all","all","03","2021","");
        APIController apiController = new APIController();

        Response portfolioScoreResponse = apiController.getPortfolioScoreResponse(portfolioID + "111","operationsrisk", apiFilterPayload);
        test.info("Received 403 status for wrong portfolioID");
        test.pass("Response received");

        portfolioScoreResponse.prettyPeek().then().assertThat().statusCode(403);
    }


    @Test(groups = {"api", "regression"},expectedExceptions = NullPointerException.class)
    @Xray(test = {2247,2652,3008,2502,2440,3093,2877,2304,})
    public void portfolioScoreNullPayload() {

        String user_id = APIUtilities.userID();
        test.info("Import portfolio for user " + user_id);

        Response response = APIUtilities.importScorePortfolio(user_id);
        test.pass("Response received");

        test.info(MarkupHelper.createCodeBlock(response.statusLine()));
        test.info(MarkupHelper.createCodeBlock(response.body().jsonPath().prettyPrint(), CodeLanguage.JSON));

        APIController apiController = new APIController();

        Response portfolioScoreResponse = apiController.getPortfolioScoreResponse("XXXX","operationsrisk", null);
        test.info("Received null pointer exception since payload is null");
        test.pass("Response received");

        portfolioScoreResponse.then().assertThat().statusCode(403);
    }
}
