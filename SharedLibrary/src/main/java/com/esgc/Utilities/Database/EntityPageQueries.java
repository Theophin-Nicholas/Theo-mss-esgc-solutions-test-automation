package com.esgc.Utilities.Database;

import com.esgc.APIModels.EntityPage.EntityControversy;
import com.esgc.APIModels.EntityPage.EntityFilterPayload;
import com.esgc.DBModels.EntityIssuerPageDBModels.P3ControversiesDBModel;
import com.esgc.DBModels.EntityIssuerPageDBModels.P3HeaderIdentifiersDBModel;
import com.esgc.DBModels.EntityIssuerPageDBModels.SourceDocumentDBModel;
import com.esgc.DBModels.EntityIssuerPageDBModels.SummaryWidgetDBModel;
import com.esgc.DBModels.EntityPage.*;
import com.esgc.TestBase.DriverUnits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.Database.DatabaseDriver.getQueryResultMap;
import static com.esgc.Utilities.PortfolioUtilities.getRowMap;


public class EntityPageQueries {


    public static List<ESGScoreSummaryDBModel> getEsgScore(String orbisID) {
        String query = "  SELECT g.ORBIS_ID,case when criteria = 'Environmental' then 'Environment' \n" +
                "                            when criteria = 'ESG' then iff ((DATA_TYPE = 'esg_pillar_score'),'ESG Score','ESG Rating') \n" +
                "      else criteria end as criteria,ROUND(\"VALUE\",0) as Score,\n" +
                "\n" +
                "   cast(SCORED_TIMESTAMP  as date) as LAST_UPDATED\n" +
                "   FROM ESG_MODEL_SCORES g\n" +
                "  join esg_entity_master e on e.ORBIS_ID = g.ORBIS_ID\n" +
                "  where criteria in('ESG','Environmental','Social','Governance')\n" +
                "  and g.orbis_id='" +orbisID +"' and DATA_TYPE = 'esg_pillar_score'\n" +
                "   AND criteria in ('ESG', 'Environmental', 'Governance', 'Social')\n" +
                "      and g.SCORED_TIMESTAMP = (select max(s2.SCORED_TIMESTAMP) from DF_TARGET.ESG_MODEL_SCORES s2 where \n" +
                "                            g.ORBIS_ID = s2.ORBIS_ID\n" +
                "                            and g.criteria = s2.criteria\n" +
                "                            and s2.DATA_TYPE='esg_pillar_score'\n" +
                "                            and s2.criteria in ('ESG', 'Environmental', 'Governance', 'Social'))\n" +
                "\t  QUALIFY row_number() over(partition by (criteria,data_type) order by SCORED_TIMESTAMP desc) = 1";
        System.out.println(query);
        return serializeIssuerSummary(getQueryResultMap(String.format(query, orbisID)));
    }

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


    private static List<ESGScoreSummaryDBModel> serializeIssuerSummary(List<Map<String, Object>> resultSet) {

        /*List<RangeAndScoreCategory> rangesAndCategories = new ArrayList<>();
        rangesAndCategories.add(new RangeAndScoreCategory("A2", 60d, 100d, ""));
        rangesAndCategories.add(new RangeAndScoreCategory("Robust", 50d, 59d, ""));
        rangesAndCategories.add(new RangeAndScoreCategory("Limited", 30d, 49d, ""));
        rangesAndCategories.add(new RangeAndScoreCategory("Weak", 0d, 29d, ""));*/
        List<ESGScoreSummaryDBModel> summaryDBModel = new ArrayList<>();

        for (Map<String, Object> rs : resultSet) {
            ESGScoreSummaryDBModel scoreCat = new ESGScoreSummaryDBModel();
            //Double score = round(Double.valueOf(rs.get("VALUEE").toString()), 2);
            scoreCat.setScore(rs.get("SCORE").toString());
            //String scorecategory = rangesAndCategories.stream().filter(range -> range.getMin() <= Math.round(score) && range.getMax() >= Math.round(score)).findFirst().get().getCategory();
            //scoreCat.setScoreCategory(scorecategory);
            scoreCat.setCriteria(rs.get("CRITERIA").toString());
            scoreCat.setLasttimestamp(rs.get("LAST_UPDATED").toString()); // .substring(0, 10));
            summaryDBModel.add(scoreCat);
        }
        return summaryDBModel;

    }

