package com.esgc.RegulatoryReporting.API.Controllers;

import com.esgc.Base.API.Controllers.APIController;
import com.esgc.RegulatoryReporting.API.RegulatoryReportingEndPoints;
import com.esgc.Utilities.DateTimeUtilities;
import io.restassured.response.Response;

import java.sql.Timestamp;
import java.util.List;


public class RegulatoryReportingAPIController extends APIController {
    boolean isInvalidTest = false;
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

    public Response getAysncGenerationAPIReposnse(String portfolioID, String year, String requestType) {
        Response response = null;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
       String CurrDate =  DateTimeUtilities.getCurrentDate("MM_dd_yyyy") ;
        String timeStamp = String.valueOf(timestamp.getTime());
        String body = "";
        if (requestType.contains("Invalid")){
            body = "{\n" +
                    "    \"report_name\": \"SFDR_Interim" + CurrDate + "_" + timeStamp +"\",\n" +
                    "    \"regulation\": \"sfdr\",\n" +
                    "    \"report_type\": \"interim\",\n" +
                    "    \"use_latest_data\": \"0\",\n" +
                    "    \"portfolios\": [\n" +
                    "        {\n" +
                    "            \"portfolio_id\": ,\n" +
                    "            \"year\": \""+year+"\",\n" +
                    "            \"file_name\": \"SFDR_0_" + CurrDate +"\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";
        }else{
            body = "{\n" +
                    "    \"report_name\": \"SFDR_Interim" + CurrDate + "_" + timeStamp +"\",\n" +
                    "    \"regulation\": \"sfdr\",\n" +
                    "    \"report_type\": \"interim\",\n" +
                    "    \"use_latest_data\": \"0\",\n" +
                    "    \"portfolios\": [\n" +
                    "        {\n" +
                    "            \"portfolio_id\": \""+portfolioID+"\",\n" +
                    "            \"year\": \""+year+"\",\n" +
                    "            \"file_name\": \"SFDR_0_" + CurrDate +"\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";
        }
        try {
            response = configSpec()
                    .when()
                    .body(body)
                    .log().all()
                    .post(RegulatoryReportingEndPoints.GET_ASYNC_GENERATION);
        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }
    public Response getStatusAPIReposnse(String requestID, String requestType) {
        Response response = null;
        String body = "";
        if (requestType.contains("Invalid")){
            body = "{ \"request_id\": } ";
        }else {
            body = "{ \"request_id\": \""+requestID+"\" } " ;
        }
        try {
            response = configSpec()
                    .when()
                    .body(body)
                    .log().all()
                    .post(RegulatoryReportingEndPoints.GET_STATUS);
        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }

    public Response getDownload(String requestID, String requestType) {
        Response response = null;
        String body = "";
        if (requestType.contains("Invalid")){
            body = "{ \"request_id\": } ";
        }else {
            body = "{ \"request_id\": \""+requestID+"\" } " ;
        }
        try {
            response = configSpec()
                    .when()
                    .body(body)
                    .log().all()
                    .post(RegulatoryReportingEndPoints.GET_DOWNLOAD);
        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }

    public List<String> getPortfolioFields() {
        Response response = getPortfolioDetails();
        List<String> portfolioField = response.jsonPath().getList("benchmark");
        return portfolioField;
    }
}
