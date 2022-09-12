package com.esgc.Tests.UI.PortfolioAnalysisPage;

import com.esgc.APIModels.APIFilterPayload;
import com.esgc.APIModels.ESGScore;
import com.esgc.Controllers.APIController;
import com.esgc.Controllers.EntityPage.EntityProfileClimatePageAPIController;
import com.esgc.Pages.ResearchLinePage;
import com.esgc.Tests.TestBases.DataValidationTestBase;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Database.DatabaseDriver;
import com.esgc.Utilities.DateTimeUtilities;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Xray;
import com.esgc.Utulities.ESGUtilities;
import io.restassured.response.Response;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.testng.annotations.Test;

import java.awt.*;
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
        assertTestCase.assertTrue(availableESGScoresScaleCategories.contains(researchLinePage.esgCardInfoBoxScore.getText()), "Verify ESG Score Scale Category is displayed");

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
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToResearchLine("ESG Assessments");
        test.info("Navigated to ESG Assessments Page");

        assertTestCase.assertTrue(researchLinePage.validateIfEsgCardInfoIsDisplayed(), "Verify ESG Card Info with coverage details");

        //ESG Score Widget should be displayed
        assertTestCase.assertTrue(researchLinePage.esgCardInfoBox.isDisplayed(), "Verify ESG Score Widget is displayed");
        assertTestCase.assertTrue(researchLinePage.esgCardEntitiyLink.isDisplayed(), "Verify ESG Score link is displayed");
        List<String> scores = new ArrayList<>(Arrays.asList(researchLinePage.esgCardEntitiyLink.getText().split("\\D+")));
        System.out.println("scores = " + scores);
        //Verify ESG score UI vs API
        APIController apiController = new APIController();
        APIFilterPayload apiFilterPayload = new APIFilterPayload();
        apiFilterPayload.setYear(DateTimeUtilities.getCurrentYear());
        apiFilterPayload.setMonth(DateTimeUtilities.getCurrentMonthNumeric());
        Response response =  apiController.getPortfolioScoreResponse("00000000-0000-0000-0000-000000000000", "ESG Assessments", apiFilterPayload);
        assertTestCase.assertEquals(response.getStatusCode(), 200, "Verify ESG API Response Status Code");
        //response.as(ESGScore.class);
        String apiScoreCategory = response.jsonPath().getString("portfolio_score[0].category").replaceAll("\\W", "");
        System.out.println("apiScoreCategory = " + apiScoreCategory);
        assertTestCase.assertEquals(researchLinePage.esgCardInfoBoxScore.getText(), apiScoreCategory, "Verify ESG Score Category UI vs API");

        //Verify ESG score Category API vs SF

        String query="with p as (\n" +
                "     select * from df_portfolio df where portfolio_id='00000000-0000-0000-0000-000000000000'\n" +
                " ),\n" +
                "  esg as (\n" +
                "    select eos.* from entity_coverage_tracking ect  \n" +
                "join ESG_OVERALL_SCORES eos on ect.orbis_id=eos.orbis_id and data_type = 'esg_pillar_score'  and sub_category = 'ESG' and eos.year || eos.month <= '202208' //and eos.RESEARCH_LINE_ID=1015\n" +
                " where  coverage_status = 'Published' and publish = 'yes'\n" +
                " qualify row_number() OVER (PARTITION BY eos.orbis_id ORDER BY eos.year DESC, eos.month DESC, eos.scored_date DESC) =1\n" +
                "    )\n" +
                "     select * from p\n" +
                "    join esg on p.bvd9_number=esg.orbis_id; ";
        DatabaseDriver.createDBConnection();
        List<Map<String,Object>> resultMap = DatabaseDriver.getQueryResultMap(query);
        int numberOfEntities = resultMap.size();
        int totalScore = resultMap.stream().mapToInt(map -> Integer.parseInt(map.get("VALUE").toString())).sum();
        System.out.println("totalScore = " + totalScore);
        System.out.println("numberOfEntities = " + numberOfEntities);
        int avgEsgScore = Math.round((float) totalScore/numberOfEntities);
        System.out.println("avgEsgScore = " + avgEsgScore);
        String sfScoreCategory = ESGUtilities.getESGPillarsCategory(resultMap.get(0).get("RESEARCH_LINE_ID").toString(), avgEsgScore);
        System.out.println("esgScoreCategory = " + sfScoreCategory);
        assertTestCase.assertEquals(apiScoreCategory, sfScoreCategory, "Verify ESG Score Category API vs SF");
        assertTestCase.assertEquals(scores.get(1), numberOfEntities, "Verify ESG Score Number of Entities UI vs SF");

        //send quesry to serializeResearchLines and get list of ResearchLineIdentifier
        //send listof ResearchLineIdentifier to


    }

    @Test(groups = {"regression", "ui", "esg"},
            description = "Verify ESG Weighted Average Score Data Validation")
    @Xray(test = {9084,9086})
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
}
