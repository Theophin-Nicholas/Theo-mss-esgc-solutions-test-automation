package com.esgc.Tests;

import com.esgc.APIModels.EMC.*;
import com.esgc.APIModels.EMCAPIController;
import com.esgc.TestBases.APITestBase;
import com.esgc.Utilities.Environment;
import com.esgc.Utilities.Xray;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.esgc.Utilities.Groups.*;

public class EMCAPITests extends APITestBase {

    protected EMCAPIController apiController = new EMCAPIController();
    Response response;
    Faker faker = new Faker();
    String  accountId = Environment.QA_TEST_ACCOUNT_ID;
    String applicationId = Environment.QA_TEST_APPLICATION_ID;

    @Test(groups = {EMC, API, REGRESSION, SMOKE})
    @Xray(test = {4172})
    public void verifyAdminUserCanGETListOfAllUsersWithAdminRoleTest() {
        response = apiController.getEMCAllAdminUsersResponse();
        response.prettyPrint();
        System.out.println("response = " + response.statusCode());
        assertTestCase.assertEquals(response.statusCode(), 200, "Status code 200 OK is verified");
        List<String> roleNames = response.jsonPath().getList("firstName");
        System.out.println("roleNames = " + roleNames);
        String name = Environment.INTERNAL_USER_USERNAME;
        System.out.println("name = " + name);
        if(name.contains("@")) name = name.split("@")[0];
        System.out.println("roleNames.contains(name) = " + roleNames.contains(name));
        assertTestCase.assertTrue(roleNames.contains(name), "Admin role is verified");
        assertTestCase.assertTrue(response.as(AdminUser[].class).length > 0, "User details are verified");
    }

    @Test(groups = {EMC, API, REGRESSION, SMOKE})
    @Xray(test = {4108, 4111, 2173})
    public void verifyAdminUserCRUDOperationsOnUserTest() {

        //Create User
        String email = faker.internet().emailAddress();
        System.out.println("email = " + email);
        System.out.println("accountId = " + accountId);

        response = apiController.postEMCNewUserResponse("mss", "QATest", "User", email, email, false, accountId);
        assertTestCase.assertEquals(response.statusCode(), 201, "Status code 200 OK is verified");
        assertTestCase.assertEquals(response.path("message"), "Resource created: " + email, "User created successfully");

        //Update User
        response = apiController.getEMCUserDetailsResponse(email);
        response.prettyPrint();
        User user = response.as(User.class);
        System.out.println("user = " + user);
        user.setLastName("NewUser");
        System.out.println("\nuser = " + user);

        response = apiController.putEMCUserResponse(email, user);
        assertTestCase.assertEquals(response.statusCode(), 204, "Status code 204 OK is verified");
        System.out.println("User updated!");

        User updatedUser = apiController.getEMCUserDetailsResponse(email).as(User.class);
        System.out.println("updatedUser = " + updatedUser);
        assertTestCase.assertEquals(updatedUser.getLastName(), "NewUser", "User updated successfully");

        //Delete User
        response = apiController.deleteEMCUserResponse(email);
        assertTestCase.assertEquals(response.statusCode(), 204, "Status code 204 OK is verified");
        response.prettyPrint();
        List<String> users = apiController.getEMCAllUsersResponse().jsonPath().getList("email");
        assertTestCase.assertFalse(users.contains(email), "User deleted successfully");
    }

    @Test(groups = {EMC, API, REGRESSION}, description = "API | EMC | Users | Verify Error Handling for Required fields when Create a User")
    @Xray(test = {2078, 2200})
    public void verifyErrorHandlingForRequiredFieldsWhenCreateUserTest() {
        String email = "erolvera.mx+006@gmail.com";

        //verify error handling for null firstName
        response = apiController.postEMCNewUserResponse("mss", "", "User", email, email, false, accountId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 406, "Status code 406 Not Acceptable is verified");
        assertTestCase.assertEquals(response.path("name"), "InvalidDataException", "Error message name is verified");
        assertTestCase.assertEquals(response.path("message"), "User firstName cannot be empty or null", "Error message is verified");

        //verify error handling for null lastName
        response = apiController.postEMCNewUserResponse("mss", "QA Test", "", email, email, false, accountId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 406, "Status code 406 Not Acceptable is verified");
        assertTestCase.assertEquals(response.path("name"), "InvalidDataException", "Error message name is verified");
        assertTestCase.assertEquals(response.path("message"), "User lastName cannot be empty or null", "Error message is verified");

        //verify error handling for null username
        response = apiController.postEMCNewUserResponse("mss", "QA Test", "User", "", email, false, accountId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 406, "Status code 406 Not Acceptable is verified");
        assertTestCase.assertEquals(response.path("name"), "InvalidDataException", "Error message name is verified");
        assertTestCase.assertEquals(response.path("message"), "User userName cannot be empty or null", "Error message is verified");

        //verify error handling for null email
        response = apiController.postEMCNewUserResponse("mss", "QA Test", "User", email, "", false, accountId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 406, "Status code 406 Not Acceptable is verified");
        assertTestCase.assertEquals(response.path("name"), "InvalidDataException", "Error message name is verified");
        assertTestCase.assertEquals(response.path("message"), "User email cannot be empty or null", "Error message is verified");

        //verify error handling for null accountId
        response = apiController.postEMCNewUserResponse("mss", "QA Test", "User", email, email, false, "");
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 406, "Status code 406 Not Acceptable is verified");
        assertTestCase.assertEquals(response.path("name"), "InvalidDataException", "Error message name is verified");
        assertTestCase.assertEquals(response.path("message"), "User accountId cannot be empty or null", "Error message is verified");

