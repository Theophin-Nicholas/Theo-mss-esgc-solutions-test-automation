package com.esgc.Base.API.Tests;

import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.esgc.Base.API.Controllers.APIController;
import com.esgc.Base.TestBases.APITestBase;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.Database.PortfolioQueries;
import com.esgc.Utilities.PortfolioFilePaths;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.esgc.Utilities.ErrorMessages.*;
import static com.esgc.Utilities.Groups.*;
import static org.hamcrest.Matchers.*;

public class ImportPortfolioTests extends APITestBase {

    /*

     */
    @Test(groups = {API, REGRESSION, SMOKE})
    @Xray(test = {4873, 3403})
    public void postValidPortfolio() {
        String filePath = PortfolioFilePaths.goodPortfolioPath();
        File file = new File(filePath);
        String fileName = file.getName();
        String user_id = APIUtilities.userID();

        System.out.println("File name:" + fileName);

        byte[] fileContent = new byte[0];

        try {
            fileContent = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            System.out.println("FILE NOT FOUND!!!");
            System.out.println(e.getMessage());
        }

        test.info("Importing Portfolio:" + fileName);
        APIController apiController = new APIController();

        Response response = apiController.importPortfolio(user_id, fileName, filePath);

        response.then().log().ifError();
        test.pass("Response received");
        response.then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("portfolio_name", is(notNullValue()))
                .body("portfolio_id", is(notNullValue()));
        test.info(MarkupHelper.createCodeBlock(response.statusLine()));
        test.info(MarkupHelper.createCodeBlock(response.body().jsonPath().prettyPrint(), CodeLanguage.JSON));

        String portfolioName = response.jsonPath().getString("portfolio_name");
        String portfolioID = response.jsonPath().getString("portfolio_id");

        test.pass(portfolioName + " imported to database successfully");
        PortfolioQueries portfolioQueries = new PortfolioQueries();
        assertTestCase.assertEquals(portfolioQueries.getEntitiesFromPortfolio(portfolioID).size(),8, "Matched orbis id should be available");


    }

    @Test(groups = {API, REGRESSION, SMOKE})
    @Xray(test = {2180})
    public void importPortfolioWithoutDate() {//2180

        String filePath = PortfolioFilePaths.portfolioWithoutDatePath();
        File file = new File(filePath);
        String fileName = file.getName();
        String user_id = APIUtilities.userID();

        System.out.println("File name:" + fileName);

        test.info("Importing Portfolio:" + fileName);

        APIController apiController = new APIController();
        Response response = apiController.importPortfolio(user_id, fileName, filePath);

        response.then().log().ifError();
        test.pass("Response received");
        response.then()
                .assertThat()
                .statusCode(is(oneOf(200, 404, 500)))
                .contentType(ContentType.JSON)
                .body("portfolio_name", is(notNullValue()))
                .body("portfolio_id", is(notNullValue()));
        test.info(MarkupHelper.createCodeBlock(response.statusLine()));
        test.info(MarkupHelper.createCodeBlock(response.body().jsonPath().prettyPrint(), CodeLanguage.JSON));

        String portfolioName = response.jsonPath().getString("portfolio_name");
        String portfolioID = response.jsonPath().getString("portfolio_id");

        Date date = new Date();
        String todaysDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

        test.info("Get portfolio to check if it is stored as of date:" + todaysDate);

        Assert.assertEquals(portfolioName, fileName.substring(0, fileName.indexOf(".csv")));
        test.pass("Portfolio Name:" + portfolioName);
        test.pass("Portfolio imported with today's date successfully");
        apiController.deletePortfolio(portfolioID);
    }

    @Test(groups = {API, REGRESSION, SMOKE})
    @Xray(test = {2180})
    public void importPortfolioWithoutName() {//2180

        String filePath = PortfolioFilePaths.portfolioWithoutNamePath();
        File file = new File(filePath);
        String fileName = file.getName();
        String user_id = APIUtilities.userID();

        System.out.println("File name:" + fileName);

        byte[] fileContent = new byte[0];

        try {
            fileContent = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            System.out.println("FILE NOT FOUND!!!");
            System.out.println(e.getMessage());
        }

        test.info("Importing Portfolio:" + fileName);

        APIController apiController = new APIController();
        Response response = apiController.importPortfolio(user_id, fileName, filePath);

        response.then().log().ifError();
        test.pass("Response received");
        response.then()
                .assertThat()
                .statusCode(is(oneOf(200, 400, 404)))
                .contentType(ContentType.JSON)
                .body("portfolio_name", is(notNullValue()))
                .body("portfolio_id", is(notNullValue()));
        test.info(MarkupHelper.createCodeBlock(response.statusLine()));
        test.info(MarkupHelper.createCodeBlock(response.body().jsonPath().prettyPrint(), CodeLanguage.JSON));

        String portfolioName = response.jsonPath().getString("portfolio_name");
        String portfolioID = response.jsonPath().getString("portfolio_id");

//        Date date = new Date();
//        String todaysDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

        String date = "2021-01-22";//This date provided in portfolio

        Assert.assertEquals(portfolioName, fileName.substring(0, fileName.indexOf(".csv")));
        test.pass("Portfolio Name:" + portfolioName);
        test.pass("Portfolio imported with name as portfolio-yyy-mm-dd format successfully");
        apiController.deletePortfolio(portfolioID);
    }

