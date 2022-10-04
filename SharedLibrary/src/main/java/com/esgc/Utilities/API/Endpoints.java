package com.esgc.Utilities.API;

/**
 * This utility class created to store all Endpoints for API calls
 */

public class Endpoints {

    //=================PORTFOLIO APIs
    public static String IMPORT_PORTFOLIO = "api/portfolio";

    //================FILTERS


    public static String GET_EMC_USER = "api/users";
    public static String GET_EMC_ALL_ACCOUNTS = "api/admin/accounts";
    public static String GET_EMC_ALL_ROLES = "api/admin/roles";
    public static String GET_EMC_ALL_ADMIN_USERS = "api/admin/users";
    public static String POST_EMC_NEW_USER = "api/users";
    public static String GET_PORTFOLIOS_FOR_USER = "api/portfolios";

    public static String POST_FILTER_OPTIONS_IN_PORTFOLIO = "api/portfolio/{portfolio_id}/filter";
    public static String PUT_PORTFOLIO_NAME_UPDATE = "api/portfolio/{portfolio_id}";
    public static String SEARCH = "api/os/search";
    public static String GET_ENTITLEMENT_HANDLER = "api/entitlement-handler";
    //=========== Portfolio Settings ===================

    public static String POST_PORTFOLIO_SETTINGS = "/api/portfolio/{portfolio_id}/portfolio-details";

}
