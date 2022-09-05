package com.esgc.Tests;

import com.esgc.Pages.EMCAccountDetailsPage;
import com.esgc.Pages.EMCMainPage;
import com.esgc.Pages.EMCUserDetailsPage;
import com.esgc.Pages.EMCUsersPage;
import com.esgc.TestBases.EMCUITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import com.github.javafaker.Faker;
import org.testng.annotations.Test;

public class UsersPageTests extends EMCUITestBase {
    String userName = "Efrain June2022";
    String userName2 = "Ferhat Test";
    Faker faker = new Faker();
    public void navigateToUser(String userName) {
        EMCMainPage mainPage = new EMCMainPage();
        System.out.println("Navigating to Accounts page");
        //Select config in the left menu
        try {
            mainPage.openSidePanel();
            System.out.println("Side panel is opened");
        } catch (Exception e) {
            System.out.println("Side panel is not opened");
        }
        mainPage.clickUsersButton();
        System.out.println("Users button is clicked");
        EMCUsersPage usersPage = new EMCUsersPage();
        BrowserUtils.waitForVisibility(usersPage.pageTitle, 10);
        if(!userName.isEmpty()) {
            System.out.println("Searching for user: " + userName);
            usersPage.selectUser(userName);
        }
    }
    @Test(groups = {"EMC", "ui", "smoke","regression","prod"})
    @Xray(test = {3033})
    public void verifyAccountsPageTest() {
        EMCMainPage mainPage = new EMCMainPage();
        assertTestCase.assertTrue(mainPage.isEMCTitleIsDisplayed(), "Main Title Verification");
        test.pass("User is on landing page");

        mainPage.openSidePanel();

        mainPage.clickUsersButton();
    }
    @Test(groups = {"EMC", "ui", "regression"})
    @Xray(test = {7625, 7626})
    public void verifyAdminUserSuspendUnsuspendUserTest() {
        navigateToUser(userName2);
        EMCUserDetailsPage userDetailsPage = new EMCUserDetailsPage();
        BrowserUtils.waitForVisibility(userDetailsPage.detailsTab, 10);
        if(userDetailsPage.userStatus.getText().equals("Suspended")) {
            System.out.println("User is suspended");
            userDetailsPage.clickUnsuspendUserButton();
            BrowserUtils.wait(5);
        }
        assertTestCase.assertTrue(userDetailsPage.userStatus.isDisplayed(), "User Details Page  - User status is displayed");
        assertTestCase.assertEquals(userDetailsPage.userStatus.getText(),"Active", "User Details Page  - User status is Active");
        assertTestCase.assertTrue(userDetailsPage.suspendButton.isDisplayed(), "User Details Page  - Suspend button is displayed");
        assertTestCase.assertTrue(userDetailsPage.resetPasswordButton.isDisplayed(), "User Details Page  - Reset Password button is displayed");
        assertTestCase.assertTrue(userDetailsPage.deleteButton.isDisplayed(), "User Details Page  - Delete button is displayed");

        //Suspend user and verify user status
        userDetailsPage.clickOnSuspendButton();
        BrowserUtils.waitForVisibility(userDetailsPage.notification, 10);
        assertTestCase.assertTrue(userDetailsPage.notification.isDisplayed(), "User Details Page  - Notification is displayed");
        BrowserUtils.waitForInvisibility(userDetailsPage.notification, 10);
        assertTestCase.assertEquals(userDetailsPage.userStatus.getText(),"Suspended", "User Details Page  - User status is Suspended");
        assertTestCase.assertTrue(userDetailsPage.unsuspendButton.isDisplayed(), "User Details Page  - Unsuspend button is displayed");
        assertTestCase.assertTrue(userDetailsPage.resetPasswordButton.isDisplayed(), "User Details Page  - Reset Password button is displayed");
        assertTestCase.assertTrue(userDetailsPage.deleteButton.isDisplayed(), "User Details Page  - Delete button is displayed");

        //Unsuspend user and verify user status
        userDetailsPage.clickOnUnsuspendButton();
        BrowserUtils.waitForVisibility(userDetailsPage.notification, 10);
        assertTestCase.assertTrue(userDetailsPage.notification.isDisplayed(), "User Details Page  - Notification is displayed");
        BrowserUtils.waitForInvisibility(userDetailsPage.notification, 10);
        assertTestCase.assertEquals(userDetailsPage.userStatus.getText(),"Active", "User Details Page  - User status is Active");
        assertTestCase.assertTrue(userDetailsPage.suspendButton.isDisplayed(), "User Details Page  - Suspend button is displayed");
        assertTestCase.assertTrue(userDetailsPage.resetPasswordButton.isDisplayed(), "User Details Page  - Reset Password button is displayed");
        assertTestCase.assertTrue(userDetailsPage.deleteButton.isDisplayed(), "User Details Page  - Delete button is displayed");
    }

