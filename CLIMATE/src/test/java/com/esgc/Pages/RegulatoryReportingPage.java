package com.esgc.Pages;

import com.esgc.Utilities.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RegulatoryReportingPage extends UploadPage {
    //LOCATORS
    @FindBy(xpath = "//li[@heap_menu='Reporting Service']")
    public WebElement pageTitle;

    @FindBy(xpath = "//div[.='Select Reporting']")
    public WebElement reportingTitle;

    @FindBy(xpath = "//div[.='Reporting']")
    public WebElement reportingSubtitle;

    @FindBy(xpath = "//div[.='Reporting']/following-sibling::div//span[2]")
    public List<WebElement> reportingNamesList;

    @FindBy(xpath = "//div[.='Reporting']/following-sibling::div//input")
    public List<WebElement> reportingRadioButtonList;

    @FindBy(xpath = "//div[.='Select Portfolios']/following-sibling::div//span[2]")
    public List<WebElement> portfolioNamesList;

    @FindBy(xpath = "//div[.='Select Portfolios']/following-sibling::div//label//input")
    public List<WebElement> portfolioRadioButtonList;

    @FindBy(xpath = "//div[.='Reporting for']/../following-sibling::div/div[2]/span")
    public List<WebElement> lastUploadedList;

    @FindBy(xpath = "//div[.='Reporting for']/../following-sibling::div/div[3]/span")
    public List<WebElement> coverageList;

    @FindBy(xpath = "//div[.='Reporting for']/../following-sibling::div/div[4]")
    public List<WebElement> reportingForList;

    @FindBy(xpath = "//ul[@role='listbox']//li")
    public List<WebElement> reportingForDropdownOptionsList;

    @FindBy(id = "link-upload")
    public WebElement uploadAnotherPortfolioLink;

    @FindBy(id = "button-download-test-id-1")
    public WebElement createReportsButton;

    @FindBy(id = "interim")
    public WebElement interimReportsOption;

    @FindBy(id = "annual")
    public WebElement annualReportsOption;

    @FindBy(id = "latest")
    public WebElement useLatestDataOption;

    @FindBy(xpath = "//*[@id='interim']//input")
    public WebElement interimReportsToggleButton;

    @FindBy(xpath = "//*[@id='annual']//input")
    public WebElement annualReportsToggleButton;

    @FindBy(xpath = "//*[@id='latest']//input")
    public WebElement useLatestDataToggleButton;

    //Regulatory Reorting Status Page Elements
    @FindBy(xpath = "//h3")
    public WebElement rrStatusPage_ReportGeneratingMessage;

    @FindBy(xpath = "//button/preceding-sibling::div//div")
    public List<WebElement> rrStatusPage_PortfoliosList;

    @FindBy(xpath = "//button[.='Download']")
    public WebElement rrStatusPage_DownloadButton;


    //METHODS
    public List<String> getReportingList() {
        return BrowserUtils.getElementsText(reportingNamesList);
    }

    public List<String> getPortfolioList() {
        return BrowserUtils.getElementsText(portfolioNamesList);
    }

    //return number of selected reporting options
    public int numberOfSelectedReportingOptions() {
        return (int) reportingRadioButtonList.stream().filter(WebElement::isSelected).count();
    }

    //return seleted reporting option's name
    public String getSelectedReportingOption() {
        for (int i = 0; i < reportingRadioButtonList.size(); i++) {
            if (reportingRadioButtonList.get(i).isSelected()) {
                return reportingNamesList.get(i).getText();
            }

        }
        System.out.println("No reporting option is selected");
        return "";
    }

    //select reporting option by index enter 1 for 1st option
    public void selectReportingOptionByIndex(int index) {
        reportingRadioButtonList.get(index - 1).click();
    }

    //select reporting option by name
    public void selectReportingOptionByName(String name) {
        reportingRadioButtonList.get(getReportingList().indexOf(name)).click();
    }

    //select portfolio option by index enter 1 for 1st option
    public void selectPortfolioOptionByIndex(int index) {
        if (portfolioRadioButtonList.get(index - 1).isSelected()) {
            System.out.println("Portfolio option is already selected");
        } else if (numberOfSelectedPortfolioOptions() == 4) {
            System.out.println("You can select up to 4 portfolios. De-select one of the portfolios first");
        } else {
            System.out.println("Selecting portfolio option by index: " + index);
            portfolioRadioButtonList.get(index - 1).click();
        }
    }

    //select portfolio option by name
    public void selectPortfolioOptionByName(String name) {
        portfolioRadioButtonList.get(getPortfolioList().indexOf(name)).click();
    }

    //select all portfolio options
    public void selectAllPortfolioOptions() {
        //select all buttons if not selected
        portfolioRadioButtonList.stream().filter(button -> !button.isSelected()).forEach(WebElement::click);
    }

    //verify selcted reporting option by name
    public boolean isSelectedReportingOptionByName(String name) {
        return reportingRadioButtonList.get(getReportingList().indexOf(name)).isSelected();
    }

    public int numberOfSelectedPortfolioOptions() {
        return (int) portfolioRadioButtonList.stream().filter(WebElement::isSelected).count();
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

    //select interim reports
    public void selectInterimReports() {
        if (!isInterimReportsSelected()) {
            interimReportsOption.click();
        }
    }

    //select annual reports
    public void selectAnnualReports() {
        if (!isAnnualReportsSelected()) {
            annualReportsOption.click();
        }
    }

    //select use latest data
    public void selectUseLatestData() {
        if (!isUseLatestDataSelected()) {
            useLatestDataOption.click();
        }
    }

    //deselect interim reports
    public void deselectInterimReports() {
        if (isInterimReportsSelected()) {
            interimReportsOption.click();
        }
    }

    //deselect annual reports
    public void deselectAnnualReports() {
        if (isAnnualReportsSelected()) {
            annualReportsOption.click();
        }
    }

    //deselect use latest data
    public void deselectUseLatestData() {
        if (isUseLatestDataSelected()) {
            useLatestDataOption.click();
        }
    }

    public boolean isInterimReportsSelected() {
        return interimReportsToggleButton.isSelected();
    }

    public boolean isAnnualReportsSelected() {
        return annualReportsToggleButton.isSelected();
    }

    public boolean isUseLatestDataSelected() {
        return useLatestDataToggleButton.isSelected();
    }

    public boolean isNewTabOpened() {
        BrowserUtils.wait(2);
        String currentTab = BrowserUtils.getCurrentWindowHandle();
        for (String tab : BrowserUtils.getWindowHandles()) {
            if (!tab.equals(currentTab)) {
                BrowserUtils.switchWindowsTo(tab);
                return rrStatusPage_ReportGeneratingMessage.isDisplayed();
            }
        }
        System.out.println("Number of Windows = " + BrowserUtils.getWindowHandles().size());
        return false;
    }

    public List<String> getSelectedPortfolioOptions() {
        List<String> selectedPortfolioOptions = new ArrayList<>();
        for (int i = 0; i < portfolioRadioButtonList.size(); i++) {
            if (portfolioRadioButtonList.get(i).isSelected()) {
                selectedPortfolioOptions.add(portfolioNamesList.get(i).getText());
            }
        }
        return selectedPortfolioOptions;
    }

    public boolean verifyReportsReadyToDownload(List<String> selectedPortfolios) {
        for (int i = 0; i < 10; i++) {
            if (rrStatusPage_PortfoliosList.size() == 0) BrowserUtils.wait(1);
            else break;
        }
        for (String element : selectedPortfolios) {
            if (!isHave(element, rrStatusPage_PortfoliosList)) {
                return false;
            }
        }
        return rrStatusPage_DownloadButton.isDisplayed();
    }

    public boolean isHave(String element, List<WebElement> list) {
        for (WebElement webElement : list) {
            if (webElement.getText().contains(element)) {
                System.out.println("Element is present");
                return true;
            }
        }
        System.out.println("Element is not present");
        return false;
    }

    public boolean verifyIfReportsDownloaded() {
        rrStatusPage_DownloadButton.click();
        BrowserUtils.wait(10);
        File dir = new File(BrowserUtils.downloadPath());
        File[] dir_contents = dir.listFiles();
        return Arrays.asList(dir_contents).stream().filter(e -> (e.getName().startsWith("SFDR")&&e.getName().contains(DateTimeUtilities.getCurrentDate("MM_dd_yyyy")))).findAny().isPresent();
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

    public void selectReportingFor(String portfolioName, String reportingYear) {
        //go through portfolios list
        for (int i = 0; i < portfolioNamesList.size(); i++) {
            //find the portfolio
            if(portfolioNamesList.get(i).getText().equals(portfolioName)){
                //verify if portfolio is selected
                if(!isPortfolioSelected(portfolioName)){
                    selectPortfolioOptionByName(portfolioName);
                }
                //click on reporting for dropdown
                reportingForList.get(i).click();
                //select reporting year
                selectReportingYear(reportingYear);
            }

        }
    }

    public void selectReportingYear(String reportingYear) {
        if(reportingForDropdownOptionsList.size()==0)
            System.out.println("A reporting for option must be clicked before this");
        for (WebElement element : reportingForDropdownOptionsList) {
            if (element.getText().equals(reportingYear)) {
                element.click();
                break;
            }
        }
    }
}
