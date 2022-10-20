package com.esgc.Utilities.Database;

import com.esgc.APIModels.EntityPage.EntityControversy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.Database.DatabaseDriver.getQueryResultMap;


public class EntityPageQueries {


    public static List<Map<String, Object>> getEsgMaterialityCategories(String orbisId){
        String query = "\n" +
                "WITH ESG AS (\n" +
                "             SELECT SCORE_TABLE.ORBIS_ID\n" +
                "                     ,SCORE_TABLE.RESEARCH_LINE_ID\n" +
                "                     ,SCORE_TABLE.SUB_CATEGORY\n" +
                "                     ,SCORE_TABLE.DATA_TYPE\n" +
                "                     ,SCORE_TABLE.VALUE\n" +
                "             FROM DF_TARGET.ESG_OVERALL_SCORES SCORE_TABLE\n" +
                "             INNER JOIN (\n" +
                "                     SELECT DISTINCT ORBIS_ID\n" +
                "                             ,RESEARCH_LINE_ID\n" +
                "                     FROM DF_TARGET.ENTITY_COVERAGE_TRACKING\n" +
                "                     WHERE COVERAGE_STATUS = 'Published'\n" +
                "                             AND PUBLISH = 'yes'\n" +
                "                             AND RESEARCH_LINE_ID IN (\n" +
                "                                     '1008'\n" +
                "                                     ,'1015'\n" +
                "                                     )\n" +
                "                             AND ORBIS_ID = '"+orbisId+"'    -- Orbis ID filter here alone\n" +
                "                     ) RESEARCH_LINE_ASSOC ON RESEARCH_LINE_ASSOC.ORBIS_ID = SCORE_TABLE.ORBIS_ID\n" +
                "                     AND RESEARCH_LINE_ASSOC.RESEARCH_LINE_ID = SCORE_TABLE.RESEARCH_LINE_ID\n" +
                "             WHERE DATA_TYPE IN (\n" +
                "                             'overall_subcategory_score'\n" +
                "                             ,'information_rate_criteria'\n" +
                "                             ) QUALIFY ROW_NUMBER() OVER (\n" +
                "                             PARTITION BY SCORE_TABLE.ORBIS_ID\n" +
                "                             ,SUB_CATEGORY\n" +
                "                             ,DATA_TYPE ORDER BY SCORED_DATE DESC\n" +
                "                             ) = 1\n" +
                "             )\n" +
                "     ,ESG_WITH_SECTOR_ID AS (\n" +
                "             SELECT ESG.ORBIS_ID\n" +
                "                     ,ESG.RESEARCH_LINE_ID\n" +
                "                     ,ESG.DATA_TYPE\n" +
                "                     ,ESG.VALUE\n" +
                "                     ,ESG.SUB_CATEGORY AS CRITERIA\n" +
                "                     ,ENTITY_MASTER_SECTOR_ID.MESG_SECTOR_ID AS SECTOR_ID\n" +
                "             FROM ESG\n" +
                "             INNER JOIN (\n" +
                "                     SELECT DISTINCT ORBIS_ID\n" +
                "                             ,MESG_SECTOR_ID\n" +
                "                             ,ENTITY_STATUS\n" +
                "                     FROM DF_TARGET.ESG_ENTITY_MASTER\n" +
                "                     ) ENTITY_MASTER_SECTOR_ID ON ENTITY_MASTER_SECTOR_ID.ORBIS_ID = ESG.ORBIS_ID\n" +
                "                     AND NVL(ENTITY_STATUS, 'Active') = 'Active'\n" +
                "             )\n" +
                "     ,MESG_SCORED_CRITERIA_WEIGHTS_PIVOTED AS (\n" +
                "             SELECT PIVOTED_DATA_FULL.SECTOR_ID\n" +
                "                     ,PIVOTED_DATA_FULL.INTERNAL_SUB_CATEGORY_ID\n" +
                "                     ,PIVOTED_DATA_FULL.\"'criteria_weight'\" AS DUAL_MATERIALITY_WEIGHT\n" +
                "                     ,PIVOTED_DATA_FULL.\"'Business Materiality'\" AS BUSINESS_MATERIALITY_WEIGHT\n" +
                "                     ,PIVOTED_DATA_FULL.\"'Stakeholder Materiality'\" AS STAKEHOLDER_MATERIALITY_WEIGHT\n" +
                "             FROM (\n" +
                "                     SELECT SECTOR_ID\n" +
                "                             ,INTERNAL_SUB_CATEGORY_ID\n" +
                "                             ,DATA_TYPE\n" +
                "                             ,DATA_VALUE\n" +
                "                     FROM DF_TARGET.SECTOR_METHODOLOGY_WEIGHTS QUALIFY ROW_NUMBER() OVER (\n" +
                "                                     PARTITION BY SECTOR_ID\n" +
                "                                     ,INTERNAL_SUB_CATEGORY_ID\n" +
                "                                     ,DATA_TYPE\n" +
                "                                     ,DATA_VALUE ORDER BY AS_OF_DATE DESC\n" +
                "                                     ) = 1\n" +
                "                     ) SMW\n" +
                "             PIVOT(MAX(DATA_VALUE) FOR DATA_TYPE IN (\n" +
                "                                     'Business Materiality'\n" +
                "                                     ,'Stakeholder Materiality'\n" +
                "                                     ,'criteria_weight'\n" +
                "                                     )) AS PIVOTED_DATA_FULL\n" +
                "             INNER JOIN ESG_WITH_SECTOR_ID ON PIVOTED_DATA_FULL.SECTOR_ID = ESG_WITH_SECTOR_ID.SECTOR_ID\n" +
                "                     AND PIVOTED_DATA_FULL.INTERNAL_SUB_CATEGORY_ID = ESG_WITH_SECTOR_ID.CRITERIA\n" +
                "                     AND ESG_WITH_SECTOR_ID.RESEARCH_LINE_ID = '1015'\n" +
                "             )\n" +
                "     ,VE_SCORED_CRITERIA_WEIGHTS AS (\n" +
                "             SELECT ESG_WITH_SECTOR_ID.ORBIS_ID\n" +
                "                     ,ESG_WITH_SECTOR_ID.CRITERIA\n" +
                "                     ,VE_SCORED_CRITERIA_WEIGHTS_FULL.CRITERIA_WEIGHT\n" +
                "             FROM (\n" +
                "                     SELECT DISTINCT RESEARCH_LINE_ID\n" +
                "                             ,ORBIS_ID\n" +
                "                             ,SUB_CATEGORY\n" +
                "                             ,VALUE AS CRITERIA_WEIGHT\n" +
                "                     FROM DF_TARGET.ESG_OVERALL_SCORES\n" +
                "                     WHERE DATA_TYPE IN ('overall_subcategory_weight') QUALIFY ROW_NUMBER() OVER (\n" +
                "                                     PARTITION BY ORBIS_ID\n" +
                "                                     ,SUB_CATEGORY\n" +
                "                                     ,DATA_TYPE ORDER BY SCORED_DATE DESC\n" +
                "                                     ) = 1\n" +
                "                     ) AS VE_SCORED_CRITERIA_WEIGHTS_FULL\n" +
                "             INNER JOIN ESG_WITH_SECTOR_ID ON VE_SCORED_CRITERIA_WEIGHTS_FULL.ORBIS_ID = ESG_WITH_SECTOR_ID.ORBIS_ID\n" +
                "                     AND VE_SCORED_CRITERIA_WEIGHTS_FULL.SUB_CATEGORY = ESG_WITH_SECTOR_ID.CRITERIA\n" +
                "                     AND VE_SCORED_CRITERIA_WEIGHTS_FULL.RESEARCH_LINE_ID = ESG_WITH_SECTOR_ID.RESEARCH_LINE_ID\n" +
                "                     AND ESG_WITH_SECTOR_ID.RESEARCH_LINE_ID = '1008'\n" +
                "             )\n" +
                "     ,CRITICAL_CONTROVERSY_DETAILS AS (\n" +
                "             SELECT DISTINCT ESG_WITH_SECTOR_ID.ORBIS_ID AS ORBIS_ID\n" +
                "                     ,ESG_WITH_SECTOR_ID.CRITERIA AS CRITERIA\n" +
                "                     ,true AS CRITICAL_CONTROVERSY_EXISTS_FLAG\n" +
                "             FROM ESG_WITH_SECTOR_ID\n" +
                "             INNER JOIN DF_TARGET.CONTROVERSY_DETAILS CONTVRSY_DTLS ON CONTVRSY_DTLS.ORBIS_ID = ESG_WITH_SECTOR_ID.ORBIS_ID\n" +
                "                     AND CONTAINS (\n" +
                "                             CONTVRSY_DTLS.RELATED_SUSTAINABILITY_DRIVER_REF\n" +
                "                             ,ESG_WITH_SECTOR_ID.CRITERIA\n" +
                "                             )\n" +
                "                     AND SEVERITY = 'Critical'\n" +
                "                     AND CONTROVERSY_STATUS = 'Active'\n" +
                "                     AND CONTROVERSY_STEPS = 'last'\n" +
                "                     AND CONTROVERSY_EVENTS > DATEADD( 'month', -48, CURRENT_DATE)::DATE\n" +
                "             )\n" +
                "     ,ESG_WITH_SECTOR_WEIGHT_DETAILS AS (\n" +
                "             SELECT ESG_WITH_SECTOR_ID.ORBIS_ID\n" +
                "                     ,ESG_WITH_SECTOR_ID.RESEARCH_LINE_ID\n" +
                "                     ,ESG_WITH_SECTOR_ID.DATA_TYPE\n" +
                "                     ,ESG_WITH_SECTOR_ID.VALUE\n" +
                "                     ,ESG_WITH_SECTOR_ID.CRITERIA\n" +
                "                     ,CASE\n" +
                "                             WHEN ESG_WITH_SECTOR_ID.RESEARCH_LINE_ID = '1015'\n" +
                "                                     THEN MESG_SCORED_CRITERIA_WEIGHTS_PIVOTED.DUAL_MATERIALITY_WEIGHT\n" +
                "                             WHEN ESG_WITH_SECTOR_ID.RESEARCH_LINE_ID = '1008'\n" +
                "                                     THEN VE_SCORED_CRITERIA_WEIGHTS.criteria_weight\n" +
                "                             END AS DUAL_MATERIALITY_WEIGHT\n" +
                "                     ,IFF(ESG_WITH_SECTOR_ID.RESEARCH_LINE_ID = '1015', MESG_SCORED_CRITERIA_WEIGHTS_PIVOTED.BUSINESS_MATERIALITY_WEIGHT, NULL) AS BUSINESS_MATERIALITY_WEIGHT\n" +
                "                     ,IFF(ESG_WITH_SECTOR_ID.RESEARCH_LINE_ID = '1015', MESG_SCORED_CRITERIA_WEIGHTS_PIVOTED.STAKEHOLDER_MATERIALITY_WEIGHT, NULL) AS STAKEHOLDER_MATERIALITY_WEIGHT\n" +
                "                     ,ESG_WITH_SECTOR_ID.SECTOR_ID\n" +
                "                     ,IFF(CRITICAL_CONTROVERSY_DETAILS.CRITICAL_CONTROVERSY_EXISTS_FLAG IS NULL, false, CRITICAL_CONTROVERSY_DETAILS.CRITICAL_CONTROVERSY_EXISTS_FLAG) AS CRITICAL_CONTROVERSY_EXISTS_FLAG\n" +
                "             FROM ESG_WITH_SECTOR_ID\n" +
                "             LEFT JOIN VE_SCORED_CRITERIA_WEIGHTS ON VE_SCORED_CRITERIA_WEIGHTS.CRITERIA = ESG_WITH_SECTOR_ID.CRITERIA\n" +
                "             LEFT JOIN MESG_SCORED_CRITERIA_WEIGHTS_PIVOTED ON MESG_SCORED_CRITERIA_WEIGHTS_PIVOTED.INTERNAL_SUB_CATEGORY_ID = ESG_WITH_SECTOR_ID.CRITERIA\n" +
                "             LEFT JOIN CRITICAL_CONTROVERSY_DETAILS ON CRITICAL_CONTROVERSY_DETAILS.CRITERIA = ESG_WITH_SECTOR_ID.CRITERIA\n" +
                "             WHERE MESG_SCORED_CRITERIA_WEIGHTS_PIVOTED.DUAL_MATERIALITY_WEIGHT IS NOT NULL\n" +
                "                     OR VE_SCORED_CRITERIA_WEIGHTS.CRITERIA_WEIGHT IS NOT NULL\n" +
                "             )\n" +
                "     ,METRIC AS (\n" +
                "             SELECT DISTINCT MSC.RESEARCH_LINE_ID\n" +
                "                    ,MSC.INTERNAL_SUB_CATEGORY_CODE AS CRITERIA_CODE\n" +
                "                    ,MSC.SUB_CATEGORY_DESCRIPTION AS CRITERIA_NAME\n" +
                "                    ,CASE WHEN MSC.COMPONENT = 'E' THEN 'Environmental'\n" +
                "                    WHEN MSC.COMPONENT = 'S' THEN 'Social'\n" +
                "                    WHEN MSC.COMPONENT = 'G' THEN 'Governance' END AS INDICATOR\n" +
                "                    ,MSC.SUB_CATEGORY_DETAILED_DESCRIPTION\n" +
                "                    ,EMFS.DOMAIN\n" +
                "            FROM DF_LOOKUP.MESG_SUB_CATEGORY MSC\n" +
                "            LEFT JOIN DF_TARGET.ESG_METRIC_FRAMEWORK_DETAIL EMFD ON EMFD.category = MSC.INTERNAL_SUB_CATEGORY_CODE\n" +
                "            AND EMFD.AS_OF_DATE = (\n" +
                "                    SELECT MAX(AS_OF_DATE)\n" +
                "                    FROM DF_TARGET.ESG_METRIC_FRAMEWORK_DETAIL\n" +
                "                    )\n" +
                "            AND EMFD.DATA_SOURCE = 'data_capture'\n" +
                "            AND EMFD.ITEM_TYPE = 'Input (raw)'\n" +
                "            AND EMFD.STATUS = 'Active'\n" +
                "            LEFT JOIN DF_TARGET.ESG_METRIC_FRAMEWORK_SUMMARY EMFS ON EMFD.TAXONOMY_ID = EMFS.TAXONOMY_ID\n" +
                "            )\n" +
                "SELECT CASE\n" +
                "             WHEN ESG_WITH_SECTOR_CRITERIA_WEIGHT_DETAILS.RESEARCH_LINE_ID = '1015'\n" +
                "                     THEN 'mesg'\n" +
                "             WHEN ESG_WITH_SECTOR_CRITERIA_WEIGHT_DETAILS.RESEARCH_LINE_ID = '1008'\n" +
                "                     THEN 've'\n" +
                "             END AS \"research_line\"\n" +
                "     ,CASE\n" +
                "             WHEN ESG_WITH_SECTOR_CRITERIA_WEIGHT_DETAILS.RESEARCH_LINE_ID = '1015'\n" +
                "                     THEN ARRAY_CONSTRUCT('Dual', 'Business', 'Stakeholder')\n" +
                "             WHEN ESG_WITH_SECTOR_CRITERIA_WEIGHT_DETAILS.RESEARCH_LINE_ID = '1008'\n" +
                "                     THEN ARRAY_CONSTRUCT('Dual')\n" +
                "             ELSE NULL\n" +
                "             END AS \"materiality_types\"\n" +
                "     ,\"orbis_id\"\n" +
                "     ,\"criteria_id\"\n" +
                "     ,\"criteria_name\"\n" +
                "     ,\"indicator\"\n" +
                "     ,\"domain\"\n" +
                "     ,\"sub_catg_desc\"\n" +
                "     ,IFF(\"criteria_score\" < 0\n" +
                "             AND ESG_WITH_SECTOR_CRITERIA_WEIGHT_DETAILS.RESEARCH_LINE_ID = '1008', 'Low', \"dual_materiality_weight_cat\") AS \"dual_materiality_weight_cat\"\n" +
                "     ,\"business_materiality_weight_cat\"\n" +
                "     ,\"stakeholder_materiality_weight_cat\"\n" +
                "     ,IFF(\"criteria_score\" < 0\n" +
                "             AND ESG_WITH_SECTOR_CRITERIA_WEIGHT_DETAILS.RESEARCH_LINE_ID = '1008', NULL, \"criteria_score\") AS \"criteria_score\"\n" +
                "     ,IFF(\"criteria_score\" < 0\n" +
                "             AND ESG_WITH_SECTOR_CRITERIA_WEIGHT_DETAILS.RESEARCH_LINE_ID = '1008', NULL, QUALIFIER) AS \"score_category\"\n" +
                "     ,\"disclosure_ratio\"\n" +
                "     ,CRITICAL_CONTROVERSY_EXISTS_FLAG AS \"critical_controversy_exists_flag\"\n" +
                "FROM (\n" +
                "     SELECT ESG_WITH_SECTOR_WEIGHT_DETAILS.ORBIS_ID AS \"orbis_id\"\n" +
                "             ,ESG_WITH_SECTOR_WEIGHT_DETAILS.CRITERIA AS \"criteria_id\"\n" +
                "             ,ESG_WITH_SECTOR_WEIGHT_DETAILS.RESEARCH_LINE_ID\n" +
                "             ,ESG_WITH_SECTOR_WEIGHT_DETAILS.CRITICAL_CONTROVERSY_EXISTS_FLAG\n" +
                "             ,DECODE(MAX(TRY_TO_NUMBER(ESG_WITH_SECTOR_WEIGHT_DETAILS.DUAL_MATERIALITY_WEIGHT)), 0, 'Low', 1, 'Moderate', 2, 'High', 3, 'Very High') AS \"dual_materiality_weight_cat\"\n" +
                "             ,DECODE(MAX(TRY_TO_NUMBER(ESG_WITH_SECTOR_WEIGHT_DETAILS.BUSINESS_MATERIALITY_WEIGHT)), 0, 'Low', 1, 'Moderate', 2, 'High', 3, 'Very High') AS \"business_materiality_weight_cat\"\n" +
                "             ,DECODE(MAX(TRY_TO_NUMBER(ESG_WITH_SECTOR_WEIGHT_DETAILS.STAKEHOLDER_MATERIALITY_WEIGHT)), 0, 'Low', 1, 'Moderate', 2, 'High', 3, 'Very High') AS \"stakeholder_materiality_weight_cat\"\n" +
                "             ,MAX(CASE\n" +
                "                             WHEN ESG_WITH_SECTOR_WEIGHT_DETAILS.DATA_TYPE = 'overall_subcategory_score'\n" +
                "                                     THEN TRY_TO_DECIMAL(ESG_WITH_SECTOR_WEIGHT_DETAILS.VALUE, 30, 2)\n" +
                "                             ELSE NULL\n" +
                "                             END) AS \"criteria_score\"\n" +
                "             ,MAX(CASE\n" +
                "                             WHEN ESG_WITH_SECTOR_WEIGHT_DETAILS.DATA_TYPE = 'information_rate_criteria'\n" +
                "                                     THEN TRY_TO_DECIMAL(ESG_WITH_SECTOR_WEIGHT_DETAILS.VALUE, 30, 2) * 100\n" +
                "                             ELSE NULL\n" +
                "                             END) AS \"disclosure_ratio\"\n" +
                "             ,METRIC.CRITERIA_NAME AS \"criteria_name\"\n" +
                "             ,METRIC.INDICATOR AS \"indicator\"\n" +
                "             ,METRIC.DOMAIN AS \"domain\"\n" +
                "             ,METRIC.SUB_CATEGORY_DETAILED_DESCRIPTION AS \"sub_catg_desc\"\n" +
                "     FROM ESG_WITH_SECTOR_WEIGHT_DETAILS\n" +
                "     LEFT JOIN METRIC ON (\n" +
                "                     ESG_WITH_SECTOR_WEIGHT_DETAILS.CRITERIA = METRIC.CRITERIA_CODE\n" +
                "                     AND ESG_WITH_SECTOR_WEIGHT_DETAILS.RESEARCH_LINE_ID = METRIC.RESEARCH_LINE_ID\n" +
                "                     )\n" +
                "     GROUP BY ESG_WITH_SECTOR_WEIGHT_DETAILS.ORBIS_ID\n" +
                "             ,ESG_WITH_SECTOR_WEIGHT_DETAILS.RESEARCH_LINE_ID\n" +
                "             ,ESG_WITH_SECTOR_WEIGHT_DETAILS.CRITERIA\n" +
                "             ,ESG_WITH_SECTOR_WEIGHT_DETAILS.CRITICAL_CONTROVERSY_EXISTS_FLAG\n" +
                "             ,METRIC.CRITERIA_NAME\n" +
                "             ,METRIC.INDICATOR\n" +
                "             ,METRIC.DOMAIN\n" +
                "             ,METRIC.SUB_CATEGORY_DETAILED_DESCRIPTION\n" +
                "     ) ESG_WITH_SECTOR_CRITERIA_WEIGHT_DETAILS\n" +
                "LEFT JOIN DF_LOOKUP.ESG_SCORE_REFERENCE LOOKUP ON 1 = 1\n" +
                "     AND LOOKUP.RESEARCH_LINE_ID = ESG_WITH_SECTOR_CRITERIA_WEIGHT_DETAILS.RESEARCH_LINE_ID\n" +
                "     AND LOOKUP.STATUS = 'Active'\n" +
                "     AND LOOKUP.GRADE IS NULL\n" +
                "     AND (\n" +
                "             FLOOR(\"criteria_score\") BETWEEN LOWER_SCORE_THRESHOLD\n" +
                "                     AND UPPER_SCORE_THRESHOLD\n" +
                "         )\n";
       // System.out.println("query = " + query);
        return getQueryResultMap(query);
    }


