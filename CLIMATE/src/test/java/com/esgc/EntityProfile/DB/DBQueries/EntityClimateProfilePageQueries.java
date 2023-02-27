package com.esgc.EntityProfile.DB.DBQueries;

import com.esgc.Utilities.Database.DatabaseDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.Database.DatabaseDriver.*;


public class EntityClimateProfilePageQueries {


    public Map<String, String> getBrownShareData(String orbisID) {
        String query = "with p as (select gs.bvd9_number,gs.SCORE_RANGE,em.entity_name, round(IFNULL(BS_FOSF_INDUSTRY_REVENUES_ACCURATE,0),0)score , row_number()OVER(partition BY bvd9_number ORDER BY gs.AS_OF_date desc) row_number\n" +
                "from BROWN_SHARE gs, vw_Entity_Data em where gs.bvd9_number = bvd9_id)\n" +
                "select * from p where row_number =1 and p.bvd9_number =  %s \n" +
                "order by score desc";

        query = String.format(query, orbisID);

        Map<String, String> data = new HashMap<String, String>();

        for (Map<String, Object> rs : getQueryResultMap(query)) {
            data.put("BVD9_NUMBER", rs.get("BVD9_NUMBER").toString());
            data.put("ENTITY_NAME", rs.get("ENTITY_NAME").toString());
            data.put("SCORE", rs.get("SCORE").toString());
            data.put("SCORE_RANGE", rs.get("SCORE_RANGE").toString());
        }

        return data;
    }

    public Map<String, String> getGreenShareData(String orbisID) {
        String query = "with p as (select gs.bvd9_number,em.entity_name, round(IFNULL(gs.GS_OVERALL_ASSESSMENT_ESTIMATE_OF_INCORPORATION,0),0)score , row_number()OVER(partition BY bvd9_number ORDER BY gs.AS_OF_date desc) row_number\n" +
                "from GREEN_SHARE gs, vw_Entity_Data em where gs.bvd9_number = bvd9_id)\n" +
                "select * from p where row_number =1 and p.bvd9_number =  %s \n" +
                "order by score desc";

        query = String.format(query, orbisID);

        Map<String, String> data = new HashMap<String, String>();

        for (Map<String, Object> rs : getQueryResultMap(query)) {
            data.put("BVD9_NUMBER", rs.get("BVD9_NUMBER").toString());
            data.put("ENTITY_NAME", rs.get("ENTITY_NAME").toString());
            data.put("SCORE", rs.get("SCORE").toString());
        }

        return data;
    }


    public List<Map<String, Object>> getEntityWithNoBrownShareInfo() {
        String query = "select eem.* from esg_entity_master eem\n" +
                "       join ENTITY_COVERAGE_TRACKING ect on ect.orbis_id=eem.orbis_id\n" +
                "       left join brown_share bs on bs.bvd9_number=eem.orbis_id \n" +
                "       where bvd9_number is null and eem.managed_type='Covered' and eem.ENTITY_STATUS='Active'; ";

        return DatabaseDriver.getQueryResultMap(query);
    }

    public List<Map<String, Object>> getEntitySectorInfo(String orbisId) {
        String query = "select distinct(MESG_SECTOR),MESG_SECTOR_ID from DF_TARGET.SECTOR_HIERARCHY where MESG_SECTOR_ID in " +
                "(select MESG_SECTOR_ID from DF_TARGET.ESG_ENTITY_MASTER where ORBIS_ID ='"+orbisId+"');";

        return getQueryResultMap(query);
    }

    public List<Map<String, Object>> getSectorCompanies(String sectorName) {
        String query = "select * from DF_TARGET.ESG_ENTITY_MASTER where MESG_SECTOR_ID in (select MESG_SECTOR_ID from DF_TARGET.SECTOR_HIERARCHY where MESG_SECTOR='"+sectorName+"') and ENTITY_STATUS ='Active';";

        return DatabaseDriver.getQueryResultMap(query);
    }

