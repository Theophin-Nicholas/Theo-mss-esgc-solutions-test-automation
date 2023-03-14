package com.esgc.Pages;

import com.esgc.Utilities.BrowserUtils;
import com.fasterxml.jackson.core.JsonFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EMCApplicationCreatePage extends EMCBasePage {

    @FindBy(tagName = "h5")
    public WebElement pageTitle;

    @FindBy (name = "key")
    public WebElement applicationKeyInput;

    @FindBy (name = "name")
    public WebElement applicationNameInput;

    @FindBy (name = "url")
    public WebElement applicationUrlInput;

    @FindBy (xpath = "//button[.='Save']")
    public WebElement saveButton;

    @FindBy (xpath = "//button[.='Cancel']")
    public WebElement cancelButton;

    @FindBy (xpath = "//button[.='Next']")
    public WebElement nextButton;

    @FindBy (xpath = "//p[.='key is a required field']")
    public WebElement keyRequiredTag;

    @FindBy (xpath = "//p[.='Application Name is a required field']")
    public WebElement applicationNameRequiredTag;

    @FindBy (xpath = "//p[.='Required']")
    public WebElement applicationURLRequiredTag;

    @FindBy (xpath = "//p[.='Must be a valid URL']")
    public WebElement mustBeAValidURLTag;

    @FindBy (xpath = "//p[.='Must be 5 characters or more']")
    public WebElement mustBe5CharactersTag;

//Create a new application elements
    @FindBy (xpath = "//h2")
    public WebElement createNewApplicationPageTitle;

    @FindBy (xpath = "//input[@value='SinglePageApplication']")
    public WebElement singlePageApplicationRadioButton;

    @FindBy (xpath = "//input[@value='ExternalApplication']")
    public WebElement externalApplicationRadioButton;

    @FindBy (xpath = "//input[@value='WebApplication']")
    public WebElement webApplicationRadioButton;

    @FindBy (xpath = "//p[.='Single-Page Application']")
    public WebElement singlePageApplicationTitle;

    @FindBy (xpath = "//p[.='External Application']")
    public WebElement externalApplicationTitle;

    @FindBy (xpath = "//p[.='Web Application']")
    public WebElement webApplicationTitle;

    public void createApplication(String applicationKey, String applicationName, String applicationUrl) {
        applicationKeyInput.sendKeys(applicationKey.toLowerCase());
        applicationNameInput.sendKeys(applicationName);
        applicationUrlInput.sendKeys(applicationUrl);
        BrowserUtils.waitForClickablility(saveButton, 5).click();
    }

    public void clickOnCancelButton() {
        BrowserUtils.waitForClickablility(cancelButton, 5).click();
    }
}

