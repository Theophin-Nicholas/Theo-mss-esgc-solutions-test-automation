package com.esgc.Pages;

import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

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
    public void clear(WebElement element) {
        while (!element.getAttribute("value").isEmpty()) {
            element.sendKeys(Keys.BACK_SPACE);
        }
    }
    public void wait(WebElement element, int seconds) {
        for (int i = 0; i < seconds; i++) {
            try {
                if (element.isDisplayed()) {
                    return;
                }
            } catch (Exception e) {
                BrowserUtils.wait(1);
            }
        }
        System.out.println("Element is not displayed after " + seconds + " seconds");
    }
    public void wait(List<WebElement> elements, int seconds) {
        for (int i = 0; i < seconds; i++) {
            try {
                if (elements.size() > 0) {
                    System.out.println("Found the list after " + i + " seconds");
                    return;
                }
            } catch (Exception e) {
                BrowserUtils.wait(1);
            }
        }
        System.out.println("Element is not displayed after " + seconds + " seconds");
    }
}
