package com.esgc.APIModels;

import com.esgc.APIModels.EMC.*;
import com.esgc.EMCEndpoints;
import com.esgc.TestBases.APITestBase;
import com.esgc.Utilities.API.Endpoints;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Environment;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.JavascriptExecutor;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class EMCAPIController extends APITestBase {
    boolean isInvalidTest = false;
    public String ON_DEMAND_PRODUCT_ID;

    RequestSpecification configSpec() {
        while (System.getProperty("token") == null) {
            try {
                String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.accessToken";
                String accessToken = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
                System.setProperty("token", accessToken);
                System.out.println("token = " + accessToken);
            } catch (Exception e) {
                System.out.println("Waiting for token to be set");
                BrowserUtils.wait(1);
            }
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

    RequestSpecification configSpec(String tokenName) {
        if (System.getProperty("token") == null) {
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
                    .header(tokenName, "Bearer " + System.getProperty("token"))
                    //.header("Authorization", "Bearer " + System.getProperty("token"))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .log().ifValidationFails();
        }
    }

    RequestSpecification configSpec(String tokenName, String tokenValue) {
        if (isInvalidTest) {
            resetInvalid();
            return given().accept(ContentType.JSON)
                    .baseUri(Environment.EMC_URL)
                    .relaxedHTTPSValidation()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .log().ifValidationFails();
        }
        return given().accept(ContentType.JSON)
                .baseUri(Environment.EMC_URL)
                .relaxedHTTPSValidation()
                .header(tokenName, tokenValue)
                //.header("Authorization", "Bearer " + System.getProperty("token"))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .log().ifValidationFails();
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

    public Response getAllUsersForAccount(String accountId) {
        System.out.println("Getting all users for account " + accountId);
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .pathParam("account-id", accountId)
                    .get(EMCEndpoints.GET_USERS);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public String getUserId(String accountId, String name) {
        List<AssignedUser> users = getAllUsersForAccount(accountId).jsonPath().getList("", AssignedUser.class);
        for (AssignedUser user : users) {
            if (user.getName().equals(name))
                return user.getId();
        }
        System.out.println("User with name " + name + " not found");
        return null;
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
                    .get(EMCEndpoints.EMC_ACCOUNTS);

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
//        System.out.println("Getting all applications");
        Response response = null;
//        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_APPS);
        try {
            response = configSpec()
                    .when()
                    .get(EMCEndpoints.EMC_APPS);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
//        System.out.println("Status Code = " + response.statusCode());
//        System.out.println();
        return response;
    }

    /**
     * This method is used to create a new application in EMC
     *
     * @param key      "key is a required field"
     * @param name     "name is a required field"
     * @param url      "url is a required field","url must be a valid URL"
     * @param provider "provider is a required field","provider must be one of the following values: mss, ma
     * @param type     "type is a required field","type must be one of the following values: SinglePageApplication, ExternalApplication, WebApplication"
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
                    .post(EMCEndpoints.EMC_ACCOUNTS);

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
                    .get(EMCEndpoints.GET_APPLICATIONS);

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
                    .put(EMCEndpoints.PUT_APPLICATION_TO_ACCOUNT);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }


    public Response putProductToAccount(String accountId, String applicationId, String productId) {
        if (!verifyApplication(accountId, applicationId))
            putApplicationToAccount(accountId, applicationId);
        Response response = null;

        try {
            response = configSpec()
                    .when()
                    .pathParam("account-id", accountId)
                    .pathParam("application-id", applicationId)
                    .pathParam("product-id", productId)
                    .put(EMCEndpoints.PUT_PRODUCT_TO_ACCOUNT);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Put Product To Account Status Code = " + response.statusCode());
        response.prettyPrint();
        return response;
    }

    public Response putProductToAccount(String accountId, String applicationId, String productId, int purchasedAssessments) {
        if (!verifyApplication(accountId, applicationId))
            putApplicationToAccount(accountId, applicationId);
        Response response = null;
        String body = "";
        ProductSection[] productSection = getSectionsOfProduct(productId).as(ProductSection[].class);
        if (productSection[0].getName().equals("AssessmentsInfo")) {
            System.out.println("Assigning SME Assessments to account by default parameters.");
            body = "{\n" +
                    "  \"info\": {\n" +
                    "    \"AssessmentsInfo\": {\n" +
                    "      \"PurchasedAssessments\": "+purchasedAssessments+",\n" +
                    "      \"UsedAssessments\": 0\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";
        }

        try {
            response = configSpec()
                    .when()
                    .body(body)
                    .pathParam("account-id", accountId)
                    .pathParam("application-id", applicationId)
                    .pathParam("product-id", productId)
                    .put(EMCEndpoints.PUT_PRODUCT_TO_ACCOUNT);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Put Product To Account Status Code = " + response.statusCode());
        response.prettyPrint();
        return response;
    }
    public Response getSectionsOfProduct(String productId) {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.GET_PRODUCT_SECTIONS);

        try {
            response = configSpec()
                    .when()
                    .pathParam("product-id", productId)
                    .get(EMCEndpoints.GET_PRODUCT_SECTIONS);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("getSectionsOfProduct Status Code = " + response.statusCode());
//        response.prettyPrint();
        return response;
    }

    /**
     * METHODS BELOW ARE RELATED TO CONFIGURATION PAGE - PERMISSION ROLES OPERATIONS
     */

    public Response getUsersForRole(String roleId) {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_ROLE_USERS);
        try {
            response = configSpec()
                    .pathParam("roleId", roleId)
                    .when().get(EMCEndpoints.EMC_ROLE_USERS);
        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
//        response.prettyPrint();
        return response;
    }

    public void assignRoleToUser(String email, String roleId) {
        if (verifyUserForRole(email, roleId)) {
            System.out.println("User already assigned to role");
            return;
        }
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_ROLE_USER_CRUD);
        try {
            response = configSpec()
                    .pathParam("roleId", roleId)
                    .pathParam("userId", email)
                    .when().put(EMCEndpoints.EMC_ROLE_USER_CRUD);
        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
    }

    public void deleteUserFromRole(String email, String roleId) {
        if (!verifyUserForRole(email, roleId)) {
            System.out.println("User already not assigned to role");
            return;
        }
        Response response = null;
        System.out.println("Removing application role from user...");
        try {
            response = configSpec()
                    .pathParam("roleId", roleId)
                    .pathParam("userId", email)
                    .when().delete(EMCEndpoints.EMC_ROLE_USER_CRUD);
        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
    }

    public boolean verifyUserForRole(String email, String roleId) {
        return getUsersForRole(roleId).jsonPath().getList("userName").contains(email);
    }

    /**
     * METHODS BELOW ARE RELATED TO APPLICATIONS PAGE - APPLICATIONS OPERATIONS
     */

    public void createApplicationAndVerify(String key, String name, String url, String provider, String type) {
        Response response = postEMCNewApplicationResponse(key, name, url, provider, type);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 201, "Status code 201 Created is verified");
        assertTestCase.assertEquals(response.path("name"), "CreatedResponse", "Application creation response name is verified");
        assertTestCase.assertEquals(response.path("message"), "Application " + key + " created", "Application creation response message with key is verified");
        assertTestCase.assertTrue(verifyApplication(key), "Application creation response type is verified");
    }

    public String getApplicationKey(String applicationName) {
        String applicationId = getApplicationId(applicationName);
        if (applicationId == null)
            return null;
        return getEMCApplicationResponse(applicationId).jsonPath().getString("key");
    }

    public Response getEMCApplicationResponse(String applicationId) {
        Response response = null;
        try {
            response = configSpec()
                    .when()
//                    .pathParam("applicationId", applicationId)
                    .get(EMCEndpoints.GET_APPLICATIONS + "/" + applicationId);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public String getApplicationId(String applicationName) {
        List<Application> applications = getEMCAllApplicationsResponse().jsonPath().getList("", Application.class);
        return applications.stream().anyMatch(app -> app.getName().equals(applicationName)) ?
                applications.stream().filter(app -> app.getName().equals(applicationName)).findFirst().get().getId() : null;
    }

    public String getApplicationUrl(String applicationName) {
        List<Application> applications = getEMCAllApplicationsResponse().jsonPath().getList("", Application.class);
        return applications.stream().anyMatch(app -> app.getName().equals(applicationName)) ?
                applications.stream().filter(app -> app.getName().equals(applicationName)).findFirst().get().getUrl() : null;
    }

    public Response getAllRolesForApplicationResponse(String applicationId) {
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .pathParam("application-id", applicationId)
                    .get(EMCEndpoints.GET_APPLICATION_ROLES);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public void assignUserToRoleAndVerify(String email, String roleId) {
        List<String> roles = Arrays.asList(Environment.ADMIN_ROLE_KEY, Environment.ADMIN_ROLE_KEY, Environment.FULFILLMENT_ROLE_KEY);
        for (String role : roles) {
            if (role.equals(roleId)) continue;
            deleteUserFromRole(email, role);
        }
        assignRoleToUser(email, roleId);
        assertTestCase.assertTrue(verifyUserForRole(email, roleId), "User is assigned to role successfully");
    }

    public Response createRoleForApplicationResponse(String applicationId, String roleName, String roleKey) {
        Response response = null;
        String payload = "{\n" +
                "  \"name\": \"" + roleName + "\",\n" +
                "  \"key\": \"" + roleKey + "\",\n" +
                "  \"applicationId\": \"" + applicationId + "\"\n" +
                "}";
        try {
            response = configSpec()
                    .when()
                    .body(payload)
                    .post(EMCEndpoints.APPLICATION_ROLE);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public boolean verifyRoleForApplication(String applicationId, String roleName) {
        return getAllRolesForApplicationResponse(applicationId).jsonPath().getList("name").contains(roleName);
    }

    /**
     * METHODS BELOW ARE FOR APPLICATION ROLE MANAGEMENT FOR USERS UNDER AN ACCOUNT
     */

    public Response getApplicationRolesForUser(String email) {
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .pathParam("user-id", email)
                    .get(EMCEndpoints.GET_APPLICATION_ROLE_FOR_USER);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public String getRoleId(String applicationId, String roleName) {
        List<Role> roles = getAllRolesForApplicationResponse(applicationId).jsonPath().getList("", Role.class);
        for (Role role : roles) {
            if (role.getName().equals(roleName))
                return role.getId();
        }
        System.out.println("Role with name " + roleName + " not found");
        System.out.println("roles = " + roles);
        return null;
    }

    public void deleteApplicationRoleFromUser(String roleId, String email) {
        System.out.println("Deleting role " + roleId + " from user " + email);
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .pathParam("application-role-id", roleId)
                    .pathParam("email", email)
                    .delete(EMCEndpoints.PUT_APPLICATION_ROLE);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        response.prettyPrint();
    }

    public boolean verifyApplicationRoleForUser(String email, String roleId) {
        System.out.println("Verifying role " + roleId + " for user " + email);
        List<UserApplicationRole> roles = getApplicationRolesForUser(email).jsonPath().getList("", UserApplicationRole.class);
        for (UserApplicationRole role : roles) {
            if (role.getRole().getId().equals(roleId))
                return true;
        }
        return false;
    }

    public Response assignApplicationRoleToUser(String applicationRoleId, String email) {
        System.out.println("Assigning application role to user");
        Response response = null;
        System.out.println("ASSIGNING APPLICATION ROLE TO USER");
        try {
            response = configSpec()
                    .when()
                    .pathParam("application-role-id", applicationRoleId)
                    .pathParam("email", email)
                    .put(EMCEndpoints.PUT_APPLICATION_ROLE);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        response.prettyPrint();
        return response;
    }

    public Response getAllProductsForApplicationResponse(String applicationId) {
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .pathParam("application-role-id", applicationId)
                    .get(EMCEndpoints.GET_APPLICATION_PRODUCTS);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response createProductForApplicationResponse(String applicationId, String productName, String productKey) {
        Response response = null;
        String payload = "{\n" +
                "  \"name\": \"" + productName + "\",\n" +
                "  \"key\": \"" + productKey + "\",\n" +
                "  \"code\": \"123\",\n" +
                "  \"sfdcId\": \"123\",\n" +
                "  \"type\": \"Bundle\",\n" +
                "  \"deliveryChannel\": \"API\",\n" +
                "  \"pricingModel\": \"Subscription\",\n" +
                "  \"application\": \"" + applicationId + "\"\n" +
                "}";
        try {
            response = configSpec()
                    .when()
                    .body(payload)
                    .post(EMCEndpoints.GET_PRODUCTS);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public boolean verifyProductForApplication(String applicationId, String productName) {
        return getAllProductsForApplicationResponse(applicationId).jsonPath().getList("name").contains(productName);
    }

    public String getProductId(String applicationId, String productName) {
        List<Product> products = getAllProductsForApplicationResponse(applicationId).jsonPath().getList("", Product.class);
        for (Product product : products) {
            if (product.getName().equals(productName))
                return product.getId();
        }
        System.out.println("Product with name " + productName + " not found");
        return null;
    }

    public Response deleteProductForApplicationResponse(String applicationId, String productId) {
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .delete(EMCEndpoints.GET_PRODUCTS + "/" + productId);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public void deleteProductFromAccount(String accountId, String productId) {
        System.out.println("Deleting product " + productId + " from account " + accountId);
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .pathParam("account-id", accountId)
                    .pathParam("product-id", productId)
                    .delete(EMCEndpoints.DELETE_PRODUCTS_FROM_ACCOUNT);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Delete Product From Account Status Code = " + response.statusCode());
        response.prettyPrint();
    }

    public boolean verifyProductForAccount(String accountId, String productId) {
        System.out.println("Verifying product " + productId + " for account " + accountId);
        List<AssignedProduct> products = getProductsForAccount(accountId).jsonPath().getList("", AssignedProduct.class);
        for (AssignedProduct product : products) {
            if (product.getProduct().getId().equals(productId))
                return true;
        }
        return false;
    }

    public Response getProductsForAccount(String accountId) {
        Response response = null;
        try {
            response = configSpec()
                    .when()
                    .pathParam("account-id", accountId)
                    .get(EMCEndpoints.GET_PRODUCTS_FOR_ACCOUNT);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
//        response.prettyPrint();
        return response;
    }

    public Response getAppIntegrationResponse(String application_key) {
        Response response = null;
        try {
            response = configSpec("Authorization")
                    .when()
                    .pathParam("appkey", application_key)
                    .get(EMCEndpoints.GET_APP_INTEGRATION);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response getAccountInfo(String accountId) {
        Response response = null;
        try {
            response = configSpec()
                    .when()
//                    .pathParam("account-id", accountId)
                    .get(EMCEndpoints.GET_EMC_ALL_ACCOUNTS + "/" + accountId);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response putAssessmentUsageInfo(String accountKey, int value) {
        Response response = null;
        String payload = "{\n" +
                "\"usedAssessmentsDelta\": "+value+"\n" +
                "}";
        try {
            response = configSpec("x-api-key", "455TEbpBTO4vMaW0NVGcF3EqhHbLhoCu4PMPAFqX")
                    .when()
                    .body(payload)
                    .pathParam("accountKey", accountKey)
                    .put(EMCEndpoints.ASSESSMENT_USAGE);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response putAssessmentUsageInfo(String accountKey, String value) {
        Response response = null;
        String payload = "{\n" +
                "\"usedAssessmentsDelta\": 0\n" +
                "}";
        try {
            response = configSpec("x-api-key", value)
                    .when()
                    .body(payload)
                    .pathParam("accountKey", accountKey)
                    .put(EMCEndpoints.ASSESSMENT_USAGE);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }
}
