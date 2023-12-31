package com.esgc.EntityProfile.DB.Tests;


import com.esgc.Base.TestBases.EntityClimateProfileDataValidationTestBase;
import com.esgc.EntityProfile.API.APIModels.EntityHeader;
import com.esgc.EntityProfile.API.APIModels.SummarySection.BrownShareAndGreenShareClimateSummary;
import com.esgc.EntityProfile.API.APIModels.SummarySection.CarbonFootprintSummary;
import com.esgc.EntityProfile.API.APIModels.SummarySection.PhysicalRiskHazardsSummary;
import com.esgc.EntityProfile.API.APIModels.SummarySection.TemperatureAlignmentSummary;
import com.esgc.EntityProfile.API.APIModels.UnderlyingDataMetrics.PhysicalRiskHazardsDetails;
import com.esgc.EntityProfile.API.APIModels.UnderlyingDataMetrics.PhysicalRiskHazardsWrapper;
import com.esgc.EntityProfile.API.Controllers.EntityProfileClimatePageAPIController;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.Xray;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.esgc.EntityProfile.DB.DBQueries.EntityClimateProfilePageQueries.getHeaderDB;
import static com.esgc.Utilities.Groups.*;

public class ESGClimateSummaryDBTest extends EntityClimateProfileDataValidationTestBase {
//TODO check https://esjira/browse/ESGCA-8761 and update date validations
    @Test(groups = {REGRESSION, DATA_VALIDATION}, dataProvider = "orbisID")
    @Xray(test = {3355, 4994})
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

    @Test(groups = {DATA_VALIDATION, REGRESSION}, dataProvider = "orbisID")
    @Xray(test = {3365, 4994})
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

    @Test(groups = {DATA_VALIDATION, REGRESSION}, dataProvider = "orbisID")
    @Xray(test = {4765, 4397})
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

    @Test(groups = {DATA_VALIDATION, REGRESSION}, dataProvider = "orbisID")
    @Xray(test = {3576, 3584})
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

    @Test(groups = {DATA_VALIDATION, REGRESSION}, dataProvider = "orbisID")
    @Xray(test = {4439, 4303})
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

    @Test(groups = {DATA_VALIDATION, REGRESSION}, dataProvider = "orbisID")
    @Xray(test = {4427})
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

    @Test(groups = {DATA_VALIDATION, REGRESSION}, dataProvider = "orbisID")
    @Xray(test = {4714, 4709})
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

    @Test(groups = {REGRESSION, DATA_VALIDATION}, dataProvider = "orbisID")
    @Xray(test = {4840, 4566})
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
}
