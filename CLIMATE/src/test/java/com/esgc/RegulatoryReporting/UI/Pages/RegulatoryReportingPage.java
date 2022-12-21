package com.esgc.RegulatoryReporting.UI.Pages;

import com.esgc.Base.UI.Pages.UploadPage;
import com.esgc.RegulatoryReporting.API.Controllers.RegulatoryReportingAPIController;
import com.esgc.Utilities.*;
import com.esgc.RegulatoryReporting.DB.DBQueries.RegulatoryReportingQueries;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.File;
import java.util.*;


public class RegulatoryReportingPage extends UploadPage {
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


    //METHODS
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
    public void selectPortfolioOptionByName(String name) {
        System.out.println("Index = " + getPortfolioList().indexOf(name));
        portfolioRadioButtonList.get(getPortfolioList().indexOf(name)).click();
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
        for(String handle:currentTabs){
            if(!windowHandles.contains(handle)){
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
        System.out.println("selectedPortfolioOptions = " + selectedPortfolioOptions);
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
        String zipFilePath = Arrays.asList(dir_contents).stream().filter(e -> ((e.getName().startsWith("SFDR") ||e.getName().startsWith("EUT")) &&
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

    public boolean verifyReportsContent(List<String> selectedPortfolios) {
        for (String portfolio : selectedPortfolios) {
            String excelName = portfolio.replaceAll("ready", "").trim();
            System.out.println("Verifying reports content for " + excelName);
            ExcelUtil excelData = getExcelData(excelName, 1);
//            System.out.println("excelData = " + excelData.getColumnsNames());
            RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
            String portfolioId = apiController.getPortfolioId(excelName);
            System.out.println("portfolioId = " + portfolioId);
            RegulatoryReportingQueries queries = new RegulatoryReportingQueries();
            List<Map<String, Object>> dbData = queries.getCompanyLevelOutput(portfolioId, "latest_data");
            for (Map<String, Object> row : dbData) {
                List<String> checkValues = new ArrayList<>(Arrays.asList("COMPANY_NAME", "VE_ID", "FACTSET_ID", "HIGH_IMPACT_CHK",
                        //column names
                        "Adverse sustainability indicator", "Average Impact Q1-Q2-Q3-Q4", "Impact Portfolio 1", "Scope of Disclosure Portfolio 1",
                        "Impact Portfolio 2", "Scope of Disclosure Portfolio 2", "Impact Portfolio 3", "Scope of Disclosure Portfolio 3",
                        "Impact Portfolio 4", "Scope of Disclosure Portfolio 4"));
                for (String data : checkValues) {
                    if (data == null) continue;
                    if (!excelData.searchData((row.get(data).toString()))) {
                        System.out.println("Data " + row.get(data).toString() + " is not found in the excel");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean verifyReportsContentForAnnualReports(List<String> selectedPortfolios) {
        String excelName = rrStatusPage_PortfoliosList.get(0).getText().replaceAll("ready", "").trim();
        for (int i = 0; i < selectedPortfolios.size(); i++) {
            ExcelUtil excelData = getExcelData(excelName, i + 1);
            RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
            String portfolioId = apiController.getPortfolioId(selectedPortfolios.get(i));
            System.out.println("portfolioId = " + portfolioId);
            RegulatoryReportingQueries queries = new RegulatoryReportingQueries();
            List<Map<String, Object>> dbData = queries.getCompanyLevelOutput(portfolioId, "latest_data");
            for (Map<String, Object> row : dbData) {
                List<String> checkValues = new ArrayList<>(Arrays.asList("COMPANY_NAME", "VE_ID", "FACTSET_ID", "HIGH_IMPACT_CHK"));
                for (String data : checkValues) {
                    if (data == null) continue;
                    if (!excelData.searchData((row.get(data).toString()))) {
                        System.out.println("Data " + row.get(data).toString() + " is not found in the excel");
                        return false;
                    }
                }
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
    //column index is for highest column index in the excel file
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
        System.out.println("Sheet Loaded "+excelUtil.getLastRowNum()+" vs. "+excelUtil.columnCount(columnIndex));
        List<String> list = new ArrayList<>();
        for (int i = 0; i <= excelUtil.getLastRowNum(); i++) {
            for (int j = 0; j <= excelUtil.columnCount(columnIndex); j++) {
                if(excelUtil.getCellData(i,j) == null || excelUtil.getCellData(i,j).equals("")) continue;
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

    public boolean verifyPortfolioCoverageForExcel(String portfolioName) {
        RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
        String portfolioId = apiController.getPortfolioId(portfolioName);
        RegulatoryReportingQueries queries = new RegulatoryReportingQueries();
        Map<String, Object> dbData = queries.getReportingYearDetails(portfolioId).get(0);
        System.out.println("dbData.size() = " + dbData.size());
        int countOfAllEntities = queries.getNumberOfCompanies(portfolioId);
        System.out.println("countOfAllEntities = " + countOfAllEntities);
        //open Excel file
        String excelName = rrStatusPage_PortfoliosList.get(0).getText().replaceAll("ready", "").trim();
        System.out.println("Verifying reports content for sheet 1");
        ExcelUtil excelData = getExcelData(excelName, 1);
        int countOfEntitiesInExcel = excelData.rowCount() - 1;
        System.out.println("countOfEntitiesInExcel = " + countOfEntitiesInExcel);
        //find coverage percentage by rounding up
        int excelCoverage = (int) Math.ceil((double) countOfEntitiesInExcel / countOfAllEntities * 100);
        System.out.println("excelCoverage = " + excelCoverage);
        int dbCoverage = Integer.parseInt(dbData.get("SFDR_COVERAGE").toString());
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
        String[] sheets = {"Green Investment Ratio Template","Underlying Data - Overview","Underlying Data - Activities","Definitions","Disclaimer"};
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
                if(template.getCellData(i,j).equals("")||template.getCellData(i,j).equals("FUTURE RELEASE")){
                    continue;
                } else if (!excelData.contains(template.getCellData(i,j).trim())) {
                    System.out.println(template.getCellData(i,j));
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
        for(String title: template.getColumnsNames()){
            if(!excelData.getColumnsNames().contains(title)){
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
        for(String title: template.getColumnsNames()){
            if(!excelData.getColumnsNames().contains(title)){
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
                if(template.getCellData(i,j)==null||template.getCellData(i,j).equals("")){
                    continue;
                } else if (!excelData.contains(template.getCellData(i,j).trim())) {
                    System.out.println(template.getCellData(i,j));
                    System.out.println("Row = " + (i+1) + " Column = " + (j+1));
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
        for(String data: getExcelDataList(excelName, "Disclaimer")){
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
        for(String sentence: templateData){
            if (!excelData.contains(sentence)) {
                System.out.println(sentence);
                return false;
            }
        }
        return true;
    }

    public boolean verifySFDRCompanyOutput(List<String> selectedPortfolios) {
        for(String portfolioName: selectedPortfolios){
            RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
            String portfolioId = apiController.getPortfolioId(portfolioName);
            RegulatoryReportingQueries queries = new RegulatoryReportingQueries();
            List<Map<String, Object>> dbData = queries.getSFDRCompanyOutput(portfolioId, DateTimeUtilities.getCurrentYear(-1));
            System.out.println("dbData.size() = " + dbData.size());

            //open Excel file
            String excelName = rrStatusPage_PortfoliosList.get(0).getText().replaceAll("ready", "").trim();
            List<String> excelData = getExcelDataList(excelName, (selectedPortfolios.indexOf(portfolioName)+1)+"_"+portfolioName);
            for(Map<String, Object> row: dbData){
                for(String key: row.keySet()){
                    if(row.get(key)==null)continue;
                    if(!excelData.contains(row.get(key).toString())){
                        System.out.println(row.get(key));
//                        return false;
                    }
                }
            }
        }
        return true;
    }
}