    @Test(groups = {"EMC", "ui", "regression"})
    @Xray(test = {7627, 7642})
    public void verifyAdminUserActivateStagedUserTest() {
        AccountsPageTests accountsPageTests = new AccountsPageTests();
        accountsPageTests.navigateToAccountsPage(accountsPageTests.accountName,"users");
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        BrowserUtils.waitForVisibility(detailsPage.addUserButton, 10);
        detailsPage.clickOnAddUserButton();
        String firstName = "QATESTUSER";
        String lastName = faker.name().lastName();
        String userName = faker.internet().emailAddress();
        detailsPage.createUser(firstName, lastName, userName, false);

        navigateToUser(firstName + " " + lastName);
        EMCUserDetailsPage userDetailsPage = new EMCUserDetailsPage();
        BrowserUtils.waitForVisibility(userDetailsPage.detailsTab, 10);
        assertTestCase.assertTrue(userDetailsPage.userStatus.isDisplayed(), "User Details Page  - User status is displayed");
        assertTestCase.assertEquals(userDetailsPage.userStatus.getText(),"Staged", "User Details Page  - User status is Staged");
        assertTestCase.assertTrue(userDetailsPage.suspendButton.isDisplayed(), "User Details Page  - Suspend button is displayed");
        assertTestCase.assertFalse(userDetailsPage.suspendButton.isEnabled(), "User Details Page  - Suspend button is not enabled for staged user");
        assertTestCase.assertTrue(userDetailsPage.activateButton.isDisplayed(), "User Details Page  - Reset Password button is displayed");
        assertTestCase.assertTrue(userDetailsPage.deleteButton.isDisplayed(), "User Details Page  - Delete button is displayed");

        //Suspend user and verify user status
        userDetailsPage.clickOnActivateButton();
        BrowserUtils.waitForVisibility(userDetailsPage.notification, 10);
        assertTestCase.assertTrue(userDetailsPage.notification.isDisplayed(), "User Details Page  - Notification is displayed");
        BrowserUtils.waitForInvisibility(userDetailsPage.notification, 10);
        assertTestCase.assertEquals(userDetailsPage.userStatus.getText(),"Pending user action", "User Details Page  - User status is Suspended");
        assertTestCase.assertTrue(userDetailsPage.suspendButton.isDisplayed(), "User Details Page  - Suspend button is displayed");
        assertTestCase.assertTrue(userDetailsPage.suspendButton.isEnabled(), "User Details Page  - Suspend button is enabled for pending user action");
        assertTestCase.assertTrue(userDetailsPage.resendActivationEmailButton.isDisplayed(), "User Details Page  - Resend Activation Email button is displayed");
        assertTestCase.assertTrue(userDetailsPage.deleteButton.isDisplayed(), "User Details Page  - Delete button is displayed");

        //Click Resend Activation Email button
        userDetailsPage.clickOnResendActivationEmailButton();
        BrowserUtils.waitForVisibility(userDetailsPage.notification, 10);
        assertTestCase.assertTrue(userDetailsPage.notification.isDisplayed(), "User Details Page  - Notification is displayed");
        BrowserUtils.waitForInvisibility(userDetailsPage.notification, 10);
        assertTestCase.assertEquals(userDetailsPage.userStatus.getText(),"Pending user action", "User Details Page  - User status is Suspended");
        assertTestCase.assertTrue(userDetailsPage.suspendButton.isDisplayed(), "User Details Page  - Suspend button is displayed");
        assertTestCase.assertTrue(userDetailsPage.suspendButton.isEnabled(), "User Details Page  - Suspend button is enabled for pending user action");
        assertTestCase.assertTrue(userDetailsPage.resendActivationEmailButton.isDisplayed(), "User Details Page  - Resend Activation Email button is displayed");
        assertTestCase.assertTrue(userDetailsPage.deleteButton.isDisplayed(), "User Details Page  - Delete button is displayed");

        //Delete user and verify user status
        userDetailsPage.deleteUser();
        BrowserUtils.waitForVisibility(userDetailsPage.notification, 10);
        assertTestCase.assertTrue(userDetailsPage.notification.isDisplayed(), "User Details Page  - Notification is displayed");
        BrowserUtils.waitForInvisibility(userDetailsPage.notification, 10);
    }
}
