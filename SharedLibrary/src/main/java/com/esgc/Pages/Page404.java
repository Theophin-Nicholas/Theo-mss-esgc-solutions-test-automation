package com.esgc.Pages;

import com.esgc.Reporting.CustomAssertion;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Page404 {

    public Page404() {
        PageFactory.initElements(Driver.getDriver(),this);
    }

    CustomAssertion assertTestCase = new CustomAssertion();

    @FindBy(xpath = "//header//li")
    public WebElement pageHeader;

    @FindBy(xpath = "//h2")
    public WebElement pageTitle;

    @FindBy(xpath = "//h2/../following-sibling::div")
    public WebElement pageStatement;

    @FindBy(xpath = "//button")
    public WebElement returnToMoodysESG360Button;

    public void verify404Page() {
        BrowserUtils.waitForVisibility(pageHeader, 15);
        assertTestCase.assertTrue(pageHeader.isDisplayed(), "Page header is displayed");
        assertTestCase.assertTrue(pageTitle.isDisplayed(), "Page title is displayed");
        assertTestCase.assertTrue(pageStatement.isDisplayed(), "Page statement is displayed");
        assertTestCase.assertTrue(returnToMoodysESG360Button.isDisplayed(), "Return to Moody's ESG 360 button is displayed");
        assertTestCase.assertTrue(Driver.getDriver().getCurrentUrl().endsWith("404"), "Current URL ends with 404");
        BrowserUtils.waitForClickablility(returnToMoodysESG360Button, 15).click();
    }
}
