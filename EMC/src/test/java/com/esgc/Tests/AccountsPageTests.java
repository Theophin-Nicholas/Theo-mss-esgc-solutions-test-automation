package com.esgc.Tests;

import com.esgc.Pages.*;
import com.esgc.TestBases.EMCUITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Xray;
import com.github.javafaker.Faker;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;


public class AccountsPageTests extends EMCUITestBase {
    Faker faker = new Faker();
    EMCAccountsPage accountsPage = new EMCAccountsPage();

    String accountName = "INTERNAL QATest - PROD123";
    String applicationName = "TestQA";
    String activeUserName = "Ferhat Test";

    String fname = "Test";
    String lname = "user";
    String email = "testuser@mail.com";

    @Test(groups = {"EMC", "ui", "regression"})//no smoke
    @Xray(test = {3377, 3476, 4174, 4222, 3374, 4175})
    public void accountCreationTests() {
        EMCMainPage homePage = new EMCMainPage();

        //Select Account in the left menu
        homePage.openSidePanel();
        homePage.clickAccountsButton();

        //User is able to see New Account Page
        EMCAccountsPage accountsPage = new EMCAccountsPage();
        assertTestCase.assertEquals(accountsPage.pageTitle.getText(), "Accounts", "Accounts page is displayed");

        //Click Create Account button
        accountsPage.createAccountButton.click();
        EMCCreateAccountPage createAccountPage = new EMCCreateAccountPage();
        assertTestCase.assertEquals(createAccountPage.pageTitle.getText(), "Create a new Account",
                "Create Account page is displayed");

        //Click Cancel button
        createAccountPage.cancelButton.click();

        //User should be redirected to Account View page
        assertTestCase.assertEquals(accountsPage.pageTitle.getText(), "Accounts", "Accounts page is displayed");

        //Click on 'Create Account' and enter all the required information to create the new Account
        accountsPage.createAccountButton.click();

        //Todays date and time in the format of dd/MM/yyyy hh:mm:ss
        String now = "QATest" + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd-hhmmss"));
        createAccountPage.createAccount(now, true);
        System.out.println(createAccountPage.accountCreatedMessage.getText());
        assertTestCase.assertEquals(createAccountPage.accountCreatedMessage.getText(), "Account created successfully",
                "Account created successfully message is displayed");

        //Go back to the Accounts section and search for the Account created in the previous step
        accountsPage.search(now);
        assertTestCase.assertEquals(accountsPage.findAccount(now), true,
                "Account is displayed in the list");

        //Check that Fullfillment User will be able to view An Account By Clicking on its Name from Accounts View Screen
        String currentAccountName = accountsPage.accountNames.get(0).getText();
        String accountStatus = accountsPage.accountStatuses.get(0).getText();
        String creationDate = accountsPage.accountCreatedDates.get(0).getText();

        accountsPage.clickOnAccount(currentAccountName);
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        assertTestCase.assertEquals(detailsPage.pageTitle.getText(), currentAccountName, "Account Details page is displayed");
        assertTestCase.assertEquals(detailsPage.accountNameInput.getAttribute("value"), currentAccountName, "Account Name is displayed");
        assertTestCase.assertEquals(detailsPage.statusLabel.getText(), accountStatus, "Account Status is displayed");
        assertTestCase.assertTrue(detailsPage.creationInfo.getText().endsWith(creationDate), "Account creation info is verified");
        assertTestCase.assertTrue(detailsPage.editButton.isDisplayed(), "Edit button is displayed");
        detailsPage.editButton.click();
        assertTestCase.assertTrue(detailsPage.cancelButton.isDisplayed(), "Edit button works");
        detailsPage.cancelButton.click();
        //Select Applications Tab
        detailsPage.clickOnApplicationsTab();
        assertTestCase.assertTrue(detailsPage.applicationsTabTitle.isDisplayed(), "Applications Tab is displayed");

        //Click Assign Applications button on the top right of the section.
        detailsPage.assignApplicationsButton.click();
        BrowserUtils.waitForVisibility(detailsPage.assignApplicationsModalTitle, 5);
        assertTestCase.assertTrue(detailsPage.assignApplicationsModalTitle.isDisplayed(), "Assign Applications popup is displayed");
        BrowserUtils.waitForClickablility(detailsPage.doneButton, 5).click();
        //Click more than one Assign button from the list of available applications
        detailsPage.assignApplication(applicationName);
        BrowserUtils.waitForVisibility(detailsPage.applicationAddedMessage, 5);
        assertTestCase.assertTrue(detailsPage.applicationAddedMessage.isDisplayed(),
                "Application is added to the list");
        detailsPage.assignApplication(applicationName + "2");
        BrowserUtils.waitForVisibility(detailsPage.applicationAddedMessage, 5);
        assertTestCase.assertTrue(detailsPage.applicationAddedMessage.isDisplayed(),
                "Application is added to the list");

        //Assign Applications modal is closed and all the applications selected are display on the list
        assertTestCase.assertTrue(detailsPage.verifyApplication(applicationName), "Application is displayed in the list");
        assertTestCase.assertTrue(detailsPage.verifyApplication(applicationName + "2"), "Application is displayed in the list");

        detailsPage.productsTab.click();
        assertTestCase.assertEquals(detailsPage.productsTabTitle.getText(), "Products",
                "Products Tab is displayed");

        detailsPage.addAllProducts(applicationName);
        assertTestCase.assertTrue(detailsPage.verifyProduct(applicationName),
                "Product is displayed in the current list");
        detailsPage.deleteProduct(applicationName);
        assertTestCase.assertFalse(detailsPage.verifyProduct(applicationName),
                "Product is not displayed in the current list");
        //Back to Accounts Table
        detailsPage.backToAccountsButton.click();
        assertTestCase.assertEquals(accountsPage.pageTitle.getText(), "Accounts", "Accounts page is displayed");

        //Select a Different Account from the table and click over the Account Name
        accountsPage.goToAccount(accountName);

        //Select Applications Tab
        detailsPage.applicationsTab.click();
        assertTestCase.assertTrue(detailsPage.applicationsTabTitle.isDisplayed(), "Applications Tab is displayed");
        //Click Assign Applications button on the top right of the section.
        detailsPage.assignApplicationsButton.click();
        BrowserUtils.waitForVisibility(detailsPage.assignApplicationsModalTitle, 5);
        assertTestCase.assertTrue(detailsPage.assignApplicationsModalTitle.isDisplayed(), "Assign Applications popup is displayed");
        BrowserUtils.waitForClickablility(detailsPage.doneButton, 5).click();
        //Search for the same application selected for Account1 and assigned to the Account2
        detailsPage.deleteApplication(applicationName);
        detailsPage.deleteApplication(applicationName + 2);

        detailsPage.addApplication(applicationName);
        BrowserUtils.wait(1);
        assertTestCase.assertTrue(detailsPage.notification.isDisplayed(),
                "Application is added message is displayed");
        detailsPage.addApplication(applicationName + 2);
        BrowserUtils.wait(1);
        assertTestCase.assertTrue(detailsPage.notification.isDisplayed(),
                "Application is added message is displayed");
        //User is able to see that Application is assigned to account2 without any problem.
        assertTestCase.assertTrue(detailsPage.verifyApplication(applicationName), "Application is added to the list");
        assertTestCase.assertTrue(detailsPage.verifyApplication(applicationName + 2), "Application is added to the list");
    }