        //verify error handling for wrong accountId
        response = apiController.postEMCNewUserResponse("mss", "QA Test", "User", email, email, false, "123456789");
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 406, "Status code 406 Not Acceptable is verified");
        assertTestCase.assertEquals(response.path("name"), "InvalidDataException", "Error message name is verified");
        assertTestCase.assertEquals(response.path("message"), "User accountId is not a valid uuid v4", "Error message is verified");

        //verify error handling for null provider
        response = apiController.postEMCNewUserResponse("", "QA Test", "User", email, email, false, accountId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 400, "Status code 400 Not Acceptable is verified");
        assertTestCase.assertEquals(response.path("name"), "BadRequestException", "Error message name is verified");
        assertTestCase.assertEquals(response.path("message"), "Invalid or unspecified user provider.", "Error message is verified");

        //verify error handling for wrong provider
        response = apiController.postEMCNewUserResponse("mssss", "QA Test", "User", email, email, false, accountId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 400, "Status code 400 Not Acceptable is verified");
        assertTestCase.assertEquals(response.path("name"), "BadRequestException", "Error message name is verified");
        assertTestCase.assertEquals(response.path("message"), "Invalid or unspecified user provider.", "Error message is verified");

        //verify error handling for correct payload
        response = apiController.postEMCNewUserResponse("mss", "QA Test", "User", email, email, false, accountId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 201, "Status code 201 Created is verified");
        assertTestCase.assertEquals(response.path("name"), "CreatedResponse", "User creation response name is verified");
        assertTestCase.assertEquals(response.path("message"), "Resource created: " + email, "User creation response message with email is verified");

        response = apiController.deleteEMCUserResponse(email);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 204, "Status code 204 OK is verified");
    }

    @Test(groups = {EMC, API, REGRESSION}, description = "API | EMC | Users | Verify User does not have Access to PUT Users with Wrong Access Token")
    @Xray(test = {1872})
    public void verifyUserCantAccessPUTUsersWithWrongAccessTokenTest() {
        String email = "erolvera.mx+006@gmail.com";
        String accessToken = System.getProperty("token");
        System.out.println("accessToken = " + accessToken);
        System.setProperty("token", "accessToken");
        try {
            //verify error handling for wrong token
            response = apiController.postEMCNewUserResponse("mss", "QA Test", "User", email, email, false, accountId);
            response.prettyPrint();
            assertTestCase.assertEquals(response.statusCode(), 401, "Status code 401 Unauthorized is verified");
            assertTestCase.assertEquals(response.path("message"), "Unauthorized", "User creation without access token response message is verified");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.setProperty("token", accessToken);
        }
    }

    @Test(groups = {EMC, API, REGRESSION}, description = "API | EMC | Users | Verify User does not have Access to PUT Users without Access Token")
    @Xray(test = {1724})
    public void verifyUserCantAccessPUTUsersWithoutAccessTokenTest() {
        String email = "erolvera.mx+006@gmail.com";

        response = apiController.postEMCNewUserResponse("mss", "QA Test", "User", email, email, false, accountId, true);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 401, "Status code 401 Unauthorized is verified");
        assertTestCase.assertEquals(response.path("message"), "Unauthorized", "User creation without access token response message is verified");

    }

    @Test(groups = {EMC, API, REGRESSION}, description = "API | EMC | Users | Update Single OKTA Users")
    @Xray(test = {1648})
    public void verifyUpdateSingleOKTAUsersTest() {
        String email = "esgc-platform-test-user-1@outlook.com";

        //Update User
        User user = apiController.getEMCUserDetailsResponse(email).as(User.class);
        System.out.println("user = " + user);
        user.setLastName("NewUser");
        System.out.println("\nNew user = " + user);

        response = apiController.putEMCUserResponse(email, user);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 405, "Status code 405 Method Not Allowed is verified");
        assertTestCase.assertEquals(response.path("name"), "CannotUpdateException", "Error message name is verified");
        assertTestCase.assertEquals(response.path("message"), "Cannot update user: " + email, "Error message with email is verified");

        response = apiController.getEMCUserDetailsResponse(email);
        response.prettyPrint();

        User updatedUser = apiController.getEMCUserDetailsResponse(email).as(User.class);
        System.out.println("updatedUser = " + updatedUser);
        assertTestCase.assertNotEquals(updatedUser.getLastName(), "NewUser", "User updated successfully");

    }

    @Test(groups = {EMC, API, REGRESSION}, description = "API | EMC | Apps | CRUD Operations for External Applications")
    @Xray(test = {3230})
    public void verifyCRUDOperationsForExternalApplicationsTest() {
        //Get All Applications
        response = apiController.getEMCAllApplicationsResponse();
        //response.prettyPrint();

        //Create Application
        String key = "qatestapp"+faker.number().digits(4);
        String name = "QA API Test App" + key.replaceAll("\\D","");
        String url = "https://www.google.com";
        String provider = "mss";
        String type = "ExternalApplication";//SinglePageApplication, ExternalApplication, WebApplication
        response = apiController.postEMCNewApplicationResponse(key, name, url, provider, type);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 201, "Status code 201 Created is verified");
        assertTestCase.assertEquals(response.path("name"), "CreatedResponse", "Application creation response name is verified");
        assertTestCase.assertEquals(response.path("message"), "Application " + key + " created", "Application creation response message with key is verified");
        List<Application> applications = apiController.getEMCAllApplicationsResponse().jsonPath().getList("", Application.class);
        System.out.println("applications.size() = " + applications.size());

        //Verify Application details
        Application application = new Application();
        for (Application app : applications) {
            //System.out.println("app = " + app);
            if (app.getId().equals(key)) {
                application = app;
                break;
            }
        }
        System.out.println("application = " + application);
        assertTestCase.assertEquals(application.getName(), name, "Application name is verified");
        assertTestCase.assertEquals(application.getUrl(), url, "Application url is verified");
        //assertTestCase.assertEquals(application.getProvider(), provider, "Application provider is verified");

        //Get Details of Single Application
        String appId = application.getId();
        response = apiController.getEMCApplicationDetailsResponse(appId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 200, "Status code 200 OK is verified");
        response.as(Application.class);

        //Update Application
        System.out.println("===================================");
        System.out.println("Updating Application with id: " + appId);
        application.setName("API Test App Updated");
        application.setUrl("https://www.google.com.mx");
        application.setProvider("mss");
        response = apiController.putEMCApplicationResponse(appId, application);

        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 204, "Status code 204 Updated is verified");
        //assertTestCase.assertEquals(response.path("name"), "UpdatedResponse", "Application update response name is verified");
        //assertTestCase.assertEquals(response.path("message"), "Application "+key+" updated", "Application update response message with key is verified");

        //Verify Application details
        Application updatedApplication = apiController.getEMCApplicationDetailsResponse(appId).as(Application.class);
        assertTestCase.assertEquals(updatedApplication.getName(), "API Test App Updated", "Application name is verified");
        assertTestCase.assertEquals(updatedApplication.getUrl(), "https://www.google.com.mx", "Application url is verified");
        //assertTestCase.assertEquals(updatedApplication.getProvider(), "mss", "Application provider is verified");

        //Delete Application - Verify You CAN"T delete an application
        response = apiController.deleteEMCApplicationResponse(appId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 204, "Status code 204 No Content is verified");
        //assertTestCase.assertEquals(response.path("name"), "DeletedResponse", "Application delete response name is verified");
