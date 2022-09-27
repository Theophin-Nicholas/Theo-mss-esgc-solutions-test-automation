package com.esgc.Tests;

import com.esgc.Pages.*;
import com.esgc.TestBases.EMCUITestBase;
import com.esgc.Utilities.*;
import com.github.javafaker.Faker;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class AccountsPageTests extends EMCUITestBase {
    Faker faker = new Faker();
    EMCAccountsPage accountsPage = new EMCAccountsPage();

    String accountName = "INTERNAL QATest - PROD123";
    String applicationName = "TestQA";
    String activeUserName = "Efrain June2022";

    String fname = "Test";
    String lname = "user";
    String email = "testuser@mail.com";

    @Test(groups = {"EMC", "ui", "regression", "smoke", "prod"})
    @Xray(test = {3377, 3476, 3477, 3478, 3479, 2526, 2527, 2528, 2583})
    public void accountDetailsVerificationTest() {
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
        assertTestCase.assertEquals(createAccountPage.pageTitle.getText(), "Create a new Account", "Create Account page is displayed");

        //Click Cancel button
        createAccountPage.cancelButton.click();

        //User should be redirected to Account View page
        assertTestCase.assertEquals(accountsPage.pageTitle.getText(), "Accounts", "Accounts page is displayed");


        accountsPage.search(accountName);
        assertTestCase.assertEquals(accountsPage.verifyAccount(accountName), true,
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
        assertTestCase.assertTrue(detailsPage.backToAccountsButton.isDisplayed(), "Back to Accounts button is displayed");

        //Back to Accounts Table
        BrowserUtils.waitForClickablility(detailsPage.backToAccountsButton, 5).click();
        assertTestCase.assertEquals(accountsPage.pageTitle.getText(), "Accounts", "Accounts page is displayed");
        accountsPage.goToAccount(currentAccountName);

        //Click Edit Hyperlink is visible on the Top Right Corner
        //Account is editable mode
        detailsPage.editButton.click();
        assertTestCase.assertTrue(detailsPage.cancelButton.isEnabled(), "Accounts Page - Users Details - Cancel button is enabled for editing");
        assertTestCase.assertTrue(detailsPage.saveButton.isDisplayed(), "Accounts Page - Users Details - Save button is enabled for editing");
        assertTestCase.assertTrue(detailsPage.accountNameInput.isEnabled(), "Accounts Page - Users Details - Account Name input is enabled for editing");
        assertTestCase.assertTrue(detailsPage.statusCheckBox.isEnabled(), "Accounts Page - Users Details - Account status checkbox is enabled for editing");
        assertTestCase.assertTrue(detailsPage.subscriberInput.isEnabled(), "Accounts Page - Users Details - Account Subscriber Input is enabled for editing");
        assertTestCase.assertTrue(detailsPage.startDateInput.isEnabled(), "Accounts Page - Users Details - Account start date input is enabled for editing");
        assertTestCase.assertTrue(detailsPage.endDateInput.isEnabled(), "Accounts Page - Users Details - Account end date input is enabled for editing");

        //Click Cancel button on Account Details editable
        //Cancel button should take the user back to the Accounts view page
        detailsPage.cancelButton.click();
        assertTestCase.assertEquals(detailsPage.pageTitle.getText(), currentAccountName, "Account Details page is displayed");
    }

    @Test(groups = {"EMC", "ui", "regression", "smoke"})
    @Xray(test = {3374, 4174, 4175, 4222, 3979, 3981, 3992})
    public void accountCreationTests() {
        EMCMainPage homePage = new EMCMainPage();

        //Select Account in the left menu
        homePage.openSidePanel();
        homePage.clickAccountsButton();

        //User is able to see New Account Page
        EMCAccountsPage accountsPage = new EMCAccountsPage();
        assertTestCase.assertEquals(accountsPage.pageTitle.getText(), "Accounts", "Accounts page is displayed");

        //Click Create Account button
        accountsPage.clickOnCreateAccountButton();
        EMCCreateAccountPage createAccountPage = new EMCCreateAccountPage();
        assertTestCase.assertEquals(createAccountPage.pageTitle.getText(), "Create a new Account",
                "Create Account page is displayed");

        //Todays date and time in the format of dd/MM/yyyy hh:mm:ss
        String newAccountName = "QATest" + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd-hhmmss"));
        createAccountPage.createAccount(newAccountName, true);
        System.out.println(createAccountPage.accountCreatedMessage.getText());
        assertTestCase.assertEquals(createAccountPage.accountCreatedMessage.getText(), "Account created successfully",
                "Account created successfully message is displayed");

        //Go back to the Accounts section and search for the Account created in the previous step
        accountsPage.search(newAccountName);
        assertTestCase.assertTrue(accountsPage.verifyAccount(newAccountName), "Account is displayed in the list");


        accountsPage.clickOnAccount(newAccountName);
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();

        //Select Applications Tab
        detailsPage.clickOnApplicationsTab();
        assertTestCase.assertTrue(detailsPage.applicationsTabTitle.isDisplayed(), "Applications Tab is displayed");
        assertTestCase.assertEquals(detailsPage.applicationsNamesList.size(),0, "Add Application button is displayed");
        assertTestCase.assertTrue(detailsPage.noApplicationMessage.isDisplayed(), "No Applications assigned message is displayed");

        //Click Assign Applications button on the top right of the section.
        detailsPage.assignApplicationsButton.click();
        BrowserUtils.waitForVisibility(detailsPage.assignApplicationsModalTitle, 5);
        assertTestCase.assertTrue(detailsPage.assignApplicationsModalTitle.isDisplayed(), "Assign Applications popup is displayed");
        BrowserUtils.waitForClickablility(detailsPage.doneButton, 5).click();
        //Click more than one Assign button from the list of available applications
        assertTestCase.assertTrue(detailsPage.assignApplication(applicationName),"Application is assigned");
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
        assertTestCase.assertEquals(detailsPage.productsTabTitle.getText(), "Products", "Products Tab is displayed");
        assertTestCase.assertEquals(detailsPage.currentProductsList.size(),0, "Add Product button is displayed");
        assertTestCase.assertTrue(detailsPage.noProductsMessage.isDisplayed(), "No Products assigned message is displayed");

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
    @Xray(test = {4812, 4813, 4814, 4815, 2350})
    public void accountDetailsPageTest() {
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
    @Xray(test = {4806, 6737, 7294, 7296, 7300})
    public void createNewUserTest() {
        navigateToAccountsPage(accountName, "users");
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        assertTestCase.assertTrue(detailsPage.addUserButton.isDisplayed(), "Users Tab is displayed");
        assertTestCase.assertTrue(detailsPage.userNamesList.size()> 0, "Users list is displayed");
        //Click on Add User button
        detailsPage.clickOnAddUserButton();
        assertTestCase.assertTrue(detailsPage.addUserPanelTitle.isDisplayed(), "Add User popup is displayed");
        assertTestCase.assertTrue(detailsPage.firstNameInput.isDisplayed(), "FirstName input is displayed");
        assertTestCase.assertTrue(detailsPage.lastNameInput.isDisplayed(), "LastName input is displayed");
        assertTestCase.assertTrue(detailsPage.userNameInput.isDisplayed(), "Username input is displayed");
        assertTestCase.assertTrue(detailsPage.emailInput.isDisplayed(), "Email input is displayed");
        assertTestCase.assertTrue(detailsPage.sendActivationEmailCheckbox.isEnabled(), "Activation checkbox is displayed");
        BrowserUtils.scrollTo(detailsPage.sendActivationEmailCheckbox);
        assertTestCase.assertTrue(detailsPage.sendActivationEmailCheckbox.isEnabled(), "Activation checkbox is displayed");

        //Enter all data required in the modal
        // Click Save button
        fname = "QATestUser";
        lname = faker.name().lastName();
        email = faker.internet().emailAddress();
        assertTestCase.assertTrue(detailsPage.createUser(fname, lname, email, false),
                "User is created successfully");
        assertTestCase.assertTrue(detailsPage.notification.isDisplayed(), "User is created message is displayed");
        assertTestCase.assertTrue(detailsPage.addUserButton.isDisplayed(), "Users Tab is displayed");
        assertTestCase.assertTrue(detailsPage.userNamesList.size()> 0, "Users list is displayed");
        assertTestCase.assertTrue(detailsPage.verifyUser(fname+" "+lname), "User is verified in users list");
        detailsPage.searchUser(email);
        EMCAccountsEditUserPage editUserPage = new EMCAccountsEditUserPage();
        assertTestCase.assertTrue(editUserPage.editPageTitle.isDisplayed(), "Edit User popup is displayed");
        assertTestCase.assertTrue(editUserPage.deleteButton.isDisplayed(), "Delete button is displayed");
        assertTestCase.assertTrue(editUserPage.deleteUser(), "User is deleted successfully");
        assertTestCase.assertTrue(detailsPage.notification.isDisplayed(), "User is deleted message is displayed");
        assertTestCase.assertFalse(detailsPage.verifyUser(fname+" "+lname), "User is not verified in users list");
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
    @Xray(test = {5179, 3994})
    public void verifyUserAssignApplicationRolesTest() {
        navigateToAccountsPage(accountName, "applications");
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        assertTestCase.assertTrue(detailsPage.assignApplicationsButton.isDisplayed(), "Users Tab is displayed");

        if (detailsPage.verifyApplication(applicationName)) detailsPage.deleteApplication(applicationName);
        assertTestCase.assertFalse(detailsPage.verifyApplication(applicationName), "Application is not present");

        assertTestCase.assertTrue(detailsPage.assignApplication(applicationName), "Application is assigned for the user");
        BrowserUtils.waitForVisibility(detailsPage.applicationAddedMessage, 5);
        assertTestCase.assertTrue(detailsPage.applicationAddedMessage.isDisplayed(), "Application is assigned for the user");
        detailsPage.clickOnDetailsTab();
        BrowserUtils.wait(1);
        detailsPage.clickOnApplicationsTab();
        BrowserUtils.wait(3);
        assertTestCase.assertTrue(detailsPage.verifyApplication(applicationName), "Application is present");
        assertTestCase.assertTrue(detailsPage.deleteApplicationsButtons.size()>0, "Trash icons are displayed");

        assertTestCase.assertTrue(detailsPage.deleteApplication(applicationName), "Application is deleted");
        assertTestCase.assertFalse(detailsPage.verifyApplication(applicationName), "Application is not present");

        BrowserUtils.waitForClickablility(detailsPage.assignApplicationsButton, 5).click();
        assertTestCase.assertTrue(detailsPage.assignApplicationsModalTitle.isDisplayed(), "Assign Application Model is displayed");
        assertTestCase.assertTrue(detailsPage.assignApplicationsModalSearchInput.isDisplayed(), "Assign Application Model - Search Input is displayed");
        BrowserUtils.waitForClickablility(detailsPage.assignApplicationsModalSearchInput, 5).sendKeys(applicationName);
        List<String> availableApplications = detailsPage.assignApplicationsModalApplicationsList.stream().map(name -> name.getText().toLowerCase()).collect(Collectors.toList());
        System.out.println("availableApplications = " + availableApplications);
        System.out.println("===================================");
        assertTestCase.assertTrue(availableApplications.contains(applicationName.toLowerCase()), "Assign Application Model - Search Result contains the application");
        BrowserUtils.waitForClickablility(detailsPage.assignApplicationModalDoneButton, 5).click();
    }

    @Test(groups = {"EMC", "ui", "regression"})
    @Xray(test = {5185, 5769, 5768})
    public void verifyUserRemoveApplicationRolesTest() {
        navigateToAccountsPage(accountName, "applications");
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        assertTestCase.assertTrue(detailsPage.assignApplicationsButton.isDisplayed(), "Applications Tab is displayed");

        //Click on Remove Application (trash can icon)
        while (detailsPage.verifyApplication(applicationName)){
            detailsPage.deleteApplication(applicationName);
        }

        //Open Users tab
        detailsPage.clickOnUsersTab();
        //Users page is displayed with all the user available listed
        assertTestCase.assertTrue(detailsPage.addUserButton.isDisplayed(), "Users Tab is displayed");

        //Click over the Name hyperlink
        BrowserUtils.waitForClickablility(detailsPage.userNamesList.get(0), 5).click();

        //User account details page are displayed
        EMCUserDetailsPage userDetailsPage = new EMCUserDetailsPage();
        assertTestCase.assertTrue(userDetailsPage.applicationRolesTab.isDisplayed(), "User Details Page  - Application Roles Tab is displayed");

        //Open Application Role tab
        BrowserUtils.waitForClickablility(userDetailsPage.applicationRolesTab, 5).click();
        //Application Role page is displayed with the options of Assign Application Role button and an Empty list of the applications
        assertTestCase.assertTrue(userDetailsPage.assignApplicationRolesButton.isDisplayed(), "Application Roles tab is verified");
        System.out.println("user name is " + userDetailsPage.userDetailsPageTitle.getText());
        //Select Application Role tab from User details
        assertTestCase.assertFalse(userDetailsPage.verifyApplicationRole(applicationName), "Un-Assigned Application Roles is not displayed");
        BrowserUtils.waitForClickablility(userDetailsPage.assignApplicationRolesButton, 5).click();
        BrowserUtils.wait(5);
        List<String> availableRoles = userDetailsPage.applicationRolesList.stream().map(name -> name.getText().toLowerCase()).collect(Collectors.toList());
        assertTestCase.assertFalse(availableRoles.contains(applicationName.toLowerCase()), "Available Roles does not contain the application");
        BrowserUtils.waitForClickablility(userDetailsPage.doneButton, 5).click();

        //re-add removed application
        BrowserUtils.waitForClickablility(userDetailsPage.backToUserButton,5).click();
        detailsPage.clickOnApplicationsTab();
        assertTestCase.assertTrue(detailsPage.assignApplicationsButton.isDisplayed(), "Applications Tab is displayed");
        assertTestCase.assertTrue(detailsPage.assignApplication(applicationName), "Application is assigned for the user");
        BrowserUtils.waitForVisibility(detailsPage.applicationAddedMessage, 5);
        assertTestCase.assertTrue(detailsPage.applicationAddedMessage.isDisplayed(), "Application is assigned for the user");
        detailsPage.clickOnUsersTab();
        BrowserUtils.waitForClickablility(detailsPage.userNamesList.get(0), 5).click();
        //User account details page are displayed
        userDetailsPage = new EMCUserDetailsPage();
        assertTestCase.assertTrue(userDetailsPage.applicationRolesTab.isDisplayed(), "User Details Page  - Application Roles Tab is displayed");
        //Open Application Role tab
        BrowserUtils.waitForClickablility(userDetailsPage.applicationRolesTab, 5).click();
        //Application Role page is displayed with the options of Assign Application Role button and an Empty list of the applications
        assertTestCase.assertTrue(userDetailsPage.assignApplicationRolesButton.isDisplayed(), "Application Roles tab is verified");
        assertTestCase.assertFalse(userDetailsPage.verifyApplicationRole(applicationName), "Application Roles is not displayed for the user");
        BrowserUtils.waitForClickablility(userDetailsPage.assignApplicationRolesButton, 5).click();
        BrowserUtils.wait(5);
        availableRoles = userDetailsPage.applicationRolesList.stream().map(name -> name.getText().toLowerCase()).collect(Collectors.toList());
        assertTestCase.assertTrue(availableRoles.contains(applicationName.toLowerCase()), "Available Roles contains the application");
        BrowserUtils.waitForClickablility(userDetailsPage.doneButton, 5).click();
        userDetailsPage.addApplicationRoles(applicationName);
        assertTestCase.assertTrue(userDetailsPage.verifyApplicationRole(applicationName), "Application Roles is displayed for the user");
        //Click Delete button (Trash Icon)
        assertTestCase.assertTrue(userDetailsPage.deleteApplicationRole(applicationName), "User is able to delete the application role");
        assertTestCase.assertFalse(userDetailsPage.verifyApplicationRole(applicationName), "Application Role is deleted from the user");

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
        if(detailsPage.verifyApplication(applicationName)) detailsPage.deleteApplication(applicationName);
        if (detailsPage.verifyApplication(applicationName+2)) detailsPage.deleteApplication(applicationName+2);

        assertTestCase.assertTrue(detailsPage.addTestApplications(applicationName), applicationName+" Applications is assigned");
        assertTestCase.assertTrue(detailsPage.addTestApplications(applicationName+2), applicationName+2+" Applications is assigned");

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
        if(detailsPage.verifyApplication(applicationName)) detailsPage.deleteApplication(applicationName);
        if (detailsPage.verifyApplication(applicationName+2)) detailsPage.deleteApplication(applicationName+2);
        assertTestCase.assertTrue(detailsPage.addTestApplications(applicationName), "Test Applications is assigned");
        assertTestCase.assertTrue(detailsPage.addTestApplications(applicationName+2), "TestQA Application is assigned");
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
    @Xray(test = {2352, 2582})
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

    @Test(groups = {"EMC", "ui","regression"}, description = "UI | EMC | Users | Verify User can Cancel/Save the Update User Information")
    @Xray(test = {4973, 4974, 4976})
    public void verifyUserCanCancelUpdateTest() {
        navigateToAccountsPage(accountName, "users");
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        assertTestCase.assertTrue(detailsPage.userNamesList.size()>0, "Users Page - List of Users is displayed");
        EMCAccountsEditUserPage editPage = new EMCAccountsEditUserPage();

        //Click over a User name
        if (detailsPage.verifyUser("QA"+activeUserName)) {
            System.out.println("QA"+activeUserName+" is already available under the account");
            detailsPage.searchUser("QA"+activeUserName);
            editPage.editUser(editPage.firstNameInput.getAttribute("value").replaceAll("QA",""),
                                editPage.lastNameInput.getAttribute("value"),
                                editPage.emailInput.getAttribute("value"));
            BrowserUtils.wait(5);
        }
        detailsPage.searchUser(activeUserName);

        //User details is displayed with EDIT hyperlink on the upper right
        assertTestCase.assertTrue(editPage.editButton.isDisplayed(),"Edit User Page - Edit Hyperlink is displayed");
        assertTestCase.assertFalse(editPage.firstNameInput.isEnabled(),"Edit User Page - First Name Input is disabled");
        assertTestCase.assertFalse(editPage.lastNameInput.isEnabled(),"Edit User Page - Last Name Input is disabled");
        assertTestCase.assertFalse(editPage.emailInput.isEnabled(),"Edit User Page - Email Input is disabled");

        //Click Edit hyperlink
        BrowserUtils.waitForClickablility(editPage.editButton, 5).click();

        //User fields are in Editable mode. Save button and Cancel button are available.
        assertTestCase.assertTrue(editPage.saveButton.isEnabled(),"Edit User Page - Save Button is enabled");
        assertTestCase.assertTrue(editPage.cancelButton.isEnabled(),"Edit User Page - Cancel Button is enabled");
        assertTestCase.assertTrue(editPage.firstNameInput.isEnabled(),"Edit User Page - First Name Input is enabled");
        assertTestCase.assertTrue(editPage.lastNameInput.isEnabled(),"Edit User Page - Last Name Input is enabled");
        assertTestCase.assertTrue(editPage.emailInput.isEnabled(),"Edit User Page - Email Input is enabled");


        String firstName = editPage.firstNameInput.getAttribute("value");
        String lastName = editPage.lastNameInput.getAttribute("value");
        String email = editPage.emailInput.getAttribute("value");

        //Update and remove one or more of the Required Fields inside the User Form
        //Fields display a Red colored Error message below the missing Field
        //Save button is not available and User can not save changes
        String color="";
        clear(editPage.firstNameInput);
        assertTestCase.assertTrue(editPage.firstNameInputWarning.isDisplayed(),"Edit User Page - First Name cannot be empty warning is displayed");
        assertTestCase.assertFalse(editPage.saveButton.isEnabled(),"Edit User Page - Save Button is disabled");
        color = Color.fromString(editPage.firstNameInputWarning.getCssValue("color")).asHex().toUpperCase();
        assertTestCase.assertEquals(color,"#F44336","Edit User Page - First Name cannot be empty warning is displayed in red color");
        editPage.firstNameInput.sendKeys("Test");

        clear(editPage.lastNameInput);
        assertTestCase.assertTrue(editPage.lastNameInputWarning.isDisplayed(),"Edit User Page - Last Name cannot be empty warning is displayed");
        assertTestCase.assertFalse(editPage.saveButton.isEnabled(),"Edit User Page - Save Button is disabled");
        color = Color.fromString(editPage.lastNameInputWarning.getCssValue("color")).asHex().toUpperCase();
        assertTestCase.assertEquals(color,"#F44336","Edit User Page - Last Name cannot be empty warning is displayed in red color");
        editPage.lastNameInput.sendKeys("Test");

        clear(editPage.emailInput);
        assertTestCase.assertTrue(editPage.emailInputWarning.isDisplayed(),"Edit User Page - Email cannot be empty warning is displayed");
        assertTestCase.assertFalse(editPage.saveButton.isEnabled(),"Edit User Page - Save Button is disabled");
        color = Color.fromString(editPage.emailInputWarning.getCssValue("color")).asHex().toUpperCase();
        assertTestCase.assertEquals(color,"#F44336","Edit User Page - Email cannot be empty warning is displayed in red color");
        editPage.emailInput.sendKeys("Test");
        assertTestCase.assertTrue(editPage.validEmailWarning.isDisplayed(),"Edit User Page - Email is not valid warning is displayed");
        editPage.emailInput.clear();
        editPage.emailInput.sendKeys(email);
        //Update/Change any of the fields inside the User Form and Click Cancel button
        assertTestCase.assertTrue(editPage.saveButton.isEnabled(),"Edit User Page - Save Button is enabled");
        assertTestCase.assertTrue(editPage.cancelButton.isEnabled(),"Edit User Page - Cancel Button is enabled");
        BrowserUtils.waitForClickablility(editPage.cancelButton, 5).click();
        assertTestCase.assertFalse(editPage.firstNameInput.isEnabled(),"Edit User Page - First Name Input is disabled");
        assertTestCase.assertFalse(editPage.lastNameInput.isEnabled(),"Edit User Page - Last Name Input is disabled");
        assertTestCase.assertFalse(editPage.emailInput.isEnabled(),"Edit User Page - Email Input is disabled");
        assertTestCase.assertTrue(editPage.firstNameInput.getAttribute("value").equals(firstName),"Edit User Page - First Name Input is not changed");
        assertTestCase.assertTrue(editPage.lastNameInput.getAttribute("value").equals(lastName),"Edit User Page - Last Name Input is not changed");
        assertTestCase.assertTrue(editPage.emailInput.getAttribute("value").equals(email),"Edit User Page - Email Input is not changed");

        //Update/Change any of the fields inside the User Form and Click Save button
        String createdInfo = editPage.createdByInfo.getText();
        String modifiedInfo = editPage.modifiedByInfo.getText();

        editPage.editUser("QA"+firstName, lastName, email);
        BrowserUtils.waitForVisibility(detailsPage.notification, 5);
        assertTestCase.assertTrue(detailsPage.notification.isDisplayed(),"Account Details Page - Notification is displayed");
        detailsPage.searchUser("QA"+firstName+" "+lastName);
        assertTestCase.assertTrue(editPage.editButton.isDisplayed(),"Edit User Page - Edit Hyperlink is displayed");
        assertTestCase.assertEquals(editPage.firstNameInput.getAttribute("value"),"QA"+firstName,"Edit User Page - First Name Input is changed");
        assertTestCase.assertEquals(editPage.createdByInfo.getText(),createdInfo,"Edit User Page - Created By Info is not changed");
        //assertTestCase.assertNotEquals(editPage.modifiedByInfo.getText(),modifiedInfo,"Edit User Page - Modified By Info is changed");
        assertTestCase.assertTrue(editPage.modifiedByInfo.getText().contains(DateTimeUtilities.getCurrentDate("MM/dd/yyyy")),"Edit User Page - Modified By Info date is verified");
        editPage.editUser(firstName, lastName, email);
    }

    @Test(groups = {"EMC", "ui","regression"}, description = "UI | EMC | Accounts Applications | Validate that User is Able to Assign More than one Application to the Account")
    @Xray(test = {3983})
    public void verifyAssignMultipleApplicationsToAccountTest() {
        navigateToAccountsPage(accountName, "applications");
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        assertTestCase.assertEquals(detailsPage.applicationsTabTitle.getText(),"Applications","Account Details Page - AApplication Tab Title is verified");
        if(detailsPage.verifyApplication(applicationName)) detailsPage.deleteApplication(applicationName);
        if(detailsPage.verifyApplication(applicationName+2)) detailsPage.deleteApplication(applicationName+2);
        BrowserUtils.waitForClickablility(detailsPage.assignApplicationsButton, 5).click();
        assertTestCase.assertTrue(detailsPage.assignApplicationsModalTitle.isDisplayed(),"Account Details Page - Assign Applications Modal is displayed");
        assertTestCase.assertTrue(detailsPage.assignApplicationsModalSearchInput.isDisplayed(),"Account Details Page - Assign Applications Modal Search Box is displayed");
        detailsPage.searchInput.sendKeys(applicationName);
        assertTestCase.assertTrue(detailsPage.assignApplicationsModalAssignButtonsList.size()>1,"Account Details Page - Assign Application modal - Multiple Assign Buttons are displayed");
        for (int i = 0; i < detailsPage.assignApplicationsModalAssignButtonsList.size(); i++) {
            BrowserUtils.waitForClickablility(detailsPage.assignApplicationsModalAssignButtonsList.get(i), 5).click();
            BrowserUtils.wait(1);
            System.out.println("i = " + i);
        }

        assertTestCase.assertTrue(detailsPage.assignApplicationModalDoneButton.isEnabled(),"Account Details Page - Assign Application Modal - Done Button is enabled");
        BrowserUtils.waitForClickablility(detailsPage.assignApplicationModalDoneButton, 5).click();
        assertTestCase.assertTrue(detailsPage.notification.isDisplayed(),"Account Details Page - Notification is displayed");
        detailsPage.clickOnDetailsTab();
        BrowserUtils.wait(1);
        detailsPage.clickOnApplicationsTab();
        BrowserUtils.wait(3);
        assertTestCase.assertTrue(detailsPage.verifyApplication(applicationName),applicationName+" application is assigned to the account");
        assertTestCase.assertTrue(detailsPage.verifyApplication(applicationName+2),applicationName+2+" application is assigned to the account");

        //Validate that User can not See Assigned Applications in Assign Application Modal
        BrowserUtils.waitForClickablility(detailsPage.assignApplicationsButton, 5).click();
        assertTestCase.assertTrue(detailsPage.assignApplicationsModalTitle.isDisplayed(),"Account Details Page - Assign Applications Modal is displayed");
        assertTestCase.assertTrue(detailsPage.assignApplicationsModalSearchInput.isDisplayed(),"Account Details Page - Assign Applications Modal Search Box is displayed");
        detailsPage.searchInput.sendKeys(applicationName);
        assertTestCase.assertTrue(detailsPage.assignApplicationsModalAssignButtonsList.size()==0,"Account Details Page - Assign Application modal - Multiple Assign Buttons are displayed");

    }

    @Test(groups = {"EMC", "ui","regression"}, description = "UI | EMC | Accounts | Verify that User Can't Create a New Account if Required Fields are Missing ")
    @Xray(test = {3983})
    public void verifyRequiredFieldsForAccountCreationTest() {
        EMCMainPage homePage = new EMCMainPage();

        //Select Account in the left menu
        homePage.openSidePanel();
        homePage.clickAccountsButton();

        //User is able to see New Account Page
        EMCAccountsPage accountsPage = new EMCAccountsPage();
        assertTestCase.assertEquals(accountsPage.pageTitle.getText(), "Accounts", "Accounts page is displayed");

        //Click Create Account button
        accountsPage.clickOnCreateAccountButton();
        EMCCreateAccountPage createPage = new EMCCreateAccountPage();
        assertTestCase.assertEquals(createPage.pageTitle.getText(), "Create a new Account",
                "Create Account page is displayed");

        createPage.accountNameInput.sendKeys("Test Account");
        assertTestCase.assertTrue(createPage.saveButton.isEnabled(), "Account Creation Page - Save button is enabled");

        //verify Start Date data required
        BrowserUtils.waitForVisibility(createPage.startDateInput, 5).click();
        assertTestCase.assertTrue(createPage.dateClearButton.isDisplayed(), "Date Clear button is displayed");
        BrowserUtils.waitForClickablility(createPage.dateClearButton, 5).click();
        assertTestCase.assertTrue(createPage.requiredTag.isDisplayed(), "Account Creation - Contract Start Date field is required message is displayed");
        assertTestCase.assertFalse(createPage.saveButton.isEnabled(),"Account Creation - Save button is disabled");
        BrowserUtils.waitForVisibility(createPage.startDateInput, 5).click();
        BrowserUtils.waitForVisibility(createPage.dateOkButton, 5).click();
        assertTestCase.assertFalse(createPage.startDateInput.getAttribute("value").isEmpty(), "Account Creation - Contract Start Date field value is not empty");

        //verify End Date data required
        BrowserUtils.waitForVisibility(createPage.endDateInput, 5).click();
        assertTestCase.assertTrue(createPage.dateClearButton.isDisplayed(), "Date Clear button is displayed");
        BrowserUtils.waitForClickablility(createPage.dateClearButton, 5).click();
        assertTestCase.assertTrue(createPage.requiredTag.isDisplayed(), "Account Creation - Contract End Date field is required message is displayed");
        assertTestCase.assertFalse(createPage.saveButton.isEnabled(),"Account Creation - Save button is disabled");
        BrowserUtils.waitForVisibility(createPage.endDateInput, 5).click();
        BrowserUtils.waitForVisibility(createPage.dateOkButton, 5).click();
        assertTestCase.assertFalse(createPage.endDateInput.getAttribute("value").isEmpty(), "Account Creation - Contract End Date field value is not empty");
        assertTestCase.assertTrue(createPage.saveButton.isEnabled(),"Account Creation - Save button is enabled");
        assertTestCase.assertTrue(createPage.cancelButton.isEnabled(),"Account Creation - Save button is enabled");
        BrowserUtils.waitForClickablility(createPage.cancelButton, 5).click();

    }

    @Test(groups = {"EMC", "ui","regression"}, description = "UI | EMC | Accounts | Verify the ability to search for an account")
    @Xray(test = {4514})
    public void verifySearchForAccountTest() {
        EMCMainPage homePage = new EMCMainPage();

        //Select Account in the left menu
        homePage.openSidePanel();
        homePage.clickAccountsButton();

        //User is able to see New Account Page
        EMCAccountsPage accountsPage = new EMCAccountsPage();
        assertTestCase.assertEquals(accountsPage.pageTitle.getText(), "Accounts", "Accounts page is displayed");
        assertTestCase.assertTrue(accountsPage.searchInput.isDisplayed(),"Accounts Page - Search Input is displayed");

        //Verify that User can search for an account
        accountsPage.search(accountName);
        assertTestCase.assertTrue(accountsPage.verifyAccount(accountName),"Account is displayed in the search results");

        //Enter the active account's full name on the search field using lowercases.
        accountsPage.search(accountName);
        assertTestCase.assertTrue(accountsPage.verifyAccount(accountName),"Account is displayed in the search results");

        //Enter the active account's full name on the search field using uppercases.
        accountsPage.search(accountName.toUpperCase());
        assertTestCase.assertTrue(accountsPage.verifyAccount(accountName),"Account is displayed in the search results");

        //Enter the active account's full name on the search field using mixed cases.
        accountsPage.search(accountName.substring(0, 1).toUpperCase() + accountName.substring(1).toLowerCase());
        assertTestCase.assertTrue(accountsPage.verifyAccount(accountName),"Account is displayed in the search results");

        //Enter the active account's partial name on the search field using lowercases.
        accountsPage.search(accountName.substring(0, 3).toLowerCase());
        assertTestCase.assertTrue(accountsPage.verifyAccount(accountName),"Account is displayed in the search results");

        //Enter the active account's partial name on the search field using uppercases.
        accountsPage.search(accountName.substring(0, 3).toUpperCase());
        assertTestCase.assertTrue(accountsPage.verifyAccount(accountName),"Account is displayed in the search results");

        //Enter the active account's partial name on the search field using mixed cases.
        accountsPage.search(accountName.substring(0, 1).toUpperCase() + accountName.substring(1, 3).toLowerCase());
        assertTestCase.assertTrue(accountsPage.verifyAccount(accountName),"Account is displayed in the search results");

        String inactiveAccountName = "Test Account";
        //Enter the inactive account's full name on the search field using lowercases.
        accountsPage.search(inactiveAccountName.toLowerCase());
        assertTestCase.assertTrue(accountsPage.verifyAccount(inactiveAccountName, false),"Account is displayed in the search results");

        //Enter the inactive account's full name on the search field using uppercases.
        accountsPage.search(inactiveAccountName.toUpperCase());
        assertTestCase.assertTrue(accountsPage.verifyAccount(inactiveAccountName,false),"Account is displayed in the search results");

        //Enter the inactive account's full name on the search field using mixed cases.
        accountsPage.search(inactiveAccountName.substring(0, 1).toUpperCase() + inactiveAccountName.substring(1).toLowerCase());
        assertTestCase.assertTrue(accountsPage.verifyAccount(inactiveAccountName,false),"Account is displayed in the search results");

        //Enter the inactive account's partial name on the search field using lowercases.
        accountsPage.search(inactiveAccountName.substring(0, 3).toLowerCase());
        assertTestCase.assertTrue(accountsPage.verifyAccount(inactiveAccountName, false),"Account is displayed in the search results");

        //Enter the inactive account's partial name on the search field using uppercases.
        accountsPage.search(inactiveAccountName.substring(0, 3).toUpperCase());
        assertTestCase.assertTrue(accountsPage.verifyAccount(inactiveAccountName, false),"Account is displayed in the search results");

        //Enter the inactive account's partial name on the search field using mixed cases.
        accountsPage.search(inactiveAccountName.substring(0, 1).toUpperCase() + inactiveAccountName.substring(1, 3).toLowerCase());
        assertTestCase.assertTrue(accountsPage.verifyAccount(inactiveAccountName, false),"Account is displayed in the search results");


    }

    @Test(groups = {"EMC", "ui", "regression"}, description = "UI | EMC | Accounts | Verify the \"Subscriber Type\" options inside Dropbox for new account")
    @Xray(test = {9591, 9592, 9606})
    public void verifySubscriberTypeInputForAccountCreationTests() {
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
        assertTestCase.assertEquals(createAccountPage.pageTitle.getText(), "Create a new Account", "Create Account page is displayed");
        assertTestCase.assertTrue(createAccountPage.accountNameInput.isEnabled(), "Account Name input is displayed");
        assertTestCase.assertTrue(createAccountPage.statusCheckbox.isEnabled(), "Account Status input is displayed");
        assertTestCase.assertTrue(createAccountPage.subscriberInput.isEnabled(), "Subscriber input is displayed");
        assertTestCase.assertTrue(createAccountPage.startDateInput.isEnabled(), "Start Date input is displayed");
        assertTestCase.assertTrue(createAccountPage.endDateInput.isEnabled(), "End Date input is displayed");
        assertTestCase.assertTrue(createAccountPage.saveButton.isDisplayed(), "Save button is displayed");
        assertTestCase.assertTrue(createAccountPage.cancelButton.isEnabled(), "Cancel button is displayed");
        createAccountPage.subscriberInput.click();
        assertTestCase.assertTrue(createAccountPage.subscriberTypeList.size() > 0, "Subscriber type list is displayed");
        String subscriberType = createAccountPage.subscriberTypeList.get(0).getText();
        createAccountPage.subscriberTypeList.get(0).click();

        assertTestCase.assertEquals(createAccountPage.subscriberInput.getAttribute("value"), subscriberType, "Subscriber type is selected");
        clear(createAccountPage.subscriberInput);
        assertTestCase.assertEquals(createAccountPage.subscriberInput.getAttribute("value"), "", "Subscriber type is cleared");
        createAccountPage.subscriberInput.click();
        subscriberType = createAccountPage.subscriberTypeList.get(1).getText();
        createAccountPage.subscriberTypeList.get(1).click();

        assertTestCase.assertEquals(createAccountPage.subscriberInput.getAttribute("value"), subscriberType, "Subscriber type is selected");
        clear(createAccountPage.subscriberInput);
        assertTestCase.assertEquals(createAccountPage.subscriberInput.getAttribute("value"), "", "Subscriber type is cleared");

        String accountName = "QATest" + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyMMdd-hhmmss"));
        createAccountPage.accountNameInput.sendKeys(accountName);
        if(createAccountPage.statusCheckbox.isSelected()){
            createAccountPage.statusCheckbox.click();
        }
        createAccountPage.subscriberInput.click();
        subscriberType = createAccountPage.subscriberTypeList.get(0).getText();
        createAccountPage.subscriberTypeList.get(0).click();

        createAccountPage.saveButton.click();
        assertTestCase.assertTrue(accountsPage.verifyAccount(accountName),"Account is displayed in the search results");
        accountsPage.search(accountName);
        accountsPage.clickOnAccount(accountName);
        createAccountPage = new EMCCreateAccountPage();
        assertTestCase.assertEquals(createAccountPage.subscriberInput.getAttribute("value"), subscriberType, "Subscriber type is selected");

    }

    @Test(groups = {"EMC", "ui", "regression"}, description = "UI | EMC | Accounts | Verify the \"Subscriber Type\" options inside Dropbox for existing account")
    @Xray(test = {9593, 9595})
    public void verifySubscriberTypeInputForOldAccountTests() {
        navigateToAccountsPage("Test Account","details");
        EMCAccountDetailsPage detailsPage = new EMCAccountDetailsPage();
        assertTestCase.assertTrue(detailsPage.editButton.isDisplayed(), "Edit button is displayed");
        assertTestCase.assertTrue(detailsPage.subscriberInput.isDisplayed(), "Subscriber input is displayed");
        BrowserUtils.waitForClickablility(detailsPage.editButton, 5).click();
        detailsPage.subscriberInput.click();
        assertTestCase.assertTrue(detailsPage.subscriberTypeList.size() > 0, "Subscriber type list is displayed");

        //Select one option from the dropdown list
        String subscriberType = detailsPage.subscriberTypeList.get(0).getText();
        detailsPage.subscriberTypeList.get(0).click();
        assertTestCase.assertEquals(detailsPage.subscriberInput.getAttribute("value"), subscriberType, "Subscriber type is selected");

        clear(detailsPage.subscriberInput);
        assertTestCase.assertEquals(detailsPage.subscriberInput.getAttribute("value"), "", "Subscriber type is cleared");

        detailsPage.subscriberInput.click();
        subscriberType = detailsPage.subscriberTypeList.get(1).getText();
        detailsPage.subscriberTypeList.get(1).click();
        assertTestCase.assertEquals(detailsPage.subscriberInput.getAttribute("value"), subscriberType, "Subscriber type is selected");

        assertTestCase.assertTrue(detailsPage.saveButton.isEnabled(), "Save button is enabled");
        assertTestCase.assertTrue(detailsPage.cancelButton.isEnabled(), "Cancel button is enabled");
        detailsPage.saveButton.click();
        assertTestCase.assertEquals(detailsPage.subscriberInput.getAttribute("value"), subscriberType, "Subscriber type is selected");
        BrowserUtils.waitForClickablility(detailsPage.editButton, 15).click();
        clear(detailsPage.subscriberInput);
        detailsPage.saveButton.click();
        assertTestCase.assertEquals(detailsPage.subscriberInput.getAttribute("value"), "", "Subscriber type is cleared");

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

    public void clear(WebElement element) {
        while (!element.getAttribute("value").isEmpty()) {
            element.sendKeys(Keys.BACK_SPACE);
        }
        element.sendKeys(Keys.TAB);
    }
}
