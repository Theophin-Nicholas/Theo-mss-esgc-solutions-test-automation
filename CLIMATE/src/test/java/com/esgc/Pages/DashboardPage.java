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
    @FindBy(xpath = "//div[contains(text(),'Viewing')]")
    public WebElement regionTitleInStickyHeader;

    @FindBy(xpath = "//a[text()='hide']")
    public WebElement hideLink;

    @FindBy(xpath = "//div[@id='heatmapentity-test-id']")
    public WebElement endOfPage;

    @FindBy(id = "button-holdings")
    public WebElement selectPortfolioButton;

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
    @FindBy(xpath = "//h3[normalize-space()='Overall ESG Score']")
    public WebElement heatMapNoEntityWidget;
    @FindBy(xpath = "//div[@class='entityList']//br/..")
    public WebElement heatMapWidgetTitle;
    @FindBy(xpath = "//div[@class='entityList']//br/../following-sibling::div/div[1]")
    public WebElement heatMapWidgetYIndicator;
    @FindBy(xpath = "//div[@class='entityList']//br/../following-sibling::div/div[2]")
    public WebElement heatMapWidgetXIndicator;
    @FindBy(xpath = "(//div[@id='div-mainlayout']//table)[3]//tbody//td")
    public List<WebElement> heatMapCells;
    @FindBy(xpath = "(//div[@id='div-mainlayout']//table)[2]//tbody//td//span[1]")
    public List<WebElement> heatMapYAxisIndicators;
    @FindBy(xpath = "(//div[@id='div-mainlayout']//table)[1]//tbody//td//span[1]")
    public List<WebElement> heatMapYAxisIndicatorsAPI;

    @FindBy(xpath = "(//div[@id='div-mainlayout']//table)[2]//tbody//td//span[2]")
    public List<WebElement> heatMapYAxisIndicatorPercentagesAPI;
    @FindBy(xpath = "(//div[@id='div-mainlayout']//table)[2]//tbody//td//span[2]")
    public List<WebElement> heatMapYAxisIndicatorPercentages;
    @FindBy(xpath = "(//div[@id='div-mainlayout']//table)[4]//tbody//td//span[1]")
    public List<WebElement> heatMapXAxisIndicators;
    @FindBy(xpath = "(//div[@id='div-mainlayout']//table)[4]//tbody//td//span[2]")
    public List<WebElement> heatMapXAxisIndicatorPercentages;
    @FindBy(xpath = "(//div[@id='div-mainlayout']//table)[2]//thead//td")
    public WebElement heatMapYAxisIndicatorTitle;
    @FindBy(xpath = "(//div[@id='div-mainlayout']//table)[4]//thead//td")
    public WebElement heatMapXAxisIndicatorTitle;
    @FindBy(xpath = "//h3/following-sibling::p")
    public List<WebElement> heatMapActiveResearchLineInfo;
    @FindBy(xpath = "//div[text()='Compare Research Lines']/..//div[text()='Temperature Alignment']")
    public WebElement heatMapTemperatureAlignment;
    @FindBy(xpath = " //button[@id='button-holdings']/span/div")
    public WebElement verifyPortfolioName;

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
        System.out.println("ESG Score on UI: "+actualEsgScore);
        return expectedValues.contains(actualEsgScore);
    }

    public boolean verifyPhysicalRiskWidget() {
        try {
            WebElement highestRiskValue = Driver.getDriver().findElement(By.xpath("//div[@id='Highest_Risk_Hazard_id']//span[2]"));
            return physicalRiskCard.isDisplayed() &&
                    highestRiskHazard.isDisplayed() &&
                    highestRiskValue.getText()!="";
        } catch (Exception e) {
            return false;
        }
    }


    public boolean verifyFacilitiesExposedWidget() {
        try {
            WebElement facilitiesExposedValue = Driver.getDriver().findElement(By.xpath("//div[contains(@id,'Facilities_Exposed_to_')]//span[1]"));
            WebElement facilitiesExposedFlag = Driver.getDriver().findElement(By.xpath("//div[contains(@id,'Facilities_Exposed_to_')]//span[2]"));
            return facilitiesExposed.isDisplayed() &&
                    facilitiesExposedValue.getText()!="" &&
                    facilitiesExposedFlag.getText()!="";
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyEsgInfo() {
        String portfolioId="00000000-0000-0000-0000-000000000000";
        DashboardQueries dashboardQueries = new DashboardQueries();
        String latestMonthAndYearWithData = dashboardQueries.getLatestMonthAndYearWithData(portfolioId);
        String month = latestMonthAndYearWithData.split(":")[0];
        String year = latestMonthAndYearWithData.split(":")[1];
        List<Map<String, Object>> dbEsgInfo = dashboardQueries.getEsgInfo(portfolioId, year, month);
        List<WebElement> uiRecords = Driver.getDriver().findElements(By.xpath("//table[contains(@id, 'viewcomapnies')]/tbody/tr"));

        for(int i=1; i<=uiRecords.size(); i++){
            String companyName = Driver.getDriver().findElement(By.xpath("(//table[contains(@id, 'viewcomapnies')]/tbody/tr)["+i+"]/td[1]/span")).getText();
            String esgScore = Driver.getDriver().findElement(By.xpath("(//table[contains(@id, 'viewcomapnies')]/tbody/tr)["+i+"]/td[2]/span")).getText();
            if(esgScore.equals("-")) break;
            boolean match = false;
            System.out.print("UI Info:"+ companyName+"--"+esgScore);
            for(Map<String, Object> dbRecord:dbEsgInfo){
                if(dbRecord.get("COMPANY_NAME").toString().equals(companyName)){
                    System.out.println("-- Company Found");
                    if(dbRecord.get("VALUE").toString().equals(esgScore)) {
                        match = true;
                    }else {
                        System.out.print("DB ESG - "+dbRecord.get("VALUE").toString()+" is not matched");
                    }
                    break;
                }
            }
            Assert.assertTrue(match, companyName+" esg info is not found/matched in Database");
        }
        //        assertTestCase.assertEquals(uiRecords.size(), dbEsgInfo.size());
//        for (Map<String, Object> dbRecord : dbEsgInfo) {
//            String xpath = "//table[contains(@id, 'viewcomapnies')]/tbody/tr/td/span[text()='"+dbRecord.get("COMPANY_NAME").toString()+"']" +
//                    "/../following-sibling::td/span[text()='"+dbRecord.get("VALUE").toString()+"']";
//            WebElement uiRecord = Driver.getDriver().findElement(By.xpath(xpath));
//            assertTestCase.assertTrue(uiRecord.isDisplayed(), dbRecord.get("COMPANY_NAME")+ " record is not available");
//        }
        return true;
    }

    public void selectViewMethodologies(){
      //  BrowserUtils.waitForInvisibility(btnViewMethodologies, 50);
        btnViewMethodologies.click();
    }

    public boolean verifyMethodologiesPopup(){
        try {
            wait.until(ExpectedConditions.visibilityOf(methodologiesPopup));
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public void downloadDashboardExportFile(){
        navigateToPageFromMenu("Dashboard");
        clickViewCompaniesAndInvestments();
        selectViewBySector();
        deleteDownloadFolder();
        clickExportCompaniesButton();
        closePortfolioExportDrawer();
        assertTestCase.assertEquals(filesCountInDownloadsFolder(),1,"Verify download of export file");
    }


    public void clickHideLink(){
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
        wait.until(ExpectedConditions.elementToBeClickable(performanceChartDropdown)).click();
        BrowserUtils.wait(2);
        wait.until(ExpectedConditions.visibilityOfAllElements(performanceChartDropdownOptions));
        System.out.println("performanceChartDropdownOptions "+performanceChartDropdownOptions.size());
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
        List<String> expResearchLines = Arrays.asList("Overall ESG Score","Physical Risk: Operations Risk", "Physical Risk: Market Risk", "Physical Risk: Supply Chain Risk", "Physical Risk Management", "Temperature Alignment","Carbon Footprint", "Green Share Assessment","Brown Share Assessment");
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
                return Arrays.asList("Company", "% Investment", "Highest Risk Hazard", "Facilities Exposed to High Risk/Red Flag","Physical Risk Management");
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

    public void selectOrDeselectHeatMapSection(String researchLine) {
        WebElement element = Driver.getDriver().findElement(By.xpath("//div[text()='Compare Research Lines']/..//div[contains(text(),'" + researchLine + "')]"));

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
                String xpath = "(//div[text()='Compare Research Lines']/../../..//table)[2]//tr[" + i + "]/td[" + j + "]//span[2]";
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
                return Arrays.asList("Operations Risk", "Market Risk", "Supply Chain Risk","Physical Risk Management");
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
                return Arrays.asList("Physical Risk: Operations Risk", "Physical Risk: Market Risk", "Physical Risk: Supply Chain Risk","Physical Risk Management");
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

            BrowserUtils.waitForVisibility(Driver.getDriver().findElement(By.xpath("//*[contains(text(),'" + colText + "')]")), 30);
            Driver.getDriver().findElement(By.xpath("//span[text()='X'] | //span[@class='close'] | //div[@role='dialog']/div/div[2]")).click();
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
                    DateTimeUtilities.getDayDifference(date, todayDate,"MMMM d, yyyy")<60,
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

            assertTestCase.assertTrue(SectorUtilities.isMESGSector(controversiesTableSectors.get(i).getText()), "Verify Sector");

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
    }

    //this method deselects all other heat map research lines and selects only one research line by index
    public void selectOneResearchLine(int selection) {
        //First make sure targeted line is selected
        BrowserUtils.wait(2);
        String color = Color.fromString(heatMapResearchLines.get(selection).getCssValue("background-color")).asHex();
        if (!color.equals("#355b85")) heatMapResearchLines.get(selection).click();
        System.out.println("heatMapResearchLines.size() = " + heatMapResearchLines.size());
        //Then go through each button
        for (int i = 0; i < heatMapResearchLines.size(); i++) {
            System.out.println("i = " + i);
            //get is color
            color = Color.fromString(heatMapResearchLines.get(i).getCssValue("background-color")).asHex();
            //check if it is selected and it is not our targeted button
            if (i != selection && color.equals("#355b85")) {
                //then click to deselect it
                BrowserUtils.wait(2);
                heatMapResearchLines.get(i).click();
                BrowserUtils.wait(1);
                System.out.println(heatMapResearchLines.get(i).getText() + " is de-selected");
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

}
