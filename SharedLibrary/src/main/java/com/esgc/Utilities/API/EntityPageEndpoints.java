package com.esgc.Utilities.API;

/**
 * This utility class created to store all Endpoints for API calls
 */

public class EntityPageEndpoints {

    public static String POST_Issuer_Summary = "api/issuer/summary";
    public static String POST_DRIVERS_SUMMARY = "/api/issuer/dual-materiality/drivers-summary";
    public static String POST_DRIVERS_DETAILS = "/api/issuer/dual-materiality/driver-details";
    public static String POST_ENTITY_CONTROVERSIES = "api/issuer/dual-materiality/controversies";
    public static String POST_SECTOR_ALLOCATION = "api/issuer/sector-allocation";
    public static String GET_CONTROVERSIES = "api/portfolios/{portfolio_id}/controversies/{region}/{year}/{month}";
    public static String POST_HEADER = "api/issuer/header";
}
