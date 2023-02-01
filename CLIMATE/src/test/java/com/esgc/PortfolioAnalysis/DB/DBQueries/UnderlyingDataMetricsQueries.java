package com.esgc.PortfolioAnalysis.DB.DBQueries;

import com.esgc.Base.DB.DBModels.IdentifierQueryModel;
import com.esgc.Base.DB.DBModels.IdentifierQueryModelFactory;
import com.esgc.Utilities.Database.DatabaseDriver;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.Database.DatabaseDriver.getQueryResultMap;

public class UnderlyingDataMetricsQueries {

    public List<Map<String, Object>> getBrownShareUnderlyingDataMetrics(String portfolioId, String month, String year) {
        IdentifierQueryModel queryModel =
                IdentifierQueryModelFactory.getIdentifierQueryModel("Brown Share", month, year);
        assert queryModel != null;

        String query = "SELECT DISTINCT \n" +
                "rl.bvd9_number, \n" +
                "BS_FOSF_INDUSTRY_REVENUES_ACCURATE AS \"Fossil fuels industry revenues\", \n" +
                "BS_FOSF_INDUSTRY_UPSTREAM AS \"Fossil fuels industry - Upstream\", \n" +
                "BS_FOSF_INDUSTRY_MIDSTREAM AS \"Fossil fuels industry - Midstream\", \n" +
                "BS_FOSF_INDUSTRY_GENERATION AS \"Fossil fuels industry - Generation\", \n" +
                "BS_FOSF_INDUSTRY_REVENUES_COAL_RESERVES AS \"Fossil fuels reserves - Coal reserves\", \n" +
                "BS_FOSF_INDUSTRY_REVENUES_OIL_RESERVES AS \"Fossil fuels reserves - Oil reserves\", \n" +
                "BS_FOSF_INDUSTRY_REVENUES_NATURAL_GAS_RESERVES AS \"Fossil fuels reserves - Natural Gas reserves\", \n" +
                "BS_FOSF_COAL_MINING AS \"Coal mining\", \n" +
                "BS_FOSF_THERMAL_COAL_MINING AS \"Thermal coal mining\", \n" +
                "BS_FOSF_COAL_FUELLED_POWER_GEN AS \"Coal-fuelled power generation\", \n" +
                "BS_FOSF_TAR_SAND_N_OIL_SHALE_EXTRACTION AS \"Tar sands and oil shale extraction\", \n" +
                "BS_FOSF_OFFSHORE_ARCTIC_DRILLING AS \"Offshore arctic drilling\", \n" +
                "BS_FOSF_ULTRA_DEEP_OFFSHORE AS \"Ultra-deep offshore\", \n" +
                "BS_FOSF_COAL_BED_METHANE AS \"Coal-bed methane (coal seam gas)\", \n" +
                "BS_FOSF_METHANE_HYDRATES AS \"Methane hydrates\", \n" +
                "BS_FOSF_HYDRAULIC_FRACTURING AS \"Hydraulic fracturing\", \n" +
                "BS_FOSF_LIQUEFIED_NATURAL_GAS AS \"Liquefied Natural Gas (LNG)\", \n" +
                "BS_PEM_TOTAL_POTENTIAL_EMISSIONS AS \"Total Potential Emissions\", \n" +
                "BS_PEM_TOTAL_COAL_POTENTIAL_EMISSIONS AS \"Total Coal Potential Emissions\", \n" +
                "BS_PEM_TOTAL_OIL_POTENTIAL_EMISSIONS AS \"Total Oil Potential Emissions\", \n" +
                "BS_PEM_TOTAL_NATURAL_GAS_POTENTIAL_EMISSIONS AS \"Total Natural Gas Potential Emissions\" \n" +
                "FROM df_portfolio b \n" +
                "JOIN BROWN_SHARE rl ON b.bvd9_number = rl.bvd9_number \n" +
                "where b.portfolio_id in ('" + portfolioId + "') \n" +
                "and rl.month = " + month + " and rl.year = " + year;
        System.out.println(query);
        return getQueryResultMap(query);
    }

