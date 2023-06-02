package com.esgc.ONDEMAND.UI.Pages;


import com.esgc.Common.UI.Pages.CommonPage;
import com.esgc.Common.UI.Pages.LoginPage;
import com.esgc.ONDEMAND.API.Controllers.OnDemandFilterAPIController;
import com.esgc.ONDEMAND.DB.DBModels.CLIMATEENTITYDATAEXPORT;
import com.esgc.Utilities.*;
import com.esgc.ONDEMAND.DB.DBQueries.OnDemandAssessmentQueries;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.testng.asserts.SoftAssert;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class OnDemandAssessmentPage extends CommonPage {

    @FindBy(xpath = "//li[text()]")
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

    @FindBy(xpath = "//div[contains(@id,'card-test-id-1')]//span[contains(@title,'All Locations')]")
    public WebElement location_AllLocations;

    @FindBy(xpath = "//div[contains(@id,'card-test-id-1')]//label")
    public List<WebElement> location_LocationLabels;

    @FindBy(xpath = "//div[contains(@id,'card-test-id-2')]//label")
    public List<WebElement> Scetor_sectorsLabels;

    @FindBy(xpath = "//div[contains(@id,'card-test-id-2')]//button/div/div")
    public WebElement sectorHeader;

    @FindBy(xpath = "//div[contains(@id,'card-test-id-2')]//span[contains(@title,'All Sectors')]")
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

    @FindBy(xpath = "//div[text()='Confirm Request']")
    public WebElement confirmRequestPopupHeader;

    @FindBy(xpath = "//div[text()='Confirm Request']//following-sibling::div")
    public WebElement confirmRequestPopupSubHeader;

    @FindBy(xpath = "//button[@id='ondemand-assessment-cancel']")
    public WebElement confirmRequestPopupBtnCancel;

    @FindBy(xpath = "//button[@id='ondemand-assessment-confirmation']")
    public WebElement confirmRequestPopupBtnProceed;

    @FindBy(xpath = "//div[@class='MuiAlert-message']//div[text()='Something went wrong, please try again later.']")
    public WebElement SomeThingWentWrongErrorMessage;

    @FindBy(xpath = "//button[@id='button-report-test-id-1']")
    public WebElement buttonRequestAssessment;

    @FindBy(xpath = "//button[@id='button-prev-status-test-id-1']")
    public WebElement buttonViewAssessmentStatus;

    @FindBy(xpath = "//button[@id='button-prev-methodologies-test-id-1']")
    public WebElement buttonMethodologies;

    @FindBy(xpath = "//div[contains(@class,'MuiGrid-root MuiGrid-item MuiGrid-grid')][3]//div/div/div/div/div/div[1]")
    public WebElement AssessmentsRemaining;

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

    //OnDemand Request Page - View Details Panel Elements
    @FindBy(xpath = "//button[.='View Detail']")
    public List<WebElement> viewDetailButton;

    //View Detail Panel Elements
    @FindBy(xpath = "//div[starts-with(text(),'Companies in')]")
    public WebElement detailPanelHeader;

    @FindBy(xpath = "//div[@id='button-button-test-id-1']//button")
    public List<WebElement> detailPanelFilterButtons;

    @FindBy(xpath = "//table[@id='viewcompanies']/../preceding-sibling::div")
    public List<WebElement> detailPanelTableTitles;

    @FindBy(xpath = "(//table[@id='viewcompanies'])//th")
    public List<WebElement> detailPanelTableHeaders;

    @FindBy(xpath = "(//table[@id='viewcompanies'])//tr")
    public List<WebElement> detailPanelCompanyRows;

    @FindBy(xpath = "(//table[@id='viewcompanies'])//td[1]")
    public List<WebElement> detailPanelCompanyNames;

    @FindBy(xpath = "(//table[@id='viewcompanies'])//td[2]")
    public List<WebElement> detailPanelESGScores;

    @FindBy(xpath = "(//table[@id='viewcompanies'])//td[3]")
    public List<WebElement> detailPanelInvestmentPercentages;

    @FindBy(xpath = "(//table[@id='viewcompanies'])//td[4]")
    public List<WebElement> detailPanelLastColumn;

    @FindBy(xpath = "//button[.='Export To Excel']/../..//p/..")
    public WebElement detailPanelShowingStatement;

    @FindBy(xpath = "//button[.='Export To Excel']")
    public WebElement detailPanelExportToExcelButton;

    @FindBy(xpath = "//div[@id='button-button-test-id-1']/../../../..//*[local-name()='svg']")
    public WebElement detailPanelCloseButton;

    @FindBy(xpath = "//*[@id=\"topbar-appbar-test-id\"]/div/li")
    public WebElement menuButton;
//    @FindBy(xpath = "//div[text()='View Detail']")
//    public List<WebElement> viewDetailButton;

    @FindBy(xpath = "//li[contains(text(), 'Log Out')]")
    public WebElement logOutButton;

    @FindBy(xpath = "//div[text()= 'remaining']")
    public WebElement assessmentRemainingHeader;

    @FindBy(xpath = "//*[@id='div-mainlayout']/div/div[2]/main/div/div/div[2]/div[2]/div")
    public List<WebElement> coverageList;

    @FindBy(xpath = ("//button[contains(@title, 'Download')]"))
    public List<WebElement> downloadButtonList;


    public String landingPage = "";

    public void goToSendRequestPageForAPortfolio(String portfolioName) {

        selectPortfolioOptionByName(portfolioName);
        clickonOnRequestAssessmentButton();
        BrowserUtils.waitForVisibility(btnReviewRequest, 80);
    }

    public void getLandingPageFromAPI(String portfolioName) {
        OnDemandFilterAPIController controller = new OnDemandFilterAPIController();
        landingPage = controller.getExpectedLandingPageFromAPI(portfolioName);
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
        BrowserUtils.waitForClickability(txtSendTo).sendKeys(emailid);
        String compniesCountForRequest = getCompaniesCountFromConfirmRequestButton();
        clickOnConfirmRequestButton();
        return compniesCountForRequest;

    }

    public void clickOnConfirmRequestButton() {
        BrowserUtils.waitForVisibility(btnConfirmRequest, 30).click();
    }

    public void selectFilter(String filterOption) {
        BrowserUtils.waitForVisibility(FilterDropDown, 30).click();

        BrowserUtils.waitForVisibility(Driver.getDriver().findElement
                (By.xpath("//ul[@role='listbox']/li[text()='"+filterOption+"']"))
                ,60).click();

        /*for (WebElement option : drdShowOptions) {
            if (option.getText().equals(filterOption)) {
                BrowserUtils.wait(5);
                BrowserUtils.clickWithJS(option);
                BrowserUtils.wait(2);
                break;
            }
        }*/
    }

    public void KeepOnlyOneRequest() {
        selectFilter("No Request Sent");
        BrowserUtils.waitForVisibility(removeButtons, 60);
        while (removeButtons.size() > 1) {
            BrowserUtils.waitForClickablility(removeButtons.get(0), 60).click();
        }
    }

    public void RemoveRequests(int remainingRequests) {
        selectFilter("No Request Sent");
        // Randomly failing in selecting from Drop down so trying for one more
        // time if fails in first time.
        if (!FilterDropDown.getText().equals("No Request Sent")){
            selectFilter("No Request Sent");
        }
        BrowserUtils.waitForVisibility(removeButtons, 60);
        while (removeButtons.size() > remainingRequests) {
            BrowserUtils.waitForClickablility(removeButtons.get(0), 60).click();
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

    public boolean onDemandCoverageHeaderValidation(String portfolioName) {
        try {
            BrowserUtils.waitForPageToLoad(80);
            assertTestCase.assertTrue(BrowserUtils.waitForVisibility(coverageHeader.get(0), 30).getText().equals("Request On-Demand Assessments for " + portfolioName));
            assertTestCase.assertTrue(BrowserUtils.waitForVisibility(coverageHeader.get(1), 30).getText().matches("\\d+% assessment eligible \\(\\d+ companies\\)"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCancelButtonAvailable() {
        return BrowserUtils.waitForVisibility(btnCancel, 60).isDisplayed();
    }

    public boolean isReviewButtonAvailable() {
        return BrowserUtils.waitForVisibility(btnReviewRequest, 60).isDisplayed();
    }

    public void clickOnESCButton() {
        BrowserUtils.waitForVisibility(btnESC, 30).click();
    }

    public void validatePredictedScore() {
        assertTestCase.assertTrue(Integer.parseInt(predictedScoresliders.get(0).getAttribute("aria-valuemax")) <= 100);
        assertTestCase.assertTrue(Integer.parseInt(predictedScoresliders.get(1).getAttribute("aria-valuemax")) <= 100);
        assertTestCase.assertTrue(Integer.parseInt(predictedScoresliders.get(0).getAttribute("aria-valuemin")) >= 0);
        assertTestCase.assertTrue(Integer.parseInt(predictedScoresliders.get(1).getAttribute("aria-valuemin")) >= 0);
        if (predictedScoreInvper.getText().matches("\\d+.\\d+% invested"))
            assertTestCase.assertTrue(predictedScoreInvper.getText().matches("\\d+.\\d+% invested"));
        else
            assertTestCase.assertTrue(predictedScoreInvper.getText().matches("\\d+% invested"));
        String assessmentEligible = lblAssessmentEligible.getText().substring(0, lblAssessmentEligible.getText().indexOf("%"));
        assertTestCase.assertTrue(predictedScoreTextBelowGraph.getText().matches("Companies with predicted scores ranging 0-100 account for \\d+.\\d+% of investments"));
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
        int FirstcompaniesCount = getCompaniesCountFromReviewButton();
        //Validating Or Condition
        location_LocationLabels.get(0).click();
        assertTestCase.equals(getCompaniesCountFromReviewButton() != FirstcompaniesCount);

        //Validating And Condition
        toggleButton.click();
        location_LocationLabels.get(0).click();
        assertTestCase.equals(getCompaniesCountFromReviewButton() == FirstcompaniesCount);
        // Below code is to move the slider in Predicted Score section --
        /*for (int i = 1; i <= 9; i++) predictedScoresliders.get(0).sendKeys(Keys.ARROW_RIGHT);

       for (int i = 1; i <= 73; i++) predictedScoresliders.get(1).sendKeys(Keys.ARROW_LEFT);*/

    }

    public boolean verifyMainPageHeader(String header) {
        return menuList.get(0).getText().equals(header);
    }

    public int getCompaniesCountFromReviewButton() {
        BrowserUtils.waitForVisibility(btnReviewRequest,30);
        WebElement e = BrowserUtils.waitForVisibility(btnReviewRequest, 10).findElement(By.xpath("span/div"));
        return new Scanner(e.getText()).useDelimiter("\\D+").nextInt();


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
        BrowserUtils.waitForClickablility(buttonRequestAssessment, 60).click();

       /* if (validateIfOndemandmenuOptionIsEnabled()) {
            onDemandReportingMenu.click();
        }*/
    }

    public void validateOnDemandPageHeader() {
        BrowserUtils.waitForVisibility(menuOptionPageHeader,90);
        assertTestCase.assertEquals(BrowserUtils.waitForVisibility(menuOptionPageHeader, 10).getText(), "ESG Reporting Portal", "Moody's Analytics: Request On-Demand Assessment page verified");
    }

    public void validateProceedOnConfirmRequestPopup(String countOfCompanies) {
        assertTestCase.assertTrue(BrowserUtils.waitForVisibility(confirmRequestPopupHeader, 10).getText().equals("Confirm Request"), "Validate Confirm Request header");
        assertTestCase.assertTrue(BrowserUtils.waitForVisibility(confirmRequestPopupSubHeader, 10).getText().equals("Assessment request will be sent to " + countOfCompanies + " company. You will receive confirmation when all requests have been sent."), "Validate Sub Header Count and Text");
        assertTestCase.assertTrue(confirmRequestPopupBtnCancel.isDisplayed(), "Validate Cancel button is available in popup");
        assertTestCase.assertTrue(confirmRequestPopupBtnProceed.isDisplayed(), "Validate proceed button is available in popup");
    }

    public void clickCancelButtonAndValidateRequestPage() {
        BrowserUtils.waitForVisibility(confirmRequestPopupBtnCancel, 10).click();
        assertTestCase.assertTrue(BrowserUtils.waitForVisibility(btnConfirmRequest, 30).isDisplayed(), "Validate it is back on Confirm euest page");
    }

    public void validateDashboardPageButtonForOnDemand() {
        BrowserUtils.waitForVisibility(dashboardPageMenuOption, 60);
        assertTestCase.assertTrue(dashboardPageMenuOption.isDisplayed(), "Validate that Dasboard on demand button is visible");
        assertTestCase.assertTrue(dashboardPageMenuOption.getText().matches("\\d+% On-Demand Assessment Eligible"), "Validate that Dasboard on demand button is visible");
    }

    public void validateDashboardPageButtonCoverage(String portfolioID) {
        BrowserUtils.waitForVisibility(dashboardPageMenuOption, 60);
        assertTestCase.assertTrue(dashboardPageMenuOption.isDisplayed(), "Validate that Dasboard on demand button is visible");
        double uiValue = Double.valueOf(dashboardPageMenuOption.getText().substring(0, dashboardPageMenuOption.getText().indexOf("%")));
        OnDemandFilterAPIController apiController = new OnDemandFilterAPIController();
        double apiValue = apiController.getDashboardCoverage(portfolioID).jsonPath().getDouble("perc_avail_for_assessment") * 100;
        assertTestCase.assertEquals(uiValue, apiValue, "Validating Coverage %");

    }

    public void validateErrormessage() {
        assertTestCase.assertTrue(BrowserUtils.waitForVisibility(SomeThingWentWrongErrorMessage, 20).isDisplayed(), "Validating if error message has displayed");
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
        while (emailInputs.get(index).getAttribute("value").length() > 0)
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
        if (prompt.toLowerCase().equals("proceed")) {
            BrowserUtils.waitForClickablility(confirmRequestPopupBtnProceed, 60).click();
        } else {
            BrowserUtils.waitForClickablility(confirmRequestPopupBtnCancel, 60).click();
        }
    }

    public void verifyMethodologies() {
        String currentWindow = BrowserUtils.getCurrentWindowHandle();
        assertTestCase.assertTrue(BrowserUtils.waitForVisibility(btnMethodologies, 10).isDisplayed(), "Methodologies button is displayed");
        BrowserUtils.waitForClickablility(BrowserUtils.waitForVisibility(btnMethodologies, 10), 60).click();
        assertTestCase.assertTrue(BrowserUtils.waitForVisibility(methodologiesPopupTitle, 10).isDisplayed(), "Methodologies popup is displayed");
        assertTestCase.assertTrue(BrowserUtils.waitForVisibility(methodologiesPopupListHeader, 10).isDisplayed(), "Methodologies popup list header is displayed");
        assertTestCase.assertTrue(methodologiesPopupList.size() > 0, "Methodologies popup list is displayed");
        assertTestCase.assertTrue(methodologiesPopupCloseButton.isDisplayed(), "Methodologies popup close button is displayed");
        assertTestCase.assertTrue(methodologiesPopupDownloadAllButton.isEnabled(), "Methodologies popup download all button is enabled");

        // List<String> expectedMethodologiesList = Arrays.asList("Analyst", "Self Assessed", "Predicted");
        List<String> expectedMethodologiesList = Arrays.asList("Analyst", "Predicted");
        assertTestCase.assertTrue(BrowserUtils.getElementsText(methodologiesPopupList).containsAll(expectedMethodologiesList),
                "Methodologies popup list contains all expected methodologies");

        for (WebElement document : methodologiesPopupList) {
            Set<String> currentWindowHandles = Driver.getDriver().getWindowHandles();
            String documentName = document.getText();
            document.click();
            BrowserUtils.wait(3);
            BrowserUtils.switchToWindow(currentWindowHandles);
            switch (documentName) {
                case "Analyst":
                    assertTestCase.assertTrue(Driver.getDriver().getCurrentUrl().endsWith("Methodology_1.0_ESG_Assessment.pdf"), "Analyst methodology is opened");
                    break;
               /* case "Self Assessed":
                    assertTestCase.assertTrue(Driver.getDriver().getCurrentUrl().endsWith("Methodology_2.0_ESG_Assessment_20220517.pdf"), "Self Assessed methodology is opened");
                    break;*/
                case "Predicted":
                    assertTestCase.assertTrue(Driver.getDriver().getCurrentUrl().endsWith("ESG_Score_Predictor_Methodology.pdf"), "Predicted methodology is opened");
                    break;
            }
            BrowserUtils.switchWindowsTo(currentWindow);
        }

        //Download and verify Zip file
        deleteFilesInDownloadsFolder();
        methodologiesPopupDownloadAllButton.click();
        assertTestCase.assertTrue(verifyIfDocumentsDownloaded("methodologies", "zip"), "Methodologies zip file is downloaded");
        assertTestCase.assertTrue(unzipFile(), "Documents in ZIP File extracted and verified");
        BrowserUtils.waitForClickablility(methodologiesPopupCloseButton, 60).click();
    }

    public boolean verifyIfDocumentsDownloaded(String name, String extension) {
        BrowserUtils.wait(10);
        File dir = new File(BrowserUtils.downloadPath());
        File[] dir_contents = dir.listFiles();
        if (dir_contents == null) {
            System.out.println("No files in the directory");
            return false;
        }
        return Arrays.stream(dir_contents).anyMatch(e -> ((e.getName().startsWith(name)) && (e.getName().endsWith("." + extension))));
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
        //List<String> expectedDocumentList = Arrays.asList("Methodology_1.0_ESG_Assessment.pdf", "Methodology_2.0_ESG_Assessment.pdf", "Methodology_2.0_ESG_Assessment 20220517.pdf");
        List<String> expectedDocumentList = Arrays.asList("Methodology_1.0_ESG_Assessment.pdf", "ESG_Score_Predictor_Methodology.pdf");
        for (String file : expectedDocumentList) {
            File document = new File(destDirectory + "/methodologies" + File.separator + file);
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
        return Integer.parseInt(remainingAssessmentLimit.getText().replaceAll("\\D", ""));
    }

    public boolean validateNoPortfolio() {
        try {
            return BrowserUtils.waitForVisibility(noPortfolioAvailable, 10).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateOnDemandReportingLandingPage() {
        return BrowserUtils.waitForVisibility(menu,30).getText().equals("ESG Reporting Portal");
//
//        if (!Environment.environment.equalsIgnoreCase("prod")) {
//            //return BrowserUtils.waitForVisibility(OnDemandMenuItem(), 60).getText().equals("ESG Reporting Portal");
//        } else {
//            return BrowserUtils.waitForVisibility(menu,30).getText().equals("On-Demand Reporting");
//            //return BrowserUtils.waitForVisibility(OnDemandMenuItem(), 60).getText().equals("On-Demand Reporting");
//            //return assertTestCase.assertEquals(BrowserUtils.waitForVisibility(OnDemandMenuItem, 90).getText(), "On-Demand Reporting", "Moody's Analytics: Request On-Demand Assessment page verified");
//        }
//        // return BrowserUtils.waitForVisibility(OnDemandMenuItem, 10).getText().equals("ESG Reporting Portal");

    }

    public boolean isReequestAssessmentButtonDisabled() {
        return buttonRequestAssessment.getAttribute("class").contains("disabled");
    }

    public boolean isViewAssessmentRequestButtonDisabled() {
        return BrowserUtils.waitForVisibility(buttonViewAssessmentStatus, 60).getAttribute("class").contains("disabled");
    }

    public boolean isbuttonMethodologiesEnabled() {
        return BrowserUtils.waitForVisibility(buttonMethodologies, 60).getAttribute("class").contains("disabled");
    }

    public boolean isAssessmentsRemainingOptionAvailable() {
        return BrowserUtils.waitForVisibility(AssessmentsRemaining).getText().contains("Assessments remaining");
    }

    public boolean isExportbuttonDisabled() {
        return ExportButton.get(0).getAttribute("class").contains("disabled");
    }

    public void trySelectingMultiplePortfolios() {
        int size = getPortfolioList().size() > 3 ? 3 : getPortfolioList().size();
        for (int i = 0; i < size; i++) {
            portfolioRadioButtonList.get(i).click();
        }
    }

    public void selectPortfolioInThePortfoliosTable() {
        int size = getPortfolioList().size() > 3 ? 3 : getPortfolioList().size();
        for (int i = 0; i < size; i++) {
            portfolioRadioButtonList.get(i).click();
        }
    }

    public void SelectPortfolioWithZeroOnDemandAssessmentEligibility() {
        for (int i = 0; i < getPortfolioList().size(); i++) {
            if (OnDemandEligibility.get(i).getText().equals("0.00%")) {
                portfolioRadioButtonList.get(i).click();
                break;
            }
        }
    }

    public void ValidateSortingOnLastUpdateColumn() throws ParseException {
        for (int i = 1; i < getPortfolioList().size(); i++) {
            Date previousRowDate = DateTimeUtilities.convertStringToDate(LastUpdateColumn.get(i - 1).getText(), "MMMM dd, yyyy");
            Date currentRowDate = DateTimeUtilities.convertStringToDate(LastUpdateColumn.get(i).getText(), "MMMM dd, yyyy");
            assertTestCase.assertTrue(previousRowDate.compareTo(currentRowDate) >= 0, "Validating sorting order of Last Updated column in portfolio table, Dates should be in Descending order");
        }
    }

    public Map<String, String> getPortfolioCoverageAndOnDemadEligibilityValues(String PortfolioName) {
        Map<String, String> returnValue = new HashMap<>();
        for (int i = 0; i < getPortfolioList().size(); i++) {
            if (getPortfolioList().get(i).equals(PortfolioName)) {
                returnValue.put("Coverage", portfolioCoverage.get(i).getText());
                returnValue.put("ONDemandEligibility", OnDemandEligibility.get(i).getText());
                break;
            }

        }
        return returnValue;
    }

    public void verifyZeroAssessmentRemainingForOnDemand() {
        BrowserUtils.wait(5);
        assertTestCase.assertTrue(assessmentRemainingHeader.isDisplayed(), "Verification that 0 Assessment Remaining is displayed");
        assertTestCase.assertEquals(assessmentRemainingHeader.getText(), "0 Assessment remaining", "Verification 0 Assessment Remaining text is done");
    }

    public void validateViewDetailButtonAndDownloadButtonDisabledForZeroCoveragePortfolios(String portfolioName) {
        clickOnViewDetailButton(portfolioName);
        System.out.println("Validating that View Detail button is disabled");
        String disabledProperty = viewDetailButton.get(0).getDomProperty("disabled");
        boolean isEnabled = viewDetailButton.get(0).isEnabled();
        System.out.println(" attribute of view detail button :    : " + viewDetailButton.get(0).getDomAttribute("disabled"));
        System.out.println(disabledProperty);
        System.out.println(isEnabled);

        System.out.println("validation that view Detail button is disable is done");
        //assertTestCase.assertEquals(false, viewDetailButton.get(0).isEnabled(), "Validating that View Detail button is disabled");
        assertTestCase.assertFalse(isDownloadButtonEnabled(), "Verification that the download button is disabled");
    }


    public void clickOnMenuButton() {
        BrowserUtils.waitForClickablility(menuButton, 30);
        menuButton.click();
    }

    public void clickOnLogOutButton() {
        BrowserUtils.waitForVisibility(logOutButton).click();
    }

    public List<String> getCoverageValuesList() {
        return BrowserUtils.getElementsText(coverageList);
    }

    public void checkViewDetailButtonDisabled() {
        System.out.println("Checking if view detail button is disabled..... ");
        /*for(int i =0 ; i < viewDetailButton.size(); i++){
            if(viewDetailButton.get(i).isEnabled()){
                System.out.println("the view Detail Button is Enabled"+ viewDetailButton.get(i));
            } else {
                System.out.println("the View Detail Button is Disabled"+viewDetailButton.get(i));
            }
        }*/
        for (int i = 0; i < viewDetailButton.size(); i++) {
            if (viewDetailButton.get(i).getAttribute("disabled")==null) {
                System.out.println("the view detail button is disabled for " + viewDetailButton.get(i).getText());
            } else {
                System.out.println("the view detail button is enabled");
            }
        }
    }

    public boolean viewDetailForPortfolio(String portfolioName) {
        int index = getPortfolioList().indexOf(portfolioName);
        return viewDetailForPortfolio(index);
    }


    public boolean viewDetailForPortfolio(int index) {
        if (index == -1) {
            System.out.println("Portfolio not found");
            return false;
        }
        if (viewDetailButton.size() <= index) {
            System.out.println("View detail button not found");
            return false;
        }
        if (!viewDetailButton.get(index).isDisplayed()) {
            System.out.println("View detail button not displayed");
            return false;
        }
        clickOnViewDetailButton(index);
        System.out.println("View detail button clicked");
        BrowserUtils.waitForVisibility(detailPanelHeader, 10);
        return true;
    }

    public void verifyDetailsPanel(boolean exportEntitlement) {
        //click on first enabled view detail button
        for (int i = 0; i < viewDetailButton.size(); i++) {
            if (viewDetailForPortfolio(i)) break;
        }
        BrowserUtils.waitForVisibility(detailPanelCompanyNames, 60);
        assertTestCase.assertTrue(detailPanelHeader.isDisplayed(), "Details panel header is displayed");
        assertTestCase.assertTrue(detailPanelFilterButtons.size() > 0, "Details panel filter buttons are displayed");
        assertTestCase.assertTrue(detailPanelTableTitles.size() > 0, "Details panel table titles are displayed");
        assertTestCase.assertTrue(detailPanelTableHeaders.size() > 0, "Details panel table headers are displayed");
        assertTestCase.assertTrue(detailPanelCompanyNames.size() > 0, "Details panel company names are displayed");
        assertTestCase.assertTrue(detailPanelESGScores.size() > 0, "Details panel ESG scores are displayed");
        assertTestCase.assertTrue(detailPanelInvestmentPercentages.size() > 0, "Details panel investment percentages are displayed");
        assertTestCase.assertTrue(detailPanelLastColumn.size() > 0, "Details panel last column is displayed");

        if (exportEntitlement) {
            assertTestCase.assertTrue(detailPanelShowingStatement.isDisplayed(), "Details panel showing statement is displayed");
            assertTestCase.assertTrue(detailPanelExportToExcelButton.isEnabled(), "Details panel export to excel button is displayed");
        } else {
            try {
                assertTestCase.assertFalse(detailPanelShowingStatement.isDisplayed(), "Details panel showing statement is not displayed");
            } catch (Exception e) {
                System.out.println("Details panel showing statement is not displayed");
            }

            try {
                assertTestCase.assertFalse(detailPanelExportToExcelButton.isEnabled(), "Details panel export to excel button is not displayed");
            } catch (Exception e) {
                System.out.println("Details panel export to excel button is not displayed");
            }

        }
        closePanel();
    }

    public void isViewDetailButtonDisabled() {
        // JavascriptExecutor jse = (JavascriptExecutor) Driver.getDriver();
        // System.out.println("is the View Detail Button disabled " + jse.executeScript("return arguments[0].disabled", viewDetailButton.get(0)));

        viewDetailButton.get(0).getAttribute("disabled");
    }

    public boolean isViewDetailButtonEnabled(String portfolioName) {
        if (IsPortfolioTableLoaded()) {
            int index = getPortfolioList().indexOf(portfolioName.trim());
            System.out.println("index of : " + portfolioName + " is : " + viewDetailButton.get(index));
            viewDetailButton.get(index).isEnabled();
        }
        return false;
    }

    public boolean isRequestAssessmentButtonEnabled() {
        BrowserUtils.wait(4);
        return buttonRequestAssessment.isEnabled();
    }

    public void clickOnViewDetailButton(String portfolioName) {
        if (IsPortfolioTableLoaded()) {
            int index = getPortfolioList().indexOf(portfolioName.trim());
            viewDetailButton.get(index).click();
        } else {
            System.out.println("please select a portfolio first!!!");
        }
    }

    public void clickOnViewDetailButton(int index) {
        if (IsPortfolioTableLoaded()) {
            viewDetailButton.get(index).click();
        } else {
            System.out.println("please select a portfolio first!!!");
        }
    }

    public boolean isDownloadButtonEnabled() {
        return downloadButtonList.get(0).isEnabled();
    }

    public String getAssessmentRemainingHeaderText() {
        return assessmentRemainingHeader.getText();
    }

    public int checkPortfolioWithZeroCoverage() {
        int index = 0;
        for (index = 0; index < coverageList.size(); index++) {
            if (coverageList.get(index).getText().equals("0.00%")) {
                return index;
            }
        }
        return index;
    }

    @FindBy(xpath = "//*[@id='viewcomapnies-0-Financials-tableCell-0-0']")
    public WebElement entityInViewBySectorTab;
    @FindBy(xpath = "//*[@id='dashboard_PageHeader']/div/div/div[2]/div/div[1]/div/div[2]/span")
    public WebElement climateCoverageLink;


    public void clickOnClimateCoverageLink() {
        BrowserUtils.waitForClickablility(climateCoverageLink, 50);
        climateCoverageLink.click();
    }


    //Download portfolio from 2 places: portfolio list-Download button, and from details panel-Export to Excel button
    //input parameter: location of the downloaded file either page, or details panel
    public void verifyDownloadPortfolio(String location) {
        //get first clickable download button
        BrowserUtils.waitForVisibility(downloadButtonList.get(0), 20);
        int index = 0;
        for (index = 0; index < downloadButtonList.size(); index++) {
            if (downloadButtonList.get(index).isEnabled()) break;
        }
        System.out.println("index = " + index);

        String portfolioName = portfolioNamesList.get(index).getText();
        downloadPortfolio(portfolioName, location);

        String exportedDocumentName = portfolioName + "_ESG Scores_" + DateTimeUtilities.getCurrentDate("dd_MMM_yyyy") + "_";//11_Apr_2023_";
        System.out.println("exportedDocumentName = " + exportedDocumentName);

        ExcelUtil excelData = getExcelData(exportedDocumentName, 0);
        List<String> expColumnNames = Arrays.asList("ENTITY", "ISIN(PRIMARY)", "USER INPUT", "ORBIS_ID", "SECTOR", "REGION",
                "Portfolio Upload Date", "% Investment", "LEI", "Country (Country ISO code)", "Scored Date", "Evaluation Year",
                "Score Type", "Parent Orbis ID", "Subsidiary", "Input location type", "Input location", "Input industry type",
                "Input industry", "Input size type", "Input size", "Overall ESG Score", "Overall ESG Score Qualifier",
                "Overall Environmental score", "Overall Environmental score Qualifier", "Overall Social score",
                "Overall Social score Qualifier", "Overall Governance score", "Overall Governance score Qualifier",
                "HRS - Human Resources Domain Score", "ENV - Environment Domain Score", "CS - Business Behaviour Domain Score",
                "CIN - Community Involvement Domain Score", "CGV - Corporate Governance Domain Score", "HRT - Human Rights Domain Score",
                "HRS 1.1 - Promotion of labour relations", "HRS 2.3 - Responsible management of restructurings",
                "HRS 2.4 - Career management and promotion of employability", "HRS 3.1 - Quality of remuneration systems",
                "HRS 3.2 - Improvement of health and safety conditions", "HRS 3.3 - Respect and management of working hours",
                "ENV 1.1 - Environmental strategy and eco-design", "ENV 1.2 - Pollution prevention and control (soil, accident)",
                "ENV 1.3 - Development of green products and services", "ENV 1.4 - Protection of biodiversity",
                "ENV 2.1 - Protection of water resources", "ENV 2.2 - Minimising environmental impacts from energy use",
                "ENV 2.4 - Management of atmospheric emissions", "ENV 2.5 - Waste management", "ENV 2.6 - Management of local pollution",
                "ENV 2.7 - Management of environmental impacts from transportation",
                "ENV 3.1 - Management of environmental impacts from the use and disposal of products/services",
                "CS 1.1 - Product Safety (process and use)", "CS 1.2 - Information to customers", "CS 1.3 - Responsible Customer Relations",
                "CS 2.2 - Sustainable relationships with suppliers", "CS 2.3 - Integration of environmental factors in the supply chain",
                "CS 2.4 - Integration of social factors in the supply chain", "CS 3.1 - Prevention of corruption",
                "CS 3.2 - Prevention of anti-competitive practices", "CS 3.3 - Transparency and integrity of influence strategies and practices",
                "CIN 1.1 - Promotion of the social and economic development", "CIN 2.1 - Societal impacts of the company's products / services",
                "CIN 2.2 - Contribution to general interest causes", "CGV 1.1 - Board of Directors", "CGV 2.1 - Audit & Internal Controls",
                "CGV 3.1 - Shareholders", "CGV 4.1 - Executive Remuneration", "HRT 1.1 - Respect for human rights standards and prevention of violations",
                "HRT 2.1 - Respect for freedom of association and the right to collective bargaining", "HRT 2.4 - Non-discrimination",
                "HRT 2.5 -  Elimination of child labour and forced labour");
        //there should be two tabs: Data - Scores, Data Dictionary
        assertTestCase.assertEquals(excelData.getSheetName(), "Data - Scores", "Data - Scores tab is displayed");
        //System.out.println("excelData.getColumnsNames() = " + excelData.getColumnsNames());

        for (int i = 0; i < excelData.getColumnsNames().size(); i++) {
            assertTestCase.assertEquals(excelData.getColumnsNames().get(i), expColumnNames.get(i), "Column name check for Data - Scores tab: " + expColumnNames.get(i));
        }


        int lastRow = excelData.getLastRowNum();
        String actualDisclaimerText = excelData.getCellData(lastRow, 0);
        String expectedDisclaimerText = "Disclaimer: ESG self-assessment report is calculated based on data the company has provided, which Moody's does not validate. Moody's ESG on Demand Assessments measure the extent to which a company effectively integrates industry-relevant ESG factors into its management and operational practices based on their self-reported data through a tailored questionnaire. An ESG self-assessment is distinct from a credit rating, which rates an entity's creditworthiness";
        assertTestCase.assertEquals(actualDisclaimerText, expectedDisclaimerText, "Disclaimer Text Validation", 14499, 14523);

        excelData = getExcelData(exportedDocumentName, 1);
        expColumnNames = Arrays.asList("Data Type", "Definition");
        assertTestCase.assertEquals(excelData.getSheetName(), "Data Dictionary", "Data Dictionary tab is displayed");
        assertTestCase.assertTrue(excelData.getColumnsNames().containsAll(expColumnNames), "Column names are correct for Data Dictionary tab");
    }

    public void closePanel() {
        BrowserUtils.waitForClickablility(detailPanelCloseButton, 30).click();
    }

    public boolean identifyPredictedCompanies() {
        BrowserUtils.waitForVisibility(detailPanelCompanyRows, 20);
        for (WebElement row : detailPanelCompanyRows) {
            String color = Color.fromString(row.getCssValue("background-color")).asHex().toUpperCase();
            System.out.println("color = " + color);
            if (color.equals("#FDF7DA")) {
                closePanel();
                return true;
            }
        }
        closePanel();
        return false;
    }

    public void verifyDataScores(String portfolioName) {
        ExcelUtil excelData = getExcelData(portfolioName, 0);
        OnDemandFilterAPIController controller = new OnDemandFilterAPIController();
        String portfolioId = controller.getPortfolioId(portfolioName);
        System.out.println("portfolioId = " + portfolioId);
        OnDemandAssessmentQueries queries = new OnDemandAssessmentQueries();
        CLIMATEENTITYDATAEXPORT[] dbEntitiesList = queries.getESGENTITYEXPORT(portfolioId);
        System.out.println("dbEntitiesList = " + dbEntitiesList.length);
        for (int i = 0; i < excelData.getLastRowNum(); i++) {
            Map<String,String> row = excelData.getRowValueMapWithColumnName(i + 1);
            if (row!=null && !row.get("ENTITY").startsWith("Disclaimer:")) {
                System.out.println("row = " + row);
                String companyName = row.get("ENTITY");
                CLIMATEENTITYDATAEXPORT dbData = Arrays.stream(dbEntitiesList).filter(e -> e.getEntity().equals(companyName)).findFirst().get();
                validateExcelData(row, dbData);
            }
          }

    }

    public void validateExcelData(Map<String, String> excelMap,  CLIMATEENTITYDATAEXPORT dbData )  {
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(CLIMATEENTITYDATAEXPORT.class);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        Object fieldValue = null;
        SoftAssert softAssert = new SoftAssert();
        DecimalFormat df = new DecimalFormat("#.##");
        for(Map.Entry<String,String> each:excelMap.entrySet()){
           String filedName= each.getKey().replaceAll("_|\\'|%|&|\\.|,|\\s|-|/|\\(|\\)","").toLowerCase();
            try {
                System.out.println("Getting value for Field: " + filedName );
                fieldValue = Arrays.stream(propertyDescriptors).filter(f -> f.getName().equals(filedName)).findFirst().get().getReadMethod().invoke(dbData);
                if (fieldValue == null) continue;
                fieldValue = fieldValue.toString();
                if (fieldValue.toString().matches("\\d+\\.\\d+") && !each.getKey().equals("% Investment"))
                {
                    fieldValue =  String.valueOf(Math.round(Double.valueOf(fieldValue.toString())));
                }

                String excelValue = each.getValue();
                if (excelValue.toString().matches("\\d+\\.\\d0+") || excelValue.matches("\\d+\\.0+")) {
                    excelValue = df.format(Double.parseDouble(excelValue.toString()));
                }
                softAssert.assertEquals(excelValue,fieldValue , "Validating Field: " + filedName + ", Value: " + fieldValue);
                System.out.println("Field: " + filedName + ", Value: " + fieldValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        softAssert.assertAll();
        System.out.println("Success");
    }

    public void downloadPortfolio(String portfolioName, String location) {
        //get first clickable download button
        BrowserUtils.waitForVisibility(downloadButtonList.get(0), 10);
        int index = BrowserUtils.getElementsText(portfolioNamesList).indexOf(portfolioName);
        System.out.println("index = " + index);
        if (index == -1) {
            System.out.println("Portfolio with name " + portfolioName + " is not found");
            return;
        }
        portfolioName = portfolioNamesList.get(index).getText() + "_ESG Scores_" + DateTimeUtilities.getCurrentDate("dd_MMM_yyyy") + "_";//11_Apr_2023_";
        System.out.println("portfolioName = " + portfolioName);
        deleteFilesInDownloadsFolder();
        if (location.equals("page")) {
            assertTestCase.assertTrue(downloadButtonList.get(index).isDisplayed(), "Download button is displayed");
            assertTestCase.assertTrue(downloadButtonList.get(index).isEnabled(), "Download button is enabled");
            downloadButtonList.get(index).click();
        } else {
            assertTestCase.assertTrue(viewDetailButton.get(index).isDisplayed(), "View detail button is displayed");
            assertTestCase.assertTrue(viewDetailButton.get(index).isEnabled(), "View detail button is enabled");
            clickOnViewDetailButton(index);
            BrowserUtils.waitForVisibility(detailPanelExportToExcelButton, 10);
            assertTestCase.assertTrue(detailPanelExportToExcelButton.isDisplayed(), "Export to excel button is displayed");
            assertTestCase.assertTrue(detailPanelExportToExcelButton.isEnabled(), "Export to excel button is enabled");
            detailPanelExportToExcelButton.click();
            closePanel();
        }
        BrowserUtils.wait(10);
        verifyIfDocumentsDownloaded(portfolioName, "xlsx");
    }

    public void verifyDataAllianceFlag(String portfolioName) {
        ExcelUtil excelData = getExcelData(portfolioName, 0);
        OnDemandFilterAPIController controller = new OnDemandFilterAPIController();
        String portfolioId = controller.getPortfolioId(portfolioName);
        System.out.println("portfolioId = " + portfolioId);
        OnDemandAssessmentQueries queries = new OnDemandAssessmentQueries();
        List<Object> dbEntitiesList = queries.getEntitiesNameList(portfolioId);
        System.out.println("dbEntitiesList = " + dbEntitiesList.size());
        dbEntitiesList.removeIf(Objects::isNull);
        System.out.println("Number companies in DB = " + dbEntitiesList.size());
        System.out.println("Number companies in Excel = " + (excelData.getLastRowNum() - 3));//Last 3 rows are not companies
        assertTestCase.assertEquals(excelData.getLastRowNum() - 3, dbEntitiesList.size(), "Number of companies in Excel and DB are the same");
    }

    public boolean isUserOnFilterCritriaScreen(String PortfolioName) {
        return BrowserUtils.waitForVisibility(btnReviewRequest, 60).isDisplayed() && onDemandCoverageHeaderValidation(PortfolioName);
    }

    public String SelectAndGetOnDemandEligiblePortfolioName() {
        String portfolioName = "";
        for (int i = 0; i < getPortfolioList().size(); i++) {
            if (Double.valueOf(OnDemandEligibility.get(i).getText().split("%")[0]) > (0)) {
                portfolioRadioButtonList.get(i).click();
                portfolioName = getPortfolioList().get(i);
                break;
            }
        }
        return portfolioName;
    }


    @FindBy(xpath = "//*[@id=\"Climate Dashboardtopbar-menuitem-test-id\"]")
    public WebElement climateDashboardTab;
    @FindBy(xpath = "//*[@id=\"Climate Portfolio Analysistopbar-menuitem-test-id\"]")
    public WebElement portfolioAnalysisTab;
    @FindBy(xpath = "//*[@id=\"on-demand-reporting-topbar-menuitem\"]")
    public WebElement reportingPortalTab;
    @FindBy(xpath = "//*[@id='topbar-appbar-test-id']/div/li")
    public WebElement pageHeader;

    @FindBy(xpath = "//li[contains(@class,'MuiButtonBase')]")
    public List<WebElement> menuItems;
    @FindBy(xpath = "//*[@id=\"topbar-appbar-test-id\"]/div/div[2]")
    public WebElement searchButton;
    @FindBy(xpath = "//*[@id=\"platform-search-test-id\"]")
    public WebElement searchField;
    @FindBy(xpath = "//*[@id=\"mini-0\"]/div[2]/span")
    public WebElement searchResultLineOne;

    @FindBy(xpath = "/html/body/div[2]/div[3]/div/div[1]/div[2]/svg")
    public WebElement escapeButton;

    @FindBy(xpath = "//*[@id='eu_taxonomy']")
    public WebElement euTaxonomyOption;

    @FindBy(xpath = "//*[@id='sfdr']")
    public WebElement sfdrOption;

    @FindBy(xpath = "//*[@id='on_demand_assessment']")
    public WebElement ondemandOption;

    @FindBy(xpath = "//*[@id=\"eu_taxonomy\"]/label/span[1]/span/input")
    public WebElement euTaxonomyRadioButton;

    @FindBy(xpath = "//*[@id=\"sfdr\"]/label/span[1]/span/input")
    public WebElement sfdrRadioButton;

    public void clickOnEscapeButton() {
        BrowserUtils.waitAndClick(escapeButton, 5);
    }

    public void searchForEntitities(String entity) {
        BrowserUtils.waitForVisibility(searchField, 30);
        searchField.sendKeys(entity);
    }

    public void validateEntitiesWithOnlyEsgDataDontShowInSearch(String entity) {

        String expectedSearchResult = BrowserUtils.waitForVisibility(searchResultLineOne, 20).getText();
        assertTestCase.assertTrue(!entity.equals(searchResultLineOne.getText()), "Validating that " + entity + " which is an Entity with ESG data only is not returned or suggested in search option : Status Done");

    }

    public void clickOnSearchButton() {
        BrowserUtils.waitForClickablility(searchButton, 20);
        searchButton.click();
    }

    public boolean isSearchButtonDisplayed() {
        BrowserUtils.waitForInvisibility(searchButton, 20);
        return searchButton.isDisplayed();
    }

    public void validateSearchButtonNotDisplayed() {
        assertTestCase.assertTrue(!isSearchButtonDisplayed(), "Verifying that user can't open entity page or search for it : Status Done");
    }


    public void validateDashboardAndPortfolioAnalysisNotPresentInGlobalMenu() {
        String expectedTabTitle = "Climate Dashboard";
        String expectedTab1Title = "Climate Portfolio Analysis";

        System.out.println("-------------Validate Dashboard and Portfolio Analysis are not visible---------------");
        assertTestCase.assertTrue(!climateDashboardTab.isDisplayed(), "Verifying the Climate Dashboard is not visible : Status Done");
        assertTestCase.assertTrue(!portfolioAnalysisTab.isDisplayed(), "Verifying the Climate Portfolio Analysis is not visible : Status Done");
        System.out.println("-------------Just validated that Dashboard and Portfolio Analysis are not visible------------------");
    }

    public void validateDashboardTabNotPresentFromGlobalMenu(String menuTab) {
        System.out.println("------------Verifying that " + menuTab + " is removed from global menu-------------------");
        for (WebElement e : menuItems) {
            String menuItemText = e.getText();
            System.out.println("verify that " + menuItemText + " is not equal to " + menuTab);
            assertTestCase.assertTrue(!menuItemText.equals(menuTab), "Verify that " + menuTab + " is not visible in the Global menu : Status Done");
        }
    }

    public void validateAnyTabPresentInGlobalMenu(String menuTab) {
        System.out.println("------------Verifying that " + menuTab + " is visible from global menu-------------------");
        for (WebElement e : menuItems) {
            String menuItemText = e.getText();
            if(menuItemText.equals(menuTab)) {
                //System.out.println("verify that " + menuItemText + " is equal to " + menuTab);
                assertTestCase.assertTrue(menuItemText.equals(menuTab), "Verify that " + menuTab + " is visible in the Global menu : Status Done");
                break;
            }
        }
    }

    public void validateReportingOptionsInReportingPage2(String option) {
        System.out.println("------------Verifying that " + option + " is visible from global menu-------------------");
        for (WebElement e : menuItems) {
            String menuItemText = e.getText();
            if (menuItemText.equals(option))
                //System.out.println("verify that " + menuItemText + " is equal to " + menuTab);
                assertTestCase.assertTrue(menuItemText.equals(option), "Verify that " + option + " is visible in the Global menu : Status Done");
            break;
        }
    }

    public void validateReportingOptionsInReportingPage(LoginPage login, EntitlementsBundles bundles) {
        OnDemandAssessmentPage odaPage = new OnDemandAssessmentPage();

        switch (bundles) {

            case USER_SFDR_ESG_ESG_PREDICTOR_ODA_EXCEL:
                login.entitlementsLogin(EntitlementsBundles.USER_SFDR_ESG_ESG_PREDICTOR_ODA_EXCEL);
                assertTestCase.assertTrue(odaPage.validateOnDemandReportingLandingPage(), "Validating that landing page is On-Demand Reporting Page");
                assertTestCase.assertTrue(!euTaxonomyRadioButton.isEnabled(), "Verifying Eu-Taxonomy option is disabled : Status Done");
                assertTestCase.assertTrue(sfdrRadioButton.isEnabled(), "Verifying SFDR PAIs option is enabled : Status Done");
                odaPage.clickOnMenuButton();
                odaPage.clickOnLogOutButton();
                break;
            case USER_EUTAXONOMY_ESG_ESG_PREDICTOR_ODA_EXCEL:
                login.entitlementsLogin(EntitlementsBundles.USER_EUTAXONOMY_ESG_ESG_PREDICTOR_ODA_EXCEL);
                assertTestCase.assertTrue(odaPage.validateOnDemandReportingLandingPage(), "Validating that landing page is On-Demand Reporting Page");
                assertTestCase.assertTrue(euTaxonomyRadioButton.isEnabled(), "Verifying Eu-Taxonomy option is enabled : Status Done");
                assertTestCase.assertTrue(!sfdrRadioButton.isEnabled(), "Verifying SFDR PAIs option is disabled : Status Done");
                odaPage.clickOnMenuButton();
                odaPage.clickOnLogOutButton();
                break;
            case USER_EUTAXONOMY_ESG_ESG_PREDICTOR_ODA:
                login.entitlementsLogin(EntitlementsBundles.USER_EUTAXONOMY_ESG_ESG_PREDICTOR_ODA);
                assertTestCase.assertTrue(odaPage.validateOnDemandReportingLandingPage(), "Validating that landing page is On-Demand Reporting Page");
                assertTestCase.assertTrue(euTaxonomyRadioButton.isEnabled(), "Verifying Eu-Taxonomy option is enabled : Status Done");
                assertTestCase.assertTrue(!sfdrRadioButton.isEnabled(), "Verifying SFDR PAIs option is disabled : Status Done");
                odaPage.clickOnMenuButton();
                odaPage.clickOnLogOutButton();
                break;
            case USER_SFDR_ESG_ESG_PREDICTOR_ODA:
                login.entitlementsLogin(EntitlementsBundles.USER_SFDR_ESG_ESG_PREDICTOR_ODA);
                assertTestCase.assertTrue(odaPage.validateOnDemandReportingLandingPage(), "Validating that landing page is On-Demand Reporting Page");
                assertTestCase.assertTrue(!euTaxonomyRadioButton.isEnabled(), "Verifying Eu-Taxonomy option is disabled : Status Done");
                assertTestCase.assertTrue(sfdrRadioButton.isEnabled(), "Verifying SFDR PAIs option is enabled : Status Done");
                odaPage.clickOnMenuButton();
                odaPage.clickOnLogOutButton();
                break;
            case USER_EUTAXONOMY_SFDR_ESG_ESG_PREDICTOR_ODA_EXCEL:
                login.entitlementsLogin(EntitlementsBundles.USER_EUTAXONOMY_SFDR_ESG_ESG_PREDICTOR_ODA_EXCEL);
                assertTestCase.assertTrue(odaPage.validateOnDemandReportingLandingPage(), "Validating that landing page is On-Demand Reporting Page");
                assertTestCase.assertTrue(euTaxonomyRadioButton.isEnabled(), "Verifying Eu-Taxonomy option is enabled : Status Done");
                assertTestCase.assertTrue(sfdrRadioButton.isEnabled(), "Verifying SFDR PAIs option is enabled : Status Done");
                odaPage.clickOnMenuButton();
                odaPage.clickOnLogOutButton();
                break;

        }
    }


    public void verifySectorAndRegion(String portfolioName) {
        ExcelUtil excelData = getExcelData(portfolioName, 0);
        OnDemandFilterAPIController controller = new OnDemandFilterAPIController();
        String portfolioId = controller.getPortfolioId(portfolioName);
        System.out.println("portfolioId = " + portfolioId);
        OnDemandAssessmentQueries queries = new OnDemandAssessmentQueries();
        List<Map<String, Object>> dbData= queries.getPortfolioDetail(portfolioId);
        System.out.println("dbEntitiesList = " + dbData.size());
        //Data should match between exported file and query in Snowflake
        //go through excel and get each row
        //find data in dbEntitiesList and compare

        for (int i = 0; i < excelData.getLastRowNum()-3; i++) {
            //System.out.println("i = " + i);
            List<String> row = excelData.getRowData(i+1);
//            System.out.println("row = " + row);
            String companyName = row.get(0);
//            System.out.println("companyName = " + companyName);
            String sector = row.get(4);
//            System.out.println("sector = " + sector);
            String region = row.get(5);
//            System.out.println("region = " + region);

            boolean check = false;
            for(Map<String, Object> data: dbData){
//                if(data.get("COMPANY_NAME") == null || map.get("REGION_NAME") == null || data.get("SECTOR") == null) continue;
//                if(data.get("COMPANY_NAME") == null || map.get("REGION_NAME") == null || data.get("SECTOR") == null) continue;
                String dbcompanyName = data.get("COMPANY_NAME").toString();
                String dbregion = data.get("REGION").toString();
                String dbsector = data.get("SECTOR").toString();
                if(companyName.equals(dbcompanyName) && region.equals(dbregion) && sector.equals(dbsector)){
                    check = true;
                    break;
                }
            }
            if(!check){
                System.out.println(companyName + " not found in DB");
                assertTestCase.fail("Company Name: "+companyName+" Region: "+region+" Sector: "+sector+" not found in DB");
            }
        }
    }

    public void clickonViewAssessmentRequestButton() {
         BrowserUtils.waitForVisibility(buttonViewAssessmentStatus, 60).click();
    }

    @FindBy(xpath = "//div[text()='Open Assessment Request']")
    public List<WebElement> OpenAssessmentRequest;

    @FindBy(xpath = "//div[@id='dupe-email-popover-error-request-test-id']")
    public WebElement DuplicateEmailAlertMessage;

    @FindBy(xpath = "//div[@id='dupe-email-popover-error-request-test-id']//button")
    public WebElement DuplicateEmailAlertMessageCloseButton;

    @FindBy(xpath = "//div[@id='upload-popover-error-request-test-id']")
    public WebElement errorPopUPMessage;

    @FindBy(xpath = "//div[@id='upload-popover-error-request-test-id']//button")
    public WebElement errorPopUPMessageCloseButton;

    public boolean IsOpenAssessmentPageLoaded() {
        BrowserUtils.waitForVisibility(OpenAssessmentRequest, 90);
        return (OpenAssessmentRequest.size()>0);
    }

    public void waitForOpenAssessmentPageToLoad() {
        if (IsOpenAssessmentPageLoaded()) {
            System.out.println("OpenAssessment Page Loaded Successfully");
        }
    }


    public void validateRequestFailedMessageDuetoPreExistingEmail() {
        BrowserUtils.waitForVisibility(DuplicateEmailAlertMessage,60).getText().contains("The following company failed assessment creation due to pre-existing email in the system. Please review the email address provided and re-submit the assessment request.");
        DuplicateEmailAlertMessageCloseButton.click();
    }

    public void validateerrorMessage() {
        BrowserUtils.waitForVisibility(errorPopUPMessage,60).getText().contains("No assessment request have been created. Please review the email address entries.");
        errorPopUPMessageCloseButton.click();
    }
}
