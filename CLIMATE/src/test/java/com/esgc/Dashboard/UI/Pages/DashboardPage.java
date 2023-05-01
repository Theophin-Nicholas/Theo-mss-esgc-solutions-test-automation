package com.esgc.Dashboard.UI.Pages;


import com.esgc.Base.UI.Pages.UploadPage;
import com.esgc.Dashboard.DB.DBQueries.DashboardQueries;
import com.esgc.Utilities.*;
import com.esgc.Utilities.Database.DatabaseDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class DashboardPage extends UploadPage {

    //=============== Summary Header
    @FindBy(xpath = "//*[./following-sibling::*/*[starts-with(text(),'Coverage: Across')]]")
    public WebElement portfolioNameInSummaryHeaders;

    @FindBy(xpath = "//header[.//*[text()='About Climate Risk']]")
    public WebElement summaryHeader;

    @FindBy(xpath = "//header[.//*[starts-with(text(),'View')]]")
    public WebElement stickyHeader;

    @FindBy(xpath = "//*[@heap_menu='Climate Dashboard']")
    public WebElement dashboardButton;

    @FindBy(xpath = "//*[@id='topbar-appbar-test-id']/div/li")
    public WebElement menuButton;

    @FindBy(xpath = "//*[@heap_id='portfolioSetting']")
    public WebElement portfolioSelectionUploadButton;

    @FindBy(xpath = "/html/body/div[2]/div[3]/div/div/header/div/div[1]/span")
    public WebElement returnButton;
    @FindBy(xpath = "//h3[contains(text(),'Portfolio Average')]")
    public WebElement heatmapPortfolioAverage;

    @FindBy(xpath = "//div[@id='portfoliomonitoring-test-id']//div[@class='scores']/preceding-sibling::span")
    public WebElement criticalControversiesInEsg;

    @FindBy(xpath = "//div[@class='score']/span")
    public List<WebElement> esgScoreBoxes;

    @FindBy(xpath = "//div[@class='score']/p")
    public List<WebElement> esgScoreBoxesLabels;

    @FindBy(xpath = "//div[text()='Physical Risk']")
    public WebElement physicalRiskCard;

    @FindBy(id = "Highest_Risk_Hazard_id")
    public WebElement highestRiskHazard;

    @FindBy(xpath = "//div[contains(@id,'Facilities_Exposed_to_')]")
    public WebElement facilitiesExposed;

    @FindBy(xpath = "//div[text()='View Methodologies']")
    public WebElement btnViewMethodologies;

    @FindBy(xpath = "//div[contains(@class,'MuiDrawer-paperAnchorRight')]//div[text()='Methodologies']")
    public WebElement methodologiesPopup;
    @FindBy(xpath = "//*[@id=\"topbar-drawer-test-id\"]/div[3]/li[4]")
    public WebElement logOutButton;
    @FindBy(xpath = "//div[contains(@class,'MuiDrawer-paperAnchorRight')]//h2[1]")
    public WebElement methodologyPopup_Header;

    @FindBy(xpath = "(//div[@class='entityList']//div/div[text()])[2]")
    public WebElement heatMapDrawerYAxisColumnName;

    @FindBy(xpath = "//div[@id='heatmapentity-test-id']//h3[text()='Brown Share Assessment']/..//h3[contains(text(),'Coverage')]")
    public WebElement heatMapBrownShareCoverage;

    @FindBy(xpath = "//a[@id='link-link-test-id-1']")
    public WebElement methodologyPopup_Link_Methodology10;
    @FindBy(xpath = "//a[@id='link-link-test-id-2']")
    public WebElement methodologyPopup_Link_Methodology20;
    @FindBy(xpath = "//a[@id='link-link-test-id-3']")
    public WebElement methodologyPopup_Link_RiskAssessmentts;

    @FindBy(xpath = "//a[@id='link-link-test-id-3']")
    public List<WebElement> methodologyPopup_Links;

    @FindBy(xpath = "//div[contains(text(),'Viewing')]")
    public WebElement regionTitleInStickyHeader;
    @FindBy(xpath = "//div[contains(@class,'Drawer-paper')]//h2")
    public List<WebElement> methodologySectionNames;

    @FindBy(xpath = "//a[text()='hide']")
    public WebElement hideLink;

    @FindBy(xpath = "//div[@id='heatmapentity-test-id']")
    public WebElement endOfPage;

    @FindBy(id = "button-holdings")
    public WebElement selectPortfolioButton;

    @FindBy(id = "score-qualty-btn")
    public WebElement scoreQualityButton;

    @FindBy(xpath = "//button[@id='score-qualty-btn']//*[local-name()='svg']/*[local-name()='path']")
    public WebElement scoreQualityStatus;

    @FindBy(xpath = "//td[@heap_id='perfchart']")
    public List<WebElement> performanceTableRecords;

    @FindBy(xpath = "//td[@heap_id='perfchart']//*[local-name()='svg']/*[local-name()='rect'][1]")
    public List<WebElement> scoreQualityIconsPerformanceTable;

    @FindBy(xpath = "//td[contains(@id,'viewcomapnies')]//*[local-name()='svg']/*[local-name()='rect'][1]")
    public List<WebElement> scoreQualityIconsCoveragePopup;

    @FindBy(xpath = "//tr//td[contains(@id,'viewcomapnies')][1]")
    public List<WebElement> rowsInCoveragePopup;

    @FindBy(xpath = "//*[@heap_id='heatmap']//*[local-name()='svg']/*[local-name()='rect'][1]")
    public List<WebElement> scoreQualityIconsInCompareRLs;

    //================ summary header tiles

    @FindBy(xpath = "//header//div[@id]/../preceding-sibling::div[text()]")
    public List<WebElement> summaryHeaderBundleTitles;

    @FindBy(xpath = "//header//div[@id]//div[text()]")
    public List<WebElement> summaryHeaderTitles;

    //=========== Portfolio - View All Companies (Summary Companies) Panel Elements
    @FindBy(xpath = "//span[starts-with(text(),'Coverage: Across')]")
    public WebElement viewAllCompaniesButton;

    @FindBy(xpath = "//div[@id='button-button-test-id-1']/../div[starts-with(.,'Companies')]")
    public WebElement summaryCompaniesPanelTitle;

    @FindBy(xpath = "//button[.='View By Sector']")
    public WebElement viewBySectorBtn;
    @FindBy(xpath = "//button[.='View By Region']")
    public WebElement viewByRegionBtn;

    @FindBy(xpath = "//td[contains(@id,'viewcomapnies')]/parent::tr")
    public List<WebElement> coveragePopupRows;

    @FindBy(xpath = "//td[contains(@id,'viewcomapnies')]/span/span")
    public List<WebElement> coveragePopupCompanyNames;

    @FindBy(xpath = "//h3[@heap_id='heatmap']/../../..")
    public List<WebElement> heatmapPopupRows;

    @FindBy(xpath = "//h3[@heap_id='heatmap']//span[@title]")
    public List<WebElement> heatmapPopupCompanyNames;

    @FindBy(xpath = "//div[contains(@class, 'MuiTableContainer-root')]/parent::div[@id]/div[1]")
    public List<WebElement> panelClassNames;
    @FindBy(xpath = "//div[contains(@class, 'MuiTableContainer-root')]/parent::div[@id]//tbody/tr/td[1]")
    public List<WebElement> panelEntityNames;
    @FindBy(xpath = "//div[contains(@class, 'MuiTableContainer-root')]/parent::div[@id]//tbody/tr/td[2]")
    public List<WebElement> panelControversies;
    @FindBy(xpath = "//div[contains(@class, 'MuiTableContainer-root')]/parent::div[@id]//tbody/tr/td[3]")
    public List<WebElement> panelInvestments;
    @FindBy(xpath = "//div[contains(@class, 'MuiTableContainer-root')]/parent::div[@id]//tbody/tr/td[4]")
    public List<WebElement> panelRegionsAndCountries;
    @FindBy(xpath = "//p[starts-with(.,'Showing')]")
    public WebElement panelShowingText;
    @FindBy(xpath = "//p[starts-with(.,'Export')]")
    public WebElement panelExportText;
    @FindBy(id = "dashboard-export-button-test-id")
    public WebElement panelExportToExcelBtn;
    @FindBy(css = "div[tabindex=\"-1\"] header svg > path[fill=\"#26415E\"]")
    public WebElement closePanelBtn;
    @FindBy(xpath = "//div[contains(@class, 'MuiTableContainer-root')]/following-sibling::div[contains(.,' more companies in this sector')]")
    public List<WebElement> moreCompaniesInThisSectorText;
    @FindBy(xpath = "//div[contains(@class, 'MuiTableContainer-root')]/following-sibling::div[contains(.,' more companies in this region')]")
    public List<WebElement> moreCompaniesInThisRegionText;

    //=========== Portfolio Monitoring  / Controversies Table
    @FindBy(xpath = "//*[text()='Portfolio Monitoring']")
    public WebElement portfolioMonitoringTitle;

    @FindBy(xpath = "//*[text()='Portfolio Monitoring']/../following-sibling::div")
    public WebElement controversiesTable;
    @FindBy(xpath = "//*[text()='Portfolio Monitoring']/../following-sibling::div//tr/td[1]")
    public List<WebElement> controversiesTableDates;
    @FindBy(xpath = "//*[text()='Portfolio Monitoring']/../following-sibling::div//tr/td[2]")
    public List<WebElement> controversiesTableSeverity;
    @FindBy(xpath = "//*[text()='Portfolio Monitoring']/../following-sibling::div//tr/td[2]//*[local-name()='svg']")
    public List<WebElement> controversiesTableCriticalSeverityIcons;
    @FindBy(xpath = "//*[text()='Portfolio Monitoring']/../following-sibling::div//tr/td[3]/span")
    public List<WebElement> controversiesTableTitles;
    @FindBy(xpath = "//*[text()='Portfolio Monitoring']/../following-sibling::div//tr/td[4]")
    public List<WebElement> controversiesTableCompanyNames;
    @FindBy(xpath = "//*[text()='Portfolio Monitoring']/../following-sibling::div//tr/td[5]")
    public List<WebElement> controversiesTableSectors;
    @FindBy(xpath = "//*[text()='Portfolio Monitoring']/../following-sibling::div//tr/td[6]")
    public List<WebElement> controversiesTableCountries;

    //Controversy Details Modal
    public By controversiesDetailModal = By.cssSelector("div.jss11265");
    public By controversiesDetailModalTitle = By.xpath("//div[text()='Controversies']/following-sibling::div[1]");
    public By controversiesDetailModalDate = By.xpath("//div[text()='Controversies']/following-sibling::div[2]");
    public By controversiesDetailModalSeverity = By.xpath("//p/span[1]");
    public By controversiesDetailModalResponsiveness = By.xpath("//p/span[2]");


    @FindBy(css = ".close >svg > path")
    public WebElement controversyCloseBtn;


    //=========== Performance Charts

    @FindBy(xpath = "//*[starts-with(text(),'Performance')]/..//th")
    public List<WebElement> performanceChartColumns;

    @FindBy(xpath = "//*[starts-with(text(),'Performance')]")
    public WebElement performanceChart;

    @FindBy(xpath = "//*[text()='Performance: Largest Holdings, Leaders, Laggards']/following-sibling::div/table/tbody/tr")
    public List<WebElement> performanceChartRows;

    @FindBy(xpath = "//table[./thead//th[text()='% Investment']]/tbody/tr/td[2]")
    public List<WebElement> investmentsInPerformanceChart;

    @FindBy(xpath = "//table/thead//th[text()='Overall ESG Score']")
    public WebElement OverallESGScoreColoumn;

    @FindBy(xpath = "//table/thead//th[text()='Total Critical Controversies']")
    public WebElement TotalCriticalControversiesColoumn;

    @FindBy(xpath = "//table[./thead//th[text()='Total Critical Controversies']]/tbody/tr/td[4]")
    public List<WebElement> TotalCriticalControversiesTabledata;

    @FindBy(xpath = "//*[starts-with(text(),'Sum')]")
    public WebElement totalInvestmentInPerformanceChart;

    @FindBy(xpath = "//span[contains(text(),'Leaders')]")
    public WebElement tabPerformanceLeaders;

    @FindBy(xpath = "//span[contains(text(),'Laggards')]")
    public WebElement tabPerformanceLaggards;

    @FindBy(xpath = "//div[contains(text(),'Performance:')]/..//div[starts-with(text(),'Show Top') or starts-with(text(),'Show Bottom')]")
    public WebElement performanceChartDropdown;

    @FindBy(xpath = "//ul[@role='listbox']//span[text()]")
    public List<WebElement> performanceChartDropdownOptions;

    @FindBy(xpath = "//td[@heap_id='perfchart']/parent::tr")
    public List<WebElement> performanceRows;

    @FindBy(xpath = "//td[@heap_id='perfchart']//span[@title]")
    public List<WebElement> performanceChartCompanyNames;

    @FindBy(xpath = "//div[@id='perfError']")
    public List<WebElement> PerformanceChartError;
    //=========== Geographic Risk Map

    @FindBy(xpath = "//div[text()='Geographic Risk Distribution']/following-sibling::div//*[@role]")
    public WebElement geographicRiskDistributionDropdown;

    @FindBy(xpath = "//div[starts-with(@id,'highcharts')]")
    public WebElement geographicRiskMap;

    @FindBy(css = "g.highcharts-tracker > g > path:not([fill='#f7f7f7'])")
    public List<WebElement> countriesOnMap;

    @FindBy(xpath = "//a[@id='link-link-test-id-1']")
    public WebElement linkGoToPortfolioAnalysis;

    //============ Country Distribution List in Geo Map

    @FindBy(xpath = "//ul[@class='countryList']/li")
    public List<WebElement> countryCardsList;

    //============= Entity List in Geo Map

    @FindBy(xpath = "//p[contains(text(),'Investments in') and contains(text(),'by Company')]")
    public WebElement holdingsTitleOnEntityList;

    @FindBy(xpath = "//span[starts-with(text(),'<')]")
    public WebElement backToCountriesLink;

    //============= Heat Map
    @FindBy(xpath = "//*[text()='Select two:']/following-sibling::div/div")
    public List<WebElement> heatMapResearchLines;

    @FindBy(xpath = "//div[text()='Brown Share Assessment']/ancestor::table//div[@heap_id='heatmap']/span")
    public List<WebElement> brownShareCategories;

    @FindBy(xpath = "//div[@class='entityList' and .//div[contains(text(),'Companies')]]")
    public WebElement heatMapEntityDrawerWidget;

    @FindBy(xpath = "//div[@class='entityList']//br/..")
    public WebElement heatMapWidgetTitle;

    @FindBy(xpath = "//div[@class='entityList']//br/../following-sibling::div/div[1]")
    public WebElement heatMapWidgetYIndicator;

    @FindBy(xpath = "//div[@class='entityList']//br/../following-sibling::div/div[2]")
    public WebElement heatMapWidgetXIndicator;

    @FindBy(xpath = "(//div[@id='div-mainlayout']//table)[3]//tbody//td")
    public List<WebElement> heatMapCells;

    @FindBy(xpath = "(//table[.//thead//div[text()]])[1]//*[@heap_id='heatmap']//span")
    public List<WebElement> heatMapYAxisIndicators;

    @FindBy(xpath = "(//div[@id='div-mainlayout']//table)[1]//tbody//td//span[1]")
    public List<WebElement> heatMapYAxisIndicatorsAPI;

    @FindBy(xpath = "(//div[@id='div-mainlayout']//table)[2]//tbody//td//span[2]")
    public List<WebElement> heatMapYAxisIndicatorPercentagesAPI;

    @FindBy(xpath = "(//div[@id='div-mainlayout']//table)[2]//tbody//td//span[2]")
    public List<WebElement> heatMapYAxisIndicatorPercentages;

    @FindBy(xpath = "(//table[.//thead//div[text()]])[2]//*[@heap_id='heatmap']//span")
    public List<WebElement> heatMapXAxisIndicators;

    @FindBy(xpath = "(//div[@id='div-mainlayout']//table)[4]//tbody//td//span[2]")
    public List<WebElement> heatMapXAxisIndicatorPercentages;

    @FindBy(xpath = "(//thead//div[text()])[1]")
    public WebElement heatMapYAxisIndicatorTitle;

    @FindBy(xpath = "(//thead//div[text()])[2]")
    public WebElement heatMapXAxisIndicatorTitle;

    @FindBy(xpath = "//div[@id='heatmapentity-test-id']//p[text()='Select two:']")
    public WebElement selectTwoLabel;

    @FindBy(xpath = "//h3/following-sibling::p")
    public List<WebElement> heatMapActiveResearchLineInfo;

    @FindBy(xpath = "//div[text()='Analyze Companies by Range']/..//div[text()='Temperature Alignment']")
    public WebElement heatMapTemperatureAlignment;

    @FindBy(xpath = "//div[@id='heatmapentity-test-id']//li//h3")
    public List<WebElement> heatMapDrawerEntityNames;

    @FindBy(xpath = "//div[@id='heatmapentity-test-id']//li//h4")
    public List<WebElement> heatMapDrawerEntityPercentages;


    //Other locators

    @FindBy(xpath = " //button[@id='button-holdings']/span/div")
    public WebElement verifyPortfolioName;

    @FindBy(xpath = "//p[contains(text(),'We score companies for Operations Risk by aggregat')]")
    public WebElement operationRiskDescription;

    @FindBy(xpath = "//p[contains(text(),'We evaluate each company’s Market Risk with two in')]")
    public WebElement marketRiskDescription;

    @FindBy(xpath = "//p[contains(text(),'We evaluate Supply Chain Risk with two indicators.')]")
    public WebElement supplyChainRiskDescription;

    @FindBy(xpath = "//p[contains(text(),'We evaluate how companies are acting to anticipate')]")
    public WebElement physicalRiskManagementDescription;

    @FindBy(xpath = "//p[contains(text(),'Temperature Alignment is a forward-looking dataset')]")
    public WebElement temperatureAlignmentDescription;

    @FindBy(xpath = "//p[contains(text(),'We evaluate a companies’ carbon footprint based on')]")
    public WebElement carbonFootprintDescription;

    @FindBy(xpath = "//p[contains(text(),'We identify companies’ involvement in products and')]")
    public WebElement greenShareAssessmentDescription;

    @FindBy(xpath = "//p[contains(text(),'Brown Share Assessment contains a dedicated screen')]")
    public WebElement brownShareAssessmentDescription;


    // methods to manipulate the web elements.


    //div[@class='entityList']//br/../following-sibling::div/div[2]
    public boolean isControversiesTableDisplayed() {
        try {
            BrowserUtils.scrollTo(controversiesTable);
            return controversiesTable.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    public void navigateToDashboardPage() {

        Driver.getDriver().navigate().to(Environment.URL);
    }

    public void clickOnDashboardButton() {
        dashboardButton.click();
        //BrowserUtils.waitForVisibility(dashboardButton, 25).click();
    }

    public void clickOnLogoutButton() {
        logOutButton.click();
    }

    public void clickOnMenuButton() {
        BrowserUtils.waitForVisibility(menuButton, 10).click();
    }

    public void clickOnPortfolioSelectionUploadButton() {
        portfolioSelectionUploadButton.click();
    }

    public void clickOnReturnButton() {
        returnButton.click();
    }

    public boolean isControversiesColumnDisplayed() {
        try {
            String xpath = "//span[contains(text(),'View By ')]/../../../../../../../..//table//th/span[contains(text(),'Controversies in')]";
            int count = Driver.getDriver().findElements(By.xpath(xpath)).size();
            if (count > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPortfolioMonitoringTableDisplayed() {
        try {
            return controversiesTable.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isStickyHeaderDisplayed() {
        try {
            BrowserUtils.scrollTo(stickyHeader);
            return stickyHeader.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyStickyHeaderInfo() {
        try {
            DashboardPage dashboardPage = new DashboardPage();
            String portfolio = "Sample Portfolio";
            selectPortfolio(portfolio);
            BrowserUtils.scrollTo(dashboardPage.endOfPage);// scrolling to the last widget on the page
            System.out.println("Sticky1");
            BrowserUtils.wait(4);
            if (!dashboardPage.isStickyHeaderDisplayed()) {
                System.out.println("Sticky2");
                return false;
            }
            assertTestCase.assertTrue(dashboardPage.regionTitleInStickyHeader.isDisplayed(), "Verify Title and Region/Sector Toggle are in Sticky Header in the Drawer ");
            System.out.println("Sticky3");
            assertTestCase.assertTrue(dashboardPage.isStickyHeaderDisplayed(), "Sticky header is not displayed");
            System.out.println("Sticky4");
            // Verify portfolio name in sticky header
            //assertTestCase.assertTrue(Driver.getDriver().findElement(By.xpath("//header[contains(@class,'Sticky')]//div[contains(text(),'Viewing " + portfolio + ": All Regions, All Sectors')]")).isDisplayed(), "Portfolio name is not displayed in sticky header");
            // assertTestCase.assertTrue(Driver.getDriver().findElements(By.xpath("//header[contains(@class,'Sticky')]//div[contains(text(),'Viewing " + portfolio + "')]")).get(0).isDisplayed(), "Portfolio name is not displayed in sticky header");

            System.out.println("Sticky5");
            // Verify physical risk climate tile details in sticky header
            String highestRiskHazardStatus = Driver.getDriver().findElement(By.xpath("//header//div[text()='Highest Risk Hazard']/..//span[2]")).getText();
            System.out.println("Sticky6");
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
            System.out.println("Sticky7");

            // Verify transition risk climate tile details in sticky header
            String temperatureAlignmentValue = Driver.getDriver().findElement(By.xpath("//header//div[text()='Temperature Alignment']/..//span[1]")).getText();
            System.out.println("Sticky8");
            ArrayList<String> carbonFootprintScores = new ArrayList<String>();
            carbonFootprintScores.add("Moderate");
            carbonFootprintScores.add("Significant");
            carbonFootprintScores.add("High");
            carbonFootprintScores.add("Intense");

            String carbonFootprintScore = Driver.getDriver().findElement(By.xpath("//header//div[text()='Carbon Footprint']/..//span[1]")).getText();
            assertTestCase.assertTrue(temperatureAlignmentValue.contains("°C")
                    && carbonFootprintScores.contains(carbonFootprintScore), "Transition Risk climate tile details are not displayed in sticky header");
            System.out.println("Sticky9");
            return true;

        } catch (Exception e) {
            System.out.println("Sticky10");
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyPhysicalRiskWidget() {
        try {
            WebElement highestRiskValue = Driver.getDriver().findElement(By.xpath("//div[@id='Highest_Risk_Hazard_id']//span[2]"));
            return physicalRiskCard.isDisplayed() &&
                    highestRiskHazard.isDisplayed() &&
                    highestRiskValue.getText() != "";
        } catch (Exception e) {
            return false;
        }
    }






    public boolean verifyFacilitiesExposedWidget() {
        try {
            WebElement facilitiesExposedValue = Driver.getDriver().findElement(By.xpath("//div[contains(@id,'Facilities_Exposed_to_')]//span[1]"));
            WebElement facilitiesExposedFlag = Driver.getDriver().findElement(By.xpath("//div[contains(@id,'Facilities_Exposed_to_')]//span[2]"));
            return facilitiesExposed.isDisplayed() &&
                    facilitiesExposedValue.getText() != "" &&
                    facilitiesExposedFlag.getText() != "";
        } catch (Exception e) {
            return false;
        }
    }


    public void selectViewMethodologies() {
        //  BrowserUtils.waitForInvisibility(btnViewMethodologies, 50);
        BrowserUtils.waitForClickablility(btnViewMethodologies, 50);
        btnViewMethodologies.click();
    }

    public boolean verifyMethodologiesPopup() {
        try {
            wait.until(ExpectedConditions.visibilityOf(methodologiesPopup));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void downloadDashboardExportFile() {
        navigateToPageFromMenu("Climate Dashboard");
        clickViewCompaniesAndInvestments();
        selectViewBySector();
        deleteDownloadFolder();
        clickExportCompaniesButton();
        closePortfolioExportDrawer();
        assertTestCase.assertTrue(filesCountInDownloadsFolder()>=1, "Verify download of export file");
    }


    public void clickHideLink() {
        hideLink.click();
    }

    public String getPortfolioMonitoringTableTitle() {
        return wait.until(ExpectedConditions.visibilityOf(portfolioMonitoringTitle)).getText();
    }

    public List<String> getPerformanceChartColumnNames() {
        return performanceChartColumns.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public void clickAndSelectAPerformanceChart(String performanceChartName) {
        BrowserUtils.scrollTo(performanceChart);
        System.out.println("Validating " + performanceChartName);
        wait.until(ExpectedConditions.elementToBeClickable(
                        Driver.getDriver()
                                .findElement(By.xpath("//button[./span[contains(text(),'" + performanceChartName + "')]]"))))
                .click();
        waitForDataLoadCompletion();
        BrowserUtils.scrollTo(performanceChart);
    }

    public boolean checkIfPerformanceChartHeaderIsHighlighted(String headerName) {
        WebElement headerElement = Driver.getDriver()
                .findElement(By.xpath("//*[starts-with(text(),'Performance')]/..//th[contains(text(),'" + headerName + "')]"));

        String actualColor = wait.until(ExpectedConditions.visibilityOf(headerElement)).getCssValue("background-color");
        System.out.println("actualColor = " + actualColor);
        return actualColor.equals("rgba(215, 237, 250, 1)");
    }

    public boolean checkIfAllCellsUnderSelectedPerformanceChartHeaderColumnAreHighlighted(String headerName) {
        int index = getPerformanceChartColumnIndexByHeader(headerName);

        List<WebElement> elementsInColumn = getPerformanceChartColumnElementsByColumnIndex(index);

        return elementsInColumn.size() ==
                (int) elementsInColumn.stream()
                        .filter(e -> e.getCssValue("background-color").equals("rgba(215, 237, 250, 1)")).count();
    }

    public void clickHeaderInPerformanceChart(String headerName) {
        wait.until(ExpectedConditions.elementToBeClickable(
                        Driver.getDriver()
                                .findElement(By.xpath("//*[starts-with(text(),'Performance')]/..//th[contains(text(),'" + headerName + "')]"))))
                .click();
        waitForDataLoadCompletion();
    }

    public int getPerformanceChartRowCount() {
        BrowserUtils.wait(10);
        return wait.until(ExpectedConditions.visibilityOfAllElements(performanceChartRows)).size();
    }

    public double calculateTotalInvestmentFromPerformanceChart() {
        wait.until(ExpectedConditions.visibilityOfAllElements(investmentsInPerformanceChart));
        return PortfolioUtilities.round(investmentsInPerformanceChart.stream()
                .mapToDouble(element -> Double.parseDouble(element.getText().substring(0, element.getText().indexOf("%")))).sum(), 1);
    }

    public double getTotalInvestmentInPerformanceChart() {
        String text = wait.until(ExpectedConditions.visibilityOf(totalInvestmentInPerformanceChart)).getText();
        return Double.parseDouble(text.substring(5, text.length() - 1));
    }

    public String getSelectedOptionFromPerformanceChartDropdown() {
        return wait.until(ExpectedConditions.elementToBeClickable(performanceChartDropdown)).getText();
    }

    public List<String> getPerformanceChartDropdownOptions() {
        BrowserUtils.scrollTo(performanceChartDropdown);
        wait.until(ExpectedConditions.elementToBeClickable(performanceChartDropdown));
        new Actions(Driver.getDriver()).moveToElement(performanceChartDropdown).pause(2000).click(performanceChartDropdown).pause(2000).build().perform();
        BrowserUtils.wait(2);
        wait.until(ExpectedConditions.visibilityOfAllElements(performanceChartDropdownOptions));
        System.out.println("performanceChartDropdownOptions " + performanceChartDropdownOptions.size());
        List<String> optionsList = performanceChartDropdownOptions.stream().map(WebElement::getText).collect(Collectors.toList());
        closeFilterByKeyboard();
        return optionsList;
    }

    public void selectPerformanceChartViewSizeFromDropdown(String size) {
        System.out.println("Performance Chart: Selecting the dropdown option - " + size);
        BrowserUtils.scrollTo(performanceChartDropdown);
        BrowserUtils.wait(5);
        BrowserUtils.waitForClickablility(performanceChartDropdown, 30).click();
        BrowserUtils.wait(10);
        //wait.until(ExpectedConditions.elementToBeClickable(performanceChartDropdown)).click();
//        wait.until(ExpectedConditions.visibilityOfAllElements(performanceChartDropdownOptions));
        performanceChartDropdownOptions.stream().filter(e -> e.getText().equals(size)).findFirst().get().click();
        waitForDataLoadCompletion();
        Actions action = new Actions(Driver.getDriver());
        action.sendKeys(Keys.ESCAPE).build().perform();
    }


    public List<String> getSummaryHeadersColumnNames() {
        wait.until(ExpectedConditions.visibilityOfAllElements(summaryHeaderTitles));

        return summaryHeaderTitles.stream().map(x -> {
            String title = x.getText();
            if (title.contains(" to ")) title = title.substring(0, title.indexOf(" to "));
            return title;
        }).collect(Collectors.toList());
    }

    public List<String> getSummaryHeadersBundleNames() {
        wait.until(ExpectedConditions.visibilityOfAllElements(summaryHeaderBundleTitles));
        return summaryHeaderBundleTitles.stream().map(x -> {
            String title = x.getText();
            return title;
        }).collect(Collectors.toList());
    }

    public void clickGeographicRiskDistributionDropdown() {
        BrowserUtils.scrollTo(geographicRiskDistributionDropdown);
        wait.until(ExpectedConditions.visibilityOf(geographicRiskMap));
        BrowserUtils.wait(2);
        wait.until(ExpectedConditions.visibilityOf(geographicRiskDistributionDropdown)).click();
    }

    public List<String> getAvailableResearchLinesFromGeographicRiskDistribution() {
        clickGeographicRiskDistributionDropdown();
        BrowserUtils.wait(2);
        List<String> researchLineList = Driver.getDriver().findElements(By.xpath("//ul//span/span"))
                .stream().map(WebElement::getText).collect(Collectors.toList());
        Actions action = new Actions(Driver.getDriver());
        action.sendKeys(Keys.ESCAPE).build().perform();
        return researchLineList;
    }

    public void selectResearchLineFromGeographicRiskMapDropDown(String researchLine) {
        BrowserUtils.scrollTo(geographicRiskDistributionDropdown);

        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(geographicRiskDistributionDropdown).pause(2000)
                .click(geographicRiskDistributionDropdown).pause(2000)
                .build().perform();
        WebElement researchLineOption = Driver.getDriver()
                .findElement(By.xpath("//ul//a[./span/span[text()='" + researchLine + "']]"));
        researchLineOption.click();
        BrowserUtils.wait(1);
      /*  actions.moveToElement(researchLineOption).pause(2000)
                .click(researchLineOption)
                .pause(2000).build().perform();*/

       /* clickGeographicRiskDistributionDropdown();
        try {
            BrowserUtils.clickWithJS(Driver.getDriver()
                    .findElement(By.xpath("//ul//a[./span/span[text()='" + researchLine + "']]")));
        } catch (StaleElementReferenceException e) {
            BrowserUtils.clickWithJS(Driver.getDriver()
                    .findElement(By.xpath("//ul//a[./span/span[text()='" + researchLine + "']]")));
        }*/

    }

    public boolean isGeographicRiskMapDisplayed() {
        try {
            BrowserUtils.scrollTo(geographicRiskMap);
            return geographicRiskMap.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getAvailableResearchLinesFromHeatMapResearchLineSelection() {
        return heatMapResearchLines.stream().map(WebElement::getText).collect(Collectors.toList());
    }
    public boolean verifyResearchLines() {
        List<String> availableResearchLines = getAvailableResearchLinesFromHeatMapResearchLineSelection();
        List<String> expResearchLines = Arrays.asList("Physical Risk: Operations Risk", "Physical Risk: Market Risk", "Physical Risk: Supply Chain Risk", "Physical Risk Management", "Temperature Alignment", "Carbon Footprint", "Green Share Assessment", "Brown Share Assessment");
        for (String line : availableResearchLines) {
            if (!expResearchLines.contains(line)) {
                System.out.println("Unverified Research Line = " + line);
                return false;
            }
        }
        return true;
    }

    public boolean verifyResearchLineColor(String color) {
        //color = color.toLowerCase();
        List<String> colors = Arrays.asList("#355b85", "#f5f7f7", "#ebf4fa", "#333333", "#ffffff");
        return colors.contains(color);

    }

    public void clickRandomCountryOnGeographicRiskMap() {
        wait.until(ExpectedConditions.visibilityOf(geographicRiskMap));

        //Some countries are not eligible to hover over/click by automation so filter them out

        countriesOnMap = countriesOnMap.stream()
                .filter(e ->
                        e.getSize().width < 100 &&
                                e.getSize().width > 30 &&
                                e.getSize().height < 80 &&
                                e.getSize().height > 15
                )
                .filter(e -> !e.getAttribute("class").contains("spain"))
                //.filter(e -> !e.getAttribute("class").contains("france"))
                .collect(Collectors.toList());

        Collections.shuffle(countriesOnMap);
        WebElement randomCountry = countriesOnMap.get(0);

        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(randomCountry).pause(2000).click().pause(2000).build().perform();

    }

    public boolean isCountryDistributionListDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(countryCardsList));
            return countryCardsList.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickRandomCountryInCountryDistributionList() {
        wait.until(ExpectedConditions.visibilityOfAllElements(countryCardsList));
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(countryCardsList.get(0)).pause(2000).click().pause(2000).build().perform();
        BrowserUtils.scrollTo(geographicRiskMap);
    }


    public boolean isHoldingsTitleDisplayedOnEntityList() {
        try {
            wait.until(ExpectedConditions.visibilityOf(holdingsTitleOnEntityList));
            System.out.println(holdingsTitleOnEntityList.getText());
            return holdingsTitleOnEntityList.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    public List<String> getExpectedListOfPerformanceChartColumnNames(EntitlementsBundles bundles) {
        switch (bundles) {
            case PHYSICAL_RISK:
                return Arrays.asList("Company", "% Investment", "Highest Risk Hazard", "Facilities Exposed to High Risk/Red Flag", "Physical Risk Management");
            case TRANSITION_RISK:
                return Arrays.asList("Company", "% Investment",
                        "Temperature Alignment",
                        "Carbon Footprint (tCO2eq)", "Green Share Assessment", "Brown Share Assessment");
            case ALL:
            case PHYSICAL_RISK_TRANSITION_RISK:
                return Arrays.asList("Company", "% Investment", "Highest Risk Hazard", "Facilities Exposed to High Risk/Red Flag",
                        "Physical Risk Management", "Temperature Alignment",
                        "Carbon Footprint (tCO2eq)", "Green Share Assessment", "Brown Share Assessment");
            default:
                Assert.fail("Bundle not found");
                return null;
        }
    }

    public List<WebElement> getPerformanceChartColumnElementsByColumnIndex(int index) {
        return performanceChart.findElements(By.xpath("./..//tbody//td[" + index + "]"));
    }

    public int getPerformanceChartColumnIndexByHeader(String header) {
        int index = 0;
        switch (header) {

            case "Company":
                index = 1;
                break;

            case "% Investment":
                index = 2;
                break;

            case "Total Critical Controversies":
                index = 3;
                break;

            case "Physical Risk Hazards":
            case "Highest Risk Hazard":
                index = 4;
                break;

            case "Facilities Exposed to High Risk/Red Flag":
                index = 5;
                break;

            case "Physical Risk Management":
                index = 6;
                break;

            case "Temperature Alignment":
                index = 7;
                break;

            case "Carbon Footprint":
            case "Carbon Footprint (tCO2eq)":
                index = 8;
                break;

            case "Brown Share":
            case "Brown Share Assessment":
                index = 9;
                break;

            case "Green Share":
            case "Green Share Assessment":
                index = 10;
                break;


        }
        return index;
    }


    public List<WebElement> getPerformanceChartColumnElementsByResearchLine(String researchLine) {
        int index = getPerformanceChartColumnIndexByHeader(researchLine);
        return getPerformanceChartColumnElementsByColumnIndex(index);
    }

    public List<String> getExpectedListOfSummaryHeadersTileNames(EntitlementsBundles bundles) {
        switch (bundles) {
            case PHYSICAL_RISK:
                return Arrays.asList("Highest Risk Hazard", "Facilities Exposed");
            case TRANSITION_RISK:
                return Arrays.asList("Temperature Alignment", "Carbon Footprint");
            case ALL:
            case PHYSICAL_RISK_TRANSITION_RISK:
                return Arrays.asList("Highest Risk Hazard", "Facilities Exposed", "Temperature Alignment", "Carbon Footprint");
            default:
                Assert.fail("Bundle not found");
                return null;
        }
    }

    public void selectOrDeselectResearchLineUnderAnalysisSection(String researchLine) {
        WebElement element = Driver.getDriver().findElement(By.xpath("//div[@heap_heatmap_id='researchline'][text()='" + researchLine + "']"));

        BrowserUtils.scrollTo(element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        BrowserUtils.clickWithJS(element);
        BrowserUtils.wait(10);
    }

    public void selectOrDeselectHeatMapSection(String researchLine) {
        WebElement element = Driver.getDriver().findElement(By.xpath("//div[text()='Analyze Companies by Range']/..//div[contains(text(),'" + researchLine + "')]"));

        BrowserUtils.scrollTo(element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        BrowserUtils.wait(5);
        BrowserUtils.clickWithJS(element);
        BrowserUtils.wait(5);
    }

    public void verifyBrownShareCoverageInHeatMapDescription() {
        BrowserUtils.wait(5);
        String uiBrownShareCoverage = heatMapBrownShareCoverage.getText();
        uiBrownShareCoverage = uiBrownShareCoverage.replace("Coverage: ", "").split("/")[0];
        DashboardQueries dashboardQueries = new DashboardQueries();
        DatabaseDriver.createDBConnection();
        int dbBrownShareCoverage = dashboardQueries.getPortfolioBrownShareInfo("00000000-0000-0000-0000-000000000000", "2022", "11").size();
        assertTestCase.assertEquals(Integer.parseInt(uiBrownShareCoverage), dbBrownShareCoverage, "Brown Share Coverage is not matching");
    }

    public void verifyBrownShareCategoryInHeatMapDrawer(List<String> expectedCategoriesList) {
        for (int i = 1; i <= 3; i++) {
            String expCategory = expectedCategoriesList.get(3 - i);
            String xpath = "//tr[" + i + "]//div[@heap_id='heatmap'][@heap_heatmap_id='gridcell']/span[2]";
            Driver.getDriver().findElement(By.xpath(xpath)).click();
            String actualCategory = heatMapDrawerYAxisColumnName.getText();
            assertTestCase.assertEquals(expCategory, actualCategory, "Expected Category is not available in the drawer");
        }
    }

    public List<Map<String, String>> getBrownShareDataFromHeatMapDrawer() {
        List<Map<String, String>> uiBrownShareHeatMapData = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            String xpath = "//tr[" + i + "]//div[@heap_id='heatmap'][@heap_heatmap_id='gridcell']/span[2]";
            Driver.getDriver().findElement(By.xpath(xpath)).click();
            String scoreCategory = heatMapDrawerYAxisColumnName.getText();
            String recordsCountXpath = "//ul[@class='list']/li";
            int recordsCount = Driver.getDriver().findElements(By.xpath(recordsCountXpath)).size();
            for (int j = 1; j <= recordsCount; j++) {
                Map<String, String> companyDetails = new HashMap<>();
                String companyName = Driver.getDriver().findElement(By.xpath(recordsCountXpath + "[" + j + "]//h3/span")).getText();
                String investmentScore = Driver.getDriver().findElement(By.xpath(recordsCountXpath + "[" + j + "]//h4")).getText();
                String scoreRange = Driver.getDriver().findElement(By.xpath(recordsCountXpath + "[" + j + "]//div[text()]")).getText();
                companyDetails.put("COMPANY_NAME", companyName);
                companyDetails.put("INVESTMENT_SCORE", investmentScore);
                companyDetails.put("SCORE_RANGE", scoreRange);
                companyDetails.put("SCORE_CATEGORY", scoreCategory);
                uiBrownShareHeatMapData.add(companyDetails);
            }
        }
        return uiBrownShareHeatMapData;
    }

    public boolean verifyHeatMapResearchLineTitle(int buttonIndex) {
        String buttonText = heatMapResearchLines.get(buttonIndex).getText();

        String yTitle = heatMapYAxisIndicatorTitle.getText();
        if (buttonText.contains(yTitle))
            return true;
        try {
            String xTitle = heatMapXAxisIndicatorTitle.getText();
            if (buttonText.contains(xTitle))
                return true;
            System.out.println("X Axis Indicator Title = " + xTitle);
        } catch (NoSuchElementException exp) {
            exp.printStackTrace();
        }
        System.out.println("Y Axis Indicator Title = " + yTitle);
        System.out.println("Research Line Button Text= " + buttonText);
        return false;
    }

    public boolean heatmapXAxisIsAvailable() {
        try {
            return heatMapXAxisIndicatorTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateSelectTwoStaticText() {
        try {
            return selectTwoLabel.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyResearchLineDescriptionFromHeatmapSection(String section, String description) {
        //selectOrDeselectHeatMapSection(section);
        String actualDescription = Driver.getDriver().findElement(By.xpath("//h3[text()='" + section + "']/../p")).getText();
        // selectOrDeselectHeatMapSection(section);
        return description.equals(actualDescription);
    }

    public boolean isHeatMapTempratureAlignmentAvailable() {
        try {
            return heatMapTemperatureAlignment.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyHeatMapTableData() {

        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 5; j++) {
                String xpath = "(//div[text()='Analyze Companies by Range']/../../..//table)[2]//tr[" + i + "]/td[" + j + "]//span[2]";
                String cellValue = Driver.getDriver().findElement(By.xpath(xpath)).getText();
                System.out.println(i + "," + j + "-" + cellValue);
                if (!cellValue.endsWith("%")) {
                    return false;
                }
                if (cellValue.contains(".")) {
                    String decimalValue = cellValue.substring(cellValue.indexOf('.') + 1, cellValue.indexOf('%'));
                    if (decimalValue.length() != 2) {
                        return false;
                    }
                } else {
                    if (!cellValue.equals("0%")) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean verifyHeatMapTableAxis(String xAxis, String yAxis) {
        if (!Driver.getDriver().findElement(By.xpath("(//div[text()='Compare Research Lines']/../../..//table)[1]//thead//div")).getText().equals(xAxis)) {
            return false;
        }
        String expValues[] = {"Above 2°C", "2°C", "Below 2°C", "Well Below 2°C", "No Info"};
        for (int i = 0; i < 5; i++) {
            if (!Driver.getDriver().findElement(By.xpath("(//div[text()='Compare Research Lines']/../../..//table)[1]//tr[" + (i + 1) + "]/td[1]/div/span[1]")).getText().equals(expValues[i])) {
                return false;
            }
        }

        if (!Driver.getDriver().findElement(By.xpath("(//div[text()='Compare Research Lines']/../../..//table)[3]//thead//div")).getText().equals(yAxis)) {
            return false;
        }
        return true;
    }


    public List<String> getExpectedListOfSummaryHeaderBundleNames(EntitlementsBundles bundles) {
        switch (bundles) {
            case PHYSICAL_RISK:
                return Arrays.asList("Physical Risk");
            case TRANSITION_RISK:
                return Arrays.asList("Transition Risk");
            case ALL:
            case PHYSICAL_RISK_TRANSITION_RISK:
                return Arrays.asList("Physical Risk", "Transition Risk");
            default:
                Assert.fail("Bundle not found");
                return null;
        }
    }

    public List<String> getExpectedListOfGeoMapResearchLines(EntitlementsBundles bundles) {
        switch (bundles) {
            case PHYSICAL_RISK:
                return Arrays.asList("Operations Risk", "Market Risk", "Supply Chain Risk", "Physical Risk Management");
            //JULY, "Physical Risk Management");
            case TRANSITION_RISK:
                return Arrays.asList("Temperature Alignment",
                        "Carbon Footprint", "Green Share Assessment", "Brown Share Assessment");
            case PHYSICAL_RISK_TRANSITION_RISK:
            case ALL:
                return Arrays.asList("Operations Risk", "Market Risk", "Supply Chain Risk",
                        "Physical Risk Management", "Temperature Alignment",
                        "Carbon Footprint", "Green Share Assessment", "Brown Share Assessment");
            default:
                Assert.fail("Bundle not found");
                return null;
        }
    }

    public List<String> getExpectedListOfHeatMapResearchLines(EntitlementsBundles bundles) {
        switch (bundles) {
            case PHYSICAL_RISK:
                return Arrays.asList("Physical Risk: Operations Risk", "Physical Risk: Market Risk", "Physical Risk: Supply Chain Risk", "Physical Risk Management");
            case TRANSITION_RISK:
                return Arrays.asList("Temperature Alignment",
                        "Carbon Footprint", "Green Share Assessment", "Brown Share Assessment");
            case PHYSICAL_RISK_TRANSITION_RISK:
            case ALL:
                return Arrays.asList("Physical Risk: Operations Risk", "Physical Risk: Market Risk", "Physical Risk: Supply Chain Risk",
                        "Physical Risk Management", "Temperature Alignment", "Carbon Footprint", "Green Share Assessment", "Brown Share Assessment");
            default:
                Assert.fail("Bundle not found");
                return null;
        }
    }

    public String getPortfolioNameInSummaryHeaders() {
        return wait.until(ExpectedConditions.visibilityOf(portfolioNameInSummaryHeaders)).getText();
    }

    public boolean verifyHyperlink(String xpath) {
        try {
            WebElement element = Driver.getDriver().findElement(By.xpath(xpath));
            String colText = BrowserUtils.waitForClickablility(element, 30).getText();
            element.click();
//TODO better approach needed
            BrowserUtils.waitForVisibility(Driver.getDriver().findElement(By.xpath("//*[contains(text(),\"" + colText + "\")]")), 30);
            Driver.getDriver().findElement(By.xpath("//*[text()='ESC']/following-sibling::* | //span[text()='X'] | //span[@class='close'] | //div[@role='dialog']/div/div[2]")).click();
            //""//"//span[text()='X'] | //span[@class='close'] | //div[@role='dialog']/div/div[2]")).click();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyPortfolioAnalysisHyperlinks(String section, int columnNo) {
        try {
            String xpath = "(//*[text()='" + section + "']/../..//table//tr/td[" + columnNo + "]/div)[1]";

            if (section.equals("Laggards by Score")) {
                xpath = "((//*[text()='" + section + "']/../..//table)[2]//tr/td[" + columnNo + "]/div)[1]";
            }

            return verifyHyperlink(xpath);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyRegionAndCountryHyperlinks(String section, int columnNo) {
        try {
            WebElement element = Driver.getDriver().findElement(By.xpath("//div[text()='" + section + "']"));
            BrowserUtils.scrollTo(element);
            BrowserUtils.waitForClickablility(element, 30);
            element.click();

            String xpath = "((//*[text()='" + section + "']/../../../..//table)[2]//tr/td[" + columnNo + "]/div)[1]";
            Boolean bln = verifyHyperlink(xpath);
            if (bln) {
                Driver.getDriver().findElement(By.xpath("//a[text()='hide']")).click();
            }
            return bln;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyDashboardHyperlinks(String section, int columnNo) {
        try {
            String xpath = "(//*[text()='" + section + "']/../..//table//tr/td[" + columnNo + "]/span)[1]";
            System.out.println("xpath = " + xpath);
            return verifyHyperlink(xpath);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyGeographicRiskHyperlink() {
        try {
            Driver.getDriver().findElements(By.xpath("//div[@id='geographic-risk-section-test-id']/../../..//div/h3")).get(0).click();
            String xpath = "(//ul[@class='entityList']//h3/span)[1]";
            return verifyHyperlink(xpath);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyHeatMapHyperlink() {
        try {
            Driver.getDriver().findElement(By.xpath("(//div[text()='Compare Research Lines']/../../..//table)[2]//tr[1]/td[2]")).click();
            String xpath = "(//ul[@class='list']//h3/span)[1]";
            return verifyHyperlink(xpath);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyPanel(String filterBy) {
        if (filterBy.toLowerCase().equals("sector")) {
            viewBySectorBtn.click();
            BrowserUtils.wait(5);
            if (!viewBySectorBtn.isEnabled()) return false;
            DatabaseDriver.createDBConnection();
            String query = "select * from (select sector,count(sector) as count from df_portfolio where portfolio_id='00000000-0000-0000-0000-000000000000' group by sector order by sector) where count>20";
            int numberOfMoreEntities = DatabaseDriver.getQueryResultMap(query).size();
            System.out.println("Number of Sectors has more than 20 entities = " + numberOfMoreEntities);
            System.out.println("moreCompaniesInThisSectorText.size() = " + moreCompaniesInThisSectorText.size());
            if (numberOfMoreEntities != moreCompaniesInThisSectorText.size()) return false;
        }
        if (filterBy.toLowerCase().equals("region")) {
            viewByRegionBtn.click();
            BrowserUtils.wait(5);
            if (!viewByRegionBtn.isEnabled()) return false;
            DatabaseDriver.createDBConnection();
            String query = "select * from (select region,count(region) as count from df_portfolio where portfolio_id='00000000-0000-0000-0000-000000000000' group by region order by region) where count>20";
            int numberOfMoreRegions = DatabaseDriver.getQueryResultMap(query).size();
            System.out.println("Number of Regions has more than 20 entities = " + numberOfMoreRegions);
            System.out.println("moreCompaniesInThisSectorText.size() = " + moreCompaniesInThisRegionText.size());
            if (numberOfMoreRegions != moreCompaniesInThisRegionText.size()) return false;

        }
        //classNames.forEach(i-> System.out.println(i.getText()));
        System.out.println("Number of Classes = " + panelClassNames.size());
        System.out.println("Number of Entities = " + panelEntityNames.size());
        System.out.println("Number of Controversies = " + panelControversies.size());
        System.out.println("investments = " + panelInvestments.size());
        System.out.println("regionsAndCountries = " + panelRegionsAndCountries.size());
        System.out.println("panelShowingText = " + panelShowingText.getText());
        System.out.println("panelExportText = " + panelExportText.getText());
        if (panelClassNames.isEmpty()) return false;
        if (panelEntityNames.isEmpty()) return false;
        if (panelControversies.isEmpty()) return false;
        if (panelInvestments.isEmpty()) return false;
        if (panelRegionsAndCountries.isEmpty()) return false;
        if (panelExportText.getText().isEmpty()) return false;
        if (panelShowingText.getText().isEmpty()) return false;
        if (!panelExportToExcelBtn.isDisplayed()) return false;
        String[] numbers = panelShowingText.getText().split("\\D+");
        System.out.println("Arrays.toString(numbers = " + Arrays.toString(numbers));
        if (panelEntityNames.size() != Integer.parseInt(numbers[1])) return false;
        int numberInExportText = Integer.parseInt(panelExportText.getText().replaceAll("\\D", ""));
        if (Integer.parseInt(numbers[2]) != numberInExportText) return false;

        //Remove Finance from from expSectorTitles.
        //A bug ticket created for it, It is just added to complete automation. Normally there shouldn't be such sector title.
        List<String> expSectorTitles = Arrays.asList("Basic Materials", "Consumer Discretionary", "Communication", "Financials", "Energy", "Real Estate", "Sovereign", "Technology", "Utilities", "Health Care", "Consumer Staples", "Industry");
        List<String> expRegionTitles = Arrays.asList("Americas", "Asia Pacific", "Europe, Middle East & Africa");
        if (filterBy.toLowerCase().equals("sector")) {
            for (int i = 0; i < panelClassNames.size() - 1; i++) {
                if (!expSectorTitles.contains(panelClassNames.get(i).getText())) {
                    System.out.println("classNames = " + panelClassNames.get(i).getText());
                    return false;
                }
            }
        }


        if (filterBy.toLowerCase().equals("region")) {
            for (int i = 0; i < panelClassNames.size() - 1; i++) {
                if (!expRegionTitles.contains(panelClassNames.get(i).getText())) {
                    System.out.println("Region Name = " + panelClassNames.get(i).getText());
                    return false;
                }
            }
        }


        return true;

    }

    public void checkControversiesAreInLast60Days() {
        String todayDate = DateTimeUtilities.getCurrentDate("MMMM d, yyyy");
        controversiesTableDates.forEach(e -> {
            String date = e.getText();
            int difference = (int) DateTimeUtilities.getDayDifference(date, todayDate, "MMMM d, yyyy");
            System.out.println("Date Difference:" + difference + " days");
            assertTestCase.assertTrue(difference <= 60,
                    "Verify Controversies are displayed by last 60 days");

        });
    }

    public void checkControversiesAreInSelectedMonthAndYear() {
        controversiesTableDates.forEach(e -> {
            String date = e.getText();
            assertTestCase.assertEquals(DateTimeUtilities.getMonthAndYearFromDate(date), particularMonthFilterButtonOnPortfolioMonitoring.getText(),
                    "Verify Controversies are displayed by selected month:" + particularMonthFilterButtonOnPortfolioMonitoring.getText());

        });
    }

    public boolean checkIfAllControversiesAreCritical() {
        try {
            if (controversiesTableCriticalSeverityIcons.size() > 0) {
                controversiesTableCriticalSeverityIcons.forEach(e -> assertTestCase.assertTrue(e.isDisplayed(), "Verify Critical Icon"));
                return true;
            } else if (controversiesTableCriticalSeverityIcons.size() == 0) {
                return false;
            }
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("Not Critical Controversy");
            return false;
        }
    }


    public void verifyControversiesAndValidateContentOfEachControversy() {

        for (int i = 0; i < controversiesTableTitles.size(); i++) {

            assertTestCase.assertTrue(DateTimeUtilities.isValidDate(controversiesTableDates.get(i).getText()), "Validate Date in table");

            String controversySector = controversiesTableSectors.get(i).getText();
            assertTestCase.assertTrue(SectorUtilities.isMESGSector(controversySector), "Verify Sector=" + controversySector);

            assertTestCase.assertFalse(controversiesTableCompanyNames.get(i).getText().isEmpty(), "Verify Company Name");

            if (controversiesTableCompanyNames.get(i).getText().length() > 75) {
                assertTestCase.assertTrue(controversiesTableCompanyNames.get(i).getText().endsWith("..."),
                        "Long text is truncated with ellipsis:" + controversiesTableCompanyNames.get(i).getText());
                String tooltip = controversiesTableCompanyNames.get(i).getAttribute("title");
                assertTestCase.assertTrue(tooltip.length() > controversiesTableCompanyNames.get(i).getText().length(),
                        "Tooltip has full text:" + tooltip);
            }

            assertTestCase.assertFalse(controversiesTableCountries.get(i).getText().isEmpty(), "Verify Country");


            controversiesTableTitles.get(i).click();
            BrowserUtils.wait(3);


            WebElement title = Driver.getDriver().findElement(controversiesDetailModalTitle);
            System.out.println("actTitle.getText() = " + title.getText());
            assertTestCase.assertEquals(controversiesTableTitles.get(i).getText(),
                    title.getText(), "Verify Controversy Title");

            WebElement date = Driver.getDriver().findElement(controversiesDetailModalDate);
            System.out.println("actDate.getText() = " + date.getText());
            assertTestCase.assertEquals(controversiesTableDates.get(i).getText(),
                    date.getText(), "Verify Controversy Details");

            WebElement severity = Driver.getDriver().findElement(controversiesDetailModalSeverity);
            String actSeverity = severity.getText();
            boolean isCriticalControversy = false;
            try {
                isCriticalControversy = controversiesTableSeverity.get(i).findElement(By.xpath(".//*[local-name()='svg']")).isDisplayed();
            } catch (NoSuchElementException e) {
                System.out.println("Not Critical Controversy");
            }

            if (isCriticalControversy) {
                assertTestCase.assertEquals("Critical", actSeverity, "Verify Critical Controversy Severity");
            } else {
                List<String> expSeverity = Arrays.asList("Minor", "Significant", "Critical", "High");
                System.out.println("actSeverity = " + actSeverity);
                assertTestCase.assertTrue(expSeverity.contains(actSeverity), "Verify Controversy Severity");
            }


            WebElement responsiveness = Driver.getDriver().findElement(controversiesDetailModalResponsiveness);
            String actResponsiveness = responsiveness.getText();
            List<String> expResponsiveness = Arrays.asList("Proactive", "Remediative", "Reactive", "Non Communicative");
            System.out.println("actResponsiveness = " + actResponsiveness);
            assertTestCase.assertTrue(expResponsiveness.contains(actResponsiveness), "Verify Responsiveness");

            String actRiskMitigation = Driver.getDriver().findElement(By.xpath("//p/span[3]")).getText();
            System.out.println("actRiskMitigation = " + actRiskMitigation);
            List<String> expRiskMitigation = Arrays.asList("Advanced", "Robust", "Limited", "Weak");
            assertTestCase.assertTrue(expRiskMitigation.contains(actRiskMitigation), "Verify Risk Migration");

            String actControversyDescription = Driver.getDriver().findElement(By.xpath("//p/following-sibling::div/div/p")).getText();
            System.out.println("actControversyDescription.length() = " + actControversyDescription.length());
            assertTestCase.assertFalse(actControversyDescription.isEmpty(), "VErify Controversy has a description");

            String actSources = Driver.getDriver().findElement(By.xpath("//p/following-sibling::div/p")).getText();
            System.out.println("actSources.length() = " + actSources.length());
            assertTestCase.assertFalse(actSources.isEmpty(), "Verify Sources");

            controversyCloseBtn.click();
        }
    }


    public void waitForHeatMap() {
        BrowserUtils.waitForVisibility(heatMapYAxisIndicatorTitle, 10);
        BrowserUtils.waitForVisibility(heatMapCells.get(0), 10);
        BrowserUtils.waitForVisibility(heatMapActiveResearchLineInfo.get(0), 10);
        BrowserUtils.wait(2);
        waitUntilHeatMapResearchLinesAreClickable();
    }

    public void waitUntilHeatMapResearchLinesAreClickable() {
        heatMapResearchLines.forEach(each ->
                wait.until(ExpectedConditions.attributeToBe(each, "cursor", "pointer")));
    }

    public boolean isHeatMapEntityListDrawerDisplayed() {
        try {
            return heatMapEntityDrawerWidget.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    //this method deselects all other heat map research lines and selects only one research line by index
    public void selectOneResearchLineOnHeatMap(int selection) {
        //First make sure targeted line is selected
        BrowserUtils.wait(2);
        selectResearchLineForHeatMap(heatMapResearchLines.get(selection).getText());
        //Then go through each button
        for (int i = 0; i < heatMapResearchLines.size(); i++) {
            if (heatMapResearchLines.get(i).getText().equals(heatMapResearchLines.get(selection).getText())) {
                continue;
            }
            if (verifySelectedResearchLineForHeatMap(heatMapResearchLines.get(i).getText())) {
                deselectResearchLineForHeatMap(heatMapResearchLines.get(i).getText());
            }
        }
    }


    public String getExpectedResearchlineName(String researchLine) {
        switch (researchLine) {
            case "Operations Risk":
            case "Market Risk":
            case "Supply Chain Risk":
                return "physicalriskhazards";
            case "Temperature Alignment":
                return "temperaturealignment";
            case "Physical Risk Management":
                return "physicalriskmanagement";
            case "Carbon Footprint":
                return "carbonfootprint";
            case "Brown Share Assessment":
                return "brownshareassessment";
            case "Green Share Assessment":
                return "greenshareassessment";
            default:
                Assert.fail("Reseacrch line not found");
                return null;

        }
    }



    public List<String> getMethodologiesSections() {
        List<String> methodologySections = new ArrayList<>();
        for (WebElement element : methodologySectionNames) {
            methodologySections.add(element.getText());
        }
        return methodologySections;
    }


    public boolean verifySelectedResearchLineForHeatMap(String researchLine) {
        for (WebElement line : heatMapResearchLines) {
            if (line.getText().equalsIgnoreCase(researchLine)) {
                String color = Color.fromString(line.getCssValue("background-color")).asHex();
                if (color.equalsIgnoreCase("#355b85")) {
                    System.out.println("Research Line is selected: " + researchLine);
                    return true;
                } else {
                    System.out.println("Research Line is not selected: " + researchLine);
                    return false;
                }
            }
        }
        System.out.println("Research Line not found: " + researchLine);
        return false;
    }

    public String getHeatMapPortfolioAverage() {
        String portfolioAverage = heatmapPortfolioAverage.getText();
        return portfolioAverage.substring(portfolioAverage.lastIndexOf(":") + 1).trim();

    }

    public boolean selectResearchLineForHeatMap(String researchLine) {
        for (WebElement line : heatMapResearchLines) {
            if (line.getText().equalsIgnoreCase(researchLine)) {
                if (!verifySelectedResearchLineForHeatMap(researchLine)) {
                    line.click();
                    System.out.println("Research line selected: " + researchLine);
                    return true;
                } else {
                    System.out.println("Research line is already selected");
                    return true;
                }
            }
        }
        System.out.println("Research line is not found");
        return false;
    }


    public boolean deselectResearchLineForHeatMap(String researchLine) {
        for (WebElement line : heatMapResearchLines) {
            if (line.getText().equalsIgnoreCase(researchLine)) {
                while (verifySelectedResearchLineForHeatMap(researchLine)) {
                    System.out.println("Deselecting research line : " + researchLine);
                    line.click();
                    BrowserUtils.wait(1);
                }
                System.out.println("Research line is already deselected");
                return true;
            }
        }
        System.out.println("Research line is not found");
        return false;
    }

    public boolean verifyHeatMapTitle(String title) {
        String xpath = "//div[@id='heatmapentity-test-id']//div[text()='" + title + "']";
        try {
            BrowserUtils.waitForVisibility(Driver.getDriver().findElement(By.xpath(xpath)), 30);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void selectRandomCell() {
        Random random = new Random();
        int randomCell;
        do {
            randomCell = random.nextInt(heatMapCells.size());
        } while (heatMapCells.get(randomCell).getText().equals("0%"));
        BrowserUtils.scrollTo(heatMapCells.get(randomCell)).click();
    }



    public void verifyTotalControversies() {
        if (PerformanceChartError.size() < 1) {
            for (WebElement e : TotalCriticalControversiesTabledata) {
                if (e.getText().equals("")) {
                    assertTestCase.assertTrue(true, "Validated if controversies are blank");
                } else if (Integer.valueOf(e.getText()) == 0) {
                    assertTestCase.assertEquals(Color.fromString(e.getCssValue("color")).asHex(), "#263238", "Validated if controversies are Zero");
                } else if (Integer.valueOf(e.getText()) > 0) {
                    assertTestCase.assertEquals(Color.fromString(e.getCssValue("color")).asHex(), "#b31717", "Validated if controversies are Zero");
                }
            }
        } else {
            throw new SkipException("Performance chart data not displayed");
        }
    }

    public boolean isOverAllESGColumAvailable() {
        try {
            return OverallESGScoreColoumn.isDisplayed();
        } catch (Exception e) {
            return false;
        }


    }

    public boolean isTotalControversiesColumAvailable() {
        try {
            return TotalCriticalControversiesColoumn.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void openCoverageDrawer(){
        BrowserUtils.waitForVisibility(viewAllCompaniesButton, 30);
        assertTestCase.assertTrue(viewAllCompaniesButton.isDisplayed(),
                "User can see and click on View Companies and Investments in Portfolio link on dashboard.");
        BrowserUtils.waitForClickablility(viewAllCompaniesButton,10).click();
    }

    public String getSelectedFilterValue(String filterOption){
        String[] options = coverageLink.getText().split(", ");
        System.out.println("Arrays.toString() = " + Arrays.toString(options));
        switch (filterOption.toLowerCase()){
            case "regions":
                return options[0].replaceAll("Viewing data in ","");
            case "sectors":
                return options[1].trim();
            case "date":
                return options[2].replaceAll("at the end of ","");
        }
        System.out.println("Filter option not found");
        return "";
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
    @FindBy(xpath = "//*[@id=\"mini-0\"]/div[2]/span")
    public WebElement searchResultLineOne;
    public void validateEntitiesWithOnlyEsgDataDontShowInSearch(String entity) {

        String expectedSearchResult = BrowserUtils.waitForVisibility(searchResultLineOne, 20).getText();
        assertTestCase.assertTrue(!entity.equals(searchResultLineOne.getText()), "Validating that " + entity + " which is an Entity with ESG data only is not returned or suggested in search option : Status Done");

    }

    @FindBy(xpath = "//li[contains(@class,'MuiButtonBase')]")
    public List<WebElement> menuItems;
    public void validateCalculationsFromGlobalMenuIsHidden() {
        System.out.println("------------Verifying that Calculations is removed from global menu-------------------");
        for (WebElement e : menuItems) {
            String menuItemText = e.getText();
            System.out.println("verify that " + menuItemText + " is not equal to Calculations");
            assertTestCase.assertTrue(!menuItemText.equals("Calculations"), "Verify the removal of 'Calculations' link or tab from the global menu : Status Done");
        }
    }


    @FindBy(xpath = "//*[@heap_menu='Climate Portfolio Analysis']")
    public WebElement portfolioAnalysisTab;

    @FindBy(xpath = "//*[@heap_menu='ESG Reporting Portal']")
    public WebElement reportingPortalTab;

    @FindBy(xpath = "//*[@id='topbar-appbar-test-id']/div/li")
    public WebElement pageHeader;

    public void clickOnClimateDashboardMenuOption() {

        BrowserUtils.waitForClickablility(dashboardButton, 15);
        dashboardButton.click();
    }

    public void clickOnPortfolioAnalysisMenuOption() {
        BrowserUtils.waitForClickablility(portfolioAnalysisTab, 15);
        portfolioAnalysisTab.click();
    }

    public void clickOnReportingPortalMenuOption() {
        BrowserUtils.waitForClickablility(reportingPortalTab, 15);
        reportingPortalTab.click();
    }

    public void validateNewReportingMenuName() {
        System.out.println("--------------Verifying the new naming for reporting menu option----------------");
        String reportingPortalTitle = reportingPortalTab.getText();
        System.out.println("the actual reporting portal title is : " + reportingPortalTitle);
        assertTestCase.assertEquals(reportingPortalTitle, "ESG Reporting Portal", "Status Done : Verifying that reporting portal is ESG Reporting Portal");

    }
    public void validateNewDashboardMenuName(){
        System.out.println("--------------Verifying the new naming for Dashboard menu option----------------");

        String dashboardTitle = BrowserUtils.waitForClickablility(dashboardButton, 15).getText();
        System.out.println("the actual dashboard title is : " + dashboardTitle);
        assertTestCase.assertEquals(dashboardTitle, "Climate Dashboard", "Status Done : Verifying that Dashboard name is Climate Dashboard ");


    }
    public void validateNewPortfolioAnalysisMenuName(){
        System.out.println("--------------Verifying the new naming for Portfolio Analysis menu option ----------------");

        String portfolioAnalysisTitle = portfolioAnalysisTab.getText();
        System.out.println("the actual portfolio analysis title is : " + portfolioAnalysisTitle);
        assertTestCase.assertEquals(portfolioAnalysisTitle, "Climate Portfolio Analysis", "Status Done : Verifying that portfolio analysis is Climate Portfolio Analysis");

    }

    public void validateClimateDashboardPageHeaders() {
        System.out.println("--------------Verifying the Climate Dashboard page headers----------------");
        BrowserUtils.waitForClickablility(dashboardButton, 10);

        dashboardButton.click();
        BrowserUtils.waitForClickablility(pageHeader, 10).click();
        String actualDashboardHeader = pageHeader.getText();
        String expectedDashboardHeader = "Climate Dashboard";
        assertTestCase.assertEquals(actualDashboardHeader, expectedDashboardHeader, "Verifying dashboard header is equal to Climate Dashboard : Status Done");
    }

    public void validateClimatePortfolioAnalysisPageHeaders() {
        System.out.println("--------------Validating the climate portfolio analysis page headers----------------");
        BrowserUtils.waitForClickablility(pageHeader, 10).click();
        String actualPortfolioTabHeader = pageHeader.getText();
        String expectedPortfolioTabHeader = "Climate Portfolio Analysis";
        assertTestCase.assertEquals(actualPortfolioTabHeader, expectedPortfolioTabHeader, "Verifying portfolio Analysis header is equal to Climate Portfolio Analysis : Status Done");

    }

    public void validateReportingPortalPageHeaders() {
        System.out.println("--------------Validating Reporting portal page headers----------------");
        BrowserUtils.waitForClickablility(pageHeader, 20).click();
        String actualReportingTabHeader = pageHeader.getText();
        String expectedReportingTabHeader = "ESG Reporting Portal";
        assertTestCase.assertEquals(actualReportingTabHeader, expectedReportingTabHeader, "Verifying ESG Reporting Portal header is equal to ESG Reporting Portal : Status Done");

    }


}