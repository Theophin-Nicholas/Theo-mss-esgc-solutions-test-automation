package com.esgc.Tests;

import com.esgc.APIModels.EMC.*;
import com.esgc.APIModels.EMCAPIController;
import com.esgc.TestBases.APITestBase;
import com.esgc.Utilities.Environment;
import com.esgc.Utilities.Xray;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

public class EMCAPITests extends APITestBase {

    protected EMCAPIController apiController = new EMCAPIController();
    Response response;
    Faker faker = new Faker();
    String accountId = Environment.QA_TEST_ACCOUNT_ID;
    String applicationId = Environment.QA_TEST_APPLICATION_ID;

    @Test(groups = {"EMC", "api", "regression"})
    @Xray(test = {6870})
    public void verifyAdminUserCanGETListOfAllUsersWithAdminRoleTest() {
        response = apiController.getEMCAllAdminUsersResponse();
        response.prettyPrint();
        System.out.println("response = " + response.statusCode());
        assertTestCase.assertEquals(response.statusCode(),200, "Status code 200 OK is verified");
        List<String> roleNames = response.jsonPath().getList("firstName");
        System.out.println("roleNames = " + roleNames);
        System.out.println("roleNames.contains(Environment.INTERNAL_USER_USERNAME) = " + roleNames.contains(Environment.INTERNAL_USER_USERNAME));
        assertTestCase.assertTrue(roleNames.contains(Environment.INTERNAL_USER_USERNAME), "Admin role is verified");
        assertTestCase.assertTrue(response.as(AdminUser[].class).length>0, "User details are verified");
    }

