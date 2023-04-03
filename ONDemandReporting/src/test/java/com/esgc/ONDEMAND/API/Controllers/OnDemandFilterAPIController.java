package com.esgc.ONDEMAND.API.Controllers;


import com.esgc.Common.API.Controllers.CommonAPIController;
import com.esgc.ONDEMAND.API.EndPoints.OnDemandEndpoints;
import io.restassured.response.Response;

import java.util.List;


public class OnDemandFilterAPIController extends CommonAPIController {



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
        System.out.println(response != null ? response.prettyPrint() : null);
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
        System.out.println(response != null ? response.prettyPrint() : null);
        return response;
    }

    public String getLandingPage(String portfolioName){
        String landingPage;
        String portfolioId = getPortfolioId(portfolioName);
        Response response = CommonAPIController.getDashboardCoverage(portfolioId);
        if (!response.jsonPath().getBoolean("assessment_requested")){
            landingPage = "filterPage" ;
        }else{
            landingPage = "batchProcessingPage" ;
        }
        return landingPage ;
    }










}