    public static List<DriverDetailsDBModel> getDriverDetails(String orbisID, String criteria) {
        String query = "WITH MDP AS (\n" +
                "select BVD9_NUMBER\n" +
                ",Metric_Code\n" +
                ",case when Metric_Value='NI' then 'Not Indicated'\n" +
                "when Metric_Value='NR' then 'No Result' \n" +
                "else Metric_Value END as Metric_Value\n" +
                ",INTERNAL_SUB_CATEGORY_CODE as CRITERIA \n" +
                ",METRIC_YEAR\n" +
                "FROM DF_TARGET.MESGC_DATAPOINTS\n" +
                "WHERE BVD9_NUMBER ='" + orbisID + "'\n"  +
                "and criteria ='" + criteria + "'\n" +
                "QUALIFY ROW_NUMBER() OVER (PARTITION BY BVD9_NUMBER,Metric_Code,INTERNAL_SUB_CATEGORY_CODE, METRIC_YEAR ORDER BY LOAD_DATETIME DESC) =1 \n" +
                ")\n" +
                "\n" +
                ", p as (\n" +
                "SELECT EMFS.CATEGORY as \"criteria_name\"\n" +
                ",MDP.BVD9_NUMBER as \"orbis_id\"\n" +
                ",EMFS.METRIC as \"item_descripton\"\n" +
                ",EMFD.PILLAR as \"pillar\"\n" +
                ",EMFD.ANGLE as \"angle\"\n" +
                ",EMFS.POSSIBLE_UNIT as \"possible_unit\"\n" +
                ",EMFRD.VALUE TREND\n" +
                ",METRIC_YEAR METRIC_YEAR\n" +
                ",CRITERIA\n" +
                ",case when try_to_decimal(MDP.Metric_Value) is not null \n" +
                "  then to_char(ROUND (try_to_decimal(MDP.Metric_Value,38,18),2))  \n" +
                "  else MDP.Metric_Value  END AS \"item_value\"\n" +
                ",case when try_to_decimal(MDP.Metric_Value) is not null \n" +
                "  then 'Number' ELSE MDP.Metric_Value end AS \"item_value2\"\n" +
                "FROM MDP\n" +
                "LEFT JOIN DF_TARGET.ESG_METRIC_FRAMEWORK_DETAIL EMFD ON ( MDP.Metric_Code = EMFD.ITEM_CODE and EMFD.ANGLE !='Contro')\n" +
                "LEFT JOIN DF_TARGET.ESG_METRIC_FRAMEWORK_SUMMARY EMFS ON (EMFD.TAXONOMY_ID = EMFS.TAXONOMY_ID)\n" +
                "LEFT JOIN df_target.esg_metric_framework_reference_detail emfrd \n" +
                "                            on (emfrd.Metric_Code = EMFD.ITEM_CODE \n" +
                "                                AND EMFRD.KEY = 'Trend_Type'\n" +
                "                                --AND EMFRD.Value = 'Trend'\n" +
                "                                AND EMFRD.STATUS = 'Active'\n" +
                "                                AND EMFRD.RESEARCH_LINE_ID = 1015\n" +
                "                               )\n" +
                "QUALIFY ROW_NUMBER() OVER (PARTITION BY MDP.BVD9_NUMBER,EMFS.CATEGORY,EMFS.METRIC,EMFD.PILLAR,EMFD.ANGLE,EMFS.POSSIBLE_UNIT, METRIC_YEAR\n" +
                "                               ORDER BY METRIC_YEAR DESC) =1\n" +
                ")\n" +
                "\n" +
                "\n" +
                "select *\n" +
                "from p \n" +
                "--where  Trend not in ('Trend')\n" +
                "qualify row_number() over (partition by \"criteria_name\", \"orbis_id\", \"item_descripton\", \"pillar\", \"angle\", \"possible_unit\"\n" +
                "                                     order by METRIC_YEAR desc)=1\n" +
                "                                     order by Trend" ;

       /* String query = "WITH MDP AS (select BVD9_NUMBER,Metric_Code,case when Metric_Value='NI' then 'Not Indicated'\n" +
                " when Metric_Value='NR' then 'No Result' else Metric_Value END as Metric_Value,INTERNAL_SUB_CATEGORY_CODE as CRITERIA FROM DF_TARGET.MESGC_DATAPOINTS\n" +
                "      QUALIFY ROW_NUMBER() OVER (PARTITION BY BVD9_NUMBER,Metric_Code,INTERNAL_SUB_CATEGORY_CODE ORDER BY METRIC_YEAR DESC,LOAD_DATETIME DESC) =1 )\n" +
                "      \n" +
                "      SELECT\n" +
                "EMFS.CATEGORY as \"criteria_name\",MDP.BVD9_NUMBER as \"orbis_id\",EMFS.METRIC as \"item_descripton\",\n" +
                "      EMFD.PILLAR as \"pillar\", EMFD.ANGLE as \"angle\",EMFS.POSSIBLE_UNIT as \"possible_unit\",\n" +
                "\n" +
                "MAX(\n" +
                "      case when try_to_decimal(MDP.Metric_Value) is not null then to_char(ROUND (try_to_decimal(MDP.Metric_Value,38,18),2))  \n" +
                "        else MDP.Metric_Value  END) AS \"item_value\"\n" +
                ",MAX( case when try_to_decimal(MDP.Metric_Value) is not null then 'Number' ELSE MDP.Metric_Value\n" +
                "                                                end) AS \"item_value2\"\n" +
                "FROM MDP\n" +
                "LEFT JOIN DF_TARGET.ESG_METRIC_FRAMEWORK_DETAIL EMFD ON ( MDP.Metric_Code = EMFD.ITEM_CODE)\n" +
                "LEFT JOIN DF_TARGET.ESG_METRIC_FRAMEWORK_SUMMARY EMFS ON (EMFD.TAXONOMY_ID = EMFS.TAXONOMY_ID) \n" +
                "WHERE BVD9_NUMBER = '" + orbisID + "'\n" +
                "and criteria = '" + criteria + "'\n" +
                "and EMFD.ANGLE !='Contro'\n" +
                "\n" +
                "\n" +
                "            group by MDP.BVD9_NUMBER,EMFS.CATEGORY,EMFS.METRIC,EMFD.PILLAR,EMFD.ANGLE,EMFS.POSSIBLE_UNIT";*/

        query = String.format(query, orbisID, criteria);
        System.out.println(query);
        List<DriverDetailsDBModel> driverDetailsDBModelList = new ArrayList<>();
        DriverUnits d = new DriverUnits();
        for (Map<String, Object> each : getQueryResultMap(query)) {

            DriverDetailsDBModel model = new DriverDetailsDBModel();
            model.setAngle(each.get("angle").toString());
            model.setPillar(each.get("pillar").toString());
            model.setCriteria_name(each.get("criteria_name").toString());
            model.setItem_value(each.get("item_value").toString());
            model.setItem_descripton(each.get("item_descripton").toString());

            String possible_unit = each.get("possible_unit").toString();
            String mappedUnit = d.getDriverUnit().get(possible_unit);
            if (mappedUnit!=null) model.setPossible_unit(mappedUnit);
            else
                model.setPossible_unit( possible_unit.toString().substring(0,possible_unit.lastIndexOf(")")).
                        replace("Quantitative (",""));
            model.setTrend(each.get("TREND").toString());
            model.setItem_value2(each.get("item_value2").toString());
            model.setMetric_Year(each.get("METRIC_YEAR").toString());


            driverDetailsDBModelList.add(model);


        }

        return driverDetailsDBModelList;


    }

