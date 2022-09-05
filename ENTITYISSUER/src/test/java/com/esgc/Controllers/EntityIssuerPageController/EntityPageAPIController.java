package com.esgc.Controllers.EntityIssuerPageController;

import com.esgc.APIModels.EntityIssuerPage.DriverDetailPayload;
import com.esgc.APIModels.EntityPage.EntityFilterPayload;
import com.esgc.Utilities.API.EntityPageEndpoints;
import com.esgc.Utilities.Environment;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;


public class EntityPageAPIController {



    boolean isInvalidTest = false;
    public void setInvalid() {
        this.isInvalidTest = true;
    }

    public void resetInvalid() {
        this.isInvalidTest = false;
    }



    private RequestSpecification configSpec() {
        if (isInvalidTest) {
            return given().accept(ContentType.JSON)
                    .baseUri(Environment.URL)
                    .relaxedHTTPSValidation()
                    .header("Authorization", "Bearer " + System.getProperty("token").substring(0,System.getProperty("token").length()-2))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .log().ifValidationFails();
        } else {
            return given().accept(ContentType.JSON)
                    .baseUri(Environment.ENTITY_URL)
                    .relaxedHTTPSValidation()
                    .header("Authorization", "Bearer " + System.getProperty("token"))
                    .header("Accept", "application/json")
                    .header("Content-type", "application/json")
                    .log().ifValidationFails();
        }
    }
            public Response getIssuerSummary() {
                Response response = null;
                try {
                    response = configSpec()
                            .when()
                            .post(EntityPageEndpoints.POST_Issuer_Summary);
                    System.out.println(response.prettyPrint());

                } catch (Exception e) {
                    System.out.println("Inside exception " + e.getMessage());
                }

                return response;
            }

            public Response getControversies(EntityFilterPayload apiFilterPayload) {
                Response response = null;
                System.out.println(configSpec()
                        .body(apiFilterPayload));
                try {
                    response = configSpec()
                            .body(apiFilterPayload)
                            .when()
                            .post(EntityPageEndpoints.POST_ENTITY_CONTROVERSIES);

                } catch (Exception e) {
                    System.out.println("Inside exception " + e.getMessage());
                }

                return response;
            }


            public Response getDriverSummary() {
                Response response = null;
                try {
                    response = configSpec()
                            .when()
                            .post(EntityPageEndpoints.POST_DRIVERS_SUMMARY);
                    System.out.println(response.prettyPrint());

                } catch (Exception e) {
                    System.out.println("Inside exception " + e.getMessage());
                }

                return response;
            }

            public Response getSectorDrivers(String orbisId) {
                //OrbisIds are based in API Key
                System.out.println("Request Sent..");
                Response response = null;
                try {
                    response = configSpec()
                            .when()
                            .body("{\"orbis_id\":\"" + orbisId + "\"}")
                            .post(EntityPageEndpoints.POST_SECTOR_ALLOCATION);
                   // System.out.println(response.prettyPrint());
                    System.out.println("Response Printed...");
                } catch (Exception e) {
                    System.out.println("Inside exception " + e.getMessage());
                }

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

            public Response getHeaderAPI() {
                Response response = null;
                try {
                    response = configSpec()
                            .when()
                            .post(EntityPageEndpoints.POST_HEADER);
                    System.out.println(response.prettyPrint());

                } catch (Exception e) {
                    System.out.println("Inside exception " + e.getMessage());
                }

                return response;
            }



        }

