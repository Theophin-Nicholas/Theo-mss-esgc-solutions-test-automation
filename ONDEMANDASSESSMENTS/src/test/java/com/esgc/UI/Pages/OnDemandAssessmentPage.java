package com.esgc.UI.Pages;

import com.esgc.Pages.PageBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class OnDemandAssessmentPage extends PageBase {

    @FindBy(xpath = "//div[@data-test='sentinelStart']/following-sibling::div//div[contains(@class,'MuiToolbar-root')]/div[text()]")
    public WebElement menuOptionPageHeader;

    @FindBy(xpath = "//li[@heap_menu='On-Demand Assessment Request']")
    public WebElement onDemandAssessmentRequest;

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
    public List<WebElement> btnCreateNewRequest;

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

    @FindBy(xpath = "//div[@role='dialog']//header/div/div/div/div")
    public List<WebElement> coverageHeader;

    @FindBy(xpath = "//button[@id='button-cancel-req-test-id']")
    public WebElement btnCancel;

    @FindBy(xpath = "//button[@id='button-review-req-test-id']")
    public WebElement btnReviewRequest;

    @FindBy(xpath = "//div[contains(text(),'ESC')]")
    public WebElement btnESC;

    @FindBy(xpath = "//button[@id='score-qualty-btn']")
    public WebElement dashboardPageMenuOption;

    @FindBy(xpath = "//div[contains(@id,'card-test-id')]")
    public List<WebElement> fiterOptionDivs;

    @FindBy(xpath = "//div[contains(@id,'card-test-id-0')]//span[@role='slider']")
    public List<WebElement> predictedScoresliders;

    @FindBy(xpath = "//div[contains(@id,'card-test-id-0')]/div/div/div/div/div/div")
    public WebElement predictedScoreInvper;

    @FindBy(xpath = "//div[contains(@id,'card-test-id-0')]/div/div/div[2]")
    public WebElement predictedScoreTextBelowGraph;

    @FindBy(xpath = "//div[contains(@id,'card-test-id-1')]//button/div/div")
    public WebElement locationHeader;

    @FindBy(xpath = "//div[contains(@id,'card-test-id-1')]//span[@title='All Locations (100%)']")
    public WebElement location_AllLocations;

    @FindBy(xpath = "//div[contains(@id,'card-test-id-1')]//label")
    public List<WebElement> location_LocationLabels;

    @FindBy(xpath = "//div[contains(@id,'card-test-id-2')]//label")
    public List<WebElement> Scetor_sectorsLabels;

    @FindBy(xpath = "//div[contains(@id,'card-test-id-2')]//button/div/div")
    public WebElement sectorHeader;

    @FindBy(xpath = "//div[contains(@id,'card-test-id-2')]//span[@title='All Sectors (100%)']")
    public WebElement sector_AllSectors;

    @FindBy(xpath = "//div[contains(@id,'card-test-id-3')]//label")
    public List<WebElement> Size_sizeLabels;

    @FindBy(xpath = "//div[contains(@id,'card-test-id-3')]//button/div/div")
    public WebElement sizeHeader;

    @FindBy(xpath = "//div[contains(@id,'card-test-id-3')]//span[@title='All Sizes ']")
    public WebElement size_AllSize;

    //@FindBy(xpath="(//input[contains(@class,'Switch-input')])[2]")
    @FindBy(xpath="(//span[@class='MuiSwitch-track'])[2]/../span[1]")
    public WebElement toggleButton ;




    public void clickReviewAndSendRequestButton() {

        if(btnCreateNewRequest.size()>0)btnCreateNewRequest.get(0).click();
        else {BrowserUtils.waitForVisibility(btnReviewRequest,30).click();
            BrowserUtils.waitForVisibility(drdShowFilter,30);}
        }
    public void clickESCkey() {
        btnESC.click();
    }

    public void sendESCkey() {
        Actions action = new Actions(Driver.getDriver());
        action.sendKeys(Keys.ESCAPE).build().perform();
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
            if(status.equalsIgnoreCase("No Request Sent")) {
                String emailXpath = "//table[@class='MuiTable-root']//tr[" + i + "]/td[5]//div[text()]//input";
                WebElement email = Driver.getDriver().findElement(By.xpath(emailXpath));
                assertTestCase.assertTrue(email.isDisplayed(), "Verifying if the email field is available");
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
        BrowserUtils.waitForVisibility(btnProceed, 30).click();
    }

    public boolean isOnDemandAssessmentRequestAvailableInMenu() {
        try{
            return onDemandAssessmentRequest.isDisplayed();
        }catch(Exception e){
            return false;
        }
    }

    public void onDemandCoverageHeaderValidation(String portfolioName) {
        assertTestCase.assertTrue(coverageHeader.get(0).getText().equals("Request On-Demand Assessments for " + portfolioName));
        assertTestCase.assertTrue(coverageHeader.get(1).getText().matches("\\d+% assessment eligible \\(\\d+ companies\\)"));
    }

    public boolean isCancelButtonAvailable() {
        return btnCancel.isDisplayed();
    }

    public boolean isReviewButtonAvailable() {
        return btnReviewRequest.isDisplayed();
    }

    public void clickOnESCButton() {
        BrowserUtils.waitForVisibility(btnESC, 30).click();
    }

    public void clickOnDemagPagelinkFromDashboardPage() {
        BrowserUtils.waitForVisibility(dashboardPageMenuOption, 30).click();
    }

    public void validatePredictedScore() {
        assertTestCase.assertTrue(Integer.valueOf(predictedScoresliders.get(0).getAttribute("aria-valuemax")) <= 100);
        assertTestCase.assertTrue(Integer.valueOf(predictedScoresliders.get(1).getAttribute("aria-valuemax")) <= 100);
        assertTestCase.assertTrue(Integer.valueOf(predictedScoresliders.get(0).getAttribute("aria-valuemin")) >= 0);
        assertTestCase.assertTrue(Integer.valueOf(predictedScoresliders.get(1).getAttribute("aria-valuemin")) >= 0);
        assertTestCase.assertTrue(predictedScoreInvper.getText().matches("\\d+.\\d+% invested"));
        assertTestCase.assertTrue(predictedScoreTextBelowGraph.getText().matches("Companies with predicted scores ranging \\d+-\\d+ account for \\d+.\\d% of investments"));
    }

    public void validateLocation() {
        assertTestCase.assertTrue(locationHeader.getText().equals("Location(Based on headquarters)"));
        assertTestCase.assertTrue(location_AllLocations.getText().contains("All Locations"));
        assertTestCase.assertTrue(location_LocationLabels.size() > 0);

    }

    public void validateSector() {
        assertTestCase.assertTrue(sectorHeader.getText().equals("Sector"));
        assertTestCase.assertTrue(sector_AllSectors.getText().contains("All Sectors"));
        assertTestCase.assertTrue(Scetor_sectorsLabels.size() > 0);

    }

    public void validateSize() {
        assertTestCase.assertTrue(sizeHeader.getText().equals("Size(Employee Count)"));
        assertTestCase.assertTrue(size_AllSize.getText().contains("All Sizes"));
        assertTestCase.assertTrue(Size_sizeLabels.size() > 0);

    }

    public List<String> getSelectedValues(String filterType) {
        String mainDivXpath = "";
        switch (filterType) {
            case "Location":
                mainDivXpath = "card-test-id-1";
                break;
            case "Sector":
                mainDivXpath = "card-test-id-2";
                break;
            case "Size":
                mainDivXpath = "card-test-id-3";
                break;
        }
        List<WebElement> AllCheckBoxes  = Driver.getDriver().findElements(By.xpath("//div[contains(@id,'"+mainDivXpath+"')]//label/span[contains(@class,'MuiCheckbox-root')]")) ;
        List<String> returnList = new ArrayList<>();
        List<WebElement> selectectedCheckBoxes=  AllCheckBoxes.stream().filter(e-> e.getAttribute("class").contains("checked")).collect(Collectors.toList());
        for (WebElement e:selectectedCheckBoxes){
            returnList.add(e.findElement(By.xpath("following-sibling::span/span")).getText());
        }

        return returnList;
    }



    public void validateAndORLogic(String Logic) {
        int FirstcompaniesCount = getCompaniesCountFromReviewButtion();
        //Validating Or Condition
        location_LocationLabels.get(0).click();
        assertTestCase.equals(getCompaniesCountFromReviewButtion()==FirstcompaniesCount);

        //Validating And Condition
        toggleButton.click();
        location_LocationLabels.get(0).click();
        assertTestCase.equals(getCompaniesCountFromReviewButtion()!=FirstcompaniesCount);
        // Below code is to move the slider in Predicted Score section --
        /*for (int i = 1; i <= 9; i++) predictedScoresliders.get(0).sendKeys(Keys.ARROW_RIGHT);

       for (int i = 1; i <= 73; i++) predictedScoresliders.get(1).sendKeys(Keys.ARROW_LEFT);*/

    }

    public boolean verifyMainPageHeader(String header){
        return menuList.get(0).getText().equals(header);
    }

    public int getCompaniesCountFromReviewButtion(){
        return  new Scanner(btnReviewRequest.getText()).useDelimiter("\\D+").nextInt();
    }
}
