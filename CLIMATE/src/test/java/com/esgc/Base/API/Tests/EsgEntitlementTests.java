package com.esgc.Base.API.Tests;

import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static com.esgc.Utilities.Groups.API;
import static com.esgc.Utilities.Groups.REGRESSION;

public class EsgEntitlementTests extends DataValidationTestBase {

    @Test(groups = {API, REGRESSION})
    @Xray(test=8375)
    public void VerifyEsgCoverageAvailable() {
        getExistingUsersAccessTokenFromUI();
        Response portfoliosResponse = APIUtilities.getAvailablePortfoliosForUser();
        JsonPath jsonPathEvaluator = portfoliosResponse.jsonPath();
        List<String> portfolioIds = jsonPathEvaluator.getList("portfolios.portfolio_id");
        String portfolioId =portfolioIds.get(portfolioIds.size()-1).toString();
        dashboardAPIController.getEsgCoverageScore(portfolioId,"","all","all", "2022","06").then().assertThat().statusCode(200);
    }

    @Test(groups = {API, REGRESSION})
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