    public static List<Map<String, Object>> getEsgMaterialityControversies(String orbisId, String criteriaId){
        String query = "SELECT c.SEVERITY,c.CONTROVERSY_TITLE,c.CONTROVERSY_EVENTS FROM df_target.controversy_details c where ORBIS_ID ='"+orbisId+"'\n" +
                "AND CONTAINS (related_sustainability_driver_ref,'"+criteriaId+"')\n" +
                "AND controversy_status = 'Active'\n" +
                "AND controversy_steps = 'last'\n" +
                "ORDER BY c.controversy_events DESC";
        return getQueryResultMap(query);
    }


    public List<EntityControversy> getControversies(String orbisId, String conditionClause) {
        String query = "SELECT DISTINCT * FROM CONTROVERSY_DETAILS \n" +
                "WHERE ORBIS_ID='" + orbisId + "' \n" +
                "AND CONTROVERSY_STATUS='Active' \n" +
                "AND CONTROVERSY_UPDATES > DATEADD(year,-1,GETDATE()) \n" +
                "%s \n" +
                "ORDER BY CONTROVERSY_UPDATES DESC";
        query = String.format(query, conditionClause);

        List<EntityControversy> list = new ArrayList<>();
        for (Map<String, Object> each : getQueryResultMap(query)) {
            EntityControversy entityControversy = new EntityControversy();

            entityControversy.setOrbisId(each.get("ORBIS_ID").toString());
            entityControversy.setControversyRef(each.get("CONTROVERSY_REF").toString());
            entityControversy.setControversyUpdates(each.get("CONTROVERSY_UPDATES").toString());
            entityControversy.setControversyDescription(each.get("CONTROVERSY_DESCRIPTION").toString());
            entityControversy.setSeverity(each.get("SEVERITY").toString());
            entityControversy.setControversyLocation(each.get("CONTROVERSY_LOCATION_S").toString());
            entityControversy.setTitle(each.get("TITLE").toString());
            entityControversy.setDriverReferences(each.get("RELATED_SUSTAINABILITY_DRIVER_REF").toString());

            list.add(entityControversy);
        }
        return list;
    }

