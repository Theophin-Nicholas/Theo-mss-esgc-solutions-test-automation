package com.esgc.APIModels;

import com.esgc.APIModels.EMC.User;
import com.esgc.TestBase.TestBase;
import com.esgc.Utilities.API.Endpoints;
import com.esgc.Utilities.Environment;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class EMCAPIController extends TestBase {
    boolean isInvalidTest = false;
    RequestSpecification configSpec() {
        //getAccessToken();
        if (isInvalidTest) {
            return given().accept(ContentType.JSON)
                    .baseUri(Environment.EMC_URL)
                    .relaxedHTTPSValidation()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .log().ifValidationFails();
        } else {
            return given().accept(ContentType.JSON)
                    .baseUri(Environment.EMC_URL)
                    .relaxedHTTPSValidation()
                    .header("authorizationToken", "Bearer " + System.getProperty("token"))
                    //.header("Authorization", "Bearer " + System.getProperty("token"))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .log().ifValidationFails();
        }
    }

    public void setInvalid() {
        this.isInvalidTest = true;
    }

    public void resetInvalid() {
        this.isInvalidTest = false;
    }

    public Response getEMCAllAdminUsersResponse() {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + Endpoints.GET_EMC_ALL_ADMIN_USERS);
        try {
            response = configSpec()
                    .when()
                    .get(Endpoints.GET_EMC_ALL_ADMIN_USERS);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response getEMCAllRolesResponse() {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + Endpoints.GET_EMC_ALL_ROLES);
        try {
            response = configSpec()
                    .when()
                    .get(Endpoints.GET_EMC_ALL_ROLES);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response getEMCUserRolesResponse(String userID) {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + Endpoints.GET_EMC_ALL_ADMIN_USERS+"/"+userID+"/roles");
        try {
            response = configSpec()
                    .when()
                    .get(Endpoints.GET_EMC_ALL_ADMIN_USERS+"/"+userID+"/roles");

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response getEMCRoleUsersResponse(String roleID) {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + Endpoints.GET_EMC_ALL_ROLES+"/"+roleID+"/users");
        try {
            response = configSpec()
                    .when()
                    .get(Endpoints.GET_EMC_ALL_ROLES+"/"+roleID+"/users");

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response postEMCNewUserResponse(String provider, String firstName, String lastName, String userName, String email, boolean isActive, String accountId) {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + Endpoints.POST_EMC_NEW_USER);
        String payload = "{\"provider\":\""+provider+"\",\"firstName\":\""+firstName+"\",\"lastName\":\""+lastName+"\",\"userName\":\""+userName+"\",\"email\":\""+email+"\",\"activate\":\""+isActive+"\",\"accountId\":\""+accountId+"\"}";
        System.out.println("payload = " + payload);
        try {
            response = configSpec()
                    .and().body(payload)
                    .when()
                    .post(Endpoints.POST_EMC_NEW_USER);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        response.prettyPrint();
        return response;
    }

    public Response putEMCUserResponse(String email, User user) {
        System.out.println("Updating user with email = " + email);
        Response response = null;

        try {
            response = configSpec()
                    .and().body(user)
                    .when().put(Endpoints.GET_EMC_USER+"/"+email);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response deleteEMCUserResponse(String email) {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + Endpoints.GET_EMC_USER+"/"+email);
        try {
            response = configSpec()
                    .when()
                    .delete(Endpoints.GET_EMC_USER+"/"+email);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response getEMCAllAccountsResponse() {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + Endpoints.GET_EMC_ALL_ACCOUNTS);
        try {
            response = configSpec()
                    .when()
                    .get(Endpoints.GET_EMC_ALL_ACCOUNTS);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response getEMCUserDetailsResponse(String email) {
        System.out.println("Getting user details for : " + email);
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + Endpoints.GET_EMC_USER);
        try {
            response = configSpec()
                    .when()
                    .get(Endpoints.GET_EMC_USER+"/"+email);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response getEMCAllUsersResponse() {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + Endpoints.GET_EMC_USER);
        try {
            response = configSpec()
                    .when()
                    .get(Endpoints.GET_EMC_USER);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }
}
