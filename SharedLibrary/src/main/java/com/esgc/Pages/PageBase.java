package com.esgc.Pages;

import com.esgc.Reporting.CustomAssertion;
import com.esgc.TestBase.TestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.ExcelUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This abstract class will be extended by Page classes
 * WebElements/locators, methods that are common to each page of the application will be stored here
 */

/* NOTE: Additional annotations

        - '@FindBys'
       - use an array of @FindBy in sequence to find an element {@FindBy(...),@FindBy(...)}
        - '@FindAll'
       - use an array of @FindBy and return all matching items
       - if an Element never changes in the DOM then we can cache it

 */
public abstract class PageBase {


    protected CustomAssertion assertTestCase = new CustomAssertion();


    //Menu Tab on top left corner
    @FindBy(xpath = "//li[@role='menuitem']")
    public WebElement menu;

    @FindBy(xpath = "//li[@role='menuitem' and not(@data-darkreader-inline-color)]")
    public List<WebElement> menuList;

    @FindBy(xpath = "//li[text()='Portfolio Analysis']")
    public WebElement portfolioAnalysis;

    @FindBy(xpath = "//li[text()='Portfolio Selection/Upload']")
    public WebElement portfolioSettings;

    @FindBy(xpath = "(//table[@id='table-id'])[1]/tbody/tr/td[1]")
    public List<WebElement> portfolioSettingsCompanies;

    @FindBy(xpath = "(//table[@id='table-id'])[1]/tbody/tr/td[2]")
    public List<WebElement> portfolioSettingsInvestmentPercentage;

    @FindBy(xpath = "//div[@id='entity-filter'] ")
    public WebElement portfolioSettingsDropDown;
    @FindBy(xpath = "//li[@id='entity-filter_Show_20_largest_investments_id']")
    public WebElement portfolioSettingsLargest20Investment;

    @FindBy(xpath = "//li[@id='entity-filter_Show_10_largest_investments_id']")
    public WebElement portfolioSettingsLargest10Investment;

    @FindBy(xpath = " //div[@id='portfolio-drawer-test-id']/div[3]/div/div/div/div/span[3]")
    public WebElement portfolioSettingsMoreCompanies;


    @FindBy(xpath = "//span[@title='Sample Portfolio']")
    public WebElement samplePortfolio;

    @FindBy(xpath = "//span[@title='SamplePortfolioToDelete']")
    public WebElement samplePortfolioToDelete;

    @FindBy(xpath = "(//button[@id='button-button-test-id-1'])[2]")
    public WebElement deleteButton;

    @FindBy(xpath = "//div[@role='dialog']/div[2]/div/div")
    public WebElement confirmPortfolioDeletePopupHeader;

    @FindBy(xpath = "//div[contains(text(),'Yes, Delete')]")
    public WebElement confirmPortfolioDeleteYesButton;

    @FindBy(xpath = "//div[contains(text(),'No, Cancel')]")
    public WebElement confirmPortfolioDeleteCancelButton;
    @FindBy(xpath = "//div[text()='Portfolio Management']")
    public WebElement header_portfolioManagement;

    @FindBy(xpath = " (//button[@id='button-holdings'])[1]/span/div")
    public WebElement getPortfolioNameFromDashboard;

    @FindBy(xpath = "//span[@title='Sample Portfolio']")
    public WebElement samplePortfolioTitle;

    @FindBy(xpath = "//a[@id='link-upload']")
    public WebElement portfolioReUpload;
    @FindBy(xpath = "(//h2[normalize-space()='Import Portfolio'])[1]")
    public WebElement importPortfolioPopUp;
    @FindBy(xpath = "//h2[normalize-space()='Import Portfolio']//*[name()='svg']")
    public WebElement closePortfolioPopUp;


    @FindBy(xpath = "//body/div[@role='presentation']/div/div/div/header/div[2]/div[2]")
    public WebElement portfolioNameWithStar;

    @FindBy(xpath = "  //input[@value='Sample Portfolio']")
    public WebElement portfolioTextBoxGetValue;

    @FindBy(xpath = "  //div[contains(text(),'Show 10 largest investments')]")
    public WebElement portfolioDropDownMenu;

    @FindBy(xpath = "//div[text()='Portfolio Management']/following-sibling::div/a[@id='link-upload']")
    public WebElement link_UploadNew;

    @FindBy(xpath = "//div[@role='presentation']//div//div//div//header/div[2]/div[1]")
    public WebElement portfolioDescription;

    @FindBy(xpath = "//table[@id='table-id']/thead/tr")
    public WebElement portfolioCompanyColumnNames;

    @FindBy(xpath = " //table[@id='table-id-1']/tbody/tr")
    public WebElement portfolioFooterText;

    @FindBy(xpath = "(//tbody)[6]/tr/td[1]")
    public List<WebElement> portfolioEntityList;

    @FindBy(xpath = "//div[@role='dialog']/div/div/li")
    public WebElement portfolioEntityName;

    @FindBy(css = "svg > path[fill-rule='evenodd'][fill='#b8b8b8']")
    public WebElement backArrow;

    @FindBy(xpath = "//span[text()='Portfolio Name']")
    public WebElement span_POrtfolioName;

    @FindBy(xpath = "//span[text()='Portfolio Name']/../../../following-sibling::div")
    public List<WebElement> portfolioSettings_portfolioList;


    @FindBy(xpath = "//span[text()='Portfolio Name']/../following-sibling::div/span")
    public WebElement span_UploadDate;

    @FindBy(xpath = "")
    public List<WebElement> listDemo;

    @FindBy(tagName = "iframe")
    public WebElement iframe;

    @FindBy(tagName = "//*[@id='cardInfo_box']//..//div[@aria-busy]")
    public WebElement LoadMask;

    @FindBy(tagName = "//*[@aria-busy]")
    public List<WebElement> allLoadMasks;

    @FindBy(xpath = "(//button[@id='button-holdings']/../../../../../div/div/div/div/div/div)[2]")
    public WebElement summaryPortfolioName;

    @FindBy(xpath = "//span[text()='About Climate Risk']")
    public WebElement summaryClimateRisk;


    //=================================================
    //Add portfolio button in portfolio selection modal
    @FindBy(xpath = "//a[text()='Upload Portfolio']")
    public WebElement uploadPortfolioButton;

    @FindBy(id = "textbox-search-test-id-2")
    public WebElement searchBar;

    @FindBy(id = "mini-0")
    public WebElement selectedPortfolio;

    @FindBy(xpath = "")
    public List<WebElement> portfolioList;

    //Portfolio filter (PortfolioSelection, ResearchLines, Regions, Sectors, Date, Benchmark dropdowns)

    @FindBy(id = "button-holdings")
    public WebElement portfolioSelectionButton;

    @FindBy(id = "RegSector-test-id-1")
    public WebElement regionsDropdown;

    @FindBy(id = "SectorsDropdown-test-id-1")
    public WebElement sectorsDropdown;

    @FindBy(id = "AsOfDateDropdown-test-id-1")
    public WebElement asOfDateDropdown;

    //@FindBy(id = "BenchmarkDropdown-test-id-1")
    @FindBy(id = "BenchmarkDropdown-id")
    public WebElement benchmarkDropdown;

    @FindBy(id = "RegSector-test-id-1")
    public WebElement filtersDropdown;

    @FindBy(xpath = "//div[contains(@class, 'MuiPopover-paper')]")
    public WebElement filtersDropdownPopup;

    @FindBy(xpath = "//div[contains(@heap_filter,'month_')]")
    public List<WebElement> monthsInAsOfDate;

    @FindBy(xpath = "//table//tr[@heap_id='event']")
    public List<WebElement> controversies;

    @FindBy(xpath = "//button/span[text()='Last 60 Days']")
    public WebElement last60DaysFilterButton;

    @FindBy(xpath = "//div[@id='button-controversy-test-id-1']/button[2]")
    public WebElement particularMonthFilterButtonOnPortfolioMonitoring;

    @FindBy(xpath = "//span[@class='MuiButton-label'][text()='Critical']")
    public WebElement criticalTab;

    @FindBy(xpath = "//span[@class='MuiButton-label'][text()='Not Critical']")
    public WebElement notCriticalTab;

    @FindBy(xpath = "//span[@class='MuiButton-label'][text()='All']")
    public WebElement allTab;

    @FindBy(xpath = "//div[text()='Regions']")
    public WebElement regionsFilter;

    @FindBy(xpath = "//div[text()='Industry Sectors']")
    public WebElement sectorsFilter;

    @FindBy(xpath = "//div[text()='As Of Date']")
    public WebElement asOfDateFilter;

    @FindBy(xpath = "//*[contains(@id,'list-asOfDate')]/div")
    public List<WebElement> asOfDateValues;

    @FindBy(xpath = "//div[@id='updates_and_current_leaders_laggards']/div")
    public WebElement updatesAndLeadersAndLaggardsHeader;

    @FindBy(id = "Nav-test-id-1")
    public WebElement ResearchLineDropdown;

    @FindBy(id = "portfolioanalysis-reportnavigation-test-id")
    public WebElement researchLineOptions;

    @FindBy(xpath = "//ul[@id='portfolioanalysis-reportnavigation-test-id']//span[text()]")
    public List<WebElement> researchLineDropdownElements;

    @FindBy(xpath = "//div[@id='list-region']/div[@role='button']")
    public List<WebElement> regionList;

    @FindBy(xpath = "//div[@id='list-sector']/div[@role='button']")
    public List<WebElement> sectorList;

    //============= Portfolio Selection Modal Elements


    @FindBy(id = "ExportDropdown-test-id-1")
    public WebElement exportDropdown;

    @FindBy(xpath = "//input[@type='radio']")
    public List<WebElement> portfolioRadioButtonsList;

    @FindBy(xpath = "div[@title='']")
    public List<WebElement> portfolioNameList;

    @FindBy(xpath = "//div[@title='']")
    public List<WebElement> portfoliosUpdatesList;

    @FindBy(xpath = "//div[starts-with(@id,'mini')]")
    public List<WebElement> portfolioCards;

    @FindBy(xpath = "//div[starts-with(@id,'mini')]//input")
    public List<WebElement> portfolioSelectionRadioButtons;

    @FindBy(xpath = "//*[@id=\"portfolio-search-test-id\"]//div[@role='dialog']")
    public WebElement SelectionModalPopup;

    @FindBy(xpath = "//header[@id=\"prop-search\"]")
    public WebElement SelectionModalPopupSearchBar;

    @FindBy(xpath = "//header[@id=\"prop-search\"]//input ")
    public WebElement SelectionModalPopupSearchBarInputField;

    @FindBy(xpath = "//*[@id='table-id']")
    public List<WebElement> tableId;

    @FindBy(xpath = "//a[contains(text(),'more companies ranked in')]")
    public List<WebElement> moreCompaniesRankedIn;

    // =======================Search Box elements=======
    @FindBy(xpath = "//input[@id='platform-search-test-id']")
    public WebElement SearchInputBox;

    @FindBy(xpath = "//div[@class=\"MuiToolbar-root MuiToolbar-regular\"]//*[local-name()='svg' and @class=\"MuiSvgIcon-root\"]")
    public WebElement closeIcon;

    @FindBy(xpath = "//button/span[text()='View By Sector']")
    public WebElement viewBySectorButton;

    @FindBy(xpath = "//button/span[text()='View By Region']")
    public WebElement viewByRegionButton;

    @FindBy(xpath = "//div[text()='Companies in ']/../../../../../..//table/tbody/tr/td[5]")
    public List<WebElement> viewByRegionSectorColumnValues;

    @FindBy(xpath = "//span[@heap_id='view-panel']")
    public WebElement viewCompaniesAndInvestmentsLink;
    @FindBy(xpath = "//table[@id='viewcomapnies-0-Basic_Materials']/tbody/tr/td[5]")
    public List<WebElement> viewRegionCountryColumnValues;

