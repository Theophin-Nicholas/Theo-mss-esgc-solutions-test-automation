package com.esgc.Pages;

import com.esgc.Utilities.BrowserUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EMCRolesPage extends EMCBasePage{

    @FindBy (tagName = "h4")
    public WebElement pageTitle;

    @FindBy (xpath = "//ul//p/preceding-sibling::span")
    public List<WebElement> roles;

    @FindBy (xpath = "//button[.='Create role']")
    public WebElement createRoleButton;


    public boolean verifyRole(String roleName) {
        for (WebElement role : roles) {
            BrowserUtils.scrollTo(role);
            if (role.getText().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    public void selectRole(String roleName) {
        for (WebElement role : roles) {
            BrowserUtils.scrollTo(role);
            if (role.getText().equals(roleName)) {
                role.click();
                break;
            }
        }
    }
}
