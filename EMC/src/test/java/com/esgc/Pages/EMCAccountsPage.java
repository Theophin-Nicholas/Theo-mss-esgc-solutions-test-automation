package com.esgc.Pages;

import com.esgc.Utilities.BrowserUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EMCAccountsPage extends EMCBasePage {
    @FindBy(tagName = "h4")
    public WebElement pageTitle;

    @FindBy(xpath = "//button[.='Create account']")
    public WebElement createAccountButton;

    @FindBy(xpath = "//input")
    public WebElement searchInput;

    @FindBy(xpath = "//input/../following-sibling::button")
    public WebElement searchButton;

    @FindBy(xpath = "//td//a")
    public List<WebElement> accountNames;

    @FindBy(xpath = "//td[2]//p")
    public List<WebElement> accountStatuses;

    @FindBy(xpath = "//td[3]//p")
    public List<WebElement> accountCreatedDates;

    @FindBy(xpath = "//td[4]//p")
    public List<WebElement> accountCreatedByEmails;

    @FindBy(xpath = "//td[5]//p")
    public List<WebElement> accountModifiedDates;

    @FindBy(xpath = "//td[6]//p")
    public List<WebElement> accountModifiedByEmails;

    public void search(String accountName) {
        System.out.println("searchInput.isDisplayed() = " + searchInput.isDisplayed());
        searchInput.clear();
        searchInput.sendKeys(accountName);
        searchButton.click();
        System.out.println("Searching for account: " + accountName);
        System.out.println(accountNames.size() + " accounts found");
    }

    public boolean findAccount(String accountName) {
        for (int i = 0; i < accountNames.size(); i++) {
            if (accountNames.get(i).getText().equals(accountName)) {
                return true;
            }
        }
        return false;
    }

    public void clickOnAccount(String accountName){
        for (int i = 0; i < accountNames.size(); i++) {
            if (accountNames.get(i).getText().equals(accountName)) {
                accountNames.get(i).click();
                break;
            }
        }
    }


    public void clickOnFirstAccount() {
        System.out.println("Clicking on first account = " + accountNames.get(0).getText());
        BrowserUtils.waitForClickablility(accountNames.get(0), 5).click();
    }
  
    public void goToAccount(String accountName){
        while (searchInput.getAttribute("value").length() > 0) {
            searchInput.sendKeys(Keys.BACK_SPACE);
        }
        searchInput.sendKeys(accountName);
        for (WebElement account : accountNames) {
            if (account.getText().equalsIgnoreCase(accountName)) {
                account.click();
                break;
            }
        }
    }

    public List<String> getAccountNames() {
        //return accountnames as String list
        return accountNames.stream().map(WebElement::getText).collect(java.util.stream.Collectors.toList());
    }
}
