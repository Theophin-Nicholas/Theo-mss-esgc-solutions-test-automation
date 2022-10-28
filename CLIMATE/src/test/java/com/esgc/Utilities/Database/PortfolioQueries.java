package com.esgc.Utilities.Database;

import com.esgc.DBModels.*;
import com.esgc.DBModels.EntityPage.PhysicalScore;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.esgc.Utilities.Database.DatabaseDriver.getQueryResultList;
import static com.esgc.Utilities.Database.DatabaseDriver.getQueryResultMap;
import static com.esgc.Utilities.PortfolioUtilities.randomBetween;

public class PortfolioQueries {

    public List<ResearchLineIdentifier> getIdentifiersNotInResearchLine(String researchLine, String month, String year) {
        IdentifierQueryModel queryModel = IdentifierQueryModelFactory.getIdentifierQueryModel(researchLine, month, year);
        assert queryModel != null;

        String query = getQueryForIdentifiersNotInResearchline(queryModel);
        System.out.println(query);

        return serializeResearchLines(getQueryResultMap(query));
    }

    public List<ResearchLineIdentifier> getIdentifiersNotInResearchLine(String researchLine) {
        IdentifierQueryModel queryModel = IdentifierQueryModelFactory.getIdentifierQueryModel(researchLine);
        assert queryModel != null;

        String query = getQueryForIdentifiersNotInResearchline(queryModel);
        System.out.println(query);

        return serializeResearchLines(getQueryResultMap(query));
    }

    public List<ResearchLineIdentifier> getIdentifiersWithPositiveScores(String researchLine) {
        IdentifierQueryModel queryModel = IdentifierQueryModelFactory.getIdentifierQueryModel(researchLine);
        assert queryModel != null;

        String query = getQueryForResearchline(queryModel, "SCORE >= 0");
        System.out.println(query);

        return serializeResearchLines(getQueryResultMap(query));
    }

    public List<ResearchLineIdentifier> getIdentifiersWithPositiveScores(String researchLine, String month, String year) {
        IdentifierQueryModel queryModel = IdentifierQueryModelFactory.getIdentifierQueryModel(researchLine, month, year);
        assert queryModel != null;

        String query = getQueryForResearchline(queryModel, "SCORE >= 0");
        System.out.println(query);

        return serializeResearchLines(getQueryResultMap(query));
    }

    public List<ResearchLineIdentifier> getIdentifiersWithNegativeScores(String researchLine) {
        IdentifierQueryModel queryModel = IdentifierQueryModelFactory.getIdentifierQueryModel(researchLine);
        assert queryModel != null;

        String query = getQueryForResearchline(queryModel, "SCORE < 0");
        System.out.println(query);

        return serializeResearchLines(getQueryResultMap(query));
    }

    public List<ResearchLineIdentifier> getIdentifiersWithNegativeScores(String researchLine, String month, String year) {
        IdentifierQueryModel queryModel = IdentifierQueryModelFactory.getIdentifierQueryModel(researchLine, month, year);
        assert queryModel != null;

        String query = getQueryForResearchline(queryModel, "SCORE < 0");
        System.out.println(query);

        return serializeResearchLines(getQueryResultMap(query));
    }


    public List<ResearchLineIdentifier> getIdentifiersWithNullScores(String researchLine, String month, String year) {
        IdentifierQueryModel queryModel = IdentifierQueryModelFactory.getIdentifierQueryModel(researchLine, month, year);
        assert queryModel != null;

        String query = getQueryForResearchline(queryModel, "SCORE IS NULL");
        System.out.println(query);

        return serializeResearchLines(getQueryResultMap(query));
    }

    public List<ResearchLineIdentifier> getIdentifiersWithNullScores(String researchLine) {
        IdentifierQueryModel queryModel = IdentifierQueryModelFactory.getIdentifierQueryModel(researchLine);
        assert queryModel != null;

        String query = getQueryForResearchline(queryModel, "SCORE IS NULL");
        System.out.println(query);

        return serializeResearchLines(getQueryResultMap(query));
    }

    //TODO For now, predicted scores are only for ESG
    public List<ResearchLineIdentifier> getIdentifiersWithPredictedScores(String researchLine, String month, String year) {
        IdentifierQueryModel queryModel = IdentifierQueryModelFactory.getIdentifierQueryModel("ESG", month, year);
        assert queryModel != null;

        String query = getQueryForIdentifiersNotInResearchline(queryModel);
        System.out.println(query);

        return serializeResearchLines(getQueryResultMap(query));
    }

    public List<Map<String, Object>> getIdentifiersWithBVD9(String bvd9_number) {
        String query = "SELECT ISIN, BBG_TICKER, SEDOL_CODE_PRIMARY, SEDOL_CODE_PRIMARY_GBR, SEDOL_2_CODE, SEDOL_2_CODE_GBR, SEDOL_3_CODE, SEDOL_3_CODE_GBR \n" +
                "FROM ENTITY_SECURITY_IDENTIFIERS WHERE ENTITY_ID_BVD9 = %s ORDER BY random();";

        return getQueryResultMap(String.format(query, bvd9_number));
    }

    public List<PhysicalRiskManagementIdentifier> getPhysicalRiskManagementIdentifiers(String conditionClause) {
        String query = "SELECT BVD9_NUMBER, ISIN, BBG_TICKER, SEDOL_CODE_PRIMARY, GS_PH_RISK_MGT_LEADERSHIP, " +
                "    GS_PH_RISK_MGT_IMPLEMENTATION, GS_PH_RISK_MGT_RESULTS, GS_PH_RISK_MGT_TOTAL " +
                "    FROM PHYSICAL_RISK_MANAGEMENT rl " +
                "    JOIN ENTITY_SECURITY_IDENTIFIERS esi ON esi.ENTITY_ID_BVD9 = rl.BVD9_NUMBER " +
                "    %s" +
                "    ORDER BY random() " +
                "    LIMIT 100;";
        query = String.format(query, conditionClause);

        List<PhysicalRiskManagementIdentifier> list = new ArrayList<>();
        for (Map<String, Object> each : getQueryResultMap(query)) {
            PhysicalRiskManagementIdentifier physicalRiskMgmtId =
                    new PhysicalRiskManagementIdentifier();
            physicalRiskMgmtId.setISIN(each.get("ISIN").toString());
            physicalRiskMgmtId.setBBG_Ticker(each.get("BBG_TICKER").toString());
            physicalRiskMgmtId.setSEDOL_CODE_PRIMARY(each.get("SEDOL_CODE_PRIMARY").toString());
            physicalRiskMgmtId.setGS_PH_RISK_MGT_IMPLEMENTATION((Double) each.get("GS_PH_RISK_MGT_IMPLEMENTATION"));
            physicalRiskMgmtId.setGS_PH_RISK_MGT_LEADERSHIP((Double) each.get("GS_PH_RISK_MGT_LEADERSHIP"));
            physicalRiskMgmtId.setGS_PH_RISK_MGT_TOTAL((Double) each.get("GS_PH_RISK_MGT_TOTAL"));
            physicalRiskMgmtId.setGS_PH_RISK_MGT_RESULTS((Double) each.get("GS_PH_RISK_MGT_RESULTS"));
            physicalRiskMgmtId.setValue(randomBetween(1, Integer.MAX_VALUE));

            list.add(physicalRiskMgmtId);
        }
        return list;
    }

