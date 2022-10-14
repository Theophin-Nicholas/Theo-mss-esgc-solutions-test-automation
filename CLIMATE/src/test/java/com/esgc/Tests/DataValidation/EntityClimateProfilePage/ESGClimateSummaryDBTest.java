package com.esgc.Tests.DataValidation.EntityClimateProfilePage;


import com.esgc.APIModels.EntityHeader;
import com.esgc.APIModels.EntityProfileClimatePage.SummarySection.BrownShareAndGreenShareClimateSummary;
import com.esgc.APIModels.EntityProfileClimatePage.SummarySection.CarbonFootprintSummary;
import com.esgc.APIModels.EntityProfileClimatePage.SummarySection.PhysicalRiskHazardsSummary;
import com.esgc.APIModels.EntityProfileClimatePage.SummarySection.TemperatureAlignmentSummary;
import com.esgc.APIModels.EntityProfileClimatePage.UnderlyingDataMetrics.PhysicalRiskHazardsDetails;
import com.esgc.APIModels.EntityProfileClimatePage.UnderlyingDataMetrics.PhysicalRiskHazardsWrapper;
import com.esgc.APIModels.EntityScoreCategory.ESGScores;
import com.esgc.Controllers.EntityPage.EntityProfileClimatePageAPIController;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Tests.TestBases.EntityClimateProfileDataValidationTestBase;
import com.esgc.Utilities.Xray;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.*;

import static com.esgc.Utilities.Database.EntityClimateProfilePageQueries.getESGDbScores;
import static com.esgc.Utilities.Database.EntityClimateProfilePageQueries.getHeaderDB;

public class ESGClimateSummaryDBTest extends EntityClimateProfileDataValidationTestBase {

    @Test(groups = {"regression", "data_validation"}, dataProvider = "orbisID")
    @Xray(test = {6020, 8151})
    public void validateGreenShare(@Optional String orbisID) {
        Map<String, String> data = entityClimateProfilepagequeries.getGreenShareData(orbisID);
        List<BrownShareAndGreenShareClimateSummary> climateSummaryGreenShareAPIResponse = Arrays.asList(
                controller.getClimateSummaryAPIResponse(orbisID, "Green Share")
                        .as(BrownShareAndGreenShareClimateSummary[].class));
        assertTestCase.assertEquals(
                data.get("SCORE") == null ? "" : Integer.parseInt(data.get("SCORE")),
                climateSummaryGreenShareAPIResponse.get(0).getClimate().getScore(),
                "Green Share Score Validation for " + orbisID);

    }

    @Test(groups = {"data_validation", "regression"}, dataProvider = "orbisID")
    @Xray(test = {6041, 8151})
    public void validateBrownShare(@Optional String orbisID) {
        Map<String, String> data = entityClimateProfilepagequeries.getBrownShareData(orbisID);
        List<BrownShareAndGreenShareClimateSummary> climateSummaryBrownShareAPIResponse = Arrays.asList(
                controller.getClimateSummaryAPIResponse(orbisID, "Brown Share")
                        .as(BrownShareAndGreenShareClimateSummary[].class));
        assertTestCase.assertEquals(
                data.get("SCORE") == null ? "" : Integer.parseInt(data.get("SCORE")),
                climateSummaryBrownShareAPIResponse.get(0).getClimate().getScore(),
                "Brown Share Score Validation for " + orbisID);


    }

