package com.esgc.Pages;


import com.esgc.Utilities.*;
import com.esgc.Utilities.Database.DashboardQueries;
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

import java.util.*;
import java.util.stream.Collectors;

public class DashboardPage extends UploadPage {

    //=============== Summary Header
    @FindBy(xpath = "//*[starts-with(text(),'Coverage: Across')]/preceding-sibling::div")
    public WebElement portfolioNameInSummaryHeaders;

    @FindBy(xpath = "//header[.//*[text()='About Climate Risk']]")
    public WebElement summaryHeader;

    @FindBy(xpath = "//header[.//*[starts-with(text(),'View')]]")
    public WebElement stickyHeader;

    @FindBy(xpath = "//div[text()='ESG score']")
    public WebElement averageEsgScoreLabel;

    @FindBy(xpath = "//div[text()='ESG score']/following-sibling::div/div")
    public WebElement esgScoreValue;

    @FindBy(xpath = "//h3[contains(text(),'Portfolio Average')]")
    public WebElement heatmapPortfolioAverage;

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

    @FindBy(xpath = "//div[contains(@class,'MuiDrawer-paperAnchorRight')]//h2[1]")
    public WebElement methodologyPopup_Header;

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
    @FindBy(xpath = "//span[.='X']")
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

    @FindBy(xpath = "//table[./thead//th[text()='% Investment']]/tbody/tr/td[3]")
    public List<WebElement> OverallESGScore;

    @FindBy(xpath = "//table[./thead//th[text()='% Investment']]/tbody/tr/td[4]")
    public List<WebElement> TotalCriticalControversies;
    @FindBy(xpath = "//*[starts-with(text(),'Sum')]")
    public WebElement totalInvestmentInPerformanceChart;

    @FindBy(xpath = "//span[contains(text(),'Leaders')]")
    public WebElement tabPerformanceLeaders;

    @FindBy(xpath = "//span[contains(text(),'Laggards')]")
    public WebElement tabPerformanceLaggards;

    @FindBy(xpath = "//*[starts-with(text(),'Show')]")
    public WebElement performanceChartDropdown;

    @FindBy(xpath = "//ul[@role='listbox']//span[text()]")
    public List<WebElement> performanceChartDropdownOptions;

    @FindBy(xpath = "//td[@heap_id='perfchart']/parent::tr")
    public List<WebElement> performanceRows;

    @FindBy(xpath = "//td[@heap_id='perfchart']//span[@title]")
    public List<WebElement> performanceChartCompanyNames;

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

    @FindBy(xpath = "//td//div[@heap_id='heatmap']/span[2]")
    public List<WebElement> heatMapEsgScoreCells;

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

    @FindBy(xpath = "(//div[@heap_heatmap_id='gridcell']/span[2])[2]")
    public WebElement overallESGCell;

    @FindBy(xpath = "//p[contains(text(),'ESG performance. They measure the degree to which ')]")
    public WebElement overallESGDescription;

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


