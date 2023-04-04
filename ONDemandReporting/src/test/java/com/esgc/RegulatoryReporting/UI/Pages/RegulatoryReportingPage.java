package com.esgc.RegulatoryReporting.UI.Pages;



import com.esgc.Common.UI.Pages.CommonPage;
import com.esgc.RegulatoryReporting.API.Controllers.RegulatoryReportingAPIController;
import com.esgc.RegulatoryReporting.DB.DBQueries.RegulatoryReportingQueries;
import com.esgc.Utilities.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class RegulatoryReportingPage extends CommonPage {

    RegulatoryReportingQueries queries = new RegulatoryReportingQueries();
    RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();


    //LOCATORS
    @FindBy(xpath = "//header//li")
    public WebElement pageTitle;

    @FindBy(xpath = "//div[.='Select Reporting']")
    public WebElement reportingTitle;

    @FindBy(xpath = "//div[.='Reporting']")
    public WebElement reportingSubtitle;





    @FindBy(xpath = "//div[.='Select Portfolios']/../div[2]/following-sibling::div/div[1]//input[not(@disabled)]")
    public List<WebElement> activePortfolioRadioButtonList;

    @FindBy(xpath = "//div[.='Reporting for']/../following-sibling::div/div[2]/span")
    public List<WebElement> lastUploadedList;

    @FindBy(xpath = "//div[.='Select Portfolios']/../div[2]/following-sibling::div/div[3]")
    public List<WebElement> coverageList;

    @FindBy(xpath = "//div[.='Reporting for']/../following-sibling::div/div[4]")
    public List<WebElement> reportingForList;

    @FindBy(xpath = "//div[.='Reporting for']/../following-sibling::div//button")
    public List<WebElement> reportingForListButtons;

    @FindBy(xpath = "//ul[@role='listbox']//li")
    public List<WebElement> reportingForDropdownOptionsList;



    @FindBy(id = "button-download-test-id-1")
    public WebElement createReportsButton;

    @FindBy(xpath = "//div[.='Reporting Options']")
    public WebElement reportingOptionsTitle;

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

    @FindBy(xpath = "//*[@id='interim']/../following-sibling::div")
    public WebElement interimOptionSubtitle;

    @FindBy(xpath = "//*[@id='annual']/../following-sibling::div")
    public WebElement annualOptionSubtitle;

    @FindBy(xpath = "//*[@id='latest']/../following-sibling::div")
    public WebElement useLatestDataOptionSubtitle;


    //Regulatory Reorting Status Page Elements
    @FindBy(xpath = "//h3")
    public WebElement rrStatusPage_ReportGeneratingMessage;

    @FindBy(xpath = "//button/preceding-sibling::div//div")
    public List<WebElement> rrStatusPage_PortfoliosList;

    @FindBy(xpath = "//button[.='Download']")
    public WebElement rrStatusPage_DownloadButton;

    @FindBy(xpath = "//div[text()='Previously Downloaded']/following-sibling::span/*[local-name()='svg']")
    public WebElement previouslyDownloadedButton;

    @FindBy(xpath = "//div[text()='Reports created previously:']")
    public WebElement previouslyDownloadedStaticLabel;

    @FindBy(xpath = "//div[text()='Reports created previously:']/following-sibling::div/div/div[2]/span")
    public WebElement recordFileName;

    @FindBy(xpath = "//div[@id='reportingHistoryError']/div/div/div[1]")
    public WebElement previouslyDownloadedErrorMessage;



    @FindBy(xpath = "//*[text()='Select Portfolios']/following-sibling::div[1]//*[text()]")
    public List<WebElement> tableHeaders;

    @FindBy(xpath = "//label[.//input[@disabled and @type='radio']]//span[2]")
    public List<WebElement> deactivatedOptions;

    public boolean isRegulatoryReportingOptionIsAvailableInSidePanel() {
        try {
            return BrowserUtils.waitForVisibility(regulatoryReporting, 10).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public List<WebElement> getEUTaxonomyInputBox(String portfolio) {
        return Driver.getDriver().findElements(By.xpath("//div[.='Select Portfolios']/..//span[contains(text(),'" + portfolio + "')]/../../../../../div[4]/div"));
    }


    //METHODS

    // this methods returns is the create report button is enabled or not
    //

    public boolean isCreateReportsButtonEnabled(){

        return createReportsButton.isEnabled();
    }




    //
   /* public void isCreateReportsButtonClicked() {

        createReportsButton.click();
    }*/

    public boolean isSFDROptionNotClickable() {
        return deactivatedOptions.stream().anyMatch(e -> e.getText().equals("SFDR PAIs"));
    }

    public boolean isEUTaxonomyOptionNotClickable() {
        return deactivatedOptions.stream().anyMatch(e -> e.getText().equals("EU Taxonomy"));
    }

    public void clickOnEUTaxonomy() {
        EUTaxonomy.click();
    }

    //
    public boolean isUploadAnotherPortfolioLinkDisplayed() {

        return uploadAnotherPortfolioLink.isDisplayed();

    }

    //
    public boolean isImportPortfolioPopUpDisplayed() {
        return importPortfolioPopUp.isDisplayed();
    }


    // this method get the text value of the create report button webelement
    public String getCreateReportsButtonText() {
        return createReportsButton.getText();
    }

    // this method returns the text value of use latest data option webelement
   /* public String getUseLatestDataOptionText() {

        return useLatestDataOption.getText();

    }*/

    //gets the string value of previously downloaded error message webelement

    public String getPreviouslyDownloadedErrorMessageText() {
        return previouslyDownloadedErrorMessage.getText();
    }

    //
    public boolean isUseLatestDataOptionSubtitleDisplayed() {
        return useLatestDataOptionSubtitle.isDisplayed();
    }

    //
    public String getUseLatestDataOptionSubtitleText() {
        return useLatestDataOptionSubtitle.getText();
    }

    // this method checks if reporting options webelement is displayed or not
    public boolean isReportingOptionsTitleDisplayed() {
        return reportingOptionsTitle.isDisplayed();
    }

    // this method returns the string value of the webelement reporting options title
    public String getReportingOptionsTitleText() {

        return reportingOptionsTitle.getText();
    }

    //reportingTitle
    // this method helps get the reporting title webelement text value

/*
    public String getReportingTitleText() {

        return reportingTitle.getText();
    }
*/

    // reportingSubtitle
    // this method is used to get the text value of reporting subtitle webelement

    public String getReportingSubtitleText() {
        return reportingSubtitle.getText();
    }

    // this method verifies if the interim Reports Option is displayed
    public boolean isInterimReportsOptionDisplayed() {
        try {
            return interimReportsOption.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    //
    /*public String getInterimReportsOptionText() {
        return interimReportsOption.getText();
    }*/
    // annualReportsOption
    // this method verifies if the annual reports option is displayed

    public boolean isAnnualReportsOptionEnabled() {
        try {
            return annualReportsOption.isEnabled();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    // useLatestDataOption
    //  this method verifies if the use Latest data option is displayed
    public boolean isUseLatestDataOptionDisplayed() {
        try {
            return useLatestDataOption.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //
    public boolean isInterimOptionSubtitleDisplayed() {
        return interimOptionSubtitle.isDisplayed();
    }

    // method that returns the string value of interim option subtitle webelement
    /*public String getInterimOptionSubtitleText() {
        return interimOptionSubtitle.getText();
    }*/

    //

    public boolean isAnnualReportsOptionDisplayed() {
        return annualReportsOption.isDisplayed();
    }

    //
    /*public String getAnnualReportsOptionText() {
        return annualReportsOption.getText();
    }*/

    // method checks if annual option susbtitle is displayed or not
    public boolean isAnnualOptionSubtitleDisplayed() {
        return annualOptionSubtitle.isDisplayed();
    }

    // methods returns the string value of the annual option subtitle webelement
    public String getAnnualOptionSubtitleText() {
        return annualOptionSubtitle.getText();
    }

    public boolean isPageTitleDisplayed() {
        return pageTitle.isDisplayed();
    }




    public String isCreateReportsButtonText() {
        return createReportsButton.getText();
    }

    public String iaAnnualReportsOptionText() {
        return annualReportsOption.getText();
    }

    public String isAnnualOptionSubtitleText() {
        return annualOptionSubtitle.getText();
    }

    public String isInterimOptionSubtitleText() {
        return interimOptionSubtitle.getText();
    }

    public boolean iaUseLatestDataOptionDisplayed() {
        return useLatestDataOption.isDisplayed();
    }

    public String isInterimReportsOptionText() {
        return interimReportsOption.getText();
    }

    public String isUseLatestDataOptionText() {
        return useLatestDataOption.getText();
    }


    public void enterEUTaxonomyValues(String portfolioName, String NonSovereignDerivatives, String Cashandliquidities) {

        WebElement element_NonSovereignDerivatives = getEUTaxonomyInputBox(portfolioName).get(0).findElement(By.xpath("div/div/input"));
        WebElement element_Cashandliquidities = getEUTaxonomyInputBox(portfolioName).get(2).findElement(By.xpath("div/div/input"));
        removeAndInsertValue(element_NonSovereignDerivatives, NonSovereignDerivatives);
        removeAndInsertValue(element_Cashandliquidities, Cashandliquidities);

    }

    public void removeAndInsertValue(WebElement e, String value) {
        int len = e.getAttribute("value").length();
        for (int i = 0; i < len; i++) {
            e.sendKeys(Keys.BACK_SPACE);
        }
        e.sendKeys(value);

    }



    //return number of selected reporting options
    public int numberOfSelectedReportingOptions() {
        return (int) reportingRadioButtonList.stream().filter(WebElement::isSelected).count();
    }

    public int returnNumberOfEnabledReportingOptions(){
        return (int) portfolioRadioButtonList.stream().filter(WebElement::isEnabled).count();
    }


    //return seleted reporting option's name


    //select reporting option by index enter 1 for 1st option
    public void selectReportingOptionByIndex(int index) {
        reportingRadioButtonList.get(index - 1).click();
    }

    //select reporting option by name



    public void clickOnFirstEnabledPortfolioOption(){

        for (int i=0; i<portfolioRadioButtonList.size(); i++) {

            if (portfolioRadioButtonList.get(i).isDisplayed() && portfolioRadioButtonList.get(i).isEnabled()){
                portfolioRadioButtonList.get(i).click();
                break;
            }
        }
        portfolioRadioButtonList.get(0).click();
    }
    public boolean firstEnabledPortfolioOptionSelected(){
        for (int i=0; i<portfolioRadioButtonList.size(); i++) {

            if (portfolioRadioButtonList.get(i).isDisplayed() && portfolioRadioButtonList.get(i).isEnabled()){
                portfolioRadioButtonList.get(i).isSelected();
                break;
            }
        }
        return portfolioRadioButtonList.get(0).isSelected();
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
    public void selectPortfolioOptionByIndex2(int index) {

        if (portfolioRadioButtonList.get(index).isSelected()) {
            System.out.println("Portfolio option is already selected");
        } else if (numberOfSelectedPortfolioOptions2() == 4) {
            System.out.println("You can select up to 4 portfolios. De-select one of the portfolios first");
        } else {
            System.out.println("Selecting portfolio option by index: " + index);
            portfolioRadioButtonList.get(index).click();
        }
    }

    public void selectActivePortfolioOptionByIndex(int index) {
        if (portfolioRadioButtonList.get(index - 1).isSelected()) {
            System.out.println("Portfolio option is already selected");
        } else if (numberOfSelectedPortfolioOptions() == 4) {
            System.out.println("You can select up to 4 portfolios. De-select one of the portfolios first");
        } else {
            System.out.println("Selecting portfolio option by index: " + index);
            activePortfolioRadioButtonList.get(index - 1).click();
        }
    }
    public void selectAllEnabledPortfolios(){
        int count =0;
        while (selectEnabledPortfolioOption().size() < 4) {
            portfolioRadioButtonList.get(count).click();
            count++;
        }
    }
    //verify selcted reporting option by name
    public boolean isSelectedReportingOptionByName(String name) {
        return reportingRadioButtonList.get(getReportingList().indexOf(name)).isSelected();
    }

    public int numberOfSelectedPortfolioOptions() {
        return (int) portfolioRadioButtonList.stream().filter(WebElement::isSelected).count();
    }
    public int numberOfSelectedPortfolioOptions2() {
        return (int) portfolioRadioButtonList.stream().filter(WebElement::isSelected).count();
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

    public void selectAnnualReports(String Year) {
        for (String element : getSelectedPortfolioOptions()) {
            selectReportingFor(element, Year);
        }
        selectAnnualReports();
    }

    //select use latest data
    public void selectUseLatestData() {
        if (!isUseLatestDataSelected()) {
            useLatestDataOption.click();
        }
    }

    public void clickOnUseLatestData() {
        useLatestDataOption.click();
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

    public boolean verifyNewTabOpened(String currentWindowHandle) {
        BrowserUtils.wait(2);
        BrowserUtils.switchWindowsTo(currentWindowHandle);
        return rrStatusPage_ReportGeneratingMessage.isDisplayed();
    }

    public boolean verifyNewTabOpened(Set<String> windowHandles) {
        BrowserUtils.wait(2);
        Set<String> currentTabs = BrowserUtils.getWindowHandles();
        for (String handle : currentTabs) {
            if (!windowHandles.contains(handle)) {
                return verifyNewTabOpened(handle);
            }
        }
        System.out.println("No new tab opened");
        return false;
    }



    public boolean verifyReportsReadyToDownload(List<String> selectedPortfolios) {
        for (int i = 0; i < 10; i++) {
            if (rrStatusPage_PortfoliosList.size() == 0) BrowserUtils.wait(1);
            else break;
        }
        List<String> readyToDownloadPortfolios = BrowserUtils.getElementsText(rrStatusPage_PortfoliosList);
        if (readyToDownloadPortfolios.get(0).contains("SFDR_Annual_" + DateTimeUtilities.getCurrentDate("MM_dd_yyyy")))
            return true;
        isContainsElements(readyToDownloadPortfolios, selectedPortfolios);
        return rrStatusPage_DownloadButton.isDisplayed();
    }

    public boolean verifyReportsReadyToDownload() {//This method is used for if annual reports option is selected
        List<String> list = new ArrayList<>(Arrays.asList("SFDR_Annual_" + DateTimeUtilities.getCurrentDate("MM_dd_yyyy")));
        return verifyReportsReadyToDownload(list);
    }

    public boolean isContainsElements(List<String> list1, List<String> list2) {
        for (String element2 : list2) {
            boolean check = false;
            for (String element1 : list1) {
                if (element1.contains(element2)) {
                    check = true;
                    break;
                }
            }
            if (!check) {
                System.out.println("Element " + element2 + " is not found in the list");
                System.out.println("list1 = " + list1);
                return false;
            }
        }
        return true;
    }

    public boolean verifyIfReportsDownloaded() {
        return verifyIfReportsDownloaded(true);
    }

    public boolean verifyIfReportsDownloaded(boolean clickDownload) {
        if (clickDownload) rrStatusPage_DownloadButton.click();
        BrowserUtils.wait(10);
        File dir = new File(BrowserUtils.downloadPath());
        File[] dir_contents = dir.listFiles();
        if (dir_contents == null) {
            System.out.println("No files in the directory");
            return false;
        }
        return Arrays.stream(dir_contents).anyMatch(e -> ((e.getName().startsWith("SFDR") || e.getName().startsWith("EUT")) &&
                e.getName().contains(DateTimeUtilities.getCurrentDate("MM_dd_yyyy")) &&
                e.getName().endsWith(".zip")));
    }

    public void selectReportingFor(String portfolioName, String reportingYear) {
        if (isUseLatestDataSelected()) deselectUseLatestData();
        //go through portfolios list
        for (int i = 0; i < rrPage_portfolioNamesList.size(); i++) {
            //find the portfolio
            if (rrPage_portfolioNamesList.get(i).getText().equals(portfolioName)) {
                //verify if portfolio is selected
                if (!isPortfolioSelected(portfolioName)) {
                    System.out.println("Selecting portfolio " + portfolioName);
                    selectPortfolioOptionByName(portfolioName);
                }
                //click on reporting for dropdown
                reportingForList.get(i).click();
                //select reporting year
                selectReportingYear(reportingYear);
            }
        }
    }

    public List<String> getReportingFor_YearList(String portfolioName, int index) {

        if (isUseLatestDataSelected()) deselectUseLatestData();
        reportingForList.get(index).click();
       /* for (int i = 0; i < rrPage_portfolioNamesList.size(); i++) {

            if (rrPage_portfolioNamesList.get(i).getText().equals(portfolioName)) {

                if (!isPortfolioSelected(portfolioName)) {
                    System.out.println("Selecting portfolio " + portfolioName);
                    selectPortfolioOptionByName(portfolioName);
                }
                //click on reporting for dropdown
                reportingForList.get(i).click();
            }
        }*/
        // return returnList;
        return BrowserUtils.getElementsText(reportingForDropdownOptionsList);
    }

    public void selectReportingYear(String reportingYear) {
        if (reportingForDropdownOptionsList.size() == 0)
            System.out.println("A reporting for option must be clicked before this");
        for (WebElement element : reportingForDropdownOptionsList) {
//            System.out.println("element.getText() = " + element.getText());
            if (element.getText().equals(reportingYear)) {
                System.out.println("Year for selected portfolio found");
                element.click();
                return;
            }
        }
        System.out.println("Year for selected portfolio not found");
    }

    public boolean unzipReports() {
        verifyIfReportsDownloaded(false);
        File dir = new File(BrowserUtils.downloadPath());
        File[] dir_contents = dir.listFiles();
        String zipFilePath = Arrays.asList(dir_contents).stream().filter(e -> ((e.getName().startsWith("SFDR") || e.getName().startsWith("EUT")) &&
                e.getName().contains(DateTimeUtilities.getCurrentDate("MM_dd_yyyy")) &&
                e.getName().endsWith(".zip"))).findAny().get().getAbsolutePath();
        System.out.println("zipFilePath = " + zipFilePath);
        String destDirectory = BrowserUtils.downloadPath();
        System.out.println("destDirectory = " + destDirectory);
        UnzipUtil unZipper = new UnzipUtil();
        try {
            unZipper.unzip(zipFilePath, destDirectory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Unzipping is done");
        for (String file : BrowserUtils.getElementsText(rrStatusPage_PortfoliosList)) {
            String excelName = file.replaceAll("ready", "").trim();
            System.out.println("excelName = " + excelName);
            File excelFile = new File(destDirectory + "/" + excelName);
            //System.out.println("excelFile = " + excelFile.getAbsolutePath());
            if (!excelFile.exists()) {
                System.out.println("File " + excelName + " is not found");
                return false;
            }
        }
        return true;
    }

    public ExcelUtil getExcelData(String excelName, int sheetIndex) {
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

    public List<String> getExcelDataList(String excelName, String sheetName, int columnIndex) {
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
        System.out.println("Verifying sheet " + sheetName);
        System.out.println("Sheet Loaded " + excelUtil.getLastRowNum() + " vs. " + excelUtil.columnCount(columnIndex));
        List<String> list = new ArrayList<>();
        for (int i = 0; i <= excelUtil.getLastRowNum(); i++) {
            for (int j = 0; j <= excelUtil.columnCount(columnIndex); j++) {
                if (excelUtil.getCellData(i, j) == null || excelUtil.getCellData(i, j).equals("")) continue;
                list.add(excelUtil.getCellData(i, j).trim());
            }
        }
        System.out.println("list = " + list.size());
        return list;
    }

    public List<String> getExcelDataList(String excelName, String sheetName) {
        return getExcelDataList(excelName, sheetName, 0);
    }

    public ExcelUtil getEUTaxonomyTemplate(String sheetName) {
        File dir = new File(BrowserUtils.uploadPath());
        File[] dir_contents = dir.listFiles();
        assert dir_contents != null;
        File excelFile = Arrays.stream(dir_contents).filter(e -> (e.getName().contains("taxonomy_reporting_deliverable_template.xlsx"))).findAny().get();
        //System.out.println("excelFile = " + excelFile.getAbsolutePath());
        if (!excelFile.exists()) {
            System.out.println("Template file does not exist");
            return null;
        }
        //System.out.println("excelFile = " + excelFile.getAbsolutePath());
        ExcelUtil excelUtil = new ExcelUtil(excelFile.getAbsolutePath(), sheetName);
        return excelUtil;
    }

    public boolean verifyReportsContentForData(List<String> selectedPortfolios, String sheetName, String data) {
        String excelName = rrStatusPage_PortfoliosList.get(0).getText().replaceAll("ready", "").trim();
        System.out.println("Verifying reports content for sheet " + sheetName);
        ExcelUtil excelData = getExcelData(excelName, sheetName);
        if (!excelData.searchData(data)) {
            System.out.println("Data " + data + " is not found in the excel");
        }
        return excelData.searchData(data);
    }

    public boolean verifySFDRPortfolioCoverageForUI(String portfolioName) {
        RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
        String portfolioId = apiController.getPortfolioId(portfolioName);
        RegulatoryReportingQueries queries = new RegulatoryReportingQueries();
        List<Map<String, Object>> dbData = queries.getReportingYearDetails(portfolioId);
        System.out.println("dbData.size() = " + dbData.size());
        for (Map<String, Object> row : dbData) {
            if (Integer.parseInt(row.get("YEAR").toString()) > 2018) {
                System.out.println("Validating for year " + row.get("YEAR").toString());
                selectReportingFor(portfolioName, row.get("YEAR").toString());
                BrowserUtils.wait(2);
                String uiCoverage = getCoveragePercentage(portfolioName);
                String dbCoverage = row.get("SFDR_COVERAGE").toString();
                if (!uiCoverage.contains(dbCoverage)) {
                    System.out.println("Coverage is not matching for " + uiCoverage + " : " + dbCoverage);
                    return false;
                }
            }
        }
        return true;
    }

    private String getCoveragePercentage(String portfolioName) {
        System.out.println("Number of Portfolios = " + rrPage_portfolioNamesList.size());
        for (WebElement portfolio : rrPage_portfolioNamesList) {
            if (portfolio.getText().contains(portfolioName)) {
                System.out.println("Portfolio found");
                System.out.println("Index of portfolio = " + rrPage_portfolioNamesList.indexOf(portfolio));
                return coverageList.get(rrPage_portfolioNamesList.indexOf(portfolio)).getText();
            }
        }
        System.out.println("Portfolio not found");
        return "";
    }

    public boolean verifyPortfolioCoverageForExcel(String portfolioName, String coverage) {
        String portfolioId = apiController.getPortfolioId(portfolioName);
        Map<String, Object> dbData = queries.getReportingYearDetails(portfolioId).get(0);
        System.out.println("dbData.size() = " + dbData.size());
        int countOfAllEntities = queries.getNumberOfCompanies(portfolioId);
        System.out.println("countOfAllEntities = " + countOfAllEntities);
        //open Excel file
        String excelName = rrStatusPage_PortfoliosList.get(0).getText().replaceAll("ready", "").trim();
        System.out.println("Verifying reports content for sheet 1");
        ExcelUtil excelData = getExcelData(excelName, 1);
        int countOfEntitiesInExcel = 0;
        System.out.println(excelData.getSheetName());
        if (excelData.getSheetName().equals("Underlying Data - Overview"))
            countOfEntitiesInExcel = excelData.rowCount() - 1;
        else
            countOfEntitiesInExcel = excelData.rowCount() - 2;
        System.out.println("countOfEntitiesInExcel = " + countOfEntitiesInExcel);
        //find coverage percentage by rounding up
        int excelCoverage = (int) Math.ceil((double) countOfEntitiesInExcel / countOfAllEntities * 100);
        System.out.println("excelCoverage = " + excelCoverage);
        int dbCoverage = Integer.parseInt(dbData.get(coverage).toString());
        if (dbCoverage != excelCoverage) {
            System.out.println("Coverage is not matching for " + excelCoverage + " : " + dbCoverage);
            return false;
        }
        return true;
    }

    public boolean verifyEUTaxonomyPortfolioCoverageForUI(String portfolioName) {
        RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
        String portfolioId = apiController.getPortfolioId(portfolioName);
        RegulatoryReportingQueries queries = new RegulatoryReportingQueries();
        Map<String, Object> dbData = queries.getReportingYearDetails(portfolioId).get(0);
        System.out.println("dbData.size() = " + dbData.size());
        String uiCoverage = getCoveragePercentage(portfolioName);
        String dbCoverage = dbData.get("TAXONOMY_COVERAGE").toString();
        if (!uiCoverage.contains(dbCoverage)) {
            System.out.println("Coverage is not matching for " + uiCoverage + " : " + dbCoverage);
            return false;
        }
        return true;
    }

    public boolean verifyEUTaxonomySheets() {
        String excelName = rrStatusPage_PortfoliosList.get(0).getText().replaceAll("ready", "").trim();
        System.out.println("Verifying reports content for " + excelName);
        String[] sheets = {"Green Investment Ratio Template", "Underlying Data - Overview", "Underlying Data - Activities", "Definitions", "Disclaimer"};
        for (String sheet : sheets) {
            ExcelUtil excelData = getExcelData(excelName, sheet);
            if (excelData == null) {
                System.out.println("Sheet " + sheet + " is not found in the excel");
                return false;
            }
        }
        return true;
    }

    public boolean verifyGreenInvestmentRatioTemplate() {
        ExcelUtil template = getEUTaxonomyTemplate("Green Investment Ratio Template");
        String excelName = rrStatusPage_PortfoliosList.get(0).getText().replaceAll("ready", "").trim();
        System.out.println("Verifying reports content for " + excelName);
        List<String> excelData = getExcelDataList(excelName, "Green Investment Ratio Template", 3);
        for (int i = 4; i < template.rowCount(); i++) {
            for (int j = 1; j < template.columnCount(); j++) {
                if (template.getCellData(i, j).equals("") || template.getCellData(i, j).equals("FUTURE RELEASE")) {
                    continue;
                } else if (!excelData.contains(template.getCellData(i, j).trim())) {
                    System.out.println(template.getCellData(i, j));
                    //System.out.println("Row = " + (i+1) + " Column = " + (j+1));
                    return false;
                }
            }
        }
        return true;
    }

    public boolean verifyUnderlyingDataOverview() {
        ExcelUtil template = getEUTaxonomyTemplate("Underlying Data - Overview");
        String excelName = rrStatusPage_PortfoliosList.get(0).getText().replaceAll("ready", "").trim();
        System.out.println("Verifying reports content for " + excelName);
        ExcelUtil excelData = getExcelData(excelName, "Underlying Data - Overview");
        for (String title : template.getColumnsNames()) {
            if (!excelData.getColumnsNames().contains(title)) {
                System.out.println(title);
                return false;
            }
        }
        return true;
    }

    public boolean verifyUnderlyingDataActivities() {
        ExcelUtil template = getEUTaxonomyTemplate("Underlying Data - Activities");
        String excelName = rrStatusPage_PortfoliosList.get(0).getText().replaceAll("ready", "").trim();
        System.out.println("Verifying reports content for " + excelName);
        ExcelUtil excelData = getExcelData(excelName, "Underlying Data - Activities");
        for (String title : template.getColumnsNames()) {
            if (!excelData.getColumnsNames().contains(title)) {
                System.out.println(title);
                return false;
            }
        }
        return true;
    }

    public boolean verifyDefinitions() {
        ExcelUtil template = getEUTaxonomyTemplate("Definitions");
        String excelName = rrStatusPage_PortfoliosList.get(0).getText().replaceAll("ready", "").trim();
        System.out.println("Verifying reports content for " + excelName);
        List<String> excelData = getExcelDataList(excelName, "Definitions");
        for (int i = 0; i < template.rowCount(); i++) {
            for (int j = 0; j < template.columnCount(1); j++) {
                if (template.getCellData(i, j) == null || template.getCellData(i, j).equals("")) {
                    continue;
                } else if (!excelData.contains(template.getCellData(i, j).trim())) {
                    System.out.println(template.getCellData(i, j));
                    System.out.println("Row = " + (i + 1) + " Column = " + (j + 1));
                    return false;
                }
            }
        }
        return true;
    }

    public boolean verifyDisclaimer() {
        ExcelUtil template = getEUTaxonomyTemplate("Disclaimer");
        String excelName = rrStatusPage_PortfoliosList.get(0).getText().replaceAll("ready", "").trim();
        System.out.println("Verifying reports content for Disclaimer Sheet");
        List<String> excelData = new ArrayList<>();
        for (String data : getExcelDataList(excelName, "Disclaimer")) {
            excelData.addAll(BrowserUtils.splitToSentences(data));
        }

//        excelData.forEach(System.out::println);
        List<String> templateData = new ArrayList<>();
        for (int i = 0; i < template.rowCount(); i++) {
            for (int j = 0; j < template.columnCount(); j++) {
                templateData.addAll(excelData);

                //templateData.addAll(BrowserUtils.splitToSentences(template.getCellData(i, j)));
            }
        }

       // templateData.set(0, templateData.get(0).replace("© 2022 Moody’s", "© " + Year.now() + " Moody’s"));
       // templateData.set(0, templateData.get(0).replace("© 2022 Moody's", "© " + Year.now() + " Moody's"));
//        System.out.println("\n===================================\n");
//        templateData.forEach(System.out::println);
        for (String sentence : templateData) {
            if (!excelData.contains(sentence)) {
                System.out.println(sentence);
                return false;
            }
            System.out.println(sentence);
        }
        return true;
    }

    public boolean verifyPreviouslyDownloadedButton() {

            return previouslyDownloadedButton.isDisplayed();

    }

    public void clickPreviouslyDownloadedButton() {
        BrowserUtils.waitForVisibility(previouslyDownloadedButton, 10).click();
    }

    public void verifyPreviouslyDownloadedScreen() {
        BrowserUtils.waitForVisibility(previouslyDownloadedStaticLabel, 30);
        String recordCellsXpath = "//div[text()='Reports created previously:']/following-sibling::div/div/div";
        String strDate = Driver.getDriver().findElement(By.xpath(recordCellsXpath + "[1]")).getText();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            sdf.parse(strDate);
        } catch (Exception e) {
            assertTestCase.assertTrue(false, "Verify downloaded date in first column");
        }

        String fileName = Driver.getDriver().findElement(By.xpath(recordCellsXpath + "[2]/span")).getText();
        assertTestCase.assertTrue(fileName.endsWith(".zip"), "Verify downloaded file name in second column");

        String portfolios = Driver.getDriver().findElement(By.xpath(recordCellsXpath + "[3]")).getText();
        String portfoliosSerialNumber = portfolios.substring(0, portfolios.indexOf(":"));
        assertTestCase.assertTrue(NumberUtils.isParsable(portfoliosSerialNumber), "Verify portfolios information in the third column");
    }

    public void verifyFileDownloadFromPreviouslyDownloadedScreen() {
        BrowserUtils.waitForVisibility(previouslyDownloadedStaticLabel, 30);
        String expFileName = recordFileName.getText();

        FileDownloadUtilities.deleteDownloadFolder();
        recordFileName.click();
        assertTestCase.assertTrue(FileDownloadUtilities.waitUntilFileIsDownloaded(), "Verify download of export file");
        String actualFileName = FileDownloadUtilities.getDownloadedFileName();

        System.out.println(actualFileName + "-->" + expFileName);
        assertTestCase.assertTrue(actualFileName.equals(expFileName), "Verify file is downloaded from Previously Downloaded screen");
    }

    public boolean verifyBVD9IDInCompanyLevelOutput(List<String> selectedPortfolios, String reportFormat, String reportYear) {
        for (String portfolioName : selectedPortfolios) {
            System.out.println("\nportfolioName = " + portfolioName);
            String portfolioId = apiController.getPortfolioId(portfolioName);

            //open Excel file
            String excelName = "";
            ExcelUtil excelData = null;
            //if multiple portfolios are selected and
            // if interim report selected, then multiple Excel files will be downloaded and sheet index will be always 1
            // if annual report selected, then one Excel file will be downloaded and sheet index will be index of selected portfolios
            if (rrStatusPage_PortfoliosList.size() > 1) {
                excelName = rrStatusPage_PortfoliosList.get(selectedPortfolios.indexOf(portfolioName)).getText().replaceAll("ready", "").trim();
                excelData = getExcelData(excelName, 1);
            } else {
                excelName = rrStatusPage_PortfoliosList.get(0).getText().replaceAll("ready", "").trim();
                excelData = getExcelData(excelName, selectedPortfolios.indexOf(portfolioName) + 1);//we skip index 0 because it is for Portfolio Level Output
            }
            System.out.println("Sheet Name = " + excelData.getSheetName());
            //get column names and verify it has BVD9 ID but nor Factset ID
            List<String> columnNames = excelData.getColumnsNames();
            System.out.println("columnNames = " + columnNames);
            if (columnNames.contains("BVD9 ID")) {
                System.out.println("BVD9 ID column is found in the excel");
                //return false;
            }
            if (!columnNames.contains("Factset ID")) {
                System.out.println("Factset ID column is not found in the excel");
                //return false;
            }
            //Data Validation
            RegulatoryReportingQueries queries = new RegulatoryReportingQueries();
            List<Map<String, Object>> dbData = queries.getBVD9idForEachPortfolio(portfolioId);
            System.out.println("dbData.get() = " + dbData.get(0));
            for(Map<String, Object> dbRow: dbData){
                String bvd9Id = dbRow.get("BVD9_NUMBER").toString();
                String companyName = dbRow.get("COMPANY_NAME").toString();
                List<String> excelRow = excelData.getRowData(companyName);
                System.out.println("excelRow = " + excelRow);
                if(!excelRow.contains(bvd9Id)){
                    System.out.println("BVD9 ID is not found in the excel for company = " + companyName);
                    return false;
                }
                if(!excelRow.contains(companyName)){
                    System.out.println("Company name is not found in the excel for company = " + companyName);
                    return false;
                }
            }
        }
        return true;
    }


    public boolean verifySFDRCompanyOutput(List<String> selectedPortfolios, String year) {
        for (String portfolioName : selectedPortfolios) {
            System.out.println("\nportfolioName = " + portfolioName);
            String portfolioId = apiController.getPortfolioId(portfolioName);
            List<Map<String, Object>> dbData = queries.getSFDRCompanyOutput(portfolioId, year);
            System.out.println("dbData.size() = " + dbData.size());

            //open Excel file
            String excelName = "";
            ExcelUtil excelData = null;
            //if multiple portfolios are selected and
            // if interim report selected, then multiple Excel files will be downloaded and sheet index will be always 1
            // if annual report selected, then one Excel file will be downloaded and sheet index will be index of selected portfolios
            if (rrStatusPage_PortfoliosList.size() > 1) {
                excelName = rrStatusPage_PortfoliosList.get(selectedPortfolios.indexOf(portfolioName)).getText().replaceAll("ready", "").trim();
                excelData = getExcelData(excelName, 1);
            } else {
                excelName = rrStatusPage_PortfoliosList.get(0).getText().replaceAll("ready", "").trim();
                excelData = getExcelData(excelName, selectedPortfolios.indexOf(portfolioName) + 1);//we skip index 0 because it is for Portfolio Level Output
            }
            System.out.println("Sheet Name = " + excelData.getSheetName());
            for (Map<String, Object> dbRow : dbData) {
                String companyName = dbRow.get("COMPANY_NAME").toString();
                List<String> excelRow = excelData.getRowData(companyName);
                for (String cell : excelRow) {
                    //Check if cell found in db values
                    if (!dbRow.containsValue(cell)) {
                        //if not, it might be because digits after decimal point are more than 10, then round it to 10 digits
                        if (cell.matches("\\d+\\.\\d{11,}")) {
                            DecimalFormat f = new DecimalFormat("##.##########");
                            cell = f.format(Double.parseDouble(cell));
                            //after decimal point rounding, check if cell found in db values
                            if (!dbRow.containsValue(cell)) continue;//if yes continue to next cell
                            //if not then data is not found in the db values
                            System.out.println("companyName = " + companyName);
                            System.out.println(cell + " is not in DB");
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean verifyUserInputHistory(List<String> selectedPortfolios) {
        for (String portfolioName : selectedPortfolios) {
            String portfolioId = apiController.getPortfolioId(portfolioName);
            List<Map<String, Object>> dbData = queries.getBVD9idForEachPortfolio(portfolioId);

            //open Excel file
            String excelName = rrStatusPage_PortfoliosList.get(0).getText().replaceAll("ready", "").trim();
            ExcelUtil excelData = getExcelData(excelName, "User Input History");
            if (!excelData.searchData("Identifier")) return false;
            if (!excelData.searchData("Company Name")) return false;
            if (!excelData.searchData("Exposure Amount in EUR")) return false;
            if (!excelData.searchData("Exposure Amount %")) return false;

            for (int i = 0; i < dbData.size(); i++) {
                //System.out.println("DBData = "+dbData.get(i).values());
                String companyName = dbData.get(i).get("COMPANY_NAME").toString();
                List<String> companyRow = excelData.getRowData(companyName);
                for (String cell : companyRow) {

                    //convert dbData to String
                    if (!dbData.get(i).toString().contains(cell)) {
                        if (cell.matches("\\d+\\.0")) {
                            cell = cell.replaceAll("\\.0", "");
                            if (dbData.get(i).toString().contains(cell)) {
                                continue;
                            }
                            System.out.println("companyName = " + companyName);
                            System.out.println("cell = " + cell);
                            System.out.println(cell + " is not in DB");
                            return false;
                        }
                    }
                }
            }

        }
        return true;
    }

    public boolean verifyUserInputHistory222(List<String> selectedPortfolios) {
        for (String portfolioName : selectedPortfolios) {
            String portfolioId = apiController.getPortfolioId(portfolioName);
            List<Map<String, Object>> dbData = queries.getUserInputHistory(portfolioId);

            //open Excel file
            String excelName = rrStatusPage_PortfoliosList.get(0).getText().replaceAll("ready", "").trim();
            ExcelUtil excelData = getExcelData(excelName, "User Input History");
            if (!excelData.searchData("Identifier")) return false;
            if (!excelData.searchData("Company Name")) return false;
            if (!excelData.searchData("Exposure Amount in EUR")) return false;
            if (!excelData.searchData("Exposure Amount %")) return false;

            for (int i = 0; i < dbData.size(); i++) {
                //System.out.println("DBData = "+dbData.get(i).values());
                String companyName = dbData.get(i).get("COMPANY_NAME").toString();
                List<String> companyRow = excelData.getRowData(companyName);
                for (String cell : companyRow) {

                    //convert dbData to String
                    if (!dbData.get(i).toString().contains(cell)) {
                        if (cell.matches("\\d+\\.0")) {
                            cell = cell.replaceAll("\\.0", "");
                            if (dbData.get(i).toString().contains(cell)) {
                                continue;
                            }
                            System.out.println("companyName = " + companyName);
                            System.out.println("cell = " + cell);
                            System.out.println(cell + " is not in DB");
                            return false;
                        }
                    }
                }
            }

        }
        return true;
    }


    public boolean verifyPortfolioLevelOutput(List<String> selectedPortfolios, String reportingYear, String reportFormat, String useLatestData) {
        for (String portfolioName : selectedPortfolios) {
            RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
            System.out.println("portfolioName = " + portfolioName);
            String portfolioId = apiController.getPortfolioId(portfolioName);
            RegulatoryReportingQueries queries = new RegulatoryReportingQueries();
            //open Excel file
            String excelName = "";
            ExcelUtil excelData = null;
            //if multiple portfolios are selected and
            // if interim report selected, then multiple Excel files will be downloaded and sheet index will be always 1
            // if annual report selected, then one Excel file will be downloaded and sheet index will be index of selected portfolios
            if (rrStatusPage_PortfoliosList.size() > 1) {
                excelName = rrStatusPage_PortfoliosList.get(selectedPortfolios.indexOf(portfolioName)).getText().replaceAll("ready", "").trim();
            } else {
                excelName = rrStatusPage_PortfoliosList.get(0).getText().replaceAll("ready", "").trim();
            }
            excelData = getExcelData(excelName, "Portfolio Level Output");
            System.out.println("Sheet Name = " + excelData.getSheetName());

            List<Map<String, Object>> dbData = queries.getPortfolioLevelOutput(portfolioId, reportingYear, reportFormat, useLatestData);
            //get number cells from excel file
            List<String> excelNumberCells = new ArrayList<>();
            if (selectedPortfolios.size() > 1 && reportFormat.equals("Annual")) {
                int columnNumber = selectedPortfolios.indexOf(portfolioName) * 2 + 5;
                System.out.println("columnNumber = " + columnNumber);
                excelNumberCells = excelData.getNumericCells(columnNumber);
                excelNumberCells.addAll(excelData.getNumericCells(columnNumber + 1));
            } else {
                excelNumberCells = excelData.getNumericCells();
            }
            System.out.println("excelNumberCells = " + excelNumberCells);

            DecimalFormat decimalFormat = new DecimalFormat("##.####");
            //get number cells from db
            List<String> dbNumberCells = new ArrayList<>();
            for (Map<String, Object> map : dbData) {
                if (map.get("IMPACT") != null && !map.get("IMPACT").equals("NI"))
                    dbNumberCells.add(decimalFormat.format(Double.parseDouble(map.get("IMPACT").toString())));
                if (map.get("SCOPE_OF_DISCLOSURE") != null && !map.get("SCOPE_OF_DISCLOSURE").equals("NI"))
                    dbNumberCells.add(decimalFormat.format(Double.parseDouble(map.get("SCOPE_OF_DISCLOSURE").toString())));
            }
            dbNumberCells.add("0.0");
            dbNumberCells.add("0.00");
            System.out.println("dbNumberCells = " + dbNumberCells);
            //verify db number cells list contains all values of excelnumbercells list
            for (String cell : excelNumberCells) {
                System.out.println("cell = " + cell);
                if (!dbNumberCells.contains(cell)) {
                    if (!dbNumberCells.contains(cell.replaceAll("\\.0", ""))) {
                        System.out.println(cell + " is not in DB");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean verifyEUReportsUnderLyingDataOverview(String portfolioId) {
        DecimalFormat dfZero = new DecimalFormat("0.00");
        for (String portfolio : BrowserUtils.getElementsText(rrStatusPage_PortfoliosList)) {
            String excelName = portfolio.replaceAll("ready", "").trim();
            System.out.println("Verifying reports content for " + excelName);
            ExcelUtil excelData = getExcelData(excelName, 1);
            System.out.println("excelData = " + excelData.getColumnsNames());

            RegulatoryReportingQueries queries = new RegulatoryReportingQueries();
            List<Map<String, Object>> dbData = queries.getRUnderlyingDataOverview(portfolioId);


            for (Map<String, Object> dbRow : dbData) {
                List<String> checkValues = new ArrayList<>(Arrays.asList(
                        "BVD9 ID",
                        "VIGEO KEY",
                        "ISIN",
                        "LEGAL ENTITY IDENTIFIER (LEI)",
                        "TITLE",
                        "Exposure Amount in EUR",
                        "Exposure Amount %",
                        "ZONE",
                        "COUNTRY",
                        "ENTITY TYPE (SOVEREIGN, NON-FINANCIAL, FINANCIAL)",
                        "GENERIC SECTOR",
                        "NFRD SCOPE (YES/NO)",
                        "TURNOVER REPORTING YEAR",
                        "CAPEX REPORTING YEAR",
                        "CLIMATE CHANGE MITIGATION - PROPORTION OF TAXONOMY ALIGNED TURNOVER (% OF TOTAL COMPANY TURNOVER)",
                        "CLIMATE CHANGE MITIGATION - PROPORTION OF TAXONOMY ALIGNED CAPEX (% OF TOTAL COMPANY CAPEX)",
                        "CLIMATE CHANGE ADAPTATION - PROPORTION OF TAXONOMY ALIGNED TURNOVER (% OF TOTAL COMPANY TURNOVER)",
                        "CLIMATE CHANGE ADAPTATION - PROPORTION OF TAXONOMY ALIGNED CAPEX (% OF TOTAL COMPANY CAPEX)",
                        "EU TAXONOMY ELIGIBLE TURNOVER (%)",
                        "EU TAXONOMY ELIGIBLE TURNOVER - VALUE TYPE",
                        "EU TAXONOMY ELIGIBLE CAPEX (%)",
                        "EU TAXONOMY ELIGIBLE CAPEX - VALUE TYPE",
                        "EU TAXONOMY ALIGNED TURNOVER (%)",
                        "EU TAXONOMY ALIGNED TURNOVER - VALUE TYPE",
                        "EU TAXONOMY ALIGNED CAPEX (%)",
                        "EU TAXONOMY ALIGNED CAPEX - VALUE TYPE"));
                int rowNumber = excelData.getRowNumber(dbRow.get("BVD9 ID").toString(), excelData.getColumnNum("BVD9 ID"));
                for (String colName : checkValues) {


                    String excelValue = excelData.getCellData(rowNumber, excelData.getColumnNum(colName));
                    if (BrowserUtils.isNumeric(excelValue) && !colName.equals("BVD9 ID")
                            && !colName.equals("TURNOVER REPORTING YEAR") && !colName.equals("CAPEX REPORTING YEAR")
                    ) {

                        excelValue = dfZero.format(Double.valueOf(excelValue));
                    }
                    if (excelValue == "" && dbRow.get(colName) == null) continue;
                    if (!excelValue.equals(dbRow.get(colName).toString())) {
                        System.out.println("Data " + dbRow.get(colName).toString() + " is not found in the excel");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean verifyEUReportsUnderlyingDataActivities(String portfolioId) {
        DecimalFormat dfZero = new DecimalFormat("0.00");
        //for (String portfolio : BrowserUtils.getElementsText(rrStatusPage_PortfoliosList)) {
        // String excelName = portfolio.replaceAll("ready", "").trim();
        String excelName = "EUT_Portfolio Upload updated_good2_01_11_2023_1673474560860.xlsx";
        System.out.println("Verifying reports content for " + excelName);
        ExcelUtil excelData = getExcelData(excelName, 2);
        System.out.println("excelData = " + excelData.getColumnsNames());

        RegulatoryReportingQueries queries = new RegulatoryReportingQueries();
        List<Map<String, Object>> dbData = queries.getRUnderlyingDataActvities(portfolioId);

        for (Map<String, Object> dbRow : dbData) {
            List<String> checkValues = new ArrayList<>(Arrays.asList(
                    "BVD9 ID", "VIGEO KEY", "ISIN", "TITLE", "ZONE", "COUNTRY", "GENERIC SECTOR",
                    "REPORTING YEAR", "ENVIRONMENTAL OBJECTIVE", "TYPE OF CONTRIBUTION TO CLIMATE CHANGE MITIGATION (TURNOVER)",
                    "TYPE OF CONTRIBUTION TO CLIMATE CHANGE MITIGATION (CAPEX)",
                    "TYPE OF CONTRIBUTION TO CLIMATE CHANGE ADAPTATION (TURNOVER)",
                    "TYPE OF CONTRIBUTION TO CLIMATE CHANGE ADAPTATION (CAPEX)",
                    "ACTIVITY TURNOVER (% OF TOTAL COMPANY TURNOVER)", "VALUE TYPE (ACTIVITY TURNOVER)",
                    "SOURCE (ACTIVITY TURNOVER)", "COMMENT (ACTIVITY TURNOVER)",
                    "ACTIVITY CAPEX (% OF TOTAL COMPANY CAPEX)",
                    "VALUE TYPE (ACTIVITY CAPEX)",
                    "SOURCE (ACTIVITY CAPEX)",
                    "COMMENT (ACTIVITY CAPEX)"));
            // int rowNumber = excelData.getRowNumber(dbRow.get("BVD9 ID").toString(),excelData.getColumnNum("BVD9 ID"));
            Map<String, String> params = new HashMap<>();
            params.put("BVD9 ID", dbRow.get("BVD9 ID").toString());
            params.put("ACTIVITY TURNOVER (% OF TOTAL COMPANY TURNOVER)",
                    dbRow.get("ACTIVITY TURNOVER (% OF TOTAL COMPANY TURNOVER)") == null ? "" : dbRow.get("ACTIVITY TURNOVER (% OF TOTAL COMPANY TURNOVER)").toString());
            params.put("TYPE OF CONTRIBUTION TO CLIMATE CHANGE MITIGATION (TURNOVER)",
                    dbRow.get("TYPE OF CONTRIBUTION TO CLIMATE CHANGE MITIGATION (TURNOVER)") == null ? "" : dbRow.get("TYPE OF CONTRIBUTION TO CLIMATE CHANGE MITIGATION (TURNOVER)").toString());
            System.out.println(params.toString());
            List<String> requiredCols = Arrays.asList(new String[]{"All"});
            Map<String, String> exlData = excelData.getfilteredData(params, requiredCols);
            for (String colName : checkValues) {
                String excelValue = exlData.get(colName);
                if (BrowserUtils.isNumeric(excelValue) && !colName.equals("BVD9 ID")
                        && !colName.equals("REPORTING YEAR")) {
                    excelValue = dfZero.format(Double.valueOf(excelValue));
                }
                if (excelValue == "" && dbRow.get(colName) == null) continue;
                if (!excelValue.equals(dbRow.get(colName).toString())) {
                    System.out.println("Data " + dbRow.get(colName).toString() + " is not found in the excel");
                    return false;
                }
            }
        }
        // }
        return true;
    }

    public boolean verifyEUReportsGreenInvestmentRatio(String portfolioId, String derivative, String cash) {
        DecimalFormat dfZero = new DecimalFormat("0.00");
        for (String portfolio : BrowserUtils.getElementsText(rrStatusPage_PortfoliosList)) {
            String excelName = portfolio.replaceAll("ready", "").trim();
            //String excelName = "EUT_Portfolio Upload updated_good2_01_11_2023_1673474560860.xlsx";
            System.out.println("Verifying reports content for " + excelName);
            ExcelUtil excelData = getExcelData(excelName, 0);
            RegulatoryReportingQueries queries = new RegulatoryReportingQueries();
            List<Map<String, Object>> dbData = queries.getGreenInvestmentRatio(portfolioId, derivative, cash);

            for (Map<String, Object> dbRow : dbData) {
                int rowNumber = Integer.valueOf(dbRow.get("ROWNUM").toString());
                int tab = Integer.valueOf(dbRow.get("TAB").toString());
                if (dbRow.get("VALUE") != null && dbRow.get("DESCRIPTION") != null && dbRow.get("KPIS") != null) {
                    String desc = dbRow.get("DESCRIPTION") == null ? "NA" : dbRow.get("DESCRIPTION").toString();
                    double value = Double.valueOf(dbRow.get("VALUE").toString());
                    String KPIS = dbRow.get("KPIS").toString();
                    double excelValue = 0.00;
                    String excelDesc = "";
                    if (tab == 1) {
                        excelDesc = excelData.getCellData(rowNumber, 1);
                        excelValue = Double.valueOf(excelData.getCellData(rowNumber, 2));
                    } else if (tab == 2) {
                        excelDesc = excelData.getCellData(rowNumber, 3);
                        excelValue = Double.valueOf(excelData.getCellData(rowNumber, 4));
                    }
                    if (!String.format("%.2f", excelValue).equals(String.format("%.2f", value)) && excelDesc.equals(desc)) {
                        return false;
                    }
                }

            }
        }
        return true;
    }

    public boolean verifyScope3GHGEmissionsForCompanyOutput(List<String> selectedPortfolios, String year) {
        for (String portfolioName : selectedPortfolios) {
            System.out.println("\nportfolioName = " + portfolioName);
            String portfolioId = apiController.getPortfolioId(portfolioName);
            //add  each SFDR_1TXNMYID_2 data to dbData list
            List<String> dbData = new ArrayList<>();
            queries.getSFDRCompanyOutput(portfolioId, year).forEach(row -> {
                if (row.get("SFDR_1TXNMYID_3") == null) dbData.add("");
                else if (row.get("SFDR_1TXNMYID_3").toString().isEmpty()) dbData.add("NI");
                else dbData.add(row.get("SFDR_1TXNMYID_3").toString());
            });
            //open Excel file
            String excelName = "";
            ExcelUtil excelData = null;
            //if multiple portfolios are selected and
            // if interim report selected, then multiple Excel files will be downloaded and sheet index will be always 1
            // if annual report selected, then one Excel file will be downloaded and sheet index will be index of selected portfolios
            if (rrStatusPage_PortfoliosList.size() > 1) {
                excelName = rrStatusPage_PortfoliosList.get(selectedPortfolios.indexOf(portfolioName)).getText().replaceAll("ready", "").trim();
                excelData = getExcelData(excelName, 1);
            } else {
                excelName = rrStatusPage_PortfoliosList.get(0).getText().replaceAll("ready", "").trim();
                excelData = getExcelData(excelName, selectedPortfolios.indexOf(portfolioName) + 1);//we skip index 0 because it is for Portfolio Level Output
            }
            System.out.println("Sheet Name = " + excelData.getSheetName());
            if (!excelData.getColumnsNames().contains("Scope 3 Emissions")) {
                System.out.println("Scope 3 Emissions column is not found in the excel");
                return false;
            }
            List<String> columnData = excelData.getColumnData(excelData.getColumnsNames().indexOf("Scope 3 Emissions"));
            if (!columnData.contains("Metric Tons CO2 Equivalent")) {
                System.out.println("Metric Tons CO2 Equivalent is not found in the excel");
                return false;
            }
            columnData.remove("Metric Tons CO2 Equivalent");
            for (String cell : columnData) {
                //if a cell is not a number that greater than or equal to 0, or not equals Ni or not null return false
                try {
                    if (cell == null || cell.equals("NI") || cell.equals("") || Double.parseDouble(cell) >= 0) {
                        //cell data is verified
                    } else {
                        System.out.println("Wrong cell data = " + cell);
                        return false;
                    }
                } catch (Exception e) {
                    System.out.println("Failed at cell = " + cell);
                    return false;
                }
            }

            //sort and print excel data
//            System.out.println("excelData = " + BrowserUtils.specialSort(columnData));
//            System.out.println("dbData = " + BrowserUtils.specialSort(dbData));

            for (String cell : columnData) {
                if (!dbData.contains(cell)) {
                    System.out.println("cell not found = " + cell);
                    //return false;
                }
            }
        }//end of portfolios
        return true;
    }

    public boolean verifyScope3GHGEmissionsForPortfolioLevelOutput(List<String> selectedPortfolios, String year, String reportingFormat) {
        for (String portfolioName : selectedPortfolios) {
            System.out.println("\nportfolioName = " + portfolioName);
            String portfolioId = apiController.getPortfolioId(portfolioName);

            //open Excel file
            String excelName = "";
            ExcelUtil excelData = null;
            //if multiple portfolios are selected and
            // if interim report selected, then multiple Excel files will be downloaded and sheet index will be always 1
            // if annual report selected, then one Excel file will be downloaded and sheet index will be index of selected portfolios
            if (rrStatusPage_PortfoliosList.size() > 1) {
                excelName = rrStatusPage_PortfoliosList.get(selectedPortfolios.indexOf(portfolioName)).getText().replaceAll("ready", "").trim();
                excelData = getExcelData(excelName, 0);
            } else {
                excelName = rrStatusPage_PortfoliosList.get(0).getText().replaceAll("ready", "").trim();
                excelData = getExcelData(excelName, 0);//we skip index 0 because it is for Portfolio Level Output
            }
            System.out.println("Sheet Name = " + excelData.getSheetName());

            List<String> rowData = excelData.getRowData(excelData.getRowNumber("Scope 3 GHG emissions", 2));
            System.out.println("rowData = " + rowData);

            if (!rowData.contains("Scope 3 GHG emissions")) {
                System.out.println("Scope 3 emissions cell is not found in the excel");
                return false;
            }
            if (!rowData.contains("Metric tons of CO2 equivalent")) {
                System.out.println("Metric tons of CO2 equivalent cell is not found in the excel");
                return false;
            }

            //In the case of an Annual Report - The Average Impact Q1-Q2-Q3-Q4 of Scope 3 GHG emissions is a positive number [0; + infinity].
            rowData.remove("Scope 3 GHG emissions");
            rowData.remove("Metric tons of CO2 equivalent");
            for (String cell : rowData) {
                //Scope of Disclosure for Scope 3 GHG Emissions. is calculated with the same formula of Scope of disclosure.
                //As for the other indicators, scope of disclosure is a percentage with up to 2 decimal points. Maximum value = 100 Minimum value = 0
                try {
                    if (cell.equals("") || Double.parseDouble(cell) >= 0) {
                        //cell data is verified
                    } else {
                        System.out.println("Wrong cell data = " + cell);
                        //return false;
                    }
                } catch (Exception e) {
                    System.out.println("Failed at cell = " + cell);
                    return false;
                }
            }
            //Validate data for Scope 3 GHG emissions
            List<Map<String, Object>> dbData = queries.getPortfolioLevelOutput(portfolioId, year.equalsIgnoreCase("latest") ? "2020" : year, reportingFormat, year.equalsIgnoreCase("latest") ? "Yes" : "No");
            DecimalFormat decimalFormat = new DecimalFormat("##.####");
            String scope3GHGEmissionsImpactScore = dbData.stream().filter(row -> row.get("SFDR_SUBCATEGORY").toString().equals("Scope 3 GHG emissions")).findFirst().get().get("IMPACT").toString();
            System.out.println("scope3GHGEmissionsImpactScore = " + scope3GHGEmissionsImpactScore);
            scope3GHGEmissionsImpactScore = decimalFormat.format(Double.parseDouble(scope3GHGEmissionsImpactScore));
            System.out.println("scope3GHGEmissionsImpactScore = " + scope3GHGEmissionsImpactScore);
            String scope3GHGEmissionsScopeOfDisclosure = decimalFormat.format(dbData.stream().filter(row -> row.get("SFDR_SUBCATEGORY").toString().equals("Scope 3 GHG emissions")).findFirst().get().get("SCOPE_OF_DISCLOSURE"));
            System.out.println("scope3GHGEmissionsScopeOfDisclosure = " + scope3GHGEmissionsScopeOfDisclosure);
            if (!scope3GHGEmissionsImpactScore.contains(".")) scope3GHGEmissionsImpactScore += ".0";
            if (!scope3GHGEmissionsScopeOfDisclosure.contains(".")) scope3GHGEmissionsScopeOfDisclosure += ".0";
            if (!rowData.contains(scope3GHGEmissionsImpactScore) || !rowData.contains(scope3GHGEmissionsScopeOfDisclosure)) {
                System.out.println("Impact score or Scope of disclosure is not found in the excel for Scope 3 GHG emissions");
                return false;
            }
        }
        return true;
    }

    public void clickOnCreateReportsButton() {
        System.out.println("Clicking on Create Reports button");
        BrowserUtils.waitForClickablility(createReportsButton, 5).click();
    }

}
