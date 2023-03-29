package com.esgc.Pages;

import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.DateTimeUtilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EMCCreateAccountPage extends EMCBasePage {
    @FindBy (xpath = "//h4")
    public WebElement pageTitle;

    @FindBy (name = "name")
    public WebElement accountNameInput;

    @FindBy (xpath = "//input[@name='status']")
    public WebElement statusCheckbox;

    @FindBy (xpath = "//div[@name='subscriberType']//input")
    public WebElement subscriberInput;

    @FindBy (xpath = "//ul/li")
    public List<WebElement> subscriberTypeList;

    @FindBy (name="contractStartDate")
    public WebElement startDateInput;

    @FindBy (name="contractEndDate")
    public WebElement endDateInput;

    @FindBy (xpath = "//button[.='Save']")
    public WebElement saveButton;

    @FindBy (xpath = "//button[.='Cancel']")
    public WebElement cancelButton;

    @FindBy (xpath = "//div[@id='notistack-snackbar']")
    public WebElement accountCreatedMessage;

    @FindBy (xpath = "//button[.='Clear']")
    public WebElement dateClearButton;

    @FindBy (xpath = "//button[.='Cancel']")
    public WebElement dateCancelButton;

    @FindBy (xpath = "//button[.='OK']")
    public WebElement dateOkButton;

    @FindBy (xpath = "//p[.='Required']")
    public WebElement requiredTag;





    //METHODS
    public void createAccount(String accountName, boolean status) {
        if (accountName.length()<5) accountName+="Test";
        accountNameInput.sendKeys(accountName);
        if (!status) statusCheckbox.click();
        saveButton.click();
    }

    public void createAccount(String accountName, boolean status, String subscriberType, String startDate, String endDate) {
        if (accountName.length()<5) accountName+="Test";
        accountNameInput.sendKeys(accountName);
        if (!status) statusCheckbox.click();
        subscriberInput.click();
        BrowserUtils.waitFor(1);
        for (WebElement each : subscriberTypeList) {
            if (each.getText().equals(subscriberType)) {
                each.click();
                break;
            }
        }
        assertTestCase.assertEquals(subscriberInput.getAttribute("value"), subscriberType, "Subscriber type is set to " + subscriberType);
        startDateInput.sendKeys(startDate);
        assertTestCase.assertEquals(startDateInput.getAttribute("value"), startDate, "Start date is set to " + startDate);
        endDateInput.sendKeys(endDate);
        assertTestCase.assertEquals(endDateInput.getAttribute("value"), endDate, "End date is set to " + endDate);
        assertTestCase.assertTrue(saveButton.isEnabled(), "Save button is enabled");
        saveButton.click();
    }

    //this verifies method is for empty account creation page
    public void verifyCreateAccountPage() {
        //verify elements displayed
        assertTestCase.assertTrue(pageTitle.isDisplayed(), "Create Account Page is displayed");
        assertTestCase.assertTrue(accountNameInput.isDisplayed(), "Account Name input is displayed");
        //assertTestCase.assertTrue(statusCheckbox.isDisplayed(), "Status checkbox is displayed");
        assertTestCase.assertTrue(subscriberInput.isDisplayed(), "Subscriber input is displayed");
        assertTestCase.assertTrue(startDateInput.isDisplayed(), "Start Date input is displayed");
        assertTestCase.assertTrue(endDateInput.isDisplayed(), "End Date input is displayed");
        assertTestCase.assertTrue(saveButton.isDisplayed(), "Save button is displayed");
        assertTestCase.assertTrue(cancelButton.isDisplayed(), "Cancel button is displayed");

        //verify elements enabled
        assertTestCase.assertTrue(accountNameInput.isEnabled(), "Account Name input is enabled");
        assertTestCase.assertTrue(statusCheckbox.isEnabled(), "Status checkbox is enabled");
        assertTestCase.assertTrue(subscriberInput.isEnabled(), "Subscriber input is enabled");
        assertTestCase.assertTrue(startDateInput.isEnabled(), "Start Date input is enabled");
        assertTestCase.assertTrue(endDateInput.isEnabled(), "End Date input is enabled");
        assertTestCase.assertFalse(saveButton.isEnabled(), "Save button is not enabled");
        assertTestCase.assertTrue(cancelButton.isEnabled(), "Cancel button is enabled");

        //verify elements are empty by default
        assertTestCase.assertEquals(accountNameInput.getAttribute("value"), "", "Account Name input is empty");
        assertTestCase.assertTrue(statusCheckbox.isSelected(), "Status checkbox is not selected");
        assertTestCase.assertEquals(subscriberInput.getAttribute("value"), "", "Subscriber input is empty");
        assertTestCase.assertEquals(startDateInput.getAttribute("value"), DateTimeUtilities.getCurrentDate("MM/dd/yyyy"), "Start Date is set to today by default");
        //tomorrow's date
        String tomorrow = DateTimeUtilities.getCurrentDate("MM/dd/yyyy");
        System.out.println("tomorrow = " + tomorrow);
        tomorrow = DateTimeUtilities.getDatePlusMinusDays(tomorrow, 1, "MM/dd/yyyy");
        System.out.println("tomorrow = " + tomorrow);
        assertTestCase.assertEquals(endDateInput.getAttribute("value"), tomorrow, "End Date is set to tomorrow by default");
        assertTestCase.assertEquals(pageTitle.getText(), "Create a new Account", "Page title is correct");
    }
}
