package com.esgc.Utilities.EndPoints;

public class PortfolioAnalysisEndpoints {
    //==================PORTFOLIO ANALYSIS PAGE APIs
    public final static String POST_PORTFOLIO_SCORE = "api/portfolio/{portfolio_id}/{research_line}/score";
    public final static String POST_PORTFOLIO_DISTRIBUTION = "api/portfolio/{portfolio_id}/{research_line}/distribution";
    public final static String POST_PORTFOLIO_COVERAGE = "api/portfolio/{portfolio_id}/{research_line}/coverage";
    public final static String POST_PORTFOLIO_HISTORY_TABLE = "api/portfolio/{portfolio_id}/{research_line}/entity-score-history";
    public final static String POST_PORTFOLIO_UPDATES = "api/portfolio/{portfolio_id}/{research_line}/updates";
    public final static String POST_PORTFOLIO_LEADERS_AND_LAGGARDS = "api/portfolio/{portfolio_id}/{research_line}/leaders-laggards";
    public final static String POST_PORTFOLIO_IMPACT_DISTRIBUTION = "api/portfolio/{portfolio_id}/{research_line}/impact";
    public final static String POST_PORTFOLIO_REGION_MAP = "api/portfolio/{portfolio_id}/{research_line}/region-map";
    public final static String POST_PORTFOLIO_REGION_SUMMARY = "api/portfolio/{portfolio_id}/{research_line}/region-summary";
    public final static String POST_PORTFOLIO_REGION_DETAILS = "api/portfolio/{portfolio_id}/{research_line}/region-details";
    public final static String POST_PORTFOLIO_SECTOR_SUMMARY = "api/portfolio/{portfolio_id}/{research_line}/sector-summary";
    public final static String POST_PORTFOLIO_SECTOR_DETAILS = "api/portfolio/{portfolio_id}/{research_line}/sector-details";
    public final static String POST_PORTFOLIO_UNDERLYING_DATA_METRICS = "api/portfolio/{portfolio_id}/{research_line}/underlying-data-metrics";
    public final static String POST_PHYSICAL_RISK_UNDERLYING_DATA_METRICS_DETAILS = "api/portfolio/{portfolio_id}/{physical_risk}/underlying-data-metrics-details";
    public final static String POST_PORTFOLIO_EMISSIONS = "api/portfolio/{portfolio_id}/{research_line}/emissions";
    public static String GET_PORTFOLIO_ANALYSIS_EXCEL_EXPORT = "/api/portfolio/{portfolio_id}/excel-report";
    public static String GET_PORTFOLIO_ANALYSIS_JSON_UPLOAD = "/api/portfolio/{portfolio_id}/upload-json-url?fileName=Carbon%20Footprint_8-Jun-2022_1654699096466.json";
}