    @FindBy(xpath = "//*[@id='dashboard-export-button-test-id' or @id='ExportDropdown-test-id-1']")
    public WebElement exportCompaniesButton;

    @FindBy(xpath = "//*[contains(@id,'ExportDropdown-test-id-1')][@data-value='pdf']")
    public WebElement exportPdf;

    // =======================Search Box elements for portfolio Analysis page =======
    @FindBy(xpath = "//div[@heap_id='search']//*[name()='svg']")
    public WebElement searchIconPortfolioPage;

    @FindBy(xpath = "//div[@heap_id='search']//*[name()='svg']")
    List<WebElement> searchIconPortfolioPages;

    @FindBy(id = "platform-search-test-id")
    public WebElement searchBarOfPortfolio;

    @FindBy(id = "phyClimate-test-id")
    public WebElement physicalClimateHazards;

    @FindBy(xpath = "//div[.='Carbon Footprint']")
    public WebElement carbonFootprintTitle;

    @FindBy(xpath = "//span[@style='padding-left: 8px;']/..")
    public List<WebElement> carbonFootprintSubTitles;

    @FindBy(xpath = "//*[@id='carbonClimate-test-id']/div[2]/div/div[4]")
    public WebElement carbonFootprintUpdateDate;


    //===================================Physical Risk hAZAR for portfolio Analysis page

    @FindBy(xpath = "//td[.='Wildfires']")
    public WebElement wildfiresText;

    @FindBy(xpath = "//th[.='Physical Risk Hazards: Operations Risk']")
    public WebElement physicalRiskHazardsOperationsRisk;

    @FindBy(xpath = "//th[.='Physical Risk Hazards: Operations Risk']/../../../tbody/tr/td")
    public List<WebElement> subtextsOfPhysicalRiskHazardsOperationsRisk;

    @FindBy(xpath = "//th[.='Facilities Exposed']")
    public WebElement textOfFacilitiesExposed;

    @FindBy(xpath = "//th[.='Facilities Exposed']/../following-sibling::*/th//span")
    public List<WebElement> textsOfMeasures;


    @FindBy(xpath = "//input[@id='textbox-test-id-1']")
    public WebElement input_PortfolioName;

    @FindBy(xpath = "//div[@id='entity-filter']")
    public WebElement ListboxInPorfolioDrawer;
    @FindBy(xpath = "(//table[@id='table-id'])[3]/thead")
    public WebElement tableHeaderPorfolioDrawer;

    @FindBy(xpath = "//div[text()='Name Saved']")
    public WebElement successMessageForNameSaved;
    //=================================================

    @FindBy(xpath = "//span[@heap_id='view-panel' and contains(text(),'Coverage:')]")
    public WebElement CoverageLink;

