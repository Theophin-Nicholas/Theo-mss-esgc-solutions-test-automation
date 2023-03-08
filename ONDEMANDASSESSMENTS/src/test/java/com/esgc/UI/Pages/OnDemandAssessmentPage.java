package com.esgc.UI.Pages;

import com.esgc.API.Controllers.OnDemandFilterAPIController;
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

    @FindBy(xpath = "//div[@role='dialog']//header/div/div/div/div")
    public List<WebElement> coverageHeader;

    @FindBy(xpath = "//button[@id='button-cancel-req-test-id']")
    public WebElement btnCancel;

    @FindBy(xpath = "//button[@id='button-review-req-test-id']")
    public WebElement btnReviewRequest;

    @FindBy(xpath = "//div[contains(text(),'ESC')]")
    public WebElement btnESC;

    @FindBy(xpath = "//button[@id='score-qualty-btn']/span/div[contains(text(),'On-Demand')]")
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
    @FindBy(xpath = "(//span[@class='MuiSwitch-track'])[2]/../span[1]")
    public WebElement toggleButton;

    @FindBy(xpath = "//*[contains(text(),'Please confirm email addresses. The listed contacts will receive an email prompting them to complete an ESG assessment questionnaire.')]")
    public WebElement emailAlertMessage;

    @FindBy(xpath="//div[text()='Confirm Request']")
    public WebElement confirmRequestPopupHeader ;

    @FindBy(xpath="//div[text()='Confirm Request']//following-sibling::div")
    public WebElement confirmRequestPopupSubHeader ;

    @FindBy(xpath="//button[@id='ondemand-assessment-cancel']")
    public WebElement confirmRequestPopupBtnCancel ;

    @FindBy(xpath = "//button[@id='ondemand-assessment-confirmation']")
    public WebElement confirmRequestPopupBtnProceed;

    @FindBy(xpath = "//div[@class='MuiAlert-message']//div[text()='Something went wrong, please try again later.']")
    public WebElement SomeThingWentWrongErrorMessage;


    public String landingPage = "";

    public void goToSendRequestPage(String portfolioName) {
        if (landingPage.equals("")) getLandingPage(portfolioName);
        if (landingPage.equals("batchProcessingPage")) {
            BrowserUtils.waitForPageToLoad(30);
            clickCreateNewRequestButton();
        }
        BrowserUtils.waitForVisibility(btnReviewRequest, 80);
    }

    public void getLandingPage(String portfolioName) {
        OnDemandFilterAPIController controller = new OnDemandFilterAPIController();
        landingPage = controller.getLandingPage(portfolioName);
    }

    public void clickReviewAndSendRequestButton() {
        BrowserUtils.waitForVisibility(btnReviewRequest, 80).click();
    }

    public void clickCreateNewRequestButton() {
        BrowserUtils.clickWithJS(BrowserUtils.waitForVisibility(btnCreateNewRequest, 80));
    }


    public void clickESCkey() {
        BrowserUtils.clickWithJS(btnESC);
    }

    public void sendESCkey() {
        BrowserUtils.ActionKeyPress(Keys.ESCAPE);
    }

    public String confirmRequestAndGetCompaniesCount(String emailid) {

        KeepOnlyOneRequest();
        BrowserUtils.scrollTo(removeButtons.get(0));
        txtSendTo.sendKeys(emailid);
        String compniesCountForRequest = getCompaniesCountFromConfirmRequestButton();
        btnConfirmRequest.click();
        return compniesCountForRequest;

    }

    public void selectFilter(String filterOption) {
        // BrowserUtils.waitForVisibility(drdShowFilter,30).click();
       // drdShowOptions.get(0).click();
        BrowserUtils.waitForVisibility(drdShowFilter,30).click();
        for (WebElement option : drdShowOptions) {
            if (option.getText().equals(filterOption)) {
                option.click();
                BrowserUtils.wait(2);
                break;
            }
        }
    }

    public void KeepOnlyOneRequest() {
        selectFilter("No Request Sent");
        while (removeButtons.size() > 1) {
            removeButtons.get(0).click();
        }
    }

    public void verifyConfirmEmailAlert() {
        /*String message = "Please confirm email addresses. The listed contacts will receive an email prompting them to complete an ESG assessment questionnaire.";
        String xpath = "//*[contains(text(),'" + message + "')]";
        WebElement element = Driver.getDriver().findElement(By.xpath(xpath));*/
        assertTestCase.assertTrue(BrowserUtils.waitForVisibility(emailAlertMessage, 30).isDisplayed(), "Email Confirm Message is not Displayed");
    }

    public void verifyCompaniesDetails() {
        int noOfRecords = companyRecords.size();
        for (int i = 1; i <= noOfRecords; i++) {
            // Verification of Second Column: Company Info
            String xpath = "//table[@class='MuiTable-root']//tr[" + i + "]/td[2]//div[text()]";
            String companyName = Driver.getDriver().findElement(By.xpath("(" + xpath + ")[1]")).getText();
            String companyInfo = Driver.getDriver().findElement(By.xpath("(" + xpath + ")[2]")).getText();
            assertTestCase.assertTrue(companyName.length() != 0, "Company Name is not displayed");
            String[] companyDetails = companyInfo.split("-");
            //assertTestCase.assertTrue(companyDetails.length==2, "Company Details is not displayed");
            assertTestCase.assertTrue(companyDetails[0].contains("Predicted: ") && companyDetails[0].replace("Predicted: ", "").trim().length() > 0, "Predicted Score is not displayed");
            assertTestCase.assertTrue(companyDetails[1].trim().length() > 0, "Predicted Score is not displayed");

            // Verification of Fourth Column: Status
            String statusXpath = "//table[@class='MuiTable-root']//tr[" + i + "]/td[4]//div[text()]";
            String status = Driver.getDriver().findElement(By.xpath("(" + statusXpath + ")[2]")).getText();
            ArrayList<String> expectedStatuses = new ArrayList<>();
            expectedStatuses.add("All Statuses");
            expectedStatuses.add("No Request Sent");
            expectedStatuses.add("Pending Activation");
            expectedStatuses.add("In Progress");
            expectedStatuses.add("Cancelled");
            expectedStatuses.add("Completed");
            assertTestCase.assertTrue(expectedStatuses.contains(status), status + " status is not available in Expected Statuses");

            // Verification of Fifth Column: Email Address
            if (status.equalsIgnoreCase("No Request Sent")) {
                String emailXpath = "//table[@class='MuiTable-root']//tr[" + i + "]/td[5]//div[text()]//input";
                WebElement email = Driver.getDriver().findElement(By.xpath(emailXpath));
                assertTestCase.assertTrue(email.isDisplayed(), "Verifying if the email field is available");
            }

        }

    }

    public ArrayList<HashMap<String, String>> getCompaniesInvestmentInfo() {
        // int noOfRecords = companyRecords.size();
        ArrayList<HashMap<String, String>> allCompaniesInfo = new ArrayList<>();
        do {
            try {
                BrowserUtils.scrollTo(lnkLoadMore);
                lnkLoadMore.click();
                BrowserUtils.wait(3);
            } catch (Exception e) {
                break;
            }
        } while (true);
        int noOfRecords = companyRecords.size();
        for (int i = 1; i <= noOfRecords; i++) {
            HashMap<String, String> companyInfo = new HashMap<>();
            String xpath = "//table[@class='MuiTable-root']//tr[" + i + "]/td[2]//div[text()]";
            String companyName = Driver.getDriver().findElement(By.xpath("(" + xpath + ")[1]")).getText();
            companyInfo.put("COMPANY_NAME", companyName);

            String invXpath = "//table[@class='MuiTable-root']//tr[" + i + "]/td[3]//div[text()][2]";
            String investments = Driver.getDriver().findElement(By.xpath(invXpath)).getText();
            companyInfo.put("InvPerc", investments.split(",")[0].trim());
            companyInfo.put("VALUE", investments.split(",")[1].trim());
            allCompaniesInfo.add(companyInfo);
        }
        return allCompaniesInfo;
    }

    public ArrayList<String> getLocationWiseInvestmentInfo() {
        ArrayList<String> allCompaniesInfo = new ArrayList<>();
        for (WebElement location : locations) {
            allCompaniesInfo.add(location.getText());
        }
        return allCompaniesInfo;
    }

    public ArrayList<String> getSectorWiseInvestmentInfo() {
        ArrayList<String> allCompaniesInfo = new ArrayList<>();
        for (WebElement sector : sectors) {
            allCompaniesInfo.add(sector.getText());
        }
        return allCompaniesInfo;
    }

    public String getEligibleAssessment() {
        String assessmentText = lblAssessmentEligible.getText();
        return assessmentText.substring(0, assessmentText.indexOf("%") + 1);
    }

    public String getHighestInvestment() {
        String assessmentText = lblHighestInvestment.getText();
        return assessmentText.substring(0, assessmentText.indexOf("%") + 1);
    }

    public void verifyShowFilterOptions() {
        ArrayList<String> expectedStatuses = new ArrayList<>();
        expectedStatuses.add("All Statuses");
        expectedStatuses.add("No Request Sent");
        expectedStatuses.add("Pending Activation");
        expectedStatuses.add("In Progress");
        expectedStatuses.add("Cancelled");
        expectedStatuses.add("Completed");

        BrowserUtils.waitForVisibility(drdShowFilter, 30).click();

        ArrayList<String> actualStatuses = new ArrayList<>();
        for (WebElement option : drdShowOptions) {
            actualStatuses.add(option.getText());
        }
        assertTestCase.assertEquals(actualStatuses, expectedStatuses, "Statuses are not matching");
        drdShowOptions.get(0).click();
    }

    public void clickProceedOnConfirmRequestPopup() {
        BrowserUtils.waitForVisibility(confirmRequestPopupBtnProceed, 30).click();
    }

    public boolean isOnDemandAssessmentRequestAvailableInMenu() {
        try {
            return onDemandAssessmentRequest.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void onDemandCoverageHeaderValidation(String portfolioName) {
        BrowserUtils.waitForPageToLoad(80);
        assertTestCase.assertTrue(BrowserUtils.waitForVisibility(coverageHeader.get(0), 30).getText().equals("Request On-Demand Assessments for " + portfolioName));
        assertTestCase.assertTrue(BrowserUtils.waitForVisibility(coverageHeader.get(1), 30).getText().matches("\\d+% assessment eligible \\(\\d+ companies\\)"));
    }

    public boolean isCancelButtonAvailable() {
        return btnCancel.isDisplayed();
    }

    public boolean isReviewButtonAvailable() {
        return BrowserUtils.waitForVisibility(btnReviewRequest,60).isDisplayed();
    }

    public void clickOnESCButton() {
        BrowserUtils.waitForVisibility(btnESC, 30).click();
    }

    public void clickOnDemandPagelinkFromDashboardPage() {
        BrowserUtils.waitForVisibility(dashboardPageMenuOption, 30).click();
    }

    public void validatePredictedScore() {
        assertTestCase.assertTrue(Integer.parseInt(predictedScoresliders.get(0).getAttribute("aria-valuemax")) <= 100);
        assertTestCase.assertTrue(Integer.parseInt(predictedScoresliders.get(1).getAttribute("aria-valuemax")) <= 100);
        assertTestCase.assertTrue(Integer.parseInt(predictedScoresliders.get(0).getAttribute("aria-valuemin")) >= 0);
        assertTestCase.assertTrue(Integer.parseInt(predictedScoresliders.get(1).getAttribute("aria-valuemin")) >= 0);
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
        List<WebElement> AllCheckBoxes = Driver.getDriver().findElements(By.xpath("//div[contains(@id,'" + mainDivXpath + "')]//label/span[contains(@class,'MuiCheckbox-root')]"));
        List<String> returnList = new ArrayList<>();
        List<WebElement> selectectedCheckBoxes = AllCheckBoxes.stream().filter(e -> e.getAttribute("class").contains("checked")).collect(Collectors.toList());
        for (WebElement e : selectectedCheckBoxes) {
            returnList.add(e.findElement(By.xpath("following-sibling::span/span")).getText());
        }

        return returnList;
    }


    public void validateAndORLogic() {
        int FirstcompaniesCount = getCompaniesCountFromReviewButtion();
        //Validating Or Condition
        location_LocationLabels.get(0).click();
        assertTestCase.equals(getCompaniesCountFromReviewButtion() == FirstcompaniesCount);

        //Validating And Condition
        toggleButton.click();
        location_LocationLabels.get(0).click();
        assertTestCase.equals(getCompaniesCountFromReviewButtion() != FirstcompaniesCount);
        // Below code is to move the slider in Predicted Score section --
        /*for (int i = 1; i <= 9; i++) predictedScoresliders.get(0).sendKeys(Keys.ARROW_RIGHT);

       for (int i = 1; i <= 73; i++) predictedScoresliders.get(1).sendKeys(Keys.ARROW_LEFT);*/

    }

    public boolean verifyMainPageHeader(String header) {
        return menuList.get(0).getText().equals(header);
    }

    public int getCompaniesCountFromReviewButtion() {
        return new Scanner(btnReviewRequest.getText()).useDelimiter("\\D+").nextInt();

    }

    public String getCompaniesCountFromConfirmRequestButton() {
        return new Scanner(btnConfirmRequest.getText()).useDelimiter("\\D+").toString();
    }

    public boolean validateIfOndemandmenuOptionIsEnabled() {
        while (onDemandAssessmentRequest.getAttribute("aria-disabled").equals("true")) {
            BrowserUtils.wait(1);
        }
        if (onDemandAssessmentRequest.getAttribute("aria-disabled").equals("false")) return true;
        else return false;

    }

    public void clickonOnOndemandMenuOption() {
        if (validateIfOndemandmenuOptionIsEnabled()) {
            onDemandAssessmentRequest.click();
        }
    }

    public void validateOnDemandPageHeader() {
        assertTestCase.assertEquals(BrowserUtils.waitForVisibility(menuOptionPageHeader, 90).getText(), "Moody's ESG360: Request On-Demand Assessment", "Moody's ESG360: Request On-Demand Assessment page verified");
    }

    public void validateProceedOnConfirmRequestPopup(String countOfCompanies) {
        assertTestCase.assertTrue(BrowserUtils.waitForVisibility(confirmRequestPopupHeader,10).getText().equals("Confirm Request"),"Validate Confirm Request header");
        assertTestCase.assertTrue(BrowserUtils.waitForVisibility(confirmRequestPopupSubHeader,10).getText().equals("Assessment request will be sent to " + countOfCompanies + " company. You will receive confirmation when all requests have been sent."),"Validate Sub Header Count and Text");
        assertTestCase.assertTrue(confirmRequestPopupBtnCancel.isDisplayed(),"Validate Cancel button is available in popup");
        assertTestCase.assertTrue(confirmRequestPopupBtnProceed.isDisplayed(),"Validate proceed button is available in popup");
    }
    public void clickCancelButtonAndValidateRequestPage(){
        BrowserUtils.waitForVisibility(confirmRequestPopupBtnCancel,10).click();
        assertTestCase.assertTrue(BrowserUtils.waitForVisibility(btnConfirmRequest,30).isDisplayed(),"Validate it is back on Confirm euest page");
    }

    public void validateDashboardPageButtonForOnDemand(){
        BrowserUtils.waitForVisibility(dashboardPageMenuOption,60);
        assertTestCase.assertTrue(dashboardPageMenuOption.isDisplayed(), "Validate that Dasboard on demand button is visible");
        assertTestCase.assertTrue(dashboardPageMenuOption.getText().matches("\\d+% On-Demand Assessment Eligible"), "Validate that Dasboard on demand button is visible");
    }

    public void validateDashboardPageButtonCoverage(String portfolioID){
        BrowserUtils.waitForVisibility(dashboardPageMenuOption,60);
        assertTestCase.assertTrue(dashboardPageMenuOption.isDisplayed(), "Validate that Dasboard on demand button is visible");
        double uiValue = Double.valueOf(dashboardPageMenuOption.getText().substring(0,dashboardPageMenuOption.getText().indexOf("%")));
        OnDemandFilterAPIController apiController = new OnDemandFilterAPIController();
       double apiValue =  apiController.getDashboardCoverage(portfolioID).jsonPath().getDouble("perc_avail_for_assessment")*100;
       assertTestCase.assertEquals(uiValue,apiValue,"Validating Coverage %");

    }

    public void validateErrormessage(){
        assertTestCase.assertTrue(BrowserUtils.waitForVisibility(SomeThingWentWrongErrorMessage,20).isDisplayed(), "Validating if error message has displayed");
    }

}