    public List<EnergyTransitionManagement> getEnergyTransitionManagementIdentifiers(String conditionClause) {
        String query = "SELECT BVD9_NUMBER, ISIN, BBG_TICKER, SEDOL_CODE_PRIMARY, GS_ENERGY_TRANSITION_LEADERSHIP, " +
                "    GS_ENERGY_TRANSITION_RESULTS, GS_ENERGY_TRANSITION_TOTAL, GS_ENERGY_TRANSITION_IMPLEMENTATION " +
                "    FROM ENERGY_TRANSITION rl " +
                "    JOIN ENTITY_SECURITY_IDENTIFIERS esi ON esi.ENTITY_ID_BVD9 = rl.BVD9_NUMBER " +
                "    %s" +
                "    ORDER BY random() " +
                "    LIMIT 100;";
        query = String.format(query, conditionClause);

        List<EnergyTransitionManagement> list = new ArrayList<>();
        for (Map<String, Object> each : getQueryResultMap(query)) {
            EnergyTransitionManagement energyTransitionManagement =
                    new EnergyTransitionManagement();
            energyTransitionManagement.setISIN(each.get("ISIN").toString());
            energyTransitionManagement.setBBG_Ticker(each.get("BBG_TICKER").toString());
            energyTransitionManagement.setSEDOL_CODE_PRIMARY(each.get("SEDOL_CODE_PRIMARY").toString());
            energyTransitionManagement.setGS_ENERGY_TRANSITION_LEADERSHIP((Double) each.get("GS_ENERGY_TRANSITION_LEADERSHIP"));
            energyTransitionManagement.setGS_ENERGY_TRANSITION_RESULTS((Double) each.get("GS_ENERGY_TRANSITION_RESULTS"));
            energyTransitionManagement.setGS_ENERGY_TRANSITION_TOTAL((Double) each.get("GS_ENERGY_TRANSITION_TOTAL"));
            energyTransitionManagement.setGS_ENERGY_TRANSITION_IMPLEMENTATION((Double) each.get("GS_ENERGY_TRANSITION_IMPLEMENTATION"));
            energyTransitionManagement.setValue(randomBetween(1, Integer.MAX_VALUE));

            list.add(energyTransitionManagement);
        }
        return list;
    }

    public List<TCFDIdentifier> getTCFDIdentifiers(String conditionClause) {
        String query = "SELECT BVD9_NUMBER, ISIN, BBG_TICKER, SEDOL_CODE_PRIMARY, GS_TCFD_STGY_LEADERSHIP, " +
                "    GS_TCFD_STGY_RESULTS, GS_TCFD_STGY_TOTAL, GS_TCFD_STGY_IMPLEMENTATION " +
                "    FROM TCFD_STRATEGY rl " +
                "    JOIN ENTITY_SECURITY_IDENTIFIERS esi ON esi.ENTITY_ID_BVD9 = rl.BVD9_NUMBER " +
                "    %s" +
                "    ORDER BY random() " +
                "    LIMIT 100;";
        query = String.format(query, conditionClause);

        List<TCFDIdentifier> list = new ArrayList<>();
        for (Map<String, Object> each : getQueryResultMap(query)) {
            TCFDIdentifier tcfdIdentifier =
                    new TCFDIdentifier();
            tcfdIdentifier.setISIN(each.get("ISIN").toString());
            tcfdIdentifier.setBBG_Ticker(each.get("BBG_TICKER").toString());
            tcfdIdentifier.setSEDOL_CODE_PRIMARY(each.get("SEDOL_CODE_PRIMARY").toString());
            tcfdIdentifier.setGS_TCFD_STGY_LEADERSHIP((Double) each.get("GS_TCFD_STGY_LEADERSHIP"));
            tcfdIdentifier.setGS_TCFD_STGY_RESULTS((Double) each.get("GS_TCFD_STGY_RESULTS"));
            tcfdIdentifier.setGS_TCFD_STGY_TOTAL((Double) each.get("GS_TCFD_STGY_TOTAL"));
            tcfdIdentifier.setGS_TCFD_STGY_IMPLEMENTATION((Double) each.get("GS_TCFD_STGY_IMPLEMENTATION"));
            tcfdIdentifier.setValue((randomBetween(1, Integer.MAX_VALUE)));

            list.add(tcfdIdentifier);
        }
        return list;
    }

    private List<ResearchLineIdentifier> serializeResearchLines(List<Map<String, Object>> resultSet) {
        List<ResearchLineIdentifier> list = new ArrayList<>();
        for (Map<String, Object> each : resultSet) {
            ResearchLineIdentifier researchLineIdentifier = new ResearchLineIdentifier();
            researchLineIdentifier.setISIN(each.get("ISIN").toString());
            researchLineIdentifier.setBBG_Ticker(each.get("BBG_TICKER").toString());
            researchLineIdentifier.setSCORE(Double.valueOf(each.get("SCORE").toString()));
            researchLineIdentifier.setCOUNTRY_CODE(each.get("COUNTRY_ISO_CODE").toString());
            researchLineIdentifier.setWORLD_REGION(each.get("WORLD_REGION").toString());
            researchLineIdentifier.setBVD9_NUMBER(each.get("BVD9_NUMBER").toString());
            researchLineIdentifier.setPLATFORM_SECTOR(each.get("SECTOR_NAME").toString());
            researchLineIdentifier.setCOMPANY_NAME(each.get("COMPANY_NAME").toString());
            researchLineIdentifier.setPREVIOUS_PRODUCED_DATE(Optional.ofNullable(each.get("PREVIOUS_PRODUCED_DATE")).orElse("").toString());
            researchLineIdentifier.setPREVIOUS_SCORE(Double.valueOf(Optional.ofNullable(each.get("PREVIOUS_SCORE")).orElse("0").toString()));
            researchLineIdentifier.setValue(randomBetween(1000, 10000000));

            try {
                researchLineIdentifier.setResearchLineIdForESGModel(each.get("RESEARCH_LINE_ID").toString());
            } catch (Exception e) {

            }

            list.add(researchLineIdentifier);
        }

        return list;
    }

    private Double getDoubleValueOf(String value) {
        Double result = value == null ? null : Double.valueOf(value);
        return result;
    }

