package com.esgc.APIModels;

import com.esgc.APIModels.EMC.Application;
import com.esgc.APIModels.EMC.AssignedApplication;
import com.esgc.APIModels.EMC.AssignedUser;
import com.esgc.APIModels.EMC.User;
import com.esgc.EMCEndpoints;
import com.esgc.TestBase.TestBase;
import com.esgc.TestBases.APITestBase;
import com.esgc.Utilities.API.Endpoints;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Environment;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.JavascriptExecutor;

import java.sql.Timestamp;
import java.util.List;

import static io.restassured.RestAssured.given;

public class EMCAPIController extends APITestBase {
    boolean isInvalidTest = false;
    public String ON_DEMAND_PRODUCT_ID;

    RequestSpecification configSpec() {
        if(System.getProperty("token") == null) {
            String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.accessToken";
            String accessToken = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
            System.setProperty("token", accessToken);
            System.out.println("token = " + accessToken);
        }
        if (isInvalidTest) {
            resetInvalid();
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
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_ADMIN_USERS);
        try {
            response = configSpec()
                    .when()
                    .get(EMCEndpoints.EMC_ADMIN_USERS);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }
    public Response getEMCAllAdminUsersPermissionsResponse() {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + Endpoints.EMC_ADMIN_USERS_PERMISSIONS);
        try {
            response = configSpec()
                    .when()
                    .get(Endpoints.EMC_ADMIN_USERS_PERMISSIONS);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response getEMCAllRolesResponse() {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_ADMIN_ROLES);
        try {
            response = configSpec()
                    .when()
                    .get(EMCEndpoints.EMC_ADMIN_ROLES);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response getEMCUserRolesResponse(String userID) {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_ADMIN_USERS + "/" + userID + "/roles");
        try {
            response = configSpec()
                    .when()
                    .get(EMCEndpoints.EMC_ADMIN_USERS + "/" + userID + "/roles");

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response getEMCRoleUsersResponse(String roleID) {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_ADMIN_ROLES + "/" + roleID + "/users");
        try {
            response = configSpec()
                    .when()
                    .get(EMCEndpoints.EMC_ADMIN_ROLES + "/" + roleID + "/users");

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response postEMCNewUserResponse(String provider, String firstName, String lastName, String userName, String email, boolean isActive, String accountId) {
        System.out.println("Creating new user");
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_USER);
        String payload = "{\"provider\":\"" + provider + "\",\"firstName\":\"" + firstName + "\",\"lastName\":\"" + lastName + "\",\"userName\":\"" + userName + "\",\"email\":\"" + email + "\",\"activate\":\"" + isActive + "\",\"accountId\":\"" + accountId + "\"}";
        System.out.println("payload = " + payload);
        try {
            response = configSpec()
                    .and().body(payload)
                    .when()
                    .post(EMCEndpoints.EMC_USER);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        System.out.println();
        return response;
    }

    public Response postEMCNewUserResponse(String provider, String firstName, String lastName, String userName, String email, boolean isActive, String accountId, boolean isInvalid) {
        isInvalidTest = isInvalid;
        return postEMCNewUserResponse(provider, firstName, lastName, userName, email, isActive, accountId);
    }

    public Response putEMCUserResponse(String email, User user) {
        System.out.println("Updating user with email = " + email);
        Response response = null;

        try {
            response = configSpec()
                    .and().body(user)
                    .when().put(EMCEndpoints.EMC_USER + "/" + email);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response deleteEMCUserResponse(String email) {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_USER + "/" + email);
        try {
            response = configSpec()
                    .when()
                    .delete(EMCEndpoints.EMC_USER + "/" + email);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response getEMCAllAccountsResponse() {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_ACCOUNTS);
        try {
            response = configSpec()
                    .when()
                    .get(EMCEndpoints.EMC_ACCOUNTS).prettyPeek();

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response getEMCUserDetailsResponse(String email) {
        System.out.println("Getting user details for : " + email);
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_USER);
        try {
            response = configSpec()
                    .when()
                    .get(EMCEndpoints.EMC_USER + "/" + email);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response getEMCAllUsersResponse() {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_USER);
        try {
            response = configSpec()
                    .when()
                    .get(EMCEndpoints.EMC_USER);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response getEMCAllApplicationsResponse() {
        System.out.println("Getting all applications");
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_APPS);
        try {
            response = configSpec()
                    .when()
                    .get(EMCEndpoints.EMC_APPS);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        System.out.println();
        return response;
    }

    /**
     * This method is used to create a new application in EMC
     * @param key "key is a required field"
     * @param name "name is a required field"
     * @param url "url is a required field","url must be a valid URL"
     * @param provider "provider is a required field","provider must be one of the following values: mss, ma
     * @param type "type is a required field","type must be one of the following values: SinglePageApplication, ExternalApplication, WebApplication"
     * @return
     */
    public Response postEMCNewApplicationResponse(String key, String name, String url, String provider, String type) {
        System.out.println("Creating new application with key = " + key);
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_APPS);
        String payload = "{" +
                "\"key\":\"" + key + "\"," +
                "\"name\":\"" + name + "\"," +
                "\"url\":\"" + url + "\"," +
                "\"provider\":\"" + provider + "\"," +
                "\"type\":\"" + type + "\"" +
                "}";
        System.out.println("payload = " + payload);
        try {
            response = configSpec()
                    .and().body(payload)
                    .when()
                    .post(EMCEndpoints.EMC_APPS);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        System.out.println();
        return response;
    }

    public Response getEMCApplicationDetailsResponse(String applicationId) {
        System.out.println("Getting application details for : " + applicationId);
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_APPS);
        try {
            response = configSpec()
                    .when()
                    .get(EMCEndpoints.EMC_APPS + "/" + applicationId);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        System.out.println();
        return response;
    }

    public Response putEMCApplicationResponse(String applicationId, Application application) {
        System.out.println("Updating application with id = " + applicationId);
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_APPS);
        try {
            response = configSpec()
                    .and().body(application)
                    .when()
                    .put(EMCEndpoints.EMC_APPS + "/" + applicationId);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        System.out.println();
        return response;
    }

    public Response deleteEMCApplicationResponse(String applicationId) {
        System.out.println("Deleting application with id = " + applicationId);
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_APPS);
        try {
            response = configSpec()
                    .when()
                    .delete(EMCEndpoints.EMC_APPS + "/" + applicationId);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        System.out.println();
        return response;
    }

    public Response getEMCAllApplicationsForAccountResponse(String accountId) {
        System.out.println("Getting all applications for account : " + accountId);
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_ACCOUNTS + "/" + accountId + "/applications");
        try {
            response = configSpec()
                    .when()
                    .get(EMCEndpoints.EMC_ACCOUNTS + "/" + accountId + "/applications");

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        System.out.println();
        return response;
    }

    public Response assignApplicationToAccountResponse(String accountId, String applicationId) {
        System.out.println("Assigning application to account");
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_ACCOUNTS + "/" + accountId + "/applications/" + applicationId);
        try {
            response = configSpec()
                    .when().put(EMCEndpoints.EMC_ACCOUNTS + "/" + accountId + "/applications/" + applicationId);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        System.out.println();
        return response;
    }

    public Response assignApplicationToAccountResponse(String accountId, String applicationId, boolean isInvalid) {
        isInvalidTest = isInvalid;
        return assignApplicationToAccountResponse(accountId, applicationId);
    }

    public Response deleteEMCRemoveApplicationFromAccountResponse(String accountId, String qaTestApplicationId) {
        System.out.println("Removing application from account");
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_ACCOUNTS + "/" + accountId + "/applications");
        try {
            response = configSpec()
                    .when()
                    .delete(EMCEndpoints.EMC_ACCOUNTS + "/" + accountId + "/applications/" + qaTestApplicationId);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        System.out.println();
        return response;
    }

    public Response deleteEMCRemoveApplicationFromAccountResponse(String accountId, String applicationId, boolean isInvalid) {
        isInvalidTest = isInvalid;
        return deleteEMCRemoveApplicationFromAccountResponse(accountId, applicationId);
    }

    public boolean verifyApplication(String accountId, String applicationID) {
        System.out.println("Verifying application with id = " + applicationID);
        Response response = getEMCAllApplicationsForAccountResponse(accountId);
        //response.prettyPrint();
        List<AssignedApplication> applications = response.jsonPath().getList("", AssignedApplication.class);
        //System.out.println("applications.size() = " + applications.size());
        for (AssignedApplication application : applications) {
            //System.out.println("application = " + application.getApplicationId());
            if (application.getApplicationId().equals(applicationID)) {
                System.out.println("Application found with id = " + applicationID);
                return true;
            }
        }
        System.out.println("Application not found with id = " + applicationID);
        return false;
    }

    public boolean verifyApplication(String applicationID) {
        System.out.println("Verifying application with id = " + applicationID);
        Response response = getEMCAllApplicationsResponse();
//        response.prettyPrint();
        List<Application> applications = response.jsonPath().getList("", Application.class);
        //System.out.println("applications.size() = " + applications.size());
        for (Application application : applications) {
            //System.out.println("application = " + application.getApplicationId());
            if (application.getId().equals(applicationID)) {
                System.out.println("Application found with id = " + applicationID);
                return true;
            }
        }
        System.out.println("Application not found with id = " + applicationID);
        return false;
    }

    public boolean verifyUser(String accountId, String userId) {
        System.out.println("Verifying user with id = " + userId);
        Response response = getListOfUsersResponse(accountId);
        //response.prettyPrint();
        List<AssignedUser> users = response.jsonPath().getList("", AssignedUser.class);
        for (AssignedUser user : users) {
            if (user.getId().equals(userId)) {
                System.out.println("User found with id = " + userId);
                return true;
            }
        }
        System.out.println("User not found with id = " + userId);
        return false;
    }

    public Response getListOfUsersResponse(String accountId) {
        System.out.println("Getting list of users for account : " + accountId);
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_ACCOUNTS + "/" + accountId + "/users");
        try {
            response = configSpec()
                    .when()
                    .get(EMCEndpoints.EMC_ACCOUNTS + "/" + accountId + "/users");

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        System.out.println();
        return response;
    }

    public Response getEMCProductsForApplicationResponse(String applicationId) {
        System.out.println("Getting all products for application : " + applicationId);
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_APPS + "/" + applicationId + "/products");
        try {
            response = configSpec()
                    .when()
                    .get(EMCEndpoints.EMC_APPS + "/" + applicationId + "/products");

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        System.out.println();
        return response;
    }

    public Response getEMCProductsForApplicationResponse(String applicationId, boolean isInvalid) {
        setInvalid();
        return getEMCProductsForApplicationResponse(applicationId);
    }

    public Response putRoleToUser(String applicationRoleId, String email) {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.PUT_APPLICATION_ROLE);
        try {
            response = configSpec()
                    .when()
                    .pathParam(applicationRoleId, "application-role-id")
                    .pathParam(email, "email")
                    .put(EMCEndpoints.PUT_APPLICATION_ROLE).prettyPeek();

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response postAccount(String name) {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_ACCOUNTS);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String time = timestamp.toString().replace(" ", "T") + "Z";

        String body = "{\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"status\": true,\n" +
                "    \"contractStartTimestamp\": \"" + time + "\",\n" +
                "    \"contractEndTimestamp\": \"" + time + "\",\n" +
                "    \"subscriberType\": \"\"\n" +
                "}";
        try {
            response = configSpec()
                    .when()
                    .body(body)
                    .post(EMCEndpoints.EMC_ACCOUNTS).prettyPeek();

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response getApplicationToAccount(String accountId, String applicationId) {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.GET_APPLICATIONS);

        try {
            response = configSpec()
                    .when()
                    .get(EMCEndpoints.GET_APPLICATIONS).prettyPeek();

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response putApplicationToAccount(String accountId, String applicationId) {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.PUT_APPLICATION_TO_ACCOUNT);

        try {
            response = configSpec()
                    .when()
                    .pathParam(applicationId, "application-id")
                    .pathParam(accountId, "account-id")
                    .put(EMCEndpoints.PUT_APPLICATION_TO_ACCOUNT).prettyPeek();

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }


    public Response putProductToAccount(String accountId, String applicationId, String productId) {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.PUT_PRODUCT_TO_ACCOUNT);
        String body = "";
        if (productId.equals(ON_DEMAND_PRODUCT_ID)) {
            body = "{\n" +
                    "    \"info\": {\n" +
                    "        \"AssessmentsInfo\": {\n" +
                    "            \"UsedAssessments\": 0,\n" +
                    "            \"PurchasedAssessments\": 100\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";
        }

        try {
            response = configSpec()
                    .when()
                    .body(body)
                    .pathParam(accountId, "account-id")
                    .pathParam(applicationId, "application-id")
                    .pathParam(productId, "product-id")
                    .put(EMCEndpoints.PUT_PRODUCT_TO_ACCOUNT).prettyPeek();

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response getSectionsOfProduct(String productId) {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.GET_PRODUCT_SECTIONS);

        try {
            response = configSpec()
                    .when()
                    .pathParam(productId, "product-id")
                    .get(EMCEndpoints.GET_PRODUCT_SECTIONS).prettyPeek();

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response getUsersForRole(String roleId) {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_ROLE_USERS);
        try{
            response = configSpec()
                    .pathParam("roleId", roleId)
                    .when().get(EMCEndpoints.EMC_ROLE_USERS);
        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public void assignRoleToUser(String email, String roleId) {
        if(verifyUserForRole(email, roleId)){
            System.out.println("User already assigned to role");
            return;
        }
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_ROLE_USER_CRUD);
        try{
            response = configSpec()
                    .pathParam("roleId", roleId)
                    .pathParam("userId", email)
                    .when().put(EMCEndpoints.EMC_ROLE_USER_CRUD).prettyPeek();
        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
    }

    public void deleteUserFromRole(String email, String roleId) {
        if(!verifyUserForRole(email, roleId)){
            System.out.println("User already not assigned to role");
            return;
        }
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_ROLE_USER_CRUD);
        try{
            response = configSpec()
                    .pathParam("roleId", roleId)
                    .pathParam( "userId", email)
                    .when().delete(EMCEndpoints.EMC_ROLE_USER_CRUD).prettyPeek();
        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
    }

    public boolean verifyUserForRole(String email, String roleId) {
        return getUsersForRole(roleId).jsonPath().getList("userName").contains(email);
    }

    public void createApplicationAndVerify(String key, String name, String url, String provider, String type) {
        Response response = postEMCNewApplicationResponse(key, name, url, provider, type);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 201, "Status code 201 Created is verified");
        assertTestCase.assertEquals(response.path("name"), "CreatedResponse", "Application creation response name is verified");
        assertTestCase.assertEquals(response.path("message"), "Application " + key + " created", "Application creation response message with key is verified");
        assertTestCase.assertTrue(verifyApplication(key), "Application creation response type is verified");
    }

    public void deleteApplicationAndVerify(String key) {
        //Delete Application
        Response response = deleteEMCApplicationResponse(key);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 403, "Status code 204 OK is verified");
        assertTestCase.assertEquals(response.path("name"), "DeletedResponse", "Application delete response name is verified");
        //assertTestCase.assertEquals(response.path("message"), "Missing Authentication Token", "Application delete response message is verified");
        assertTestCase.assertFalse(verifyApplication(key), "Application is deleted successfully");
    }
}
