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

    public Response getAysncGenerationAPIReposnse() {
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .body("{\n" +
                            "    \"report_name\": \"SFDR_Interim_03_07_2023_1678212318878\",\n" +
                            "    \"regulation\": \"sfdr\",\n" +
                            "    \"report_type\": \"interim\",\n" +
                            "    \"use_latest_data\": \"0\",\n" +
                            "    \"portfolios\": [\n" +
                            "        {\n" +
                            "            \"portfolio_id\": \"78597308-8e69-4cd5-b8c9-53d64b598e19\",\n" +
                            "            \"year\": \"2023\",\n" +
                            "            \"file_name\": \"SFDR_0_03_07_2023\"\n" +
                            "        }\n" +
                            "    ]\n" +
                            "}")
                    .log().all()
                    .post(RegulatoryReportingEndPoints.GET_ASYNC_GENERATION);
        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }
    public Response getStatusAPIReposnse(String requestID) {
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .body("{ \"request_id\": \""+requestID+"\" } ")
                    .log().all()
                    .post(RegulatoryReportingEndPoints.GET_STATUS);
        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }

    public Response getDownload(String requestID) {
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .body("{ \"request_id\": \""+requestID+"\" } ")
                    .log().all()
                    .post(RegulatoryReportingEndPoints.GET_DOWNLOAD);
        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }


}
