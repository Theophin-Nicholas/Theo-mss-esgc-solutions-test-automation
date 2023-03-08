package com.esgc.Tests;

import com.esgc.APIModels.EMCAPIController;
import com.esgc.Pages.*;
import com.esgc.TestBases.EMCUITestBase;
import com.esgc.Utilities.*;
import com.github.javafaker.Faker;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class UsersPageTests extends EMCUITestBase {
    String userName = "Efrain June2022";
    String activeUser = "Ferhat Test";
    Faker faker = new Faker();
    EMCUsersPage usersPage = new EMCUsersPage();

    public void navigateToUser(String userName) {
        EMCMainPage mainPage = new EMCMainPage();
        System.out.println("Navigating to Users page");
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
        if (!userName.isEmpty()) {
            System.out.println("Searching for user: " + userName);
            usersPage.selectUser(userName);
            EMCUserDetailsPage userDetailsPage = new EMCUserDetailsPage();
            wait(userDetailsPage.firstNameInput, 10);
        }
    }

    @Test(groups = {EMC, UI, SMOKE, REGRESSION, PROD})
    @Xray(test = {3033})
    public void verifyAccountsPageTest() {
        navigateToUser("");
        wait(usersPage.names, 10);
        assertTestCase.assertTrue(usersPage.names.size() > 0, "Users Page - Users are displayed");
    }

    @Test(groups = {EMC, UI, REGRESSION})
    @Xray(test = {7625, 7626})
    public void verifyAdminUserSuspendUnsuspectedUserTest() {
        navigateToUser(activeUser);
        EMCUserDetailsPage userDetailsPage = new EMCUserDetailsPage();
        BrowserUtils.waitForVisibility(userDetailsPage.detailsTab, 10);
        if (userDetailsPage.userStatus.getText().equals("Suspended")) {
            System.out.println("User is suspended");
            userDetailsPage.clickUnsuspendUserButton();
            BrowserUtils.wait(5);
        }
        assertTestCase.assertTrue(userDetailsPage.userStatus.isDisplayed(), "User Details Page  - User status is displayed");
        assertTestCase.assertEquals(userDetailsPage.userStatus.getText(), "Active", "User Details Page  - User status is Active");
        assertTestCase.assertTrue(userDetailsPage.suspendButton.isDisplayed(), "User Details Page  - Suspend button is displayed");
        assertTestCase.assertTrue(userDetailsPage.resetPasswordButton.isDisplayed(), "User Details Page  - Reset Password button is displayed");
        assertTestCase.assertTrue(userDetailsPage.deleteButton.isDisplayed(), "User Details Page  - Delete button is displayed");

        //Suspend user and verify user status
        userDetailsPage.clickOnSuspendButton();
        BrowserUtils.waitForVisibility(userDetailsPage.notification, 10);
        assertTestCase.assertTrue(userDetailsPage.notification.isDisplayed(), "User Details Page  - Notification is displayed");
        BrowserUtils.waitForInvisibility(userDetailsPage.notification, 10);
        assertTestCase.assertEquals(userDetailsPage.userStatus.getText(), "Suspended", "User Details Page  - User status is Suspended");
        assertTestCase.assertTrue(userDetailsPage.unsuspendButton.isDisplayed(), "User Details Page  - Unsuspend button is displayed");
        //assertTestCase.assertTrue(userDetailsPage.resetPasswordButton.isDisplayed(), "User Details Page  - Reset Password button is displayed");
        assertTestCase.assertTrue(userDetailsPage.deleteButton.isDisplayed(), "User Details Page  - Delete button is displayed");

        //Unsuspend user and verify user status
        userDetailsPage.clickOnUnsuspendButton();
        BrowserUtils.waitForVisibility(userDetailsPage.notification, 10);
        assertTestCase.assertTrue(userDetailsPage.notification.isDisplayed(), "User Details Page  - Notification is displayed");
        BrowserUtils.waitForInvisibility(userDetailsPage.notification, 10);
        assertTestCase.assertEquals(userDetailsPage.userStatus.getText(), "Active", "User Details Page  - User status is Active");
        assertTestCase.assertTrue(userDetailsPage.suspendButton.isDisplayed(), "User Details Page  - Suspend button is displayed");
        assertTestCase.assertTrue(userDetailsPage.resetPasswordButton.isDisplayed(), "User Details Page  - Reset Password button is displayed");
        assertTestCase.assertTrue(userDetailsPage.deleteButton.isDisplayed(), "User Details Page  - Delete button is displayed");
    }

    @Test(groups = {EMC, UI, REGRESSION})
    @Xray(test = {7627, 7642})
    public void verifyAdminUserActivateStagedUserTest() {
        AccountsPageTests accountsPageTests = new AccountsPageTests();
        accountsPageTests.navigateToAccountsPage(accountsPageTests.accountName, "users");
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
        assertTestCase.assertEquals(userDetailsPage.userStatus.getText(), "Staged", "User Details Page  - User status is Staged");
        assertTestCase.assertTrue(userDetailsPage.activateButton.isDisplayed(), "User Details Page  - Reset Password button is displayed");
        assertTestCase.assertTrue(userDetailsPage.deleteButton.isDisplayed(), "User Details Page  - Delete button is displayed");

        //Suspend user and verify user status
        userDetailsPage.clickOnActivateButton();
        BrowserUtils.waitForVisibility(userDetailsPage.notification, 10);
        assertTestCase.assertTrue(userDetailsPage.notification.isDisplayed(), "User Details Page  - Notification is displayed");
        //BrowserUtils.waitForInvisibility(userDetailsPage.notification, 10);
        assertTestCase.assertEquals(userDetailsPage.userStatus.getText(), "Pending user action", "User Details Page  - User status is Suspended");
        assertTestCase.assertTrue(userDetailsPage.suspendButton.isDisplayed(), "User Details Page  - Suspend button is displayed");
        assertTestCase.assertTrue(userDetailsPage.suspendButton.isEnabled(), "User Details Page  - Suspend button is enabled for pending user action");
        assertTestCase.assertTrue(userDetailsPage.resendActivationEmailButton.isDisplayed(), "User Details Page  - Resend Activation Email button is displayed");
        assertTestCase.assertTrue(userDetailsPage.deleteButton.isDisplayed(), "User Details Page  - Delete button is displayed");

        //Click Resend Activation Email button
        userDetailsPage.clickOnResendActivationEmailButton();
        BrowserUtils.waitForVisibility(userDetailsPage.notification, 10);
        assertTestCase.assertTrue(userDetailsPage.notification.isDisplayed(), "User Details Page  - Notification is displayed");
        BrowserUtils.waitForInvisibility(userDetailsPage.notification, 10);
        assertTestCase.assertEquals(userDetailsPage.userStatus.getText(), "Pending user action", "User Details Page  - User status is Suspended");
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

    @Test(groups = {EMC, UI, REGRESSION}, description = "UI | EMC | Roles | Verify Role/Group user Information is Available for Investor User")
    @Xray(test = {5211})
    public void verifyRoleGroupUserInformationAvailableForInvestorUserTest() {
        navigateToUser(activeUser);
        EMCUserDetailsPage detailsPage = new EMCUserDetailsPage();
        detailsPage.clickOnApplicationRolesTab();
        detailsPage.deleteAllRoles();
        detailsPage.assignApplicationRoles("MESG Platform", "Investor");
        Driver.closeDriver();
        Driver.getDriver().get(Environment.EMC_URL);
        BrowserUtils.waitForPageToLoad(10);
        LoginPageEMC loginPageEMC = new LoginPageEMC();
        loginPageEMC.loginEMCWithParams("ferhat.demir-non-empl@moodys.com", "Apple@2023??");
        EMCMainPage mainPage = new EMCMainPage();
        BrowserUtils.waitForVisibility(mainPage.EMCTitle, 10);
        String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.claims.groups";
        String result = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
        System.out.println("result = " + result);
        String environment = ConfigurationReader.getProperty("environment");
        System.out.println("environment = " + environment);
        if (environment.equals("qa"))
            assertTestCase.assertTrue(result.contains("mesg-platform-investor-qa"), "Investor Role is available for QA Environment");
        else if (environment.equals("prod"))
            assertTestCase.assertTrue(result.contains("mesg-platform-investor-dev"), "Investor Role is available for PROD Environment");
        else if (environment.equals("uat"))
            assertTestCase.assertTrue(result.contains("mesg-platform-investor-uat"), "Investor Role is available for UAT Environment");
        else if (environment.equals("qa2"))
            assertTestCase.assertTrue(result.contains("mesg-platform-investor-qatwo"), "Investor Role is available for QA2 Environment");
        Driver.closeDriver();
        Driver.getDriver().get(Environment.EMC_URL);
        BrowserUtils.waitForPageToLoad(10);
        loginPageEMC = new LoginPageEMC();
        loginPageEMC.loginWithInternalUser();
        mainPage = new EMCMainPage();
        mainPage.goToUsersPage();
    }

    @Test(groups = {EMC, UI, REGRESSION}, description = "UI | EMC | Roles | Verify Role/Group user Information is Available for Issuer User")
    @Xray(test = {5212})
    public void verifyRoleGroupUserInformationAvailableForIssuerUserTest() {
        navigateToUser(activeUser);
        EMCUserDetailsPage detailsPage = new EMCUserDetailsPage();

        detailsPage.clickOnApplicationRolesTab();
        detailsPage.deleteAllRoles();
        detailsPage.assignApplicationRoles("MESG Platform", "Issuer");
        Driver.closeDriver();
        Driver.getDriver().get(Environment.EMC_URL);
        BrowserUtils.waitForPageToLoad(10);
        LoginPageEMC loginPageEMC = new LoginPageEMC();
        loginPageEMC.loginEMCWithParams("ferhat.demir-non-empl@moodys.com", "Apple@2023??");
        EMCMainPage mainPage = new EMCMainPage();
        BrowserUtils.waitForVisibility(mainPage.EMCTitle, 10);
        String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.claims.groups";
        String result = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
        System.out.println("result = " + result);
        String environment = ConfigurationReader.getProperty("environment");
        System.out.println("environment = " + environment);
        assertTestCase.assertTrue(result.contains("mesg-platform-investor"), "Investor Role is available for QA Environment");
        Driver.closeDriver();
        Driver.getDriver().get(Environment.EMC_URL);
        BrowserUtils.waitForPageToLoad(10);
        loginPageEMC = new LoginPageEMC();
        loginPageEMC.loginWithInternalUser();
        mainPage = new EMCMainPage();
        mainPage.goToUsersPage();
    }

    @Test(groups = {EMC, UI, REGRESSION}, description = "UI | EMC | Users | Validate Users Page Options Menu - Export Users, Sync Users")
    @Xray(test = {10541, 10542, 10543})
    public void verifyAccountsPageOptionsMenuTest() {
        navigateToUser("");
        wait(usersPage.userNames, 10);
        assertTestCase.assertTrue(usersPage.isOptionsAvailable("Export all"), "Export Users option is available for Admin");
        assertTestCase.assertTrue(usersPage.isOptionsAvailable("Sync users"), "Sync Users option is available for Admin");
        usersPage.exportUsers();
        assertTestCase.assertTrue(usersPage.verifyUsersExportFileDownloaded(), "Users Exported");
        System.out.println("Users Exported");
        assertTestCase.assertTrue(usersPage.verifyExportColumns(), "Exported columns are correct");
        System.out.println("Exported columns are correct");
        assertTestCase.assertTrue(usersPage.verifyExportedNumberOfUsers(), "Exported number of users are correct");
        System.out.println("Exported number of users are correct");
    }

    @Test(groups = {EMC, UI, REGRESSION, API}, description = "UI | EMC | User details | Verify User details have the Account information")
    @Xray(test = {12229, 12280})
    public void verifyUserDetailsAccountInformationTest() {
        navigateToUser(activeUser);
        EMCUserDetailsPage detailsPage = new EMCUserDetailsPage();
        assertTestCase.assertTrue(detailsPage.verifyUserDetails(), "User details are verified");
        BrowserUtils.waitAndClick(detailsPage.accountInfoButton, 5);
        EMCAccountDetailsPage accountDetailsPage = new EMCAccountDetailsPage();
        assertTestCase.assertTrue(accountDetailsPage.verifyAccountDetails(), "Account details are verified");
        try{
            loginAsViewer();
            navigateToUser(activeUser);
            detailsPage = new EMCUserDetailsPage();
            assertTestCase.assertTrue(detailsPage.verifyUserDetails(), "User details are verified");
            BrowserUtils.waitAndClick(detailsPage.accountInfoButton, 5);
            accountDetailsPage = new EMCAccountDetailsPage();
            assertTestCase.assertTrue(accountDetailsPage.verifyAccountDetails(), "Account details are verified");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //close the browser and login with admin role user
            Driver.quit();
            Driver.getDriver().get(Environment.EMC_URL);
            BrowserUtils.waitForPageToLoad(10);
            LoginPageEMC loginPageEMC = new LoginPageEMC();
            loginPageEMC.loginWithInternalUser();
            navigateToUser(activeUser);
        }
    }
}
