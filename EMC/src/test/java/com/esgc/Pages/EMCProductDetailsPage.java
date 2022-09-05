package com.esgc.Pages;

import com.esgc.Utilities.BrowserUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EMCProductDetailsPage extends EMCBasePage {
    //Products tab elements - Add product pop up elements

    @FindBy (xpath = "//h4")
    public WebElement pageTitle;

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

    @FindBy (xpath = "//span[starts-with(.,'Created')]")
    public WebElement createdInfo;

    @FindBy (xpath = "//span[starts-with(.,'Modified')]")
    public WebElement modifiedInfo;

    @FindBy (xpath = "//button[.='Edit']")
    public WebElement editedButton;

    @FindBy (xpath = "//span[.='Back to Application Products']")
    public WebElement backToApplicationProductsButton;

    @FindBy (xpath = "//ul//li")
    public List<WebElement> dropDownOptions;

    @FindBy (xpath = "//button[.='Save']")
    public WebElement saveProductButton;

    @FindBy (xpath = "//button[.='Cancel']")
    public WebElement cancelProductButton;


    public boolean verifyDetails(String productName) {
        BrowserUtils.waitForVisibility(pageTitle, 5);
        if(!pageTitle.isDisplayed()) {
            System.out.println("Page title is not displayed");
            return false;
        }
        if(!productNameInput.getAttribute("value").equals(productName)) {
            System.out.println("Product name is not correct");
            return false;
        }
        if(productKeyInput.getAttribute("value").isEmpty()) {
            System.out.println("Product key is not correct");
            return false;
        }
        if(productCodeInput.getAttribute("value").isEmpty()) {
            System.out.println("Product code is not correct");
            return false;
        }
        if(productSfdcIdInput.getAttribute("value").isEmpty()) {
            System.out.println("Product sfdc id is not correct");
            return false;
        }
        if(productTypeDropdown.getAttribute("value").isEmpty()) {
            System.out.println("Product type is not correct");
            return false;
        }
        if(productDeliveryChannelDropdown.getAttribute("value").isEmpty()) {
            System.out.println("Product delivery channel is not correct");
            return false;
        }
        if(productPricingModelDropdown.getAttribute("value").isEmpty()) {
            System.out.println("Product pricing model is not correct");
            return false;
        }
        if(!createdInfo.isDisplayed()) {
            System.out.println("Created info is not displayed");
            return false;
        }
        if(!modifiedInfo.isDisplayed()) {
            System.out.println("Modified info is not displayed");
            return false;
        }
        return true;
    }

    public void clickOnEditButton() {
        BrowserUtils.waitForClickablility(editedButton, 5).click();
        System.out.println("Clicked on Edit button");
    }

    public boolean editProduct(String productName, String productCode, String productSfdcId, String productType, String productDeliveryChannel, String productPricingModel) {

        try{

            BrowserUtils.waitForVisibility(pageTitle, 5);
            clearAndSendKeys(productNameInput, productName);

            if(productKeyInput.isEnabled()){
                System.out.println("Product key should not be editable");
                return false;
            }
            clearAndSendKeys(productCodeInput, productCode);
            clearAndSendKeys(productSfdcIdInput, productSfdcId);

            clickAndSelect(productTypeDropdown, productType);
            clickAndSelect(productDeliveryChannelDropdown, productDeliveryChannel);
            clickAndSelect(productPricingModelDropdown, productPricingModel);
            BrowserUtils.waitForClickablility(saveProductButton, 5).click();
            //BrowserUtils.waitForInvisibility(addProductPopUpTitle, 5);
            return true;
        } catch (Exception e){
            System.out.println("Error while editing product");
            e.printStackTrace();
            return false;
        }

    }

    private void clearAndSendKeys(WebElement element, String productName) {
        while (element.getAttribute("value").length() > 0) {
            element.sendKeys(Keys.BACK_SPACE);
        }
        element.sendKeys(productName);
    }

    public boolean clickAndSelect(WebElement button, String value) {

        BrowserUtils.waitForClickablility(button,5).click();
        for (WebElement element : dropDownOptions) {
            if (element.getText().toLowerCase().equals(value.toLowerCase())) {
                System.out.println("Found " + value + " in the list");
                BrowserUtils.waitForClickablility(element, 10).click();
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
}