    @Test(groups = {"data_validation", "regression"}, dataProvider = "orbisID")
    @Xray(test = {6052, 6344})
    public void validateTemperatureAlignmentData(@Optional String orbisID) {

        Map<String, String> data = entityClimateProfilepagequeries.getTempratureAlignmentData(orbisID);
        TemperatureAlignmentSummary climateSummaryTempertureAlignmentAPIResponse = Arrays.asList(
                controller.getClimateSummaryAPIResponse(orbisID, "Temperature Alignment")
                        .as(TemperatureAlignmentSummary[].class)).get(0);

        assertTestCase.assertEquals(data.get("EMISSIONS_REDUCTION_TARGET_YEAR"),
                climateSummaryTempertureAlignmentAPIResponse.getClimate().getEmissions_reduction_target_year(),
                "Temperature Alignment Validation for EMISSIONS_REDUCTION_TARGET_YEAR");
        assertTestCase.assertEquals(data.get("IMPLIED_TEMPERATURE_RISE").equals("No Info") ?
                        -1d : Double.parseDouble(data.get("IMPLIED_TEMPERATURE_RISE")),
                climateSummaryTempertureAlignmentAPIResponse.getClimate().getImplied_temperature_rise(),
                "Temperature Alignment Validation for IMPLIED_TEMPERATURE_RISE");
        assertTestCase.assertEquals(data.get("UPDATED_DATE"),
                climateSummaryTempertureAlignmentAPIResponse.getClimate().getLast_updated_date(),
                "Temperature Alignment Validation for UPDATED_DATE");
        assertTestCase.assertEquals(data.get("SCORE_CATEGORY"),
                climateSummaryTempertureAlignmentAPIResponse.getClimate().getScore_category(),
                "Temperature Alignment Validation for SCORE_CATEGORY");
        assertTestCase.assertEquals(data.get("TARGET_DESCRIPTION"),
                climateSummaryTempertureAlignmentAPIResponse.getClimate().getTarget_description(),
                "Temperature Alignment Validation for TARGET_DESCRIPTION");

    }

    @Test(groups = {"data_validation", "regression"}, dataProvider = "orbisID")
    @Xray(test = {5999, 6000})
    public void validateHighestRiskHazardCategory(@Optional String orbisID) {

        Map<String, Object> data = entityClimateProfilepagequeries.getHighestRiskHazardData(orbisID).get(0);
        String expectedCategoryName = data.get("RISK_CATEGORY_NAME").toString();
        double expectedFacilitiesExposed = Double.parseDouble(data.get("PERCENT_FACILITIES_EXPOSED").toString());

        PhysicalRiskHazardsSummary physicalRiskHazardsSummary = Arrays.asList(controller
                .getClimateSummaryAPIResponse(orbisID, "physicalriskhazard").as(PhysicalRiskHazardsSummary[].class)).get(0);

        String actualRiskCategory = physicalRiskHazardsSummary.getPhysical_risk_hazard().getHighest_risk_hazard_category();
        double actualFacilitiesExposed = physicalRiskHazardsSummary.getPhysical_risk_hazard().getHighest_risk_hazard_fac_exp();

        assertTestCase.assertEquals(actualRiskCategory, expectedCategoryName, "Physical Risk Hazards Summary Validation for Highest Risk Score");
        assertTestCase.assertEquals(expectedFacilitiesExposed, actualFacilitiesExposed);

    }

    @DataProvider(name = "orbisID")
    public Object[][] provideFilterParameters() {

        return new Object[][]
                {
                        {"000002959"},
                        {"000411117"},
                        {"000411117"},
                        {"492557665"}, {"001812590"}, {"048977121"}
                };
    }

    @Test(groups = {"data_validation", "regression"}, dataProvider = "orbisID")
    @Xray(test = {6029, 7097})
    public void validateCarbonFootprintData(@Optional String orbisID) {

        Map<String, String> data = entityClimateProfilepagequeries.getCarbonFootprintSummary(orbisID);
        List<CarbonFootprintSummary> carbonFootprintApiResponses = Arrays.asList(
                controller.getClimateSummaryAPIResponse(orbisID, "carbonfootprint")
                        .as(CarbonFootprintSummary[].class));

        for (int i = 0; i < carbonFootprintApiResponses.size(); i++) {
            System.out.println(i + "=============" + carbonFootprintApiResponses.get(i).getCarbon().emissions_scope_1);
        }
        assertTestCase.assertEquals("" + carbonFootprintApiResponses.get(0).getCarbon().carbon_footprint_value_total,
                "" + data.get("CARBON_FOOTPRINT_VALUE_TOTAL"), "Carbon Footprint score data matches between API and Database");
        assertTestCase.assertEquals("" + carbonFootprintApiResponses.get(0).getCarbon().emissions_scope_1,
                "" + data.get("CARBON_FOOTPRINT_VALUE_SCOPE_1"), "Carbon Footprint scope 1data matches between API and Database");
        assertTestCase.assertEquals("" + carbonFootprintApiResponses.get(0).getCarbon().emissions_scope_2,
                "" + data.get("CARBON_FOOTPRINT_VALUE_SCOPE_2"), "Carbon Footprint scope 2 data matches between API and Database");
        assertTestCase.assertEquals("" + carbonFootprintApiResponses.get(0).getCarbon().emissions_scope_3,
                "" + data.get("CARBON_FOOTPRINT_VALUE_SCOPE_3"), "Carbon Footprint scope 3 data matches between API and Database");
        assertTestCase.assertEquals("" + carbonFootprintApiResponses.get(0).getCarbon().score_category,
                "" + data.get("SCORE_CATEGORY"), "Carbon Footprint score category data matches between API and Database");
        assertTestCase.assertEquals("" + carbonFootprintApiResponses.get(0).getCarbon().estimated,
                "" + data.get("ESTIMATED"), "Carbon Footprint estimated data matches between API and Database");

        //assertTestCase.assertEquals(""+carbonfootprint.get(0).getCarbon().last_updated_date, ""+ data.get("AS_OF_DATE"));
        //Dates are not matching with between database and API
    }