    public List<EntityControversy> getControversies(String portfolioId, int lastDays) {
        String query = "SELECT DISTINCT cd.* FROM DF_PORTFOLIO df\n" +
                "JOIN CONTROVERSY_DETAILS cd ON df.bvd9_number=cd.orbis_id\n" +
                "WHERE df.portfolio_id='" + portfolioId + "'\n" +
                "    AND cd.CONTROVERSY_STATUS='Active'\n" +
                "    AND cd.CONTROVERSY_EVENTS >= dateadd(day, -" + lastDays + ", current_date)\n" +
                "    AND cd.CONTROVERSY_STEPS='last'\n" +
                "    ORDER BY cd.CONTROVERSY_EVENTS DESC";

        List<EntityControversy> list = new ArrayList<>();
        for (Map<String, Object> each : getQueryResultMap(query)) {
            EntityControversy entityControversy = new EntityControversy();

            entityControversy.setOrbisId(each.get("ORBIS_ID").toString());
            entityControversy.setControversyRef(each.get("CONTROVERSY_REF").toString());
            entityControversy.setControversyUpdates(each.get("CONTROVERSY_UPDATES").toString());
            entityControversy.setControversyDescription(each.get("CONTROVERSY_DESCRIPTION").toString());
            entityControversy.setSeverity(each.get("SEVERITY").toString());
            entityControversy.setControversyLocation(each.get("CONTROVERSY_LOCATION_S").toString());
            entityControversy.setTitle(each.get("TITLE").toString());
            entityControversy.setControversyEvents(each.get("CONTROVERSY_EVENTS").toString());
            entityControversy.setDriverReferences(each.get("RELATED_SUSTAINABILITY_DRIVER_REF").toString());

            list.add(entityControversy);
        }
        return list;
    }