    private List<EntityWithScores> serializeEntities(List<Map<String, Object>> resultSet) {
        List<EntityWithScores> list = new ArrayList<>();
        for (Map<String, Object> each : resultSet) {
            EntityWithScores entityWithScores = new EntityWithScores();
            entityWithScores.setBVD9_NUMBER(each.get("BVD9_NUMBER").toString());
            entityWithScores.setCOMPANY_NAME(each.get("COMPANY_NAME").toString());
            entityWithScores.setVALUE(Integer.valueOf(each.get("VALUE").toString()));
            entityWithScores.setWORLD_REGION(each.get("WORLD_REGION").toString());
            entityWithScores.setSECTOR(each.get("SECTOR").toString());

            entityWithScores.setOPERATIONS_RISK_SCORE(getDoubleValueOf(each.get("OPERATIONS_RISK_SCORE") == null ? null : each.get("OPERATIONS_RISK_SCORE").toString()));
            entityWithScores.setMARKET_RISK_SCORE(getDoubleValueOf(each.get("MARKET_RISK_SCORE") == null ? null : each.get("MARKET_RISK_SCORE").toString()));
            entityWithScores.setSUPPLY_CHAIN_RISK_SCORE(getDoubleValueOf(each.get("SUPPLY_CHAIN_RISK_SCORE") == null ? null : each.get("SUPPLY_CHAIN_RISK_SCORE").toString()));
            entityWithScores.setTEMPERATURE_ALIGNMENT(getDoubleValueOf(each.get("TEMPERATURE_ALIGNMENT_SCORE") == null ? null : each.get("TEMPERATURE_ALIGNMENT_SCORE").toString()));
            entityWithScores.setCARBON_SCORE(getDoubleValueOf(each.get("CARBON_SCORE") == null ? null : each.get("CARBON_SCORE").toString()));
            entityWithScores.setBROWN_SHARE_SCORE(getDoubleValueOf(each.get("BROWN_SHARE_SCORE") == null ? null : each.get("BROWN_SHARE_SCORE").toString()));
            entityWithScores.setGREEN_SHARE_SCORE(getDoubleValueOf(each.get("GREEN_SHARE_SCORE") == null ? null : each.get("GREEN_SHARE_SCORE").toString()));


            list.add(entityWithScores);
        }

        return list;
    }

    //return queries that can get the right table names with a given value
    private String getQueryForResearchline(IdentifierQueryModel queryModel, String scoreModifier) {
        String model = "";
        if(queryModel.getTableName().contains("ESG")){
            model = "          rl.Research_line_id,";
        }
        return "WITH rl AS\n" +
                "(\n" +
                "       SELECT " + queryModel.getEntityIdColumnName() + " AS bvd9_number,\n" +
                "              " + queryModel.getPreviousScoreColumnName() + "             AS previous_score,\n" +
                "              " + queryModel.getPreviusProducedDateColumnName() + "             AS previous_produced_date,\n" +
                "              " + queryModel.getScoreColumnName() + "             AS score\n" +
                "       FROM   " + queryModel.getTableName() + " )\n" +
                "SELECT    rl.bvd9_number,\n" +
                "          nvl2(score,score, -999) AS score,\n" +
                "          country_code          AS country_iso_code,\n" +
                "          entity_proper_name    AS company_name,\n" +
                "          l1_sector             AS sector_name,\n" +
                model +
                "          world_region,\n" +
                "          isin,\n" +
                "          bbg_ticker,\n" +
                "          previous_score,\n" +
                "          previous_produced_date\n" +
                "FROM      esg_entity_master e\n" +
                "LEFT JOIN      rl\n" +
                "ON        e.orbis_id = rl.bvd9_number\n" +
                "LEFT JOIN factset_moodys_ds.sym_v1.sym_sec_entity sse\n" +
                "ON        e.factset_entity_id = sse.factset_entity_id\n" +
                "LEFT JOIN sector_hierarchy s\n" +
                "ON        e.mesg_sector_id = s.mesg_sector_id\n" +
                "JOIN\n" +
                "          (\n" +
                "                 SELECT *\n" +
                "                 FROM   (\n" +
                "                                 SELECT   dfi.*,\n" +
                "                                          row_number() OVER (partition BY dfi.bvd9_number ORDER BY random()) rownum\n" +
                "                                 FROM     df_derived.isin_sorted dfi\n" +
                "                                 JOIN     rl\n" +
                "                                 ON       dfi.bvd9_number=rl.bvd9_number) shuffled\n" +
                "                 WHERE  rownum <=10 ) i\n" +
                "ON        i.fsym_id = sse.fsym_id\n" +
                "AND       i.bvd9_number=rl.bvd9_number\n" +
                "JOIN\n" +
                "          (\n" +
                "                 SELECT *\n" +
                "                 FROM   (\n" +
                "                                 SELECT   dfi.*,\n" +
                "                                          row_number() OVER (partition BY dfi.bvd9_number ORDER BY random()) rownum\n" +
                "                                 FROM     df_derived.bbg_sorted dfi\n" +
                "                                 JOIN     rl\n" +
                "                                 ON       dfi.bvd9_number=rl.bvd9_number) shuffled\n" +
                "                 WHERE  rownum <=10 ) bbg\n" +
                "ON        bbg.fsym_id = sse.fsym_id\n" +
                "AND       bbg.bvd9_number=rl.bvd9_number\n" +
                "LEFT JOIN factset_moodys_ds.sym_v1.sym_coverage sc\n" +
                "ON        sc.fsym_id = sse.fsym_id\n" +
                "WHERE     " + scoreModifier + "\n" +
                "AND       isin IS NOT NULL\n" +
                "AND       e.ENTITY_STATUS='Active'\n" +
                "AND       bbg_ticker IS NOT NULL\n" +
                "AND       rl.bvd9_number IS NOT NULL\n" +
                "AND       world_region IS NOT NULL\n" +
                "AND       world_region != 'UNKN'\n" +
                "AND       world_region != 'GLBL'\n" +
                "AND       sc.active_flag = 'True'\n" +
                "AND       isin NOT LIKE '%#%'\n" +
                "AND       isin NOT LIKE '%*%'\n" +
                "AND       isin NOT LIKE '%@%'\n" +
                "AND       bbg_ticker NOT LIKE '%/%'\n" +
                "AND       bbg_ticker NOT LIKE '%#%'\n" +
                "AND       bbg_ticker NOT LIKE '%*%'\n" +
                "AND       bbg_ticker NOT LIKE '%@%'\n" +
                "AND       bbg_ticker NOT LIKE '%-%'\n" +
                "ORDER BY  random() limit 100;";
    }

    private String getQueryForIdentifiersNotInResearchline(IdentifierQueryModel queryModel) {
        String table;

        if (queryModel.getTableName().contains("ESG")) {
            table = "ESG_OVERALL_SCORES EOS";
        } else {
            table = queryModel.getTableName().substring(0, queryModel.getTableName().indexOf("WHERE"));
        }
        return "    SELECT i.BVD9_NUMBER, -1111 as SCORE, country_code as COUNTRY_ISO_CODE, " +
                "               ENTITY_PROPER_NAME as COMPANY_NAME, L1_SECTOR as SECTOR_NAME, WORLD_REGION  , ISIN , BBG_TICKER" +
                "    FROM ESG_ENTITY_MASTER e " +
                "    left join FACTSET_MOODYS_DS.SYM_V1.SYM_SEC_ENTITY sse on e.factset_entity_id = sse.factset_entity_id \n " +
                "    left join SECTOR_HIERARCHY s on e.mesg_sector_id = s.mesg_sector_id " +
                " LEFT JOIN factset_moodys_ds.sym_v1.sym_coverage sc\n" +
                " ON        sc.fsym_id = sse.fsym_id\n" +
                //  "    left join FACTSET_MOODYS_DS.SYM_V1.SYM_BBG bbg on bbg.fsym_id = sse.fsym_id\n " +
                //  "    left join FACTSET_MOODYS_DS.SYM_V1.SYM_SEDOL sedol on sedol.fsym_id = sse.fsym_id " +
                "    left join (SELECT * FROM   (SELECT   *,Row_number() OVER (partition BY BVD9_number ORDER BY Random()) rownum FROM     DF_DERIVED.ISIN_SORTED ) shuffled WHERE  rownum = 10 ) i on i.fsym_id = sse.fsym_id" +
                "    left join (SELECT * FROM   (SELECT   *,Row_number() OVER (partition BY BVD9_number ORDER BY Random()) rownum FROM     DF_DERIVED.BBG_SORTED ) shuffled WHERE  rownum = 10 ) bbg on bbg.fsym_id = sse.fsym_id" +
                "    WHERE i.bvd9_number NOT IN " +
                "(SELECT " + queryModel.getEntityIdColumnName() + " FROM " + table + ") " +
                "    AND bbg.bvd9_number NOT IN " +
                "(SELECT " + queryModel.getEntityIdColumnName() + " FROM " + table + ") " +
                "AND ISIN is not null AND WORLD_REGION is not null   " +
                "AND       world_region != 'UNKN'\n" +
                "AND       world_region != 'GLBL'\n" +
                "  AND BBG_TICKER is not null  " +
                "    AND ISIN NOT LIKE '%#%' AND ISIN NOT LIKE '%*%' AND ISIN NOT LIKE '%@%'" +
                "    AND BBG_TICKER NOT LIKE '%/%' AND BBG_TICKER NOT LIKE '%-%' AND BBG_TICKER NOT LIKE '%#%' AND BBG_TICKER NOT LIKE '%*%' AND BBG_TICKER NOT LIKE '%@%'" +
                "    AND       e.ENTITY_STATUS='Active'\n" +
                "    AND       sc.active_flag = 'True'\n" +
                "    LIMIT 50;";

    }

