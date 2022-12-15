package com.esgc.Utilities;

import com.esgc.Base.API.APIModels.Portfolio;
import com.esgc.Base.API.Controllers.APIController;
import com.esgc.Utilities.EndPoints.CommonEndPoints;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

import java.io.File;

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
}
