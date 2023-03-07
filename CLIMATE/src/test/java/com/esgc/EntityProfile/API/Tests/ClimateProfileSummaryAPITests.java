package com.esgc.EntityProfile.API.Tests;

import com.esgc.Base.TestBases.EntityClimateProfileTestBase;
import com.esgc.EntityProfile.API.APIModels.SummarySection.*;
import com.esgc.EntityProfile.API.Controllers.EntityProfileClimatePageAPIController;
import com.esgc.Utilities.ESGUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.Groups.*;
import static org.hamcrest.Matchers.*;

public class ClimateProfileSummaryAPITests extends EntityClimateProfileTestBase {

    @Test(groups = {API, REGRESSION, ENTITY_PROFILE}, dataProvider = "API Research Lines")
    @Xray(test = {5995, 6030, 6036, 6042, 7812})
    public void ValidateClimateSummaryAPIResponse(String researchLine, String orbis_id) {
        System.out.println("researchLine = " + researchLine);
        System.out.println("orbis_id = " + orbis_id);
        EntityProfileClimatePageAPIController entityClimateProfileApiController = new EntityProfileClimatePageAPIController();
        test.info("Climate Profile Summary " + researchLine + " widget API validation");
        Response response = entityClimateProfileApiController
                .getClimateSummaryAPIResponse(orbis_id, researchLine);

        response.then().log().all();
        switch (researchLine) {
            case "greenshareasmt":
            case "brownshareasmt":
                response.as(BrownShareAndGreenShareClimateSummary[].class);
                break;
            case "temperaturealgmt":
                response.as(TemperatureAlignmentSummary[].class);
                break;
            case "physicalriskhazard":
                response.as(PhysicalRiskHazardsSummary[].class);
                break;
            case "carbonfootprint":
                response.as(CarbonFootprintSummary[].class);
                break;
            case "physicalriskmanagement":
                response.as(PhysicalRiskManagementSummary[].class);
                break;
        }
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(".", everyItem(is(notNullValue())));

        test.pass("Successfully validated for " + researchLine + "Widget API");
    }


    @Test(groups = {API, REGRESSION, ENTITY_PROFILE}, dataProvider = "API Research Lines")
    @Xray(test = {5996, 6037, 6043, 7813})
    public void ValidateClimateSummaryAPIWithInvalidToken(String researchLine, String orbis_id) {
        System.out.println("researchLine = " + researchLine);
        System.out.println("orbis_id = " + orbis_id);
        test.info("Unauthorised Access : Climate Summary API POST request for " + researchLine + "Widget");
        EntityProfileClimatePageAPIController entityClimateProfileApiController = new EntityProfileClimatePageAPIController();

        Response response = entityClimateProfileApiController
                .getClimateSummaryAPIResponseWithTemperedToken(orbis_id, researchLine);

        response.then().assertThat().statusCode(403);
        response.then().assertThat().body("message", equalTo("Forbidden"));
        test.pass("Successfully validated for " + researchLine + "research line");
    }

    @Test(groups = {API, REGRESSION, ENTITY_PROFILE}, dataProvider = "orbis_id")
    @Xray(test = {6710})
    public void verifyDynamicDataScoresAPITest(String orbis_id) {
        EntityProfileClimatePageAPIController entityClimateProfileApiController = new EntityProfileClimatePageAPIController();
        test.info("Climate Profile Summary " + orbis_id + " widget API validation");
        Response response = entityClimateProfileApiController
                .getClimateTempProjection(orbis_id);

        assertTestCase.assertEquals(response.getStatusCode(), 200, "Climate Temperature Projection API Response status is verified");
        assertTestCase.assertFalse(response.body().asString().isEmpty(), "Climate Temperature Projection API Response body is verified");

    }

