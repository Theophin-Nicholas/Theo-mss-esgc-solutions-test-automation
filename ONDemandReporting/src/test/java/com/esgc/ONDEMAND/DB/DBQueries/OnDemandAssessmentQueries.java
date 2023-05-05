package com.esgc.ONDEMAND.DB.DBQueries;

import com.esgc.ONDEMAND.DB.DBModels.OnDemandPortfolioTable;
import com.esgc.Utilities.Database.DatabaseDriver;
import com.esgc.Utilities.DateTimeUtilities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class OnDemandAssessmentQueries {

    public List<Map<String, Object>> getCompaniesInvestmentInfo(String portfolioId) {
        String query1 = "select SUM(VALUE) as TotalInv from df_target.df_portfolio Where portfolio_id = '"+portfolioId+"'";
        String totalInvestments = DatabaseDriver.getQueryResultMap(query1).get(0).get("TOTALINV").toString();

        String query2 = "select COMPANY_NAME, VALUE, Round((VALUE/"+totalInvestments+")*100, 2) as InvPerc from df_target.df_portfolio Where portfolio_id = '"+portfolioId+"' order by COMPANY_NAME ASC";
        System.out.println("getCompaniesInvestmentInfo query: "+query2);
        return DatabaseDriver.getQueryResultMap(query2);
    }

    public List<String> getLocationWiseInvestmentInfo(String portfolioId) {

        List<String> dbLocationsInfo = new ArrayList<>();

        String query1 = "select SUM(VALUE) as TotalInv from df_target.df_portfolio Where portfolio_id = '"+portfolioId+"'";
        int totalInvestments = Integer.valueOf(DatabaseDriver.getQueryResultMap(query1).get(0).get("TOTALINV").toString());

        String query2 = "select REGION_NAME,Round((SUM(VALUE)/"+totalInvestments+")*100, 2) as InvPerc from df_target.df_portfolio Where portfolio_id = '"+portfolioId+"' group by region_name;";
        List<Map<String, Object>> dbLocationWiseCompaniesInfo = DatabaseDriver.getQueryResultMap(query2);

        for(int i=0; i<dbLocationWiseCompaniesInfo.size(); i++){
            dbLocationsInfo.add(dbLocationWiseCompaniesInfo.get(i).get("REGION_NAME")+" ("+dbLocationWiseCompaniesInfo.get(i).get("InvPerc")+")");
        }

        String query3 = "select COUNTRY_NAME,Round((SUM(VALUE)/"+totalInvestments+")*100, 2) as InvPerc from df_target.df_portfolio Where portfolio_id = '"+portfolioId+"' group by COUNTRY_NAME;";
        List<Map<String, Object>> dbSectorWiseCompaniesInfo = DatabaseDriver.getQueryResultMap(query3);

        for(int i=0; i<dbSectorWiseCompaniesInfo.size(); i++){
            dbLocationsInfo.add(dbSectorWiseCompaniesInfo.get(i).get("REGION_NAME")+" ("+dbSectorWiseCompaniesInfo.get(i).get("InvPerc")+")");
        }

        return dbLocationsInfo;
    }

    public List<String> getSectorWiseInvestmentInfo(String portfolioId) {

        List<String> dbSectorsInfo = new ArrayList<>();
        String query1 = "select SUM(VALUE) as TotalInv from df_target.df_portfolio Where portfolio_id = '"+portfolioId+"'";
        int totalInvestments = Integer.valueOf(DatabaseDriver.getQueryResultMap(query1).get(0).get("TOTALINV").toString());

        String query2 = "select SECTOR,Round((SUM(VALUE)/"+totalInvestments+")*100, 2) as InvPerc from df_target.df_portfolio Where portfolio_id = '"+portfolioId+"' group by SECTOR;";
        List<Map<String, Object>> dbSectorWiseCompaniesInfo = DatabaseDriver.getQueryResultMap(query2);

        for(int i=0; i<dbSectorWiseCompaniesInfo.size(); i++){
            dbSectorsInfo.add(dbSectorWiseCompaniesInfo.get(i).get("REGION_NAME")+" ("+dbSectorWiseCompaniesInfo.get(i).get("InvPerc")+")");
        }
        return dbSectorsInfo;
    }

    public String getHighestInvestment(String portfolioId) {
        String query1 = "select SUM(VALUE) as TotalInv from df_target.df_portfolio Where portfolio_id = '"+portfolioId+"'";
        String totalInvestments = DatabaseDriver.getQueryResultMap(query1).get(0).get("TOTALINV").toString();
        System.out.println("totalInvestments = " + totalInvestments);
        String query2 = "select ROUND(max(score)/"+totalInvestments+"*100,2) as MAXSCORE from (\n" +
                "select SUM(ptf.VALUE) score from df_target.ESG_OVERALL_SCORES EST\n" +
                "JOIN df_target.df_portfolio ptf on ptf.BVD9_NUMBER = EST.ORBIS_ID\n" +
                "Where portfolio_id = '"+portfolioId+"'\n" +
                "and SUB_CATEGORY = 'ESG' and DATA_TYPE = 'esg_pillar_score' and EST.score_quality='Predicted' group by EST.VALUE)";
        String maxScore = DatabaseDriver.getQueryResultMap(query2).get(0).get("MAXSCORE").toString()+"%";
        System.out.println("maxScore = " + maxScore);
        return maxScore;
    }

    public String getEligibleAssessment(String portfolioId) {
        String query1 = "SELECT Sum(ptf.value) as NOMINATOR\n" +
                "FROM   df_target.df_portfolio ptf\n" +
                "       JOIN df_target.esg_overall_scores EST\n" +
                "         ON ptf.bvd9_number = EST.orbis_id\n" +
                "WHERE  portfolio_id = '" + portfolioId + "'\n" +
                "       AND sub_category = 'ESG'\n" +
                "       AND data_type = 'esg_pillar_score'\n" +
                "       AND est.score_quality = 'Predicted'; ";
        System.out.println("query1 = " + query1);
        
        double companiesWithPredictedScores = Double.parseDouble(DatabaseDriver.getQueryResultMap(query1).get(0).get("NOMINATOR").toString());

        String query2 = "SELECT Sum(ptf.value) as TOTAL\n" +
                "FROM   df_target.df_portfolio ptf\n" +
                "WHERE  portfolio_id = '" + portfolioId + "'; ";

        System.out.println("query2 = " + query2);
        double companiesWithEsg = Double.parseDouble(DatabaseDriver.getQueryResultMap(query2).get(0).get("TOTAL").toString());

        return companiesWithPredictedScores/companiesWithEsg*100+"%";
    }

    public List<String> getOnDemandOrbisIDsForPortfolioCreation(String ScoreQuality, String DataAlliance, int dataCount) {
        String query = "" ;
        query = "select * from entity_score_type est \n" +
                "right JOIN ENTITY_IDENTIFIERS_DETAIL EID on EID.BVD9_NUMBER = est.ORBIS_ID where " ;
        if (DataAlliance!=""){
            query = query + "DATA_ALLIANCE= '" + DataAlliance+ "' And ";
        }
        query = query + "est.SCORE_QUALITY ='" + ScoreQuality+ "' and est.Entity_Status = 'Active' and est.IS_Current = 'Y' limit " + dataCount;

        if(ScoreQuality.equals("SFDR Only"))
            query="select BVD9_NUMBER as ORBIS_ID from \"DF_TARGET\".\"REGULATORY_REPORT_SFDR\" sf\n" +
                    "where sf.BVD9_NUMBER not in (select eu.orbis_ID from EU_TAXONOMY_OVERVIEW eu)\n" +
                    "limit "+dataCount;

        if(ScoreQuality.equals("EU Taxonomy Only"))
            query="select eu.orbis_ID from EU_TAXONOMY_OVERVIEW eu\n" +
                    "where eu.orbis_id not in (select BVD9_NUMBER from \"DF_TARGET\".\"REGULATORY_REPORT_SFDR\")\n" +
                    "limit "+dataCount;

        if(ScoreQuality.equals("NotSFDRNotEUTaxonomy"))
            query="select ORBIS_ID from \"DF_TARGET\".\"ORBIS_ENTITY_SCORE\"\n" +
                    "where orbis_id not in (select orbis_ID from EU_TAXONOMY_OVERVIEW)\n" +
                    "and orbis_id not in (select BVD9_NUMBER from \"DF_TARGET\".\"REGULATORY_REPORT_SFDR\")\n" +
                    "limit "+dataCount;

        if(ScoreQuality.equals("DataAlliance"))
            query="select distinct orbis_id, data_alliance from ENTITY_SCORE_TYPE\n" +
                    "where data_alliance is not null\n" +
                    "order by data_alliance desc limit "+dataCount;

        if(ScoreQuality.equals("BothSFDRAndEUTaxonomy"))
            query="select distinct ORBIS_ID from \"DF_TARGET\".\"ORBIS_ENTITY_SCORE\"\n" +
                    "where orbis_id in (select orbis_ID from EU_TAXONOMY_OVERVIEW)\n" +
                    "and orbis_id in (select BVD9_NUMBER from \"DF_TARGET\".\"REGULATORY_REPORT_SFDR\")\n" +
                    "limit "+dataCount;
        List<String> dataList =  new ArrayList<>();
        for (Map<String, Object> result : DatabaseDriver.getQueryResultMap(query)){
            dataList.add(result.get("ORBIS_ID").toString());
        }
        return dataList ;
    }

    public List<OnDemandPortfolioTable> getDataForPortfolioTable(String portfolioID, String scoreQuality) {
        String query = "" ;
        query = "SELECT dp.portfolio_id ,nvl(dp.bvd9_number,dp.sec_id) as \"bvd9_number\"\n" +
                ",dp.company_name,dp.region,dp.sector,est.score_quality,max(est.entity_status) entity_status\n" +
                ", SUM(dp.value) value, COUNT(*) OVER(PARTITION BY dp.portfolio_id) total_companies, SUM(SUM(dp.value)) " +
                "OVER(PARTITION BY dp.portfolio_id) AS total_value\n" +
                "FROM df_target.df_portfolio dp left join df_target.entity_score_type est on est.orbis_id = dp.bvd9_number and est.is_current = 'Y'\n" +
                "and est.entity_status = 'Active' and est.score_quality in " +
                "("+scoreQuality+") " +
                "where portfolio_id = '"+portfolioID+"' " +
                "GROUP BY dp.portfolio_id, \"bvd9_number\", dp.region,dp.company_name, dp.sector ,est.SCORE_QUALITY \n" ;
        System.out.println("getDataForPortfolioTable query : "+query);
        List<OnDemandPortfolioTable> dataList =  new ArrayList<>();
        for (Map<String, Object> result : DatabaseDriver.getQueryResultMap(query)){
            OnDemandPortfolioTable list = new OnDemandPortfolioTable();
            list.setPORTFOLIO_ID(result.get("PORTFOLIO_ID").toString());
            list.setBvd9_number(result.get("bvd9_number").toString());
            list.setCOMPANY_NAME(result.get("COMPANY_NAME")==null ? "NA":result.get("COMPANY_NAME").toString());
            list.setREGION(result.get("REGION")==null ? "NA":result.get("REGION").toString());
            list.setSECTOR(result.get("SECTOR")==null ? "NA":result.get("SECTOR").toString());
            list.setSCORE_QUALITY(result.get("SCORE_QUALITY")==null ? "NA":result.get("SCORE_QUALITY").toString());
            list.setENTITY_STATUS(result.get("ENTITY_STATUS")==null ? "NA":result.get("ENTITY_STATUS").toString());
            list.setVALUE(Integer.valueOf(result.get("VALUE").toString()));
            list.setTOTAL_COMPANIES(Integer.valueOf(result.get("TOTAL_COMPANIES").toString()));
            list.setTOTAL_VALUE(Integer.valueOf(result.get("TOTAL_VALUE").toString()));
            dataList.add(list);
        }
        return dataList ;
    }

    public List<Map<String,Object>> getEntitiesWithScoreType(String portfolioId){


        String query = "SELECT  dp.portfolio_id\n" +
                "                            ,nvl(dp.bvd9_number,dp.sec_id) as \"bvd9_number\"\n" +
                "                            ,dp.company_name\n" +
                "                            ,dp.region\n" +
                "                            ,dp.sector\n" +
                "                            ,est.score_quality\n" +
                "                            ,max(est.entity_status) entity_status\n" +
                "                            , SUM(dp.value) value\n" +
                "                            , COUNT(*) OVER(PARTITION BY dp.portfolio_id) total_companies\n" +
                "                            , SUM(SUM(dp.value)) OVER(PARTITION BY dp.portfolio_id) AS total_value\n" +
                "                    FROM df_target.df_portfolio dp left join df_target.entity_score_type est\n" +
                "                                                on est.orbis_id = dp.bvd9_number\n" +
                "                                                and est.is_current = 'Y'\n" +
                "                                                and est.entity_status = 'Active'\n" +
                "                                                and est.score_quality in ('Analytical', 'Subsidiary', 'Predicted', 'Self-Assessed')\n" +
                "                    where portfolio_id = '"+portfolioId+"'\n" +
                "                    GROUP BY dp.portfolio_id, \"bvd9_number\", dp.region,dp.company_name, dp.sector ,est.SCORE_QUALITY ";


        System.out.println("getEntitiesWithScoreType query : "+query);
        return DatabaseDriver.getQueryResultMap(query);
    }

    public List<Object> getEntitiesNameList(String portfolioId){

        List<Object> rowList = new ArrayList<>();

        String query = "SELECT  dp.portfolio_id\n" +
                "                            ,nvl(dp.bvd9_number,dp.sec_id) as \"bvd9_number\"\n" +
                "                            ,dp.company_name\n" +
                "                            ,dp.region\n" +
                "                            ,dp.sector\n" +
                "                            ,est.score_quality\n" +
                "                            ,max(est.entity_status) entity_status\n" +
                "                            , SUM(dp.value) value\n" +
                "                            , COUNT(*) OVER(PARTITION BY dp.portfolio_id) total_companies\n" +
                "                            , SUM(SUM(dp.value)) OVER(PARTITION BY dp.portfolio_id) AS total_value\n" +
                "                    FROM df_target.df_portfolio dp left join df_target.entity_score_type est\n" +
                "                                                on est.orbis_id = dp.bvd9_number\n" +
                "                                                and est.is_current = 'Y'\n" +
                "                                                and est.entity_status = 'Active'\n" +
                "                                                and est.score_quality in ('Analytical', 'Subsidiary', 'Predicted', 'Self-Assessed')\n" +
                "                    where portfolio_id = '"+portfolioId+"'\n" +
                "                    GROUP BY dp.portfolio_id, \"bvd9_number\", dp.region,dp.company_name, dp.sector ,est.SCORE_QUALITY ";
        System.out.println("getEntitiesNameList query : "+query);
        rowList.addAll(DatabaseDriver.getColumnData(query, "COMPANY_NAME"));
        return rowList;
    }

    public List<String> getEntitiesNamesFromResultSet(String portfolioId){
        List<Map<String, Object>> resultSet = getEntitiesWithScoreType(portfolioId);

        List<String> entityNames = new LinkedList<>();

        for(int i=0; i<entityNames.size(); i++){
            entityNames.add(resultSet.get(i).get("COMPANY_NAME").toString());
        }
        return entityNames;
    }

    public List<Map<String, Object>> getESGENTITYEXPORT(String portfolioId) {
        String query="select * from VW_DASHBOARD_CLIMATE_ENTITY_DATA_EXPORT\n" +
                "where portfolio_id = '"+portfolioId+"'\n" +
                "and year='"+ DateTimeUtilities.getCurrentYear() +"' and month = '"+DateTimeUtilities.getCurrentMonthNumeric()+"'";
        System.out.println("getESGENTITYEXPORT query : "+query);
        return DatabaseDriver.getQueryResultMap(query);
    }

    public List<Map<String, Object>> getPortfolioDetail(String portfolioId) {
        String query = "select * from df_portfolio where portfolio_id='"+portfolioId+"' and bvd9_number is not null";
        return DatabaseDriver.getQueryResultMap(query);
    }

    public List<Map<String, Object>> getPortfolioSectorDetail(String portfolioId) {
        String query = "WITH p AS (\n" +
                "             SELECT p.bvd9_number as bvd9_number\n" +
                "                   ,p.company_name\n" +
                "                   ,MAX(e.parent_entity_name) as parent_entity_name\n" +
                "               FROM df_portfolio p left join df_target.esg_entity_master e\n" +
                "                                          ON p.bvd9_number = e.orbis_id\n" +
                "                                         AND e.entity_status = 'Active'\n" +
                "              WHERE p.portfolio_id = '"+portfolioId+"'\n" +
                "             GROUP BY p.company_name,p.bvd9_number,p.sec_id,p.region, p.sector\n" +
                "                            )\n" +
                "   ,SEC_DET AS (\n" +
                "             SELECT distinct\n" +
                "                    orbis_id\n" +
                "                   ,MST.MESG_SECTOR_ID\n" +
                "                   ,sec.MESG_SECTOR\n" +
                "                   ,SEC.L1_SECTOR\n" +
                "                   ,SEC.L2_SECTOR\n" +
                "               FROM df_target.ESG_ENTITY_MASTER MST\n" +
                "                    JOIN DF_TARGET.SECTOR_HIERARCHY SEC\n" +
                "                      ON MST.MESG_SECTOR_ID=SEC.MESG_SECTOR_ID\n" +
                "\t\t\t\t     LEFT JOIN DF_TARGET.MESG_SECTOR_DESCRIPTION SEC_DESC\n" +
                "                            ON MST.MESG_SECTOR_ID=SEC_DESC.MESG_SECTOR_ID\n" +
                "            QUALIFY ROW_NUMBER() OVER(PARTITION BY mst.ORBIS_ID,mst.BVD_ID_NUMBER ORDER BY mst.AS_OF_DATE DESC) = 1 )\n" +
                "    ,PRE_SEC_DET AS (\n" +
                "             SELECT distinct\n" +
                "                    orbis_id\n" +
                "                   ,SEC.MESG_SECTOR_ID\n" +
                "                   ,sec.MESG_SECTOR\n" +
                "                   ,SEC.L1_SECTOR\n" +
                "                   ,SEC.L2_SECTOR\n" +
                "               FROM df_target.ORBIS_ENTITY_MASTER MST\n" +
                "                    JOIN DF_LOOKUP.ESG_NACE_TO_MESG_SECTOR_ID_MAPPING NACE\n" +
                "                      ON MST.NACE2_CORE_CODE =NACE.NACE_CODE\n" +
                "                    JOIN DF_TARGET.SECTOR_HIERARCHY SEC\n" +
                "                      ON NACE.MESG_SECTOR_ID=SEC.MESG_SECTOR_ID\n" +
                "\t\t\t\t     LEFT JOIN DF_TARGET.MESG_SECTOR_DESCRIPTION SEC_DESC\n" +
                "                            ON SEC.MESG_SECTOR_ID=SEC_DESC.MESG_SECTOR_ID\n" +
                "            QUALIFY ROW_NUMBER() OVER(PARTITION BY mst.ORBIS_ID ORDER BY mst.MODIFY_DATE_TIME DESC) = 1 )\n" +
                "    ,PF_SEC_DET  AS(\n" +
                "            SELECT P.BVD9_NUMBER\n" +
                "                  ,nvl(p.parent_entity_name,p.company_name) as entity_name\n" +
                "                  ,nvl(SD.MESG_SECTOR_ID,PSD.MESG_SECTOR_ID) as MESG_SECTOR_ID\n" +
                "                  ,nvl(SD.MESG_SECTOR,PSD.MESG_SECTOR) as MESG_SECTOR\n" +
                "                  ,nvl(SD.L1_SECTOR,PSD.L1_SECTOR) as L1_SECTOR\n" +
                "                  ,nvl(SD.L2_SECTOR,pSD.L2_SECTOR) as L2_SECTOR\n" +
                "              FROM P\n" +
                "                   LEFT JOIN SEC_DET SD\n" +
                "                          ON P.bvd9_number = SD.orbis_id\n" +
                "                   LEFT JOIN PRE_SEC_DET PSD\n" +
                "                          ON P.bvd9_number = PSD.orbis_id\n" +
                "             WHERE P.bvd9_number is not null\n" +
                "   )\n" +
                "   SELECT * FROM PF_SEC_DET;\n";
        //System.out.println("query = " + query);
        return DatabaseDriver.getQueryResultMap(query);
    }
}