    public int getCarbonFootPrintIntensityScore(String portfolioId, String month, String year) {
        String query = "WITH t AS\n" +
                "(\n" +
                "       SELECT Sum(value) total_inv\n" +
                "       FROM   df_target.df_portfolio df\n" +
                "       JOIN   df_target.CARBON_FOOTPRINT c on c.bvd9_number=df.bvd9_number " +
                "       AND CARBON_FOOTPRINT_VALUE_TOTAL IS NOT NULL AND MONTH='" + month + "' AND YEAR='" + year + "'\n" +
                "       WHERE  portfolio_id = '%p'), p AS\n" +
                "(\n" +
                "         SELECT   p.bvd9_number,\n" +
                "                  p.region,\n" +
                "                  p.region_name,\n" +
                "                  p.sector,\n" +
                "                  p.company_name,\n" +
                "                  p.as_of_date,\n" +
                "                  p.portfolio_id,\n" +
                "                  Sum(p.value) OVER(partition BY p.bvd9_number) value,\n" +
                "                  t.total_inv                                   total_value\n" +
                "         FROM     df_target.df_portfolio p\n" +
                "         JOIN     t\n" +
                "         ON       1=1\n" +
                "         JOIN\n" +
                "                  (\n" +
                "                           SELECT   cf.bvd9_number,\n" +
                "                                    cf.carbon_footprint_value_total,\n" +
                "                                    fi.sales,\n" +
                "                                    cf.year,\n" +
                "                                    cf.month,\n" +
                "                                    fi.fiscal_month_end_date,\n" +
                "                                    fi.local_currency\n" +
                "                           FROM     df_target.carbon_footprint cf\n" +
                "                           JOIN     df_lookup.financial_info fi\n" +
                "                           ON       cf.bvd9_number = fi.bvd9_number\n" +
                "                           AND      fi.sales IS NOT NULL\n" +
                "                           AND      cf.year\n" +
                "                                             || cf.month >= To_char(fi.fiscal_month_end_date, 'YYYYMM')\n" +
                "                           AND      cf.year = '%y'\n" +
                "                           AND      cf.month ='%m' qualify row_number() OVER(partition BY cf.bvd9_number ORDER BY cf.carbon_footprint_value_total DESC nulls last)=1 ) q\n" +
                "         ON       q.bvd9_number=p.bvd9_number\n" +
                "         AND      p.bvd9_number IS NOT NULL\n" +
                "         WHERE    p.portfolio_id ='%p'\n" +
                "         AND      p.bvd9_number IS NOT NULL qualify row_number() OVER(partition BY p.bvd9_number ORDER BY p.region_name nulls last)=1 ) , ex_rate AS\n" +
                "(\n" +
                "         SELECT   rt.fromcurrcode,\n" +
                "                  rt.tocurrcode,\n" +
                "                  rt.exratedate,\n" +
                "                  rt.midrate\n" +
                "         FROM     df_lookup.fx_rate rt\n" +
                "         WHERE    rt.tocurrcode = 'USD'\n" +
                "         AND      rt.exratedate <=\n" +
                "                  (\n" +
                "                         SELECT max(as_of_date)\n" +
                "                         FROM   p) qualify row_number() OVER(partition BY rt.fromcurrcode,rt.tocurrcode ORDER BY rt.exratedate DESC nulls last) = 1 )\n" +
                "SELECT    cf.bvd9_number,\n" +
                "          p.value,\n" +
                "          total_inv,\n" +
                "          fi.sales,\n" +
                "          fi.local_currency,\n" +
                "          fi.fiscal_month_end_date financial_info_date ,\n" +
                "          carbon_footprint_value_total ,\n" +
                "          CASE\n" +
                "                    WHEN fi.local_currency != 'USD' THEN div0(fi.sales, ex_rate.midrate)\n" +
                "                    ELSE fi.sales\n" +
                "          END sales_usd,\n" +
                "          IFNULL((p.value/total_inv)*(carbon_footprint_value_total/NULLIF(sales_usd,0)),0) as caluculatedValue\n" +
                "          \n" +
                "FROM      df_target.carbon_footprint cf\n" +
                "JOIN      t\n" +
                "ON        1=1\n" +
                "JOIN      p\n" +
                "ON        cf.bvd9_number=p.bvd9_number\n" +
                "AND       p.bvd9_number IS NOT NULL\n" +
                "JOIN      df_lookup.financial_info fi\n" +
                "ON        cf.bvd9_number = fi.bvd9_number\n" +
                "AND       cf.year\n" +
                "                    || cf.month >= to_char(fi.fiscal_month_end_date, 'YYYYMM')\n" +
                "AND       cf.year = '%y'\n" +
                "AND       cf.month = '%m'\n" +
                "LEFT JOIN ex_rate\n" +
                "ON        fi.local_currency = ex_rate.fromcurrcode\n" +
                "AND       ex_rate.tocurrcode = 'USD'\n" +
                "WHERE     cf.carbon_footprint_value_total IS NOT NULL\n" +
                "AND       fi.sales IS NOT NULL\n" +
                "AND       cf.year = '%y'\n" +
                "AND       cf.month ='%m' qualify row_number() OVER(partition BY cf.bvd9_number ORDER BY fi.fiscal_month_end_date DESC) = 1 ;";

        query = query.replaceAll("%y", year);
        query = query.replaceAll("%m", month);
        query = query.replaceAll("%p", portfolioId);

        List<BigDecimal> value = new ArrayList<>();
        for (Map<String, Object> rs : getQueryResultMap(query)) {

            value.add((BigDecimal) rs.get("CALUCULATEDVALUE"));
        }
        System.out.println(query);

        return value.stream().reduce(BigDecimal.ZERO, BigDecimal::add).setScale(0, RoundingMode.HALF_UP).intValue();
        //Integer.valueOf(value.stream().reduce(BigDecimal.ZERO,BigDecimal::add).intValue());


    }

