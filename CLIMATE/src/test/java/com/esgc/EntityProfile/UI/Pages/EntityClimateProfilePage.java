package com.esgc.EntityProfile.UI.Pages;


import com.esgc.Base.API.Controllers.APIController;
import com.esgc.EntityProfile.DB.DBQueries.EntityClimateProfilePageQueries;
import com.esgc.PortfolioAnalysis.API.APIModels.RangeAndScoreCategory;
import com.esgc.Utilities.*;
import com.esgc.Utilities.Database.DatabaseDriver;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Year;
import java.util.List;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.esgc.Utilities.Database.DatabaseDriver.getQueryResultMap;
import static com.esgc.Utilities.DateTimeUtilities.getFormattedDate;


public class EntityClimateProfilePage extends ClimatePageBase {
    public List<String> sectorsList = Arrays.asList("Automobiles", "ARA - all sectors", "Oil&Gas", "Electric & Gas Utilities",
            "Shipping", "Airlines", "Cement", "Steel", "Aluminium");

    @FindBy(xpath = "//div[@aria-labelledby='alert-dialog-title']/div[@style]//*[text()]")
    public List<WebElement> companyHeaderItems;

    @FindBy(xpath="//span[@data-testid='confidence_level_test_id_2']")
    public WebElement confidenceLevel;

    @FindBy(xpath = " (//*[name()='g'][contains(@class,'highcharts-legend-item highcharts-line-')])")
    public List<WebElement> temperatureAlignmentCharBenchmark;

    @FindBy(xpath = "(//div[@id='controversyError'])")
    public List<WebElement> controversiesNoInfo;

    @FindBy(xpath = "//div[@id='tempAlignProjErr']")
    public List<WebElement> noInfoElementChart;

    @FindBy(xpath = "//div[@id='tempAlignErr']")
    public List<WebElement> noInfoElement;

    @FindBy(xpath = "//span[text()='Transition Risk']/../../..//div[text()='Brown Share Assessment']/../../../following-sibling::div//div[text()='No information available.']")
    public WebElement trBrownShareAssessmentNoInfo;

    @FindBy(xpath = "//span[text()='Transition Risk']/../../..//div[text()='Brown Share Assessment']/../../../../../following-sibling::div//div[text()='No sector comparison chart available.']")
    public WebElement trBrownShareAssessmentSectorChartNoInfo;

    @FindBy(xpath = "//li[@role='menuitem']/../../../div[2]//*[local-name()='svg']")
    public WebElement entityProfileCloseIcon;

    @FindBy(xpath = "//div[@id='carbonClimate-test-id']/div/div/div/div/div/div")
    public List<WebElement> carbonFootprintNoInfo;

    @FindBy(xpath = "(//div[@id='carbonClimate-test-id'])[2]/div[2]/div/div/span[1]")
    public List<WebElement> carbonFootprintValueUnderlying;

    @FindBy(xpath = "(//div[@id='carbonClimate-test-id'])[1]/div[2]/div/div/span[1]")
    public List<WebElement> carbonFootprintValueSummary;

    @FindBy(xpath = "(//*[name()='svg'][@class='MuiSvgIcon-root'])[1]")
    public WebElement searchIcon;

    @FindBy(xpath = "//input[@id='platform-search-test-id']")
    public WebElement searchInputBox;

    @FindBy(xpath = "//div[@id='mini-0']")
    public WebElement firstElementOfSearch;

    @FindBy(id = "export_sources_doc_button")
    public WebElement exportSourcesDocumentsTab;

    @FindBy(xpath = "//button[@id='export_pdf']")
    public WebElement pdfDownloadButton;

    @FindBy(id = "ref_Meth_button")
    public WebElement referenceAndMethodologiesTab;

    @FindBy(xpath = "//div[@role='dialog'][not(@aria-describedby)]//h2")
    public WebElement exportPopupTitleElement;

    @FindBy(xpath = "//div[@role='dialog'][not(@aria-describedby)]/div[contains(@class,'Content')]/div/div")
    public WebElement exportPopupSubtitleElement;

    @FindBy(xpath = "//div[@role='dialog'][not(@aria-describedby)]//div[contains(translate(text(), " +
            "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'source documents')]")
    public WebElement sourceDocumentsDiv;
    @FindBy(xpath = "//div[@role='dialog'][not(@aria-describedby)]//div/ul/li/a")
    public List<WebElement> listSourceDocuments;

    @FindBy(xpath = "//div[text()='This company has ']/span")
    public WebElement subsidiaryLink;

    @FindBy(xpath = "//li[@role='menuitem']//span[text()]")
    public WebElement globalHeaderCompanyNameLabel;

    @FindBy(xpath = "//header/..//div[starts-with(text(),'Subsidiar')]")
    public WebElement subsidiaryCompaniesHeader;

    @FindBy(xpath = "//header/..//div[starts-with(text(),'Subsidiar')]/following-sibling::div")
    public WebElement subsidiaryCompaniesPopupDescription;

    @FindBy(xpath = "//table[@id='subsidiaries-table-id']//th")
    public List<WebElement> subsidiaryCompaniesTableColumns;

    @FindBy(xpath = "//div[@id='entitySourceDocuments']//div[contains(@class,'MuiGrid-item')]/div[1]")
    public WebElement sourceDocumentsMessage;

    @FindBy(xpath = "//div[@role]/div[@aria-hidden='true']")
    public WebElement outSideOfPopup;

    @FindBy(xpath = "//div[@role='dialog'][not(@aria-describedby)]//button")
    public WebElement exportCloseIcon;

    @FindBy(xpath = "//div[@id='div-mainlayout']//header")
    public WebElement companyHeader;

    @FindBy(xpath = "//div[@id='greenClimate-test-id']")
    public WebElement greenShareCard;

    @FindBy(xpath = "//div[@id='brownClimate-test-id']")
    public WebElement brownShareCard;

    @FindBy(xpath = "(//table[@id='table-id-1'])[2]")
    public WebElement BrownShareTransitionRiskTable;

    @FindBy(xpath = "//div[@id='greenClimate-test-id']/div/div/div[1]/div")
    public List<WebElement> listOfGreenShareCardlabels;

    @FindBy(xpath = "//div[@id='brownClimate-test-id']/div/div/div[1]/div")
    public List<WebElement> listOfBrownShareCardlabels;

    @FindBy(xpath = "(//div[@id='greenClimate-test-id'])[2]")
    public WebElement underlyingDataMetrics_GreenShareAssessmentCard;

    @FindBy(xpath = "(//div[@id='greenClimate-test-id'])[2]//table")
    public WebElement underlyingDataMetrics_GreenShareAssessment_Table;

    @FindBy(xpath = "(//*[name()='path'][@class='highcharts-point highcharts-color-0'])")
    public List<WebElement> pieCharts;

    @FindBy(xpath = "//div[@id='phyRskMgmClimate-test-id']//div[@id='chiclet-id']/child::div[2]/span")
    public WebElement PhysicalRiskMgmtWidgetValue;

    @FindBy(xpath = "//div[@id='greenClimate-test-id']//div[text()='Green Share']/following-sibling::div/span[2]")
    public WebElement GreenShareWidgetValue;

    @FindBy(xpath = "//div[@id='brownClimate-test-id']//div[text()='Brown Share']/following-sibling::div/span[2]")
    public WebElement BrownShareWidgetValue;

    @FindBy(xpath = "//div[@id='tempAlignclimate-test-id']")
    public WebElement temperatureAlignmentWidget;

//    @FindBy(id = "carbonClimate-test-id")
//    public WebElement carbonFootprintWidget ;

    @FindBy(xpath = "//div[.='Most Risk']")
    public WebElement mostRiskText;

    @FindBy(xpath = "//div[.='Least Risk']")
    public WebElement leastRiskText;

    @FindBy(xpath = "//div[.='Red Flag']")
    public WebElement redFlag;

    @FindBy(xpath = "//div[.='No Risk']")
    public WebElement noRisk;

    @FindBy(xpath = "//div[.='Physical Climate Risk: Operations Risk']")
    public WebElement physicalClimateRiskOperationsRiskText;

    @FindBy(xpath = "//div[@id='carbonClimate-test-id']")
    public WebElement carbonFootprintWidget;

    @FindBy(xpath = "//div[@id='carbonClimate-test-id']/div/following-sibling::div/div/div")
    public WebElement carbonFootprintLineItemUnderCarbonFootPrintSummaryWidget;

    @FindBy(xpath = "//div[@id='tempAlignclimate-test-id']/div[1]/div/div")
    public List<WebElement> temperatureAlignmentWidgetHeader;

    @FindBy(xpath = "//p[text()='Materiality: Low']/../..//ul/li")
    public List<WebElement> lowMaterialityElements;

    @FindBy(xpath = "//p/../..//ul/li")
    public List<WebElement> materialityElements;

    @FindBy(xpath = "//div[@id='tempAlignclimate-test-id']/div[2]/div/div")
    public List<WebElement> temperatureAlignmentDetailDivs;

    @FindBy(id = "phyClimate-test-id")
    public WebElement physicalClimateHazards;

    @FindBy(xpath = "//span[contains(text(),'Orbis ID:')]/following-sibling::span")
    public WebElement orbisIdLabel;

    @FindBy(xpath = "//div/span[contains(text(),'Orbis ID:')]/following-sibling::span")
    public WebElement orbisIdValue;

    @FindBy(xpath = "//div[text()='Green Share']/../following-sibling::div//div[2]/span")
    public WebElement greenShareScoreRangeLabel;

    @FindBy(xpath = "//span[.='Transition Risk']")
    public WebElement transitionRiskPage;

    @FindBy(xpath = "///*[@fill='#b6d1d1']")
    public List<WebElement> loadingIcons;

    @FindBy(xpath = "//span[text()='Physical Risk']/..")
    public WebElement physicalRiskTab;

    @FindBy(xpath = "//span[text()='Transition Risk']/..")
    public WebElement transitionRiskTab;

    @FindBy(xpath = "//button[@heap_perfchart_id='Materiality']")
    public WebElement esgMaterialityTab;

    @FindBy(xpath = "//section//li/section/span[1]/span[1]")
    public List<WebElement> esgSections;

    @FindBy(xpath = "//section//div/p")
    public List<WebElement> esgMaterialityColumns;

    @FindBy(xpath = "//div[@role='dialog'][not(@aria-describedby)]//h2//div[contains(@class,'MuiGrid-item')][1]")
    public WebElement materialityPopupTitle;

    @FindBy(id = "dropdown-graph-selection-id")
    public WebElement comparisonChartRiskSelectionDropdown;

    @FindBy(xpath = "//div[.='No sector comparison chart available.']")
    public WebElement noSectorComparisonChartAvailable;

    @FindBy(xpath = "//*[@data-value='operationsRisk']/../li")
    public List<WebElement> comparisonChartRiskSelectionOptions;

    @FindBy(xpath = "//span[@variant='outlined']/../*")
    public List<WebElement> comparisonChartLegends;

    @FindBy(xpath = "//span[text()='Transition Risk']/../../..//div[text()='Brown Share Assessment']/../../../../../following-sibling::div//span[@variant='outlined']/../*")
    public List<WebElement> brownShareComparisonChartLegends;

    @FindBy(xpath = "//div[text()='Brown Share Assessment']/../../../../../..//*[local-name()='path' and @class='highcharts-plot-line ']")
    public WebElement brownShareComparisonChartAverageLine;

    @FindBy(xpath = "//span[text()='Transition Risk']/../../..//div[text()='Brown Share Assessment']/../../../../../following-sibling::div//div[text()]")
    public List<WebElement> brownShareComparisonChartAxes;

    @FindBy(xpath = "//span[@variant='outlined']/../p")
    public List<WebElement> Para_comparisonChartLegends;

    @FindBy(xpath = "//span[@variant='outlined']/../../../../preceding-sibling::div")
    public WebElement comparisonChartHeader;

    @FindBy(xpath = "//*[local-name()='rect' and @opacity='1' and @stroke='#ffffff']")
    public List<WebElement> comparisonChartBars;

    @FindBy(xpath = " //*[local-name()='rect' and @opacity='1' ]")
    public List<WebElement> comparisonChartBarsBig;


    @FindBy(xpath = "//span[@variant='outlined']/../../../../../../following-sibling::div[1]/div[1]/div")
    public List<WebElement> comparisonChartYAxis;

    @FindBy(xpath = "//*[@id='table-id-1']/../../preceding-sibling::div")
    public WebElement operationsRiskLabel;

    @FindBy(xpath = "(//div[text()='Market Risk'])")
    public WebElement marketRiskLabel;

    @FindBy(xpath = "//div[contains(text(),'Supply Chain Risk')]")
    public WebElement supplyChainRiskLabel;

    @FindBy(xpath = "//div[text()='Physical Climate Risk: Operations Risk']")
    public WebElement physicalClimateRiskOperationsRiskLabel;

    @FindBy(xpath = "//span[text()='Transition Risk']/../../..//div[@id='tempAlignclimate-test-id']")
    public WebElement transitionRiskTemperatureAlignmentWidget;

    @FindBy(xpath = "//span[text()='Transition Risk']/../../..//div[@id='carbonClimate-test-id']")
    public WebElement transitionRiskCarbonFootprintWidget;

    @FindBy(xpath = "(//div[@id='carbonClimate-test-id']//div[@id='innerBox00'])[2]")
    public WebElement transitionRiskCarbonFootprintCategory;

    @FindBy(xpath = "//span[text()='Transition Risk']/../../..//div[text()='Green Share Assessment']")
    public WebElement transitionRiskGreenShareWidget;

    @FindBy(xpath = "//span[text()='Transition Risk']/../../..//div[text()='Green Share Assessment']/../../../following-sibling::div//table/tbody/tr/td[1]")
    public List<WebElement> transitionRiskGreenShareTableBody;

    @FindBy(xpath = "//span[text()='Transition Risk']/../../..//div[text()='Green Share Assessment']/../../../following-sibling::div//table/thead/tr/th")
    public List<WebElement> transitionRiskGreenShareTableHeadings;

    @FindBy(xpath = "//span[text()='Transition Risk']/../../..//div[text()='Brown Share Assessment']")
    public WebElement transitionRiskBrownShareWidget;

    @FindBy(xpath = "//span[text()='Transition Risk']/../../..//div[text()='Brown Share Assessment']/../../../following-sibling::div/div/div[1]")
    public WebElement transitionRiskBrownShareWidgetSubHeading;

    @FindBy(xpath = "//span[text()='Transition Risk']/../../..//div[text()='Brown Share Assessment']/../../../following-sibling::div/div/div[1]/span")
    public WebElement transitionRiskBrownShareWidgetOverallRevenue;

    @FindBy(xpath = "//span[text()='Transition Risk']/../../..//div[text()='Brown Share Assessment']/../../../following-sibling::div/div/div[3]")
    public WebElement transitionRiskBrownShareWidgetUpdatedDate;

    @FindBy(xpath = "//div[text()='Brown Share Assessment']//div[@id='climate-from-summaryoverview-']//div[text()]")
    public WebElement transitionRiskBrownShareCategory;

    @FindBy(xpath = "//span[text()='Transition Risk']/../../..//div[text()='Brown Share Assessment']/../../../following-sibling::div//table/tbody/tr/td[1]")
    public List<WebElement> transitionRiskBrownShareTableBody;

    @FindBy(xpath = "//span[text()='Transition Risk']/../../..//div[text()='Brown Share Assessment']/../../../following-sibling::div//table/tbody/tr/td[2]")
    public List<WebElement> transitionRiskBrownShareTableValues;

    @FindBy(xpath = "//span[text()='Transition Risk']/../../..//div[text()='Brown Share Assessment']/../../../following-sibling::div//table/thead/tr/th")
    public List<WebElement> transitionRiskBrownShareTableHeadings;

    @FindBy(id = "temperature-alignment-chart-placeholder")
    public WebElement summaryWidget;

    @FindBy(id = "tempAlignProjErr")
    public WebElement noUpdatesSummaryWidget;

    @FindBy(xpath = "//div[@id='temperature-alignment-chart-placeholder']/div/div/div[1]")
    public WebElement temperatureAlignmentGraphTitle;

    @FindBy(xpath = "//div[@id='temperature-alignment-chart-placeholder']/div/div/div[3]")
    public WebElement temperatureAlignmentGraphFooter;

    @FindBy(id = "climate-from-summaryoverview-")
    public WebElement temperatureAlignmentStatus;

    //div[@id='temperature-alignment-chart-placeholder']//*[local-name() = 'g']//*[local-name() = 'text']
    @FindBy(xpath = " //div[@id='temperature-alignment-chart-placeholder']//*[local-name() = 'g']//*[local-name() = 'text']")
    public List<WebElement> widgetTexts;

    @FindBy(xpath = "//*[local-name() = 'text' and starts-with(.,'Emissions')]")
    public WebElement industryMetric;

    @FindBy(xpath = "//div[@id='temperature-alignment-chart-placeholder']//*[local-name() = 'path' and @fill='#1F8CFF']")
    public List<WebElement> historicalLineDots;

    @FindBy(xpath = "//*[local-name()='text' and contains(.,'Year:')]")
    public WebElement historyLineDotHoverText;

    @FindBy(xpath = "//div[@id='temperature-alignment-chart-placeholder']//*[local-name() = 'path' and @fill='#6FA0AD']")
    public List<WebElement> benchmark15CLineDots;

    @FindBy(xpath = "//div[@id='temperature-alignment-chart-placeholder']//*[local-name() = 'path' and @fill='#A1C8D3']")
    public List<WebElement> benchmark165CLineDots;

    @FindBy(xpath = "//div[@id='temperature-alignment-chart-placeholder']//*[local-name() = 'path' and @fill='#E19E7C']")
    public List<WebElement> benchmark27CLineDots;

    @FindBy(id = "phyRskMgmClimate-test-id")
    public WebElement physicalRiskManagementWidget;

