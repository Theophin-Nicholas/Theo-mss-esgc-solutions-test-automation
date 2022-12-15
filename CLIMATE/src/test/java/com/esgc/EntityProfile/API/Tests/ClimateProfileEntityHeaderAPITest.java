package com.esgc.EntityProfile.API.Tests;

import com.esgc.EntityProfile.API.APIModels.EntityHeader;
import com.esgc.EntityProfile.API.Controllers.EntityProfileClimatePageAPIController;
import com.esgc.Base.TestBases.EntityClimateProfileTestBase;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class ClimateProfileEntityHeaderAPITest extends EntityClimateProfileTestBase {

    @Test(groups = {"api", "regression", "entity_climate_profile"},dataProvider = "Company With Orbis ID")
    @Xray(test = {10047})
    public void validateTransitionRiskSectorComparisonChartContentResponse(String... dataprovider) {
        EntityProfileClimatePageAPIController entityClimateProfileApiController = new EntityProfileClimatePageAPIController();

        Response response = entityClimateProfileApiController
                .geCompanyHeaderAPIResponse(dataprovider[1]);
        response.as(EntityHeader[].class);
        response.then().log().all();
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);


    }



}