    public List<Map<String, Object>> getCompanyScoresInResearchLineWithPortfolioID(String researchLine, String month, String year, String portfolioId) {

        IdentifierQueryModel queryModel = IdentifierQueryModelFactory.getIdentifierQueryModel(researchLine, month, year);
        assert queryModel != null;
        if(researchLine.equals("ESG")){
            String query = " with p as (\n" +
                    "     select * from df_portfolio df where portfolio_id='00000000-0000-0000-0000-000000000000'\n" +
                    " ),\n" +
                    "  esg as (\n" +
                    "    select eos.* from entity_coverage_tracking ect  \n" +
                    "join ESG_OVERALL_SCORES eos on ect.orbis_id=eos.orbis_id and data_type = 'esg_pillar_score'  and sub_category = 'ESG' and eos.year || eos.month <= '202209' and eos.value>70//and eos.RESEARCH_LINE_ID=1015\n" +
                    " where  coverage_status = 'Published' and publish = 'yes'\n" +
                    " qualify row_number() OVER (PARTITION BY eos.orbis_id ORDER BY eos.year DESC, eos.month DESC, eos.scored_date DESC) =1\n" +
                    "    )\n" +
                    "     select esg.orbis_id as BVD9_NUMBER, esg.value as SCORE from p\n" +
                    "    join esg on p.bvd9_number=esg.orbis_id; ";

            System.out.println(query);

            return getQueryResultMap(query);
        }
        String tableName = queryModel.getTableName().substring(0, queryModel.getTableName().indexOf("WHERE"));
        String dateFilter = queryModel.getTableName().substring(queryModel.getTableName().indexOf("WHERE") + 5);
        String qualifier = "";

        if (dateFilter.contains("PARTITION BY")) {
            qualifier = dateFilter.substring(dateFilter.indexOf("QUALIFY"));
            dateFilter = dateFilter.substring(0, dateFilter.indexOf("QUALIFY"));
        }

        String bvd9Column = queryModel.getEntityIdColumnName();
        String scoreColumn = queryModel.getScoreColumnName();


        String query = "SELECT rl." + bvd9Column + " AS bvd9_number,\n" +
                "              rl." + scoreColumn + "             AS score\n" +
                "       FROM   df_portfolio df \n" +
                "   JOIN   " + tableName + " rl   on  rl." + bvd9Column + " = df.bvd9_number  AND \n" +
                dateFilter +
                "        WHERE PORTFOLIO_ID='" + portfolioId + "'\n" +
                qualifier + ";";

        System.out.println(query);

        return getQueryResultMap(query);

    }

    public List<EntityWithScores> getCompanyScoresInAllResearchLinesWithPortfolioID(String month, String year, String portfolioId) {

        String query = "select distinct df.company_name, df.value,\n" +
                "world_region,\n" +
                "sector,\n" +
                "portfolio_id,\n" +
                "df.bvd9_number,\n" +
                "OPERATIONS_RISK_SCORE,\n" +
                "MARKET_RISK_SCORE, \n" +
                "SUPPLY_CHAIN_RISK_SCORE,\n" +
                "TEMPERATURE_SCORE_ACTUAL AS TEMPERATURE_ALIGNMENT_SCORE,\n" +
                "CARBON_FOOTPRINT_VALUE_TOTAL AS CARBON_SCORE,\n" +
                "BS_FOSF_INDUSTRY_REVENUES_ACCURATE AS BROWN_SHARE_SCORE,\n" +
                "GS_OVERALL_ASSESSMENT_ESTIMATE_OF_INCORPORATION AS GREEN_SHARE_SCORE\n" +
                "from df_portfolio df \n" +
                "left join carbon_footprint cf on cf.bvd9_number=df.bvd9_number and cf.month='" + month + "' and cf.year = '" + year + "'\n" +
                "left join temperature_alignment ta on ta.bvd9_number=df.bvd9_number and ta.month=cf.month and ta.year=cf.year\n" +
                "left join brown_share bs on bs.bvd9_number=df.bvd9_number and bs.month=cf.month and bs.year=cf.year\n" +
                "left join green_share gs on gs.bvd9_number=df.bvd9_number and gs.month=cf.month and cf.year=gs.year\n" +
                "left join entity_score es on es.ENTITY_ID_BVD9=df.bvd9_number and es.release_year || es.release_month <= '" + year + "" + month + "'   and es.release_year || es.release_month >= '" + (Integer.parseInt(year) - 1) + "" + month + "'\n" +
                "left join esg_entity_master eem on cf.bvd9_number=eem.orbis_id \n" +
                "left join FACTSET_MOODYS_DS.SYM_V1.SYM_SEC_ENTITY sse on eem.factset_entity_id = sse.factset_entity_id\n" +
                "where PORTFOLIO_ID='" + portfolioId + "'" +
                "and world_region is not null";

        System.out.println(query);

        return serializeEntities(getQueryResultMap(query));

    }

