package com.esgc.Pages;

import com.esgc.Utilities.BrowserUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.Map;

public class EMCAccountsEditUserPage extends EMCBasePage {
    @FindBy (tagName = "body")
    public WebElement body;
    @FindBy (xpath = "//h4")
    public WebElement editPageTitle;

    @FindBy (xpath = "//button[.='Back to Users']")
    public WebElement backToUsersButton;
    @FindBy (xpath = "//button[.='Suspend']")
    public WebElement suspendButton;
    @FindBy (xpath = "//button[.='Unsuspend']")
    public WebElement unsuspendButton;
    @FindBy (xpath = "//button[.='Reset password']")
    public WebElement resetPasswordButton;
    @FindBy (xpath = "//button[.='Resend Activation Email']")
    public WebElement resentActivationEmailButton;
    @FindBy (xpath = "//button[.='Delete']")
    public WebElement deleteButton;
    @FindBy (xpath = "//button[.='Activate']")
    public WebElement activateButton;
    @FindBy (xpath = "//span[.='Details']")
    public WebElement detailsTab;
    @FindBy (xpath = "//span[.='Application ROles']")
    public WebElement applicationRolesTab;
    @FindBy (xpath = "//button[.='Edit']")
    public WebElement editButton;
    @FindBy (xpath = "//h6[.='First Name']/../..//input")
    public WebElement firstNameInput;
    @FindBy (xpath = "//h6[.='Last Name']/../..//input")
    public WebElement lastNameInput;
    @FindBy (xpath = "//h6[.='Email']/../..//input")
    public WebElement emailInput;
    @FindBy (xpath = "//button[.='Cancel']")
    public WebElement cancelButton;
    @FindBy (xpath = "//button[.='Save']")
    public WebElement saveButton;
    @FindBy (xpath = "(//h6)[2]")
    public WebElement status;
    @FindBy (xpath = "//span[starts-with(.,'Created by')]")
    public WebElement createdByInfo;
    @FindBy (xpath = "//span[starts-with(.,'Modified by')]")
    public WebElement modifiedByInfo;
    @FindBy (xpath = "//div[.='This user was provisioned by Okta']")
    public WebElement oktaUserMessage;

    @FindBy (xpath = "//h2[.='Delete users']")
    public WebElement deleteConfirmationPopup;

    @FindBy (xpath = "//h2/../..//button[.='Delete']")
    public WebElement popupDeleteButton;

    @FindBy (xpath = "//button[.='Cancel']")
    public WebElement popupCancelButton;

    @FindBy (xpath = "(//div[@id='notistack-snackbar'])[1]")
    public WebElement deleteMessage;

    @FindBy (xpath = "//p[.='First Name is a required field']")
    public WebElement firstNameInputWarning;

    @FindBy (xpath = "//p[.='Last Name is a required field']")
    public WebElement lastNameInputWarning;
    @FindBy (xpath = "//p[.='User Name is a required field']")
    public WebElement userNameInputWarning;
    @FindBy (xpath = "//p[.='Must be a valid email Address']")
    public WebElement validEmailWarning;
    @FindBy (xpath = "//p[.='Email is a required field']")
    public WebElement emailInputWarning;



    public void editUser(String firstName, String lastName, String emailAddress) {
        System.out.println("firstNameInput = " + firstNameInput.getAttribute("value"));
        System.out.println("lastNameInput = " + lastNameInput.getAttribute("value"));
        System.out.println("emailInput = " + emailInput.getAttribute("value"));
        System.out.println("emailAddress = " + emailAddress);
        editButton.click();

        clear(firstNameInput);
        firstNameInput.sendKeys(firstName);
        clear(lastNameInput);
        lastNameInput.sendKeys(lastName);
        clear(emailInput);
        emailInput.sendKeys(emailAddress);
        BrowserUtils.waitForClickablility(saveButton, 5).click();
    }

    public boolean deleteUser(){
        deleteButton.click();
        System.out.println("delete button clicked");
        BrowserUtils.waitForClickablility(popupDeleteButton, 5).click();
        System.out.println("popup delete button clicked");
        BrowserUtils.waitForVisibility(deleteMessage, 5);
        System.out.println("delete message is displayed");
        return deleteMessage.isDisplayed();
    }

    public Map<String,String> getCurrentInfo() {
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("firstName", firstNameInput.getAttribute("value"));
        userInfo.put("lastName", lastNameInput.getAttribute("value"));
        userInfo.put("email", emailInput.getAttribute("value"));
        userInfo.put("status", status.getText());
        userInfo.put("createdBy", createdByInfo.getText());
        userInfo.put("modifiedBy", modifiedByInfo.getText());
        //userInfo.put("oktaUserMessage", oktaUserMessage.getText());
        System.out.println(userInfo);
        return userInfo;

    }
}
