package com.esgc.PortfolioAnalysis.API.Tests;

import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.esgc.Base.API.APIModels.APIFilterPayload;
import com.esgc.Base.API.Controllers.APIController;
import com.esgc.Base.TestBases.APITestBase;
import com.esgc.PortfolioAnalysis.API.APIModels.PortfolioScore;
import com.esgc.PortfolioAnalysis.API.APIModels.PortfolioScoreWrapper;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.API;
import static com.esgc.Utilities.Groups.REGRESSION;

public class PortfolioScoreTests extends APITestBase {

    @Test(groups = {API, REGRESSION},dataProvider = "API Research Lines")
    @Xray(test = {1515,  1421,  1260, 1452})
    //1978 TCFD
    //Energy Transition 3009
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
            case "ESG Assessments":
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

    @Test(groups = {API, REGRESSION})
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


    @Test(groups = {API, REGRESSION},expectedExceptions = NullPointerException.class)
    @Xray(test = {1518,1508,1513,1995,1688,1565})
    //3008, 3093 Energy Transition
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
