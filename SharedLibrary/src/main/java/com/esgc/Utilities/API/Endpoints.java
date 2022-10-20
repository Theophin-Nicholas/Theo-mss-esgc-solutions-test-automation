package com.esgc.Utilities.API;

/**
 * This utility class created to store all Endpoints for API calls
 */

public class Endpoints {
    public static String EMC_USER = "api/users";
    public static String EMC_APPS = "api/applications";
    public static String EMC_ACCOUNTS = "api/accounts";
    public static String EMC_ADMIN_ROLES = "api/admin/roles";
    public static String EMC_ADMIN_USERS = "api/admin/users";
//    public static String POST_EMC_NEW_USER = "api/users";
    public static String GET_PORTFOLIOS_FOR_USER = "api/portfolios";
    public static String IMPORT_PORTFOLIO = "api/portfolio/upload";
    public static String POST_FILTER_OPTIONS_IN_PORTFOLIO = "api/portfolios/{portfolio_id}/filter";
    public static String POST_PORTFOLIO_SCORE = "api/portfolios/{portfolio_id}/{research_line}/score";
    public static String POST_PORTFOLIO_DISTRIBUTION = "api/portfolios/{portfolio_id}/{research_line}/distribution";
    public static String POST_PORTFOLIO_COVERAGE = "api/portfolios/{portfolio_id}/{research_line}/coverage";
    public static String POST_PORTFOLIO_UPDATES = "api/portfolios/{portfolio_id}/{research_line}/updates";
    public static String POST_PORTFOLIO_EMISSIONS = "api/portfolios/{portfolio_id}/{research_line}/emissions";
    public static String POST_LEADERS_AND_LAGGARDS = "api/portfolios/{portfolio_id}/{research_line}/leaders-laggards";
    public static String POST_PORTFOLIO_REGION_SUMMARY = "api/portfolios/{portfolio_id}/{research_line}/region-summary";
    public static String POST_PORTFOLIO_REGION_DETAILS = "api/portfolios/{portfolio_id}/{research_line}/region-details";
    public static String POST_PORTFOLIO_SECTOR_SUMMARY = "api/portfolios/{portfolio_id}/{research_line}/sector-summary";
    public static String POST_PORTFOLIO_SECTOR_DETAILS = "api/portfolios/{portfolio_id}/{research_line}/sector-details";
    public static String POST_PORTFOLIO_REGION_MAP = "api/portfolios/{portfolio_id}/{research_line}/region-map";
    public static String POST_PORTFOLIO_UNDERLYING_DATA_METRICS = "api/portfolios/{portfolio_id}/{research_line}/underlying-data-metrics";
    public static String POST_PHYSICAL_RISK_UNDERLYING_DATA_METRICS_DETAILS = "api/portfolios/{portfolio_id}/physicalrisk/{physical_risk}/underlying-data-metrics-details";
    public static String POST_IMPACT_DISTRIBUTION = "api/portfolios/{portfolio_id}/{research_line}/impact";
    public static String POST_HISTORY_TABLE = "api/portfolios/{portfolio_id}/{research_line}/entity-score-history";
    public static String POST_CARBONFOOTPRINT_EMMISSION = "api/portfolios/{portfolio_id}/carbonfootprint/emissions";
    public static String POST_HEATMAP = "api/portfolios/{portfolio_id}/heat-map";
    public static String PUT_PORTFOLIO_NAME_UPDATE = "api/portfolios/{portfolio_id}";
    public static String SEARCH = "api/os/search";
    public static String GET_ENTITLEMENT_HANDLER = "api/entitlement-handler";
    //=========== Portfolio Settings ===================

    public static String POST_PORTFOLIO_SETTINGS = "/api/portfolios/{portfolio_id}/portfolio-details";

}