    public List<Map<String, Object>> getCarbonFootprintUnderlyingDataMetrics(String portfolioId, String month, String year) {
        IdentifierQueryModel queryModel =
                IdentifierQueryModelFactory.getIdentifierQueryModel("Carbon Footprint", month, year);
        assert queryModel != null;

        String query = "SELECT DISTINCT \n" +
                "rl.bvd9_number, \n" +
                "rl.CARBON_FOOTPRINT_VALUE_SCOPE_1 AS \"Scope 1 (t CO2 eq)\" , \n" +
                "rl.CARBON_FOOTPRINT_VALUE_SCOPE_2 AS \"Scope 2 (t CO2 eq)\", \n" +
                "rl.CARBON_FOOTPRINT_VALUE_SCOPE_3 AS \"Scope 3 (t CO2 eq)\" \n" +
                "FROM df_portfolio b \n" +
                "JOIN CARBON_FOOTPRINT rl ON b.bvd9_number = rl.bvd9_number \n" +
                "where b.portfolio_id in ('" + portfolioId + "') \n" +
                "and rl.month = " + month + " and rl.year = " + year;
        System.out.println(query);
        return getQueryResultMap(query);
    }

    public List<Map<String, Object>> getOperationsRiskUnderlyingDataMetrics(String portfolioId, String month, String year, String region, String sector) {
        String regionFilter = region.equals("all") ? "" : "AND p.REGION = '" + region + "'";
        String sectorFilter = sector.equals("all") ? "" : "AND p.SECTOR = '" + sector + "'";

        String query =
                "WITH p\n" +
                        "     AS (SELECT bvd9_number,\n" +
                        "                portfolio_id,\n" +
                        "                --max(CONCAT(release_year,release_month)) as MaxDate,\n" +
                        "                sector,\n" +
                        "                country_code,\n" +
                        "                region\n" +
                        "         FROM   df_portfolio\n" +
                        "                INNER JOIN entity_score R1\n" +
                        "                        ON bvd9_number = entity_id_bvd9\n" +
                        "         WHERE  portfolio_id = '" + portfolioId + "'\n" +
                        "                AND r1.release_year\n" +
                        "                    || r1.release_month <= '" + year + month + "'\n" +
                        "         GROUP  BY bvd9_number,\n" +
                        "                   portfolio_id,\n" +
                        "                   value,\n" +
                        "                   sector,\n" +
                        "                   country_code,\n" +
                        "                   region),\n" +
                        "     q\n" +
                        "     AS (SELECT pt.bvd9_number,\n" +
                        "                Max(Concat(release_year, release_month)) AS MaxDate\n" +
                        "         FROM   entity_score s\n" +
                        "                JOIN df_portfolio pt\n" +
                        "                  ON pt.bvd9_number = s.entity_id_bvd9\n" +
                        "         WHERE  s.release_year\n" +
                        "                || s.release_month <= '" + year + month + "'\n" +
                        "                AND pt.portfolio_id = '" + portfolioId + "'\n" +
                        "         GROUP  BY pt.bvd9_number),\n" +
                        "     main_table\n" +
                        "     AS (SELECT portfolio_id,\n" +
                        "                NAME,\n" +
                        "                category,\n" +
                        "                risk_threshold_id,\n" +
                        "                Sum(fac_count) FAC_COUNT\n" +
                        "         FROM   (SELECT p.portfolio_id,\n" +
                        "                        risk_category_name               NAME,\n" +
                        "                        risk_threshold_id,\n" +
                        "                        CASE\n" +
                        "                          WHEN s.risk_threshold_id = 0 THEN 'No Risk'\n" +
                        "                          WHEN s.risk_threshold_id = 1 THEN 'Low Risk'\n" +
                        "                          WHEN s.risk_threshold_id = 2 THEN 'Medium Risk'\n" +
                        "                          WHEN s.risk_threshold_id = 3 THEN 'High Risk'\n" +
                        "                          WHEN s.risk_threshold_id = 4 THEN 'Red Flag Risk'\n" +
                        "                        END                              Category,\n" +
                        "                        Sum(s.number_facilities_exposed) AS FAC_COUNT\n" +
                        "                 FROM   p\n" +
                        "                        JOIN df_target.entity_score r1\n" +
                        "                          ON p.bvd9_number = r1.entity_id_bvd9\n" +
                        "                        JOIN df_target.facility_score_country_sector_thresholds\n" +
                        "                             s\n" +
                        "                          ON s.entity_id_bvd9 = p.bvd9_number\n" +
                        "                        JOIN df_target.risk_category r2\n" +
                        "                          ON r2.risk_category_id = s.risk_category_id\n" +
                        "                        JOIN q\n" +
                        "                          ON q.bvd9_number = p.bvd9_number\n" +
                        "                 WHERE  p.portfolio_id = '" + portfolioId + "'\n" +
                        "               " + sectorFilter + "               \n" +
                        "               " + regionFilter + "               \n" +

                        "                        AND r1.release_year\n" +
                        "                            || r1.release_month = q.maxdate\n" +
                        "                        AND r1.release_year\n" +
                        "                            || r1.release_month = s.release_year\n" +
                        "                                                  || s.release_month\n" +
                        "                        AND r2.risk_category_id IN ( 1, 2, 4, 10,\n" +
                        "                                                     12, 14, 16 )\n" +
                        "                 GROUP  BY p.portfolio_id,\n" +
                        "                           risk_threshold_id,\n" +
                        "                           risk_category_name)\n" +
                        "         GROUP  BY portfolio_id,\n" +
                        "                   NAME,\n" +
                        "                   category,\n" +
                        "                   risk_threshold_id),\n" +
                        "     sum_table\n" +
                        "     AS (SELECT portfolio_id,\n" +
                        "                NAME,\n" +
                        "                Sum(fac_count) FAC_SUM\n" +
                        "         FROM   main_table\n" +
                        "         GROUP  BY portfolio_id,\n" +
                        "                   NAME),\n" +
                        "     r\n" +
                        "     AS (SELECT m.NAME                        \"RISKHAZARD\",\n" +
                        "                m.category                    \"RISKCATEGORY\",\n" +
                        "                risk_threshold_id,\n" +
                        "                m.fac_count / S.fac_sum * 100 \"PERCENTAGE\"\n" +
                        "         FROM   main_table M\n" +
                        "                JOIN sum_table S\n" +
                        "                  ON S.portfolio_id = M.portfolio_id\n" +
                        "                     AND s.NAME = M.NAME)\n" +
                        "SELECT *\n" +
                        "FROM   r ";
//                "SELECT portfolio_id,\n" +
//                        "       NAME,\n" +
//                        "       category,\n" +
//                        "       risk_threshold_id,\n" +
//                        "       Sum(fac_count) FAC_COUNT\n" +
//                        "FROM   (SELECT df.portfolio_id,\n" +
//                        "               risk_category_name               NAME,\n" +
//                        "               risk_threshold_id,\n" +
//                        "               CASE\n" +
//                        "                 WHEN s.risk_threshold_id = 0 THEN 'No Risk'\n" +
//                        "                 WHEN s.risk_threshold_id = 1 THEN 'Low Risk'\n" +
//                        "                 WHEN s.risk_threshold_id = 2 THEN 'Medium Risk'\n" +
//                        "                 WHEN s.risk_threshold_id = 3 THEN 'High Risk'\n" +
//                        "                 WHEN s.risk_threshold_id = 4 THEN 'Red Flag Risk'\n" +
//                        "               END                              Category,\n" +
//                        "               Sum(s.number_facilities_exposed) AS FAC_COUNT\n" +
//                        "        FROM   df_portfolio df\n" +
//                        "JOIN(SELECT \n" +
//                        "    bvd9_number, \n" +
//                        "    portfolio_id, \n" +
//                        "    --max(CONCAT(release_year,release_month)) as MaxDate,\n" +
//                        "    sector, \n" +
//                        "    country_code, \n" +
//                        "    region_name \n" +
//                        "  FROM \n" +
//                        "    df_portfolio \n" +
//                        "    inner join entity_score R1 on bvd9_number = ENTITY_ID_BVD9 \n" +
//                        "  WHERE \n" +
//                        "    portfolio_id = '" + portfolioId + "' -- region_filter + sector_filter + `\n" +
//                        "    AND r1.release_year || r1.release_month <= '" + year + month + "' \n" +
//                        "  GROUP BY \n" +
//                        "    bvd9_number, \n" +
//                        "    portfolio_id, \n" +
//                        "    value, \n" +
//                        "    sector, \n" +
//                        "    country_code, \n" +
//                        "    region_name) ON 1=1" +
//                        "               JOIN df_target.entity_score r1\n" +
//                        "                 ON df.bvd9_number = r1.entity_id_bvd9\n" +
//                        "                    AND r1.release_year = " + year + "\n" +
//                        "                    AND r1.release_month = 12\n" +
//                        "               JOIN df_target.facility_score_country_sector_thresholds s\n" +
//                        "                 ON s.entity_id_bvd9 = df.bvd9_number\n" +
//                        "               JOIN df_target.risk_category r2\n" +
//                        "                 ON r2.risk_category_id = s.risk_category_id\n" +
//                        "        WHERE  df.portfolio_id = '" + portfolioId + "'\n" +
//                        "               " + sectorFilter + "               \n" +
//                        "               " + regionFilter + "               \n" +
//                        "               AND r2.risk_category_id IN ( 1, 2, 4,\n" +
//                        "                                             12, 14, 16 )\n" +
//                        "        GROUP  BY df.portfolio_id,\n" +
//                        "                  df.bvd9_number,\n" +
//                        "                  risk_threshold_id,\n" +
//                        "                  risk_category_name)\n" +
//                        "GROUP  BY portfolio_id,\n" +
//                        "          NAME,\n" +
//                        "          category,\n" +
//                        "          risk_threshold_id; ";

        System.out.println(query);
        return getQueryResultMap(query);
    }