    public List<carbonFootPrintEmissionDBModel> getCarbonFootPrintEmimmisionTest(String portfolioId, String month, String year) {
        String query = "WITH p AS ( --calculate total value and filter out duplicates by bvd9_number\n" +
                "        SELECT portfolio_id,bvd9_number, region, region_name, sector, company_name,currency,as_of_date,\n" +
                "               SUM(value) OVER(PARTITION BY bvd9_number) value,\n" +
                "               SUM(value) OVER() total_value\n" +
                "        FROM df_target.df_portfolio\n" +
                "        WHERE portfolio_id = '%p'\n" +
                "        AND bvd9_number IS NOT NULL\n" +
                "        QUALIFY ROW_NUMBER() OVER(partition by BVD9_NUMBER ORDER BY REGION_NAME NULLS LAST)=1\n" +
                "              ), r AS (\n" +
                "                select bvd9_number\n" +
                "                ,year, month\n" +
                "                , ((value_usd/1000000)/assets_usd)   * CARBON_FOOTPRINT_VALUE_TOTAL           Total_Financed_Emissions_TotalAssets\n" +
                "                , ((value_usd/1000000)/market_value_usd)   * CARBON_FOOTPRINT_VALUE_TOTAL     Total_Financed_Emissions_MarketCap\n" +
                "                , (value_usd/1000000)                                                         denominator_for_Financed_Emissions\n" +
                "                , ((value_usd/1000000)/assets_usd)   * sales_usd                              Pre_Agg_for_Carbon_Intensity_per_Volume_of_Sales\n" +
                "                , ((value_usd/1000000)/market_value_usd)   * sales_usd                        Pre_Agg_for_Carbon_Intensity_per_Volume_of_Sales2\n" +
                "                from (\n" +
                "                SELECT distinct cf.bvd9_number, cf.year, cf.month, fi.assets, fi.sales, fi.mkt_val market_value\n" +
                "                        ,fi.local_currency, fi.Fiscal_Month_End_Date financial_info_date, p.as_of_date,rt.exratedate,rt.midrate,rt.exratedate,rt1.midrate,p.currency\n" +
                "                        ,cf.CARBON_FOOTPRINT_VALUE_TOTAL\n" +
                "                        ,case when p.currency != 'USD' THEN div0(p.value, to_decimal(rt.midrate,20,8)) ELSE p.value END value_usd\n" +
                "                        ,case when fi.local_currency != 'USD' THEN div0(fi.assets, to_decimal(rt1.midrate,20,8)) ELSE fi.assets END assets_usd\n" +
                "                        ,case when fi.local_currency != 'USD' THEN div0(fi.mkt_val, to_decimal(rt1.midrate,20,8)) ELSE fi.mkt_val END market_value_usd\n" +
                "                        ,case when fi.local_currency != 'USD' THEN div0(fi.sales, to_decimal(rt1.midrate,20,8)) ELSE fi.sales END sales_usd\n" +
                "                 FROM p\n" +
                "                 JOIN df_target.CARBON_FOOTPRINT cf ON p.bvd9_number      = cf.bvd9_number\n" +
                "                 JOIN df_lookup.financial_info fi ON cf.bvd9_number       = fi.bvd9_number\n" +
                "                                                    AND cf.year || cf.month >= TO_CHAR(fi.Fiscal_Month_End_Date, 'YYYYMM')\n" +
                "                                                    and cf.year = '%y'\n" +
                "                                                        and cf.month = '%m'\n" +
                "                  and portfolio_id='%p'\n" +
                "                 LEFT JOIN (-- Get most recent Exchange Rate for portfolio value conversion to USD\n" +
                " select distinct p.portfolio_id,p.as_of_date,p.bvd9_number,rt.fromcurrcode,rt.tocurrcode,rt.exratedate,rt.MIDRATE\n" +
                " FROM p\n" +
                "                            JOIN df_lookup.fx_rate rt\n" +
                "ON p.currency = rt.fromcurrcode AND rt.tocurrcode = 'USD'\n" +
                "AND p.as_of_date >= rt.exratedate\n" +
                "QUALIFY ROW_NUMBER() OVER(PARTITION BY rt.fromcurrcode,rt.tocurrcode ORDER BY rt.exratedate DESC) = 1\n" +
                ") rt\n" +
                "ON p.currency = rt.fromcurrcode AND rt.tocurrcode = 'USD'\n" +
                "\n" +
                "                 LEFT JOIN (-- Get most recent Exchange Rate for Sales, Assets & Mktg_Value conversion to USD\n" +
                "select distinct p.portfolio_id,p.as_of_date,p.bvd9_number,rt.fromcurrcode,rt.tocurrcode,rt.exratedate,rt.MIDRATE\n" +
                "FROM p\n" +
                "                            JOIN df_lookup.fx_rate rt\n" +
                "ON rt.tocurrcode = 'USD'\n" +
                "AND p.as_of_date >= rt.exratedate\n" +
                "QUALIFY ROW_NUMBER() OVER(PARTITION BY rt.fromcurrcode,rt.tocurrcode ORDER BY rt.exratedate DESC) = 1\n" +
                ") rt1\n" +
                "ON fi.local_currency = rt1.fromcurrcode AND rt1.tocurrcode = 'USD'\n" +
                " WHERE cf.CARBON_FOOTPRINT_VALUE_TOTAL IS NOT NULL\n" +
                "AND fi.assets is not null and fi.sales is not null and fi.mkt_val is not null\n" +
                "AND cf.year              = '%y'\n" +
                "                    AND cf.month             = '%m'\n" +
                "                  QUALIFY ROW_NUMBER() OVER(PARTITION BY cf.bvd9_number ORDER BY fi.Fiscal_Month_End_Date DESC) = 1\n" +
                "              )\n" +
                "              ), s as (\n" +
                "                select sum(r.Total_Financed_Emissions_TotalAssets) as Total_Financed_Emissions_TotalAssets\n" +
                "                    ,sum(r.Total_Financed_Emissions_MarketCap) as Total_Financed_Emissions_MarketCap\n" +
                "                    ,sum(r.denominator_for_Financed_Emissions) as denominator_for_Financed_Emissions\n" +
                "                    ,sum(r.Pre_Agg_for_Carbon_Intensity_per_Volume_of_Sales) as Pre_Agg_for_Carbon_Intensity_per_Volume_of_Sales\n" +
                "                    ,sum(r.Pre_Agg_for_Carbon_Intensity_per_Volume_of_Sales2) as Pre_Agg_for_Carbon_Intensity_per_Volume_of_Sales2\n" +
                "                    from r\n" +
                "              ), t as (\n" +
                "                select s.Total_Financed_Emissions_TotalAssets          as Total_Financed_Emissions_TotalAssets\n" +
                "                    ,s.Total_Financed_Emissions_MarketCap              as Total_Financed_Emissions_MarketCap\n" +
                "                    ,div0(s.Total_Financed_Emissions_TotalAssets, s.denominator_for_Financed_Emissions)  as Financed_Emissions_per_Millions_Invested_TotalAssets\n" +
                "                    ,div0(s.Total_Financed_Emissions_MarketCap, s.denominator_for_Financed_Emissions)  as Financed_Emissions_per_Millions_Invested_MarketCap\n" +
                "                    ,div0(s.Total_Financed_Emissions_TotalAssets, s.Pre_Agg_for_Carbon_Intensity_per_Volume_of_Sales)  as carbon_intensity_per_volume_of_sales_Total_Assets\n" +
                "                    ,div0(s.Total_Financed_Emissions_MarketCap, s.Pre_Agg_for_Carbon_Intensity_per_Volume_of_Sales2)  as carbon_intensity_per_volume_of_sales_Market_Cap\n" +
                "                    from s\n" +
                "                )  \n" +
                "                  select 'Total Financed Emissions' as category, t.Total_Financed_Emissions_TotalAssets as totalAssets, t.Total_Financed_Emissions_MarketCap as marketCapitalization from t\n" +
                "                    union\n" +
                "                  select 'Financed Emissions per million Invested' as category, t.Financed_Emissions_per_Millions_Invested_TotalAssets as totalAssets, t.Financed_Emissions_per_Millions_Invested_MarketCap as marketCapitalization from t\n" +
                "                    union\n" +
                "                  select 'Carbon Intensity per Sales' as category, t.carbon_intensity_per_volume_of_sales_Total_Assets as totalAssets, t.carbon_intensity_per_volume_of_sales_Market_Cap as marketCapitalization from t\n";

        query = query.replaceAll("%y", year);
        query = query.replaceAll("%m", month);
        query = query.replaceAll("%p", portfolioId);

        List<carbonFootPrintEmissionDBModel> list = new ArrayList<>();
        for (List<Object> rs : getQueryResultList(query)) {
            carbonFootPrintEmissionDBModel CarbonFootprintEmission =
                    new carbonFootPrintEmissionDBModel();
            CarbonFootprintEmission.setCategory(rs.get(0).toString());
            CarbonFootprintEmission.setTotalAssets((BigDecimal) rs.get(1));
            CarbonFootprintEmission.setMarketCapitalization((BigDecimal) rs.get(2));
            list.add(CarbonFootprintEmission);
        }
        return list;


    }

