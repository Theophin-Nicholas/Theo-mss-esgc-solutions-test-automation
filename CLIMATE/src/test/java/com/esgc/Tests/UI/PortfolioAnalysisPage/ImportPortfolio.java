package com.esgc.Tests.UI.PortfolioAnalysisPage;

import com.esgc.Controllers.APIController;
import com.esgc.Pages.LoginPage;
import com.esgc.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Tests.UI.DashboardPage.PortfolioSettings;
import com.esgc.Utilities.*;
import com.esgc.Utilities.Database.DatabaseDriver;
import com.esgc.Utilities.Database.PortfolioQueries;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.ErrorMessages.*;

public class ImportPortfolio extends UITestBase {
    ResearchLinePage researchLinePage = new ResearchLinePage();
    PortfolioQueries portfolioQueries = new PortfolioQueries();
        /*
    US JIRA # - ESGCA-227
    Acceptance Criteria:
    Error Popup should be displayed when file is not uploaded successfully
 */

    @Test(groups = {"regression", "ui", "smoke", "robot_dependency"})
    @Xray(test = {330})
    public void VerifyFileUploadErrorPopup() {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        test.info("Navigated to Portfolio Analysis Page");
        test.info("Clicked on Upload button");

        researchLinePage.clickUploadPortfolioButton();
        test.info("Navigated to Upload Page");

        test.info("Waited for upload button's visibility");

        test.info("Clicked on the Browse File button");
        researchLinePage.clickBrowseFile();
        BrowserUtils.wait(2);

        String inputFile = System.getProperty("user.dir") + ConfigurationReader.getProperty("PortfolioWithInvalidData");

        RobotRunner.selectFileToUpload(inputFile);

        BrowserUtils.wait(4);
        test.info("Import portfolio file was selected");
        researchLinePage.clickUploadButton();
        test.info("Clicked on the Upload button");
        BrowserUtils.wait(4);
        assertTestCase.assertTrue(researchLinePage.CheckifErrorPopUpIsDisplyed(), "Error pop up verification");
        test.pass("Verified:After an unsuccessful file upload attempt,error popup was displayed successfully");

    }

    /*
  US JIRA # - ESGCA-227
  Acceptance Criteria:
  Successful message popup should be displayed when file is uploaded successfully
*/
//1027
    @Test(groups = {"regression", "ui", "smoke", "robot_dependency"},dataProviderClass = DataProviderClass.class,
            dataProvider = "Valid Portfolios")
    @Xray(test = {1027, 3044, 11069})
    public void VerifyFileUploadSuccessPopup(String portfolio) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        test.info("Navigated to Portfolio Analysis Page");
        test.info("Clicked on Upload button");

        researchLinePage.clickUploadPortfolioButton();
        test.info("Navigated to Upload Page");

        BrowserUtils.waitForVisibility(researchLinePage.uploadButton, 2);
        test.info("Waited for upload button's visibility");

        test.info("Clicked on the Browse File button");
        researchLinePage.clickBrowseFile();
        BrowserUtils.wait(2);

        String inputFile = System.getProperty("user.dir") + ConfigurationReader.getProperty(portfolio);
        RobotRunner.selectFileToUpload(inputFile);

        BrowserUtils.wait(10);

        String expectedFileName = "\"" + inputFile.substring(inputFile.lastIndexOf(File.separator) + 1) + "\"";
        String actualFileName = researchLinePage.selectedFileName.getText().substring(0, researchLinePage.selectedFileName.getText().indexOf("Remove") - 1);

        assertTestCase.assertEquals(actualFileName, expectedFileName, "File selected from dialog", 304, 305, 306);

        assertTestCase.assertTrue(researchLinePage.isRemoveButtonPresent(), "Remove hyperlink is displayed", 307);
        test.info("Import portfolio file was selected");
        assertTestCase.assertFalse(researchLinePage.isBrowseButtonPresent(), "Browse button is disabled", 308);

        researchLinePage.clickUploadButton();
        test.info("Clicked on the Upload button");

        assertTestCase.assertTrue(researchLinePage.checkIfUploadingMaskIsDisplayed(), "Load Mask");


        assertTestCase.assertTrue(researchLinePage.checkifSuccessPopUpIsDisplyed(), "Success pop up is displayed", 335);
        test.pass("Verified:After a successful file upload,Success popup was displayed successfully");

        assertTestCase.assertEquals(researchLinePage.AlertMessage.getText(), "Portfolio Upload Successfully Saved", "Success message verified", 335);
        test.pass("Verified:Message for successfull portfolio upload was as expected");

        assertTestCase.assertTrue(researchLinePage.CheckifClosebuttonIsDisplayed(), "Close button verified");
        test.pass("Verified:'X' button was displayed on the successful message popup");

