package com.esgc.Utilities.API;

public class DashboardEndPoints {

    //========Performance Chart
    public static String POST_PERFORMANCE_CHART =
            "api/portfolios/{portfolio_id}/analysis/{performance_chart}/{size}/portfolio-researchlines";


    //=============Geographic Risk Map=========
    public static String POST_ENTITY_LIST = "api/portfolios/{portfolio_id}/{research_line}/entity-list";
    public static String POST_ENTITY_PATH= "/api/entity/{orbisId}/transitionrisk/carbonfootprint/{api}";

    //============ Heat Map =================
    public static String POST_HEAT_MAP = "api/portfolios/{portfolio_id}/heat-map";
    public static String POST_HEAT_MAP_ENTITY_LIST = "api/portfolios/{portfolio_id}/heat-map-entity-list";
    public static String POST_DashBoard_COVERAGE = "api/portfolios/{portfolio_id}/dashboard-coverage";

    public static String POST_CONTROVERSIES = "api/portfolios/{portfolio_id}/controversies";
    public static String POST_EXPORT_CLIMATE_DATA = "api/portfolios/{portfolio_id}/export-climate-data";
    public static String GET_PORTFOLIO_ANALYIS_EXCEL_EXPORT = "/api/portfolio/excel-report";
    public static String GET_PORTFOLIO_ANALYIS_JSON_UPLOAD = "/api/portfolio/upload-json-url?fileName=Carbon%20Footprint_8-Jun-2022_1654699096466.json";

    //============ Portfolio Summary =================
    public static String POST_PORTFOLIO_SUMMARY_COMPANIES = "api/portfolios/{portfolio_id}/portfolio-summary-companies";
    public static String POST_ESG_SCORES = "api/portfolios/{portfolio_id}/corpesgdata/esgasmt/score";

}
