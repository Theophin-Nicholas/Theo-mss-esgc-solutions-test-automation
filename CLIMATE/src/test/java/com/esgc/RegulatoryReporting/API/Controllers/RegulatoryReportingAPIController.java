package com.esgc.RegulatoryReporting.API.Controllers;

import com.esgc.Base.API.Controllers.APIController;
import com.esgc.RegulatoryReporting.API.RegulatoryReportingEndPoints;
import io.restassured.response.Response;

import java.util.List;

public class RegulatoryReportingAPIController extends APIController {
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


        public Response getDashboardPortfolioDetails() {
            Response response = null;
            try {
                response = configSpec()
                        .when()
                        .get(RegulatoryReportingEndPoints.GET_DASHBOARD_COVERAGE);
            } catch (Exception e) {
                System.out.println("Inside exception " + e.getMessage());
            }
            return response;
        }

    public Response getDownloadHistory() {
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .get(RegulatoryReportingEndPoints.GET_DOWNLOAD_HISTORY);
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
            if(portfolioNames.get(i).equals(portfolioName)) {
                System.out.println("Returning portfolio id: " + portfolioIds.get(i));
                return portfolioIds.get(i);
            }
        }
        System.out.println("No portfolios found with matching name. Searching portfolios starts with.");
        for (int i = 0; i < portfolioNames.size(); i++) {
            if(portfolioNames.get(i).startsWith(portfolioName)) {
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

    public List<String> getPortfolioFields() {
        Response response = getPortfolioDetails();
        List<String> portfolioField = response.jsonPath().getList("benchmark");
        return portfolioField;
    }
}
