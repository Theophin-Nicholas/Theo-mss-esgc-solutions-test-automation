package com.esgc.Pages;

import com.esgc.Utilities.BrowserUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EMCUserDetailsPage extends EMCBasePage {
    @FindBy(tagName = "h4")
    public WebElement userDetailsPageTitle;

    @FindBy(xpath = "//h4/following-sibling::div[1]//span")
    public WebElement accountInfoButton;

    @FindBy(xpath = "//h4/following-sibling::div/div[1]")
    public WebElement providerInfo;

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
    public WebElement emailInput;

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

    @FindBy(xpath = "//div[@aria-expanded='true']//..//button")
    public List<WebElement> applicationRolesListDeleteButtons;

    @FindBy(xpath = "//button[.='Assign application roles']")
    public WebElement assignApplicationRolesButton;

    @FindBy(xpath = "//h2/../..//p[2]")
    public List<WebElement> newApplicationRolesList;

    @FindBy(xpath = "//h2/../..//button")
    public List<WebElement> newApplicationRolesAssignButtons;

    @FindBy(xpath = "//button[.='Assign']/../..//span[1]")
    public List<WebElement> newApplicationRolesNames;

    @FindBy(xpath = "//button[.='Done']")
    public WebElement doneButton;

    @FindBy(xpath = "//button[.='Back to Users']")
    public WebElement backToUserButton;

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

                break;
            }
        }
        BrowserUtils.scrollTo(doneButton);
        BrowserUtils.waitForClickablility(doneButton, 15).click();
        System.out.println("done button clicked");
        BrowserUtils.waitForClickablility(detailsTab, 10).click();
        BrowserUtils.wait(3);
        BrowserUtils.waitForClickablility(applicationRolesTab, 10).click();
        BrowserUtils.wait(5);
        System.out.println("application role not found in available applications list");
    }

    public boolean verifyApplicationRole(String applicationName) {
        BrowserUtils.waitForClickablility(applicationRolesTab, 10).click();
        //wait(applicationRolesList.get(), 5);
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
//        wait(applicationRolesList, 10);
        for (WebElement applicationRole : applicationRolesList) {
            if (applicationRole.getText().equals(applicationName)) {
                System.out.println("Application Found");
                applicationRole.click();
                System.out.println(applicationRolesListFeaturesNames.size() + " application roles will be deleted");
                while (applicationRolesListDeleteButtons.size() > 0) {
                    applicationRolesListDeleteButtons.get(0).click();
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

    public void clickOnApplicationRolesTab() {
        System.out.println("Clicking on application roles tab");
        wait(applicationRolesTab,20);
        BrowserUtils.waitForClickablility(applicationRolesTab, 15).click();
    }

    public void deleteAllRoles() {
        for (WebElement roles : applicationRolesList) {
            roles.click();
            for (WebElement deleteButton : applicationRolesListDeleteButtons) {
                deleteButton.click();
            }
        }
    }

    public void assignApplicationRoles(String applicationName, String roleName) {
        System.out.println("Assigning application roles");
        BrowserUtils.waitForClickablility(assignApplicationRolesButton, 15).click();
        for (WebElement applicationRole : newApplicationRolesList) {
            if (applicationRole.getText().contains(applicationName)) {
                System.out.println("Application Found");
                applicationRole.click();
                BrowserUtils.wait(5);
                for (WebElement roles : newApplicationRolesNames) {
                    if (roles.getText().equals(roleName)) {
                        System.out.println("Role Found");
                        newApplicationRolesNames.get(newApplicationRolesNames.indexOf(roles) + 1).click();
                        System.out.println("application assign button clicked");
                        break;
                    }
                }
                BrowserUtils.scrollTo(doneButton);
                BrowserUtils.waitForClickablility(doneButton, 15).click();
                System.out.println("done button clicked");
                BrowserUtils.waitForClickablility(detailsTab, 10).click();
                BrowserUtils.wait(3);
                BrowserUtils.waitForClickablility(applicationRolesTab, 10).click();
                BrowserUtils.wait(5);
                return;
            }
        }
        System.out.println("application role not found in available applications list");
    }

    public void assignApplicationRoles(String applicationName) {
        BrowserUtils.waitForClickablility(applicationRolesTab, 15).click();
        System.out.println("Assigning application roles");
        BrowserUtils.waitForClickablility(assignApplicationRolesButton, 15).click();
        for (WebElement applicationRole : newApplicationRolesList) {
            if (applicationRole.getText().equals(applicationName)) {
                System.out.println("Application Found");
                applicationRole.click();
                BrowserUtils.wait(5);
                for (int i = 0; i < newApplicationRolesNames.size(); i++) {
                    newApplicationRolesNames.get(i).click();
                    System.out.println("Role added = " + newApplicationRolesNames.get(i).getText());
                }
                BrowserUtils.scrollTo(doneButton);
                BrowserUtils.waitForClickablility(doneButton, 15).click();
                System.out.println("done button clicked");
                BrowserUtils.waitForClickablility(detailsTab, 10).click();
                BrowserUtils.wait(3);
                BrowserUtils.waitForClickablility(applicationRolesTab, 10).click();
                BrowserUtils.wait(5);
                return;
            }
        }
        System.out.println("application role not found in available applications list");
    }

    public boolean verifyUserDetails() {
        assertTestCase.assertTrue(firstNameInput.isDisplayed(), "First Name is displayed");
        assertTestCase.assertTrue(lastNameInput.isDisplayed(), "Last Name is displayed");
        assertTestCase.assertTrue(emailInput.isDisplayed(), "Email is displayed");
        assertTestCase.assertTrue(userStatus.isDisplayed(), "User Status is displayed");
        assertTestCase.assertTrue(lastLoginInfo.isDisplayed(), "Last Login Info is displayed");
        assertTestCase.assertTrue(providerInfo.isDisplayed(), "Last Login Date is displayed");
        assertTestCase.assertTrue(userDetailsPageTitle.isDisplayed(), "User Details Page Title is displayed");
        return true;
    }
}