    @Test(groups = {"data_validation", "regression"}, dataProvider = "orbisID")
    @Xray(test = {6192})
    public void validateSectorComparisonChartPhysicalRiskData(@Optional String orbisID) {

        double expectedScore = Double.parseDouble(entityClimateProfilepagequeries.getSectorComparisonChartPhysicalRiskData(orbisID).get(0).get("SCORE").toString());
        String expectedCompanyName = entityClimateProfilepagequeries.getSectorComparisonChartPhysicalRiskData(orbisID).get(0).get("COMPANY_NAME").toString();
        String expSectorName = entityClimateProfilepagequeries.getSectorComparisonChartPhysicalRiskData(orbisID).get(0).get("SECTOR").toString();
        System.out.println("expectedScore = " + expectedScore);

        Response physicalriskhazard = controller.getComparisonChartContentResponse(orbisID, "Operations Risk");
        JsonPath js = physicalriskhazard.jsonPath();

        String actualCompanyName = js.getString("company_name").replaceAll("\\[", "").replaceAll("\\]", "");
        String actualSectorName = js.getString("mesg_sector").replaceAll("\\[", "").replaceAll("\\]", "");
        //double actualScore = Double.parseDouble(actualRiskNumber.substring(1, actualRiskNumber.length() - 1));


        assertTestCase.assertEquals(expectedCompanyName, actualCompanyName);
        assertTestCase.assertEquals(expSectorName, actualSectorName);
        // assertTestCase.assertEquals(expectedScore,actualScore);

    }

    @Test(groups = {"data_validation", "regression"}, dataProvider = "orbisID")
    @Xray(test = {6222, 6225})
    public void validateSectorComparisonChartTransitionRiskCarbonFootprintData(@Optional String orbisID) {

        //double expectedScore = Double.parseDouble(entityClimateProfilepagequeries.getSectorComparisonChartPhysicalRiskData(orbisID).get(0).get("SCORE").toString());
        String expectedCompanyName = entityClimateProfilepagequeries.getSectorComparisonChartPhysicalRiskData(orbisID).get(0).get("COMPANY_NAME").toString();
        String expSectorName = entityClimateProfilepagequeries.getSectorComparisonChartPhysicalRiskData(orbisID).get(0).get("SECTOR").toString();
        //System.out.println("expectedScore = " + expectedScore);

        Response physicalriskhazard = controller.getComparisonChartContentResponse(orbisID, "Carbon Footprint");
        JsonPath js = physicalriskhazard.jsonPath();

        String actualCompanyName = js.getString("company_name").replaceAll("\\[", "").replaceAll("\\]", "");
        String actualSectorName = js.getString("mesg_sector").replaceAll("\\[", "").replaceAll("\\]", "");
        //double actualScore = Double.parseDouble(actualRiskNumber.substring(1, actualRiskNumber.length() - 1));


        assertTestCase.assertEquals(expectedCompanyName, actualCompanyName);
        assertTestCase.assertEquals(expSectorName, actualSectorName);
        // assertTestCase.assertEquals(expectedScore,actualScore);

    }

