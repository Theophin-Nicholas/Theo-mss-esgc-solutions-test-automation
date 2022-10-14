package com.esgc.Tests.API.PortfolioAnalysis;

import com.esgc.APIModels.APIFilterPayloadWithImpactFilter;
import com.esgc.APIModels.ImpactDistributionWrappers;
import com.esgc.Controllers.APIController;
import com.esgc.Tests.TestBases.APITestBase;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class ImpactTableTests extends APITestBase {

    @Test(groups = {"api", "regression"}, dataProvider = "No ESG API Research Lines")
    @Xray(test = {4957})
    public void getImpactDistributionSuccess(String researchLine) {
        test.info("Tests ESGCA-4957");
        APIController apiController = new APIController();
        APIFilterPayloadWithImpactFilter apiFilterPayload = new APIFilterPayloadWithImpactFilter("all", "all", "03", "2021", "top5");
        Response response = apiController.getImpactDistributionResponse(portfolioID,researchLine,apiFilterPayload);
        List<ImpactDistributionWrappers> impactDistribution = Arrays.asList(response.getBody().as(ImpactDistributionWrappers[].class));
        response.then().assertThat().statusCode(200);
        Assert.assertTrue(impactDistribution.size()>0);
    }

    @Test(groups = {"api", "regression"}, dataProvider = "API Research Lines")
    @Xray(test = {5011})
    public void getImpactDistributionInvalidPayload(String researchLine) {
        // API Returns 200 instead of 500
        test.info("Tests ESGCA-5011");
        APIController apiController = new APIController();
        APIFilterPayloadWithImpactFilter apiFilterPayload = new APIFilterPayloadWithImpactFilter("", "", "", "", "xxx");
        Response response = apiController.getImpactDistributionResponse(portfolioID,researchLine,apiFilterPayload);
        System.out.println("STatus Code" + response.statusCode());
    }

    @Test(groups = {"api", "regression"}, dataProvider = "No ESG API Research Lines")
    @Xray(test = {5010})
    public void getImpactDistributionInvalidToken(String researchLine) {
        test.info("Tests ESGCA-5010");
        APIFilterPayloadWithImpactFilter apiFilterPayload = new APIFilterPayloadWithImpactFilter("all", "all", "03", "2021", "top5");
        APIController apiController = new APIController();
        apiController.setInvalid();
        String portfolio_id = "002e85b1-4cdc-4bd9-8314-c51bd48ddf61";
        apiController.getImpactDistributionResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(401);
        apiController.resetInvalid();
    }

}
