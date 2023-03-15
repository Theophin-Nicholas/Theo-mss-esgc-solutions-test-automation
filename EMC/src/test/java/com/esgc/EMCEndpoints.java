package com.esgc;

public class EMCEndpoints {
    public static final String GET_EMC_USER = "api/users";
    public static final String EMC_APPS = "api/applications";
    public static final String GET_EMC_ALL_ACCOUNTS = "api/accounts";
    public static final String GET_EMC_ALL_ROLES = "api/admin/roles";
    public static final String GET_EMC_ALL_ADMIN_USERS = "api/admin/users";
    public static final String POST_EMC_NEW_USER = "api/users";

    public static String EMC_ADMIN_ROLES = "api/admin/roles";
    public static String EMC_ADMIN_USERS = "api/admin/users";

    //========User
    public static String EMC_USER = "api/users";
    public static String EMC_ROLE_USERS = "api/admin/roles/{roleId}/users";
    public static String EMC_ROLE_USER_CRUD = "api/admin/roles/{roleId}/users/{userId}";

    //========Account
    public static String EMC_ACCOUNTS = "api/accounts";
    public static final String PUT_APPLICATION_TO_ACCOUNT = "api/accounts/{account-id}/applications/{application-id}";
    public static String PUT_PRODUCT_TO_ACCOUNT = "api/accounts/{account-id}/applications/{application-id}/products/{product-id}";
    public static String GET_PRODUCTS_FOR_ACCOUNT = "api/accounts/{account-id}/products";
    public static String DELETE_PRODUCTS_FROM_ACCOUNT = "api/accounts/{account-id}/products/{product-id}";
    public static String GET_USERS = "api/accounts/{account-id}/users";
    public static String GET_APPLICATIONS = "api/applications";
    public static String ASSESSMENT_USAGE = "api/sme/accounts/{accountKey}/assessments_usage";

    //========Application Roles==========
    //to set Investor or Issuer role to a user
    public static String APPLICATION_ROLE = "api/applicationroles";
    public static String PUT_APPLICATION_ROLE = "api/applicationroles/{application-role-id}/users/{email}";
    public static String GET_APPLICATION_ROLES = "api/applications/{application-id}/roles";
    public static String GET_APPLICATION_ROLE_FOR_USER = "api/users/{user-id}/applicationroles";
    //========Application Products==========
    public static String GET_APPLICATION_PRODUCTS = "api/applications/{application-role-id}/products";
    public static String GET_PRODUCT_SECTIONS = "api/products/{product-id}/sections";

    //======== Products==========
    public static String GET_PRODUCTS = "api/products";

    //======== App Integration==========
    public static String GET_APP_INTEGRATION = "api/app_integration/userinfo?appKey={appkey}&roles=true&entitlements=true&account=true&profile=true";
}