    public Map<String, String> getTempratureAlignmentData(String orbisID) {
        String query = "\n" +
                "with p as (select BVD9_NUMBER,em.entity_name, SCORE_CATEGORY,TEMPERATURE_SCORE as Implied_Temperature_Rise,TARGET_YEAR as Emissions_Reduction_Target_Year,\n" +
                "TARGET_DESCRIPTION,AS_OF_DATE as updated_Date from TEMPERATURE_ALIGNMENT gs, vw_Entity_Data em where gs.bvd9_number = em.bvd9_id)\n" +
                "select * from p where  p.bvd9_number =  %s order by updated_Date desc limit 1";

        query = String.format(query, orbisID);
        System.out.println(query);
        Map<String, String> data = new HashMap<String, String>();

        for (Map<String, Object> rs : getQueryResultMap(query)) {
            data.put("BVD9_NUMBER", rs.get("BVD9_NUMBER").toString());
            data.put("ENTITY_NAME", rs.get("ENTITY_NAME").toString());
            data.put("SCORE_CATEGORY", rs.get("SCORE_CATEGORY").toString());
            data.put("IMPLIED_TEMPERATURE_RISE", rs.get("IMPLIED_TEMPERATURE_RISE").toString());
            data.put("EMISSIONS_REDUCTION_TARGET_YEAR", rs.get("EMISSIONS_REDUCTION_TARGET_YEAR").toString());
            data.put("TARGET_DESCRIPTION", (rs.get("TARGET_DESCRIPTION") == null ? null : rs.get("TARGET_DESCRIPTION").toString()));
            data.put("UPDATED_DATE", rs.get("UPDATED_DATE").toString());

        }
        return data;
    }

    public Map<String, String> getCarbonFootprintSummary(String orbisID) {
        String query = "\n" +
                " with p as ( select cf.bvd9_number,CARBON_FOOTPRINT_VALUE_TOTAL,SCORE_CATEGORY,CARBON_FOOTPRINT_VALUE_SCOPE_1,CARBON_FOOTPRINT_VALUE_SCOPE_2,CARBON_FOOTPRINT_VALUE_SCOPE_3, ESTIMATED,AS_OF_DATE \n" +
                "                , row_number()OVER(partition BY bvd9_number ORDER BY AS_OF_date desc) row_number\n" +
                "                from  carbon_footprint cf, vw_Entity_Data em where cf.bvd9_number = em.bvd9_id) select * from p where row_number =1 and p.bvd9_number =   %s \n" +
                "                order by CARBON_FOOTPRINT_VALUE_SCOPE_3 desc;";

        query = String.format(query, orbisID);

        Map<String, String> data = new HashMap<String, String>();

        for (Map<String, Object> rs : getQueryResultMap(query)) {
            data.put("BVD9_NUMBER", rs.get("BVD9_NUMBER").toString());
            data.put("CARBON_FOOTPRINT_VALUE_TOTAL", rs.get("CARBON_FOOTPRINT_VALUE_TOTAL").toString());
            data.put("SCORE_CATEGORY", rs.get("SCORE_CATEGORY").toString());
            data.put("CARBON_FOOTPRINT_VALUE_SCOPE_1", rs.get("CARBON_FOOTPRINT_VALUE_SCOPE_1").toString());
            data.put("CARBON_FOOTPRINT_VALUE_SCOPE_2", rs.get("CARBON_FOOTPRINT_VALUE_SCOPE_2").toString());
            data.put("CARBON_FOOTPRINT_VALUE_SCOPE_3", rs.get("CARBON_FOOTPRINT_VALUE_SCOPE_3").toString());
            data.put("AS_OF_DATE", rs.get("CARBON_FOOTPRINT_VALUE_SCOPE_3").toString());
            data.put("ESTIMATED", rs.get("ESTIMATED").toString());


        }
        System.out.println(data);
        return data;
    }

    public List<Map<String, Object>> getHighestRiskHazardData(String orbisID) {
        String query = "SELECT   rc.risk_category_name                    risk_category_name,\n" +
                "         Sum(percent_facilities_exposed) * 100 AS percent_facilities_exposed\n" +
                "FROM     df_target.entity_score_thresholds et\n" +
                "JOIN     df_target.risk_category rc\n" +
                "ON       et.risk_category_id=rc.risk_category_id\n" +
                "WHERE    entity_id_bvd9 = '" + orbisID + "'\n" +
                "AND      risk_threshold_id IN (3,4)\n" +
                "AND      et.release_year\n" +
                "                  || et.release_month=\n" +
                "         (\n" +
                "                SELECT Max(Concat(release_year,release_month))\n" +
                "                FROM   df_target.entity_score_category s\n" +
                "                WHERE  s.entity_id_bvd9 = et.entity_id_bvd9 )\n" +
                "AND      et.risk_category_id =\n" +
                "         (\n" +
                "                  SELECT   risk_category_id\n" +
                "                  FROM     df_target.entity_score_category\n" +
                "                  WHERE    entity_id_bvd9 = '" + orbisID + "'\n" +
                "                  AND      risk_category_id IN (14,1,12,4,2,16 ) qualify row_number() OVER( ORDER BY release_year DESC, release_month DESC, entity_category_score DESC nulls last)=1)\n" +
                "GROUP BY rc.risk_category_name ;";

        return DatabaseDriver.getQueryResultMap(query);
    }

