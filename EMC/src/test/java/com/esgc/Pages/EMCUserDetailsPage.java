package com.esgc.Pages;

import com.esgc.Utilities.BrowserUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EMCUserDetailsPage extends EMCBasePage {
    @FindBy(tagName = "h4")
    public WebElement userDetailsPageTitle;

    @FindBy(xpath = "(//h6)[2]")
    public WebElement userStatus;

    @FindBy(xpath = "(//h6)[4]")
    public WebElement lastLoginInfo;

    @FindBy(xpath = "//button[.='Suspend']")
    public WebElement suspendButton;

    @FindBy(xpath = "//button[.='Unsuspend']")
    public WebElement unsuspendButton;

    @FindBy(xpath = "//button[.='Reset password']")
    public WebElement resetPasswordButton;

    @FindBy(xpath = "//button[.='Resend Activation Email']")
    public WebElement resendActivationEmailButton;

    @FindBy(xpath = "//button[.='Delete']")
    public WebElement deleteButton;

    @FindBy(xpath = "//button[.='Activate']")
    public WebElement activateButton;

    @FindBy(xpath = "//div[.='Delete users']/..//button[.='Delete']")
    public WebElement popupDeleteButton;

    @FindBy(xpath = "//div[.='Delete users']/..//button[.='Cancel']")
    public WebElement popupCancelButton;

    @FindBy(xpath = "(//input)[1]")
    public WebElement firstNameInput;

    @FindBy(xpath = "(//input)[2]")
    public WebElement lastNameInput;

    @FindBy(xpath = "(//input)[3]")
    public WebElement emailNameInput;

    @FindBy(xpath = "//span[starts-with(.,'Created by')]")
    public WebElement createdByInfo;

    @FindBy(xpath = "//span[starts-with(.,'Modified by')]")
    public WebElement modifiedByInfo;

    @FindBy(xpath = "//span[.='Details']")
    public WebElement detailsTab;

    @FindBy(xpath = "//span[.='Application Roles']")
    public WebElement applicationRolesTab;

    @FindBy(xpath = "//ul//p[2]")
    public List<WebElement> applicationRolesList;

    @FindBy(xpath = "//li/div[1]//span")
    public List<WebElement> applicationRolesListFeaturesNames;

    @FindBy(xpath = "//ul//button")
    public List<WebElement> applicationRolesListDeleteButtons;

    @FindBy(xpath = "//button[.='Assign application roles']")
    public WebElement assignApplicationRolesButton;

    @FindBy(xpath = "//h2/../..//p[2]")
    public List<WebElement> newApplicationRolesList;

    @FindBy(xpath = "//h2/../..//button")
    public List<WebElement> newApplicationRolesAssignButtons;

    @FindBy(xpath = "//button[.='Done']")
    public WebElement doneButton;

    @FindBy(xpath = "//button[.='Back to Users']")
    public WebElement backToUserButton;

    public void addApplicationRoles() {
        BrowserUtils.waitForClickablility(assignApplicationRolesButton, 15).click();
        BrowserUtils.waitForClickablility(newApplicationRolesList.get(0), 5).click();
        BrowserUtils.wait(5);
        BrowserUtils.waitForClickablility(newApplicationRolesAssignButtons.get(0), 10).click();
        System.out.println("application assign button clicked");
        BrowserUtils.wait(3);
        BrowserUtils.waitForClickablility(doneButton, 15).click();
        BrowserUtils.wait(3);
        System.out.println("done button clicked");
        BrowserUtils.waitForClickablility(applicationRolesTab, 10).click();
    }

    public void addApplicationRoles(String applicationName) {
        BrowserUtils.waitForClickablility(assignApplicationRolesButton, 15).click();
        for (WebElement applicationRole : newApplicationRolesList) {
            if (applicationRole.getText().equals(applicationName)) {
                System.out.println("application role found in available applications list");
                applicationRole.click();
                BrowserUtils.wait(5);
                System.out.println(newApplicationRolesAssignButtons.size() + " new application roles will be assigned");
                BrowserUtils.waitForClickablility(newApplicationRolesAssignButtons.get(0), 10).click();
                System.out.println("application assign button clicked");
                BrowserUtils.scrollTo(doneButton);
                BrowserUtils.waitForClickablility(doneButton, 15).click();
                System.out.println("done button clicked");
                BrowserUtils.waitForClickablility(detailsTab, 10).click();
                BrowserUtils.wait(3);
                BrowserUtils.waitForClickablility(applicationRolesTab, 10).click();
                BrowserUtils.wait(5);
                break;
            }
        }
        System.out.println("application role not found in available applications list");
    }

    public boolean verifyApplicationRole(String applicationName) {
        BrowserUtils.wait(3);
        for (WebElement applicationRole : applicationRolesList) {
            if (applicationRole.getText().equals(applicationName)) {
                System.out.println("Application role found");
                return true;
            }
        }
        System.out.println("Application role not found");
        return false;
    }

    public boolean deleteApplicationRole(String applicationName) {
        System.out.println("Deleting all application roles for " + applicationName);
        BrowserUtils.waitForClickablility(applicationRolesTab, 10).click();
        for (WebElement applicationRole : applicationRolesList) {
            if (applicationRole.getText().equals(applicationName)) {
                System.out.println("Application Found");
                applicationRole.click();
                System.out.println(applicationRolesListFeaturesNames.size() + " application roles will be deleted");
                boolean check = true;
                while (check) {
                    for (WebElement deleteButton : applicationRolesListDeleteButtons) {
                        if (deleteButton.isDisplayed()) {
                            deleteButton.click();
                            check = true;
                            break;
                        }
                    }
                    check = false;
                }
                System.out.println("All application features deleted successfully");
                BrowserUtils.waitForClickablility(detailsTab, 10).click();
                BrowserUtils.wait(2);
                BrowserUtils.waitForClickablility(applicationRolesTab, 10).click();
                BrowserUtils.wait(5);
                return true;
            }
        }
        System.out.println("No applications found to delete");
        return false;
    }

    public void clickOnSuspendButton() {
        System.out.println("Clicking on suspend button");
        BrowserUtils.waitForClickablility(suspendButton, 15).click();
    }

    public void clickOnUnsuspendButton() {
        System.out.println("Clicking on unsuspend button");
        BrowserUtils.waitForClickablility(unsuspendButton, 15).click();
    }

    public void clickOnActivateButton() {
        System.out.println("Clicking on activate button");
        BrowserUtils.waitForClickablility(activateButton, 15).click();
    }

    public void deleteUser() {
        System.out.println("Clicking on delete button");
        BrowserUtils.waitForClickablility(deleteButton, 15).click();
        System.out.println("Clicking on confirm delete button");
        BrowserUtils.waitForClickablility(popupDeleteButton, 15).click();
    }

    public void clickOnResendActivationEmailButton() {
        System.out.println("Clicking on resend activation email button");
        BrowserUtils.waitForClickablility(resendActivationEmailButton, 15).click();
    }

    public void clickUnsuspendUserButton() {
        System.out.println("Clicking on unsuspend user button");
        BrowserUtils.waitForClickablility(unsuspendButton, 15).click();
    }
}
