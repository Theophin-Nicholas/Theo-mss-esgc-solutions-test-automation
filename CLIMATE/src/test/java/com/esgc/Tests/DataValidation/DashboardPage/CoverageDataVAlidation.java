package com.esgc.Tests.DataValidation.DashboardPage;

import com.esgc.APIModels.APIFilterPayloadWithoutBenchmark;
import com.esgc.APIModels.DashboardModels.DashboardCoverage;
import com.esgc.Controllers.DashboardAPIController;
import com.esgc.Tests.TestBases.DataValidationTestBase;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.Database.DashboardQueries;
import com.esgc.Utilities.Xray;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class CoverageDataVAlidation extends DataValidationTestBase {

    @Test(groups = {"regression", "data_validation", "dashboard"})
    @Xray(test = {3643, 3645,
            8272, //ESG
            11049//Subs
    })
    public void verifyCoverage() {
        Response portfoliosResponse = APIUtilities.getAvailablePortfoliosForUser();
        JsonPath jsonPathEvaluator = portfoliosResponse.jsonPath();
        List<String> portfolioIds = jsonPathEvaluator.getList("portfolios.portfolio_id");
        String portfolioId = portfolioIds.get(portfolioIds.size() - 1).toString();

        APIFilterPayloadWithoutBenchmark apiFilterPayload = new APIFilterPayloadWithoutBenchmark("all", "all", "03", "2022");

        DashboardAPIController dashboardAPIController = new DashboardAPIController();
        DashboardCoverage coverageAPIResponse = dashboardAPIController
                .getCoverageAPIREsponse(portfolioId, apiFilterPayload).as(DashboardCoverage.class);


        DashboardQueries dashboardQueries = new DashboardQueries();
        Map<String, String> dbResult = dashboardQueries.getCoverage(portfolioId, 2022, 03);
        assertTestCase.assertEquals(coverageAPIResponse.getCoverage(),
                dbResult.get("Coverage") + "/" + dbResult.get("TotalCompanies"), "Validating Total coverage");
        assertTestCase.assertEquals(Math.round(coverageAPIResponse.getPerc_investment()), Integer.parseInt(dbResult.get("CoveragePercent")),
                "Validating Total percentage of coverage");

    }
}

