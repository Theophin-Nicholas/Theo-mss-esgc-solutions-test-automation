package com.esgc.Tests;

import com.esgc.Pages.*;
import com.esgc.TestBases.EMCUITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Xray;
import com.github.javafaker.Faker;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

import static com.esgc.Utilities.Groups.*;

public class ApplicationPageTests extends EMCUITestBase {
    String applicationName = "TestQA";
    String testApplicationName = "TestQA2";

    public void navigateToApplicationsPage(String applicationName, String tabName) {
        EMCMainPage homePage = new EMCMainPage();
        homePage.goToApplicationsPage();
        EMCApplicationsPage applicationsPage = new EMCApplicationsPage();
        assertTestCase.assertEquals(applicationsPage.pageTitle.getText(), "Applications", "Applications page is displayed");
        //Search for the account where a New user will be created
        if (!applicationName.isEmpty()) {
            applicationsPage.search(applicationName);
            assertTestCase.assertEquals(applicationsPage.applications.size() > 0, true, "Application  is displayed");
            System.out.println(applicationsPage.applications.get(0).getText().toLowerCase());
            assertTestCase.assertTrue(applicationsPage.applications.get(0).getText().toLowerCase().contains(applicationName.toLowerCase()),
                    "Application is displayed");

            //On Account Details view, select USERS tab
            applicationsPage.clickOnFirstApplication();
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
    @Xray(test = {2330, 5101, 5103,2153})
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
        navigateToApplicationsPage(testApplicationName,"details");
        EMCApplicationDetailsPage detailsPage = new EMCApplicationDetailsPage();
        assertTestCase.assertTrue(detailsPage.ApplicationName.isDisplayed(), "Application Name is displayed");
        assertTestCase.assertTrue(detailsPage.editButton.isEnabled(), "Edit button is enabled");
        BrowserUtils.waitForClickablility(detailsPage.editButton,5).click();
        assertTestCase.assertTrue(detailsPage.ApplicationName.isEnabled(), "Application Name is enabled");
        clear(detailsPage.ApplicationName);
        detailsPage.ApplicationName.sendKeys("TestQATest");
        BrowserUtils.waitForClickablility(detailsPage.saveButton,5).click();
        String actApplicationNameAfterSave = detailsPage.ApplicationName.getAttribute("value");
        assertTestCase.assertEquals(actApplicationNameAfterSave, "TestQATest", "Application Name is verified");
        BrowserUtils.waitForClickablility(detailsPage.editButton,5).click();
        clear(detailsPage.ApplicationName);
        detailsPage.ApplicationName.sendKeys(testApplicationName);
        BrowserUtils.waitForClickablility(detailsPage.saveButton,5).click();
    }

    @Test(groups = {EMC, UI, REGRESSION})//no smoke
    @Xray(test = {5173,7657})
    public void verifyUserCreateNewRoleForApplicationTest() {
        EMCMainPage emcMainPage = new EMCMainPage();
        //Navigate to EMC Application Panel
        emcMainPage.openSidePanel();
        //Click on Application
        emcMainPage.clickApplicationsButton();
        //Navigate to the Application Name on table.
        EMCApplicationsPage applicationsPage = new EMCApplicationsPage();
        //Click on Application Name
        applicationsPage.selectApplication(applicationName);
        //Verify that User is Able to See Application Details.
        EMCApplicationDetailsPage detailsPage = new EMCApplicationDetailsPage();
        detailsPage.clickOnRolesTab();
        assertTestCase.assertTrue(detailsPage.addRoleButton.isDisplayed(), "Application Name is displayed");
        BrowserUtils.waitForClickablility(detailsPage.addRoleButton,5).click();
        BrowserUtils.waitForVisibility(detailsPage.addRolePopUpTitle, 5);
        assertTestCase.assertTrue(detailsPage.addRolePopUpTitle.isDisplayed(), "Add Role Pop Up is displayed");
        String now = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("ddhhmmss"));
        detailsPage.roleNameInput.sendKeys("QATest" + now);
        detailsPage.roleKeyInput.sendKeys(now, Keys.TAB);
        BrowserUtils.waitForClickablility(detailsPage.saveButton, 10).click();
        BrowserUtils.waitForVisibility(detailsPage.notification, 10);
        assertTestCase.assertTrue(detailsPage.getApplicationRolesNames().contains("QATest" + now), "Role Name is displayed");
    }

    @Test(groups = {EMC, UI, REGRESSION})
    @Xray(test = {2312})
    public void verifyEMCApplicationsSortedAlphabeticallyByNameTest() {
        navigateToApplicationsPage("", "roles");
        EMCApplicationsPage applicationsPage = new EMCApplicationsPage();
        List<String> applicationNames = applicationsPage.getApplicationNames();
        //convert to lower case and sort alphabetically
        applicationNames.sort(String::compareToIgnoreCase);
        assertTestCase.assertEquals(applicationNames, applicationsPage.getApplicationNames(), "Applications are sorted alphabetically");
    }

