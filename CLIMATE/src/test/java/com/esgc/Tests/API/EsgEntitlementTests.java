package com.esgc.Tests.API;

import com.esgc.Tests.TestBases.DataValidationTestBase;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

public class EsgEntitlementTests extends DataValidationTestBase {

    @Test(groups = {"api", "regression"})
    @Xray(test=8375)
    public void VerifyEsgCoverageAvailable() {
        getExistingUsersAccessTokenFromUI();
        Response portfoliosResponse = APIUtilities.getAvailablePortfoliosForUser();
        JsonPath jsonPathEvaluator = portfoliosResponse.jsonPath();
        List<String> portfolioIds = jsonPathEvaluator.getList("portfolios.portfolio_id");
        String portfolioId =portfolioIds.get(portfolioIds.size()-1).toString();
        dashboardAPIController.getEsgCoverageScore(portfolioId,"","all","all", "2022","06").then().assertThat().statusCode(200);
    }

    @Test(groups = {"api", "regression"})
    @Xray(test=8376)
    public void VerifyControversiesNotAvailableBundle() {
        getNoEsgBundleAccessTokenDataValidation();
        Response portfoliosResponse = APIUtilities.getAvailablePortfoliosForUser();
        JsonPath jsonPathEvaluator = portfoliosResponse.jsonPath();
        List<String> portfolioIds = jsonPathEvaluator.getList("portfolios.portfolio_id");
        String portfolioId =portfolioIds.get(portfolioIds.size()-1).toString();
        dashboardAPIController.getEsgCoverageScore(portfolioId,"","all","all", "2022","06").then().assertThat().statusCode(403);
    }
}
