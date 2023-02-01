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

    //========Account
    public static String EMC_ACCOUNTS = "api/accounts";
    public static final String PUT_APPLICATION_TO_ACCOUNT = "api/accounts/{account-id}/applications/{application-id}";
    public static String PUT_PRODUCT_TO_ACCOUNT = "api/accounts/{account-id}/applications/{application-id}/products/{product-id}";

    public static String GET_APPLICATIONS = "api/applications";

    //========Application Roles==========
    //to set Investor or Issuer role to a user
    public static String PUT_APPLICATION_ROLE = "api/applicationroles/{application-role-id}/users/{email}";

    //========Application Products==========
    public static String GET_APPLICATION_PRODUCTS = "api/applications/{application-role-id}/products";
    public static String GET_PRODUCT_SECTIONS = "api/products/{product-id}/sections";
}
