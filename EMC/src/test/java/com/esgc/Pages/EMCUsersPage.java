package com.esgc.Pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static com.esgc.Utilities.BrowserUtils.scrollTo;

public class EMCUsersPage extends EMCBasePage {
    @FindBy(xpath = "(//input)[1]")
    public WebElement searchInput;

    @FindBy(xpath = "//tbody//th/a")
    public List<WebElement> names;

    @FindBy(xpath = "//tbody//td[2]/a")
    public List<WebElement> userNames;

    @FindBy(xpath = "//tbody//td[3]/a")
    public List<WebElement> accountNames;

    @FindBy(xpath = "//p[2]")
    public WebElement totalUsers;

    @FindBy(tagName = "h4")
    public WebElement pageTitle;

    public int getTotalUsers() {
        String totalUsersText = totalUsers.getText();
        String totalUsersNumber = totalUsersText.substring(totalUsersText.indexOf("of") + 3);
        return Integer.parseInt(totalUsersNumber);
    }

    public void selectUser(String userName) {
        clear(searchInput);
        searchInput.sendKeys(userName);
        for (WebElement name : names) {
            scrollTo(name);
            if (name.getText().equals(userName)) {
                System.out.println("User found: " + userName);
                name.click();
                return;
            }
        }
        System.out.println("User not found: " + userName);
    }

    public List<String> getNames() {
        return names.stream().map(WebElement::getText).collect(java.util.stream.Collectors.toList());
    }

    public List<String> getUserNames() {
        return userNames.stream().map(WebElement::getText).collect(java.util.stream.Collectors.toList());
    }

    public List<String> getAccountNames() {
        return accountNames.stream().map(WebElement::getText).collect(java.util.stream.Collectors.toList());
    }

    public void clear(WebElement element) {
        while (element.getAttribute("value").length() > 0) {
            element.sendKeys(Keys.BACK_SPACE);
        }
        element.sendKeys(Keys.TAB);
    }

    public void createUser() {
    }
}






