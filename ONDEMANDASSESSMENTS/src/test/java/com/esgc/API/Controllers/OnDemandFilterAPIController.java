package com.esgc.API.Controllers;

import com.esgc.API.APIModels.Portfolio;
import com.esgc.API.OnDemandEndpoints;
import com.esgc.Utilities.Environment;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<String> getPortfolioIds(String portfolioName) {
        Response response = getPortfolioDetails();
        response.prettyPrint();
        List<Portfolio> portfolios= Arrays.asList(response.as(Portfolio[].class)).stream().
                filter(i -> i.getPortfolio_name().equals(portfolioName)).collect(Collectors.toList());
       List<String> portfolioIds = new ArrayList<>();
       for(Portfolio portfolio : portfolios){
           portfolioIds.add(portfolio.getPortfolio_id());
       }

        return portfolioIds;
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

    public synchronized Response importPortfolio(String user_id, String fileName, String filepath) {
        try {
            return configSpec()
                    .header("Content-Type", "multipart/form-data")
                    .param("userId", user_id)
                    .multiPart("file", fileName, FileUtils.readFileToByteArray(new File(filepath)), "text/csv")
                    .param("filename", "\"" + fileName + "\"")
                    .when()
                    .put(OnDemandEndpoints.IMPORT_PORTFOLIO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String PortfolioUploadFilePath(String fileName){
        return System.getProperty("user.dir")+ File.separator+"src"+
                File.separator+"test"+File.separator+"resources"+
                File.separator+"upload"+File.separator+fileName;
    }

    public  String uploadPortfolio(String inputFile){
       try {
           deletePortfolioIfExists(inputFile);
           String path = PortfolioUploadFilePath(inputFile)+".csv";
           Response response = importPortfolio(Environment.DATA_USERNAME, inputFile+".csv", path);
           return response.jsonPath().getString("portfolio_id");
       }catch(Exception e) {
           e.printStackTrace();
           return "Portfolio couldn't upload";
        }
    }
    public  void deletePortfolioIfExists(String inputFile){
        try {
            List<String> portfolioIDs = getPortfolioIds(inputFile);
            for (String id : portfolioIDs){
                deletePortfolio(id);
            }
        }catch(Exception e) {
            e.printStackTrace();

        }
    }

    public synchronized Response deletePortfolio(String portfolioId) {
        return configSpec()
                .header("Content-Type", "application/json, text/plain, */*")
                .pathParam("portfolio_id", portfolioId)
                .when()
                .delete(OnDemandEndpoints.DELETE_PORTFOLIO).prettyPeek();
    }

}
