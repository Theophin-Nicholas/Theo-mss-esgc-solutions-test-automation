package com.esgc.Pages;

import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.CSVUtil;
import com.github.javafaker.Faker;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.util.List;

public class EMCUserImportPage extends EMCBasePage{

    Faker faker = new Faker();

    @FindBy(xpath = "//button[.='Back to account users']")
    public WebElement backToAccountUsersButton;

    @FindBy(xpath = "//h3")
    public WebElement pageTitle;

    @FindBy(xpath = "(//p)[1]")
    public WebElement pageStatement;

    @FindBy(xpath = "(//p)[1]//a[1]")
    public WebElement downloadTemplateLink;

    @FindBy(xpath = "(//p)[1]//a[2]")
    public WebElement checkOutGuideLink;

    @FindBy(xpath = "(//p)[2]")
    public WebElement uploadFileStatement;

    //not displayed
    @FindBy(xpath = "//input")
    public WebElement fileInput;

    @FindBy(xpath = "//button[.='Import']")
    public WebElement importButton;

    @FindBy(xpath = "//div[@role='button']//span")
    public WebElement uploadedFileName;

    @FindBy(xpath = "//div[@role='button']//span/following-sibling::*")
    public WebElement removeFileButton;

    @FindBy(xpath = "(//p)[3]")
    public WebElement uploadResultStatement;

    @FindBy(xpath = "//li//p")
    public WebElement foundIssuesStatement;

    @FindBy(xpath = "//div[@role='dialog']//button[.='Import']")
    public WebElement popupImportButton;

    @FindBy(xpath = "//div[@role='dialog']//button[.='Cancel']")
    public WebElement popupCancelButton;




    public void verifyImportPage(){
        assertTestCase.assertTrue(backToAccountUsersButton.isDisplayed(),"Back to account users button is displayed");
        assertTestCase.assertTrue(pageTitle.isDisplayed(),"Page title is displayed");
        assertTestCase.assertTrue(pageStatement.isDisplayed(),"Page statement is displayed");
        assertTestCase.assertTrue(downloadTemplateLink.isDisplayed(),"Download template link is displayed");
        assertTestCase.assertTrue(checkOutGuideLink.isDisplayed(),"Check out guide link is displayed");
        assertTestCase.assertTrue(uploadFileStatement.isDisplayed(),"Upload file statement is displayed");
        //assertTestCase.assertTrue(fileInput.isDisplayed(),"File input is displayed");
        assertTestCase.assertTrue(importButton.isDisplayed(),"Import button is displayed");
    }

    public void clickBackToAccountUsersButton(){
        BrowserUtils.waitAndClick(backToAccountUsersButton, 5);
    }

    public boolean downloadAndVerifyTemplate(){
        BrowserUtils.deleteFilesInDownloadsFolder();
        BrowserUtils.waitAndClick(downloadTemplateLink, 10);
        System.out.println("Clicked on download template link");
        BrowserUtils.wait(5);
        return BrowserUtils.verifyFileDownloaded("user-template.csv");
    }


    public boolean verifyTemplateContent() {
        CSVUtil csvUtil = new CSVUtil(BrowserUtils.downloadPath(), "user-template.csv");
        List<String> columnNames = csvUtil.getColumnNames();
        System.out.println("columnNames = " + columnNames);
        List<String> expectedColumnNames = List.of("FirstName","LastName","Email","ApplicationRoles","SendActivationEmail");
        return columnNames.containsAll(expectedColumnNames);
    }