    @Test(groups = {"EMC", "ui","regression", "smoke", "prod"})
    @Xray(test = {4813, 2350})
    public void accountDetailsPageTest() {
        navigateToAccountsPage(accountName, "users");
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        assertTestCase.assertTrue(detailsPage.addUserButton.isDisplayed(), "Users Tab is displayed");

        //Click on Add User button
        detailsPage.addUserButton.click();
        assertTestCase.assertTrue(detailsPage.addUserPanelTitle.isDisplayed(), "Add User popup is displayed");
        assertTestCase.assertTrue(detailsPage.firstNameInput.isDisplayed(), "FirstName input is displayed");
        assertTestCase.assertTrue(detailsPage.lastNameInput.isDisplayed(), "LastName input is displayed");
        assertTestCase.assertTrue(detailsPage.userNameInput.isDisplayed(), "Username input is displayed");
        assertTestCase.assertTrue(detailsPage.emailInput.isDisplayed(), "Email input is displayed");
        BrowserUtils.scrollTo(detailsPage.sendActivationEmailCheckbox);
        assertTestCase.assertTrue(detailsPage.sendActivationEmailCheckbox.isEnabled(), "Activation checkbox is displayed");
        assertTestCase.assertTrue(!detailsPage.saveButton.isEnabled(), "Save button is not enabled");

        //Enter data for some of the required fields, do not fill all of them.
        detailsPage.firstNameInput.sendKeys("", Keys.TAB);
        assertTestCase.assertTrue(!detailsPage.saveButton.isEnabled(), "Save button is not enabled");
        assertTestCase.assertTrue(detailsPage.firstNameInputWarning.isDisplayed(), "FirstName input warning is displayed");
        detailsPage.firstNameInput.sendKeys("Test");

        detailsPage.lastNameInput.sendKeys("", Keys.TAB);
        assertTestCase.assertTrue(!detailsPage.saveButton.isEnabled(), "Save button is not enabled");
        assertTestCase.assertTrue(detailsPage.lastNameInputWarning.isDisplayed(), "LastName input warning is displayed");
        detailsPage.lastNameInput.sendKeys("Test");

        detailsPage.userNameInput.sendKeys("", Keys.TAB);
        assertTestCase.assertTrue(!detailsPage.saveButton.isEnabled(), "Save button is not enabled");
        assertTestCase.assertTrue(detailsPage.userNameInputWarning.isDisplayed(), "Username input warning is displayed");
        detailsPage.userNameInput.sendKeys("Test");
        assertTestCase.assertTrue(detailsPage.validEmailWarning.isDisplayed(), "Valid email warning is displayed");
        detailsPage.userNameInput.sendKeys("@mail.com", Keys.ENTER);

        assertTestCase.assertEquals(detailsPage.userNameInput.getAttribute("value"), detailsPage.emailInput.getAttribute("value"),
                "username input passed to email input");
        assertTestCase.assertTrue(detailsPage.sendActivationEmailCheckbox.isSelected(), "Send activation email checkbox is selected by default");
        assertTestCase.assertTrue(detailsPage.saveButton.isEnabled(), "Save button is enabled");
        detailsPage.cancelButton.click();
    }

    @Test(groups = {"EMC", "ui", "regression"})
    @Xray(test = {4806})
    public void createNewUserTest() {
        navigateToAccountsPage(accountName, "users");
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        assertTestCase.assertTrue(detailsPage.addUserButton.isDisplayed(), "Users Tab is displayed");

        //Click on Add User button
        detailsPage.clickOnAddUserButton();
        assertTestCase.assertTrue(detailsPage.addUserPanelTitle.isDisplayed(), "Add User popup is displayed");
        assertTestCase.assertTrue(detailsPage.firstNameInput.isDisplayed(), "FirstName input is displayed");
        assertTestCase.assertTrue(detailsPage.lastNameInput.isDisplayed(), "LastName input is displayed");
        assertTestCase.assertTrue(detailsPage.userNameInput.isDisplayed(), "Username input is displayed");
        assertTestCase.assertTrue(detailsPage.emailInput.isDisplayed(), "Email input is displayed");
        BrowserUtils.scrollTo(detailsPage.sendActivationEmailCheckbox);
        assertTestCase.assertTrue(detailsPage.sendActivationEmailCheckbox.isEnabled(), "Activation checkbox is displayed");

        //Enter all data required in the modal
        // Click Save button
        fname = "QATestUser";
        lname = faker.name().lastName();
        email = faker.internet().emailAddress();
        assertTestCase.assertTrue(detailsPage.createUser(fname, lname, email, true),
                "User is created successfully");
        detailsPage.searchUser(email);
        EMCAccountsEditUserPage editUserPage = new EMCAccountsEditUserPage();
        assertTestCase.assertTrue(editUserPage.deleteUser(), "User is deleted successfully");
    }

    @Test(enabled = true, groups = {"EMC", "ui", "regression", "smoke"})
    @Xray(test = {4818})
    public void createAndEditNewUserTest() {
        navigateToAccountsPage(accountName, "users");
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        assertTestCase.assertTrue(detailsPage.addUserButton.isDisplayed(), "Users Tab is displayed");

        //Click on Add User button
        detailsPage.clickOnAddUserButton();
        //Enter all data required in the modal
        // Click Save button
        fname = "QATest User";
        lname = faker.name().name();
        email = faker.internet().emailAddress();
        assertTestCase.assertTrue(
                detailsPage.createUser(fname, lname, email, false), "User is created successfully");
        System.out.println("User is created");
        //EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        detailsPage.clickOnUsersTab();
        detailsPage.searchUser(fname + " " + lname);
        EMCAccountsEditUserPage editUserPage = new EMCAccountsEditUserPage();
        assertTestCase.assertTrue(editUserPage.editPageTitle.isDisplayed(), "Edit User page is displayed");
        String newFirstName = "QANew User";
        String newLastName = faker.name().name();
        String newEmail = faker.internet().emailAddress();
        editUserPage.editUser(newFirstName, newLastName, newEmail);
        assertTestCase.assertTrue(detailsPage.verifyUser(newFirstName + " " + newLastName), "User is edited successfully");
        detailsPage.searchUser(newFirstName + " " + newLastName);
        Map<String, String> currentInfo = editUserPage.getCurrentInfo();
        assertTestCase.assertEquals(currentInfo.get("firstName"), newFirstName, "First Name is updated");
        assertTestCase.assertEquals(currentInfo.get("lastName"), newLastName, "Last Name is updated");
        assertTestCase.assertEquals(currentInfo.get("email"), newEmail, "Email is updated");
        editUserPage.deleteUser();

    }