    @FindBy(xpath = "//div[@id='phyRskMgmClimate-test-id']/div/div/div/div")
    public WebElement physicalRiskManagementWidgetTitle;

    @FindBy(xpath = "//div[@id='phyRskMgmClimate-test-id']/div/div/div/div[2]")
    public WebElement physicalRiskManagementWidgetDescription;

    @FindBy(xpath = "(//div[@id='chiclet-id']//span)[2]")
    public WebElement physicalRiskManagementWidgetStatus;


    @FindBy(xpath = "//div[@id='prmErr']")
    public WebElement physicalRiskManagementWidgetNoData;

    @FindBy(xpath = "//span[text()='Transition Risk']/../../..//div[@id='carbonClimate-test-id']//span/..")
    public List<WebElement> transitionRiskCarbonFootprintWidgetItems;

    @FindBy(xpath = "//span[text()='Transition Risk']/../../..//div[@id='carbonClimate-test-id']//span")
    public List<WebElement> transitionRiskCarbonFootprintWidgetValues;

    //Operations Risk Table Columns

    @FindBy(xpath = "//table[@id='table-id-1']")
    public WebElement physicalRiskOperationRisk;

    @FindBy(xpath = "(//*[@id='table-id-1'])[2]//td[1]")
    public List<WebElement> physicalClimateHazardsItems;

    @FindBy(xpath = "(//*[@id='table-id-1'])[2]//td[2]")
    public List<WebElement> riskLevelItems;

    @FindBy(xpath = "(//*[@id='table-id-1'])[2]//td[3]")
    public List<WebElement> facilitiesExposedItems;


    //Market Risk Table
    @FindBy(xpath = "(//*[@id='table-id-2'])")
    public List<WebElement> marketRiskTable;

    @FindBy(xpath = "(//*[@id='table-id-2'])[2]//td[1]")
    public List<WebElement> marketRiskIndicators;

    @FindBy(xpath = "(//*[@id='table-id-2'])[2]//td[2]")
    public List<WebElement> marketRiskScores;


    //Supply CHain Risk Table
    @FindBy(xpath = "(//*[@id='table-id-3'])")
    public List<WebElement> supplyChainRiskTable;

    @FindBy(xpath = "(//*[@id='table-id-3'])//td[1]")
    public List<WebElement> supplyChainRiskIndicators;

    @FindBy(xpath = "(//*[@id='table-id-3'])//td[2]")
    public List<WebElement> supplyChainRiskScores;


    @FindBy(xpath = "//button/span[contains(text(),'Physical Risk')]")
    public WebElement button_PhysicalRiskTab;

    @FindBy(xpath = "//div[normalize-space()='PHYSICAL RISK MANAGEMENT']")
    public WebElement header_PhysicalRiskManagement;

    @FindBy(xpath = "//div[contains(text(),'Physical Risk Management') and .//*[starts-with(text(),'Anticipation,')]]")
    public WebElement PhysicalRiskManagementTable;
    //ESG Monitoring Section

    @FindBy(xpath = "//div[@id='cardInfo_box'][./div[text()='ESG Score']]")
    public WebElement esgScoreWidget;

    @FindBy(xpath = "//div[@id='sector_comparison_chart_box']")
    public WebElement sectorComparisonChart;

    @FindBy(xpath = "//div[.='ESG Score']")
    public WebElement esgTitle;

    @FindBy(xpath = "//div[@id='cardInfo_box']/div[text()='ESG Score']/parent::div/div/div/div")
    public List<WebElement> esgScores;

    @FindBy(xpath = "//div[.='Overall ESG Score']/../div/div/div/div[2]")
    public List<WebElement> esgScoreCategories;

    //Ignore first element
    @FindBy(xpath = "//div[ @id='portfolio_box' ]/div/div/div/div")
    public List<WebElement> esgScorePillars;

    @FindBy(xpath = "//div[contains(@class,'MuiTooltip-tooltip MuiTooltip-tooltipPlacementTop')]/ul/li")
    public List<WebElement> carbonFootPrintTooltip;


    @FindBy(xpath = "//div[@id='cardInfo_box']/div/div/div/div")
    public List<WebElement> esgScore;
    @FindBy(xpath = "//div[@id='sector_comparison_chart_box']/div")
    public WebElement esgSectorComparisonChart;
    @FindBy(xpath = "//div[@id='temperature-alignment-chart-placeholder']/div/div/div")
    public List<WebElement> tempAlignmentChart;
    @FindBy(xpath = "//div[@id='tempAlignclimate-test-id']")
    public WebElement tempAlignmentCard;
    @FindBy(xpath = "//div[@id='carbonClimate-test-id']")
    public WebElement carbonFootprintCard;
    @FindBy(xpath = "//div[@id='phyClimate-test-id']")
    public WebElement physicalRiskHazardsCard;
    @FindBy(xpath = "//div[@id='phyRskMgmClimate-test-id']")
    public WebElement physicalRiskManagementCard;
    @FindBy(xpath = "//span[normalize-space()='Controversies']")
    public WebElement controversies;
    @FindBy(xpath = "//p[normalize-space()='Materiality: Very High']")
    public WebElement esgSubCategory;
    @FindBy(xpath = "//span[normalize-space()='ESG Materiality']")
    public WebElement esgMateriality;

    @FindBy(xpath = " //span[contains(text(),'Overall Disclosure Score')]")
    public WebElement entityDisclosureScore;

    @FindBy(xpath = "//div[contains(text(),'About')]/ancestor::div[contains(@class,'MuiPaper-root MuiDrawer')]")
    public WebElement aboutDrawerMainDiv;

    @FindBy(xpath = "//table[@id='table-id-3']/../../../following-sibling::div[1]/span")
    public WebElement PhysicalRisk_ClimateHazard_UpdatedDate_Span;

    @FindBy(xpath = "//table[@id='table-id-3']/../../../following-sibling::div[3]/span")
    public WebElement PhysicalRisk_PhysicalRiskManagement_UpdatedDate_Span;

    // Entity Page
    @FindBy(xpath = "//*[@id=\"controversyError\"]/div/div/div[1] ")
    public WebElement noControversies;

    @FindBy(xpath = "//span[normalize-space()='Controversies']")
    public List<WebElement> controversiesTitle;

    //return WebElement

    public WebElement getCompanyHeader(String companyName) {
        return Driver.getDriver().findElement(By.xpath("//span[contains(text(),'" + companyName + "')]"));
    }

    @FindBy(xpath = "//div[normalize-space()='Filter by most impacted categories of ESG:']")
    public WebElement controversiesStaticText;

    @FindBy(xpath = "//div[normalize-space()='Filter by most impacted categories of ESG:']//following-sibling::div")
    public List<WebElement> subCategoryList;

    @FindBy(xpath = "//tr[@heap_id='event' and @notclickable='false']")
    public List<WebElement> controversiesTableRow;

    @FindBy(xpath = "//div[@id='methodologies_modal']//div[@role='dialog']/div[2]/div/div/div/span")
    //main/div/div/div/div/div/div/div/div/div/div/span[1]")
    public WebElement controversiesPopUpClose;

    @FindBy(xpath = "(//div[contains(text(),'Controversies')])")
    public WebElement controversiesPopUpVerify;

    @FindBy(xpath = "//*[@id=\"div-mainlayout\"]/div/div/main/header/div/div/div/div[2]/span[4]/span")
    public WebElement sectorInHeader;

    @FindBy(xpath = "//body/div[@id='company-summary-panel']/div/div/div[5]")
    public WebElement companyDrawerSector;


    ///============= Methods

    public boolean isWidgetHave(String text) {
        System.out.println("widgetTexts.size() = " + widgetTexts.size());
        // BrowserUtils.waitForVisibility(widgetTexts.get(0), 5);
        for (int i = 0; i < widgetTexts.size(); i++) {
            System.out.println("widgetTexts.getText() = " + widgetTexts.get(i).getText());
            if (i == widgetTexts.size() - 2) break;
        }
        return widgetTexts.stream().anyMatch(e -> e.getText().contains(text));
    }

    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public String searchAndLoadClimateProfilePage(String companyToSearch) {
        waitForDataLoadCompletion();
        clickSearchIcon();
        searchInputBox.sendKeys(companyToSearch);
        String companyName = wait.until(ExpectedConditions.visibilityOf(firstElementOfSearch)).getText().split("\n")[0];
        firstElementOfSearch.click();
        return companyName;
    }

    public boolean validateGlobalCompanyNameHeader(String companyName) {
        try{
            return wait.until(ExpectedConditions.visibilityOf(Driver.getDriver().findElement(By.xpath("//li//span[text()='"+companyName+"']")))).isDisplayed();
        }catch(Exception e){
            return false;
        }
    }

    public void clickGlobalHeader() {
        BrowserUtils.waitForClickablility(globalHeaderCompanyNameLabel,20).click();
    }

    public void clickSubsidiaryCompaniesLink() {
        subsidiaryLink.click();
    }

    public void verifySubsidiaryCompaniesCount(int count) {
        String subsidiaryText = subsidiaryLink.getText();
        subsidiaryText = subsidiaryText.substring(0,subsidiaryText.indexOf(" "));
        assertTestCase.assertEquals(count, Integer.parseInt(subsidiaryText),"Verification of Subsidiary Companies Count");
    }