    public boolean uploadTemplateAndVerifyUsers() {
        //edit users in template
        enterUserToTemplate(1, "QA Test", "User1", faker.internet().emailAddress(), "15121703", "no");
        enterUserToTemplate(2, "QA Test", "User2", faker.internet().emailAddress(), "15121703", "yes");

        //send address of template to file input
        fileInput.sendKeys(BrowserUtils.downloadPath() + File.separator +"user-template.csv");
        BrowserUtils.wait(5);
        //click import button
        assertTestCase.assertTrue(importButton.isEnabled(),"Import button is enabled");
        assertTestCase.assertTrue(uploadedFileName.isDisplayed(),"Uploaded file name is displayed");
        System.out.println("uploadedFileName = " + uploadedFileName.getText());
        assertTestCase.assertTrue(uploadResultStatement.isDisplayed(),"Found users statement is displayed");
        System.out.println("foundUsersStatement = " + uploadResultStatement.getText());
        assertTestCase.assertEquals(uploadResultStatement.getText(),"We found 2 users in the file.","Found users statement contains 2 users found");
        BrowserUtils.waitAndClick(importButton, 10);
        System.out.println("Clicked on import button");

        //verify popup
        wait(popupImportButton, 10);
        assertTestCase.assertTrue(popupImportButton.isDisplayed(),"Popup import button is displayed");
        assertTestCase.assertTrue(popupCancelButton.isDisplayed(),"Popup cancel button is displayed");
        BrowserUtils.waitAndClick(popupImportButton, 10);
        EMCAccountDetailsPage accountDetailsPage = new EMCAccountDetailsPage();
        wait(accountDetailsPage.userNamesList, 10);
        assertTestCase.assertTrue(accountDetailsPage.notification.isDisplayed(),"Importing users notification is displayed");
        for (int i = 0; i < 10; i++) {
            if(accountDetailsPage.verifyUser("QA Test User1") && accountDetailsPage.verifyUser("QA Test User2")){
                break;
            }
            BrowserUtils.wait(1);
            BrowserUtils.refresh();
        }
        assertTestCase.assertTrue(accountDetailsPage.verifyUser("QA Test User1"),"QA Test User1 is imported");
        assertTestCase.assertTrue(accountDetailsPage.verifyUser("QA Test User2"),"QA Test User2 is imported");
        return true;
    }

    public void enterRandomUserToTemplate(int rowNum) {
        enterUserToTemplate(rowNum, "QA Test", "User", faker.internet().emailAddress(), "15121703", "no");
    }

    public void enterUserToTemplate(int rowNum, String firstName, String lastName, String email, String applicationRoles, String sendActivationEmail) {
        CSVUtil csvUtil = new CSVUtil(BrowserUtils.downloadPath(), "user-template.csv");
        csvUtil.setCell(rowNum, 0, firstName);
        csvUtil.setCell(rowNum, 1, lastName);
        csvUtil.setCell(rowNum, 2, email);
        csvUtil.setCell(rowNum, 3, applicationRoles);
        csvUtil.setCell(rowNum, 4, sendActivationEmail);
    }

