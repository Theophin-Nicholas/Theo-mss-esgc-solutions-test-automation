package com.esgc.API;

/**
 * This utility class created to store all Endpoints for API calls
 */

public class OnDemandEndpoints {

    public static String GET_PORTFOLIO_DETAILS = "/api/report/portfolio-details";
    public static String POST_ON_DEMAND_FILTER = "/api/portfolio/{portfolioId}/on-demand/filter";
    public static String GET_ON_DEMAND_STATUS = "/api/portfolio/{portfolioId}/on-demand/status";
    public static String DASHBOARD_COVERAGE = "api/portfolio/{portfolioId}/dashboard-coverage" ;
    public static String IMPORT_PORTFOLIO = "api/portfolio";
    public static String DELETE_PORTFOLIO = "api/portfolio/{portfolio_id}";



}