        String expectedPortfolioName = ConfigurationReader.getProperty(portfolio).substring(
                ConfigurationReader.getProperty(portfolio).lastIndexOf("\\")+1,
                ConfigurationReader.getProperty(portfolio).lastIndexOf(".")
        );//"Portfolio Upload updated_good";
        assertTestCase.assertEquals(researchLinePage.getPlaceholderInSuccessPopUp(), expectedPortfolioName, "Portfolio name in pop up");

        researchLinePage.waitForDataLoadCompletion();

        assertTestCase.assertEquals(researchLinePage.getSelectedPortfolioNameFromDropdown(), expectedPortfolioName, "Portfolio name verification", 1298);
        assertTestCase.assertEquals(researchLinePage.getPortfolioNameFromSubtitle(), expectedPortfolioName, "Portfolio name in subtitle");
        assertTestCase.assertEquals(researchLinePage.getPortfolioNames().get(0).trim(), expectedPortfolioName, "Portfolio name is in Portfolio Selection", 663);
        String benchmarkPortfolioName = researchLinePage.getOptionsAsStringListFromFiltersDropdown("benchmark").stream().filter(e -> e.equals(expectedPortfolioName)).findFirst().get();
        assertTestCase.assertEquals(benchmarkPortfolioName, expectedPortfolioName, "Portfolio name is in Benchmark", 663, 705);
        assertTestCase.assertFalse(researchLinePage.checkifSuccessPopUpIsDisplyed(), "Success pop up disappeared", 337);
    }

    //test case:ESGCA-985
    @Test(groups = {"regression", "ui", "smoke"})
    @Xray(test = {985})
    public void VerifyUploadModal() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        test.info("Navigated to Portfolio Analysis Page");

        researchLinePage.clickUploadPortfolioButton();
        test.info("Clicked on Upload button");
        test.info("Navigated to Upload Page");

        BrowserUtils.waitForVisibility(researchLinePage.uploadButton, 2);
        BrowserUtils.wait(5);
        assertTestCase.assertTrue(researchLinePage.checkIfPortfolioUploadModalIsDisplayed(), "Portfolio Upload Modal Displayed");
        assertTestCase.assertTrue(researchLinePage.checkIfPortfolioUploadModalSubTitleIsDisplayedAsExpected(), "Subtitle Displayed");
        assertTestCase.assertTrue(researchLinePage.checkIfPortfolioUploadModalDescriptionIsDisplayedAsExpected(), "Description verification");
        assertTestCase.assertTrue(researchLinePage.downloadTemplateLink.isDisplayed(), "Download Template Link displayed", 188);
        assertTestCase.assertTrue(researchLinePage.browseFileButton.isEnabled(), "Browse File button is available", 303);
        assertTestCase.assertTrue(researchLinePage.checkIfCloseUploadModalButtonIsDisabled(), "Close button displayed");
        assertTestCase.assertTrue(researchLinePage.checkIfUploadButtonIsDisabled(), "Upload button disabled", 250);

        researchLinePage.closeUploadModal();
        BrowserUtils.wait(3);
        assertTestCase.assertFalse(researchLinePage.checkIfPortfolioUploadModalIsDisplayed(), "Modal closed");
        test.pass("Upload modal verified");
    }

     /*
    US JIRA # - ESGCA-227
    Acceptance Criteria:
    User is able to modify portfolio name after successful upload message
    */

    @Test(groups = {"regression", "ui", "smoke", "robot_dependency"})
    @Xray(test = {336, 3045})
    public void VerifyPortfolioCanBeRenamed() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        test.info("Navigated to Portfolio Analysis Page");

        test.info("Clicked on Upload button");

        researchLinePage.clickUploadPortfolioButton();
        test.info("Navigated to Upload Page");

        BrowserUtils.waitForVisibility(researchLinePage.uploadButton, 2);
        test.info("Waited for upload button's visibility");

        test.info("Clicked on the Browse File button");
        researchLinePage.clickBrowseFile();
        BrowserUtils.wait(2);

        String inputFile = System.getProperty("user.dir") + ConfigurationReader.getProperty("PortfolioWithValidData2");
        RobotRunner.selectFileToUpload(inputFile);

        BrowserUtils.wait(4);
        test.info("Import portfolio file was selected");
        researchLinePage.clickUploadButton();
        test.info("Clicked on the Upload button");
        BrowserUtils.wait(3);

        BrowserUtils.waitForVisibility(researchLinePage.successMessagePopUP, 2);
        test.info("Waited for the Successful popup's visibility");

        assertTestCase.assertTrue(researchLinePage.CheckifNameInputFielIsPresent(), "File can be renamed from pop up", 336);
        test.pass("Verified:After a successful file upload,A name input field was displayed on successful popup message");

        String newPortfolioName = ConfigurationReader.getProperty("NewPortfolioName");
        researchLinePage.NameInputField.sendKeys(newPortfolioName);
        test.info("Entered the new name for the uploaded portfolio");

        researchLinePage.clickOnButtonWithLabel("Save");
        test.info("Clicked on Save button");

        researchLinePage.waitForDataLoadCompletion();

        assertTestCase.assertEquals(researchLinePage.getSelectedPortfolioNameFromDropdown(), newPortfolioName, "File is renamed", 336);
        assertTestCase.assertEquals(researchLinePage.getPortfolioNameFromSubtitle(), newPortfolioName, "File is renamed", 336);
        assertTestCase.assertEquals(researchLinePage.getPortfolioNames().get(0).trim(), newPortfolioName, "File is renamed in Portfolio Selection", 642);
        String benchmarkPortfolioName = researchLinePage.getOptionsAsStringListFromFiltersDropdown("benchmark").stream().filter(e -> e.equals(newPortfolioName)).findFirst().get();
        assertTestCase.assertEquals(benchmarkPortfolioName, newPortfolioName, "File name is renamed in Benchmark", 642);
    }

    /*
     US JIRA # - ESGCA-227
     Acceptance Criteria:
     Observe Save button is not appearing unless user has not click to rename portfolio.
    */
    @Test(groups = {"regression", "ui", "smoke", "robot_dependency"})
    @Xray(test = {3046})
    public void VerifySaveButtonVisibility() {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        test.info("Navigated to Portfolio Analysis Page");
        test.info("Clicked on Upload button");

        researchLinePage.clickUploadPortfolioButton();
        test.info("Navigated to Upload Page");

        BrowserUtils.waitForVisibility(researchLinePage.uploadButton, 2);
        test.info("Waited for upload button's visibility");

        test.info("Clicked on the Browse File button");
        researchLinePage.clickBrowseFile();
        BrowserUtils.wait(2);

        String inputFile = System.getProperty("user.dir") + ConfigurationReader.getProperty("PortfolioWithValidData3");
        RobotRunner.selectFileToUpload(inputFile);

        BrowserUtils.wait(4);
        test.info("Import portfolio file was selected");
        researchLinePage.clickUploadButton();
        test.info("Clicked on the Upload button");

        assertTestCase.assertTrue(researchLinePage.checkifSuccessPopUpIsDisplyed(), "Success Pop Up presented", 338);
        test.info("Waited for the Successful popup's visibility");

        assertTestCase.assertFalse(researchLinePage.isSaveButtonPresent(), "Save button is not displayed", 338);
        test.pass("Verified:After a successful file upload,Save button was not present by default on Successful message popup.");

        //researchLinePage.clickCloseButton();
        BrowserUtils.wait(2);
        Assert.assertFalse(researchLinePage.checkifSuccessPopUpIsDisplyed());
    }

    //test case:316
    @Test(groups = {"regression", "ui", "smoke", "robot_dependency"})
    public void VerifyFileRemovedFromUploadModal() {

        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");
        test.info("Clicked on Upload button");

        researchLinePage.clickUploadPortfolioButton();
        test.info("Navigated to Upload Page");

        BrowserUtils.waitForVisibility(researchLinePage.uploadButton, 2);
        test.info("Waited for upload button's visibility");

        test.info("Clicked on the Browse File button");
        researchLinePage.clickBrowseFile();
        BrowserUtils.wait(2);

        String inputFile = System.getProperty("user.dir") + ConfigurationReader.getProperty("PortfolioWithValidData");
        RobotRunner.selectFileToUpload(inputFile);

        BrowserUtils.wait(4);
        test.info("Import portfolio file was selected");
        researchLinePage.isRemoveButtonPresent();
        test.info("Clicked on Remove button");
        researchLinePage.clickRemoveButton();
        BrowserUtils.wait(3);

        assertTestCase.assertTrue(researchLinePage.isFileRemovedFromModal(), "File removed", 316);
        test.pass("Remove button verified");
    }

      /*
    US JIRA # - ESGCA-461
    Acceptance Criteria:
    Error Popup should be displayed when file is not uploaded successfully with correct error message
 */

    @Test(groups = {"regression", "ui", "errorMessages", "robot_dependency"}, dataProvider = "ErrorMessages", singleThreaded = true)
    public void importPortfolio_verifyErrorPopupMessages(String fileName, String errorMessage, Integer... testCaseNumber) {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        test.info("Navigated to Portfolio Analysis Page");
        test.info("Clicked on Upload button");

        researchLinePage.clickUploadPortfolioButton();
        test.info("Navigated to Upload Page");

        test.info("Clicked on the Browse File button");
        researchLinePage.clickBrowseFile();
        BrowserUtils.wait(2);

        String inputFile = PortfolioFilePaths.getFilePathForInvalidPortfolio(fileName);
        RobotRunner.selectFileToUpload(inputFile);

        BrowserUtils.wait(4);
        test.info("Import portfolio file was selected");
        researchLinePage.clickUploadButton();
        test.info("Clicked on the Upload button");
        BrowserUtils.wait(2);

        String popUpMessage = researchLinePage.getErrorMessage();
        String expectedErrorMessage = errorMessage.trim().replaceAll("  ", " ").replace(" ", " ");

        assertTestCase.assertTrue(researchLinePage.CheckifErrorPopUpIsDisplyed(), " Error Pop Up displayed", testCaseNumber);
        assertTestCase.assertEquals(popUpMessage, expectedErrorMessage, "Error message verification", testCaseNumber);
        test.pass("Verified:After an unsuccessful file upload attempt,error popup was displayed successfully");
        test.pass("Verified:Error Message");

    }

    /*
    US JIRA # - ESGCA-461
    Acceptance Criteria:
    Error Popup should be displayed when file is uploaded successfully but contains unknown identifiers
 */

    @Test(groups = {"regression", "ui", "smoke", "robot_dependency"}, singleThreaded = true)
    @Xray(test = 984)
    public void importPortfolio_verifyUnknownIdentifierMessage() {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        test.info("Navigated to Portfolio Analysis Page");
        test.info("Clicked on Upload button");

        researchLinePage.clickUploadPortfolioButton();
        test.info("Navigated to Upload Page");

        BrowserUtils.waitForVisibility(researchLinePage.uploadButton, 2);
        test.info("Waited for upload button's visibility");

        test.info("Clicked on the Browse File button");
        researchLinePage.clickBrowseFile();
        BrowserUtils.wait(2);

        String inputFile = PortfolioFilePaths.getFilePathForInvalidPortfolio("InvalidIdentifier.csv");
        RobotRunner.selectFileToUpload(inputFile);

        BrowserUtils.wait(4);
        test.info("Import portfolio file was selected");
        researchLinePage.clickUploadButton();
        test.info("Clicked on the Upload button");
        BrowserUtils.wait(2);

        String popUpMessage = researchLinePage.getUnknownIdentifierPopUpMessage();
        String expectedErrorMessage = "124/126 unique identifiers matched; the system was unable to match 2 identifiers for: Invalid Identifier Included Portfolio  Included Portfolio";

        Assert.assertTrue(researchLinePage.checkIfUnknownIdentifierPopUpDisplayed());
        Assert.assertEquals(popUpMessage, expectedErrorMessage);

        List<String> identifiers = researchLinePage.getUnknownIdentifiersFromPopUpMessage();
        Assert.assertTrue(identifiers.containsAll(Arrays.asList("BBBBB", "AAAAAA")));

        identifiers.forEach(each -> test.info("Unknown identifier in portfolio:" + each));

        test.pass("Verified:File uploaded,unknown identifiers popup was displayed successfully");
        test.pass("Verified:Unknown Identifiers Message");
        test.pass("Verified:Unknown Identifiers");

    }

    @Test(groups = {"regression", "ui", "errorMessages", "robot_dependency"}, dataProvider = "ErrorMessages", singleThreaded = true)
    public void importPortfolio_ErrorMessageMissingBBGorISINIndetifier(String fileName, String errorMessage) {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        test.info("Navigated to Portfolio Analysis Page");
        test.info("Clicked on Upload button");

        researchLinePage.clickUploadPortfolioButton();
        test.info("Navigated to Upload Page");

        test.info("Clicked on the Browse File button");
        researchLinePage.clickBrowseFile();
        BrowserUtils.wait(2);

        String inputFile = PortfolioFilePaths.getFilePathForInvalidPortfolio(fileName);
        RobotRunner.selectFileToUpload(inputFile);

        BrowserUtils.wait(4);
        test.info("Import portfolio file was selected");
        researchLinePage.clickUploadButton();
        test.info("Clicked on the Upload button");
        BrowserUtils.wait(2);

        String popUpMessage = researchLinePage.getErrorMessage();
        String expectedErrorMessage = errorMessage.trim().replaceAll("  ", " ").replace(" ", " ");

        assertTestCase.assertTrue(researchLinePage.CheckifErrorPopUpIsDisplyed(), " Error Pop Up displayed");
        assertTestCase.assertEquals(popUpMessage, expectedErrorMessage, "Error message verification");
        test.pass("Verified:After an unsuccessful file upload attempt,error popup was displayed successfully");
        test.pass("Verified:Error Message");

    }

    @DataProvider(name = "ErrorMessages")
    public Object[][] dpMethod() {

        return new Object[][]{
                {"PredictedScoredEntityWithISIN.csv",PREDICTED_SCORED_ENTITY_WITH_ISIN,11071},
               {"InvalidCurrencyInPortfolio.csv", INVALID_CURRENCY_ERROR_MESSAGE, 498},//498
                {"InvalidCurrencyCodeInPortfolio.csv", INVALID_CURRENCY_ERROR_MESSAGE, 498},//498
                 {"InvalidCurrencyCodeInPortfolio2.csv", INVALID_CURRENCY_ERROR_MESSAGE, 3047},
                 {"NoIdentifierInPortfolio.csv", NO_IDENTIFIER_ERROR_MESSAGE, 504, 839},//504//839
                 {"EmptyIdentifier.csv", EMPTY_IDENTIFIER_ERROR_MESSAGE, 504, 839},//504//839
                 {"InvalidIdentifierValue.csv", INVALID_IDENTIFIER_VALUE_ERROR_MESSAGE, 506, 837},//506//837
                 {"MissingIdentifier.csv", MISSING_IDENTIFIER_ERROR_MESSAGE, 504, 839, 824},//504//839
 //                {"InvalidDate.csv", INVALID_DATE_ERROR_MESSAGE, 512, 836},//512/836
 //                {"InvalidDate2.csv", INVALID_DATE_ERROR_MESSAGE, 512, 836},//512/836
 //                {"InvalidDate3.csv", INVALID_DATE_ERROR_MESSAGE, 512, 836},//512/836
                 {"NoHeader.csv", INVALID_COLUMN_ERROR_MESSAGE, 520, 831},//520//831
                 {"InvalidHeader.csv", INVALID_HEADER_ERROR_MESSAGE, 520},//520
                 {"ValueMissingHeader.csv", INVALID_COLUMN_ERROR_MESSAGE, 520},//520
                 {"ValueMissingHeader2.csv", CHECK_DOCUMENT_ERROR_MESSAGE, 520},//520
                 {"InvalidColumn.csv", INVALID_COLUMN_ERROR_MESSAGE, 520},//520
                 {"InvalidFile.json", INVALID_FILE_ERROR_MESSAGE, 507, 815, 4154},//507,815, 4154
                 {"InvalidFile.txt", INVALID_FILE_ERROR_MESSAGE, 507, 815, 4154},//507,815, 4154
                 {"EmptyFile.csv", EMPTY_FILE_ERROR_MESSAGE, 524},//524
                 {"SeveralMissingFields.csv", SEVERAL_MISSING_ERROR_MESSAGE, 819},//819 several missing fields
                 {"MissingValue.csv", MISSING_VALUE_ERROR_MESSAGE, 840},//840 value missing
                 {"AllUnmatchedIdentifiers.csv", All_UNMATCHED_IDENTIFIERS_ERROR_MESSAGE, 984},//all value unmatched
                {"MISSING_ISIN_OR_BBG_TICKER_IDENTIFIER.csv",MISSING_ISIN_OR_BBG_TICKER_IDENTIFIER,10102}


        };
    }

    public boolean uploadPortfolio(String fileName) {
        try {
            researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
            test.info("Navigated to Portfolio Analysis Page");
            test.info("Clicked on Upload button");

            researchLinePage.clickUploadPortfolioButton();
            test.info("Navigated to Upload Page");

            BrowserUtils.waitForVisibility(researchLinePage.uploadButton, 2);
            test.info("Waited for upload button's visibility");

            test.info("Clicked on the Browse File button");
            researchLinePage.clickBrowseFile();
            BrowserUtils.wait(2);

            String inputFile = System.getProperty("user.dir") + ConfigurationReader.getProperty(fileName);
            RobotRunner.selectFileToUpload(inputFile);

            BrowserUtils.wait(4);

            String expectedFileName = "\""+fileName + ".csv\"";
            String actualFileName = researchLinePage.selectedFileName.getText().substring(0, researchLinePage.selectedFileName.getText().indexOf("Remove") - 1);

            assertTestCase.assertEquals(actualFileName, expectedFileName, "File selected from dialog", 304, 305, 306);

            assertTestCase.assertTrue(researchLinePage.isRemoveButtonPresent(), "Remove hyperlink is displayed", 307);
            test.info("Import portfolio file was selected");
            assertTestCase.assertFalse(researchLinePage.isBrowseButtonPresent(), "Browse button is disabled", 308);

            researchLinePage.clickUploadButton();
            test.info("Clicked on the Upload button");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Test(groups = {"regression", "ui", "robot_dependency"}, description = "Upload BBG portfolio file with valid data")
    @Xray(test = {4300, 7000, 9778, 9911, 9912})
    public void VerifyBBGPortfolioUploadTest() {
        String portfolioName = "BBGPortfolioWithValidData";
        uploadPortfolio(portfolioName);
        assertTestCase.assertTrue(researchLinePage.checkIfUploadingMaskIsDisplayed(), "Load Mask");
        assertTestCase.assertTrue(researchLinePage.checkifSuccessPopUpIsDisplyed(), "Success pop up is displayed");
        test.pass("Verified:After a successful file upload,Success popup was displayed successfully");
        assertTestCase.assertEquals(researchLinePage.AlertMessage.getText(), "Portfolio Upload Successfully Saved", "Success message verified");
        test.pass("Verified:Message for successfull portfolio upload was as expected");

        BrowserUtils.waitForClickablility(researchLinePage.closeAlert, 10).click();
        researchLinePage.pressESCKey();
        //portfolio upload process and verification is done
        //verify more details of uploaded portfolio
        assertTestCase.assertEquals(researchLinePage.portfolioSelectionButton.getText(), portfolioName, "Number of companies in portfolio verified");

        researchLinePage.selectPortfolio(portfolioName);
        APIController apiController = new APIController();
        String portfolioID = apiController.getCurrentPortfolioId();
        System.out.println("portfolioID = " + portfolioID);

        Map<String, Object> result = portfolioQueries.getPortfolioDetails(portfolioID);
        String expDate = result.get("AS_OF_DATE").toString();

        expDate = DateTimeUtilities.getDatePlusMinusDays(expDate, 1, "yyyy-MM-dd");
        System.out.println("expDate = " + expDate);
        assertTestCase.assertEquals(DateTimeUtilities.getCurrentDate("yyyy-MM-dd"), expDate, "Portfolio As Of Date verified");

        //On this portfolio currency field left empty and default currency is set as USD
        String expCurrency = result.get("CURRENCY").toString();
        assertTestCase.assertEquals(expCurrency, "USD", "Portfolio Currency verified");

        //delete portfolio
        researchLinePage.deletePortfolio(portfolioName);
    }

    @Test(groups = {"regression", "ui", "robot_dependency"}
            , description = "UI | Upload | Verify the notification when User Uploads a portfolio having multiple BBG tickers( basic/Full ) belonging to multiple BVD9s as identifiers")
    @Xray(test = {4302})
    public void VerifyMultipleBBGPortfolioUploadTest() {
        uploadPortfolio("PortfolioWithMultipleBBGTickers");
        assertTestCase.assertTrue(researchLinePage.checkIfUploadingMaskIsDisplayed(), "Load Mask");
        assertTestCase.assertTrue(researchLinePage.checkifSuccessPopUpIsDisplyed(), "Success pop up is displayed");
        test.pass("Verified:After a successful file upload,Success popup was displayed successfully");
        assertTestCase.assertEquals(researchLinePage.AlertMessage.getText(), "The following identifiers are matched to the same entity.", "Same entity warning message verified");
        assertTestCase.assertTrue(researchLinePage.AlertMessageMultipleTickersLists.size()>0, "List of tickers verified");
        test.pass("Verified:Message for Multiple BVDIDs warning was as expected");
        BrowserUtils.waitForClickablility(researchLinePage.closeAlert, 10).click();
        researchLinePage.closeUploadModal();
    }

    @Test(groups = {"regression", "ui", "robot_dependency"}, description = "Upload portfolio with same identifiers")
    @Xray(test = {4303, 9913})
    public void VerifyUploadPortfolioWithSameIdentifiersTest() {
        String portfolioName = "PortfolioWithSameIdentifiers";
        uploadPortfolio(portfolioName);
        assertTestCase.assertTrue(researchLinePage.checkIfUploadingMaskIsDisplayed(), "Load Mask");
        assertTestCase.assertTrue(researchLinePage.checkifSuccessPopUpIsDisplyed(), "Success pop up is displayed");
        test.pass("Verified:After a successful file upload,Success popup was displayed successfully");
        assertTestCase.assertEquals(researchLinePage.AlertMessage.getText(), "Portfolio Upload Successfully Saved", "Success message verified");
        test.pass("Verified:Message for successfull portfolio upload was as expected");
        BrowserUtils.waitForClickablility(researchLinePage.closeAlert, 10).click();
        researchLinePage.pressESCKey();

        //there is 8 companies in the portfolio, if portfolio changes this must be changed
        assertTestCase.assertTrue(researchLinePage.portfolioCoverageCompanies.getText().endsWith("8"), "Number of companies in portfolio verified");
        researchLinePage.selectPortfolio(portfolioName);
        APIController apiController = new APIController();
        String portfolioID = apiController.getCurrentPortfolioId();
        System.out.println("portfolioID = " + portfolioID);
        Map<String, Object> result = portfolioQueries.getPortfolioDetails(portfolioID);

        //On this portfolio currency field set as GBP
        String expCurrency = result.get("CURRENCY").toString();
        assertTestCase.assertEquals(expCurrency, "GBP", "Portfolio Currency verified");

        //delete portfolio
        researchLinePage.deletePortfolio("PortfolioWithSameIdentifiers");
    }

    @Test(groups = {"regression", "ui", "robot_dependency"}
            , description = "UI | Portfolio Upload | Verify currency values in imported portfolio when user enters an incorrect/uncovered currency value ")
    @Xray(test = {9914})
    public void VerifyIncorrectCurrencyPortfolioUploadTest() {
        String portfolioName = "PortfolioWithIncorrectCurrency";
        uploadPortfolio(portfolioName);
        assertTestCase.assertTrue(researchLinePage.checkIfUploadingMaskIsDisplayed(), "Load Mask");
        assertTestCase.assertTrue(researchLinePage.checkifSuccessPopUpIsDisplyed(), "Success pop up is displayed");
        test.pass("Verified:After a successful file upload,Success popup was displayed successfully");
        assertTestCase.assertEquals(researchLinePage.AlertMessage.getText(),
                "Import incomplete. Please enter all values as described in the template. Currency can be USD, GBP, or EUR.",
                "Incorrect currency warning message verified");
        BrowserUtils.waitForClickablility(researchLinePage.closeAlert, 10).click();
        researchLinePage.closeUploadModal();
    }

    @Test(groups = {"regression", "ui", "robot_dependency"}, description = "Upload portfolio with EUR Currency")
    @Xray(test = {9913})
    public void VerifyUploadPortfolioWithEURCurrencyTest() {
        String portfolioName = "SamplePortfolioToDelete";
        uploadPortfolio(portfolioName);
        assertTestCase.assertTrue(researchLinePage.checkIfUploadingMaskIsDisplayed(), "Load Mask");
        assertTestCase.assertTrue(researchLinePage.checkifSuccessPopUpIsDisplyed(), "Success pop up is displayed");
        test.pass("Verified:After a successful file upload,Success popup was displayed successfully");
        assertTestCase.assertEquals(researchLinePage.AlertMessage.getText(), "Portfolio Upload Successfully Saved", "Success message verified");
        test.pass("Verified:Message for successfull portfolio upload was as expected");
        BrowserUtils.waitForClickablility(researchLinePage.closeAlert, 10).click();
        researchLinePage.pressESCKey();

        //there is 8 companies in the portfolio, if portfolio changes this must be changed
        researchLinePage.selectPortfolio(portfolioName);
        APIController apiController = new APIController();
        String portfolioID = apiController.getCurrentPortfolioId();
        System.out.println("portfolioID = " + portfolioID);
        Map<String, Object> result = portfolioQueries.getPortfolioDetails(portfolioID);

        //On this portfolio currency field set as EUR
        String expCurrency = result.get("CURRENCY").toString();
        assertTestCase.assertEquals(expCurrency, "EUR", "Portfolio Currency verified");

        //delete portfolio
        researchLinePage.deletePortfolio("PortfolioWithSameIdentifiers");
    }

    @Test(groups = {"regression", "ui", "robot_dependency"}, description = "UI | Portfolio Upload | Verify the error message for non numeric number in Value field for less than 5 rows")
    @Xray(test = {9931})
    public void VerifyPortfolioWithLessThan5NonNumericValuesUploadTest() {
        String portfolioName = "PortfolioWithLessThan5NonNumericValues";
        uploadPortfolio(portfolioName);
        assertTestCase.assertTrue(researchLinePage.checkIfUploadingMaskIsDisplayed(), "Load Mask");
        assertTestCase.assertTrue(researchLinePage.checkifSuccessPopUpIsDisplyed(), "Success pop up is displayed");
        test.pass("Verified:After a successful file upload,Success popup was displayed successfully");
        assertTestCase.assertEquals(researchLinePage.AlertMessage.getText(),
                "Import incomplete. Please enter only whole or decimal numbers in the Value field. Non-numeric/Special character found in line(s) 14,15 in the Value field.",
                "Non-numeric Values warning message verified");
        BrowserUtils.waitForClickablility(researchLinePage.closeAlert, 10).click();
        researchLinePage.closeUploadModal();
    }

    @Test(groups = {"regression", "ui", "robot_dependency"}, description = "UI | Portfolio Upload | Verify the error message for non numeric number in Value field for more than 5 rows")
    @Xray(test = {9933})
    public void VerifyPortfolioWithMoreThan5NonNumericValuesUploadTest() {
        String portfolioName = "PortfolioWithMoreThan5NonNumericValues";
        uploadPortfolio(portfolioName);
        assertTestCase.assertTrue(researchLinePage.checkIfUploadingMaskIsDisplayed(), "Load Mask");
        assertTestCase.assertTrue(researchLinePage.checkifSuccessPopUpIsDisplyed(), "Success pop up is displayed");
        test.pass("Verified:After a successful file upload,Success popup was displayed successfully");
        assertTestCase.assertEquals(researchLinePage.AlertMessage.getText(),
                "Import incomplete. Please enter only whole or decimal numbers in the Value field. Non-numeric/Special character found in line(s) 19,20,21,24 in the Value field.",
                "Non-numeric Values warning message verified");
        BrowserUtils.waitForClickablility(researchLinePage.closeAlert, 10).click();
        researchLinePage.closeUploadModal();
    }

    @Test(groups = {"regression", "ui", "robot_dependency"}, description = "UI | Portfolio Upload | Verify error messages for missing less/more than 5 fields")
    @Xray(test = {10028})
    public void VerifyPortfolioWithLessThan5MissingFieldsUploadTest() {
        String portfolioName = "PortfolioWithLessThan5MissingFields";
        uploadPortfolio(portfolioName);
        assertTestCase.assertTrue(researchLinePage.checkIfUploadingMaskIsDisplayed(), "Load Mask");
        assertTestCase.assertTrue(researchLinePage.checkifSuccessPopUpIsDisplyed(), "Success pop up is displayed");
        test.pass("Verified:After a successful file upload,Success popup was displayed successfully");
        assertTestCase.assertTrue(researchLinePage.AlertMessage.getText().contains("Identifier Missing"),"Identifier Missing message verified");
        assertTestCase.assertTrue(researchLinePage.AlertMessage.getText().contains("Identifier Type Missing"),"Identifier Type Missing message verified");
        assertTestCase.assertTrue(researchLinePage.AlertMessage.getText().contains("Value Missing"),"Value Missing message verified");
        BrowserUtils.waitForClickablility(researchLinePage.closeAlert, 10).click();
        researchLinePage.closeUploadModal();

        portfolioName = "PortfolioWithMoreThan5MissingFields";
        uploadPortfolio(portfolioName);
        assertTestCase.assertTrue(researchLinePage.checkIfUploadingMaskIsDisplayed(), "Load Mask");
        assertTestCase.assertTrue(researchLinePage.checkifSuccessPopUpIsDisplyed(), "Success pop up is displayed");
        test.pass("Verified:After a successful file upload,Success popup was displayed successfully");
        assertTestCase.assertEquals(researchLinePage.AlertMessage.getText(),
                "Import incomplete. Please enter all values as described in the template. Too many fields empty, please make sure each investment has an identifier type, an identifier and a value.",
                "Too many fields empty message verified");
        BrowserUtils.waitForClickablility(researchLinePage.closeAlert, 10).click();
        researchLinePage.closeUploadModal();
    }

    @Test(groups = {"regression", "ui", "robot_dependency"}, description = "UI | Portfolio Upload | Verify UI changes on Portfolio modal")
    @Xray(test = {10358})
    public void verifyUIChangesOnPortfolioUploadModal() {
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        test.info("Navigated to Portfolio Analysis Page");
        test.info("Clicked on Upload button");

        researchLinePage.clickUploadPortfolioButton();
        test.info("Navigated to Upload Page");

        BrowserUtils.waitForVisibility(researchLinePage.uploadButton, 2);
        test.info("Waited for upload button's visibility");
        assertTestCase.assertTrue(researchLinePage.checkIfPortfolioUploadModalDescriptionIsDisplayedAsExpected(), "Portfolio Upload Modal Description is displayed as expected");
    }

    @Test(groups = {"regression", "ui", "errorMessages", "robot_dependency"})
    @Xray(test = {11081})
    public void importEntityWithPredictedScoreWithoutEntitlements() {

        LoginPage login = new LoginPage();
        login.clickOnLogout();
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_ESG_ENTITLEMENT);

        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        test.info("Navigated to Portfolio Analysis Page");
        test.info("Clicked on Upload button");

        researchLinePage.clickUploadPortfolioButton();
        test.info("Navigated to Upload Page");

        test.info("Clicked on the Browse File button");
        researchLinePage.clickBrowseFile();
        BrowserUtils.wait(2);

        String inputFile = PortfolioFilePaths.getFilePathForInvalidPortfolio("PortfolioWithPredictedScoredEntity.csv");
        RobotRunner.selectFileToUpload(inputFile);

        BrowserUtils.wait(4);
        test.info("Import portfolio file was selected");
        researchLinePage.clickUploadButton();
        test.info("Clicked on the Upload button");
        BrowserUtils.wait(2);

        assertTestCase.assertTrue(researchLinePage.AlertMessage.getText().contains("unique identifiers matched; the system was unable to match"), "Verify error message");
        assertTestCase.assertTrue(researchLinePage.AlertMessageMultipleTickersLists.size()>0, "List of tickers verified");
        BrowserUtils.waitForClickablility(researchLinePage.closeAlert, 10).click();
    }

    @Test(groups = {"regression", "ui", "errorMessages", "robot_dependency"})
    @Xray(test = {11075})
    public void importPortfolioWithUnmatchedOrbisId() {

        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        test.info("Navigated to Portfolio Analysis Page");
        test.info("Clicked on Upload button");

        researchLinePage.clickUploadPortfolioButton();
        test.info("Navigated to Upload Page");

        test.info("Clicked on the Browse File button");
        researchLinePage.clickBrowseFile();
        BrowserUtils.wait(2);

        String inputFile = PortfolioFilePaths.getFilePathForInvalidPortfolio("PortfolioWithUnmatchedOrbisId.csv");
        RobotRunner.selectFileToUpload(inputFile);

        BrowserUtils.wait(4);
        test.info("Import portfolio file was selected");
        researchLinePage.clickUploadButton();
        test.info("Clicked on the Upload button");
        BrowserUtils.wait(2);

        assertTestCase.assertTrue(researchLinePage.AlertMessage.getText().contains("Entities in your portfolio found in our coverage. Please upload a different portfolio or check your file to try again."), "Verify error message");
        BrowserUtils.waitForClickablility(researchLinePage.closeAlert, 10).click();
        researchLinePage.closeUploadModal();

        PortfolioQueries portfolioQueries = new PortfolioQueries();
        assertTestCase.assertEquals(portfolioQueries.getEntityDetailsWithOrbisId("111111111").size(),0, "Unmatched ornis id should not be available");

    }
}