    public boolean verifySubsidiaryCompaniesLink() {
        try {
            BrowserUtils.waitForVisibility(subsidiaryLink,30);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public void verifySubsidiaryCompaniesPopup(String companyName) {
        verifyCompanyNameInCoveragePopup(companyName);
        verifyCompanyIsClickableInCoveragePopup(companyName);
        assertTestCase.assertTrue(subsidiaryCompaniesHeader.isDisplayed(),"Verify Subsidiary Companies popup header");

        String expDescription="Unless assessed, subsidiaries have the same score as their parent company";
        String actDescription=subsidiaryCompaniesPopupDescription.getText();
        assertTestCase.assertEquals(actDescription,expDescription,"Verify Subsidiary Companies popup description");

        assertTestCase.assertEquals(subsidiaryCompaniesTableColumns.get(0).getText(),"Company Name","Verify Subsidiary Companies Table columns");
        assertTestCase.assertEquals(subsidiaryCompaniesTableColumns.get(1).getText(),"ESG Score","Verify Subsidiary Companies Table columns");
    }

    public boolean verifySubsidiaryCompaniesSectionInCoveragePopup() {
        try{
            return subsidiaryCompaniesHeader.isDisplayed();
        }catch(Exception e){
            return false;
        }
    }

    public void verifyCompanyNameInCoveragePopup(String subsidiaryCompanyName) {
        String xpath = "//div[contains(@class,'CompanyNameWrapper')]//span[@title][text()='"+subsidiaryCompanyName+"']";
        assertTestCase.assertEquals(Driver.getDriver().findElements(By.xpath(xpath)).size(), 1);
    }

    public void verifyCompanyIsClickableInCoveragePopup(String companyName) {
        String xpath = "//div[contains(@class,'CompanyNameWrapper')]//span[@title][text()='"+companyName+"']";
        WebElement element = Driver.getDriver().findElement(By.xpath(xpath));
        assertTestCase.assertTrue(element.getCssValue("text-decoration").contains("underline"));
    }


    public boolean isProvidedFilterClickableInMaterialityMatrixFooter(String filterName) {
        try {
            WebElement element = Driver.getDriver().findElement(By.xpath("//div/a[text()='" + filterName + "']"));
            BrowserUtils.scrollTo(element);
            BrowserUtils.waitForClickablility(element, 30);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isProvidedFilterSelectedByDefaultInMaterialityMatrixFooter(String filterName) {
        WebElement element = Driver.getDriver().findElement(By.xpath("//div/a[text()='" + filterName + "']"));
        BrowserUtils.scrollTo(element);
        String classAttribute = element.getAttribute("class");
        System.out.println(classAttribute + " " + classAttribute.split("jss").length);
        return classAttribute.split("jss").length == 4;
    }


    public void validateCompanyHeader(String companyName) {
        List<String> actualHeaderItems = new ArrayList<>();
        for(WebElement item:companyHeaderItems) {
            actualHeaderItems.add(item.getText());
        }

        List<String> expectedHeaderItems = new ArrayList<>();
        expectedHeaderItems.add(companyName);
        //TODO: On Demand Release
        // expectedHeaderItems.add("Confidence Level:");
        expectedHeaderItems.add("Export/Sources Documents");
        expectedHeaderItems.add("Reference and Methodologies");
        expectedHeaderItems.add("ESC");

        for(String expItem:expectedHeaderItems) {
            boolean matched = false;
            for(String actItem:actualHeaderItems){
                if(actItem.contains(expItem)){
                    matched = true;
                    break;
                }
            }
            assertTestCase.assertTrue(matched, expItem+" is not available in the header");
        }
    }

    public void selectExportSourcesDocuments() {
        BrowserUtils.waitForClickablility(exportSourcesDocumentsTab, 30).click();
    }

    public boolean IsExportSourcesDocumentsButtonAvailable() {
        try {
            return exportSourcesDocumentsTab.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean IsSourceDocumentsDivAvailable() {
        try {
            return sourceDocumentsDiv.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean IsPDFButtonAvailable() {
        try {
            return pdfDownloadButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void selectPdfDownload() {
        BrowserUtils.waitForClickablility(pdfDownloadButton, 30).click();
    }

    public boolean IsPdfDownloadButtonAvailable() {
        try{
         return pdfDownloadButton.isDisplayed();
        }catch(Exception e){
            return false;
        }
    }

    public boolean checkDownloadProgressBarIsPresent() {
        try {
            return Driver.getDriver().findElement(By.xpath("//div[text()='Download in progress!']")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void closeEntityProfilePage() {
        entityProfileCloseIcon.click();
    }

    public void closeDownloadProgressBar() {
        Driver.getDriver().findElement(By.xpath("//div[text()='Download in progress!']/following-sibling::div/button/span")).click();

    }

    public boolean verifyDownloadProgressMessage() {
        try {
            WebElement progressMessage = Driver.getDriver().findElement(By.xpath("//div[text()='Download in progress!']"));
            BrowserUtils.waitForVisibility(progressMessage, 30);
            BrowserUtils.waitForInvisibility(progressMessage, 60);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean verifyDownloadPdfButtonIsDisabled() {
        return pdfDownloadButton.getAttribute("class").contains("disable");
    }

    public boolean verifyMethodologiesTabIsDisplayed() {
        try {
            BrowserUtils.waitForVisibility(referenceAndMethodologiesTab, 30);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void selectReferenceAndMethodologiesTab() {
        BrowserUtils.waitForClickablility(referenceAndMethodologiesTab, 30).click();
    }

    public void scrollToBottomOfPage() {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)", "");
    }

    public List<String> getMethodologiesLinks() {
        List<WebElement> links = Driver.getDriver().findElements(By.xpath("//a[@download]"));
        List<String> methodologies = new ArrayList<>();
        for (WebElement link : links) {
            methodologies.add(link.getText());
        }
        return methodologies;
    }

    public boolean verifyPopup() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(exportPopupTitleElement)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyPopupTitle(String entityName) {
        String exportPopupTitle = exportPopupTitleElement.getText();
        System.out.println("exportPopupTitle = " + exportPopupTitle);
        System.out.println("entityName = " + entityName);
        return exportPopupTitle.contains(entityName);
    }

    public boolean verifyExportPopupSubTitle(String subTitle) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(30));
        wait.until(ExpectedConditions.textToBePresentInElement(exportPopupSubtitleElement, subTitle));
        String exportPopupSubtitle = exportPopupSubtitleElement.getText();
        return exportPopupSubtitle.equals(subTitle);
    }

    public boolean verifySourceDocuments() {
        return listSourceDocuments.size() > 0;
    }

    public boolean verifyExportPopupNoDocsMessage(String message) {
        return sourceDocumentsMessage.getText().equals(message);
    }

    public boolean downloadSourceDocument() {
        String docName = listSourceDocuments.get(0).getText();
        return validateLinksOpenedInNewTab(listSourceDocuments.get(0), docName);
    }

    public boolean validateLinksOpenedInNewTab(WebElement element, String whatToValidate) {
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
                deleteDownloadFolder(); // Delete existing files
                // downloadPdfFromNewTab(whatToValidate);
                // BrowserUtils.wait(10);
                // assertTestCase.assertTrue(isDocumentDownloaded(whatToValidate));
                driver.close();
                BrowserUtils.wait(2);
                driver.switchTo().window(mainWindowHandler);
            }
        }
        return true;
    }

    public void downloadPdfFromNewTab(String fileName) {
        try {
            Robot rb = new Robot();
            rb.keyPress(KeyEvent.VK_CONTROL);
            rb.keyPress(KeyEvent.VK_S);
            BrowserUtils.wait(2);
            rb.keyRelease(KeyEvent.VK_CONTROL);
            rb.keyRelease(KeyEvent.VK_S);
            String filePath = BrowserUtils.downloadPath() + "\\" + fileName;
            File directory = new File(BrowserUtils.downloadPath());
            if (!directory.exists()) {
                directory.mkdir();
            }
            StringSelection stringSelection = new StringSelection(filePath);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
            BrowserUtils.wait(5);
            rb.keyPress(KeyEvent.VK_CONTROL);
            rb.keyPress(KeyEvent.VK_V);
            BrowserUtils.wait(2);
            rb.keyRelease(KeyEvent.VK_CONTROL);
            rb.keyRelease(KeyEvent.VK_V);
            BrowserUtils.wait(2);
            rb.keyPress(KeyEvent.VK_ENTER);
            BrowserUtils.wait(2);
            rb.keyRelease(KeyEvent.VK_ENTER);
        } catch (Exception e) {
            System.out.println("Exception occurred while downloading pdf file");
        }
    }

    public boolean downloadMethodologyDocument() {
        deleteDownloadFolder();
        String docName = listSourceDocuments.get(0).getText();
        listSourceDocuments.get(0).click();
        BrowserUtils.wait(10);
        return isDocumentDownloaded(docName);
    }

    public boolean isDocumentDownloaded(String fileName) {
        System.out.println(BrowserUtils.downloadPath());
        File dir = new File(BrowserUtils.downloadPath());
        File[] dir_contents = dir.listFiles();
        for (File file : dir_contents) {
            System.out.println(file.getAbsolutePath());
        }
        return Arrays.asList(dir_contents).stream().filter(e -> e.getName().contains(fileName)).findAny().isPresent();
    }

    public void clickOutsideOfPopup() {
        //outSideOfPopup.click();

        JavascriptExecutor executor = (JavascriptExecutor) Driver.getDriver();
        executor.executeScript("arguments[0].click();", outSideOfPopup);
    }

    public void closePopup() {
        exportCloseIcon.click();
    }

    public boolean validateifPieChartIsAvailable(String widgetName) {
        String percentValue = "";
        switch (widgetName) {
            case "greenshare":
                percentValue = Driver.getDriver().findElement(By.xpath("(//div[@id='chiclet-id'])[2]")).getText();
                System.out.println(percentValue + " ===>");
                if (!percentValue.equalsIgnoreCase("0%"))
                    return pieCharts.get(0).isDisplayed();
                else
                    return true;
            case "brownshare":
                percentValue = Driver.getDriver().findElement(By.xpath("(//div[@id='chiclet-id'])[3]")).getText();
                System.out.println(percentValue + " ===>");
                if (!percentValue.equalsIgnoreCase("0%"))
                    return pieCharts.get(1).isDisplayed();
                else
                    return true;
        }
        return false;
    }

    public boolean checkIfGreenShareCardISAvailable() {
        return greenShareCard.isDisplayed();
    }

    public boolean checkIfBrownShareCardISAvailable() {
        return brownShareCard.isDisplayed();
    }

    public String getColorcode(String reserchLine, int score) {
        APIController apicontroller = new APIController();
        List<RangeAndScoreCategory> ranges = apicontroller.getResearchLineRangesAndScoreCategoriesForUpdates(reserchLine);
        String rangeCategory = ranges.stream().filter(e -> e.getMax() >= score && e.getMin() <= score).findFirst().get().getCategory();
        String colorCode = "";
        switch (reserchLine) {
            case "Green Share":
                switch (rangeCategory) {
                    case "None":
                        colorCode = "#39a885";
                        break;
                    case "Minor":
                        colorCode = "#e19e7c";
                        break;
                    case "Significant":
                        colorCode = "#d7dee0";
                        break;
                    case "Major":
                        colorCode = "#39a885";
                        break;
                }
                break;
            case "Brown Share":
                switch (rangeCategory) {
                    case "0%":
                        colorCode = "#6fa0ad";
                        break;
                    case "0-20%":
                        colorCode = "#a1c8d3";
                        break;
                    case "20-100%":
                        colorCode = "#b28559";
                        break;

                }
                break;
        }
        return colorCode;
    }

    public boolean validateGreenCardValueBoxIsAvailable() {
        return GreenShareWidgetValue.isDisplayed();
    }

    public boolean validateBrownCardValueBoxIsAvailable() {
        return BrownShareWidgetValue.isDisplayed();
    }

    public void validateColorOfValueBoxAndPieChart(String researchLine, int score) {
        String expectedColorCode = "";
        String actualValueBoxcolor = "";
        String actualpieChartColor = "";
        String percentValue = "";
        System.out.println("reserchLine = " + researchLine);
        System.out.println("score = " + score);
        switch (researchLine) {
            case "Green Share":
                percentValue = Driver.getDriver().findElement(By.xpath("(//div[@id='chiclet-id'])[2]")).getText();
                expectedColorCode = getColorcode(researchLine, score);
                if (!percentValue.equalsIgnoreCase("0%")) {
                    actualValueBoxcolor = Color.fromString(GreenShareWidgetValue.getCssValue("background-color")).asHex();
                    actualpieChartColor = Color.fromString(pieCharts.get(0).getCssValue("fill")).asHex();
                    System.out.println("actualValueBoxcolor " + actualValueBoxcolor);
                    System.out.println("expectedColorCode = " + expectedColorCode);
                    Assert.assertTrue(actualValueBoxcolor.equals(expectedColorCode));
                    Assert.assertTrue(actualpieChartColor.equals(expectedColorCode));
                }
                break;
            case "Brown Share":
                expectedColorCode = getColorcode(researchLine, score);
                percentValue = Driver.getDriver().findElement(By.xpath("(//div[@id='chiclet-id'])[3]")).getText();
                if (!percentValue.equalsIgnoreCase("0%")) {
                    actualValueBoxcolor = Color.fromString(BrownShareWidgetValue.getCssValue("background-color")).asHex();
                    actualpieChartColor = Color.fromString(pieCharts.get(1).getCssValue("fill")).asHex();
                    System.out.println("actualValueBoxcolor " + actualValueBoxcolor);
                    System.out.println("expectedColorCode = " + expectedColorCode);
                    Assert.assertTrue(actualValueBoxcolor.equals(expectedColorCode));
                    Assert.assertTrue(actualpieChartColor.equals(expectedColorCode));
                }
                break;
        }
    }

    public boolean checkIfTempratureAlignmentWidgetISAvailable() {
        return temperatureAlignmentWidget.isDisplayed();
    }

    public boolean checkIfCarbonFootprintWidgetISAvailable() {
        BrowserUtils.scrollTo(carbonFootprintWidget);
        return carbonFootprintWidget.isDisplayed();
    }

    public boolean checkIfPhysicalClimateRiskChartAndtextsAREVisible() {
        BrowserUtils.scrollTo(mostRiskText);
        return physicalClimateRiskOperationsRiskText.isDisplayed() &&
                // redFlag.isDisplayed() &&
                //noRisk.isDisplayed() &&
                leastRiskText.isDisplayed() &&
                mostRiskText.isDisplayed()
                ;
    }

//    public boolean checkIfCarbonFootprintWidgetISAvailable(){
//        return carbonFootprintWidget.isDisplayed();
//    }

    public boolean isCompanyComparedWithItsSector(String nameOfTheCompany) {

        String xpath = "//div[contains(text(),'" + nameOfTheCompany + " compared')]";
        return BrowserUtils.isElementVisible(Driver.getDriver().findElement(By.xpath(xpath)), 3);
    }

    public boolean verifyCompanyDisplayedNextToSectorName(String nameOfTheCompany) {
        String xpath = "//p[text()='" + nameOfTheCompany + "']";
        return BrowserUtils.isElementVisible(Driver.getDriver().findElement(By.xpath(xpath)), 3);
    }

    public void navigateToTransitionRisk() {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        js.executeScript("arguments[0].click()", transitionRiskPage);
        //  BrowserUtils.waitForClickablility(transitionRiskPage, 30).click();
    }

    public void navigateToPhysicalRisk() {
        BrowserUtils.scrollTo(physicalRiskTab);
        BrowserUtils.waitForClickablility(physicalRiskTab, 10).click();
        BrowserUtils.waitForVisibility(comparisonChartRiskSelectionDropdown, 10);
        BrowserUtils.scrollTo(comparisonChartRiskSelectionDropdown);
    }

    public void validateTemperatureAligmentHeaderText() {
        Assert.assertTrue(temperatureAlignmentWidgetHeader.get(0).isDisplayed());
        Assert.assertTrue(Color.fromString(temperatureAlignmentWidgetHeader.get(0).getCssValue("color")).asHex().equals("#333333"));
        Assert.assertTrue(temperatureAlignmentWidgetHeader.get(0).getCssValue("font-size").equals("16px"));
        Assert.assertTrue(temperatureAlignmentWidgetHeader.get(0).getCssValue("font-family").equals("WhitneyNarrSemiBold, Roboto, Arial, sans-serif"));
        Assert.assertTrue(temperatureAlignmentWidgetHeader.get(0).getText().equals("Temperature Alignment"));
    }

    public void validateQualitativeScoreHeader() {
        Assert.assertTrue(temperatureAlignmentWidgetHeader.get(1).isDisplayed());
        WebElement QualitativeScoreInnerBox = temperatureAlignmentWidgetHeader.get(1).findElement(By.xpath("//div[@id='innerBox00']"));
        Assert.assertEquals(Color.fromString(QualitativeScoreInnerBox.getCssValue("background-color")).asHex(),
                getTemperatureAligmentColor(QualitativeScoreInnerBox.getText()).get("background"));
        Assert.assertEquals(Color.fromString(QualitativeScoreInnerBox.getCssValue("color")).asHex(),
                getTemperatureAligmentColor(QualitativeScoreInnerBox.getText()).get("text"));
        Assert.assertEquals(QualitativeScoreInnerBox.getCssValue("font-size"), ("12px"));
        Assert.assertEquals(QualitativeScoreInnerBox.getCssValue("font-family"), ("WhitneyNarrSemiBold, Roboto, Arial, sans-serif"));
    }

    public void validateTemperatureAlignmentImpliedTemperatureRise() {
        if (temperatureAlignmentDetailDivs.get(0).isDisplayed()) {
            Assert.assertTrue(temperatureAlignmentDetailDivs.get(0).getText().contains("Implied Temperature Rise:"));
            Assert.assertEquals(Color.fromString(temperatureAlignmentDetailDivs.get(0).getCssValue("color")).asHex(), ("#263238"));
            Assert.assertEquals(temperatureAlignmentDetailDivs.get(0).getCssValue("font-size"), ("12px"));
            Assert.assertEquals(temperatureAlignmentDetailDivs.get(0).getCssValue("font-family"), "WhitneyNarrMedium, Roboto, Arial, sans-serif");
            Assert.assertTrue(temperatureAlignmentDetailDivs.get(0).findElement(By.xpath("span")).getText().contains("°C"));
        }
    }

    public void validateTemperatureAlignmentEmissionsReductionTargetYear() {
        if (temperatureAlignmentDetailDivs.get(1).isDisplayed()) {
            Assert.assertTrue(temperatureAlignmentDetailDivs.get(1).getText().contains("Emissions Reduction Target Year:"));
            Assert.assertEquals(Color.fromString(temperatureAlignmentDetailDivs.get(1).getCssValue("color")).asHex(), "#263238");
            Assert.assertEquals(temperatureAlignmentDetailDivs.get(1).getCssValue("font-size"), "12px");
            Assert.assertEquals(temperatureAlignmentDetailDivs.get(1).getCssValue("font-family"),
                    "WhitneyNarrMedium, Roboto, Arial, sans-serif");
            Assert.assertTrue(validateIfNumeic(temperatureAlignmentDetailDivs.get(1).findElement(By.xpath("span")).getText()));
        }

    }

    public void validateTemperatureAlignmentTargetDescription() {
        if (temperatureAlignmentDetailDivs.get(2).isDisplayed()) {
            Assert.assertTrue(temperatureAlignmentDetailDivs.get(2).getText().contains("Target Description:"));
            Assert.assertEquals(Color.fromString(temperatureAlignmentDetailDivs.get(2).getCssValue("color")).asHex(), "#263238");
            Assert.assertEquals(temperatureAlignmentDetailDivs.get(2).getCssValue("font-size"), "12px");
            Assert.assertEquals(temperatureAlignmentDetailDivs.get(2).getCssValue("font-family"),
                    ("WhitneyNarrMedium, Roboto, Arial, sans-serif"));

        }
    }

    public void validateTemperatureAlignmentUpdatedOn() {
        if (temperatureAlignmentDetailDivs.get(2).isDisplayed()) {
            Assert.assertTrue(temperatureAlignmentDetailDivs.get(2).getText().contains("The company reports a target"));
            Assert.assertEquals(Color.fromString(temperatureAlignmentDetailDivs.get(2).getCssValue("color")).asHex(), "#263238");
            Assert.assertEquals(temperatureAlignmentDetailDivs.get(2).getCssValue("font-size"), "12px");
            Assert.assertEquals(temperatureAlignmentDetailDivs.get(2).getCssValue("font-family"),
                    ("WhitneyNarrMedium, Roboto, Arial, sans-serif"));

        }
    }

    public boolean validateIfNumeic(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }


    public Map<String, String> getTemperatureAligmentColor(String value) {
        Map<String, String> color = new HashMap<>();
        switch (value.toUpperCase()) {
            case "WELL BELOW 2°C":
                color.put("background", "#eac550");
                color.put("text", "#333333");
                break;
            case "BELOW 2°C":
                color.put("background", "#e8951c");
                color.put("text", "#ffffff");

                break;
            case "2°C":
                color.put("background", "#dd581d");
                color.put("text", "#ffffff");

                break;
            case "ABOVE 2°C":
                color.put("background", "#d63229");
                color.put("text", "#ffffff");

                break;
            case "NO INFO":
                color.put("background", "#dbe5a3");
                color.put("text", "#333333");

                break;
        }
        return color;
    }

    public boolean verifyPhysicalClimateHazardCardText() {

        return physicalClimateHazards.getText().contains("Physical Climate Hazards") &&
                physicalClimateHazards.getText().contains("Highest Risk Hazard:");
        // && physicalClimateHazards.getText().contains("% Facilities Exposed to");

    }

    public String getEntityOrbisIdFromUI() {
        clickGlobalHeader();
        String orbisId = orbisIdLabel.getText();
        sendESCkey();
        return orbisId;
    }

    public String getGreenShareScoreRange() {
        return greenShareScoreRangeLabel.getText().replace("%", "");
    }

    public boolean verifyScoreAndRange(String scoreRange, String score) {
        int iScore = Integer.parseInt(score);
        if (scoreRange.contains("-")) {
            String range[] = scoreRange.split("-");
            if (iScore >= Integer.parseInt(range[0].trim()) && iScore <= Integer.parseInt(range[1].trim())) {
                return true;
            } else {
                return false;
            }
        } else {
            return iScore == Integer.parseInt(scoreRange);
        }
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

    public boolean verifyOverallEsgScoreWidget() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(esgScoreWidget)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifySectorComparisonChart() {
        try {
            wait.until(ExpectedConditions.visibilityOf(sectorComparisonChart));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void selectTransitionRiskTab() {
        transitionRiskTab.click();
    }

    public void selectPhysicalRiskTab() {
        physicalRiskTab.click();
    }

    public void selectEsgMaterialityTab() {
        esgMaterialityTab.click();
    }

    public List<String> readEsgMaterialityCategories() {
        List<String> categories = new ArrayList<String>();
        String sectionName = "";
        for (WebElement section : esgSections) {
            sectionName = section.getText();
            if (!sectionName.equals("None for the sector"))
                categories.add(section.getText());
        }
        return categories;
    }

    public void validateSubCategoriesButtonProperties() {
        List<WebElement> SubCategoriesPercentageLabels = Driver.getDriver().findElements(By.xpath("//section//li/section/span[2]"));
        List<WebElement> SubCategoriesProgressBars = Driver.getDriver().findElements(By.xpath("//section//li/section/span/aside//span[@role='progressbar']"));
        assertTestCase.assertEquals(SubCategoriesPercentageLabels.size(), SubCategoriesProgressBars.size(), "Verify progress bars for all sub categories ");

        //Verify Disclosure ratio text under all subcategories
        for (WebElement section : SubCategoriesPercentageLabels) {
            assertTestCase.assertTrue(section.getText().startsWith("Disclosure Ratio "));
        }
    }

    public void validateSubCategories() {

        for (int i = 1; i <= esgMaterialityColumns.size(); i++) {
            String xpathCategories = "(//div/section//ul)[" + i + "]//li";
            int categoriesCount = Driver.getDriver().findElements(By.xpath(xpathCategories)).size();
            int scores[] = new int[categoriesCount];
            for (int j = 1; j <= categoriesCount; j++) {
                String categoryBgColor = Driver.getDriver().findElement(By.xpath("(//div/section//ul)[" + i + "]//li[" + j + "]")).getCssValue("background-color");
                System.out.println("BG Color: " + categoryBgColor);
                if (i != 4) {
                    String xpathCategoryScore = "(//div/section//ul)[" + i + "]//li[" + j + "]/section/span[1]/span[2]";
                    String score = Driver.getDriver().findElement(By.xpath(xpathCategoryScore)).getText();
                    int iScore = Integer.parseInt(score);
                    scores[i - 1] = iScore;
                    System.out.println("Score: " + iScore);
                    if (iScore >= 60) {
                        assertTestCase.assertEquals(categoryBgColor, "rgba(219, 229, 163, 1)");
                    } else if (iScore >= 50) {
                        assertTestCase.assertEquals(categoryBgColor, "rgba(234, 197, 80, 1)");
                    } else if (iScore >= 30) {
                        assertTestCase.assertEquals(categoryBgColor, "rgba(232, 149, 28, 1)");
                    } else {
                        assertTestCase.assertEquals(categoryBgColor, "rgba(221, 88, 29, 1)");
                    }
                } else {
                    assertTestCase.assertEquals(categoryBgColor, "rgba(255, 255, 255, 1)");
                }
            }
            for (int k = 0; k < (scores.length - 1); k++) {
                assertTestCase.assertTrue(scores[i] >= scores[i + 1], "Verify the order of categories");
            }
        }

    }

//    public void validateSubCategoriesButtonColorProperties() {
//        List<WebElement> SubCategoriesPercentageLabels = Driver.getDriver().findElements(By.xpath("//section//li/section/span[2]"));
//
//        //Verify Disclosure ratio text under all subcategories
//        int i=0;
//        for (WebElement section : materialityElements) {
//            String colorCode = Color.fromString(section.getCssValue("background-color")).asHex();
//            String score = Driver.getDriver().findElement(By.xpath("(//section//li/section/span[2])["+(++i)+"]")).getText();
//            score = score.substring(score.lastIndexOf(' '), score.length()-1);
//            if(Integer.parseInt(score)>=60){
//                assertTestCase.assertEquals(colorCode, "");
//            } else if(Integer.parseInt(score)>=40){
//                assertTestCase.assertEquals(colorCode, "");
//            } else if(Integer.parseInt(score)>=20){
//                assertTestCase.assertEquals(colorCode, "");
//            }
//        }
//    }

    public void validateNoneForTheSectorButton() {
        // Based on the xpath - can say element is not clickable
        List<WebElement> sectors = Driver.getDriver().findElements(By.xpath("//ul/li/section/span[text()='None for the sector']"));
        Assert.assertTrue(sectors.size() != 0, "Check 'None for the Sector' button");
    }

    public void validateEsgMaterialityLegends() {

        String labelXpath = "//button[@heap_perfchart_id='Materiality']/../../..//div[contains(@class,'MuiPaper-elevation1')]//span";

        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("(" + labelXpath + ")[1]")).getText(), "Weak");
        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("(" + labelXpath + "/div)[1]")).getAttribute("style"), "background: rgb(221, 88, 29);");

        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("(" + labelXpath + ")[2]")).getText(), "Limited");
        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("(" + labelXpath + "/div)[2]")).getAttribute("style"), "background: rgb(232, 149, 28);");

        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("(" + labelXpath + ")[3]")).getText(), "Robust");
        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("(" + labelXpath + "/div)[3]")).getAttribute("style"), "background: rgb(234, 197, 80);");

        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("(" + labelXpath + ")[4]")).getText(), "Advanced");
        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("(" + labelXpath + "/div)[4]")).getAttribute("style"), "background: rgb(219, 229, 163);");