//        assertTestCase.assertEquals(response.path("message"), "Missing Authentication Token", "Application delete response message is verified");
        List<Application> updatedApplications = apiController.getEMCAllApplicationsResponse().jsonPath().getList("", Application.class);
        assertTestCase.assertFalse(updatedApplications.contains(application), "Application is not deleted successfully");
    }

    @Test(groups = {EMC, API, REGRESSION}, description = "API | EMC | Apps | CRUD Operations for Wev Applications")
    @Xray(test = {3300})
    public void verifyCRUDOperationsForWebApplicationsTest() {
        //Get All Applications
        response = apiController.getEMCAllApplicationsResponse();
        //response.prettyPrint();

        //Create Application
        String key = "qatestapp"+faker.number().digits(4);
        String name = "QA API Test App" + key.replaceAll("\\D","");
        String url = "https://www.google.com";
        String provider = "mss";
        String type = "WebApplication";//SinglePageApplication, ExternalApplication, WebApplication
        response = apiController.postEMCNewApplicationResponse(key, name, url, provider, type);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 201, "Status code 201 Created is verified");
        assertTestCase.assertEquals(response.path("name"), "CreatedResponse", "Application creation response name is verified");
        assertTestCase.assertEquals(response.path("message"), "Application " + key + " created", "Application creation response message with key is verified");
        List<Application> applications = apiController.getEMCAllApplicationsResponse().jsonPath().getList("", Application.class);
        System.out.println("applications.size() = " + applications.size());

        //Verify Application details
        Application application = new Application();
        for (Application app : applications) {
            //System.out.println("app = " + app);
            if (app.getId().equals(key)) {
                application = app;
                break;
            }
        }
        System.out.println("application = " + application);
        assertTestCase.assertEquals(application.getName(), name, "Application name is verified");
        assertTestCase.assertEquals(application.getUrl(), url, "Application url is verified");
        //assertTestCase.assertEquals(application.getProvider(), provider, "Application provider is verified");

        //Get Details of Single Application
        String appId = application.getId();
        response = apiController.getEMCApplicationDetailsResponse(appId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 200, "Status code 200 OK is verified");
        response.as(Application.class);

        //Update Application
        System.out.println("===================================");
        System.out.println("Updating Application with id: " + appId);
        application.setName("API Test App Updated");
        application.setUrl("https://www.google.com.mx");
        application.setProvider("mss");
        response = apiController.putEMCApplicationResponse(appId, application);

        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 204, "Status code 204 Updated is verified");
        //assertTestCase.assertEquals(response.path("name"), "UpdatedResponse", "Application update response name is verified");
        //assertTestCase.assertEquals(response.path("message"), "Application "+key+" updated", "Application update response message with key is verified");

        //Verify Application details
        Application updatedApplication = apiController.getEMCApplicationDetailsResponse(appId).as(Application.class);
        assertTestCase.assertEquals(updatedApplication.getName(), "API Test App Updated", "Application name is verified");
        assertTestCase.assertEquals(updatedApplication.getUrl(), "https://www.google.com.mx", "Application url is verified");
        //assertTestCase.assertEquals(updatedApplication.getProvider(), "mss", "Application provider is verified");

        //Delete Application - Verify You CAN"T delete an application
        response = apiController.deleteEMCApplicationResponse(appId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 204, "Status code 204 No Content is verified");
        //assertTestCase.assertEquals(response.path("name"), "DeletedResponse", "Application delete response name is verified");
