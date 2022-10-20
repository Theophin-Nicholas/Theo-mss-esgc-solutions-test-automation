package com.esgc.APIModels;

import com.esgc.APIModels.EMC.Application;
import com.esgc.APIModels.EMC.AssignedApplication;
import com.esgc.APIModels.EMC.AssignedUser;
import com.esgc.APIModels.EMC.User;
import com.esgc.EMCEndpoints;
import com.esgc.TestBase.TestBase;
import com.esgc.Utilities.Environment;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static io.restassured.RestAssured.given;

public class EMCAPIController extends TestBase {
    boolean isInvalidTest = false;
    RequestSpecification configSpec() {
        //getAccessToken();
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
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.GET_EMC_ALL_ADMIN_USERS);
        try {
            response = configSpec()
                    .when()
                    .get(EMCEndpoints.GET_EMC_ALL_ADMIN_USERS);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response getEMCAllRolesResponse() {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.GET_EMC_ALL_ROLES);
        try {
            response = configSpec()
                    .when()
                    .get(EMCEndpoints.GET_EMC_ALL_ROLES);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response getEMCUserRolesResponse(String userID) {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.GET_EMC_ALL_ADMIN_USERS+"/"+userID+"/roles");
        try {
            response = configSpec()
                    .when()
                    .get(EMCEndpoints.GET_EMC_ALL_ADMIN_USERS+"/"+userID+"/roles");

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response getEMCRoleUsersResponse(String roleID) {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.GET_EMC_ALL_ROLES+"/"+roleID+"/users");
        try {
            response = configSpec()
                    .when()
                    .get(EMCEndpoints.GET_EMC_ALL_ROLES+"/"+roleID+"/users");

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response postEMCNewUserResponse(String provider, String firstName, String lastName, String userName, String email, boolean isActive, String accountId) {
        System.out.println("Creating new user");
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.POST_EMC_NEW_USER);
        String payload = "{\"provider\":\""+provider+"\",\"firstName\":\""+firstName+"\",\"lastName\":\""+lastName+"\",\"userName\":\""+userName+"\",\"email\":\""+email+"\",\"activate\":\""+isActive+"\",\"accountId\":\""+accountId+"\"}";
        System.out.println("payload = " + payload);
        try {
            response = configSpec()
                    .and().body(payload)
                    .when()
                    .post(EMCEndpoints.POST_EMC_NEW_USER);

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
                    .when().put(EMCEndpoints.GET_EMC_USER+"/"+email);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response deleteEMCUserResponse(String email) {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.GET_EMC_USER+"/"+email);
        try {
            response = configSpec()
                    .when()
                    .delete(EMCEndpoints.GET_EMC_USER+"/"+email);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response getEMCAllAccountsResponse() {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.GET_EMC_ALL_ACCOUNTS);
        try {
            response = configSpec()
                    .when()
                    .get(EMCEndpoints.GET_EMC_ALL_ACCOUNTS);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response getEMCUserDetailsResponse(String email) {
        System.out.println("Getting user details for : " + email);
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.GET_EMC_USER);
        try {
            response = configSpec()
                    .when()
                    .get(EMCEndpoints.GET_EMC_USER+"/"+email);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        return response;
    }

    public Response getEMCAllUsersResponse() {
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.GET_EMC_USER);
        try {
            response = configSpec()
                    .when()
                    .get(EMCEndpoints.GET_EMC_USER);

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

    public Response postEMCNewApplicationResponse(String key, String name, String url, String provider) {
        System.out.println("Creating new application with key = " + key);
        Response response = null;
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.EMC_APPS);
        String payload = "{\"key\":\""+key+"\",\"name\":\""+name+"\",\"url\":\""+url+"\",\"provider\":\""+provider+"\"}";
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
                    .get(EMCEndpoints.EMC_APPS+"/"+applicationId);

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
                    .put(EMCEndpoints.EMC_APPS+"/"+applicationId);

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
                    .delete(EMCEndpoints.EMC_APPS+"/"+applicationId);

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
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.GET_EMC_ALL_ACCOUNTS+"/"+accountId+"/applications");
        try {
            response = configSpec()
                    .when()
                    .get(EMCEndpoints.GET_EMC_ALL_ACCOUNTS+"/"+accountId+"/applications");

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
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.GET_EMC_ALL_ACCOUNTS+"/"+accountId+"/applications/"+applicationId);
        try {
            response = configSpec()
                    .when().put(EMCEndpoints.GET_EMC_ALL_ACCOUNTS+"/"+accountId+"/applications/"+applicationId);

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
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.GET_EMC_ALL_ACCOUNTS+"/"+accountId+"/applications");
        try {
            response = configSpec()
                    .when()
                    .delete(EMCEndpoints.GET_EMC_ALL_ACCOUNTS+"/"+accountId+"/applications/"+qaTestApplicationId);

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
        System.out.println("EMC API URL: " + Environment.EMC_URL + EMCEndpoints.GET_EMC_ALL_ACCOUNTS+"/"+accountId+"/users");
        try {
            response = configSpec()
                    .when()
                    .get(EMCEndpoints.GET_EMC_ALL_ACCOUNTS+"/"+accountId+"/users");

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Status Code = " + response.statusCode());
        System.out.println();
        return response;
    }
}