    public PhysicalScore getPhysicalHazardScore(String portfolioid, String yearmonth) {
        String query = "with p as (select bvd9_number,\n" +
                "   sum(value) as \"value\" ,\n" +
                "   sum(sum(value)) over()   as \"total_inv\",\n" +
                "                \"value\"/\"total_inv\" as weighted_avg\n" +
                "   from df_target.df_portfolio where portfolio_id = %s\n" +
                "   group by bvd9_number\n" +
                "   ) ,\n" +
                "   category as (select risk_category_id , sum(\"value\" * entity_category_score)/\"total_inv\" risk_category_value,\n" +
                "MAX(sc.release_year || sc.release_month) as RELEASE_DT\n" +
                "from df_target.entity_score_category sc, p\n" +
                "where sc.release_year || sc.release_month=\n" +
                "( select  max(CONCAT(release_year,release_month)) as MaxDate  from  df_target.entity_score_category s where \n" +
                "                                        s.release_year || s.release_month <= %s)\n" +
                "and sc.entity_id_bvd9 = p.bvd9_number\n" +
                "and risk_category_id in (14,1,12,4,2,16)\n" +
                "    and sc.entity_category_score is not null\n" +
                "and sc.entity_category_score >= 0\n" +
                "group by 1, \"total_inv\"\n" +
                "   qualify row_number() over(order by risk_category_value desc)=1\n" +
                "  ) \n" +
                "  ,\n" +
                "  fac as (            \n" +
                "select entity_id_bvd9,th.risk_category_id, risk_threshold_id ,number_facilities_exposed as number_facilities_exposed,  \n" +
                "sum(number_facilities_exposed) over(partition by entity_id_bvd9 ) as total_number_facilities_exposed,\n" +
                "weighted_avg, category.risk_category_value\n" +
                "from  df_target.entity_score_thresholds th,p,category\n" +
                "where th.release_year || th.release_month=category.RELEASE_DT\n" +
                "and th.risk_category_id=category.risk_category_id  \n" +
                "and th.entity_id_bvd9= p.bvd9_number\n" +
                "order by entity_id_bvd9,th.risk_category_id\n" +
                "  ),\n" +
                "  fac_avg as (\n" +
                "select risk_category_id,risk_threshold_id ,\n" +
                "sum(number_facilities_exposed*weighted_avg) /sum(total_number_facilities_exposed*weighted_avg) as avg_val,\n" +
                "                        max(risk_category_value) as risk_category_value\n" +
                "from fac where risk_threshold_id in (3,4) group  by risk_category_id,risk_threshold_id\n" +
                " ),\n" +
                "  y as(\n" +
                "  select  risk_category_name \"highest_risk_hazard\", to_char(round(sum(avg_val)*100,2))  facilities_exposed  ,\n" +
                "                df_target.physical_risk_score_category(max(risk_category_value)) as hrh_risk_category\n" +
                "from fac_avg\n" +
                "  join df_target.risk_category rc on fac_avg.risk_category_id=rc.risk_category_id group by fac_avg.risk_category_id,risk_category_name\n" +
                "  ),\n" +
                "  z AS (\n" +
                "SELECT\n" +
                "score \"highest_risk_hazard\",\n" +
                "facilities_exposed \"facilities_exposed\",\n" +
                "hrh_risk_category \"hrh_risk_category\"\n" +
                "FROM\n" +
                "y unpivot (score FOR NAME IN (\"highest_risk_hazard\"))\n" +
                ")\n" +
                "SELECT * \n" +
                "FROM\n" +
                "z";
        query = String.format(query, portfolioid, yearmonth);
        PhysicalScore score = new PhysicalScore();
        for (Map<String, Object> rs : getQueryResultMap(query)) {
            score.setFacilities_exposed(rs.get("facilities_exposed").toString());
            score.setHighest_risk_hazard(rs.get("highest_risk_hazard").toString());
            score.setHrh_risk_category(rs.get("hrh_risk_category").toString());

        }
        return score;
    }

    public List<List<Object>> getEsgInfoFromDB() {
        String query1 = "select sum(value) from df_portfolio where portfolio_id='00000000-0000-0000-0000-000000000000'";
        String total = getQueryResultList(query1).get(0).get(0).toString();

        String query2 = "select df.COMPANY_NAME,round((df.value/" + total + ")*100,2) as investment,eos.value, ect.METHODOLOGY_VERSION from df_portfolio df\n" +
                " join entity_coverage_tracking ect on ect.orbis_id=df.bvd9_number and coverage_status = 'Published' and publish = 'yes'\n" +
                " join ESG_OVERALL_SCORES eos on ect.orbis_id=eos.orbis_id and data_type = 'overall_alphanumeric_score' and sub_category = 'ESG'\n" +
                " where portfolio_id='00000000-0000-0000-0000-000000000000'\n" +
                " and eos.year || eos.month <= '202208'\n" +
                " qualify row_number() OVER (PARTITION BY eos.orbis_id ORDER BY eos.year DESC, eos.month DESC, eos.scored_date DESC) =1";

        return getQueryResultList(query2);
    }

    public int getEsgScoreOfPortfolio() {
        String query1 = "select sum(value) from df_portfolio where portfolio_id='00000000-0000-0000-0000-000000000000'";
        String total = getQueryResultList(query1).get(0).get(0).toString();

        String query2 = "select df.COMPANY_NAME,round((df.value/" + total + ")*100,2) as investment,eos.value, ect.METHODOLOGY_VERSION from df_portfolio df " +
                " join entity_coverage_tracking ect on ect.orbis_id=df.bvd9_number and coverage_status = 'Published' and publish = 'yes'" +
                " join ESG_OVERALL_SCORES eos on ect.orbis_id=eos.orbis_id and data_type = 'esg_pillar_score' and sub_category = 'ESG'" +
                " where portfolio_id='00000000-0000-0000-0000-000000000000'" +
                " and eos.year || eos.month <= '202208'" +
                " qualify row_number() OVER (PARTITION BY eos.orbis_id ORDER BY eos.year DESC, eos.month DESC, eos.scored_date DESC) =1";

        List<Map<String, Object>> esgInfo = getQueryResultMap(query2);
        int totalCompanies = esgInfo.size();
        int sum = 0;
        for(Map<String, Object> entityEsgInfo: esgInfo){
            sum= sum+Integer.valueOf(entityEsgInfo.get("VALUE").toString());
        }

        return sum/totalCompanies;

    }

    public String getLastUpdatedDateOfEntity(String entityName) {
        String query = "Select UPDATED_DATE from DF_TARGET.ENTITY_SEARCH_FEED where entity_proper_name like ('"+entityName+"')";
        String dateDB = getQueryResultList(query).get(0).get(0).toString().substring(0,10);

        SimpleDateFormat month_date = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String month_name = "";
        try {
            Date date = sdf.parse(dateDB);
            month_name = month_date.format(date);
        } catch (Exception e) {
        }
        return month_name;
    }

    public List<Map<String, Object>> getESGModelWithPortfolioID(String month, String year, String portfolioId) {
        String query = "select DISTINCT eos.ORBIS_ID, eos.RESEARCH_LINE_ID from df_portfolio  df\n" +
                "join ESG_OVERALL_SCORES eos on eos.orbis_id=df.bvd9_number\n" +
                "join entity_coverage_tracking ect  on ect.orbis_id=eos.orbis_id \n" +
                "and data_type = 'esg_pillar_score'  and sub_category = 'ESG' \n" +
                "and eos.year || eos.month <= '" + year + month + "' and coverage_status = 'Published' and publish = 'yes'\n" +
                "where portfolio_id='" + portfolioId + "' ;";

        System.out.println(query);

        return getQueryResultMap(query);
    }