    @Test(groups = {"EMC", "ui", "regression"})
    @Xray(test = {4839})
    public void verifyOktaUserDetailsTest() {

        navigateToAccountsPage("All entitlements", "users");

        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        assertTestCase.assertTrue(detailsPage.addUserButton.isDisplayed(), "Users Tab is displayed");
        detailsPage.searchUser("External User");
        EMCAccountsEditUserPage editUserPage = new EMCAccountsEditUserPage();
        assertTestCase.assertTrue(editUserPage.editPageTitle.isDisplayed(), "Edit User page is displayed");
        Map<String, String> currentInfo = editUserPage.getCurrentInfo();
        assertTestCase.assertFalse(currentInfo.get("firstName").isEmpty(), "First name is displayed");
        assertTestCase.assertFalse(currentInfo.get("lastName").isEmpty(), "Last name is displayed");
        assertTestCase.assertFalse(currentInfo.get("email").isEmpty(), "Email is displayed");
        assertTestCase.assertFalse(currentInfo.get("status").isEmpty(), "Status is displayed");
        assertTestCase.assertTrue(editUserPage.oktaUserMessage.isDisplayed(), "This user was provisioned by Okta message is displayed");
    }

    @Test(groups = {"EMC", "ui", "smoke", "regression", "prod"})
    @Xray(test = {4849})
    public void verifyEMCUserDetailsTest() {
        navigateToAccountsPage(accountName, "users");
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        assertTestCase.assertTrue(detailsPage.addUserButton.isDisplayed(), "Users Tab is displayed");
        detailsPage.searchUser("");//goes to first user
        EMCAccountsEditUserPage editUserPage = new EMCAccountsEditUserPage();
        assertTestCase.assertTrue(editUserPage.editPageTitle.isDisplayed(), "Edit User page is displayed");
        Map<String, String> currentInfo = editUserPage.getCurrentInfo();
        assertTestCase.assertFalse(currentInfo.get("firstName").isEmpty(), "First name is displayed");
        assertTestCase.assertFalse(currentInfo.get("lastName").isEmpty(), "Last name is displayed");
        assertTestCase.assertFalse(currentInfo.get("email").isEmpty(), "Email is displayed");
        assertTestCase.assertFalse(currentInfo.get("status").isEmpty(), "Status is displayed");
        assertTestCase.assertTrue(currentInfo.get("createdBy").split(" ").length >= 5, "Created by info is displayed");
        assertTestCase.assertTrue(currentInfo.get("modifiedBy").split(" ").length >= 5, "Modified by info is displayed");
    }

    @Test(groups = {"EMC", "ui", "smoke", "regression"})
    @Xray(test = {5044, 4809})
    public void verifyAllUsersSortedByNameTest() {
        navigateToAccountsPage(accountName, "users");
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        assertTestCase.assertTrue(detailsPage.addUserButton.isDisplayed(), "Users Tab is displayed");
        while (detailsPage.userNamesList.size() < 2) {
            detailsPage.createUser();
        }
        assertTestCase.assertTrue(detailsPage.userNamesList.size() >= 2, "All users are displayed");
        assertTestCase.assertTrue(detailsPage.isSortedByName(), "Users Table is displayed");
    }

    @Test(groups = {"EMC", "ui", "regression"})
    @Xray(test = {2351})
    public void verifyAllAccountsSortedByNameTest() {
        navigateToAccountsPage("", "users");
        List<String> accountNames = accountsPage.getAccountNames();
        //convert to lower case and sort alphabetically
        accountNames.sort(String::compareToIgnoreCase);
        assertTestCase.assertEquals(accountNames, accountsPage.getAccountNames(), "Accounts are sorted alphabetically");

    }

    @Test(groups = {"EMC", "ui", "smoke", "regression", "prod"})
    @Xray(test = {5492})
    public void verifySearchBoxOnUsersPageTest() {
        navigateToAccountsPage(accountName, "users");
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        assertTestCase.assertTrue(detailsPage.addUserButton.isDisplayed(), "Users Tab is displayed");
        assertTestCase.assertTrue(detailsPage.searchInput.isDisplayed(), "Search Box is displayed");

    }

    @Test(groups = {"EMC", "ui", "regression"})
    @Xray(test = {5517})
    public void verifySuspendUserTest() {
        navigateToAccountsPage("AutomationAccount", "users");
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        assertTestCase.assertTrue(detailsPage.addUserButton.isDisplayed(), "Users Tab is displayed");
        //erolvera.mx+333@gmail.com and Helloworld123 as Pass

        detailsPage.searchUser("Automation UserTest");
        EMCAccountsEditUserPage editUserPage = new EMCAccountsEditUserPage();
        //if user is already suspended, click on the unsuspend button
        if (editUserPage.status.getText().equals("Suspended")) editUserPage.unsuspendButton.click();
        //Suspend the user
        assertTestCase.assertTrue(editUserPage.suspendButton.isDisplayed(), "Suspend User Button is displayed");
        editUserPage.suspendButton.click();
        assertTestCase.assertTrue(editUserPage.unsuspendButton.isDisplayed(), "Unsuspend User Button is displayed");
        assertTestCase.assertEquals(editUserPage.status.getText(), "Suspended", "Suspended status Message is displayed");
        //open a new tab and go to mesg and login with suspended external user
        String currentTab = Driver.getDriver().getWindowHandle();
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("window.open('https://solutions-qa.mra-esg-nprd.aws.moodys.tld/','_blank');");
        Set<String> windows = Driver.getDriver().getWindowHandles();
        try {
            Driver.getDriver().switchTo().window(windows.toArray()[1].toString());
            LoginPageEMC loginPage = new LoginPageEMC();
            BrowserUtils.waitForPageToLoad(10);
            loginPage.clickOnLogout();
            loginPage.loginWithParams("erolvera.mx+333@gmail.com", "Helloworld123");
            assertTestCase.assertTrue(loginPage.UnauthorisedUserErrorMsg.isDisplayed(), "Login successfully failed for suspended user");
            System.out.println("Login successfully failed for suspended user");

            //un suspend the user
            Driver.getDriver().switchTo().window(currentTab);
            editUserPage.unsuspendButton.click();
            assertTestCase.assertTrue(editUserPage.suspendButton.isDisplayed(), "Suspend User Button is displayed");
            //open a new tab and go to mesg and login with active external user
            System.out.println("User unsuspened successfully");
            Driver.getDriver().switchTo().window(windows.toArray()[1].toString());
            BrowserUtils.waitForClickablility(loginPage.loginButton, 5).click();
            System.out.println("Login successful");
            DashboardPage dashboardPage = new DashboardPage();
            assertTestCase.assertTrue(dashboardPage.portfolioSelectionButton.isDisplayed(),
                    "Login sucessfull for account suspended and unsuspended");
            System.out.println("Login successful");

        } catch (Exception e) {
            System.out.println("Login failed for suspended user");
        }
        Driver.getDriver().close();
        Driver.getDriver().switchTo().window(currentTab);

    }

