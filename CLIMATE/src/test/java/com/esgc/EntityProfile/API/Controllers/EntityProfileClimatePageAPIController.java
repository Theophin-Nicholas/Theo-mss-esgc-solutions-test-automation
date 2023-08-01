package com.esgc.EntityProfile.API.Controllers;

import com.esgc.EntityProfile.API.EntityProfilePageEndpoints;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Environment;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.JavascriptExecutor;

import static io.restassured.RestAssured.given;


public class EntityProfileClimatePageAPIController {

    private RequestSpecification configSpec() {
        return given().accept(ContentType.JSON)
                .baseUri(Environment.URL)
                .relaxedHTTPSValidation()
                .header("Authorization", "Bearer " + System.getProperty("token"))
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .log().ifValidationFails();
    }

    public Response getClimateSummaryAPIResponse(String orbis_id, String research_line) {
        Response response = null;
        try {
            response = configSpec()
                    .pathParam("orbis_id", orbis_id)
                    .pathParam("research_line", apiResourceMapper(research_line))
                    .log().all()
                    .when()
                    .post(EntityProfilePageEndpoints.POST_CLIMATE_SUMMARY).prettyPeek();

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println(response.prettyPrint());
        return response;
    }

    public Response getClimateTempProjection(String orbis_id) {
        Response response = null;
        try {
            response = configSpec()
                    .pathParam("orbis_id", orbis_id)
                    //.pathParam("research_line", apiResourceMapper(research_line))

                    .when().post(EntityProfilePageEndpoints.POST_CLIMATE_TEMPERATURE_ALIGNMENT_PROJECTION);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }

    public Response getESGClimateSummary(String orbis_id) {
        Response response = null;
        try {
            response = configSpec()
                    .pathParam("orbis_id", orbis_id)
                    .when().post(EntityProfilePageEndpoints.POST_ESG_CLIMATE_SUMMARY);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }

    public Response getClimateSummaryAPIResponseWithTemperedToken(String orbis_id, String research_line) {
        Response response = null;
        try {
            response = given().accept(ContentType.JSON)
                    .baseUri(Environment.URL)
                    .relaxedHTTPSValidation()
                    .header("Authorization", "Bearer " + System.getProperty("token").substring(3))
                    .header("Accept", "application/json")
                    .header("Content-type", "application/json")
                    .log().ifValidationFails()
                    .pathParam("orbis_id", orbis_id)
                    .pathParam("research_line", apiResourceMapper(research_line))
                    .when()
                    .post(EntityProfilePageEndpoints.POST_CLIMATE_SUMMARY);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }

    public Response getSectorComparisonChartContentResponseWithTemperedToken(String orbis_id, String research_line) {
        Response response = null;
        try {
            response = given().accept(ContentType.JSON)
                    .baseUri(Environment.URL)
                    .relaxedHTTPSValidation()
                    .header("Authorization", "Bearer " + System.getProperty("token").substring(3))
                    .header("Accept", "application/json")
                    .header("Content-type", "application/json")
                    .log().ifValidationFails()
                    .pathParam("orbis_id", orbis_id)
                    .pathParam("research_line", apiResourceMapperForClimateSectorComparison(research_line))
                    .when()
                    .post(EntityProfilePageEndpoints.POST_CLIMATE_SECTOR_COMPARISON);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }

    public Response getComparisonChartContentResponse(String orbis_id, String research_line) {
        Response response = null;

        System.out.println("URL = " + Environment.URL + EntityProfilePageEndpoints.POST_CLIMATE_SECTOR_COMPARISON);
        try {
            response = configSpec()
                    .pathParam("orbis_id", orbis_id)
                    .pathParam("research_line", apiResourceMapperForClimateSectorComparison(research_line))
                    .when()
                    .post(EntityProfilePageEndpoints.POST_CLIMATE_SECTOR_COMPARISON);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println(response.prettyPrint());
        return response;
    }

    private String apiResourceMapper(String researchLine) {
        switch (researchLine) {
            case "Brown Share":
            case "brownshareasmt":
                return "brownshareasmt";

            case "Green Share":
            case "greenshareasmt":
                return "greenshareasmt";

            case "physicalriskhazard":
                return "physicalriskhazard";


            case "Temperature Alignment":
            case "temperaturealgmt":
                return "temperaturealgmt";

            case "Carbon Footprint":
            case "carbonfootprint":
                return "carbonfootprint";

            case "Physical Risk Management":
            case "physicalriskmgmt":
                return "physicalriskmgmt";

        }
        return "";
    }

    private String apiResourceMapperForClimateSectorComparison(String researchLine) {
        switch (researchLine) {

            case "Carbon Footprint":
            case "carbonfootprint":
                return "carbonfootprint";

            case "Operations Risk":
            case "operationsrisk":
                return "operationsrisk";

            case "Market Risk":
            case "marketrisk":
                return "marketrisk";

            case "Supply Chain Risk":
            case "supplychainrisk":
                return "supplychainrisk";
        }
        return "";
    }

    public Response getEntityUnderLyingDataMetricsAPIResponse(String orbis_id, String researchLine) {
        Response response = null;
        try {
            response = configSpec()
                    .pathParam("orbis_id", orbis_id)
                    .pathParam("research_line", researchLine)
                    .when()
                    .post(EntityProfilePageEndpoints.POST_ENTITY_UNDERLYING_DATA_METRICS);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }

    public Response getHeaderDetailsWithPayload(String orbisID) {
        Response response = null;
        try {
            response = configSpec()
                    .body("{\"orbis_id\":\"" + orbisID + "\"}")
                    .when()
                    .post(EntityProfilePageEndpoints.POST_HEADER);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        assert response != null;
        response.prettyPrint();
        return response;
    }

    public Response getEntityPageResponse(String orbisId, String api) {
        Response response = null;
        String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.accessToken";
        String accessToken = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
        System.setProperty("token", accessToken);

        try {
            response = configSpec()
                    .pathParam("orbisId", orbisId)
                    .pathParam("api", api)
                    .when()
                    .post(EntityProfilePageEndpoints.POST_ENTITY_PATH);
        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }

    public Response getEntityMaterialityMetrics(String orbis_id) {
        Response response = null;
        try {
            response = configSpec()
                    .body("{\"orbis_id\":\"" + orbis_id + "\"}")
                    .when()
                    .post(EntityProfilePageEndpoints.POST_ENTITY_MATERIALITY_METRICS).prettyPeek();

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }

    public Response geCompanyHeaderAPIResponse(String orbis_id) {
        Response response = null;
        response = configSpec()
                .body("{\"orbis_id\":\"" + orbis_id+ "\"}")

                .when()
                .post(EntityProfilePageEndpoints.POST_HEADER);
        System.out.println(response.prettyPrint());
        return response;
    }

    public Response getControversiesAPI(String payload) {
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
                    .log().all()
                    .post(EntityProfilePageEndpoints.POST_ENTITY_CONTROVERSIES);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("API Response: "+response.prettyPrint());
        return response;
    }
}
