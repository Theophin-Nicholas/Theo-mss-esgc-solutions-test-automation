package com.esgc.PortfolioManagement.DB.DBQueries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.Database.DatabaseDriver.getQueryResultMap;

public class PredictedScoreQueries {

    public ArrayList<String> getPredictedScoredCompanies(String portfolioId) {
        ArrayList<String> companyNames = new ArrayList<>();
        String query = "select DISTINCT COMPANY_NAME from df_target.ESG_OVERALL_SCORES EST\n" +
                "JOIN df_target.df_portfolio ptf on ptf.BVD9_NUMBER = EST.ORBIS_ID\n" +
                "Where portfolio_id = '"+portfolioId+"'\n" +
                "and SUB_CATEGORY = 'ESG'\n" +
                "and DATA_TYPE = 'esg_pillar_score'";
        List<Map<String, Object>> companiesDetails = getQueryResultMap(query);
        for(Map<String, Object> company:companiesDetails){
            companyNames.add(company.get("COMPANY_NAME").toString());
        }
        return companyNames;
    }
}
