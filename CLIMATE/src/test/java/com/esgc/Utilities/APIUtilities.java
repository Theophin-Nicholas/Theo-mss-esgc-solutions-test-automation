package com.esgc.Utilities;

import com.esgc.Base.API.APIModels.Portfolio;
import com.esgc.Base.API.Controllers.APIController;
import com.esgc.RegulatoryReporting.API.Controllers.RegulatoryReportingAPIController;
import com.esgc.Utilities.API.Endpoints;
import com.esgc.Utilities.EndPoints.CommonEndPoints;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.oneOf;

/**
 * Useful methods stored under this class for API Testing
 */

public class APIUtilities {

    public static int invalidPayloadStatusCode = 400;

    /**
     * This method calls user_id as QA
     *
     * @return ID of user as QA
     */
    public static String userID() {
        return Environment.USER_ID;
    }

    /**
     * This method is used to access a portfolio for a user
     *
     * @param user_id      - id of the user
     * @param portfolio_id - id of the portfolio
     * @return Portfolio object, return null object if portfolio does not exist
     */
    public static Portfolio getPortfolioByID(String user_id, String portfolio_id) {

        try {
            Response response = given()
                    .baseUri(Environment.URL)
                    .relaxedHTTPSValidation()
                    .header("Authorization", "Bearer " + System.getProperty("token"))
                    .queryParam("user_id", user_id)
                    .when()
                    .get(CommonEndPoints.GET_PORTFOLIOS_FOR_USER);

            response.then().log().ifError();
            response.then().assertThat().statusCode(200);
            return response.jsonPath().getObject("portfolios.find{it.portfolio_id=='" + portfolio_id + "'}", Portfolio.class);
        } catch (Exception e) {
            System.out.println("PORTFOLIO DOES NOT EXIST!!!");
            System.out.println(e.getMessage());
        }
        return null;
    }


    public static Response getAvailablePortfoliosForUser() {

        try {
            Response response = given()
                    .baseUri(Environment.URL)
                    .relaxedHTTPSValidation()
                    .header("Authorization", "Bearer " + System.getProperty("token"))
                    .when()
                    .get(CommonEndPoints.GET_PORTFOLIOS_FOR_USER);

            response.then().log().ifError();
            response.then().assertThat().statusCode(200);
            return response;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * This method tries to import invalid portfolio to user account through API
     *
     * @param user_id  - id of user
     * @param filePath - path of portfolio
     * @return response
     * <p>
     * Response Example:
     * {
     * "errorType": "ValueError",
     * "errorMessage": "Invalid Portfolio - invalid headers in line 3 .  Must utilize the format in the downloadable csv template file."
     * }
     */
    public static Response importInvalidPortfolio(String user_id, String filePath) {
        File file = new File(filePath);
        String fileName = file.getName() + filePath.substring(filePath.lastIndexOf("."));

        APIController controller = new APIController();

        Response response = controller.importPortfolio(user_id, fileName,
                filePath);
        response.then().log().all();

        String errorType = response.jsonPath().getString("errorType");

        if (errorType.contains("Exception")) {
            response.then().assertThat().statusCode(is(oneOf(500, 400)));
        } else {
            response.then().assertThat().statusCode(is(oneOf(400, 403, 404, 415)));
        }

        return response;
    }

    /**
     * This method imports valid portfolio to use it
     *
     * @param user_id - id of user
     * @return response
     * <p>
     * Response Example:
     * {
     * "portfolio_name": "My AMAZING Portfolio",
     * "portfolio_id": "f7fb1e34-4e0e-4226-86eb-827273bed5d1"
     * }
     */
    public static Response importScorePortfolio(String user_id) {
        File file = new File(PortfolioFilePaths.scorePortfolioPath());
        String fileName = file.getName();

        System.out.println("File name:" + fileName);

        APIController controller = new APIController();
        Response response = controller.importPortfolio(user_id, fileName,
                PortfolioFilePaths.scorePortfolioPath());
        response.then().log().all().assertThat().body("portfolio_name", Matchers.notNullValue());

        String portfolioName = response.jsonPath().getString("portfolio_name");

        System.out.println(portfolioName + " Successfully Imported for User:" + user_id);

        return response;
    }

    public static Response getUserPortfolioDetails() {
        Response response = null;
        APIController apiController = new APIController();
        try {
            response = apiController.configSpec()
                    .when()
                    .get(Endpoints.GET_PORTFOLIOS_FOR_USER);
        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        //response.prettyPrint();
        return response;
    }

    public Response getPortfolioDetails(String portfolioId) {
        Response response = null;
        APIController apiController = new APIController();
        try {
            response = apiController.configSpec()
                    .pathParam("portfolio_id", portfolioId)
                    .when().get(Endpoints.POST_PORTFOLIO_SETTINGS);
        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }

    public static List<String> getPortfolioNames() {
        return getUserPortfolioDetails().jsonPath().getList("portfolios.portfolio_name");
    }

    public static List<String> getPortfolioIds() {
        return getUserPortfolioDetails().jsonPath().getList("portfolios.portfolio_id");
    }

    public void deleteDuplicatePortfolios() {
        List<String> portfolioNames = getPortfolioNames();
        //sort the list
        portfolioNames.sort(String::compareToIgnoreCase);
        Map<String, Integer> portfolioNumbers = new HashMap<>();
        for (String portfolioName : portfolioNames) {
            if (portfolioNumbers.containsKey(portfolioName)) {
                portfolioNumbers.put(portfolioName, portfolioNumbers.get(portfolioName) + 1);
            } else {
                portfolioNumbers.put(portfolioName, 1);
            }
        }
        portfolioNumbers.forEach((key, value) -> {if (value > 1) System.out.println(key+" = "+value);});
        for (String portfolioName : portfolioNumbers.keySet()) {
            for(int i = 1; i < portfolioNumbers.get(portfolioName); i++) {
                deletePortfolio(getPortfolioId(portfolioName));
            }
        }
    }

    public static String getPortfolioId(String portfolioName) {
        List<String> portfolioNames = getPortfolioNames();
        System.out.println("portfolioNames.size() = " + portfolioNames.size());
        List<String> portfolioIds = getPortfolioIds();
        System.out.println("portfolioIds.size() = " + portfolioIds.size());
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

    public synchronized Response deletePortfolio(String portfolioId) {
        APIController apiController = new APIController();
        return apiController.configSpec()
                .header("Content-Type", "application/json, text/plain, */*")
                .pathParam("portfolio_id", portfolioId)
                .when()
                .delete(Endpoints.PUT_PORTFOLIO_NAME_UPDATE).prettyPeek();
    }


    public synchronized static void deleteImportedPortfoliosAfterTest() {
        APIController apiController = new APIController();
        RegulatoryReportingAPIController apiController2 = new RegulatoryReportingAPIController();
        List<String> portfolioNames = apiController2.getPortfolioNames();
        Collections.shuffle(portfolioNames);

        if (portfolioNames.size() > 10) {
            for (int i = 0; i < portfolioNames.size() - 10; i++) {
                if (portfolioNames.get(i).equals("Sample Portfolio")) continue;
                System.out.println("Deleting the imported portfolio");
                //Delete Portfolio
                apiController.deletePortfolio(apiController2.getPortfolioId(portfolioNames.get(i)));
            }
        }
    }
}
