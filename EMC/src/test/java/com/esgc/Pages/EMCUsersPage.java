package com.esgc.Pages;

import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.CSVUtil;
import com.esgc.Utilities.DateTimeUtilities;
import com.esgc.Utilities.UnzipUtil;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.esgc.Utilities.BrowserUtils.scrollTo;
import static com.esgc.Utilities.BrowserUtils.waitAndClick;

public class EMCUsersPage extends EMCBasePage {
    @FindBy(xpath = "(//input)[1]")
    public WebElement searchInput;

    @FindBy(xpath = "//thead//th")
    public List<WebElement> tableHeaders;

    @FindBy(xpath = "//tbody//tr[1]/*")
    public List<WebElement> firstRow;

    @FindBy(xpath = "//tbody//th/a")
    public List<WebElement> names;

    @FindBy(xpath = "//tbody//td[2]/a")
    public List<WebElement> userNames;

    @FindBy(xpath = "//tbody//td[3]/a")
    public List<WebElement> accountNames;

    @FindBy(xpath = "//tbody//td[4]//span")
    public List<WebElement> providerList;

    @FindBy(xpath = "//tbody//td//input")
    public List<WebElement> userCheckBoxes;

    @FindBy(xpath = "//p[2]")
    public WebElement totalUsers;

    @FindBy(xpath = "//button[@title='Next page']")
    public WebElement nextPageButton;

    @FindBy(xpath = "//button[@title='Previous page']")
    public WebElement previousPageButton;

    @FindBy(tagName = "h3")
    public WebElement pageTitle;

    @FindBy(xpath = "//div[.='Users']/following-sibling::div[1]//button")
    public WebElement optionsMenu;

    @FindBy(xpath = "//ul[@role='menu']/li")
    public List<WebElement> menuOptions;

    @FindBy(xpath = "//button[.='Sync']")
    public WebElement popupSyncButton;

    @FindBy(xpath = "//button[.='Cancel']")
    public WebElement popupCancelButton;

    @FindBy(xpath = "//h2[.='Sync users']")
    public WebElement syncUsersPopupLoader;

    public int getTotalUsers() {
        BrowserUtils.wait(2);
        String totalUsersText = totalUsers.getText();
        System.out.println("totalUsersText = " + totalUsersText);
        String totalUsersNumber = totalUsersText.split(" of ")[1];
        System.out.println("totalUsersNumber = " + totalUsersNumber);
        return Integer.parseInt(totalUsersNumber);
    }

    public void selectUser(String userName) {
        wait(searchInput, 15);
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

    public boolean isOptionsAvailable(String optionName) {
        waitAndClick(optionsMenu, 5);
        List<String> options = BrowserUtils.getElementsText(menuOptions);
        System.out.println("options = " + options);
        clickAwayInBlankArea();
        if(!options.contains(optionName)) {
            clickAwayInBlankArea();
            waitAndClick(optionsMenu, 5);
            options = BrowserUtils.getElementsText(menuOptions);
            System.out.println("options = " + options);
            clickAwayInBlankArea();
        }
        return options.contains(optionName);
    }

    public void exportUsers() {
        UnzipUtil.deleteFilesInDownloadFolder();
        waitAndClick(optionsMenu, 5);
        for (WebElement option : menuOptions) {
            if (option.getText().equals("Export all")) {
                option.click();
                System.out.println("Exporting all users");
                return;
            }
        }
    }

    public boolean verifyUsersExportFileDownloaded() {
        System.out.println("downloadPath() = " + BrowserUtils.downloadPath());
        File dir = new File(BrowserUtils.downloadPath());
        File[] dir_contents = dir.listFiles();
        int counter = 0;
        while(dir_contents == null || dir_contents.length == 0) {
            //System.out.println("Waiting for file to download...");
            BrowserUtils.wait(1);
            dir_contents = dir.listFiles();
            counter++;
            if(counter == 100) {
                System.out.println("File not downloaded");
                return false;
            }
        }
        return Arrays.stream(dir_contents).anyMatch(e -> (e.getName().startsWith(DateTimeUtilities.getCurrentDate("yyyy-MM-dd")) && e.getName().endsWith("users.csv")));
    }

    public boolean verifyExportColumns() {
        CSVUtil exportFile = new CSVUtil(BrowserUtils.downloadPath() , "users.csv");
        List<String> actualColumns = exportFile.getColumnNames();
        System.out.println("actualColumns = " + actualColumns);
        List<String> expectedColumns = Arrays.asList("FirstName","LastName","Email","CreatedAt","AccountName","Roles");
        return actualColumns.containsAll(expectedColumns);
    }

    public boolean verifyExportedNumberOfUsers() {
        CSVUtil exportFile = new CSVUtil(BrowserUtils.downloadPath() , "users.csv");
        int  actualNumberOfUsers = exportFile.getNumberOfRows();
        System.out.println("actualNumberOfUsers = " + actualNumberOfUsers);
        int expectedNumberOfUsers = getTotalUsers();
        System.out.println("expectedNumberOfUsers = " + expectedNumberOfUsers);
        //return true if difference is less than 5
        //return Math.abs(actualNumberOfUsers - expectedNumberOfUsers) < 10;
        return actualNumberOfUsers == expectedNumberOfUsers;
    }

    public void syncUsers() {
        waitAndClick(optionsMenu, 5);
        for (WebElement option : menuOptions) {
            if (option.getText().equals("Sync users")) {
                BrowserUtils.doubleClick(option);
                System.out.println("Syncing all users");
                assertTestCase.assertTrue(popupSyncButton.isDisplayed(), "Sync popup is displayed");
                assertTestCase.assertTrue(popupCancelButton.isDisplayed(), "Cancel button is displayed");
                waitAndClick(popupSyncButton, 5);
                waitForInvisibility(syncUsersPopupLoader, 30);
                return;
            }
        }
    }

    public boolean verifyUser(String userName) {
        return verifyUser(userName, true);
    }

    public boolean verifyUser(String userName, boolean search) {
        if(search) {
            wait(searchInput, 15);
            clear(searchInput);
            searchInput.sendKeys(userName);
        }
        for (WebElement name : names) {
            scrollTo(name);
            if (name.getText().equalsIgnoreCase(userName)) {
                System.out.println("User found: " + userName);
                return true;
            }
        }
        System.out.println("User not found: " + userName);
        return false;
    }

    public void searchUser(String userName) {
        wait(searchInput, 15);
        clear(searchInput);
        searchInput.sendKeys(userName);
    }

    public boolean verifyUserListSorted() {
        int counter = 0;
        while (nextPageButton.isEnabled()){
            counter++;
            List<String> names = getNames();
//            System.out.println("names = " + names);
            List<String> sortedNames = BrowserUtils.specialSort(names);
            if(!names.equals(sortedNames)){
                System.out.println("List is not sorted");
                System.out.println("sortedNames = " + sortedNames);
                System.out.println("counter = " + counter);
                return false;
            }
            waitAndClick(nextPageButton, 5);
            BrowserUtils.wait(1);
        }
        return true;
    }
}