    public static List<DriverTrendDetailModel> getTrendDetails(String orbisID, String criteria) {
        String query = "select md.bvd9_number, emfs.metric, md.METRIC_YEAR, emfs.possible_unit, md.metric_value, emfd.pillar,EMFD.ANGLE\n" +
                "from df_target.esg_metric_framework_reference_detail emfrd\n" +
                "join DF_TARGET.ESG_METRIC_FRAMEWORK_DETAIL EMFD on emfrd.Metric_Code = EMFD.ITEM_CODE\n" +
                "join df_target.mesgc_datapoints md on md.metric_code = emfrd.metric_code\n" +
                "join df_target.ESG_METRIC_FRAMEWORK_SUMMARY emfs on emfs.taxonomy_id = emfd.taxonomy_id\n" +
                "where emfrd.KEY = 'Trend_Type'\n" +
                "and emfrd.value = 'Trend'\n" +
                "and emfrd.STATUS = 'Active'\n" +
                "and emfrd.research_line_id = 1015\n" +
                "and md.bvd9_number = '" + orbisID + "'\n" +
                "and md.internal_sub_category_code = '" + criteria + "'\n"  +
                "order by Metric, Metric_year asc ;" ;



        query = String.format(query, orbisID, criteria);

        List<DriverTrendDetailModel> driverTrendDetailsDBModelList = new ArrayList<>();
        DriverUnits d = new DriverUnits();
        for (Map<String, Object> each : getQueryResultMap(query)) {

            DriverTrendDetailModel model = new DriverTrendDetailModel();
            model.setMETRIC(each.get("METRIC").toString());
            model.setMETRIC_YEAR(each.get("METRIC_YEAR").toString());
            String possible_unit = each.get("POSSIBLE_UNIT").toString();
            String mappedUnit = d.getDriverUnit().get(possible_unit);
            if (mappedUnit!=null) model.setPOSSIBLE_UNIT(mappedUnit);
            else
                model.setPOSSIBLE_UNIT( possible_unit.toString().substring(0,possible_unit.lastIndexOf(")")).
                        replace("Quantitative (",""));

            if (each.get("METRIC_VALUE").toString().equals("NI")) model.setMETRIC_VALUE("Not Indicated");
            else model.setMETRIC_VALUE(String.format("%.2f", Double.valueOf(each.get("METRIC_VALUE").toString())));
            model.setPILLAR(each.get("PILLAR").toString());
            model.setANGLE(each.get("ANGLE").toString());
            driverTrendDetailsDBModelList.add(model);


        }

        return driverTrendDetailsDBModelList;


    }