    public List<Map<String, Object>> getMarketRiskUnderlyingDataMetrics(String portfolioId, String month, String year) {
        IdentifierQueryModel queryModel =
                IdentifierQueryModelFactory.getIdentifierQueryModel("marketrisk", month, year);
        assert queryModel != null;

        String query = "SELECT df.portfolio_id,\n" +
                "       df.bvd9_number,\n" +
                "       risk_category_name NAME,\n" +
                "       entity_category_score\n" +
                "FROM   df_portfolio df\n" +
                "       JOIN df_target.entity_score r1\n" +
                "         ON df.bvd9_number = r1.entity_id_bvd9\n" +
                "            AND r1.release_year = " + year + "\n" +
                "            AND r1.release_month = 12\n" +
                "       JOIN df_target.entity_score_category r2\n" +
                "         ON r1.entity_id_bvd9 = r2.entity_id_bvd9\n" +
                "       JOIN df_target.risk_category r3\n" +
                "         ON r2.risk_category_id = r3.risk_category_id\n" +
                "WHERE  df.portfolio_id = '" + portfolioId + "'\n" +
                "       AND r3.risk_category_id IN ( 8, 9 )\n" +
                "GROUP  BY df.portfolio_id,\n" +
                "          df.bvd9_number,\n" +
                "          risk_category_name,\n" +
                "          entity_category_score ";
        System.out.println(query);
        return getQueryResultMap(query);
    }

