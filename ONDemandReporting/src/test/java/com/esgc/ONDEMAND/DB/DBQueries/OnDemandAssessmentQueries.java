package com.esgc.ONDEMAND.DB.DBQueries;

import com.esgc.ONDEMAND.DB.DBModels.OnDemandPortfolioTable;
import com.esgc.Utilities.Database.DatabaseDriver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class OnDemandAssessmentQueries {

    public List<Map<String, Object>> getCompaniesInvestmentInfo(String portfolioId) {
        String query1 = "select SUM(VALUE) as TotalInv from df_target.df_portfolio Where portfolio_id = '"+portfolioId+"'";
        String totalInvestments = DatabaseDriver.getQueryResultMap(query1).get(0).get("TOTALINV").toString();

        String query2 = "select COMPANY_NAME, VALUE, Round((VALUE/"+totalInvestments+")*100, 2) as InvPerc from df_target.df_portfolio Where portfolio_id = '"+portfolioId+"' order by COMPANY_NAME ASC";
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

        String query2 = "select ROUND(max(score)/"+totalInvestments+"*100,2) as MAXSCORE from (\n" +
                "select SUM(ptf.VALUE) score from df_target.ESG_OVERALL_SCORES EST\n" +
                "JOIN df_target.df_portfolio ptf on ptf.BVD9_NUMBER = EST.ORBIS_ID\n" +
                "Where portfolio_id = '"+portfolioId+"'\n" +
                "and SUB_CATEGORY = 'ESG' and DATA_TYPE = 'esg_pillar_score' and EST.score_quality='Predicted' group by EST.VALUE)";
        String maxScore = DatabaseDriver.getQueryResultMap(query2).get(0).get("MAXSCORE").toString()+"%";

        return maxScore;
    }

    public String getEligibleAssessment(String portfolioId) {
        String query1 = "select count(DISTINCT ORBIS_ID) as companiesCount from df_target.ESG_OVERALL_SCORES EST\n" +
                "JOIN df_target.df_portfolio ptf on ptf.BVD9_NUMBER = EST.ORBIS_ID\n" +
                "Where portfolio_id = '"+portfolioId+"'\n" +
                "and SUB_CATEGORY = 'ESG' and DATA_TYPE = 'esg_pillar_score'";
        int companiesWithPredictedScores = Integer.parseInt(DatabaseDriver.getQueryResultMap(query1).get(0).get("COMPANIESCOUNT").toString());

        String query2 = "select count(DISTINCT ORBIS_ID) as companiesCount from df_target.ESG_OVERALL_SCORES EST\n" +
                "JOIN df_target.df_portfolio ptf on ptf.BVD9_NUMBER = EST.ORBIS_ID\n" +
                "Where portfolio_id = '"+portfolioId+"'\n" +
                "and SUB_CATEGORY = 'ESG'";
        int companiesWithEsg = Integer.parseInt(DatabaseDriver.getQueryResultMap(query2).get(0).get("COMPANIESCOUNT").toString());

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

        rowList.addAll(DatabaseDriver.getColumnData(query, "COMPANY_NAME"));
        return rowList;
    }


    public List<String> getEntitieNamesFromResultSet(String portfolioId){
        List<Map<String, Object>> resultSet = getEntitiesWithScoreType(portfolioId);



        List<String> entityNames = new LinkedList<>();

        for(int i=0; i<entityNames.size(); i++){
            entityNames.add(resultSet.get(i).get("COMPANY_NAME").toString());
        }

        return entityNames;
    }


}
