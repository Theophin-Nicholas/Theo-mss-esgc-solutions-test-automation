package com.esgc.EntityProfile.DB.Tests;

import com.esgc.EntityProfile.API.APIModels.EntityControversies.Controversies;
import com.esgc.EntityProfile.API.Controllers.EntityProfileClimatePageAPIController;
import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.EntityProfile.API.EntityProfilePageEndpoints;
import com.esgc.Utilities.Environment;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.esgc.EntityProfile.DB.DBQueries.EntityClimateProfilePageQueries.getCoverage;
import static io.restassured.RestAssured.given;


public class EntityClimateControversies extends DataValidationTestBase {

    //Verify the Subcategories data for the entity is matching with the snowflake db
    @Xray(test = {8418,8419})
    @Test(groups = {"regression", "ui", "smoke", "entity_climate_profile"},
            description = "Verify the Subcategories data for the entity is matching with the snowflake db")
    public void entityClimateControversies() {
        // Get the api response
        EntityProfileClimatePageAPIController apiContoller = new EntityProfileClimatePageAPIController();
        Controversies contList = apiContoller.getControversiesAPI("{\"orbis_id\":\"000411117\"}").as(Controversies.class);

        Map<String, Integer> subCategoryDetails = new HashMap<>();
        for (int i = 0; i < contList.getSub_categories().size(); i++) {
            System.out.println("Category = " + contList.getSub_categories().get(i).getSub_category());
            System.out.println("Count = " + contList.getSub_categories().get(i).getSub_category_cnt());
            System.out.println("================");
            subCategoryDetails.put(contList.getSub_categories().get(i).getSub_category(), contList.getSub_categories().get(i).getSub_category_cnt());
        }
        System.out.println("subCategoryDetails = " + subCategoryDetails);

        //Get the snowflake response
        List<String> dbResults = getCoverage("000411117");

        //Compare both values
        Map<String, Integer> dbResultsMap = new HashMap<>();
        int bb = 0, e = 0, hRights = 0, hResources = 0, cg = 0, ci = 0, counter = 0;
        System.out.println("dbResults Size = " + dbResults.size());
        for (String domain : dbResults) {
            System.out.println("domain = " + domain);
            System.out.println("counter = " + ++counter);
            if (domain.contains("Business Behaviour")) {
                bb++;
                System.out.println("bb = " + bb);
            }
            if (domain.contains("Environment")) {
                e++;
                System.out.println("e = " + e);
            }
            if (domain.contains("Human Resources")) {
                hResources++;
                System.out.println("hResources = " + hResources);
            }
            if (domain.contains("Human Rights")) {
                hRights++;
                System.out.println("hRights = " + hRights);
            }
            if (domain.contains("Corporate Governance")) {
                cg++;
                System.out.println("cg = " + cg);
            }
            if (domain.contains("Community Involvement")) {
                ci++;
                System.out.println("ci = " + ci);
            }
            System.out.println("-----------------------------");
        }
        dbResultsMap.put("Business Behaviour", bb);
        dbResultsMap.put("Environment", e);
        dbResultsMap.put("Human Resources", hResources);
        dbResultsMap.put("Human Rights", hRights);
        dbResultsMap.put("Corporate Governance", cg);
        dbResultsMap.put("Community Involvement", ci);
        System.out.println("dbResultsMap = " + dbResultsMap);
        assertTestCase.assertTrue(dbResultsMap.equals(subCategoryDetails));
        System.out.println("dbResultsMap.equals(subCategoryDetails) = " + dbResultsMap.equals(subCategoryDetails));

    }

    public Response getControversies(String payload) {
        Response response = null;
        try {
            response = given().accept(ContentType.JSON)
                    .baseUri(Environment.URL)
                    .relaxedHTTPSValidation()
                    .header("Authorization", "Bearer " + System.getProperty("token"))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(payload)
                    .when()
                    .post(EntityProfilePageEndpoints.POST_ENTITY_CONTROVERSIES);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }

}