    public List<Map<String, Object>> getSupplyChainRiskUnderlyingDataMetrics(String portfolioId, String month, String year) {
        IdentifierQueryModel queryModel =
                IdentifierQueryModelFactory.getIdentifierQueryModel("supplychainrisk", month, year);
        assert queryModel != null;

        String query = "SELECT df.portfolio_id,\n" +
                "       df.bvd9_number,\n" +
                "       risk_category_name NAME,\n" +
                "       entity_category_score\n" +
                "FROM   df_portfolio df\n" +
                "       JOIN df_target.entity_score r1\n" +
                "         ON df.bvd9_number = r1.entity_id_bvd9\n" +
                "            AND r1.release_year = " + year + "\n" +
                "            AND r1.release_month = 12\n" +
                "       JOIN df_target.entity_score_category r2\n" +
                "         ON r1.entity_id_bvd9 = r2.entity_id_bvd9\n" +
                "       JOIN df_target.risk_category r3\n" +
                "         ON r2.risk_category_id = r3.risk_category_id\n" +
                "WHERE  df.portfolio_id = '" + portfolioId + "'\n" +
                "       AND r3.risk_category_id IN ( 6, 7 )\n" +
                "GROUP  BY df.portfolio_id,\n" +
                "          df.bvd9_number,\n" +
                "          risk_category_name,\n" +
                "          entity_category_score ";
        System.out.println(query);
        return getQueryResultMap(query);
    }