    @Test(groups = {"EMC", "ui", "smoke", "regression"})
    @Xray(test = {6169, 6170})
    public void verifyActivateUserTest() {
        navigateToAccountsPage(accountName, "users");
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        detailsPage.clickOnAddUserButton();

        //Enter all data required in the modal
        // Click Save button
        fname = "QATest User";
        lname = faker.name().lastName();
        email = faker.internet().emailAddress();
        assertTestCase.assertTrue(detailsPage.createUser(fname, lname, email, false),
                "User is created successfully with activation email sent");
        detailsPage.searchUser(email);
        EMCAccountsEditUserPage editUserPage = new EMCAccountsEditUserPage();
        System.out.println("fname = " + fname);
        System.out.println("act fname = " + editUserPage.firstNameInput.getAttribute("value"));
        assertTestCase.assertEquals(editUserPage.firstNameInput.getAttribute("value"), fname, "First name is verified");
        assertTestCase.assertEquals(editUserPage.lastNameInput.getAttribute("value"), lname, "Last name is verified");
        assertTestCase.assertFalse(editUserPage.suspendButton.isEnabled(), "Suspend button is verified");
        assertTestCase.assertTrue(editUserPage.activateButton.isDisplayed(), "Activate button is verified");
        assertTestCase.assertTrue(editUserPage.activateButton.isEnabled(), "Activate button is enabled");
        assertTestCase.assertEquals(editUserPage.status.getText(), "Staged", "Status is displayed");
        editUserPage.activateButton.click();
        BrowserUtils.wait(10);
        assertTestCase.assertTrue(editUserPage.suspendButton.isEnabled(), "Suspend button is verified");
        assertTestCase.assertEquals(editUserPage.status.getText(), "Pending user action", "Status message is verified");
        assertTestCase.assertTrue(editUserPage.resentActivationEmailButton.isEnabled(), "Resend Activation Email button is verified");
        editUserPage.deleteUser();
    }

    @Test(groups = {"EMC", "ui", "smoke", "regression", "prod"})
    @Xray(test = {6185})
    public void resetPasswordButtonTest() {
        navigateToAccountsPage(accountName, "users");
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        assertTestCase.assertTrue(detailsPage.addUserButton.isDisplayed(), "Users Tab is displayed");
        detailsPage.searchUser(activeUserName);

        EMCAccountsEditUserPage editUserPage = new EMCAccountsEditUserPage();
        assertTestCase.assertTrue(editUserPage.editPageTitle.isDisplayed(), "Edit User page is displayed");
        assertTestCase.assertFalse(editUserPage.firstNameInput.getAttribute("value").isEmpty(), "First name is displayed");
        assertTestCase.assertFalse(editUserPage.lastNameInput.getAttribute("value").isEmpty(), "Last name is displayed");
        assertTestCase.assertEquals(editUserPage.status.getText(), "Active", "Status is displayed");
        assertTestCase.assertTrue(editUserPage.suspendButton.isEnabled(), "Suspend button is displayed");
        assertTestCase.assertTrue(editUserPage.resetPasswordButton.isEnabled(), "Reset Password button is displayed");
    }

    @Test(groups = {"EMC", "ui", "regression"})
    @Xray(test = {6794, 6792, 6795, 6797})
    public void deleteMultipleUsersTest() {
        navigateToAccountsPage(accountName, "users");
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        assertTestCase.assertTrue(detailsPage.addUserButton.isDisplayed(), "Users Tab is displayed");
        detailsPage.deleteRandomUsers();
        for (int i = 0; i < 3; i++) {
            String email = faker.internet().emailAddress();
            detailsPage.clickOnAddUserButton();
            detailsPage.createUser("Aaaaaa", "Aaaaaa", email, false);
            BrowserUtils.waitForVisibility(detailsPage.notification, 10);
            BrowserUtils.waitForInvisibility(detailsPage.notification, 10);
        }
        String firstUser = detailsPage.userNamesList.get(0).getText();
        int initialUserCount = detailsPage.userNamesList.size();
        if (initialUserCount > 8) {
            System.out.println("User count is greater than 8. This test most probably will fail");
        }
        detailsPage.userCheckboxList.get(0).click();
        assertTestCase.assertTrue(detailsPage.deleteButton.isDisplayed(), "Delete button is verified");
        detailsPage.deleteButton.click();
        BrowserUtils.waitForVisibility(detailsPage.deleteConfirmationPopup, 5);
        assertTestCase.assertTrue(detailsPage.deleteConfirmationPopup.isDisplayed(), "Delete confirmation is displayed");
        assertTestCase.assertTrue(detailsPage.popupDeleteButton.isDisplayed(), "Delete button is verified");
        assertTestCase.assertTrue(detailsPage.popupCancelButton.isDisplayed(), "Cancel button is verified");
        detailsPage.popupDeleteButton.click();
        BrowserUtils.waitForVisibility(detailsPage.deleteMessage, 5);
        assertTestCase.assertTrue(detailsPage.deleteMessage.isDisplayed(), "User deleted message is displayed");
        BrowserUtils.waitForInvisibility(detailsPage.deleteMessage, 15);
        detailsPage.clickOnDetailsTab();
        BrowserUtils.wait(1);
        detailsPage.clickOnUsersTab();
        BrowserUtils.wait(3);
        detailsPage.userCheckboxList.get(0).click();
        assertTestCase.assertTrue(detailsPage.deleteButton.isDisplayed(), "Delete button is verified");
        detailsPage.userCheckboxList.get(1).click();
        detailsPage.deleteButton.click();
        BrowserUtils.waitForVisibility(detailsPage.deleteConfirmationPopup, 5);
        assertTestCase.assertTrue(detailsPage.deleteConfirmationPopup.isDisplayed(), "Delete confirmation is displayed");
        assertTestCase.assertTrue(detailsPage.popupDeleteButton.isDisplayed(), "Delete button is verified");
        assertTestCase.assertTrue(detailsPage.popupCancelButton.isDisplayed(), "Cancel button is verified");
        detailsPage.popupDeleteButton.click();
        BrowserUtils.waitForVisibility(detailsPage.deleteMessage, 5);
        assertTestCase.assertTrue(detailsPage.deleteMessage.isDisplayed(), "User deleted message is displayed");
        BrowserUtils.waitForInvisibility(detailsPage.deleteMessage, 15);
        detailsPage.clickOnDetailsTab();
        BrowserUtils.wait(1);
        detailsPage.clickOnUsersTab();
        BrowserUtils.wait(3);
        assertTestCase.assertFalse(detailsPage.verifyUser(firstUser), "User is not present");
    }

