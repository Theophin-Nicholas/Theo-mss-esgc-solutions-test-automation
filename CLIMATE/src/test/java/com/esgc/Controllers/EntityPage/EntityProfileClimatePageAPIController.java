package com.esgc.Controllers.EntityPage;

import com.esgc.Utilities.API.EntityClimateProfilePageEndpoints;
import com.esgc.Utilities.API.EntityPageEndpoints;
import com.esgc.Utilities.Environment;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

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

                    .when()
                    .post(EntityClimateProfilePageEndpoints.POST_climateSummary);

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

                    .when().post(EntityClimateProfilePageEndpoints.POST_climateTempProjection);

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
                    .when().post(EntityClimateProfilePageEndpoints.POST_ESGClimateSummary);

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
                    .post(EntityClimateProfilePageEndpoints.POST_climateSummary);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }

    public Response getClimateSummaryAPIResponseWithInvalidToken() {
        Response response = null;
        String fullURI = "https://solutions-dev-us-east-1.mra-esg-nprd.aws.moodys.tld/api/portfolios/00000000-0000-0000-0000-000000000000/transitionrisk/temperaturealgmt/sector-temp-rise";
        try {
            response = given().accept(ContentType.JSON)
                    //.header("Authorization", System.getProperty("token"))//wrong token
                    .when().get(fullURI);
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
                    .post(EntityClimateProfilePageEndpoints.POST_climateSectorComparison);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }

    public Response getComparisonChartContentResponse(String orbis_id, String research_line) {
        Response response = null;

        System.out.println("URL = " + Environment.URL + EntityClimateProfilePageEndpoints.POST_climateSectorComparison);
        try {
            response = configSpec()
                    .pathParam("orbis_id", orbis_id)
                    .pathParam("research_line", apiResourceMapperForClimateSectorComparison(research_line))
                    .when()
                    .post(EntityClimateProfilePageEndpoints.POST_climateSectorComparison);

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
                return "transitionrisk/brownshareasmt";

            case "Green Share":
            case "greenshareasmt":
                return "transitionrisk/greenshareasmt";

            case "physicalriskhazard":
                return "physicalrisk/physicalriskhazard";


            case "Temperature Alignment":
            case "temperaturealgmt":
                return "transitionrisk/temperaturealgmt";

            case "Carbon Footprint":
            case "carbonfootprint":
                return "transitionrisk/carbonfootprint";

            case "Physical Risk Management":
            case "physicalriskmgmt":
                return "physicalrisk/physicalriskmgmt";

        }
        return "";
    }

    private String apiResourceMapperForClimateSectorComparison(String researchLine) {
        switch (researchLine) {

            case "Carbon Footprint":
            case "carbonfootprint":
                return "transitionrisk/carbonfootprint";

            case "Operations Risk":
            case "operationsrisk":
                return "physicalrisk/operationsrisk";

            case "Market Risk":
            case "marketrisk":
                return "physicalrisk/marketrisk";

            case "Supply Chain Risk":
            case "supplychainrisk":
                return "physicalrisk/supplychainrisk";
        }
        return "";
    }

    public Response getEntityUnderLyingPhysicalHazardAPIResponse(String orbis_id) {
        Response response = null;
        try {
            response = configSpec()
                    .pathParam("orbis_id", orbis_id)
                    .when()
                    .post(EntityClimateProfilePageEndpoints.POST_EntityUnderlyingDataMetrics);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }

    public Response getHeaderDetailsWithPayload(String payload) {
        Response response = null;
        try {
            response = configSpec()
                    .body(payload)
                    .when()
                    .post(EntityPageEndpoints.POST_HEADER);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        assert response != null;
        response.prettyPrint();
        return response;
    }

    public Response geCompanyHeaderAPIResponse(String orbis_id) {
        Response response = null;
        response = configSpec()
                .body("{\"orbis_id\":\"" + orbis_id+ "\"}")

                .when()
                .post(EntityClimateProfilePageEndpoints.POST_Header);
        System.out.println(response.prettyPrint());
        return response;
    }
}
