package com.esgc.Pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EMCCreateRolePage extends EMCBasePage {
    @FindBy(tagName = "h4")
    public WebElement pageTitle;

    @FindBy(name = "key")
    public WebElement keyInput;

    @FindBy(name = "name")
    public WebElement nameInput;

    @FindBy(xpath = "//input[@name='description']")
    public WebElement descriptionInput;

    @FindBy(id = "permissions")
    public WebElement permissionsInput;

    @FindBy(tagName = "li")
    public List<WebElement> permissionsList;

    @FindBy(xpath = "//button[.='Back']")
    public WebElement backButton;

    @FindBy(xpath = "//button[.='Save']")
    public WebElement saveButton;

    @FindBy(xpath = "//span[.='General Information']")
    public WebElement generalInfoTag;

    @FindBy(xpath = "//span[.='Permissions']")
    public WebElement permissionsLabel;

    @FindBy(xpath = "//p[.='key is a required field']")
    public WebElement keyIsRequiredError;

    @FindBy(xpath = "//p[.='name is a required field']")
    public WebElement nameIsRequiredError;

    @FindBy(xpath = "//p[.='Must be a valid key xxx-xxx']")
    public WebElement mustBeAValidKeyError;

    //Methods
    public void assignPermission(String permission) {
        permissionsInput.click();
        for (WebElement element : permissionsList) {
            if (element.getText().equals(permission)) {
                element.click();
                System.out.println(element.getText()+" is selected");
                element.sendKeys(Keys.ESCAPE);
                break;
            }
        }
    }
}