        String criticalControversiesXpath = "//button[@heap_perfchart_id='Materiality']/../../..//div[text()='Critical controversies']";
        assertTestCase.assertTrue(Driver.getDriver().findElement(By.xpath(criticalControversiesXpath)).isDisplayed());

    }

    public boolean validateNoPopupIsDisplayedWhenClickedOnSubCategories() {
        Driver.getDriver().findElement(By.xpath("(//ul/li/section)[1]")).click();
        try {
            return !Driver.getDriver().findElement(By.xpath("//h2[@class='MuiTypography-root MuiTypography-h6']")).isDisplayed();
        } catch (Exception e) {
            return true;
        }
    }

    public boolean verifyMaterialityMatrixYaxisLabels() {
        boolean highScore = Driver.getDriver().findElement(By.xpath("//section/div[text()='Higher Score']")).isDisplayed();
        boolean lowScore = Driver.getDriver().findElement(By.xpath("//section/div[text()='Lower Score']")).isDisplayed();

        return highScore && lowScore;
    }

    public void verifyLowMaterialityMatrixColumnProperties() {
        for (WebElement element : lowMaterialityElements) {
            Assert.assertTrue(Color.fromString(element.getCssValue("background-color")).asHex().equals("#ffffff"));
        }
    }

    public List<String> readEsgMaterialityColumns() {
        List<String> columns = new ArrayList<String>();
        for (WebElement column : esgMaterialityColumns) {
            columns.add(column.getText());
        }
        return columns;
    }

    public void selectMaterialityMatrixFilter(String filterName) {
        WebElement element = Driver.getDriver().findElement(By.xpath("//div[@id='driverFilter']//a[text()='" + filterName + "']"));
        wait.until(ExpectedConditions.visibilityOf(element));
        BrowserUtils.scrollTo(element);
        element.click();
        BrowserUtils.wait(3);
        String classAttribute = element.getAttribute("class");
        Assert.assertTrue(classAttribute.split("jss").length == 4, "Verify selected filter is highlites");
    }

    public boolean verifyCategoryControversies(Map<String, Object> dbRecordCategory, List<Map<String, Object>> dbRecordControversies) {
        String categoryNameDB = dbRecordCategory.get("criteria_name").toString().trim();
        WebElement categoryEle = Driver.getDriver().findElement(By.xpath("//section//li//span[contains(text(),'" + categoryNameDB + "')]"));
        System.out.println("categoryEle = " + categoryEle);
        System.out.println("categoryEle = " + categoryEle.getText());
        BrowserUtils.scrollTo(categoryEle);
        BrowserUtils.clickWithJS(categoryEle);
        BrowserUtils.wait(3);
        String popupTitle = BrowserUtils.waitForVisibility(materialityPopupTitle, 30).getText().trim();
        assertTestCase.assertEquals(categoryNameDB, popupTitle, "Verify Category popup is opened");

        String relatedControversiesXpath = "//h3[text()='Related Controversies']/../section";
        List<WebElement> controversies = Driver.getDriver().findElements(By.xpath(relatedControversiesXpath));
        if (dbRecordControversies.size() == 0) {
            BrowserUtils.waitForVisibility(Driver.getDriver().findElement(By.id("controversyError")), 30);
        } else {
            assertTestCase.assertEquals(controversies.size(), dbRecordControversies.size(), dbRecordCategory.get("criteria_name") + ": Number of controversies are not matching");
        }
        for (int i = 0; i < dbRecordControversies.size(); i++) {
            String controversySeverityUI = "";
            String controversyNameUI = "";
            String controversyDateUI = "";
            if (dbRecordControversies.get(i).get("SEVERITY").toString().equals("Critical")) {
                controversySeverityUI = Driver.getDriver().findElement(By.xpath(relatedControversiesXpath + "[" + (i + 1) + "]/span[1]")).getText();
                controversyNameUI = Driver.getDriver().findElement(By.xpath(relatedControversiesXpath + "[" + (i + 1) + "]/span[2]")).getText();
                controversyDateUI = Driver.getDriver().findElement(By.xpath(relatedControversiesXpath + "[" + (i + 1) + "]/span[3]")).getText();
                assertTestCase.assertEquals(controversySeverityUI, dbRecordControversies.get(i).get("SEVERITY").toString(), "Controversy Record " + i + ": Severity Verification");
            } else {
                controversyNameUI = Driver.getDriver().findElement(By.xpath(relatedControversiesXpath + "[" + (i + 1) + "]/span[1]")).getText();
                controversyDateUI = Driver.getDriver().findElement(By.xpath(relatedControversiesXpath + "[" + (i + 1) + "]/span[2]")).getText();
            }

            assertTestCase.assertEquals(controversyNameUI, dbRecordControversies.get(i).get("CONTROVERSY_TITLE").toString(), "Controversy Record " + i + ": Controversy Title Verification");

            SimpleDateFormat month_date = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateDB = dbRecordControversies.get(i).get("CONTROVERSY_EVENTS").toString();
            String month_name = "";
            try {
                System.out.println("dateDB = " + dateDB);
                Date date = sdf.parse(dateDB);
                month_name = month_date.format(date);
            } catch (Exception e) {
            }

            assertTestCase.assertEquals(controversyDateUI, month_name, "Controversy Record " + i + ": Controversy Date Verification");

            if (dbRecordControversies.size() != 0) {
                controversies.get(i).click();
                BrowserUtils.wait(3);
                WebElement controveryPopup = Driver.getDriver().findElement(By.xpath("//div[text()='Controversies']/following-sibling::div[text()=\"" + controversyNameUI + "\"]"));
                BrowserUtils.waitForVisibility(controveryPopup, 30);
                Driver.getDriver().findElement(By.xpath("//span[@class='close']")).click();
                BrowserUtils.waitForInvisibility(controveryPopup, 30);
                BrowserUtils.wait(3);
            }
        }
        JavascriptExecutor executor = (JavascriptExecutor) Driver.getDriver();
        WebElement element = Driver.getDriver().findElement(By.xpath("(//div[@role]/div[@aria-hidden='true'])[2]"));
        executor.executeScript("arguments[0].click();", element);
        BrowserUtils.waitForInvisibility(materialityPopupTitle, 30);
        return true;
    }

    public boolean verifyPhysicalRiskItems() {
        BrowserUtils.wait(5);
        try {
            BrowserUtils.scrollTo(physicalRiskTab);
            System.out.println("====================");
            //System.out.println(physicalClimateHazards.getText());
            return physicalRiskTab.isDisplayed() && operationsRiskLabel.isDisplayed()
                    && marketRiskLabel.isDisplayed() && supplyChainRiskLabel.isDisplayed()
                    && physicalClimateRiskOperationsRiskLabel.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyTransitionRiskItems() {
        try {
            BrowserUtils.clickWithJS(transitionRiskTab);
            System.out.println("====================");
            return transitionRiskTab.isDisplayed() && transitionRiskTemperatureAlignmentWidget.isDisplayed()
                    && transitionRiskCarbonFootprintWidget.isDisplayed()
                    && transitionRiskBrownShareWidget.isDisplayed()
                    && transitionRiskGreenShareWidget.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean clickAndVerifyMethodologyLink(String researchLine) {
        String METHODOLOGY_PHYSICAL_CLIMATE_RISK_JAN_2022 = "Methodology_CorporatePhysicalClimateRiskOperationsRisk(Jan2022)";
        String TRANSITION_RISK_TEMPERATURE_ALIGNMENT = "Methodology_Climate_TemperatureAlignmentData";
        String TRANSITION_RISK = "Methodology_Climate_ClimateRiskAssessment";
        String DEFAULT = "Methodology_VEC_Climate_v4";
        try {
            WebElement methodology;
            if (researchLine.equals("Physical Risk"))
                methodology = Driver.getDriver().findElement(By.xpath("//span[text()='" + researchLine + "']/../../../..//a[contains(@id,'link-test-id')]"));
            else
                methodology = Driver.getDriver().findElement(By.xpath("//span[contains(text(),'Risk')]/../../..//div[text()='" + researchLine + "']/../../../..//a[contains(@id,'link-test-id')]"));

            wait.until(ExpectedConditions.visibilityOf(methodology)).click();
            //Switch to the new tab
            Set<String> handles = Driver.getDriver().getWindowHandles();
            if (handles.size() == 2) {
                String currentWindowHandle = Driver.getDriver().getWindowHandle();
                for (String handle : handles) {
                    if (!handle.equals(currentWindowHandle)) {
                        Driver.getDriver().switchTo().window(handle);
                        String url = Driver.getDriver().getCurrentUrl();
                        url = url.replaceAll("%20", "");
                        Driver.getDriver().close();
                        Driver.getDriver().switchTo().window(currentWindowHandle);
                        switch (researchLine) {
                            case "Physical Risk":
                                return url.contains(METHODOLOGY_PHYSICAL_CLIMATE_RISK_JAN_2022);
                            case "Temperature Alignment":
                                return url.contains(TRANSITION_RISK_TEMPERATURE_ALIGNMENT);
                            case "Carbon Footprint":
                            case "Brown Share Assessment":
                            case "Green Share Assessment":
                                return url.contains(TRANSITION_RISK);
                            default:
                                return url.contains(DEFAULT);
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

    public boolean verifyIndustrySector(String entityName) {
        List<String> sectors = new ArrayList<String>(Arrays.asList("Airlines", "Aluminum", "Automobiles", "Cement", "Electric and Gas", "Oil & Gas", "Shipping", "Steel", "ARA - all sectors"));
        List<String> units = new ArrayList<>(Arrays.asList("gCO2/RTK", "tCO2e/t", "gCO2/vkm", "tCO2/t", "gCO2/kWh", "tCO2e/TJ", "gCO2/t-km", "tCO2/t", "tCO2"));
        Map<String, String> sectorMap = new HashMap<>();
        for (int i = 0; i < sectors.size(); i++) {
            sectorMap.put(sectors.get(i), units.get(i));
        }

        DatabaseDriver.createDBConnection();

        String query = "select * from (select distinct(COMPANY_NAME),BENCHMARK_SECTOR from DF_PORTFOLIO_1 p left join TEMPERATURE_ALIGNMENT_PROJECTIONS t on  p.BVD9_NUMBER=t.BVD9_NUMBER) where COMPANY_NAME='" + entityName + "'";
        Map<String, Object> result = DatabaseDriver.getRowMap(query);
        System.out.println("result = " + result);
        String industryMetric = this.industryMetric.getText();
        industryMetric = industryMetric.substring(industryMetric.indexOf("(") + 1, industryMetric.indexOf(")"));
        return industryMetric.equals(sectorMap.get(result.get("BENCHMARK_SECTOR")));

    }

    public boolean verifyTemperatureAlignmentGraphFooter() {
        String expectedFooter = "This extrapolation is a theoretical decarbonization trend based on the company’s targeted reduction between its stated base year and the target year. This trend, therefore, indicates the emissions reduction that the company aims to achieve and can be used to assess the ambitiousness of a company's target in comparison to the benchmark trajectories shown.";
        return temperatureAlignmentGraphFooter.getText().equals(expectedFooter);
    }

    public boolean verifyTemperatureAlignmentGraphLegends() {

        for (WebElement text : widgetTexts) {
            if (text.getText().startsWith("\\d.\\d")) {
                System.out.println("Checking for legend text: " + text.getText());
                String front = text.getText().substring(0, text.getText().indexOf(" - "));
                String back = text.getText().substring(text.getText().indexOf(" - ") + 3);
                if (!front.matches("\\d.\\d°C IEA Scenario")) {
                    System.out.println("Lenegd text does not match: " + text.getText());
                    return false;
                }
                if (!sectorsList.contains(back)) {
                    System.out.println("Sector not found in list: " + back);
                    System.out.println("Expected sectors: " + sectorsList);
                    return false;
                }
            }
        }
        return true;
    }

    public boolean verifyLineDots(List<WebElement> lineName) {
        for (WebElement dot : lineName) {
            BrowserUtils.hover(temperatureAlignmentGraphTitle);
            BrowserUtils.hover(dot);
            WebElement tooltip = Driver.getDriver().findElement(By.xpath("(//*[@visibility='visible'])"));
            System.out.println("tooltip.getText() = " + tooltip.getText());
            BrowserUtils.waitForVisibility(tooltip, 5);
            if (!tooltip.isDisplayed()) return false;


        }
        return true;
    }

    public boolean verifyLineDots(String lineName) {
        List<WebElement> targetLine;
        switch (lineName) {
            case "historical":
                targetLine = historicalLineDots;
                break;
            case "Benchmark 1.5C":
                targetLine = benchmark15CLineDots;
                break;
            case "Benchmark 1.65C":
                targetLine = benchmark165CLineDots;
                break;
            case "Benchmark 2.7C":
                targetLine = benchmark27CLineDots;
                break;
            default: {
                System.out.println("Correct line name is not provided. Please enter one of the names below:" +
                        "historical" +
                        "Benchmark 1.5C" +
                        "Benchmark 1.65C" +
                        "Benchmark 2.7C");
                targetLine = historicalLineDots;
            }
        }
        return verifyLineDots(targetLine);
    }

    public boolean verifyCarbonFootprintWidgetItems() {
        if (transitionRiskCarbonFootprintWidgetItems.isEmpty()) {
            System.out.println("Transition Risk Carbon Footprint widget items are not displayed");
            return false;
        }
        List<WebElement> items = transitionRiskCarbonFootprintWidgetItems;
        boolean check = true;
        for (int i = 0; i < items.size(); i++) {
            switch (i) {
                case 0:
                    check = check && items.get(i).getText().startsWith("Carbon Footprint");
                    break;
                case 1:
                    check = check && items.get(i).getText().startsWith("Scope 1 (t CO2 eq)");
                    break;
                case 2:
                    check = check && items.get(i).getText().startsWith("Scope 2 (t CO2 eq)");
                    break;
                case 3:
                    check = check && items.get(i).getText().startsWith("Scope 3 (t CO2 eq)");
                    break;
                case 4:
                    check = check && items.get(i).getText().startsWith("Source");
                    break;
                case 5:
                    check = check && items.get(i).getText().startsWith("3rd Party Verification");
                    break;
                case 6:
                    check = check && items.get(i).getText().startsWith("Carbon Footprint Reporting Year");
                    break;
                case 7:
                    check = check && items.get(i).getText().startsWith("Estimated");
                    break;
                case 8:
                    check = check && items.get(i).getText().startsWith("Carbon Intensity (tCO2eq/M revenue)");
                    break;
            }
            if (!check) {
                System.out.println("Widget Item " + i + " is not verified " + items.get(i).getText());
            }

        }

        //verify carbon footprint category color is true
        String actColor = Color.fromString(transitionRiskCarbonFootprintCategory.getCssValue("background-color")).asHex();
        System.out.println("TR - Carbon Footprint - actColor = " + actColor);
        String expColor = new ResearchLineColors().getColorForScoreCategory(transitionRiskCarbonFootprintCategory.getText());
        System.out.println("TR - Carbon Footprint - expColor = " + expColor);
        check = check && actColor.equalsIgnoreCase(expColor);
        if (!check) System.out.println("TR - Carbon Footprint - Category color is not verified");
        return check;
    }

    public boolean verifyCarbonFootprintWidgetValue(String entityID) {
        if (transitionRiskCarbonFootprintWidgetValues.isEmpty()) {
            System.out.println("Transition Risk Carbon Footprint widget items are not displayed");
            return false;
        }
        List<WebElement> items = transitionRiskCarbonFootprintWidgetValues;
        boolean check = true;
        DatabaseDriver.createDBConnection();

        String query = "SELECT TOP 1 * FROM CARBON_FOOTPRINT WHERE BVD9_NUMBER=" + entityID + " ORDER BY YEAR DESC, MONTH DESC";
        Map<String, Object> result = DatabaseDriver.getRowMap(query);
        List<String> values = new ArrayList<>();
        for (Object value : result.values()) {
            if (value == null) continue;
            if (value.toString().equals("-1")) values.add("No Info");
            else values.add(value.toString().replace(",", ""));
        }
        System.out.println("values = " + values);
        for (int i = 0; i < items.size(); i++) {
            System.out.println("items = " + items.get(i).getText().replaceAll(",", ""));
            switch (i) {
                case 9:
                case 1:
                    break;
                default:
                    check = check && values.contains(items.get(i).getText().replaceAll(",", ""));
            }
            if (!check) {
                System.out.println("Widget Item " + i + " is not verified " + items.get(i).getText());
            }

        }
        //verify if carbon footprint category displayed
        check = check && transitionRiskCarbonFootprintCategory.isDisplayed();
        //verify if carbon footprint category true
        check = check && transitionRiskCarbonFootprintCategory.getText().equalsIgnoreCase(ESGUtilities.getCarbonFootprintCategory((Long) result.get("CARBON_FOOTPRINT_VALUE_TOTAL")));
        return check;

    }

    public boolean verifyTempratureAlignmentForNoInfoValue(String entityID) {


        DatabaseDriver.createDBConnection();
        System.setProperty("env", ConfigurationReader.getProperty("environment"));
        String env = System.getProperty("env");
        String query = "select BVD9_NUMBER,SCORE_CATEGORY,TEMPERATURE_SCORE as Implied_Temperature_Rise,TARGET_YEAR as Emissions_Reduction_Target_Year,\n" +
                "TARGET_DESCRIPTION,AS_OF_DATE as updated_Date from \"" + env.toUpperCase() + "_MESGC\".\"DF_TARGET\".\"TEMPERATURE_ALIGNMENT\" where BVD9_NUMBER in (select BVD9_NUMBER from \"DF_TARGET\".\"ENTITY_IDENTIFIERS_DETAIL\"\n" +
                "where BVD9_NUMBER = " + entityID + ") order by updated_Date desc limit 1";
        if (temperatureAlignmentDetailDivs.get(0).getText().equals("No information available.")) {
            System.out.println("Widget is not displayed");
            try {
                Map<String, Object> noResult = DatabaseDriver.getRowMap(query);
            } catch (Exception e) {
                System.out.println("No result is returned");
                return true;
            }
            return false;
        }
        Map<String, Object> result = DatabaseDriver.getRowMap(query);
        System.out.println("result = " + result);
        if (result.isEmpty()) {
            System.out.println("No data found for entityID = " + entityID);
            return true;
        }
        boolean check = true;
        if (result.get("IMPLIED_TEMPERATURE_RISE").toString().equals("-1") || result.get("IMPLIED_TEMPERATURE_RISE").toString().equals("No Info")) {
            System.out.println("Implied Temperature Rise with No Info or with value -1 is found!");
            check = check && temperatureAlignmentDetailDivs.get(0).getText().endsWith("No Info");
        }
        if (result.get("EMISSIONS_REDUCTION_TARGET_YEAR").toString().equals("-1") || result.get("EMISSIONS_REDUCTION_TARGET_YEAR").toString().equals("No Info")) {
            System.out.println("EMISSIONS_REDUCTION_TARGET_YEAR with No Info or with value -1 is found!");
            check = check && temperatureAlignmentDetailDivs.get(1).getText().endsWith("No Info");
        }

        return check;

    }

    public boolean selectComparisonChartRiskType(String riskName) {
        System.out.println("Selecting " + riskName + " from Comparison Chart Risk Type");
        BrowserUtils.waitForClickablility(comparisonChartRiskSelectionDropdown, 5).click();
        BrowserUtils.waitForVisibility(comparisonChartRiskSelectionOptions.get(0), 5);
        for (WebElement risk : comparisonChartRiskSelectionOptions) {
            if (risk.getText().toLowerCase().endsWith(riskName.toLowerCase())) {
                System.out.println("Selected " + riskName + " from Comparison Chart Risk Type");
                BrowserUtils.clickWithJS(risk);
                return true;
            }
        }
        System.out.println("Please enter correct risk name. Available options are: " +
                comparisonChartRiskSelectionOptions.stream().map(WebElement::getText).collect(Collectors.toList()));
        return false;
    }

    public boolean verifyComparisonChartRiskTypes() {
        BrowserUtils.waitForClickablility(comparisonChartRiskSelectionDropdown, 5).click();
        System.out.println("Dropdown is clicked");
        List<String> researchLines = new ArrayList<>(Arrays.asList("Physical Climate Risk: Operations Risk",
                "Physical Climate Risk: Market Risk",
                "Physical Climate Risk: Supply Chain Risk",
                "Physical Risk Management"));
        if (comparisonChartRiskSelectionOptions.size() != researchLines.size()) return false;
        for (WebElement risk : comparisonChartRiskSelectionOptions) {
            if (!researchLines.contains(risk.getText())) {
                System.out.println("This research line is not verified: " + risk.getText());
                return false;
            }
        }
        comparisonChartRiskSelectionOptions.get(0).click();
        return true;
    }

    public Map<String, String> getEntityInfo(String entityID) {
        Map<String, String> entityInfo = new HashMap<>();
        DatabaseDriver.createDBConnection();
        String query = "SELECT TOP 1 * FROM ENTITY_IDENTIFIERS_DETAIL WHERE BVD9_NUMBER=" + entityID;
        Map<String, Object> result = DatabaseDriver.getRowMap(query);
        for (String key : result.keySet()) {
            if (result.get(key) == null) continue;
            if (result.get(key).toString().equals("-1")) entityInfo.put(key, "No Info");
            else entityInfo.put(key, result.get(key).toString());
        }
        return entityInfo;
    }

    public boolean verifyMarketRiskComparisonChart(String entityID, String riskName) {

        if (!comparisonChartRiskSelectionDropdown.isDisplayed()) {
            System.out.println(riskName + " Comparison Chart is not displayed");
            return false;
        }
        if (!comparisonChartRiskSelectionDropdown.getText().endsWith(riskName)) {
            System.out.println("Please make sure Market Risk is selected");
            return false;
        }
        System.out.println("comparisonChartHeader = " + comparisonChartHeader.getText());
        Map<String, String> companyInfo = getEntityInfo(entityID);
        System.out.println("companyInfo = " + companyInfo);
        String sentence = companyInfo.get("COMPANY_NAME") + " compared to ";
        if (!comparisonChartHeader.getText().contains(sentence)) {
            System.out.println("Comparison Chart header is not in correct format");
            return false;
        }
        if (comparisonChartHeader.getText().replaceAll("\\D", "").length() < 1) {
            System.out.println("Comparison Chart header doesn't contain number of companies");
            return false;
        }
        if (!comparisonChartHeader.getText().contains("companies in")) {
            System.out.println("Comparison Chart header doesn't contain \"companies\" after number");
            return false;
        }
       /* if (!comparisonChartHeader.getText().contains(companyInfo.get("SECTOR"))) {
            System.out.println("Comparison Chart header doesn't contain correct sector");
            //return false;
        }*/
        if (!comparisonChartLegends.get(1).getText().contains(companyInfo.get("SECTOR"))) {
            System.out.println("Comparison Chart legend doesn't contain correct sector");
            //return false;
        }
        if (!comparisonChartLegends.get(3).getText().contains(companyInfo.get("COMPANY_NAME"))) {
            System.out.println("Comparison Chart legend doesn't contain correct company name");
            return false;
        }
        String color = Color.fromString(comparisonChartLegends.get(0).getCssValue("background-color")).asHex();
        /*  System.out.println("First color " + color);
        if (!color.toUpperCase().equalsIgnoreCase("#8da3b7")) {
            System.out.println("First Comparison Chart legend box doesn't have correct color");
            return false;
        }*/
        color = Color.fromString(comparisonChartLegends.get(2).getCssValue("background-color")).asHex();
        System.out.println("Color " + color);
        if (!color.toLowerCase().equalsIgnoreCase("#1f8cff")) {
            System.out.println("Second Comparison Chart legend box doesn't have correct color");
            return false;
        }
        return true;
    }

    public boolean verifyComparisonChartContent(String entityID, String riskName) {
        if (!comparisonChartRiskSelectionDropdown.isDisplayed()) {
            System.out.println(riskName + " Comparison Chart is not displayed");
            return false;
        }
        if (!comparisonChartRiskSelectionDropdown.getText().endsWith(riskName)) {
            System.out.println("Please make sure" + riskName + "is selected");
            return false;
        }
        BrowserUtils.scrollTo(comparisonChartRiskSelectionDropdown);
        //The bar chart should display the underlying raw score (0-100) for relative comparison of companies across the sector.
        if (!Driver.getDriver().findElement(By.xpath("//div[.='0']")).isDisplayed()) {
            System.out.println("Bar chart doesn't display 0 indicator");
            return false;
        }
        if (!Driver.getDriver().findElement(By.xpath("//div[.='100']")).isDisplayed()) {
            System.out.println("Bar chart doesn't display 100 indicator");
            return false;
        }
        //Greyline represents the benchmark line =avg. Market risk score of all entities (in our coverage) within a given sector
        //The line must always appear on top and must be visible.
        if (!Driver.getDriver().findElement(By.xpath("(//*[name()='path'][@class='highcharts-plot-line '])[1]")).isDisplayed()) {
            System.out.println("Average line doesn't display");
            return false;
        }
        //The blue bar represents the entity the user is looking at
        WebElement blueBar = Driver.getDriver().findElement(By.xpath("//*[@fill='#1F8CFF']"));
        BrowserUtils.waitForVisibility(blueBar, 50);
        if (!blueBar.isDisplayed()) {
            System.out.println("Blue bar doesn't display");
            return false;
        }
//        On the hover of each bar - a tooltip should appear with the following data points
//        Company name
//        Market risk score
        System.out.println("Hovering over bars...");
        BrowserUtils.hover(comparisonChartBars.get(50));
        WebElement tooltip = Driver.getDriver().findElement(By.xpath("//*[local-name()='g' and @visibility='visible']"));
        BrowserUtils.waitForVisibility(tooltip, 5);
        String tooltipText = tooltip.getText();
        System.out.println("Left-most bar's tooltipText = " + tooltipText);
        if (!tooltipText.contains("Company Name")) {
            System.out.println("Left-most bar's tooltip doesn't contain \"Company Name\"");
            return false;
        }

        if (riskName.equals("Market Risk")) {
            if (!tooltipText.contains("Market risk score")) {
                System.out.println("Left-most bar's tooltip doesn't contain \"Market risk score\"");
                return false;
            }
        }
        if (riskName.equals("Supply Chain Risk")) {
            if (!tooltipText.contains("Supplychain score")) {
                System.out.println("Left-most bar's tooltip doesn't contain \"Supplychain score\"");
                return false;
            }
        }
        BrowserUtils.hover(comparisonChartBars.get(comparisonChartBars.size() - 1));
        BrowserUtils.wait(1);
        tooltip = Driver.getDriver().findElement(By.xpath("//*[local-name()='g' and @visibility='visible']"));
        BrowserUtils.waitForVisibility(tooltip, 5);
        String tooltipText2 = tooltip.getText();
        System.out.println("tooltipText = " + tooltipText2);
        if (!tooltipText.contains("Company Name")) {
            System.out.println("Right-most bar's tooltip doesn't contain \"Company Name\"");
            return false;
        }
        if (riskName.equals("Market Risk")) {
            if (!tooltipText.contains("Market risk score")) {
                System.out.println("Left-most bar's tooltip doesn't contain \"Market risk score\"");
                return false;
            }
        }
        if (riskName.equals("Supply Chain Risk")) {
            if (!tooltipText.contains("Supplychain score")) {
                System.out.println("Left-most bar's tooltip doesn't contain \"Supplychain score\"");
                return false;
            }
        }
        //The X-axis represents companies in the sector represented as least risk > most risk (left to right)
        //This will fail if a company has number in it's name
        int firstScore = Integer.parseInt(tooltipText.replaceAll("\\D", ""));
        int lastScore = Integer.parseInt(tooltipText2.replaceAll("\\D", ""));
        System.out.println("firstScore = " + firstScore);
        System.out.println("lastScore = " + lastScore);
        if (firstScore >= lastScore) {
            System.out.println("X-axis doesn't display in correct order");
            return false;
        }
        return true;
    }

    public boolean verifyComparisonChartBlueBar(String entityID) {

        if (!comparisonChartRiskSelectionDropdown.isDisplayed()) {
            System.out.println("Market Risk Comparison Chart is not displayed");
            return false;
        }
        if (!comparisonChartRiskSelectionDropdown.getText().endsWith("Market Risk")) {
            System.out.println("Please make sure Market Risk is selected");
            return false;
        }
        //hover over the blue bar and check the data displayed
        BrowserUtils.scrollTo(comparisonChartHeader);
        WebElement blueBar = Driver.getDriver().findElement(By.xpath("(//*[name()='rect'][@class='highcharts-point highcharts-color-87'])"));
        System.out.println("blueBar.isDisplayed() = " + blueBar.isDisplayed());
        BrowserUtils.hover(blueBar);
        System.out.println("Hover over is done");
        WebElement tooltip = Driver.getDriver().findElement(By.xpath("//*[local-name()='g' and @visibility='visible']"));
        BrowserUtils.wait(1);
        System.out.println("tooltip.isDisplayed() = " + tooltip.isDisplayed());
        String tooltipText = tooltip.getText().replaceAll("\\D", "");
        System.out.println("tooltipText = " + tooltipText);

        String query = "select top 1 * from \"DF_TARGET\".\"ENTITY_SCORE\" where ENTITY_ID_BVD9='" + entityID + "' order by release_date desc;";
        DatabaseDriver.createDBConnection();
        Map<String, Object> result = DatabaseDriver.getRowMap(query);
        System.out.println("result = " + result);

        String dbScore = result.get("MARKET_RISK_SCORE").toString();
        dbScore = dbScore.substring(0, dbScore.indexOf("."));
        System.out.println("dbScore = " + dbScore);
        int dbScoreInt = Integer.parseInt(dbScore);
        System.out.println("dbScoreInt = " + dbScoreInt);
        int tooltipScore = Integer.parseInt(tooltipText);
        System.out.println("tooltipScore = " + tooltipScore);
        if (!(dbScoreInt <= tooltipScore)) {
            System.out.println("Blue bar's tooltip doesn't contain the correct score");
            return false;
        }
//        if(!tooltipText.equals(dbScore)){
//            System.out.println("dbScore = " + dbScore);
//            System.out.println("tooltipText = " + tooltipText);
//            System.out.println("Tooltip text doesn't have correct score");
//            return false;
//        }
        return true;
    }

    public boolean verifyComparisonChartNthBar(String entityID, int barNumber) {

        if (!comparisonChartRiskSelectionDropdown.isDisplayed()) {
            System.out.println("Market Risk Comparison Chart is not displayed");
            return false;
        }
        if (!comparisonChartRiskSelectionDropdown.getText().endsWith("Market Risk")) {
            System.out.println("Please make sure Market Risk is selected");
            return false;
        }
        //hover over the blue bar and check the data displayed
        BrowserUtils.scrollTo(comparisonChartHeader);
        System.out.println(barNumber + "th bar.isDisplayed() = " + comparisonChartBars.get(barNumber - 1).isDisplayed());
        BrowserUtils.hover(comparisonChartBars.get(barNumber - 1));
        BrowserUtils.wait(1);
        WebElement tooltip = Driver.getDriver().findElement(By.xpath("//*[local-name()='g' and @visibility='visible']"));
        System.out.println("tooltip.isDisplayed() = " + tooltip.isDisplayed());
        String tooltipText = tooltip.getText().replaceAll("\\D", "");
        System.out.println("tooltipText = " + tooltipText);

        String query = "SELECT MST.ORBIS_ID AS ORBIS_ID, MST.MESG_SECTOR AS MESG_SECTOR, MST.ENTITY_PROPER_NAME, MARKET_RISK_SCORE, avg(MARKET_RISK_SCORE) over() as SECTOR_AVG_SCORE, physical_risk_score_category(MARKET_RISK_SCORE) as ENTITY_SCORE_CATEGORY FROM (SELECT A.ORBIS_ID,A.ENTITY_PROPER_NAME,A.WORLD_REGION,B.MESG_SECTOR_ID, B.MESG_SECTOR ,B.L1_SECTOR, B.L1_SECTOR_ID FROM ESG_ENTITY_MASTER A, (SELECT MESG_SECTOR_ID, MESG_SECTOR ,L1_SECTOR, L1_SECTOR_ID FROM SECTOR_HIERARCHY QUALIFY ROW_NUMBER() OVER (PARTITION BY MESG_SECTOR_ID ORDER BY AS_OF_DATE)=1 ) B WHERE ENTITY_STATUS='Active' and A.MESG_SECTOR_ID=B.MESG_SECTOR_ID QUALIFY ROW_NUMBER() OVER (PARTITION BY ORBIS_ID ORDER BY UPDATED_DATE DESC)=1) EMS JOIN (SELECT A.ORBIS_ID,A.ENTITY_PROPER_NAME,B.MESG_SECTOR_ID, B.MESG_SECTOR ,B.L1_SECTOR, B.L1_SECTOR_ID FROM ESG_ENTITY_MASTER A, (SELECT MESG_SECTOR_ID, MESG_SECTOR ,L1_SECTOR, L1_SECTOR_ID FROM SECTOR_HIERARCHY QUALIFY ROW_NUMBER() OVER (PARTITION BY MESG_SECTOR_ID ORDER BY AS_OF_DATE)=1 ) B WHERE ENTITY_STATUS='Active' and A.MESG_SECTOR_ID=B.MESG_SECTOR_ID QUALIFY ROW_NUMBER() OVER (PARTITION BY ORBIS_ID ORDER BY UPDATED_DATE DESC)=1) MST ON EMS.MESG_SECTOR_ID = MST.MESG_SECTOR_ID JOIN ENTITY_SCORE ES ON ES.ENTITY_ID_BVD9=MST.ORBIS_ID AND RELEASE_YEAR||RELEASE_MONTH=(select max(RELEASE_YEAR||RELEASE_MONTH) from ENTITY_SCORE) WHERE EMS.ORBIS_ID = '" + entityID + "' AND EMS.World_REGION = 'AMER' AND ES.MARKET_RISK_SCORE IS NOT NULL AND ES.MARKET_RISK_SCORE>=0 ORDER BY MARKET_RISK_SCORE";
        DatabaseDriver.createDBConnection();
        List<Map<String, Object>> result = DatabaseDriver.getQueryResultMap(query);
        System.out.println("result.size() = " + result.size());
        System.out.println("result = " + result.get(barNumber - 1));

        String dbScore = result.get(barNumber - 1).get("MARKET_RISK_SCORE").toString();
        dbScore = dbScore.substring(0, dbScore.indexOf("."));
        System.out.println("dbScore = " + dbScore);

        if (!tooltipText.equals(dbScore)) {
            System.out.println("dbScore = " + dbScore);
            System.out.println("tooltipText = " + tooltipText);
            System.out.println("Tooltip text doesn't have correct score");
            return false;
        }

        return true;
    }

    public boolean verifyComparisonChartForMarketRiskAverage(String entityID) {
        boolean check = true;
        if (!comparisonChartRiskSelectionDropdown.isDisplayed()) {
            System.out.println("Market Risk Comparison Chart is not displayed");
            return false;
        }
        if (!comparisonChartRiskSelectionDropdown.getText().endsWith("Market Risk")) {
            System.out.println("Please make sure Market Risk is selected");
            return false;
        }
        //hover over the blue bar and check the data displayed
        BrowserUtils.scrollTo(comparisonChartHeader);

        String query = "SELECT MST.ORBIS_ID AS ORBIS_ID, MST.MESG_SECTOR AS MESG_SECTOR, MST.ENTITY_PROPER_NAME, MARKET_RISK_SCORE, avg(MARKET_RISK_SCORE) over() as SECTOR_AVG_SCORE, physical_risk_score_category(MARKET_RISK_SCORE) as ENTITY_SCORE_CATEGORY FROM (SELECT A.ORBIS_ID,A.ENTITY_PROPER_NAME,A.WORLD_REGION,B.MESG_SECTOR_ID, B.MESG_SECTOR ,B.L1_SECTOR, B.L1_SECTOR_ID FROM ESG_ENTITY_MASTER A, (SELECT MESG_SECTOR_ID, MESG_SECTOR ,L1_SECTOR, L1_SECTOR_ID FROM SECTOR_HIERARCHY QUALIFY ROW_NUMBER() OVER (PARTITION BY MESG_SECTOR_ID ORDER BY AS_OF_DATE)=1 ) B WHERE ENTITY_STATUS='Active' and A.MESG_SECTOR_ID=B.MESG_SECTOR_ID QUALIFY ROW_NUMBER() OVER (PARTITION BY ORBIS_ID ORDER BY UPDATED_DATE DESC)=1) EMS JOIN (SELECT A.ORBIS_ID,A.ENTITY_PROPER_NAME,B.MESG_SECTOR_ID, B.MESG_SECTOR ,B.L1_SECTOR, B.L1_SECTOR_ID FROM ESG_ENTITY_MASTER A, (SELECT MESG_SECTOR_ID, MESG_SECTOR ,L1_SECTOR, L1_SECTOR_ID FROM SECTOR_HIERARCHY QUALIFY ROW_NUMBER() OVER (PARTITION BY MESG_SECTOR_ID ORDER BY AS_OF_DATE)=1 ) B WHERE ENTITY_STATUS='Active' and A.MESG_SECTOR_ID=B.MESG_SECTOR_ID QUALIFY ROW_NUMBER() OVER (PARTITION BY ORBIS_ID ORDER BY UPDATED_DATE DESC)=1) MST ON EMS.MESG_SECTOR_ID = MST.MESG_SECTOR_ID JOIN ENTITY_SCORE ES ON ES.ENTITY_ID_BVD9=MST.ORBIS_ID AND RELEASE_YEAR||RELEASE_MONTH=(select max(RELEASE_YEAR||RELEASE_MONTH) from ENTITY_SCORE) WHERE EMS.ORBIS_ID = '" + entityID + "' AND EMS.World_REGION = 'AMER' AND ES.MARKET_RISK_SCORE IS NOT NULL AND ES.MARKET_RISK_SCORE>=0 ORDER BY MARKET_RISK_SCORE";
        DatabaseDriver.createDBConnection();
        List<Map<String, Object>> result = DatabaseDriver.getQueryResultMap(query);
        System.out.println("result size = " + (result.size() - 1));

        int numberOfCompanies = Integer.parseInt(comparisonChartHeader.getText().replaceAll("\\D", ""));
        System.out.println("numberOfCompanies on UI = " + numberOfCompanies);

        check = check && (result.size() - 1) == numberOfCompanies;

        //find average of MARKET_RISK_SCORE in result
        double sum = 0;
        for (int i = 0; i < result.size(); i++) {
            sum += Double.parseDouble(result.get(i).get("MARKET_RISK_SCORE").toString());
        }
        double average = Math.round(sum / result.size());
        System.out.println("average = " + ((int) average));
        int uiAverage = Integer.parseInt(comparisonChartLegends.get(1).getText().replaceAll("\\D", ""));
        System.out.println("uiAverage = " + uiAverage);

        check = check && (uiAverage == (int) average);

        return check;
    }

    public boolean verifyComparisonChartForSupplyChain(String entityID) {

        if (!comparisonChartRiskSelectionDropdown.isDisplayed()) {
            System.out.println("Supply Chain Risk Comparison Chart is not displayed");
            return false;
        }
        if (!comparisonChartRiskSelectionDropdown.getText().endsWith("Supply Chain Risk")) {
            System.out.println("Please make sure Supply Chain Risk is selected");
            return false;
        }
        //hover over the blue bar and check the data displayed
        BrowserUtils.scrollTo(comparisonChartHeader);
        WebElement blueBar = Driver.getDriver().findElement(By.xpath("//*[local-name()='rect' and @fill='#1f8cff' or @fill='rgb(31,140,255)']"));
        System.out.println("blueBar.isDisplayed() = " + blueBar.isDisplayed());
        BrowserUtils.hover(blueBar);
        BrowserUtils.wait(1);

        WebElement tooltip = Driver.getDriver().findElement(By.xpath("//*[local-name()='g' and @visibility='visible']"));
        System.out.println("tooltip.isDisplayed() = " + tooltip.isDisplayed());
        String tooltipText = tooltip.getText().replaceAll("\\D", "");
        System.out.println("tooltipText = " + tooltipText);

        String query = "select top 1 SUPPLY_CHAIN_RISK_SCORE from esg_entity_master e join entity_score es on es.entity_id_bvd9 = e.ORBIS_ID where e.ORBIS_ID='" + entityID + "' order by RELEASE_DATE desc;";
        DatabaseDriver.createDBConnection();
        Map<String, Object> result = DatabaseDriver.getRowMap(query);
        System.out.println("result = " + result);

        String dbScore = result.get("SUPPLY_CHAIN_RISK_SCORE").toString();
        dbScore = dbScore.substring(0, dbScore.indexOf("."));
        System.out.println("dbScore = " + dbScore);
        int expNumber = Integer.parseInt(dbScore);
        int actNumber = Integer.parseInt(tooltipText);
        if (!(expNumber == actNumber || expNumber == actNumber - 1)) {
            System.out.println("dbScore = " + dbScore);
            System.out.println("tooltipText = " + tooltipText);
            System.out.println("Supply Chain Risk Score is not correct");
            return false;
        }
        return true;
    }

    public boolean verifyComparisonChartForSupplyChainRiskAverage(String entityID) {
        boolean check = true;
        if (!comparisonChartRiskSelectionDropdown.isDisplayed()) {
            System.out.println("Supply Chain Risk Comparison Chart is not displayed");
            return false;
        }
        if (!comparisonChartRiskSelectionDropdown.getText().endsWith("Supply Chain Risk")) {
            System.out.println("Please make sure Supply Chain Risk is selected");
            return false;
        }
        //hover over the blue bar and check the data displayed
        BrowserUtils.scrollTo(comparisonChartHeader);

        String query = "SELECT MST.ORBIS_ID AS ORBIS_ID, MST.MESG_SECTOR AS MESG_SECTOR, MST.ENTITY_PROPER_NAME, SUPPLY_CHAIN_RISK_SCORE, avg(SUPPLY_CHAIN_RISK_SCORE) over() as SECTOR_AVG_SCORE, physical_risk_score_category(SUPPLY_CHAIN_RISK_SCORE) as ENTITY_SCORE_CATEGORY FROM (SELECT A.ORBIS_ID,A.ENTITY_PROPER_NAME,A.WORLD_REGION,B.MESG_SECTOR_ID, B.MESG_SECTOR ,B.L1_SECTOR, B.L1_SECTOR_ID FROM ESG_ENTITY_MASTER A, (SELECT MESG_SECTOR_ID, MESG_SECTOR ,L1_SECTOR, L1_SECTOR_ID FROM SECTOR_HIERARCHY QUALIFY ROW_NUMBER() OVER (PARTITION BY MESG_SECTOR_ID ORDER BY AS_OF_DATE)=1 ) B WHERE ENTITY_STATUS='Active' and A.MESG_SECTOR_ID=B.MESG_SECTOR_ID QUALIFY ROW_NUMBER() OVER (PARTITION BY ORBIS_ID ORDER BY UPDATED_DATE DESC)=1) EMS JOIN (SELECT A.ORBIS_ID,A.ENTITY_PROPER_NAME,B.MESG_SECTOR_ID, B.MESG_SECTOR ,B.L1_SECTOR, B.L1_SECTOR_ID FROM ESG_ENTITY_MASTER A, (SELECT MESG_SECTOR_ID, MESG_SECTOR ,L1_SECTOR, L1_SECTOR_ID FROM SECTOR_HIERARCHY QUALIFY ROW_NUMBER() OVER (PARTITION BY MESG_SECTOR_ID ORDER BY AS_OF_DATE)=1 ) B WHERE ENTITY_STATUS='Active' and A.MESG_SECTOR_ID=B.MESG_SECTOR_ID QUALIFY ROW_NUMBER() OVER (PARTITION BY ORBIS_ID ORDER BY UPDATED_DATE DESC)=1) MST ON EMS.MESG_SECTOR_ID = MST.MESG_SECTOR_ID JOIN ENTITY_SCORE ES ON ES.ENTITY_ID_BVD9=MST.ORBIS_ID AND RELEASE_YEAR||RELEASE_MONTH=(select max(RELEASE_YEAR||RELEASE_MONTH) from ENTITY_SCORE) WHERE EMS.ORBIS_ID = '" + entityID + "' AND EMS.World_REGION = 'AMER' AND ES.SUPPLY_CHAIN_RISK_SCORE IS NOT NULL AND ES.SUPPLY_CHAIN_RISK_SCORE>=0 ORDER BY SUPPLY_CHAIN_RISK_SCORE";
        DatabaseDriver.createDBConnection();
        List<Map<String, Object>> result = DatabaseDriver.getQueryResultMap(query);
        System.out.println("result size = " + (result.size() - 1));

        int numberOfCompanies = Integer.parseInt(comparisonChartHeader.getText().replaceAll("\\D", ""));
        System.out.println("numberOfCompanies on UI = " + numberOfCompanies);

        check = check && (result.size() - 1) == numberOfCompanies;

        //find average of MARKET_RISK_SCORE in result
        double sum = 0;
        for (int i = 0; i < result.size(); i++) {
            sum += Double.parseDouble(result.get(i).get("SUPPLY_CHAIN_RISK_SCORE").toString());
        }
        double average = Math.round(sum / result.size());
        System.out.println("average = " + ((int) average));
        int uiAverage = Integer.parseInt(comparisonChartLegends.get(1).getText().replaceAll("\\D", ""));
        System.out.println("uiAverage = " + uiAverage);

        check = check && (uiAverage == (int) average);

        return check;
    }

    public boolean verifyOperationsRiskTableUnderlyingData(String entity) {
        BrowserUtils.scrollTo(operationsRiskLabel);

        List<String> physicalClimateHazards = physicalClimateHazardsItems.stream().map(WebElement::getText).collect(Collectors.toList());
        List<String> riskLevels = riskLevelItems.stream().map(WebElement::getText).collect(Collectors.toList());
        List<String> facilitiesExposed = facilitiesExposedItems.stream().map(WebElement::getText).collect(Collectors.toList());
        //current year
        String currentYear = Year.now().minusYears(1).toString();
        System.out.println("currentYear = " + currentYear);

        //Database Connection
        String query = "select *,round(PERCENT_FACILITIES_EXPOSED*100) as percentage from DF_TARGET.ENTITY_SCORE_THRESHOLDS et join DF_TARGET.RISK_CATEGORY rc on et.RISK_CATEGORY_ID=rc.RISK_CATEGORY_ID where ENTITY_ID_BVD9 ='" + entity + "' and RISK_THRESHOLD_ID in (3,4) and et.RISK_CATEGORY_ID in (14,1,12,4,2,16 ) and RELEASE_YEAR='" + currentYear + "' order by RISK_CATEGORY_NAME, RISK_THRESHOLD_ID ASC";
        DatabaseDriver.createDBConnection();
        List<Map<String, Object>> result = DatabaseDriver.getQueryResultMap(query);
        System.out.println(result.size() + " records found");

        for (int i = 0; i < physicalClimateHazards.size(); i++) {
            System.out.println("\nChecking " + (i + 1) + "th row");
            System.out.println("result = " + result.get(i).get("RISK_CATEGORY_NAME") + " " + result.get(i).get("PERCENTAGE"));
            System.out.println("physicalClimateHazards = " + physicalClimateHazards.get(i));
            System.out.println("riskLevels = " + riskLevels.get(i));
            System.out.println("facilitiesExposed = " + facilitiesExposed.get(i));

            if (!physicalClimateHazards.get(i).equals(result.get(i).get("RISK_CATEGORY_NAME").toString())) {
                System.out.println("Physical Climate Hazards are not matching");
                return false;
            }

            if (!(new ArrayList<>(Arrays.asList("High Risk", "Red Flag Risk")).contains(riskLevels.get(i)))) {
                System.out.println("Risk Level type is not matching");
                return false;
            }
            String percentage = result.get(i).get("PERCENTAGE").toString() + "%";
            System.out.println("percentage = " + percentage);
            if (percentage.equals("0%")) {
                if (!(facilitiesExposed.get(i).equals("<1%") || (facilitiesExposed.get(i).equals(percentage)))) {
                    System.out.println("0% is not matching");
                    return false;
                }
            } else {
                if (!facilitiesExposed.get(i).endsWith(percentage)) {
                    System.out.println("Percentage is not matching");
                    return false;

                }
            }
        }
        return true;
    }

    public boolean verifyMarketRiskTableUnderlyingData(String entity) {
        BrowserUtils.scrollTo(marketRiskLabel);

        List<String> indicators = marketRiskIndicators.stream().map(WebElement::getText).collect(Collectors.toList());
        System.out.println("indicators = " + indicators);
        List<String> scores = marketRiskScores.stream().map(WebElement::getText).collect(Collectors.toList());
        System.out.println("scores = " + scores);


        //Database Connection
        String query = "select b.RISK_CATEGORY_Name, a.RISK_CATEGORY_ID, ENTITY_CATEGORY_SCORE, RESULT_SET_ID from DF_TARGET.ENTITY_SCORE_CATEGORY a, RISK_CATEGORY b where ENTITY_ID_BVD9 = '" + entity + "' and a.RISK_CATEGORY_ID in (8, 9 ) and a.RISK_CATEGORY_ID = b.RISK_CATEGORY_ID qualify row_number() over(PARTITION BY a.RISK_CATEGORY_ID order by release_month, release_year desc)=1 order by RISK_CATEGORY_NAME";
        DatabaseDriver.createDBConnection();
        List<Map<String, Object>> result = DatabaseDriver.getQueryResultMap(query);
        System.out.println(result.size() + " records found");

        for (int i = 0; i < indicators.size(); i++) {
            System.out.println("\nChecking " + (i + 1) + "th row");


            if (!indicators.get(i).equals(result.get(i).get("RISK_CATEGORY_NAME").toString())) {
                System.out.println("Market Risk Category Name is not matching");
                System.out.println("UI indicator = " + indicators.get(i));
                System.out.println("Database Indicator = " + result.get(i).get("RISK_CATEGORY_NAME").toString());
                return false;
            }

            if (!result.get(i).get("ENTITY_CATEGORY_SCORE").toString().startsWith(scores.get(i))) {
                System.out.println("Market risk Score not matching");
                System.out.println("UI Score = " + scores.get(i));
                System.out.println("Database Score = " + result.get(i).get("ENTITY_CATEGORY_SCORE").toString());
                return false;
            }
        }
        return true;
    }

    public boolean verifySupplyChainRiskTableUnderlyingData(String entity) {
        BrowserUtils.scrollTo(supplyChainRiskLabel);

        List<String> indicators = supplyChainRiskIndicators.stream().map(WebElement::getText).collect(Collectors.toList());
        System.out.println("indicators = " + indicators);
        List<String> scores = supplyChainRiskScores.stream().map(WebElement::getText).collect(Collectors.toList());
        System.out.println("scores = " + scores);

        //Database Connection
        String query = "select * from DF_TARGET.ENTITY_SCORE_CATEGORY where ENTITY_ID_BVD9 = '" + entity + "' and RISK_CATEGORY_ID in (6, 7 ) qualify row_number() over(partition by RISK_CATEGORY_ID order by RELEASE_YEAR desc, RELEASE_MONTH desc)=1";
        DatabaseDriver.createDBConnection();
        List<Map<String, Object>> result = DatabaseDriver.getQueryResultMap(query);
        System.out.println(result.size() + " records found");

        for (int i = 0; i < indicators.size(); i++) {
            System.out.println("\nChecking " + (i + 1) + "th row");

            if (!(new ArrayList<>(Arrays.asList("Country of Origin", "Resource Demand")).contains(indicators.get(i)))) {
                System.out.println("Supply Chain Risk Category Name is not matching");
                System.out.println("UI indicator = " + indicators.get(i));
                return false;
            }

            if (!result.get(i).get("ENTITY_CATEGORY_SCORE").toString().startsWith(scores.get(i))) {
                System.out.println("Supply Chain risk Score not matching");
                System.out.println("UI Score = " + scores.get(i));
                System.out.println("Database Score = " + result.get(i).get("ENTITY_CATEGORY_SCORE").toString());
                return false;
            }
        }
        return true;
    }

    public boolean verifyOperationsRiskTableForUI() {
        BrowserUtils.scrollTo(supplyChainRiskLabel);
        /*
        User should see the breakdown of the physical risk hazards-Operations Risk for the company:

        1. Header Title: “Risk Level” Operations Risk
            a. The title should be dynamic based of the overall Operations Risk Level of the company using the Operations Risk Score.The color of the entire title should change to the color of the risk level
        2. The table should show the below datapoints:
            1. Physical Climate Hazards (Should be listed alphabetically) - Floods, Heat Stress, Hurricanes & Typhoons, Sea Level Rise, Water Stress, Wildfires
            2. Risk Level - No Risk, Low Risk, Medium Risk, High Risk, Red Flag
            3. Facilities Exposed to High Risk/Red Flag
                This will show the % of facilities mapped to the company that is exposed to High Risk and Red Flag Risk for the given hazard
         */
        if (!operationsRiskLabel.getText().equals("OPERATIONS RISK")) {
            System.out.println("Operations Risk Title is not verified");
            return false;
        }
        List<String> expectedHazards = new ArrayList<>(Arrays.asList("Floods", "Heat Stress", "Hurricanes & Typhoons", "Sea Level Rise", "Water Stress", "Wildfires"));
        List<String> hazards = physicalClimateHazardsItems.stream().map(WebElement::getText).collect(Collectors.toList());

        for (String hazard : hazards) {
            if (!expectedHazards.contains(hazard)) {
                System.out.println("This Physical Climate Hazard is  not found = " + hazard);
                return false;
            }
        }
        if (!Ordering.natural().isOrdered(hazards)) {
            System.out.println("Hazards are not in alphabetical order");
            return false;
        }
        //check if UI risk in and expectedRisk
        List<String> expectedRisks = new ArrayList<>(Arrays.asList("No Risk", "Low Risk", "Medium Risk", "High Risk", "Red Flag Risk"));
        List<String> risks = riskLevelItems.stream().map(WebElement::getText).collect(Collectors.toList());
        for (String risk : risks) {
            if (!expectedRisks.contains(risk)) {
                System.out.println("This Risk Level is  not found = " + risk);
                return false;
            }
        }

        for (WebElement facility : facilitiesExposedItems) {
            if (!facility.getText().matches("<*\\d+%")) {
                System.out.println("Facilities Exposed percentage is not in correct format");
                return false;
            }
        }
        return true;
    }

    public boolean verifyMarketRiskTableForUI() {
        BrowserUtils.scrollTo(supplyChainRiskLabel);
        /*
        User should see the breakdown of the market risk for the company
        1. Header Title: “Risk Level” Market Risk
            The title should be dynamic based of the overall Operations Risk Level of the company using the Market Risk Score.The color of the entire title should change to the color of the risk level
        2.The table should show the below data points:
            a. Indicator - Country of Sale, Water Sensitivity
            b. Risk Score - Show raw score
         */
        if (!marketRiskLabel.getText().startsWith("Market Risk")) {
            System.out.println("Market Risk Title is not verified");
            return false;
        }
        List<String> expectedIndicators = new ArrayList<>(Arrays.asList("Country of Sales", "Weather Sensitivity"));

        for (WebElement indicator : marketRiskIndicators) {
            if (!expectedIndicators.contains(indicator.getText())) {
                System.out.println("This Market Risk indicator is  not found = " + indicator.getText());
                return false;
            }
        }

        for (WebElement facility : facilitiesExposedItems) {
            if (facility.getText().contains("\\D")) {
                System.out.println("Market Risk score is not in correct format");
                return false;
            }
        }
        return true;
    }

    public boolean verifySupplyChainRiskTableForUI() {
        BrowserUtils.scrollTo(supplyChainRiskLabel);
        /*
        User should see the breakdown of the Supply Chain risk for the company
        1. Header Title: Supply Chain Risk
        2.The table should show the below data points:
            a. Indicator- Country of Origin, Resource Demand
            b. Score - Show Raw Score
         */
        if (!supplyChainRiskLabel.getText().equals("SUPPLY CHAIN RISK")) {
            System.out.println("Supply Chain Risk Title is not verified");
            return false;
        }
        List<String> expectedIndicators = new ArrayList<>(Arrays.asList("Country of Origin", "Resource Demand"));

        for (WebElement indicator : supplyChainRiskIndicators) {
            if (!expectedIndicators.contains(indicator.getText())) {
                System.out.println("This Supply Chain Risk indicator is  not found = " + indicator.getText());
                return false;
            }
        }

        for (WebElement score : supplyChainRiskScores) {
            if (score.getText().contains("\\D")) {
                System.out.println("Supply Chain Risk score is not in correct format");
                return false;
            }
        }
        return true;//End of code
    }

    public boolean verifyESGScoreValue() {
        for (WebElement score : esgScores) {
          //  System.out.println("score " + score.getText().split("\n")[1]);
            if (score.getText().split("/")[1].equals("100")) {
                System.out.println("ESG score is verified = " + score.getText());
                String color = Color.fromString(score.getCssValue("color")).asHex();
                System.out.println("color = " + color);
                if (!color.equals("#ffffff")) return false;
                return score.isDisplayed();
            }
        }
        return false;
    }

    public void verifyESGScoreHeaders() {
       List<String> pillars= Arrays.asList(new String[]{"Environment","Social","Governance"});
        int count = 0;
        //actPillars.put("ESG Rating");
        for (WebElement pillar : esgScorePillars) {
            if (count == 0) {
                count++;
                continue;
            }

            String pillarName = pillar.getText().substring(0, pillar.getText().indexOf("\n"));
            String pillarScore = pillar.getText().substring(pillar.getText().indexOf("\n") + 1);
            assertTestCase.assertTrue(pillars.contains(pillarName),"Validating the pillar name " + pillarName);
            assertTestCase.assertEquals(pillarScore.split("/")[1],("100"), "Validating " + pillarName + " Score format" ) ;
            String color = Color.fromString(pillar.getCssValue("color")).asHex();
            System.out.println("color = " + color);
            assertTestCase.assertEquals(color,"#ffffff","validating color");
        }


    }

    public boolean verifyESGScorePillars(String Entity) {
        Map<String, String> actPillars = new HashMap<>();
        //we need to skip first element because xpath locates an extra element which is not a pillar
        int count = 0;
        //actPillars.put("ESG Rating");
        for (WebElement pillar : esgScorePillars) {
            if (count == 0) {
                count++;
                continue;
            }

            String pillarName = pillar.getText().substring(0, pillar.getText().indexOf("\n"));
            String pillarScore = pillar.getText().substring(pillar.getText().indexOf("\n") + 1);
            if (pillarName.equalsIgnoreCase("ESG Score")) {
                actPillars.put("ESG Rating", pillarScore);
                continue;
            }
            actPillars.put(pillarName, pillarScore);
        }
        System.out.println("actPillars = " + actPillars);

        //run query
        DatabaseDriver.createDBConnection();

        String query = "SELECT P.RESEARCH_LINE_ID ,ORBIS_ID, lkp1.scale,lkp1.grade, SUB_CATEGORY,DATA_TYPE,p.SCORE, score_msg, lkp1.QUALIFIER QUAL, lkp2.QUALIFIER as score_category, lkp1.score_range, lkp1.as_of_date,lkp2.score_range sc_range FROM (SELECT ESG.ORBIS_ID,ESG.RESEARCH_LINE_ID,ESG.DATA_TYPE, ESG.YEAR,ESG.MONTH,CASE WHEN ESG.SUB_CATEGORY = 'Environmental' THEN 'Environment' WHEN SUB_CATEGORY = 'ESG' THEN iff ((DATA_TYPE = 'esg_pillar_score'),'ESG Rating','ESG Score') WHEN DATA_TYPE = 'overall_alphanumeric_score' THEN 'ESG Score' ELSE SUB_CATEGORY END as SUB_CATEGORY, CASE WHEN DATA_TYPE = 'esg_pillar_score' THEN try_to_double(VALUE) ELSE NULL END as score , CASE WHEN DATA_TYPE = 'overall_alphanumeric_score' THEN VALUE ELSE NULL END as score_msg FROM DF_TARGET.ESG_OVERALL_SCORES ESG JOIN DF_TARGET.ENTITY_COVERAGE_TRACKING CT ON ESG.ORBIS_ID = CT.ORBIS_ID AND CT.COVERAGE_STATUS = 'Published' AND PUBLISH = 'yes' AND CT.RESEARCH_LINE_ID IN (1015,1008) WHERE ESG.ORBIS_ID = '" + Entity + "' AND ESG.DATA_TYPE IN('overall_alphanumeric_score' ,'esg_pillar_score') QUALIFY ROW_NUMBER() OVER(PARTITION BY ESG.ORBIS_ID,DATA_TYPE,SUB_CATEGORY ORDER BY SCORED_DATE DESC) = 1 limit 5) p LEFT OUTER JOIN DF_LOOKUP.ESG_SCORE_REFERENCE lkp1  ON lkp1.GRADE = P.SCORE_MSG AND lkp1.RESEARCH_LINE_ID = P.RESEARCH_LINE_ID AND lkp1.STATUS = 'Active' LEFT OUTER JOIN DF_LOOKUP.ESG_SCORE_REFERENCE lkp2 ON 1=1 AND lkp2.RESEARCH_LINE_ID = P.RESEARCH_LINE_ID and lkp2.GRADE  IS NULL AND split(lkp2.SCORE_RANGE,'')[1]::number AND (floor(score) BETWEEN lkp2.LOWER_SCORE_THRESHOLD AND lkp2.UPPER_SCORE_THRESHOLD) AND lkp2.STATUS = 'Active'";
        List<Map<String, Object>> dbResult = DatabaseDriver.getQueryResultMap(query);
        //result.forEach(System.out::println);
        //Verify the value for Environment , Social, Governance pillar value for the entity is reflecting correctly
        //actPillars = {Social=42/100, Environment=51/100, Governance=51/100}
        for (String pillar : actPillars.keySet()) {
            for (Map<String, Object> row : dbResult) {
                if (row.get("SUB_CATEGORY").toString().equals(pillar)) {
                    // String expScoreCategory = new ESGUtilities().getESGPillarsCategory(row.get("RESEARCH_LINE_ID").toString(), (int) Double.parseDouble(row.get("SCORE").toString()));
                    String value = String.valueOf((int) Double.parseDouble(row.get("SCORE").toString()));
                    if (!actPillars.get(pillar).split("/")[0].equals(value)) {
                        System.out.println("ESg Pillar Category Doesnt Match for " + pillar + " = " + actPillars.get(pillar) + " with " + row.get("SCORE"));
                        return false;
                    }
                }
            }
        }
        //Verify the data on the UI for Overall ESG score matches with the db
        String escScore = String.valueOf((int) Double.parseDouble(dbResult.get(0).get("SCORE").toString()));
        System.out.println("escScore = " + escScore);
        for (WebElement score : esgScores) {
            if (score.getText().contains(escScore)) {
                System.out.println("ESG Score is verified = " + score.getText());
                return score.isDisplayed();
            }
        }
        return false;
    }

    public boolean validatePhysicalRiskMananagementTableIsAvailable() {
        try {
            BrowserUtils.scrollTo(PhysicalRiskManagementTable);
            return wait.until(ExpectedConditions.visibilityOf(PhysicalRiskManagementTable)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void validatePhysicalRiskManagementTable() {
        assertTestCase.assertTrue(header_PhysicalRiskManagement.isDisplayed(), "Validate Physical Risk Management Title is available");
        List<WebElement> columns = PhysicalRiskManagementTable.findElements(By.xpath("parent::div/following-sibling::div/div/table/thead/tr/th"));
        assertTestCase.assertTrue(columns.get(0).getText().equals("Indicator"), "Validate Indicator column");
        assertTestCase.assertTrue(columns.get(1).getText().equals("Risk Level"), "Validate Risk Level column");
        assertTestCase.assertTrue(columns.get(2).getText().equals("Score"), "Validate Score column");

        List<WebElement> indcatorColumnDetails = PhysicalRiskManagementTable.findElements(By.xpath("tbody/tr/td[1]"));
        List<String> expectedList = Arrays.asList(new String[]{"Overall Physical Risk Management Score", "Leadership", "Implementation", "Results"});
        for (WebElement e : indcatorColumnDetails) {
            assertTestCase.assertTrue(expectedList.contains(e.getText()), "Validate Physical Risk management Table content");
        }

    }

    public List<String> getComparisonChartRiskSelectionOptions() {
        //BrowserUtils.waitForClickablility(comparisonChartRiskSelectionDropdown, 5).click();
        List<String> options = new ArrayList<>();
        for (WebElement option : comparisonChartRiskSelectionOptions) {
            options.add(option.getText());
        }
//        comparisonChartRiskSelectionOptions.get(0).click();
        return options;
    }

    public Map<String, String> getBrownShareNAData() {
        DatabaseDriver.createDBConnection();
        List<String> orbisID = new ArrayList<>();

        String query = "select * from brown_share where BS_FOSF_INDUSTRY_REVENUES_ACCURATE_SOURCE is null " +
                " QUALIFY ROW_NUMBER() OVER (PARTITION BY BVD9_NUMBER ORDER BY year DESC,month DESC) =1 Limit 1";

        Map<String, String> data = new HashMap<String, String>();

        for (Map<String, Object> rs : getQueryResultMap(query)) {
            data.put("BVD9_NUMBER", rs.get("BVD9_NUMBER").toString());
            data.put("SCORE_RANGE", rs.get("SCORE_RANGE").toString());
        }
        System.out.println(data);
        return data;


    }

    public void verifyCarbonFootPrintToolTip(WebElement lineName) {

        List<String> expectedTooltipvalue = Arrays.asList(new String[]{
                "We evaluate a companies carbon footprint based on the direct (Scope 1) and indirect (Scope 2) emissions.",
                "When emissions data is not publicly disclosed, we estimate Scope 1 and Scope 2 emissions using our own proprietary models.",
                "Other indirect emissions (Scope 3), are also collected or estimated and provided but are not part of the carbon footprint grade."});
        BrowserUtils.scrollTo(lineName);
        BrowserUtils.hover(lineName);
        List<WebElement> tooltipsLi = Driver.getDriver().findElements
                (By.xpath("//div[contains(@class,'MuiTooltip-tooltip MuiTooltip-tooltipPlacementTop')]/ul/li"));

        for (WebElement tooltip : tooltipsLi) {
            assertTestCase.assertTrue(expectedTooltipvalue.contains(tooltip.getText()), "Validating the CarbonFoot Print ToolTip");
        }


    }

    public void verifyBrownShareFootPrintTable(List<String> metrics) {
//        navigateToTransitionRisk();
        Assert.assertTrue(transitionRiskBrownShareTableHeadings.get(0).getText().equals("Fossil Fuel Disclosures"), "Validating Table Heading");
        Assert.assertTrue(transitionRiskBrownShareTableHeadings.get(1).getText().equals("Investment in Category"), "Validating Table Heading");
        BrowserUtils.scrollTo(transitionRiskBrownShareTableBody.get(0));
        for (String metric : metrics) {
            assertTestCase.assertTrue(transitionRiskBrownShareTableBody.stream().filter(e -> e.getText().equals(metric)).count() > 0, "Validating Brown Share " + metric + " Metric");
        }
    }

    public void verifyGreenShareTableDataIsSorted() {
        navigateToTransitionRisk();
        BrowserUtils.scrollTo(transitionRiskGreenShareTableHeadings.get(0));
        ArrayList<String> uiList = new ArrayList<>();

        for (WebElement e : transitionRiskGreenShareTableBody) {
            uiList.add(e.getText());
        }
        ArrayList<String> sortedList = new ArrayList<>();
        for (String g : uiList) {
            sortedList.add(g);
        }
        Collections.sort(sortedList);
        assertTestCase.assertTrue(sortedList.equals(uiList), "Validating Green Share Table is in sorted order");
    }

    public void verifyGreenShareTableHeadings() {
        navigateToTransitionRisk();
        Assert.assertTrue(transitionRiskGreenShareTableHeadings.get(0).getText().equals("Products and Technologies"), "Validating Table Heading");
        Assert.assertTrue(transitionRiskGreenShareTableHeadings.get(1).getText().equals("Investment in Category"), "Validating Table Heading");

    }

    public void verifyBrownShareWidget() {
        navigateToTransitionRisk();
        BrowserUtils.scrollTo(transitionRiskBrownShareWidgetUpdatedDate);
        assertTestCase.assertTrue(transitionRiskBrownShareWidget.getText().contains("Brown Share Assessment"), "Validating Brown Share heading");
        assertTestCase.assertTrue(transitionRiskBrownShareWidgetSubHeading.getText().contains("% of Overall Revenue derived from Fossil Fuel\n" +
                "Activities:"), "Validating Brown Share Sub heading");

        String updatedDateText = transitionRiskBrownShareWidgetUpdatedDate.getText();
        assertTestCase.assertTrue(updatedDateText.contains("Updated on"), "Validating Date ");
        assertTestCase.assertTrue(isValidFormat("MMMM d, yyyy", updatedDateText.split("on ")[1]), "Validating Date format");

    }

    public void verifyBrownShareCategory() {
        List<String> expectedCategories = new ArrayList<>();
        expectedCategories.add("NO INVOLVEMENT");
        expectedCategories.add("MINOR INVOLVEMENT");
        expectedCategories.add("MAJOR INVOLVEMENT");
        String actualCategory = transitionRiskBrownShareCategory.getText();
        assertTestCase.assertTrue(expectedCategories.contains(actualCategory), expectedCategories+" Category is not available from Expected Brown share Categories");
    }

    public void verifyBrownShareWidgetOverallRevenue(String orbisId) {
        String uiOverallRevenuePercent = transitionRiskBrownShareWidgetOverallRevenue.getText();
        EntityClimateProfilePageQueries queries = new EntityClimateProfilePageQueries();
        String dbOverallRevenuePercent = queries.getBrownShareData(orbisId).get("SCORE_RANGE");
        assertTestCase.assertEquals(uiOverallRevenuePercent.replace(" ",""),dbOverallRevenuePercent, "Overall Revenue Percent from UI is not matching with DB");

    }

    public void verifyBrownShareComparisonChartLegends(String sectorName, String companyName) {
        assertTestCase.assertEquals(brownShareComparisonChartLegends.get(0).getAttribute("style"),"background: rgb(178, 133, 89);", "Brown Share Comparison Chart - Verify first legend color");
        assertTestCase.assertTrue(brownShareComparisonChartLegends.get(1).getText().contains(sectorName), "Brown Share Comparison Chart - Verify first legend label");
        assertTestCase.assertEquals(brownShareComparisonChartLegends.get(2).getAttribute("style"),"background: rgb(31, 140, 255);", "Brown Share Comparison Chart - Verify second legend color");
        assertTestCase.assertTrue(brownShareComparisonChartLegends.get(3).getText().contains(companyName), "Brown Share Comparison Chart - Verify second legend label");
    }

    public void verifyBrownShareComparisonChartAxes() {
        assertTestCase.assertEquals(brownShareComparisonChartAxes.get(1).getText(),"Major", "Brown Share Comparison Chart - Verify X-Axis Label");
        assertTestCase.assertEquals(brownShareComparisonChartAxes.get(2).getText(),"None", "Brown Share Comparison Chart - Verify X-Axis Label");
        assertTestCase.assertEquals(brownShareComparisonChartAxes.get(3).getText(),"0%", "Brown Share Comparison Chart - Verify Y-Axis Label");
        assertTestCase.assertEquals(brownShareComparisonChartAxes.get(4).getText(),">=50%", "Brown Share Comparison Chart - Verify Y-Axis Label");
    }

    public void verifyBrownShareComparisonChartAverageLine() {
        try{
            brownShareComparisonChartAverageLine.isDisplayed();
        }catch (Exception e) {
            assertTestCase.assertTrue(false, "Average Line is not available");
        }
    }

    public void verifyBrownShareComparisonChartSectorDesc(String sectorName, int sectorCompaniesCount, String companyName) {
        String expDescription = companyName+" compared to "+sectorCompaniesCount+" companies in "+sectorName;
        String actualDescription = brownShareComparisonChartAxes.get(0).getText();
        assertTestCase.assertEquals(actualDescription, expDescription, "Brown Share Comparison Chart - Verify entity description");
    }

    public String getBrownShareSummaryValue() {
        navigateToTransitionRisk();
        return transitionRiskBrownShareWidget.findElement(By.xpath("div")).getText();
    }

    public void verifyPhysicalRiskWidgetHeadings() {

        Assert.assertTrue(Color.fromString(operationsRiskLabel.getCssValue("color")).asHex().equals("#263238"), "Validating Operation Risk lable is in Black color");
        Assert.assertTrue(Color.fromString(supplyChainRiskLabel.getCssValue("color")).asHex().equals("#263238"), "Validating Supply chain Risk lable is in Black color");
        Assert.assertTrue(Color.fromString(marketRiskLabel.getCssValue("color")).asHex().equals("#263238"), "Market Risk lable is in Black color");

    }

    public void verifyComparisonChartYAxix() {
        assertTestCase.assertTrue(comparisonChartYAxis.get(0).getText().equals("100"));
        assertTestCase.assertTrue(comparisonChartYAxis.get(1).getText().equals("0"));
    }

    public void verifyComparisonChartLegendHasNumericvalue() {
        assertTestCase.assertTrue(NumberUtils.isParsable(Para_comparisonChartLegends.get(0).getText().split(":")[1].trim()), "Validate Legend has Numeric value");
        assertTestCase.assertTrue(NumberUtils.isParsable(Para_comparisonChartLegends.get(1).getText().split(":")[1].trim()), "Validate Legend has Numeric value");
    }

    public Boolean validateInfoIcon(String comapnyName) {
        return getCompanyHeader(comapnyName).findElement(By.xpath("../*[name()='svg']")).isDisplayed();
    }

    public void openAboutDrawer(String comapnyName) {
        getCompanyHeader(comapnyName).click();
    }

    public Boolean isAboutDrawerAvailable() {
        return wait.until(ExpectedConditions.visibilityOf(aboutDrawerMainDiv)).isDisplayed();
    }

    public Boolean validateAboutHeader(String comapnyName) {
        WebElement header = aboutDrawerMainDiv.findElement(By.xpath("header"));
        return header.getText().split("\n")[0].equals("About " + comapnyName);
    }

    public void validateAboutDrawerDetails(String comapnyName) {
        List<String> expectedsections = Arrays.asList("ISIN", "Orbis ID", "LEI", "Region", "Sector", "Model", "Company's Description - Coming Soon");
        List<WebElement> detailDiv = aboutDrawerMainDiv.findElements(By.xpath("div/div"));
        for (WebElement e : detailDiv) {
            assertTestCase.assertTrue(expectedsections.contains(e.getText().split(":")[0]), "Validate " + e.getText() + " text");
        }
    }

    public void validateCompanyNameHoverFunctionality(String CompanyName) {
        WebElement Company = getCompanyHeader(CompanyName);
        BrowserUtils.hover(Company);
        BrowserUtils.wait(1);
        String color = Color.fromString(Company.findElement(By.xpath(".."))
                .getCssValue("background-color")).asHex().toUpperCase();
        System.out.println(color);
        assertTestCase.assertTrue(color.equals("#EBF4FA")
                , "Validate Company Name hover functionality");
    }

    public void validatePhysicalClimateHazardUpdatedDate(String orbisID) {
        EntityClimateProfilePageQueries entityClimateProfilepagequeries = new EntityClimateProfilePageQueries();
        // DatabaseDriver.createDBConnection();
        String dbDate = getFormattedDate(entityClimateProfilepagequeries.getUpdatedDateforPhysicalClimateHazard(orbisID).substring(0, 10), "MMMM d, yyyy");
        if (!dbDate.equals("")) {
            BrowserUtils.scrollTo(physicalClimateHazards);
            validateUpdatedOndate(physicalClimateHazards.getText().split("\n")[3], dbDate);
            navigateToPhysicalRisk();
            BrowserUtils.scrollTo(PhysicalRisk_ClimateHazard_UpdatedDate_Span);
            validateUpdatedOndate(PhysicalRisk_ClimateHazard_UpdatedDate_Span.getText(), dbDate);
        }

    }

    public void validatePhysicalRiskManagementUpdatedDate(String orbisID) {
        EntityClimateProfilePageQueries entityClimateProfilepagequeries = new EntityClimateProfilePageQueries();
        String dbDate = getFormattedDate(entityClimateProfilepagequeries.getUpdatedDateforPhysicalRiskManagement(orbisID), "MMMM d, yyyy");
        if (!dbDate.equals("")) {
            BrowserUtils.scrollTo(physicalRiskManagementWidgetDescription);
            //TODO check here
            validateUpdatedOndate(physicalRiskManagementWidgetDescription.getText().split("\n")[1], dbDate);
            navigateToPhysicalRisk();
            BrowserUtils.scrollTo(PhysicalRisk_PhysicalRiskManagement_UpdatedDate_Span);
            validateUpdatedOndate(PhysicalRisk_PhysicalRiskManagement_UpdatedDate_Span.getText(), dbDate);
        }
    }

    public void validateUpdatedOndate(String updatedDateText, String dbDate) {
        assertTestCase.assertTrue(updatedDateText.contains("Updated on"), "Validating Date ");
        String UIUpdateDate = updatedDateText.split("on ")[1];
        assertTestCase.assertTrue(isValidFormat("MMMM d, yyyy", UIUpdateDate), "Validating Date format");
        assertTestCase.assertTrue(dbDate.equals(UIUpdateDate), "Validate Date from Database");

    }

    public String getUnderlyingCarbonPrintDetails() {
        navigateToTransitionRisk();
        BrowserUtils.scrollTo(transitionRiskCarbonFootprintWidget);
        List<WebElement> elements = transitionRiskCarbonFootprintWidget.findElements(By.xpath("div/div/div"));
        List<String> returnList = new ArrayList<>();
        for (WebElement e : Iterables.skip(elements, 1)) {
            if (e.getText().contains("Updated on")) {
                break;
            } else {
                List<WebElement> spans = e.findElements(By.xpath("span"));
                String divValue = e.getText();
                if (spans.size() > 0) {
                    for (WebElement span : spans) divValue = divValue.replace(span.getText(), " " + span.getText());
                }


                if (divValue.contains("Reporting Year"))
                    returnList.addAll(Arrays.stream(divValue.split("((?=Reporting Year)|(?<=Reporting Year))"))
                            .map(String::trim).collect(Collectors.toList()));
                else if (divValue.contains("(tCO2eq/M revenue)"))
                    returnList.addAll(Arrays.stream(divValue.split("((?=\\(tCO2eq/M revenue\\))|(?<=\\(tCO2eq/M revenue\\)))"))
                            .map(String::trim).collect(Collectors.toList()));
                else
                    returnList.add(divValue);
            }
        }

        return returnList.stream().collect(Collectors.joining(" "));
    }

    public String getPhysicalClimateHazards() {
        BrowserUtils.scrollTo(wait.until(ExpectedConditions.visibilityOf(physicalClimateHazards)));
        String str = physicalClimateHazards.getText().replaceAll("\n", " ");
        str = str.replace("HIGHEST RISK HAZARD: ", "HIGHEST RISK HAZARD : ");
        String[] values = str.split(("((?= \\d+%)|(?<=\\d% ))").toString());
        return values[0] + " " + values[2].split("Update")[0] + values[1].trim();
    }

    public String getPhysicalRiskManagement() {
        BrowserUtils.scrollTo(wait.until(ExpectedConditions.visibilityOf(physicalRiskManagementWidget)));
        String str = physicalRiskManagementWidget.getText();
        str=str.replaceAll("\n", " ");
        String Values[] = str.split((" "));
        str = str.replace("Physical Risk Management Anticipation", "Physical Risk Management " + Values[Values.length - 1] + " Anticipation").split("Updated")[0].trim();
        str=str.replace("Anticipation"," Anticipation").replace("  "," ");
        return str;
    }

    public String getTempratureAlignmentWidget() {
        BrowserUtils.scrollTo(wait.until(ExpectedConditions.visibilityOf(temperatureAlignmentWidget)));
        String str = temperatureAlignmentWidget.getText().replaceAll("\n", " ");
        String climateText = temperatureAlignmentStatus.getText();
        str = str.replace(climateText, climateText.toUpperCase(Locale.ROOT));
        str = str.replace(":", " ");
        str = str.replace(" °C Emissions Reduction Target Year ", " oC Emissions Reduction Target Year ");
        return str.split(". Updated")[0];
    }


    public String getGreenShareWidget() {
        BrowserUtils.scrollTo(wait.until(ExpectedConditions.visibilityOf(greenShareCard)));
        String returnString = "";
        if (!greenShareCard.getText().contains("No information available."))
            returnString = "Offering Green Solutions " + GreenShareWidgetValue.getText();
        else
            returnString = greenShareCard.getText();
        return returnString;
    }

    public String getBrownShareWidget() {
        BrowserUtils.scrollTo(wait.until(ExpectedConditions.visibilityOf(brownShareCard)));
        String returnString = "";
        if (!brownShareCard.getText().contains("No information available."))
            returnString = "Brown Share Overall Fossil Fuels Industry Revenues " + BrownShareWidgetValue.getText();
        else
            returnString = brownShareCard.getText();
        return returnString;
    }

    public String getPhysicalRiskOperationRisk() {
        navigateToPhysicalRisk();
        String str = physicalRiskOperationRisk.getText().replaceAll("\n", " ");
        str = str.replace("Physical Climate Hazards Risk Level Facilities Exposed ", "Physical Risk OPERATIONS RISK PHYSICAL CLIMATE HAZARDS RISK LEVEL FACILITIES EXPOSED ");
        return str;

    }


    public String getPhysicalRiskMarketRisk() {
        navigateToPhysicalRisk();
        WebElement e = marketRiskTable.get(marketRiskTable.size() - 1);
        BrowserUtils.scrollTo(e);
        String str = e.getText().replaceAll("\n", " ");
        str = str.replace("Indicator Score", "MARKET RISK INDICATOR SCORE");
        return str;

    }

    public String getPhysicalRiskSupplyChainRisk() {
        navigateToPhysicalRisk();
        WebElement e = supplyChainRiskTable.get(supplyChainRiskTable.size() - 1);
        BrowserUtils.scrollTo(e);
        String str = e.getText().replaceAll("\n", " ");
        str = str.replace("Indicator Score", "SUPPLY CHAIN RISK INDICATOR SCORE");
        return str;

    }

    public String getunderlyingDataMetricsGreenShareAssessment() {
        navigateToTransitionRisk();
        BrowserUtils.scrollTo(wait.until(ExpectedConditions.visibilityOf(underlyingDataMetrics_GreenShareAssessmentCard)));
        String returnString = "";
        if (!underlyingDataMetrics_GreenShareAssessmentCard.getText().contains("No information available.")) {
            returnString = underlyingDataMetrics_GreenShareAssessment_Table.getText().replaceAll("\n", " ");
            returnString = returnString.replace("Products and Technologies Investment in Category",
                    "Underlying Data Transition Risk GREEN SHARE PRODUCTS AND TECHNOLOGIES INVESTMENT IN CATEGORY");
        } else
            returnString = underlyingDataMetrics_GreenShareAssessmentCard.getText();
        return returnString;

    }

    public List<String> getHederValues(String CompanyName) {
        List<String> returnList = new ArrayList<>();
        WebElement companyNameHeader = getCompanyHeader(CompanyName);
        List<WebElement> companySummary = companyNameHeader.findElements(By.xpath("parent::div/following-sibling::div/span"));
        returnList.add(CompanyName);
        String identifiers = companySummary.get(0).getText().split("\\|")[0].trim();

        //TODO : Change includeEmptyIdentifier parameter in below line to false after implementation of ESGCA-10987
        returnList.add(getIdentifiers(identifiers, false));

        return returnList;
    }

    //This method os to get List of identifiers,
    // includeEmptyIdentifier : True will provide all 3 identifiers (ISIN, LEI, Orbis ID) eeven though their values are not available on UI.
    // includeEmptyIdentifier : false will provide only identifiers which are available on UI.
    public String getIdentifiers(String listOfIdentifiers, @NotNull Boolean includeEmptyIdentifier) {
        String returnString = "";
        if (includeEmptyIdentifier) {
            Map<Object, Object> map =
                    Arrays.stream(listOfIdentifiers.split(","))
                            .map(s -> s.split(":", 2))
                            .collect(Collectors.toMap(f -> f[0].trim(), f -> f.length > 1 ? f[1].trim() : ""));
            returnString = new StringBuilder().append("ISIN: " + (map.get("ISIN") != null ? map.get("ISIN").toString() : ""))
                    .append(", LEI: " + (map.get("LEI") != null ? map.get("LEI").toString() : ""))
                    .append(", Orbis ID: " + (map.get("Orbis ID") != null ? map.get("Orbis ID").toString() : "")).toString();
        } else if (!(includeEmptyIdentifier)) {
            returnString = listOfIdentifiers;
        }
        return returnString;
    }

    public List<String> getESGSummaryDetails() {
        BrowserUtils.scrollTo(esgScores.get(0));
        List<String> returnList = new ArrayList<>();
        for (WebElement e : esgScores) {
            if (e.getText().contains("\n")) {
                String[] a = e.getText().split("\n");
                returnList.add(a[1] + " " + (a[0].contains("Environment") ? "Environmental" : a[0]));
            } else {
                returnList.add(e.getText());
            }
        }
        return returnList;

    }

    public void verifyUnderlyingDataForBrownShareWidget(String orbisID) {
        //navigateToTransitionRisk();
        BrowserUtils.scrollTo(transitionRiskBrownShareWidgetUpdatedDate);
        EntityClimateProfilePageQueries entityCPPQueries = new EntityClimateProfilePageQueries();
        Map<String, Object> result = entityCPPQueries.getBrownShareAssessmentDataForEntity(orbisID);

        assertTestCase.assertTrue(transitionRiskBrownShareWidgetSubHeading.getText().contains(result.get("BS_FOSF_INDUSTRY_REVENUES").toString()),
                "Brown Share Widget Sub Heading is verified matching with Database");

        String expDate = result.get("PRODUCED_DATE").toString();
        System.out.println("expDate = " + expDate);
        //
        expDate = DateTimeUtilities.getDatePlusMinusDays(expDate, 1, "MMMM d, yyyy");
        System.out.println("expDate = " + expDate);
        String actDate = transitionRiskBrownShareWidgetUpdatedDate.getText();
        System.out.println("actDate = " + actDate);
        assertTestCase.assertTrue(actDate.contains(expDate),
                "Brown Share Widget Updated Date is verified matching with Database");
    }


    public String getunderlyingDataMetricsBrownShareAssessment() {
        navigateToTransitionRisk();
        BrowserUtils.scrollTo(wait.until(ExpectedConditions.visibilityOf(BrownShareTransitionRiskTable)));
        String returnString = "";
        if (!BrownShareTransitionRiskTable.getText().contains("No information available.")) {
            returnString = BrownShareTransitionRiskTable.getText().replaceAll("\n", " ");
            returnString = returnString.replace("Fossil Fuel Disclosures Investment in Category ",
                    "Underlying Data Transition Risk BROWN SHARE FOSSIL FUEL DISCLOSURES INVESTMENT IN CATEGORY ");
        } else
            returnString = underlyingDataMetrics_GreenShareAssessmentCard.getText();
        return returnString;

    }


}