    public List<Map<String, Object>> getSectorComparisonChartPhysicalRiskData(String orbisID) {
        String query = "WITH rl AS\n" +
                "(\n" +
                "      SELECT ENTITY_ID_BVD9 AS bvd9_number,\n" +
                "              OPERATIONS_RISK_SCORE             AS score  \n" +
                "       FROM   ENTITY_SCORE WHERE RELEASE_MONTH='12' AND RELEASE_YEAR='2021'  )\n" +
                "SELECT    distinct rl.bvd9_number,\n" +
                "          rl.score,\n" +
                "          country_code          AS country_iso_code,\n" +
                "          entity_proper_name    AS company_name,\n" +
                "          l1_sector             AS sector_name,\n" +
                "          s.MESG_SECTOR         AS sector,\n" +
                "          ENTITY_STATUS,\n" +
                "          world_region\n" +
                "FROM      esg_entity_master e\n" +
                "JOIN      rl\n" +
                "ON        e.orbis_id = rl.bvd9_number\n" +
                "JOIN (select distinct  MESG_SECTOR_ID,MESG_SECTOR  ,l1_sector from sector_hierarchy) s\n" +
                "ON        e.mesg_sector_id = s.mesg_sector_id\n" +
                "where  rl.bvd9_number = '" + orbisID + "' and ENTITY_STATUS  = 'Active'\n" +
                "order by score desc;";

        return DatabaseDriver.getQueryResultMap(query);
    }

    public Map<String, String> getPhysicalRiskData(String orbisID) {
        String query = " select bvd9_number,prm.score_category, prm.GS_PH_RISK_MGT_TOTAL ,GS_PH_RISK_MGT_LEADERSHIP,GS_PH_RISK_MGT_IMPLEMENTATION,GS_PH_RISK_MGT_RESULTS\n" +
                "  from physical_risk_management prm where  prm.bvd9_number = '" + orbisID + "' \n" +
                "  order by prm.year desc, prm.month desc Limit 1";

        query = String.format(query, orbisID);

        Map<String, String> data = new HashMap<String, String>();

        for (Map<String, Object> rs : getQueryResultMap(query)) {
            data.put("BVD9_NUMBER", rs.get("BVD9_NUMBER").toString());
            data.put("SCORE_CATEGORY", rs.get("SCORE_CATEGORY").toString());
            data.put("GS_PH_RISK_MGT_TOTAL", rs.get("GS_PH_RISK_MGT_TOTAL").toString());
            data.put("GS_PH_RISK_MGT_LEADERSHIP", rs.get("GS_PH_RISK_MGT_LEADERSHIP").toString());
            data.put("GS_PH_RISK_MGT_IMPLEMENTATION", rs.get("GS_PH_RISK_MGT_IMPLEMENTATION").toString());
            data.put("GS_PH_RISK_MGT_RESULTS", rs.get("GS_PH_RISK_MGT_RESULTS").toString());
        }

        return data;
    }

    public static List<String> getCoverage(String orbisId) {
        String query = " select Related_Domain\n" +
                " from Controversy_details\n" +
                " where Orbis_id = '" + orbisId + "' and CONTROVERSY_STATUS = 'Active' and controversy_steps = 'last'\n" +
                "order by CONTROVERSY_EVENTS desc";

        List<String> result = getQueryResultListMap(query);
        System.out.println("result = " + result);
        return result;
    }

