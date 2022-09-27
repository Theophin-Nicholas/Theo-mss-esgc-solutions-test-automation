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
        if (!searchInput.isDisplayed()) System.out.println("searchInput is not Displayed!");
        if(!searchInput.getAttribute("value").isEmpty()) clear(searchInput);
        searchInput.sendKeys(accountName);
        searchButton.click();
        if (accountNames.size()==0) System.out.println("No account found!");
    }

    public boolean verifyAccount(String accountName) {
        for (int i = 0; i < accountNames.size(); i++) {
            if (accountNames.get(i).getText().equals(accountName)) {
                return true;
            }
        }
        return false;
    }
    public boolean verifyAccount(String accountName, boolean status) {
        String statusString = status ? "Active" : "Inactive";
        for (int i = 0; i < accountNames.size(); i++) {
            if (accountNames.get(i).getText().equals(accountName)) {
                if (accountStatuses.get(i).getText().equals(statusString)) {
                    return true;
                }
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

    public void clickOnCreateAccountButton() {
        System.out.println("Clicking on create account button");
        BrowserUtils.waitForClickablility(createAccountButton, 25).click();
    }
}
