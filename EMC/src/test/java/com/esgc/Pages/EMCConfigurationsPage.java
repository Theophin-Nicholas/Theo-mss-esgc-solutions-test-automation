package com.esgc.Pages;

import com.esgc.Utilities.BrowserUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EMCConfigurationsPage extends EMCBasePage {

    @FindBy(xpath = "//h4")
    public WebElement pageTitle;

    @FindBy(xpath = "//h6[.='Users']")
    public WebElement usersButton;

    @FindBy(xpath = "//h6[.='Permission Roles']")
    public WebElement permissionRolesButton;

    @FindBy(xpath = "//h6[.='Site Settings']")
    public WebElement sitesSettingsButton;

    @FindBy(xpath = "//p[.='User Settings']")
    public WebElement sitesSettingsTag;

    @FindBy(xpath = "//p[.='Miscellaneous']")
    public WebElement miscellaneousTag;

    public void clickOnPermissionRoles() {
        System.out.println("clickOnPermissionRoles");
        BrowserUtils.waitForClickablility(permissionRolesButton, 5).click();
    }
}
