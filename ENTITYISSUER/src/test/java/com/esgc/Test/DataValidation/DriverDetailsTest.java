package com.esgc.Test.DataValidation;

import com.esgc.APIModels.EntityIssuerPage.*;
import com.esgc.DBModels.EntityIssuerPageDBModels.DriverDetailsDBModel;
import com.esgc.DBModels.EntityIssuerPageDBModels.DriverTrendDetailModel;
import com.esgc.Test.TestBases.EntityIssuerPageDataValidationTestBase;
import com.esgc.Utilities.Database.EntityIssuerQueries;
import com.esgc.Utilities.Environment;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.esgc.Pages.LoginPageIssuer.OrbisID;

public class DriverDetailsTest extends EntityIssuerPageDataValidationTestBase {
    List<String> criteria = new ArrayList<>();

    @Xray(test = {6290,7074,8896})
    @Test(groups = {"entity_issuer","regression"})
    public void driverDetailsTestForNonTrendMetrcs() {
         String orbisID = OrbisID;
        //get all criteria using DriverSummary API
        setCriteria(orbisID);

        //Fail if no criteria found
        if (criteria.size() == 0) {
            test.info("No criteria to test");
            return;
        }


        //Call Driver details API for each criteria and validate DB vs API results
        for (int i = 0; i < criteria.size(); i++) {
            String criteriaId = criteria.get(i);
            System.out.println("Checking for criteria =" + criteriaId);

            //DB call for driverDetails
            List<DriverDetailsDBModel> driverDetailsDBModelList = EntityIssuerQueries.getDriverDetails(orbisID, criteriaId).stream().
                    filter(e->e.getTrend().equals("Non_Trend")).collect(Collectors.toList());

            //API call for driverDetails
            DriverDetailPayload driverPayload = new DriverDetailPayload(criteriaId, orbisID);
            Response response = controller.getDriverDetails(driverPayload);
            List<DriverDetailsAPIWrapper> driverDetailsAPIResponse = Arrays.asList(response.getBody().as(DriverDetailsAPIWrapper[].class));
            List<String> expectedMetricValues = Arrays.asList(new String[]{"Yes","Not Indicated", "No"});

            // Ignore ones which return empty json
            if (driverDetailsAPIResponse.size() > 0) {

                //Assertions for pillar, angle, criteria
                assertTestCase.assertEquals((int) driverDetailsAPIResponse.get(0).getCountTotal(), driverDetailsDBModelList.size());
                for (Item item : driverDetailsAPIResponse.get(0).item) {
                    for (ItemDetail d : item.getItemDetails()) {
                        DriverDetailsDBModel dbdata = driverDetailsDBModelList.stream().filter(f -> f.getItem_descripton().equals(d.getItemDescripton())).findFirst().get();
                        assertTestCase.assertEquals(dbdata.getItem_descripton(), d.getItemDescripton(), "Validate if Item description is matched");
                        assertTestCase.assertEquals(dbdata.getItem_value(), d.getItemValue(), "Validate if Item Value is matched");

                        if (dbdata.getItem_value2().equals("Number")){
                            String decimalPattern = "([0-9]*)\\.([0-9]*)";
                            boolean match = Pattern.matches(decimalPattern, dbdata.getItem_value());
                            assertTestCase.assertTrue(match, "Validating if Item value is numeric");
                        }
                        else assertTestCase.assertTrue(expectedMetricValues.contains(dbdata.getItem_value()),"Validating if item value is in " + expectedMetricValues);

                        assertTestCase.assertEquals(dbdata.getPillar(), d.getPillar(), "Validate if Pillar is matched");
                        assertTestCase.assertEquals(dbdata.getAngle(), d.getAngle(), "Validate if Angle is matched");
                        assertTestCase.assertEquals(dbdata.getPossible_unit(), d.getPossibleUnit(), "Validate if possible unit  is matched");
                        assertTestCase.assertEquals(dbdata.getCriteria_name(), d.getCriteriaName(), "Validate if criteria is matched");
                    }
                }

            }
        }

        test.info("Verified all driver details successfully");
    }


    @Xray(test = {7984})
    @Test(groups = {"entity_issuer","regression"})
    public void driverDetailsTestForTrendMetrics() {
        String orbisID = OrbisID;
        //get all criteria using DriverSummary API
        setCriteria(orbisID);

        //Fail if no criteria found
        if (criteria.size() == 0) {
            test.info("No criteria to test");
            return;
        }


        //Call Driver details API for each criteria and validate DB vs API results
        for (int i = 0; i < criteria.size(); i++) {
            String criteriaId = criteria.get(i);
            System.out.println("Checking for criteria =" + criteriaId);

            //DB call for driverDetails
            List<DriverTrendDetailModel> driverTrendDetailsDBModelList = EntityIssuerQueries.getTrendDetails(orbisID, criteriaId);


            //API call for driverDetails
            DriverDetailPayload driverPayload = new DriverDetailPayload(criteriaId, orbisID);
            Response response = controller.getDriverDetails(driverPayload);
            List<DriverDetailsAPIWrapper> driverDetailsAPIResponse = Arrays.asList(response.getBody().as(DriverDetailsAPIWrapper[].class));

            if (driverDetailsAPIResponse.get(0).getTrends().size()>0 && driverTrendDetailsDBModelList.size()>0){
                //Assertions for pillar, angle, criteria

                for (Trend item : driverDetailsAPIResponse.get(0).getTrends()) {
                    List<DriverTrendDetailModel> MetricData = driverTrendDetailsDBModelList.stream().filter(f ->
                            f.getMETRIC().equals(item.getTrend_name())).collect(Collectors.toList());
                    for (TrendDetail d : item.getTrend_details()) {
                        DriverTrendDetailModel dbdata = MetricData.stream().filter(f ->
                                f.getMETRIC_YEAR().equals(d.getItem_year())).findFirst().get();
                        assertTestCase.assertEquals(dbdata.getMETRIC_VALUE(), d.getItem_value(), "Validate if Item Value is matched");
                        assertTestCase.assertEquals(dbdata.getMETRIC_YEAR(), d.getItem_year(), "Validate if Item Year is matched");
                        assertTestCase.assertEquals(dbdata.getPOSSIBLE_UNIT(), d.getPossible_unit(), "Validate if possible Unit is matched");

                    }
                }

            }
            }


        test.info("Verified all driver details Trend Metrics successfully");

    }

    public void setCriteria(String orbisID) {

        //Call driverSummary API to get all possible criteria
        List<ScoreAllocation> driverSummaryAPIResponse = Arrays.asList(
                controller.getSectorDrivers(orbisID)
                        .as(ScoreAllocation[].class));

        // Filter and put it in a list
        List<DriverDetail> driverScoreCriteria = driverSummaryAPIResponse.stream().flatMap(f -> f.getDriver_details().stream()).collect(Collectors.toList());
        List<Driver> driversData = driverScoreCriteria.stream().flatMap(f -> f.getDrivers().stream()).collect(Collectors.toList());
        criteria = driversData.stream().map(Driver::getCriteria_id).collect(Collectors.toList());
    }


    @DataProvider(name = "orbisID")
    public Object[][] provideFilterParameters() {

        return new Object[][]
                {
                        {"000040265"}
                };
    }
}
