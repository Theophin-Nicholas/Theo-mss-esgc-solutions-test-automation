package com.esgc.Dashboard.DB.Tests;

import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.PortfolioUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.snowflake.client.jdbc.internal.net.minidev.json.JSONObject;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.Groups.*;

public class PortfolioCompaniesAndInvestments extends DataValidationTestBase {
    @Test(groups = {REGRESSION, DASHBOARD})
    @Xray(test = {4002, 4122, 3902})
    public void verifyInvestmentsAndControversies() throws ParseException {
        Response portfoliosResponse = APIUtilities.getAvailablePortfoliosForUser();
        JsonPath jsonPathEvaluator = portfoliosResponse.jsonPath();
        List<String> portfolioIds = jsonPathEvaluator.getList("portfolios.portfolio_id");
        String portfolioId = portfolioIds.get(portfolioIds.size() - 1).toString();

        Response response = dashboardAPIController.getPortfolioSummaryCompanies(portfolioId);

        Map<String, Object> map = response.then().extract().path(".");
        JSONObject jsonObject = new JSONObject(map);

        Iterator<String> keys = jsonObject.keySet().iterator();
        List<String> sectorNames = new ArrayList<>();
        while (keys.hasNext()) {
            sectorNames.add(keys.next());
        }
        List<Map<String, Object>> companiesControversiesList = dashboardQueries.getCompanyTotalControversiesInThePortfolio(portfolioId);
        List<Map<String, Object>> companiesCriticalControversiesList = dashboardQueries.getCompanyTotalCriticalControversiesInThePortfolio(portfolioId);
        List<Map<String, Object>> companiesPercentagesList = dashboardQueries.getCompanyInvestmentPercentageInThePortfolio(portfolioId);
        for (int i = 0; i < sectorNames.size(); i++) {
            List<String> entitySize = response.then().extract().path("'" + sectorNames.get(i) + "'.entities");
            for (int j = 0; j < entitySize.size(); j++) {
                String companyName = response.then().extract().path("'" + sectorNames.get(i) + "'.entities[" + j + "].company_name").toString();
                String orbisID = response.then().extract().path("'" + sectorNames.get(i) + "'.entities[" + j + "].bvd9_number").toString();
                // Verify companies investment percentage
                String apiPercentageInvestment = response.then().extract().path("'" + sectorNames.get(i) + "'.entities[" + j + "].perc_investment").toString();

                String dbPercentageInvestment = companiesPercentagesList.stream()
                        .filter(e -> e.get("ORBIS_ID").toString().equals(orbisID)).findFirst().get().get("% Investment").toString();
                dbPercentageInvestment = String.valueOf(PortfolioUtilities.round(Double.parseDouble(dbPercentageInvestment), 2));

                System.out.println(companyName + "--> API:" + apiPercentageInvestment + " -- DB:" + dbPercentageInvestment);
                assertTestCase.assertEquals(apiPercentageInvestment, dbPercentageInvestment, companyName + " investment percentage verification");

                // Verify companies total controversies count
                int apiTotalControversies = Integer.parseInt(response.then().extract().path("'" + sectorNames.get(i) + "'.entities[" + j + "].controversies_total").toString());
                int dbTotalControversies = 0;
                try {
                    dbTotalControversies = Integer.parseInt(companiesControversiesList.stream()
                            .filter(e -> e.get("ORBIS_ID").toString().equals(orbisID)).findFirst().get().get("COUNT").toString());

                } catch (Exception e) {

                }
                System.out.println(companyName + "-TotalControversies--> API:" + apiTotalControversies + " -- DB:" + dbTotalControversies);
                assertTestCase.assertEquals(apiTotalControversies, dbTotalControversies, companyName + " total controversies verification");

                // Verify companies critical controversies count
                int apiCriticalControversies = Integer.parseInt(response.then().extract().path("'" + sectorNames.get(i) + "'.entities[" + j + "].controversies_critical").toString());
                int dbCriticalControversies = 0;
                try {
                    dbCriticalControversies = Integer.parseInt(companiesCriticalControversiesList.stream()
                            .filter(e -> e.get("ORBIS_ID").toString().equals(orbisID)).findFirst().get().get("COUNT").toString());
                } catch (Exception e) {

                }
                System.out.println(companyName + "-CriticalControversies--> API:" + apiCriticalControversies + " -- DB:" + dbCriticalControversies);
                assertTestCase.assertEquals(apiCriticalControversies, dbCriticalControversies, companyName + " critical controversies verification");

            }
        }

    }

    @Test(groups = {DASHBOARD, REGRESSION, UI, SMOKE})
    @Xray(test = {4887})
    public void verifyDashboardCoverageHyperlink() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToDashboardPage();
        // ESGT-4887: Verify Summary Companies Panel Hyperlink is Changed
        dashboardPage.clickViewCompaniesAndInvestments();
        assertTestCase.assertTrue(dashboardPage.isExportButtonEnabled(), "Verify Export button is available");

        dashboardPage.closePortfolioExportDrawer();
    }

}