    public static List<String> getCoverageForWidgets(String orbisId, String researchLine) {
        String query = "";
        if (researchLine.equalsIgnoreCase("Temperature Alignment")) {
            query = "select max(data_value) UpdateDate\n" +
                    "from RESEARCH_DATA\n" +
                    "where bvd9_number = " + orbisId + "\n" +
                    "and research_structure_datapoint_id =1017 and research_Data_id = 9";
            List<String> result = getQueryResultListUnderlyingDataTempAligment(query);
            System.out.println("result = " + result);
            return result;
        } else if (researchLine.equalsIgnoreCase("Carbon Footprint")) {
            query = "select max(produced_date) UpdateDate from CARBON_FOOTPRINT\n" +
                    "where bvd9_number = " + orbisId;
        } else if (researchLine.equalsIgnoreCase("Brown Share Assessments")) {
            query = "select max(produced_date) UpdateDate from BROWN_SHARE where bvd9_number = " + orbisId;
        } else if (researchLine.equalsIgnoreCase("Green Share Assessments")) {
            query = "select max(produced_date) UpdateDate from GREEN_SHARE\n" +
                    "where bvd9_number = " + orbisId;
        }
        //This is for another test case
        if (researchLine.equalsIgnoreCase("Brown Share")) {
            query = "select * from GREEN_SHARE As C where bvd9_number =" + orbisId + " and C.Year ='2022' and C.Month ='08'";
        } else if (researchLine.equalsIgnoreCase("Green Share")) {
            query = "select \n" +
                    "GS_BUILDING_MATERIALS_WOOD_ESTIMATE_OF_INCORPORATION_SOURCE SOURCE,\n" +
                    "GS_BUILDING_MATERIALS_WOOD_ESTIMATE_OF_INCORPORATION MAIN,\n" +
                    "GS_BUILDING_MATERIALS_WOOD_SCALE_OF_INCORPORATION SCALE\n" +
                    "from GREEN_SHARE As C where bvd9_number =039634868 and C.Year ='2022' and C.Month ='08'";
        }

        List<String> result = getQueryResultListUnderlyingData(query);
        System.out.println("result = " + result);
        return result;
    }

    public static List<String> getCoverageForGreenShare(String orbisId, String researchLine) {
        String query = "";
        List<String> result=new ArrayList<>();
        if (researchLine.equalsIgnoreCase("Brown Share")) {
            query = "select BS_FOSF_TAR_SAND_N_OIL_SHALE_EXTRACTION_SERVICES_THRESHOLD THRESHOLD,\n" +
                    "BS_FOSF_TAR_SAND_N_OIL_SHALE_EXTRACTION_SERVICES_SOURCE SOURCE,\n" +
                    "BS_FOSF_TAR_SAND_N_OIL_SHALE_EXTRACTION_SERVICES SERVICES,\n" +
                    "BS_FOSF_METHANE_HYDRATES_SOURCE METHANE_HYDRATES,\n"+
                    "BS_FOSF_LIQUEFIED_NATURAL_GAS_SOURCE LIQUEFIED_NATURAL_GAS\n"+
                    "from brown_share\n" +
                    "where bvd9_number = "+orbisId+"\n" +
                    "and AS_OF_DATE = ( select MAX(AS_OF_DATE) from brown_share where bvd9_number = "+orbisId+" )";
            result = getQueryResultListUDBrownShare(query);
        } else if (researchLine.equalsIgnoreCase("Green Share")) {
            query = "select \n" +
                    "GS_BUILDING_MATERIALS_WOOD_ESTIMATE_OF_INCORPORATION_SOURCE SOURCE,\n" +
                    "GS_BUILDING_MATERIALS_WOOD_ESTIMATE_OF_INCORPORATION MAIN,\n" +
                    "GS_BUILDING_MATERIALS_WOOD_SCALE_OF_INCORPORATION SCALE\n" +
                    "from GREEN_SHARE As C where bvd9_number =039634868 and C.Year ='2022' and C.Month ='08'";
            result = getQueryResultListUDGreenShare(query);
        }
        System.out.println("result = " + result);
        return result;
    }

    public static List<String> getExportOrSourceDocumentsFromDB(String orbisId) {
        String query = "select DOCUMENT_NAME from MESGC_DATAPOINTS where BVD9_NUMBER ='"+orbisId+"' \n" +
                " and DOCUMENT_NAME != 'NA' QUALIFY row_number() over (partition by (\"DOCUMENT_NAME\" ,\"DOCUMENT_URL\",internal_document_name) ORDER BY METRIC_YEAR desc) =1\n" +
                " ORDER BY METRIC_YEAR desc";
        List<String> docsList=new ArrayList<>();
        List<List<Object>> result = getQueryResultList(query);
        for(List<Object> obj:result){
            docsList.add(obj.get(0).toString());
        }
        return docsList;
    }

    public static List<String> getHeaderDB(String orbisId) {
        String query = "SELECT esg.value Disclosure_Rate\n" +
                "FROM DF_TARGET.ESG_OVERALL_SCORES ESG  \n" +
                "JOIN DF_TARGET.ENTITY_COVERAGE_TRACKING CT ON ESG.ORBIS_ID = CT.ORBIS_ID \n" +
                "AND CT.COVERAGE_STATUS = 'Published' AND PUBLISH = 'yes'\n" +
                "WHERE ESG.ORBIS_ID in ('" + orbisId + "')\n" +
                "AND ESG.DATA_TYPE IN('information_rate_global')\n" +
                "QUALIFY ROW_NUMBER() OVER(PARTITION BY ESG.ORBIS_ID,DATA_TYPE ORDER BY SCORED_DATE DESC) = 1;";
        System.out.println("query = " + query);
        List<String> result = getQueryResultLisDisclosure(query);
        return result;
    }