    public boolean uploadTemplateAndVerifyTemplateWithWrongData() {
        //edit users in template
        enterUserToTemplate(1, "", "", "", "", "");
        enterUserToTemplate(2, "", "", "", "", "");

        //send address of template to file input
        fileInput.sendKeys(BrowserUtils.downloadPath() + File.separator +"user-template.csv");
        //BrowserUtils.wait(5);
        //click import button

        assertTestCase.assertTrue(uploadedFileName.isDisplayed(),"Uploaded file name is displayed");
        System.out.println("uploadedFileName = " + uploadedFileName.getText());
        assertTestCase.assertTrue(uploadResultStatement.isDisplayed(),"Found users statement is displayed");
        System.out.println("foundUsersStatement = " + uploadResultStatement.getText());
        assertTestCase.assertEquals(uploadResultStatement.getText(),"We found 1 issue:","Found users statement contains 2 users found");
        assertTestCase.assertTrue(foundIssuesStatement.isDisplayed(),"Found issues statement is displayed");
        assertTestCase.assertTrue(foundIssuesStatement.getText().contains("The uploaded file is empty. Check out the help page for more information."),
                "The uploaded file is empty message is displayed");
        assertTestCase.assertFalse(importButton.isEnabled(),"Import button is disabled");
        BrowserUtils.waitAndClick(removeFileButton, 10);

        //Template file have users with Wrong First Name
        verifyTemplateWithData("firstName", "", "First Name is a required field");
        verifyTemplateWithData("firstName", "", "Must be 1 characters or more");
        verifyTemplateWithData("firstName", " ", "First Name must be a trimmed string");
        verifyTemplateWithData("firstName", faker.lorem().characters(256), "Must be 255 characters or less");

        //Template file have users with Wrong Last Name
        verifyTemplateWithData("lastName", "", "Last Name is a required field");
        verifyTemplateWithData("lastName", " ", "Last Name must be a trimmed string");
        verifyTemplateWithData("lastName", "", "Must be 1 characters or more");
        verifyTemplateWithData("lastName", faker.lorem().characters(256), "Must be 255 characters or less");

        //Template file have users with Wrong Email
        verifyTemplateWithData("email", "", "Must be a valid email Address");
        verifyTemplateWithData("email", " ", "Must be a valid email Address");
        verifyTemplateWithData("email", "test", "Must be a valid email Address");
        verifyTemplateWithData("email", "test@", "Must be a valid email Address");
        verifyTemplateWithData("email", "test@gmail", "Must be a valid email Address");
        verifyTemplateWithData("email", "test@gmail .com", "Must be a valid email Address");
        //verifyTemplateWithData("email", "test-@gmail.com", "Must be a valid email Address");
        //verifyTemplateWithData("email", "test.test@gmail.com", "Must be a valid email Address");
        verifyTemplateWithData("email", ".test@gmail.com", "Must be a valid email Address");
        //verifyTemplateWithData("email", "1test@gmail.com", "Must be a valid email Address");
        //verifyTemplateWithData("email", "test#test@gmail.com", "Must be a valid email Address");
        //verifyTemplateWithData("email", "test@gmail.c", "Must be a valid email Address");
        verifyTemplateWithData("email", "test@gmail#test.com", "Must be a valid email Address");
        verifyTemplateWithData("email", "test@gmail..com", "Must be a valid email Address");

        //Template file have users with Wrong Application Role
        verifyTemplateWithData("applicationRole", "", "Must be 1 role or more");
        verifyTemplateWithData("applicationRole", " ", "Must be a valid key xxx-xxx");

        clickBackToAccountUsersButton();
        return true;
    }

    public void verifyTemplateWithData(String field, String data, String message){
        BrowserUtils.refresh();
        switch (field){
            case "firstName":
                enterUserToTemplate(1, data, "User1", faker.internet().emailAddress(), "15121703", "no");
                break;
            case "lastName":
                enterUserToTemplate(1, "QA Test", data, faker.internet().emailAddress(), "15121703", "no");
                break;
            case "email":
                enterUserToTemplate(1, "QA Test", "User1", data, "15121703", "no");
                break;

            case "applicationRole":
                enterUserToTemplate(1, "QA Test", "User1", faker.internet().emailAddress(), data, "no");
                break;
            case "sendActivationEmail":
                enterUserToTemplate(1, "QA Test", "User1", faker.internet().emailAddress(), "15121703", data);
                break;
        }
        fileInput.sendKeys(BrowserUtils.downloadPath() + File.separator +"user-template.csv");
        //BrowserUtils.wait(5);
        assertTestCase.assertTrue(uploadedFileName.isDisplayed(),"Uploaded file name is displayed");
        System.out.println("uploadedFileName = " + uploadedFileName.getText());
        assertTestCase.assertTrue(uploadResultStatement.isDisplayed(),"Found users statement is displayed");
        System.out.println("foundUsersStatement = " + uploadResultStatement.getText());
        assertTestCase.assertEquals(uploadResultStatement.getText(),"We found 1 issue:","Found users statement contains 2 users found");
        assertTestCase.assertTrue(foundIssuesStatement.isDisplayed(),"Found issues statement is displayed");
        System.out.println("foundIssuesStatement = " + foundIssuesStatement.getText());
        assertTestCase.assertTrue(foundIssuesStatement.getText().contains(message),"Correct Invalid data message is displayed");
        assertTestCase.assertFalse(importButton.isEnabled(),"Import button is disabled");
        BrowserUtils.waitAndClick(removeFileButton, 10);
    }
}