    public List<Map<String, Object>> getTCFDUnderlyingDataMetrics(String portfolioId, String month, String year) {
        IdentifierQueryModel queryModel =
                IdentifierQueryModelFactory.getIdentifierQueryModel("TCFD", month, year);
        assert queryModel != null;

        String query = "SELECT DISTINCT \n" +
                "rl.bvd9_number, \n" +
                "CLIMATE_CHANGE_FACTORED_FINANCIAL_PLANNING, \n" +
                "CLIMATE_CHG_ANALYSIS_IMPACT_COMPANY_BUSINESS, \n" +
                "DEV_LOW_CARBON_TRANS_PLAN_LONG_TERM_BUSINESS_STRATEGY, \n" +
                "DEV_PRODUCTS_SERVICES_CONTRIBUTE_TRANS_LOW_CARBON_ECONOMY, \n" +
                "ENGAGEMENT_WITH_COMPANIES_VALUE_CHAIN, \n" +
                "INTEGRATION_INTO_OVERALL_ENTERPRISE_RISK_MANAGEMENT, \n" +
                "PROCESS_INFO_BOARD_COMMITTEE_CLIMATE_CHANGE, \n" +
                "PROCESS_INFO_CEO_CFO_COO_CLIMATE, \n" +
                "RISK_MAP_MATERIAL_ASSESMENT_BALANCE_SCORE_CARD, \n" +
                "TCFD_REPORT_COMPLIANCE, \n" +
                "TCFD_SIGNATORY, \n" +
                "USE_INTERNAL_CARBON_PRICE \n" +
                "FROM TCFD_STRATEGY rl \n" +
                "LEFT JOIN df_portfolio b ON rl.bvd9_number = b.bvd9_number \n" +
                "where b.portfolio_id in ('" + portfolioId + "') \n" +
                "and rl.MONTH = '" + month + "' and rl.YEAR = '" + year + "'";
        System.out.println(query);
        return getQueryResultMap(query);
    }

    public List<Map<String, Object>> getGreenShareUnderlyingDataMetrics(String portfolioId, String month, String year) {
        IdentifierQueryModel queryModel =
                IdentifierQueryModelFactory.getIdentifierQueryModel("Green Share", month, year);
        assert queryModel != null;

        String query = "SELECT DISTINCT \n" +
                "rl.bvd9_number, \n" +
                //"GS_AFFORESTATION_ESTIMATE_OF_INCORPORATION,\n" +
                //"GS_AFFORESTATION_SCALE_OF_INCORPORATION,\n" +
                "GS_BUILDING_MATERIALS_WATER_EFFICIENCY_ESTIMATE_OF_INCORPORATION AS \"Building materials allowing water efficiency\",\n" +

                "GS_BUILDING_MATERIALS_WOOD_ESTIMATE_OF_INCORPORATION AS \"Building materials from wood\",\n" +

                "GS_ELECTRIC_ENGINE_ESTIMATE_OF_INCORPORATION AS \"Electric engine\",\n" +
                //"GS_ELECTRIC_ENGINE_SCALE_OF_INCORPORATION,\n" +
                "GS_ELECTRIC_VEHICLES_ESTIMATE_OF_INCORPORATION AS \"Electric vehicles\",\n" +
                //"GS_ELECTRIC_VEHICLES_SCALE_OF_INCORPORATION,\n" +
                "GS_ENERGY_DEMAND_SIDE_ESTIMATE_OF_INCORPORATION AS \"Energy demand-side management\",\n" +
                //"GS_ENERGY_DEMAND_SIDE_SCALE_OF_INCORPORATION,\n" +
                "GS_ENERGY_STORAGE_ESTIMATE_OF_INCORPORATION AS \"Energy storage\",\n" +
                //"GS_ENERGY_STORAGE_SCALE_OF_INCORPORATION,\n" +
                "GS_GREEN_BUILDINGS_ESTIMATE_OF_INCORPORATION AS \"Green buildings\",\n" +
                //"GS_GREEN_BUILDINGS_SCALE_OF_INCORPORATION,\n" +
                "GS_GREEN_LENDING_ESTIMATE_OF_INCORPORATION AS \"Green lending\",\n" +
                //"GS_GREEN_LENDING_SCALE_OF_INCORPORATION,\n" +
                "GS_HYBRID_ENGINE_ESTIMATE_OF_INCORPORATION AS \"Hybrid engine\",\n" +
                //"GS_HYBRID_ENGINE_SCALE_OF_INCORPORATION,\n" +
                "GS_HYBRID_VEHICLES_ESTIMATE_OF_INCORPORATION AS \"Hybrid vehicles\",\n" +
                //"GS_HYBRID_VEHICLES_SCALE_OF_INCORPORATION,\n" +
                //"GS_OVERALL_ASSESSMENT_ESTIMATE_OF_INCORPORATION,\n" +
                //"GS_OVERALL_ASSESSMENT_ESTIMATE_OF_INCORPORATION_SOURCE,\n" +
                //"GS_OVERALL_ASSESSMENT_SCALE_OF_INCORPORATION,\n" +
                "GS_RENEWABLE_ENERGY_ESTIMATE_OF_INCORPORATION AS \"Renewable energy\"\n" +
                //"GS_RENEWABLE_ENERGY_SCALE_OF_INCORPORATION\n" +
                "FROM GREEN_SHARE rl \n" +
                "LEFT JOIN df_portfolio b ON rl.bvd9_number = b.bvd9_number \n" +
                "where b.portfolio_id in ('" + portfolioId + "') \n" +
                "and rl.MONTH = '" + month + "' and rl.YEAR = '" + year + "'";
        System.out.println(query);
        return getQueryResultMap(query);
    }