    @Test(groups = {"EMC", "api", "regression"})
    //@Xray(test = {6870})
    public void verifyAdminUserCanGETListOfAllRolesTest() {
        response = apiController.getEMCAllRolesResponse();
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(),200, "Status code 200 OK is verified");
        assertTestCase.assertTrue(response.as(Role[].class).length>0, "Role details are verified");
    }

    @Test(groups = {"EMC", "api", "regression"})
    @Xray(test = {6871,6872, 4979})
    public void verifyAdminUserCRUDOperationsOnUserTest() {

        //Create User
        String email = faker.internet().emailAddress();
        System.out.println("email = " + email);
        System.out.println("accountId = " + accountId);

        response = apiController.postEMCNewUserResponse("mss","QATest","User",email,email,false,accountId);
        assertTestCase.assertEquals(response.statusCode(),201, "Status code 200 OK is verified");
        assertTestCase.assertEquals(response.path("message"), "Resource created: "+email, "User created successfully");

        //Update User
        response = apiController.getEMCUserDetailsResponse(email);
        response.prettyPrint();
        User user = response.as(User.class);
        System.out.println("user = " + user);
        user.setLastName("NewUser");
        System.out.println("\nuser = " + user);

        response = apiController.putEMCUserResponse(email,user);
        assertTestCase.assertEquals(response.statusCode(),204, "Status code 204 OK is verified");
        System.out.println("User updated!");

        User updatedUser = apiController.getEMCUserDetailsResponse(email).as(User.class);
        System.out.println("updatedUser = " + updatedUser);
        assertTestCase.assertEquals(updatedUser.getLastName(),"NewUser", "User updated successfully");

        //Delete User
        response = apiController.deleteEMCUserResponse(email);
        assertTestCase.assertEquals(response.statusCode(),204, "Status code 204 OK is verified");
        response.prettyPrint();
        List<String> users = apiController.getEMCAllUsersResponse().jsonPath().getList("email");
        assertTestCase.assertFalse(users.contains(email), "User deleted successfully");
    }

    @Test(groups = {"EMC", "api", "regression"}, description = "API | EMC | Users | Verify Error Handling for Required fields when Create a User")
    @Xray(test = {5001})
    public void verifyErrorHandlingForRequiredFieldsWhenCreateUserTest() {
        String email = "erolvera.mx+006@gmail.com";

        //verify error handling for null firstName
        response = apiController.postEMCNewUserResponse("mss","","User",email,email,false,accountId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(),406, "Status code 406 Not Acceptable is verified");
        assertTestCase.assertEquals(response.path("name"), "InvalidDataException", "Error message name is verified");
        assertTestCase.assertEquals(response.path("message"), "User firstName cannot be empty or null", "Error message is verified");

        //verify error handling for null lastName
        response = apiController.postEMCNewUserResponse("mss","QA Test","",email,email,false,accountId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(),406, "Status code 406 Not Acceptable is verified");
        assertTestCase.assertEquals(response.path("name"), "InvalidDataException", "Error message name is verified");
        assertTestCase.assertEquals(response.path("message"), "User lastName cannot be empty or null", "Error message is verified");

        //verify error handling for null username
        response = apiController.postEMCNewUserResponse("mss","QA Test","User","",email,false,accountId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(),406, "Status code 406 Not Acceptable is verified");
        assertTestCase.assertEquals(response.path("name"), "InvalidDataException", "Error message name is verified");
        assertTestCase.assertEquals(response.path("message"), "User userName cannot be empty or null", "Error message is verified");

        //verify error handling for null email
        response = apiController.postEMCNewUserResponse("mss","QA Test","User",email,"",false,accountId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(),406, "Status code 406 Not Acceptable is verified");
        assertTestCase.assertEquals(response.path("name"), "InvalidDataException", "Error message name is verified");
        assertTestCase.assertEquals(response.path("message"), "User email cannot be empty or null", "Error message is verified");

        //verify error handling for null accountId
        response = apiController.postEMCNewUserResponse("mss","QA Test","User",email,email,false,"");
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(),406, "Status code 406 Not Acceptable is verified");
        assertTestCase.assertEquals(response.path("name"), "InvalidDataException", "Error message name is verified");
        assertTestCase.assertEquals(response.path("message"), "User accountId cannot be empty or null", "Error message is verified");

        //verify error handling for wrong accountId
        response = apiController.postEMCNewUserResponse("mss","QA Test","User",email,email,false,"123456789");
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(),406, "Status code 406 Not Acceptable is verified");
        assertTestCase.assertEquals(response.path("name"), "InvalidDataException", "Error message name is verified");
        assertTestCase.assertEquals(response.path("message"), "User accountId is not a valid uuid v4", "Error message is verified");

        //verify error handling for null provider
        response = apiController.postEMCNewUserResponse("","QA Test","User",email,email,false,accountId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(),400, "Status code 400 Not Acceptable is verified");
        assertTestCase.assertEquals(response.path("name"), "BadRequestException", "Error message name is verified");
        assertTestCase.assertEquals(response.path("message"), "Invalid or unspecified user provider.", "Error message is verified");

        //verify error handling for wrong provider
        response = apiController.postEMCNewUserResponse("mssss","QA Test","User",email,email,false,accountId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(),400, "Status code 400 Not Acceptable is verified");
        assertTestCase.assertEquals(response.path("name"), "BadRequestException", "Error message name is verified");
        assertTestCase.assertEquals(response.path("message"), "Invalid or unspecified user provider.", "Error message is verified");

        //verify error handling for correct payload
        response = apiController.postEMCNewUserResponse("mss","QA Test","User",email,email,false,accountId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(),201, "Status code 201 Created is verified");
        assertTestCase.assertEquals(response.path("name"), "CreatedResponse", "User creation response name is verified");
        assertTestCase.assertEquals(response.path("message"), "Resource created: "+email, "User creation response message with email is verified");

        response = apiController.deleteEMCUserResponse(email);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(),204, "Status code 204 OK is verified");
    }

    @Test(groups = {"EMC", "api", "regression"}, description = "API | EMC | Users | Verify User does not have Access to PUT Users with Wrong Access Token")
    @Xray(test = {5034})
    public void verifyUserCantAccessPUTUsersWithWrongAccessTokenTest() {
        String email = "erolvera.mx+006@gmail.com";
        String accessToken = System.getProperty("token");
        System.out.println("accessToken = " + accessToken);
        System.setProperty("token", "accessToken");
        try{
            //verify error handling for wrong token
            response = apiController.postEMCNewUserResponse("mss","QA Test","User",email,email,false,accountId);
            response.prettyPrint();
            assertTestCase.assertEquals(response.statusCode(),401, "Status code 401 Unauthorized is verified");
            assertTestCase.assertEquals(response.path("message"), "Unauthorized", "User creation without access token response message is verified");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.setProperty("token", accessToken);
        }
    }

    @Test(groups = {"EMC", "api", "regression"}, description = "API | EMC | Users | Verify User does not have Access to PUT Users without Access Token")
    @Xray(test = {5033})
    public void verifyUserCantAccessPUTUsersWithoutAccessTokenTest() {
        String email = "erolvera.mx+006@gmail.com";

        response = apiController.postEMCNewUserResponse("mss","QA Test","User",email,email,false,accountId, true);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(),401, "Status code 401 Unauthorized is verified");
        assertTestCase.assertEquals(response.path("message"), "Unauthorized", "User creation without access token response message is verified");

    }

    @Test(groups = {"EMC", "api", "regression"}, description = "API | EMC | Users | Update Single OKTA Users")
    @Xray(test = {4980})
    public void verifyUpdateSingleOKTAUsersTest() {
        String email = "esgc-platform-test-user-1@outlook.com";

        //Update User
        User user = apiController.getEMCUserDetailsResponse(email).as(User.class);
        System.out.println("user = " + user);
        user.setLastName("NewUser");
        System.out.println("\nNew user = " + user);

        response = apiController.putEMCUserResponse(email,user);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(),405, "Status code 405 Method Not Allowed is verified");
        assertTestCase.assertEquals(response.path("name"), "CannotUpdateException", "Error message name is verified");
        assertTestCase.assertEquals(response.path("message"), "Cannot update user: "+email, "Error message with email is verified");

        response = apiController.getEMCUserDetailsResponse(email);
        response.prettyPrint();

        User updatedUser = apiController.getEMCUserDetailsResponse(email).as(User.class);
        System.out.println("updatedUser = " + updatedUser);
        assertTestCase.assertNotEquals(updatedUser.getLastName(),"NewUser", "User updated successfully");

    }

    @Test(groups = {"EMC", "api", "regression"}, description = "API | EMC | Apps | CRUD Operations for Applications")
    @Xray(test = {})
    public void verifyCRUDOperationsForApplicationsTest() {
        //Get All Applications
        response = apiController.getEMCAllApplicationsResponse();
        //response.prettyPrint();

        //Create Application
        String key = "qatestapp"+System.currentTimeMillis();
        String name = "API Test App";
        String url = "https://www.google.com";
        String provider = "mss";
        response = apiController.postEMCNewApplicationResponse(key,name,url, provider);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(),201, "Status code 201 Created is verified");
        assertTestCase.assertEquals(response.path("name"), "CreatedResponse", "Application creation response name is verified");
        assertTestCase.assertEquals(response.path("message"), "Application "+key+" created", "Application creation response message with key is verified");
        List<Application> applications = apiController.getEMCAllApplicationsResponse().jsonPath().getList("", Application.class);
        System.out.println("applications.size() = " + applications.size());

        //Verify Application details
        Application application = new Application();
        for(Application app : applications){
            //System.out.println("app = " + app);
            if(app.getId().equals(key)){
                application = app;
                break;
            }
        }
        System.out.println("application = " + application);
        assertTestCase.assertEquals(application.getName(), name, "Application name is verified");
        assertTestCase.assertEquals(application.getUrl(), url, "Application url is verified");
        //assertTestCase.assertEquals(application.getProvider(), provider, "Application provider is verified");

        //Get Details of Single Application
        String applicationId = application.getId();
        response = apiController.getEMCApplicationDetailsResponse(applicationId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(),200, "Status code 200 OK is verified");
        response.as(Application.class);

        //Update Application
        System.out.println("===================================");
        System.out.println("Updating Application with id: "+applicationId);
        application.setName("API Test App Updated");
        application.setUrl("https://www.google.com.mx");
        application.setProvider("mss");
        response = apiController.putEMCApplicationResponse(applicationId,application);

        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(),204, "Status code 204 Updated is verified");
        //assertTestCase.assertEquals(response.path("name"), "UpdatedResponse", "Application update response name is verified");
        //assertTestCase.assertEquals(response.path("message"), "Application "+key+" updated", "Application update response message with key is verified");

        //Verify Application details
        Application updatedApplication = apiController.getEMCApplicationDetailsResponse(applicationId).as(Application.class);
        assertTestCase.assertEquals(updatedApplication.getName(), "API Test App Updated", "Application name is verified");
        assertTestCase.assertEquals(updatedApplication.getUrl(), "https://www.google.com.mx", "Application url is verified");
        //assertTestCase.assertEquals(updatedApplication.getProvider(), "mss", "Application provider is verified");

        //Delete Application
        response = apiController.deleteEMCApplicationResponse(applicationId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(),403, "Status code 204 OK is verified");
        //assertTestCase.assertEquals(response.path("name"), "DeletedResponse", "Application delete response name is verified");
        assertTestCase.assertEquals(response.path("message"), "Missing Authentication Token", "Application delete response message is verified");
        List<Application> updatedApplications = apiController.getEMCAllApplicationsResponse().jsonPath().getList("", Application.class);
        assertTestCase.assertFalse(updatedApplications.contains(application), "Application is not deleted successfully");
    }

    @Test(groups = {"EMC", "api", "regression"}, description = "API | EMC | Accounts Applications | Validate User is Able to Delete the Relation between Account and Application")
    @Xray(test = {4042})
    public void verifyRemoveApplicationFromAccountTest() {
        //verify if application assigned to account
        if(!apiController.verifyApplication(accountId, applicationId)){
            response = apiController.assignApplicationToAccountResponse(accountId, applicationId);
            response.prettyPrint();
            assertTestCase.assertEquals(response.statusCode(),200, "Status code 200 OK is verified");
            assertTestCase.assertEquals(response.path("statusCode")+"", "202", "Response status code is verified");
        }
        //delete application from account
        response = apiController.deleteEMCRemoveApplicationFromAccountResponse(accountId, applicationId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(),204, "Status code 204 OK is verified");
        //verify if application is deleted from account
        assertTestCase.assertFalse(apiController.verifyApplication(accountId, applicationId), "Application is deleted from account successfully");
    }

    @Test(groups = {"EMC", "api", "regression"},
            description = "API | EMC | Accounts Applications | Verify User does not have Access to DELETE Applications with Invalid Access Token")
    @Xray(test = {4043})
    public void verifyUserCantDeleteApplicationWithWrongAccessTokenTest() {

        //verify account has application assigned
        if(!apiController.verifyApplication(accountId, applicationId)){
            response = apiController.assignApplicationToAccountResponse(accountId, applicationId);
            response.prettyPrint();
            assertTestCase.assertEquals(response.statusCode(),200, "Status code 200 OK is verified");
            assertTestCase.assertEquals(response.path("statusCode")+"", "202", "Response status code is verified");
        }
        String accessToken = System.getProperty("token");
        System.out.println("accessToken = " + accessToken);
        System.setProperty("token", "accessToken");
        try{
            //verify error handling for wrong token
            response = apiController.deleteEMCRemoveApplicationFromAccountResponse(accountId, applicationId);
            response.prettyPrint();
            assertTestCase.assertEquals(response.statusCode(),401, "Status code 401 Unauthorized is verified");
            assertTestCase.assertEquals(response.path("message"), "Unauthorized", "User deletion without access token response message is verified");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.setProperty("token", accessToken);
        }
    }

    @Test(groups = {"EMC", "api", "regression"},
            description = "API | EMC | Accounts Applications | Verify User does not have Access to PUT Applications with Invalid Access Token")
    @Xray(test = {4050})
    public void verifyUserCantAssignApplicationWithWrongAccessTokenTest() {

        //verify account has application assigned
        if(apiController.verifyApplication(accountId, applicationId)){
            System.out.println("Application is already assigned to account");
            response = apiController.deleteEMCRemoveApplicationFromAccountResponse(accountId, applicationId);
            assertTestCase.assertEquals(response.statusCode(),204, "Status code 204 Deleted is verified");
            assertTestCase.assertFalse(apiController.verifyApplication(accountId, applicationId), "Application is deleted from account successfully");
        }
        String accessToken = System.getProperty("token");
        System.out.println("accessToken = " + accessToken);
        System.setProperty("token", "accessToken");
        try{
            //verify error handling for wrong token
            response = apiController.assignApplicationToAccountResponse(accountId, applicationId);
            response.prettyPrint();
            assertTestCase.assertEquals(response.statusCode(),401, "Status code 401 Unauthorized is verified");
            assertTestCase.assertEquals(response.path("message"), "Unauthorized", "User deletion without access token response message is verified");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.setProperty("token", accessToken);
        }
    }

    @Test(groups = {"EMC", "api", "regression"},
            description = "API | EMC | Accounts Applications | Verify User does not have Access to Assign/DELETE Applications without Access Token")
    @Xray(test = {4044, 4047})
    public void verifyUserCantDeleteApplicationWithoutAccessTokenTest() {

        //verify account has application assigned
        if(!apiController.verifyApplication(accountId, applicationId)){
            response = apiController.assignApplicationToAccountResponse(accountId, applicationId);
            response.prettyPrint();
            assertTestCase.assertEquals(response.statusCode(),200, "Status code 200 OK is verified");
            assertTestCase.assertEquals(response.path("statusCode")+"", "202", "Response status code is verified");
        }
        response = apiController.deleteEMCRemoveApplicationFromAccountResponse(accountId, applicationId, true);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(),401, "Status code 401 Unauthorized is verified");
        assertTestCase.assertEquals(response.path("message"), "Unauthorized", "User creation without access token response message is verified");

        //delete application from account and try to assign without access token
        response = apiController.deleteEMCRemoveApplicationFromAccountResponse(accountId, applicationId);
        response = apiController.assignApplicationToAccountResponse(accountId, applicationId, true);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(),401, "Status code 401 Unauthorized is verified");
    }

    @Test(groups = {"EMC", "api", "regression"},
            description = "API | EMC | Accounts Applications | Verify that User is Able to Add a New Relation between Account and Applications")
    @Xray(test = {4046})
    public void verifyAssignApplicationToAccountTest() {
        if(apiController.verifyApplication(accountId, applicationId)){
            System.out.println("Application is already assigned to account");
            response = apiController.deleteEMCRemoveApplicationFromAccountResponse(accountId, applicationId);
            assertTestCase.assertEquals(response.statusCode(),204, "Status code 204 Deleted is verified");
            assertTestCase.assertFalse(apiController.verifyApplication(accountId, applicationId), "Application is deleted from account successfully");
        }
        response = apiController.assignApplicationToAccountResponse(accountId, applicationId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(),200, "Status code 200 OK is verified");
        assertTestCase.assertEquals(response.path("statusCode")+"", "202", "Response status code is verified");
        assertTestCase.assertTrue(apiController.verifyApplication(accountId, applicationId), "Application is deleted from account successfully");
    }

    @Test(groups = {"EMC", "api", "regression"},
            description = "API | EMC | Accounts | Verify a user's with proper permission role (view users) API response to create, update, retrieve and dlete the user list from an account")
    @Xray(test = {7309})
    public void verifyUserCRUDOperationsUnderAccountTest() {
        response = apiController.getListOfUsersResponse(accountId);
        //response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(),200, "Status code 200 OK is verified");
        List<AssignedUser> users = response.jsonPath().getList("users", AssignedUser.class);
        System.out.println("users.size() = " + users.size());

        //create user
        String email = "test" + System.currentTimeMillis() + "@test.com";
        String firstName = "QAtest";
        String lastName = "QAtest";
        String provider = "mss";
        response = apiController.postEMCNewUserResponse("mss", firstName, lastName, email, email, false, accountId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(),201, "Status code 201 create is verified");
        assertTestCase.assertEquals(response.path("name"), "CreatedResponse", "Response name is verified");
        assertTestCase.assertEquals(response.path("message"), "Resource created: "+email, "Response message is verified");
        assertTestCase.assertTrue(apiController.verifyUser(accountId, email), "User is verified in account");

        //Delete user and verify
        response = apiController.deleteEMCUserResponse(email);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(),204, "Status code 204 Deleted is verified");
        assertTestCase.assertFalse(apiController.verifyUser(accountId, email), "User is deleted from account successfully");

    }
}
