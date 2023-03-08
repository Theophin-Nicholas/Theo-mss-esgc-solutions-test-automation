package com.esgc.Pages;

import com.esgc.Reporting.CustomAssertion;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class EMCMainPage extends EMCBasePage{

    protected WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
    protected Actions actions = new Actions(Driver.getDriver());
    CustomAssertion assertTestCase = new CustomAssertion();

    //============== Common Elements for All Pages

    @FindBy(xpath = "//h6[text()='Entitlements Management Console']")
    public WebElement EMCTitle;

    @FindBy(xpath = "//h6/../button")
    public WebElement openSidePanelButton;

    @FindBy(xpath = "//button[not(@aria-label)]")
    public WebElement closeSidePanelButton;

    @FindBy(xpath = "//ul//a")
    public List<WebElement> menuOptions;

    @FindBy(xpath = "//a[.='Applications']")
    public WebElement applicationsButton;

    @FindBy(xpath = "//a[.='Accounts']")
    public WebElement accountsButton;

    @FindBy(xpath = "//a[.='Users']")
    public WebElement usersButton;

    @FindBy(xpath = "//a[.='Configuration']")
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

    public void goToApplicationsPage() {
        try {
            openSidePanel();
        } catch (Exception e) {
            System.out.println("Side panel is not opened");
        }
        System.out.println("Side panel is opened");
        clickApplicationsButton();
        System.out.println("Applications button is clicked");
    }
    public void goToAccountsPage() {
        try {
            openSidePanel();
            System.out.println("Side panel is opened");
            clickAccountsButton();
            System.out.println("Accounts button is clicked");
        } catch (Exception e) {
            System.out.println("Side panel is not opened");
        }

    }
    public void goToUsersPage() {
        try {
            openSidePanel();
        } catch (Exception e) {
            System.out.println("Side panel is not opened");
        }
        System.out.println("Side panel is opened");
        clickUsersButton();
        System.out.println("Users button is clicked");
    }
    public void goToConfigurationsPage() {
        try {
            openSidePanel();
        } catch (Exception e) {
            System.out.println("Side panel is not opened");
        }
        System.out.println("Side panel is opened");
        clickConfigurationsButton();
        System.out.println("Configurations button is clicked");
    }

    public void verifyAdminRoleUsersMenuOptions() {
        try {
            openSidePanel();
        } catch (Exception e) {
            System.out.println("Side panel is not opened");
        }
        System.out.println("Side panel is opened");
        try {
            wait.until(ExpectedConditions.visibilityOf(menuOptions.get(0)));
            System.out.println("Menu options are displayed");
        } catch (Exception e) {
            System.out.println("Menu options are not displayed");
        }
        List<String> menuOptionsList = BrowserUtils.getElementsText(menuOptions);
        List<String> expectedMenuOptionsList = Arrays.asList("Applications", "Accounts", "Users", "Configuration");
        assertTestCase.assertTrue(menuOptionsList.containsAll(expectedMenuOptionsList), "Menu options are not displayed");
    }

    public void verifyFulfillmentRoleUsersMenuOptions() {
        try {
            openSidePanel();
        } catch (Exception e) {
            System.out.println("Side panel is not opened");
        }
        System.out.println("Side panel is opened");
        try {
            wait.until(ExpectedConditions.visibilityOf(menuOptions.get(0)));
            System.out.println("Menu options are displayed");
        } catch (Exception e) {
            System.out.println("Menu options are not displayed");
        }
        wait(menuOptions, 60);
        List<String> menuOptionsList = BrowserUtils.getElementsText(menuOptions);
        List<String> expectedMenuOptionsList = Arrays.asList("Applications", "Accounts", "Users");
        assertTestCase.assertTrue(menuOptionsList.containsAll(expectedMenuOptionsList), "Menu options are displayed");
        assertTestCase.assertFalse(menuOptionsList.contains("Configuration"), "Configuration menu option is displayed");
    }
}
