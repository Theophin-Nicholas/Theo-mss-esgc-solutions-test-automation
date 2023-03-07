package com.esgc.RegulatoryReporting.UI.Pages;

import com.esgc.Base.UI.Pages.UploadPage;
import com.esgc.RegulatoryReporting.API.Controllers.RegulatoryReportingAPIController;
import com.esgc.Utilities.*;
import com.esgc.RegulatoryReporting.DB.DBQueries.RegulatoryReportingQueries;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class RegulatoryReportingPage extends UploadPage {

    RegulatoryReportingQueries queries = new RegulatoryReportingQueries();
    RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();


    //LOCATORS
    @FindBy(xpath = "//header//li")
    public WebElement pageTitle;

    @FindBy(xpath = "//div[.='Select Reporting']")
    public WebElement reportingTitle;

    @FindBy(xpath = "//div[.='Reporting']")
    public WebElement reportingSubtitle;

    @FindBy(xpath = "//div[.='Reporting']/following-sibling::div//span[2]")
    public List<WebElement> reportingNamesList;

    @FindBy(xpath = "//div[.='Reporting']/following-sibling::div//input")
    public List<WebElement> reportingRadioButtonList;

    @FindBy(xpath = "//div[.='Select Portfolios']/../div[2]/following-sibling::div/div[1]//span[2]")
    public List<WebElement> rrPage_portfolioNamesList;

    @FindBy(xpath = "//div[.='Select Portfolios']/../div[2]/following-sibling::div/div[1]//input")
    public List<WebElement> portfolioRadioButtonList;

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

    @FindBy(id = "link-upload")
    public WebElement uploadAnotherPortfolioLink;

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

    @FindBy(xpath = "//fieldset[@id='eu_taxonomy']")
    public WebElement EUTaxonomy;

    public List<WebElement> getEUTaxonomyInputBox(String portfolio) {
        return Driver.getDriver().findElements(By.xpath("//div[.='Select Portfolios']/..//span[contains(text(),'" + portfolio + "')]/../../../../../div[4]/div"));
    }


    //METHODS

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

    public List<String> getReportingList() {
        return BrowserUtils.getElementsText(reportingNamesList);
    }

    public List<String> getPortfolioList() {
        return BrowserUtils.getElementsText(rrPage_portfolioNamesList);
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
    public int selectPortfolioOptionByName(String name) {
        int index = getPortfolioList().indexOf(name.trim());
        System.out.println("Index = " + index);
        portfolioRadioButtonList.get(index).click();
        return index;
    }

    public int deSelectPortfolioOptionByName(String name) {
        return selectPortfolioOptionByName(name);
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

    public boolean verifyReportsContentForData(List<String> selectedPortfolios, int sheetIndex, String data) {
        String excelName = rrStatusPage_PortfoliosList.get(0).getText().replaceAll("ready", "").trim();
        System.out.println("Verifying reports content for sheet " + sheetIndex);
        ExcelUtil excelData = getExcelData(excelName, sheetIndex);
        if (!excelData.searchData(data)) {
            System.out.println("Data " + data + " is not found in the excel");
        }
        return excelData.searchData(data);
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
                templateData.addAll(BrowserUtils.splitToSentences(template.getCellData(i, j)));
            }
        }
//        System.out.println("\n===================================\n");
//        templateData.forEach(System.out::println);
        for (String sentence : templateData) {
            if (!excelData.contains(sentence)) {
                System.out.println(sentence);
                return false;
            }
        }
        return true;
    }

    public boolean verifyPreviouslyDownloadedButton() {
        try {
            return previouslyDownloadedButton.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void clickPreviouslyDownloadedButton() {
        previouslyDownloadedButton.click();
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
            String portfolioId = apiController.getPortfolioId(portfolioName);
            RegulatoryReportingQueries queries = new RegulatoryReportingQueries();
            //open Excel file
            String excelName = rrStatusPage_PortfoliosList.get(0).getText().replaceAll("ready", "").trim();
            ExcelUtil excelData = getExcelData(excelName, "Portfolio Level Output");

            List<Map<String, Object>> dbData = queries.getPortfolioLevelOutput(portfolioId, reportingYear, reportFormat, useLatestData);
            //get number cells from excel file
            List<String> excelNumberCells = excelData.getNumericCells();
            //System.out.println("excelNumberCells = " + excelNumberCells);
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

    public boolean verifyScope3GHGEmissionsForPortfolioLevelOutput(List<String> selectedPortfolios, String year) {
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
            List<Map<String, Object>> dbData = queries.getPortfolioLevelOutput(portfolioId, year.equalsIgnoreCase("latest")?"2020":year, "Annual", year.equalsIgnoreCase("latest")?"Yes":"No");
            DecimalFormat decimalFormat = new DecimalFormat("##.####");
            String scope3GHGEmissionsImpactScore = decimalFormat.format(dbData.stream().filter(row -> row.get("SFDR_SUBCATEGORY").toString().equals("Scope 3 GHG emissions")).findFirst().get().get("IMPACT"));
            System.out.println("scope3GHGEmissionsImpactScore = " + scope3GHGEmissionsImpactScore);
            String scope3GHGEmissionsScopeOfDisclosure = decimalFormat.format(dbData.stream().filter(row -> row.get("SFDR_SUBCATEGORY").toString().equals("Scope 3 GHG emissions")).findFirst().get().get("SCOPE_OF_DISCLOSURE"));
            System.out.println("scope3GHGEmissionsScopeOfDisclosure = " + scope3GHGEmissionsScopeOfDisclosure);
            if(!scope3GHGEmissionsImpactScore.contains(".")) scope3GHGEmissionsImpactScore += ".0";
            if(!scope3GHGEmissionsScopeOfDisclosure.contains(".")) scope3GHGEmissionsScopeOfDisclosure += ".0";
            if(!rowData.contains(scope3GHGEmissionsImpactScore) || !rowData.contains(scope3GHGEmissionsScopeOfDisclosure)){
                System.out.println("Impact score or Scope of disclosure is not found in the excel for Scope 3 GHG emissions");
                return false;
            }
        }
        return true;
    }
}
