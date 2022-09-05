package com.esgc.Utilities.API;

/**
 * This utility class created to store all Endpoints for API calls
 */

public class EntityClimateProflePageEndpoints {

    public static String POST_climateSummary = "/api/entity/{orbis_id}/{research_line}/climate-summary";
    public static String POST_climateSectorComparison = "/api/entity/{orbis_id}/{research_line}/climate-sector-comparison";
    public static String POST_climateTempProjection = "/api/entity/{orbis_id}/transitionrisk/temperaturealgmt/climate-temp-projection";

}