    @Test(groups = {API, REGRESSION, ENTITY_PROFILE}, dataProvider = "orbis_id")
    @Xray(test = {8232})
    public void verifyAPIForOverallESGScoreWidget(String orbis_id) {
        EntityProfileClimatePageAPIController entityClimateProfileApiController = new EntityProfileClimatePageAPIController();
        test.info("ESG Score widget API validation for " + orbis_id);
        Response response = entityClimateProfileApiController
                .getESGClimateSummary(orbis_id);
        response.prettyPeek();

        assertTestCase.assertEquals(response.getStatusCode(), 200, "ESG Climate Summary Projection API Response status is verified");
        JsonPath jsonPath = response.jsonPath();
        //verify sub category score is one of Environment, Social, Governance, ESG Score
        List<String> apiCategoryList = jsonPath.getList("esg_assessment[0].score_categories.sub_category");//esg_assessment.categories.sub_category
        String researchLine = jsonPath.getString("esg_assessment[0].research_line_id");//esg_assessment.categories.research_line
        System.out.println("apiCategoryList = " + apiCategoryList);
        System.out.println("researchLine = " + researchLine);
        List<String> expCategoryList = Arrays.asList("Environment", "Social", "Governance", "ESG Score");
        for (String category : apiCategoryList) {
            if (!expCategoryList.contains(category))
                System.out.println("category is not present in the api response = " + category);
            assertTestCase.assertTrue(apiCategoryList.contains(category), "ESG Climate Summary Projection API Category type is verified for " + category);
        }

        //verify Score vs Score Category is as expected
        List<Map<String, Object>> apiCategories = jsonPath.get("esg_assessment[0].score_categories");
        System.out.println("apiCategories.size() = " + apiCategories.size());
        for (Map<String, Object> category : apiCategories) {
            //Verify ESg Score Category
            if (category.get("score").toString().endsWith(".esg")) {
                String expESGScore = new ESGUtilities().getESGAlphanumericScoreCategory(category.get("score").toString());
                if (!expESGScore.equals(category.get("qualifier").toString())) {
                    System.out.println("ESG Score vs Score Category is not verified = " + category.get("qualifier") + " :" + expESGScore);
                }
                assertTestCase.assertEquals(category.get("qualifier").toString(), expESGScore,
                        "ESG Climate Summary Projection API ESG Alphanumeric Score is verified.");
                continue;
            }
            //Verify Pillar Categories
            String expCategory = "";
            if (!category.get("score").toString().contains("ESG")) {
                expCategory = new ESGUtilities().getESGPillarsCategory(researchLine, (int) Double.parseDouble(category.get("score").toString()));
                System.out.println("expCategory = " + expCategory);
                List<String> expCategoryScoreRanges = new ESGUtilities().getESGPillarScoreRangeCategory(researchLine);
                System.out.println("expCategoryScoreRange = " + expCategoryScoreRanges);
                if (!category.get("score_category").equals(expCategory)) {
                    System.out.println("Category Score vs. Category Name mismatch = " + category.get("score_category") + " vs " + expCategory);
                }
                assertTestCase.assertEquals(category.get("score_category"), expCategory, "ESG Climate Summary Projection API Response body for score vs score category is verified");
                if (!expCategoryScoreRanges.contains(category.get("score_range").toString()))
                    System.out.println("Score Range in API is not a valid range = " + category.get("score_range"));
                assertTestCase.assertTrue(expCategoryScoreRanges.contains(category.get("score_range").toString()), "ESG Climate Summary Projection API Response body for Score Range is verified");
            }
        }
    }

    @Test(groups = {API, REGRESSION, ENTITY_PROFILE})
    @Xray(test = {6710})
    public void verifyPostAPIRequestForTemperatureAlignmentGraphWithInvalidTokenTest() {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://solutions-qa.mra-esg-nprd.aws.moodys.tld/api/portfolio/00000000-0000-0000-0000-000000000000/transitionrisk/temperaturealgmt/sector-temp-rise";
        Response response = RestAssured.given().accept(ContentType.JSON).when().contentType(ContentType.JSON).get();
        response.prettyPrint();
        assertTestCase.assertEquals(response.getStatusCode(), 403,
                "Temperature Alignment Graph Post request with invalid token  API Response status is verified");
        assertTestCase.assertEquals(response.path("message"), "Forbidden",
                "Temperature Alignment Graph Post request with invalid token  API Response message is verified");

    }

    @DataProvider(name = "orbis_id")
    public Object[][] orbisIdProvider() {

        return new Object[][]{
                {"051058092"},
                //{"108645382"},
                {"001812590"},
                {"006105314"},
                {"000411117"}
        };
    }

}
