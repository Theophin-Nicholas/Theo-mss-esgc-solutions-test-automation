package com.esgc.Pages;

import com.esgc.Reporting.CustomAssertion;
import com.esgc.Tests.UI.DashboardPage.PortfolioSettings;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.ScoreCategories;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class ResearchLinePage extends UploadPage {

    //============== Portfolio Score

    @FindBy(xpath = "//*[@id='portfolio_4_box']//span")
    public WebElement PortfolioScoreTitle;
    @FindBy(xpath = "(//div[contains(@id,'test-id-from-overview-')])[2]//div[text()='Score']/following-sibling::h6")
    public WebElement portfolioScoreValue;

    @FindBy(xpath = "(//div[contains(@id,'phy-risk-mgm-test-id-from-overview-')])/div/div/div/h6")
    public WebElement portfolioScoreGreenShareValue;

    @FindBy(xpath = "//div[text()='Coverage']/../h6")
    public WebElement portfolioScoreGreenShareCompaniesValue;
    @FindBy(xpath = "//div[contains(@id,'test-id-from-overview-')]//h6[contains(text(),'%')]/following-sibling::div/preceding-sibling::h6")
    public WebElement portfolioScoreGreenShareInvestmentValue;

    @FindBy(xpath = "(//div[contains(@id,'test-id-from-overview-')])[3]//div/h6 ")
    public WebElement portfolioCarbonIntensityValue;

    @FindBy(xpath = "(//div[contains(@id,'test-id-from-overview-')])[3]//div/div[2]")
    public WebElement portfolioCarbonIntensityText;


    //Ranks should be one of these: Weak, Limited, Robust, Advanced, N/A
    @FindBy(xpath = "(//div[contains(@id,'test-id-from-overview-')])[1]//div[text()]")
    public WebElement portfolioScoreCategory;

    //Scores should be between 0-100
    @FindBy(xpath = "(//div[contains(@id,'test-id-from-overview-')])[2]//div[text()='Score']/following-sibling::div")
    public WebElement portfolioScore;

    //=============== Portfolio Distribution

    @FindBy(id = "physical-risk-management-overview-portfolio-distribution-minimaltable-test-id")
    public WebElement portfolioDistributionTable;

    @FindBy(xpath = "(//table[@id='physical-risk-management-overview-portfolio-distribution-minimaltable-test-id'])[1]/tbody/tr")
    public List<WebElement> portfolioDistributionAllTable;

    @FindBy(xpath = "(//table[@id='physical-risk-management-overview-portfolio-distribution-minimaltable-test-id'])[1]/thead/tr")
    public List<WebElement> portfolioDistributionAllTableHeaders;

    //============ Portfolio Coverage Elements

    @FindBy(xpath = "//div[text()='Coverage']/ancestor::div[contains(@id,'test-id-from-overview-')]")
    public WebElement portfolioCoverageTable;//whole chart

    @FindBy(xpath = "//div[text()='Companies']")
    public WebElement portfolioCoverageCompaniesChartTitle;//Companies

    @FindBy(xpath = "//div[text()='Investment']")
    public WebElement portfolioCoverageInvestmentChartTitle;//Investment

    @FindBy(xpath = "//div[text()='Coverage']/../h6")
    public WebElement portfolioCoverageCompanies;//Companies

    @FindBy(xpath = "//div[contains(@id,'test-id-from-overview-')]//h6[contains(text(),'%')]")
    public WebElement portfolioCoverageInvestment;//Investment

    //This list contains 2 element
    //First one coverage rate ex: 123/129
    //Second one investment percentage rate ex: %95
    @FindBy(css = "div[dir='ltr'] text[x='45'] ")
    public List<WebElement> portfolioCoverageValues;

    /* Returns below texts
    Of Investments In Companies Offering Green Solutions
    Companies
    Investment
     */
    //Returns score value like 8%
    @FindBy(xpath = "(//div[contains(@id,'phy-risk-mgm-test-id-from-overview-')])/div/div/div/following-sibling::div")
    public List<WebElement> greenSharePortfolioScoreTexts;

    @FindBy(xpath = "(//div[contains(@id,'crbn-rsk-test-id-from-overview-')])/div/div/div/following-sibling::div")
    public List<WebElement> greenSharePortfolioScoreText;

    //Returns Investment value like 7%
    @FindBy(xpath = "//div[contains(@id,'test-id-from-overview-')]//h6[contains(text(),'%')]/following-sibling::div/preceding-sibling::h6")
    public WebElement greenShareInvestmentValue;


    //============== Portfolio Overview

    @FindBy(xpath = "//a[@id='link-link-test-id-3']")
    public WebElement methodologyLink;

    @FindBy(xpath = "//*[@title and text()]")
    public WebElement portfolioNameInSubTitle;

    @FindBy(xpath = "//span[@title]")
    public WebElement pageTitle;

    //============== Drill Down Panel
    @FindBy(xpath = "//div[@tabindex='-1']")
    public WebElement drillDownPanel;

    @FindBy(xpath = "//div[@role]/div[@aria-hidden='true']")
    public WebElement outSideOfDrillDownPanel;

    @FindBy(xpath = "//div[@tabindex='-1']//div[text()]")
    public WebElement drillDownTitle;

    @FindBy(xpath = "//a[text()='hide']")
    public WebElement hideButton;

    @FindBy(xpath = "//*[starts-with(@id,'regionTab')]")
    public List<WebElement> drillDownSections;

    @FindBy(xpath = "//*[starts-with(@id,'regionTab')]/thead/tr")
    public List<WebElement> drillDownColumnHeaders;

    @FindBy(xpath = "//*[starts-with(@id,'regionTab')]/tbody/tr")
    public List<WebElement> drillDownCompanyRows;

    //=============== Updates

    @FindBy(xpath = "//*[text()='Updates']//..//table//th")
    public List<WebElement> updatesTableColumns;

    @FindBy(xpath = "//*[text()='Updates']//..//table/tbody/tr")
    public List<WebElement> updatesCompaniesList;

    @FindBy(id = "laggardsError")
    public WebElement updatesNoDataMessage;

    //=========Updates=========
    @FindBy(xpath = "//h6[text()='Updates']/following-sibling::div//thead//th")
    public List<WebElement> brownAndGreenShareHeaders;

    @FindBy(xpath = "(//tr[@class='MuiTableRow-root MuiTableRow-head'])[5]/th")
    public List<WebElement> carbonFootprintHeaders;

    //=============== Leaders and Laggards

    @FindBy(xpath = "//h6[text()='Laggards by Score']")
    public WebElement laggardsSubTitle;

    @FindBy(xpath = "//h6[text()='Leaders by Score']")
    public WebElement leadersSubTitle;

    @FindBy(xpath = "//*[@id='table-id']")
    public WebElement LeadersAndLaggardsTable;

    @FindBy(xpath = "//*[@id='table-id']")
    public List<WebElement> LeadersAndLaggardsTablelist;

    @FindBy(xpath = "//*[@id='table-id']//tr//th")
    public List<WebElement> LeadersAndLaggardsTableColumns;

    @FindBy(xpath = "//h6[starts-with(text(),'Leaders')]/..//table[@id='table-id']/thead/following-sibling::tbody/tr")
    public List<WebElement> leadersCompaniesList;

    @FindBy(xpath = "//h6[starts-with(text(),'Laggards')]/..//table[@id='table-id']/thead/following-sibling::tbody/tr")
    public List<WebElement> laggardsCompaniesList;

    @FindBy(id = "laggardsError")
    public WebElement laggardsNoDataMessage;

    @FindBy(id = "leadersError")
    public WebElement leadersNoDataMessage;


    //=============== Region/Sector

    @FindBy(xpath = "//span[text()='Companies']/preceding-sibling::div")
    public List<WebElement> regionSectorCompanyCountsInChart;

    @FindBy(xpath = "//*[starts-with(@id,'regionminimaltable')]/tbody/tr/td[2]")
    public List<WebElement> companyCountColumnPerCategoryInRegionSectorChart;

    @FindBy(xpath = "//div[contains(@id,'regioncard-test-id')]")
    public List<WebElement> RegionSectorsTables;

    @FindBy(xpath = "//div[starts-with(@id,'regioncard')]/button")
    public List<WebElement> regionSectorChartNames;

    private final List<String> expectedRegionSectorList = Arrays.asList("Asia Pacific", "Americas", "Europe, Middle East & Africa", "Industry", "Financials", "Health Care", "Technology", "Real Estate", "Consumer Discretionary", "Basic Materials", "Utilities", "Communication", "Consumer Staples", "Energy", "Finance", "Sovereign");
    private final List<String> expectedRegionList = Arrays.asList("Asia Pacific", "Americas", "Europe, Middle East & Africa");
    //============ Region Map

    @FindBy(xpath = "//*[contains(text(), 'Weighted average scores')]")
    public WebElement mapLabel;

    @FindBy(xpath = "//*[text()='Weighted average scores based on portfolio ']/..")
    public WebElement map;


    //=============== Underlying Data Metrics

    @FindBy(xpath = "//div[@class='MuiGrid-root MuiGrid-container MuiGrid-item']")
    public List<WebElement> widgetWithHeaderandDiv;

    @FindBy(xpath = "//span//div[./br and text()]")
    public List<WebElement> underlyingDataMetricHeaders;


    //=============== Export

    @FindBy(id = "ExportDropdown-test-id-1")
    public WebElement exportDropdown;

    @FindBy(xpath = "//span[text()='Exporting']/../..")
    public WebElement exportingLoadMask;


    //================ Benchmark

    @FindBy(xpath = "//*[contains(text(),'selected. Please')]")
    public WebElement noBenchmarkMessage;

    @FindBy(xpath = "(//div[@id='distribution_box'])[2]")
    public WebElement BenchmarkSection;

    @FindBy(xpath = "//*[@id='benchmark_4_box']//div[text()='Score']/following-sibling::h6")
    public WebElement CarbonFootPrintBenchmarkScoreFootPrintvalue;

    @FindBy(xpath = "//*[@id='benchmark_4_box']//div[contains(text(),'Footprint')]")
    public WebElement CarbonFootPrintBenchmarkScoreFootPrintText;

    @FindBy(xpath = "//*[@id='benchmark_4_box']//div[contains(text(),'Intensity')]/..//h6")
    public WebElement CarbonFootPrintBenchmarkScoreIntensityvalue;

    @FindBy(xpath = "//*[@id='benchmark_4_box']//div[contains(text(),'Intensity')]")
    public WebElement CarbonFootPrintBenchmarkScoreFootIntensityText;

    @FindBy(xpath = "//*[@id='benchmark_4_box']//div[contains(text(),'Of Investments In Companies Offering Green Solutions')]/preceding-sibling::div/h6")
    public WebElement GreenShareBenchmarkScoreValue;

    @FindBy(xpath = "//*[@id='benchmark_4_box']//div[contains(text(),'Of Investments In Companies Offering Green Solutions')]")
    public WebElement GreenShareBenchmarkScoreText;

    @FindBy(xpath = "//*[@id='benchmark_4_box']//div[contains(text(),'Overall Fossil Fuels Industry Revenues')]/preceding-sibling::div/h6")
    public WebElement BrownShareBenchmarkScoreValue;

    @FindBy(xpath = "//*[@id='benchmark_4_box']//div[contains(text(),'Overall Fossil Fuels Industry Revenues')]")
    public WebElement BrownShareBenchmarkScoreText;

    @FindBy(xpath = "//*[@id='benchmark_4_box']//div//..//div[text()='Coverage']")
    public WebElement BenchMarkCoverageSection;

    @FindBy(xpath = "//*[@id='benchmark_4_box']//div//..//div[text()='Coverage']/following-sibling::h6")
    public WebElement BenchMarkCoverageSectionCompaniesValue;

    @FindBy(xpath = "//*[@id='benchmark_4_box']//div//..//div[text()='Coverage']//..//div[text()='Companies']")
    public WebElement BenchMarkCoverageCompaniesSection;

    @FindBy(xpath = "//*[@id='benchmark_4_box']//div//..//div[text()='Coverage']/../../following-sibling::div//h6")
    public WebElement BenchMarkCoverageSectionInvestmentValue;

    @FindBy(xpath = "//*[@id='benchmark_4_box']//div//..//div[text()='Coverage']//..//..//..//div[text()='Investment']")
    public WebElement BenchMarkCoverageInvestmentSection;

    @FindBy(xpath = "//*[@id='benchmark_4_box']//span")
    public WebElement BenchmarkScoreTitle;

    @FindBy(xpath = "//*[@id='benchmark_4_box']//table[@id='physical-risk-management-overview-portfolio-distribution-minimaltable-test-id']/tbody/tr")
    public List<WebElement> BenchMarkDistributionTableRow;

    @FindBy(xpath = "//*[@id='benchmark_4_box']//table[@id='physical-risk-management-overview-portfolio-distribution-minimaltable-test-id']/thead/tr")
    public List<WebElement> BenchMarkDistributionTableHeader;

    //*[@id='benchmark_4_box']//div//..//div[text()='Overall Fossil Fuels Industry Revenues']

    //============ Carbon Footprint Emissions

    @FindBy(xpath = "//div[text()='Emissions']")
    public WebElement EmissionsSectionWidgetTitle;

    @FindBy(xpath = "//div[text()='Emissions']//..//..//..//span[text()='Total Financed Emissions']")
    public WebElement EmissionsTotalFinanceSectionTitle;

    @FindBy(xpath = "//div[text()='Emissions']//..//..//..//span[text()='Total Financed Emissions']/following-sibling::div[1]")
    public WebElement EmissionsTotalFinanceSectionDetail;

    @FindBy(xpath = "//div[text()='Emissions']//..//..//..//span[text()='Financed Emissions per million Invested']")
    public WebElement Financed_Emissions_per_million_Invested_Title;

    @FindBy(xpath = "//div[text()='Emissions']//..//..//..//span[text()='Financed Emissions per million Invested']/following-sibling::div[1]")
    public WebElement Financed_Emissions_per_million_Invested_Detail;

    @FindBy(xpath = "//div[text()='Emissions']//..//..//..//span[text()='Carbon Intensity per Sales']")
    public WebElement Carbon_Intensity_per_SalesTitle;

    @FindBy(xpath = "//div[text()='Emissions']//..//..//..//span[text()='Carbon Intensity per Sales']/following-sibling::div[1]")
    public WebElement Carbon_Intensity_per_SalesDetail;


    @FindBy(xpath = "//table[@id='carbonfootprint-financial-emission-minimaltable-test-id']")
    public List<WebElement> EmissionsTables;

    @FindBy(xpath = "//div[@id='PortfolioScoreESG-test-id']//div[@style][./span[contains(text(),'Highest Risk Hazard:')]]")
    public WebElement physicalRiskHazardsScoreWidget;

    @FindBy(xpath = "//div[@id='PortfolioScoreESG-test-id']//div[@style][./span[contains(text(),'Highest Risk Hazard:')]]//span")
    public WebElement physicalRiskHazardsScoreWidgetText;

    @FindBy(xpath = "//div[@id='PortfolioScoreESG-test-id']//b/..")
    public WebElement facilitiesExposedWidget;

    @FindBy(xpath = "//div[@id='phy-risk-mgm-test-id-from-overview-']/div/div")
    public WebElement scoreWidget;

    @FindBy(xpath = "//div[@id='ta-test-id-from-overview-']/div[1]/div/h6")
    public List<WebElement> temperatureScoreWidget;

    @FindBy(xpath = "//div[@id='crbn-rsk-test-id-from-overview-']/div/div")
    public WebElement carbonFootprintScoreWidget;

    @FindBy(xpath = "//div[@id='radar_box']")
    public WebElement radarGraph;


    @FindBy(xpath = "//div[@id='history_box_portfolio']")
    public WebElement historyTable;

    @FindBy(xpath = "//div[@id='history_box_benchmark']")
    public WebElement historyTableBenchMark;

    @FindBy(xpath = "(//*[name()='rect'][@class='highcharts-point highcharts-color-0'])[1]")
    public WebElement historyChartUnmatched;

    //=============== Impact Table/ Graph elements

    @FindBy(xpath = "//div[@id='impact-filter']")
    public WebElement impactFilter;

    @FindBy(xpath = "//div[@id='impact-filter']")
    public List<WebElement> impactFilters;
    @FindBy(xpath = "//div[@class='impactTableWrapper']")
    public WebElement impactTable;

    @FindBy(xpath = "//div[@class='impactTableWrapper']")
    public List<WebElement> impactTables;

    @FindBy(xpath = "//*[text()='Physical Risk Hazards: Operations Risk']")
    public WebElement impactTableMainTitle;

    @FindBy(xpath = "//div[@class='impactTableWrapper']")
    public List<WebElement> impactTableList;

    @FindBy(xpath = "//div[@class='impactTableWrapper']/ancestor::div[2]/following::div")
    public WebElement impactGraph;

    @FindBy(xpath = "//div[text()='Positive impact based on investment and score']")
    public WebElement positiveImpactTable;

    @FindBy(xpath = "//div[text()='Negative impact based on investment and score']")
    public WebElement negativeImpactTable;

    @FindBy(xpath = "//div[text()='Positive Impact Distribution']")
    public WebElement positiveImpactGraph;

    @FindBy(xpath = "//div[text()='Negative Impact Distribution']")
    public WebElement negativeImpactGraph;

    @FindBy(xpath = "//div[@id='PortfolioScoreESG-test-id']//child::div[contains(text(),'Weighted')]")
    public List<WebElement> portfolioScoreWidget;

    @FindBy(id = "summary_box")
    public WebElement impactTableDescription;

    @FindBy(xpath = "//div[@id='BenchmarkScoreESG-test-id']//child::div[contains(text(),'Weighted')]")
    public List<WebElement> benchmarkPortfolioScoreWidget;

    @FindBy(css = "g.highcharts-label > text")
    public List<WebElement> maptooltip;

    @FindBy(css = "g.highcharts-tracker > g > path:not([fill='#f7f7f7'])")
    public List<WebElement> countriesOnMap;

    @FindBy(xpath = "//a[contains(normalize-space(),'more companies with same investment %')]")
    public List<WebElement> impactTableDrillDownLink;

    @FindBy(xpath = "//a[@heap_id='Updates']")
    public WebElement updatesMoreCompaniesLink;

    //exclude first and last element from list
    @FindBy(xpath = "//div[@heap_impacts_id='Positive']//tr/td/..")
    public List<WebElement> impactTableCompanies;

    @FindBy(xpath = "//div[@heap_impacts_id='Positive']//tr/td[1]")
    public List<WebElement> impactTableCompanyNames;

    @FindBy(xpath = "//div[@heap_impacts_id='Positive']//tr/td[2]")
    public List<WebElement> impactTableInvestmentPercentages;

    @FindBy(xpath = "//div[@heap_impacts_id='Positive']//tr/td[2]")
    public List<WebElement> impactTableScores;

    @FindBy(xpath = "//div[@heap_impacts_id='Positive']//tr/td[2]")
    public List<WebElement> impactTableIndicators;


    @FindBy(xpath = "//div[@heap_impacts_id='Negative']//tr/td[1]")
    public List<WebElement> impactTableCompanyNameNegativeSide;


    @FindBy(xpath = "//div[@heap_impacts_id='Negative']//tr/td[2]")
    public List<WebElement> impactTableInvestmentPercentagesNegativeSide;

    //===========Portfolio Analysis (Entity Page Automation (Hyperlink))

    @FindBy(xpath = "(((//div[@class='MuiGrid-root MuiGrid-container MuiGrid-item'])[2]//table)[1]//tr[1]//td)[1]")
    public List<WebElement> companyNameLeaderLeggard;


    //ESG Assessments

    @FindBy(xpath = "//div[@id='portfolio_box']")
    public WebElement EsgPortfolioBox;

    @FindBy(xpath = "//div[@id='benchmark_box']")
    public WebElement EsgBenchmarkBox;


    @FindBy(xpath = "//div[@id='cardInfo_box']")
    public WebElement esgCardInfoBox;

    @FindBy(xpath = "//div[@id='cardInfo_box']//span")
    public WebElement esgCardInfoBoxScore;

    @FindBy(xpath = "//div[@id='cardInfo_box']//a")
    public WebElement esgCardEntitiyLink;

    @FindBy(xpath = "//div[@id='legend_summary_link_box']")
    public WebElement EsgSummaryBox;

    @FindBy(xpath = "//div[@id='legend_summary_link_box']//a")
    public List<WebElement> ESGSummaryBoxLink;

    @FindBy(xpath = "//div[@id='cardInfo_box']//a[contains(text(),'Coverage')]")
    public WebElement esgPortfolioCoverageLink;

    @FindBy(xpath = "//div[contains(@class,'MuiDrawer-paper')]//div[contains(text(), 'ESG in ')]")
    public WebElement esgCompaniesPopup;

    @FindBy(xpath = "//*[@id='distribution_box']")
    public WebElement esgScoreDistribution;

    @FindBy(xpath = "//*[@id='distribution_box']/div")
    public WebElement esgScoreDistributionHeader;

    @FindBy(xpath = "//*[@id='distribution_box']//table//th")
    public List<WebElement> esgScoreDistributionTableHeader;

    @FindBy(xpath = "//*[@id='distribution_box']//table/tbody/tr/td[1]")
    public List<WebElement> esgScoreDistributionTableCategoryList;

    @FindBy(xpath = "//table[@id='ESGTable']/tbody/tr/td[1]")
    public List<WebElement> esgAssessmentsCountryTableList;

    @FindBy(xpath = "//div[contains(@class,'MuiDrawer-paperAnchorRight')]/header/div/div/div[1]")
    public WebElement esgAssessmentsCountryDrwaerPopup;


    @FindBy(xpath = "//table[@id='table-id-2']")
    public WebElement physicalRiskHazardInvestmentTable;

    //Sector and Geographic Distribution Section Elements
    @FindBy(xpath = "(//div[.='Sector and Geographic Distribution'])")
    public WebElement geoSectionTitle;

    @FindBy(xpath = "(//div[text()='Sector and Geographic Distribution']/../following-sibling::div[1])")
    public WebElement geoSectionTreemap;

    @FindBy(xpath = "//table[@id='ESGTable']//th")
    public List<WebElement> geoTableHeaders;

    @FindBy(xpath = "//table[@id='ESGTable']//td[1]")
    public List<WebElement> geoTableCountryList;

    @FindBy(xpath = "//table[@id='ESGTable']//td[2]")
    public List<WebElement> geoTableInvPercentagesList;

    @FindBy(xpath = "//table[@id='ESGTable']//td[3]")
    public List<WebElement> geoTableIESGScoreList;

    @FindBy(xpath = "//a[.='hide']/../../div")
    public WebElement geoSectionDrawerTitle;

    @FindBy(xpath = "//a[.='hide']")
    public WebElement geoSectionHideButton;


    //=============== Methods


    //==============Portfolio Score, Portfolio Distribution, Portfolio Coverage Common Methods ===========


    /**
     * Checks if Portfolio Subtitle Displayed in Physical Risk Management Page
     * <p>
     * You need to pass subtitle as parameter
     * ex: Portfolio Score
     *
     * @param - subtitle of table/section
     * @return true - if not displayed returns false
     */
    public void checkHyperlinkOfLegendAndLeggard() {
        ArrayList<String> hyperlinks1 = new ArrayList<String>();
        for (WebElement hyperlink : companyNameLeaderLeggard) {
            hyperlinks1.add(hyperlink.getText());
        }
        ArrayList<String> hyperlinks2 = new ArrayList<String>();

        for (int i = 0; i < hyperlinks1.size(); i++) {
            Driver.getDriver().findElement(By.xpath("(((//div[@class='MuiGrid-root MuiGrid-container MuiGrid-item'])[2]//table)[1]//tr[i]//td)[1]")).getCssValue("border-bottom");
            Driver.getDriver().findElement(By.xpath("(((//div[@class='MuiGrid-root MuiGrid-container MuiGrid-item'])[2]//table)[1]//tr[i]//td)[1]")).click();
            Driver.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            hyperlinks2.add(Driver.getDriver().findElement(By.xpath("((//div[@id='div-mainlayout']//header)[5]/div/div/div/div)[1]")).getText());
        }
        for (int i = 0; i < hyperlinks1.size(); i++) {
            Assert.assertEquals(hyperlinks1.get(i), hyperlinks2.get(i));
        }

    }

    public boolean checkIfWidgetTitleIsDisplayed(String subtitle) {
        try {
            System.out.println("Check if " + subtitle + " Subtitle Is Displayed");
            String xpath = "//div[text()='" + subtitle + "']";
            WebElement subtitleElement = Driver.getDriver().findElement(By.xpath(xpath));
            wait.until(ExpectedConditions.visibilityOf(subtitleElement));
            System.out.println("TEST PASSED");
            return subtitleElement.isDisplayed();
        } catch (Exception e) {
            System.out.println("FAILED");
            e.printStackTrace();
            return false;
        }
    }


    public boolean checkIfHistoryTableExists(String subtitle) {
        try {
            BrowserUtils.wait(6);
            //wait.until(ExpectedConditions.visibilityOf(historyTable));
            return historyTable.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkIfBenchMarkHistoryTableExists() {
        try {
            BrowserUtils.wait(5);
            //wait.until(ExpectedConditions.visibilityOf(historyTableBenchMark));
            return historyTableBenchMark.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    public boolean checkifWidgetTitleIsDisplayedWithID(String subtitle, String commonWidgetsID) {
        try {
            System.out.println("Check if " + subtitle + " Subtitle Is Displayed");
            String xpath = "//*[@id='" + commonWidgetsID + "']";
            WebElement subtitleElement = Driver.getDriver().findElement(By.xpath(xpath));
            wait.until(ExpectedConditions.visibilityOf(subtitleElement));
            System.out.println("TEST PASSED");
            return subtitleElement.isDisplayed();
        } catch (Exception e) {
            System.out.println("FAILED");
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyInvestmentsLessthanOnePercentInPortfolioAndBenchmark() {
        String investmentsXpath = "//div[@id='phy-risk-mgm-test-id-from-overview-']//div[text()='Investment']/preceding-sibling::h6";
        return verifyValuesLessThanOnePercent(investmentsXpath);
    }

    public boolean verifyInvestmentsLessthanOnePercentInPortfolioDistribution() {
        String investmentsXpath = "(//table[@id='physical-risk-management-overview-portfolio-distribution-minimaltable-test-id'])[1]//tr/td[2]";
        return verifyValuesLessThanOnePercent(investmentsXpath);
    }

    public boolean verifyInvestmentsLessthanOnePercentInBenchmarkDistribution() {
        String investmentsXpath = "//div[@id='cardInfo_box']//div[text()='Investment']/preceding-sibling::h6";
        List<WebElement> updateInvestments = Driver.getDriver().findElements(By.xpath(investmentsXpath));
        if (updateInvestments.size() == 0) {
            if (verifyMessage("Portfolio", "No Updates for this time period.") &&
                    verifyMessage("Portfolio", "There are no updates in All Regions, Sovereign for the time period you selected.")) {
                return true;
            }
            return false;
        }
        return verifyValuesLessThanOnePercent(investmentsXpath);
    }

    public boolean verifyInvestmentsLessthanOnePercentInUpdatesSection() {
        String investmentsXpath = "//td[@heap_id='updates']/following-sibling::td[5]";
        List<WebElement> updateInvestments = Driver.getDriver().findElements(By.xpath(investmentsXpath));
        return verifyValuesLessThanOnePercent(investmentsXpath);
    }

    public boolean verifyInvestmentsLessthanOnePercentInUpdatesDrawerSection() {
        String moreCompaniesXpath = "//a[@heap_id='Updates']";
        String investmentsXpath = "//div[contains(@class,'MuiDrawer')]//table[@id='table-id']//tr/td[3]";
        List<WebElement> moreCompanies = Driver.getDriver().findElements(By.xpath(moreCompaniesXpath));
        return verifyInvestmentsLessthanOnePercentInDrawers(moreCompanies, investmentsXpath);
    }

    public boolean verifyInvestmentsLessthanOnePercentInImpactSection() {
        String investmentsXpath = "//td[@heap_id='impacts']/following-sibling::td[1]";
        return verifyValuesLessThanOnePercent(investmentsXpath);
    }

    public boolean verifyInvestmentsLessthanOnePercentInImpactSectionDrawers() {
        selectImpactFilterOption("Top 10");
        String impactsMoreCompaniesXpath = "//a[contains(text(),'more companies with same investment %')]";
        String investmentsXpath = "//table[@id='impact-table']//tr/td[2]";
        List<WebElement> impactsMoreCompanies = Driver.getDriver().findElements(By.xpath(impactsMoreCompaniesXpath));
        return verifyInvestmentsLessthanOnePercentInDrawers(impactsMoreCompanies, investmentsXpath);
    }

    public boolean verifyInvestmentsLessthanOnePercentInLeadersAndLaggards() {
        String investmentsXpath = "//td[@heap_id='leadlag']/following-sibling::td[1]";
        return verifyValuesLessThanOnePercent(investmentsXpath);
    }

    public boolean verifyInvestmentsLessthanOnePercentInLeadersAndLaggardsDrawers() {
        String leadersAndLaggardsMoreCompaniesXpath = "//a[contains(text(),'more companies ranked')]";
        String investmentsXpath = "(//table[@id='table-id'])[3]//tr/td[3]";
        List<WebElement> leadersAndLaggardsMoreCompanies = Driver.getDriver().findElements(By.xpath(leadersAndLaggardsMoreCompaniesXpath));
        return verifyInvestmentsLessthanOnePercentInDrawers(leadersAndLaggardsMoreCompanies, investmentsXpath);
    }

    public boolean verifyInvestmentsLessthanOnePercentInRegionsAndSectors() {
        String xpath = "//table[contains(@id,'regionminimaltable-test-id-')]//tr/td[3]";
        return verifyValuesLessThanOnePercent(xpath);
    }

    public boolean verifyInvestmentsLessthanOnePercentInRegionsAndSectorsDrawers(String researchLine) {
        String regionsAndSectorsXpath = "//div[contains(@class,'MuiCardHeader-content')]";
        String investmentsXpath = "//table[contains(@id,'regionTab')]//tr/td[4]";
        if (researchLine.equals("Physical Risk Hazards") || researchLine.equals("Brown Share Assessment")) {
            investmentsXpath = "//table[contains(@id,'regionTab')]//tr/td[3]";
        }
        List<WebElement> regionsAndSectors = Driver.getDriver().findElements(By.xpath(regionsAndSectorsXpath));
        return verifyInvestmentsLessthanOnePercentInDrawers(regionsAndSectors, investmentsXpath);
    }

    public boolean verifyInvestmentsLessthanOnePercentInDrawers(List<WebElement> sections, String investmentsXpath) {

        for (WebElement section : sections) {
            BrowserUtils.scrollTo(section);
            section.click();
            BrowserUtils.wait(2);
            if (!verifyValuesLessThanOnePercent(investmentsXpath)) {
                return false;
            }
            hideButton.click();
            BrowserUtils.wait(2);
        }
        return true;
    }

    public boolean verifyValuesLessThanOnePercent(String investmentsXpath) {
        String investmentPerc = "";
        double investment;
        List<WebElement> investments = Driver.getDriver().findElements(By.xpath(investmentsXpath));
        for (int i = 0; i < investments.size(); i++) {
            System.out.println("i = " + i);
            BrowserUtils.scrollTo(investments.get(i));
            investmentPerc = investments.get(i).getText();
            System.out.println("investments.size() = " + investments.size());
            System.out.println("investmentPerc = " + investmentPerc);
            investment = Double.parseDouble(investmentPerc
                    .replace("%", "")
                    .replace("<", ""));
            System.out.println("investment = " + investment);
            if (investment > 0 && investment < 1) {
                System.out.println("Investment is less then one");
                if (investmentPerc.equals("<1%")) {
                    System.out.println("Investment has <1%");
                    return false;
                }
            }
        }
        return true;
    }


    public void hoverOverRandomCountryOnMapAndCheckIfTooltipDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(map));

        //Some countries are not eligible to hover over by automation so filter them out

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

        /*
        countriesOnMap.forEach(e -> {
            System.out.println("e.getAttribute(\"class\") = " + e.getAttribute("class"));
            System.out.println("e.getCssValue() = " + e.getCssValue("path"));
            System.out.println("e.getRect() = " + e.getRect().toString());
            System.out.println("e = " + e.getSize().width);

        });
        */


        //get country name from class attribute
        //e.getAttribute("class") = highcharts-point highcharts-name-germany highcharts-key-de
        String countryName = randomCountry
                .getAttribute("class")
                .substring(33, randomCountry
                        .getAttribute("class").lastIndexOf(" "));

        String expectedCountry = "";
        String[] words = countryName.split("-");
        for (String word : words) {
            expectedCountry += word.substring(0, 1).toUpperCase() + (word.substring(1)) + " ";
        }
        expectedCountry = expectedCountry.trim();

        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(countriesOnMap.get(0)).pause(2000).build().perform();

        String tooltipDetails = maptooltip.get(0).getAttribute("innerHTML");

        //Get country name from innerHTML
        //<tspan style=\"font-weight:bold;\"> Country: </tspan>China<tspan class....
        System.out.println("tooltipDetails = " + tooltipDetails);
        String actualCountry =
                tooltipDetails.substring(tooltipDetails.indexOf("</tspan>") + 8,
                        tooltipDetails.indexOf("<tspan class"));

        assertTestCase.assertEquals(actualCountry, expectedCountry, "Country Map Tooltip Verification", 1310);
    }


    /**
     * Checks if Load Mask is Displayed in Physical Risk Management Page
     * If true it means UI made an API call
     * <p>
     * You need to pass table/section subtitle as parameter
     * ex: Portfolio Score
     * <p>
     * In this case method will check if load mask is on page in that specific section
     *
     * @param loadingData - subtitle of table/section
     * @return true - if not displayed returns false
     */

    public boolean checkIfPortfolioLoadMaskIsDisplayed(String loadingData) {
        try {
            System.out.println("Check if " + loadingData + " Load Mask Is Displayed");
            String xpath = "(//*[text()=' Portfolio ']//..//div[@aria-busy])[1]";
            By loadMaskByXpath = By.xpath(xpath);
            WebElement loadMask = Driver.getDriver().findElement(loadMaskByXpath);
            boolean loadMaskIsOnScreen = loadMask.isDisplayed();
            System.out.println(loadingData + " Load Mask Is Displayed");
            System.out.println("Wait until loading completed");
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loadMaskByXpath));
            System.out.println("Data is pulled up");
            System.out.println("TEST PASSED");
            return loadMaskIsOnScreen;
        } catch (Exception e) {
            System.out.println(loadingData + " Load Mask Is Not Displayed");
            System.out.println("FAILED");
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkIfRegionMapLabelIsCorrect() {
        wait.until(ExpectedConditions.visibilityOf(mapLabel));
        BrowserUtils.scrollTo(mapLabel);
        String actualMapLabelText = mapLabel.getText();
        System.out.println("actualMapLabelText = " + actualMapLabelText);
        String expectedMapLabelText = "Weighted average scores based on portfolio companies’ headquarter locations";
        return actualMapLabelText.equals(expectedMapLabelText);
    }

    public boolean checkIfRegionMapIsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(map));
            BrowserUtils.scrollTo(map);
            System.out.println("TEST PASSED");
            return portfolioCoverageTable.isDisplayed();
        } catch (Exception e) {
            System.out.println("FAILED");
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Checks if Load Mask is Displayed in Dropdown
     * If true it means UI made an API call
     * <p>
     * You need to pass dropdown name as parameter
     * ex: Export
     * <p>
     * In this case method will check if load mask is in the dropdown
     *
     * @param loadingData - name of dropdown
     * @return true - if not displayed returns false
     */

    public boolean checkIfDropdownLoadMaskIsDisplayedFor(String loadingData) {
        try {
            System.out.println("Check if " + loadingData + " Load Mask Is Displayed");
            String xpath = "//li[text()='" + loadingData + "']//..//div[@aria-busy]";
            By loadMaskByXpath = By.xpath(xpath);
            WebElement loadMask = Driver.getDriver().findElement(loadMaskByXpath);
            boolean loadMaskIsOnScreen = loadMask.isDisplayed();
            System.out.println(loadingData + " Load Mask Is Displayed");
            System.out.println("Wait until loading completed");
            wait.until(ExpectedConditions.invisibilityOf(loadMask));
            System.out.println("Data is pulled up");
            System.out.println("TEST PASSED");
            return loadMaskIsOnScreen;
        } catch (Exception e) {
            System.out.println(loadingData + " Load Mask Is Not Displayed");
            System.out.println("FAILED");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if Table is Displayed in Physical Risk Management Page
     * <p>
     * You need to pass table/section subtitle as parameter
     * ex: Portfolio Score
     * <p>
     * In this case method will check if table is on page in that specific section
     *
     * @param dataTable - subtitle of table/section
     * @return true - if not displayed returns false
     */
    //overview-portfolio-score-minimaltable-test-id
    //
    public boolean checkIfPortfolioTableIsDisplayed(String dataTable) {
        try {
            if (dataTable.equals("Coverage")) {
                return checkIfPortfolioCoverageChartIsDisplayed();
            } else if (dataTable.equals("Score")) {
                System.out.println("Checking Portfolio Score");
                String id = "PortfolioScoreESG-test-id";
                WebElement tableElement = Driver.getDriver().findElement(By.id(id));
                wait.until(ExpectedConditions.visibilityOf(tableElement));
                System.out.println("TEST PASSED");
                return tableElement.isDisplayed();
            } else {
                System.out.println("Check if " + dataTable + " Is Displayed");
                String xpath = "//div[text()='" + dataTable + "')]//..//table";
                WebElement tableElement = Driver.getDriver().findElement(By.xpath(xpath));
                wait.until(ExpectedConditions.visibilityOf(tableElement));
                System.out.println("TEST PASSED");
                return tableElement.isDisplayed();
            }
        } catch (Exception e) {
            System.out.println("FAILED");
            e.printStackTrace();
            return false;
        }
    }

    public String getPortfolioScoreFromUI() {
        return wait.until(ExpectedConditions.visibilityOf(portfolioScore)).getText();
    }

    public String getPortfolioScoreCategoryFromUI() {
        return wait.until(ExpectedConditions.visibilityOf(portfolioScoreCategory)).getText();
    }

    public boolean checkIfPortfolioScoreCategoryColorIsCorrect(String researchLine) {
        try {
            System.out.println("Portfolio Score Category Validation Started");
            System.out.println("###################################################################");
            String scoreCategory = getPortfolioScoreCategoryFromUI();
            System.out.println("Score/ScoreCategory:" + scoreCategory);
            String expectedColor;
            String actualColor = Color.fromString(portfolioScoreCategory.getCssValue("background-color")).asHex();

            if (researchLine.equals("Physical Risk Hazards")) {
                actualColor = Color.fromString(
                        Driver.getDriver().findElement(By.xpath("//div[./*[starts-with(text(),'Highest Risk Hazard')]]"))
                                .getCssValue("background-color")).asHex();
                //color is one of expected colors
                System.out.println(actualColor);
                return Arrays.asList(new String[]{"#4FA3CD", "#8DA3B7", "#A9898E", "#C06960", "#D63229"}).contains(actualColor.toUpperCase());
            } else if (Arrays.asList("Operations Risk", "Market Risk", "Supply Chain Risk").contains(researchLine)) {
                //for these pages, we show score itself instead score category
                expectedColor = getColorByScore(researchLine, Integer.parseInt(scoreCategory));
            } else {
                expectedColor = getColorByScoreCategory(researchLine, scoreCategory);
            }

            System.out.println("Actual Color:" + actualColor);
            System.out.println("Expected color:" + expectedColor);
            System.out.println("\n###################################################################");
            System.out.println("Portfolio Score Validation Completed");
            return actualColor.equalsIgnoreCase(expectedColor);

        } catch (Exception e) {
            System.out.println("### Portfolio Score Validation Error ###");
            e.printStackTrace();
            return false;
        }
    }

    //============= Portfolio Coverage Methods

    /**
     * Checks if Portfolio Coverage Chart Titles Displayed in Physical Risk Management Page
     * <p>
     *
     * @return true - if not displayed returns false
     */

    public boolean checkIfPortfolioCoverageChartTitlesAreDisplayed() {
        try {
            System.out.println("Check if Portfolio Coverage Chart Titles Are Displayed");
            wait.until(ExpectedConditions.visibilityOf(portfolioCoverageCompaniesChartTitle));
            wait.until(ExpectedConditions.visibilityOf(portfolioCoverageInvestmentChartTitle));
            System.out.println("TEST PASSED");
            return portfolioCoverageCompaniesChartTitle.isDisplayed() && portfolioCoverageInvestmentChartTitle.isDisplayed();
        } catch (Exception e) {
            System.out.println("FAILED");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Checks if Portfolio Coverage Chart is Displayed in Physical Risk Management Page
     *
     * @return true - if not displayed returns false
     */

    public boolean checkIfPortfolioCoverageChartIsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(portfolioCoverageTable));
            System.out.println("TEST PASSED");
            return portfolioCoverageTable.isDisplayed();
        } catch (Exception e) {
            System.out.println("FAILED");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * This method return a Map contains Portfolio Coverage Table
     * companies -> coverage rate ex : 123/129
     * investment -> % rate ex 95%
     *
     * @return Portfolio Coverage Table as a Map
     */
    public Map<String, WebElement> getPortfolioCoverageChartAsMap() {
        Map<String, WebElement> table = new HashMap<>();
        System.out.println("Getting Portfolio Score Chart Values As Map");
        try {
            table.put("Companies", portfolioCoverageValues.get(0));
            table.put("Investment", portfolioCoverageValues.get(1));
            return table;
        } catch (Exception e) {
            System.out.println("Portfolio Coverage Elements Cannot Found");
            return null;
        }
    }

    public String getCoverageCompanies() {
        return wait.until(ExpectedConditions.visibilityOf(portfolioCoverageCompanies)).getText();
    }

    public String getCoverageInvestment() {
        return wait.until(ExpectedConditions.visibilityOf(portfolioCoverageInvestment)).getText();
    }

    public void clickExportDropdown() {
        wait.until(ExpectedConditions.elementToBeClickable(exportDropdown)).click();
    }

    /**
     * Select data and export
     *
     * @param dataType     - For now only Data is acceptable. CSV, PDF will come later
     * @param researchLine - page name
     */
    public void selectExportData(String dataType, String researchLine) {
        //li[contains(text(),'Data - Carbon Footprint')]
        String xpath = "";
        if (dataType.toLowerCase().equals("pdf"))
            xpath = String.format("//li[contains(text(),'%s (%s)')]", researchLine, dataType);
        else {
            xpath = String.format("//li[contains(text(),'%s - %s')]", dataType, researchLine);
            if(researchLine.equals("ESG Assessments"))
                xpath = String.format("//li[contains(text(),'%s')]", researchLine);
        }
        System.out.println(xpath);
        WebElement exportOption = Driver.getDriver().findElement(By.xpath(xpath));
        wait.until(ExpectedConditions.elementToBeClickable(exportOption)).click();
    }

    public void verifyExportOptions(String researchLine) {
        String pdfOptionXpath = "//li[starts-with(@id,'ExportDropdown')][@data-value='pdf'][text()='"+researchLine+" (pdf)']";
        String excelOptionXpath = "//li[starts-with(@id,'ExportDropdown')][@data-value='individual'][text()='Data - "+researchLine+" (.xlsx)']";
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath(pdfOptionXpath), 1));
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath(excelOptionXpath), 1));
    }

    /**
     * Checks if Physical Risk Management Subtitle Displayed
     *
     * @return true - if not displayed returns false
     */

    public boolean checkIfExportingLoadingMaskIsDisplayed() {
        try {
            System.out.println("Exporting Loading Mask Check");
            boolean loadMaskIsDisplayed = wait.until(ExpectedConditions.visibilityOf(exportingLoadMask)).isDisplayed();
            wait.until(ExpectedConditions.invisibilityOf(exportingLoadMask));
            System.out.println("Exporting Completed!");
            return loadMaskIsDisplayed;
        } catch (Exception e) {
            System.out.println("Failed");
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkIfLeadersAndLaggardsTableDisplayed(String commonWidgetsID) {
        try {
            wait.until(ExpectedConditions.visibilityOf(LeadersAndLaggardsTable));
            System.out.println("TEST PASSED");
            return LeadersAndLaggardsTable.isDisplayed();
        } catch (Exception e) {
            System.out.println("FAILED");
            e.printStackTrace();
            return false;
        }
    }


    public boolean checkifLeadersAndLaggardsTableUIIsperDesign(String PageName) {
        try {
            List<String> ExpectedColumnList = new ArrayList<String>();
            switch (PageName) {
                case "Physical Risk Hazards":
                case "Operations Risk":
                case "Market Risk":
                case "Supply Chain Risk":
                    ExpectedColumnList.add("Rank");
                    ExpectedColumnList.add("Company");
                    ExpectedColumnList.add("% Investment");
                    ExpectedColumnList.add("Score");
                    //ExpectedColumnList.add("Updated");
                    break;
                case "Physical Risk Management":
                case "Energy Transition Management":
                case "TCFD Strategy":
                    ExpectedColumnList.add("Rank");
                    ExpectedColumnList.add("Company");
                    ExpectedColumnList.add("% Investment");
                    ExpectedColumnList.add("Score");
                    ExpectedColumnList.add("");
                    //ExpectedColumnList.add("Updated");
                    break;
                case "Brown Share Assessment":
                    ExpectedColumnList.add("Rank");
                    ExpectedColumnList.add("Company");
                    ExpectedColumnList.add("% Investment");
                    ExpectedColumnList.add("Score Range");
                    //ExpectedColumnList.add("Updated");
                    break;
                case "Green Share Assessment":
                    ExpectedColumnList.add("Rank");
                    ExpectedColumnList.add("Company");
                    ExpectedColumnList.add("% Investment");
                    ExpectedColumnList.add("Score Range");
                    ExpectedColumnList.add("");
                    // ExpectedColumnList.add("Updated");
                    break;
                case "Carbon Footprint":
                    ExpectedColumnList.add("Rank");
                    ExpectedColumnList.add("Company");
                    ExpectedColumnList.add("% Investment");
                    ExpectedColumnList.add("Score (tCO2eq)");
                    ExpectedColumnList.add("");
                    // ExpectedColumnList.add("Updated");
                    break;
                case "ESG Assessments":
                    ExpectedColumnList.add("Rank");
                    ExpectedColumnList.add("Company");
                    ExpectedColumnList.add("% Investment");
                    ExpectedColumnList.add("ESG Score");
                    ExpectedColumnList.add("Model Version");
                    break;

                default:
                    System.out.println("You provided wrong page name for this method: checkifLeadersAndLaggardsTableUIIsperDesign()");
            }

            wait.until(ExpectedConditions.visibilityOf(LeadersAndLaggardsTable));
            List<String> ColumnListForLeaders = new ArrayList<String>();
            List<String> ColumnListForLaggards = new ArrayList<String>();
            BrowserUtils.scrollTo(LeadersAndLaggardsTableColumns.get(0));
            for (int i = 0; i < (LeadersAndLaggardsTableColumns.size()) / 2; i++) {
                ColumnListForLeaders.add(LeadersAndLaggardsTableColumns.get(i).getText());
                Assert.assertTrue(leadersSubTitle.isDisplayed());
            }
            for (int i = (LeadersAndLaggardsTableColumns.size()) / 2; i < LeadersAndLaggardsTableColumns.size(); i++) {
                ColumnListForLaggards.add(LeadersAndLaggardsTableColumns.get(i).getText());
                Assert.assertTrue(laggardsSubTitle.isDisplayed());
            }

            return ColumnListForLeaders.equals(ExpectedColumnList) && ColumnListForLaggards.equals(ExpectedColumnList);
        } catch (Exception e) {
            System.out.println("FAILED");
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyLeadersHasMax10Companies() {
        try {
            return leadersCompaniesList.size() >= 1 && leadersCompaniesList.size() <= 10;
        } catch (Exception e) {
            return leadersNoDataMessage.isDisplayed();
        }
    }

    public boolean verifyLaggardsHasMax10Companies() {
        try {
            return laggardsCompaniesList.size() <= 10;
        } catch (Exception e) {
            return laggardsNoDataMessage.isDisplayed();
        }
    }

    public boolean verifyUpdatesHasMax10Companies() {
        try {
            return updatesCompaniesList.size() >= 1 && updatesCompaniesList.size() <= 10;
        } catch (Exception e) {
            return updatesNoDataMessage.isDisplayed();
        }
    }

    public boolean verifyUpdatesSortingOrder(String page){
        try {
            List<WebElement> rows = Driver.getDriver().findElements(By.xpath("//td[@heap_id='updates']"));
            if (rows.size() > 0) {
                SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
                for (int i = 1; i < rows.size() - 1; i++) {
                    String updateDateXpath = "(//td[@heap_id='updates']/following-sibling::td[1]//span[@title])";
                    if(page.equals("Brown Share Assessment"))
                        updateDateXpath = "(//td[@heap_id='updates']/following-sibling::td[1])";
                    Date updatedDate1 = sdformat.parse(Driver.getDriver().findElement(By.xpath(updateDateXpath+"[" + i + "]")).getText());
                    Date updatedDate2 = sdformat.parse(Driver.getDriver().findElement(By.xpath(updateDateXpath+"[" + (i + 1) + "]")).getText());

                    if (updatedDate1.compareTo(updatedDate2) < 0)
                        return false;
                    else if (updatedDate1.compareTo(updatedDate2) == 0) {
                        float row1Inv = Float.parseFloat(Driver.getDriver().findElement(By.xpath("(//td[@heap_id='updates']/following-sibling::td[5])[" + i + "]")).getText().replace("%", ""));
                        float row2Inv = Float.parseFloat(Driver.getDriver().findElement(By.xpath("(//td[@heap_id='updates']/following-sibling::td[5])[" + (i + 1) + "]")).getText().replace("%", ""));
                        if (row1Inv < row2Inv)
                            return false;
                        else if (row1Inv == row2Inv) {
                            String row1Company = Driver.getDriver().findElement(By.xpath("(//td[@heap_id='updates']//span[@title])[" + i + "]")).getText();
                            String row2Company = Driver.getDriver().findElement(By.xpath("(//td[@heap_id='updates']//span[@title])[" + (i + 1) + "]")).getText();
                            if (row1Company.compareTo(row2Company) > 0)
                                return false;
                        }
                    }
                }
            } else {
                System.out.println("No data was displayed for Updates Section");
            }
        }catch(Exception e){
            return false;
        }

        return true;
    }


    public boolean verifyRegionSectorsTables(String PageName) {
        try {
            List<String> ExpectedColumnList = expectedRegionSectorList;
            List<String> RegionSectorsTablesHeaders = new ArrayList<String>();

            switch (PageName) {
                case "Physical Risk Hazards":
                case "Operations Risk":
                case "Market Risk":
                case "Supply Chain Risk":
                case "Brown Share Assessment":
                    RegionSectorsTablesHeaders.addAll(Arrays.asList("Weighted Avg.", "Companies", "Investment", "Score Range", "Companies", "% Investment"));
                    break;
                case "Physical Risk Management":
                case "Carbon Footprint":
                case "Energy Transition Management":
                case "TCFD Strategy":
                case "Green Share Assessment":
                    RegionSectorsTablesHeaders.addAll(Arrays.asList("Weighted Avg.", "Companies", "Investment", "Category", "Companies", "% Investment"));
                    break;
                case "Temperature Alignment":
                    RegionSectorsTablesHeaders.addAll(Arrays.asList("Weighted Avg.", "Companies", "Investment", "Alignment", "Companies", "% Investment"));
                    break;
                default:
                    System.out.println("You provided wrong page name for this method: checkifLeadersAndLaggardsTableUIIsperDesign()");
            }

            wait.until(ExpectedConditions.visibilityOf(RegionSectorsTables.get(0)));

            BrowserUtils.scrollTo(RegionSectorsTables.get(0));
            for (int i = 0; i < RegionSectorsTables.size(); i++) {
                String tableText = RegionSectorsTables.get(i).getText();
                String expectedColumn = ExpectedColumnList.get(0);
                for (int k = 0; k < ExpectedColumnList.size(); k++) {
                    if (tableText.contains(ExpectedColumnList.get(k))) {
                        expectedColumn = ExpectedColumnList.get(k);
                        break;
                    }
                }

                Assert.assertTrue(tableText.contains(expectedColumn), "Failure to match table header");
                for (int j = 0; j < RegionSectorsTablesHeaders.size(); j++) {
                    Assert.assertTrue(tableText.contains(RegionSectorsTablesHeaders.get(j)), "Failure to match table column header");
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("FAILED");
            Assert.fail("Failure to match table header");
            return false;
        }
    }

    public boolean checkIfDrillDownPanelIsDisplayed() {
        try {
            System.out.println("Check if Drill Down Panel Is Displayed");
            wait.until(ExpectedConditions.visibilityOf(drillDownPanel));
            System.out.println("Drill Down Panel is Displayed");
            return drillDownPanel.isDisplayed();
        } catch (Exception e) {
            System.out.println("Drill Down Panel is NOT Displayed");
            return false;
        }
    }


    public boolean verifyRegionSectorDrillDowns(String page) {

        List<String> headerList = new ArrayList<>();

        switch (page) {
            case "Physical Risk Hazards":
            case "Operations Risk":
            case "Market Risk":
            case "Supply Chain Risk":
                headerList.add("0-19 Score % Investment");
                headerList.add("20-39 Score % Investment");
                headerList.add("40-59 Score % Investment");
                headerList.add("60-79 Score % Investment");
                headerList.add("80-100 Score % Investment");
                break;

            case "Physical Risk Management":
            case "Energy Transition Management":
            case "TCFD":
                headerList.add("Advanced Score % Investment");
                headerList.add("Robust Score % Investment");
                headerList.add("Limited Score % Investment");
                headerList.add("Weak Score % Investment");
                break;

            case "Brown Share Assessment":
                headerList.add("0% Score Range % Investment");
                headerList.add("0-20% Score Range % Investment");
                headerList.add("20-100% Score Range % Investment");
                break;

            case "Carbon Footprint":
                headerList.add("Moderate Score (tCO2eq) % Investment");
                headerList.add("Significant Score (tCO2eq) % Investment");
                headerList.add("High Score (tCO2eq) % Investment");
                headerList.add("Intense Score (tCO2eq) % Investment");
                break;

            case "Green Share Assessment":
                headerList.add("Major Score Range % Investment");
                headerList.add("Significant Score Range % Investment");
                headerList.add("Minor Score Range % Investment");
                headerList.add("None Score Range % Investment");
                break;
            case "Temperature Alignment":
                headerList.add("Well Below 2°C Implied Temperature Rise % Investment");
                headerList.add("Below 2°C Implied Temperature Rise % Investment");
                headerList.add("2°C Implied Temperature Rise % Investment");
                headerList.add("Above 2°C Implied Temperature Rise % Investment");
                headerList.add("No Info Implied Temperature Rise % Investment");
                break;

        }

        wait.until(ExpectedConditions.visibilityOfAllElements(regionSectorChartNames));

        //loop through each Region/Sector chart, click and open drill down
        for (int i = 0; i < regionSectorChartNames.size(); i++) {
            WebElement regionSectorChart = regionSectorChartNames.get(i);

            String expectedRegionSectorName = regionSectorChartNames.get(i).getText();
            Assert.assertTrue(expectedRegionSectorList.contains(expectedRegionSectorName));

            int countOfCompanies = Integer.parseInt(regionSectorCompanyCountsInChart.get(i).getText().substring(0, regionSectorCompanyCountsInChart.get(i).getText().indexOf('/')));

            wait.until(ExpectedConditions.elementToBeClickable(regionSectorChart)).click();

            System.out.println("Clicked on " + regionSectorChart.getText() + " chart");

            wait.until(ExpectedConditions.visibilityOf(drillDownPanel));

            Assert.assertTrue(checkIfDrillDownPanelIsDisplayed(), "Drill Down Panel is not displayed");

            System.out.println("Drill Down Panel is opened");

            String drillDownTitleText = drillDownTitle.getText();
            Assert.assertEquals(drillDownTitleText, expectedRegionSectorName);

            Assert.assertTrue(checkIfHideButtonIsDisplayedInDrillDownPanel(), "Hide button is not displayed in Drill Down Panel");

            int categorySizeInChart = headerList.size();
            int count = 0;
            for (int j = 0; j < categorySizeInChart; j++) {
                String companyCountInCategory = companyCountColumnPerCategoryInRegionSectorChart.get((categorySizeInChart * i) + j).getText();
                System.out.println("=======" + companyCountInCategory);
                //if there is no company in category, section header will not be displayed in drill down panel
                if (companyCountInCategory.equals("0")) continue;

                String actualHeader = drillDownColumnHeaders.get(count).getText();
                String expectedHeader = headerList.get(j);

                Assert.assertEquals(actualHeader, expectedHeader);
                count++;
            }
            System.out.println("All Drill Down headers are verified");

            Assert.assertEquals(count, drillDownSections.size());

            int actualCompanyCount = drillDownCompanyRows.size();

            Assert.assertEquals(actualCompanyCount, countOfCompanies, "Count of companies in Drill Down is not matched with Chart");

            clickHideButtonInDrillDownPanel();

            wait.until(ExpectedConditions.elementToBeClickable(regionSectorChart));

            System.out.println("Drill Down Panel is closed");
        }
        return true;

    }

    public boolean checkIfDrillDownPanelsAreClosed() {
        try {
            System.out.println("Check if Drill Down Panel is closed by a click on out of Panel");
            for (int i = 0; i < regionSectorChartNames.size(); i++) {
                WebElement regionSectorChart = regionSectorChartNames.get(i);
                wait.until(ExpectedConditions.elementToBeClickable(regionSectorChart)).click();
                System.out.println("Clicked on " + regionSectorChart.getText() + " chart");
                wait.until(ExpectedConditions.visibilityOf(drillDownPanel));
                System.out.println("Drill Down Panel is opened");
                clickOutsideOfDrillDownPanel();
                System.out.println("Clicked on outside of drill down panel");
                wait.until(ExpectedConditions.visibilityOf(regionSectorChart));
                System.out.println("Drill Down Panel is closed");
                System.out.println("TEST PASSED");
            }
            return true;
        } catch (Exception e) {
            System.out.println("FAILED");
            System.out.println("Drill Down is not closed properly");
            return false;
        }
    }

    public boolean checkIfRegionSectorChartClickableAndHighlightedWithHover() {
        try {
            System.out.println("Check if Region/Sector Chart is highlighted with hover");
            for (int i = 0; i < RegionSectorsTables.size(); i++) {
                WebElement regionSectorChart = RegionSectorsTables.get(i);
                wait.until(ExpectedConditions.elementToBeClickable(regionSectorChart));
                Assert.assertEquals(regionSectorChart.getCssValue("border"), "1px solid rgb(255, 255, 255)");
                new Actions(Driver.getDriver()).moveToElement(regionSectorChart).build().perform();
                System.out.println("Hovered over chart");
                Assert.assertEquals(regionSectorChart.getCssValue("border"), "1px solid rgb(83, 121, 163)");
                System.out.println("Chart is highlighted with hover action");
                System.out.println("TEST PASSED");
            }
            return true;
        } catch (Exception e) {
            System.out.println("FAILED");
            System.out.println("Drill Down is not closed properly");
            return false;
        }
    }

    public boolean checkIfHideButtonIsDisplayedInDrillDownPanel() {
        try {
            System.out.println("Check if hide button Is Displayed");
            wait.until(ExpectedConditions.visibilityOf(hideButton));
            System.out.println("TEST PASSED");
            return hideButton.isDisplayed();
        } catch (Exception e) {
            System.out.println("FAILED");
            e.printStackTrace();
            return false;
        }
    }

    public void clickHideButtonInDrillDownPanel() {
        wait.until(ExpectedConditions.visibilityOf(hideButton)).click();
    }


    public void clickOutsideOfDrillDownPanel() {
        outSideOfDrillDownPanel.click();
    }

    public boolean checkIfUnderLyingDataMetricsIsAvailable(String page) {
        boolean matched = false;
        try {
            String headerName = "";
            String headerDivId = "";
            List<String> dataRows = new ArrayList<String>();
            List<String> dataHeaders = new ArrayList<String>();
            String followingXpath = "div/div/div/span";
            String followingXpath1 = "";

            switch (page) {
                case "Physical Risk Hazards":
                    headerDivId = "Physical Risk Hazards";
                    headerName = "Additional Physical Risk Hazards";
                    dataRows.add("Country of Sales");
                    dataRows.add("Weather Sensitivity");
                    dataRows.add("Resource Demand");
                    dataRows.add("Country of Origin");
                    dataHeaders.add("Market Risk");
                    dataHeaders.add("Supply Chain Risk");
                    String followingXpathForSubHeading = "//../following-sibling::div";
                    break;
                case "Operations Risk":
                    headerDivId = "//div[@id='operations_risk_risk_categories']/div//parent::div/parent::header";
                    headerName = page + " - Risk Categories";
                    dataRows.add("Floods");
                    dataRows.add("Heat Stress");
                    dataRows.add("Hurricanes & Typhoons");
                    dataRows.add("Sea Level Rise");
                    dataRows.add("Water Stress");
                    dataRows.add("Wildfires");
                    dataHeaders.add("% Facilities Exposed");
                    break;
                case "Market Risk":
                    headerDivId = "//div[@id='market_risk_risk_categories']/div//parent::div/parent::header";
                    headerName = page + " - Risk Categories";
                    dataRows.add("Country of Sales");
                    dataRows.add("Weather Sensitivity");
                    dataHeaders.add("Company Distribution");
                    break;
                case "Supply Chain Risk":
                    headerDivId = "supply_chain_risk_risk_categories";
                    headerName = page + " - Risk Categories";
                    dataRows.add("Resource Demand");
                    dataRows.add("Country of Origin");
                    dataHeaders.add("Company Distribution");
                    break;
                case "Carbon Footprint":
                    headerDivId = "//div[@id='carbon_footprint_risk_categories']/div//parent::div/parent::header";
                    headerName = page + " Scope";
                    dataRows.add("Scope 1 (t CO2 eq)");
                    dataRows.add("Scope 2 (t CO2 eq)");
                    dataRows.add("Scope 3 (t CO2 eq)");
                    dataHeaders.add("Total Scope 1 Emissions");
                    dataHeaders.add("624,063,885 (t CO2 eq)");
                    dataHeaders.add("Total Scope");
                    dataHeaders.add("(t CO2 eq)");
                    followingXpath = "div/div/div/div/span";
                    followingXpath1 = "div/div[2]/span";
                    break;
                case "Brown Share Assessment":
                    headerDivId = "//div[@id='fossil_fuel_disclosures']/div//parent::div/parent::header";
                    headerName = "Fossil Fuel Disclosures";
                    dataRows.add("Fossil fuels industry - Upstream");
                    dataRows.add("Fossil fuels industry - Midstream");
                    dataRows.add("Fossil fuels industry - Generation");
                    dataRows.add("Fossil fuels industry revenues");
                    dataRows.add("Fossil fuels reserves - Coal reserves");
                    dataRows.add("Fossil fuels reserves - Oil reserves");
                    dataRows.add("Fossil fuels reserves - Natural Gas reserves");
                    dataRows.add("Coal mining");
                    dataRows.add("Thermal coal mining");
                    dataRows.add("Coal-fuelled power generation");
                    dataRows.add("Tar sands and oil shale extraction");
                    dataRows.add("Offshore arctic drilling");
                    dataRows.add("Ultra-deep offshore");
                    dataRows.add("Coal-bed methane (coal seam gas)");
                    dataRows.add("Hydraulic fracturing");
                    dataRows.add("Liquefied Natural Gas (LNG)");
                    dataRows.add("Total Potential Emissions");
                    dataRows.add("Total Coal Potential Emissions");
                    dataRows.add("Total Natural Gas Potential Emissions");
                    dataRows.add("Total Oil Potential Emissions");
                    dataHeaders.add("Investment in Category");
                    dataHeaders.add("Companies in Category");
                    followingXpath = "div/div/div/span";
                    break;
                case "Energy Transition Management":
                    headerDivId = "energy_transition_management";
                    headerName = page;
                    dataRows.add("Societal Impacts of Products/Services");
                    dataRows.add("Green Products");
                    dataRows.add("Energy");
                    dataRows.add("Atmospheric Emissions");
                    dataRows.add("Transportation");
                    dataRows.add("Use and Disposal of Products");
                    dataHeaders.add("Investment in Category");
                    break;
                case "TCFD Strategy":
                    headerDivId = "TCFD_disclosures";
                    headerName = "TCFD Disclosures";
                    dataRows.add("Climate-change factored into financial planning");
                    dataRows.add("Integration into overall enterprise risk management");
                    dataRows.add("Climate change scenario analysis and their potential impacts on the company's business");
                    dataRows.add("Use of an internal carbon price");
                    dataRows.add("Processes used to inform the board members/committees about climate change related issues");
                    dataRows.add("Engagement with companies or value chain");
                    dataRows.add("TCFD Signatory");
                    dataRows.add("Processes used to inform the management (CEO, CFO, COO etc) of climate-related");
                    dataRows.add("TCFD Reporting Compliance");
                    dataRows.add("Developed a low-carbon transition plan to support long-term business strategy");
                    dataRows.add("Development of products or services contributing to transition to a low carbon economy");
                    dataRows.add("Risk mapping, materiality assessments and balanced score cards");
                    dataHeaders.add("% Investment");
                    dataHeaders.add("Companies in Holdings");
                    break;
                case "Green Share Assessment":
                    headerDivId = "//div[@id='overview_of_products_and_technologies']/div//parent::div/parent::header";
                    headerName = "Overview of the Products and Technologies\n" +
                            "Estimate of Incorporation";
                    dataRows.add("Building materials allowing water efficiency");
                    dataRows.add("Building materials from wood");
                    dataRows.add("Electric engine");
                    dataRows.add("Electric vehicles");
                    dataRows.add("Energy demand-side management");
                    dataRows.add("Energy storage");
                    dataRows.add("Green buildings");
                    dataRows.add("Green lending");
                    dataRows.add("Hybrid engine");
                    dataRows.add("Hybrid vehicles");
                    dataRows.add("Renewable energy");
                    dataHeaders.add("Investment in Category");
                    followingXpath = "div/div/div/span";
                    break;

            }
            WebElement underlyingDataMetricsHeader = Driver.getDriver().findElement(By.xpath(headerDivId));
            BrowserUtils.scrollTo(underlyingDataMetricsHeader);
            if (underlyingDataMetricsHeader.getText().equals(headerName)) {
                List<WebElement> spanForDataRows = underlyingDataMetricsHeader.findElements(By.xpath("following-sibling::" + followingXpath));
                if (spanForDataRows.size() == dataRows.size()) {
                    for (int i = 0; i < dataRows.size(); i++) {
                        String[] spanData = spanForDataRows.get(i).getText().split("\n");
                        if (i == 0 && !headerDivId.equals("Physical Risk Hazards")) {
                            if (page.equals("Carbon Footprint")) {
                                for (String headerValue : dataHeaders) {
                                    if (spanForDataRows.get(i).getText().contains(headerValue)) {
                                        System.out.println("MATCHED " + headerValue);
                                        matched = true;
                                    } else {
                                        return false;
                                    }
                                }
                            } else {
                                for (String headerValue : dataHeaders) {
                                    if (Arrays.stream(spanData).anyMatch(p -> p.contains(headerValue))) {
                                        matched = true;
                                    } else {
                                        return false;
                                    }
                                }
                            }
                        }
                        if (dataRows.contains(spanData[0].toString()))
                            matched = true;
                        else
                            return false;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("FAILED");
            e.printStackTrace();
            matched = false;
        }
        return matched;
    }

    public static WebElement getOneElement(List<WebElement> elements, String requiredText) {
        for (WebElement element : elements) {
            if (element.getText().contains(requiredText)) {
                return element;
            }
        }
        return null;
    }

    public boolean validatePortfolioDistribution(String page) {
        try {
            List<String> dataRows = new ArrayList<String>();
            List<String> dataHeaders = new ArrayList<String>();
            dataHeaders.add("% Investment");
            dataHeaders.add("Companies");
            switch (page) {
                case "Physical Risk Hazards":
                case "Operations Risk":
                case "Market Risk":
                case "Supply Chain Risk":
                    dataRows.add("0-19");
                    dataRows.add("20-39");
                    dataRows.add("40-59");
                    dataRows.add("60-79");
                    dataRows.add("80-100");
                    dataHeaders.add("Score Range");
                    break;
                case "Physical Risk Management":
                case "Energy Transition Management":
                case "TCFD Strategy":
                    dataRows.add("Advanced");
                    dataRows.add("Robust");
                    dataRows.add("Limited");
                    dataRows.add("Weak");
                    dataHeaders.add("Category");
                    break;
                case "Carbon Footprint":
                    dataRows.add("Moderate");
                    dataRows.add("Significant");
                    dataRows.add("High");
                    dataRows.add("Intense");
                    dataHeaders.add("Category");
                    break;
                case "Brown Share Assessment":
                    dataRows.add("0%");
                    dataRows.add("0-20%");
                    dataRows.add("20-100%");
                    dataHeaders.add("Score Range");
                    break;
                case "Green Share Assessment":
                    dataRows.add("Major");
                    dataRows.add("Significant");
                    dataRows.add("Minor");
                    dataRows.add("None");
                    dataHeaders.add("Category");
                    break;
                case "Temperature Alignment":
                    dataRows.add("Well Below 2°C");
                    dataRows.add("Below 2°C");
                    dataRows.add("2°C");
                    dataRows.add("Above 2°C");
                    dataRows.add("No Info");
                    dataHeaders.add("Temperature Alignment");
            }
            //validate Portfolio Distribution Header
            Assert.assertTrue(portfolioDistributionTable.findElement
                    (By.xpath("./../../preceding-sibling::div")).getText().contains("Portfolio Distribution"));

            //validate Portfolio Distribution Table Headers
            for (String coloumHeaders : dataHeaders)
                Assert.assertTrue(portfolioDistributionTable.findElements
                        (By.xpath("thead//tr//th")).stream().anyMatch(p -> p.getText().equals(coloumHeaders)));

            //validate Portfolio Distribution Table categories
            for (String categories : dataRows)
                Assert.assertTrue(portfolioDistributionTable.findElements
                        (By.xpath("tbody//tr")).stream().filter(p -> p.findElement(By.xpath("(td)[1]")).getText().equals(categories)).count() == 1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getExpectedListOfResearchLines(EntitlementsBundles bundles) {
        switch (bundles) {
            case PHYSICAL_RISK:
                return Arrays.asList("Physical Risk Hazards", "Physical Risk Management");
            //case PHYSICAL_RISK_CLIMATE_GOVERNANCE:
            //    return Arrays.asList("Operations Risk", "Market Risk", "Supply Chain Risk", "Physical Risk Management", "TCFD Strategy", "Green Share Assessment");
            case TRANSITION_RISK:
                return Arrays.asList("Temperature Alignment", "Carbon Footprint", "Green Share Assessment", "Brown Share Assessment"/*, "Temperature Alignment"*/);
            //case TRANSITION_RISK_CLIMATE_GOVERNANCE:
            //    return Arrays.asList("Carbon Footprint", "Brown Share Assessment"/*, "Temperature Alignment"*/, "Energy Transition Management", "TCFD Strategy", "Green Share Assessment");
            //case CLIMATE_GOVERNANCE:
            //    return Arrays.asList("TCFD Strategy", "Green Share Assessment");
            case PHYSICAL_RISK_TRANSITION_RISK:
                return Arrays.asList("Physical Risk Hazards", "Physical Risk Management", "Temperature Alignment", "Carbon Footprint", "Green Share Assessment", "Brown Share Assessment");

            case ALL:
                return Arrays.asList("Physical Risk Hazards", "Physical Risk Management", "Temperature Alignment", "Carbon Footprint", "Green Share Assessment", "Brown Share Assessment", "ESG Assessments");
            default:
                Assert.fail("Bundle not found");
                return null;
        }
    }

    public ResearchLinePage clickOnBenchmarkDropdown() {

        wait.until(ExpectedConditions.elementToBeClickable(benchmarkDropdown)).click();
        return this;
    }

    public void SelectAPortfolioFromBenchmark(String option) {
        //Get all options under dropdown
        // List<WebElement> options = Driver.getDriver().findElements(By.xpath("//li[contains(text(),'" + elementTitle + "')]/../li"));
        List<WebElement> options = Driver.getDriver().findElements(By.xpath("//ul[@role='listbox']//..//li"));

        //select provided option from picked dropdown
        try {

            BrowserUtils.scrollTo(Driver.getDriver().findElement(By.xpath("//ul[@role='listbox']//..//li[text()='" + option + "']")));
            BrowserUtils.clickWithJS(Driver.getDriver().findElement(By.xpath("//ul[@role='listbox']//..//li[text()='" + option + "']")));
            //wait.until(ExpectedConditions.elementToBeClickable(Driver.getDriver().findElement(By.xpath("//ul[@role='listbox']//..//li[text()='" + option + "']")))).click();
        } catch (Exception e) {
            System.out.println("Could not click option under dropdown");
            e.printStackTrace();
        }
    }

    public boolean IsBenchmarkListBoxDisplayed() {
        try {
            System.out.println("Benchmark Section was loaded");
            boolean benchMarkIsDisplayed = wait.until(ExpectedConditions.visibilityOf(BenchmarkSection)).isDisplayed();
            //wait.until(ExpectedConditions.invisibilityOf(BenchmarkSection));
            return benchMarkIsDisplayed;
        } catch (Exception e) {
            System.out.println("Failed");
            e.printStackTrace();
            return false;
        }
    }

    public boolean isNoBenchmarkMessageDisplayed() {
        try {
            System.out.println("Benchmark Section was loaded");
            boolean noBenchMarkMessageIsDisplayed = wait.until(ExpectedConditions.visibilityOf(noBenchmarkMessage)).isDisplayed();
            //wait.until(ExpectedConditions.invisibilityOf(BenchmarkSection));
            return noBenchMarkMessageIsDisplayed;
        } catch (Exception e) {
            System.out.println("Failed");
            e.printStackTrace();
            return false;
        }
    }


    public String getBenchmarkPortfolioName() {
        return benchmarkDropdown.getText();
    }

    public boolean IsBenchmarkCoverageSectionDisplayed() {
        try {
            System.out.println("Benchmark Section was loaded");
            boolean BenchMarkCoverageSectionIsDisplayed = wait.until(ExpectedConditions.visibilityOf(BenchMarkCoverageSection)).isDisplayed();
            return BenchMarkCoverageSectionIsDisplayed;
        } catch (Exception e) {
            System.out.println("Failed");
            e.printStackTrace();
            return false;
        }
    }

    public boolean IsBenchmarkCoverageCompaniesSectionDisplayed() {
        try {
            System.out.println("Benchmark Section was loaded");
            boolean IsCompaniesDataDisplayed = wait.until(ExpectedConditions.visibilityOf(BenchMarkCoverageCompaniesSection)).isDisplayed();
            return IsCompaniesDataDisplayed;
        } catch (Exception e) {
            System.out.println("Failed");
            e.printStackTrace();
            return false;
        }
    }

    public boolean IsBenchmarkCoverageInvestmentSectionDisplayed() {
        try {
            System.out.println("Benchmark Section was loaded");
            boolean IsInvestmentDataDisplayed = wait.until(ExpectedConditions.visibilityOf(BenchMarkCoverageInvestmentSection)).isDisplayed();
            return IsInvestmentDataDisplayed;
        } catch (Exception e) {
            System.out.println("Failed");
            e.printStackTrace();
            return false;
        }
    }

    public String getPortfolioScoreTitle() {
        try {
            System.out.println("Benchmark Section was loaded");
            String portfolioScoreTitle = wait.until(ExpectedConditions.visibilityOf(PortfolioScoreTitle)).getText();
            return portfolioScoreTitle;
        } catch (Exception e) {
            System.out.println("Failed");
            e.printStackTrace();
            return null;
        }
    }

    public String getUIValues(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element)).getText();
        } catch (Exception e) {
            System.out.println("Element not Visible");
            e.printStackTrace();
            return null;
        }
    }

    public String getBenchmarkScoreTitle() {
        try {
            System.out.println("Benchmark Section was loaded");
            String benchmarkScoreTitle = wait.until(ExpectedConditions.visibilityOf(BenchmarkScoreTitle)).getText();
            return benchmarkScoreTitle;
        } catch (Exception e) {
            System.out.println("Failed");
            e.printStackTrace();
            return null;
        }
    }

    public boolean IsEmissionWidgetIsPresent() {
        try {
            System.out.println("Navigate to Emissions section");
            boolean IsEmissionDisplayed = wait.until(ExpectedConditions.visibilityOf(EmissionsSectionWidgetTitle)).isDisplayed();
            return IsEmissionDisplayed;
        } catch (Exception e) {
            System.out.println("Failed");
            e.printStackTrace();
            return false;
        }
    }

    public void navigateToParticluarWidget(String WidgetsName) {
        WebElement linkToNavigate = Driver.getDriver().findElement(By.xpath("//span[contains(text(),'" + WidgetsName + "')]"));
        wait.until(ExpectedConditions.elementToBeClickable(linkToNavigate)).click();
    }

    public boolean IsTotalFinancialEmissionSubsectionPresentUnderEmission() {
        try {
            System.out.println("Navigate to Emissions section");
            boolean IsTotalFinanceEmissionDisplayed = wait.until(ExpectedConditions.visibilityOf(EmissionsTotalFinanceSectionTitle)).isDisplayed();
            return IsTotalFinanceEmissionDisplayed;
        } catch (Exception e) {
            System.out.println("Failed");
            e.printStackTrace();
            return false;
        }
    }

    public boolean IsTotalFinancedEmissionPerMillionSubsectionPresentUnderEmission() {
        try {
            System.out.println("Navigate to Emissions section");
            boolean IsTotalFinanceEmissionDisplayed = wait.until(ExpectedConditions.visibilityOf(Financed_Emissions_per_million_Invested_Title)).isDisplayed();
            return IsTotalFinanceEmissionDisplayed;
        } catch (Exception e) {
            System.out.println("Failed");
            e.printStackTrace();
            return false;
        }
    }

    public boolean IsCarbon_Intensity_per_SalesSubsectionPresentUnderEmission() {
        try {
            System.out.println("Navigate to Emissions section");
            boolean IsTotalFinanceEmissionDisplayed = wait.until(ExpectedConditions.visibilityOf(Carbon_Intensity_per_SalesTitle)).isDisplayed();
            return IsTotalFinanceEmissionDisplayed;
        } catch (Exception e) {
            System.out.println("Failed");
            e.printStackTrace();
            return false;
        }
    }

    public boolean clickAndVerifyMethodologyLink(String researchLine) {
        String METHODOLOGY_PHYSICAL_CLIMATE_RISK_MARCH_2021 = "Methodology_CorporatePhysicalClimateRiskOperationsRisk(Jan2022)";
        String PRM_METHODOLOGY = "Methodology_VEC_Climate_v4";
        String TRANSITION_RISKAND_CLIMATE_GOVERNANCE_JUNE_2021 = "TransitionRiskandClimateGovernance-June2021";
        String TRANSITION_RISK = "Methodology_Climate_ClimateRiskAssessment";
        String TEMP_ALIGNMENT = "Methodology_Climate_TemperatureAlignmentData";
        try {
            wait.until(ExpectedConditions.visibilityOf(methodologyLink)).click();
            //Switch to the new tab
            Set<String> handles = Driver.getDriver().getWindowHandles();
            if (handles.size() == 2) {
                String currentWindowHandle = Driver.getDriver().getWindowHandle();
                for (String handle : handles) {
                    if (!handle.equals(currentWindowHandle)) {
                        Driver.getDriver().switchTo().window(handle);
                        String url = Driver.getDriver().getCurrentUrl();
                        url = url.replaceAll("%20", "");
                        System.out.println("Methodology URL is:" + url);
                        Driver.getDriver().close();
                        Driver.getDriver().switchTo().window(currentWindowHandle);
                        switch (researchLine) {
                            case "Physical Risk Hazards":
                            case "Operations Risk":
                            case "Market Risk":
                            case "Supply Chain Risk":
                                return url.contains(METHODOLOGY_PHYSICAL_CLIMATE_RISK_MARCH_2021);
                            case "Carbon Footprint":
                            case "Brown Share Assessment":
                            case "Green Share Assessment":
                                return url.contains(TRANSITION_RISK);
                            case "Temperature Alignment":
                                return url.contains(TEMP_ALIGNMENT);
                            case "Physical Risk Management":
                                return url.contains(PRM_METHODOLOGY);
                            default:
                                return url.contains(TRANSITION_RISKAND_CLIMATE_GOVERNANCE_JUNE_2021);
                        }
                    }
                }
            } else {
                System.out.println("Multiple tabs open. Cannot validate! ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void Is_MoM_QoQ_TextAvailable(String page) {
        try {
            String whatToValidate = "";

            switch (page) {
                case "Physical Risk Hazards":
                case "Operations Risk":
                case "Market Risk":
                case "Supply Chain Risk":
                    whatToValidate = "quarter";
                    break;
                case "Physical Risk Management":
                case "Energy Transition Management":
                case "TCFD Strategy":
                case "Carbon Footprint":
                case "Brown Share Assessment":
                case "Green Share Assessment":
                    whatToValidate = "month";
                    break;
            }
            String finalText = whatToValidate;
            wait.until(ExpectedConditions.visibilityOf(RegionSectorsTables.get(0)));
            RegionSectorsTables.stream().forEach(i ->
                    {
                        String displayText = i.findElement(By.xpath("(.//div)[position()=(last())]")).getText();
                        int changeCount = Integer.parseInt(displayText.split(" ")[0]);
                        Assert.assertTrue(changeCount >= 0, "Doesnot have number in text");
                        if (changeCount < 2)
                            Assert.assertTrue(displayText.equals(changeCount + " " + "change this " + finalText), "Required text didnt match");
                        else
                            Assert.assertTrue(displayText.equals(changeCount + " " + "changes this " + finalText), "Required text didnt match");
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkScoreWidget(String researchLine) {
        System.out.println("Verifying Score Widget");
        String score;
        if (researchLine.equals("Carbon Footprint")) {
            score = wait.until(ExpectedConditions.visibilityOf(carbonFootprintScoreWidget)).getText();
        } else if (researchLine.equals("Physical Risk Hazards")) {
            String riskHazard = wait.until(ExpectedConditions.visibilityOf(physicalRiskHazardsScoreWidget)).getText();
            List<String> riskHazards = new ArrayList<>(Arrays.asList("Floods", "Heat Stress", "Hurricanes & Typhoons", "Sea Level Rise", "Water Stress", "Wildfires"));
            riskHazard = riskHazard.substring(riskHazard.indexOf(":") + 1);
            return riskHazards.contains(riskHazard.trim());
        } else if (researchLine.equals("Temperature Alignment")) {
            score = wait.until(ExpectedConditions.visibilityOf(temperatureScoreWidget.get(1))).getText();
            //TODO Need to update the logic or validation. Temp put return
            return true;
        } else {
            score = wait.until(ExpectedConditions.visibilityOf(scoreWidget)).getText();
        }
        System.out.println("Score is " + score);
        boolean isCategory = Arrays.stream(ScoreCategories.values()).anyMatch((t) -> t.name().equals(score));
        int scoreValue = 0;
        if (!isCategory) {
            try {
                //check if it's just a number or a percentage (Eg: 61 or 0%)
                scoreValue = Integer.parseInt(score);
            } catch (Exception e) {
                System.out.println("In catch. Unable to parse score");
                return researchLine.equals("Brown Share Assessment");
            }
        }
        return (isCategory || scoreValue > 0);
    }

    public boolean checkRadarGraph() {
        return wait.until(ExpectedConditions.visibilityOf(radarGraph)).isDisplayed();
    }

    public String getPortfolioNameFromSubtitle() {
        BrowserUtils.wait(10);
        return portfolioNameInSubTitle.getText();
    }

    public List<WebElement> selectAnAsOfDateWhereUpdatesAreAvailable(String monthYear) {
        selectOptionFromFiltersDropdown("as_of_date", "April 2021");
        closeFilterByKeyboard();
        /*try {
            for (int counter = 0; counter < 6; counter++) {
                String month = asOfDateValues.get(counter).getText();

                if (month.equalsIgnoreCase(monthYear)) {
                    asOfDateValues.get(counter).click();
                    Thread.sleep(1000);
                    new Actions(Driver.getDriver()).sendKeys(Keys.ESCAPE).perform();
                    Thread.sleep(5000);
                    if (isUpdatesAndLeadersAndLaggardsHeaderDisplayed() && updatesTableColumns.size() > 0) {
                        System.out.println(" going to break");
                        break;
                    }
                }
            }
            selectOptionFromFiltersDropdown("as_of_date", monthYear);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return updatesTableColumns;
    }

    //Leaders & Laggards if there are more than 10 rows, check hyperlink displayed or not
    public boolean checkMoreCompaniesExistLink(String page) {
        try {
            if (moreCompaniesRankedIn.size() == 0) return false;
        } catch (Exception e) {
            return true;
        }
        String expectedHeader;

        switch (page) {
            case "Brown Share Assessment":
            case "Green Share Assessment":
                expectedHeader = "Rank Company % Investment Score Range";
                break;

            case "Carbon Footprint":
                expectedHeader = "Rank Company % Investment Score (tCO2eq)";
                break;

            case "ESG Assessments":
                expectedHeader = "Rank Company % Investment ESG Score Model Version";
                break;
            default:
                expectedHeader = "Rank Company % Investment Score";
                break;
        }

        wait.until(ExpectedConditions.visibilityOfAllElements(moreCompaniesRankedIn));
        for (int i = 0; i < moreCompaniesRankedIn.size(); i++) {
            WebElement moreCompaniesLink = moreCompaniesRankedIn.get(i);
            String numCompaniesText = moreCompaniesLink.getText();
            int countOfCompanies = Integer.parseInt(numCompaniesText.substring(0, numCompaniesText.indexOf(" more companies")));
            wait.until(ExpectedConditions.elementToBeClickable(moreCompaniesLink)).click();
            System.out.println("Clicked on \"" + moreCompaniesLink.getText() + "\" link");

            wait.until(ExpectedConditions.visibilityOf(drillDownPanel));
            System.out.println("Drill Down Panel is opened");

            String drillDownTitleText = drillDownTitle.getText();
            if (numCompaniesText.contains("top 10")) {
                Assert.assertEquals(drillDownTitleText, "Leaders");
            } else {
                Assert.assertEquals(drillDownTitleText, "Laggards");
            }

            Assert.assertTrue(checkIfHideButtonIsDisplayedInDrillDownPanel(), "Hide button is not displayed in Drill Down Panel");

            String actualHeader = drillDownPanel.findElement(By.xpath(".//thead/tr")).getText();

            Assert.assertEquals(actualHeader, expectedHeader);

            int actualCountOfCompanies = drillDownPanel.findElements(By.xpath(".//tbody/tr")).size();

            Assert.assertEquals(actualCountOfCompanies, countOfCompanies + 10);

            clickHideButtonInDrillDownPanel();
            wait.until(ExpectedConditions.elementToBeClickable(moreCompaniesLink));
            System.out.println("Drill Down Panel is closed");
        }


        return true;
    }

    public boolean VerifyIfScoreLogicIsCorrectForLeaders(String page) {
        boolean LeadersScoreLogicStatus = false;

        List<WebElement> LeadersTableScores = Driver.getDriver().findElements(By.xpath("//*[text()='Leaders by Score']//..//table//tbody//tr//td[4]"));
        if (LeadersTableScores.size() > 0) {
            for (int i = 0; i < LeadersTableScores.size() - 1; i++) {
                if ((Integer.parseInt(LeadersTableScores.get(i).getText().replaceAll("[^a-zA-Z0-9]", "")) <= Integer.parseInt(LeadersTableScores.get(i + 1).getText().replaceAll("[^a-zA-Z0-9]", ""))) && (page.equals("Physical Risk Hazards") || page.equals("Operations Risk") || page.equals("Market Risk") || page.equals("Supply Chain Risk") || page.equals("Carbon Footprint") || page.equals("Brown Share Assessment"))) {
                    LeadersScoreLogicStatus = true;
                } else if ((Integer.parseInt(LeadersTableScores.get(i).getText().replaceAll("[^a-zA-Z0-9]", "")) >= Integer.parseInt(LeadersTableScores.get(i + 1).getText().replaceAll("[^a-zA-Z0-9]", ""))) && (page.equals("Physical Risk Management") || page.equals("Energy Transition Management") || page.equals("TCFD Strategy") || page.equals("Green Share Assessment"))) {
                    LeadersScoreLogicStatus = true;
                } else {
                    LeadersScoreLogicStatus = false;
                }
            }
        } else {
            System.out.println("No data was displayed for Leaders Section");
        }

        return LeadersScoreLogicStatus;
    }

    public boolean VerifyIfScoreLogicIsCorrectForLaggards(String page) {
        boolean LaggardsScoreLogicStatus = false;

        List<WebElement> LeadersTableScores = Driver.getDriver().findElements(By.xpath("//*[text()='Laggards by Score']//..//table//tbody//tr//td[4]"));
        try {
            if (LeadersTableScores.size() > 0) {
                for (int i = 0; i < LeadersTableScores.size() - 1; i++) {
                    if ((Integer.parseInt(LeadersTableScores.get(i).getText().replaceAll("[^a-zA-Z0-9]", "")) >= Integer.parseInt(LeadersTableScores.get(i + 1).getText().replaceAll("[^a-zA-Z0-9]", ""))) && (page.equals("Physical Risk Hazards") || page.equals("Operations Risk") || page.equals("Market Risk") || page.equals("Supply Chain Risk") || page.equals("Carbon Footprint") || page.equals("Brown Share Assessment"))) {
                        LaggardsScoreLogicStatus = true;
                    } else if ((Integer.parseInt(LeadersTableScores.get(i).getText().replaceAll("[^a-zA-Z0-9]", "")) <= Integer.parseInt(LeadersTableScores.get(i + 1).getText().replaceAll("[^a-zA-Z0-9]", ""))) && (page.equals("Physical Risk Management") || page.equals("Energy Transition Management") || page.equals("TCFD Strategy"))) {
                        LaggardsScoreLogicStatus = true;
                    } else if (LeadersTableScores.get(i).getText().replaceAll("[^a-zA-Z0-9]", "").equals(LeadersTableScores.get(i + 1).getText().replaceAll("[^a-zA-Z0-9]", "")) && (page.equals("Green Share Assessment"))) {
                        LaggardsScoreLogicStatus = true;
                    } else {
                        LaggardsScoreLogicStatus = false;
                    }
                }
            } else {
                System.out.println("No data was displayed for Laggards Section");
            }

        } catch (NumberFormatException e) {
            for (int i = 0; i < LeadersTableScores.size() - 1; i++) {
                if (LeadersTableScores.get(i).getText().equals("None") || LeadersTableScores.get(i).getText().equals(LeadersTableScores.get(i + 1).getText())) {
                    LaggardsScoreLogicStatus = true;
                } else if (Integer.parseInt(LeadersTableScores.get(i).getText().replaceAll("[^a-zA-Z0-9]", "")) <= Integer.parseInt(LeadersTableScores.get(i + 1).getText().replaceAll("[^a-zA-Z0-9]", "")) && (page.equals("Green Share Assessment"))) {
                    LaggardsScoreLogicStatus = true;
                } else {
                    LaggardsScoreLogicStatus = false;
                }
            }
            return LaggardsScoreLogicStatus;
        }


        return LaggardsScoreLogicStatus;
    }

    public boolean VerifySortingOrderForLeaders() {

        List<WebElement> rows = Driver.getDriver().findElements(By.xpath("//div[@heap_leadlag_id='leaders']//tbody/tr"));
        if (rows.size() > 0) {
            for (int i = 1; i < rows.size() - 1; i++) {
                int row1Rank = Integer.parseInt(Driver.getDriver().findElement(By.xpath("//div[@heap_leadlag_id='leaders']//tbody/tr["+i+"]/td[1]")).getText());
                int row2Rank = Integer.parseInt(Driver.getDriver().findElement(By.xpath("//div[@heap_leadlag_id='leaders']//tbody/tr["+(i+1)+"]/td[1]")).getText());
                if(row1Rank>row2Rank)
                    return false;
                else if (row1Rank==row2Rank){
                    float row1Inv = Float.parseFloat(Driver.getDriver().findElement(By.xpath("//div[@heap_leadlag_id='leaders']//tbody/tr["+i+"]/td[3]")).getText().replace("%",""));
                    float row2Inv = Float.parseFloat(Driver.getDriver().findElement(By.xpath("//div[@heap_leadlag_id='leaders']//tbody/tr["+(i+1)+"]/td[3]")).getText().replace("%",""));
                    if(row1Inv<row2Inv)
                        return false;
                    else if(row1Inv==row2Inv) {
                        String row1Company = Driver.getDriver().findElement(By.xpath("//div[@heap_leadlag_id='leaders']//tbody/tr[" + i + "]/td[2]//span/span")).getText();
                        String row2Company = Driver.getDriver().findElement(By.xpath("//div[@heap_leadlag_id='leaders']//tbody/tr[" + (i + 1) + "]/td[2]//span/span")).getText();
                        if (row1Company.compareToIgnoreCase(row2Company) > 0)
                            return false;
                    }
                }
            }
        } else {
            System.out.println("No data was displayed for Leaders Section");
        }

        return true;
    }

    public boolean VerifySortingOrderForLaggards() {

        List<WebElement> rows = Driver.getDriver().findElements(By.xpath("//div[@heap_leadlag_id='laggards']//tbody/tr"));
        if (rows.size() > 0) {
            for (int i = 1; i < rows.size() - 1; i++) {
                int row1Rank = Integer.parseInt(Driver.getDriver().findElement(By.xpath("//div[@heap_leadlag_id='laggards']//tbody/tr["+i+"]/td[1]")).getText());
                int row2Rank = Integer.parseInt(Driver.getDriver().findElement(By.xpath("//div[@heap_leadlag_id='laggards']//tbody/tr["+(i+1)+"]/td[1]")).getText());
                if(row1Rank<row2Rank)
                    return false;
                else if (row1Rank==row2Rank){
                    String row1Company = Driver.getDriver().findElement(By.xpath("//div[@heap_leadlag_id='laggards']//tbody/tr["+i+"]/td[2]//span/span")).getText();
                    String row2Company = Driver.getDriver().findElement(By.xpath("//div[@heap_leadlag_id='laggards']//tbody/tr["+(i+1)+"]/td[2]//span/span")).getText();
                    if(row1Company.compareToIgnoreCase(row2Company)<0)
                        return false;
                }
            }
        } else {
            System.out.println("No data was displayed for Laggards Section");
        }

        return true;
    }

    public void validateRegionCardOrder() {
        int previousCompanyCount = 0;
        List<WebElement> regoinSectorWidgets = getListofRegionWidgets().get(0);
        for (int i = 0; i < regoinSectorWidgets.size(); i++) {
            int companyCounts = Integer.valueOf(regoinSectorWidgets.get(i).getText().split("\n")[3].split("/")[0]);
            if (previousCompanyCount != 0) {
                Assert.assertTrue(previousCompanyCount >= companyCounts);
                if (previousCompanyCount == companyCounts) {
                    Collator collator = Collator.getInstance(Locale.US);
                    collator.setStrength(Collator.PRIMARY);
                    Assert.assertTrue(collator.compare(regoinSectorWidgets.get(i).getText().split("\n")[0],
                            regoinSectorWidgets.get(i - 1).getText().split("\n")[0]) == 1);

                }
            }
            previousCompanyCount = companyCounts;
        }

    }

    public List<List<WebElement>> getListofRegionWidgets() {
        List<WebElement> regionWidgets = new ArrayList<>();
        List<WebElement> sectorWidgets = new ArrayList<>();

        for (int i = 0; i < RegionSectorsTables.size(); i++) {
            String Widgetheader = RegionSectorsTables.get(i).getText().split("\n")[0];
            if (expectedRegionList.stream().filter(p -> p.equals(Widgetheader)).count() > 0) {
                regionWidgets.add(RegionSectorsTables.get(i));
            } else {
                sectorWidgets.add(RegionSectorsTables.get(i));
            }
        }
        return Arrays.asList(regionWidgets, sectorWidgets);
    }

    public void validateSectorCardOrder() {
        int previousCompanyCount = 0;
        List<WebElement> regoinSectorWidgets = getListofRegionWidgets().get(1);
        for (int i = 0; i < regoinSectorWidgets.size(); i++) {
            int companyCounts = Integer.valueOf(regoinSectorWidgets.get(i).getText().split("\n")[3].split("/")[0]);
            if (previousCompanyCount != 0) {
                Assert.assertTrue(previousCompanyCount >= companyCounts);
                if (previousCompanyCount == companyCounts) {
                    Collator collator = Collator.getInstance(Locale.US);
                    collator.setStrength(Collator.PRIMARY);
                    Assert.assertTrue(collator.compare(regoinSectorWidgets.get(i).getText().split("\n")[0],
                            regoinSectorWidgets.get(i - 1).getText().split("\n")[0]) == 1);

                }
            }
            previousCompanyCount = companyCounts;
        }

    }

    /**
     * Impact Table and graph functions
     */

    public List<String> impactFilterOptions() {
            wait.until(ExpectedConditions.elementToBeClickable(impactFilter)).isDisplayed();
            impactFilter.click();
            BrowserUtils.wait(2);
            String text = Driver.getDriver().findElement(By.xpath("//ul[@role='listbox']")).getText();
            List<String> options = Arrays.asList(text.split("\\n"));
            return options;
    }

    public void selectImpactFilterOption(String option) {
        wait.until(ExpectedConditions.elementToBeClickable(impactFilter)).isDisplayed();
        BrowserUtils.scrollTo(impactFilter);
        impactFilter.click();
        BrowserUtils.wait(2);
        Driver.getDriver().findElement(By.xpath("//ul[@role='listbox']/li[text()='" + option + "']")).click();
    }

//    public boolean checkIfImpactGraphBarsAreFilledWithColor() {
//        wait.until(ExpectedConditions.elementToBeClickable(impactFilter)).isDisplayed();
//        impactFilter.click();
//        BrowserUtils.wait(2);
//        Driver.getDriver().findElement(By.xpath("//ul[@role='listbox']/li[text()='" + option + "']")).click();
//    }


    public boolean isImpactTablePresent() {
        return wait.until(ExpectedConditions.visibilityOf(impactTable)).isDisplayed();
    }

    public boolean isImpactGraphPresent() {
        return wait.until(ExpectedConditions.visibilityOf(impactGraph)).isDisplayed();
    }

    public List<String> verifyImpactTableColumns() {
        if (impactTables.size() > 0) {
            wait.until(ExpectedConditions.visibilityOf(impactTable)).isDisplayed();
            System.out.println("List: " + impactTable.findElements(By.xpath(".//thead/tr/th")).stream().map(WebElement::getText).collect(Collectors.toList()));
            return impactTable.findElements(By.xpath(".//thead/tr/th")).stream().map(WebElement::getText).collect(Collectors.toList());
        } else {
            System.out.println("No Impact table");
            return Arrays.asList("No Impact Table Present");
        }
    }

    public boolean verifyPercentageSymbolWithInvestmentColumn() {
        BrowserUtils.wait(10);
        List<WebElement> tableColumns = Driver.getDriver().findElements(By.xpath("//table/thead/tr/th"));
        for(WebElement column:tableColumns){
            if(column.getText().endsWith("Investment")){
                System.out.println("Testing column");
                if(!column.getText().equals("% Investment"))
                    return false;
            }
        }
        return true;
    }

    public boolean verifyImpactTableScoreCategoryColors(String researchLine) {
        wait.until(ExpectedConditions.visibilityOf(impactTable)).isDisplayed();
        List<WebElement> spanElements = new ArrayList<>(impactTable.findElements(By.xpath(".//tbody/tr[*]/td[4]/*")));
        List<WebElement> actualScores = new ArrayList<>(impactTable.findElements(By.xpath(".//tbody/tr[*]/td[3]")));
        //remove empty Strings
        actualScores.removeIf(e -> e.getText().isEmpty());
        List<WebElement> actualScoresBrownShare = new ArrayList<>(impactTable.findElements(By.xpath(".//tbody/tr[*]/td[3]/*")));
        actualScoresBrownShare.removeIf(e -> e.getText().isEmpty());
        if (actualScoresBrownShare.isEmpty()) return true;
        String expectedColor;
        String actualColor;

        switch (researchLine) {

            case "Brown Share Assessment":
            case "Green Share Assessment":
            case "Temperature Alignment":
            case "Carbon Footprint":
                for (int i = 0; i < actualScoresBrownShare.size(); i++) {
                    expectedColor = getColorByScoreCategory(researchLine, actualScoresBrownShare.get(i).getText());
                    actualColor = Color.fromString(actualScoresBrownShare.get(i).getCssValue("background-color")).asHex();
                    System.out.println("expectedColor = " + expectedColor);
                    System.out.println("actualColor = " + actualColor);
                    if (!expectedColor.equalsIgnoreCase(actualColor)) {
                        return false;
                    }
                }
                break;

            default:
                for (int i = 0; i < actualScores.size(); i++) {
                    expectedColor = getColorByScore(researchLine, Integer.parseInt(actualScores.get(i).getText()));
                    actualColor = Color.fromString(spanElements.get(i).getCssValue("background-color")).asHex();
                    if (!expectedColor.equalsIgnoreCase(actualColor)) {
                        return false;
                    }
                }
        }

        return true;
    }

    public boolean impactWidgetTitles() {
        return wait.until(ExpectedConditions.visibilityOf(positiveImpactTable)).isDisplayed() &&
                wait.until(ExpectedConditions.visibilityOf(negativeImpactTable)).isDisplayed() &&
                wait.until(ExpectedConditions.visibilityOf(positiveImpactGraph)).isDisplayed() &&
                wait.until(ExpectedConditions.visibilityOf(negativeImpactGraph)).isDisplayed();

    }


    public void validateCarbonFootPrint() {

        assertTestCase.assertEquals(portfolioScoreWidget.get(0).getText(), "Weighted Average Carbon Footprint (tCO2eq)");
        assertTestCase.assertEquals(portfolioScoreWidget.get(1).getText(), "Weighted Average Carbon Intensity (tCO2eq/USD M revenue)");
        assertTestCase.assertEquals(benchmarkPortfolioScoreWidget.get(0).getText(), "Weighted Average Carbon Footprint (tCO2eq)");
        assertTestCase.assertEquals(benchmarkPortfolioScoreWidget.get(1).getText(), "Weighted Average Carbon Intensity (tCO2eq/USD M revenue)");
        assertTestCase.assertTrue(EmissionsTotalFinanceSectionTitle.findElement(By.xpath("following-sibling::div[2]")).getText().contains("tCO2eq"), "Emissions (tCO2eq)");
        assertTestCase.assertTrue(Financed_Emissions_per_million_Invested_Title.findElement(By.xpath("following-sibling::div[2]")).getText().contains("tCO2eq"), "Emissions (tCO2eq)");
        assertTestCase.assertTrue(Carbon_Intensity_per_SalesTitle.findElement(By.xpath("following-sibling::div[2]")).getText().contains("tCO2eq"), "Emissions (tCO2eq)");
        impactTableList.stream().forEach(x -> assertTestCase.assertEquals(x.findElement(By.xpath(".//thead/tr/th[3]")).getText(), "Score (tCO2eq)", "Impact Table Score (tCO2eq)"));
        LeadersAndLaggardsTablelist.stream().forEach(x -> assertTestCase.assertEquals(x.findElement(By.xpath(".//thead/tr/th[4]")).getText(), "Score (tCO2eq)", "Leaders Laggards Score (tCO2eq)"));
        RegionSectorsTables.stream().forEach(x -> assertTestCase.assertTrue(x.findElement(By.xpath("div/div/div/div/span")).getText().contains("tCO2eq"), "Region Sector Score (tCO2eq)"));
        for (int i = 0; i < regionSectorChartNames.size(); i++) {
            WebElement regionSectorChart = regionSectorChartNames.get(i);
            wait.until(ExpectedConditions.elementToBeClickable(regionSectorChart)).click();
            BrowserUtils.wait(5);
            drillDownColumnHeaders.stream().forEach(e -> assertTestCase.assertTrue(e.findElement(By.xpath("th[2]")).getText().contains("tCO2eq"), "Region Sector Drill Down Score (tCO2eq)"));
            clickOutsideOfDrillDownPanel();
        }
        for (int i = 0; i < EmissionsTables.size(); i++) {
            List<WebElement> emissionTable = EmissionsTables.get(i).findElements(By.xpath(".//tbody/tr"));
            for (int x = 0; x < emissionTable.size(); x++) {
                assertTestCase.assertTrue(emissionTable.get(x).findElement(By.xpath(".//td[2]")).getText().contains("tCO2eq"), "Emissions (tCO2eq)");
                assertTestCase.assertTrue(emissionTable.get(x).findElement(By.xpath(".//td[3]")).getText().contains("tCO2eq"), "Emissions (tCO2eq)");
            }

        }
        /*Assert.assertTrue(updatesTableColumns.stream()
                .filter(p -> p.getText().contains("Score"))public boolean checkIffTooltipSIsDisplayedOnHover() {
    }.findFirst().get().getText().contains("tCO2eq"));
*/
    }


    public boolean checkIfTooltipSIsDisplayedOnHover(String UnderlyingDataIndicator) {

        // verify tooltip

        System.out.println("UnderlyingDataIndicator = " + UnderlyingDataIndicator);
        List<WebElement> bars = Driver.getDriver().findElements(By.xpath("//b[.='" + UnderlyingDataIndicator + "']/../following-sibling::div//*[local-name()='g']/*[local-name()='rect']"));
        //filter out if bar height is 0

        bars.forEach(e -> System.out.println("e.getRect().getHeight() = " + e.getRect().getHeight()));
        bars = bars.stream().filter(e -> e.getRect().getHeight() > 0).collect(Collectors.toList());

        BrowserUtils.scrollTo(bars.get(0));
        BrowserUtils.hover(bars.get(0));
        String toolTipText = "";
        for (WebElement bar : bars) {
            BrowserUtils.hover(bar);
            try {
                WebElement hoverWidget = Driver.getDriver().findElement(By.xpath("(//*[@visibility='visible'])[2]"));
                toolTipText = hoverWidget.getText();
                System.out.println("toolTipText = " + toolTipText);
                break;
            } catch (Exception e) {
                System.out.println("No tooltip is displayed");
            }
        }
        return !toolTipText.isEmpty();
    }

    public boolean verifyTooltip(List<WebElement> bars) {

        BrowserUtils.scrollTo(bars.get(0));
        BrowserUtils.hover(bars.get(0));
        String toolTipText = "";

        for (WebElement bar : bars) {
            if (Integer.parseInt(bar.getAttribute("height")) > 1) {
                BrowserUtils.hover(bar);
                try {
                    WebElement hoverWidget = Driver.getDriver().findElement(By.xpath("(//*[@visibility='visible'])[1]"));
                    toolTipText = hoverWidget.getText();
                    System.out.println("toolTipText = " + toolTipText);
                    if (toolTipText.isEmpty()) {
                        return false;
                    }
                    if (toolTipText.contains("Not Covered") && !NumberUtils.isParsable(toolTipText.split("Not Covered")[1].split("%")[0])) {
                        return false;
                    }


                } catch (Exception e) {
                    System.out.println("No tooltip is displayed");
                    return false;
                }
            }
        }
        return true;
    }

    public List<String> getUnderlyingDataMetricHeaders() {
        return wait.until(ExpectedConditions.visibilityOfAllElements(underlyingDataMetricHeaders))
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public boolean checkIfTooltipSIsDisplayedForGreenShareDataMetrics() {
        String xpath = "//div[@id='overview_of_products_and_technologies']/../..//div[contains(@id,'highcharts')]//*[local-name()='g']/*[local-name()='rect']";
        List<WebElement> bars = Driver.getDriver().findElements(By.xpath(xpath));
        return verifyTooltip(bars);
    }

    public boolean checkIfTooltipSIsDisplayedForBrownShareDataMetrics() {
        String xpath = "//div[text()='Fossil Fuel Disclosures']/../../..//div[contains(@id,'highcharts')]//*[local-name()='g']/*[local-name()='rect']";
        List<WebElement> bars = Driver.getDriver().findElements(By.xpath(xpath));
        return verifyTooltip(bars);
    }

    public boolean verifyPhysicalRiskHazardsDrawerBehavior() {

        // Verify table columns
        String tableXpath = "//div[@role='presentation'][not(@aria-hidden)]//table[contains(@id,'mrksupplyriskPanelTab')]";
        int noOfTables = Driver.getDriver().findElements(By.xpath(tableXpath)).size();
        int noOfScoreColumns = Driver.getDriver().findElements(By.xpath(tableXpath + "//th[text()='Score']")).size();
        int noOfInvestmentColumns = Driver.getDriver().findElements(By.xpath(tableXpath + "//th[text()='% Investment']")).size();
        int noOfRangeColumns = Driver.getDriver().findElements(By.xpath(tableXpath + "//th[contains(text(),'-')]")).size();
        if (!(noOfScoreColumns == noOfTables && noOfInvestmentColumns == noOfTables && noOfRangeColumns == noOfTables)) {
            System.out.println("Column names are not matching");
            return false;
        }

        // Verify table ranges are in sorted order
        String range = "";
        int endRange = 0;
        for (int i = 1; i <= noOfRangeColumns; i++) {
            range = Driver.getDriver().findElement(By.xpath("(" + tableXpath + "//th[contains(text(),'-')])[" + i + "]")).getText();
            String[] rangeValues = range.split("-");
            if (endRange <= Integer.parseInt(rangeValues[0]) && Integer.parseInt(rangeValues[0]) < Integer.parseInt(rangeValues[1])) {
                endRange = Integer.parseInt(rangeValues[1]);
            } else {
                System.out.println("Table ranges are not in sorted manner");
                return false;
            }
        }

        // Verify entities in table are sorted
        for (int i = 1; i <= noOfTables; i++) {
            int noOfRows = Driver.getDriver().findElements(By.xpath("(" + tableXpath + ")[2]/tbody/tr")).size();
            if (noOfRows > 1) {
                int initialScore = 0;
                for (int j = 1; j <= noOfRows; j++) {
                    String score = Driver.getDriver().findElement(By.xpath("(" + tableXpath + ")[2]/tbody/tr[" + j + "]/td[2]")).getText();
                    if (Integer.parseInt(score) >= initialScore) {
                        initialScore = Integer.parseInt(score);
                    } else {
                        System.out.println("Entities are not sorted based score");
                        return false;
                    }
                }
            }
        }

        // Verify navigation to entity page
        WebElement entity = Driver.getDriver().findElement(By.xpath("(" + tableXpath + "//tr/td/div)[1]"));
        String entityName = Driver.getDriver().findElement(By.xpath("(" + tableXpath + "//tr/td/div)[1]")).getText();
        Driver.getDriver().findElement(By.xpath("(" + tableXpath + "//tr/td/div)[1]")).click();
        BrowserUtils.wait(5);
        BrowserUtils.waitForVisibility(Driver.getDriver().findElement(By.xpath("//span[contains(text(),'" + entityName + "')]")), 30);
        clickCloseIcon();

        return true;
    }

    public void verifyColorLegendOfScoreCategory() {
        String xpath = "//div[text()='Physical Risk Hazards: Operations Risk']//span[text()='0-19']/div";
        String actualColor = Driver.getDriver().findElement(By.xpath(xpath)).getAttribute("style");
        assertTestCase.assertEquals(actualColor, "background: rgb(79, 163, 205);");

        xpath = "//div[text()='Physical Risk Hazards: Operations Risk']//span[text()='20-39']/div";
        actualColor = Driver.getDriver().findElement(By.xpath(xpath)).getAttribute("style");
        assertTestCase.assertEquals(actualColor, "background: rgb(141, 163, 183);");

        xpath = "//div[text()='Physical Risk Hazards: Operations Risk']//span[text()='40-59']/div";
        actualColor = Driver.getDriver().findElement(By.xpath(xpath)).getAttribute("style");
        assertTestCase.assertEquals(actualColor, "background: rgb(169, 137, 142);");

        xpath = "//div[text()='Physical Risk Hazards: Operations Risk']//span[text()='60-79']/div";
        actualColor = Driver.getDriver().findElement(By.xpath(xpath)).getAttribute("style");
        assertTestCase.assertEquals(actualColor, "background: rgb(192, 105, 96);");

        xpath = "//div[text()='Physical Risk Hazards: Operations Risk']//span[text()='80-100']/div";
        actualColor = Driver.getDriver().findElement(By.xpath(xpath)).getAttribute("style");
        assertTestCase.assertEquals(actualColor, "background: rgb(218, 73, 48);");
    }

    public boolean verifyPhysicalRiskHazardsDrawers(String riskHazard, String topic) {
        System.out.println("riskHazard = " + riskHazard);
        System.out.println("topic = " + topic);
        String xpath = "//div[text()='" + riskHazard + "']//b[text()='" + topic + "']/../..//div[contains(@id, 'highcharts')]//*[name()='g' and contains(@class,'highcharts')]//*[name()='rect']";
        //div[text()='" + riskHazard + "']//*[name()='svg']//*[name()='g' and contains(@class,'highcharts')]//*[name()='g' and contains(@class,'highcharts')]//*[name()='rect']
        List<WebElement> bars = Driver.getDriver().findElements(By.xpath(xpath));
        BrowserUtils.scrollTo(bars.get(0));
        boolean isDrawerVisible = false;
        boolean checkDrawerBehavior = false;
        for (WebElement bar : bars) {
            System.out.println("bar.getAttribute(\"height\") = " + bar.getAttribute("height"));
            if (Integer.parseInt(bar.getAttribute("height")) > 1) {
                isDrawerVisible = false;
                bar.click();
                BrowserUtils.wait(4);
                WebElement element = Driver.getDriver().findElement(By.xpath("//div[@class='MuiDrawer-root MuiDrawer-modal']//div[text()='" + riskHazard.replace(" Chain", "") + "']/div[text()='" + topic + "']"));
                isDrawerVisible = BrowserUtils.isElementVisible(element, 60);
                if (isDrawerVisible) {
                    if (!checkDrawerBehavior) {
                        if (!verifyPhysicalRiskHazardsDrawerBehavior())
                            return false;
                        checkDrawerBehavior = true;
                    }
                    Driver.getDriver().findElement(By.xpath("(//a[text()='hide'])[2]")).click();
                    BrowserUtils.wait(2);
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public void validateImpactDrillDown() {
        //Validate Positive Impact drill down
        if (impactTableDrillDownLink.size() > 0) {
            impactTableDrillDownLink.get(0).click();
            assertTestCase.assertTrue(wait.until(ExpectedConditions.visibilityOf(drillDownPanel)).isDisplayed(), "Validate Drill down is displayed");
            assertTestCase.assertTrue(wait.until(ExpectedConditions.visibilityOf(drillDownTitle)).getText().equals("Positive impact based on investment and score"), "Validate Drill down header");
            clickOutsideOfDrillDownPanel();

            //Validate Negative Impact drill down
            if ((impactTableDrillDownLink.size() > 1)) {
                impactTableDrillDownLink.get(1).click();
                assertTestCase.assertTrue(wait.until(ExpectedConditions.visibilityOf(drillDownPanel)).isDisplayed(), "Validate Drill down is displayed");
                assertTestCase.assertTrue(wait.until(ExpectedConditions.visibilityOf(drillDownTitle)).getText().equals("Negative impact based on investment and score"), "Validate Drill down header");
                clickOutsideOfDrillDownPanel();
            }
        }
    }

    public boolean isElementPresent(String xpath) {
        try {
            Driver.getDriver().findElement(By.xpath(xpath));
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public void validateExportButtonIsNotAvailable() {
        Assert.assertTrue(!isElementPresent("id = ExportDropdown-test-id-1"));
    }

    public Boolean ValidateifEsgPortfolioBoxIsDisplayed() {
        return EsgPortfolioBox.isDisplayed();
    }

    public Boolean ValidateifEsgsummaryBoxIsDisplayed() {
        return EsgSummaryBox.isDisplayed();
    }

    public Boolean validateIfEsgCardInfoIsDisplayed() {
        return esgCardInfoBox.isDisplayed();
    }

    public void validateEsgAssessmentLegends(){

        String labelXpath = "//div[contains(text(),'ESG Assessment Score:')]//span";

        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("("+labelXpath+")[1]")).getText(),"Advanced");
        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("("+labelXpath+"/div)[1]")).getAttribute("style"),"background: rgb(219, 229, 163);");

        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("("+labelXpath+")[2]")).getText(),"Robust");
        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("("+labelXpath+"/div)[2]")).getAttribute("style"),"background: rgb(234, 197, 80);");

        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("("+labelXpath+")[3]")).getText(),"Limited");
        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("("+labelXpath+"/div)[3]")).getAttribute("style"),"background: rgb(232, 149, 28);");

        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("("+labelXpath+")[4]")).getText(),"Weak");
        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("("+labelXpath+"/div)[4]")).getAttribute("style"),"background: rgb(221, 88, 29);");

        assertTestCase.assertTrue(Driver.getDriver().findElement(By.xpath("("+labelXpath+")[1]")).getCssValue("font-size").equals("10px"));


    }

    public void validatePhysicalRiskMgmtLegend(){

        String labelXpath = "//div[contains(text(),'Physical Risk Management Score:')]//span";

        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("("+labelXpath+")[1]")).getText(),"Advanced");
        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("("+labelXpath+"/div)[1]")).getAttribute("style"),"background: rgb(34, 149, 149);");

        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("("+labelXpath+")[2]")).getText(),"Robust");
        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("("+labelXpath+"/div)[2]")).getAttribute("style"),"background: rgb(90, 151, 114);");

        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("("+labelXpath+")[3]")).getText(),"Limited");
        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("("+labelXpath+"/div)[3]")).getAttribute("style"),"background: rgb(175, 157, 63);");

        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("("+labelXpath+")[4]")).getText(),"Weak");
        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("("+labelXpath+"/div)[4]")).getAttribute("style"),"background: rgb(223, 161, 36);");

    }

    public Boolean validateCompaniesEsgPopupIsDisplayed() {
        return esgCompaniesPopup.isDisplayed();
    }

    public List<List<String>> readEsgInfoFromDrawer() {
        String rowsXpath = "//div[contains(@class,'MuiDrawer-paper')]//tr";
        int noOfRows = Driver.getDriver().findElements(By.xpath(rowsXpath)).size();
        List<List<String>> esgInfoList = new ArrayList<>();
        for (int i = 1; i < noOfRows; i++) {
            ArrayList<String> columnsInDrawer = new ArrayList<>();
            columnsInDrawer.add(Driver.getDriver().findElement(By.xpath(rowsXpath + "[" + i + "]/td[1]/div")).getText());
            columnsInDrawer.add(Driver.getDriver().findElement(By.xpath(rowsXpath + "[" + i + "]/td[2]")).getText());
            columnsInDrawer.add(Driver.getDriver().findElement(By.xpath(rowsXpath + "[" + i + "]/td[3]")).getText());
            columnsInDrawer.add(Driver.getDriver().findElement(By.xpath(rowsXpath + "[" + i + "]/td[4]/span")).getText());
            esgInfoList.add(columnsInDrawer);
        }
        return esgInfoList;
    }

    public Boolean verifyColumnsInEsgDrawer(ArrayList<String> columns) {
        for (String column : columns) {
            WebElement element = Driver.getDriver().findElement(By.xpath("//div[contains(@class,'MuiDrawer-paper')]//th[text()='" + column + "']"));
            if (!element.isDisplayed())
                return false;
        }
        return true;
    }

    public Boolean verifyEsgMethodologyValues() {
        String methodologiesXpath = "//div[contains(@class,'MuiDrawer-paper')]//table[contains(@id,'table-id')]//tr/td[4]/span";
        List<WebElement> methodologyValues = Driver.getDriver().findElements(By.xpath(methodologiesXpath));
        assertTestCase.assertTrue(methodologyValues.size()!=0);
        String methodologyValue = "";
        for (WebElement methodology : methodologyValues) {
            BrowserUtils.scrollTo(methodology);
            methodologyValue = methodology.getText();
            if (!(methodologyValue.equals("1.0") || methodologyValue.equals("2.0"))) {
                return false;
            }
        }
        return true;
    }

    public Boolean verifyEsgScoreValues() {
        String scoreValuesXpath = "//div[contains(@class,'MuiDrawer-paper')]//table[contains(@id,'table-id')]//tr/td[3]";
        List<WebElement> scoreValues = Driver.getDriver().findElements(By.xpath(scoreValuesXpath));
        assertTestCase.assertTrue(scoreValues.size()!=0);
        String scoreValue = "";
        for (WebElement score : scoreValues) {
            BrowserUtils.scrollTo(score);
            scoreValue = score.getText();
            if (!(scoreValue.equals("Advanced")
                    || scoreValue.equals("Robust")
                    || scoreValue.equals("Limited")
                    || scoreValue.equals("Weak"))) {
                return false;
            }
        }
        return true;
    }

    public Boolean verifyEsgCompaniesOrder() {
        String esgScoresXpath = "//div[contains(@class,'MuiDrawer-paper')]//table[@id='table-id']//tr/td[3]";
        String investmentsXpath = "//div[contains(@class,'MuiDrawer-paper')]//table[@id='table-id']//tr/td[2]";
        String companyNameXpath = "//div[contains(@class,'MuiDrawer-paper')]//table[@id='table-id']//tr/td[1]/div";
        List<WebElement> methodologyValues = Driver.getDriver().findElements(By.xpath(esgScoresXpath));

        for (int i = 1; i < methodologyValues.size(); i++) {
            BrowserUtils.scrollTo(methodologyValues.get(i));
            String currentRecordScore = Driver.getDriver().findElement(By.xpath("(" + esgScoresXpath + ")[" + i + "]")).getText().replace(".esg", "");
            String nextRecordScore = Driver.getDriver().findElement(By.xpath("(" + esgScoresXpath + ")[" + (i + 1) + "]")).getText().replace(".esg", "");
            if (currentRecordScore.compareTo(nextRecordScore) > 0) {
                return false;
            } else if (currentRecordScore.compareTo(nextRecordScore) == 0) {
                String currentRecordInvestment = Driver.getDriver().findElement(By.xpath("(" + investmentsXpath + ")[" + i + "]")).getText();
                String nextRecordInvestment = Driver.getDriver().findElement(By.xpath("(" + investmentsXpath + ")[" + (i + 1) + "]")).getText();
                if (Float.parseFloat(currentRecordInvestment) < Float.parseFloat(nextRecordInvestment)) {
                    return false;
                } else if (Float.parseFloat(currentRecordInvestment) == Float.parseFloat(nextRecordInvestment)) {
                    String currentRecordCompanyName = Driver.getDriver().findElement(By.xpath("(" + companyNameXpath + ")[" + i + "]")).getText();
                    String nextRecordCompanyName = Driver.getDriver().findElement(By.xpath("(" + companyNameXpath + ")[" + (i + 1) + "]")).getText().replaceAll("[^a-zA-Z ]", "Z");
                    System.out.println("Sorting Order Check: " + currentRecordCompanyName + "-->" + nextRecordCompanyName);
                    if (currentRecordCompanyName.compareToIgnoreCase(nextRecordCompanyName) > 0) {
                        System.out.print("Sorting is failed");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void ValidateSummaryBoxText() {
        String ExpextedText = "ESG Assessment\n" +
                "Weak\n" +
                "Limited\n" +
                "Robust\n" +
                "Advanced\n" +
                "Our ESG Assessment Framework is designed to provide relevant and comparable insight across sectors and regions by encompassing ESG drivers of risks or opportunities that can be material for entities in the short or long term. This double materiality framework covers a broad range of stakeholders including shareholders, employees, sub-contractors, suppliers, business partners, customers, local communities, regulatory authorities, society, as well as the environment.\n" +
                "\n" +
                "The Assessment Framework takes into account universally recognized sustainability standards, guidelines and recommendations set by international organizations with a mandate to define expectations of conduct for business organizations.\n" +
                "\n" +
                "Portfolio scores are calculated as a weighted average of the entities' scores, based on the respective investment weight of all the entities in the portfolio.\n" +
                "Read more about Methodology ESG Assessment 1.0\n" +
                "Read more about Methodology ESG Assessment 2.0\n" +
                "Read more about Methodology Controversy Risk Assessment";
        BrowserUtils.wait(2);
        String UIText = EsgSummaryBox.getText();
        BrowserUtils.wait(2);
        assertTestCase.assertEquals(ExpextedText, UIText);

    }

    public void ValidateSummaryBoxTextLinks() {
        validateLinksOpenedInNewTab(ESGSummaryBoxLink.get(0), "Methodology%201.0%20ESG%20Assessment.pdf");
        validateLinksOpenedInNewTab(ESGSummaryBoxLink.get(1), "Methodology%202.0%20ESG%20Assessment.pdf");
        validateLinksOpenedInNewTab(ESGSummaryBoxLink.get(2), "Methodology%20Controversy%20Risk%20Assessment.pdf");

    }

    public void selectEsgPortfolioCoverage() {
        wait.until(ExpectedConditions.elementToBeClickable(esgPortfolioCoverageLink)).click();
    }

    public void validateLinksOpenedInNewTab(WebElement element, String whatToValidate) {
        WebDriver driver = Driver.getDriver();
        wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        String mainWindowHandler = driver.getWindowHandle();
        element.click();
        Set<String> allWindowHandles = driver.getWindowHandles();
        Iterator<String> iterator = allWindowHandles.iterator();
        while (iterator.hasNext()) {
            String ChildWindow = iterator.next();
            if (!mainWindowHandler.equalsIgnoreCase(ChildWindow)) {
                driver.switchTo().window(ChildWindow);
                String url = driver.getCurrentUrl();
                System.out.println("actual url   = " + url);
                System.out.println("Expected url = " + whatToValidate);
                assertTestCase.assertTrue(driver.getCurrentUrl().contains(whatToValidate));
                driver.close();
                BrowserUtils.wait(2);
                driver.switchTo().window(mainWindowHandler);
            }
        }
    }

    public Boolean isInvestmentSectionAvailable() {
        return physicalRiskHazardInvestmentTable.isDisplayed();
    }

    public void validateInvestmentSectionRiskLevels() {
        List<String> expectedRiskLevels = Arrays.asList(new String[]{"No Risk", "Low", "Medium", "High", "Red Flag"});

        List<WebElement> elementForRiskNames = physicalRiskHazardInvestmentTable.findElements(By.xpath("thead/tr[2]/th"));

        for (WebElement e : elementForRiskNames) {
            if (!e.getText().isEmpty())
                assertTestCase.assertTrue(expectedRiskLevels.contains(e.getText()), "Validating Risk levels");
        }

    }

    public boolean validateOrder(String option) {
        /**
         * Sorting logic should be
         * -Top 5, Top 10 and Top 10% of Investment = Sort by %investment Descending
         * -Bottom 5, Bottom 10 and Bottom 10% of Investment = Sort by %Investment Ascending
         * -If Percentages are same sorting should be in alphabetical order
         */
        selectImpactFilterOption(option);
        BrowserUtils.wait(5);
        System.out.println("Verifying for " + option);
        if (option.contains("Top")) {
            for (int i = 0; i < impactTableCompanies.size() - 2; i++) {
                //Top 5, Top 10 and Top 10% of Investment = Sort by %investment Descending
                if (impactTableInvestmentPercentages.get(i).getText().compareTo(impactTableInvestmentPercentages.get(i + 1).getText()) > 0) {
                    System.out.println("Investment Percentage is not in descending order");
                    return false;
                }
                //If Percentages are same sorting should be in alphabetical order
                else if (impactTableInvestmentPercentages.get(i).getText().equals(impactTableInvestmentPercentages.get(i + 1).getText())) {
                    if (impactTableCompanyNames.get(i).getText().compareToIgnoreCase(impactTableCompanyNames.get(i + 1).getText()) > 0) {
                        System.out.println("Company Name is not in ascending order for same investment percentage");
                        System.out.println(impactTableCompanyNames.get(i).getText() + " | " + impactTableCompanyNames.get(i + 1).getText());
                        return false;
                    }
                }
            }
            return true;
        } else if (option.contains("Bottom")) {
            for (int i = 0; i < impactTableCompanies.size() - 2; i++) {
                //Bottom 5, Bottom 10 and Bottom 10% of Investment = Sort by %Investment Ascending
                if (impactTableInvestmentPercentages.get(i).getText().compareTo(impactTableInvestmentPercentages.get(i + 1).getText()) < 0) {
                    System.out.println("Investment Percentage is not in descending order");

                    return false;
                }
                //If Percentages are same sorting should be in alphabetical order
                else if (impactTableInvestmentPercentages.get(i).getText().equals(impactTableInvestmentPercentages.get(i + 1).getText())) {
                    if (impactTableCompanyNames.get(i).getText().compareToIgnoreCase(impactTableCompanyNames.get(i + 1).getText()) > 0) {
                        System.out.println("Company Name is not in ascending order for same investment percentage");
                        System.out.println(impactTableCompanyNames.get(i).getText() + " | " + impactTableCompanyNames.get(i + 1).getText());
                        return false;
                    }
                }
            }
            return true;
        } else {
            System.out.println("Invalid option");
            return false;
        }


    }

    public boolean checkLLeadersAndLaggardsEntityLinks(String page) {

        wait.until(ExpectedConditions.visibilityOfAllElements(leadersCompaniesList));
        wait.until(ExpectedConditions.visibilityOfAllElements(laggardsCompaniesList));

        WebElement WebElementleadersCompaniesList = leadersCompaniesList.get(0);
        String entityName = WebElementleadersCompaniesList.getText().split("\n")[1];
        WebElementleadersCompaniesList.click();
        BrowserUtils.wait(10);

             /*EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
            assertTestCase.assertTrue(entityProfilePage.validateGlobalHeader(entityName), "Validate entity Page has opened");*/

        Actions action = new Actions(Driver.getDriver());
        action.sendKeys(Keys.ESCAPE).build().perform();


        WebElement WebElementladdersCompaniesList = laggardsCompaniesList.get(0);
        entityName = WebElementladdersCompaniesList.getText().split("\n")[1];
        WebElementladdersCompaniesList.click();
        BrowserUtils.wait(10);

             /*EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
            assertTestCase.assertTrue(entityProfilePage.validateGlobalHeader(entityName), "Validate entity Page has opened");*/
        action.sendKeys(Keys.ESCAPE).build().perform();

        return true;
    }

    public boolean leaderAndlaggardSortingOrder(String option) {
        /**
         * Sorting logic should be
         * -Top 5, Top 10 and Top 10% of Investment = Sort by %investment Descending
         * -Bottom 5, Bottom 10 and Bottom 10% of Investment = Sort by %Investment Ascending
         * -If Percentages are same sorting should be in alphabetical order
         */
        selectImpactFilterOption(option);
        System.out.println("Verifying for " + option);
        if (option.contains("Top")) {
            for (int i = 0; i < impactTableCompanies.size() - 2; i++) {
                //Top 5, Top 10 and Top 10% of Investment = Sort by %investment Descending
                if (impactTableInvestmentPercentages.get(i).getText().compareTo(impactTableInvestmentPercentages.get(i + 1).getText()) > 0) {
                    System.out.println("Investment Percentage is not in descending order");
                    return false;
                }
                //If Percentages are same sorting should be in alphabetical order
                else if (impactTableInvestmentPercentages.get(i).getText().equals(impactTableInvestmentPercentages.get(i + 1).getText())) {
                    if (impactTableCompanyNames.get(i).getText().compareTo(impactTableCompanyNames.get(i + 1).getText()) > 0) {
                        System.out.println("Company Name is not in ascending order for same investment percentage");
                        System.out.println(impactTableCompanyNames.get(i).getText() + " | " + impactTableCompanyNames.get(i + 1).getText());
                        return false;
                    }
                }
            }
            return true;
        } else if (option.contains("Bottom")) {
            for (int i = 0; i < impactTableCompanies.size() - 2; i++) {
                //Bottom 5, Bottom 10 and Bottom 10% of Investment = Sort by %Investment Ascending
                if (impactTableInvestmentPercentages.get(i).getText().compareTo(impactTableInvestmentPercentages.get(i + 1).getText()) < 0) {
                    System.out.println("Investment Percentage is not in descending order");

                    return false;
                }
                //If Percentages are same sorting should be in alphabetical order
                else if (impactTableInvestmentPercentages.get(i).getText().equals(impactTableInvestmentPercentages.get(i + 1).getText())) {
                    if (impactTableCompanyNames.get(i).getText().compareTo(impactTableCompanyNames.get(i + 2).getText()) > 0) {
                        System.out.println("Company Name is not in ascending order for same investment percentage");
                        System.out.println(impactTableCompanyNames.get(i).getText() + " | " + impactTableCompanyNames.get(i + 1).getText());
                        return false;
                    }
                }
            }
            return true;
        } else {
            System.out.println("Invalid option");
            return false;
        }

    }

    public void validateEsgGradeDistribution() {
        assertTestCase.assertTrue(esgScoreDistribution.isDisplayed(), "Validate Grade Distribution Widget is available");
        assertTestCase.assertTrue(esgScoreDistributionHeader.getText().equals("ESG Score Distribution"), "Validate Grade Distribution Widget Header");
        assertTestCase.assertTrue(esgScoreDistributionTableHeader.get(0).getText().equals(""), "Validate Category column");
        assertTestCase.assertTrue(esgScoreDistributionTableHeader.get(1).getText().equals("% Investment"), "Validate Category column");

        List<String> Categories = Arrays.asList(new String[]{"Advanced", "Robust", "Limited", "Weak"});
        for (int i = 0; i < esgScoreDistributionTableCategoryList.size(); i++) {
            assertTestCase.assertTrue(esgScoreDistributionTableCategoryList.get(i).getText().equals(Categories.get(i)), "Validate " + Categories.get(i) + "Category");
        }


    }

    public void validateCountry() {
        String countryName = esgAssessmentsCountryTableList.get(0).getText();
        esgAssessmentsCountryTableList.get(0).click();
        BrowserUtils.wait(5);
        List<String> drawerHeading = Arrays.asList(esgAssessmentsCountryDrwaerPopup.getText().split("\n"));
        assertTestCase.assertTrue(drawerHeading.get(0).equals("All Sectors, " + countryName));

        assertTestCase.assertTrue(drawerHeading.get(1).contains("companies"), "Validate companies in header");
        assertTestCase.assertTrue(drawerHeading.get(1).contains("% Investment"), "Validate % Investment in header");
    }

    public double getInvestmentPercentSum(String impactType) {
        try {
            if (impactType.equals("Positive"))
                return Double.valueOf(impactTableInvestmentPercentages.get(impactTableInvestmentPercentages.size() - 1).getText().split("%")[0]);
            else if (impactType.equals("Negative"))
                return Double.valueOf(impactTableInvestmentPercentagesNegativeSide.get(impactTableInvestmentPercentagesNegativeSide.size() - 1).getText().split("%")[0]);
        } catch (Exception e) {
            return 0.00;
        }
        return 0.00;
    }

    public List<String> getInvestmentCompanies(String impactType) {
        List<String> companyNames = new ArrayList<>();
        if (impactType.equals("Positive"))
            impactTableCompanyNames.stream().forEach(f -> companyNames.add(f.getText()));
        else if (impactType.equals("Negative"))
            impactTableCompanyNameNegativeSide.stream().forEach(f -> companyNames.add(f.getText()));
        return companyNames;
    }

    public void validateEsgLeadersANDlaggersScorValuese() {
        List<String> Categories = Arrays.asList(new String[]{"Advanced", "Robust", "Limited", "Weak"});
        List<WebElement> e = LeadersAndLaggardsTable.findElements(By.xpath("//tbody/tr/td[4]"));
        for (int i = 0; i < e.size(); i++) {
            assertTestCase.assertTrue(Categories.contains(e.get(i).getText()), "Validate score categories");
        }
    }

    public void validateEsgLeadersANDlaggersModelvalues() {
        List<String> Categories = Arrays.asList(new String[]{"1.0", "2.0"});
        List<WebElement> e = LeadersAndLaggardsTable.findElements(By.xpath("//tbody/tr/td[5]"));
        for (int i = 0; i < e.size(); i++) {
            assertTestCase.assertTrue(Categories.contains(e.get(i).getText()), "Validate model values");
        }
    }

    public Boolean ValidateifEsgBenchmarkPortfolioBoxIsDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(EsgBenchmarkBox)).isDisplayed();
    }

    public boolean IsBenchmarkLableAvailable() {
        try {
            WebElement e = EsgBenchmarkBox.findElement(By.xpath("span"));
           return wait.until(ExpectedConditions.visibilityOf(e)).getText().equals("Benchmark");

        } catch (Exception e) {
            System.out.println("Failed");
            e.printStackTrace();
            return false;
        }
    }

    public boolean IsBenchmarkScoreCategoryAvailable() {
        try {
            WebElement e = EsgBenchmarkBox.findElement(By.xpath("div[1]/div[1]/div/span"));
            List<String> list =  Arrays.asList(new String[]{"Advanced","Robust","Limited","Weak"});
            return list.contains(wait.until(ExpectedConditions.visibilityOf(e)).getText());

        } catch (Exception e) {
            System.out.println("Failed");
            e.printStackTrace();
            return false;
        }
    }

    public boolean IsBenchmarkCoverageSectionAvailableAndMatchesWithPattern() {
        try {
            WebElement e = EsgBenchmarkBox.findElement(By.xpath("div[1]/div[2]/a"));
            String pattern = "Coverage: \\d+ companies; (?:\\d+(?:\\.\\d*)?|\\.\\d+)% investments";
            return e.getText().matches(pattern) ;

        } catch (Exception e) {
            System.out.println("Failed");
            e.printStackTrace();
            return false;
        }
    }

    public void validateBenchMarkDistributionSection() {
        try {
            WebElement DistibutionSection = EsgBenchmarkBox.findElement(By.xpath("div[2]"));
            assertTestCase.assertTrue(DistibutionSection.isDisplayed(),"Validate that Benchmark Distribution section is available");
            assertTestCase.assertTrue(DistibutionSection.findElement(By.xpath("div")).getText().equals("Benchmark Distribution"));
            WebElement DistibutionSection_Table = DistibutionSection.findElement(By.xpath("//table"));
            WebElement DistibutionSection_Table_Header = DistibutionSection_Table.findElement(By.xpath("thead/tr/th[2]"));
            List<WebElement> DistibutionSection_Table_Body = DistibutionSection_Table.findElements(By.xpath("tbody/tr"));
            assertTestCase.assertTrue(DistibutionSection_Table_Header.getText().equals("% Investment"),"Validate that Benchmark Distribution section's table header is '% Investment'");
            List<String> list =  Arrays.asList(new String[]{"Advanced","Robust","Limited","Weak"});
            for(WebElement e : DistibutionSection_Table_Body){
                String[] rowText = e.getText().split("\n");
                assertTestCase.assertTrue(list.contains(rowText[0]),"Validate Table category");
                assertTestCase.assertTrue(rowText[1].matches("(?:\\d+(?:\\.\\d*)?|\\.\\d+)%"),"Validate Table investment Pecentage");
            }

        } catch (Exception e) {
            System.out.println("Failed");
            e.printStackTrace();

        }
    }

    public Boolean IsGeoSectionTreeMapAvailable(){
        return geoSectionTreemap.isDisplayed();
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

    public void selectPortfolio(String portfolioName){
        System.out.println("Selecting portfolio: " + portfolioName);
        try{
            if (menu.isDisplayed()) {
                clickMenu();
                BrowserUtils.wait(2);
                portfolioSettings.click();
            }
            WebElement targetPortfolio = Driver.getDriver().findElement(By.xpath("//span[@title='"+portfolioName+"']"));
            BrowserUtils.scrollTo(targetPortfolio);
            targetPortfolio.click();
            BrowserUtils.wait(4);
            System.out.println("Portfolio selected");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deletePortfolio(String portfolioName) {
        System.out.println("Deleting portfolio: " + portfolioName);
        selectPortfolio(portfolioName);
        try{

            BrowserUtils.waitForClickablility(deleteButton, 15).click();
            BrowserUtils.waitForVisibility(confirmPortfolioDeletePopupHeader,10);
            confirmPortfolioDeleteYesButton.click(); //clicking the Yes button and deleting the portfolio
            BrowserUtils.wait(6);
            pressESCKey();
            System.out.println("Portfolio deleted");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

