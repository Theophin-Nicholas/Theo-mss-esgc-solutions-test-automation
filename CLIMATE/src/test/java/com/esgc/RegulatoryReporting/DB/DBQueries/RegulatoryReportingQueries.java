package com.esgc.RegulatoryReporting.DB.DBQueries;

import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.Database.DatabaseDriver.getQueryResultMap;

public class RegulatoryReportingQueries {
    public List<Map<String,Object>> getCompanyLevelOutput(String portfolioId, String dataType) {
        String query = "SELECT COMPANY_NAME,VE_ID,FACTSET_ID,HIGH_IMPACT_CHK,SFDR_1TXNMYID_1,SFDR_1TXNMYID_2,SFDR_1TXNMYID_4,RPTNG_YR_FOR_SFDR_1,SFDR_2TXNMYID_27,RPTNG_YR_FOR_SFDR_2,SFDR_3TXNMYID_28,RPTNG_YR_FOR_SFDR_3,SFDR_4TXNMYID_23,RPTNG_YR_FOR_SFDR_4,SFDR_5TXNMYID_5,SFDR_5TXNMYID_6,SFDR_5TXNMYID_7,SFDR_5TXNMYID_8,SFDR_5TXNMYID_11,SFDR_5TXNMYID_12,RPTNG_YR_FOR_SFDR_5,SFDR_6TXNMYID_35,RPTNG_YR_FOR_SFDR_6,SFDR_7TXNMYID_25,RPTNG_YR_FOR_SFDR_7,SFDR_8TXNMYID_13,RPTNG_YR_FOR_SFDR_8,SFDR_9TXNMYID_14,RPTNG_YR_FOR_SFDR_9,SFDR_10TXNMYID_29,RPTNG_YR_FOR_SFDR_10,SFDR_12TXNMYID_15,SFDR_12TXNMYID_16,RPTNG_YR_FOR_SFDR_12,SFDR_13TXNMYID_20,SFDR_13TXNMYID_21,SFDR_13TXNMYID_22,SFDR_13FemByTot,SFDR_13FemByMen,RPTNG_YR_FOR_SFDR_13,SFDR_14TXNMYID_24,RPTNG_YR_FOR_SFDR_14,SFDR_17TXNMYID_26,RPTNG_YR_FOR_SFDR_17\n" +
                "FROM VW_SFDR_REPORT_COMPANY_OUTPUT\n" +
                "WHERE PORTFOLIO_ID='"+portfolioId+"' AND DATE_BASED_DATA_TYPE='"+dataType+"'";
        return getQueryResultMap(query);
    }

    public int getNumberOfCompanies(String portfolioId) {
        return getPortfolioDetails(portfolioId).size();
    }
    public List<Map<String, Object>> getPortfolioDetails(String portfolioId) {
        String query = "select * from DF_PORTFOLIO where PORTFOLIO_ID='"+portfolioId+"'";
        return getQueryResultMap(query);
    }

    public List<Map<String, Object>> getReportingYearDetails(String portfolioId) {
        String query = "SELECT DISTINCT tt.portfolio_id --as \"portfolio_id\"\n" +
                "\t\t,sfdr_coverage, taxonomy_coverage\n" +
                "\t\t,reporting_year AS year\n" +
                "  \t\t, tt.as_of_date\n" +
                "\tFROM  (SELECT distinct portfolio_id\n" +
                "\t\t,rrs.bvd9_number orbis_id\n" +
                "\t\t,COUNT(DISTINCT(rrs.bvd9_number)) OVER (partition by portfolio_id) Covered_Comapnies\n" +
                "\t\t,total_companies\n" +
                "\t\t,p.AS_OF_DATE\n" +
                "\tFROM df_target.regulatory_report_sfdr rrs ,\n" +
                "(SELECT portfolio_id\n" +
                "\t\t,nvl(bvd9_number, sec_id) AS bvd9_number\n" +
                "\t\t,COUNT(*) OVER (partition by portfolio_id) total_companies\n" +
                "\t\t,MAX(AS_OF_DATE) OVER (partition by portfolio_id) AS_OF_DATE\n" +
                "\tFROM df_target.df_portfolio\n" +
                "\tWHERE portfolio_id IN  ('"+portfolioId+"')) p\n" +
                "    WHERE rrs.bvd9_number = p.bvd9_number) sc left outer join\n" +
                "        df_target.regulatory_report_sfdr rrs\n" +
                "\ton  rrs.BVD9_NUMBER = sc.orbis_id   inner join\n" +
                "    (select distinct sc.portfolio_id, round((sc.Covered_Comapnies / sc.total_companies) * 100, 0) AS sfdr_coverage, round((tc.Covered_Comapnies / tc.total_companies) * 100, 0) AS taxonomy_coverage,\n" +
                "       sc.AS_OF_DATE from\n" +
                "       (SELECT distinct portfolio_id\n" +
                "\t\t,eto.orbis_id\n" +
                "\t\t,COUNT(DISTINCT(eto.orbis_id)) OVER (partition by portfolio_id) Covered_Comapnies\n" +
                "\t\t,total_companies\n" +
                "\t\t,p.AS_OF_DATE\n" +
                "\tFROM (SELECT portfolio_id\n" +
                "\t\t,nvl(bvd9_number, sec_id) AS bvd9_number\n" +
                "\t\t,COUNT(*) OVER (partition by portfolio_id) total_companies\n" +
                "\t\t,MAX(AS_OF_DATE) OVER (partition by portfolio_id) AS_OF_DATE\n" +
                "\tFROM df_target.df_portfolio\n" +
                "\tWHERE portfolio_id IN  ('"+portfolioId+"')) p left outer join df_target.EU_TAXONOMY_OVERVIEW eto\n" +
                "\t\t\ton eto.orbis_id = p.bvd9_number) tc,\n" +
                "       (SELECT distinct portfolio_id\n" +
                "\t\t,rrs.bvd9_number orbis_id\n" +
                "\t\t,COUNT(DISTINCT(rrs.bvd9_number)) OVER (partition by portfolio_id) Covered_Comapnies\n" +
                "\t\t,total_companies\n" +
                "\t\t,p.AS_OF_DATE\n" +
                "\tFROM df_target.regulatory_report_sfdr rrs ,\n" +
                "(SELECT portfolio_id\n" +
                "\t\t,nvl(bvd9_number, sec_id) AS bvd9_number\n" +
                "\t\t,COUNT(*) OVER (partition by portfolio_id) total_companies\n" +
                "\t\t,MAX(AS_OF_DATE) OVER (partition by portfolio_id) AS_OF_DATE\n" +
                "\tFROM df_target.df_portfolio\n" +
                "\tWHERE portfolio_id IN  ('"+portfolioId+"')) p\n" +
                "    WHERE rrs.bvd9_number = p.bvd9_number) sc\n" +
                "       where sc.portfolio_id = tc.portfolio_id)\n" +
                "    tt on tt.portfolio_id = sc.portfolio_id\t\n" +
                "\tORDER BY portfolio_id\n" +
                "\t\t,reporting_year DESC;";
        return getQueryResultMap(query);
    }

    public List<Map<String, Object>> getSFDRCompanyOutput(String portfolioId, String year) {
        String query = "SELECT * FROM VW_SFDR_REPORT_COMPANY_OUTPUT WHERE PORTFOLIO_ID='"+portfolioId+"' AND YEAR = '"+year+"' AND DATE_BASED_DATA_TYPE='yearly'";
        return getQueryResultMap(query);
    }
}
