package com.esgc.Pages;

import com.esgc.Utilities.BrowserUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EMCRoleDetailsPage extends EMCBasePage {
    @FindBy(xpath = "//h4")
    public WebElement pageTitle;

    @FindBy(xpath = "//div[.='Roles']")
    public WebElement backToRolesButton;

    @FindBy(xpath = "//span[.='Details']")
    public WebElement detailsTab;

    @FindBy(xpath = "//span[.='Permissions']")
    public WebElement permissionsTab;

    @FindBy(xpath = "//button/span[.='Users']")
    public WebElement usersTab;

    //Details tab Elements
    @FindBy(xpath = "//span[.='General Information']")
    public WebElement generalInfoTag;

    @FindBy(xpath = "//button[.='Edit']")
    public WebElement editButton;

    @FindBy(xpath = "//input[@name='key']")
    public WebElement keyInput;

    @FindBy(xpath = "//input[@name='name']")
    public WebElement nameInput;

    @FindBy(xpath = "//input[@name='description']")
    public WebElement descriptionInput;

    @FindBy(xpath = "//span[starts-with(.,'Created')]")
    public WebElement createdByInfo;

    @FindBy(xpath = "//span[starts-with(.,'Modified')]")
    public WebElement modifiedByInfo;

    //Permissions Tab Elements

    @FindBy(xpath = "//span[.='Role permissions']")
    public WebElement rolePermissionsTag;

    @FindBy(xpath = "//button[.='Assign permissions']")
    public WebElement assignPermissionsButton;

    @FindBy(xpath = "(//div[.='Assign Role Members']/..//input)[1]")
    public WebElement searchPermissionsInput;

    @FindBy(xpath = "//li//span")
    public List<WebElement> permissionNamesList;

    @FindBy(xpath = "//li//p")
    public List<WebElement> permissionKeysList;

    @FindBy(xpath = "//li//button")
    public List<WebElement> permissionDeleteButtons;

    //Users Tab Elements

    @FindBy(xpath = "//span[.='Role members']")
    public WebElement roleMembersTag;

    @FindBy(xpath = "//button[.='Assign members']")
    public WebElement assignMembersButton;

    @FindBy(xpath = "//li//p/../span")
    public List<WebElement> memberNamesList;

    @FindBy(xpath = "//li//p")
    public List<WebElement> memberEmailsList;

    @FindBy(xpath = "//li//button")
    public List<WebElement> memberDeleteButtons;

    //add user to role elements
    @FindBy(xpath = "//h2")
    public WebElement assignRoleMemberPopupTitle;

    @FindBy(xpath = "//input[starts-with(@placeholder,'Search')]")
    public WebElement assignMemberSearchInput;

    @FindBy(xpath = "//span/input")
    public List<WebElement> assignMemberCheckBoxes;

    @FindBy(xpath = "///ul//p/../span")
    public List<WebElement> assignMemberNames;

    @FindBy(xpath = "//button[.='Assign']")
    public WebElement assignButton;

    @FindBy(xpath = "//button[.='Back']")
    public WebElement backButton;

    @FindBy(xpath = "//button[.='Save']")
    public WebElement saveButton;

    @FindBy(xpath = "//button[.='Cancel']")
    public WebElement cancelButton;


    //ROLE DETAILS PAGE METHODS
    public void addUserToRole(String userName) {
        BrowserUtils.waitForClickablility(assignMembersButton, 5).click();
        assignMemberSearchInput.sendKeys(userName);
        assignMemberCheckBoxes.get(0).click();
        BrowserUtils.waitForClickablility(assignButton, 5).click();
        BrowserUtils.wait(5);
        BrowserUtils.waitForClickablility(detailsTab, 15).click();
        BrowserUtils.wait(1);
        BrowserUtils.waitForClickablility(usersTab, 5).click();
        BrowserUtils.wait(3);
    }

    public boolean verifyRoleMember(String userName) {
        for (WebElement member : memberNamesList) {
            BrowserUtils.scrollTo(member);
            if (member.getText().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public void removeMemberFromRole(String userName) {
        for (WebElement member : memberNamesList) {
            BrowserUtils.scrollTo(member);
            if (member.getText().equals(userName)) {
                memberDeleteButtons.get(memberNamesList.indexOf(member)).click();
            }
        }
        BrowserUtils.waitForClickablility(detailsTab, 5).click();
        BrowserUtils.wait(1);
        BrowserUtils.waitForClickablility(usersTab, 5).click();
        BrowserUtils.wait(3);
    }

    public boolean verifyPermission(String permissionName) {
        System.out.println("Verifying permission " + permissionName);
        for (WebElement permission : permissionNamesList) {
            BrowserUtils.scrollTo(permission);
            if (permission.getText().equals(permissionName)) {
                System.out.println("Permission " + permissionName + " found");
                return true;
            }
        }
        return false;
    }

    public void deletePermission(String search_account_users) {
        System.out.println("Deleting permission " + search_account_users);
        for (WebElement permission : permissionNamesList) {
            BrowserUtils.scrollTo(permission);
            if (permission.getText().equals(search_account_users)) {
                permissionDeleteButtons.get(permissionNamesList.indexOf(permission)).click();
                BrowserUtils.wait(1);
                BrowserUtils.waitForClickablility(detailsTab, 5).click();
                BrowserUtils.wait(1);
                BrowserUtils.waitForClickablility(permissionsTab, 5).click();
                BrowserUtils.wait(3);
                break;
            }
        }
    }

    public void assignPermission(String permissionName) {
        System.out.println("Assigning permission " + permissionName);
        BrowserUtils.waitForClickablility(assignPermissionsButton,5).click();
        searchPermissionsInput.sendKeys(permissionName);
        BrowserUtils.wait(3);
        assignMemberCheckBoxes.get(0).click();
        BrowserUtils.waitForClickablility(assignButton,5).click();
        BrowserUtils.wait(3);
        detailsTab.click();
        BrowserUtils.wait(1);
        permissionsTab.click();
        BrowserUtils.wait(3);
    }

    public boolean verifyUser(String userName) {
        System.out.println("Verifying user " + userName);
        for (WebElement member : memberNamesList) {
            BrowserUtils.scrollTo(member);
            if (member.getText().equals(userName)) {
                System.out.println("User " + userName + " found");
                return true;
            }
        }
        return false;
    }

    public void assignMember(String userName) {
        System.out.println("Assigning member " + userName);
        BrowserUtils.scrollTo(assignMembersButton);
        BrowserUtils.waitForClickablility(assignMembersButton,5).click();
        assignMemberSearchInput.sendKeys(userName);
        BrowserUtils.wait(3);
        assignMemberCheckBoxes.get(0).click();
        BrowserUtils.waitForClickablility(assignButton,5).click();
        BrowserUtils.wait(3);
        detailsTab.click();
        BrowserUtils.wait(1);
        usersTab.click();
        BrowserUtils.wait(3);
    }

    public void deleteMember(String userName) {
        System.out.println("Deleting member " + userName);
        for (WebElement member : memberNamesList) {
            BrowserUtils.scrollTo(member);
            if (member.getText().equals(userName)) {
                memberDeleteButtons.get(memberNamesList.indexOf(member)).click();
//                break;
            }
        }
    }
}
