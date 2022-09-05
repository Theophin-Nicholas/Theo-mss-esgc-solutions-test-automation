package com.esgc.Tests.API;

import com.esgc.Tests.TestBases.DataValidationTestBase;
import com.esgc.Utulities.APIUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

public class ControversiesEntitlementTests extends DataValidationTestBase {

    @Test(groups = {"api", "regression"})
    @Xray(test=7958)
    public void VerifyControversiesAvailable() {
        Response portfoliosResponse = APIUtilities.getAvailablePortfoliosForUser();
        JsonPath jsonPathEvaluator = portfoliosResponse.jsonPath();
        List<String> portfolioIds = jsonPathEvaluator.getList("portfolios.portfolio_id");
        String portfolioId =portfolioIds.get(portfolioIds.size()-1).toString();
        dashboardAPIController.getControversies(portfolioId,"all","all", "latest","latest").then().assertThat().statusCode(200);
    }

    @Test(groups = {"api", "regression"})
    @Xray(test=7958)
    public void VerifyControversiesNotAvailableBundle() {
        getNoControversiesBundleAccessTokenDataValidation();
        Response portfoliosResponse = APIUtilities.getAvailablePortfoliosForUser();
        JsonPath jsonPathEvaluator = portfoliosResponse.jsonPath();
        List<String> portfolioIds = jsonPathEvaluator.getList("portfolios.portfolio_id");
        String portfolioId =portfolioIds.get(portfolioIds.size()-1).toString();
        dashboardAPIController.getControversies(portfolioId,"all","all","latest","latest").then().assertThat().statusCode(403);
    }
}
