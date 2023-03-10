package com.esgc.Tests;

import com.esgc.APIModels.EMCAPIController;
import com.esgc.Pages.*;
import com.esgc.TestBases.EMCUITestBase;
import com.esgc.Utilities.*;
import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.esgc.Utilities.Groups.*;

public class ApplicationPageTests extends EMCUITestBase {
    String applicationName = "TestQA";//singlePageApplication
    String webApplicationName = "QA Test Web Application";
    String externalApplicationName = "QA Test External Application";
    String testApplicationName = "TestQA2";
    String testWebAppName = "Test Web Application";
    Faker faker = new Faker();
    EMCApplicationsPage applicationsPage = new EMCApplicationsPage();


    public void navigateToApplicationsPage(String applicationName, String tabName) {
        EMCMainPage homePage = new EMCMainPage();
        homePage.goToApplicationsPage();
        applicationsPage = new EMCApplicationsPage();
        wait(applicationsPage.pageTitle, 20);
        assertTestCase.assertEquals(applicationsPage.pageTitle.getText(), "Applications", "Applications page is displayed");
        //Search for the account where a New user will be created
        if (!applicationName.isEmpty()) {
            assertTestCase.assertTrue(applicationsPage.verifyApplication(applicationName), "Application is displayed");

            //On Account Details view, select USERS tab
            applicationsPage.selectApplication(applicationName);
            //BrowserUtils.wait(10);
            EMCApplicationDetailsPage detailsPage = new EMCApplicationDetailsPage();
            switch (tabName.toLowerCase()) {
                case "details":
                    detailsPage.clickOnDetailsTab();
                    break;
                case "products":
                    detailsPage.clickOnProductsTab();
                    break;
                case "roles":
                    detailsPage.clickOnRolesTab();
                    break;
            }
        }
    }

    @Test(groups = {EMC, UI, SMOKE, REGRESSION, PROD})
    @Xray(test = {2330, 5101, 5103, 2153, 2154, 2155})
    public void applicationDetailsVerificationTests() {
        EMCMainPage emcMainPage = new EMCMainPage();
        //Navigate to EMC Application Panel
        emcMainPage.openSidePanel();
        //Click on Application
        emcMainPage.clickApplicationsButton();
        //Navigate to the Application Name on table.
        EMCApplicationsPage emcApplicationsPage = new EMCApplicationsPage();
        emcApplicationsPage.searchInput.sendKeys("TestQA");
        String expApplicationName = emcApplicationsPage.getApplicationNames().get(0);
        String expApplicationURL = emcApplicationsPage.applicationLinks.get(0).getText();
        //Click on Application Name
        emcApplicationsPage.selectApplication(expApplicationName);

        EMCApplicationDetailsPage emcApplicationDetailsPage = new EMCApplicationDetailsPage();
        //New screen should be opened having three sections when user click into a specific application
        assertTestCase.assertTrue(emcApplicationDetailsPage.detailsTab.isDisplayed(), "Details tab is displayed");
        assertTestCase.assertTrue(emcApplicationDetailsPage.ProductsTab.isDisplayed(), "Products tab is displayed");
        assertTestCase.assertTrue(emcApplicationDetailsPage.rolesTab.isDisplayed(), "Roles tab is displayed");
        //Verify that User is Able to See Application Details.
        emcApplicationDetailsPage.clickOnDetailsTab();
        assertTestCase.assertTrue(emcApplicationDetailsPage.ApplicationName.isDisplayed(), "Application Name is displayed");
        assertTestCase.assertTrue(emcApplicationDetailsPage.ApplicationURL.isDisplayed(), "Application URL is displayed");
        assertTestCase.assertTrue(emcApplicationDetailsPage.createdByText.isDisplayed(), "Created info is displayed");
        assertTestCase.assertTrue(emcApplicationDetailsPage.modifiedByText.isDisplayed(), "Modified info is displayed");
        String actApplicationName = emcApplicationDetailsPage.getApplicationName();
        String actApplicationURL = emcApplicationDetailsPage.getApplicationURL();
        assertTestCase.assertEquals(expApplicationName, actApplicationName, "Application name is verified");
        assertTestCase.assertEquals(expApplicationURL, actApplicationURL, "Application URL is verified");

        //Verify The Application's Details/Products/Roles page is displayed
        emcApplicationDetailsPage.clickOnProductsTab();
        assertTestCase.assertTrue(emcApplicationDetailsPage.addProductButton.isDisplayed(), "Products Tab is displayed");
        emcApplicationDetailsPage.clickOnRolesTab();
        assertTestCase.assertTrue(emcApplicationDetailsPage.addRoleButton.isDisplayed(), "Roles Tab is displayed");
        assertTestCase.assertTrue(emcApplicationDetailsPage.applicationRolesNames.size() > 0, "Application Roles Names are displayed");
        assertTestCase.assertTrue(emcApplicationDetailsPage.applicationRolesKeys.size() > 0, "Application Roles Keys are displayed");
        assertTestCase.assertEquals(emcApplicationDetailsPage.applicationRolesNames.size(), emcApplicationDetailsPage.applicationRolesKeys.size(),
                "Application Roles Names vs. Keys are verified");
        emcApplicationDetailsPage.backToApplicationsButton.click();
    }

