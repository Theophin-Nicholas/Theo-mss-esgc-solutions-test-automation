package com.esgc.Common.UI.Pages;


import com.esgc.Utilities.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommonPage extends UploadPortfolio {

    //=============== Summary Header

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

    @FindBy(xpath = "//div[.='Select Action']")
    public WebElement divSelectAction;

    @FindBy(xpath = "//div[.='Select Action']/following-sibling::div[1]")
    public WebElement divService;

    @FindBy(xpath = "//div[.='Select Action']/..//span[2]")
    public List<WebElement> reportingNamesList;

    @FindBy(xpath = "//div[.='Select Action']/..//input")
    public List<WebElement> reportingRadioButtonList;

    @FindBy(xpath = "//div[.='Portfolio']/../following-sibling::div//label/span[2]")
    public List<WebElement> portfolioNamesList;

    @FindBy(xpath = "//div[@id='prop-search']//*[@heap_id='portfolio-selection']/div[1]")
    public List<WebElement> dashboardPortfolioNamesList;

    @FindBy(xpath = "//*[@heap_id='portfolio-selection']/div[1]/span")
    public List<WebElement> portfolioSelectionModalPortfolioNamesList;

    @FindBy(xpath = "//div[contains(text(),'Select Portfolio')]")
    public WebElement divSelectPortfolio;

    @FindBy(xpath = "//div[contains(text(),'Select Portfolio')]/following-sibling::div/div/p[text()='No portfolio available.']")
    public WebElement noPortfolioAvailable;

    // @FindBy(xpath = "//li[@heap_menu='ESG Reporting Portal']")
    // public WebElement OnDemandMenuItem;

    @FindBy(xpath = "//div[contains(text(),'Select Portfolio')]/../div[2]/following-sibling::div/div[1]//input")
    public List<WebElement> portfolioRadioButtonList;

    @FindBy(id = "link-upload")
    public WebElement uploadAnotherPortfolioLink;

    @FindBy(xpath = "//div[contains(text(),'Select Portfolio')]/../div[2]/div")
    public List<WebElement> PortfolioTableHeaders;

    @FindBy(xpath = "//div[contains(text(),'Select Portfolio')]/../div[2]/following-sibling::div//button[2]")
    public List<WebElement> ExportButton;



    /*public WebElement OnDemandMenuItem(){
        WebElement e = null;
        if (Environment.environment.equalsIgnoreCase("qa")){
            e = BrowserUtils.waitForVisibility(Driver.getDriver().findElement(By.xpath("//li[@heap_menu='ESG Reporting Portal']")));

        }else{
            e = BrowserUtils.waitForVisibility(Driver.getDriver().findElement(By.xpath("//li[@heap_menu='On-Demand Reporting']")));
        }
        return e;
    }*/

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
//        try {
            if (menuList.size()<2) {
                clickMenu();
                BrowserUtils.wait(2);
                portfolioSettings.click();
                System.out.println("Portfolio settings clicked");
            }
            BrowserUtils.waitForVisibility(portfolioManagementPortfolioNames, 10);
            if(portfolioManagementPortfolioNames.size()==0){
                System.out.println("No portfolio available");
                return;
            }
            for (WebElement portfolio : portfolioManagementPortfolioNames) {
                if (portfolio.getText().equalsIgnoreCase(portfolioName)) {
                    BrowserUtils.waitForClickablility(portfolio, 10).click();
                    break;
                }
            }
            System.out.println("Portfolio selected");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


    //This method is to select the reporting option on reporting landing page
    public void navigateToReportingService(String reportingService) {

        BrowserUtils.wait(5);
        navigateToPageFromMenu("reportingservice", "ESG Reporting Portal");
        if (reportingService.contains("EU")) {
            System.out.println("EU Taxonomy option is being selected");
            clickOnEUTaxonomyOption();
        }
        if (reportingService.contains("SFDR")) {
            System.out.println("SFDR PAIs option is being selected");
            clickOnSFDRPAIsOption();
        }
        if (reportingService.contains("On-Demand")) {
            clickOnDemandOption();
        }
        waitForPortfolioTableToLoad();
    }

    public void clickOnSFDRPAIsOption() {
        BrowserUtils.waitForClickability(SFDRPAIs).click();
    }

    public void clickOnDemandOption() {
        BrowserUtils.wait(5);
        BrowserUtils.waitForVisibility(OnDemandAssessment, 20);
        BrowserUtils.clickWithJS(OnDemandAssessment);
        System.out.println("On Demand Assessment option is selected");
    }

    public void clickOnEUTaxonomyOption() {
        BrowserUtils.waitForClickability(EUTaxonomy).click();
    }

    public String getPageTitleText() {

        return pageTitle.getText();
    }

    public List<String> getReportingList() {
        System.out.println("reportingNamesList = " + BrowserUtils.getElementsText(reportingNamesList));
        return BrowserUtils.getElementsText(reportingNamesList);
    }

    public List<String> getPortfolioList() {
        BrowserUtils.waitForVisibility(portfolioNamesList, 10);
        return BrowserUtils.getElementsText(portfolioNamesList);
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
        for (int i = 0; i < reportingNamesList.size(); i++) {
            if (reportingNamesList.get(i).getText().toLowerCase().contains(name.toLowerCase())) {
                reportingRadioButtonList.get(i).click();
                break;
            }
        }
        BrowserUtils.waitForVisibility(portfolioNamesList, 10);
    }

    public void ValidateReportingOptions(List<String> reportingOptions) {
        BrowserUtils.waitForVisibility(reportingNamesList.get(0), 10);
        assertTestCase.assertTrue(getReportingList().containsAll(reportingOptions), "Validate if all three reporting options are available");
    }

    // this method will be used to click on 1 checkbox that is displayed and enabled
    public List<WebElement> selectEnabledPortfolioOption() {
        List<WebElement> enabledCheckboxesElements = new ArrayList<WebElement>();
        for (int i = 0; i < portfolioRadioButtonList.size(); i++) {

            if (portfolioRadioButtonList.get(i).isEnabled()) {
                enabledCheckboxesElements.add(portfolioRadioButtonList.get(i));
                break;
            }
        }
        return enabledCheckboxesElements;
    }

    public List<String> selectEnabledPortfolioOptionText() {
        List<String> enabledCheckboxesText = new ArrayList<String>();
        for (int i = 0; i < portfolioRadioButtonList.size(); i++) {

            if (portfolioRadioButtonList.get(i).isEnabled()) {
                enabledCheckboxesText.add(selectEnabledPortfolioOption().get(i).getText());
                break;
            }
        }
        return enabledCheckboxesText;
    }

    public int selectPortfolioOptionByName(String name) {
        if (IsPortfolioTableLoaded()) {
            int index = getPortfolioList().indexOf(name.trim());
            if(index == -1){
                System.out.println("Portfolio not found");
                return -1;
            }
            System.out.println("Index = " + index);
            if(portfolioRadioButtonList.get(index).isEnabled()){
                portfolioRadioButtonList.get(index).click();
                return index;
            } else {
                return -1;
            }

        } else {
            return -1;
        }
    }

    public boolean IsPortfolioTableLoaded() {
        return (BrowserUtils.waitForVisibility(divSelectPortfolio, 80).isDisplayed() && getAvailablePortfolioCountt() > 0);
    }

    public void waitForPortfolioTableToLoad() {
        if (IsPortfolioTableLoaded()) {
            System.out.println("Portfolio Table Successfully loaded ");
        }
    }

    public int deSelectPortfolioOptionByName(String name) {
        return selectPortfolioOptionByName(name);
    }

    public List<String> getSelectedPortfolioOptions() {
        List<String> selectedPortfolioOptions = new ArrayList<>();
        for (int i = 0; i < portfolioRadioButtonList.size(); i++) {
            if (portfolioRadioButtonList.get(i).isSelected()) {
                selectedPortfolioOptions.add(portfolioNamesList.get(i).getText());
            }
        }
        //System.out.println("selectedPortfolioOptions = " + selectedPortfolioOptions);
        return selectedPortfolioOptions;
    }

    //select all portfolio options
    public void selectAllPortfolioOptions() {
        //select all buttons if not selected
        int count = 0;
        while (getSelectedPortfolioOptions().size() < 4 && count < portfolioNamesList.size()) {
            if(!portfolioRadioButtonList.get(count).isSelected())
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
        System.out.println("Verifying portfolio: " + portfolioName);
        //System.out.println(getPortfolioList());
        return getPortfolioList().contains(portfolioName);
    }

    public boolean isPortfolioSelected(String portfolioName) {
        return portfolioRadioButtonList.get(getPortfolioList().indexOf(portfolioName)).isSelected();
    }

    public boolean isPortfolioSelectionEnabled(String portfolioName) {
        return portfolioRadioButtonList.get(getPortfolioList().indexOf(portfolioName)).isEnabled();
    }

    public boolean isSelectActionHeadingAvailable() {
        return divSelectAction.isDisplayed() && divSelectAction.getText().equals("Select Action");
    }

    public boolean isServiceSubHeadingAvailable() {
        return divService.isDisplayed() && divService.getText().equals("Service");
    }

    public boolean isPortfolioAvailableInList(String portfolioName) {
        return getPortfolioList().contains(portfolioName);
    }

    public int getAvailablePortfolioCountt() {
        return portfolioRadioButtonList.size();
    }

    public List<String> getPortfolioTableHeadersList() {
        BrowserUtils.waitForVisibility(PortfolioTableHeaders.get(0), 60);
        List<String> headerList = new ArrayList<>();
        for (WebElement e : PortfolioTableHeaders) {
            if (!e.getText().equals(""))
                headerList.add(e.getText());
        }
        return headerList;
    }

    public void validatePortfolioTableHeaders() {
        List<String> expectedHeaders = Arrays.asList(new String[]{"Portfolio", "Last Uploaded", "Coverage", "On-Demand Assessment Eligible"});
        assertTestCase.assertTrue(expectedHeaders.containsAll(getPortfolioTableHeadersList()), "Validating Portfolio Table Headers");

    }

    public void validatePortfolioTableHeadersDesignProperties() {
        BrowserUtils.waitForVisibility(PortfolioTableHeaders.get(0), 10);
        for (WebElement e : PortfolioTableHeaders) {
            if (!e.getText().equals("")) {
                assertTestCase.assertTrue(Color.fromString(e.getCssValue("color")).asHex().equals("#999999"));
                assertTestCase.assertTrue(e.getCssValue("font-size").equals("10px"));
            }

        }
    }

    public void deleteFilesInDownloadsFolder() {
        File dir = new File(BrowserUtils.downloadPath());
        File[] dir_contents = dir.listFiles();
        if (dir_contents == null) {
            System.out.println("No files in the directory");
            return;
        }
        for (File file : dir_contents) {
            file.delete();
        }
        System.out.println("All files in the directory are deleted");
    }

    public ExcelUtil getExcelData(String excelName, int sheetIndex) {
        System.out.println("excelName = " + excelName);
        File dir = new File(BrowserUtils.downloadPath());
        File[] dir_contents = dir.listFiles();
        assert dir_contents != null;
        File excelFile = Arrays.stream(dir_contents).filter(e -> (e.getName().contains(excelName))).findAny().get();
        //System.out.println("excelFile = " + excelFile.getAbsolutePath());
        if (!excelFile.exists()) {
            System.out.println(excelName + " file does not exist");
            return null;
        }
        //System.out.println(excelName + " file found");
        //System.out.println("excelFile = " + excelFile.getAbsolutePath());
        ExcelUtil excelUtil = new ExcelUtil(excelFile.getAbsolutePath(), sheetIndex);
        return excelUtil;
    }

    public ExcelUtil getExcelData(String excelName, String sheetName) {
        File dir = new File(BrowserUtils.downloadPath());
        File[] dir_contents = dir.listFiles();
        assert dir_contents != null;
        File excelFile = Arrays.stream(dir_contents).filter(e -> (e.getName().contains(excelName))).findAny().get();
        //System.out.println("excelFile = " + excelFile.getAbsolutePath());
        if (!excelFile.exists()) {
            System.out.println(excelName + " file does not exist");
            return null;
        }
        //System.out.println(excelName + " file found");
        //System.out.println("excelFile = " + excelFile.getAbsolutePath());
        ExcelUtil excelUtil = new ExcelUtil(excelFile.getAbsolutePath(), sheetName);
        return excelUtil;
    }

    public boolean verifyPortfolioEnabled(String portfolioName) {
        return portfolioRadioButtonList.get(getPortfolioList().indexOf(portfolioName)).isEnabled();
    }

    public boolean verifyPortfolioUploadLink() {
        BrowserUtils.waitForVisibility(uploadAnotherPortfolioLink, 10);
        return uploadAnotherPortfolioLink.isDisplayed();
    }

    public String getFirstEnabledPortfolioName() {
        for (int i = 0; i < portfolioRadioButtonList.size(); i++) {
            if (portfolioRadioButtonList.get(i).isEnabled())
                return getPortfolioList().get(i);
        }
        return null;
    }
}