    public static List<DriverScoreDBModel> getDriverScore(String orbisID) {
        String query = "select distinct a.orbis_id, b.ENTITY_NAME_BVD,  c.MESG_SECTOR, a.criteria,f.category as name,f.INDICATOR, f.DOMAIN,\n" +
                "a.data_type, a.value, ROUND(d.value,2) as Score,\n" +
                "CASE\n" +
                "    WHEN Score >=0 and Score<=29 THEN 'Weak'\n" +
                "    WHEN Score >=30 and Score<=49 THEN 'Limited'\n" +
                "    WHEN Score >=50 and Score<=59 THEN 'Robust'\n" +
                "    WHEN Score >=60 and Score<=100 THEN 'A2'\n" +
                "    ELSE null\n" +
                "END AS scoreCategory\n" +
                "from ESG_MODEL_SCORES a\n" +
                "join ESG_MODEL_SCORES d on a.orbis_id=d.orbis_id and d.criteria=a.criteria and d.data_type='criteria_score'\n" +
                "join ESG_ENTITY_MASTER b on a.orbis_id=b.orbis_id\n" +
                "join sector_hierarchy c on b.MESG_SECTOR_ID=c.MESG_SECTOR_ID\n" +
                "join ESG_METRIC_FRAMEWORK_DETAIL e on a.criteria=e.category\n" +
                "join ESG_METRIC_FRAMEWORK_SUMMARY f on e.taxonomy_id=f.taxonomy_id\n" +
                "where a.orbis_id=%s\n" +
                "and a.data_type='criteria_weight'\n" +
                //"and b.as_of_month='12' and c.month=b.as_of_month " +
                "and c.SUBINDUSTRY_SECTOR_ID=concat(c.MESG_SECTOR_ID,'-11')\n" +
                "order by a.value desc,score asc;";

        return serializeDriverScore(getQueryResultMap(String.format(query, orbisID)));
    }

