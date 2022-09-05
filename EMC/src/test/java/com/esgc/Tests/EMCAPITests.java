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
    @Xray(test = {6871,6872})
    public void verifyAdminUserCRUDOperationsOnUserTest() {

        //Create User
        String email = faker.internet().emailAddress();
        System.out.println("email = " + email);
        String accountId = "6763cd03-aa47-4887-b129-efbe27d88f6b";//INTERNAL QATest - PROD123 Account
        response = apiController.postEMCNewUserResponse("mss","QATest","User",email,email,false,accountId);
        assertTestCase.assertEquals(response.statusCode(),201, "Status code 200 OK is verified");
        assertTestCase.assertEquals(response.path("message"), "Resource created: "+email, "User created successfully");

        //Update User
        User user = apiController.getEMCUserDetailsResponse(email).as(User.class);
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


}
