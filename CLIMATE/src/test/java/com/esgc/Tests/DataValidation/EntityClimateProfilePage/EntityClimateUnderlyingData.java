package com.esgc.Tests.DataValidation.EntityClimateProfilePage;

import com.esgc.APIModels.EntityProfileClimatePage.SummarySection.TemperatureAlignmentSummary;
import com.esgc.APIModels.EntityUnderlyingData.BrownShare;
import com.esgc.APIModels.EntityUnderlyingData.CarbonFootprint;
import com.esgc.APIModels.EntityUnderlyingData.GreenShare;
import com.esgc.Tests.TestBases.EntityClimateProfileDataValidationTestBase;
import com.esgc.Utilities.API.EntityClimateProfilePageEndpoints;
import com.esgc.Utilities.Environment;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.esgc.Utilities.Database.EntityClimateProfilePageQueries.getCoverageForGreenShare;
import static com.esgc.Utilities.Database.EntityClimateProfilePageQueries.getCoverageForWidgets;
import static io.restassured.RestAssured.given;

public class EntityClimateUnderlyingData extends EntityClimateProfileDataValidationTestBase {

    @Xray(test = {8005, 8006})
    @Test(groups = {"regression", "ui", "smoke", "entity_climate_profile"})
    public void verifyUnderlyingDataMetricsCorrectGreenShare() {
        String orbisId = "039634868";//"000411117";
        List<String> dbResults;
        String greenShare = EntityClimateProfilePageEndpoints.POST_UnderlyingDataGreenShare;
        List<GreenShare> greenShareList = Arrays.asList(getUnderlyingDataAPI(orbisId, greenShare).as(GreenShare[].class));
        System.out.println("Green Share = " + greenShareList.get(0).getGreen_share_products());

        dbResults = getCoverageForGreenShare(orbisId, "Green Share");
        System.out.println("dbResults = " + dbResults.size());
        String firstValue = dbResults.get(0);
        System.out.println("firstValue = " + firstValue);
        double secondValue = Double.parseDouble(dbResults.get(1));
        System.out.println("secondValue = " + secondValue);
        String thirdValue = dbResults.get(2);
        System.out.println("thirdValue = " + thirdValue);
        String thirdValueAPI = greenShareList.get(0).getGreen_share_products().get(1).getInvestment_in_category_msg();
        System.out.println("thirdValueAPI = " + thirdValueAPI);
        /**
         * Logic:
         * If the first value is less than zero then we are skipping the second value and
         * verifiying third value.
         * if the first value is not less than then check the second value.
         * if the second value is less than zero then verify the third value.
         *
         * If the all values less than zero or non than UI needs to populate NA
         */
        if (firstValue.equalsIgnoreCase("-2.00")) {
            assertTestCase.assertEquals(thirdValue, thirdValueAPI);
        } else if (secondValue < 0) {
            assertTestCase.assertEquals(thirdValue, thirdValueAPI);
        } else if (firstValue.equalsIgnoreCase("-2.00") && secondValue < 0) {
            assertTestCase.assertEquals(thirdValue, thirdValueAPI);
        } else if (firstValue.equalsIgnoreCase("-2.00") && secondValue < 0 && thirdValue.equals("None")) {
            assertTestCase.assertTrue(thirdValueAPI.equals("NA"));
        }
    }

    @Xray(test = {8218, 8262, 8805})
    @Test(groups = {"regression", "ui", "smoke", "entity_climate_profile"})
    public void verifyUnderlyingDataMetricsCorrectBrownShare() {
        String orbisId = "143622191";//"000411117";
        List<String> dbResults;
        String brownShare = EntityClimateProfilePageEndpoints.POST_UnderlyingDataBrownShare;
        List<BrownShare> brownShareList = Arrays.asList(getUnderlyingDataAPI(orbisId, brownShare).as(BrownShare[].class));
        System.out.println("Brown Share = " + brownShareList.get(0).getBrown_share_products());

        dbResults = getCoverageForGreenShare(orbisId, "Brown Share");
        System.out.println("dbResults = " + dbResults.size());
        String firstValue = dbResults.get(0);
        System.out.println("firstValue = " + firstValue);
        double secondValue = Double.parseDouble(dbResults.get(1));
        System.out.println("secondValue = " + secondValue);
        String thirdValue = String.valueOf(Double.parseDouble(dbResults.get(2)));
        System.out.println("thirdValue = " + thirdValue);
        String fourthValue = dbResults.get(3);
        String fifthValue = dbResults.get(4);

        String thirdValueAPI = String.valueOf(brownShareList.get(0).getBrown_share_products().get(12).getInvestment_in_category());
        System.out.println("thirdValueAPI = " + thirdValueAPI);
        String fourthValueAPI = String.valueOf(brownShareList.get(0).getBrown_share_products().get(17).getInvestment_in_category_msg());
        String fifthValueAPI = String.valueOf(brownShareList.get(0).getBrown_share_products().get(19).getInvestment_in_category_msg());
        assertTestCase.assertEquals(fourthValueAPI, fourthValue);
        assertTestCase.assertEquals(fifthValueAPI,fifthValue);

        if (!firstValue.equalsIgnoreCase("N/A")) {
            assertTestCase.assertEquals(thirdValue, thirdValueAPI);
        }

    }

