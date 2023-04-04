package com.esgc.Tests;

import com.esgc.Pages.*;
import com.esgc.TestBases.EMCUITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Environment;
import com.esgc.Utilities.Xray;
import com.github.javafaker.Faker;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class ConfigurationPageTests extends EMCUITestBase {
    String accountName = "INTERNAL QATest - PROD123";
    String applicationName = "TestQA";
    String roleName = "TestRole";
    String userName = "Ferhat Demir";
    Faker faker = new Faker();
    public void navigateToConfigPage(String option) {
        EMCMainPage mainPage = new EMCMainPage();
        mainPage.goToConfigurationsPage();
        EMCConfigurationsPage configurationsPage = new EMCConfigurationsPage();
        BrowserUtils.waitForVisibility(configurationsPage.pageTitle, 10);
        if(option.toLowerCase().equals("users")){
            System.out.println("Navigating to Users page under Config Page");
            configurationsPage.usersButton.click();
        } else if(option.toLowerCase().equals("permission roles")){
            System.out.println("Navigating to Permission Roles page under Config Page");
            configurationsPage.permissionRolesButton.click();
        } else if(option.toLowerCase().equals("sites settings")){
            System.out.println("Navigating to Site Settings page under Config Page");
            configurationsPage.sitesSettingsButton.click();
        }
    }

    public void clear(WebElement element) {
        while (element.getAttribute("value").length() > 0) {
            element.sendKeys(Keys.BACK_SPACE);
        }
        element.sendKeys(Keys.TAB);
    }

    @Test(groups = {EMC, UI, SMOKE,REGRESSION,PROD})
    @Xray(test = {6627,7820, 6944})
    public void configurationPageAdminTests() {
        EMCMainPage mainPage = new EMCMainPage();
        System.out.println("Navigating to Accounts page");
        //Select Account in the left menu
        try {
            mainPage.openSidePanel();
            System.out.println("Side panel is opened");
        } catch (Exception e) {
            System.out.println("Side panel is not opened");
        }

        mainPage.clickConfigurationsButton();
        System.out.println("Configuration button is clicked");
        EMCConfigurationsPage configurationsPage = new EMCConfigurationsPage();

        //Go to the Permission Roles section by selecting it from the User Settings
        configurationsPage.clickOnPermissionRoles();
        EMCRolesPage rolesPage = new EMCRolesPage();

        //The Permission Roles section is opened and all roles available on EMC are displayed as a list
        //rolesPage.roles.forEach(role -> System.out.println(role.getText()));
        assertTestCase.assertTrue(rolesPage.roles.size() > 0, "Roles are located in the page");
        System.out.println("rolesPage.pageTitle = " + rolesPage.pageTitle.getText());
        assertTestCase.assertEquals(rolesPage.pageTitle.getText(),"Roles","Page title is correct");
        BrowserUtils.waitForClickablility(rolesPage.roles.get(0), 20);
        String roleName = rolesPage.roles.get(0).getText();
        System.out.println("roleName = " + roleName);
        BrowserUtils.waitForClickablility(rolesPage.roles.get(0),10).click();
        System.out.println("Role is selected");
        EMCRoleDetailsPage detailsPage = new EMCRoleDetailsPage();
        assertTestCase.assertEquals(detailsPage.pageTitle.getText(),roleName,"Role Details Page title is correct");
        assertTestCase.assertTrue(detailsPage.generalInfoTag.isDisplayed(),"Details Tab - General Info tag is displayed");
        BrowserUtils.waitForClickablility(detailsPage.permissionsTab, 5).click();
        assertTestCase.assertTrue(detailsPage.rolePermissionsTag.isDisplayed(),"Permissions Tab - Role Permissions tag is displayed");
        assertTestCase.assertTrue(detailsPage.permissionNamesList.size()>0,"Permissions Tab - List of Permissions is displayed");
        BrowserUtils.waitForClickablility(detailsPage.usersTab, 5).click();
        assertTestCase.assertTrue(detailsPage.roleMembersTag.isDisplayed(),"Users Tab - Role members tag is displayed");
    }

    @Test(groups = {EMC, UI,REGRESSION})//no smoke
    @Xray(test = {6667,6669})
    public void configurationPageAdminCreateRoleTest() {
        navigateToConfigPage("permission roles");
        EMCRolesPage rolesPage = new EMCRolesPage();
        //Click on the Create Role button
        BrowserUtils.waitForVisibility(rolesPage.createRoleButton, 15);
        assertTestCase.assertTrue(rolesPage.createRoleButton.isDisplayed(), "Create Role button is displayed");
        BrowserUtils.waitForClickablility(rolesPage.createRoleButton, 5).click();

        //The Role creation modal is opened
        EMCCreateRolePage createPage = new EMCCreateRolePage();
        //EMCRoleDetailsPage detailsPage = new EMCRoleDetailsPage();
        assertTestCase.assertEquals(createPage.pageTitle.getText(),"Create Role", "Create Role page loaded");
        assertTestCase.assertTrue(createPage.generalInfoTag.isDisplayed(),"Create Role Page - General Info tag is displayed");

        //Fill the Key mandatory field (leave Name empty), fill the optional fields (Description and Permissions) and click on Save
        String roleName = "qatestrole"+new Faker().food().fruit().replaceAll("\\W","").toLowerCase();
        System.out.println("roleName = " + roleName);
        createPage.keyInput.sendKeys(roleName);
        createPage.descriptionInput.sendKeys("This is a test role");
        assertTestCase.assertTrue(createPage.permissionsLabel.isDisplayed(),"Create Role Page - Permissions label is displayed");
        assertTestCase.assertTrue(createPage.permissionsInput.isDisplayed(),"Create Role Page - Permissions input is displayed");
        createPage.assignPermission("view-users");
        System.out.println("Selected Permissions = " + createPage.permissionsInput.getText());
        assertTestCase.assertTrue(createPage.permissionsInput.getText().contains("view-users"),"Create Role Page - Users tag is displayed");
        clear(createPage.nameInput);
        assertTestCase.assertFalse(createPage.saveButton.isEnabled(),"Create Role Page - Save button is enabled");
        assertTestCase.assertTrue(createPage.backButton.isEnabled(),"Create Role Page - Back button is enabled");
        assertTestCase.assertTrue(createPage.nameIsRequiredError.isDisplayed(),"Create Role Page - Name is required error is displayed");

        //Fill the Name mandatory field (leave Key empty), fill the optional fields (Description and Permissions) and click on Save
        clear(createPage.keyInput);
        createPage.nameInput.sendKeys(roleName);
        assertTestCase.assertFalse(createPage.saveButton.isEnabled(),"Create Role Page - Save button is enabled");
        assertTestCase.assertTrue(createPage.backButton.isEnabled(),"Create Role Page - Back button is enabled");
        assertTestCase.assertTrue(createPage.keyIsRequiredError.isDisplayed(),"Create Role Page - Key is required error is displayed");

        //Fill the mandatory fields (Key and Name), the optional fields (Description and Permissions) and click on Save
        createPage.keyInput.sendKeys(roleName);
        assertTestCase.assertFalse(createPage.nameInput.getAttribute("value").isEmpty(),"Create Role Page - Name info is filled");
        assertTestCase.assertFalse(createPage.keyInput.getAttribute("value").isEmpty(),"Create Role Page - Key info is filled");
        //assertTestCase.assertFalse(createPage.nameIsRequiredError.isDisplayed(),"Create Role Page - Name is required error is not displayed");
        //assertTestCase.assertFalse(createPage.keyIsRequiredError.isDisplayed(),"Create Role Page - Key is required error is not displayed");
        assertTestCase.assertTrue(createPage.saveButton.isEnabled(),"Create Role Page - Save button is enabled");
        assertTestCase.assertTrue(createPage.backButton.isEnabled(),"Create Role Page - Back button is enabled");
        BrowserUtils.waitForClickablility(createPage.saveButton, 5).click();

        //The new Role is created and the same is now visible on the Role list
        BrowserUtils.waitForVisibility(rolesPage.notification, 15);
        assertTestCase.assertTrue(rolesPage.notification.isDisplayed(), "Role created message is displayed");
        assertTestCase.assertTrue(rolesPage.verifyRole(roleName),"New role is creation is verified");
    }

    @Test(groups = {EMC, UI,REGRESSION,SMOKE,PROD})
    @Xray(test = {7818})
    public void verifyAdminUserManagesConfigPageTest() {
        EMCMainPage mainPage = new EMCMainPage();
        mainPage.openSidePanel();
        mainPage.clickConfigurationsButton();
        EMCConfigurationsPage configurationsPage = new EMCConfigurationsPage();

        assertTestCase.assertTrue(configurationsPage.sitesSettingsTag.isDisplayed(), "Site Settings tag is displayed");
        assertTestCase.assertTrue(configurationsPage.miscellaneousTag.isDisplayed(), "Miscellaneous tag is displayed");
        assertTestCase.assertTrue(configurationsPage.usersButton.isDisplayed(), "Users Button is displayed under user settings");
        assertTestCase.assertTrue(configurationsPage.permissionRolesButton.isDisplayed(),"Permission Roles button is displayed under user settings");
        assertTestCase.assertTrue(configurationsPage.sitesSettingsButton.isDisplayed(), "Site Settings button is displayed under Miscellaneous settings");
    }

    @Test(groups = {EMC, UI,REGRESSION})
    @Xray(test = {7827,7828})
    public void verifyAdminUserAddMemberToRoleTest() {
        navigateToConfigPage("permission roles");
        EMCRolesPage rolesPage = new EMCRolesPage();
        assertTestCase.assertTrue(rolesPage.verifyRole(roleName), "TestRole are located in the page");
        rolesPage.selectRole(roleName);
        EMCRoleDetailsPage detailsPage = new EMCRoleDetailsPage();
        assertTestCase.assertEquals(detailsPage.pageTitle.getText(),roleName, roleName+" page loaded");
        detailsPage.clickOnUsersTab();
        assertTestCase.assertTrue(detailsPage.roleMembersTag.isDisplayed(),"Users Tab - Role members tag is displayed");

        if(detailsPage.verifyRoleMember(userName)) {
            System.out.println("User is already a member of the role");
            detailsPage.removeMemberFromRole(userName);
        }

        detailsPage.addUserToRole(userName);
        //assertTestCase.assertTrue(detailsPage.notification.isDisplayed(), "Users assigned message is displayed");
        assertTestCase.assertTrue(detailsPage.verifyRoleMember(userName), "User is is verified among current role members");

        detailsPage.removeMemberFromRole(userName);
        assertTestCase.assertTrue(detailsPage.notification.isDisplayed(), "Users removed message is displayed");
        detailsPage.clickOnDetailsTab();
        BrowserUtils.wait(2);
        detailsPage.clickOnUsersTab();
        assertTestCase.assertFalse(detailsPage.verifyRoleMember(userName), "Member is removed from role");
    }

    @Test(groups = {EMC, UI,REGRESSION})
    @Xray(test = {8137,8140,8143})
    public void verifyAdminUserViewRoleDetailsTest() {
        navigateToConfigPage("permission roles");
        EMCRolesPage rolesPage = new EMCRolesPage();
        assertTestCase.assertTrue(rolesPage.verifyRole(roleName), "TestRole are located in the page");
        rolesPage.selectRole(roleName);
        EMCRoleDetailsPage detailsPage = new EMCRoleDetailsPage();
        assertTestCase.assertEquals(detailsPage.pageTitle.getText(),roleName, roleName+" page loaded");
        assertTestCase.assertTrue(detailsPage.generalInfoTag.isDisplayed(),"Details Tab - General Info tag is displayed");
        assertTestCase.assertFalse(detailsPage.keyInput.getAttribute("value").isEmpty(),"Details Tab - key input is verified");
        assertTestCase.assertFalse(detailsPage.nameInput.getAttribute("value").isEmpty(),"Details Tab - name input is verified");
        assertTestCase.assertFalse(detailsPage.descriptionInput.getAttribute("value").isEmpty(),"Details Tab - description input is verified");
        assertTestCase.assertTrue(detailsPage.createdByInfo.isDisplayed(),"Details Tab - Created By info is displayed");
        assertTestCase.assertTrue(detailsPage.modifiedByInfo.isDisplayed(),"Details Tab - Modified By info is displayed");
        assertTestCase.assertTrue(detailsPage.editButton.isEnabled(),"Details Tab - Edit button is enabled");
        assertTestCase.assertTrue(detailsPage.permissionsTab.isDisplayed(),"Details Tab - Permissions tab is displayed");
        assertTestCase.assertTrue(detailsPage.usersTab.isDisplayed(),"Details Tab - Users tab is displayed");
        BrowserUtils.waitForClickablility(detailsPage.editButton, 5).click();
        assertTestCase.assertFalse(detailsPage.keyInput.isEnabled(),"Details Tab - key input is not enabled for editing");
        assertTestCase.assertTrue(detailsPage.nameInput.isEnabled(),"Details Tab - name input is enabled for editing");
        assertTestCase.assertTrue(detailsPage.descriptionInput.isEnabled(),"Details Tab - description input is enabled for editing");
        assertTestCase.assertTrue(detailsPage.saveButton.isDisplayed(),"Details Tab - Save button is displayed");
        assertTestCase.assertTrue(detailsPage.cancelButton.isDisplayed(),"Details Tab - Cancel button is displayed");
        String initialName = detailsPage.nameInput.getAttribute("value");
        String initialDescription = detailsPage.descriptionInput.getAttribute("value");
        detailsPage.nameInput.sendKeys("Edited");
        detailsPage.descriptionInput.sendKeys("Edited");
        assertTestCase.assertEquals(detailsPage.nameInput.getAttribute("value"),initialName+"Edited","Details Tab - Name input is edited");
        assertTestCase.assertEquals(detailsPage.descriptionInput.getAttribute("value"),initialDescription+"Edited","Details Tab - Description input is edited");
        BrowserUtils.waitForClickablility(detailsPage.cancelButton,5).click();
        assertTestCase.assertEquals(detailsPage.nameInput.getAttribute("value"),initialName,"Details Tab - Name input is not saved");
        assertTestCase.assertEquals(detailsPage.descriptionInput.getAttribute("value"),initialDescription,"Details Tab - Description input is not saved");
        assertTestCase.assertTrue(detailsPage.backToRolesButton.isDisplayed(), "Details Tab - Back to Roles button is displayed");
        BrowserUtils.waitForClickablility(detailsPage.backToRolesButton,5).click();
    }

    @Test(groups = {EMC, UI,REGRESSION})
    @Xray(test = {6668, 6635})
    public void verifyAdminUserCreateRoleWithoutOptionalFieldsTest() {
        navigateToConfigPage("permission roles");
        EMCRolesPage rolesPage = new EMCRolesPage();
        //Click on the Create Role button
        BrowserUtils.waitForVisibility(rolesPage.createRoleButton, 15);
        assertTestCase.assertTrue(rolesPage.createRoleButton.isDisplayed(), "Create Role button is displayed");
        BrowserUtils.waitForClickablility(rolesPage.createRoleButton, 5).click();

        //The Role creation modal is opened
        EMCCreateRolePage createRolePage = new EMCCreateRolePage();
        assertTestCase.assertEquals(createRolePage.pageTitle.getText(),"Create Role", "Create Role page loaded");
        assertTestCase.assertTrue(createRolePage.generalInfoTag.isDisplayed(),"Create Role Page - General Info tag is displayed");

        //Fill the mandatory fields (Key and Name), DO NOT fill the optional fields (Description and Permissions) and click on Save
        String roleName = "qatestrole"+faker.number().digits(6);
        System.out.println("roleName = " + roleName);
        createRolePage.keyInput.sendKeys(roleName);
        createRolePage.nameInput.sendKeys(roleName);
        assertTestCase.assertTrue(createRolePage.descriptionInput.getAttribute("value").isEmpty(),"Create Role Page - description input is verified");
        assertTestCase.assertTrue(createRolePage.permissionsLabel.isDisplayed(),"Create Role Page - Permissions label is displayed");
        assertTestCase.assertTrue(createRolePage.permissionsInput.isDisplayed(),"Create Role Page - Permissions input is displayed");
        assertTestCase.assertTrue(createRolePage.permissionsInput.getText().isEmpty(),"Create Role Page - Permissions input is verified");
        assertTestCase.assertTrue(createRolePage.saveButton.isEnabled(),"Create Role Page - Save button is enabled");
        assertTestCase.assertTrue(createRolePage.backButton.isEnabled(),"Create Role Page - Back button is enabled");
        BrowserUtils.waitForClickablility(createRolePage.saveButton, 5).click();

        //The new Role is created and the same is now visible on the Role list
        BrowserUtils.waitForVisibility(rolesPage.notification, 15);
        assertTestCase.assertTrue(rolesPage.notification.isDisplayed(), "Role created message is displayed");
        BrowserUtils.waitForClickablility(rolesPage.createRoleButton, 5).click();
        BrowserUtils.waitForClickablility(createRolePage.backButton, 5).click();
        assertTestCase.assertTrue(rolesPage.verifyRole(roleName),"New role is creation is verified");
    }

    @Test(groups = {EMC, UI,REGRESSION})
    @Xray(test = {6670})
    public void verifyAdminUserCreateRoleCancelButtonTest() {
        navigateToConfigPage("permission roles");
        EMCRolesPage rolesPage = new EMCRolesPage();
        //Click on the Create Role button
        BrowserUtils.waitForVisibility(rolesPage.createRoleButton, 15);
        assertTestCase.assertTrue(rolesPage.createRoleButton.isDisplayed(), "Create Role button is displayed");
        BrowserUtils.waitForClickablility(rolesPage.createRoleButton, 5).click();

        //The Role creation modal is opened
        EMCCreateRolePage createRolePage = new EMCCreateRolePage();
        assertTestCase.assertEquals(createRolePage.pageTitle.getText(),"Create Role", "Create Role page loaded");
        assertTestCase.assertTrue(createRolePage.generalInfoTag.isDisplayed(),"Create Role Page - General Info tag is displayed");

        //Fill the mandatory fields (Key and Name), DO NOT fill the optional fields (Description and Permissions) and click on Save
        String roleName = "qatestrole"+ java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd-hhmmss"));
        System.out.println("roleName = " + roleName);
        createRolePage.keyInput.sendKeys(roleName);
        createRolePage.nameInput.sendKeys(roleName);
        createRolePage.descriptionInput.sendKeys("qatestdescription");
        createRolePage.assignPermission("view-users");
        assertTestCase.assertTrue(createRolePage.saveButton.isEnabled(),"Create Role Page - Save button is enabled");
        assertTestCase.assertTrue(createRolePage.backButton.isEnabled(),"Create Role Page - Back button is enabled");
        BrowserUtils.waitForClickablility(createRolePage.backButton, 5).click();

        //The new Role is created and the same is now visible on the Role list
        assertTestCase.assertTrue(rolesPage.pageTitle.isDisplayed(), "Roles Page is displayed");
        assertTestCase.assertFalse(rolesPage.verifyRole(roleName),"New role creation is not verified");
    }

    @Test(groups = {EMC, UI,REGRESSION,SMOKE, PROD})
    @Xray(test = {6671})
    public void verifyAdminUserCreateRoleKeyInputMustBeAValidCharTest() {
        navigateToConfigPage("permission roles");
        EMCRolesPage rolesPage = new EMCRolesPage();
        //Click on the Create Role button
        BrowserUtils.waitForVisibility(rolesPage.createRoleButton, 15);
        assertTestCase.assertTrue(rolesPage.createRoleButton.isDisplayed(), "Create Role button is displayed");
        BrowserUtils.waitForClickablility(rolesPage.createRoleButton, 5).click();

        //The Role creation modal is opened
        EMCCreateRolePage createRolePage = new EMCCreateRolePage();
        assertTestCase.assertEquals(createRolePage.pageTitle.getText(),"Create Role", "Create Role page loaded");
        assertTestCase.assertTrue(createRolePage.generalInfoTag.isDisplayed(),"Create Role Page - General Info tag is displayed");

        //Review the allowed keys to be entered on the Key field
        //The format for the Key field is "{a-z}-{0-9}" (alphanumeric), if unwanted characters are introduced the user receives a notification
        char c = '!';
        while(c<'0'){
            createRolePage.keyInput.sendKeys(c+"",Keys.TAB);
            assertTestCase.assertTrue(createRolePage.mustBeAValidKeyError.isDisplayed(),c+" is not allowed for key field");
            clear(createRolePage.keyInput);
            c++;
        }
        c = ':';
        while(c<'A'){
            createRolePage.keyInput.sendKeys(c+"",Keys.TAB);
            assertTestCase.assertTrue(createRolePage.mustBeAValidKeyError.isDisplayed(),c+" is not allowed for key field");
            clear(createRolePage.keyInput);
            c++;
        }
        c = '{';
        while(c<='~'){
            createRolePage.keyInput.sendKeys(c+"",Keys.TAB);
            assertTestCase.assertTrue(createRolePage.mustBeAValidKeyError.isDisplayed(),c+" is not allowed for key field");
            clear(createRolePage.keyInput);
            c++;
        }
        BrowserUtils.waitForClickablility(createRolePage.backButton, 5).click();
    }

    @Test(groups = {EMC, UI,REGRESSION,SMOKE})
    @Xray(test = {6672})
    public void verifyAdminUserCreateRoleDuplicateKeyNotAllowedTest() {
        navigateToConfigPage("permission roles");
        EMCRolesPage rolesPage = new EMCRolesPage();
        //Get the first role key from the list
        BrowserUtils.waitForVisibility(rolesPage.roles.get(0), 15).click();
        EMCRoleDetailsPage roleDetailsPage = new EMCRoleDetailsPage();
        String key = roleDetailsPage.keyInput.getAttribute("value");
        System.out.println("key = " + key);
        BrowserUtils.waitForVisibility(roleDetailsPage.backToRolesButton, 15).click();

        //Click on the Create Role button
        BrowserUtils.waitForVisibility(rolesPage.createRoleButton, 15);
        assertTestCase.assertTrue(rolesPage.createRoleButton.isDisplayed(), "Create Role button is displayed");
        BrowserUtils.waitForClickablility(rolesPage.createRoleButton, 5).click();

        //The Role creation modal is opened
        EMCCreateRolePage createRolePage = new EMCCreateRolePage();
        assertTestCase.assertEquals(createRolePage.pageTitle.getText(),"Create Role", "Create Role page loaded");
        assertTestCase.assertTrue(createRolePage.generalInfoTag.isDisplayed(),"Create Role Page - General Info tag is displayed");

        //Enter a duplicated key (already present on another Role) on the Key field
        createRolePage.keyInput.sendKeys(key);
        createRolePage.nameInput.sendKeys("qatestrole");
        assertTestCase.assertTrue(createRolePage.saveButton.isEnabled(),"Create Role Page - Save button is enabled");
        assertTestCase.assertTrue(createRolePage.backButton.isEnabled(),"Create Role Page - Back button is enabled");
        BrowserUtils.waitForClickablility(createRolePage.saveButton, 5).click();
        //The user receives a notification regarding the duplicated key and the creation process cannot continue (keys are unique within OKTA)
        BrowserUtils.waitForVisibility(createRolePage.notification, 15);
        assertTestCase.assertTrue(createRolePage.notification.isDisplayed(),"Duplicated key error is displayed");
        String message = createRolePage.notification.getText();
        assertTestCase.assertTrue(message.contains("Failed creating the role. Please try again."),"Duplicated key error is displayed");
    }

    @Test(groups = {EMC, UI,REGRESSION,SMOKE})
    @Xray(test = {6949, 6950})
    public void verifyAdminUserAssignPermissionsTest() {
        navigateToConfigPage("permission roles");
        EMCRolesPage rolesPage = new EMCRolesPage();
        assertTestCase.assertTrue(rolesPage.verifyRole(roleName), "TestRole are located in the page");
        rolesPage.selectRole(roleName);
        EMCRoleDetailsPage detailsPage = new EMCRoleDetailsPage();
        BrowserUtils.waitForClickablility(detailsPage.permissionsTab, 5).click();
        assertTestCase.assertTrue(detailsPage.assignPermissionsButton.isDisplayed(),"Permissions Tab - Assign Permissions button is displayed");
        if (detailsPage.verifyPermission("Search account users")){
            System.out.println("Permission is already assigned to the role. Deleting it...");
            detailsPage.deletePermission("Search account users");
        }
        detailsPage.assignPermission("Search account users");
        assertTestCase.assertTrue(detailsPage.verifyPermission("Search account users"),"Permission is assigned to the role");
        detailsPage.deletePermission("Search account users");
        assertTestCase.assertFalse(detailsPage.verifyPermission("Search account users"),"Permission is not assigned to the role");
    }

    @Test(groups = {EMC, UI,REGRESSION}, description = "UI | EMC | Roles | Verify User with Admin Role can view Accounts menu on EMC")
    @Xray(test = {7314, 7435, 7554, 7555, 7597, 7598})
    public void verifyUserWithAdminRoleCanViewAccountsMenuTest() {
        navigateToConfigPage("permission roles");
        EMCRolesPage rolesPage = new EMCRolesPage();

        assertTestCase.assertTrue(rolesPage.verifyRole("Administrator"), "TestRole are located in the page");
        rolesPage.selectRole("Administrator");
        EMCRoleDetailsPage detailsPage = new EMCRoleDetailsPage();
        BrowserUtils.waitForClickablility(detailsPage.usersTab, 5).click();
        if(!detailsPage.verifyUser("Ferhat Demir")){
            System.out.println("User is not assigned to the role. Assigning it...");
            detailsPage.assignMember("Ferhat Demir");
        }
        assertTestCase.assertTrue(detailsPage.verifyUser("Ferhat Demir"),"User is assigned to the role");

        //Login with Test User
        Driver.quit();
        Driver.getDriver().get(Environment.EMC_URL);
        BrowserUtils.waitForPageToLoad(10);
        LoginPageEMC loginPageEMC = new LoginPageEMC();
        loginPageEMC.loginEMCWithParams("ferhat.demir-non-empl@moodys.com", "Apple@2023??");

        //Go to Accounts page and open account
        EMCMainPage homePage = new EMCMainPage();
        homePage.goToAccountsPage();
        EMCAccountsPage accountsPage = new EMCAccountsPage();
        BrowserUtils.wait(5);
        assertTestCase.assertEquals(accountsPage.pageTitle.getText(), "Accounts", "Accounts page is displayed");
        System.out.println("Accounts page is displayed");
        assertTestCase.assertEquals(accountsPage.accountNames.size() > 0, true, "Account is displayed");
        accountsPage.goToAccount(accountName);

        //verify details tab
        EMCAccountDetailsPage accountDetailsPage = new EMCAccountDetailsPage();
        BrowserUtils.waitForVisibility(accountDetailsPage.editButton,20);
        assertTestCase.assertTrue(accountDetailsPage.editButton.isEnabled(), "Users tab is verified for Admin Role");
        BrowserUtils.waitForClickablility(accountDetailsPage.editButton, 5).click();
        assertTestCase.assertTrue(accountDetailsPage.accountNameInput.isEnabled(), "Admin User can edit fields");
        assertTestCase.assertTrue(accountDetailsPage.saveButton.isEnabled(), "Save button is verified for Admin Role");
        assertTestCase.assertTrue(accountDetailsPage.cancelButton.isEnabled(), "Cancel button is verified for Admin Role");
        accountDetailsPage.cancelButton.click();
        //verify applications tab
        accountDetailsPage.clickOnApplicationsTab();
        BrowserUtils.wait(5);
        assertTestCase.assertTrue(accountDetailsPage.assignApplicationsButton.isEnabled(), "Applications tab is verified for Admin Role");
        if(accountDetailsPage.verifyApplication(applicationName)) accountDetailsPage.deleteApplication(applicationName);
        assertTestCase.assertTrue(accountDetailsPage.assignApplication(applicationName), "Assigning application is verified for Admin Role");
        assertTestCase.assertTrue(accountDetailsPage.verifyApplication(applicationName), "Application is assigned to the account");
        assertTestCase.assertTrue(accountDetailsPage.deleteApplication(applicationName), "Deleting application is verified for Admin Role");
        assertTestCase.assertFalse(accountDetailsPage.verifyApplication(applicationName), "Application is not assigned to the account");
        accountDetailsPage.assignApplication(applicationName);
        //verify products tab
        accountDetailsPage.clickOnProductsTab();
        BrowserUtils.wait(3);
        assertTestCase.assertTrue(accountDetailsPage.assignProductsButton.isEnabled(), "Products tab is verified for Admin Role");
        BrowserUtils.waitForClickablility(accountDetailsPage.currentProductsList.get(0), 5).click();
        wait(accountDetailsPage.currentProductFeaturesList,250);
        assertTestCase.assertTrue(accountDetailsPage.currentProductFeaturesList.size()>0, "Products and Features List is verified for Admin Role");
        assertTestCase.assertTrue(accountDetailsPage.currentProductFeaturesDeleteButtons.size()>0, "Products and Features Delete Buttons List is verified for Admin Role");
        assertTestCase.assertTrue(accountDetailsPage.currentProductFeaturesDeleteButtons.get(0).isEnabled(), "Products and Features List Delete Buttons are enabled for Admin Role");

        //verify users tab
        accountDetailsPage.clickOnUsersTab();
        BrowserUtils.wait(3);
        assertTestCase.assertTrue(accountDetailsPage.addUserButton.isEnabled(), "Users tab is verified for Admin Role");
        accountDetailsPage.clickOnAddUserButton();
        String fname = new Faker().name().firstName();
        String lname = new Faker().name().lastName();
        assertTestCase.assertTrue(accountDetailsPage.createUser(fname,lname,new Faker().internet().emailAddress(),false), "User creation is verified for Admin Role");
        assertTestCase.assertTrue(accountDetailsPage.verifyUser(fname+" "+lname), "New user is verified for Admin Role");
        assertTestCase.assertTrue(accountDetailsPage.deleteUser(fname+" "+lname), "User deletion is verified for Admin Role");
        assertTestCase.assertFalse(accountDetailsPage.verifyUser(fname+" "+lname), "New user deletion is verified for Admin Role");

        //Go back to roles page and reset settings for the test users
        Driver.closeDriver();
        Driver.getDriver().get(Environment.EMC_URL);
        loginPageEMC = new LoginPageEMC();
        BrowserUtils.waitForVisibility(loginPageEMC.usernameBox, 25);
        loginPageEMC.loginWithInternalUser();
        navigateToConfigPage("permission roles");

        rolesPage = new EMCRolesPage();
        while(!rolesPage.verifyRole("Administrator")){BrowserUtils.wait(1);}
        rolesPage.selectRole("Administrator");
        detailsPage = new EMCRoleDetailsPage();
        BrowserUtils.waitForClickablility(detailsPage.usersTab, 25).click();
        while(!detailsPage.verifyUser("Ferhat Demir")){BrowserUtils.wait(1);}
        detailsPage.deleteMember("Ferhat Demir");
        BrowserUtils.waitForVisibility(detailsPage.notification, 15).click();
        assertTestCase.assertTrue(detailsPage.notification.isDisplayed(),"User unassigned from the role notification is displayed");
        assertTestCase.assertFalse(detailsPage.verifyUser("Ferhat Demir"),"User is not assigned to the role");
    }

    @Test(groups = {EMC, UI,REGRESSION}, description = "UI | EMC | Roles | Verify non-Admin User and can't view Accounts menu")
    @Xray(test = {7335})
    public void verifyUserWithNonAdminRoleCantViewAccountsMenuTest() {
        navigateToConfigPage("permission roles");
        EMCRolesPage rolesPage = new EMCRolesPage();
        while(!rolesPage.verifyRole("Administrator")){BrowserUtils.wait(1);}
        assertTestCase.assertTrue(rolesPage.verifyRole("Administrator"), "TestRole are located in the page");
        rolesPage.selectRole("Administrator");
        EMCRoleDetailsPage detailsPage = new EMCRoleDetailsPage();
        BrowserUtils.waitForClickablility(detailsPage.usersTab, 5).click();
        if(detailsPage.verifyUser("Ferhat Demir")){
            System.out.println("User is assigned to the role. Deleting it...");
            detailsPage.deleteMember("Ferhat Demir");
            BrowserUtils.waitForVisibility(detailsPage.notification, 5).click();
            assertTestCase.assertTrue(detailsPage.notification.isDisplayed(),"User unassigned from the role notification is displayed");
        }
        assertTestCase.assertFalse(detailsPage.verifyUser("Ferhat Demir"),"User is not assigned to the role");
        Driver.closeDriver();
        Driver.getDriver().get(Environment.EMC_URL);
        BrowserUtils.waitForPageToLoad(10);
        LoginPageEMC loginPageEMC = new LoginPageEMC();
        loginPageEMC.loginEMCWithParams("ferhat.demir-non-empl@moodys.com", "Apple@2023??");
        EMCMainPage homePage = new EMCMainPage();

        try {
            homePage.goToAccountsPage();
            EMCAccountsPage accountsPage = new EMCAccountsPage();
            BrowserUtils.wait(5);
            assertTestCase.assertEquals(accountsPage.pageTitle.getText(), "Accounts", "Accounts page is displayed");
            System.out.println("Accounts page is displayed");
            //Search for the account where a New user will be created
            assertTestCase.assertEquals(accountsPage.accountNames.size() > 0, true, "Account is displayed");

            //On Account Details view, select USERS tab
            accountsPage.goToAccount(accountName);
            EMCAccountDetailsPage accountDetailsPage = new EMCAccountDetailsPage();
            accountDetailsPage.wait(accountDetailsPage.editButton,10);
            assertTestCase.assertFalse(accountDetailsPage.editButton.isDisplayed(), "Users tab is displayed");
        } catch (Exception e) {
            assertTestCase.assertTrue(true, "Accounts button is not displayed");
        }

        Driver.closeDriver();
        Driver.getDriver().get(Environment.EMC_URL);
        BrowserUtils.waitForPageToLoad(10);
        loginPageEMC = new LoginPageEMC();
        loginPageEMC.loginWithInternalUser();
        BrowserUtils.wait(5);
        Driver.getDriver().manage().window().maximize();
    }

    @Test(groups = {EMC, UI,REGRESSION}, description = "UI | EMC | Roles | Verify User with View Role can view Accounts menu on EMC")
    @Xray(test = {7434, 7336, 7338, 7552, 7599, 7389})
    public void verifyUserWithViewerRoleCanViewAccountsMenuTest() {
        navigateToConfigPage("permission roles");
        EMCRolesPage rolesPage = new EMCRolesPage();
        while(!rolesPage.verifyRole("Viewer")){BrowserUtils.wait(1);}
        assertTestCase.assertTrue(rolesPage.verifyRole("Viewer"), "TestRole are located in the page");
        rolesPage.selectRole("Viewer");
        EMCRoleDetailsPage detailsPage = new EMCRoleDetailsPage();
        BrowserUtils.waitForClickablility(detailsPage.usersTab, 5).click();
        if(!detailsPage.verifyUser("Ferhat Demir")){
            System.out.println("User is not assigned to the role. Assigning it...");
            detailsPage.assignMember("Ferhat Demir");
        }
        assertTestCase.assertTrue(detailsPage.verifyUser("Ferhat Demir"),"User is assigned to the role");
        Driver.quit();
        Driver.getDriver().get(Environment.EMC_URL);
        BrowserUtils.waitForPageToLoad(10);
        LoginPageEMC loginPageEMC = new LoginPageEMC();
        loginPageEMC.loginEMCWithParams("ferhat.demir-non-empl@moodys.com", "Apple@2023??");
        EMCMainPage homePage = new EMCMainPage();
        homePage.goToAccountsPage();
        EMCAccountsPage accountsPage = new EMCAccountsPage();
        BrowserUtils.waitForVisibility(accountsPage.pageTitle, 20);
        assertTestCase.assertEquals(accountsPage.pageTitle.getText(), "Accounts", "Accounts page is displayed");
        BrowserUtils.wait(10);
        assertTestCase.assertEquals(accountsPage.accountNames.size() > 0, true, "Account is displayed");

        accountsPage.goToAccount(accountName);
        EMCAccountDetailsPage accountDetailsPage = new EMCAccountDetailsPage();
        BrowserUtils.wait(5);
        assertTestCase.assertTrue(accountDetailsPage.usersTab.isDisplayed(), "Users tab is displayed");
        assertTestCase.assertTrue(accountDetailsPage.applicationsTab.isDisplayed(), "Applications tab is enabled");
        assertTestCase.assertTrue(accountDetailsPage.productsTab.isDisplayed(), "Products tab is enabled");
        assertTestCase.assertTrue(accountDetailsPage.accountNameInput.isDisplayed(), "Account Name is displayed");

        //verify details tab
        try{
            assertTestCase.assertFalse(accountDetailsPage.editButton.isDisplayed(), "Users tab is displayed");
        } catch (Exception e){
            assertTestCase.assertTrue(true, "edit button is not displayed for viewer users");
        }
        //verify applications tab
        try{
            accountDetailsPage.clickOnApplicationsTab();
            BrowserUtils.waitForVisibility(accountDetailsPage.applicationsNamesList.get(0), 15);
            assertTestCase.assertFalse(accountDetailsPage.assignApplicationsButton.isDisplayed(), "Add Application button shouldn't displayed");
        } catch (Exception e){
            assertTestCase.assertTrue(true, "Assign Application button is not displayed");
        }
        //verify products tab
        try{
            accountDetailsPage.clickOnProductsTab();
            BrowserUtils.waitForVisibility(accountDetailsPage.currentProductsList.get(0), 15);
            assertTestCase.assertFalse(accountDetailsPage.assignProductsButton.isDisplayed(), "Add Product button shouldn't displayed");
        } catch (Exception e){
            assertTestCase.assertTrue(true, "Assign Product button is not displayed");
        }
        //verify users tab
        try{
            accountDetailsPage.clickOnUsersTab();
            BrowserUtils.waitForVisibility(accountDetailsPage.userNamesList.get(0), 15);
            assertTestCase.assertFalse(accountDetailsPage.addUserButton.isDisplayed(), "Add User button shouldn't displayed");
        } catch (Exception e){
            assertTestCase.assertTrue(true, "Assign User button is not displayed");
        }
        try{
            accountDetailsPage.wait(accountDetailsPage.userNamesList.get(0),20);
            assertTestCase.assertTrue(accountDetailsPage.userNamesList.size()>0, "Users List is enabled for Admin Role");
            BrowserUtils.waitForClickablility(accountDetailsPage.userCheckboxList.get(0),5).click();
            assertTestCase.assertFalse(accountDetailsPage.deleteButton.isEnabled(), "Users Delete Button is not enabled for Admin Role");
        } catch (Exception e){
            assertTestCase.assertTrue(true, "Delete User button is not displayed");
        }

        //Go back to Admin user and reset settings for the test user
        Driver.closeDriver();
        Driver.getDriver().get(Environment.EMC_URL);
        BrowserUtils.waitForPageToLoad(10);
        loginPageEMC = new LoginPageEMC();
        loginPageEMC.loginWithInternalUser();
        navigateToConfigPage("permission roles");
        BrowserUtils.wait( 5);

        rolesPage = new EMCRolesPage();
        assertTestCase.assertTrue(rolesPage.verifyRole("Viewer"), "TestRole are located in the page");
        rolesPage.selectRole("Viewer");
        detailsPage = new EMCRoleDetailsPage();
        BrowserUtils.waitForClickablility(detailsPage.usersTab, 5).click();
        while(!detailsPage.verifyUser("Ferhat Demir")){BrowserUtils.wait(1);}
        detailsPage.deleteMember("Ferhat Demir");
        BrowserUtils.waitForVisibility(detailsPage.notification, 5).click();
        assertTestCase.assertTrue(detailsPage.notification.isDisplayed(),"User unassigned from the role notification is displayed");
        assertTestCase.assertFalse(detailsPage.verifyUser("Ferhat Demir"),"User is not assigned to the role");
    }
}
