package com.esgc.OnDemandAssessment.UI.Pages;

import com.esgc.Base.UI.Pages.UploadPage;
import com.esgc.Pages.PageBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OnDemandAssessmentPage extends UploadPage {

    @FindBy(xpath = "//div[@data-test='sentinelStart']/following-sibling::div//div[contains(@class,'MuiToolbar-root')]/div[text()]")
    public WebElement menuOptionPageHeader;

    @FindBy(xpath = "//li[@heap_menu='On-Demand Assessment Request']")
    public WebElement onDemandAssessmentRequest;

    @FindBy(id = "button-review-req-test-id")
    public WebElement reviewAndSendRequestButton;

    @FindBy(xpath = "//div[@data-testid='remove-entity']")
    public List<WebElement> removeButtons;

    @FindBy(xpath = "//table[@class='MuiTable-root']//tr")
    public List<WebElement> companyRecords;

    @FindBy(xpath = "//input[@placeholder='Enter email address']")
    public WebElement txtSendTo;

    @FindBy(xpath = "//button[@id='ondemand-assessment']")
    public WebElement btnConfirmRequest;

    @FindBy(xpath = "//button[@id='ondemand-assessment-confirmation']")
    public WebElement btnProceed;

    @FindBy(xpath = "//div[@role='button'][text()='All Statuses']")
    public WebElement drdShowFilter;

    @FindBy(xpath = "//ul[@role='listbox']/li[text()]")
    public List<WebElement> drdShowOptions;

    @FindBy(xpath = "//div[text()='Create New Request']")
    public WebElement btnCreateNewRequest;

    @FindBy(xpath = "//div[contains(text(),'Load More')]")
    public WebElement lnkLoadMore;

    @FindBy(xpath = "//*[@id='card-test-id-1']//span[@title]")
    public List<WebElement> locations;

    @FindBy(xpath = "//*[@id='card-test-id-2']//span[@title]")
    public List<WebElement> sectors;

    @FindBy(xpath = "//div[contains(text(),'assessment eligible')]")
    public WebElement lblAssessmentEligible;

    @FindBy(xpath = "(//div[contains(text(),'Predicted ESG Score')]/../../../..//div[text()])[2]")
    public WebElement lblHighestInvestment;


    public void clickReviewAndSendRequestButton() {
        try{
            if(btnCreateNewRequest.isDisplayed()){
                btnCreateNewRequest.click();
            }
        }catch(Exception e){}
        BrowserUtils.waitForVisibility(reviewAndSendRequestButton,30).click();
    }

    public void sendESCkey() {
        txtSendTo.sendKeys(Keys.ESCAPE);
    }

    public void confirmRequest() {
        while(removeButtons.size()>1){
            removeButtons.get(0).click();
            BrowserUtils.wait(2);
        }
        BrowserUtils.scrollTo(removeButtons.get(0));
        txtSendTo.sendKeys("qatest"+Math.random()+"@gmail.com");
        btnConfirmRequest.click();
    }

    public void verifyConfirmEmailAlert(String message) {
        String xpath = "//*[contains(text(),'"+message+"')]";
        WebElement element = Driver.getDriver().findElement(By.xpath(xpath));
        assertTestCase.assertTrue(element.isDisplayed(),"Email Confirm Message is not Displayed");
    }

    public void verifyCompaniesDetails() {
        int noOfRecords = companyRecords.size();
        for(int i=1; i<=noOfRecords; i++){
            // Verification of Second Column: Company Info
            String xpath = "//table[@class='MuiTable-root']//tr["+i+"]/td[2]//div[text()]";
            String companyName = Driver.getDriver().findElement(By.xpath("("+xpath+")[1]")).getText();
            String companyInfo = Driver.getDriver().findElement(By.xpath("("+xpath+")[2]")).getText();
            assertTestCase.assertTrue(companyName.length()!=0, "Company Name is not displayed");
            String[] companyDetails = companyInfo.split("-");
            //assertTestCase.assertTrue(companyDetails.length==2, "Company Details is not displayed");
            assertTestCase.assertTrue(companyDetails[0].contains("Predicted: ") && companyDetails[0].replace("Predicted: ","").trim().length()>0, "Predicted Score is not displayed");
            assertTestCase.assertTrue(companyDetails[1].trim().length()>0, "Predicted Score is not displayed");

            // Verification of Fourth Column: Status
            String statusXpath = "//table[@class='MuiTable-root']//tr["+i+"]/td[4]//div[text()]";
            String status = Driver.getDriver().findElement(By.xpath("("+statusXpath+")[2]")).getText();
            ArrayList<String> expectedStatuses = new ArrayList<>();
            expectedStatuses.add("All Statuses");
            expectedStatuses.add("No Request Sent");
            expectedStatuses.add("Pending Activation");
            expectedStatuses.add("In Progress");
            expectedStatuses.add("Cancelled");
            expectedStatuses.add("Completed");
            assertTestCase.assertTrue(expectedStatuses.contains(status), status+" status is not available in Expected Statuses");

            // Verification of Fifth Column: Email Address
            if(!status.equalsIgnoreCase("Cancelled")) {
                String emailXpath = "//table[@class='MuiTable-root']//tr[" + i + "]/td[5]//div[text()]//input";
                WebElement email = Driver.getDriver().findElement(By.xpath(emailXpath));
                assertTestCase.assertTrue(email.isDisplayed(), "Email field is not available");
            }

        }

    }

    public ArrayList<HashMap<String, String>> getCompaniesInvestmentInfo(){
        int noOfRecords = companyRecords.size();
        ArrayList<HashMap<String, String>> allCompaniesInfo = new ArrayList<>();
        do{
            try{
                BrowserUtils.scrollTo(lnkLoadMore);
                lnkLoadMore.click();
                BrowserUtils.wait(3);
            }catch(Exception e){
                break;
            }
        }while(true);

        for(int i=1; i<=noOfRecords; i++){
            HashMap<String, String> companyInfo = new HashMap<>();
            String xpath = "//table[@class='MuiTable-root']//tr["+i+"]/td[2]//div[text()]";
            String companyName = Driver.getDriver().findElement(By.xpath("("+xpath+")[1]")).getText();
            companyInfo.put("COMPANY_NAME",companyName);

            String invXpath = "//table[@class='MuiTable-root']//tr["+i+"]/td[3]//div[text()][2]";
            String investments = Driver.getDriver().findElement(By.xpath(invXpath)).getText();
            companyInfo.put("InvPerc",investments.split(",")[0].trim());
            companyInfo.put("VALUE",investments.split(",")[1].trim());
            allCompaniesInfo.add(companyInfo);
        }
        return allCompaniesInfo;
    }

    public ArrayList<String> getLocationWiseInvestmentInfo(){
        ArrayList<String> allCompaniesInfo = new ArrayList<>();
        for(int i=0; i<locations.size(); i++){
            allCompaniesInfo.add(locations.get(i).getText());
        }
        return allCompaniesInfo;
    }

    public ArrayList<String> getSectorWiseInvestmentInfo(){
        ArrayList<String> allCompaniesInfo = new ArrayList<>();
        for(int i=0; i<sectors.size(); i++){
            allCompaniesInfo.add(sectors.get(i).getText());
        }
        return allCompaniesInfo;
    }

    public String getEligibleAssessment(){
        String assessmentText = lblAssessmentEligible.getText();
        return assessmentText.substring(0,assessmentText.indexOf("%")+1);
    }

    public String getHighestInvestment(){
        String assessmentText = lblHighestInvestment.getText();
        return assessmentText.substring(0,assessmentText.indexOf("%")+1);
    }

    public void verifyShowFilterOptions(){
        ArrayList<String> expectedStatuses = new ArrayList<>();
        expectedStatuses.add("All Statuses");
        expectedStatuses.add("No Request Sent");
        expectedStatuses.add("Pending Activation");
        expectedStatuses.add("In Progress");
        expectedStatuses.add("Cancelled");
        expectedStatuses.add("Completed");

        drdShowFilter.click();

        ArrayList<String> actualStatuses = new ArrayList<>();
        for(WebElement option:drdShowOptions){
            actualStatuses.add(option.getText());
        }
        assertTestCase.assertEquals(actualStatuses,expectedStatuses, "Statuses are not matching");
        drdShowOptions.get(0).click();
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
