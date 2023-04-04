package com.esgc.Common.API.Controllers;


import com.esgc.Common.API.APIModels.Portfolio;
import com.esgc.Common.API.EndPoints.CommonEndPoints;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Environment;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;


public class CommonAPIController {

    static boolean isInvalidTest = false;

    public static synchronized RequestSpecification configSpec() {
        if (System.getProperty("token") == null) {
            String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.accessToken";
            String accessToken = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
            System.setProperty("token", accessToken);
        }
        if (isInvalidTest) {
            return given().accept(ContentType.JSON)
                    .baseUri(Environment.URL)
                    .relaxedHTTPSValidation()
                    //.header("Authorization", "Bearer 12345678")
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .log().ifValidationFails();
        } else {
            return given().accept(ContentType.JSON)
                    .baseUri(Environment.URL)
                    .relaxedHTTPSValidation()
                    .header("Authorization", "Bearer " + System.getProperty("token"))
                    // .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .log().ifValidationFails();
        }
    }

    public synchronized void setInvalid() {
        this.isInvalidTest = true;
    }

    public synchronized void resetInvalid() {
        this.isInvalidTest = false;
    }

    public synchronized Response deletePortfolio(String portfolioId) {
        return configSpec()
                .header("Content-Type", "application/json, text/plain, */*")
                .pathParam("portfolio_id", portfolioId)
                .when()
                .delete(CommonEndPoints.DELETE_PORTFOLIO).prettyPeek();
    }
    public void deletePortfolioByName(String portfolioName) {
        try {
            List<String> portfolioIDs = getPortfolioIds(portfolioName);
            for (String id : portfolioIDs){
                deletePortfolio(id);
            }
        }catch(Exception e) {
            e.printStackTrace();
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
    public static String PortfolioUploadFilePath(String fileName){
        return System.getProperty("user.dir")+ File.separator+"src"+
                File.separator+"test"+File.separator+"resources"+
                File.separator+"upload"+File.separator+fileName;
    }

    public List<String> getPortfolioIds(String portfolioName) {
        Response response = getPortfolioDetails();
        response.prettyPrint();
        List<Portfolio> portfolios= Arrays.asList(response.as(Portfolio.class)).stream().
                filter(i -> i.getPortfolios().get(0).getPortfolio_name().equals(portfolioName)).collect(Collectors.toList());
        List<String> portfolioIds = new ArrayList<>();
        for(Portfolio portfolio : portfolios){
            portfolioIds.add(portfolio.getPortfolios().get(0).getPortfolio_id());
        }

        return portfolioIds;
    }

    public synchronized Response importPortfolio(String user_id, String fileName, String filepath) {
        try {
            return configSpec()
                    .header("Content-Type", "multipart/form-data")
                    .param("userId", user_id)
                    .multiPart("file", fileName, FileUtils.readFileToByteArray(new File(filepath)), "text/csv")
                    .param("filename", "\"" + fileName + "\"")
                    .when()
                    .put(CommonEndPoints.IMPORT_PORTFOLIO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Response getPortfolioDetails() {
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .log().all()
                    .get(CommonEndPoints.GET_PORTFOLIO_DETAILS);
        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }
    public List<String> getPortfolioNames() {
        Response response = getPortfolioDetails();
        List<String> portfolioNames = response.jsonPath().getList("portfolio_name");
        return portfolioNames;
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

    public static Response getDashboardCoverage(String portfolioId) {
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
                    .post(CommonEndPoints.DASHBOARD_COVERAGE).prettyPeek();

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println(response.prettyPrint());
        return response;
    }


    public Response getEntitlementHandlerResponse() {
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .get(CommonEndPoints.GET_ENTITLEMENT_HANDLER);
        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }
}
