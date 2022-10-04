package com.esgc.Utilities.EndPoints;

/**
 * This utility class created to store all Endpoints for API calls
 */

public class EntityProfilePageEndpoints {


    public static String POST_HEADER = "api/issuer/header";
    public static String POST_ENTITY_PATH= "/api/entity/{orbisId}/transitionrisk/carbonfootprint/{api}";
    public static String POST_CLIMATE_SUMMARY = "/api/entity/{orbis_id}/{research_line}/climate-summary";
    public static String POST_CLIMATE_SECTOR_COMPARISON = "/api/entity/{orbis_id}/{research_line}/climate-sector-comparison";
    public static String POST_CLIMATE_TEMPERATURE_ALIGNMENT_PROJECTION = "/api/entity/{orbis_id}/transitionrisk/temperaturealgmt/climate-temp-projection";
    public static String POST_ESG_CLIMATE_SUMMARY = "/api/entity/{orbis_id}/corpesgdata/esgasmt/climate-summary";
    public static String POST_ENTITY_CONTROVERSIES = "api/issuer/dual-materiality/controversies";
    public static String POST_EXPORT_SOURCE_DOCUMENTS = "/api/entity/{entity_id}/corpesgdata/esgasmt/source-documents";
    public static String POST_ENTITY_UNDERLYING_DATA_METRICS = "/api/entity/{orbis_id}/{research_line}/entity-underlying-data-metrics";
    public static String POST_ENTITY_MATERIALITY_METRICS = "/api/issuer/materiality";
}