    public static List<String> getESGDbScores(String orbisId) {
        String query = " select value from esg_overall_scores \n" +
                " where orbis_id=" + orbisId + " \n" +
                " AND (\n" +
                "        sub_category='Environmental' \n" +
                "        or sub_category='Governance'\n" +
                "        or sub_category='Social' \n" +
                "        or sub_category='ESG'\n" +
                "    ) \n" +
                "   and data_type='esg_pillar_score' ";

        List<String> result = getQueryResultListESGScores(query);
        return result;
    }

    public static Map<String, Object> getEntityHeaderDetails(String orbisId) {
        String query = "with p as (select ENTITY_NAME_BVD,BVD_ID_NUMBER,ORBIS_ID,COUNTRY_CODE,LEI, ISIN, MESG_SECTOR_ID\n" +
                "from df_target.ESG_ENTITY_MASTER eem  left join DF_DERIVED.ISIN_SORTED di on eem.ORBIS_ID = di.BVD9_NUMBER and PRIMARY_ISIN = 'Y'\n" +
                "where ORBIS_ID = '" + orbisId + "' ),\n" +
                "ems as (select eos.RESEARCH_LINE_ID, p.ORBIS_ID  from entity_coverage_tracking ect\n" +
                "join p  on ect.orbis_id=p.orbis_id \n" +
                "join ESG_OVERALL_SCORES eos on ect.orbis_id=eos.orbis_id and data_type = 'esg_pillar_score' and sub_category = 'ESG' and eos.year || eos.month <= '202208'\n" +
                "where coverage_status = 'Published' and publish = 'yes')\n" +
                "select p.ENTITY_NAME_BVD, p.ORBIS_ID,p.COUNTRY_CODE,p.LEI, p.ISIN, ems.RESEARCH_LINE_ID,\n" +
                "case when ems.RESEARCH_LINE_ID=1008 then  ve.GENERIC_SECTOR\n" +
                "when ems.RESEARCH_LINE_ID=1015 then  L1_SECTOR end as sector\n" +
                ",SH.MESG_SECTOR_ID from p join ems on ems.ORBIS_ID = p.ORBIS_ID\n" +
                "join DF_TARGET.SECTOR_HIERARCHY SH on SH.MESG_SECTOR_ID = p.MESG_SECTOR_ID\n" +
                "left join DF_TARGET.ESG_VE_SCORES VE on vE.ORBIS_ID = p.ORBIS_ID\n" +
                "qualify row_number() OVER (PARTITION BY p.orbis_id ORDER BY RESEARCH_LINE_ID) =1";
        System.out.println("query = " + query);
        return getQueryResultMap(query).get(0);
    }

    public static String getUpdatedDateforPhysicalRiskManagement(String orbisId) {

        String query = "select TO_VARCHAR(PRODUCED_DATE) as PRODUCED_DATE  from PHYSICAL_RISK_MANAGEMENT where bvd9_number in\n" +
                "(Select BVD9_ID from VW_ENTITY_DATA where bvd_ID in\n" +
                "(select bvd_id_number from ESG_ENTITY_MASTER where orbis_id='" + orbisId + "') )\n" +
                " order by produced_date desc limit  1";
        try {
            return getQueryResultMap(query).get(0).get("PRODUCED_DATE").toString();
        } catch (Exception e) {
            return "";
        }

    }


    public static String getUpdatedDateforPhysicalClimateHazard(String orbisId) {
        String query = " Select * from ENTITY_SCORE where entity_id_bvd9 in (Select BVD9_ID from VW_ENTITY_DATA where bvd_ID in\n" +
                "(select bvd_id_number from ESG_ENTITY_MASTER where orbis_id='" + orbisId + "') )\n" +
                " order by Release_date desc limit 1;";
        try {
            return getQueryResultMap(query).get(0).get("RELEASE_DATE").toString();
        } catch (Exception e) {
            return "";
        }
    }

    public Map<String, Object> getBrownShareAssessmentDataForEntity(String orbisID) {
        String query = "select top 1 * from  DF_TARGET.BROWN_SHARE where bvd9_NUMBER = "+orbisID+" order by year desc, month desc";
        return getQueryResultMap(query).get(0);
    }
}
