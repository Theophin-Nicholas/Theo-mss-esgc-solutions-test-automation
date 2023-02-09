package com.esgc.Pages;

import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class OnDemandAssessmentPage extends PageBase {

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
        BrowserUtils.waitForVisibility(btnReviewRequest, 30).click();
    }

    public void confirmRequest() {
        while (removeButtons.size() >= 1) {
            removeButtons.get(0).click();
            BrowserUtils.wait(2000);
        }
        txtSendTo.sendKeys("qatest@gmail.com");
        btnConfirmRequest.click();
    }

    public void clickProceedOnConfirmRequestPopup() {
        BrowserUtils.waitForVisibility(btnProceed, 30).click();
    }

    public boolean isOnDemandAssessmentRequestAvailableInMenu() {
        try {
            return onDemandAssessmentRequest.isDisplayed();
        } catch (Exception e) {
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

    public int getCompaniesCountFromReviewButtion(){
        return  new Scanner(btnReviewRequest.getText()).useDelimiter("\\D+").nextInt();
    }
}