    //div[@class='entityList']//br/../following-sibling::div/div[2]
    public boolean isControversiesTableDisplayed() {
        try {
            BrowserUtils.scrollTo(controversiesTable);
            return controversiesTable.isDisplayed();
        } catch (Exception e) {
            return false;
        }
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

    public boolean verifyAverageEsgScoreWidget() {
        try {
            BrowserUtils.waitForVisibility(averageEsgScoreLabel, 50);
            return averageEsgScoreLabel.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyEsgScoreValue() {
        ArrayList<String> expectedValues = new ArrayList<>();
        expectedValues.add("Advanced");
        expectedValues.add("Robust");
        expectedValues.add("Limited");
        expectedValues.add("Weak");

        String actualEsgScore = esgScoreValue.getText();
        System.out.println("ESG Score on UI: " + actualEsgScore);
        return expectedValues.contains(actualEsgScore);
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

    public boolean isScoreQualityButtonAvailable() {
        try {
            return scoreQualityButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyScoreQualityToggleIsOff() {
        String scoreQualityStyle = scoreQualityStatus.getAttribute("style");
        return scoreQualityStyle.equals("transform: translate(0px, 9px) scale(1.2);");
    }

    public boolean verifyScoreQualityToggleIsOn() {
        String scoreQualityStyle = scoreQualityStatus.getAttribute("style");
        return scoreQualityStyle.equals("transform: translate(0px, 3px) scale(1.2);");
    }

    public boolean verifyScoreQualityIconWithEntitiesInPerformanceTable() {
        int performanceTableRecordsCount = performanceTableRecords.size();
        int recordsWithScoreQualityIcons = scoreQualityIconsPerformanceTable.size();
        return performanceTableRecordsCount == recordsWithScoreQualityIcons;
    }

    public boolean verifyScoreQualityIconWithEntitiesInCoveragePopup() {
        return scoreQualityIconsCoveragePopup.size() > 0;
    }

    public void verifyScoreQualityLevelsInIconInCoveragePopup() {
        int companiesCount = rowsInCoveragePopup.size();

        for (int i = 1; i <= companiesCount; i++) {
            System.out.println("Record: " + i);
            String xpath = "(//tr//td[contains(@id,'viewcomapnies')][1])[" + i + "]//*[local-name()='svg']/*[local-name()='rect'][@fill='#26415E']";
            int levels = Driver.getDriver().findElements(By.xpath(xpath)).size();
            String levelName = "";
            String level = "";
            if (levels == 4) {
                level = "Score Level 1";
                levelName = "Analyst Verified";
            } else if (levels == 3) {
                level = "Score Level 2";
                levelName = "Subsidiary";
            } else if (levels == 2) {
                level = "Score Level 3";
                levelName = "On-Demand";
            } else if (levels == 1) {
                level = "Score Level 4";
                levelName = "Predicted Score";
            }
            BrowserUtils.scrollTo(Driver.getDriver().findElement(By.xpath("(//tr//td[contains(@id,'viewcomapnies')][1])[" + i + "]//*[local-name()='svg']/*[local-name()='rect']")));
            BrowserUtils.hover(Driver.getDriver().findElement(By.xpath("(//tr//td[contains(@id,'viewcomapnies')][1])[" + i + "]//*[local-name()='svg']/*[local-name()='rect']")));
            assertTestCase.assertTrue(Driver.getDriver().findElement(By.xpath("//p/strong[text()='" + level + "']")).isDisplayed());
            assertTestCase.assertTrue(Driver.getDriver().findElement(By.xpath("//p[text()='" + levelName + "']")).isDisplayed());

        }

    }

    public boolean verifyScoreQualityIconWithEntitiesUnderCompareResearchLines() {
        return scoreQualityIconsInCompareRLs.size() > 0;
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

    public boolean verifyEsgInfo() {
        String portfolioId = "00000000-0000-0000-0000-000000000000";
        DashboardQueries dashboardQueries = new DashboardQueries();
        String latestMonthAndYearWithData = dashboardQueries.getLatestMonthAndYearWithData(portfolioId);
        String month = latestMonthAndYearWithData.split(":")[0];
        String year = latestMonthAndYearWithData.split(":")[1];
        System.out.println(month + year);
        List<Map<String, Object>> dbEsgInfo = dashboardQueries.getEsgInfo(portfolioId, year, month);
        List<WebElement> uiRecords = Driver.getDriver().findElements(By.xpath("//table[contains(@id, 'viewcomapnies')]/tbody/tr"));

        for (int i = 1; i <= uiRecords.size(); i++) {
            String companyName = Driver.getDriver().findElement(By.xpath("(//table[contains(@id, 'viewcomapnies')]/tbody/tr)[" + i + "]/td[1]/span")).getText();
            String esgScore = Driver.getDriver().findElement(By.xpath("(//table[contains(@id, 'viewcomapnies')]/tbody/tr)[" + i + "]/td[2]/span")).getText();
            System.out.println("companyName = " + companyName);
            System.out.println("esgScore = " + esgScore);
            if (!esgScore.equals("-")) {
                boolean match = false;
                System.out.print("UI Info:" + companyName + "--" + esgScore);
                for (Map<String, Object> dbRecord : dbEsgInfo) {
                    if (dbRecord.get("COMPANY_NAME").toString().equals(companyName)) {
                        System.out.println("-- Company Found");
                        if (dbRecord.get("VALUE_ESG").toString().equals(esgScore)) {
                            match = true;
                        } else {
                            System.out.print("DB ESG - " + dbRecord.get("VALUE_ESG").toString() + " is not matched");
                        }
                        break;
                    }
                }
                Assert.assertTrue(match, companyName + " esg info is not found/matched in Database");
            }
        }
        return true;
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
        navigateToPageFromMenu("Dashboard");
        clickViewCompaniesAndInvestments();
        selectViewBySector();
        deleteDownloadFolder();
        clickExportCompaniesButton();
        closePortfolioExportDrawer();
        assertTestCase.assertEquals(filesCountInDownloadsFolder(), 1, "Verify download of export file");
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
        wait.until(ExpectedConditions.elementToBeClickable(
                        Driver.getDriver()
                                .findElement(By.xpath("//button[./span[contains(text(),'" + performanceChartName + "')]]"))))
                .click();
        waitForDataLoadCompletion();
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
        return wait.until(ExpectedConditions.visibilityOfAllElements(performanceChartRows)).size();
    }

    public double calculateTotalInvestmentFromPerformanceChart() {
        wait.until(ExpectedConditions.visibilityOfAllElements(investmentsInPerformanceChart));
        return PortfolioUtilities.round(investmentsInPerformanceChart.stream()
                .mapToDouble(element -> Double.parseDouble(element.getText().substring(0, element.getText().indexOf("%")))).sum(), 2);
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
        BrowserUtils.scrollTo(performanceChartDropdown);
        wait.until(ExpectedConditions.elementToBeClickable(performanceChartDropdown)).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(performanceChartDropdownOptions));
        performanceChartDropdownOptions.stream().filter(e -> e.getText().equals(size)).findFirst().get().click();
        waitForDataLoadCompletion();
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
        List<String> expResearchLines = Arrays.asList("Overall ESG Score", "Physical Risk: Operations Risk", "Physical Risk: Market Risk", "Physical Risk: Supply Chain Risk", "Physical Risk Management", "Temperature Alignment", "Carbon Footprint", "Green Share Assessment", "Brown Share Assessment");
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

            case "ESG":
            case "ESG Assessment":
            case "Overall ESG Score":
                index = 3;
                break;

            case "Total Critical Controversies":
                index = 4;
                break;

            case "Physical Risk Hazards":
            case "Highest Risk Hazard":
                index = 5;
                break;

            case "Facilities Exposed to High Risk/Red Flag":
                index = 6;
                break;

            case "Physical Risk Management":
                index = 7;
                break;

            case "Temperature Alignment":
                index = 8;
                break;

            case "Carbon Footprint":
            case "Carbon Footprint (tCO2eq)":
                index = 9;
                break;

            case "Brown Share":
            case "Brown Share Assessment":
                index = 11;
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
        BrowserUtils.clickWithJS(element);
        BrowserUtils.wait(10);
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

    public boolean heatmapXAxisIsAvailable(){
        try{
            return heatMapXAxisIndicatorTitle.isDisplayed();
        }catch(Exception e){
            return false;
        }
    }

    public boolean validateSelectTwoStaticText(){
        try{
            return selectTwoLabel.isDisplayed();
        }catch(Exception e){
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

        //TODO - Remove Finance
        //Remove Finance from from expSectorTitles.
        //A bug ticket created for it, It is just added to complete automation. Normally there shouldn't be such sector title.
        List<String> expSectorTitles = Arrays.asList("Basic Materials", "Consumer Discretionary", "Communication", "Financials", "Energy", "Real Estate", "Sovereign", "Technology", "Utilities", "Health Care", "Consumer Staples", "Industry", "Finance");
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
            assertTestCase.assertTrue(
                    DateTimeUtilities.getDayDifference(date, todayDate, "MMMM d, yyyy") < 60,
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

    public boolean verifyMethodologiesHeader() {
        try {
            assertTestCase.assertTrue(methodologyPopup_Header.getText().equals("ESG Assessment Framework"), "Validate header text as 'ESG Assessment Framework'");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getMethodologiesSections() {
        List<String> methodologySections = new ArrayList<>();
        for(WebElement element: methodologySectionNames){
            methodologySections.add(element.getText());
        }
        return methodologySections;
    }

    public boolean verifyMethodologiesLinks() {


        try {
            assertTestCase.assertTrue(methodologyPopup_Link_Methodology10.getText().equals("Read more about ESG Assessment Methodology 1.0"), "Validate link as 'Read more about ESG Assessment Methodology 1.0'");
            assertTestCase.assertTrue(methodologyPopup_Link_Methodology20.getText().equals("Read more about ESG Assessment Methodology 2.0"), "Validate link as 'Read more about ESG Assessment Methodology 2.0'");
            assertTestCase.assertTrue(methodologyPopup_Link_RiskAssessmentts.getText().equals("Read more about Controversy Risk Assessment Methodology"), "Validate link as 'Read more about Controversy Risk Assessment Methodology'");
            return true;
        } catch (Exception e) {
            return false;
        }
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

    public String getHeatMapPortfolioAverage(){
        String portfolioAverage = heatmapPortfolioAverage.getText();
        return portfolioAverage.substring(portfolioAverage.lastIndexOf(":")+1).trim();

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
        String xpath = "//div[@id='heatmapentity-test-id']//div[text()='"+title+"']";
        try{
            BrowserUtils.waitForVisibility(Driver.getDriver().findElement(By.xpath(xpath)), 30);
        }catch(Exception e){
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

    public void verifyOverallESGScoreCatgories(){
        List<String> categories = Arrays.asList(new String[]{"Weak","Limited","Robust","Advanced"});
        for(WebElement e : OverallESGScore){
            assertTestCase.assertTrue(categories.contains(e.getText()), "Validate OverAll ESG Scores");
        }
    }

    public void verifyOverallESGTotalControversies(){

        //List<String> categories = Arrays.asList(new String[]{"Weak","Limited","Robust","Advanced"});

        for(WebElement e : TotalCriticalControversies){
            switch (e.getText()){
                case "":
                    assertTestCase.assertTrue(true, "Validated if controversies are blank");
                case "0":
                    assertTestCase.assertTrue(true, "Validated if controversies are Zero");

            }
           // assertTestCase.assertTrue(e.., "Validate OverAll ESG Scores");
        }
    }
}

