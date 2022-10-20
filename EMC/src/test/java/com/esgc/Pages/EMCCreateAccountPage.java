package com.esgc.Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EMCCreateAccountPage extends EMCBasePage {
    @FindBy (xpath = "//h4")
    public WebElement pageTitle;

    @FindBy (name = "name")
    public WebElement accountNameInput;

    @FindBy (name = "status")
    public WebElement statusCheckbox;

    @FindBy (xpath = "//div[@name='subscriberType']//input")
    public WebElement subscriberInput;

    @FindBy (xpath = "//ul/li")
    public List<WebElement> subscriberTypeList;

    @FindBy (name="contractStartDate")
    public WebElement startDateInput;

    @FindBy (name="contractEndDate")
    public WebElement endDateInput;

    @FindBy (xpath = "//button[.='Save']")
    public WebElement saveButton;

    @FindBy (xpath = "//button[.='Cancel']")
    public WebElement cancelButton;

    @FindBy (xpath = "//div[@id='notistack-snackbar']")
    public WebElement accountCreatedMessage;

    @FindBy (xpath = "//button[.='Clear']")
    public WebElement dateClearButton;

    @FindBy (xpath = "//button[.='Cancel']")
    public WebElement dateCancelButton;

    @FindBy (xpath = "//button[.='OK']")
    public WebElement dateOkButton;

    @FindBy (xpath = "//p[.='Required']")
    public WebElement requiredTag;





    //METHODS
    public void createAccount(String accountName, boolean status) {
        if (accountName.length()<5) accountName+="Test";
        accountNameInput.sendKeys(accountName);
        if (!status) statusCheckbox.click();
        saveButton.click();
    }
}
