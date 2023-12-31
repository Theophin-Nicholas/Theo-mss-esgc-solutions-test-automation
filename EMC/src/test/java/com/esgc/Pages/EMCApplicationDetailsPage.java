package com.esgc.Pages;

import com.esgc.Utilities.BrowserUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EMCApplicationDetailsPage extends EMCBasePage{
    @FindBy (xpath = "//h4")
    public WebElement pageTitle;

    @FindBy (xpath = "//button[.='Details']")
    public WebElement detailsTab;

    @FindBy (xpath = "//button[.='Products']")
    public WebElement ProductsTab;

    @FindBy (xpath = "//button[.='Roles']")
    public WebElement rolesTab;

    @FindBy (xpath = "//h4/following-sibling::div[1]")
    public WebElement providerType;

    @FindBy (xpath = "//h4/following-sibling::div[2]")
    public WebElement applicationType;

    @FindBy (xpath = "//button[.='Back to Applications']")
    public WebElement backToApplicationsButton;

    //Details tab elements
    @FindBy (xpath = "(//input)[1]")
    public WebElement ApplicationName;

    @FindBy (xpath = "(//input)[2]")
    public WebElement ApplicationURL;

    @FindBy (xpath = "//span[starts-with(.,'Created by')]")
    public WebElement createdByText;

    @FindBy (xpath = "//span[starts-with(.,'Modified by')]")
    public WebElement modifiedByText;

    @FindBy (xpath = "//button[.='Edit']")
    public WebElement editButton;

    @FindBy (xpath = "//button[.='Save']")
    public WebElement saveButton;

    @FindBy (xpath = "//button[.='Cancel']")
    public WebElement cancelButton;

    @FindBy (xpath = "//button[.='Delete']")
    public WebElement deleteButton;

    @FindBy (xpath = "//button[.='Add product']")
    public WebElement addProductButton;

    @FindBy (xpath = "//input[@placeholder='Search by name, email, etc...']")
    public WebElement searchInput;

    @FindBy (xpath = "//tbody//a")
    public List<WebElement> productsList;

    @FindBy (xpath = "//tbody//td//input")
    public List<WebElement> productsCheckboxList;

    @FindBy (xpath = "//button[@title='Delete']")
    public WebElement deleteProductsButton;

    @FindBy (xpath = "//button[.='Add Role']")
    public WebElement addRoleButton;

    @FindBy (xpath = "//main//h6")
    public List<WebElement> noProductMessages;

    @FindBy (xpath = "//tbody//a")
    public List<WebElement> applicationRolesNames;

    @FindBy (xpath = "//tbody//td[2]")
    public List<WebElement> applicationRolesKeys;

    @FindBy (xpath = "//h2[.='Add Role']")
    public WebElement addRolePopUpTitle;

    @FindBy (xpath = "//input[@name='name']")
    public WebElement roleNameInput;

    @FindBy (xpath = "//input[@name='key']")
    public WebElement roleKeyInput;

    @FindBy (xpath = "(//h6/../following-sibling::div//input)[1]")
    public WebElement roleDetailNameInput;

    @FindBy (xpath = "(//h6/../following-sibling::div//input)[2]")
    public WebElement roleDetailKeyInput;

    @FindBy (xpath = "//span[starts-with(.,'Created by')]")
    public WebElement roleDetailCreatedInfo;

    @FindBy (xpath = "//span[starts-with(.,'Modified by')]")
    public WebElement roleDetailModifiedInfo;

    //Products tab elements - Add product pop up elements

    @FindBy (xpath = "//h4[.='Add product']")
    public WebElement addProductPopUpTitle;

    @FindBy (xpath = "//input[@name='name']")
    public WebElement productNameInput;

    @FindBy (xpath = "//input[@name='key']")
    public WebElement productKeyInput;

    @FindBy (xpath = "//input[@name='code']")
    public WebElement productCodeInput;

    @FindBy (xpath = "//input[@name='sfdcId']")
    public WebElement productSfdcIdInput;

    @FindBy (xpath = "//label[.='Product Type']/following-sibling::div//input")
    public WebElement productTypeDropdown;

    @FindBy (xpath = "//label[.='Product Delivery Channel']/following-sibling::div//input")
    public WebElement productDeliveryChannelDropdown;

    @FindBy (xpath = "//label[.='Product Pricing Model']/following-sibling::div//input")
    public WebElement productPricingModelDropdown;

    @FindBy (xpath = "//ul//li")
    public List<WebElement> dropDownOptions;

    @FindBy (xpath = "//button[.='Save']")
    public WebElement saveProductButton;

    @FindBy (xpath = "//button[.='Cancel']")
    public WebElement cancelProductButton;

    @FindBy (xpath = "//hr/..//button[.='Delete']")
    public WebElement productDeletePopUpDeleteButton;

    @FindBy (xpath = "//hr/..//button[.='Cancel']")
    public WebElement productDeletePopUpCancelButton;

//METHODS
    public List<String> getApplicationRolesNames() {
        return applicationRolesNames.stream().map(WebElement::getText).collect(java.util.stream.Collectors.toList());
    }

    public List<String> getApplicationRolesKeys() {
        return applicationRolesKeys.stream().map(WebElement::getText).collect(java.util.stream.Collectors.toList());
    }

    public String getApplicationName(){
        return ApplicationName.getAttribute("value");
    }
    public String getApplicationURL(){
        return ApplicationURL.getAttribute("value");
    }

    public void clickOnDetailsTab() {
        System.out.println("Navigating to Details tab");
        BrowserUtils.waitForClickablility(detailsTab, 5).click();
    }

    public void clickOnProductsTab() {
        System.out.println("Navigating to Products tab");
        BrowserUtils.waitForClickablility(ProductsTab, 5).click();
    }

    public void clickOnRolesTab() {
        System.out.println("Navigating to Roles tab");
        BrowserUtils.waitForClickablility(rolesTab, 5).click();
    }

    public void clickOnAddProductButton() {
        BrowserUtils.waitForClickablility(addProductButton, 5).click();
        System.out.println("Clicked on Add Product button");
    }
    public boolean addProduct(String productName, String productKey, String productCode, String productSfdcId, String productType, String productDeliveryChannel, String productPricingModel) {
        try{
            clickOnAddProductButton();
            BrowserUtils.waitForVisibility(addProductPopUpTitle, 5);
            productNameInput.sendKeys(productName);
            productKeyInput.sendKeys(productKey);
            productCodeInput.sendKeys(productCode);
            productSfdcIdInput.sendKeys(productSfdcId);

            clickAndSelect(productTypeDropdown, productType);
            clickAndSelect(productDeliveryChannelDropdown, productDeliveryChannel);
            clickAndSelect(productPricingModelDropdown, productPricingModel);
            BrowserUtils.waitForClickablility(saveProductButton, 5).click();
            //BrowserUtils.waitForInvisibility(addProductPopUpTitle, 5);
            return true;
        } catch (Exception e){
            System.out.println("Error while adding product");
            e.printStackTrace();
            return false;
        }

    }

    public boolean clickAndSelect(WebElement button, String value) {

        BrowserUtils.waitForClickablility(button,5).click();
        for (WebElement element : dropDownOptions) {
            if (element.getText().toLowerCase().equals(value.toLowerCase())) {
                System.out.println("Found " + value + " in the list");
                element.click();
                return true;
            }
        }
        System.out.println("Could not find " + value + " in the list");
        System.out.println("Available options are: ");
        for (WebElement element : dropDownOptions) {
            System.out.println(element.getText());
        }
        return false;
    }

    public void clickOnEditButton() {
        BrowserUtils.waitForClickablility(editButton, 5).click();
        System.out.println("Clicked on Edit button");
    }

    public void selectProduct(String productName) {
        clickOnProductsTab();
        for (WebElement element : productsList) {
            System.out.println("Product name = "+element.getText());
            if (element.getText().equals(productName)) {
                System.out.println("Found " + productName + " in the list");
                element.click();
                BrowserUtils.waitForInvisibility(element, 5);
                break;
            }
        }
    }

    public void clickOnAddRoleButton() {
        System.out.println("Clicking on Add Role button");
        BrowserUtils.waitForClickablility(addRoleButton, 5).click();

    }

    public void clickOnSaveButton() {
        System.out.println("Clicking on Save button");
        BrowserUtils.waitForClickablility(saveButton, 5).click();
    }

    public void clickOnCancelButton() {
        System.out.println("Clicking on Cancel button");
        BrowserUtils.waitForClickablility(cancelButton, 5).click();
    }

    public void searchProduct(String productName) {
        System.out.println("Searching for product " + productName);
        clear(searchInput);
        BrowserUtils.waitForClickablility(searchInput, 5).sendKeys(productName);
    }

    public void verifyApplicationDetails(String roleType) {
        verifyApplicationDetails();
        if (roleType.equals("Admin")) {
            assertTestCase.assertTrue(editButton.isDisplayed(), "Edit button is displayed");
            BrowserUtils.waitForClickablility(editButton, 5).click();
            assertTestCase.assertTrue(ApplicationName.isEnabled(), "Application name is enabled");
            assertTestCase.assertTrue(ApplicationURL.isEnabled(), "Application URL is enabled");
            assertTestCase.assertTrue(saveButton.isDisplayed(), "Save button is displayed");
            assertTestCase.assertTrue(cancelButton.isDisplayed(), "Cancel button is displayed");
            clickOnCancelButton();
        } else {
            try {
                if(editButton.isDisplayed()) {
                    System.out.println("Edit button shouldn't be displayed");
                    assertTestCase.assertTrue(false, "Edit button shouldn't be displayed");
                }
            } catch (Exception e) {
                System.out.println("Edit button is not displayed as expected");
            }
        }
    }

    public void verifyApplicationDetails() {
        wait(ApplicationURL, 250);
        assertTestCase.assertTrue(ApplicationName.isDisplayed(), "Application name is displayed");
        assertTestCase.assertTrue(ApplicationURL.isDisplayed(), "Application URL is displayed");
        assertTestCase.assertTrue(detailsTab.isDisplayed(), "Details tab is displayed");
        assertTestCase.assertTrue(ProductsTab.isDisplayed(), "Products tab is displayed");
        assertTestCase.assertTrue(rolesTab.isDisplayed(), "Roles tab is displayed");
        assertTestCase.assertTrue(pageTitle.isDisplayed(), "Page title is displayed");
        assertTestCase.assertTrue(createdByText.isDisplayed(), "Created by text is displayed");
        assertTestCase.assertTrue(modifiedByText.isDisplayed(), "Modified by text is displayed");
        assertTestCase.assertTrue(backToApplicationsButton.isDisplayed(), "Back to Applications button is displayed");
        assertTestCase.assertTrue(providerType.isDisplayed(), "Provider type is displayed");
        assertTestCase.assertTrue(applicationType.isDisplayed(), "Application type is displayed");
        assertTestCase.assertFalse(ApplicationName.isEnabled(), "Application name is not editable");
        assertTestCase.assertFalse(ApplicationURL.isEnabled(), "Application URL is not editable");
    }

    public void selectRole(String roleName) {
        clickOnRolesTab();
        for (WebElement element : applicationRolesNames) {
            System.out.println("Role name = "+element.getText());
            if (element.getText().equals(roleName)) {
                System.out.println("Found " + roleName + " in the list");
                element.click();
                BrowserUtils.wait(2);
                break;
            }
        }
    }

    public boolean verifyRoleDetails() {
        assertTestCase.assertTrue(pageTitle.isDisplayed(), "Page title is displayed");
        assertTestCase.assertTrue(createdByText.isDisplayed(), "Created by text is displayed");
        assertTestCase.assertTrue(modifiedByText.isDisplayed(), "Modified by text is displayed");
        assertTestCase.assertTrue(roleDetailNameInput.isDisplayed(), "Role name input is displayed");
        assertTestCase.assertTrue(roleDetailKeyInput.isDisplayed(), "Role key input is displayed");
        return true;
    }

    public void verifyApplicationRolesTab(String userType) {
        clickOnRolesTab();
        wait(applicationRolesNames, 10000);
        assertTestCase.assertTrue(pageTitle.isDisplayed(), "Page title is displayed");
        assertTestCase.assertTrue(backToApplicationsButton.isDisplayed(), "Back to Applications button is displayed");
        assertTestCase.assertTrue(applicationType.isDisplayed(), "Application type is displayed");
        assertTestCase.assertTrue(providerType.isDisplayed(), "Provider type is displayed");
        assertTestCase.assertTrue(applicationRolesNames.size()>0, "Application roles are displayed");
        assertTestCase.assertTrue(applicationRolesKeys.size()>0, "Application roles keys are displayed");
        if(userType.equals("Admin")) {
            assertTestCase.assertTrue(addRoleButton.isDisplayed(), "Add role button is displayed");
            assertTestCase.assertTrue(deleteButton.isDisplayed(), "Delete button is displayed");
        } else {
            try {
                if(addRoleButton.isDisplayed()) {
                    System.out.println("Add role button shouldn't be displayed");
                    assertTestCase.assertTrue(false, "Add role button shouldn't be displayed");
                }
                if (deleteButton.isDisplayed()) {
                    System.out.println("Delete button shouldn't be displayed");
                    assertTestCase.assertTrue(false, "Delete button shouldn't be displayed");
                }
            } catch (Exception e) {
                System.out.println("Delete button is not displayed as expected");
            }
        }
    }
}
