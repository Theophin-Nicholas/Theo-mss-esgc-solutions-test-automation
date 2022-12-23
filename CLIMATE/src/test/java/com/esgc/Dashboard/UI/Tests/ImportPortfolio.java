package com.esgc.Dashboard.UI.Tests;

import com.esgc.Base.TestBases.DashboardUITestBase;
import com.esgc.Base.TestBases.DataProvider;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.Utilities.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ImportPortfolio extends DashboardUITestBase {


    //Test Cases: 303, 304, 305, 306, 307,985, 1298,
//main test case 3218
    @Test(groups = {"regression", "ui", "smoke", "robot_dependency", "dashboard"})
    @Xray(test = 3218)
    public void VerifyFileUploadSuccessPopup() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");

        dashboardPage.clickUploadPortfolioButton();
        test.info("Clicked on Upload button");
        test.info("Navigated to Upload Page");

        BrowserUtils.waitForVisibility(dashboardPage.uploadButton, 2);
        test.info("Waited for upload button's visibility");

        test.info("Clicked on the Browse File button");
        dashboardPage.clickBrowseFile();
        BrowserUtils.wait(2);

        String inputFile = System.getProperty("user.dir") + ConfigurationReader.getProperty("PortfolioWithValidData");
        RobotRunner.selectFileToUpload(inputFile);

        BrowserUtils.wait(4);

        String expectedFileName = "\"" + inputFile.substring(inputFile.lastIndexOf(File.separator) + 1) + "\"";
        String actualFileName = dashboardPage.selectedFileName.getText().substring(0, dashboardPage.selectedFileName.getText().indexOf("Remove") - 1);

        assertTestCase.assertEquals(actualFileName, expectedFileName, "File selected from dialog", 304, 305, 306);

        assertTestCase.assertTrue(dashboardPage.isRemoveButtonPresent(), "Remove hyperlink is displayed", 307);
        test.info("Import portfolio file was selected");

        assertTestCase.assertFalse(dashboardPage.isBrowseButtonPresent(), "Browse button is disabled", 308);
        dashboardPage.clickUploadButton();
        test.info("Clicked on the Upload button");
        BrowserUtils.wait(3);

        assertTestCase.assertTrue(dashboardPage.checkIfUploadingMaskIsDisplayed(), "Load mask");

        BrowserUtils.waitForVisibility(dashboardPage.successMessagePopUP, 2);
        test.info("Waited for the Successful popup's visibility");

        assertTestCase.assertTrue(dashboardPage.checkifSuccessPopUpIsDisplyed(), "Success pop up is displayed", 335);
        test.pass("Verified:After a successful file upload,Success popup was displayed successfully");

        assertTestCase.assertEquals(dashboardPage.AlertMessage.getText(), "Portfolio Upload Successfully Saved", "Success message verified", 335);
        test.pass("Verified:Message for successful portfolio upload was as expected");

        assertTestCase.assertTrue(dashboardPage.CheckifClosebuttonIsDisplayed(), "Close button displayed");
        test.pass("Verified:'X' button was displayed on the successful message popup");

        String expectedPortfolioName = "Portfolio Upload updated_good";
        assertTestCase.assertEquals(dashboardPage.getPlaceholderInSuccessPopUp(), expectedPortfolioName, "Portfolio name displayed in pop up");

        dashboardPage.waitForDataLoadCompletion();

        assertTestCase.assertEquals(dashboardPage.getSelectedPortfolioNameFromDropdown(), expectedPortfolioName, "Portfolio name verification", 1298);
        assertTestCase.assertTrue(dashboardPage.getPortfolioNameInSummaryHeaders().endsWith(expectedPortfolioName), "Portfolio name in subtitle");
        BrowserUtils.wait(10);
        assertTestCase.assertFalse(dashboardPage.checkifSuccessPopUpIsDisplyed(), "Success pop up disappeared", 337);

    }


    @Test(groups = {"regression", "ui", "smoke", "robot_dependency", "dashboard"})
    public void VerifyFileUploadErrorPopup() {
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");
        test.info("Clicked on Upload button");

        dashboardPage.clickUploadPortfolioButton();
        test.info("Navigated to Upload Page");

        test.info("Clicked on the Browse File button");
        dashboardPage.clickBrowseFile();
        BrowserUtils.wait(2);

        String inputFile = System.getProperty("user.dir") + ConfigurationReader.getProperty("PortfolioWithInvalidData");

        RobotRunner.selectFileToUpload(inputFile);

        BrowserUtils.wait(4);
        test.info("Import portfolio file was selected");
        dashboardPage.clickUploadButton();
        test.info("Clicked on the Upload button");
        BrowserUtils.wait(4);
        Assert.assertTrue(dashboardPage.CheckifErrorPopUpIsDisplyed());
        test.pass("Verified:After an unsuccessful file upload attempt,error popup was displayed successfully");

    }

    //test case: 336
    @Test(groups = {"regression", "ui", "smoke", "robot_dependency", "dashboard"})
    public void VerifyPortfolioCanBeRenamed() {
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");
        test.info("Clicked on Upload button");

        dashboardPage.clickUploadPortfolioButton();
        test.info("Navigated to Upload Page");

        BrowserUtils.waitForVisibility(dashboardPage.uploadButton, 2);
        test.info("Waited for upload button's visibility");

        test.info("Clicked on the Browse File button");
        dashboardPage.clickBrowseFile();
        BrowserUtils.wait(2);

        String inputFile = System.getProperty("user.dir") + ConfigurationReader.getProperty("PortfolioWithValidData");
        RobotRunner.selectFileToUpload(inputFile);

        BrowserUtils.wait(4);
        test.info("Import portfolio file was selected");
        dashboardPage.clickUploadButton();
        test.info("Clicked on the Upload button");
        BrowserUtils.wait(3);

        BrowserUtils.waitForVisibility(dashboardPage.successMessagePopUP, 2);
        test.info("Waited for the Successful popup's visibility");

        assertTestCase.assertTrue(dashboardPage.CheckifNameInputFielIsPresent(), "File can be renamed from pop up", 336);
        test.pass("Verified:After a successful file upload,A name input field was displayed on successful popup message");

        String newPortfolioName = ConfigurationReader.getProperty("NewPortfolioName");
        dashboardPage.NameInputField.sendKeys(newPortfolioName);
        test.info("Entered the new name for the uploaded portfolio");

        dashboardPage.clickOnButtonWithLabel("Save");
        test.info("Clicked on Save button");

        dashboardPage.waitForDataLoadCompletion();

        assertTestCase.assertEquals(dashboardPage.getSelectedPortfolioNameFromDropdown(), newPortfolioName, "File is renamed", 336);
        assertTestCase.assertEquals(dashboardPage.getPortfolioNameInSummaryHeaders(), newPortfolioName, "File is renamed", 336);

    }

    //test case:ESGCA-985
    @Test(groups = {"regression", "ui", "smoke", "dashboard"})
    public void VerifyUploadModal() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");

        dashboardPage.clickUploadPortfolioButton();
        test.info("Clicked on Upload button");
        test.info("Navigated to Upload Page");

        BrowserUtils.waitForVisibility(dashboardPage.uploadButton, 2);
        BrowserUtils.wait(5);
        assertTestCase.assertTrue(dashboardPage.checkIfPortfolioUploadModalIsDisplayed(), "Portfolio Upload Modal Displayed", 985);
        assertTestCase.assertTrue(dashboardPage.checkIfPortfolioUploadModalSubTitleIsDisplayedAsExpected(), "Sub Title Displayed");
        assertTestCase.assertTrue(dashboardPage.checkIfPortfolioUploadModalDescriptionIsDisplayedAsExpected(), "Modal Description Verification");
        assertTestCase.assertTrue(dashboardPage.downloadTemplateLink.isDisplayed(), "Download Template Link displayed", 188);
        assertTestCase.assertTrue(dashboardPage.browseFileButton.isEnabled(), "Browse File button is available", 303);
        assertTestCase.assertTrue(dashboardPage.checkIfCloseUploadModalButtonIsDisabled(), "Close button verification");
        assertTestCase.assertTrue(dashboardPage.checkIfUploadButtonIsDisabled(), "Upload button disabled", 250);

        dashboardPage.closeUploadModal();
        BrowserUtils.wait(3);
        assertTestCase.assertFalse(dashboardPage.checkIfPortfolioUploadModalIsDisplayed(), "Modal closed");
        test.pass("Upload modal verified");
    }

    //test case:338
    @Test(groups = {"regression", "ui", "robot_dependency", "dashboard"})
    public void VerifySaveButtonVisibility() {

        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");
        test.info("Clicked on Upload button");

        dashboardPage.clickUploadPortfolioButton();
        test.info("Navigated to Upload Page");

        BrowserUtils.waitForVisibility(dashboardPage.uploadButton, 2);
        test.info("Waited for upload button's visibility");

        test.info("Clicked on the Browse File button");
        dashboardPage.clickBrowseFile();
        BrowserUtils.wait(2);

        String inputFile = System.getProperty("user.dir") + ConfigurationReader.getProperty("PortfolioWithValidData");
        RobotRunner.selectFileToUpload(inputFile);

        BrowserUtils.wait(4);
        test.info("Import portfolio file was selected");
        dashboardPage.clickUploadButton();
        test.info("Clicked on the Upload button");

        assertTestCase.assertTrue(dashboardPage.checkifSuccessPopUpIsDisplyed(), "Success Pop Up presented", 338);
        test.info("Waited for the Successful popup's visibility");

        assertTestCase.assertFalse(dashboardPage.isSaveButtonPresent(), "Save button is not displayed", 338);
        test.pass("Verified:After a successful file upload,Save button was not present by default on Successful message popup.");

        dashboardPage.clickCloseButton();
        BrowserUtils.wait(2);
        Assert.assertFalse(dashboardPage.checkifSuccessPopUpIsDisplyed());
    }


    //test case:316
    @Test(groups = {"regression", "ui", "smoke", "robot_dependency", "dashboard"})
    public void VerifyFileRemovedFromUploadModal() {

        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");
        test.info("Clicked on Upload button");

        dashboardPage.clickUploadPortfolioButton();
        test.info("Navigated to Upload Page");

        BrowserUtils.waitForVisibility(dashboardPage.uploadButton, 2);
        test.info("Waited for upload button's visibility");

        test.info("Clicked on the Browse File button");
        dashboardPage.clickBrowseFile();
        BrowserUtils.wait(2);

        String inputFile = System.getProperty("user.dir") + ConfigurationReader.getProperty("PortfolioWithValidData");
        RobotRunner.selectFileToUpload(inputFile);

        BrowserUtils.wait(4);
        test.info("Import portfolio file was selected");
        dashboardPage.isRemoveButtonPresent();
        test.info("Clicked on Remove button");
        dashboardPage.clickRemoveButton();
        BrowserUtils.wait(3);

        assertTestCase.assertTrue(dashboardPage.isFileRemovedFromModal(), "File removed", 316);
        test.pass("Remove button verified");
    }

    @Test(groups = {"regression", "ui", "errorMessages",  "robot_dependency", "dashboard"},
            dataProviderClass = DataProvider.class, dataProvider = "ErrorMessages", singleThreaded = true)
    public void importPortfolio_verifyErrorPopupMessages(String fileName, String errorMessage, Integer... testCaseNumber) {
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");
        test.info("Clicked on Upload button");

        dashboardPage.clickUploadPortfolioButton();
        test.info("Navigated to Upload Page");

        test.info("Clicked on the Browse File button");
        dashboardPage.clickBrowseFile();
        BrowserUtils.wait(2);

        String inputFile = PortfolioFilePaths.getFilePathForInvalidPortfolio(fileName);
        RobotRunner.selectFileToUpload(inputFile);

        BrowserUtils.wait(4);
        test.info("Import portfolio file was selected");
        dashboardPage.clickUploadButton();
        test.info("Clicked on the Upload button");
        BrowserUtils.wait(2);

        String popUpMessage = dashboardPage.getErrorMessage();
        String expectedErrorMessage = errorMessage.trim().replaceAll("  ", " ").replace(" ", " ");

        assertTestCase.assertTrue(dashboardPage.CheckifErrorPopUpIsDisplyed(), "Error Pop Up Displayed", testCaseNumber);
        assertTestCase.assertEquals(popUpMessage, expectedErrorMessage, "Error Message Verification", testCaseNumber);
        test.pass("Verified:After an unsuccessful file upload attempt,error popup was displayed successfully");
        test.pass("Verified:Error Message");

    }

    @Test(groups = {"regression", "ui", "smoke", "robot_dependency", "dashboard"}, singleThreaded = true)
    @Xray(test = 984)
    public void importPortfolio_verifyUnknownIdentifierMessage() {
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");
        test.info("Clicked on Upload button");

        dashboardPage.clickUploadPortfolioButton();
        test.info("Navigated to Upload Page");

        BrowserUtils.waitForVisibility(dashboardPage.uploadButton, 2);
        test.info("Waited for upload button's visibility");

        test.info("Clicked on the Browse File button");
        dashboardPage.clickBrowseFile();
        BrowserUtils.wait(2);

        String inputFile = PortfolioFilePaths.getFilePathForInvalidPortfolio("InvalidIdentifier.csv");
        RobotRunner.selectFileToUpload(inputFile);

        BrowserUtils.wait(4);
        test.info("Import portfolio file was selected");
        dashboardPage.clickUploadButton();
        test.info("Clicked on the Upload button");
        BrowserUtils.wait(2);

        String popUpMessage = dashboardPage.getUnknownIdentifierPopUpMessage();
        String expectedErrorMessage = "124/126 unique identifiers matched; the system was unable to match 2 identifiers for: Invalid Identifier Included Portfolio";

        Assert.assertTrue(dashboardPage.checkIfUnknownIdentifierPopUpDisplayed());
        Assert.assertEquals(popUpMessage, expectedErrorMessage);

        List<String> identifiers = dashboardPage.getUnknownIdentifiersFromPopUpMessage();
        Assert.assertTrue(identifiers.containsAll(Arrays.asList("Unmatched Identifiers:", "BBBBB", "AAAAAA")));

        identifiers.forEach(each -> test.info("Unknown identifier in portfolio:" + each));

        test.pass("Verified:File uploaded,unknown identifiers popup was displayed successfully");
        test.pass("Verified:Unknown Identifiers Message");
        test.pass("Verified:Unknown Identifiers");

    }



}
