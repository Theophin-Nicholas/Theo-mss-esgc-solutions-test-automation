package com.esgc.Pages;

import com.esgc.Utilities.BrowserUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class EMCAccountsPage extends EMCBasePage {
    @FindBy(tagName = "h3")
    public WebElement pageTitle;

    @FindBy(xpath = "//a[.='Create account']")
    public WebElement createAccountButton;

    @FindBy(xpath = "//input")
    public WebElement searchInput;

    @FindBy(xpath = "//button[.='Search']")
    public WebElement searchButton;

    @FindBy(xpath = "//th")
    public List<WebElement> tableHeaders;

    @FindBy(xpath = "//th")
    public List<WebElement> firstRow;

    @FindBy(xpath = "//td//a")
    public List<WebElement> accountNames;

    @FindBy(xpath = "//td[2]")
    public List<WebElement> accountStatuses;

    @FindBy(xpath = "//td[3]")
    public List<WebElement> accountCreatedDates;

    @FindBy(xpath = "//td[4]")
    public List<WebElement> accountCreatedByEmails;

    @FindBy(xpath = "//td[5]")
    public List<WebElement> accountModifiedDates;

    @FindBy(xpath = "//td[6]")
    public List<WebElement> accountModifiedByEmails;

    @FindBy(xpath = "//p[.='Rows per page:']/following-sibling::div//input")
    public WebElement rowsPerPageButton;

    @FindBy(xpath = "//li[.='50']")
    public WebElement option50;

    @FindBy(xpath = "//button[@title='Next page']")
    public WebElement nextPageButton;

    @FindBy(xpath = "//button[@title='Previous page']")
    public WebElement previousPageButton;

    public void search(String accountName) {
        if (!searchInput.isDisplayed()) System.out.println("searchInput is not Displayed!");
        clear(searchInput);
        searchInput.sendKeys(accountName);
        searchButton.click();
        BrowserUtils.waitForVisibility(accountNames,5);
        if (accountNames.size()==0) System.out.println("No account found!");
    }

    public boolean verifyAccount(String accountName) {
        search(accountName);
        for(WebElement account : accountNames){
            if(account.getText().equalsIgnoreCase(accountName)){
                return true;
            }
        }
        System.out.println("Account " + accountName + " not found!");
        return false;
    }
    public boolean verifyAccount(String accountName, boolean status) {
        String statusString = status ? "Active" : "Inactive";
        search(accountName);
        boolean check = true;
        while (check){
            for (int i = 0; i < accountNames.size(); i++) {
                if (accountNames.get(i).getText().equalsIgnoreCase(accountName)) {
                    if (accountStatuses.get(i).getText().equals(statusString)) {
                        return true;
                    }
                }
            }
            if (nextPageButton.isEnabled()){
                System.out.println("Account not found. Searching on next page...");
                nextPageButton.click();
            }else {
                check = false;
            }
        }

        System.out.println("Account " + accountName + " not found!");
        return false;
    }

    public void expandList() {
        BrowserUtils.waitAndClick(rowsPerPageButton,5);
        BrowserUtils.waitAndClick(option50,5);
    }


    public void clickOnAccount(String accountName){
        for (int i = 0; i < accountNames.size(); i++) {
            if (accountNames.get(i).getText().equals(accountName)) {
                accountNames.get(i).click();
                break;
            }
        }
    }


//    public void clickOnFirstAccount() {
//        System.out.println("Clicking on first account = " + accountNames.get(0).getText());
//        BrowserUtils.waitForClickablility(accountNames.get(0), 5).click();
//    }
  
    public void goToAccount(String accountName){
        search(accountName);
        boolean check = true;
        while (check){
            for (int i = 0; i < accountNames.size(); i++) {
                if (accountNames.get(i).getText().equalsIgnoreCase(accountName)) {
                    accountNames.get(i).click();
                    return;
                }
            }
            if (nextPageButton.isEnabled()){
                System.out.println("Account not found. Searching on next page...");
                nextPageButton.click();
            }else {
                check = false;
            }
        }
    }

    public List<String> getAccountNames() {
        //return accountnames as String list
         return accountNames.stream().map(name -> name.getText().toLowerCase()).collect(Collectors.toList());
    }

    public void clickOnCreateAccountButton() {
        System.out.println("Clicking on create account button");
        BrowserUtils.waitForClickablility(createAccountButton, 25).click();
    }

    public void verifyAccountsPage() {
        assertTestCase.assertTrue(pageTitle.getText().equals("Accounts"), "Page title is verified");
        assertTestCase.assertTrue(createAccountButton.isDisplayed(), "Create account button is displayed");
        assertTestCase.assertTrue(searchInput.isDisplayed(), "Search input is displayed");
        assertTestCase.assertTrue(searchButton.isDisplayed(), "Search button is displayed");
        assertTestCase.assertTrue(accountNames.size() > 0, "Account names are displayed");
        assertTestCase.assertTrue(accountStatuses.size() > 0, "Account statuses are displayed");
        assertTestCase.assertTrue(accountCreatedDates.size() > 0, "Account created dates are displayed");
        assertTestCase.assertTrue(accountCreatedByEmails.size() > 0, "Account created by emails are displayed");
        assertTestCase.assertTrue(accountModifiedDates.size() > 0, "Account modified dates are displayed");
        assertTestCase.assertTrue(accountModifiedByEmails.size() > 0, "Account modified by emails are displayed");
    }
}