    @Test(groups = {"regression", "data_validation"}, dataProvider = "orbisID")
    @Xray(test = {8988, 8989})
    public void validatePhysicalRiskManagement(@Optional String orbisID) {
        Map<String, String> data = entityClimateProfilepagequeries.getPhysicalRiskData(orbisID);
        if (data.size() > 0) {
            PhysicalRiskHazardsWrapper climateSummaryGreenShareAPIResponse = controller.getEntityUnderLyingDataMetricsAPIResponse(orbisID, "physicalriskmgmt")
                    .as(PhysicalRiskHazardsWrapper.class);

            assertTestCase.assertTrue(data.get("SCORE_CATEGORY").equals(climateSummaryGreenShareAPIResponse.getScore_category()), "Validating Score Category");

            for (PhysicalRiskHazardsDetails apiData : climateSummaryGreenShareAPIResponse.getUnderlying_metrics()) {


                String score = "";
                switch (apiData.getTitle()) {
                    case "Overall Physical Risk Management Score":
                        score = data.get("GS_PH_RISK_MGT_TOTAL");
                        break;
                    case "Leadership":
                        score = data.get("GS_PH_RISK_MGT_LEADERSHIP");
                        break;
                    case "Implementation":
                        score = data.get("GS_PH_RISK_MGT_IMPLEMENTATION");
                        break;
                    case "Results":
                        score = data.get("GS_PH_RISK_MGT_RESULTS");
                        break;

                }
                assertTestCase.assertTrue(score.equals(String.valueOf(apiData.getScore())), "Validating Score Value");
                // assertTestCase.assertTrue(data.get("SCORE_CATEGORY").toString().equals(apiData.getScore_category()),"Validating Score Category");
                if (data.get("SCORE_CATEGORY").equals("N/A")) {
                    assertTestCase.assertTrue(data.get("SCORE_CATEGORY").equals(apiData.getScore_msg()), "Validating Score msg");
                }

            }
        } else {
            System.out.println("Entity does not have data to test");
        }
    }

    @Test(groups = {"regression","smoke", "data_validation"},
            dataProviderClass = DataProviderClass.class, dataProvider = "orbisIDWithDisclosureScore")
    @Xray(test = {8750})
    public void validateDisclosureRatio(@Optional String orbisID) {
        //Get the header details via API
        List<EntityHeader> entityHeader = Arrays.asList(controller.getHeaderDetailsWithPayload("{\"orbis_id\":\"" + orbisID + "\"}").getBody().as(EntityHeader[].class));
        int disclosureRate = entityHeader.get(0).getOverall_disclosure_score();
        System.out.println("list = " + entityHeader.get(0).getOverall_disclosure_score());

        //Get the entity details which has Overall Disclosure rate from DB
        List<String> dbResults = getHeaderDB(orbisID);

        //Verify API and DB values
        int disclosureDBRRate = (int) (Double.parseDouble(dbResults.get(0)) * 100);
        assertTestCase.assertEquals(disclosureRate, disclosureDBRRate);

    }

    @Test(groups = {"regression", "smoke","data_validation"},
            dataProviderClass = DataProviderClass.class, dataProvider = "orbisIDWithDisclosureScore")
    @Xray(test = {9841})
    public void verifyAPIForOverallESGScoreWidget(String orbis_id) {
        EntityProfileClimatePageAPIController entityClimateProfileApiController = new EntityProfileClimatePageAPIController();
        test.info("ESG Score widget API validation for " + orbis_id);
        // Get the API response as ArrayList
        Response response = entityClimateProfileApiController.getESGClimateSummary(orbis_id);
        assertTestCase.assertEquals(response.getStatusCode(), 200, "ESG Climate Summary Projection API Response status is verified");
        List<ESGScores> esgList = Arrays.asList(response.as(ESGScores[].class));
        int size = esgList.get(0).getEsg_assessment().getScore_categories().size();
        List<String> allScores = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (!esgList.get(0).getEsg_assessment().getScore_categories().get(i).getScore().toString().contains("ESG")) {
                Double value= (Double) esgList.get(0).getEsg_assessment().getScore_categories().get(i).getScore();
                allScores.add(String.valueOf(Math.round(value*10000.0)/10000.0));
            }
        }
        System.out.println("allScores = " + allScores);

        List<String> dbResults = getESGDbScores(orbis_id);
        List<String> dbScores=new ArrayList<>();
        for(String dbScore: dbResults){
            dbScores.add(String.valueOf(Math.round(Double.parseDouble(dbScore)*10000.0)/10000.0));
        }
        System.out.println("dbScores = " + dbScores);
        //Compare both values
        Collections.sort(allScores);
        Collections.sort(dbScores);
        assertTestCase.assertSame(allScores,dbScores);
    }
}
