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

    @FindBy (xpath = "//thead//th")
    public List<WebElement> tableHeaders;

    @FindBy (xpath = "//tbody/tr[1]/*")
    public List<WebElement> firstRow;

    @FindBy (xpath = "//tbody//td[1]//input")
    public List<WebElement> checkBoxes;

    @FindBy (xpath = "//tbody//th/a")
    public List<WebElement> applications;

    @FindBy (xpath = "//tbody//td[2]")
    public List<WebElement> applicationLinks;

    @FindBy (xpath = "//tbody//td[3]")
    public List<WebElement> providerList;

    @FindBy (xpath = "//tbody//td[4]")
    public List<WebElement> typeList;

    @FindBy (xpath = "//tbody//td[5]")
    public List<WebElement> createdInfoList;

    @FindBy (xpath = "//tbody//td[6]")
    public List<WebElement> createdByList;

    @FindBy (xpath = "//tbody//td[7]")
    public List<WebElement> modifiedList;

    @FindBy (xpath = "//button[.='Create application']")
    public WebElement createApplicationButton;

    @FindBy (xpath = "//button[.='Create application']")
    public List<WebElement> createApplicationButtonList;

    @FindBy (tagName = "input")
    public WebElement searchInput;

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

    @FindBy (xpath = "//main//hr/..//button")
    public WebElement deleteButton;

    @FindBy (xpath = "//div[@role='dialog']//button[.='Delete']")
    public WebElement deletePopupDeleteButton;

    @FindBy (xpath = "//div[@role='dialog']//button[.='Cancel']")
    public WebElement deletePopupCancelButton;

    @FindBy(xpath = "//button[@title='Next page']")
    public WebElement nextPageButton;

    @FindBy(xpath = "//button[@title='Previous page']")
    public WebElement previousPageButton;


    public List<String> getApplicationNames() {
        List<String> names = new ArrayList<String>();
        for (WebElement element : applications) {
            names.add(element.getText());
        }
        return names;
    }

    public void goToApplication(String applicationName) {
        searchApplication(applicationName);
        boolean check = true;
        while (check){
            for (WebElement element : applications) {
                if (element.getText().equals(applicationName)) {
                    System.out.println("Application found: " + applicationName);
                    System.out.println("Application name: " + element.getText());
                    element.click();
                    return;
                }
            }
            if (nextPageButton.isEnabled()) {
                System.out.println("Application not found in current page, going to next page");
                nextPageButton.click();
            } else {
                check = false;
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
    public void goToApplication(int applicationIndex) {
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
        BrowserUtils.wait(1);
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

    public boolean verifyApplication(String applicationName, boolean isSearch) {
        if (isSearch) {
            searchApplication(applicationName);
        }
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
        BrowserUtils.waitForVisibility(createPage.notification, 5);
        assertTestCase.assertTrue(createPage.notification.isDisplayed(), "Notification is displayed");
        System.out.println("Notification text: " + createPage.notification.getText());
        if(notification.getText().contains("success")){
            System.out.println("Application created successfully");
            EMCApplicationDetailsPage detailsPage = new EMCApplicationDetailsPage();
            detailsPage.verifyApplicationDetails();
            detailsPage.backToApplicationsButton.click();
        }else{
            System.out.println("Application creation failed");
        }
    }

    public void clickOnCancelButton() {
        BrowserUtils.waitForClickablility(cancelButton, 5).click();
        System.out.println("Clicked on cancel button");
    }

    public void clickOnDeleteButton() {
        System.out.println("Clicking on delete button");
        BrowserUtils.scrollTo(deleteButton).click();
        BrowserUtils.waitForVisibility(deletePopupDeleteButton, 5);
    }

    public void deleteApplication(String applicationName) {
        selectApplication(applicationName);
        clickOnDeleteButton();
        BrowserUtils.waitForClickablility(deletePopupDeleteButton, 5).click();
        wait(notification, 10);
    }

    private void selectApplication(String applicationName) {
        searchApplication(applicationName);
        for (WebElement element : applications) {
            if (element.getText().equals(applicationName)) {
                checkBoxes.get(applications.indexOf(element)).click();
                return;
            }
        }
    }
}