    @Test(groups = {EMC, UI, REGRESSION})
    @Xray(test = {2332})
    public void verifyApplicationURLClickableTest() {
        navigateToApplicationsPage("", "roles");
        EMCApplicationsPage applicationsPage = new EMCApplicationsPage();
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

    @Test(groups = {EMC, UI, REGRESSION})
    @Xray(test = {2324,2347,2348,2349,7649})
    public void verifyInformationRequiredForCreatingApplicationFulfilledTest() {
        navigateToApplicationsPage("", "details");
        EMCApplicationsPage applicationsPage = new EMCApplicationsPage();
        assertTestCase.assertTrue(applicationsPage.createApplicationButton.isDisplayed(), "Save Button is displayed");
        applicationsPage.clickOnCreateApplicationButton();
        EMCApplicationCreatePage createPage = new EMCApplicationCreatePage();
        assertTestCase.assertTrue(createPage.pageTitle.isDisplayed(), "Create Application Page Title is displayed");
        assertTestCase.assertFalse(createPage.saveButton.isEnabled(), "Save Button is not enabled");

        //Check application name field
        createPage.applicationNameInput.sendKeys("Test",Keys.TAB);
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
        createPage.applicationNameInput.sendKeys("QA Test Application");
        createPage.applicationKeyInput.sendKeys("qatest");
        createPage.applicationUrlInput.sendKeys("Test");
        assertTestCase.assertTrue(createPage.mustBeAValidURLTag.isDisplayed(), "Must be a valid URL message is displayed");
        clear(createPage.applicationUrlInput);
        createPage.applicationUrlInput.sendKeys("https://qatest.com");
        BrowserUtils.waitForClickablility(createPage.saveButton, 10);
        assertTestCase.assertTrue(createPage.saveButton.isEnabled(), "Save Button is enabled");
        BrowserUtils.waitForClickablility(createPage.saveButton, 10).click();
        assertTestCase.assertTrue(applicationsPage.notification.isDisplayed(),"Application Created Notification is displayed");
    }

    public void clear(WebElement element) {
        while (element.getAttribute("value").length() > 0) {
            element.sendKeys(Keys.BACK_SPACE);
        }
        element.sendKeys(Keys.TAB);
    }

    @Test(groups = {EMC, UI, REGRESSION})
    @Xray(test = {2624,7655})
    public void verifyUserSaveNewProductTest() {
        navigateToApplicationsPage(applicationName, "products");
        EMCApplicationDetailsPage detailsPage = new EMCApplicationDetailsPage();
        BrowserUtils.waitForVisibility(detailsPage.addProductButton, 10);
        assertTestCase.assertTrue(detailsPage.addProductButton.isDisplayed(), "Add Product Button is displayed");
        detailsPage.clickOnAddProductButton();
        assertTestCase.assertFalse(detailsPage.saveProductButton.isEnabled(), "Save Button is disabled for empty form");
        assertTestCase.assertTrue(detailsPage.cancelProductButton.isEnabled(), "Cancel Button is enabled and displayed");
        BrowserUtils.waitForClickablility(detailsPage.cancelProductButton,5).click();
        String now = new Faker().name().firstName().toLowerCase();
        assertTestCase.assertTrue(detailsPage.addProduct("Test Product"+now,"testqa"+now,"123","123","Option","DH","License"), "Test Product is added");
        BrowserUtils.waitForVisibility(detailsPage.notification, 10);
        assertTestCase.assertTrue(detailsPage.notification.isDisplayed(), "Success Confirmation message is displayed on the Screen");
    }

    @Test(groups = {EMC, UI, REGRESSION, SMOKE, PROD})
    @Xray(test = {3537, 3539})
    public void verifyProductDetailsTest() {
        navigateToApplicationsPage(applicationName, "products");
        EMCApplicationDetailsPage detailsPage = new EMCApplicationDetailsPage();
        BrowserUtils.waitForVisibility(detailsPage.addProductButton, 10);
        assertTestCase.assertTrue(detailsPage.addProductButton.isDisplayed(), "Add Product Button is displayed");
        assertTestCase.assertTrue(detailsPage.productsList.size()>0, "There are products assigned to the application");
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
        if(detailsPage.productsList.size()==0){
            String name = new Faker().name().username().toLowerCase();
            detailsPage.addProduct("QATest Product_"+name,"testqa"+name,"123","123","Option","DH","License");
        }

        String productName = detailsPage.productsList.get(0).getText();
        System.out.println("productName = " + productName);
        BrowserUtils.waitForClickablility(detailsPage.productsList.get(0), 10).click();
        EMCProductDetailsPage productDetailsPage = new EMCProductDetailsPage();
        assertTestCase.assertTrue(productDetailsPage.verifyDetails(productName), "Product Details Page is verified");
        assertTestCase.assertTrue(productDetailsPage.editedButton.isEnabled(), "Edit Button is enabled");
        productDetailsPage.clickOnEditButton();
        //Edit each field and verify that the save button is enabled
        productDetailsPage.editProduct("QA Test Product","456","456","Bundle","API","Subscription");
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
        assertTestCase.assertEquals(productDetailsPage.productNameInput.getAttribute("value"),"QA Test Product", "Product Name is edited");
        //assertTestCase.assertEquals(productDetailsPage.productKeyInput.getAttribute("value"),"testqaa", "Product Key is not edited");
        assertTestCase.assertEquals(productDetailsPage.productCodeInput.getAttribute("value"),"456", "Product Code is edited");
        assertTestCase.assertEquals(productDetailsPage.productSfdcIdInput.getAttribute("value"),"456", "Product SFDC ID is edited");
        assertTestCase.assertEquals(productDetailsPage.productTypeDropdown.getAttribute("value"),"Bundle", "Product Type is edited");
        assertTestCase.assertEquals(productDetailsPage.productDeliveryChannelDropdown.getAttribute("value"),"API", "Product Delivery Channel is edited");
        assertTestCase.assertEquals(productDetailsPage.productPricingModelDropdown.getAttribute("value"),"Subscription", "Product Pricing Model is edited");

        productDetailsPage.editProduct(productName,"123","123","Option","DH","License");
        BrowserUtils.waitForClickablility(detailsPage.backToApplicationsButton, 10).click();
    }

    @Test(groups = {EMC, UI, REGRESSION})
    @Xray(test = {7654,2151,2156})
    public void verifyAdminUserEditApplicationTest() {
        //String applicationName = "TestQA";
        navigateToApplicationsPage(testApplicationName, "details");
        EMCApplicationDetailsPage detailsPage = new EMCApplicationDetailsPage();
        BrowserUtils.waitForVisibility(detailsPage.editButton, 10);
        assertTestCase.assertTrue(detailsPage.editButton.isDisplayed(), "Edit Button is displayed");
        detailsPage.clickOnEditButton();
        assertTestCase.assertTrue(detailsPage.saveButton.isDisplayed(), "Save button is displayed");
        assertTestCase.assertTrue(detailsPage.cancelButton.isDisplayed(), "Cancel button is displayed");
        clear(detailsPage.ApplicationName);
        detailsPage.ApplicationName.sendKeys("QATest_Edited");
        clear(detailsPage.ApplicationURL);
        detailsPage.ApplicationURL.sendKeys("https://esg.moodys.com");
        assertTestCase.assertTrue(detailsPage.saveButton.isEnabled(), "Save Button is enabled");
        BrowserUtils.waitForClickablility(detailsPage.saveButton, 10).click();
        BrowserUtils.waitForVisibility(detailsPage.notification, 10);
        assertTestCase.assertTrue(detailsPage.notification.isDisplayed(),"Application Updated Notification is displayed");
        BrowserUtils.waitForInvisibility(detailsPage.notification, 10);
        assertTestCase.assertTrue(detailsPage.ApplicationName.getAttribute("value").equals("QATest_Edited"), "Application Name is updated");
        assertTestCase.assertTrue(detailsPage.ApplicationURL.getAttribute("value").equals("https://esg.moodys.com"), "Application URL is updated");
        detailsPage.clickOnEditButton();
        clear(detailsPage.ApplicationName);
        detailsPage.ApplicationName.sendKeys(testApplicationName);
        clear(detailsPage.ApplicationURL);
        detailsPage.ApplicationURL.sendKeys("https://moodys.com");
        BrowserUtils.waitForClickablility(detailsPage.saveButton, 10).click();
        BrowserUtils.waitForVisibility(detailsPage.notification, 10);
        assertTestCase.assertTrue(detailsPage.notification.isDisplayed(),"Application Updated Notification is displayed");

    }

    @Test(groups = {EMC, UI, REGRESSION})
    @Xray(test = {2152})
    public void verifyAllApplicationsVisibleInListViewOnApplicationPanelTest() {
        navigateToApplicationsPage("", "details");
        EMCApplicationsPage applicationsPage = new EMCApplicationsPage();
        for(WebElement application : applicationsPage.applications){
            BrowserUtils.scrollTo(application);
            assertTestCase.assertTrue(application.isDisplayed(), application.getText()+ " Application is displayed");
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
        assertTestCase.assertTrue(detailsPage.detailsTab.isDisplayed(), "Roles Tab is displayed");

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
}
