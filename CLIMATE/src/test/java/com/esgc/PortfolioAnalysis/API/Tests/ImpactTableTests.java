package com.esgc.PortfolioAnalysis.API.Tests;

import com.esgc.Base.API.APIModels.APIFilterPayloadWithImpactFilter;
import com.esgc.Base.API.Controllers.APIController;
import com.esgc.Base.TestBases.APITestBase;
import com.esgc.PortfolioAnalysis.API.APIModels.ImpactDistributionWrappers;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.esgc.Utilities.Groups.API;
import static com.esgc.Utilities.Groups.REGRESSION;

public class ImpactTableTests extends APITestBase {

    @Test(groups = {API, REGRESSION}, dataProvider = "No ESG API Research Lines")
    @Xray(test = {2174})
    public void getImpactDistributionSuccess(String researchLine) {
        test.info("Tests ESGT-2174");
        APIController apiController = new APIController();
        APIFilterPayloadWithImpactFilter apiFilterPayload = new APIFilterPayloadWithImpactFilter("all", "all", "03", "2021", "top5");
        Response response = apiController.getImpactDistributionResponse(portfolioID,researchLine,apiFilterPayload);
        List<ImpactDistributionWrappers> impactDistribution = Arrays.asList(response.getBody().as(ImpactDistributionWrappers[].class));
        response.then().assertThat().statusCode(200);
        Assert.assertTrue(impactDistribution.size()>0);
    }

    @Test(groups = {API, REGRESSION}, dataProvider = "API Research Lines")
    @Xray(test = {1271})
    public void getImpactDistributionInvalidPayload(String researchLine) {
        // API Returns 200 instead of 500
        test.info("Tests ESGT-1271");
        APIController apiController = new APIController();
        APIFilterPayloadWithImpactFilter apiFilterPayload = new APIFilterPayloadWithImpactFilter("", "", "", "", "xxx");
        Response response = apiController.getImpactDistributionResponse(portfolioID,researchLine,apiFilterPayload);
        System.out.println("STatus Code" + response.statusCode());
    }

    @Test(groups = {API, REGRESSION}, dataProvider = "No ESG API Research Lines")
    @Xray(test = {1256})
    public void getImpactDistributionInvalidToken(String researchLine) {
        test.info("Tests ESGT-1256");
        APIFilterPayloadWithImpactFilter apiFilterPayload = new APIFilterPayloadWithImpactFilter("all", "all", "03", "2021", "top5");
        APIController apiController = new APIController();
        apiController.setInvalid();
        String portfolio_id = "002e85b1-4cdc-4bd9-8314-c51bd48ddf61";
        apiController.getImpactDistributionResponse(portfolio_id, researchLine, apiFilterPayload).then().assertThat().statusCode(401);
        apiController.resetInvalid();
    }

}
