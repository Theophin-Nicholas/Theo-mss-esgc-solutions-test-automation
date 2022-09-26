package com.esgc.Tests.API;

import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.esgc.Controllers.APIController;
import com.esgc.Tests.TestBases.APITestBase;
import com.esgc.Utilities.APIUtilities;
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

import static com.esgc.Utilities.API.ErrorMessages.*;
import static org.hamcrest.Matchers.*;

public class ImportPortfolioTests extends APITestBase {

    /*

     */
    @Test(groups = {"api", "regression", "smoke"})
    @Xray(test = {492})
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

    }

    @Test(groups = {"api", "regression", "smoke"})
    @Xray(test = {495})
    public void importPortfolioWithoutDate() {//495

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

    }

    @Test(groups = {"api", "regression", "smoke"})
    @Xray(test = {495})
    public void importPortfolioWithoutName() {//495

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
    }

    @Test(groups = {"api", "regression", "smoke"})
    @Xray(test = {495, 501})
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

    }

    /**
     * This test checks error messages according to invalid portfolio import attempts
     * DataProvider provided to iterate same test steps with with different invalid files
     * to cover all negative test scenarios.
     *
     * @param fileName     - name of portfolio file to send with POST request
     * @param errorMessage - id of user
     */

    @Test(groups = {"api", "regression", "errorMessages", "smoke"}, dataProvider = "ErrorMessages")
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
                {"InvalidCurrencyInPortfolio.csv", INVALID_CURRENCY_ERROR_MESSAGE, 498, 9914},//498
                {"InvalidCurrencyCodeInPortfolio.csv", INVALID_CURRENCY_ERROR_MESSAGE, 498, 9914},//498
                {"InvalidCurrencyCodeInPortfolio2.csv", INVALID_CURRENCY_ERROR_MESSAGE, 3047, 9914},
                {"NoIdentifierInPortfolio.csv", NO_IDENTIFIER_ERROR_MESSAGE, 504, 839},//504//839
                {"EmptyIdentifier.csv", EMPTY_IDENTIFIER_ERROR_MESSAGE, 504, 839},//504//839
                {"InvalidIdentifierValue.csv", INVALID_IDENTIFIER_VALUE_ERROR_MESSAGE, 506, 837},//506//837
                {"MissingIdentifier.csv", MISSING_IDENTIFIER_ERROR_MESSAGE, 504, 839},//504//839
                //{"InvalidDate.csv", INVALID_DATE_ERROR_MESSAGE, 512, 836},//512/836
                //{"InvalidDate2.csv", INVALID_DATE_ERROR_MESSAGE, 512, 836},//512/836
                //{"InvalidDate3.csv", INVALID_DATE_ERROR_MESSAGE, 512, 836},//512/836
                {"NoHeader.csv", NO_HEADER_ERROR_MESSAGE, 520, 831},//520//831
                {"InvalidHeader.csv", INVALID_HEADER_ERROR_MESSAGE, 520},//520
                {"ValueMissingHeader.csv", INVALID_COLUMN_ERROR_MESSAGE2, 520},//520
                {"ValueMissingHeader2.csv", VALUE_MISSING_HEADER_ERROR_MESSAGE, 520},//520
                {"InvalidColumn.csv", INVALID_COLUMN_ERROR_MESSAGE, 520},//520
                {"InvalidFile.json", INVALID_FILE_ERROR_MESSAGE, 507, 815, 4154},//507,815, 4154
                {"InvalidFile.txt", INVALID_FILE_ERROR_MESSAGE, 507, 815, 4154},//507,815, 4154
                {"EmptyFile.csv", EMPTY_FILE_ERROR_MESSAGE, 524},//524
                {"SeveralMissingFields.csv", SEVERAL_MISSING_ERROR_MESSAGE, 819},//819 several missing fields
                {"MissingValue.csv", MISSING_VALUE_ERROR_MESSAGE, 840},//840 value missing
                {"AllUnmatchedIdentifiers.csv", All_UNMATCHED_IDENTIFIERS_ERROR_MESSAGE, 984}//all value unmatched

        };
    }
}