    public static String getSectorFromOrbisId(String orbisId) {
        String query = "Select distinct MESG_SECTOR from sector_hierarchy where MESG_SECTOR_ID = " +
                "(select max(mesg_sector_id) as sectorCode from df_target.esg_entity_master e where orbis_id = '" + orbisId + "' and entity_status='Active');";
        return getQueryResultMap(String.format(query, orbisId)).get(0).get("MESG_SECTOR").toString();
    }

    public static List<SectorDriverDBModel> getSectorDrivers(String sector) {
        String query = "select distinct //a.orbis_id, \n" +
                "a.criteria, \n" +
                "f.INDICATOR, CONCAT(f.DOMAIN, ' / ',f.category) as name, a.value,\n" +
                "CASE \n" +
                "WHEN a.value =0 THEN 'Low' \n" +
                "WHEN a.value =1 THEN 'Moderate' \n" +
                "WHEN a.value =2 THEN 'High' \n" +
                "WHEN a.value =3 THEN 'Very High' \n" +
                "END AS criteria_weight \n" +
                "from ESG_MODEL_SCORES a\n" +
                "join ESG_MODEL_SCORES d on a.orbis_id=d.orbis_id and d.criteria=a.criteria and a.data_type='criteria_weight'\n" +
                "join ESG_ENTITY_MASTER b on a.orbis_id=b.orbis_id\n" +
                "join sector_hierarchy c on b.MESG_SECTOR_ID=c.MESG_SECTOR_ID\n" +
                "join ESG_METRIC_FRAMEWORK_DETAIL e on a.criteria=e.category \n" +
                "                \n" +
                "join ESG_METRIC_FRAMEWORK_SUMMARY f on e.taxonomy_id=f.taxonomy_id\n" +
                "\n" +
                "where c.MESG_SECTOR='" + sector + "' \n" +
                "//a.orbis_id='000316599'\n" +
                "AND E.AS_OF_DATE = (SELECT MAX(AS_OF_DATE) FROM DF_TARGET.ESG_METRIC_FRAMEWORK_DETAIL) \n" +
                "and NVL(b.ENTITY_STATUS,'Active') = 'Active'\n" +
                "//and name='Communities / Societal Impacts of Product or Service'\n" +
                "AND e.DATA_SOURCE = 'data_capture' AND e.ITEM_TYPE = 'Input (raw)' AND e.STATUS='Active'\n" +
                "\n" +
                "                \n" +
                "//and b.as_of_year='2022' \n" +
                "//and c.month=b.as_of_month \n" +
                "and c.SUBINDUSTRY_SECTOR_ID=concat(c.MESG_SECTOR_ID,'-11')\n" +

                "//and Indicator = 'Social'\n" +
                "order by indicator asc, a.value asc, name desc;\n" +
                "//order by a.value desc,score asc; \n" +
                ";";
        System.out.println(query);
        return serializeSectorDriver(getQueryResultMap(String.format(query, sector)));
    }

    private static List<DriverScoreDBModel> serializeDriverScore(List<Map<String, Object>> resultSet) {
        List<DriverScoreDBModel> driverScoreDBModel = new ArrayList<>();
        for (Map<String, Object> rs : resultSet) {
            DriverScoreDBModel driverScore = new DriverScoreDBModel();
            driverScore.setORBIS_ID(rs.get("ORBIS_ID").toString());
            driverScore.setENTITY_NAME_BVD(rs.get("ENTITY_NAME_BVD").toString());
            driverScore.setMESG_SECTOR(rs.get("MESG_SECTOR").toString());
            driverScore.setCRITERIA(rs.get("CRITERIA").toString());
            driverScore.setNAME(rs.get("NAME").toString());
            driverScore.setINDICATOR(rs.get("INDICATOR").toString());
            driverScore.setDOMAIN(rs.get("DOMAIN").toString());
            driverScore.setDATA_TYPE(rs.get("DATA_TYPE").toString());
            driverScore.setVALUE(Integer.valueOf(rs.get("VALUE").toString()));
            driverScore.setSCORE(Double.valueOf(rs.get("SCORE").toString()));
            driverScore.setSCORECATEGORY(rs.get("SCORECATEGORY").toString());
            driverScoreDBModel.add(driverScore);
        }
        return driverScoreDBModel;

    }

