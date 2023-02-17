package com.esgc.API.Controllers;

import com.esgc.API.OnDemandEndpoints;
import com.esgc.Utilities.Environment;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static io.restassured.RestAssured.given;


public class OnDemandFilterAPIController {

    private RequestSpecification configSpec() {
        return given().accept(ContentType.JSON)
                .baseUri(Environment.URL)
                .relaxedHTTPSValidation()
                .header("Authorization", "Bearer " + System.getProperty("token"))
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .log().ifValidationFails();
    }

    public Response getCompaniesWithFilter(String portfolioId) {
        Response response = null;
        try {
            response = configSpec()
                    .pathParam("portfolioId", portfolioId)
                    .log().all()
                    .when()
                    .post(OnDemandEndpoints.POST_ON_DEMAND_FILTER).prettyPeek();

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println(response.prettyPrint());
        return response;
    }

    public Response getOnDemandsStatus(String portfolioId) {
        Response response = null;
        try {
            response = configSpec()
                    .pathParam("portfolioId", portfolioId)
                    .log().all()
                    .when()
                    .get(OnDemandEndpoints.GET_ON_DEMAND_STATUS).prettyPeek();

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println(response.prettyPrint());
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

    public Response getPortfolioDetails() {
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .get(OnDemandEndpoints.GET_PORTFOLIO_DETAILS);
        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }

    public Response getDashboardCoverage(String portfolioId) {
        String region = "all";
        String sector = "all";
        String month = "02";
        String year = "2023";
        Response response = null;
        try {
            response = configSpec()
                    .pathParam("portfolioId", portfolioId)
                    .body("{\"region\":\"" + region + "\",\"sector\":\"" + sector + "\",\"month\":\"" + month + "\",\"year\":\"" + year + "\"}")
                    .log().all()
                    .when()
                    .post(OnDemandEndpoints.DASHBOARD_COVERAGE).prettyPeek();

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println(response.prettyPrint());
        return response;
    }

    public String getLandingPage(String portfolioName){
        String landingPage = "";
        String portfolioId = getPortfolioId(portfolioName);
        Response response = getDashboardCoverage(portfolioId);
        if (response.jsonPath().getBoolean("assessment_requested") ==false){
            landingPage = "filterPage" ;
        }else{
            landingPage = "batchProcessingPage" ;
        }
        return landingPage ;
    }

}
