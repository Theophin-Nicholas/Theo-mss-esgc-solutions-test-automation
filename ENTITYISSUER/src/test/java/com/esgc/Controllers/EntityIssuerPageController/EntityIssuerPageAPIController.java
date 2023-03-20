package com.esgc.Controllers.EntityIssuerPageController;

import com.esgc.APIModels.EntityIssuerPage.DriverDetailPayload;
import com.esgc.APIModels.EntityIssuerPage.ESGCategories;
import com.esgc.APIModels.EntityIssuerPage.EntityFilterPayload;
import com.esgc.APIModels.EntityIssuerPage.SubCategories;
import com.esgc.Utilities.APIUtulities.EntityIssuerPageEndPoints;
import com.esgc.Utilities.Environment;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;


public class EntityIssuerPageAPIController {

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
                    .header("Authorization", "Bearer " +
                            System.getProperty("token").substring(0, System.getProperty("token").length() - 2))
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

    public Response getSourceDocument(String orbis_id) {
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .body("{\"orbis_id\":\"" + orbis_id + "\"}")
                    .post(EntityIssuerPageEndPoints.POST_SOURCE_DOCUMENTS).prettyPeek();

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }

    public Response getSummaryWidget(String orbis_id) {
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .body("{\"orbis_id\":\"" + orbis_id + "\"}")
                    .post(EntityIssuerPageEndPoints.POST_SUMMARY).prettyPeek();

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }

    public Response getHeaderDetails() {
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .post(EntityIssuerPageEndPoints.POST_HEADER).prettyPeek();

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }

    public Response getControversiesAPI(String orbis_id) {
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .body("{\"orbis_id\":\"" + orbis_id + "\"}")
                    .post(EntityIssuerPageEndPoints.POST_CONTROVERSIES).prettyPeek();

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }


    public Response getIssuerSummary() {
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .post(EntityIssuerPageEndPoints.POST_ISSUER_SUMMARY);
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
                    .post(EntityIssuerPageEndPoints.POST_ENTITY_CONTROVERSIES);

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
                    .post(EntityIssuerPageEndPoints.POST_DRIVERS_SUMMARY);
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
                    .post(EntityIssuerPageEndPoints.POST_SECTOR_ALLOCATION);
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
                    .post(EntityIssuerPageEndPoints.POST_DRIVERS_DETAILS).prettyPeek();

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
                    .post(EntityIssuerPageEndPoints.POST_HEADER);
            System.out.println(response.prettyPrint());

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }
    // ESG content to be de-scoped , remove. check with furkan ???

    public static  List<ESGCategories> getESGSubCategories(String methodology) {

        List<ESGCategories> categories = new ArrayList<>();

        switch (methodology) {
            case "Environmental":
                categories.add(new ESGCategories(methodology,  getSubcategoriesPojo(Arrays.asList(new String[]
                        {"Environmental Protection","Transition Risks",
                                "Biodiversity","Water Management","Air Emissions","Material Flows", "Physical Risks"}))));
                break;

            case "Social":
                categories.add(new ESGCategories(methodology,  getSubcategoriesPojo(Arrays.asList(new String[]
                        {
                        "Labor Rights & Relations",
                        "Reorganizations",
                        "Career Development",
                        "Health & Safety",
                        "Wages & Work Hours",
                        "Diversity & Inclusion",
                        "Fundamental Human Rights",
                        "Modern Slavery",
                        "Social & Economic Development",
                        "Societal Impact",
                        "Responsible Tax",
                        "Product Safety",
                        "Customer Engagement"
                        }))));

                break;

            case "Governance":
                categories.add(new ESGCategories(methodology, getSubcategoriesPojo(Arrays.asList(new String[]
                        {
                                "Supplier Relations",
                                "Sustainable Sourcing",
                                "Business Ethics",
                                "Competition",
                                "Lobbying",
                                "Board",
                                "Internal Controls & Risk Management",
                                "Stakeholder Relations",
                                "Executive Remuneration",
                                "Cyber & Technological Risks"
                        }))));

                break;
        }
        return categories;
    }

    public static List<SubCategories> getSubcategoriesPojo (List<String> list){
        List<SubCategories> subcat = new ArrayList<>();
        for(String e : list){
            subcat.add(new SubCategories(e));
        }
      return  subcat;
    }
    public Response getVAlidateURLApiResponse(String url) {
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .body("{\"url\":\"" + url + "\"}")
                    .post(EntityIssuerPageEndPoints.POST_VALIDATE_URL).prettyPeek();

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }
}