//        assertTestCase.assertEquals(response.path("message"), "Missing Authentication Token", "Application delete response message is verified");
        List<Application> updatedApplications = apiController.getEMCAllApplicationsResponse().jsonPath().getList("", Application.class);
        assertTestCase.assertFalse(updatedApplications.contains(application), "Application is not deleted successfully");
    }

    @Test(groups = {EMC, API, REGRESSION, SMOKE}, description = "API | EMC | API | Application | Verify user can Create a new External Application")
    @Xray(test = {2689, 2749})
    public void verifyUserCreateApplicationsTest() {
        //Get All Applications
        response = apiController.getEMCAllApplicationsResponse();
        //response.prettyPrint();

        //Create External Application
        String key = "test-app-external-qa" + faker.number().digits(4);
        String name = "QA Test External API" + key.replaceAll("\\D", "");
        String url = "https://test.com.mx/";
        String provider = "ma";
        String type = "ExternalApplication";//SinglePageApplication, ExternalApplication, WebApplication
        apiController.createApplicationAndVerify(key, name, url, provider, type);

        //Create Web Application
        key = "test-app-internal-qa" + faker.number().digits(4);
        name = "QA Test internal API" + key.replaceAll("\\D", "");
        url = "https://test.com.mx/";
        provider = "mss";
        type = "WebApplication";//SinglePageApplication, ExternalApplication, WebApplication
        apiController.createApplicationAndVerify(key, name, url, provider, type);

        //Create Single Page Application
        key = "test-app-single-qa" + faker.number().digits(4);
        name = "QA Test Single Page API" + key.replaceAll("\\D", "");
        url = "https://test.com.mx/";
        provider = "mss";
        type = "SinglePageApplication";//SinglePageApplication, ExternalApplication, WebApplication
        apiController.createApplicationAndVerify(key, name, url, provider, type);
    }

    @Test(groups = {EMC, API, REGRESSION}, description = "API | EMC | API | Application | Verify user can't Create a new Application with duplicate key")
    @Xray(test = {2659, 2688})
    public void verifyUserCantCreateApplicationsWithDuplicateKeyTest() {
        //Get All Applications
        response = apiController.getEMCAllApplicationsResponse();
        //response.prettyPrint();

        String applicationName = Environment.MESG_APPLICATION_NAME;
        System.out.println("applicationName = " + applicationName);
        String applicationKey = apiController.getApplicationKey(applicationName);
        System.out.println("applicationKey = " + applicationKey);
        String applicationUrl = apiController.getApplicationUrl(applicationName);
        System.out.println("applicationUrl = " + applicationUrl);
        assertTestCase.assertTrue(apiController.verifyApplication(apiController.getApplicationId(applicationName)), "Application creation response type is verified");

        //Create External Application with duplicate name
        String provider = "mss";
        String type = "ExternalApplication";//SinglePageApplication, ExternalApplication, WebApplication
        Response response = apiController.postEMCNewApplicationResponse(applicationKey, applicationName, applicationUrl, provider, type);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 409, "Status code 409 Created is verified");
        assertTestCase.assertEquals(response.path("name"), "DuplicateException", "Application creation failed response name is verified");
        assertTestCase.assertEquals(response.path("message"), "An application with name "+applicationName+" already exists.", "Application creation failed response message is verified");


        //Create Web Application with duplicate name
        type = "WebApplication";//SinglePageApplication, ExternalApplication, WebApplication
        response = apiController.postEMCNewApplicationResponse(applicationKey, applicationName, applicationUrl, provider, type);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 409, "Status code 409 Created is verified");
        assertTestCase.assertEquals(response.path("name"), "DuplicateException", "Application creation failed response name is verified");
        assertTestCase.assertEquals(response.path("message"), "An application with name "+applicationName+" already exists.", "Application creation failed response message is verified");


        //Create Single Page Application with duplicate name
        type = "SinglePageApplication";//SinglePageApplication, ExternalApplication, WebApplication
        response = apiController.postEMCNewApplicationResponse(applicationKey, applicationName, applicationUrl, provider, type);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 409, "Status code 409 Created is verified");
        assertTestCase.assertEquals(response.path("name"), "DuplicateException", "Application creation failed response name is verified");
        assertTestCase.assertEquals(response.path("message"), "An application with name "+applicationName+" already exists.", "Application creation failed response message is verified");

        applicationName ="Random Name";
        assertTestCase.assertFalse(apiController.verifyApplication(apiController.getApplicationId(applicationName)), "Application creation response type is verified");
        //Create External Application with duplicate key
        type = "ExternalApplication";//SinglePageApplication, ExternalApplication, WebApplication
        response = apiController.postEMCNewApplicationResponse(applicationKey, applicationName, applicationUrl, provider, type);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 409, "Status code 409 Created is verified");
        assertTestCase.assertEquals(response.path("name"), "DuplicateException", "Application creation failed response name is verified");
        assertTestCase.assertEquals(response.path("message"), "An application with Key "+applicationKey+" already exists.", "Application creation failed response message is verified");

        //Create Web Application with duplicate key
        type = "WebApplication";//SinglePageApplication, ExternalApplication, WebApplication
        response = apiController.postEMCNewApplicationResponse(applicationKey, applicationName, applicationUrl, provider, type);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 409, "Status code 409 Created is verified");
        assertTestCase.assertEquals(response.path("name"), "DuplicateException", "Application creation failed response name is verified");
        assertTestCase.assertEquals(response.path("message"), "An application with Key "+applicationKey+" already exists.", "Application creation failed response message is verified");


        //Create Single Page Application with duplicate key
        type = "SinglePageApplication";//SinglePageApplication, ExternalApplication, WebApplication
        response = apiController.postEMCNewApplicationResponse(applicationKey, applicationName, applicationUrl, provider, type);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 409, "Status code 409 Created is verified");
        assertTestCase.assertEquals(response.path("name"), "DuplicateException", "Application creation failed response name is verified");
        assertTestCase.assertEquals(response.path("message"), "An application with Key "+applicationKey+" already exists.", "Application creation failed response message is verified");
    }

    @Test(groups = {EMC, API, REGRESSION}, description = "API | EMC | Accounts Applications | Validate User is Able to Delete the Relation between Account and Application")
    @Xray(test = {1374})
    public void verifyRemoveApplicationFromAccountTest() {
        //verify if application assigned to account
        if (!apiController.verifyApplication(accountId, applicationId)) {
            response = apiController.assignApplicationToAccountResponse(accountId, applicationId);
            response.prettyPrint();
            assertTestCase.assertEquals(response.statusCode(), 200, "Status code 200 OK is verified");
            assertTestCase.assertEquals(response.path("statusCode") + "", "202", "Response status code is verified");
        }
        //delete application from account
        response = apiController.deleteEMCRemoveApplicationFromAccountResponse(accountId, applicationId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 204, "Status code 204 OK is verified");
        //verify if application is deleted from account
        assertTestCase.assertFalse(apiController.verifyApplication(accountId, applicationId), "Application is deleted from account successfully");
    }

    @Test(groups = {EMC, API, REGRESSION},
            description = "API | EMC | Accounts Applications | Verify User does not have Access to DELETE Applications with Invalid Access Token")
    @Xray(test = {1654})
    public void verifyUserCantDeleteApplicationWithWrongAccessTokenTest() {

        //verify account has application assigned
        if (!apiController.verifyApplication(accountId, applicationId)) {
            response = apiController.assignApplicationToAccountResponse(accountId, applicationId);
            response.prettyPrint();
            assertTestCase.assertEquals(response.statusCode(), 200, "Status code 200 OK is verified");
            assertTestCase.assertEquals(response.path("statusCode") + "", "202", "Response status code is verified");
        }
        String accessToken = System.getProperty("token");
        System.out.println("accessToken = " + accessToken);
        System.setProperty("token", "accessToken");
        try {
            //verify error handling for wrong token
            response = apiController.deleteEMCRemoveApplicationFromAccountResponse(accountId, applicationId);
            response.prettyPrint();
            assertTestCase.assertEquals(response.statusCode(), 401, "Status code 401 Unauthorized is verified");
            assertTestCase.assertEquals(response.path("message"), "Unauthorized", "User deletion without access token response message is verified");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.setProperty("token", accessToken);
        }
    }

    @Test(groups = {EMC, API, REGRESSION},
            description = "API | EMC | Accounts Applications | Verify User does not have Access to PUT Applications with Invalid Access Token")
    @Xray(test = {2063})
    public void verifyUserCantAssignApplicationWithWrongAccessTokenTest() {

        //verify account has application assigned
        if (apiController.verifyApplication(accountId, applicationId)) {
            System.out.println("Application is already assigned to account");
            response = apiController.deleteEMCRemoveApplicationFromAccountResponse(accountId, applicationId);
            assertTestCase.assertEquals(response.statusCode(), 204, "Status code 204 Deleted is verified");
            assertTestCase.assertFalse(apiController.verifyApplication(accountId, applicationId), "Application is deleted from account successfully");
        }
        String accessToken = System.getProperty("token");
        System.out.println("accessToken = " + accessToken);
        System.setProperty("token", "accessToken");
        try {
            //verify error handling for wrong token
            response = apiController.assignApplicationToAccountResponse(accountId, applicationId);
            response.prettyPrint();
            assertTestCase.assertEquals(response.statusCode(), 401, "Status code 401 Unauthorized is verified");
            assertTestCase.assertEquals(response.path("message"), "Unauthorized", "User deletion without access token response message is verified");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.setProperty("token", accessToken);
        }
    }

    @Test(groups = {EMC, API, REGRESSION},
            description = "API | EMC | Accounts Applications | Verify User does not have Access to Assign/DELETE Applications without Access Token")
    @Xray(test = {1252, 1326})
    public void verifyUserCantDeleteApplicationWithoutAccessTokenTest() {

        //verify account has application assigned
        if (!apiController.verifyApplication(accountId, applicationId)) {
            response = apiController.assignApplicationToAccountResponse(accountId, applicationId);
            response.prettyPrint();
            assertTestCase.assertEquals(response.statusCode(), 200, "Status code 200 OK is verified");
            assertTestCase.assertEquals(response.path("statusCode") + "", "202", "Response status code is verified");
        }
        response = apiController.deleteEMCRemoveApplicationFromAccountResponse(accountId, applicationId, true);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 401, "Status code 401 Unauthorized is verified");
        assertTestCase.assertEquals(response.path("message"), "Unauthorized", "User creation without access token response message is verified");

        //delete application from account and try to assign without access token
        response = apiController.deleteEMCRemoveApplicationFromAccountResponse(accountId, applicationId);
        response = apiController.assignApplicationToAccountResponse(accountId, applicationId, true);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 401, "Status code 401 Unauthorized is verified");
    }

    @Test(groups = {EMC, API, REGRESSION, SMOKE, PROD},
            description = "API | EMC | Accounts Applications | Verify that User is Able to Add a New Relation between Account and Applications")
    @Xray(test = {1746})
    public void verifyAssignApplicationToAccountTest() {
        if (apiController.verifyApplication(accountId, applicationId)) {
            System.out.println("Application is already assigned to account");
            response = apiController.deleteEMCRemoveApplicationFromAccountResponse(accountId, applicationId);
            assertTestCase.assertEquals(response.statusCode(), 204, "Status code 204 Deleted is verified");
            assertTestCase.assertFalse(apiController.verifyApplication(accountId, applicationId), "Application is deleted from account successfully");
        }
        response = apiController.assignApplicationToAccountResponse(accountId, applicationId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 200, "Status code 200 OK is verified");
        assertTestCase.assertEquals(response.path("statusCode") + "", "202", "Response status code is verified");
        assertTestCase.assertTrue(apiController.verifyApplication(accountId, applicationId), "Application is deleted from account successfully");
    }

    @Test(groups = {EMC, API, REGRESSION, SMOKE},
            description = "API | EMC | Accounts | Verify a user's with proper permission role (view users) API response to create, update, retrieve and dlete the user list from an account")
    @Xray(test = {4183, 4067, 4148, 1489})
    public void verifyUserCRUDOperationsUnderAccountTest() {
        response = apiController.getListOfUsersResponse(accountId);
        //response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 200, "Status code 200 OK is verified");
        List<AssignedUser> users = response.jsonPath().getList("users", AssignedUser.class);
        System.out.println("users.size() = " + users.size());

        //create user
        String email = "test" + System.currentTimeMillis() + "@test.com";
        String firstName = "QAtest";
        String lastName = "QAtest";
        String provider = "mss";
        response = apiController.postEMCNewUserResponse("mss", firstName, lastName, email, email, false, accountId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 201, "Status code 201 create is verified");
        assertTestCase.assertEquals(response.path("name"), "CreatedResponse", "Response name is verified");
        assertTestCase.assertEquals(response.path("message"), "Resource created: " + email, "Response message is verified");
        assertTestCase.assertTrue(apiController.verifyUser(accountId, email), "User is verified in account");

        //Delete user and verify
        response = apiController.deleteEMCUserResponse(email);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 204, "Status code 204 Deleted is verified");
        assertTestCase.assertFalse(apiController.verifyUser(accountId, email), "User is deleted from account successfully");

    }

    @Test(groups = {EMC, API, REGRESSION},
            description = "API | EMC | Issuer | Verify user is not Able to Create a New Issuer Account using Moody's email")
    @Xray(test = {1287})
    public void verifyUserCantCreateNewIssuerAccountUsingMoodyEmailTest() {
        response = apiController.getListOfUsersResponse(accountId);
        //response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 200, "Status code 200 OK is verified");
        List<AssignedUser> users = response.jsonPath().getList("users", AssignedUser.class);
        System.out.println("users.size() = " + users.size());

        //create user
        String email = "qatest@moodys.com";
        String firstName = "QAtest";
        String lastName = "QAtest";
        String provider = "mss";
        response = apiController.postEMCNewUserResponse("mss", firstName, lastName, email, email, false, accountId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 403, "Status code 403 Exception is verified");
        assertTestCase.assertEquals(response.path("name"), "ForbiddenException", "Response name is verified");
        assertTestCase.assertEquals(response.path("message"), "Cannot create user: " + email, "Response message is verified");
        assertTestCase.assertFalse(apiController.verifyUser(accountId, email), "User is verified in account");

    }

    @Test(groups = {EMC, API, REGRESSION},
            description = "API | EMC | Issuer | Verify user is not able to create a New External Issuer Account for an existing User")
    @Xray(test = {2033})
    public void verifyUserCANTCreateNewExternalIssuerAccountWithExistingUserTest() {
        //create user
        String email = "ferhat.demir@atos.net";
        String firstName = "QAtest";
        String lastName = "QAtest";
        String provider = "mss";
        assertTestCase.assertTrue(apiController.verifyUser(accountId, email), "User is verified in account");
        response = apiController.postEMCNewUserResponse("mss", firstName, lastName, email, email, false, accountId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 409, "Status code 409 Duplicate Account Exception is verified");
        assertTestCase.assertEquals(response.path("name"), "DuplicateException", "Response name is verified");
        assertTestCase.assertEquals(response.path("message"), "Duplicate resource: " + email, "Response message is verified");
    }

    @Test(groups = {EMC, API, REGRESSION},
            description = "API | EMC | Product | Verify GET Products API Response with Valid Parameters and Products Sort By Name")
    @Xray(test = {1333, 1735})
    public void verifyGETProductsAPIResponseWithValidParametersTest() {
        assertTestCase.assertTrue(apiController.verifyApplication(applicationId), "Application is verified in account");
        response = apiController.getEMCProductsForApplicationResponse(applicationId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 200, "Status code 200 OK is verified");
        List<Product> products = response.jsonPath().getList("products", Product.class);
        System.out.println("products.size() = " + products.size());
        assertTestCase.assertTrue(products.size() > 0, "Products are listed");
        List<String> names = response.jsonPath().getList("products.name");
        //sort names
        names.sort(String::compareToIgnoreCase);
        assertTestCase.assertEquals(names, response.jsonPath().getList("products.name"), "Products are sorted by name");
    }

    @Test(groups = {EMC, API, REGRESSION, SMOKE, PROD},
            description = "API | EMC | Product | Verify User does not have Access to GET Products without Access Token")
    @Xray(test = {2093})
    public void verifyUserCantAccessGETProductsWithoutAccessTokenTest() {
        assertTestCase.assertTrue(apiController.verifyApplication(applicationId), "Application is verified in account");
        response = apiController.getEMCProductsForApplicationResponse(applicationId, true);//send true for no token
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 401, "Status code 401 Unauthorized is verified");
        assertTestCase.assertEquals(response.path("message"), "Unauthorized", "Response message is verified");
    }

    @Test(groups = {EMC, API, REGRESSION, SMOKE, PROD},
            description = "API | EMC | Product | Verify User does not have Access to GET Products with Invalid Access Token")
    @Xray(test = {1309})
    public void verifyUserCantAccessGETProductsWithInvalidAccessToken() {
        assertTestCase.assertTrue(apiController.verifyApplication(applicationId), "Application is verified in account");
        String accessToken = System.getProperty("token");
        System.out.println("accessToken = " + accessToken);
        System.setProperty("token", "accessToken");
        try {
            //verify error handling for wrong token
            response = apiController.getEMCProductsForApplicationResponse(applicationId);//send true for no token
            response.prettyPrint();
            assertTestCase.assertEquals(response.statusCode(), 401, "Status code 401 Unauthorized is verified");
            assertTestCase.assertEquals(response.path("message"), "Unauthorized", "Response message is verified");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.setProperty("token", accessToken);
        }
    }

    @Test(groups = {EMC, UI, REGRESSION, SMOKE})
    @Xray(test = {3976, 4034})
    public void verifyAdminUserCanGETListOfAllAdminUserPermissionsTest() {
        response = apiController.getEMCAllAdminUsersPermissionsResponse();
        response.prettyPrint();
        System.out.println("response = " + response.statusCode());

        assertTestCase.assertEquals(response.statusCode(), 200, "Status code 200 OK is verified");
        List<String> roleNames = response.jsonPath().getList("");
        List<String> expectedAdminRoleNames = Arrays.asList("activate-account-user", "admin-assign-role-permissions", "admin-assign-role-users",
                "admin-create-role", "admin-remove-role-permissions", "admin-remove-role-users", "admin-sync-users", "admin-view-configuration",
                "admin-view-role", "admin-view-role-detail", "admin-view-role-permissions", "admin-view-role-users", "admin-view-roles",
                "assign-account-applications", "assign-account-products", "assign-account-user-application-roles", "create-account",
                "create-account-user", "create-application-product", "create-application-role", "edit-account-detail",
                "edit-account-user-detail", "edit-application-detail", "edit-application-product", "reactivate-account-user",
                "remove-account-applications", "remove-account-products", "remove-account-user", "remove-account-user-application-roles",
                "reset-password-account-user", "search-account-users", "search-accounts", "search-applications", "search-users",
                "suspend-account-user", "unsuspend-account-user", "view-account", "view-account-applications", "view-account-detail",
                "view-account-products", "view-account-user", "view-account-user-application-roles", "view-account-user-detail",
                "view-account-users", "view-accounts", "view-application", "view-application-detail", "view-application-product",
                "view-application-products", "view-application-role", "view-application-roles", "view-applications", "view-users");
        List<String> expectedViewerRoleNames = Arrays.asList("search-account-users", "search-accounts", "search-applications",
                "search-users", "view-account", "view-account-applications", "view-account-detail", "view-account-products",
                "view-account-user", "view-account-user-application-roles", "view-account-user-detail", "view-account-users",
                "view-accounts", "view-application", "view-application-detail", "view-application-product", "view-application-products",
                "view-application-role", "view-application-roles", "view-applications", "view-users");
        assertTestCase.assertTrue(roleNames.containsAll(expectedAdminRoleNames), "Admin Role names are verified");
        assertTestCase.assertTrue(roleNames.containsAll(expectedViewerRoleNames), "Viewer Role names are verified");
    }

    @Test(groups = {EMC, API, REGRESSION}, description = "API | EMC | Applications | Roles | Verify the user is able to create a new role for an application")
    @Xray(test = {1643})
    public void createRoleForApplicationTest() {
        response = apiController.getAllRolesForApplicationResponse(applicationId);
        String roleName = "qatestrole"+faker.number().digits(4);
        response = apiController.createRoleForApplicationResponse(applicationId, roleName, roleName);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 201, "Status code 201 Created is verified");
        assertTestCase.assertEquals(response.path("name"), "CreatedResponse", "Response name is verified");
        assertTestCase.assertEquals(response.path("message"), "Application Role "+roleName+" created", "Response message is verified");
        assertTestCase.assertTrue(apiController.verifyRoleForApplication(applicationId, roleName), "Role is verified for application");
    }

    @Test(groups = {EMC, API, REGRESSION}, description = "API | EMC | Applications | Roles | Verify that a role for an application can be assigned to a user")
    @Xray(test = {1643, 3540})
    public void assignRoleToUserTest() {
        //get email of user with name Active User
        String email = apiController.getUserId(accountId, "Active User");
        System.out.println("email = " + email);
        String mesgAppId = apiController.getApplicationId(Environment.MESG_APPLICATION_NAME);
        System.out.println("mesgAppId = " + mesgAppId);

        //get Investor role id for MESG application
        String roleId = apiController.getRoleId(mesgAppId, "Investor");
        System.out.println("roleId = " + roleId);
        apiController.deleteApplicationRoleFromUser(roleId, email);
        assertTestCase.assertFalse(apiController.verifyApplicationRoleForUser(email, roleId), "User don't have that role");
        apiController.assignApplicationRoleToUser(roleId, email);
        assertTestCase.assertTrue(apiController.verifyApplicationRoleForUser(email, roleId), "User have that role");
    }

    @Test(groups = {EMC, API, REGRESSION}, description = "API | EMC | Applications | Products | CRUD Operations for Products")
    @Xray(test = {3965})
    public void verifyCRUDOperationsForApplicationsProductsTest() {
        String applicationId = apiController.getApplicationId("TestQA");
        System.out.println("appId = " + applicationId);

        //get all products for application
        response = apiController.getAllProductsForApplicationResponse(applicationId);
        response.prettyPrint();

        //create new product for application
        String productName = "qatestproduct"+faker.number().digits(4);
        response = apiController.createProductForApplicationResponse(applicationId, productName, productName);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 200, "Status code 201 Created is verified");
        assertTestCase.assertEquals(response.path("statusCode")+"", "201", "Response name is verified");
        assertTestCase.assertTrue(apiController.verifyProductForApplication(applicationId, productName), "Product is verified for application");
        String productId = apiController.getProductId(applicationId, productName);
        System.out.println("productId = " + productId);

        //delete product for application
        response = apiController.deleteProductForApplicationResponse(applicationId, productId);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 204, "Status code 200 OK is verified");
        assertTestCase.assertFalse(apiController.verifyProductForApplication(applicationId, productName), "Product is not verified for application");
    }

    @Test(groups = {EMC, API, REGRESSION, SMOKE}, description = "API | EMC | SME Assessment | Add SME Product to Account")
    @Xray(test = {3864, 3825})
    public void verifyAddSMEProductToAccountTest() {
        String accountId = Environment.QA_TEST_ACCOUNT_ID;
        System.out.println("accountId = " + accountId);
        String applicationId = apiController.getApplicationId(Environment.MESG_APPLICATION_NAME);
        System.out.println("applicationId = " + applicationId);
        String productId = apiController.getProductId(applicationId, "ESG On-Demand Assessment");
        System.out.println("productId = " + productId);
        if (apiController.verifyProductForAccount(accountId, productId)) {
            System.out.println("Product is already added to account. Deleting it.");
            apiController.deleteProductFromAccount(accountId, productId);
        }
        assertTestCase.assertFalse(apiController.verifyProductForAccount(accountId, productId), "Product is verified for account");
        apiController.putProductToAccount(accountId, applicationId, productId, 100);
        assertTestCase.assertTrue(apiController.verifyProductForAccount(accountId, productId), "Product is verified for account");
    }

    @Test(groups = {EMC, API, REGRESSION, SMOKE}, description = "API | EMC | App Integration | Add App Integration Product to Account")
    @Xray(test = {4245})
    public void appIntegrationResponseTests() {
        String Application_key = apiController.getApplicationKey(Environment.MESG_APPLICATION_NAME);
        System.out.println("Application_key = " + Application_key);
        response = apiController.getAppIntegrationResponse(Application_key);
        response.prettyPrint();
        List<String> purchasedAssessmentsList = response.jsonPath().getList("entitlements.info.assessmentsInfo.usedAssessments");
        System.out.println("purchasedAssessmentsList = " + purchasedAssessmentsList);
        List<String> usedAssessmentsList = response.jsonPath().getList("entitlements.info.assessmentsInfo.purchasedAssessments");
        System.out.println("usedAssessmentsList = " + usedAssessmentsList);
        assertTestCase.assertEquals(response.statusCode(), 200, "Status code 200 OK is verified");
        assertTestCase.assertTrue(purchasedAssessmentsList.size() > 0, "Purchased assessments list is not empty");
        assertTestCase.assertTrue(usedAssessmentsList.size() > 0, "Used assessments list is not empty");
    }

    @Test(groups = {EMC, API, REGRESSION}, description = "API | EMC | User Info | Verify User does not have Access to GET UserInfo without Access Token")
    @Xray(test = {4120, 4132})
    public void appIntegrationResponseWithoutAccessTokenTests() {
        String Application_key = apiController.getApplicationKey(Environment.MESG_APPLICATION_NAME);
        System.out.println("Application_key = " + Application_key);
        apiController.setInvalid();
        response = apiController.getAppIntegrationResponse(Application_key);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 401, "Status code 401 Unauthorized is verified");
        assertTestCase.assertEquals(response.path("message")+"", "Unauthorized", "Response message is verified");
        String token = System.getProperty("token");
        try{
            System.setProperty("token", "InvalidToken");
            response = apiController.getAppIntegrationResponse(Application_key);
            response.prettyPrint();
            assertTestCase.assertEquals(response.statusCode(), 401, "Status code 401 Unauthorized is verified");
            assertTestCase.assertEquals(response.path("message")+"", "Unauthorized", "Response message is verified");
        } catch (Exception e) {
            System.out.println("Exception is thrown");
        } finally {
            System.setProperty("token", token);
        }
        response = apiController.getAppIntegrationResponse(Application_key);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 200, "Status code 200 OK is verified");
    }

    @Test(groups = {EMC, API, REGRESSION, SMOKE}, description = "API | EMC | SME | Verify that PUT update the Used SME assessments on ESG On-Demand Assessments")
    @Xray(test = {4052, 3821, 4204, 4254, 4223})
    public void verifyPutUpdateUsedSMEAssessmentsTest() {
        String accountId = Environment.QA_TEST_ACCOUNT_ID;
        System.out.println("accountId = " + accountId);
        response = apiController.getAccountInfo(accountId);
        response.prettyPrint();
        String accountKey = response.path("key");
        System.out.println("accountKey = " + accountKey);

        response = apiController.putAssessmentUsageInfo(accountKey, 1);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 200, "Status code 200 OK is verified");
        assertTestCase.assertEquals(response.path("info.usedAssessmentsDelta")+"", "1", "usedAssessmentsDelta is verified");
        System.out.println("usedAssessmentsDelta with positive value verified");

        response = apiController.putAssessmentUsageInfo(accountKey, -1);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 200, "Status code 200 OK is verified");
        assertTestCase.assertEquals(response.path("info.usedAssessmentsDelta")+"", "-1", "usedAssessmentsDelta is verified");
        System.out.println("usedAssessmentsDelta with negative value verified");

        //request more than available assessment count
        int currentPurchasedAssessments = response.jsonPath().getInt("info.currentPurchasedAssessments")+1;
        System.out.println("currentPurchasedAssessments = " + currentPurchasedAssessments);
        response = apiController.putAssessmentUsageInfo(accountKey, currentPurchasedAssessments);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 200, "Status code 200 OK is verified");
        assertTestCase.assertEquals(response.path("name"), "InvalidUsedAssessmentsDelta", "InvalidUsedAssessmentsDelta is verified");
        System.out.println("InvalidUsedAssessmentsDelta with more than available assessments verified");

        //request less than used assessments

        response = apiController.putAssessmentUsageInfo(accountKey, -currentPurchasedAssessments);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 200, "Status code 200 OK is verified");
        assertTestCase.assertEquals(response.path("name"), "InvalidUsedAssessmentsDelta", "InvalidUsedAssessmentsDelta is verified");

        apiController.setInvalid();
        response = apiController.putAssessmentUsageInfo(accountKey, 10);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 403, "Status code 403 Forbidden is verified");
        assertTestCase.assertEquals(response.path("message"), "Forbidden", "Response message is verified");

        response = apiController.putAssessmentUsageInfo(accountKey, "InvalidValue");
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(), 403, "Status code 403 Forbidden is verified");
        assertTestCase.assertEquals(response.path("message"), "Forbidden", "Response message is verified");
    }
}
