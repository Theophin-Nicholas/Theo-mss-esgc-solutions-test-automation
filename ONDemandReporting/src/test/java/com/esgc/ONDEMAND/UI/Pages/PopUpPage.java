package com.esgc.ONDEMAND.UI.Pages;

import com.esgc.Common.UI.Pages.CommonPage;
import com.esgc.Utilities.BrowserUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PopUpPage extends CommonPage {

    @FindBy(xpath = "//*[@id='invalid-entitlements-test-id']/div[3]/div/div[1]/h2")
    WebElement popUpHeader;

    @FindBy( xpath = "//*[@id=\"invalid-entitlements-test-id\"]/div[3]/div/div[2]")
    WebElement popUpMessage;

    @FindBy(xpath = "//*[@id=\"invalid-entitlements-button-test-id\"]")
    WebElement okButton;


    public void clickOnOKButton(){
        System.out.println("Clicking on OK button now and going to login Page.....");
        okButton.click();
    }

    public boolean isOkButtonEnabled(){
        return okButton.isEnabled();
    }

    public String popUpHeaderText(){
        return BrowserUtils.waitForVisibility(popUpHeader).getText();
    }

    public String popUpMessageText(){
        return BrowserUtils.waitForVisibility(popUpMessage).getText();
    }

    public void validateTheContentOfPopUp(){
        String expectedPopHeaderText = "Invalid Entitlement";
        String expectedPopMessageLineOne = "Invalid product combinations.";
        String expectedPopMessageLineTwo = "Please contact clientservices@moodys.com for further assistance.";


        System.out.println(popUpHeaderText());
        System.out.println(popUpMessageText());
        assertTestCase.assertEquals(popUpHeaderText(), expectedPopHeaderText, "The PopUp contains Invalid Entitlement text");
        assertTestCase.assertTrue(popUpMessageText().contains(expectedPopMessageLineOne), "The PopUp contains Invalid product combinations text description");
        assertTestCase.assertTrue(popUpMessageText().contains(expectedPopMessageLineTwo), "The PopUp contains Please contact clientservices@moodys.com for further assistance text description");
        assertTestCase.assertTrue(isOkButtonEnabled(), "the OK button is enabled");
        clickOnOKButton();

    }

}