    public List<EntityControversy> getControversies(String portfolioId, int year, int month) {
        String strMonth = "";
        if (String.valueOf(month).length() == 1) {
            strMonth = "0" + month;
        } else {
            strMonth = "" + month;
        }

        String query = "SELECT DISTINCT cd.* FROM DF_PORTFOLIO df\n" +
                "JOIN CONTROVERSY_DETAILS cd ON df.bvd9_number=cd.orbis_id\n" +
                "WHERE df.portfolio_id='" + portfolioId + "'\n" +
                "    AND cd.CONTROVERSY_STATUS='Active'\n" +
                "    AND cd.CONTROVERSY_STEPS='last'\n" +
                "    And cd.CONTROVERSY_EVENTS LIKE '" + year + "-" + strMonth + "%'\n" +
                "    ORDER BY cd.CONTROVERSY_EVENTS DESC";

        List<EntityControversy> list = new ArrayList<>();
        for (Map<String, Object> each : getQueryResultMap(query)) {
            EntityControversy entityControversy = new EntityControversy();

            entityControversy.setOrbisId(each.get("ORBIS_ID").toString());
            entityControversy.setControversyRef(each.get("CONTROVERSY_REF").toString());
            entityControversy.setControversyUpdates(each.get("CONTROVERSY_UPDATES").toString());
            entityControversy.setControversyDescription(each.get("CONTROVERSY_DESCRIPTION").toString());
            entityControversy.setSeverity(each.get("SEVERITY").toString());
            entityControversy.setControversyLocation(each.get("CONTROVERSY_LOCATION_S").toString());
            entityControversy.setTitle(each.get("TITLE").toString());
            entityControversy.setControversyEvents(each.get("CONTROVERSY_EVENTS").toString());
            entityControversy.setDriverReferences(each.get("RELATED_SUSTAINABILITY_DRIVER_REF").toString());

            list.add(entityControversy);
        }
        return list;
    }

    public String getCompanyWithMultipleControversies(String portfolioId) {
        String query = "Select TOP 1 TITLE, count(*) from (SELECT DISTINCT cd.* FROM DF_PORTFOLIO df\n" +
                "JOIN CONTROVERSY_DETAILS cd ON df.bvd9_number=cd.orbis_id\n" +
                "WHERE df.portfolio_id='" + portfolioId + "'\n" +
                "    AND cd.CONTROVERSY_STATUS='Active'\n" +
                "                 AND cd.CONTROVERSY_EVENTS >= dateadd(day, -60, current_date)\n" +
                "                AND cd.CONTROVERSY_STEPS='last' )\n" +
                "GROUP BY TITLE HAVING count(*)>1";
        Map<String, Object> record = getQueryResultMap(query).get(0);
        return record.get("TITLE").toString() + ";" + record.get("COUNT(*)").toString();
    }


}