    @Test(groups = {"EMC", "ui", "regression"})
    @Xray(test = {6801, 6793, 6796})
    public void removeOnlyEMCUsersTest() {
        navigateToAccountsPage(accountName, "users");
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        assertTestCase.assertTrue(detailsPage.addUserButton.isDisplayed(), "Users Tab is displayed");
        while (detailsPage.userNamesList.size() < 2) {
            detailsPage.createUser();
        }
        String firstUser = detailsPage.userNamesList.get(0).getText();
        detailsPage.userNamesList.get(0).click();
        EMCAccountsEditUserPage editUserPage = new EMCAccountsEditUserPage();
        assertTestCase.assertTrue(editUserPage.deleteButton.isDisplayed(), "Delete button is verified");
        editUserPage.deleteButton.click();
        BrowserUtils.waitForVisibility(editUserPage.deleteConfirmationPopup, 5);
        assertTestCase.assertTrue(editUserPage.deleteConfirmationPopup.isDisplayed(), "Delete confirmation is displayed");
        assertTestCase.assertTrue(editUserPage.popupDeleteButton.isDisplayed(), "Delete button is verified");
        assertTestCase.assertTrue(editUserPage.popupCancelButton.isDisplayed(), "Cancel button is verified");
        editUserPage.popupCancelButton.click();
        editUserPage.backToUsersButton.click();
        detailsPage.usersTab.click();
        assertTestCase.assertTrue(detailsPage.addUserButton.isDisplayed(), "Users Tab is displayed");
        assertTestCase.assertTrue(detailsPage.verifyUser(firstUser), "User is present");
        detailsPage.clickOnBackToAccountsButton();
        //Search for the account where a New user will be created
        EMCAccountsPage accountsPage = new EMCAccountsPage();
        accountsPage.search("All entitlements");
        assertTestCase.assertEquals(accountsPage.accountNames.size() > 0, true, "Account is displayed");
        assertTestCase.assertTrue(accountsPage.accountNames.get(0).getText().toLowerCase().contains("all entitlements"),
                "Account is displayed");

        //On Account Details view, select USERS tab
        accountsPage.accountNames.get(0).click();
        detailsPage.usersTab.click();
        assertTestCase.assertTrue(detailsPage.addUserButton.isDisplayed(), "Users Tab is displayed");
        detailsPage.searchUser("Test User");
        assertTestCase.assertTrue(editUserPage.oktaUserMessage.isDisplayed(), "Okta User is displayed");
        assertTestCase.assertTrue(editUserPage.deleteButton.isDisplayed(), "Delete button for Okta User is displayed");
        editUserPage.deleteButton.click();
        BrowserUtils.waitForVisibility(editUserPage.deleteConfirmationPopup, 5);
        assertTestCase.assertTrue(editUserPage.deleteConfirmationPopup.isDisplayed(), "Delete confirmation is displayed");
        editUserPage.popupCancelButton.click();
        //todo: Click Delete button - User is deleted in EMC
        //todo: Search for the user on OKTA - User is present in Okta but not on EMC
    }

    @Test(enabled = true, groups = {"EMC", "ui", "regression"})
    @Xray(test = {5519})
    public void activateUserTest() {
        navigateToAccountsPage("AutomationAccount", "users");
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        assertTestCase.assertTrue(detailsPage.addUserButton.isDisplayed(), "Users Tab is displayed");
        //erolvera.mx+333@gmail.com and Helloworld123 as Pass
        detailsPage.searchUser("Automation UserTest");
        EMCAccountsEditUserPage editUserPage = new EMCAccountsEditUserPage();
        //if user is already suspended, click on the unsuspend button
        if (editUserPage.status.getText().equals("Suspended")) editUserPage.unsuspendButton.click();
        //Suspend the user
        assertTestCase.assertTrue(editUserPage.suspendButton.isDisplayed(), "Suspend User Button is displayed");
        editUserPage.suspendButton.click();
        assertTestCase.assertTrue(editUserPage.unsuspendButton.isDisplayed(), "Unsuspend User Button is displayed");
        assertTestCase.assertEquals(editUserPage.status.getText(), "Suspended", "Suspended status Message is displayed");
        //open a new tab and go to mesg and login with suspended external user
        String currentTab = Driver.getDriver().getWindowHandle();
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("window.open('https://solutions-qa.mra-esg-nprd.aws.moodys.tld/','_blank');");
        Set<String> windows = Driver.getDriver().getWindowHandles();
        Driver.getDriver().switchTo().window(windows.toArray()[1].toString());
        LoginPageEMC loginPage = new LoginPageEMC();
        loginPage.clickOnLogout();
        loginPage.loginWithParams("erolvera.mx+333@gmail.com", "Helloworld123");
        assertTestCase.assertTrue(loginPage.UnauthorisedUserErrorMsg.isDisplayed(), "Login successfully failed for suspended user");


        //un suspend the user
        Driver.getDriver().switchTo().window(currentTab);
        editUserPage.unsuspendButton.click();
        assertTestCase.assertTrue(editUserPage.suspendButton.isDisplayed(), "Suspend User Button is displayed");
        //open a new tab and go to mesg and login with active external user

        Driver.getDriver().switchTo().window(windows.toArray()[1].toString());
        BrowserUtils.waitForPageToLoad(10);
        //BrowserUtils.waitForClickablility(loginPage.termsAndConditionsCheckBox, 5).click();
        Driver.getDriver().navigate().refresh();
        //BrowserUtils.wait(60);
        System.out.println("Login attempted");
        loginPage.loginWithParams("erolvera.mx+333@gmail.com", "Helloworld123");
        //loginPage.termsAndConditionsLabel.click();
        System.out.println("loginPage.termsAndConditionsLabel = " + loginPage.termsAndConditionsLabel);
        //loginPage.termsAndConditionsCheckBox.click();
        loginPage.loginButton.click();
        System.out.println("Login successful");
        DashboardPage dashboardPage = new DashboardPage();

        assertTestCase.assertTrue(dashboardPage.portfolioSelectionButton.isDisplayed(), "Login sucessfull for account suspended and unsuspended");
        Driver.getDriver().close();
        Driver.getDriver().switchTo().window(currentTab);
        //todo: Check the mailbox for the activated user
        //todo: Update the user password and verify the status in EMC
        //todo: Check the user login to MESG with the new password
    }

