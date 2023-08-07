package com.esgc.EntityProfile.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.EntityProfile.API.APIModels.SummarySection.EntityPageSummaryCarbonFootprintMain;
import com.esgc.EntityProfile.API.Controllers.EntityProfileClimatePageAPIController;
import com.esgc.EntityProfile.DB.DBQueries.EntityClimateProfilePageQueries;
import com.esgc.EntityProfile.UI.Pages.EntityClimateProfilePage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Database.DatabaseDriver;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.testng.annotations.Test;

import java.time.Year;
import java.util.*;

import static com.esgc.Utilities.Groups.*;

public class EntityClimateProfileSummaryTests extends UITestBase {

    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "Entity")
    @Xray(test = {3548, 3559})
    public void physicalClimateHazards(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        EntityClimateProfilePage entityClimateProfilePage = new EntityClimateProfilePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.navigateToEntity(Entity);
        assertTestCase.assertTrue(entityClimateProfilePage.isPhysicalClimateHazardCardDisplayed(), "Physical Climate Hazards is displayed");
    }

    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "Entity")
    @Xray(test = {3706})
    public void verifyGreenShareRange(String Entity) {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        EntityClimateProfilePage entityClimateProfilePage = new EntityClimateProfilePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.navigateToEntity(Entity);
        String orbisID = entityClimateProfilePage.getEntityOrbisIdFromUI();
        EntityClimateProfilePageQueries entityClimateProfilepagequeries = new EntityClimateProfilePageQueries();
        Map<String, String> data = entityClimateProfilepagequeries.getGreenShareData(orbisID);
        String scoreUI = entityClimateProfilePage.getGreenShareScoreRange();
        assertTestCase.assertTrue(entityClimateProfilePage.verifyScoreAndRange(scoreUI, data.get("SCORE")));
    }

    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "Entity")
    @Xray(test = {4359})
    public void verifyEntityPageSummaryWidgetTest(String Entity) {
        LoginPage loginPage = new LoginPage();
        List<WebElement> check = Driver.getDriver().findElements(By.xpath("//div[@id='RegSector-test-id-1']"));
        System.out.println("check.size() ===> " + check.size());
        if (check.size() == 0) {
            loginPage.login();
        }
        ResearchLinePage researchLinePage = new ResearchLinePage();
        EntityClimateProfilePage entityClimateProfilePage = new EntityClimateProfilePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.navigateToEntity(Entity);
        assertTestCase.assertTrue(entityClimateProfilePage.summaryWidget.isDisplayed(),
                "Entity Summary Widget is displayed");
        assertTestCase.assertNotEquals(entityClimateProfilePage.temperatureAlignmentStatus.getText(), "No Info",
                "Transition Risk Subscriber is verified");
    }

    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "noInfoOrbisID")
    @Xray(test = {4946, 4690})
    public void nonTransitionRiskSubscriberTest(String orbisID) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        EntityClimateProfilePage entityClimateProfilePage = new EntityClimateProfilePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.navigateToFirstEntity(orbisID);
        assertTestCase.assertTrue(entityClimateProfilePage.noUpdatesSummaryWidget.isDisplayed(),
                "Entity Summary Widget is displayed");
        assertTestCase.assertTrue(entityClimateProfilePage.noUpdatesSummaryWidget.getText().contains("Not enough information for a projection."),
                "Entity Summary Widget is verified");
        //assertTestCase.assertEquals(entityClimateProfilePage.temperaturAlignmentStatus.getText(),"No Info","non Transition Risk Subscriber is verified");

        DatabaseDriver.createDBConnection();
        String query = "select * from TEMPERATURE_ALIGNMENT_PROJECTIONS \n" +
                "where data_type in ('Extrapolation based on target','Extrapolation based on target - Intensity') and value>0  and BVD9_NUMBER='" + orbisID + "' \n" +
                "order by TEMPERATURE_ALIGNMENT_YEAR";
        int numberOfMoreEntities = DatabaseDriver.getQueryResultMap(query).size();
        assertTestCase.assertEquals(numberOfMoreEntities, 0, "The company has no projection data and no graph displayed");

    }

    @Test(groups = {REGRESSION, UI, DATA_VALIDATION}, dataProviderClass = DataProviderClass.class, dataProvider = "orbisID")
    @Xray(test = {4690, 4736})
    public void verifyTemperatureAlignmentGraphTest(String orbisID) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        researchLinePage.navigateToFirstEntity(orbisID);
        DatabaseDriver.createDBConnection();
        String query = "select * from TEMPERATURE_ALIGNMENT_PROJECTIONS \n" +
                "where data_type in ('Extrapolation based on target','Extrapolation based on target - Intensity') and value>0  and BVD9_NUMBER='" + orbisID + "' \n";
        List<Map<String, Object>> companyProjections = DatabaseDriver.getQueryResultMap(query);
        System.out.println("companyProjections = " + companyProjections);
        EntityClimateProfilePage entityClimateProfilePage = new EntityClimateProfilePage();
        //Check the sectors of the company in SF.
        assertTestCase.assertTrue(entityClimateProfilePage.isWidgetHave(companyProjections.get(0).get("BENCHMARK_SECTOR").toString()), "Benchmark Sector is verified");

        //SF should pass all the future projections values to UI and not limit till 2030 i.e. pass all the data beyond 2030 as well.
        //UI should be dynamic in order to handle dates beyond 2030
        assertTestCase.assertTrue(verifyForFutureData(companyProjections), "Future data is plotted on the widget");

        //Check the Historical Emission section in the graph, for a company in each sector available in SF.
        for (int i = 1; i < entityClimateProfilePage.historicalLineDots.size() - 1; i++) {
            BrowserUtils.hover(entityClimateProfilePage.historicalLineDots.get(i));
            try {
                System.out.println("Dot Info = " + entityClimateProfilePage.historyLineDotHoverText.getText());
                String year = entityClimateProfilePage.historyLineDotHoverText.getText().split(" ")[3];
                System.out.println("year = " + year);
                String number = entityClimateProfilePage.historyLineDotHoverText.getText().split(" ")[2].replaceAll("Year:", "");
                System.out.println("number = " + number);
                //If there are negative values, then those values should NOT be plotted on the graph.
                assertTestCase.assertTrue(Double.parseDouble(number) > 0, "No negative values are plotted on the graph");
                //Historical data displayed on the graph should be equal to what's available on SF for the chosen company for each sector
                assertTestCase.assertTrue(verifyInDataTable(companyProjections, year), "Year is verified");
                assertTestCase.assertTrue(verifyInDataTable(companyProjections, number), "Number is verified");
            } catch (Exception e) {
                System.out.println("Wrong dot");
            }
        }
    }

    public boolean verifyForFutureData(List<Map<String, Object>> companyProjections) {
        boolean check = true;

        for (Map<String, Object> map : companyProjections) {
            System.out.println("map = " + map);
            System.out.println("map.get(\"TEMPERATURE_ALIGNMENT_YEAR\") = " + map.get("TEMPERATURE_ALIGNMENT_YEAR"));
            if (Integer.parseInt(map.get("TEMPERATURE_ALIGNMENT_YEAR").toString()) > 2030) {
                EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
                if (profilePage.isWidgetHave(map.get("TEMPERATURE_ALIGNMENT_YEAR").toString())) return true;
                else check = false;
            }
        }
        return check;
    }

    public boolean verifyInDataTable(List<Map<String, Object>> companyProjections, String data) {

        for (Map<String, Object> map : companyProjections) {
            if (map.get("TEMPERATURE_ALIGNMENT_YEAR").toString().equals(data)) return true;

            if (Double.parseDouble(map.get("VALUE").toString()) == Double.parseDouble(data)) return true;
        }
        return false;
    }


    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "Entity")
    @Xray(test = {4968, 4308, 4998})
    public void verifyXYAxisTest(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        EntityClimateProfilePage entityClimateProfilePage = new EntityClimateProfilePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.navigateToEntity(Entity);
        assertTestCase.assertTrue(entityClimateProfilePage.summaryWidget.isDisplayed(),
                "Entity Summary Widget is displayed");
        /*
        The graph should contain 3 sets of data
            Legal Entity Historical Value
            Company Target Projected Value Trajectory
            IEA Benchmark Data
                1.5 Degrees Celcius
                2.7 Degrees Celcius
                1.65 Degrees Celcius
        The graph must contain all 3 data set as shown in the data section
         */
        //assertTestCase.assertTrue(entityClimateProfilePage.historicalCompanyEmissions.isDisplayed(),"Historical Company Emissions is displayed");
        //assertTestCase.assertTrue(entityClimateProfilePage.companyTemperatureTarget.isDisplayed(),"Company Target Projected Value Trajectory is displayed");
        //assertTestCase.assertTrue(entityClimateProfilePage.isWidgetHave("1.5"),"ARA - all sectors IEA Scenario - 1.5°C is displayed");
        //assertTestCase.assertTrue(entityClimateProfilePage.isWidgetHave("1.65"),"ARA - all sectors IEA Scenario - 1.65°C is displayed");
        //assertTestCase.assertTrue(entityClimateProfilePage.isWidgetHave("2.7"),"ARA - all sectors IEA Scenario - 2.7°C is displayed");

        assertTestCase.assertTrue(entityClimateProfilePage.verifyIndustrySector(Entity), "Historical Company Emissions is displayed");
        assertTestCase.assertFalse(entityClimateProfilePage.isWidgetHave("2014"), "X Axis values verified");

        //Check the list of 'industry Sector Benchmark'
        //Airlines, Aluminum, Automobiles, Cement, Electric & Gas Utilities, Oil & Gas, Shipping, Steel
        //The graph should have a list of 'Industry Sector Benchmark' that a user can select to compare to their portfolio.


        //The color of the line must be:#1F8CFF - PB 3/15
        assertTestCase.assertTrue(entityClimateProfilePage.historicalLineDots.size() > 0, "Historical Line is verified");
        assertTestCase.assertTrue(entityClimateProfilePage.benchmark15CLineDots.size() > 0, "Benchmark 1.5 °C Line is verified");
        assertTestCase.assertTrue(entityClimateProfilePage.benchmark165CLineDots.size() > 0, "Benchmark 1.65 °C Line is verified");
        assertTestCase.assertTrue(entityClimateProfilePage.benchmark27CLineDots.size() > 0, "Benchmark 2.7 °C Line is verified");


    }

    //JULY
    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "Entity")
    @Xray(test = {5046})
    public void verifyPhysicalRiskManagementCardTest(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.navigateToEntity(Entity);

        EntityClimateProfilePage entityClimateProfilePage = new EntityClimateProfilePage();

        assertTestCase.assertTrue(entityClimateProfilePage.physicalRiskManagementWidget.isDisplayed(), "Physical Risk Management Widget is displayed");
        assertTestCase.assertEquals(entityClimateProfilePage.physicalRiskManagementWidgetTitle.getText(), "Physical Risk Management", "Physical Risk Management Widget Title is verified");
        assertTestCase.assertTrue(entityClimateProfilePage.physicalRiskManagementWidgetDescription.getText().contains("Anticipation, prevention and management of physical impacts of climate change"), "Physical Risk Management Widget SubTitle is verified");
        String actColor = Color.fromString(entityClimateProfilePage.physicalRiskManagementWidgetStatus.getCssValue("background-color")).asHex().toUpperCase();
        String expColor = entityClimateProfilePage.getColorByScoreCategory("Physical Risk Management", entityClimateProfilePage.physicalRiskManagementWidgetStatus.getText());
        if (!actColor.equals(expColor)) {
            System.out.println("Widget Expected Color = " + expColor);
            System.out.println("Widget Background Color = " + actColor);
        }
        assertTestCase.assertEquals(actColor, expColor, "Status name and color is verified");

    }

    //JULY
    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "orbisID")
    @Xray(test = {4649})
    public void verifyPhysicalRiskManagementScoreCategory(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.navigateToFirstEntity(Entity);

        EntityClimateProfilePage entityClimateProfilePage = new EntityClimateProfilePage();

        assertTestCase.assertTrue(entityClimateProfilePage.physicalRiskManagementWidgetStatus.isDisplayed(), "Physical Risk Management Widget is displayed");
        DatabaseDriver.createDBConnection();
        String query = "select BVD9_NUMBER,YEAR,MONTH,GS_PH_RISK_MGT_TOTAL,SCORE_CATEGORY, AS_OF_DATE from df_target.physical_risk_management where BVD9_NUMBER='" + Entity + "' order by AS_OF_DATE DESC";
        List<Map<String, Object>> result = DatabaseDriver.getQueryResultMap(query);
        String status = entityClimateProfilePage.physicalRiskManagementWidgetStatus.getText();
        for (Map<String, Object> row : result) {
            String scoreCategory = row.get("SCORE_CATEGORY").toString().toUpperCase();
            int score = Integer.parseInt(row.get("GS_PH_RISK_MGT_TOTAL").toString());
            assertTestCase.assertEquals(status, scoreCategory, "Status name is verified with SF");
            if (score < 30) assertTestCase.assertEquals(status, "WEAK", "Status name is verified with SF");
            else if (score >= 30 && score < 50)
                assertTestCase.assertEquals(status, "LIMITED", "Status name is verified with SF");
            else if (score >= 50 && score < 60)
                assertTestCase.assertEquals(status, "ROBUST", "Status name is verified with SF");
            else if (score >= 60) assertTestCase.assertEquals(status, "ADVANCED", "Status name is verified with SF");
        }
    }

    //JULY
    @Test(groups = {REGRESSION, UI})
    @Xray(test = {4649})
    public void VerifyCategoryScoreWhenNoDataForCurrentMonth() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        String Entity = "017721867";
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.navigateToFirstEntity(Entity);

        EntityClimateProfilePage entityClimateProfilePage = new EntityClimateProfilePage();

        assertTestCase.assertTrue(entityClimateProfilePage.physicalRiskManagementWidgetNoData.isDisplayed(), "Physical Risk Management Widget is displayed");

        DatabaseDriver.createDBConnection();
        String query = "select BVD9_NUMBER,YEAR,MONTH,GS_PH_RISK_MGT_TOTAL,SCORE_CATEGORY, AS_OF_DATE from df_target.physical_risk_management where BVD9_NUMBER='" + Entity + "' order by AS_OF_DATE DESC";
        Map<String, Object> result = new HashMap<>();
        try {
            result = DatabaseDriver.getRowMap(query);
        } catch (Exception e) {
            System.out.println("No data for current entity");
        }
        assertTestCase.assertTrue(result.isEmpty(), "No data for entity is verified");
    }

    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "orbisID")
    @Xray(test = {4909, 4916, 4924, 4653})
    public void verifyTemperatureAlignmentGraphTitleTest(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.navigateToFirstEntity(Entity);

        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        BrowserUtils.waitForVisibility(profilePage.temperatureAlignmentGraphTitle, 10);
        assertTestCase.assertTrue(profilePage.temperatureAlignmentGraphTitle.isDisplayed(), "Temperature Alignment Graph is displayed");
        String previousYear = Year.now().minusYears(1).toString();
        System.out.println("previousYear = " + previousYear);
        String currentYear = Year.now().toString();
        System.out.println("currentYear = " + currentYear);
        assertTestCase.assertFalse(profilePage.isWidgetHave(previousYear), "Temperature Alignment Graph is not displayed for previous year");
        assertTestCase.assertTrue(profilePage.isWidgetHave(currentYear), "Temperature Alignment Graph is displayed for current year");
        //Verify that there is no grey layover on the graph for the future data is not automatable due to such thing doesnt exist
        assertTestCase.assertTrue(profilePage.temperatureAlignmentGraphFooter.isDisplayed(), "Temperature Alignment Graph footer is displayed");
        assertTestCase.assertTrue(profilePage.verifyTemperatureAlignmentGraphFooter(), "Temperature Alignment Graph footer is verified");
        //Verify the design for the description(footer) is not automated due to description image is not clear and some items are not automate-able]
        //We dont automate font size and style
        //
    }

    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "orbisID")
    @Xray(test = {4592})
    public void verifyLegendsForTemperatureAlignmentGraphTest(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.navigateToFirstEntity(Entity);

        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        profilePage.navigateToTransitionRisk();

        BrowserUtils.wait(5);
        assertTestCase.assertTrue(profilePage.isWidgetHave("Company Target Emissions Projection"), "Temperature Alignment Graph - Legend Title is displayed");
        assertTestCase.assertTrue(profilePage.verifyTemperatureAlignmentGraphLegends(), "Temperature Alignment Graph legends are verified");
        assertTestCase.assertTrue(profilePage.verifyLineDots(profilePage.historicalLineDots), "Historical line dots and tooltips are verified");
    }

    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "orbisID")
    @Xray(test = {4792, 4411, 4563, 5047, 4321})
    public void verifyUnderlyingDataPresentForCarbonFootprintSummaryWidget(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToFirstEntity(Entity);
        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        profilePage.navigateToTransitionRisk();
        assertTestCase.assertTrue(profilePage.verifyTransitionRiskItems(), "Transition Risk items are verified");
        assertTestCase.assertTrue(profilePage.verifyCarbonFootprintWidgetItems(),
                "Transition Risk - Carbon Footprint Summary Widget items are verified");
        assertTestCase.assertTrue(profilePage.verifyCarbonFootprintWidgetValue(Entity),
                "Transition Risk - Carbon Footprint Summary Widget values are verified");
    }

    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "noInfoOrbisID")
    @Xray(test = {4394})
    public void verifyNoInfoDisplayedTemperatureAlignmentWidgetTest(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        BrowserUtils.wait(5);
        researchLinePage.navigateToFirstEntity(Entity);

        //Verify Carbon Footprint No Info in Summary

        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        BrowserUtils.wait(5);
        profilePage.navigateToTransitionRisk();

        assertTestCase.assertTrue(profilePage.verifyTempratureAlignmentForNoInfoValue(Entity),
                "Transition Risk - Temprature Alignment Summary Widget values are verified");
    }

    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "orbisID")
    @Xray(test = {4435})
    public void verifyDisplaySectorComparisonChartForMarketRisk(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.navigateToFirstEntity(Entity);

        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        profilePage.navigateToPhysicalRisk();

        //BrowserUtils.wait(5);
        assertTestCase.assertTrue(profilePage.selectComparisonChartRiskType("Market Risk"), "Market Risk is selected");
        assertTestCase.assertTrue(profilePage.verifyMarketRiskComparisonChart(Entity, "Market Risk"),
                "Market Risk Comparison Chart is verified for chart title, and legends");

    }

    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "orbisID")
    @Xray(test = {4901})
    public void verifySectorComparisonChartContentMarketRisk(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.navigateToFirstEntity(Entity);

        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        profilePage.navigateToPhysicalRisk();
        //BrowserUtils.wait(5);
        assertTestCase.assertTrue(profilePage.selectComparisonChartRiskType("Market Risk"), "Market Risk is selected");
        assertTestCase.assertTrue(profilePage.verifyComparisonChartContent(Entity, "Market Risk"),
                "Market Risk Comparison Chart is verified for chart CONTENT!");

    }

    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "orbisID")
    @Xray(test = {4532})
    public void verifySectorComparisonChartUnderlyingDataForMarketRiskTest(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.navigateToFirstEntity(Entity);

        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        profilePage.navigateToPhysicalRisk();
        //BrowserUtils.wait(5);
        assertTestCase.assertTrue(profilePage.selectComparisonChartRiskType("Market Risk"), "Market Risk is selected");
        assertTestCase.assertTrue(profilePage.verifyComparisonChartBlueBar(Entity),
                "Market Risk Comparison Chart - Blue bar data is verified!");

        assertTestCase.assertTrue(profilePage.verifyComparisonChartNthBar(Entity, profilePage.comparisonChartBars.size()),
                "Market Risk Comparison Chart - Last bar data is verified!");

        assertTestCase.assertTrue(profilePage.verifyComparisonChartNthBar(Entity, 10),
                "Market Risk Comparison Chart - First bar data is verified!");

        //todo: there is bug in the underlying data for the chart on the first bar
//        assertTestCase.assertTrue(profilePage.verifyComparisonChartNthBar(Entity,1),
//                "Market Risk Comparison Chart - First bar data is verified!");
    }

    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "orbisID")
    @Xray(test = {3614})
    public void verifyDisplaySectorComparisonChartForSupplyChainRisk(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.navigateToFirstEntity(Entity);

        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        profilePage.navigateToPhysicalRisk();
        //BrowserUtils.wait(5);
        assertTestCase.assertTrue(profilePage.selectComparisonChartRiskType("Supply Chain Risk"), "Supply Chain Risk is selected");
        assertTestCase.assertTrue(profilePage.verifyMarketRiskComparisonChart(Entity, "Supply Chain Risk"),
                "Supply Chain Comparison Chart is verified for chart title, and legends");

    }

    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "orbisID")
    @Xray(test = {3478})
    public void verifySectorComparisonChartContentSupplyChainRisk(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.navigateToFirstEntity(Entity);

        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        profilePage.navigateToPhysicalRisk();
        //BrowserUtils.wait(5);
        assertTestCase.assertTrue(profilePage.selectComparisonChartRiskType("Supply Chain Risk"), "Supply Chain Risk is selected");
        assertTestCase.assertTrue(profilePage.verifyComparisonChartContent(Entity, "Supply Chain Risk"),
                "Supply Chain Risk Comparison Chart is verified for chart CONTENT!");

    }

    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "orbisID")
    @Xray(test = {4532})
    public void verifySectorComparisonChartUnderlyingDataForSupplyChainRiskTest(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.navigateToFirstEntity(Entity);

        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        profilePage.navigateToPhysicalRisk();
        //BrowserUtils.wait(5);
        assertTestCase.assertTrue(profilePage.selectComparisonChartRiskType("Supply Chain Risk"), "Supply Chain Risk is selected");
        assertTestCase.assertTrue(profilePage.verifyComparisonChartForSupplyChain(Entity),
                "Supply Chain  Risk Comparison Chart data is verified!");


    }

    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "orbisID")
    @Xray(test = {3486})
    public void verifySectorComparisonChartUnderlyingDataAverageForSupplyChainRiskTest(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.navigateToFirstEntity(Entity);

        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        profilePage.navigateToPhysicalRisk();
        //BrowserUtils.wait(5);
        assertTestCase.assertTrue(profilePage.selectComparisonChartRiskType("Supply Chain Risk"), "Supply Chain Risk is selected");
        assertTestCase.assertTrue(profilePage.verifyComparisonChartForSupplyChainRiskAverage(Entity),
                "Supply Chain Risk Comparison Chart - Average data is verified!");
    }

    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "orbisID")
    @Xray(test = {4332})
    public void verifyDataForOperationRiskUnderPhysicalRiskTabTest(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.navigateToFirstEntity(Entity);

        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        profilePage.navigateToPhysicalRisk();
        //BrowserUtils.wait(5);
        profilePage.navigateToPhysicalRisk();
        assertTestCase.assertTrue(profilePage.verifyPhysicalRiskItems(), "Physical Risk Items are verified!");
        assertTestCase.assertTrue(profilePage.verifyOperationsRiskTableUnderlyingData(Entity),
                "Operations Risk Comparison Chart - Average data is verified!");
    }

    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "orbisID")
    @Xray(test = {4930})
    public void verifyDataForMarketRiskUnderPhysicalRiskTabTest(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        researchLinePage.navigateToFirstEntity(Entity);

        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        profilePage.navigateToPhysicalRisk();
        assertTestCase.assertTrue(profilePage.verifyPhysicalRiskItems(), "Physical Risk Items are verified!");
        assertTestCase.assertTrue(profilePage.verifyMarketRiskTableUnderlyingData(Entity),
                "Market Risk Comparison Chart - Average data is verified!");
    }

    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "orbisID")
    @Xray(test = {4717})
    public void verifyDataForSupplyChainRiskUnderPhysicalRiskTabTest(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        researchLinePage.navigateToFirstEntity(Entity);

        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        profilePage.navigateToPhysicalRisk();
        assertTestCase.assertTrue(profilePage.verifyPhysicalRiskItems(), "Physical Risk Items are verified!");
        assertTestCase.assertTrue(profilePage.verifySupplyChainRiskTableUnderlyingData(Entity),
                "Supply Chain Risk Comparison Chart - Average data is verified!");
    }

    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "orbisID")
    @Xray(test = {4739})
    public void verifyPhysicalClimateRiskSubscriberAbleToSeeThreeResearchLinesTest(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.navigateToFirstEntity(Entity);

        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        profilePage.navigateToPhysicalRisk();
        //BrowserUtils.wait(5);
        assertTestCase.assertTrue(profilePage.verifyPhysicalRiskItems(),
                "Verification of Market Risk, Supply Chain Risk, Operations Risk labels");
    }

    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "orbisID")
    @Xray(test = {4911})
    public void verifyUIOfOperationsRiskTableTest(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.navigateToFirstEntity(Entity);

        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        profilePage.navigateToPhysicalRisk();
        //BrowserUtils.wait(5);
        assertTestCase.assertTrue(profilePage.verifyPhysicalRiskItems(),
                "Verification of Market Risk, Supply Chain Risk, Operations Risk labels");

        assertTestCase.assertTrue(profilePage.verifyOperationsRiskTableForUI(),
                "Verification of Operations Risk table for UI");
    }

    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "orbisID")
    @Xray(test = {4726})
    public void verifyUIOfMarketRiskTableTest(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.navigateToFirstEntity(Entity);

        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        profilePage.navigateToPhysicalRisk();
        //BrowserUtils.wait(5);
        assertTestCase.assertTrue(profilePage.verifyPhysicalRiskItems(),
                "Verification of Market Risk, Supply Chain Risk, Operations Risk labels");

        assertTestCase.assertTrue(profilePage.verifyMarketRiskTableForUI(),
                "Verification of Market Risk table for UI");
    }

    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "orbisID")
    @Xray(test = {4457})
    public void verifyUIOfSupplyChainRiskTableTest(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.navigateToFirstEntity(Entity);

        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        profilePage.navigateToPhysicalRisk();
        //BrowserUtils.wait(5);
        assertTestCase.assertTrue(profilePage.verifyPhysicalRiskItems(),
                "Verification of Market Risk, Supply Chain Risk, Operations Risk labels");

        assertTestCase.assertTrue(profilePage.verifySupplyChainRiskTableForUI(),
                "Verification of Supply Chain Risk table for UI");
    }

    @Test(groups = {REGRESSION, UI})
    @Xray(test = {3707})
    public void verifyBrownShareforNAScores() {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        Map<String, String> data = profilePage.getBrownShareNAData();

        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");

        researchLinePage.navigateToFirstEntity(data.get("BVD9_NUMBER"));
        BrowserUtils.wait(5);
        assertTestCase.assertTrue(profilePage.getBrownShareSummaryValue().equals(data.get("SCORE_RANGE")), "Validating Score Range Value");

    }

    @Test(groups = {REGRESSION, UI})
    @Xray(test = {4431})
    public void verifyCarbonFootprintTooltip() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        BrowserUtils.wait(5);
        researchLinePage.navigateToFirstEntity("Apple, Inc.");
        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        // Validating Summary Widget
        profilePage.verifyCarbonFootPrintToolTip(profilePage.carbonFootprintLineItemUnderCarbonFootPrintSummaryWidget);
        //validating Under Transition Risk Tab
        profilePage.navigateToTransitionRisk();
        profilePage.verifyCarbonFootPrintToolTip(profilePage.transitionRiskCarbonFootprintWidgetItems.get(0));
        System.out.println("validated the Tool Tip of Carbon Footprint");
    }

    @Test(groups = {REGRESSION, UI},
            dataProviderClass = DataProviderClass.class, dataProvider = "orbisIDWithBrownShareScore")
    @Xray(test = {4380, 4980, 4382, 4486, 4316, 4325})
    public void verifyBrownShareUnderTransitionRisk(String orbisID) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToFirstEntity(orbisID);
        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        profilePage.verifyBrownShareWidget();
        List<String> metrics = Arrays.asList(new String[]{"Fossil fuels industry - Upstream", "Fossil fuels industry - Midstream", "Fossil fuels industry - Generation",
                "Coal in electricity fuel mix", "Total Oil Potential Emissions(Gt CO2)", "Total Natural Gas Potential Emissions(Gt CO2)"});
        profilePage.verifyBrownShareFootPrintTable(metrics);
        profilePage.verifyUnderlyingDataForBrownShareWidget(orbisID);
    }

    @Test(groups = {REGRESSION, UI})
    @Xray(test = {4318, 4590})
    public void verifyGreenShareUnderTransitionRisk() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        BrowserUtils.wait(5);
        researchLinePage.navigateToFirstEntity("Exxon Mobil Corp.");
        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        profilePage.verifyGreenShareTableHeadings();
        profilePage.verifyGreenShareTableDataIsSorted();

        System.out.println("validated Green Share table is Sorted");
    }

    @Test(groups = {REGRESSION, UI})
    @Xray(test = {4344})
    public void verifyPhysicalRiskSection() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        BrowserUtils.wait(5);
        researchLinePage.navigateToFirstEntity("Exxon Mobil Corp.");

        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        profilePage.navigateToPhysicalRisk();

        profilePage.verifyPhysicalRiskWidgetHeadings();

        List<String> riskname = Arrays.asList(new String[]{"Supply Chain Risk", "Operations Risk", "Market Risk"});

        for (String r : riskname) {
            Boolean isRiskSelected = profilePage.selectComparisonChartRiskType(r);
            if (isRiskSelected) {
                profilePage.verifyComparisonChartYAxix();
                profilePage.verifyComparisonChartLegendHasNumericvalue();
            }
        }

    }

    @Test(groups = {REGRESSION, UI, SMOKE},
            dataProviderClass = DataProviderClass.class, dataProvider = "orbisIDWithoutTempAlignment")
    @Xray(test = {4940, 4627, 4977, 4154})
    public void verifyNoDisplayMessageForNoData(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        BrowserUtils.wait(2);
        researchLinePage.navigateToFirstEntity(Entity);
        BrowserUtils.wait(5);
        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();

        //Temperature Alignment widget No Info check
        if (profilePage.noInfoElement.size() != 0) {
            BrowserUtils.scrollTo(profilePage.noInfoElement.get(0));
            assertTestCase.assertEquals(profilePage.noInfoElement.get(0).getText(), "No information available.");
            System.out.println("Assertion Successful and message : " + profilePage.noInfoElement.get(0).getText());
        } else {
            System.out.println("There is available data in Temperature Alignment Widget");
        }
        // Temperature Alignment Chart No Info check
        if (profilePage.noInfoElementChart.size() != 0) {
            BrowserUtils.scrollTo(profilePage.noInfoElementChart.get(0));
            assertTestCase.assertEquals(profilePage.noInfoElementChart.get(0).getText(), "Not enough information for a projection.");
            System.out.println("Assertion Successful and message : " + profilePage.noInfoElementChart.get(0).getText());
        } else {
            System.out.println("There is available data in Temperature Alignment Chart");
        }
        //Controversies No Info check
        if (profilePage.controversiesNoInfo.size() != 0) {
            BrowserUtils.scrollTo(profilePage.controversiesNoInfo.get(0));
            assertTestCase.assertEquals(profilePage.controversiesNoInfo.get(0).getText(), "No ESG Incidents to display.");
            System.out.println("Assertion Successful and message : " + profilePage.controversiesNoInfo.get(0).getText());
        } else {
            System.out.println("There is available data in Controversies");
        }

        //Physical Risk Management No Info Check
        int size = Driver.getDriver().findElements(By.xpath("(//div[@id='phyRskMgmClimate-test-id'])[1]/div/div/div/div[2]")).size();
        if (size == 0) {
            WebElement physicalManagementNoInfo = Driver.getDriver().findElement(By.xpath("(//div[@id='phyRskMgmClimate-test-id'])[1]/div/div/div/div"));
            assertTestCase.assertEquals(physicalManagementNoInfo.getText(), "No information available.");
            System.out.println("Assertion Successful for Physical Risk Management ");

        } else {
            System.out.println("There is Physical Risk Management Data in Widget");
        }

    }

    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "noInfoCarbonFootprintOrbisID")
    @Xray(test = {3676, 4729, 4819, 4309})
    public void verifyNoInfoDisplayedCarbonFootPrint(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        BrowserUtils.wait(3);
        researchLinePage.navigateToFirstEntity(Entity);

        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();

        profilePage.navigateToTransitionRisk();
        BrowserUtils.wait(10);
        ///  List<WebElement> carbonFootprintNoInfo = Driver.getDriver().findElements(By.xpath("//div[@id='carbonClimate-test-id']/div/div/div/div/div/div"));
        List<String> scoreCategory = new ArrayList<>(Arrays.asList("HIGH", "INTENSE", "MODERATE", "SIGNIFICANT"));
        //List<WebElement> carbonFootprintValueSummary = Driver.getDriver().findElements(By.xpath("(//div[@id='carbonClimate-test-id'])[1]/div[2]/div/div/span[1]"));
        // List<WebElement> carbonFootprintValueUnderlying = Driver.getDriver().findElements(By.xpath("(//div[@id='carbonClimate-test-id'])[2]/div[2]/div/div/span[1]"));

        System.out.println("Element Size: " + profilePage.carbonFootprintNoInfo.size());
        //Verify Carbon Footprint No Info in Summary
        BrowserUtils.scrollTo(profilePage.carbonFootprintNoInfo.get(0));
        if (!(scoreCategory.contains(profilePage.carbonFootprintNoInfo.get(0).getText()))) {
            //Carbon Footprint : No Info  (header)
            assertTestCase.assertEquals(profilePage.carbonFootprintNoInfo.get(0).getText(), "NO INFO");
            //Carbon Footprint : No Info
            assertTestCase.assertEquals(profilePage.carbonFootprintValueSummary.get(0).getText(), "No Info");
            //Estimated : No Info
            assertTestCase.assertEquals(profilePage.carbonFootprintValueSummary.get(4).getText(), "No Info");
        }

        //Verify Carbon Footprint No Info in Underlying Data
        BrowserUtils.waitForVisibility(profilePage.carbonFootprintNoInfo.get(1), 10);
        BrowserUtils.scrollTo(profilePage.carbonFootprintNoInfo.get(1));
        if (!(scoreCategory.contains(profilePage.carbonFootprintNoInfo.get(1).getText()))) {
            //Carbon Footprint : No Info  (header)
            assertTestCase.assertEquals(profilePage.carbonFootprintNoInfo.get(1).getText(), "NO INFO");
            //Carbon Footprint : No Info
            assertTestCase.assertEquals(profilePage.carbonFootprintValueUnderlying.get(0).getText(), "No Info");
            //Estimated : No Info
            assertTestCase.assertEquals(profilePage.carbonFootprintValueUnderlying.get(6).getText(), "No Info");
        }
    }

    @Test(groups = {REGRESSION, UI, SMOKE}, dataProviderClass = DataProviderClass.class, dataProvider = "noInfoCarbonFootprintOrbisID")
    @Xray(test = {6345, 4729})
    public void verifyNoInfoDisplayedCarbonFootPrint1(String Entity) {
        LoginPage loginPage = new LoginPage();
        if(loginPage.loginButtons.size()>0) {
            loginPage.login();
        }
        ResearchLinePage researchLinePage = new ResearchLinePage();
      /*  test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");*/
        BrowserUtils.wait(3);
        researchLinePage.navigateToFirstEntity(Entity);

        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        profilePage.navigateToTransitionRisk();
        BrowserUtils.wait(10);

        BrowserUtils.scrollTo(profilePage.carbonFootprintValueUnderlying.get(0));
        int value = Integer.parseInt(profilePage.carbonFootprintValueUnderlying.get(0).getText().replaceAll(",", ""));
        if (value < 100000) {
            //light green // Moderate
            assertTestCase.assertEquals(profilePage.carbonFootprintNoInfo.get(1).getText(), "MODERATE");
        } else if (value >= 100000 && value < 1000000) {
            //forest green // Significant
            assertTestCase.assertEquals(profilePage.carbonFootprintNoInfo.get(1).getText(), "SIGNIFICANT");
        } else if (value >= 1000000 && value < 10000000) {
            //brown //High
            assertTestCase.assertEquals(profilePage.carbonFootprintNoInfo.get(1).getText(), "HIGH");
        } else {
            //red //Intense
            assertTestCase.assertEquals(profilePage.carbonFootprintNoInfo.get(1).getText(), "INTENSE");
        }

    }

    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "orbisID")
    @Xray(test = {4965, 4345})
    public void verifyAPIValuesVsUIValues(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToFirstEntity(Entity);

        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();

        //Verifying the Benchmark from Tem Alignment Chart
        for (int i = 1; i < profilePage.temperatureAlignmentCharBenchmark.size(); i++) {
            assertTestCase.assertTrue(profilePage.temperatureAlignmentCharBenchmark.get(i).getText().contains("benchmark"));
        }

        profilePage.navigateToTransitionRisk();

        // Get the UI Values for Summary - Carbon Footprint
        String carbonFootprintUI = profilePage.carbonFootprintValueSummary.get(0).getText();
        String scope1UI = profilePage.carbonFootprintValueSummary.get(1).getText();
        String scope2UI = profilePage.carbonFootprintValueSummary.get(2).getText();
        String scope3UI = profilePage.carbonFootprintValueSummary.get(3).getText();
        String estimatedUI = profilePage.carbonFootprintValueSummary.get(4).getText();


        //Get the API values for Summary - Carbon Footprint
        EntityProfileClimatePageAPIController apiController = new EntityProfileClimatePageAPIController();
        Response response = apiController.getEntityPageResponse(Entity, "climate-summary");
        response.prettyPeek();
        List<EntityPageSummaryCarbonFootprintMain> list = Arrays.asList(response.getBody().as(EntityPageSummaryCarbonFootprintMain[].class));
        String carbonFootprintAPI = String.valueOf(list.get(0).getCarbon_footprint().getCarbon_footprint_value_total());
        String scope1API = String.valueOf(list.get(0).getCarbon_footprint().getEmissions_scope_1());
        String scope2API = String.valueOf(list.get(0).getCarbon_footprint().getEmissions_scope_2());
        String scope3API = String.valueOf(list.get(0).getCarbon_footprint().getEmissions_scope_3());
        String estimatedAPI = list.get(0).getCarbon_footprint().getEstimated();

        //Get the API Values for Underlying Data - Carbon Footprint
        response = apiController.getEntityPageResponse(Entity, "entity-underlying-data-metrics");
        response.prettyPeek();
        ArrayList<?> arrayList = response.then().extract().path(".");
        Map<String, Object> map = (Map<String, Object>) arrayList.get(0);
        Collection<Object> values = map.values();

        List<String> APIValues = new ArrayList<>();
        for (Object t : values)
            APIValues.add(t.toString());

        //Compare UI vs API values
        //Summary
        assertTestCase.assertEquals(carbonFootprintUI.replaceAll(",", ""), carbonFootprintAPI);
        assertTestCase.assertEquals(scope1UI.replaceAll(",", ""), scope1API);
        assertTestCase.assertEquals(scope2UI.replaceAll(",", ""), scope2API);
        assertTestCase.assertEquals(scope3UI.replaceAll(",", ""), scope3API);
        assertTestCase.assertEquals(estimatedUI, estimatedAPI);

        //Underlying Data Section
        int counter = 0;
        for (WebElement we : profilePage.carbonFootprintValueUnderlying) {
            System.out.println("we.getText() = " + we.getText());
            if (counter == 8) continue; // not able to handle Carbon Intensity (tCO2eq/M revenue)10
            if (we.getText().length() > 11) {
                assertTestCase.assertTrue(APIValues.contains(we.getText().trim()));
                counter++;
                continue;
            }
            assertTestCase.assertTrue(APIValues.contains(we.getText().replaceAll(",", "").trim()));
            counter++;
        }

    }

    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "entitlementCheckPrd")
    @Xray(test = {3886, 3884, 4825, 4934, 4857, 4834, 4875, 4396, 4426, 4432, 4474})
    public void verifyEntitlementBaseWidgets(String username, String password, String entitlement) {
        //Trying to log in with only Entitlement User
        LoginPage loginPage = new LoginPage();
        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        List<WebElement> check = Driver.getDriver().findElements(By.xpath("//div[@id='RegSector-test-id-1']"));
        System.out.println("check.size() = " + check.size());
        if (check.size() == 1) {
            profilePage.pressESCKey();
            loginPage.clickOnLogout();
        }

        loginPage.loginWithParams(username, password);
        ResearchLinePage researchLinePage = new ResearchLinePage();
        BrowserUtils.wait(3);
        researchLinePage.navigateToFirstEntity("000411117");//apple


        // profilePage.navigateToTransitionRisk();
        /* Verify that User is able to see just Transition Risk Entitlement */
        if (entitlement.equals("Transition Risk")) {
            assertTestCase.assertTrue(profilePage.tempAlignmentChart.size() > 0);//  Temperature alignment graph
            assertTestCase.assertTrue(profilePage.checkIfTempratureAlignmentWidgetISAvailable());// Temperature alignment card
            assertTestCase.assertTrue(profilePage.checkIfCarbonFootprintWidgetISAvailable());//Carbon Footprint card
            assertTestCase.assertTrue(profilePage.checkIfGreenShareCardISAvailable());// Green Share card
            assertTestCase.assertTrue(profilePage.checkIfBrownShareCardISAvailable());// Brown share card
            assertTestCase.assertTrue(profilePage.transitionRiskTab.isDisplayed());//  Transition Risk tab
        } else if (entitlement.equals("Physical Risk")) {
            /* Verify that User is able to see just Physical Risk Entitlement */
            assertTestCase.assertTrue(profilePage.physicalRiskHazardsCard.isDisplayed()); // Physical Climate Hazards card
            assertTestCase.assertTrue(profilePage.physicalRiskManagementCard.isDisplayed()); //Physical Risk Management card
            assertTestCase.assertTrue(profilePage.physicalRiskTab.isDisplayed()); //Physical Risk tab

        } else if (entitlement.equals("Physical Risk and Transition Risk")) {
            /* Verify that User is able to see just Physical Risk and Transition Risk Entitlements  */
            assertTestCase.assertTrue(profilePage.physicalRiskHazardsCard.isDisplayed()); // Physical Climate Hazards card
            assertTestCase.assertTrue(profilePage.physicalRiskManagementCard.isDisplayed()); // Physical Risk Management card
            assertTestCase.assertTrue(profilePage.physicalRiskTab.isDisplayed()); //Physical Risk tab
            assertTestCase.assertTrue(profilePage.tempAlignmentChart.size() > 0); //Temperature alignment graph
            assertTestCase.assertTrue(profilePage.tempAlignmentCard.isDisplayed()); //Temperature alignment card
            assertTestCase.assertTrue(profilePage.carbonFootprintCard.isDisplayed()); // Carbon Footprint card
            assertTestCase.assertTrue(profilePage.checkIfGreenShareCardISAvailable()); //Green Share card
            assertTestCase.assertTrue(profilePage.checkIfBrownShareCardISAvailable()); //Brown share card
            assertTestCase.assertTrue(profilePage.physicalRiskTab.isDisplayed()); //Transition Risk tab

        } else if (entitlement.equals("Physical Risk, Transition Risk, Corporate ESG and Controversies Entitlements")) {
            /* Verify that User is able to see just Physical Risk, Transition Risk, Corporate ESG and Controversies Entitlements   */
            assertTestCase.assertFalse(profilePage.esgScore.size() > 0); // ESG Overall score
            assertTestCase.assertFalse(profilePage.esgSectorComparisonChart.isDisplayed()); // ESG Sector comparison chart
            assertTestCase.assertFalse(profilePage.esgMateriality.isDisplayed()); //  ESG Materiality tab
            assertTestCase.assertFalse(profilePage.esgSubCategory.isDisplayed()); // Subcategory modal - under ESG Materiality tab
            assertTestCase.assertTrue(profilePage.controversies.isDisplayed()); // Controversies
            assertTestCase.assertTrue(profilePage.physicalRiskHazardsCard.isDisplayed()); // Physical Climate Hazards card
            assertTestCase.assertTrue(profilePage.physicalRiskManagementCard.isDisplayed()); // Physical Risk Management card
            assertTestCase.assertTrue(profilePage.physicalRiskTab.isDisplayed()); // Physical Risk tab
            assertTestCase.assertTrue(profilePage.tempAlignmentChart.size() > 0); // Temperature alignment graph
            assertTestCase.assertTrue(profilePage.tempAlignmentCard.isDisplayed()); //  Temperature alignment card
            assertTestCase.assertTrue(profilePage.carbonFootprintCard.isDisplayed()); // Carbon Footprint card
            assertTestCase.assertTrue(profilePage.checkIfGreenShareCardISAvailable()); // Green Share card
            assertTestCase.assertTrue(profilePage.checkIfBrownShareCardISAvailable()); // Brown share card
            assertTestCase.assertTrue(profilePage.physicalRiskTab.isDisplayed()); // Transition Risk tab

        } else if (entitlement.equals("Physical Risk, Transition Risk and Controversies")) {

            /* Verify that User is able to see Physical Risk, Transition Risk and Controversies Entitlements  */
            assertTestCase.assertTrue(profilePage.physicalRiskHazardsCard.isDisplayed()); //  Physical Climate Hazards card
            assertTestCase.assertTrue(profilePage.physicalRiskManagementCard.isDisplayed()); // Physical Risk Management card
            assertTestCase.assertTrue(profilePage.physicalRiskTab.isDisplayed()); // Physical Risk tab
            assertTestCase.assertTrue(profilePage.tempAlignmentChart.size() > 0); //  Temperature alignment graph
            assertTestCase.assertTrue(profilePage.tempAlignmentCard.isDisplayed()); // Temperature alignment card
            assertTestCase.assertTrue(profilePage.carbonFootprintCard.isDisplayed()); // Carbon Footprint card
            assertTestCase.assertTrue(profilePage.checkIfGreenShareCardISAvailable()); // Green Share card
            assertTestCase.assertTrue(profilePage.checkIfBrownShareCardISAvailable()); // Brown share card
            assertTestCase.assertTrue(profilePage.physicalRiskTab.isDisplayed()); // Transition Risk tab
            assertTestCase.assertTrue(profilePage.controversies.isDisplayed()); // Controversies
        }
        loginPage.clickOnLogout();
    }


    @Test(groups = {REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "Entity")
    @Xray(test = {4386, 4444, 4360, 4387, 4797})
    public void verifyPhysicalRiskDropDownMenusTest(String Entity) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.navigateToFirstEntity(Entity);

        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        profilePage.navigateToPhysicalRisk();
        //The user should see the "Physical Climate Risk: Operations Risk" selected by default when the page loads
        assertTestCase.assertEquals(profilePage.comparisonChartRiskSelectionDropdown.getText(), "Physical Climate Risk: Operations Risk",
                "Physical Risk dropdown menu default RL is verified");

        //Verify the selected research line name
        //The research line should be formatted as a dropdown menu list and other Physical Risk related RLs should be on the same
        assertTestCase.assertTrue(profilePage.verifyComparisonChartRiskTypes(), "Dropdown menu list of Physical Risk RLs is verified");

        //Select a different RL and review the chart box's displayed information
        List<String> expectedRLs = profilePage.getComparisonChartRiskSelectionOptions();
        System.out.println("expectedRLs = " + expectedRLs);
        List<WebElement> expOperationsRiskData = profilePage.facilitiesExposedItems;
        System.out.println("expOperationsRiskData.size() = " + expOperationsRiskData.size());
        List<WebElement> expMarketRiskScoreData = profilePage.marketRiskScores;
        System.out.println("expMarketRiskScoreData.size() = " + expMarketRiskScoreData.size());
        List<WebElement> expSupplyChainRiskData = profilePage.supplyChainRiskScores;
        System.out.println("expSupplyChainRiskData.size() = " + expSupplyChainRiskData.size());
        //Repeat step #7 for the other RL(s)
        for (String rl : expectedRLs) {

            profilePage.selectComparisonChartRiskType(rl);
            assertTestCase.assertEquals(profilePage.comparisonChartRiskSelectionDropdown.getText(), rl, "Selected RL is verified");
            System.out.println("Entity Name on Chart = " + profilePage.comparisonChartLegends.get(3).getText());
            System.out.println("Entity = " + Entity);
            //Physical Risk management chart is not fully ready. "Entity Name on Chart =  SunRun, Inc.: 52"
            if (!rl.equalsIgnoreCase("Physical Risk Management"))
                assertTestCase.assertTrue(profilePage.comparisonChartLegends.get(3).getText().contains(Entity), "The widget displays information in regards of the selected company");
            WebElement lastBar;
            if (profilePage.comparisonChartBarsBig.size() != 0)
                lastBar = profilePage.comparisonChartBarsBig.get(profilePage.comparisonChartBarsBig.size() - 1);
            else
                lastBar = profilePage.comparisonChartBars.get(profilePage.comparisonChartBars.size() - 1);
            BrowserUtils.scrollTo(lastBar);
            BrowserUtils.hover(lastBar);
            WebElement tooltip = Driver.getDriver().findElement(By.xpath("//*[local-name()='g' and @visibility='visible']"));
            System.out.println("tooltip.getText() = " + tooltip.getText());
            String rlName = "";
            if (rl.contains(":")) rlName = rl.split(": ")[1];
            else rlName = rl;

            //The Physical Risk's card is updated and is now reflecting a sector comparison chart associated  to selected research line .
            switch (rlName) {
                case "Operations Risk":
                    assertTestCase.assertTrue(tooltip.getText().contains("Operation Risk Score"), "Operations Risk tooltip is verified");
                    break;
                case "Supply Chain Risk":
                    assertTestCase.assertTrue(tooltip.getText().contains("Supplychain score"), "Supply Chain Risk tooltip is verified");
                    break;
                case "Market Risk":
                    assertTestCase.assertTrue(tooltip.getText().contains("Market risk score"), "Market Risk tooltip is verified");
                    break;
                case "Physical Risk Management":
                    assertTestCase.assertTrue(tooltip.getText().contains("Physical Risk Management Score"), "Physical Risk Management tooltip is verified");
                    break;
                default:
                    System.out.println("RL name is not verified");
            }
            assertTestCase.assertEquals(expOperationsRiskData, profilePage.facilitiesExposedItems, "Operations Risk data is verified");
            assertTestCase.assertEquals(expMarketRiskScoreData, profilePage.marketRiskScores, "Market Risk data is verified");
            assertTestCase.assertEquals(expSupplyChainRiskData, profilePage.supplyChainRiskScores, "Supply Chain Risk data is verified");
        }


    }

    @Test(groups = {REGRESSION, UI})
    @Xray(test = {4720})
    public void verifyPhysicalRiskDropDownMenusForNoDataTest() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        //    test.info("Navigate to Portfolio Analysis page");
        //  researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.navigateToFirstEntity("The Guardian Life Insurance Company of America");

        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        profilePage.navigateToPhysicalRisk();
        //The user should see the "Physical Climate Risk: Operations Risk" selected by default when the page loads
        assertTestCase.assertEquals(profilePage.comparisonChartRiskSelectionDropdown.getText(), "Physical Climate Risk: Operations Risk",
                "Physical Risk dropdown menu default RL is verified");

        //Verify the selected research line name
        //The research line should be formatted as a dropdown menu list and other Physical Risk related RLs should be on the same
        assertTestCase.assertTrue(profilePage.verifyComparisonChartRiskTypes(), "Dropdown menu list of Physical Risk RLs is verified");

        //Select a different RL and review the chart box's displayed information
        List<String> expectedRLs = profilePage.getComparisonChartRiskSelectionOptions();
        System.out.println("expectedRLs = " + expectedRLs);

        //Repeat step #7 for the other RL(s)
        for (String rl : expectedRLs) {
            profilePage.selectComparisonChartRiskType(rl);
            assertTestCase.assertEquals(profilePage.comparisonChartRiskSelectionDropdown.getText(), rl, "Selected RL is verified");
            //assertTestCase.assertTrue(profilePage.noSectorComparisonChartAvailable.isDisplayed(), "No data message is displayed for " + rl);
        }
    }
}