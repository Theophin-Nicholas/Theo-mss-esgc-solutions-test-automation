package com.esgc.EntityProfile.API.Tests;

import com.esgc.Base.TestBases.EntityClimateProfileTestBase;
import com.esgc.EntityProfile.API.APIModels.SectorComparison;
import com.esgc.EntityProfile.API.Controllers.EntityProfileClimatePageAPIController;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class ClimateProfileSectorComparisonChartAPITests extends EntityClimateProfileTestBase {
//TODO add all research lines
    @Test(groups = {API, REGRESSION, ENTITY_PROFILE}, dataProvider = "API Research Lines2 Carbon Footprint")
    @Xray(test = {1939})
    public void validateTransitionRiskSectorComparisonChartContentResponse(String researchLine, String orbis_id) {
        EntityProfileClimatePageAPIController entityClimateProfileApiController = new EntityProfileClimatePageAPIController();
        test.info("Climate Profile Transition " + researchLine + " widget API validation");
        Response response = entityClimateProfileApiController
                .getComparisonChartContentResponse(orbis_id, researchLine);
        if(researchLine.equals("carbonfootprint"))
            response.as(SectorComparison.class);
        else
            response.as(SectorComparison[].class);
        response.then().log().all();
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);

        test.pass("Successfully validated for " + researchLine + "Widget API");
    }

    @Test(groups = {API, REGRESSION, ENTITY_PROFILE}, dataProvider = "API Research Lines2 Carbon Footprint")
    @Xray(test = {1400})
    public void validateTransitionRiskSectorComparisonChartContentResponseWithInvalidToken(String researchLine, String orbis_id) {
        test.info("Unauthorised Access : Climate Summery API POST request for " + researchLine + "Widget");
        EntityProfileClimatePageAPIController entityClimateProfileApiController = new EntityProfileClimatePageAPIController();
        Response response = entityClimateProfileApiController
                .getSectorComparisonChartContentResponseWithTemperedToken(orbis_id, researchLine);
        response.then().assertThat().statusCode(403);
        test.pass("Successfully validated for " + researchLine + "research line");
    }

    @Test(groups = {API, REGRESSION, ENTITY_PROFILE}, dataProvider = "API Research Lines2 Operations Risk")
    @Xray(test = {3853})
    public void validatePhysicalRiskSectorComparisonChartContentResponse(String researchLine, String orbis_id) {
        EntityProfileClimatePageAPIController entityClimateProfileApiController = new EntityProfileClimatePageAPIController();
        Response response = entityClimateProfileApiController
                .getComparisonChartContentResponse(orbis_id, researchLine);
        response.as(SectorComparison[].class);
        response.then().log().all();
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);

        test.pass("Successfully validated for " + researchLine + "Widget API");
    }

    @Test(groups = {API, REGRESSION, ENTITY_PROFILE}, dataProvider = "API Research Lines2 Operations Risk")
    @Xray(test = {3853})
    public void validatePhysicalRiskSectorComparisonChartContentResponseWithInvalidToken(String researchLine, String orbis_id) {
        test.info("Unauthorised Access : Climate Summery API POST request for " + researchLine + "Widget");
        EntityProfileClimatePageAPIController entityClimateProfileApiController = new EntityProfileClimatePageAPIController();
        Response response = entityClimateProfileApiController
                .getSectorComparisonChartContentResponseWithTemperedToken(orbis_id, researchLine);
        response.then().assertThat().statusCode(403);
        test.pass("Successfully validated for " + researchLine + "research line");
    }

    @Test(groups = {API, REGRESSION, ENTITY_PROFILE}, dataProvider = "API Research Lines2 Market Risk")
    @Xray(test = {4060})
    public void verifyComparisonChartContentResponseForMarketRisk(String researchLine, String orbis_id) {
        test.info("Authorised Access : Physical Climate Risk: Market Risk API POST request for " + researchLine + "Widget");
        EntityProfileClimatePageAPIController entityClimateProfileApiController = new EntityProfileClimatePageAPIController();
        Response response = entityClimateProfileApiController
                .getComparisonChartContentResponse(orbis_id, researchLine);
        response.then().assertThat().statusCode(200);
        assertTestCase.assertTrue(response.path("company_name").toString().length()>3);

        test.pass("Successfully validated for " + researchLine + "research line");
    }

    @Test(groups = {API, REGRESSION, ENTITY_PROFILE}, dataProvider = "API Research Lines2 Market Risk")
    @Xray(test = {4085})
    public void verifySectorComparisonChartContentResponseWithInvalidParameters(String researchLine, String orbis_id) {
        test.info("Unauthorised Access : Comparison Chart API POST request for " + researchLine + "Widget");
        EntityProfileClimatePageAPIController entityClimateProfileApiController = new EntityProfileClimatePageAPIController();
        Response response = entityClimateProfileApiController
                .getSectorComparisonChartContentResponseWithTemperedToken(orbis_id, researchLine);
        response.then().assertThat().statusCode(403);
        assertTestCase.assertEquals(response.path("message").toString(),"Forbidden","Forbidden message is displayed");
        response.then().log().all();
        test.pass("Successfully validated for " + researchLine + "research line");
    }

    @Test(groups = {API, REGRESSION, ENTITY_PROFILE}, dataProvider = "API Research Lines2 Supply Chain Risk")
    @Xray(test = {3630})
    public void verifyComparisonChartContentResponseForSupplyChainRiskTest(String researchLine, String orbis_id) {
        System.out.println("========================================");
        System.out.println("researchLine = " + researchLine);
        System.out.println("orbis_id = " + orbis_id);
        test.info("Authorised Access : Physical Climate Risk: Market Risk API POST request for " + researchLine + "Widget");
        EntityProfileClimatePageAPIController entityClimateProfileApiController = new EntityProfileClimatePageAPIController();
        Response response = entityClimateProfileApiController
                .getComparisonChartContentResponse(orbis_id, researchLine);
        response.then().assertThat().statusCode(200);
        assertTestCase.assertTrue(response.path("company_name").toString().length()>3);

        test.pass("Successfully validated for " + researchLine + "research line");
    }

    @Test(groups = {API, REGRESSION, ENTITY_PROFILE}, dataProvider = "API Research Lines2 Supply Chain Risk")
    @Xray(test = {3708})
    public void verifySectorComparisonChartContentResponseWithInvalidParametersForSupplyChainRiskTest(String researchLine, String orbis_id) {
        test.info("Unauthorised Access : Comparison Chart API POST request for " + researchLine + "Widget");
        EntityProfileClimatePageAPIController entityClimateProfileApiController = new EntityProfileClimatePageAPIController();
        Response response = entityClimateProfileApiController
                .getSectorComparisonChartContentResponseWithTemperedToken(orbis_id, researchLine);
        response.then().assertThat().statusCode(403);
        assertTestCase.assertEquals(response.path("message").toString(),"Forbidden","Forbidden message is displayed");
        response.then().log().all();
        test.pass("Successfully validated for " + researchLine + "research line");
    }
}