    private static List<SectorDriverDBModel> serializeSectorDriver(List<Map<String, Object>> resultSet) {
        List<SectorDriverDBModel> driverScoreDBModel = new ArrayList<>();
        for (Map<String, Object> rs : resultSet) {
            SectorDriverDBModel sectorDriver = new SectorDriverDBModel();
            sectorDriver.setNAME(rs.get("NAME").toString());
            sectorDriver.setINDICATOR(rs.get("INDICATOR").toString());
            sectorDriver.setVALUE(Double.valueOf(rs.get("VALUE").toString()).intValue());
            sectorDriver.setCritteria_weight(rs.get("CRITERIA_WEIGHT").toString());
            sectorDriver.setCRITERIA(rs.get("CRITERIA").toString());
            driverScoreDBModel.add(sectorDriver);
        }
        return driverScoreDBModel;

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

    public List<EntityControversy> getControversies(String orbisId, EntityFilterPayload entityAPIFilterPayload) {
        String conditionClause = null;
        EntityPageQueries entityQueries = new EntityPageQueries();
        if (entityAPIFilterPayload != null && entityAPIFilterPayload.getCriteria() != null) {
            conditionClause = "AND RELATED_SUSTAINABILITY_DRIVER_REF LIKE ('%" + entityAPIFilterPayload.getCriteria() + "%')";
        }
        List<EntityControversy> entityControversies = entityQueries.getControversies(orbisId, conditionClause);
        return entityControversies;
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

    public static List<SourceDocumentDBModel> getSourceDocumentDBData(String orbisID) {
        String query = " with metric as (SELECT DISTINCT EMFD.CATEGORY AS CRITERIA_CODE,EMFS.CATEGORY AS CRITERIA_NAME,EMFS.DOMAIN ,EMFS.INDICATOR\n" +
                "FROM DF_TARGET.ESG_METRIC_FRAMEWORK_DETAIL EMFD,DF_TARGET.ESG_METRIC_FRAMEWORK_SUMMARY EMFS\n" +
                "WHERE EMFD.TAXONOMY_ID = EMFS.TAXONOMY_ID AND EMFD.AS_OF_DATE = (SELECT MAX(AS_OF_DATE) FROM DF_TARGET.ESG_METRIC_FRAMEWORK_DETAIL)\n" +
                "AND EMFD.DATA_SOURCE = 'data_capture' AND EMFD.ITEM_TYPE = 'Input (raw)' AND EMFD.STATUS='Active' ),\n" +
                "SH AS (SELECT DISTINCT MESG_SECTOR_ID,MESG_SECTOR FROM DF_TARGET.SECTOR_HIERARCHY)\n" +
                "SELECT DOCUMENT_NAME, DOCUMENT_URL\n" +
                "FROM DF_TARGET.MESGC_DATAPOINTS MD\n" +
                "JOIN ESG_ENTITY_MASTER EEM ON (MD.BVD9_NUMBER = EEM.ORBIS_ID AND EEM.ENTITY_STATUS='Active')\n" +
                "JOIN SH ON (EEM.MESG_SECTOR_ID = SH.MESG_SECTOR_ID)\n" +
                "JOIN METRIC ON (MD.INTERNAL_SUB_CATEGORY_CODE = METRIC.CRITERIA_CODE)\n" +
                "where ORBIS_ID='" + orbisID + "'\n" +
                "and DOCUMENT_NAME is not null\n" +
                "and DOCUMENT_URL is not null\n" +
                "group by  DOCUMENT_NAME, Document_URL ";

        List<SourceDocumentDBModel> sourceDocumentDBData = new ArrayList<>();
        for (Map<String, Object> each : getQueryResultMap(query)) {
            SourceDocumentDBModel model = new SourceDocumentDBModel();
            model.setDocument_name(each.get("DOCUMENT_NAME").toString());
            model.setDocument_url(each.get("DOCUMENT_URL").toString());
            sourceDocumentDBData.add(model);
        }

        return sourceDocumentDBData;


    }


    public static String getSectorDescription(String orbisID) {
        String query = "select orbis_id , ENTITY_PROPER_NAME, a.MESG_SECTOR_ID ,MESG_SECTOR_DESCRIPTION from ESG_ENTITY_MASTER a, MESG_SECTOR_DESCRIPTION as b \n" +
                "where a.MESG_SECTOR_ID = b.MESG_SECTOR_ID and orbis_id ='" + orbisID + "'";
        System.out.println(query);
        System.out.println(getQueryResultMap(query).get(0));
        return getQueryResultMap(query).get(0).get("MESG_SECTOR_DESCRIPTION").toString();
    }

    public static List<SummaryWidgetDBModel> getESGScoreSummaryWidgetData(String orbisID) {
        String query = "Select ORBIS_ID, 'ESG Score' as category,VALUE, SCORED_TIMESTAMP from DF_TARGET.ESG_MODEL_SCORES\n" +
                "where CRITERIA='ESG' and DATA_TYPE= 'esg_pillar_score' and ORBIS_ID= '" + orbisID + "'\n" +
                "QUALIFY ROW_NUMBER() OVER (PARTITION BY ORBIS_ID ORDER BY SCORED_TIMESTAMP DESC) =1\n" +
                "union all\n" +
                "Select ORBIS_ID, 'ESG Rating' as category,VALUE, SCORED_TIMESTAMP from DF_TARGET.ESG_MODEL_SCORES\n" +
                "where CRITERIA='ESG' and DATA_TYPE= 'esg_pillar_score_bucket' and ORBIS_ID= '" + orbisID + "'\n" +
                "QUALIFY ROW_NUMBER() OVER (PARTITION BY ORBIS_ID ORDER BY SCORED_TIMESTAMP DESC) =1\n" +
                "union all\n" +
                "Select ORBIS_ID, 'Environment' as category,VALUE, SCORED_TIMESTAMP\n" +
                "from DF_TARGET.ESG_MODEL_SCORES\n" +
                "where  CRITERIA = 'Environmental'\n" +
                "and DATA_TYPE= 'esg_pillar_score'\n" +
                "and ORBIS_ID= '" + orbisID + "'\n" +
                "QUALIFY ROW_NUMBER() OVER (PARTITION BY ORBIS_ID ORDER BY SCORED_TIMESTAMP DESC) =1\n" +
                "union all \n" +
                "Select ORBIS_ID, 'Social' as category,VALUE, SCORED_TIMESTAMP\n" +
                "from DF_TARGET.ESG_MODEL_SCORES\n" +
                "where  CRITERIA = 'Social'\n" +
                "and DATA_TYPE= 'esg_pillar_score'\n" +
                "and ORBIS_ID= '" + orbisID + "'\n" +
                "QUALIFY ROW_NUMBER() OVER (PARTITION BY ORBIS_ID ORDER BY SCORED_TIMESTAMP DESC) =1\n" +
                "union all \n" +
                "Select ORBIS_ID, 'Governance' as category,VALUE, SCORED_TIMESTAMP\n" +
                "from DF_TARGET.ESG_MODEL_SCORES\n" +
                "where  CRITERIA = 'Governance'\n" +
                "and DATA_TYPE= 'esg_pillar_score'\n" +
                "and ORBIS_ID= '" + orbisID + "'\n" +
                "QUALIFY ROW_NUMBER() OVER (PARTITION BY ORBIS_ID ORDER BY SCORED_TIMESTAMP DESC) =1";

        List<SummaryWidgetDBModel> summaryWidget = new ArrayList<>();
        for (Map<String, Object> each : getQueryResultMap(query)) {
            SummaryWidgetDBModel model = new SummaryWidgetDBModel();
            model.setORBIS_ID(each.get("ORBIS_ID").toString());
            model.setCATEGORY(each.get("CATEGORY").toString());
            model.setVALUE(each.get("VALUE").toString());
            model.setSCORED_TIMESTAMP(each.get("SCORED_TIMESTAMP").toString());
            summaryWidget.add(model);
        }

        return summaryWidget;


    }

    public static P3HeaderIdentifiersDBModel getHeaderIdentifiers(String orbisID) {
        String query = "SELECT a.ORBIS_ID,a.LEI,b.ISIN,a.ENTITY_PROPER_NAME, c.COMMON_NAME as COUNTRY_NAME , d.MESG_SECTOR\n" +
                " FROM DF_TARGET.esg_entity_master a, DF_DERIVED.ISIN_SORTED b, DF_LOOKUP.ISO_COUNTRY_REGION_LKUP c ,  sector_hierarchy d  \n" +
                "where a.ORBIS_ID = b.BVD9_NUMBER and a.COUNTRY_CODE = c.ISO_2_CHAR_CODE \n" +
                "and a.MESG_SECTOR_ID=d.MESG_SECTOR_ID\n" +
                "and b.BVD9_NUMBER = '" + orbisID + "' AND b.PRIMARY_ISIN = 'Y'\n" +
                "QUALIFY ROW_NUMBER() OVER (PARTITION BY ORBIS_ID ORDER BY ISIN DESC) =1";


        P3HeaderIdentifiersDBModel headerIdentifier = new P3HeaderIdentifiersDBModel();
        Map<String, Object> each = new HashMap<>();
        each = getRowMap(query);

        headerIdentifier.setORBIS_ID(each.get("ORBIS_ID").toString());
        headerIdentifier.setLEI(each.get("LEI") == null ? null : each.get("LEI").toString());
        headerIdentifier.setISIN(each.get("ISIN").toString());
        headerIdentifier.setENTITY_PROPER_NAME(each.get("ENTITY_PROPER_NAME").toString());
        headerIdentifier.setCOUNTRY_NAME(each.get("COUNTRY_NAME").toString());
        headerIdentifier.setMESG_SECTOR(each.get("MESG_SECTOR").toString());
        return headerIdentifier;
    }

    public static List<P3ControversiesDBModel> getControversiesData(String orbisID) {
        String query = "SELECT  distinct\n" +
                "                cd.orbis_id AS \"orbis_id\"\n" +
                "                ,cd.controversy_events AS \"controversy_events\"\n" +
                "                ,cd.controversy_description AS \"controversy_description\"\n" +
                "                ,cd.severity AS \"severity\"\n" +
                "                ,cd.number_of_controversies_overall as \"total_controversies\"\n" +
                "                ,cd.controversy_title AS \"controversy_title\"\n" +
                "               ,cd.CONTROVERSY_UPDATES\n" +
                "                FROM CONTROVERSY_DETAILS cd\n" +
                "where cd.orbis_id='" + orbisID + "'AND \n" +
                "cd.CONTROVERSY_STATUS='Active' AND CONTROVERSY_STEPS='last' \n" +
                "ORDER BY cd.controversy_events DESC ";
        System.out.println(query);
        List<P3ControversiesDBModel> ControversiesDBModel = new ArrayList<>();
        for (Map<String, Object> each : getQueryResultMap(query)) {
            P3ControversiesDBModel model = new P3ControversiesDBModel();
            model.setORBIS_ID(each.get("orbis_id").toString());
            model.setControversy_events(each.get("controversy_events").toString());
            model.setControversy_description(each.get("controversy_description").toString());
            model.setSeverity(each.get("severity").toString());
            model.setTotal_controversies(each.get("total_controversies").toString());
            model.setControversy_title(each.get("controversy_title").toString());
            model.setCONTROVERSY_UPDATES(each.get("CONTROVERSY_UPDATES").toString());
            ControversiesDBModel.add(model);
        }

        return ControversiesDBModel;


    }

    public static int getOverallDisclosureRatio(String orbisID) {
        String query = "Select * from esg_model_scores where data_type='information_rate_global' and orbis_id='"+ orbisID +"'";
        Map<String, Object> each = new HashMap<>();
        each = getRowMap(query);
        return (int) Math.round(Double.valueOf(each.get("VALUE").toString())*100);

    }


}