    @Xray(test = {10231})
    @Test(groups = {"regression", "ui", "smoke", "entity_climate_profile"})
    public void verifyUpdatedDateInAllWidgets() {
        List<String> researchLines = Arrays.asList("Temperature Alignment", "Carbon Footprint", "Brown Share Assessments", "Green Share Assessment");
        for (String researchLine : researchLines) {
            System.out.println("researchLine = " + researchLine);
            String orbisId = "000411117";
            List<String> dbResults = new ArrayList<>();
            String temperatureAlignment = EntityClimateProfilePageEndpoints.POST_SummaryTemperatureAlignment;
            String carbonFootprint = EntityClimateProfilePageEndpoints.POST_UnderlyingDataCarbonFootprint;
            String greenShare = EntityClimateProfilePageEndpoints.POST_UnderlyingDataGreenShare;
            String brownShare = EntityClimateProfilePageEndpoints.POST_UnderlyingDataBrownShare;
            //Get the snowflake response
            if (researchLine.equalsIgnoreCase("Temperature Alignment")) {
                // Get the api response
                List<TemperatureAlignmentSummary> tempAlignment = Arrays.asList(getUnderlyingDataAPI(orbisId, temperatureAlignment).as(TemperatureAlignmentSummary[].class));
                System.out.println("tempAlignment = " + tempAlignment.get(0).getClimate().getLast_updated_date());
                String tempAlignmentAPI = tempAlignment.get(0).getClimate().getLast_updated_date();
                dbResults = getCoverageForWidgets(orbisId, researchLine);
                String dbDate = dbResults.get(0).substring(0, 4) + "-" + dbResults.get(0).substring(4, 6) + "-" + dbResults.get(0).substring(6, 8);
                assertTestCase.assertEquals(tempAlignmentAPI, dbDate);
            } else if (researchLine.equalsIgnoreCase("Carbon Footprint")) {
                List<CarbonFootprint> carbonFootprints = Arrays.asList(getUnderlyingDataAPI(orbisId, carbonFootprint).as(CarbonFootprint[].class));
                System.out.println("Carbon Footprint API = " + carbonFootprints.get(0).getLast_updated_date());
                String carbonFootprintsAPI = carbonFootprints.get(0).getLast_updated_date();
                dbResults = getCoverageForWidgets(orbisId, researchLine);
                assertTestCase.assertEquals(carbonFootprintsAPI, dbResults.get(0));
            } else if (researchLine.equalsIgnoreCase("Brown Share Assessments")) {
                List<BrownShare> brownShareList = Arrays.asList(getUnderlyingDataAPI(orbisId, brownShare).as(BrownShare[].class));
                System.out.println("Brown Share Assessments API = " + brownShareList.get(0).getUpdated_date());
                String brownShareAPI = brownShareList.get(0).getUpdated_date();
                dbResults = getCoverageForWidgets(orbisId, researchLine);
                assertTestCase.assertEquals(brownShareAPI, dbResults.get(0));
            } else if (researchLine.equalsIgnoreCase("Green Share Assessments")) {
                List<GreenShare> greenShareList = Arrays.asList(getUnderlyingDataAPI(orbisId, greenShare).as(GreenShare[].class));
                System.out.println("Green Share Assessments API = " + greenShareList.get(0).getUpdated_date());
                String carbonFootprintsAPI = greenShareList.get(0).getUpdated_date();
                dbResults = getCoverageForWidgets(orbisId, researchLine);
                assertTestCase.assertEquals(carbonFootprintsAPI, dbResults.get(0));
            }
            System.out.println("dbResults = " + dbResults);
            dbResults.clear();
        }
    }

    public Response getUnderlyingDataAPI(String orbis_id, String endPoint) {
        Response response = null;
        try {
            response = given().accept(ContentType.JSON)
                    .baseUri(Environment.URL)
                    .relaxedHTTPSValidation()
                    .header("Authorization", "Bearer " + System.getProperty("token"))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .pathParam("entity_id", orbis_id)
                    .when()
                    .post(endPoint);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }
}