package com.esgc.OnDemandAssessment.UI.Pages;

import com.esgc.Base.UI.Pages.UploadPage;
import com.esgc.Pages.PageBase;
import com.esgc.Utilities.BrowserUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class OnDemandAssessmentPage extends UploadPage {

    @FindBy(xpath = "//div[@data-test='sentinelStart']/following-sibling::div//div[contains(@class,'MuiToolbar-root')]/div[text()]")
    public WebElement menuOptionPageHeader;

    @FindBy(xpath = "//li[@heap_menu='On-Demand Assessment Request']")
    public WebElement onDemandAssessmentRequest;

    @FindBy(xpath = "//li[@heap_menu='On-Demand Assessment Request']")
    public WebElement reviewAndSendRequestButton;

    @FindBy(xpath = "//div[@data-testid='remove-entity']")
    public List<WebElement> removeButtons;

    @FindBy(xpath = "//input[@placeholder='Enter email address']")
    public WebElement txtSendTo;

    @FindBy(xpath = "//button[@id='ondemand-assessment']")
    public WebElement btnConfirmRequest;

    @FindBy(xpath = "//button[@id='ondemand-assessment-confirmation']")
    public WebElement btnProceed;

    public void clickReviewAndSendRequestButton() {
        BrowserUtils.waitForVisibility(reviewAndSendRequestButton,30).click();
    }

    public void confirmRequest() {
        while(removeButtons.size()>=1){
            removeButtons.get(0).click();
            BrowserUtils.wait(2000);
        }
        txtSendTo.sendKeys("qatest@gmail.com");
        btnConfirmRequest.click();
    }

    public void clickProceedOnConfirmRequestPopup() {
        BrowserUtils.waitForVisibility(btnProceed,30).click();
    }

    public boolean isOnDemandAssessmentRequestAvailableInMenu() {
        try{
            return onDemandAssessmentRequest.isDisplayed();
        }catch(Exception e){
            return false;
        }
    }
}