    @Test(groups = {"EMC", "ui", "smoke"})
    @Xray(test = {5179})
    public void verifyUserAssignApplicationRolesTest() {
        navigateToAccountsPage(accountName, "applications");
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        assertTestCase.assertTrue(detailsPage.assignApplicationsButton.isDisplayed(), "Users Tab is displayed");
        while (detailsPage.verifyApplication(applicationName)) {
            detailsPage.deleteApplication(applicationName);
        }
        assertTestCase.assertTrue(detailsPage.assignApplication(applicationName), "Application is assigned for the user");
        BrowserUtils.waitForVisibility(detailsPage.applicationAddedMessage, 5);
        assertTestCase.assertTrue(detailsPage.applicationAddedMessage.isDisplayed(), "Application is assigned for the user");
        while (detailsPage.verifyApplication(applicationName)) {
            detailsPage.deleteApplication(applicationName);
        }
    }

    @Test(groups = {"EMC", "ui", "smoke"})
    @Xray(test = {5185, 5769})
    public void verifyUserRemoveApplicationRolesTest() {
        navigateToAccountsPage(accountName, "applications");
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        assertTestCase.assertTrue(detailsPage.assignApplicationsButton.isDisplayed(), "Applications Tab is displayed");
        if (detailsPage.verifyApplication(applicationName)) {
            System.out.println("Application is already assigned for the user");
        } else {
            System.out.println("Application is not assigned for the user. Adding application");
            detailsPage.assignApplication(applicationName);
        }
        BrowserUtils.waitForClickablility(detailsPage.usersTab, 5).click();
        assertTestCase.assertTrue(detailsPage.addUserButton.isDisplayed(), "Users Tab is displayed");
        BrowserUtils.waitForClickablility(detailsPage.userNamesList.get(0), 5).click();
        EMCUserDetailsPage userDetailsPage = new EMCUserDetailsPage();
        BrowserUtils.waitForClickablility(userDetailsPage.applicationRolesTab, 5).click();
        assertTestCase.assertTrue(userDetailsPage.assignApplicationRolesButton.isDisplayed(), "Application Roles tab is verified");
        System.out.println("user name is " + userDetailsPage.userDetailsPageTitle.getText());
        //Select Application Role tab from User details
        if (userDetailsPage.verifyApplicationRole(applicationName)) {
            System.out.println(applicationName + " is already assigned to the user");
        } else {
            System.out.println(applicationName + " is not assigned to the user");
            userDetailsPage.addApplicationRoles(applicationName);
        }
        assertTestCase.assertTrue(userDetailsPage.verifyApplicationRole(applicationName), "Application Role is assigned to the user");
        //Click Delete button (Trash Icon)
        assertTestCase.assertTrue(userDetailsPage.deleteApplicationRole(applicationName), "User is able to delete the application role");
        assertTestCase.assertFalse(userDetailsPage.verifyApplicationRole(applicationName), "Application Role is deleted from the user");
        userDetailsPage.addApplicationRoles(applicationName);
        assertTestCase.assertTrue(userDetailsPage.verifyApplicationRole(applicationName), "Application Role is assigned to the user");

    }

    @Test(groups = {"EMC", "ui", "smoke"})
    @Xray(test = {3993})
    public void verifyUserRemovesApplicationTest() {
        navigateToAccountsPage(accountName, "applications");

        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        assertTestCase.assertTrue(detailsPage.assignApplicationsButton.isDisplayed(), "Applications Tab is displayed");
        if (!detailsPage.verifyApplication(applicationName)) {
            System.out.println("Application is not assigned for the user");
            detailsPage.assignApplication(applicationName);
            BrowserUtils.wait(5);
        }
        assertTestCase.assertTrue(detailsPage.deleteApplication(applicationName), "Application is deleted");
        System.out.println("Application Deleted Message = " + BrowserUtils.waitForVisibility(detailsPage.notification, 5).getText());
        assertTestCase.assertTrue(detailsPage.applicationAddedMessage.isDisplayed(), "Application deleted message is displayed");
        assertTestCase.assertEquals(detailsPage.applicationAddedMessage.getText(), "Application " + applicationName + " removed successfully");
    }

    @Test(groups = {"EMC", "ui", "regression", "smoke"})
    @Xray(test = {3991})
    public void verifyUserAssignOneApplicationToMultipleAccounts() {
        navigateToAccountsPage(accountName, "applications");

        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        assertTestCase.assertTrue(detailsPage.assignApplicationsButton.isDisplayed(), "Applications Tab is displayed");
        assertTestCase.assertTrue(detailsPage.addTestApplications(applicationName), "MESG Platform Applications is assigned");
        //assertTestCase.assertTrue(detailsPage.addTestApplications("TestQA"), "TestQA Application is assigned");

        //We can choose a different test account
        String accountName2 = "Test Account";
        detailsPage.clickOnBackToAccountsButton();
        try {
            detailsPage.clickOnBackToAccountsButton();
        } catch (Exception e) {
            System.out.println("No need to click on back to accounts button twice");
        }
        EMCAccountsPage accountsPage = new EMCAccountsPage();
        accountsPage.search(accountName2);
        accountsPage.clickOnFirstAccount();
        detailsPage.clickOnApplicationsTab();

        assertTestCase.assertTrue(detailsPage.assignApplicationsButton.isDisplayed(), "Applications Tab is displayed");
        assertTestCase.assertTrue(detailsPage.addTestApplications(applicationName), "Test Applications is assigned");
        //assertTestCase.assertTrue(detailsPage.addTestApplications("TestQA"), "TestQA Application is assigned");
    }