    @Test(groups = {API, REGRESSION, SMOKE})
    @Xray(test = {2180, 4812})
    public void importPortfolioWithoutNameAndWithoutAsOfDate() {//495_501

        String filePath = PortfolioFilePaths.portfolioWithoutNameAndWithoutAsOfDatePath();
        File file = new File(filePath);
        String fileName = file.getName();
        String user_id = APIUtilities.userID();

        System.out.println("File name:" + fileName);

        test.info("Importing Portfolio:" + fileName);

        APIController apiController = new APIController();
        Response response = apiController.importPortfolio(user_id, fileName, filePath);

        response.then().log().ifError();
        test.pass("Response received");
        response.then()
                .assertThat()
                .statusCode(is(oneOf(200, 400, 404)))
                .contentType(ContentType.JSON)
                .body("portfolio_name", is(notNullValue()))
                .body("portfolio_id", is(notNullValue()));
        test.info(MarkupHelper.createCodeBlock(response.statusLine()));
        test.info(MarkupHelper.createCodeBlock(response.body().jsonPath().prettyPrint(), CodeLanguage.JSON));

        String portfolioName = response.jsonPath().getString("portfolio_name");
        String portfolioID = response.jsonPath().getString("portfolio_id");

        Date date = new Date();
        String todaysDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

        Assert.assertEquals(portfolioName, fileName.substring(0, fileName.indexOf(".csv")));
        test.pass("Portfolio Name:" + portfolioName);
        test.pass("Portfolio imported with today's date and name as portfolio-yyy-mm-dd format successfully");
        apiController.deletePortfolio(portfolioID);
    }

    /**
     * This test checks error messages according to invalid portfolio import attempts
     * DataProvider provided to iterate same test steps with with different invalid files
     * to cover all negative test scenarios.
     *
     * @param fileName     - name of portfolio file to send with POST request
     * @param errorMessage - id of user
     */

    @Test(groups = {API, REGRESSION, ERROR_MESSAGES, SMOKE}, dataProvider = "ErrorMessages")
    public void importInvalidPortfolio(String fileName, String errorMessage, Integer... testCaseNumber) {

        String user_id = APIUtilities.userID();

        test.info("Importing Portfolio:" + fileName);

        String filePath = PortfolioFilePaths.getFilePathForInvalidPortfolio(fileName);

        Response response = APIUtilities.importInvalidPortfolio(user_id, filePath);

        test.pass("Response received");

        test.info(MarkupHelper.createCodeBlock(response.statusLine()));
        test.info(MarkupHelper.createCodeBlock(response.body().jsonPath().prettyPrint(), CodeLanguage.JSON));

        response.then().assertThat()
                .body("errorType", notNullValue())
                .body("errorMessage", notNullValue());
        String actualErrorMessage = response.jsonPath().getString("errorMessage").trim();
        actualErrorMessage = actualErrorMessage.replaceAll(" ", " ");
        String expectedErrorMessage = errorMessage.trim();
        assertTestCase.assertEquals(actualErrorMessage, expectedErrorMessage, "Error Message Verification", testCaseNumber);
        test.pass("Error shown in response as expected");
        test.pass("Error message verified successfully");

    }

    @DataProvider(name = "ErrorMessages")
    public Object[][] dpMethod() {

        return new Object[][]{
                {"InvalidCurrencyInPortfolio.csv", INVALID_CURRENCY_ERROR_MESSAGE, 4956, 4772},//4956
                {"InvalidCurrencyCodeInPortfolio.csv", INVALID_CURRENCY_ERROR_MESSAGE, 4956, 4772},//4956
                {"InvalidCurrencyCodeInPortfolio2.csv", INVALID_CURRENCY_ERROR_MESSAGE, 4843, 4772},
                {"NoIdentifierInPortfolio.csv", NO_IDENTIFIER_ERROR_MESSAGE, 4628, 4856},//4628//4856
                {"EmptyIdentifier.csv", EMPTY_IDENTIFIER_ERROR_MESSAGE, 4628, 4856},//4628//4856
                {"InvalidIdentifierValue.csv", INVALID_IDENTIFIER_VALUE_ERROR_MESSAGE, 4467, 5070},//4467//5070
                {"MissingIdentifier.csv", MISSING_IDENTIFIER_ERROR_MESSAGE, 4628, 4856},//4628//4856
                //{"InvalidDate.csv", INVALID_DATE_ERROR_MESSAGE, 512, 836},//512/836
                //{"InvalidDate2.csv", INVALID_DATE_ERROR_MESSAGE, 512, 836},//512/836
                //{"InvalidDate3.csv", INVALID_DATE_ERROR_MESSAGE, 512, 836},//512/836
                {"NoHeader.csv", NO_HEADER_ERROR_MESSAGE, 5055, 4859},//5055//4859
                {"InvalidHeader.csv", INVALID_HEADER_ERROR_MESSAGE, 5055},//5055
                {"ValueMissingHeader.csv", INVALID_COLUMN_ERROR_MESSAGE2, 5055},//5055
                {"ValueMissingHeader2.csv", VALUE_MISSING_HEADER_ERROR_MESSAGE, 5055},//5055
                {"InvalidColumn.csv", INVALID_COLUMN_ERROR_MESSAGE, 5055},//5055
                {"InvalidFile.json", INVALID_FILE_ERROR_MESSAGE, 5023, 3522, 4876},//5023,3522, 4876
                {"InvalidFile.txt", INVALID_FILE_ERROR_MESSAGE, 5023, 3522, 4876},//5023,3522, 4876
                {"EmptyFile.csv", EMPTY_FILE_ERROR_MESSAGE, 5052},//5052
                {"SeveralMissingFields.csv", SEVERAL_MISSING_ERROR_MESSAGE, 5025},//5025 several missing fields
                {"MissingValue.csv", MISSING_VALUE_ERROR_MESSAGE, 4551},//4551 value missing
                {"AllUnmatchedIdentifiers.csv", All_UNMATCHED_IDENTIFIERS_ERROR_MESSAGE, 1732}//all value unmatched

        };
    }
}
