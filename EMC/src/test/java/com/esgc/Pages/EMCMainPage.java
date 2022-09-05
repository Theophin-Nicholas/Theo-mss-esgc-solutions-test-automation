package com.esgc.Pages;

import com.esgc.Utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EMCMainPage {

    protected WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
    protected Actions actions = new Actions(Driver.getDriver());

    //============== Common Elements for All Pages

    @FindBy(xpath = "//h6[text()='Entitlements Management Console']")
    public WebElement EMCTitle;

    @FindBy(xpath = "//h6/../button")
    public WebElement openSidePanelButton;

    @FindBy(xpath = "//button[not(@aria-label)]")
    public WebElement closeSidePanelButton;

    @FindBy(xpath = "//*[text()='Applications']")
    public WebElement applicationsButton;

    @FindBy(xpath = "//*[text()='Accounts']")
    public WebElement accountsButton;

    @FindBy(xpath = "//*[text()='Users']")
    public WebElement usersButton;

    @FindBy(xpath = "//*[text()='Configuration']")
    public WebElement configurationButton;


    //============= Common Methods for All Pages

    /**
     * Page Factory Design Pattern is implemented to simplify the process of creating WebElements
     * which is initialized only in EMCMainPAge class.
     */
    public EMCMainPage() {

        PageFactory.initElements(Driver.getDriver(), this);

    }

    public boolean isEMCTitleIsDisplayed() {
        try {
            return EMCTitle.isDisplayed();
        } catch (Exception e) {
            System.out.println("FAILED");
            e.printStackTrace();
            return false;
        }
    }

    public boolean isLabelDisplayed(String label) {
        try {
            WebElement element = Driver.getDriver().findElement(By.xpath("//*[text()='" + label + "']"));
            return element.isDisplayed();
        } catch (Exception e) {
            System.out.println("FAILED");
            e.printStackTrace();
            return false;
        }
    }

    public void openSidePanel() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(openSidePanelButton));
            wait.until(ExpectedConditions.visibilityOf(openSidePanelButton)).click();
        } catch (Exception e) {
            System.out.println("Side Panel is not displayed");

        }
    }

    public void clickApplicationsButton() {
        wait.until(ExpectedConditions.visibilityOf(applicationsButton)).click();
    }

    public void clickAccountsButton() {
        wait.until(ExpectedConditions.visibilityOf(accountsButton)).click();
    }

    public void clickUsersButton() {
        wait.until(ExpectedConditions.visibilityOf(usersButton)).click();
    }

    public void clickConfigurationsButton() {
        wait.until(ExpectedConditions.visibilityOf(configurationButton)).click();
    }
}
