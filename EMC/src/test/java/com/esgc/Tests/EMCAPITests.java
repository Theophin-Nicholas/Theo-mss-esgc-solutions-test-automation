package com.esgc.Tests;

import com.esgc.APIModels.EMC.AdminUser;
import com.esgc.APIModels.EMC.Role;
import com.esgc.APIModels.EMC.User;
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
        String accountId = Environment.QA_TEST_ACCOUNT_ID;
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
        String accountId = Environment.QA_TEST_ACCOUNT_ID;

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
        String accountId = Environment.QA_TEST_ACCOUNT_ID;
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
        String accountId = Environment.QA_TEST_ACCOUNT_ID;

        response = apiController.postEMCNewUserResponse("mss","QA Test","User",email,email,false,accountId, true);
        response.prettyPrint();
        assertTestCase.assertEquals(response.statusCode(),401, "Status code 401 Unauthorized is verified");
        assertTestCase.assertEquals(response.path("message"), "Unauthorized", "User creation without access token response message is verified");

    }

    @Test(groups = {"EMC", "api", "regression"}, description = "API | EMC | Users | Update Single OKTA Users")
    @Xray(test = {4980})
    public void verifyUpdateSingleOKTAUsersTest() {
        String email = "esgc-platform-test-user-1@outlook.com";
        String accountId = Environment.QA_TEST_ACCOUNT_ID;

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

//        //Delete User
//        response = apiController.deleteEMCUserResponse(email);
//        assertTestCase.assertEquals(response.statusCode(),204, "Status code 204 OK is verified");
//        response.prettyPrint();
//        List<String> users = apiController.getEMCAllUsersResponse().jsonPath().getList("email");
//        assertTestCase.assertFalse(users.contains(email), "User deleted successfully");
    }
}
