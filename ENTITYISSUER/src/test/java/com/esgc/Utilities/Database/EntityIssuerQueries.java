package com.esgc.Utilities.Database;

import com.esgc.DBModels.EntityIssuerPageDBModels.P3ControversiesDBModel;
import com.esgc.DBModels.EntityIssuerPageDBModels.P3HeaderIdentifiersDBModel;
import com.esgc.DBModels.EntityIssuerPageDBModels.SourceDocumentDBModel;
import com.esgc.DBModels.EntityIssuerPageDBModels.SummaryWidgetDBModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.Database.DatabaseDriver.getQueryResultMap;
import static com.esgc.Utilities.Database.DatabaseDriver.getRowMap;

public class EntityIssuerQueries {
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
}
