package com.esgc.Tests.API.EntityClimate;

import com.esgc.APIModels.EntityClimateProfile.CompanyHeader;
import com.esgc.APIModels.EntityProfileClimatePage.SectorComparison;
import com.esgc.Controllers.EntityPage.EntityProfileClimatePageAPIController;
import com.esgc.Tests.TestBases.EntityClimateProfileTestBase;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class ClimateProfileCompanyHeaderAPITest extends EntityClimateProfileTestBase {

    @Test(groups = {"api", "regression", "entity_climate_profile"},dataProvider = "Company With Orbis ID")
    @Xray(test = {10047})
    public void validateTransitionRiskSectorComparisonChartContentResponse(String... dataprovider) {
        EntityProfileClimatePageAPIController entityClimateProfileApiController = new EntityProfileClimatePageAPIController();

        Response response = entityClimateProfileApiController
                .geCompanyHeaderAPIResponse(dataprovider[1]);
        response.as(CompanyHeader[].class);
        response.then().log().all();
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);


    }



}