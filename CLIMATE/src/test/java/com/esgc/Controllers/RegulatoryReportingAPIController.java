package com.esgc.Controllers;

import com.esgc.APIModels.DashboardModels.APIHeatMapPayload;
import com.esgc.APIModels.Portfolio;
import com.esgc.Utilities.EndPoints.DashboardEndPoints;
import com.esgc.Utilities.EndPoints.RegulatoryReportingEndPoints;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class RegulatoryReportingAPIController extends APIController{
    public Response getPortfolioDetails() {
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .get(RegulatoryReportingEndPoints.GET_PORTFOLIO_DETAILS);
        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }
    public String getPortfolioId(String portfolioName) {
        Response response = getPortfolioDetails();
        List<String> portfolioNames = response.jsonPath().getList("portfolio_name");
        List<String> portfolioIds = response.jsonPath().getList("portfolio_id");
        for (int i = 0; i < portfolioNames.size(); i++) {
            if(portfolioNames.get(i).contains(portfolioName)) {
                System.out.println("Returning portfolio id: " + portfolioIds.get(i));
                return portfolioIds.get(i);
            }
        }
        System.out.println("Portfolio name not found");
        return "";
    }

    public List<String> getPortfolioNames() {
        Response response = getPortfolioDetails();
        List<String> portfolioNames = response.jsonPath().getList("portfolio_name");
        return portfolioNames;
    }
}