    @Test(groups = {EMC, UI, REGRESSION})//no smoke
    @Xray(test = {2346, 2322, 2323})
    public void verifyUserEnterApplicationNameTest() {
        navigateToApplicationsPage(testApplicationName, "details");
        EMCApplicationDetailsPage detailsPage = new EMCApplicationDetailsPage();
        assertTestCase.assertTrue(detailsPage.ApplicationName.isDisplayed(), "Application Name is displayed");
        assertTestCase.assertTrue(detailsPage.editButton.isEnabled(), "Edit button is enabled");
        BrowserUtils.waitForClickablility(detailsPage.editButton, 5).click();
        assertTestCase.assertTrue(detailsPage.ApplicationName.isEnabled(), "Application Name is enabled");
        clear(detailsPage.ApplicationName);
        detailsPage.ApplicationName.sendKeys("TestQATest");
        BrowserUtils.waitForClickablility(detailsPage.saveButton, 5).click();
        String actApplicationNameAfterSave = detailsPage.ApplicationName.getAttribute("value");
        assertTestCase.assertEquals(actApplicationNameAfterSave, "TestQATest", "Application Name is verified");
        BrowserUtils.waitForClickablility(detailsPage.editButton, 5).click();
        clear(detailsPage.ApplicationName);
        detailsPage.ApplicationName.sendKeys(testApplicationName);
        BrowserUtils.waitForClickablility(detailsPage.saveButton, 5).click();
    }

    @Test(groups = {EMC, UI, REGRESSION, SMOKE})
    @Xray(test = {5173, 5174, 7657, 13413})
    public void verifyUserCreateNewRoleForApplicationTest() {
        List<String> applicationNames = Arrays.asList(applicationName, externalApplicationName, webApplicationName);
        for (String appType:applicationNames){
            System.out.println(appType);
            navigateToApplicationsPage(appType, "roles");
            EMCApplicationDetailsPage detailsPage = new EMCApplicationDetailsPage();
            assertTestCase.assertTrue(detailsPage.addRoleButton.isDisplayed(), "Application Name is displayed");
            BrowserUtils.waitForClickablility(detailsPage.addRoleButton, 5).click();
            BrowserUtils.waitForVisibility(detailsPage.addRolePopUpTitle, 5);
            assertTestCase.assertTrue(detailsPage.addRolePopUpTitle.isDisplayed(), "Add Role Pop Up is displayed");
            String roleName = "QATest"+faker.number().digits(5);
            detailsPage.roleNameInput.sendKeys(roleName);
            detailsPage.roleKeyInput.sendKeys(roleName.toLowerCase(), Keys.TAB);
            BrowserUtils.waitForClickablility(detailsPage.saveButton, 10).click();
            BrowserUtils.waitForVisibility(detailsPage.notification, 10);
            assertTestCase.assertTrue(detailsPage.getApplicationRolesNames().contains(roleName), "Role Name is displayed");
            detailsPage.selectRole(roleName);
            assertTestCase.assertTrue(detailsPage.verifyRoleDetails(), "Role Details are displayed");
        }
    }