    protected WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(20));
    protected Actions actions = new Actions(Driver.getDriver());
    private String finalStringToCheck;

    //====================================


    /**
     * Page Factory Design Pattern is implemented to simplify the process of creating WebElements
     * which is initialized only in PageBase class.
     */
    public PageBase() {

        PageFactory.initElements(Driver.getDriver(), this);

    }

    /**
     * Method for switching to iframe (if there is only 1 iframe)
     */
    public void switchToIframe() {

        Driver.getDriver().switchTo().frame(iframe);
    }

    /**
     * Method for exiting from iframe
     */
    public void exitFromIframe() {

        Driver.getDriver().switchTo().defaultContent();
    }
    //=================Common Methods for Navigation Tabs on Top of the Page ============================================

    /*
     * This method will verify if Regions Sections and As Of Date drop down
     */
    public boolean isFiltersDropdownDisplayed() {
        try {
            return filtersDropdown.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isFiltersDropdownPopupDisplayed() {
        try {
            return filtersDropdownPopup.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
    This is to verify the date format is correct
     */
    public boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }


    /*
     * This method will verify if Regions Sections and As Of Date drop down
     */
    public boolean isRegionsSectionAndAsOfDateDropdownDisplayed() {
        try {
            return filtersDropdown.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * This method return the data for  Regions Sections and As Of Date drop down
     */
    public String getRegionsSectionAndAsOfDateDropdownSelectedValue() {
        try {
            return filtersDropdown.getText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * This method will verify if Updates and Leaders and Laggards Title is visible
     */
    public boolean isUpdatesAndLeadersAndLaggardsHeaderDisplayed() {
        try {
            BrowserUtils.waitForVisibility(updatesAndLeadersAndLaggardsHeader, 5);
            return updatesAndLeadersAndLaggardsHeader.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
     * This method will click on Regions Sections and As Of Date drop down
     */
    public void clickRegionsSectionAndAsOfDateDropdown() {
        try {
            if (isRegionsSectionAndAsOfDateDropdownDisplayed()) {
                wait.until(ExpectedConditions.visibilityOf(filtersDropdown)).click();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getLastUpdatedDateContainsSearchKeyWord(String searchKeyword) {
        String lastUpdateXpath = "//span[@title='" + searchKeyword + "']/../following-sibling::div/span";
        String lastUpdatedDate = Driver.getDriver().findElement(By.xpath(lastUpdateXpath)).getText();
        return lastUpdatedDate;
    }

    public boolean verifyMessage(String section, String message) {
        try {
            //BrowserUtils.scrollTo(Driver.getDriver().findElement(By.xpath("//div/div/div[3]/main/div/div[1]/div[3]/div[2]/div[1]/div")));
            String xpath = "//*[contains(text(),'" + section + "')]/../..//div[contains(text(),'" + message + "')]";
            System.out.println("xpath = " + xpath);
            WebElement verifyMessage = Driver.getDriver().findElement(By.xpath(xpath));
            return verifyMessage.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyMessageTransitionRisk(String section, String message) {
        try {
            String xpath = "//*[contains(text(),'" + section + "')]/../../..//div[text()='" + message + "']";
            return Driver.getDriver().findElement(By.xpath(xpath)).isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyMessage(String section, String message, int noOfOccurences) {
        try {
            String xpath = "//*[text()='" + section + "']/../..//div[text()='" + message + "']";
            System.out.println("xpath = " + xpath);
            return (Driver.getDriver().findElements(By.xpath(xpath)).size() == noOfOccurences);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void selectLastAvailableMonthInAsOfDate() {
        try {
            monthsInAsOfDate.get(monthsInAsOfDate.size() - 1).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectAsOfDateWithIndex(int index) {
        try {
            monthsInAsOfDate.get(index - 1).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectRegion(String region) {
        BrowserUtils.wait(1);
        WebElement element = Driver.getDriver().findElement(By.xpath("//div[@heap_filter='region_" + region + "']"));
        BrowserUtils.waitForClickablility(element, 20).click();
    }

    public void selectSector(String sector) {
        Driver.getDriver().findElement(By.xpath("//div[@heap_filter='sector_" + sector + "']")).click();
    }

    public void selectAsOfDate(String month) {
        Driver.getDriver().findElement(By.xpath("//div[@heap_filter='month_" + month + "']")).click();
    }


    /**
     * Click method for Menu Tab on top left corner
     */
    public void clickMenu() {
        wait.until(ExpectedConditions.visibilityOf(menu));
        BrowserUtils.clickWithJS(menu);
    }

    /**
     * Checks if Research Line title is Displayed
     *
     * @param page - name of research line (Physical Risk Management, OperationRisk etc.)
     * @return true - if not displayed returns false
     */

    public boolean checkIfPageTitleIsDisplayed(String page) {
        try {
            System.out.println(page + " Sub Title Check");
            WebElement subtitle = Driver.getDriver().findElement(By.xpath("//div[text()='" + page + "']"));
            return wait.until(ExpectedConditions.visibilityOf(subtitle)).isDisplayed();
        } catch (Exception e) {
            System.out.println("Failed");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if Research Line title is Displayed next to portfolio analysis
     *
     * @param page - name of research line (Physical Risk Management, OperationRisk etc.)
     * @return true - if not displayed returns false
     */

    public boolean checkIfResearchLineTitleIsDisplayed(String page) {
        try {
            System.out.println(page + " Sub Title Check");
            WebElement subtitle = Driver.getDriver().findElement(By.xpath("//div[contains(@class,'MuiBox-root')]/div[text()='" + page + "']"));
            String portfolioName = getSelectedPortfolioNameFromDropdown();
            return wait.until(ExpectedConditions.visibilityOf(subtitle)).getText().equals(portfolioName + "\n: " + page);
        } catch (Exception e) {
            System.out.println("Failed");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method check if Page Description displayed correctly.
     * Also check as content.
     *
     * @return - true if description not displayed return false
     */
    public String checkIfDescriptionDisplayed(String description) {
        String result = "";
        try {
            System.out.println("Check if description is displayed");
            WebElement descriptionOfPage = Driver.getDriver().findElement(By.xpath("//*[@id=\"summary_box\"]"));
            result = wait.until(ExpectedConditions.visibilityOf(descriptionOfPage)).getText();
            System.out.println("TEST PASS");
            return result;
        } catch (Exception e) {
            System.out.println("Description is not displayed");
            e.printStackTrace();
            return result;
        }
    }

    /**
     * Clicks the portfolio selection button on Page to select or upload a portfolio
     */
    public void clickPortfolioSelectionButton() {
        wait.until(ExpectedConditions.elementToBeClickable(portfolioSelectionButton)).click();
    }

    public void clickResearchLineDropdown() {
        wait.until(ExpectedConditions.elementToBeClickable(ResearchLineDropdown)).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(researchLineDropdownElements));
    }

    public String getSelectedPortfolioNameFromDropdown() {
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(portfolioSelectionButton, "Loading...")));
        return portfolioSelectionButton.getText();
    }


    public void navigateToPageFromMenu(String page) {
        clickMenu();
        // Dynamic xpath - Helps us to pass page names "Dashboard", "Portfolio Analysis"
        String pageXpath = "//li[text()='" + page + "']";
        WebElement pageElement = Driver.getDriver().findElement(By.xpath(pageXpath));
        wait.until(ExpectedConditions.elementToBeClickable(pageElement)).click();
    }

    /**
     * Method for navigation through research lines in the Portfolio Analysis page
     * e.g. Carbon Footprint, Operations Risk, Market Risk...
     * The method navigates to a specific research line by page parameter
     *
     * @param page
     */
    public void navigateToResearchLine(String page) {
        navigateToPageFromMenu("Portfolio Analysis");
        selectResearchLineFromDropdown(page);
        // clickFiltersDropdown();
        // selectOptionFromFiltersDropdown("as_of_date", "April 2021");
        // closeFilterByKeyboard();
        TestBase.test.info("User is on " + page + " page");
    }

    public void navigateToResearchLineByIndex(int index) {
        navigateToPageFromMenu("Portfolio Analysis");
        selectResearchLineFromDropdownByIndex(index);
    }

    public void clickMoreCompaniesDrillDown(String researchLine) {
        switch (researchLine) {
            case "Physical Risk Hazards":
            case "Operations Risk":
            case "Market Risk":
            case "Supply Chain Risk":
                WebElement pageElement = Driver.getDriver().findElement(By.partialLinkText("more companies updated as of"));
                wait.until(ExpectedConditions.elementToBeClickable(pageElement)).click();
                break;

            case "Physical Risk Management":
            case "Carbon Footprint":
            case "Brown Share Assessment":
            case "Energy Transition Management":
            case "TCFD Strategy":
            case "Green Share Assessment":
                WebElement hrefElement = Driver.getDriver().findElement(By.partialLinkText("more companies updated in"));
                wait.until(ExpectedConditions.elementToBeClickable(hrefElement)).click();
                break;

            default:
                System.out.println("Invalid research line selected");
        }
    }

    /**
     * Method for navigation through research lines in the Portfolio Analysis page
     * e.g. Carbon Footprint, Operations Risk, Market Risk...
     * The method navigates to a specific research line by page parameter
     *
     * @return
     */
    public List<String> getAvailableResearchLines() {
        clickResearchLineDropdown();
        BrowserUtils.wait(2);
        String pageXpath = "//ul[@id='portfolioanalysis-reportnavigation-test-id']//span/span";
        return Driver.getDriver().findElements(By.xpath(pageXpath))
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    /**
     * Select a research line from dropdown for data analysis in dashboard
     * e.g. Carbon Footprint, Operations Risk, Market Risk...
     *
     * @param page
     */
    public void selectResearchLineFromDropdown(String page) {
        // if(!BrowserUtils.isElementVisible(researchLineOptions,3))
        try {
            clickResearchLineDropdown();
            // Dynamic xpath - Helps us to pass page names "Operations Risk", "Market Risk"
            String pageXpath = "//ul[@id='portfolioanalysis-reportnavigation-test-id']//span[contains(text(),'" + page + "')]";
            WebElement pageElement = Driver.getDriver().findElement(By.xpath(pageXpath));
            wait.until(ExpectedConditions.elementToBeClickable(pageElement)).click();
        } catch (Exception e) {
            System.out.println("Couldn't find " + page);
            Actions a = new Actions(Driver.getDriver());
            a.sendKeys(Keys.ESCAPE).build().perform();
        }
    }


    public void selectResearchLineFromDropdownByIndex(int index) {
        // if(!BrowserUtils.isElementVisible(researchLineOptions,3))
        clickResearchLineDropdown();
        // Dynamic xpath - Helps us to pass page names "Operations Risk", "Market Risk"
        String pageXpath = "//ul[@id='portfolioanalysis-reportnavigation-test-id']//span[text()]";
        WebElement pageElement = Driver.getDriver().findElements(By.xpath(pageXpath)).get(index);
        wait.until(ExpectedConditions.elementToBeClickable(pageElement)).click();
    }


    //================Check if portfolio filter dropdowns displayed:

    //==============Portfolio Name , Regions, Sectors, As Of Date, Benchmark

    public boolean checkIfPortfolioNameDropdownIsDisplayed() {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(portfolioSelectionButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkIfRegionsDropdownIsDisplayed() {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(regionsDropdown)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkIfSectorsDropdownIsDisplayed() {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(sectorsDropdown)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkIfAsOfDateDropdownIsDisplayed() {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(asOfDateDropdown)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkIfBenchmarkDropdownIsDisplayed() {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(portfolioSelectionButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    //================ Select Options from Filters dropdown ========================================

    /**
     * User can see portfolio count in search bar in portfolio selection modal
     *
     * @return that many portfolios imported into user's account
     */
    public int getPortfolioCountForUser() {
        clickPortfolioSelectionButton();
        wait.until(ExpectedConditions.visibilityOfAllElements(portfolioCards));
        int count = portfolioCards.size();
        portfolioCards.get(0).click();
        return count;
    }

    /**
     * User can see portfolio names in portfolio selection modal
     *
     * @return name of all portfolios imported into user's account
     */
    public List<String> getPortfolioNames() {
        if (!checkIfSelectionModalPopupIsDisplayed()) clickPortfolioSelectionButton();
        wait.until(ExpectedConditions.visibilityOfAllElements(portfolioCards));
        List<String> portfolioNameList =
                portfolioCards.stream().map(e -> e.getText().trim().substring(0, e.getText().length() - 10))
                        .filter(e -> !e.equals(""))
                        .collect(Collectors.toList());
        portfolioCards.get(0).click();
        BrowserUtils.wait(3);
        return portfolioNameList;
    }

    /**
     * Selects the first portfolio which is already selected to close modal without trigger loading data
     */
    public void selectFirstPortfolioFromPortfolioSelectionModal() {
        clickPortfolioSelectionButton();
        wait.until(ExpectedConditions.visibilityOfAllElements(portfolioCards)).get(0).click();
    }

    /**
     * Selects random portfolio from portfolio selection modal
     */
    public void selectRandomPortfolioFromPortfolioSelectionModal() {
        if (!checkIfSelectionModalPopupIsDisplayed()) clickPortfolioSelectionButton();
        List<WebElement> list =
                wait.until(ExpectedConditions.visibilityOfAllElements(portfolioCards))
                        .stream().skip(1).collect(Collectors.toList());
        Collections.shuffle(list);
        list.get(0).click();
    }

    /**
     * Selects random portfolio from portfolio selection modal
     */
    public String selectRandomPortfolioFromPortfolioSelectionModalAndGetPortfolioName() {
        clickPortfolioSelectionButton();
        List<WebElement> list =
                wait.until(ExpectedConditions.visibilityOfAllElements(portfolioCards))
                        .stream().skip(1).collect(Collectors.toList());
        Collections.shuffle(list);
        String name = new String(list.get(0).getText());
        list.get(0).click();
        return name;
    }

    /**
     * Selects portfolio by name from portfolio selection modal
     */
    public void selectPortfolioByNameFromPortfolioSelectionModal(String portfolioName) {
        clickPortfolioSelectionButton();
        wait.until(ExpectedConditions.visibilityOfAllElements(portfolioCards));
        List<WebElement> list =
                portfolioCards.stream().filter(e -> e.getText().substring(0, e.getText().length() - 11).equals(portfolioName))
                        .collect(Collectors.toList());
        list.get(list.size() - 1).click();
    }

    /**
     * Selects sample portfolio (default portfolio for all users) from portfolio selection modal
     */
    public void selectSamplePortfolioFromPortfolioSelectionModal() {
        wait.until(ExpectedConditions.visibilityOfAllElements(portfolioSelectionButton));
        if (portfolioSelectionButton.getAttribute("title").equals("Sample Portfolio")) return;
        clickPortfolioSelectionButton();
        wait.until(ExpectedConditions.visibilityOfAllElements(portfolioCards));
        List<WebElement> list =
                portfolioCards.stream().filter(e -> e.getText().substring(0, e.getText().length() - 11).equals("Sample Portfolio"))
                        .collect(Collectors.toList());
        list.get(list.size() - 1).click();
    }

    /**
     * This method selects random option under one dropdown.
     * Dropdown name should be provided to use it
     * ex: Select random portfolio
     * dropdown = portfolio_name
     * ex2: Select random date
     * dropdown = as_of_date
     *
     * @param dropdown - Dropdown Name should be one of these:
     *                 -portfolio_name
     *                 -regions
     *                 -sectors
     *                 -as_of_date
     *                 -benchmark
     */
    public String selectRandomOptionFromFiltersDropdown(String dropdown) {

        WebElement element = null;
        String elementTitle = null;
        switch (dropdown) {
            case "portfolio_name":
                return selectRandomPortfolioFromPortfolioSelectionModalAndGetPortfolioName();
            case "regions":
                element = regionsDropdown;
                elementTitle = "list-region";
                break;
            case "sectors":
                element = sectorsDropdown;
                elementTitle = "list-sector";
                break;
            case "as_of_date":
                element = asOfDateDropdown;
                elementTitle = "list-asOfDate";
                break;
            case "benchmark":
                element = benchmarkDropdown;
                elementTitle = "Select Benchmark";
                break;
            default:
                System.out.println("You provided wrong dropdown name for this method: selectRandomOptionFromDropdown()");
        }
        //click on menu element
        List<WebElement> options = Driver.getDriver().findElements(By.xpath("//div[contains(@id,'" + elementTitle + "')]//span[text()]"));

        if (elementTitle.equals("Select Benchmark")) {
            options = Driver.getDriver().findElements(By.xpath("//ul[@role='listbox']//..//li[text()]"));
        }
        //Generate random number
        Random random = new Random();

        //Should not select first element because it is already selected option
        //that's why we add 1
        int randomIndex = random.nextInt(options.size() - 1) + 1;

        //Should not select last element for portfolio name
        // because it is upload portfolio option
        //that's why subtract 1
        if (elementTitle.equals("Select Benchmark")) {
            randomIndex = randomIndex - 1;
        }

        //select random option from picked dropdown
        try {
            actions.moveToElement(options.get(randomIndex)).pause(1000).click(options.get(randomIndex)).pause(3000).build().perform();
            // return options.get(randomIndex).getText();
            return "Success";
        } catch (Exception e) {
            System.out.println("Could not click option under dropdown");
            e.printStackTrace();
        }
        return null;

    }

    /**
     * This method selects first option in one dropdown.
     * Dropdown name should be provided to use it
     * ex: Select first option under benchmark to deactivate benchmark
     * dropdown = benchmark
     *
     * @param dropdown - Dropdown Name should be one of these - Filters Options:
     *                 portfolio_name
     *                 regions
     *                 sectors
     *                 as_of_date
     *                 benchmark
     */
    public void selectFirstOptionFromFiltersDropdown(String dropdown) {

        WebElement element = null;
        String elementTitle = null;
        switch (dropdown) {
            case "portfolio_name":
                selectFirstPortfolioFromPortfolioSelectionModal();
                return;
            case "regions":
                element = regionsDropdown;
                elementTitle = "list-region";
                break;
            case "sectors":
                element = sectorsDropdown;
                elementTitle = "list-sector";
                break;
            case "as_of_date":
                element = asOfDateDropdown;
                elementTitle = "list-asOfDate";
                break;
            case "benchmark":
                element = benchmarkDropdown;
                elementTitle = "No Benchmark";
                break;
            default:
                System.out.println("You provided wrong dropdown name for this method: selectFirstOptionFromDropdown()");
        }
        //click on menu element
        List<WebElement> options = Driver.getDriver().findElements(By.xpath("//div[contains(@id,'" + elementTitle + "')]//span[text()]"));

        //select first option from picked dropdown
        try {
            actions.moveToElement(options.get(0)).pause(1000).click(options.get(0)).sendKeys(Keys.ESCAPE).build().perform();

        } catch (Exception e) {
            System.out.println("Could not click option under dropdown");
            e.printStackTrace();
        }
    }

    /**
     * This method selects provided option in one dropdown.
     * Dropdown name and option should be provided to use it
     * ex: select March 2021 in as_of_date dropdown
     * dropdown = as_of_date
     * option = March 2021
     *
     * @param dropdown - Dropdown Name should be one of these - Filters Options:
     *                 portfolio_name
     *                 regions
     *                 sectors
     *                 as_of_date
     *                 benchmark
     * @param option   - should be a presented option under dropdown
     */
    public void selectOptionFromFiltersDropdown(String dropdown, String option) {

        WebElement element = null;
        String elementTitle = null;
        switch (dropdown) {
            case "portfolio_name":
                selectPortfolioByNameFromPortfolioSelectionModal(option);
                return;
            case "regions":
                element = regionsDropdown;
                elementTitle = "list-region";
                break;
            case "sectors":
                element = sectorsDropdown;
                elementTitle = "list-sector";
                break;
            case "as_of_date":
                element = asOfDateDropdown;
                elementTitle = "list-asOfDate";
                break;
            case "benchmark":
                element = benchmarkDropdown;
                elementTitle = "No Benchmark";
                break;
            default:
                System.out.println("You provided wrong dropdown name for this method: selectOptionFromDropdown()");
        }
        //click on menu element
        // actions.click(element).pause(4000).perform();

        //Get all options under dropdown
        // List<WebElement> options = Driver.getDriver().findElements(By.xpath("//li[contains(text(),'" + elementTitle + "')]/../li"));
        List<WebElement> options = Driver.getDriver().findElements(By.xpath("//div[contains(@id,'" + elementTitle + "')]//span[text()]"));

        //select provided option from picked dropdown
        try {
            for (WebElement each : options) {
                if (each.getText().equals(option)) {
                    actions.moveToElement(each).pause(1000).click(each).sendKeys(Keys.ESCAPE).build().perform();

                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Could not click option under dropdown");
            e.printStackTrace();
        }
    }

    /**
     * This method selects provided option in one dropdown.
     * Dropdown name and option should be provided to use it
     * ex: select March 2021 in as_of_date dropdown
     * dropdown = as_of_date
     * option = March 2021
     *
     * @param dropdown - Dropdown Name should be one of these - Filters:
     *                 portfolio_name
     *                 regions
     *                 sectors
     *                 as_of_date
     *                 benchmark
     */
    public List<String> getOptionsAsStringListFromFiltersDropdown(String dropdown) {
        WebElement element = null;
        String elementTitle = null;
        switch (dropdown) {
            case "portfolio_name":
                return getPortfolioNames();
            case "regions":
                element = regionsDropdown;
                elementTitle = "All Regions";
                break;
            case "sectors":
                element = sectorsDropdown;
                elementTitle = "All Sectors";
                break;
            case "as_of_date":
                element = asOfDateDropdown;
                elementTitle = "March 2021";
                break;
            case "benchmark":
                element = benchmarkDropdown;
                elementTitle = "No Benchmark";
                break;
            default:
                System.out.println("You provided wrong dropdown name for this method: getOptionsAsStringListFromDropdown()");
        }
        //click on menu element
        actions.click(element).pause(4000).perform();

        //Get all options under dropdown
        List<WebElement> options = Driver.getDriver().findElements(By.xpath("//li[contains(text(),'" + elementTitle + "')]/../li"));
        List<String> result = new ArrayList<>();

        //get options as string from picked dropdown
        options.forEach(each -> result.add(each.getText()));

        //click on page to close dropdown
        actions.click().pause(4000).perform();
        return result;
    }


    //====================  Download file methods
    /*
    method takes the download directory and the file name, which will check for the file name mentioned
    in the directory and will return 'True' if the document is
    available in the folder else 'false'. When we are sure of the file name, we can
    make use of this method to verify.
    --- Used for download template verification ESGCA-192
     */
    public String getDownloadedFileName() {

        File dir = new File(BrowserUtils.downloadPath());
        File[] dir_contents = dir.listFiles();
        Arrays.asList(dir_contents).stream().filter(e -> e.getName().startsWith("ESG portfolio import"));
        return dir_contents[0].getName(); //0 - only 1 folder
        //template name
    }

    public boolean isTemplateDownloaded() {
        File dir = new File(BrowserUtils.downloadPath());
        File[] dir_contents = dir.listFiles();

        return Arrays.asList(dir_contents).stream().filter(e -> e.getName().startsWith("ESG portfolio import")).findAny().isPresent();

    }

    public boolean isCompaniesAndInvestmentsExcelDownloaded() {
        File dir = new File(BrowserUtils.downloadPath());
        File[] dir_contents = dir.listFiles();
        LocalDateTime ldt = LocalDateTime.now();
        String date = DateTimeFormatter.ofPattern("MM_dd_yyyy", Locale.ENGLISH).format(ldt);
        return Arrays.asList(dir_contents).stream().filter(e -> e.getName().contains(date)).findAny().isPresent();
    }

    public int filesCountInDownloadsFolder() {
        File dir = new File(BrowserUtils.downloadPath());
        File[] dir_contents = dir.listFiles();
        return dir_contents.length;
    }

    public String getDownloadedCompaniesExcelFilePath() {
        File dir = new File(BrowserUtils.downloadPath());
        File[] listOfFiles = dir.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }

        String filePath = "";
        if (listOfFiles.length == 1)
            filePath = listOfFiles[0].getName();
        return dir + "/" + filePath;
    }

    public boolean isCompaniesAndInvestmentsPdfDownloaded() {
        File dir = new File(BrowserUtils.downloadPath());
        File[] dir_contents = dir.listFiles();
        return Arrays.asList(dir_contents).stream().filter(e -> e.getName().contains(".pdf")).findAny().isPresent();
    }

    /* deletes the download folder under resources after each test to
     * prevent saving the template after each time
     * used at ESGCA 192_287
     */
    public void deleteDownloadFolder() {
        try {
            File dir = new File(BrowserUtils.downloadPath());
            File[] dir_contents = dir.listFiles();

            for (int i = 0; i < dir_contents.length; i++) {
                if (dir_contents[i].isDirectory()) {
                    try {
                        FileUtils.deleteDirectory(dir_contents[i]);
                    } catch (IOException e) {
                        System.out.println("Folder is empty");
                        System.out.println("Failed");
                        e.printStackTrace();
                    }
                } else {
                    dir_contents[i].delete();
                }
            }
            dir.delete();
        } catch (Exception e) {
            System.out.println("No existing files in the Folder");
            // e.printStackTrace();
        }
    }

    /**
     * checks the file from a specific directory
     *
     * @returns file name after '.' so we get the file extension with this method
     */
    public String getDownloadedFile_Ext() {
        File dir = new File(BrowserUtils.downloadPath());
        File[] files = dir.listFiles();

        String fileName = files[0].getName();
        return fileName.substring(fileName.lastIndexOf("."));
        //get the extension
    }

    /**
     * The method reads the data from .csv file after defining column and row number
     *
     * @param rowNum
     * @param columnNum
     * @return data from csv file - String -e.g. ESGCA-317
     */
    public String getDataFromCSV(int rowNum, int columnNum) {
        String csvData = "";
        try (CSVParser parser = new CSVParser(new FileReader(BrowserUtils.templatePath()), CSVFormat.DEFAULT)) {
            List<CSVRecord> list = parser.getRecords();
            csvData = list.get(rowNum).get(columnNum);
            System.out.println("Read data: " + csvData);

        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return csvData;

    }

    /**
     * The method reads the data from .csv file after defining column and row number
     *
     * @param rowNum
     * @param columnNum
     * @return data from csv file - String -e.g. ESGCA-2092
     */
    public String getDataFromExportedFile(int rowNum, int columnNum, String researchLine) {
        String excelData = "";
        String sheetName = String.format("Summary %s", researchLine);
        if (researchLine.equals("ESG Assessment")) {
            sheetName = "Data - ESG";
        }
        ExcelUtil excelUtil = new ExcelUtil(BrowserUtils.exportPath(researchLine), sheetName);

        excelData = excelUtil.getCellData(rowNum, columnNum);
        // System.out.println("Number of Rows : "+excelUtil.rowCount());
        System.out.println("Read data: " + excelData);

        return excelData;

    }

    public String getFirstCellValueFromExportedFile(String text, String researchLine) {
        String excelData = "";
        String sheetName = String.format("Summary %s", researchLine);
        ExcelUtil excelUtil = new ExcelUtil(BrowserUtils.exportPath(researchLine), sheetName);

        excelData = excelUtil.searchFirstColumnCellData(text).toString();
        System.out.println("Read data: " + excelData);

        return excelData;

    }

    public void openSelectionModalPopUp() {
        clickPortfolioSelectionButton();
        BrowserUtils.wait(3);
        wait.until(ExpectedConditions.elementToBeClickable(uploadPortfolioButton));

    }


    public boolean checkIfSelectionModalPopupIsDisplayed() {
        try {
            return BrowserUtils.isElementVisible(SelectionModalPopupSearchBar, 3);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean validateTextOfPortfolioSearchField() {
        String placeholderText = SelectionModalPopupSearchBarInputField.getAttribute("placeholder");
        return placeholderText.startsWith("Search in ")
                && placeholderText.endsWith(" results");
    }

    public boolean checkIfSearchBarIsDisplayed() {
        try {
            return SelectionModalPopup.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void enterPortfolioNameInSearchBar(String portfolioName) {
        //wait.until(ExpectedConditions.visibilityOf(SelectionModalPopupSearchBarInputField));
        SelectionModalPopupSearchBarInputField.click();
        SelectionModalPopupSearchBarInputField.sendKeys(portfolioName);

        // SelectionModalPopupSearchBarInputField.sendKeys(portfolioName);
        BrowserUtils.wait(1);

    }

    public void selectAPortfolio(int index) {
        if (!checkIfSelectionModalPopupIsDisplayed()) clickPortfolioSelectionButton();
        portfolioCards.get(index).click();
        BrowserUtils.wait(2);

    }

    public boolean checkIfUploadPortfolioIsPrsent() {
        try {
            wait.until(ExpectedConditions.visibilityOf(uploadPortfolioButton));
            System.out.println("TEST PASSED");
            return uploadPortfolioButton.isDisplayed();
        } catch (Exception e) {
            System.out.println("FAILED");
            e.printStackTrace();
            return false;
        }

    }

    public void clickAwayinBlankArea() {
        Driver.getDriver().findElement(By.xpath("//body")).click();
        BrowserUtils.wait(2);
    }

    public void closeFilterByKeyboard() {
        new Actions(Driver.getDriver()).sendKeys(Keys.TAB).pause(2000).build().perform();
        //Driver.getDriver().findElement(By.xpath("//ul")).sendKeys(Keys.TAB);
        //BrowserUtils.wait(2);
    }

    public void pressESCKey() {
        new Actions(Driver.getDriver()).sendKeys(Keys.ESCAPE).pause(2000).build().perform();
    }

    public void clickCoordination(int x, int y) {
        new Actions(Driver.getDriver()).moveByOffset(x, y).pause(2000).click().pause(2000).build().perform();
//        JavascriptExecutor executor = (JavascriptExecutor) Driver.getDriver();
//        executor.executeScript("document.elementFromPoint(" + x + "," + y + ").click();");
    }

    public String getaPortfolioNameandSelectthePortfolio() {
        String PortfolioName = portfolioCards.get(1).getText();
        portfolioSelectionRadioButtons.get(1).click();
        BrowserUtils.wait(2);
        return PortfolioName;
    }

    public boolean checkIfLoadMaskIsDisplayed() {
        try {
            return LoadMask.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getListOfRegion() {
        wait.until(ExpectedConditions.visibilityOfAllElements(regionList));
        List<String> regionsList =
                regionList.stream().map(e -> e.getText())
                        .collect(Collectors.toList());
        return regionsList;
    }

    public List<String> getListOfSector() {
        wait.until(ExpectedConditions.visibilityOfAllElements(sectorList));
        List<String> SectorsList =
                sectorList.stream().map(e -> e.getText())
                        .collect(Collectors.toList());
        return SectorsList;
    }

    public void refreshCurrentWindow() {
        Driver.getDriver().navigate().refresh();
    }

    public List<String> getPortfolioUploadDates() {
        wait.until(ExpectedConditions.visibilityOfAllElements(portfolioCards));
        List<String> portfolioNameList =
                portfolioCards.stream().map(e -> e.getText().substring(e.getText().length() - 10, e.getText().length()))
                        .collect(Collectors.toList());
        portfolioCards.get(0).click();
        return portfolioNameList;
    }

    public String getClimateEntitlementBundleName(String researchLine) {
        switch (researchLine) {
            case "Physical Risk Hazards":
            case "Operations Risk":
            case "Market Risk":
            case "Supply Chain Risk":
            case "Physical Risk Management":
                return "Physical Risk";
            case "Carbon Footprint":
            case "Brown Share Assessment":
            case "Energy Transition Management":
                return "Transition Risk";
            case "TCFD Strategy":
            case "Green Share Assessment":
                return "Climate Governance & Strategy";
            default:
                return null;
        }
    }

    public void clickLast60DaysFilterButton() {
        wait.until(ExpectedConditions.elementToBeClickable(last60DaysFilterButton)).click();
    }

    public void clickMonthButtonOnPortfolioMonitoring() {
        wait.until(ExpectedConditions.elementToBeClickable(particularMonthFilterButtonOnPortfolioMonitoring)).click();
    }

    public void clickPortfolioMonitoringCriticalTab() {
        wait.until(ExpectedConditions.elementToBeClickable(criticalTab)).click();
    }

    public void clickPortfolioMonitoringNotCriticalTab() {
        wait.until(ExpectedConditions.elementToBeClickable(notCriticalTab)).click();
    }

    public void clickPortfolioMonitoringAllTab() {
        wait.until(ExpectedConditions.elementToBeClickable(allTab)).click();
    }

    public boolean verifyLast60DaysControversies() {

        try {
            SimpleDateFormat sdformat = new SimpleDateFormat("MMMM dd, yyyy");
            int count = Driver.getDriver().findElements(By.xpath("//span[text()='Portfolio Monitoring']/../following-sibling::div//tr/td[1]/span")).size();
            for (int i = 1; i <= count; i++) {
                String eventDate = Driver.getDriver().findElement(By.xpath("//span[text()='Portfolio Monitoring']/../following-sibling::div//tr[" + i + "]/td[1]/span")).getText();
                Date currentDate = new Date();
                long diff = currentDate.getTime() - sdformat.parse(eventDate).getTime();
                long diffDays = diff / (24 * 60 * 60 * 1000) % 30;

                if (diffDays > 60) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean verifyLastMonthControversies() {
        LocalDate now = LocalDate.now();
        LocalDate earlier = now.minusMonths(1);
        String lastMonth = earlier.getMonth().name();
        int count = Driver.getDriver().findElements(By.xpath("//span[text()='Portfolio Monitoring']/../following-sibling::div//tr/td[1]/span")).size();
        for (int i = 1; i <= count; i++) {
            String date = Driver.getDriver().findElement(By.xpath("//span[text()='Portfolio Monitoring']/../following-sibling::div//tr[" + i + "]/td[1]/span")).getText();
            System.out.println("Index: " + i + " Actual: " + date.toUpperCase() + " Expected to start with: " + lastMonth.toUpperCase());
            if (!date.toUpperCase().startsWith(lastMonth.toUpperCase()))
                return false;
        }
        return true;
    }

    public String[][] getControversies() {
        int rows = Driver.getDriver().findElements(By.xpath("//span[text()='Portfolio Monitoring']/../following-sibling::div//tr")).size();
        int cols = Driver.getDriver().findElements(By.xpath("//span[text()='Portfolio Monitoring']/../following-sibling::div//tr[1]/td")).size();
        String controversies[][] = new String[rows][cols];
        String xpath = "";
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                xpath = "//span[text()='Portfolio Monitoring']/../following-sibling::div//tr[" + i + "]/td[" + j + "]";
                controversies[i - 1][j - 1] = Driver.getDriver().findElement(By.xpath(xpath)).getText();
            }
        }
        return controversies;
    }

    public boolean compareArrays(String mainArray[][], String subArray[][]) {

        int temp = 0;
        for (int i = 0; i < subArray.length; i++) {
            boolean match = false;
            for (int j = temp; j < mainArray.length; j++) {
                System.out.println("Comparing " + i + "with " + j);
                if (Arrays.equals(subArray[i], mainArray[j])) {
                    for (int k = 0; k < subArray[i].length; k++) {
                        System.out.println(subArray[i][k] + "-->" + mainArray[j][k]);
                    }
                    match = true;
                    temp = j + 1;
                    break;
                }
            }
            if (!match) {
                return false;
            }
        }
        return true;
    }


    /**
     * To select an option in filters dropdown, you need to click on it to open filters
     * <p>
     * This method will click on Regions Sectors and As Of Date drop down
     */
    public void clickFiltersDropdown() {
        try {
            if (isFiltersDropdownDisplayed()) {
                wait.until(ExpectedConditions.visibilityOf(filtersDropdown)).click();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isRegionsFilterPresent() {
        return wait.until(ExpectedConditions.visibilityOf(regionsFilter)).isDisplayed();
    }

    public boolean isSectorFilterPresent() {
        return sectorsFilter.isDisplayed();
    }

    public boolean isAsOfDateFilterPresent() {
        return asOfDateFilter.isDisplayed();
    }

    public boolean verifyRegionValuesAndSort(ArrayList<String> expRegionValues) {
        List<WebElement> regions = Driver.getDriver().findElements(By.xpath("//div[@id='list-region']/div/div/span"));
        ArrayList<String> regionValues = new ArrayList<>();
        for (WebElement region : regions) {
            regionValues.add(region.getText());
        }
        return regionValues.equals(expRegionValues);
    }

    public boolean verifyDefaultSelectedSectorValue(String expSector) {
        String actualSelectedSector = Driver.getDriver().findElement(By.xpath("//div[@id='list-sector']/div[contains(@class,'Mui-selected')]/div/span")).getText();
        return actualSelectedSector.equals(expSector);
    }

    public boolean verifyDefaultSelectedRegionValue(String expRegion) {
        String actualSelectedRegion = Driver.getDriver().findElement(By.xpath("//div[@id='list-region']/div[contains(@class,'Mui-selected')]/div/span")).getText();
        return actualSelectedRegion.equals(expRegion);
    }

    public boolean verifyDefaultSelectedAsOfDateValue() {
        String actualSelectedAsOfDate = Driver.getDriver().findElement(By.xpath("//div[@id='list-asOfDate']/div[contains(@class,'Mui-selected')]/div/span")).getText();
        LocalDate now = LocalDate.now();
        LocalDate earlier = now.minusMonths(1);
        String month = earlier.getMonth().name();
        String year = String.valueOf(earlier.getYear());
        String expAsOfDate = month + " " + year;
        return actualSelectedAsOfDate.equalsIgnoreCase(expAsOfDate);
    }

    public boolean verifyAsOfDatesSort() {
        LocalDate earlier = LocalDate.now();
        for (int i = 1; i <= 17; i++) {
            String actDate = Driver.getDriver().findElement(By.xpath("(//div[@id='list-asOfDate']/div/div/span)[" + i + "]")).getText();
            earlier = earlier.minusMonths(1);
            String expDate = earlier.getMonth().name() + " " + earlier.getYear();
            if (!(actDate.equalsIgnoreCase(expDate)))
                return false;
        }
        return true;
    }

    public boolean verifySectorsSort() {
        List<WebElement> sectors = Driver.getDriver().findElements(By.xpath("//div[@id='list-sector']/div"));
        ArrayList<String> sectorValues = new ArrayList<>();
        for (WebElement sector : sectors) {
            sectorValues.add(sector.getText());
        }

        List copySectorValues = new ArrayList(sectorValues);
        Collections.sort(copySectorValues);
        return copySectorValues.equals(sectorValues);
    }

    /**
     * Clicks the 'Upload' button in portfolio selection modal
     */
    public void clickUploadPortfolioButton() {
        clickPortfolioSelectionButton();
        BrowserUtils.wait(5);
        wait.until(ExpectedConditions.elementToBeClickable(uploadPortfolioButton));
        BrowserUtils.clickWithJS(uploadPortfolioButton);
    }

    public void clickViewCompaniesAndInvestments() {
        wait.until(ExpectedConditions.elementToBeClickable(viewCompaniesAndInvestmentsLink)).click();
        BrowserUtils.wait(5);
    }

    public void selectViewBySector() {
        wait.until(ExpectedConditions.elementToBeClickable(viewBySectorButton)).click();
        BrowserUtils.wait(5);
    }

    public void selectViewByRegion() {
        BrowserUtils.wait(5);
        wait.until(ExpectedConditions.elementToBeClickable(viewByRegionButton)).click();
        BrowserUtils.wait(5);
    }

    public void verifyDrawerRegionCountryColumn(){
        List<String>expectedValues =Arrays.asList("EMEA","AMER","APAC");
        for(WebElement value :viewRegionCountryColumnValues){
            assertTestCase.assertTrue(expectedValues.contains(value.getText().substring(0,value.getText().indexOf("/"))));
        }
    }

    public boolean verifyRegion(String region) {
        return Driver.getDriver().findElement(By.xpath("//div[text()='" + region + "']")).isDisplayed();
    }

    public boolean verifyViewByRegionTableColumns(String columnName) {
        String xpath = "//th/span[contains(text(),'" + columnName + "')]";
        return Driver.getDriver().findElements(By.xpath(xpath)).size() == 3; //For 3 regions
    }


    public boolean verifyViewByRegionTableSectorColumnsValues(ArrayList<String> expectedSectorValues) {
        String columnValue = "";
        for (WebElement element : viewByRegionSectorColumnValues) {
            columnValue = element.getText();
            //System.out.println("Sector: "+columnValue);
            if (!expectedSectorValues.contains(columnValue)) {
                return false;
            }
        }
        return true;
    }

    public boolean isExportButtonEnabled() {
        try {
            return exportCompaniesButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickExportCompaniesButton() {
        BrowserUtils.scrollTo(exportCompaniesButton);
        wait.until(ExpectedConditions.elementToBeClickable(exportCompaniesButton)).click();
        BrowserUtils.wait(30);
    }

    public void clickExportCompaniesButtonInPortfolioAnalysis() {
        BrowserUtils.scrollTo(exportCompaniesButton);
        wait.until(ExpectedConditions.elementToBeClickable(exportCompaniesButton)).click();
        BrowserUtils.wait(3);
    /*    Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(exportPdf).pause(2000).click().pause(2000).build().perform();*/
        wait.until(ExpectedConditions.elementToBeClickable(exportPdf)).click();
        BrowserUtils.wait(30);
    }

    public void waitForDataLoadCompletion() {
        BrowserUtils.waitForInvisibility(allLoadMasks, 30);
        // wait.until(ExpectedConditions.invisibilityOfAllElements(allLoadMasks));
    }

    public boolean checkIfUpdatesAsOfModalWindowPresent() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(tableId));
            if (tableId.size() >= 3) {
                System.out.println("Updates Modal Window found. Now validating columns inside the table");

                for (int i = 0; i < tableId.size(); i++) {
                    List<WebElement> rows = tableId.get(i).findElements(By.xpath(".//thead/tr/th"));
                    if (rows.size() != 3) {
                        continue;
                    } else {
                        if (rows.get(0).getText() == "Company"
                                && (rows.get(1).getText() == "Score" || rows.get(1).getText() == "Score Range")
                                && rows.get(2).getText() == "% Investment")
                            System.out.println("Found table with Company, Score and % Investment");
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void ValidateGlobalSidePanel() {
        try {
            //Validate if Menu is available
            Assert.assertTrue(menu.isDisplayed(), "Menu Item is not displayed");
            clickMenu();
            List<String> menuItemsArray = Arrays.asList("Moody's ESG360", "Dashboard", "Portfolio Analysis", "Portfolio Settings", "Contact Us", "Log Out",
                    "Switch Application", "Climate on Demand", "Company Portal", "DataLab");

            //Validate if all menu items are available
            for (String m : menuItemsArray)
                Assert.assertTrue(menuList.stream().filter(p -> p.getText().equals(m)).count() == 1, m + " Menus are not correctly listed");

            //To get page URL
            String url = Driver.getDriver().getCurrentUrl();

            if (url.contains("dashboard")) {
                Assert.assertEquals(menuList.get(0).getText(), "Moody's ESG360: Dashboard", "Global Header Title is not matched for Dashboard");
                finalStringToCheck = menuItemsArray.get(1);
            }

            if (url.contains("portfolioanalysis")) {
                Assert.assertEquals(menuList.get(0).getText(), "Moody's ESG360: Portfolio Analysis", "Global Header Title is not matched for Portfolio Analysis");
                finalStringToCheck = menuItemsArray.get(2);
            }
            System.out.println(menuList.stream().filter(p -> p.getText().equals(finalStringToCheck))
                    .findFirst().get().getCssValue("background-color"));

            //Validate if Menu Item in URL is selected
            Assert.assertTrue(menuList.stream().filter(p -> p.getText().equals(finalStringToCheck))
                    .findFirst().get().getCssValue("background-color").equalsIgnoreCase("rgba(215, 237, 250, 1)"), "Open page menu is not selected");

            //Click on cross button
            Driver.getDriver().findElement(By.xpath("(//*[name()='svg'][@class='MuiSvgIcon-root'])[2]")).click();
            // menuList.get(0).findElement(By.xpath("span")).click();
            //Validating that menu list is closed and background page is still on
            waitForDataLoadCompletion();
            Assert.assertTrue(menuList.size() == 1 && Driver.getDriver().getCurrentUrl().equals(url), "Menu is still displayed and is not focused on main page");

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void isUpdatesAndLeadersAndLaggardsHeaderDisplayUpdatedHeader(String page, CustomAssertion assertionTestCase) {
        try {
            clickFiltersDropdown();
            selectRandomOptionFromFiltersDropdown("as_of_date");
            closeFilterByKeyboard();

            String filters = getRegionsSectionAndAsOfDateDropdownSelectedValue();
            String monthDate = filters.substring(filters.indexOf("at the end of") + 14);
            String whatToValidate = "";
            switch (page) {
                case "Physical Risk Hazards":
                case "Operations Risk":
                case "Market Risk":
                case "Supply Chain Risk":
                    whatToValidate = "Updates as of " + monthDate + ", Impact, and Current Leaders/Laggards";
                    break;
                case "Temperature Alignment":
                    whatToValidate = "Impact";
                    break;
                default:
                    whatToValidate = "Updates in " + monthDate + ", Impact, and Current Leaders/Laggards";
            }
            assertionTestCase.assertEquals(whatToValidate, updatesAndLeadersAndLaggardsHeader.getText(), "Header message is not correct", 477, 592);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public String getColorByScoreCategory(String researchLine, String scoreCategory) {
        if (researchLine.equals("Green Share Assessment")) {
            switch (scoreCategory) {
                case "NONE":
                    return "#B28559";
                case "MINOR":
                    return "#BFB43A";
                case "SIGNIFICANT":
                    return "#6FB24B";
                case "MAJOR":
                    return "#39A885";
            }
        } else if (researchLine.equals("Temperature Alignment")) {
            switch (scoreCategory) {
                case "Well Below 2°C":
                    return "#eac550";
                case "Below 2°C":
                    return "#E8951C";
                case "2°C":
                    return "#DD581D";
                case "Above 2°C":
                    return "#D63229";
            }
        } else if (researchLine.toUpperCase().equals("ESG")) {
            switch (scoreCategory.toUpperCase()) {
                case "WEAK":
                    return "#DD581D";
                case "LIMITED":
                    return "#E8951C";
                case "ROBUST":
                    return "#EAC550";
                case "ADVANCED":
                    return "#DBE5A3";
            }
        } else {
            switch (scoreCategory) {
                case "WEAK":
                    return "#DFA124";
                case "LIMITED":
                    return "#AF9D3F";
                case "ROBUST":
                    return "#5A9772";
                case "ADVANCED":
                case "A2":
                    return "#229595";

                case "NO RISK":
                case "0-19":
                    return "#4FA3CD";
                case "LOW RISK":
                case "20-39":
                    return "#8DA3B7";
                case "MEDIUM RISK":
                case "40-59":
                    return "#A9898E";
                case "HIGH RISK":
                case "60-79":
                    return "#C06960";
                case "RED FLAG RISK":
                case "80-100":
                    return "#DA4930";

                case "MODERATE":
                    return "#87C097";
                case "SIGNIFICANT":
                    return "#8B9F80";
                case "HIGH":
                    return "#A37863";
                case "INTENSE":
                    return "#D02C2C";

                case "0%":
                    return "#39A885";
                case "0-20%":
                    return "#6FB24B";
                case "20-100%":
                    return "#B28559";

                case "WELL BELOW 2°C":
                    return "#EAc550";
                case "BELOW 2°C":
                    return "#E8951C";
                case "2°C":
                    return "#DD581d";
                case "ABOVE 2°C":
                    return "#D63229";
                case "NO INFO":
                    return "#E5E2A3";

            }

        }
        return null;
    }

    private static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }


    public String getColorByScore(String researchLine, int score) {

        switch (researchLine) {
            case "Supply Chain Risk":
            case "Market Risk":
            case "Operations Risk":
            case "Physical Risk Hazards":
                if (isBetween(score, 0, 19)) {
                    return "#4FA3CD";
                } else if (isBetween(score, 20, 39)) {
                    return "#8DA3B7";
                } else if (isBetween(score, 40, 59)) {
                    return "#A9898E";
                } else if (isBetween(score, 60, 79)) {
                    return "#C06960";
                } else if (isBetween(score, 80, 100)) {
                    return "#DA4930";
                }

            case "Physical Risk Management":
            case "Energy Transition Management":
            case "TCFD Strategy":
                if (isBetween(score, 0, 29)) {
                    return "#DFA124";
                } else if (isBetween(score, 30, 49)) {
                    return "#AF9D3F";
                } else if (isBetween(score, 50, 59)) {
                    return "#5A9772";
                } else if (isBetween(score, 60, 100)) {
                    return "#229595";
                }
            case "Carbon Footprint":
                if (isBetween(score, 0, 99999)) {
                    return "#87C097";
                } else if (isBetween(score, 100000, 999999)) {
                    return "#8B9F80";
                } else if (isBetween(score, 1000000, 9999999)) {
                    return "#A37863";
                } else if (isBetween(score, 10000000, Integer.MAX_VALUE)) {
                    return "#D02C2C";
                }

            case "Browns Share Assessment":
                if (isBetween(score, 0, 0)) {
                    return "#39A885";
                } else if (isBetween(score, 1, 20)) {
                    return "#6FB24B";
                } else if (isBetween(score, 21, 100)) {
                    return "#B28559";
                }

            case "Green Share Assessment":
                if (isBetween(score, 0, 0)) {
                    return "#B28559";
                } else if (isBetween(score, 1, 20)) {
                    return "#BFB43A";
                } else if (isBetween(score, 21, 50)) {
                    return "#6FB24B";
                } else if (isBetween(score, 51, 100)) {
                    return "#39A885";
                }
            default:
                return null;
        }

    }

    public void clickSearchIcon() {
        List<WebElement> svgElements = Driver.getDriver().findElements(By.xpath("//*[local-name()='svg']"));
        svgElements.get(1).click();
        wait.until(ExpectedConditions.visibilityOf(SearchInputBox));

    }

    public boolean isSearchBoxDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(SearchInputBox)).isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public void clickCloseIcon() {
        List<WebElement> closeIcon = Driver.getDriver().findElements(By.xpath("//div[@class='MuiToolbar-root MuiToolbar-regular']//*[local-name()='svg' and @class='MuiSvgIcon-root']"));
        closeIcon.get(1).click();
    }

    public void sendESCkey() {
        SearchInputBox.sendKeys(Keys.ESCAPE);
    }

    public void searchEntity(String searchKeyword) {
        SearchInputBox.sendKeys(searchKeyword);
    }

    public boolean isSearchIconDisplayed() {
        try {
            return searchIconPortfolioPage.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkIfSearchBarOfPortfolioIsDisplayed2() {
        try {
            return BrowserUtils.isElementVisible(searchBarOfPortfolio, 3);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkIfSearchResultIsHighlighted(String searchKeyword) {

        searchBarOfPortfolio.sendKeys(searchKeyword);
        BrowserUtils.wait(3);
        String xpathSearchKeyWord = "//mark[.='" + searchKeyword + "']";
        try {
            System.out.println(Driver.getDriver().findElement(By.xpath(xpathSearchKeyWord)).getText());
            return BrowserUtils.isElementVisible(Driver.getDriver().findElement(By.xpath(xpathSearchKeyWord)), 3);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkIfSearchResultsContainsEllipses(String searchKeyword) {
        WebElement element = Driver.getDriver().findElement(By.xpath("//div[@id='mini-0']/div"));
        String resultKeyword = Driver.getDriver().findElement(By.xpath("//div[@id='mini-0']/div/span")).getText();
        return searchKeyword.contains(resultKeyword) && element.getCssValue("text-overflow").equalsIgnoreCase("ellipsis");
    }


    public boolean checkIfSearchResultsContainsSearchKeyWord(String searchKeyword) {

        searchBarOfPortfolio.sendKeys(searchKeyword);
        BrowserUtils.wait(3);
        String xpathSearchKeyWord = "//mark[.='" + searchKeyword + "']";
        List<WebElement> list = Driver.getDriver().findElements(By.xpath(xpathSearchKeyWord));
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).getText().contains(searchKeyword)) {
                return false;
            }
        }
        return true;
    }

    public void checkSearchResultWithWildChars(String searchKeyword) {
        searchBarOfPortfolio.sendKeys(searchKeyword);
        BrowserUtils.wait(3);
        BrowserUtils.isElementVisible(Driver.getDriver().findElement(By.xpath("//header[@id='prop-search']/following-sibling::*/DIV/div/div/div[1]")), 3);
        int numberOfSearchResult = Driver.getDriver().findElements(By.xpath("//header[@id='prop-search']/following-sibling::*/DIV/div/div/div")).size();
        assertTestCase.assertEquals(numberOfSearchResult, 10);
        String xpathSearchKeyWord = "//mark[.='" + searchKeyword + "']";
        List<WebElement> list = Driver.getDriver().findElements(By.xpath(xpathSearchKeyWord));
        searchKeyword = searchKeyword.replace("%", "").replace("*", "");
        for (int i = 0; i < list.size(); i++) {
            assertTestCase.assertTrue(list.get(i).getText().contains(searchKeyword));
        }
    }

    public boolean checkIfNumberOfSearchResultIsTen(String searchKeyword) {

        searchBarOfPortfolio.sendKeys(searchKeyword);
        BrowserUtils.wait(3);
        try {
            BrowserUtils.isElementVisible(Driver.getDriver().findElement(By.xpath("//header[@id='prop-search']/following-sibling::*/DIV/div/div/div[1]")), 3);
            int numberOfSearchResult = Driver.getDriver().findElements(By.xpath("//header[@id='prop-search']/following-sibling::*/DIV/div/div/div")).size();
            System.out.println(numberOfSearchResult);
            return numberOfSearchResult == 10;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean checkWarningWhenNoMatchEntry(String searchKeyword) {

        searchBarOfPortfolio.sendKeys(searchKeyword);
        BrowserUtils.wait(3);
        try {
            BrowserUtils.isElementVisible(Driver.getDriver().findElement(By.xpath("(//div[.='No results found'])[3]")), 3);
            String warningMessage = Driver.getDriver().findElement(By.xpath("(//div[.='No results found'])[3]")).getText();
            System.out.println(warningMessage);
            return warningMessage.equals("No results found");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    String searchKeyword;

    public boolean checkClickingOnEntityName(String searchKeyword) {
        this.searchKeyword = searchKeyword;

        searchBarOfPortfolio.sendKeys(searchKeyword);
        BrowserUtils.wait(3);
        try {
            BrowserUtils.isElementVisible(Driver.getDriver().findElement(By.xpath("//header[@id='prop-search']/following-sibling::*/DIV/div/div/div[3]")), 3);
            String text = Driver.getDriver().findElement(By.xpath("//header[@id='prop-search']/following-sibling::*/DIV/div/div/div[3]")).getText();
            System.out.println(text);
            Driver.getDriver().findElement(By.xpath("//header[@id='prop-search']/following-sibling::*/DIV/div/div/div[3]")).click();
            //BrowserUtils.isElementVisible(Driver.getDriver().findElement(By.xpath("//header[@id='prop-search']/following-sibling::*/DIV/div/div/div[3]")),3);
            //searchIconPortfolioPages.get(0).click();
            //return isSearchBoxAppearonDashboardPage();
            List<WebElement> check = Driver.getDriver().findElements(By.xpath("//input[@id='platform-search-test-id']"));
            if (check.size() == 0)
                return false;
            else
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean checkIfUserIsOnRightPage() {

        BrowserUtils.isElementVisible(Driver.getDriver().findElement(By.xpath("//div[.='Apple, Inc.']")), 3);
        String text = Driver.getDriver().findElement(By.xpath("//div[.='Apple, Inc.']")).getText();
        System.out.println(text);
        return text.contains(searchKeyword);
    }

    public void clickCloseXIcon() {
        BrowserUtils.wait(2);
        Driver.getDriver().findElement(By.xpath("//(//*[@fill='#26415E'])[4]")).click();
    }

    public void closePortfolioExportDrawer() {
        Driver.getDriver().findElement(By.xpath("//span[text()='X']")).click();

    }

    public void clickCloseXIconWithJs() {
        wait.until(ExpectedConditions.visibilityOf(Driver.getDriver().findElement(By.xpath("//(//*[@fill='#26415E'])[4]"))));
        BrowserUtils.clickWithJS(Driver.getDriver().findElement(By.xpath("//(//*[@fill='#26415E'])[4]")));
    }

    public boolean userIsOnHomePage() {
        String xpath = "(//div[@id='legend_box'])[1]";
        return Driver.getDriver().findElement(By.xpath(xpath)).isDisplayed();

    }

    String entityName;

    public void goToEntity(String entityName) {
        this.entityName = entityName;
        navigateToPageFromMenu("Portfolio Analysis");
        searchIconPortfolioPage.click();
        enterPortfolioNameInSearchBar(entityName);
        String xpath = "//span[.='" + entityName + "']";
        Driver.getDriver().findElement(By.xpath(xpath)).click();


    }

    public boolean checkIfUserIsOnRightPage(String entityName) {
        this.entityName = entityName;
        BrowserUtils.isElementVisible(Driver.getDriver().findElement(By.xpath("//div[.='" + entityName + "']")), 3);
        String text = Driver.getDriver().findElement(By.xpath("//div[.='" + entityName + "']")).getText();
        System.out.println(text);
        return text.contains(entityName);
    }

    public boolean validateCarbonFootPrintAndSubLabels() {

        String emissionScope = "Emissions Scope";
        String t_CO2_eq = "t CO2";
        for (int i = 0; i < carbonFootprintSubTitles.size(); i++) {
            System.out.println(carbonFootprintSubTitles.get(i).getText());
            System.out.println("======");
            if (!(carbonFootprintSubTitles.get(i).getText().contains(emissionScope) &&

                    !carbonFootprintSubTitles.get(i).getText().contains(t_CO2_eq)) &&
                    !carbonFootprintTitle.getText().contains("Carbon Footprint")) {
                return false;
            }
        }
        return true;

    }

    public boolean validateTextOfCarbonPrintUpdateDate() {
        return carbonFootprintUpdateDate.getText().contains("Updated on");
    }

    public boolean verifyBackGroundColor() {
        String color = Driver.getDriver().findElement(By.id("innerBox00")).getCssValue("background-color");
        String backGroundOfUpdateDateColor = carbonFootprintUpdateDate.getCssValue("color");
        String headersColor = carbonFootprintTitle.getCssValue("color");
        System.out.println("headersColor" + headersColor);
        System.out.println("color" + color);
        System.out.println("backGroundOfUpdateDateColor" + backGroundOfUpdateDateColor);
        return true;

    }

    public void navigateToEntity(String nameOfEntity) {
        searchIconPortfolioPage.click();
        searchBarOfPortfolio.sendKeys(nameOfEntity);
        BrowserUtils.wait(3);
        ////span[@title='Assa Abloy AB']
        String xpathSearchKeyWord = "//span[@title='" + nameOfEntity + "']";
        System.out.println(Driver.getDriver().findElement(By.xpath(xpathSearchKeyWord)).getText());
        BrowserUtils.isElementVisible(Driver.getDriver().findElement(By.xpath(xpathSearchKeyWord)), 3);
        Driver.getDriver().findElement(By.xpath(xpathSearchKeyWord)).click();

    }

    public void navigateToFirstEntity(String nameOfEntity) {
        BrowserUtils.isElementVisible(searchIconPortfolioPage, 5);
        searchIconPortfolioPage.click();
        searchBarOfPortfolio.sendKeys(nameOfEntity);
        BrowserUtils.wait(3);
        ////span[@title='Assa Abloy AB']
        String xpathSearchKeyWord = "//div[@id='mini-0']//span";
        System.out.println(Driver.getDriver().findElement(By.xpath(xpathSearchKeyWord)).getText());
        BrowserUtils.isElementVisible(Driver.getDriver().findElement(By.xpath(xpathSearchKeyWord)), 3);
        Driver.getDriver().findElement(By.xpath(xpathSearchKeyWord)).click();

    }

    public boolean isPhysicalClimateHazardCardDisplayed() {
        try {
            BrowserUtils.scrollTo(physicalClimateHazards);
            System.out.println("====================");
            //System.out.println(physicalClimateHazards.getText());
            return physicalClimateHazards.isDisplayed() && physicalClimateHazards.getText().contains("Physical Climate Hazards")
                    && physicalClimateHazards.getText().contains("Physical Climate Hazards")
                    && physicalClimateHazards.getText().contains("% Facilities Exposed to");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyPhysicalClimateHazardCardText() {

        return physicalClimateHazards.getText().contains("Physical Climate Hazards") &&
                physicalClimateHazards.getText().contains("Highest Risk Hazard:");
        // && physicalClimateHazards.getText().contains("% Facilities Exposed to");

    }

    public boolean verifyPhysicalRiskHazardDescription(String riskType, String entitlement, String description) {
        try {
            String xpath = "//div[text()='" + riskType + "']//b[text()='" + entitlement + "']/parent::div";
            WebElement element = Driver.getDriver().findElement(By.xpath(xpath));
            BrowserUtils.scrollTo(element);
            return element.getText().contains(description);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPhysicalRiskHazardTextsDisplayed() {
        try {
            List<String> listExpected = new ArrayList<>(Arrays.asList("Floods", "Heat Stress", "Hurricanes & Typhoons", "Sea Level Rise", "Water Stress", "Wildfires"));
            List<String> listActual = new ArrayList<>();

            List<String> listExpectedTextsOfMeasures = new ArrayList<>(Arrays.asList("No Risk", "Low", "Medium", "High", "Red Flag"));
            List<String> listActualTextsOfMeasures = new ArrayList<>();

            for (int i = 0; i < listExpectedTextsOfMeasures.size(); i++) {
                listActualTextsOfMeasures.add(textsOfMeasures.get(i).getText());
            }

            for (int i = 0; i < listExpected.size(); i++) {
                listActual.add(subtextsOfPhysicalRiskHazardsOperationsRisk.get(i).getText());
            }

            BrowserUtils.scrollTo(wildfiresText);
            return physicalRiskHazardsOperationsRisk.getText().equals("Physical Risk Hazards: Operations Risk") &&
                    textOfFacilitiesExposed.getText().equals("Facilities Exposed") &&
                    listActual.equals(listExpected) &&
                    listActualTextsOfMeasures.equals(listExpectedTextsOfMeasures)
                    ;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean showTheExactNumberInsteadOfLessSign() {
        List<WebElement> listOfAll = Driver.getDriver().findElements(By.xpath("//*[@id='table-id-1']//td"));
        // listOfBackgroundColoredCells shows the list of color background cells in table
        List<WebElement> listOfBackgroundColoredCells = Driver.getDriver().findElements(By.xpath("//*[@id='table-id-1']/tbody/tr[*]/td[*]"));

        for (int i = 0; i < listOfAll.size(); i++) {
            if ((("" + listOfAll.get(i)).contains("<")) && listOfBackgroundColoredCells.size() == 15) {
                return false;
            }
        }
        return true;
    }

    public boolean validateSelectedPortfolio() {
        String portfolio = getSelectedPortfolioNameFromDropdown();
        String expectedPortfolio = summaryPortfolioName.getText();
        return portfolio.equals(expectedPortfolio);
    }

    public boolean verifyCoverage() {
        String portfolio = getSelectedPortfolioNameFromDropdown();
        BrowserUtils.wait(5);
        String coverage = Driver.getDriver().findElement(By.xpath("(//div[text()='" + portfolio + "']/following-sibling::div/span)[1]")).getText();
        String[] coverageText = coverage.split("\n")[0].split(" ");
        return coverageText[0].equals("Coverage:")
                && coverageText[1].equals("Across")
                && coverageText[2].chars().allMatch(Character::isDigit)
                && coverageText[3].equals("companies,")
                && coverageText[4].equals("representing")
                && coverageText[5].substring(0, coverageText[5].indexOf('%') - 1).chars().allMatch(Character::isDigit)
                && coverageText[6].equals("of")
                && coverageText[7].equals("investments");
    }

    public boolean verifyClimateRisk() {
        wait.until(ExpectedConditions.elementToBeClickable(summaryClimateRisk)).click();
        BrowserUtils.wait(2);
        return Driver.getDriver().findElement(By.xpath("//h2[text()='Climate Risk']")).isDisplayed();
    }

    public boolean verifyPhysicalRiskClimateTile() {
        try {

            String highestRiskHazardStatus = Driver.getDriver().findElement(By.xpath("//div[text()='Physical Risk']/..//div[text()='Highest Risk Hazard']/..//span[2]")).getText();
            System.out.println("highestRiskHazardStatus = " + highestRiskHazardStatus);
            ArrayList<String> highestRiskHazardStatusList = new ArrayList<String>();
            highestRiskHazardStatusList.add("Floods");
            highestRiskHazardStatusList.add("Heat Stress");
            highestRiskHazardStatusList.add("Hurricanes & Typhoons");
            highestRiskHazardStatusList.add("Sea Level Rise");
            highestRiskHazardStatusList.add("Water Stress");
            highestRiskHazardStatusList.add("Wildfires");

            String facilitiesExposedValue = Driver.getDriver().findElement(By.xpath("//div[text()='Physical Risk']/..//div[text()='Facilities Exposed to " + highestRiskHazardStatus + "']/..//span[1]")).getText();
            System.out.println("facilitiesExposedValue = " + facilitiesExposedValue);
            System.out.println("facilitiesExposedValue = " + facilitiesExposedValue.substring(0, facilitiesExposedValue.indexOf('%') - 1).chars().allMatch(Character::isDigit));
            return highestRiskHazardStatusList.contains(highestRiskHazardStatus) &&
                    facilitiesExposedValue.substring(0, facilitiesExposedValue.indexOf('%') - 1).chars().allMatch(Character::isDigit);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyTransitionRiskClimateTile() {
        try {
            String temperatureAlignmentValue = Driver.getDriver().findElement(By.xpath("//div[text()='Transition Risk']/..//div[text()='Temperature Alignment']/..//span[1]")).getText();

            ArrayList<String> carbonFootprintScores = new ArrayList<String>();
            carbonFootprintScores.add("Moderate");
            carbonFootprintScores.add("Significant");
            carbonFootprintScores.add("High");
            carbonFootprintScores.add("Intense");

            String carbonFootprintScore = Driver.getDriver().findElement(By.xpath("//div[text()='Transition Risk']/..//div[text()='Carbon Footprint']/..//span[1]")).getText();
            return temperatureAlignmentValue.contains("°C")
                    && carbonFootprintScores.contains(carbonFootprintScore);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyStickyHeaderInfo() {
        try {
            DashboardPage dashboardPage = new DashboardPage();
            String portfolio = "Sample Portfolio";
            selectPortfolio(portfolio);
            BrowserUtils.scrollTo(dashboardPage.endOfPage);// scrolling to the last widget on the page
            BrowserUtils.wait(2);
            if (!dashboardPage.isStickyHeaderDisplayed()) {
                return false;
            }
            assertTestCase.assertTrue(dashboardPage.regionTitleInStickyHeader.isDisplayed(), "Verify Title and Region/Sector Toggle are in Sticky Header in the Drawer ");
            assertTestCase.assertTrue(dashboardPage.isStickyHeaderDisplayed(), "Sticky header is not displayed");

            // Verify portfolio name in sticky header
            assertTestCase.assertTrue(Driver.getDriver().findElement(By.xpath("//header[contains(@class,'Sticky')]//div[contains(text(),'Viewing " + portfolio + ": All Regions, All Sectors')]")).isDisplayed(), "Portfolio name is not displayed in sticky header");

            // Verify physical risk climate tile details in sticky header
            String highestRiskHazardStatus = Driver.getDriver().findElement(By.xpath("//header//div[text()='Highest Risk Hazard']/..//span[2]")).getText();
            ArrayList<String> highestRiskHazardStatusList = new ArrayList<String>();
            highestRiskHazardStatusList.add("Floods");
            highestRiskHazardStatusList.add("Heat Stress");
            highestRiskHazardStatusList.add("Hurricanes & Typhoons");
            highestRiskHazardStatusList.add("Sea Level Rise");
            highestRiskHazardStatusList.add("Water Stress");
            highestRiskHazardStatusList.add("Wildfires");

            String facilitiesExposedValue = Driver.getDriver().findElement(By.xpath("//header//div[text()='Facilities Exposed to " + highestRiskHazardStatus + "']/..//span[1]")).getText();
            assertTestCase.assertTrue(highestRiskHazardStatusList.contains(highestRiskHazardStatus) &&
                    facilitiesExposedValue.substring(0, facilitiesExposedValue.indexOf('%') - 1).chars().allMatch(Character::isDigit), "Physical Risk climate tile details are not displayed in sticky header");


            // Verify transition risk climate tile details in sticky header
            String temperatureAlignmentValue = Driver.getDriver().findElement(By.xpath("//header//div[text()='Temperature Alignment']/..//span[1]")).getText();

            ArrayList<String> carbonFootprintScores = new ArrayList<String>();
            carbonFootprintScores.add("Moderate");
            carbonFootprintScores.add("Significant");
            carbonFootprintScores.add("High");
            carbonFootprintScores.add("Intense");

            String carbonFootprintScore = Driver.getDriver().findElement(By.xpath("//header//div[text()='Carbon Footprint']/..//span[1]")).getText();
            assertTestCase.assertTrue(temperatureAlignmentValue.contains("°C")
                    && carbonFootprintScores.contains(carbonFootprintScore), "Transition Risk climate tile details are not displayed in sticky header");

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void istemperatureAlignmentFieldsavailableInExportedExcel() {
        File dir = new File(BrowserUtils.downloadPath());
        File[] dir_contents = dir.listFiles();
        LocalDateTime ldt = LocalDateTime.now();
        String date = DateTimeFormatter.ofPattern("MM_dd_yyyy", Locale.ENGLISH).format(ldt);
        Assert.assertTrue(Arrays.asList(dir_contents).stream().filter(e -> e.getName().contains(date)).findAny().isPresent(), "Verify Download of Excel file");
        String pathName = Arrays.asList(dir_contents).stream().filter(e -> e.getName().contains(date)).findAny().toString();

        ResearchLinePage researchLinePage = new ResearchLinePage();
        ExcelUtil excelUtil = new ExcelUtil(pathName.substring(9, pathName.length() - 1), "Data Dictionary");
        List<String> excelData;
        excelData = excelUtil.getColumnData(0);
        System.out.println("Read data: " + excelData);
        List<String> requiredStrings = Arrays.asList(
                "Temperature Alignment Produced Date",
                "Approach",
                "Data Type",
                "Temperature Alignment",
                "Implied Temperature Rise",
                "Implied Temperature Rise Target Year",
                "Emissions Reduction Base Year",
                "Emissions Reduction Target Year",
                "Target Disclosure",
                "Target Description",
                "Source",
                "Scope",
                "Units"/*, //TODO this part is not available in UAT
                "Bicycles : Reasonable Estimate of Incorporation",
                "Bio-based chemicals : Reasonable Estimate of Incorporation",
                "Contaminated site rehabilitation : Reasonable Estimate of Incorporation"*/
        );
        for (String s : requiredStrings) {
            Assert.assertTrue(excelData.stream().filter(f -> f.equals(s)).count() >= 1);
        }


    }

    public void clickCoverageLink() {
        wait.until(ExpectedConditions.elementToBeClickable(CoverageLink)).click();
        BrowserUtils.wait(5);
    }

    public boolean validatePOrtfolioManagementHeaderIsAvailable() {
        return header_portfolioManagement.isDisplayed();
    }

    public boolean validateUploadNewLinkIsAvailable() {
        return link_UploadNew.isDisplayed();
    }

    public boolean validateSideArrowIsAvailable() {
        return wait.until(ExpectedConditions.visibilityOf(backArrow)).isDisplayed();
    }

    public boolean validatespanPortfolioNameColumnIsAvailable() {
        return span_POrtfolioName.isDisplayed();
    }

    public boolean validatespanUploadDateColumnIsAvailable() {
        return span_POrtfolioName.isDisplayed();
    }

    public List<String> getScoreCategoriesByResearchLine(String researchLine) {

        switch (researchLine) {
            case "operationsrisk":
            case "marketrisk":
            case "supplychainrisk":
            case "Operations Risk":
            case "Market Risk":
            case "Supply Chain Risk":
                return Arrays.asList("No Risk", "Low Risk", "Medium Risk", "High Risk", "Red Flag Risk");

            case "Physical Risk Hazards":
                return Arrays.asList("Floods", "Heat Stress", "Hurricanes & Typhoons", "Sea Level Rise", "Water Stress", "Wildfires");

            case "Physical Risk Management":
            case "Energy Transition Management":
            case "TCFD":
                return Arrays.asList("Advanced", "Robust", "Limited", "Weak");

            case "Brown Share":
            case "Brown Share Assessment":
                return Arrays.asList("0%", "0-20%", "20-100%");

            case "Carbon Footprint":
                return Arrays.asList("Moderate", "Significant", "High", "Intense");

            case "Green Share":
            case "Green Share Assessment":
                return Arrays.asList("Major", "Significant", "Minor", "None");

            case "Temperature Alignment":
                return Arrays.asList("Well Below 2°C", "Below 2°C", "2°C", "Above 2°C", "No Info", "Above 2°C");

            case "ESG":
            case "ESG Assessments":
            case "Overall ESG Score":
                return Arrays.asList("a1.esg", "a2.esg", "a3.esg", "b1.esg", "b2.esg", "b3.esg", "c1.esg", "c2.esg", "c3.esg", "e.esg");
        }
        return null;
    }

    public String getPortfolioName() {
        String portfolioName = "";
        for (WebElement e : portfolioSettings_portfolioList) {
            portfolioName = e.findElements(By.xpath("div/div/div/div/span")).get(0).getText();
            if (!portfolioName.equals("Sample Portfolio")) {
                if (portfolioName.contains("Automation")) {
                    break;
                }
            }
        }
        return portfolioName;
    }

    public void selectPortfolio(String portfolioName) {
        try {
            WebElement portfolio = Driver.getDriver().findElement(By.xpath("//button[@title='" + portfolioName + "']"));
            BrowserUtils.scrollTo(portfolio);
            portfolio.click();
            System.out.println("Portfolio found : " + portfolioName);
        } catch (Exception e) {
            System.out.println("Could not find the Portfolio");
            return;
        }
    }

    public Boolean ValidatePortfolioNameFeildIsEditable() {
        return input_PortfolioName.isEnabled();
    }

    public void updatePortfolio(String newName) {
        while (!StringUtils.isEmpty(input_PortfolioName.getAttribute("value"))) {
            input_PortfolioName.sendKeys("\u0008");
        }
        input_PortfolioName.sendKeys(newName);
    }

    public void closeMenuByClickingOutSide() {
        actions.moveByOffset(500, 200).click().build().perform();
    }

    public void validatePortfolioNameNotChangedAfterUpdateAndClickOutside(String OriginalPortFolioName) {
        updatePortfolio(OriginalPortFolioName + "111");
        // BrowserUtils.wait(10);
        closeMenuByClickingOutSide();
        clickMenu();
        portfolioSettings.click();
        selectPortfolio(OriginalPortFolioName);
        assertTestCase.assertTrue(getPortfolioDrawerHeader(OriginalPortFolioName).isDisplayed());
    }

    public WebElement getPortfolioDrawerHeader(String portfolioName) {
        return Driver.getDriver().findElement(By.xpath("//span[@title='" + portfolioName + "']"));
    }

    public void clickInSidePortfolioDrawer() {
        input_PortfolioName.sendKeys(Keys.TAB);
    }

    public void validatePortfolioNameChangedAfterUpdateAndClickInsideDrawer(String OriginalPortFolioName) {
        String newPortfolioName = "Automation123";
        updatePortfolio(newPortfolioName);
        clickInSidePortfolioDrawer();
        assertTestCase.assertTrue(getPortfolioDrawerHeader(newPortfolioName).isDisplayed(), "Validate if portfolio name saved " +
                "and after change and click inside the drawer");
        undoPortfolioNameChange(OriginalPortFolioName);
    }

    public void validatePortfolioNameSavedAutomaticallyAfterTwoSecond(String OriginalPortFolioName) {
        String newPortfolioName = "After2Second";
        updatePortfolio(newPortfolioName);
        BrowserUtils.wait(2);
        assertTestCase.assertTrue(wait.until(ExpectedConditions.visibilityOf(successMessageForNameSaved)).isDisplayed(), "Validate Succee message is displayd after save");
        assertTestCase.assertTrue(getPortfolioDrawerHeader(newPortfolioName).isDisplayed(), "Validate that portfolio name saved " +
                "automatically  after change and wait for more than 2 seconds");

        undoPortfolioNameChange(OriginalPortFolioName);
    }

    public void validatePortfolioNameRevertbyCtrlZ(String OriginalPortFolioName) {
        input_PortfolioName.sendKeys("123");
        BrowserUtils.wait(2);
        assertTestCase.assertTrue(getPortfolioDrawerHeader(OriginalPortFolioName + "123").isDisplayed(), "Validate that portfolio new name is saved ");
        if (System.getProperty("os.name").contains("Mac"))
            input_PortfolioName.sendKeys(Keys.COMMAND + "z");
        else
            input_PortfolioName.sendKeys(Keys.CONTROL + "z");
        assertTestCase.assertTrue(getPortfolioDrawerHeader(OriginalPortFolioName).isDisplayed(), "Validate that portfolio name is reverted by Ctrl / Command + Z ");
    }

    public void validatePortfolioNameUpdatedInAllLocations(String OriginalPortFolioName) {
        String newPortfolioName = "Automation Change in All Locations";
        updatePortfolio(newPortfolioName);
        BrowserUtils.wait(2);
        assertTestCase.assertTrue(wait.until(ExpectedConditions.visibilityOf(successMessageForNameSaved)).isDisplayed(), "Validate Succee message is displayd after save");
        assertTestCase.assertTrue(getPortfolioDrawerHeader(newPortfolioName).isDisplayed(), "Validate that chnaged portfolio name is displayed in header ");
        closeMenuByClickingOutSide();
        navigateToPageFromMenu("Dashboard");
        assertTestCase.assertTrue(getPortfolioNames().contains(newPortfolioName + "\n"), "Validate updated Portfolio name has appreared on Dashboard page, Portfolio selection modal");

        navigateToPageFromMenu("Portfolio Analysis");
        assertTestCase.assertTrue(getPortfolioNames().contains(newPortfolioName + "\n"), "Validate updated Portfolio name has appreared on Dashboard page, Portfolio selection modal");

        clickMenu();
        portfolioSettings.click();
        selectPortfolio(newPortfolioName);
        undoPortfolioNameChange(OriginalPortFolioName);
    }

    public void validateblankPortfolioName(String OriginalPortFolioName) {
        updatePortfolio("");
        BrowserUtils.wait(3);
        try {
            successMessageForNameSaved.isDisplayed();
        } catch (Exception e) {
            Boolean messageBoxNotDisplayed = true;
            assertTestCase.assertTrue(messageBoxNotDisplayed, "Validate Succee message is not displayd with blank portfolio name");
        }


        assertTestCase.assertTrue(getPortfolioDrawerHeader(OriginalPortFolioName).isDisplayed(), "Validate that portfolio name didn't chnaged ");

    }

    public void undoPortfolioNameChange(String OriginalPortFolioName) {
        updatePortfolio(OriginalPortFolioName);
        BrowserUtils.wait(4);
        clickInSidePortfolioDrawer();
    }
}
