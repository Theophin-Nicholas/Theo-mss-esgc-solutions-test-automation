package com.esgc.RegulatoryReporting.API;

public class RegulatoryReportingEndPoints {
    //=================PORTFOLIO APIs
    public static String GET_PORTFOLIO_DETAILS = "/api/report/portfolio-details";
    public static String GET_DOWNLOAD_HISTORY = "/api/report/download-history";
    public static String GET_DASHBOARD_COVERAGE = "api/portfolio/{portfolioid}}/dashboard-coverage" ;
    public static String GET_ASYNC_GENERATION = "api/report/async-generation";
    public static String GET_STATUS = "api/report/status";
    public static String GET_DOWNLOAD = "api/report/download";
}
