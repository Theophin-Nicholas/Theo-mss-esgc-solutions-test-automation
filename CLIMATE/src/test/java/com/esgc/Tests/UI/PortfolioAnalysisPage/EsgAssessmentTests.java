package com.esgc.Tests.UI.PortfolioAnalysisPage;

import com.esgc.APIModels.APIFilterPayload;
import com.esgc.Controllers.APIController;
import com.esgc.Pages.DashboardPage;
import com.esgc.Pages.ResearchLinePage;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Database.DatabaseDriver;
import com.esgc.Utilities.DateTimeUtilities;
import com.esgc.Utilities.ESGUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.testng.annotations.Test;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EsgAssessmentTests extends UITestBase {

    @Test(groups = {"regression", "ui", "smoke", "esg"},
            description = "Verify ESG Weighted Average Score Data Validation")
    @Xray(test = {8176})
    public void verifyESGWeightedAverageScoreDataValidationTest() {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToResearchLine("ESG Assessments");
        test.info("Navigated to ESG Assessments Page");

        assertTestCase.assertTrue(researchLinePage.validateIfEsgCardInfoIsDisplayed(), "Verify ESG Card Info with coverage details");

        //ESG Score Widget should be displayed
        assertTestCase.assertTrue(researchLinePage.esgCardInfoBox.isDisplayed(), "Verify ESG Score Widget is displayed");
        //ESG score scale category should be displayed: Weak, Limited, Robust, Advanced
        List<String> availableESGScoresScaleCategories = new ArrayList<>(Arrays.asList("Weak", "Limited", "Robust", "Advanced"));
        System.out.println("UI Data = " + researchLinePage.esgCardInfoBoxScore.getText());
//        assertTestCase.assertTrue(availableESGScoresScaleCategories.contains(researchLinePage.esgCardInfoBoxScore.getText()), "Verify ESG Score Scale Category is displayed");

        //The color of the Category should be:
        //Weak	#DD581D
        //Limited	#E8951C
        //Robust	#EAC550
        //Advanced	#DBE5A3
        String expectedColor = researchLinePage.getColorByScoreCategory("ESG",researchLinePage.esgCardInfoBoxScore.getText());
        String actualColor = Color.fromString(researchLinePage.esgCardInfoBoxScore.getCssValue("background-color")).asHex().toUpperCase();
        System.out.println("actualColor = " + actualColor);
        assertTestCase.assertEquals(actualColor, expectedColor, "Verify ESG Score Scale Category Color");
    }

    @Test(groups = {"regression", "ui", "esg"},
            description = "Verify displayed data for ESG is matching with SF data")
    @Xray(test = {8178})
    public void verifyESGDisplayedDataIsMatchingWithSFDataTest() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();

        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToResearchLine("ESG Assessments");
        test.info("Navigated to ESG Assessments Page");

        assertTestCase.assertTrue(researchLinePage.validateIfEsgCardInfoIsDisplayed(), "Verify ESG Card Info with coverage details");

        //ESG Score Widget should be displayed
        assertTestCase.assertTrue(researchLinePage.esgCardInfoBox.isDisplayed(), "Verify ESG Score Widget is displayed");
        assertTestCase.assertTrue(researchLinePage.esgCardEntitiyLink.isDisplayed(), "Verify ESG Score link is displayed");
        List<String> scores = new ArrayList<>(Arrays.asList(researchLinePage.esgCardEntitiyLink.getText().split("\\D+")));
        System.out.println("scores = " + scores);

        //Verify ESG portfolio score category comparison UI vs API
        APIController apiController = new APIController();
        APIFilterPayload apiFilterPayload = new APIFilterPayload();
        apiFilterPayload.setYear(DateTimeUtilities.getCurrentYear());
        apiFilterPayload.setMonth(DateTimeUtilities.getCurrentMonthNumeric());
        Response response =  apiController.getPortfolioScoreResponse("00000000-0000-0000-0000-000000000000", "ESG Assessments", apiFilterPayload);
        response.prettyPrint();
        assertTestCase.assertEquals(response.getStatusCode(), 200, "Verify ESG API Response Status Code");
        //response.as(ESGScore.class);
        String apiScoreCategory = response.jsonPath().getString("portfolio_score[0].category").replaceAll("\\W", "");
        System.out.println("apiScoreCategory = " + apiScoreCategory);
        assertTestCase.assertEquals(researchLinePage.esgCardInfoBoxScore.getText(), apiScoreCategory, "Verify ESG Score Category UI vs API");

        //Verify ESG score Category API vs SF
        String query="WITH p AS\n" +
                "(\n" +
                "       SELECT *\n" +
                "       FROM   df_portfolio df\n" +
                "       WHERE  portfolio_id='00000000-0000-0000-0000-000000000000' ), \n" +
                "esg AS\n" +
                "(\n" +
                "         SELECT   eos.*\n" +
                "         FROM     entity_coverage_tracking ect\n" +
                "         JOIN     esg_overall_scores eos\n" +
                "         ON       ect.orbis_id=eos.orbis_id\n" +
                "         AND      data_type = 'esg_pillar_score'\n" +
                "         AND      sub_category = 'ESG'\n" +
                "         AND      eos.year\n" +
                "                           || eos.month <= '"+DateTimeUtilities.getCurrentYear()+DateTimeUtilities.getCurrentMonthNumeric()+"'\n" +
                "         WHERE    coverage_status = 'Published'\n" +
                "         AND      publish = 'yes' qualify row_number() OVER (partition BY eos.orbis_id ORDER BY eos.year DESC, eos.month DESC, eos.scored_date DESC) =1 )\n" +
                "SELECT *, p.value as esg_value, esg.value as esg_score_value\n" +
                "FROM   p\n" +
                "left JOIN   esg\n" +
                "ON     p.bvd9_number=esg.orbis_id where esg.value>=0";
        DatabaseDriver.createDBConnection();
        List<Map<String,Object>> resultMap = DatabaseDriver.getQueryResultMap(query);

        int totalScore = 0;//resultMap.stream().mapToInt(map -> Integer.parseInt(map.get("esg_value").toString())).sum();

        for(Map<String,Object> map : resultMap){
            if(map.get("ESG_VALUE")!=null){
                totalScore += Integer.parseInt(map.get("ESG_VALUE").toString());
            }
        }
        System.out.println("totalScore = " + totalScore);
        
        double inv_percentage = 0;
        double weighted_avg_total = 0;

        for(Map<String,Object> map : resultMap){
            if(map.get("ESG_SCORE_VALUE")!=null){
                double singleEntityPercentage =  Double.parseDouble(map.get("ESG_VALUE").toString())/totalScore;
                inv_percentage += singleEntityPercentage;
                weighted_avg_total+=singleEntityPercentage* ESGUtilities.getESGPillarsScale(map.get("RESEARCH_LINE_ID").toString(), Integer.parseInt(map.get("ESG_SCORE_VALUE").toString()));
            }
        }
        System.out.println("weighted_avg_total = " + weighted_avg_total);
        System.out.println("inv_percentage = " + inv_percentage);
        //Verify ESG weighted_avg value API vs SF
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.HALF_UP);


        String apiWeightedAvgScore =  response.jsonPath().getString("portfolio_score[0].weighted_avg");
        System.out.println("apiWeightedAvgScore = " + apiWeightedAvgScore);
        System.out.println("df.format(weighted_avg_total) = " + df.format(weighted_avg_total));
        assertTestCase.assertTrue(apiWeightedAvgScore.contains(df.format(weighted_avg_total)),"ESG weighted_avg value is verified based on API vs SF");
    }

    @Test(groups = {"regression", "ui", "esg"},
            description = "Verify ESG Weighted Average Score Data Validation")
    @Xray(test = {9084,9086,9083})
    public void verifyGeographicAndSectorDistributionSectionComponentsTest() {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToResearchLine("ESG Assessments");
        test.info("Navigated to ESG Assessments Page");

        assertTestCase.assertTrue(researchLinePage.validateIfEsgCardInfoIsDisplayed(), "Verify ESG Card Info with coverage details");

        //ESG Score Widget should be displayed
        assertTestCase.assertTrue(researchLinePage.esgCardInfoBox.isDisplayed(), "Verify ESG Score Widget is displayed");

        //Title of the section should be "Sector and Geographic Distribution".
        assertTestCase.assertTrue(researchLinePage.geoSectionTitle.isDisplayed(), "Sector and Geographic Distribution Section Title is displayed");
        BrowserUtils.scrollTo(researchLinePage.geoSectionTitle);
        assertTestCase.assertEquals(researchLinePage.geoSectionTitle.getText(), "Sector and Geographic Distribution", "Sector and Geographic Distribution Section Title is verified");

        assertTestCase.assertTrue(researchLinePage.IsGeoSectionTreeMapAvailable(),"Validate if Tree map section is available");
        //verify Geo Table Headers
        assertTestCase.assertEquals(researchLinePage.geoTableHeaders.size(), 3, "Verify Geo Table Headers");
        assertTestCase.assertEquals(researchLinePage.geoTableHeaders.get(0).getText(), "Country", "Verify Geo Table Headers");
        assertTestCase.assertEquals(researchLinePage.geoTableHeaders.get(1).getText(), "% Investment", "Verify Geo Table Headers");
        assertTestCase.assertEquals(researchLinePage.geoTableHeaders.get(2).getText(), "ESG Score", "Verify Geo Table Headers");

        //verify Geo Table Data
        assertTestCase.assertTrue(researchLinePage.geoTableCountryList.size() > 0, "Verify Geo Table Data");
        assertTestCase.assertTrue(researchLinePage.geoTableInvPercentagesList.size() > 0, "Verify Geo Table Data");
        assertTestCase.assertTrue(researchLinePage.geoTableIESGScoreList.size() > 0, "Verify Geo Table Data");

        for(WebElement country : researchLinePage.geoTableCountryList){
            try{
                System.out.println("Verifying drawer for country: " + country.getText());
                BrowserUtils.scrollTo(country);
                country.click();
                if(!researchLinePage.geoSectionDrawerTitle.isDisplayed()) country.click();
                assertTestCase.assertTrue(researchLinePage.geoSectionDrawerTitle.isDisplayed(), "Verify Geo Section Drawer Title is displayed for " + country.getText());
                researchLinePage.geoSectionHideButton.click();
            } catch (Exception e){

                if(researchLinePage.geoSectionDrawerTitle.isDisplayed())
                researchLinePage.geoSectionHideButton.click();
            }


        }
    }

    @Test(groups = {"regression", "ui", "smoke", "esg"})
    @Xray(test = {8704})
    public void verifyESGGradeDistributionIsDisplayed() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine("ESG Assessments");
        test.info("Navigated to ESG Assessments Page");
        researchLinePage.validateEsgGradeDistribution();
    }

    @Test(enabled = false,groups = {"regression", "ui", "smoke", "esg"})
    @Xray(test = {9133})
    public void verifyESGGRegionMapAndCountryTableDrawer() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine("ESG Assessments");
        test.info("Navigated to ESG Assessments Page");
        researchLinePage.validateCountry();
    }
}
