package com.esgc.Utilities.API;

/**
 * This utility class created to store all Endpoints for API calls
 */

public class EntityClimateProfilePageEndpoints {

    public static String POST_climateSummary = "/api/entity/{orbis_id}/{research_line}/climate-summary";
    public static String POST_climateSectorComparison = "/api/entity/{orbis_id}/{research_line}/climate-sector-comparison";
    public static String POST_climateTempProjection = "/api/entity/{orbis_id}/transitionrisk/temperaturealgmt/climate-temp-projection";
    public static String POST_ESGClimateSummary = "/api/entity/{orbis_id}/corpesgdata/esgasmt/climate-summary";
    public static String POST_ExportSourceDocuments = "/api/entity/{entity_id}/corpesgdata/esgasmt/source-documents";
    public static String POST_EntityUnderlyingDataMetrics = "/api/entity/{orbis_id}/physicalrisk/physicalriskmgmt/entity-underlying-data-metrics";

}
