package com.esgc.EntityProfile.API.Tests;

import com.esgc.Base.TestBases.EntityClimateProfileTestBase;
import com.esgc.EntityProfile.API.APIModels.ESGMateriality.ESGMaterlityDriverSummaryAPIWrapper;
import com.esgc.EntityProfile.API.Controllers.EntityProfileClimatePageAPIController;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class MaterialityMetricsAPITests extends EntityClimateProfileTestBase {

    //TODO add materiality metrics API model
    @Test(groups = {"api", "regression", "entity_climate_profile"})
    public void ValidateMaterialityMetricsAPIResponse() {
        String orbis_id = "000411117";
        System.out.println("orbis_id = " + orbis_id);
        EntityProfileClimatePageAPIController entityClimateProfileApiController = new EntityProfileClimatePageAPIController();
        test.info("Materiality Metrics widget API validation");
        Response response = entityClimateProfileApiController
                .getEntityMaterialityMetrics(orbis_id);
        ESGMaterlityDriverSummaryAPIWrapper APIdata = response.as(ESGMaterlityDriverSummaryAPIWrapper.class);
        response.then().log().all();

        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                ;

        test.pass("Successfully validated Widget API");
    }

}
