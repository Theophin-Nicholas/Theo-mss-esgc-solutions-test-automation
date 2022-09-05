package com.esgc.Pages;

import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public abstract class EMCBasePage {
    @FindBy(id = "notistack-snackbar")
    public WebElement notification;
    public EMCBasePage() {
        PageFactory.initElements(Driver.getDriver(),this);
    }
    public void clickAwayInBlankArea() {
        Driver.getDriver().findElement(By.xpath("(//body//div//button)[2]")).click();
        BrowserUtils.wait(2);
    }
}
