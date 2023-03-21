package com.esgc.Dashboard.DB.Tests;

import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.Utilities.APIUtilities;
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
    // TODO: update queries (db methods should take month and year)
    @Test(groups = {REGRESSION, DASHBOARD})
    @Xray(test = {6218, 6385, 6386, 11049})
    public void verifyInvestmentsAndControversies() throws ParseException {
        Response portfoliosResponse = APIUtilities.getAvailablePortfoliosForUser();
        JsonPath jsonPathEvaluator = portfoliosResponse.jsonPath();
        List<String> portfolioIds = jsonPathEvaluator.getList("portfolios.portfolio_id");
        String portfolioId =portfolioIds.get(portfolioIds.size()-1).toString();

        Response response  = dashboardAPIController.getPortfolioSummaryCompanies(portfolioId);

        Map<String, Object> map = response.then().extract().path(".");
        JSONObject jsonObject = new JSONObject(map);

        Iterator<String> keys = jsonObject.keySet().iterator();
        List<String> sectorNames = new ArrayList<>();
        while (keys.hasNext()) {
            sectorNames.add(keys.next());
        }

        List<List<Object>> totalAPIList = new ArrayList<>();
        for (int i = 0; i < sectorNames.size(); i++) {
            List<String> entitySize = response.then().extract().path("'" + sectorNames.get(i) + "'.entities");
            for (int j = 0; j < entitySize.size(); j++) {
                String companyName = response.then().extract().path("'" + sectorNames.get(i) + "'.entities[" + j + "].company_name").toString();

                // Verify companies investment percentage
                String apiPercentageInvestment = response.then().extract().path("'" + sectorNames.get(i) + "'.entities[" + j + "].perc_investment").toString();
                String dbPercentageInvestment = dashboardQueries.getCompanyInvestmentPercentage(portfolioId, companyName);
                System.out.println(companyName+"--> API:"+apiPercentageInvestment+" -- DB:"+dbPercentageInvestment);
                assertTestCase.assertTrue(apiPercentageInvestment.equals(dbPercentageInvestment), companyName+" investment percentage verification");

                // Verify companies total controversies count
                int apiTotalControversies = Integer.parseInt(response.then().extract().path("'" + sectorNames.get(i) + "'.entities[" + j + "].controversies_total").toString());
                int dbTotalControversies = dashboardQueries.getCompanyTotalControversies(portfolioId, companyName);
                System.out.println(companyName+"-TotalControversies--> API:"+apiTotalControversies+" -- DB:"+dbTotalControversies);
                assertTestCase.assertEquals(apiTotalControversies, dbTotalControversies, companyName+" total controversies verification");

                // Verify companies critical controversies count
                int apiCriticalControversies = Integer.parseInt(response.then().extract().path("'" + sectorNames.get(i) + "'.entities[" + j + "].controversies_critical").toString());
                int dbCriticalControversies = dashboardQueries.getCompanyCriticalControversies(portfolioId, companyName);
                System.out.println(companyName+"-CriticalControversies--> API:"+apiCriticalControversies+" -- DB:"+dbCriticalControversies);
                assertTestCase.assertEquals(apiCriticalControversies, dbCriticalControversies, companyName+" critical controversies verification");

            }
        }

    }


/*
    // to be de-scoped remove esg content. validate with furkan ???
    @Test(groups = {DASHBOARD, REGRESSION, UI, SMOKE, ESG})
    @Xray(test = {8320, 8321})
    public void verifyCoverageAndEsgInfo(){
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("TestEsgScores");

        // ESGCA-8321: Verify Summary Companies Panel Hyperlink is Changed
        dashboardPage.clickViewCompaniesAndInvestments();
        assertTestCase.assertTrue(dashboardPage.isExportButtonEnabled(), "Verify Export button is available");

        // ESGCA-8320: Verify that newly added ESG columns are displayed on Company list drawer
        dashboardPage.selectViewByRegion();
        // remove to be de-scoped
      //  assertTestCase.assertTrue(dashboardPage.verifyViewByRegionTableColumns("ESG Score"), "Verify ESG Score Column is available");
      //  assertTestCase.assertTrue(dashboardPage.verifyEsgInfo(), "Verify ESG Info of listed companies");
     //   System.out.println("VIEW BY SECTOR ");
      //  dashboardPage.selectViewBySector();
      //  assertTestCase.assertTrue(dashboardPage.verifyViewByRegionTableColumns("ESG Score"), "Verify ESG Score Column is available");
        assertTestCase.assertTrue(dashboardPage.verifyEsgInfo(), "Verify ESG Info of listed companies");//TODO randomly failed
        dashboardPage.closePortfolioExportDrawer();
    }*/

}