    @Test(groups = {"EMC", "ui", "regression"})
    @Xray(test = {5547})
    public void verifyCreatingExternalAccountWithoutBeingActivatedTest() {
        navigateToAccountsPage(accountName, "users");
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        assertTestCase.assertTrue(detailsPage.addUserButton.isDisplayed(), "Users Tab is displayed");

        //Click on Add User button
        detailsPage.clickOnAddUserButton();
        //Verify New User Page is displayed
        BrowserUtils.waitForVisibility(detailsPage.addUserPanelTitle, 15);
        assertTestCase.assertTrue(detailsPage.addUserPanelTitle.isDisplayed(), "Add User popup is displayed");
        assertTestCase.assertTrue(detailsPage.firstNameInput.isDisplayed(), "FirstName input is displayed");
        assertTestCase.assertTrue(detailsPage.lastNameInput.isDisplayed(), "LastName input is displayed");
        assertTestCase.assertTrue(detailsPage.userNameInput.isDisplayed(), "Username input is displayed");
        assertTestCase.assertTrue(detailsPage.emailInput.isDisplayed(), "Email input is displayed");
        BrowserUtils.scrollTo(detailsPage.sendActivationEmailCheckbox);
        assertTestCase.assertTrue(detailsPage.sendActivationEmailCheckbox.isEnabled(), "Activation checkbox is displayed");
        assertTestCase.assertTrue(!detailsPage.saveButton.isEnabled(), "Save button is not enabled");

        //Enter data for some of the required fields, do not fill all of them.
        String firstName = "QATest " + faker.name().firstName();
        String lastName = faker.name().lastName();
        String userName = "qatest_" + faker.internet().emailAddress();
        System.out.println("Fullname = " + firstName + " " + lastName);
        System.out.println("Username = " + userName);

        //Enter required information and deselect Send Activation Email checkbox
        detailsPage.firstNameInput.sendKeys(firstName);
        detailsPage.lastNameInput.sendKeys(lastName);
        detailsPage.userNameInput.sendKeys(userName, Keys.ENTER);
        detailsPage.sendActivationEmailCheckbox.click();
        //email automatically copied from username field
        assertTestCase.assertEquals(userName, detailsPage.emailInput.getAttribute("value"), "username input passed to email input");
        assertTestCase.assertFalse(detailsPage.sendActivationEmailCheckbox.isSelected(), "Send activation email checkbox is de-selected");
        assertTestCase.assertTrue(detailsPage.saveButton.isEnabled(), "Save button is enabled");
        BrowserUtils.waitForClickablility(detailsPage.saveButton, 5).click();
        BrowserUtils.waitForVisibility(detailsPage.applicationAddedMessage, 5);
        assertTestCase.assertTrue(detailsPage.applicationAddedMessage.isDisplayed(), "User added message is displayed");
        detailsPage.clickOnBackToUsersButton();
        BrowserUtils.waitForVisibility(detailsPage.userNamesList.get(0), 15);
        assertTestCase.assertTrue(detailsPage.verifyUser(firstName + " " + lastName), "User verified");
        assertTestCase.assertTrue(detailsPage.deleteUser(firstName + " " + lastName), "User deleted");
        BrowserUtils.waitForVisibility(detailsPage.notification, 5);
        assertTestCase.assertTrue(detailsPage.notification.isDisplayed(), "User deleted message is displayed");
    }

