package com.esgc.PortfolioAnalysis.DB.Tests;

import com.esgc.Base.API.Controllers.APIController;
import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.PortfolioAnalysis.API.APIModels.Entities;
import com.esgc.PortfolioAnalysis.API.APIModels.PhysicalRiskEntities;
import com.esgc.PortfolioAnalysis.DB.DBQueries.UnderlyingDataMetricsQueries;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.Groups.DATA_VALIDATION;
import static com.esgc.Utilities.Groups.REGRESSION;

public class UnderlyingMetricsDetailsTest extends DataValidationTestBase {
//TODO null companies should be removed and we need to extend the coverage with other portfolios
    public static String portfolioId = "00000000-0000-0000-0000-000000000000";

    @Test(groups = {REGRESSION, DATA_VALIDATION})
    @Xray(test = 5049)
    public void verifyMarketRiskUnderlyingDataMetricsDetails() {
        UnderlyingDataMetricsQueries underlyingDataMetricsQueries = new UnderlyingDataMetricsQueries();
        List<Map<String, Object>> dbResults = underlyingDataMetricsQueries.getMarketRiskUnderlyingDataMetricsDetails(portfolioId, "12","2021");


        List<PhysicalRiskEntities> physicalRiskEntities =
                Arrays.asList(controller.getPhysicalRiskUnderlyingDataMetricsResponse(portfolioId, "marketrisk")
                        .as(PhysicalRiskEntities[].class));

        verifyRiskEntities(physicalRiskEntities, dbResults);

    }

    @Test(groups = {REGRESSION, DATA_VALIDATION})
    @Xray(test = 5049)
    public void verifySupplyRiskUnderlyingDataMetricsDetails() {

        UnderlyingDataMetricsQueries underlyingDataMetricsQueries = new UnderlyingDataMetricsQueries();
        List<Map<String, Object>> dbResults = underlyingDataMetricsQueries.getSupplyChainRiskUnderlyingDataMetricsDetails(portfolioId, "05","2022");

        APIController apiController = new APIController();
        Response supplyRiskResponse = apiController.getPhysicalRiskUnderlyingDataMetricsResponse(portfolioId, "supplychainrisk");
        List<PhysicalRiskEntities> physicalRiskEntities = Arrays.asList( supplyRiskResponse.as(PhysicalRiskEntities[].class));

        verifyRiskEntities(physicalRiskEntities, dbResults);

    }

    public void verifyRiskEntities(List<PhysicalRiskEntities> physicalRiskEntities, List<Map<String, Object>> dbResults){
         for(PhysicalRiskEntities pre: physicalRiskEntities){
            if(pre.getRiskEntities09()!=null)
            for(Entities company:pre.getRiskEntities09()){
                assertTestCase.assertTrue(verifyCompanyDetailsInDatabaseResults(dbResults,company), "Check for the company name in DB: "+company.getCompany_name());
            }

            if(pre.getRiskEntities1019()!=null)
            for(Entities company:pre.getRiskEntities1019()){
                assertTestCase.assertTrue(verifyCompanyDetailsInDatabaseResults(dbResults,company), "Check for the company name in DB: "+company.getCompany_name());
            }

            if(pre.getRiskEntities2029()!=null)
            for(Entities company:pre.getRiskEntities2029()){
                assertTestCase.assertTrue(verifyCompanyDetailsInDatabaseResults(dbResults,company), "Check for the company name in DB: "+company.getCompany_name());
            }

            if(pre.getRiskEntities3039()!=null)
            for(Entities company:pre.getRiskEntities3039()){
                assertTestCase.assertTrue(verifyCompanyDetailsInDatabaseResults(dbResults,company), "Check for the company name in DB: "+company.getCompany_name());
            }

            if(pre.getRiskEntities4049()!=null)
            for(Entities company:pre.getRiskEntities4049()){
                assertTestCase.assertTrue(verifyCompanyDetailsInDatabaseResults(dbResults,company), "Check for the company name in DB: "+company.getCompany_name());
            }

            if(pre.getRiskEntities5059()!=null)
            for(Entities company:pre.getRiskEntities5059()){
                assertTestCase.assertTrue(verifyCompanyDetailsInDatabaseResults(dbResults,company), "Check for the company name in DB: "+company.getCompany_name());
            }

            if(pre.getRiskEntities6069()!=null)
            for(Entities company:pre.getRiskEntities6069()){
                assertTestCase.assertTrue(verifyCompanyDetailsInDatabaseResults(dbResults,company), "Check for the company name in DB: "+company.getCompany_name());
            }

            if(pre.getRiskEntities7079()!=null)
            for(Entities company:pre.getRiskEntities7079()){
                assertTestCase.assertTrue(verifyCompanyDetailsInDatabaseResults(dbResults,company), "Check for the company name in DB: "+company.getCompany_name());
            }

            if(pre.getRiskEntities8089()!=null)
            for(Entities company:pre.getRiskEntities8089()){
                assertTestCase.assertTrue(verifyCompanyDetailsInDatabaseResults(dbResults,company), "Check for the company name in DB: "+company.getCompany_name());
            }

            if(pre.getRiskEntities90100()!=null)
            for(Entities company:pre.getRiskEntities90100()){
                assertTestCase.assertTrue(verifyCompanyDetailsInDatabaseResults(dbResults,company), "Check for the company name in DB: "+company.getCompany_name());
            }
        }
    }

    public boolean verifyCompanyDetailsInDatabaseResults(List<Map<String, Object>> dbResults, Entities company){
        boolean check = false;
        List<Map<String, Object>> companiesPercentagesList = dashboardQueries.getCompanyInvestmentPercentageInThePortfolio(portfolioId);
        for(Map<String, Object> record: dbResults){
            if(record.get("COMPANY_NAME").toString().trim().equalsIgnoreCase(company.getCompany_name().trim())){
                String dbPercentageInvestment = companiesPercentagesList.stream()
                        .filter(e -> e.get("ORBIS_ID").toString().equals(company.getOrbis_id())).findFirst().get().get("% Investment").toString();
                if(Double.parseDouble(dbPercentageInvestment) == company.getInvestment_pct())
                    return false;
                check = true;
                break;
            }
        }
        return check;
    }

}
