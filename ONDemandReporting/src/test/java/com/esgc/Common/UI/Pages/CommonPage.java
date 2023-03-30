package com.esgc.Common.UI.Pages;


import com.esgc.Pages.PageBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.ConfigurationReader;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.RobotRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class CommonPage extends UploadPortfolio {

    //=============== Summary Header
    @FindBy(xpath = "//div[@heap_id='portfolio-selection']//div[1]/span")
    public List<WebElement> portfolioNameList;

    @FindBy(id = "upload-button-upload-test-id")
    public WebElement uploadButton;

    @FindBy(id = "upload-button-browsefile-test-id")
    public WebElement browseFileButton;

    @FindBy(xpath = "//*[@class='MuiAlert-message']")
    public WebElement successMessagePopUP;


    @FindBy(xpath = "//span[@class='MuiIconButton-label']//*[name()='svg']")
    public WebElement closeAlert;

    @FindBy(xpath = "//h2/button")
    public WebElement closeUploadModalButton;

    @FindBy(xpath = "//fieldset[@id='eu_taxonomy']")
    public WebElement EUTaxonomy;

    @FindBy(xpath = "//fieldset[@id='sfdr']")
    public WebElement SFDRPAIs;

    @FindBy(xpath = "//fieldset[@id='on_demand_assessment']")
    public WebElement OnDemandAssessment;

    @FindBy(xpath = "//header//li")
    public WebElement pageTitle;


    //TODO after march release, update them to 'Select Action'
    @FindBy(xpath = "//div[.='Select Portfolio']/..//span[2]")
    public List<WebElement> reportingNamesList;

    @FindBy(xpath = "//div[.='Select Portfolio']/..//input")
    public List<WebElement> reportingRadioButtonList;

    @FindBy(xpath = "//div[.='Select Portfolio']/../div[2]/following-sibling::div/div[1]//span[2]")
    public List<WebElement> rrPage_portfolioNamesList;

    @FindBy (xpath = "//div[text()='Select Portfolio']")
    public WebElement divSelectPortfolio ;

    @FindBy(xpath = "//div[.='Select Portfolio']/../div[2]/following-sibling::div/div[1]//input")
    public List<WebElement> portfolioRadioButtonList;

    @FindBy(id = "link-upload")
    public WebElement uploadAnotherPortfolioLink;

    //-----


    // To Delete any portfolio
    public void deletePortfolio(String portfolioName) {
        System.out.println("Deleting portfolio: " + portfolioName);
        selectPortfolioFromPortfolioSelectionModel(portfolioName);
        try {
            BrowserUtils.waitForClickablility(deleteButton, 15).click();
            BrowserUtils.waitForVisibility(confirmPortfolioDeletePopupHeader, 10);
            confirmPortfolioDeleteYesButton.click(); //clicking the Yes button and deleting the portfolio
            BrowserUtils.wait(6);
            pressESCKey();
            System.out.println("Portfolio deleted");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Select portfolio under portfolio selection upload model
    public void selectPortfolioFromPortfolioSelectionModel(String portfolioName) {
        System.out.println("Selecting portfolio: " + portfolioName);
        try {
            if (!menu.isDisplayed()) {
                clickMenu();
                BrowserUtils.wait(2);
                portfolioSettings.click();
            }
            WebElement targetPortfolio = Driver.getDriver().findElement(By.xpath("//div[@id='portfolio-drawer-test-id']//span[@title='" + portfolioName + "']"));
            System.out.println("Portfolio Located");
            BrowserUtils.scrollTo(targetPortfolio);
            BrowserUtils.waitForClickablility(targetPortfolio, 10).click();
            System.out.println("Portfolio selected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//This method is to select the reporting option on reporting landing page
    public void navigateToReportingService(String reportingService) {
        BrowserUtils.wait(3);
        navigateToPageFromMenu("reportingservice","On-Demand Reporting");
        if (reportingService.contains("SFDR")) {
            clickOnEUTaxonomyOption();
        }
        if (reportingService.contains("EU")) {
            clickOnSFDRPAIsOption();
        }
        if (reportingService.contains("On-Demand")) {
            clickOnDemandOption();
        }
    }

    public void clickOnSFDRPAIsOption() {
        BrowserUtils.waitForClickability(SFDRPAIs).click();
    }

    public void clickOnDemandOption() {
        BrowserUtils.wait(5);
        BrowserUtils.waitForVisibility(OnDemandAssessment,20);
        BrowserUtils.clickWithJS(OnDemandAssessment);
    }
    public void clickOnEUTaxonomyOption() {
        BrowserUtils.waitForClickability(EUTaxonomy).click();
    }

    public String getPageTitleText() {

        return pageTitle.getText();
    }

    public List<String> getReportingList() {
        return BrowserUtils.getElementsText(reportingNamesList);
    }

    public List<String> getPortfolioList() {
        return BrowserUtils.getElementsText(rrPage_portfolioNamesList);
    }



    public String getSelectedReportingOption() {
        for (int i = 0; i < reportingRadioButtonList.size(); i++) {
            if (reportingRadioButtonList.get(i).isSelected()) {
                return reportingNamesList.get(i).getText();
            }

        }
        System.out.println("No reporting option is selected");
        return "";
    }

    public void selectReportingOptionByName(String name) {
        //todo: add more flexibility to the method
        reportingRadioButtonList.get(getReportingList().indexOf(name)).click();
    }


    // this method will be used to click on 1 checkbox that is displayed and enabled
    public List<WebElement> selectEnabledPortfolioOption(){
        List<WebElement> enabledCheckboxesElements = new ArrayList<WebElement>();
        for (int i=0; i<portfolioRadioButtonList.size(); i++) {

            if (portfolioRadioButtonList.get(i).isEnabled()){
                enabledCheckboxesElements.add(portfolioRadioButtonList.get(i));
                break;
            }
        }
        return enabledCheckboxesElements;
    }

    public List<String> selectEnabledPortfolioOptionText(){
        List<String> enabledCheckboxesText = new ArrayList<String>();
        for (int i=0; i<portfolioRadioButtonList.size(); i++) {

            if (portfolioRadioButtonList.get(i).isEnabled()){
                enabledCheckboxesText.add(selectEnabledPortfolioOption().get(i).getText());
                break;
            }
        }
        return enabledCheckboxesText;
    }

    public int selectPortfolioOptionByName(String name) {
        if (IsPortfolioTableLoaded()) {
            int index = getPortfolioList().indexOf(name.trim());
            System.out.println("Index = " + index);
            portfolioRadioButtonList.get(index).click();
            return index;
        }else{
            return 0;
        }
    }

    public boolean IsPortfolioTableLoaded(){
       // BrowserUtils.waitFor(10);
        return BrowserUtils.waitForVisibility(divSelectPortfolio,60).isDisplayed();
    }

    public int deSelectPortfolioOptionByName(String name) {
        return selectPortfolioOptionByName(name);
    }

    public List<String> getSelectedPortfolioOptions() {
        List<String> selectedPortfolioOptions = new ArrayList<>();
        for (int i = 0; i < portfolioRadioButtonList.size(); i++) {
            if (portfolioRadioButtonList.get(i).isSelected()) {
                selectedPortfolioOptions.add(rrPage_portfolioNamesList.get(i).getText());
            }
        }
        //System.out.println("selectedPortfolioOptions = " + selectedPortfolioOptions);
        return selectedPortfolioOptions;
    }
    //select all portfolio options
    public void selectAllPortfolioOptions() {
        //select all buttons if not selected
        int count = 0;
        while (getSelectedPortfolioOptions().size() < 4) {
            portfolioRadioButtonList.get(count).click();
            count++;
        }
        //portfolioRadioButtonList.stream().filter(button -> !button.isSelected()).forEach(WebElement::click);
    }

    public boolean uploadPortfolio(String fileName) {
        openPortfolioUploadModal();
        try {
            BrowserUtils.waitForVisibility(uploadButton, 2);
            clickBrowseFile();
            BrowserUtils.wait(2);
            String inputFile = System.getProperty("user.dir") + ConfigurationReader.getProperty(fileName);
            RobotRunner.selectFileToUpload(inputFile);
            BrowserUtils.wait(4);
            clickUploadButton();
            while (!checkifSuccessPopUpIsDisplyed()) {
                BrowserUtils.wait(1);
            }
            closePopUp();
            pressESCKey();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void openPortfolioUploadModal() {
        //if(checkIfUploadingMaskIsDisplayed()) return;
        BrowserUtils.waitForClickablility(uploadAnotherPortfolioLink, 10).click();
    }

    public void deselectAllPortfolioOptions() {
        //select all buttons if not selected
        System.out.println("Deselecting all portfolio options");
        portfolioRadioButtonList.stream().filter(WebElement::isSelected).forEach(WebElement::click);
    }

    public boolean verifyPortfolio(String portfolioName) {
        return getPortfolioList().contains(portfolioName);
    }

    public boolean isPortfolioSelected(String portfolioName) {
        return portfolioRadioButtonList.get(getPortfolioList().indexOf(portfolioName)).isSelected();
    }

    public boolean isPortfolioSelectionEnabled(String portfolioName) {
        return portfolioRadioButtonList.get(getPortfolioList().indexOf(portfolioName)).isEnabled();
    }

}