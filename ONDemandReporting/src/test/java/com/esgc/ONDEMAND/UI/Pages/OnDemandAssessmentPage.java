package com.esgc.ONDEMAND.UI.Pages;


import com.esgc.Common.UI.Pages.CommonPage;
import com.esgc.ONDEMAND.API.Controllers.OnDemandFilterAPIController;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.DateTimeUtilities;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.UnzipUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.ParseException;
import java.util.*;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class OnDemandAssessmentPage extends CommonPage {

    @FindBy(xpath = "//div[@data-test='sentinelStart']/following-sibling::div//div[contains(@class,'MuiToolbar-root')]/div[text()]")
    public WebElement menuOptionPageHeader;

    @FindBy(xpath = "//li[@heap_menu='ESG Reporting Portal']")
    public WebElement onDemandReportingMenu;

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

    @FindBy(xpath = "//div[@aria-haspopup='listbox']")
    public WebElement FilterDropDown;

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

    @FindBy(xpath = "//div/b")
    public WebElement remainingAssessmentLimit;

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

    @FindBy(xpath = "//div/*[local-name()='svg' and @fill='#1F8CFF']/..")
//"//*[contains(text(),'Please confirm email addresses. The listed contacts will receive an email prompting them to complete an ESG assessment questionnaire.')]")
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

    @FindBy(xpath = "//button[@id='button-report-test-id-1']")
    public WebElement buttonRequestAssessment ;

    @FindBy(xpath="//button[@id='button-prev-status-test-id-1']")
    public WebElement buttonViewAssessmentStatus ;

    @FindBy(xpath="//button[@id='button-prev-methodologies-test-id-1']")
    public WebElement buttonMethodologies ;

    @FindBy(xpath="//div[contains(@class,'MuiGrid-root MuiGrid-item MuiGrid-grid')][3]//div/div/div/div/div/div[1]")
    public WebElement AssessmentsRemaining ;

    @FindBy(xpath = "//div[contains(text(),'Select Portfolio')]/../div[2]/following-sibling::div/div[3]")
    public List<WebElement> portfolioCoverage;
    @FindBy(xpath = "//div[@data-testid='input-email']//input")
    public List<WebElement> emailInputs;

    @FindBy(xpath = "//span[.='Duplicate email']")
    public List<WebElement> duplicateEmailNotification;

    @FindBy(xpath = "//button[.='Methodologies']")
    public WebElement btnMethodologies;

    @FindBy(xpath = "//h2")
    public WebElement methodologiesPopupTitle;

    @FindBy(xpath = "//h2[.=' Methodology Documents ']")
    public WebElement methodologiesPopupListHeader;

    @FindBy(xpath = "//h2/..//li/a")
    public List<WebElement> methodologiesPopupList;

    @FindBy(xpath = "//button[.='Download All .ZIP']")
    public WebElement methodologiesPopupDownloadAllButton;

    @FindBy(xpath = "//h2/button")
    public WebElement methodologiesPopupCloseButton;



    @FindBy(xpath = "//div[contains(text(),'Select Portfolio')]/../div[2]/following-sibling::div/div[4]")
    public List<WebElement> OnDemandEligibility;

    @FindBy(xpath = "//div[contains(text(),'Select Portfolio')]/../div[2]/following-sibling::div/div[2]")
    public List<WebElement> LastUpdateColumn;

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
        BrowserUtils.waitForVisibility(FilterDropDown,30).click();
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

    public void RemoveRequests(int remainingRequests) {
        selectFilter("No Request Sent");
        while (removeButtons.size() > remainingRequests) {
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

        BrowserUtils.waitForVisibility(FilterDropDown, 30).click();
        BrowserUtils.wait(5);
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
            return onDemandReportingMenu.isDisplayed();
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
        return btnConfirmRequest.getText().replaceAll("[^0-9]", "");
        //new Scanner(btnConfirmRequest.getText()).useDelimiter("\\D+").toString();
    }

    public boolean validateIfOndemandmenuOptionIsEnabled() {
        while (onDemandReportingMenu.getAttribute("aria-disabled").equals("true")) {
            BrowserUtils.wait(1);
        }
        if (onDemandReportingMenu.getAttribute("aria-disabled").equals("false")) return true;
        else return false;

    }

    public void clickonOnRequestAssessmentButton() {
        BrowserUtils.scrollTo(buttonRequestAssessment);
        BrowserUtils.waitForClickablility(buttonRequestAssessment,60).click();

       /* if (validateIfOndemandmenuOptionIsEnabled()) {
            onDemandReportingMenu.click();
        }*/
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

    public int getNumberOfEmailInputs() {
        return emailInputs.size();
    }

    //index starts from 0
    public void enterEmail(String email, int index) {
        if (index > getNumberOfEmailInputs()) {
            System.out.println("Index is greater than number of email inputs");
            return;
        }
        while(emailInputs.get(index).getAttribute("value").length() > 0)
            emailInputs.get(index).sendKeys(Keys.BACK_SPACE);
        emailInputs.get(index).sendKeys(email, Keys.TAB);
    }

    public void verifyDuplicateEmailNotification() {
        assertTestCase.assertTrue(duplicateEmailNotification.size() > 0, "Duplicate email notification is displayed");
    }

    public void clickConfirmRequest() {
        System.out.println("Clicking on Confirm Request button");
        BrowserUtils.waitForClickablility(btnConfirmRequest, 60).click();
    }

    public void verifyConfirmRequestPopup(String prompt) {
        BrowserUtils.waitForVisibility(confirmRequestPopupHeader, 10);
        assertTestCase.assertTrue(confirmRequestPopupHeader.isDisplayed(), "Validate Confirm Request header");
        assertTestCase.assertTrue(confirmRequestPopupSubHeader.isDisplayed(), "Validate Sub Header Count and Text");
        assertTestCase.assertTrue(confirmRequestPopupBtnCancel.isDisplayed(), "Validate Cancel button is available in popup");
        assertTestCase.assertTrue(confirmRequestPopupBtnProceed.isDisplayed(), "Validate proceed button is available in popup");
        if(prompt.toLowerCase().equals("proceed")) {
            BrowserUtils.waitForClickablility(confirmRequestPopupBtnProceed, 60).click();
        }
        else{
            BrowserUtils.waitForClickablility(confirmRequestPopupBtnCancel, 60).click();
        }
    }

    public void verifyMethodologies() {
        String currentWindow = BrowserUtils.getCurrentWindowHandle();
        assertTestCase.assertTrue(btnMethodologies.isDisplayed(), "Methodologies button is displayed");
        BrowserUtils.waitForClickablility(btnMethodologies, 60).click();
        assertTestCase.assertTrue(methodologiesPopupTitle.isDisplayed(), "Methodologies popup is displayed");
        assertTestCase.assertTrue(methodologiesPopupListHeader.isDisplayed(), "Methodologies popup list header is displayed");
        assertTestCase.assertTrue(methodologiesPopupList.size()>0, "Methodologies popup list is displayed");
        assertTestCase.assertTrue(methodologiesPopupCloseButton.isDisplayed(), "Methodologies popup close button is displayed");
        assertTestCase.assertTrue(methodologiesPopupDownloadAllButton.isEnabled(), "Methodologies popup download all button is enabled");

        List<String> expectedMethodologiesList = Arrays.asList("Analyst", "Self Assessed", "Predicted");
        assertTestCase.assertTrue(BrowserUtils.getElementsText(methodologiesPopupList).containsAll(expectedMethodologiesList),
                "Methodologies popup list contains all expected methodologies");

        for(WebElement document : methodologiesPopupList) {
            Set<String> currentWindowHandles = Driver.getDriver().getWindowHandles();
            String documentName = document.getText();
            document.click();
            BrowserUtils.wait(3);
            BrowserUtils.switchToWindow(currentWindowHandles);
            switch (documentName) {
                case "Analyst":
                    assertTestCase.assertTrue(Driver.getDriver().getCurrentUrl().endsWith("Methodology_1.0_ESG_Assessment.pdf"), "Analyst methodology is opened");
                    break;
                case "Self Assessed":
                    assertTestCase.assertTrue(Driver.getDriver().getCurrentUrl().endsWith("Methodology_2.0_ESG_Assessment_20220517.pdf"), "Self Assessed methodology is opened");
                    break;
                case "Predicted":
                    assertTestCase.assertTrue(Driver.getDriver().getCurrentUrl().endsWith("Methodology_2.0_ESG_Assessment.pdf"), "Predicted methodology is opened");
                    break;
            }
            BrowserUtils.switchWindowsTo(currentWindow);
        }

        //Download and verify Zip file
        deleteFilesInDownloadsFolder();
        methodologiesPopupDownloadAllButton.click();
        assertTestCase.assertTrue(verifyIfDocumentsDownloaded(), "Methodologies zip file is downloaded");
        assertTestCase.assertTrue(unzipFile(), "Documents in ZIP File extracted and verified");
        BrowserUtils.waitForClickablility(methodologiesPopupCloseButton, 60).click();
    }

    public boolean verifyIfDocumentsDownloaded() {
        BrowserUtils.wait(10);
        File dir = new File(BrowserUtils.downloadPath());
        File[] dir_contents = dir.listFiles();
        if (dir_contents == null) {
            System.out.println("No files in the directory");
            return false;
        }
        return Arrays.stream(dir_contents).anyMatch(e -> ((e.getName().startsWith("methodologies")) && (e.getName().endsWith(".zip"))));
    }

    public boolean unzipFile() {
        File dir = new File(BrowserUtils.downloadPath());
        File[] dir_contents = dir.listFiles();
        assert dir_contents != null;
        String zipFilePath = Arrays.stream(dir_contents).filter(e -> ((e.getName().startsWith("methodologies")) && (e.getName().endsWith(".zip")))).findFirst().get().getAbsolutePath();
        System.out.println("zipFilePath = " + zipFilePath);
        String destDirectory = BrowserUtils.downloadPath();//+"/methodologies";
        System.out.println("destDirectory = " + destDirectory);
        UnzipUtil unZipper = new UnzipUtil();
        try {
            unZipper.unzip(zipFilePath, destDirectory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Unzipping is done");
        List<String> expectedDocumentList = Arrays.asList("Methodology_1.0_ESG_Assessment.pdf", "Methodology_2.0_ESG_Assessment.pdf", "Methodology_2.0_ESG_Assessment 20220517.pdf");
        for (String file : expectedDocumentList) {
            File document = new File(destDirectory+File.separator+file);
            if (!document.exists()) {
                System.out.println("File " + file + " is not found");
                return false;
            }
        }
        return true;
    }

    public int getRemainingAssessmentLimit() {
        //remainingAssessmentLimit.getText(); will give you "996 assessment requests" etc
        BrowserUtils.waitForVisibility(remainingAssessmentLimit, 30);
        return Integer.parseInt(remainingAssessmentLimit.getText().replaceAll("\\D",""));
    }
   public boolean validateNoPortfolio(){
        try {
            return BrowserUtils.waitForVisibility(noPortfolioAvailable, 10).isDisplayed();
        }catch(Exception e){
            return false;
        }
   }

    public boolean validateOnDemandReportingLandingPage(){
        return BrowserUtils.waitForVisibility(OnDemandMenuItem,10).getText().equals("ESG Reporting Portal");
    }

    public boolean isReequestAssessmentButtonDisabled(){
        return buttonRequestAssessment.getAttribute("class").contains("disabled");
    }

    public boolean isViewAssessmentRequestButtonDisabled(){
        return buttonViewAssessmentStatus.getAttribute("class").contains("disabled");
    }
    public boolean isbuttonMethodologiesEnabled(){
        return buttonMethodologies.getAttribute("class").contains("disabled");
    }

    public boolean isAssessmentsRemainingOptionAvailable(){
        return AssessmentsRemaining.getText().contains("Assessments remaining");
    }

    public boolean isExportbuttonDisabled(){
        return ExportButton.get(0).getAttribute("class").contains("disabled");
    }

    public void trySelectingMultiplePortfolios() {
       for(int i = 0 ; i< getPortfolioList().size() ; i++ ) {
            portfolioRadioButtonList.get(i).click();
        }
    }
    public void SelectPortfolioWithZeroOnDemandAssessmentEligibility() {
        for(int i = 0 ; i< getPortfolioList().size() ; i++ ) {
            if(OnDemandEligibility.get(i).getText().equals("0.00%")) {
                portfolioRadioButtonList.get(i).click();
                break;
            }
        }
    }

    public void ValidateSortingOnLastUpdateColumn() throws ParseException {
        for(int i = 1 ; i< getPortfolioList().size() ; i++ ) {
            Date previousRowDate = DateTimeUtilities.convertStringToDate(LastUpdateColumn.get(i-1).getText(),"MMMM dd, yyyy");
            Date currentRowDate = DateTimeUtilities.convertStringToDate(LastUpdateColumn.get(i).getText(),"MMMM dd, yyyy");
            assertTestCase.assertTrue(previousRowDate.compareTo(currentRowDate) >= 0,"Validating sorting order of Last Updated column in portfolio table, Dates should be in Descending order");
        }
    }

    public Map<String,String> getPortfolioCoverageAndOnDemadEligibilityValues(String PortfolioName){
        Map<String,String> returnValue = new HashMap<>();
         for(int i = 0 ; i< getPortfolioList().size() ; i++ ) {
            if (getPortfolioList().get(i).equals(PortfolioName)){
                returnValue.put("Coverage",portfolioCoverage.get(i).getText());
                returnValue.put("ONDemandEligibility",OnDemandEligibility.get(i).getText());
             break;
            }

        }
        return returnValue ;
    }


}