    @Test(groups = {"EMC", "ui", "regression"})
    @Xray(test = {2352})
    public void VerifyAccountPageDisplaysListOfAccountsInformation() {
        navigateToAccountsPage("", "users");

        for (int i = 0; i < accountsPage.accountNames.size(); i++) {
            //verify Account names
            BrowserUtils.scrollTo(accountsPage.accountNames.get(i));
            BrowserUtils.waitForClickablility(accountsPage.accountNames.get(i), 5);
            assertTestCase.assertFalse(accountsPage.accountNames.get(i).getAttribute("href").isEmpty(), "Account name is clickable");
            //verify Account statuses
            String status = accountsPage.accountStatuses.get(i).getText();
            assertTestCase.assertTrue(status.equals("Active") || status.equals("Inactive"), "Account status is verified");
            //verify account created dates
            assertTestCase.assertTrue(accountsPage.accountCreatedDates.get(i).getText().matches("\\d{2}/\\d{2}/\\d{4}"), "Account created date is verified");
            //verify account created By info is email address
            assertTestCase.assertTrue(accountsPage.accountCreatedByEmails.get(i).getText().matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"), "Account created by is verified");
            //verify account Modified dates
            assertTestCase.assertTrue(accountsPage.accountModifiedDates.get(i).getText().matches("\\d{2}/\\d{2}/\\d{4}"), "Account modified date is verified");
            //verify account Modified By info is email address
            assertTestCase.assertTrue(accountsPage.accountModifiedByEmails.get(i).getText().matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"), "Account modified by is verified");
            i = i + 20;
        }
    }

    @Test(groups = {"EMC", "ui", "regression"})
    @Xray(test = {6537})
    public void verifyProductsCanRemovedFromAccountTest() {
        navigateToAccountsPage(accountName, "applications");

        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        //First, verify if application is added to the account
        if (!detailsPage.verifyApplication(applicationName)) {
            System.out.println(applicationName + " must be added to the account first");
            detailsPage.addApplication(applicationName);
        } else {
            System.out.println(applicationName + " is already added to the account");
        }

        //switch to the products tab
        detailsPage.clickOnProductsTab();

        //verify if product is added to the account, then remove to add it again
        if (detailsPage.verifyProduct(applicationName)) {
            System.out.println(applicationName + " is already added to the account");
            detailsPage.deleteProduct(applicationName);
        }
        //add product again
        detailsPage.addAllProducts(applicationName);
        //verify products list includes application name
        assertTestCase.assertTrue(detailsPage.verifyProduct(applicationName), "Product addition is verified");
        //delete product
        detailsPage.deleteProduct(applicationName);
        assertTestCase.assertFalse(detailsPage.verifyProduct(applicationName), "Product addition is verified");
        //assertTestCase.assertTrue(detailsPage.notification.isDisplayed(), "Notification is displayed");
    }

    @Test(groups = {"EMC", "ui", "regression"})
    @Xray(test = {7603, 7604})
    public void verifyRemoveApplicationRoleUnderUserAccountsTest() {
        navigateToAccountsPage(accountName, "applications");

        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        //First, verify if application is added to the account
        if (!detailsPage.verifyApplication(applicationName)) {
            System.out.println(applicationName + " must be added to the account first");
            detailsPage.addApplication(applicationName);
        } else {
            System.out.println(applicationName + " is already added to the account");
        }

        //Click on Users Tab and Select one User from the list
        detailsPage.clickOnUsersTab();
        assertTestCase.assertTrue(detailsPage.pageTitle.isDisplayed(), "Users Page is verified");
        assertTestCase.assertTrue(detailsPage.userNamesList.get(0).isDisplayed(), "At least one User is available under the account");
        detailsPage.userNamesList.get(0).click();
        EMCUserDetailsPage userDetailsPage = new EMCUserDetailsPage();
        BrowserUtils.waitForClickablility(userDetailsPage.applicationRolesTab, 5).click();
        assertTestCase.assertTrue(userDetailsPage.assignApplicationRolesButton.isDisplayed(), "Application Roles tab is verified");
        System.out.println("user name is " + userDetailsPage.userDetailsPageTitle.getText());
        //Select Application Role tab from User details
        if (userDetailsPage.verifyApplicationRole(applicationName)) {
            System.out.println(applicationName + " is already assigned to the user");
        } else {
            System.out.println(applicationName + " is not assigned to the user");
            userDetailsPage.addApplicationRoles(applicationName);
        }
        assertTestCase.assertTrue(userDetailsPage.verifyApplicationRole(applicationName), "Application Role is assigned to the user");
        //Click Delete button (Trash Icon)
        assertTestCase.assertTrue(userDetailsPage.deleteApplicationRole(applicationName), "User is able to delete the application role");
        assertTestCase.assertFalse(userDetailsPage.verifyApplicationRole(applicationName), "Application Role is deleted from the user");
        userDetailsPage.addApplicationRoles(applicationName);
        assertTestCase.assertTrue(userDetailsPage.verifyApplicationRole(applicationName), "Application Role is assigned to the user");
    }

    @Test(groups = {"EMC", "ui", "regression"})
    @Xray(test = {8222, 8259})
    public void verifyAssignApplicationRoleForMAUserAccountsTest() {
        navigateToAccountsPage(accountName, "applications");
        //Application name for MA User
        String applicationName = "External Scores API";
        String maUserName = "MA Test Account";
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        //First, verify if application is added to the account
        if (!detailsPage.verifyApplication(applicationName)) {
            System.out.println(applicationName + " must be added to the account first");
            detailsPage.addApplication(applicationName);
        } else {
            System.out.println(applicationName + " is already added to the account");
        }

        //Click on Users Tab and Select one User from the list
        detailsPage.clickOnUsersTab();
        assertTestCase.assertTrue(detailsPage.pageTitle.isDisplayed(), "Users Page is verified");
        assertTestCase.assertTrue(detailsPage.userNamesList.get(0).isDisplayed(), "At least one User is available under the account");
        assertTestCase.assertTrue(detailsPage.verifyUser(maUserName), "MA User is available under the account");
        detailsPage.searchUser(maUserName);
        EMCUserDetailsPage userDetailsPage = new EMCUserDetailsPage();
        BrowserUtils.waitForClickablility(userDetailsPage.applicationRolesTab, 5).click();
        assertTestCase.assertTrue(userDetailsPage.assignApplicationRolesButton.isDisplayed(), "Application Roles tab is verified");
        System.out.println("user name is " + userDetailsPage.userDetailsPageTitle.getText());
        //Select Application Role tab from User details
        if (userDetailsPage.verifyApplicationRole(applicationName)) {
            System.out.println(applicationName + " is already assigned to the user");
        } else {
            System.out.println(applicationName + " is not assigned to the user");
            userDetailsPage.addApplicationRoles(applicationName);
        }
        assertTestCase.assertTrue(userDetailsPage.verifyApplicationRole(applicationName), "Application Role is assigned to the user");
        //Click Delete button (Trash Icon)
        assertTestCase.assertTrue(userDetailsPage.deleteApplicationRole(applicationName), "User is able to delete the application role");
        assertTestCase.assertFalse(userDetailsPage.verifyApplicationRole(applicationName), "Application Role is deleted from the user");
        userDetailsPage.addApplicationRoles(applicationName);
        assertTestCase.assertTrue(userDetailsPage.verifyApplicationRole(applicationName), "Application Role is assigned to the user");
    }

    @Test(groups = {"EMC", "ui", "regression"})
    @Xray(test = {8473})
    public void verifyCantAssignMSSApplicationRoleForMAUserAccountsTest() {
        navigateToAccountsPage(accountName, "applications");
        //Application name for MA User
        String maUserName = "MA Test Account";
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        //First, verify if application is added to the account
        if (!detailsPage.verifyApplication(applicationName)) {
            System.out.println(applicationName + " must be added to the account first");
            detailsPage.addApplication(applicationName);
        } else {
            System.out.println(applicationName + " is already added to the account");
        }

        //Click on Users Tab and Select one User from the list
        detailsPage.clickOnUsersTab();
        assertTestCase.assertTrue(detailsPage.pageTitle.isDisplayed(), "Users Page is verified");
        assertTestCase.assertTrue(detailsPage.userNamesList.get(0).isDisplayed(), "At least one User is available under the account");
        assertTestCase.assertTrue(detailsPage.verifyUser(maUserName), "MA User is available under the account");
        detailsPage.searchUser(maUserName);
        EMCUserDetailsPage userDetailsPage = new EMCUserDetailsPage();
        BrowserUtils.waitForClickablility(userDetailsPage.applicationRolesTab, 5).click();
        assertTestCase.assertTrue(userDetailsPage.assignApplicationRolesButton.isDisplayed(), "Application Roles tab is verified");
        System.out.println("user name is " + userDetailsPage.userDetailsPageTitle.getText());
        //Select Application Role tab from User details

        assertTestCase.assertFalse(userDetailsPage.verifyApplicationRole(applicationName), "MSS Application Role is not assigned to the user");
        //Try to assign MSS Application Role to the user
        userDetailsPage.addApplicationRoles(applicationName);
        assertTestCase.assertFalse(userDetailsPage.verifyApplicationRole(applicationName), "MSS Application Role is not assigned to the user");
    }

    public void navigateToAccountsPage(String accountName, String tabName) {
        EMCMainPage homePage = new EMCMainPage();
        System.out.println("Navigating to Accounts page");
        //Select Account in the left menu
        try {
            homePage.openSidePanel();
        } catch (Exception e) {
            System.out.println("Side panel is not opened");
        }
        System.out.println("Side panel is opened");
        homePage.clickAccountsButton();
        System.out.println("Accounts button is clicked");
        //User is able to see New Account Page
        EMCAccountsPage accountsPage = new EMCAccountsPage();
        BrowserUtils.waitForVisibility(accountsPage.accountNames.get(0), 15);
        //assertTestCase.assertEquals(accountsPage.pageTitle.getText(), "Accounts", "Accounts page is displayed");
        System.out.println("Accounts page is displayed");
        //Search for the account where a New user will be created
        if (accountName.isEmpty()) return;
        accountsPage.search(accountName);
        //assertTestCase.assertEquals(accountsPage.accountNames.size() > 0, true, "Account is displayed");
        System.out.println(accountsPage.accountNames.get(0).getText().toLowerCase());
        //assertTestCase.assertTrue(accountsPage.accountNames.get(0).getText().toLowerCase().contains(accountName.toLowerCase()),"Account is displayed");

        //On Account Details view, select USERS tab
        accountsPage.clickOnFirstAccount();
        //BrowserUtils.wait(10);
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        switch (tabName.toLowerCase()) {
            case "users":
                detailsPage.clickOnUsersTab();
                break;
            case "applications":
                detailsPage.clickOnApplicationsTab();
                break;
            case "products":
                detailsPage.clickOnProductsTab();
                break;
            case "details":
                detailsPage.clickOnDetailsTab();
                break;
        }
    }
}
