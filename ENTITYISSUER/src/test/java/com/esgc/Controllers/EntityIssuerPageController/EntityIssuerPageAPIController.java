package com.esgc.Controllers.EntityIssuerPageController;

import com.esgc.APIModels.EntityIssuerPage.DriverDetailPayload;
import com.esgc.APIModels.EntityIssuerPage.ESGCategories;
import com.esgc.APIModels.EntityIssuerPage.SubCategories;
import com.esgc.APIModels.RangeAndScoreCategory;
import com.esgc.Utilities.API.EntityPageEndpoints;
import com.esgc.Utilities.APIUtulities.EntityIssuerPageEndPoints;
import com.esgc.Utilities.Environment;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.snowflake.client.jdbc.internal.net.minidev.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