    @Test(groups = {EMC, UI, REGRESSION, SMOKE, PROD})
    @Xray(test = {3720, 13449})
    public void verifyUserCancelCreateNewRoleForApplicationTest() {
        List<String> applicationNames = Arrays.asList(applicationName, externalApplicationName, webApplicationName);
        if(ConfigurationReader.getProperty("environment").equals("prod")){
            applicationNames = Arrays.asList(applicationName);
        }
        for (String appType:applicationNames){
            System.out.println(appType);
            navigateToApplicationsPage(appType, "roles");
            EMCApplicationDetailsPage detailsPage = new EMCApplicationDetailsPage();
            assertTestCase.assertTrue(detailsPage.addRoleButton.isDisplayed(), "Application Name is displayed");
            BrowserUtils.waitForClickablility(detailsPage.addRoleButton, 5).click();
            BrowserUtils.waitForVisibility(detailsPage.addRolePopUpTitle, 5);
            assertTestCase.assertTrue(detailsPage.addRolePopUpTitle.isDisplayed(), "Add Role Pop Up is displayed");
            String now = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("ddhhmmss"));
            detailsPage.roleNameInput.sendKeys("QATest" + now);
            detailsPage.roleKeyInput.sendKeys(now, Keys.TAB);
            assertTestCase.assertTrue(detailsPage.cancelButton.isEnabled(), "Cancel button is enabled");
            assertTestCase.assertTrue(detailsPage.saveButton.isEnabled(), "Save button is enabled");
            BrowserUtils.waitForClickablility(detailsPage.cancelButton, 10).click();
            assertTestCase.assertFalse(detailsPage.getApplicationRolesNames().contains("QATest" + now), "New role is not created");
        }

    }

    @Test(groups = {EMC, UI, REGRESSION})
    @Xray(test = {2312})
    public void verifyEMCApplicationsSortedAlphabeticallyByNameTest() {
        navigateToApplicationsPage("", "roles");

        List<String> applicationNames = applicationsPage.getApplicationNames();
        //convert to lower case and sort alphabetically
        applicationNames.sort(String::compareToIgnoreCase);
        assertTestCase.assertEquals(applicationNames, applicationsPage.getApplicationNames(), "Applications are sorted alphabetically");
    }

    @Test(groups = {EMC, UI, REGRESSION})
    @Xray(test = {2332})
    public void verifyApplicationURLClickableTest() {
        navigateToApplicationsPage("", "roles");

        applicationsPage.searchApplication(applicationName);
        String currentURL = Driver.getDriver().getCurrentUrl();
        String currentWindowsHandle = Driver.getDriver().getWindowHandle();
        BrowserUtils.waitForClickablility(applicationsPage.applicationLinks.get(0), 10).click();
        BrowserUtils.waitForPageToLoad(10);
        Driver.getDriver().switchTo().window(Driver.getDriver().getWindowHandles().toArray()[1].toString());
        String newURL = Driver.getDriver().getCurrentUrl();
        String newWindowsHandle = Driver.getDriver().getWindowHandle();
        assertTestCase.assertFalse(currentURL.equals(newURL), "Application URL is clickable");
        assertTestCase.assertNotEquals(currentWindowsHandle, newWindowsHandle, "Application URL is clickable");
        Driver.getDriver().close();
        Driver.getDriver().switchTo().window(currentWindowsHandle);
    }

    @Test(groups = {EMC, UI, REGRESSION})
    @Xray(test = {2333})
    public void verifyApplicationCreatedAndModifiedDetails() {
        navigateToApplicationsPage(applicationName, "details");
        EMCApplicationDetailsPage detailsPage = new EMCApplicationDetailsPage();
        String actApplicationName = detailsPage.getApplicationName();
        assertTestCase.assertEquals(actApplicationName, applicationName, "Application Name is verified");

        String actApplicationCreatedBy = detailsPage.createdByText.getText();
        assertTestCase.assertTrue(actApplicationCreatedBy.startsWith("Created by"), "Created By is verified");
        //verify that created by has an email address
        assertTestCase.assertTrue(actApplicationCreatedBy.contains("@"), "Created By has an email address");
        //verify that created by has a date
        assertTestCase.assertTrue(actApplicationCreatedBy.contains("/"), "Created By has a date");

        String actApplicationModifiedBy = detailsPage.modifiedByText.getText();
        assertTestCase.assertTrue(actApplicationModifiedBy.startsWith("Modified by"), "Modified By is verified");
        //verify that created by has an email address
        assertTestCase.assertTrue(actApplicationModifiedBy.contains("@"), "Modified By has an email address");
        //verify that created by has a date
        assertTestCase.assertTrue(actApplicationModifiedBy.contains("/"), "Modified By has a date");
    }

    @Test(groups = {EMC, UI, REGRESSION, SMOKE})
    @Xray(test = {2324, 2347, 2348, 2349, 5782, 5783, 7649, 12716, 12717, 13181, 13185})
    public void verifyInformationRequiredForCreatingApplicationFulfilledTest() {
        navigateToApplicationsPage("", "details");

        assertTestCase.assertTrue(applicationsPage.createApplicationButton.isDisplayed(), "Save Button is displayed");
        applicationsPage.clickOnCreateApplicationButton();
        EMCApplicationCreatePage createPage = new EMCApplicationCreatePage();
        assertTestCase.assertTrue(createPage.createNewApplicationPageTitle.isDisplayed(), "Create Application Page Title is displayed");
        assertTestCase.assertTrue(createPage.singlePageApplicationRadioButton.isEnabled(), "Single Page Application Radio Button is not enabled");
        assertTestCase.assertTrue(createPage.externalApplicationRadioButton.isEnabled(), "External Application Radio Button is not enabled");
        assertTestCase.assertTrue(createPage.webApplicationRadioButton.isEnabled(), "Web Application Radio Button is not enabled");
        assertTestCase.assertTrue(createPage.cancelButton.isEnabled(), "Cancel Button is enabled");
        assertTestCase.assertFalse(createPage.nextButton.isEnabled(), "Next Button is not enabled");
        BrowserUtils.doubleClick(createPage.webApplicationRadioButton);
        assertTestCase.assertTrue(createPage.webApplicationRadioButton.isSelected(), "External Application Radio Button is selected");
        assertTestCase.assertTrue(createPage.nextButton.isEnabled(), "Next Button is enabled");
        BrowserUtils.waitForClickablility(createPage.nextButton, 10).click();

        assertTestCase.assertTrue(createPage.pageTitle.isDisplayed(), "Create Application Page Title is displayed");
        assertTestCase.assertFalse(createPage.saveButton.isEnabled(), "Save Button is not enabled");

        //Check application name field
        createPage.applicationNameInput.sendKeys("Test", Keys.TAB);
        assertTestCase.assertTrue(createPage.mustBe5CharactersTag.isDisplayed(), "Must be 5 characters tag is displayed");
        createPage.applicationNameInput.sendKeys(" Test");
        //assertTestCase.assertFalse(createPage.mustBe5CharactersTag.isDisplayed(), "Must be 5 characters tag is not displayed");
        assertTestCase.assertFalse(createPage.saveButton.isEnabled(), "Save Button is not enabled");
        clear(createPage.applicationNameInput);
        assertTestCase.assertTrue(createPage.applicationNameRequiredTag.isDisplayed(), "key is a required field message is displayed");

        //check application key field
        createPage.applicationKeyInput.sendKeys("test");
        assertTestCase.assertFalse(createPage.saveButton.isEnabled(), "Save Button is not enabled");
        clear(createPage.applicationKeyInput);
        assertTestCase.assertTrue(createPage.keyRequiredTag.isDisplayed(), "key is a required field message is displayed");

        //check application url field
        createPage.applicationUrlInput.sendKeys("test application");
        assertTestCase.assertFalse(createPage.saveButton.isEnabled(), "Save Button is not enabled");
        clear(createPage.applicationUrlInput);
        assertTestCase.assertTrue(createPage.applicationURLRequiredTag.isDisplayed(), "url is a required field message is displayed");

        //enter all fields
        Faker faker = new Faker();
        String applicationName = "QA Test Application"+faker.number().digits(4);
        String applicationKey = "qatestapp"+applicationName.replaceAll("\\D+","");
        String applicationUrl = "https://www.google.com";
        createPage.applicationNameInput.sendKeys(applicationName);
        createPage.applicationKeyInput.sendKeys(applicationKey);
        createPage.applicationUrlInput.sendKeys("invalid url.com");
        assertTestCase.assertTrue(createPage.mustBeAValidURLTag.isDisplayed(), "Must be a valid URL message is displayed");
        clear(createPage.applicationUrlInput);
        createPage.applicationUrlInput.sendKeys("https://qatest.com");
        BrowserUtils.waitForClickablility(createPage.saveButton, 10);
        assertTestCase.assertTrue(createPage.saveButton.isEnabled(), "Save Button is enabled");
        assertTestCase.assertTrue(createPage.cancelButton.isEnabled(), "Cancel Button is not enabled");
        BrowserUtils.waitForClickablility(createPage.cancelButton, 10).click();
        assertTestCase.assertTrue(applicationsPage.createApplicationButton.isDisplayed(), "Create Application Button is displayed");
        assertTestCase.assertFalse(applicationsPage.verifyApplication(applicationName), "Application is not displayed in the list");
        applicationsPage.createApplication(applicationName, applicationKey, applicationUrl, "External");
        wait(applicationsPage.notification, 10);
        assertTestCase.assertTrue(applicationsPage.notification.isDisplayed(), "Application Created Notification is displayed");
        assertTestCase.assertTrue(applicationsPage.verifyApplication(applicationName), "Application is displayed in the list");
        applicationsPage.selectApplication(applicationName);
        EMCApplicationDetailsPage detailsPage = new EMCApplicationDetailsPage();
        detailsPage.clickOnProductsTab();
        assertTestCase.assertTrue(detailsPage.addProductButton.isDisplayed(), "Add Product Button is displayed");
        assertTestCase.assertTrue(detailsPage.productsList.size()==0, "Products List is empty for new application");

        detailsPage.clickOnRolesTab();
        assertTestCase.assertTrue(detailsPage.addRoleButton.isDisplayed(), "Add Role Button is displayed");
        assertTestCase.assertTrue(detailsPage.applicationRolesNames.size()==0, "Roles List is empty for new application");
        assertTestCase.assertTrue(detailsPage.noProductMessages.size()>0, "No Products messages are displayed");
    }

    @Test(groups = {EMC, UI, REGRESSION})
    @Xray(test = {2624, 7655, 12505})
    public void verifyUserSaveNewProductTest() {
        navigateToApplicationsPage(applicationName, "products");
        EMCApplicationDetailsPage detailsPage = new EMCApplicationDetailsPage();
        BrowserUtils.waitForVisibility(detailsPage.addProductButton, 10);
        assertTestCase.assertTrue(detailsPage.addProductButton.isDisplayed(), "Add Product Button is displayed");
        detailsPage.clickOnAddProductButton();
        assertTestCase.assertFalse(detailsPage.saveProductButton.isEnabled(), "Save Button is disabled for empty form");
        assertTestCase.assertTrue(detailsPage.cancelProductButton.isEnabled(), "Cancel Button is enabled and displayed");
        BrowserUtils.waitForClickablility(detailsPage.cancelProductButton, 5).click();
        String now = new Faker().name().firstName().toLowerCase();
        assertTestCase.assertTrue(detailsPage.addProduct("Test Product" + now, "testqa" + now, "123", "123", "Option", "DH", "License"), "Test Product is added");
        BrowserUtils.waitForVisibility(detailsPage.notification, 10);
        detailsPage.clickOnProductsTab();
        assertTestCase.assertTrue(detailsPage.notification.isDisplayed(), "Success Confirmation message is displayed on the Screen");
        assertTestCase.assertTrue(detailsPage.productsCheckboxList.size() > 0, "Product Checkbox list is displayed");
        detailsPage.searchProduct("Test Product" + now);
        detailsPage.productsCheckboxList.get(0).click();
        assertTestCase.assertTrue(detailsPage.deleteProductsButton.isDisplayed(), "Delete Product Button is displayed");
        BrowserUtils.waitForClickablility(detailsPage.deleteProductsButton, 10).click();
        assertTestCase.assertTrue(detailsPage.deleteButton.isDisplayed(), "Confirm Delete Button is displayed");
        assertTestCase.assertTrue(detailsPage.cancelButton.isDisplayed(), "Confirm Cancel Button is displayed");
        BrowserUtils.waitForClickablility(detailsPage.deleteButton, 10).click();
    }

    @Test(groups = {EMC, UI, REGRESSION, SMOKE, PROD})
    @Xray(test = {3537, 3539})
    public void verifyProductDetailsTest() {
        navigateToApplicationsPage(applicationName, "products");
        EMCApplicationDetailsPage detailsPage = new EMCApplicationDetailsPage();
        BrowserUtils.waitForVisibility(detailsPage.addProductButton, 10);
        assertTestCase.assertTrue(detailsPage.addProductButton.isDisplayed(), "Add Product Button is displayed");
        assertTestCase.assertTrue(detailsPage.productsList.size() > 0, "There are products assigned to the application");
        String productName = detailsPage.productsList.get(0).getText();
        System.out.println("productName = " + productName);
        BrowserUtils.waitForClickablility(detailsPage.productsList.get(0), 10).click();
        EMCProductDetailsPage productDetailsPage = new EMCProductDetailsPage();
        assertTestCase.assertTrue(productDetailsPage.verifyDetails(productName), "Product Details Page is verified");
        assertTestCase.assertTrue(productDetailsPage.editedButton.isEnabled(), "Edit Button is enabled");
    }

    @Test(groups = {EMC, UI, REGRESSION})
    @Xray(test = {7656})
    public void editProductDetailsTest() {
        navigateToApplicationsPage(applicationName, "products");
        EMCApplicationDetailsPage detailsPage = new EMCApplicationDetailsPage();
        BrowserUtils.waitForVisibility(detailsPage.addProductButton, 10);
        assertTestCase.assertTrue(detailsPage.addProductButton.isDisplayed(), "Add Product Button is displayed");

        //if application doesn't have any products, add one
        if (detailsPage.productsList.size() == 0) {
            String name = new Faker().name().username().toLowerCase();
            detailsPage.addProduct("QATest Product_" + name, "testqa" + name, "123", "123", "Option", "DH", "License");
        }

        String productName = detailsPage.productsList.get(0).getText();
        System.out.println("productName = " + productName);
        BrowserUtils.waitForClickablility(detailsPage.productsList.get(0), 10).click();
        EMCProductDetailsPage productDetailsPage = new EMCProductDetailsPage();
        assertTestCase.assertTrue(productDetailsPage.verifyDetails(productName), "Product Details Page is verified");
        assertTestCase.assertTrue(productDetailsPage.editedButton.isEnabled(), "Edit Button is enabled");
        productDetailsPage.clickOnEditButton();
        //Edit each field and verify that the save button is enabled
        productDetailsPage.editProduct("QA Test Product", "456", "456", "Bundle", "API", "Subscription");
        BrowserUtils.waitForVisibility(detailsPage.notification, 10);
        assertTestCase.assertTrue(detailsPage.notification.isDisplayed(), "Product Updated Notification is displayed");
        detailsPage.clickOnDetailsTab();
        BrowserUtils.wait(1);
        detailsPage.clickOnProductsTab();
        BrowserUtils.wait(3);
        //BrowserUtils.waitForInvisibility(detailsPage.notification, 10);
        detailsPage.selectProduct("QA Test Product");
        productDetailsPage.clickOnEditButton();
        BrowserUtils.waitForVisibility(productDetailsPage.backToApplicationProductsButton, 10).click();
        BrowserUtils.wait(3);
        detailsPage.selectProduct("QA Test Product");
        productDetailsPage.clickOnEditButton();
        BrowserUtils.wait(3);
        assertTestCase.assertEquals(productDetailsPage.productNameInput.getAttribute("value"), "QA Test Product", "Product Name is edited");
        //assertTestCase.assertEquals(productDetailsPage.productKeyInput.getAttribute("value"),"testqaa", "Product Key is not edited");
        assertTestCase.assertEquals(productDetailsPage.productCodeInput.getAttribute("value"), "456", "Product Code is edited");
        assertTestCase.assertEquals(productDetailsPage.productSfdcIdInput.getAttribute("value"), "456", "Product SFDC ID is edited");
        assertTestCase.assertEquals(productDetailsPage.productTypeDropdown.getAttribute("value"), "Bundle", "Product Type is edited");
        assertTestCase.assertEquals(productDetailsPage.productDeliveryChannelDropdown.getAttribute("value"), "API", "Product Delivery Channel is edited");
        assertTestCase.assertEquals(productDetailsPage.productPricingModelDropdown.getAttribute("value"), "Subscription", "Product Pricing Model is edited");

        productDetailsPage.editProduct(productName, "123", "123", "Option", "DH", "License");
        BrowserUtils.waitForClickablility(detailsPage.backToApplicationsButton, 10).click();
    }

    @Test(groups = {EMC, UI, REGRESSION, SMOKE})
    @Xray(test = {7654, 2151, 2156, 13123, 13139, 13141})
    public void verifyAdminUserEditApplicationTest() {
        navigateToApplicationsPage(testWebAppName, "details");
        EMCApplicationDetailsPage detailsPage = new EMCApplicationDetailsPage();
        BrowserUtils.waitForVisibility(detailsPage.editButton, 10);
        assertTestCase.assertTrue(detailsPage.editButton.isDisplayed(), "Edit Button is displayed");
        detailsPage.clickOnEditButton();
        assertTestCase.assertTrue(detailsPage.saveButton.isDisplayed(), "Save button is displayed");
        assertTestCase.assertTrue(detailsPage.cancelButton.isDisplayed(), "Cancel button is displayed");
        String url = detailsPage.ApplicationURL.getAttribute("value");
        System.out.println("url = " + url);
        clear(detailsPage.ApplicationName);
        detailsPage.ApplicationName.sendKeys("QATest_Edited");
        clear(detailsPage.ApplicationURL);
        detailsPage.ApplicationURL.sendKeys("https://esg.moodys.com");
        assertTestCase.assertTrue(detailsPage.saveButton.isEnabled(), "Save Button is enabled");
        BrowserUtils.waitForClickablility(detailsPage.saveButton, 10).click();
        BrowserUtils.waitForVisibility(detailsPage.notification, 10);
        assertTestCase.assertTrue(detailsPage.notification.isDisplayed(), "Application Updated Notification is displayed");
        BrowserUtils.waitForInvisibility(detailsPage.notification, 10);
        assertTestCase.assertTrue(detailsPage.ApplicationName.getAttribute("value").equals("QATest_Edited"), "Application Name is updated");
        assertTestCase.assertTrue(detailsPage.ApplicationURL.getAttribute("value").equals("https://esg.moodys.com"), "Application URL is updated");

        //Change account name and url back to original
        detailsPage.clickOnEditButton();
        clear(detailsPage.ApplicationName);
        detailsPage.ApplicationName.sendKeys(testWebAppName);
        clear(detailsPage.ApplicationURL);
        detailsPage.ApplicationURL.sendKeys(url);
        BrowserUtils.waitForClickablility(detailsPage.saveButton, 10).click();
        BrowserUtils.waitForVisibility(detailsPage.notification, 10);
        assertTestCase.assertTrue(detailsPage.notification.isDisplayed(), "Application Updated Notification is displayed");

        detailsPage.clickOnEditButton();
        clear(detailsPage.ApplicationName);
        detailsPage.ApplicationName.sendKeys("Cancel edit");
        clear(detailsPage.ApplicationURL);
        detailsPage.ApplicationURL.sendKeys("https://google.com");
        BrowserUtils.waitForClickablility(detailsPage.cancelButton, 10).click();
        assertTestCase.assertTrue(detailsPage.ApplicationName.getAttribute("value").equals(testWebAppName), "Application Name is not updated");
        assertTestCase.assertTrue(detailsPage.ApplicationURL.getAttribute("value").equals(url), "Application URL is not updated");
    }

    @Test(groups = {EMC, UI, REGRESSION})
    @Xray(test = {2152})
    public void verifyAllApplicationsVisibleInListViewOnApplicationPanelTest() {
        navigateToApplicationsPage("", "details");

        for (WebElement application : applicationsPage.applications) {
            BrowserUtils.scrollTo(application);
            assertTestCase.assertTrue(application.isDisplayed(), application.getText() + " Application is displayed");
        }
    }

    @Test(groups = {EMC, UI, REGRESSION}, description = "UI | EMC | OKTA | Application Role | Verify App Role Key is unique in EMC and OKTA")
    @Xray(test = {5691})
    public void verifyAppRoleKeyUniqueForEMCAndOKTA() {
        navigateToApplicationsPage(applicationName, "roles");
        EMCApplicationDetailsPage detailsPage = new EMCApplicationDetailsPage();

        //Application Details page is displayed with Products and Roles tab
        assertTestCase.assertTrue(detailsPage.ProductsTab.isDisplayed(), "Products Tab is displayed");
        assertTestCase.assertTrue(detailsPage.rolesTab.isDisplayed(), "Roles Tab is displayed");
        assertTestCase.assertTrue(detailsPage.detailsTab.isDisplayed(), "Details Tab is displayed");

        //Click Add Role button
        assertTestCase.assertTrue(detailsPage.addRoleButton.isDisplayed(), "Add Role Button is displayed");
        detailsPage.clickOnAddRoleButton();

        //Add Role modal is displayed with fields: Role Name, Role Key
        assertTestCase.assertTrue(detailsPage.addRolePopUpTitle.isDisplayed(), "Add Role Pop Up is displayed");
        assertTestCase.assertTrue(detailsPage.roleNameInput.isDisplayed(), "Role Name Input is displayed");
        assertTestCase.assertTrue(detailsPage.roleKeyInput.isDisplayed(), "Role Key Input is displayed");

        //Add Role name and an Existing Role Key and click Save button
        //Role Name: Issuer
        //Role Key: mesg-platform-issuer-qa
        detailsPage.roleNameInput.sendKeys("Issuer");
        detailsPage.roleKeyInput.sendKeys("mesg-platform-issuer-qa");
        detailsPage.clickOnSaveButton();

        //Warning error message is displayed "Role key already exist in EMC and OKTA"
        BrowserUtils.waitForVisibility(detailsPage.notification, 10);
        assertTestCase.assertTrue(detailsPage.notification.isDisplayed(), "Error Message is displayed");
        assertTestCase.assertTrue(detailsPage.notification.getText().equals("A role with the same name already exists. Try a different name."), "Error Message is displayed");

        //Click Cancel button
        detailsPage.clickOnCancelButton();
    }

    @Test(groups = {EMC, UI, REGRESSION}, description = "UI | EMC | Products | Validate User can View the Product Details View Sorting")
    @Xray(test = {3538})
    public void verifyUserViewProductDetailsSortedTest() {
        navigateToApplicationsPage(applicationName, "products");
        EMCApplicationDetailsPage detailsPage = new EMCApplicationDetailsPage();

        //Verify Product Details View is sorted by Product Name
        List<String> products = BrowserUtils.getElementsText(detailsPage.productsList);
        products.sort(String::compareToIgnoreCase);
        assertTestCase.assertEquals(products, BrowserUtils.getElementsText(detailsPage.productsList), "Products are sorted by Product Name");
    }

    @Test(groups = {EMC, UI, REGRESSION, SMOKE}, description = "UI | EMC | API | Application | Verify user can not Create an existing External/Internal Application")
    @Xray(test = {12719, 13187, 13189})
    public void verifyUserCantCreateExistingExternalInternalApplicationTest() {
        navigateToApplicationsPage("", "details");

        wait(applicationsPage.createApplicationButton, 20);
        assertTestCase.assertTrue(applicationsPage.createApplicationButton.isDisplayed(), "Save Button is displayed");
        //enter all fields
        String applicationName = "External Scores API";
        String applicationKey = "mesgc-scores-api-ext-qa";
        String applicationUrl = "https://mesg-scores-api-ext-qa.com";

        //create external application
        applicationsPage.createApplication(applicationName, applicationKey, applicationUrl, "External");
        assertTestCase.assertTrue(applicationsPage.notification.isDisplayed(), "Application Created Notification is displayed");
        System.out.println("Notification: " + applicationsPage.notification.getText());
        assertTestCase.assertEquals(applicationsPage.notification.getText(), "An application with the same name already exists. Try a different name.",
                "Application already exists Notification is displayed");
        applicationsPage.clickOnCancelButton();
        //waits for notification to disappear for 5 seconds
        BrowserUtils.wait(5);

        //create single page application
        applicationsPage.createApplication(applicationName, applicationKey, applicationUrl, "single-page");
        assertTestCase.assertTrue(applicationsPage.notification.isDisplayed(), "Application Created Notification is displayed");
        System.out.println("Notification: " + applicationsPage.notification.getText());
        assertTestCase.assertEquals(applicationsPage.notification.getText(), "An application with the same name already exists. Try a different name.",
                "Application already exists Notification is displayed");
        applicationsPage.clickOnCancelButton();
        BrowserUtils.wait(5);

        //create web page application name already exists
        applicationsPage.createApplication(applicationName, applicationKey, applicationUrl, "web");
        assertTestCase.assertTrue(applicationsPage.notification.isDisplayed(), "Application Created Notification is displayed");
        System.out.println("Notification: " + applicationsPage.notification.getText());
        assertTestCase.assertEquals(applicationsPage.notification.getText(), "An application with the same name already exists. Try a different name.",
                "Application already exists Notification is displayed");
        applicationsPage.clickOnCancelButton();
        BrowserUtils.wait(5);

        //create web page application key already exists
        applicationsPage.createApplication(applicationName + faker.number().digits(3), applicationKey, applicationUrl, "web");
        assertTestCase.assertTrue(applicationsPage.notification.isDisplayed(), "Application Created Notification is displayed");
        System.out.println("Notification: " + applicationsPage.notification.getText());
        assertTestCase.assertEquals(applicationsPage.notification.getText(), "Failed creating application. Please try again.",
                "Application already exists Notification is displayed");
        applicationsPage.clickOnCancelButton();
        BrowserUtils.wait(5);
    }

    @Test(groups = {EMC, UI, REGRESSION, SMOKE},
            description = "UI | EMC | API | Application | Verify user can not Create a new External Application if required fields are missing")
    @Xray(test = {12721})
    public void verifyUserCantCreateAApplicationWithoutRequiredFields() {
        navigateToApplicationsPage("", "details");

        assertTestCase.assertTrue(applicationsPage.createApplicationButton.isDisplayed(), "Save Button is displayed");
        applicationsPage.clickOnCreateApplicationButton();

        EMCApplicationCreatePage createPage = new EMCApplicationCreatePage();
        BrowserUtils.doubleClick(createPage.externalApplicationRadioButton);
        assertTestCase.assertTrue(createPage.externalApplicationRadioButton.isSelected(), "External Application Radio Button is selected");
        BrowserUtils.waitForClickablility(createPage.nextButton, 10).click();

        createPage.applicationNameInput.sendKeys("External Test Application");
        createPage.applicationUrlInput.sendKeys("https://external-test-application.com");
        assertTestCase.assertFalse(createPage.saveButton.isEnabled(), "Save Button is disabled");
        createPage.applicationKeyInput.sendKeys("application");
        assertTestCase.assertTrue(createPage.saveButton.isEnabled(), "Save Button is enabled");
        createPage.clickOnCancelButton();

        applicationsPage.clickOnCreateApplicationButton();

        createPage = new EMCApplicationCreatePage();
        BrowserUtils.doubleClick(createPage.singlePageApplicationRadioButton);
        assertTestCase.assertTrue(createPage.singlePageApplicationRadioButton.isSelected(), "Single Page Application Radio Button is selected");
        BrowserUtils.waitForClickablility(createPage.nextButton, 10).click();

        createPage.applicationNameInput.sendKeys("Internal Test Application");
        createPage.applicationUrlInput.sendKeys("https://internal-test-application.com");
        assertTestCase.assertFalse(createPage.saveButton.isEnabled(), "Save Button is disabled");
        createPage.applicationKeyInput.sendKeys("application");
        assertTestCase.assertTrue(createPage.saveButton.isEnabled(), "Save Button is enabled");
        createPage.clickOnCancelButton();
    }

    @Test(groups = {EMC, UI, REGRESSION, SMOKE}, description = "UI | EMC | Applications | An EMC administrator can create a new web applications")
    @Xray(test = {13184})
    public void verifyAdminCreateWebApplicationTest() {
        navigateToApplicationsPage("", "details");

        wait(applicationsPage.createApplicationButton, 20);
        assertTestCase.assertTrue(applicationsPage.createApplicationButton.isDisplayed(), "Save Button is displayed");
        //enter all fields
        String applicationName = "QA Test Web Application" + faker.number().digits(5);
        String applicationKey = "qawebapp" + applicationName.replaceAll("\\D", "");
        String applicationUrl = "https://www.qatest.com";

        //create web application
        applicationsPage.createApplication(applicationName, applicationKey, applicationUrl, "Web");
        assertTestCase.assertTrue(applicationsPage.notification.isDisplayed(), "Application Created Notification is displayed");
        System.out.println("Notification: " + applicationsPage.notification.getText());
        assertTestCase.assertEquals(applicationsPage.notification.getText(), "Application created successfully.",
                "Application Created Notification is displayed");
        assertTestCase.assertTrue(applicationsPage.verifyApplication(applicationName), "Application creation is successful");
    }

    @Test(groups = {EMC, UI, REGRESSION, API, SMOKE}, description = "UI | EMC | Application | Verify user with VIEW role is not able to create a Web application.")
    @Xray(test = {13196, 13239})
    public void verifyUserDetailsAccountInformationTest() {
        navigateToApplicationsPage("", "details");

        try {
            loginAsViewer();
            navigateToApplicationsPage("", "details");
        } catch (Exception e) {
            System.out.println("Viewer role user is not able to login");
            closeAndLoginWithAdmin();
        }

        System.out.println("Viewer role user navigated to applications page");
        //convert applications page create application button to List<WebElement>
        wait(applicationsPage.createApplicationButtonList, 5);
        if (applicationsPage.createApplicationButtonList.size() > 0) {
            System.out.println("Create Application Button is displayed for viewer role user");
            try {
                closeAndLoginWithAdmin();
            } catch (Exception e) {
                System.out.println("Viewer role user is able to create an application");
            }
            assertTestCase.fail("Viewer role user is able to create an application");
        }
        applicationsPage.selectApplication(testWebAppName);
        EMCApplicationDetailsPage detailsPage = new EMCApplicationDetailsPage();
        detailsPage.verifyApplicationDetails("Viewer");
        closeAndLoginWithAdmin();
    }

    public void closeAndLoginWithAdmin() {
        //close the browser and login with admin role user
        Driver.quit();
        Driver.getDriver().get(Environment.EMC_URL);
        BrowserUtils.waitForPageToLoad(10);
        LoginPageEMC loginPageEMC = new LoginPageEMC();
        loginPageEMC.loginWithInternalUser();
        navigateToApplicationsPage("", "details");
    }

    @Test(groups = {EMC, UI, REGRESSION}, description = "UI | EMC | The ability to view all accounts | Verify that User is able to Click Account tab on Accounts Screen s")
    @Xray(test = {2353, 2313, 2311})
    public void verifyUserClickAccountMenuOnApplicationsPageTest() {
        navigateToApplicationsPage("", "details");
        wait(applicationsPage.applications, 20);
        assertTestCase.assertTrue(applicationsPage.applications.size() > 0, "Applications are displayed");
        navigateToMenu("Accounts");
        EMCAccountsPage accountsPage = new EMCAccountsPage();
        wait(accountsPage.accountNames, 20);
        assertTestCase.assertTrue(accountsPage.accountNames.size() > 0, "Accounts are displayed");
    }

    @Test(groups = {EMC, UI, REGRESSION, PROD}, description = "UI | EMC | Verify Applications Page")
    @Xray(test = {2314})
    public void verifyApplicationsPageTest() {
        navigateToApplicationsPage("", "details");
        wait(applicationsPage.applications, 20);
        assertTestCase.assertTrue(applicationsPage.pageTitle.isDisplayed(), "Page Title is displayed");
        assertTestCase.assertTrue(applicationsPage.createApplicationButton.isDisplayed(), "Create Application Button is displayed");
        assertTestCase.assertTrue(applicationsPage.searchInput.isDisplayed(), "Search Input is displayed");
        assertTestCase.assertTrue(applicationsPage.applications.size() > 0, "Applications are displayed");
        assertTestCase.assertTrue(applicationsPage.applicationLinks.size()>0, "Application Links are displayed");
        assertTestCase.assertTrue(applicationsPage.providerList.size()>0, "Provider List is displayed");
        assertTestCase.assertTrue(applicationsPage.typeList.size()>0, "Type List is displayed");
        assertTestCase.assertTrue(applicationsPage.createdInfoList.size()>0, "Created Info List is displayed");
        assertTestCase.assertTrue(applicationsPage.createdByList.size()>0, "Created By List is displayed");
        assertTestCase.assertTrue(applicationsPage.modifiedList.size()>0, "Modified List is displayed");
        //assertTestCase.assertTrue(applicationsPage.checkBoxes.size()>0, "Check Boxes are displayed");
    }

    @Test(groups = {EMC, UI, REGRESSION, SMOKE, PROD}, description = "UI | EMC | Applications | Verify the ability to search for an application")
    @Xray(test = {4519})
    public void verifyApplicationSearchTest() {
        navigateToApplicationsPage("", "details");
        wait(applicationsPage.applications, 20);
        applicationsPage.searchApplication(applicationName);
        assertTestCase.assertTrue(applicationsPage.verifyApplication(applicationName, false), "Applications are displayed");
        applicationsPage.searchApplication(applicationName.toLowerCase());
        assertTestCase.assertTrue(applicationsPage.verifyApplication(applicationName, false), "Applications are displayed");
        applicationsPage.searchApplication(applicationName.toUpperCase());
        assertTestCase.assertTrue(applicationsPage.verifyApplication(applicationName, false), "Applications are displayed");
        //switch lower to upper/ upper case to lower case
        applicationsPage.searchApplication(applicationName.substring(0, 1).toUpperCase() + applicationName.substring(1).toLowerCase());
        assertTestCase.assertTrue(applicationsPage.verifyApplication(applicationName, false), "Applications are displayed");
        applicationsPage.searchApplication(applicationName.substring(0, 1).toLowerCase() + applicationName.substring(1).toUpperCase());
        assertTestCase.assertTrue(applicationsPage.verifyApplication(applicationName, false), "Applications are displayed");
        //search with partial name
        applicationsPage.searchApplication(applicationName.substring(0, 5));
        assertTestCase.assertTrue(applicationsPage.verifyApplication(applicationName, false), "Applications are displayed");
        applicationsPage.searchApplication(applicationName.substring(0, 5).toLowerCase());
        assertTestCase.assertTrue(applicationsPage.verifyApplication(applicationName, false), "Applications are displayed");
        applicationsPage.searchApplication(applicationName.substring(0, 5).toUpperCase());
        assertTestCase.assertTrue(applicationsPage.verifyApplication(applicationName, false), "Applications are displayed");
    }
}