    public List<Map<String, Object>> getEnergyTransitionManagementUnderlyingDataMetrics(String portfolioId, String month, String year) {
        IdentifierQueryModel queryModel =
                IdentifierQueryModelFactory.getIdentifierQueryModel("Energy Transition Management", month, year);
        assert queryModel != null;

        String query = "SELECT DISTINCT \n" +
                "rl.bvd9_number, \n" +
                "GS_ENERGY_TRANSITION_ATMOSPHERIC_EMISSIONS AS \"Atmospheric Emissions\",\n" +
                "GS_ENERGY_TRANSITION_ENERGY AS \"Energy\",\n" +
                "GS_ENERGY_TRANSITION_GREEN_PRODUCTS AS \"Green Products\",\n" +
                "GS_ENERGY_TRANSITION_SOCIETAL_IMPACTS_OF_PRODUCTS_SERVICES AS  \"Societal Impacts of Products/Services\",\n" +
                "GS_ENERGY_TRANSITION_TRANSPORTATION AS \"Transportation\",\n" +
                "GS_ENERGY_TRANSITION_USE_AND_DISPOSAL_OF_PRODUCTS AS \"Use and Disposal of Products\" \n" +
                "FROM ENERGY_TRANSITION rl \n" +
                "LEFT JOIN df_portfolio b ON rl.bvd9_number = b.bvd9_number \n" +
                "where b.portfolio_id in ('" + portfolioId + "') \n" +
                "and rl.MONTH = '" + month + "' and rl.YEAR = '" + year + "'";

        System.out.println(query);
        return getQueryResultMap(query);
    }


    @Test
    public void testUDMQueries() {
        DatabaseDriver.createDBConnection();

        getCarbonFootprintUnderlyingDataMetrics(
                "00000000-0000-0000-0000-000000000000",
                "04", "2021").forEach(System.out::println);

    }

    public List<Map<String, Object>> getMarketRiskUnderlyingDataMetricsDetails(String portfolioId, String month, String year) {

        String query = "select DISTINCT(p.COMPANY_NAME) from df_target.DF_PORTFOLIO p\n" +
                "JOIN df_target.entity_score s\n" +
                "ON p.bvd9_number =s.entity_id_bvd9\n" +
                "JOIN df_target.entity_score_category esc\n" +
                "ON s.entity_id_bvd9 = esc.entity_id_bvd9\n" +
                "JOIN df_target.risk_category rc\n" +
                "ON esc.risk_category_id = rc.risk_category_id                                \n" +
                "WHERE  s.release_year || s.release_month = "+year+""+month+"\n" +
                " AND s.release_year || s.release_month = esc.release_year || esc.release_month\n" +
                "AND p.bvd9_number IS NOT NULL\n" +
                "and rc.risk_category_id in (8,9)\n" +
                "and p.portfolio_id = '"+portfolioId+"'\n" +
                "ORDER BY p.company_name;";
        System.out.println(query);
        return getQueryResultMap(query);
    }

    public List<Map<String, Object>> getSupplyChainRiskUnderlyingDataMetricsDetails(String portfolioId, String month, String year) {

        String query = "select DISTINCT(p.COMPANY_NAME) from df_target.DF_PORTFOLIO p\n" +
                "JOIN df_target.entity_score s\n" +
                "ON p.bvd9_number =s.entity_id_bvd9\n" +
                "JOIN df_target.entity_score_category esc\n" +
                "ON s.entity_id_bvd9 = esc.entity_id_bvd9\n" +
                "JOIN df_target.risk_category rc\n" +
                "ON esc.risk_category_id = rc.risk_category_id                                \n" +
                "WHERE  s.release_year || s.release_month = "+year+""+month+"\n" +
                " AND s.release_year || s.release_month = esc.release_year || esc.release_month\n" +
                "AND p.bvd9_number IS NOT NULL\n" +
                "and rc.risk_category_id in (6,7)\n" +
                "and p.portfolio_id = '"+portfolioId+"'\n" +
                "ORDER BY p.company_name;";

        System.out.println(query);
        return getQueryResultMap(query);
    }
}
