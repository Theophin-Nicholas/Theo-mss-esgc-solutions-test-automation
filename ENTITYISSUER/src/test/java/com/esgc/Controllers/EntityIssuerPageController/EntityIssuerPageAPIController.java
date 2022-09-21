package com.esgc.Controllers.EntityIssuerPageController;

import com.esgc.APIModels.EntityIssuerPage.DriverDetailPayload;
import com.esgc.Utilities.API.EntityPageEndpoints;
import com.esgc.Utilities.Environment;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;


public class EntityIssuerPageAPIController {

    private RequestSpecification configSpec() {
        return given().accept(ContentType.JSON)
                .baseUri(Environment.URL)
                .relaxedHTTPSValidation()
                .header("Authorization", "Bearer " + System.getProperty("token"))
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .log().ifValidationFails();
    }

    public Response getSouceDocument(String orbis_id) {
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .body("{\"orbis_id\":\"" + orbis_id +"\"}")
                    .post(EntityIssuerPageEndPoints.POST_sourceDocuments);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println(response.prettyPrint());
        return response;
    }

    public Response getSummaryWidget(String orbis_id) {
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .body("{\"orbis_id\":\"" + orbis_id +"\"}")
                    .post(EntityIssuerPageEndPoints.POST_SUMMARY);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println(response.prettyPrint());
        return response;
    }

    public Response getHeaderDetails() {
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .post(EntityIssuerPageEndPoints.POST_HEADER);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println(response.prettyPrint());
        return response;
    }

    public Response getControversiesAPI(String orbis_id) {
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .body("{\"orbis_id\":\"" + orbis_id +"\"}")
                    .post(EntityIssuerPageEndPoints.POST_CONTROVERSIES);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println(response.prettyPrint());
        return response;
    }

    public Response getDriverDetails(DriverDetailPayload apiFilterPayload) {
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .body(apiFilterPayload)
                    //.body("{\"orbis_id\":\"" + orbisId + "\", \"criteria\":\"" + criteria + "\"}")
                    .post(EntityPageEndpoints.POST_DRIVERS_DETAILS);
            System.out.println(response.prettyPrint());

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }


}
