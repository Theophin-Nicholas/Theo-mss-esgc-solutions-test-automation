package com.esgc.Pages;

import com.esgc.Utilities.BrowserUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class EMCApplicationsPage extends EMCBasePage{
    @FindBy(tagName = "h4")
    public WebElement pageTitle;

    @FindBy (xpath = "//tbody//td[1]//a")
    public List<WebElement> applications;

    @FindBy (xpath = "//tbody//td[2]")
    public List<WebElement> applicationLinks;

    @FindBy (xpath = "//button[.='Create application']")
    public WebElement createApplicationButton;

    @FindBy (tagName = "input")
    public WebElement searchInput;

    @FindBy (xpath = "//button[@type='submit']")
    public WebElement searchButton;
    //Create Application Page Objects

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



    public List<String> getApplicationNames() {
        List<String> names = new ArrayList<String>();
        for (WebElement element : applications) {
            names.add(element.getText());
        }
        return names;
    }

    public void selectApplication(String applicationName ) {
        clearSearchInput();
        searchInput.sendKeys(applicationName);
        for (WebElement element : applications) {
            if (element.getText().equals(applicationName)) {
                System.out.println("Application found: " + applicationName);
                System.out.println("Application name: " + element.getText());
                element.click();
                break;
            }
        }
    }
    public void clearSearchInput() {
        while (!searchInput.getAttribute("value").isEmpty()) {
            searchInput.sendKeys(Keys.BACK_SPACE);
        }
    }
    public void selectApplicationLink(String applicationName ) {
        int counter = 0;
        for (WebElement element : applications) {
            if (element.getText().equals(applicationName)) {
                applicationLinks.get(counter).click();
                break;
            }
            counter++;
        }
    }
    public void selectApplication(int applicationIndex) {
        BrowserUtils.waitForClickablility(applications.get(applicationIndex), 5);
        applications.get(applicationIndex).click();
    }

    public void selectApplicationLink(int applicationIndex) {
        applicationLinks.get(applicationIndex).click();
    }

    public List<WebElement> searchApplication(String applicationName) {
        while (!searchInput.getAttribute("value").isEmpty()) {
            searchInput.sendKeys(Keys.BACK_SPACE);
        }
        searchInput.sendKeys(applicationName);
        searchButton.click();
        return applications;
    }

    public boolean verifyApplication(String applicationName) {
        clearSearchInput();
        List<WebElement> applications = searchApplication(applicationName);
        for (WebElement element : applications) {
            if (element.getText().equals(applicationName)) {
                System.out.println("Application found: " + applicationName);
                System.out.println("Application name: " + element.getText());
                return true;
            }
        }
        return false;
    }

    public void createApplication(String applicationName) {
        createApplicationButton.click();
        applicationKeyInput.sendKeys(applicationName.toLowerCase());
        applicationNameInput.sendKeys(applicationName);
        applicationUrlInput.sendKeys("https://" + applicationName + ".com");
        BrowserUtils.waitForClickablility(saveButton,10).click();
    }
    public void createApplication(String applicationName,String applicationLink) {
        createApplicationButton.click();
        applicationKeyInput.sendKeys(applicationName.toLowerCase());
        applicationNameInput.sendKeys(applicationName);
        applicationUrlInput.sendKeys(applicationLink);
        BrowserUtils.waitForClickablility(saveButton,10).click();
    }
    public void search(String applicationName) {
        System.out.println("searchInput.isDisplayed() = " + searchInput.isDisplayed());
        searchInput.clear();
        searchInput.sendKeys(applicationName);
        searchButton.click();
        System.out.println("Searching for account: " + applicationName);
        System.out.println(applications.size() + " accounts found");
    }

    public void clickOnFirstApplication() {
        System.out.println("Clicking on first account = " + applications.get(0).getText());
        BrowserUtils.waitForClickablility(applications.get(0), 5).click();
    }

    public void clickOnCreateApplicationButton() {
        BrowserUtils.waitForClickablility(createApplicationButton, 5).click();
        System.out.println("Clicked on create application button");
    }
}