    public List<List<Object>> getPortfolioCompaniesFromDB() {
        String query1 = "select company_name, value from df_portfolio\n" +
                "where portfolio_id='00000000-0000-0000-0000-000000000000'\n" +
                "and company_name is not null order by value desc";
        return getQueryResultList(query1);
    }
    public List<List<Object>>  getPortfolioCompaniesTotalValuesFromDB() {
        String query1 = "select  sum(value)\n" +
                "from df_portfolio df\n" +
                "where portfolio_id='00000000-0000-0000-0000-000000000000'";
        return getQueryResultList(query1);
    }

    public List<ESGLeaderANDLaggers> getESGLeadersAndLaggersData(String portfolioid, String yearmonth) {
       /* String query = " with p as (SELECT bvd9_number,region, sector, SUM(value) as invvalue, COUNT(*) OVER() total_companies, " +
                "SUM(SUM(value)) OVER() AS total_value FROM df_target.df_portfolio WHERE 1=1 " +
                "AND portfolio_id = '" + portfolioid +"'" +
                "GROUP BY bvd9_number, region, sector ) ,esg as (select eos.*,ect.research_line_id as rl from entity_coverage_tracking ect  " +
                "join ESG_OVERALL_SCORES eos on ect.orbis_id=eos.orbis_id and data_type = 'esg_pillar_score'  and sub_category = 'ESG'" +
                "and eos.year || eos.month <= '" + yearmonth +"'" +
                "where  coverage_status = 'Published' and publish = 'yes' " +
                "qualify row_number() OVER (PARTITION BY eos.orbis_id ORDER BY eos.year DESC, eos.month DESC, eos.scored_date DESC) =1),q " +
                "as(select p.bvd9_number,eem.entity_proper_name AS company_name, round(esg.VALUE,0) as score," +
                "round((100*(p.invvalue/p.total_value)),2) AS investment_pct, esg.rl from p    " +
                "left join esg on p.bvd9_number=esg.orbis_id " +
                "LEFT JOIN DF_TARGET.ESG_ENTITY_MASTER eem ON (p.bvd9_number = eem.orbis_id AND eem.ENTITY_STATUS = 'Active')" +
                "where 1=1 and SUB_CATEGORY = 'ESG' QUALIFY row_number() over(partition by company_name ORDER BY esg.VALUE desc) = 1 " +
                "ORDER BY esg.VALUE desc, investment_pct desc,company_name) " +
                "select q.bvd9_number,q.company_name, RANK() OVER(ORDER BY q.score desc) AS Rank, q.investment_pct, q.score, q.rl as Scoring_RLID," +
                "case when q.rl=1008 then case when (score >= 0 and score <= 29) then 1 " +
                "when (score >= 30 and score <= 49) then 2" +
                "when (score >= 50 and score <= 59) then 3 " +
                "when (score >= 60 and score <= 100) then 4 end " +
                "when q.rl=1015 then " +
                "case when (score >= 0 and score <= 24) then 1 " +
                "when (score >= 25 and score <= 44) then 2 " +
                "when (score >= 45 and score <= 64) then 3 " +
                "when (score >= 65 and score <= 100) then 4 " +
                "end  end as scale " +
                "from q ORDER BY scale desc, q.score desc, q.investment_pct desc,q.company_name ";*/

        String query = "with p as (SELECT bvd9_number, COMPANY_NAME " +
                ",region, sector, SUM(value) as invvalue, COUNT(*) OVER() total_companies, SUM(SUM(value)) OVER() AS total_value " +
                "FROM df_target.df_portfolio WHERE 1=1 AND portfolio_id = '" + portfolioid +"' " +
                "  GROUP BY bvd9_number, COMPANY_NAME, region, sector), " +
                "e as (select p.*,ect.RESEARCH_LINE_ID, floor(eos.VALUE) as score, METHODOLOGY_VERSION " +
                ",case " +
                "when ect.RESEARCH_LINE_ID=1008 then " +
                "case " +
                "when (score < 30) then 1 " +
                "when (score < 50) then 2 " +
                "when (score < 60) then 3 " +
                "when (score >= 60) then 4 " +
                "end " +
                "when ect.RESEARCH_LINE_ID=1015 then " +
                "case " +
                "when (score < 25) then 1 " +
                "when (score < 45 ) then 2 " +
                "when (score < 65) then 3 " +
                "when (score >= 65) then 4 " +
                "end " +
                "end as scale " +
                "from p " +
                "join entity_coverage_tracking ect on ect.orbis_id=p.bvd9_number and coverage_status = 'Published' and publish = 'yes' " +
                "left join ESG_OVERALL_SCORES eos on ect.orbis_id=eos.orbis_id and data_type in ('esg_pillar_score' ) and sub_category = 'ESG' " +
                "where ect.RESEARCH_LINE_ID in (1008,1015) and score and eos.year || eos.month <= '" + yearmonth +"' " +
                "qualify row_number() OVER (PARTITION BY eos.orbis_id ORDER BY eos.year DESC, eos.month DESC, eos.scored_date DESC) =1) " +
                "select BVD9_Number, Company_name, RANK() OVER(ORDER BY Score desc NULLS LAST) AS Rank, " +
                "round((100*(e.invvalue/e.total_value)),6) AS investment_pct, SCORE,RESEARCH_LINE_ID as SCORING_RLID, Scale, METHODOLOGY_VERSION from e " +
                "order by scale desc, Score desc, INVESTMENT_PCT desc, Company_name";

        List<ESGLeaderANDLaggers> score = new ArrayList<>();
        try{
        for (Map<String, Object> rs : getQueryResultMap(query)) {
            ESGLeaderANDLaggers entity= new ESGLeaderANDLaggers();
            entity.setBVD9_NUMBER(rs.get("BVD9_NUMBER").toString());
            if (rs.get("COMPANY_NAME")!=null) entity.setCOMPANY_NAME(rs.get("COMPANY_NAME").toString()); else entity.setCOMPANY_NAME("");
            entity.setRANK(Integer.valueOf(rs.get("RANK").toString()));
            entity.setInvestmentPercentage(Double.valueOf(rs.get("INVESTMENT_PCT").toString()));
            entity.setSCORE((int) Math.round(Double.valueOf(rs.get("SCORE").toString())));
            entity.setSCORING_RLID(Integer.valueOf(rs.get("SCORING_RLID").toString()));
            entity.setScale(Integer.valueOf(rs.get("SCALE").toString()));
            entity.setMETHODOLOGY_VERSION((rs.get("METHODOLOGY_VERSION").toString()));
            score.add(entity);
        }}catch(Exception e){
            System.out.println(e.toString());
            System.out.println("Failed at recored Number " +  score.size());
        }

        return score;
    }

    public Map<String, Object> getPortfolioDetails(String portfolioid) {
        String query = "SELECT * FROM df_target.df_portfolio WHERE 1=1 AND portfolio_id = '" + portfolioid +"'";
        Map<String, Object> rs = getQueryResultMap(query).get(0);
        return rs;
    }
}
