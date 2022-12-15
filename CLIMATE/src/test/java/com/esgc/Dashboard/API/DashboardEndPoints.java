package com.esgc.Dashboard.API;

public class DashboardEndPoints {

    //========Performance Chart
    public static String POST_PERFORMANCE_CHART =
            "api/portfolio/{portfolio_id}/portfolio-researchlines";

    //=============Geographic Risk Map=========
    public static String POST_GEO_MAP_ENTITY_LIST = "api/portfolio/{portfolio_id}/{research_line}/entity-list";

    //============ Heat Map =================
    public static String POST_HEAT_MAP = "api/portfolio/{portfolio_id}/heat-map";
    public static String POST_HEAT_MAP_ENTITY_LIST = "api/portfolio/{portfolio_id}/heat-map-entity-list";


    //============ Controversies
    public static String POST_CONTROVERSIES = "api/portfolio/{portfolio_id}/{research_line}/portfolio-controversies";


    public static String POST_DASHBOARD_EXPORT_CLIMATE_DATA = "api/portfolio/{portfolio_id}/export-climate-data";

    //============ Portfolio Summary =================
    public static String POST_PORTFOLIO_SUMMARY_COMPANIES = "api/portfolio/{portfolio_id}/portfolio-summary-companies";
    public static String POST_ESG_SCORES = "api/portfolio/{portfolio_id}/esgasmt/score";
    public static String POST_DASHBOARD_COVERAGE = "api/portfolio/{portfolio_id}/dashboard-coverage";



}
