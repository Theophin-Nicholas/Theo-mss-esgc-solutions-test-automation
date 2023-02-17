package com.esgc.Pages;

import com.esgc.Utilities.BrowserUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class EMCApplicationsPage extends EMCBasePage{
    @FindBy(tagName = "h3")
    public WebElement pageTitle;

    @FindBy (xpath = "//tbody//td[1]//a")
    public List<WebElement> applications;

    @FindBy (xpath = "//tbody//td[2]")
    public List<WebElement> applicationLinks;

    @FindBy (xpath = "//button[.='Create application']")
    public WebElement createApplicationButton;

    @FindBy (xpath = "//button[.='Create application']")
    public List<WebElement> createApplicationButtonList;

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
                return;
            }
        }
        System.out.println("Application not found: " + applicationName);
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

    public void clickOnFirstApplication() {
        System.out.println("Clicking on first account = " + applications.get(0).getText());
        BrowserUtils.waitForClickablility(applications.get(0), 5).click();
    }

    public void clickOnCreateApplicationButton() {
        BrowserUtils.waitForClickablility(createApplicationButton, 5).click();
        System.out.println("Clicked on create application button");
    }

    public void createApplication(String applicationName, String applicationKey, String applicationUrl, String appType) {
        clickOnCreateApplicationButton();
        EMCApplicationCreatePage createPage = new EMCApplicationCreatePage();
        switch (appType.toLowerCase()){
            case "single-page":
                BrowserUtils.doubleClick(createPage.singlePageApplicationRadioButton);
                assertTestCase.assertTrue(createPage.singlePageApplicationRadioButton.isSelected(), "Single Page Application Radio Button is selected");
                break;
            case "external":
                BrowserUtils.doubleClick(createPage.externalApplicationRadioButton);
                assertTestCase.assertTrue(createPage.externalApplicationRadioButton.isSelected(), "External Application Radio Button is selected");
                break;
            case "web":
                BrowserUtils.doubleClick(createPage.webApplicationRadioButton);
                assertTestCase.assertTrue(createPage.webApplicationRadioButton.isSelected(), "Web Application Radio Button is selected");
                break;
        }

        assertTestCase.assertTrue(createPage.nextButton.isEnabled(), "Next Button is enabled");
        BrowserUtils.waitForClickablility(createPage.nextButton, 10).click();
        createPage.applicationNameInput.sendKeys(applicationName);
        createPage.applicationKeyInput.sendKeys(applicationKey);
        createPage.applicationUrlInput.sendKeys(applicationUrl+Keys.TAB);
        assertTestCase.assertTrue(createPage.saveButton.isEnabled(), "Save Button is enabled");
        BrowserUtils.waitForClickablility(createPage.saveButton, 10).click();
        wait(notification, 10);
    }

    public void clickOnCancelButton() {
        BrowserUtils.waitForClickablility(cancelButton, 5).click();
        System.out.println("Clicked on cancel button");
    }
}

