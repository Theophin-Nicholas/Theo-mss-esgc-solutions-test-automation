package com.esgc.RegulatoryReporting.DB.DBQueries;

import com.esgc.Utilities.DateTimeUtilities;
import com.esgc.Utilities.PortfolioUtilities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

import static com.esgc.Utilities.Database.DatabaseDriver.*;

public class RegulatoryReportingQueries {
    public List<Map<String,Object>> getCompanyLevelOutput(String portfolioId, String dataType) {
        String query = "SELECT COMPANY_NAME,VE_ID,FACTSET_ID,HIGH_IMPACT_CHK,SFDR_1TXNMYID_1,SFDR_1TXNMYID_2,SFDR_1TXNMYID_4,RPTNG_YR_FOR_SFDR_1,SFDR_2TXNMYID_27,RPTNG_YR_FOR_SFDR_2,SFDR_3TXNMYID_28,RPTNG_YR_FOR_SFDR_3,SFDR_4TXNMYID_23,RPTNG_YR_FOR_SFDR_4,SFDR_5TXNMYID_5,SFDR_5TXNMYID_6,SFDR_5TXNMYID_7,SFDR_5TXNMYID_8,SFDR_5TXNMYID_11,SFDR_5TXNMYID_12,RPTNG_YR_FOR_SFDR_5,SFDR_6TXNMYID_35,RPTNG_YR_FOR_SFDR_6,SFDR_7TXNMYID_25,RPTNG_YR_FOR_SFDR_7,SFDR_8TXNMYID_13,RPTNG_YR_FOR_SFDR_8,SFDR_9TXNMYID_14,RPTNG_YR_FOR_SFDR_9,SFDR_10TXNMYID_29,RPTNG_YR_FOR_SFDR_10,SFDR_12TXNMYID_15,SFDR_12TXNMYID_16,RPTNG_YR_FOR_SFDR_12,SFDR_13TXNMYID_20,SFDR_13TXNMYID_21,SFDR_13TXNMYID_22,SFDR_13FemByMen,RPTNG_YR_FOR_SFDR_13,SFDR_14TXNMYID_24,RPTNG_YR_FOR_SFDR_14,SFDR_17TXNMYID_26,RPTNG_YR_FOR_SFDR_17\n" +
                "FROM VW_SFDR_REPORT_COMPANY_OUTPUT\n" +
                "WHERE PORTFOLIO_ID='"+portfolioId+"' AND DATE_BASED_DATA_TYPE='"+dataType+"'";
        return getQueryResultMap(query);
    }

    public List<Map<String,Object>> getLatestCompanyLevelOutput(String portfolioId) {
        String query = "SELECT COMPANY_NAME, IDENTIFIER_TYPE, YEAR, VE_ID,FACTSET_ID,HIGH_IMPACT_CHK,SFDR_1TXNMYID_1,SFDR_1TXNMYID_2,SFDR_1TXNMYID_4,RPTNG_YR_FOR_SFDR_1,SFDR_2TXNMYID_27,\n" +
                "RPTNG_YR_FOR_SFDR_2,SFDR_3TXNMYID_28,RPTNG_YR_FOR_SFDR_3,SFDR_4TXNMYID_23,RPTNG_YR_FOR_SFDR_4,SFDR_5TXNMYID_5,SFDR_5TXNMYID_7,SFDR_5TXNMYID_11,\n" +
                "SFDR_5TXNMYID_6,SFDR_5TXNMYID_8,SFDR_5TXNMYID_12,RPTNG_YR_FOR_SFDR_5,SFDR_6TXNMYID_35,RPTNG_YR_FOR_SFDR_6,SFDR_7TXNMYID_25,RPTNG_YR_FOR_SFDR_7,\n" +
                "SFDR_8TXNMYID_13,RPTNG_YR_FOR_SFDR_8,SFDR_9TXNMYID_14,RPTNG_YR_FOR_SFDR_9,SFDR_10TXNMYID_29,RPTNG_YR_FOR_SFDR_10,SFDR_12TXNMYID_16,SFDR_12TXNMYID_15,\n" +
                "RPTNG_YR_FOR_SFDR_12,SFDR_13TXNMYID_20,SFDR_13TXNMYID_21,SFDR_13TXNMYID_22,SFDR_13TXNMYID_19,SFDR_13FemByMen,RPTNG_YR_FOR_SFDR_13,SFDR_14TXNMYID_24,\n" +
                "RPTNG_YR_FOR_SFDR_14,SFDR_17TXNMYID_26,RPTNG_YR_FOR_SFDR_17\n" +
                "FROM VW_SFDR_REPORT_COMPANY_OUTPUT\n" +
                "--where COMPANY_NAME like '%Vantage Towers AG%' SFDR_13FemByTot,\n" +
                "WHERE PORTFOLIO_ID='"+portfolioId+"'\n" +
                "//AND YEAR = '2020'\n" +
                "//AND VE_ID='FI0009005987'\n" +
                "AND DATE_BASED_DATA_TYPE='yearly'\n" +
                "AND SFDR_1TXNMYID_4<>'NULL'\n" +
                "//AND FACTSET_ID='001ZJJ-E'\n" +
                "//ORDER BY FACTSET_ID, YEAR DESC\n" +
                "qualify row_number() OVER (partition BY FACTSET_ID ORDER BY FACTSET_ID, YEAR DESC) =1";
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
        String query = "SELECT COMPANY_NAME , IDENTIFIER_TYPE, YEAR, VE_ID,FACTSET_ID,HIGH_IMPACT_CHK,SFDR_1TXNMYID_1,SFDR_1TXNMYID_2,SFDR_1TXNMYID_4,RPTNG_YR_FOR_SFDR_1,SFDR_2TXNMYID_27,\n" +
                "RPTNG_YR_FOR_SFDR_2,SFDR_3TXNMYID_28,RPTNG_YR_FOR_SFDR_3,SFDR_4TXNMYID_23,RPTNG_YR_FOR_SFDR_4,SFDR_5TXNMYID_5,SFDR_5TXNMYID_7,SFDR_5TXNMYID_11,\n" +
                "SFDR_5TXNMYID_6,SFDR_5TXNMYID_8,SFDR_5TXNMYID_12,RPTNG_YR_FOR_SFDR_5,SFDR_6TXNMYID_35,RPTNG_YR_FOR_SFDR_6,SFDR_7TXNMYID_25,RPTNG_YR_FOR_SFDR_7,\n" +
                "SFDR_8TXNMYID_13,RPTNG_YR_FOR_SFDR_8,SFDR_9TXNMYID_14,RPTNG_YR_FOR_SFDR_9,SFDR_10TXNMYID_29,RPTNG_YR_FOR_SFDR_10,SFDR_12TXNMYID_16,SFDR_12TXNMYID_15,\n" +
                "RPTNG_YR_FOR_SFDR_12,SFDR_13TXNMYID_20,SFDR_13TXNMYID_21,SFDR_13TXNMYID_22,SFDR_13TXNMYID_19,SFDR_13FemByMen,RPTNG_YR_FOR_SFDR_13,SFDR_14TXNMYID_24,\n" +
                "RPTNG_YR_FOR_SFDR_14,SFDR_17TXNMYID_26,RPTNG_YR_FOR_SFDR_17\n" +
                "FROM VW_SFDR_REPORT_COMPANY_OUTPUT\n" +
                "--where COMPANY_NAME like '%Vantage Towers AG%' SFDR_13FemByTot,\n" +
                "WHERE PORTFOLIO_ID='"+portfolioId+"'\n" +
                "AND YEAR = '"+year+"'\n" +
                "AND DATE_BASED_DATA_TYPE='yearly'";

        return getQueryResultMap(query);
    }

    public List<String> getSFDRCompanyOutputForAllYears(String portfolioId, String companyName) {
        List<String> data = new ArrayList<>();
        for (int i = 2019; i < 2022; i++) {
            List<Map<String, Object>> result = getSFDRCompanyOutput(portfolioId, String.valueOf(i));
            for (Map<String, Object> company:result){
                if(company.get("COMPANY_NAME").equals(companyName)){
                    company.values().forEach(value -> {
                        if(value==null) data.add("NI");
                        else data.add(value.toString());
                    });
                }
            }
        }
        return data;
    }

    public List<Map<String, Object>> getUserInputHistory(String portfolioId) {
        String query = "with p as (Select * from df_portfolio where portfolio_id ='"+portfolioId+"')\n" +
                "select SEC_ID, COMPANY_NAME, \"VALUE\", round(\"VALUE\"*100/(select sum(\"VALUE\") from p),2) as \"Exposure Amount Percent\" from p";
        return getQueryResultMap(query);
    }
    public List<Map<String, Object>> getUserInputHistoryWithEUR(String portfolioId) {
        String query = "with p as (Select * from df_portfolio where portfolio_id ='"+portfolioId+"'),\n" +
                "c as (select top 1 MIDRATE from DF_LOOKUP.fx_rate where FROMCURRCODE='USD' and TOCURRCODE='EUR' order by CREATE_DATE_TIME Desc)\n" +
                "select SEC_ID, COMPANY_NAME, VALUE*c.MIDRATE as VALUE, round(VALUE*100/(select sum(VALUE) from p),2) as percentage from p, c order by sec_id";
        return getQueryResultMap(query);
    }

    /**
     *
     * @param portfolioId : one portfolio at a time
     * @param reportingYear : even latest data is selected, still reporting year is needed to be provided
     * @param reportFormat : "Interim" or "Annual"
     * @param useLatestData : "Yes" or "No"
     * @return ist<Map<String, Object>>
     */
    public List<Map<String, Object>> getPortfolioLevelOutput(String portfolioId, String reportingYear, String reportFormat, String useLatestData) {
        String query = "";
        if(reportFormat.equals("Interim")){
            query = "with p AS (\n" +
                    "\tSELECT PORTFOLIO_ID, BVD9_NUMBER, VALUE, CURRENCY,\n" +
                    "   SUM(VALUE) OVER (partition by portfolio_id) TOTAL_VALUE\n" +
                    "\tFROM df_target.df_portfolio\n" +
                    "\tWHERE portfolio_id IN ('"+portfolioId+"')           \n" +
                    "\t) --SELECT * FROM p\n" +
                    "\t,eem\n" +
                    "AS (\n" +
                    "\tSELECT portfolio_id\n" +
                    "\t\t,eem.orbis_id,\n" +
                    "        VALUE,\n" +
                    "     TOTAL_VALUE,\n" +
                    "        CURRENCY,\n" +
                    "     eem.FACTSET_ENTITY_ID,\n" +
                    "      fsm.FSYM_company_ID  \n" +
                    " FROM p \n" +
                    "  left outer join DF_TARGET.ESG_ENTITY_MASTER EEM on (p.BVD9_NUMBER = EEM.orbis_id and eem.entity_status ='Active')\n" +
                    "  left outer join DF_DERIVED.ISIN_SORTED ISS ON (EEM.FACTSET_ENTITY_ID = ISS.FACTSET_ENTITY_ID ) and ISS.PRIMARY_ISIN = 'Y'\n" +
                    "  left outer join FACTSET_MOODYS_DS.ff_v3.ff_sec_map fsm on (iss.fsym_id = fsm.fsym_id)\t) \n" +
                    "\t,cy\n" +
                    "AS (\n" +
                    "\tSELECT portfolio_id,\n" +
                    "         rrs.*,COUNT(DISTINCT(rrs.BVD9_NUMBER)) OVER (partition by portfolio_id) TOTAL_CELLS,\n" +
                    "         EEM.VALUE COMPANY_VALUE, \n" +
                    "         EEM.TOTAL_VALUE,\n" +
                    "         CURRENCY,\n" +
                    "       EEM.FACTSET_ENTITY_ID,\n" +
                    "      EEM.FSYM_company_ID\n" +
                    "\tFROM df_target.regulatory_report_sfdr rrs \n" +
                    "\t\t,eem\n" +
                    "\tWHERE rrs.BVD9_NUMBER = eem.orbis_id\n" +
                    "   AND (case when '"+useLatestData+"' =  'No' then REPORTING_YEAR = "+reportingYear+" else  REPORTING_YEAR >=  year(current_date) -3 and REPORTING_YEAR <= year(current_date) end )                            \n" +
                    "   QUALIFY ROW_NUMBER() OVER(PARTITION BY portfolio_id, BVD9_NUMBER, INDICATOR, TAXONOMY_ID ORDER BY REPORTING_YEAR DESC) = 1   \t\n" +
                    "\t), \n" +
                    "    \n" +
                    " ENT_VALUE AS (SELECT CY.*, FBDA.FF_ENTRPR_VAL, FBA.FF_CASH_ST, FBA.FF_SALES  FROM CY  left outer join FACTSET_MOODYS_DS.FF_V3.FF_BASIC_DER_AF FBDA on (CY.FSYM_company_ID  = FBDA.FSYM_ID and FBDA.FF_FYR = CY.REPORTING_YEAR) \n" +
                    "                 left outer join  FACTSET_MOODYS_DS.FF_V3.FF_BASIC_AF FBA ON FBA.FSYM_ID =  CY.FSYM_company_ID AND FBA.FF_FYR = CY.REPORTING_YEAR ),\n" +
                    "       \n" +
                    "    er as (select PORTFOLIO_ID, VE_ID, BVD9_NUMBER, FACTSET_ID, REPORTING_YEAR, PRODUCED_DATE, PRODUCED_YEAR, PRODUCED_MONTH, INDICATOR, NAME, METRIC_UNIT, RELATED_TO, TAXONOMY_ID, VALUE, TOTAL_CELLS, CURRENCY,   \n" +
                    "           \n" +
                    "            FR.MIDRATE * COMPANY_VALUE AS EXPOSURE_AMOUNT , FR.MIDRATE * TOTAL_VALUE TOTAL_EXPOSURE_AMOUNT,  (FR.MIDRATE * COMPANY_VALUE)/(FR.MIDRATE * TOTAL_VALUE) weight_in_portfolio, \n" +
                    "            (CASE WHEN (((FR.MIDRATE * FF_ENTRPR_VAL) + (FR.MIDRATE * FF_CASH_ST)) * 1000000) < 0 THEN NULL ELSE (((FR.MIDRATE * FF_ENTRPR_VAL) + (FR.MIDRATE * FF_CASH_ST)) * 1000000) END)  AS ENTRPR_VAL, \n" +
                    "            ((FR.MIDRATE * FF_SALES) * 1000000) AS REVENUE\n" +
                    "           from ENT_VALUE EV, DF_LOOKUP.FX_RATE  FR where EV.PRODUCED_DATE = FR.EXRATEDATE AND  FR.FROMCURRCODE = EV.CURRENCY AND FR.TOCURRCODE = 'EUR'   \n" +
                    "        ),      \n" +
                    "\n" +
                    "all_columns as (SELECT ER.*, (CASE WHEN NACE_SECTION_CODE  IN ('A','B','C','D','E','F','G','H','L') then 'Y' else 'N' END) AS HIGH_IMPACT_CHECk     \n" +
                    "      FROM er \n" +
                    "LEFT OUTER JOIN FACTSET_MOODYS_DS.ENT_V1.ENT_ENTITY_COVERAGE fec ON fec.FACTSET_ENTITY_ID = ER.FACTSET_ID\n" +
                    "LEFT OUTER JOIN FACTSET_MOODYS_DS.REF_V2.NACE_CLASSIFICATION_MAP nm ON fec.NACE_CODE = nm.NACE_CODE),\n" +
                    "\n" +
                    "output as\n" +
                    "\n" +
                    "(SELECT CATEGORY, SUB_CATEGORY, SFDR_CATEGORY, SFDR_SUBCATEGORY, METRIC_TYPE, sfdr_indicator, spt.TAXONOMY_ID, spt.PORTFOLIO_ID, IFNULL(TO_VARCHAR(ROUND(impact, 2)), 'NI') AS IMPACT, ROUND((Scope_of_Disclosure/TOTAL_CELLS) * 100, 2)  Scope_of_Disclosure  from \n" +
                    " (select spt.*, er.PORTFOLIO_ID from DF_TARGET.SFDR_PORTFOLIO_TEMPLATE spt, (select distinct portfolio_id from er) er)  spt full outer join (\n" +
                    "  \n" +
                    "  \n" +
                    "  select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(CASE WHEN VALUE = 'NI' THEN 0 ELSE VALUE * (EXPOSURE_AMOUNT/ENTRPR_VAL)   END) as Impact, SUM(CASE WHEN VALUE = 'NI' OR VALUE IS NULL THEN 0 ELSE 1 END)  AS Scope_of_Disclosure,  TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_1' AND TAXONOMY_ID = 1  GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID,TOTAL_CELLS\n" +
                    "\n" +
                    "UNION ALL\n" +
                    "select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(CASE WHEN VALUE = 'NI' THEN 0 ELSE VALUE * (EXPOSURE_AMOUNT/ENTRPR_VAL)   END) as Impact, SUM(CASE WHEN VALUE = 'NI' OR VALUE IS NULL THEN 0 ELSE 1 END)  AS Scope_of_Disclosure, TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_1' AND TAXONOMY_ID = 2  GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID,TOTAL_CELLS\n" +
                    "\n" +
                    "UNION ALL\n" +
                    "select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(CASE WHEN VALUE = 'NI' THEN 0 ELSE VALUE * (EXPOSURE_AMOUNT/ENTRPR_VAL)   END) as Impact, SUM(CASE WHEN VALUE = 'NI' OR VALUE IS NULL THEN 0 ELSE 1 END)  AS Scope_of_Disclosure, TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_1' AND TAXONOMY_ID = 3  GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID,TOTAL_CELLS\n" +
                    "\n" +
                    "\n" +
                    "UNION ALL\n" +
                    "select A.PORTFOLIO_ID, A.INDICATOR, A.TAXONOMY_ID, SUM(A.Impact), B.Scope_of_Disclosure, B.TOTAL_CELLS FROM (select PORTFOLIO_ID, INDICATOR, 4 as TAXONOMY_ID, \n" +
                    "SUM(CASE WHEN   VALUE = 'NI' THEN 0 ELSE VALUE * (EXPOSURE_AMOUNT/ENTRPR_VAL)  END) as Impact \n" +
                    "from all_columns  where INDICATOR = 'SFDR_1' AND  TAXONOMY_ID = 4 GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID ) A, \n" +
                    "(select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(CASE WHEN VALUE = 'NI' OR VALUE IS NULL THEN 0 ELSE 1 END)  AS Scope_of_Disclosure, TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_1' AND TAXONOMY_ID = 4 GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID,TOTAL_CELLS) B WHERE a.PORTFOLIO_ID = b.PORTFOLIO_ID and a.INDICATOR = b.INDICATOR \n" +
                    "   GROUP BY A.PORTFOLIO_ID, A.INDICATOR, A.TAXONOMY_ID, B.TOTAL_CELLS, B.Scope_of_Disclosure\n" +
                    "   \n" +
                    "\n" +
                    "UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(VALUE * EXPOSURE_AMOUNT)/SUM(EXPOSURE_AMOUNT) AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS FROM   (select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_2' AND  TAXONOMY_ID = 27  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS\n" +
                    "\n" +
                    "   UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(VALUE * EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT) AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS FROM   (select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_3' AND  TAXONOMY_ID = 28  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS  \n" +
                    "   \n" +
                    " UNION ALL\n" +
                    "   \n" +
                    "   \n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(CASE WHEN VALUE = 'No' THEN 0 ELSE EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT END) * 100 AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS FROM ( select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_4' AND  TAXONOMY_ID = 23  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS  \n" +
                    "\n" +
                    "\n" +
                    "UNION ALL\n" +
                    "   \n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(VALUE * EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT) * 100 AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS FROM   (select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_5' AND  TAXONOMY_ID = 11  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS\n" +
                    "\n" +
                    "UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(VALUE * EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT) * 100 AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS FROM   (select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_5' AND  TAXONOMY_ID = 12  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS  \n" +
                    "   \n" +
                    "\n" +
                    "UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(CASE WHEN HIGH_IMPACT_CHECK = 'N' THEN 0 ELSE (EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT) * VALUE END) AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS FROM ( \n" +
                    "  select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS, HIGH_IMPACT_CHECK\n" +
                    "from all_columns  where INDICATOR = 'SFDR_6'  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS     \n" +
                    "   \n" +
                    "   \n" +
                    "UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM((EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT) * VALUE) * 100 AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS FROM ( \n" +
                    "  select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_7'  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS  \n" +
                    "   \n" +
                    "   \n" +
                    "UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM((EXPOSURE_AMOUNT/(ENTRPR_VAL/1000000)) * VALUE)/SUM(EXPOSURE_AMOUNT) AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS  FROM (select PORTFOLIO_ID, INDICATOR, ENTRPR_VAL, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_8'  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS  \n" +
                    "   \n" +
                    "  \n" +
                    "   \n" +
                    " UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM((EXPOSURE_AMOUNT/(ENTRPR_VAL/1000000)) * VALUE)/SUM(EXPOSURE_AMOUNT) AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS  FROM (select PORTFOLIO_ID, INDICATOR, ENTRPR_VAL, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_9'  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS    \n" +
                    "\n" +
                    "   \n" +
                    " UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(CASE WHEN VALUE = 'Yes' THEN   EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT  END) * 100 AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS  FROM (select PORTFOLIO_ID, INDICATOR, ENTRPR_VAL, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_10'  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS    \n" +
                    "\n" +
                    "\n" +
                    " UNION ALL \n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(VALUE * EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT) * 100 AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS FROM   (select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_12' AND  TAXONOMY_ID = 15  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS\n" +
                    "\n" +
                    " UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(VALUE * EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT) * 100 AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS FROM   (select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_12' AND  TAXONOMY_ID = 16  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS  \n" +
                    "     \n" +
                    "   \n" +
                    "   \n" +
                    "       UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, 20 as TAXONOMY_ID, SUM((VALUE/(1-VALUE)) * EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT) * 100 AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS FROM   (select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_13' AND  TAXONOMY_ID = 19  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS  \n" +
                    "\n" +
                    "   \n" +
                    "    UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, 21 as TAXONOMY_ID, SUM(VALUE * EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT) * 100 AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS FROM   (select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_13' AND  TAXONOMY_ID = 19  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS  \n" +
                    "\n" +
                    "   \n" +
                    " UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(CASE WHEN VALUE = 'Yes' THEN   EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT  END) * 100 AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS  FROM (select PORTFOLIO_ID, INDICATOR, ENTRPR_VAL, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_14'  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS    \n" +
                    "   \n" +
                    "UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(VALUE * EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT  ) * 100 AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS  FROM (select PORTFOLIO_ID, INDICATOR, ENTRPR_VAL, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_17'  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS   \n" +
                    "\n" +
                    "\n" +
                    ") calc  on spt.PORTFOLIO_ID = calc.PORTFOLIO_ID and spt.sfdr_indicator = calc.indicator  and spt.TAXONOMY_ID = calc.TAXONOMY_ID \n" +
                    " order by PORTFOLIO_ID,TO_NUMBER(SUBSTRING(TRIM(spt.SFDR_INDICATOR),  6, 2))  ASC , spt.TAXONOMY_ID asc )\n" +
                    " \n" +
                    " \n" +
                    "     SELECT * FROM OUTPUT";
        }
        else {
            query="                 \n" +
                    "           with p AS (\n" +
                    "\tSELECT PORTFOLIO_ID, BVD9_NUMBER, VALUE, CURRENCY,\n" +
                    "   SUM(VALUE) OVER (partition by portfolio_id) TOTAL_VALUE\n" +
                    "\tFROM df_target.df_portfolio\n" +
                    "\tWHERE portfolio_id IN ('"+portfolioId+"')           \n" +
                    "\t) --SELECT * FROM p\n" +
                    "\t,eem\n" +
                    "AS (\n" +
                    "\tSELECT portfolio_id\n" +
                    "\t\t,eem.orbis_id,\n" +
                    "        VALUE,\n" +
                    "     TOTAL_VALUE,\n" +
                    "        CURRENCY,\n" +
                    "     eem.FACTSET_ENTITY_ID,\n" +
                    "      fsm.FSYM_company_ID      \n" +
                    " FROM p \n" +
                    "  left outer join DF_TARGET.ESG_ENTITY_MASTER EEM on (p.BVD9_NUMBER = EEM.orbis_id and eem.entity_status ='Active')\n" +
                    "  left outer join DF_DERIVED.ISIN_SORTED ISS ON (EEM.FACTSET_ENTITY_ID = ISS.FACTSET_ENTITY_ID ) and ISS.PRIMARY_ISIN = 'Y'\n" +
                    "  left outer join FACTSET_MOODYS_DS.ff_v3.ff_sec_map fsm on (iss.fsym_id = fsm.fsym_id)\t) \n" +
                    "\t,cy\n" +
                    "AS (\n" +
                    "\tSELECT portfolio_id,\n" +
                    "         rrs.*, COUNT(DISTINCT(rrs.BVD9_NUMBER)) OVER (partition by portfolio_id) TOTAL_CELLS,\n" +
                    "         EEM.VALUE COMPANY_VALUE, \n" +
                    "         EEM.TOTAL_VALUE,\n" +
                    "         CURRENCY,\n" +
                    "       EEM.FACTSET_ENTITY_ID,\n" +
                    "      EEM.FSYM_company_ID\n" +
                    "\tFROM df_target.regulatory_report_sfdr rrs \n" +
                    "\t\t,eem\n" +
                    "\tWHERE rrs.BVD9_NUMBER = eem.orbis_id\n" +
                    "   AND (case when '"+useLatestData+"' =  'No' then REPORTING_YEAR = "+reportingYear+" else  REPORTING_YEAR >=  year(current_date) -3 and REPORTING_YEAR <= year(current_date) end )                            \n" +
                    "   QUALIFY ROW_NUMBER() OVER(PARTITION BY portfolio_id, BVD9_NUMBER, INDICATOR, TAXONOMY_ID ORDER BY REPORTING_YEAR DESC) = 1   \t\n" +
                    "\t), \n" +
                    "    \n" +
                    "    ENT_VALUE AS (SELECT CY.*, FBDA.FF_ENTRPR_VAL, FBA.FF_CASH_ST, FBA.FF_SALES  FROM CY  left outer join FACTSET_MOODYS_DS.FF_V3.FF_BASIC_DER_AF FBDA on (CY.FSYM_company_ID  = FBDA.FSYM_ID and FBDA.FF_FYR = CY.REPORTING_YEAR) \n" +
                    "                 left outer join  FACTSET_MOODYS_DS.FF_V3.FF_BASIC_AF FBA ON FBA.FSYM_ID =  CY.FSYM_company_ID AND FBA.FF_FYR = CY.REPORTING_YEAR ),\n" +
                    "       \n" +
                    "    er as (select PORTFOLIO_ID, VE_ID, BVD9_NUMBER, FACTSET_ID, REPORTING_YEAR, PRODUCED_DATE, PRODUCED_YEAR, PRODUCED_MONTH, INDICATOR, NAME, METRIC_UNIT, RELATED_TO, TAXONOMY_ID, VALUE, TOTAL_CELLS, CURRENCY,   \n" +
                    "           \n" +
                    "            FR.MIDRATE * COMPANY_VALUE AS EXPOSURE_AMOUNT , FR.MIDRATE * TOTAL_VALUE TOTAL_EXPOSURE_AMOUNT,  (FR.MIDRATE * COMPANY_VALUE)/(FR.MIDRATE * TOTAL_VALUE) weight_in_portfolio, \n" +
                    "            (CASE WHEN (((FR.MIDRATE * FF_ENTRPR_VAL) + (FR.MIDRATE * FF_CASH_ST)) * 1000000) < 0 THEN NULL ELSE (((FR.MIDRATE * FF_ENTRPR_VAL) + (FR.MIDRATE * FF_CASH_ST)) * 1000000) END)  AS ENTRPR_VAL, ((FR.MIDRATE * FF_SALES) * 1000000) AS REVENUE\n" +
                    "           from ENT_VALUE EV, DF_LOOKUP.FX_RATE  FR where EV.PRODUCED_DATE = FR.EXRATEDATE AND  FR.FROMCURRCODE = EV.CURRENCY AND FR.TOCURRCODE = 'EUR'   \n" +
                    "        ),      \n" +
                    "\n" +
                    "all_columns as (SELECT ER.*, (CASE WHEN NACE_SECTION_CODE  IN ('A','B','C','D','E','F','G','H','L') then 'Y' else 'N' END) AS HIGH_IMPACT_CHECk     \n" +
                    "      FROM er \n" +
                    "LEFT OUTER JOIN FACTSET_MOODYS_DS.ENT_V1.ENT_ENTITY_COVERAGE fec ON fec.FACTSET_ENTITY_ID = ER.FACTSET_ID\n" +
                    "LEFT OUTER JOIN FACTSET_MOODYS_DS.REF_V2.NACE_CLASSIFICATION_MAP nm ON fec.NACE_CODE = nm.NACE_CODE),\n" +
                    "\n" +
                    "output as\n" +
                    "\n" +
                    "(SELECT CATEGORY, SUB_CATEGORY, SFDR_CATEGORY, SFDR_SUBCATEGORY, METRIC_TYPE, sfdr_indicator, spt.TAXONOMY_ID, spt.PORTFOLIO_ID, ROUND(impact, 2) AS IMPACT, ROUND((Scope_of_Disclosure/TOTAL_CELLS) * 100, 2)  Scope_of_Disclosure  from \n" +
                    " (select spt.*, er.PORTFOLIO_ID from DF_TARGET.SFDR_PORTFOLIO_TEMPLATE spt, (select distinct portfolio_id from er) er)  spt full outer join (\n" +
                    "  \n" +
                    "  \n" +
                    "  select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(CASE WHEN VALUE = 'NI' THEN 0 ELSE VALUE * (EXPOSURE_AMOUNT/ENTRPR_VAL)   END) as Impact, SUM(CASE WHEN VALUE = 'NI' OR VALUE IS NULL THEN 0 ELSE 1 END)  AS Scope_of_Disclosure,  TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_1' AND TAXONOMY_ID = 1  GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID,TOTAL_CELLS\n" +
                    "\n" +
                    "UNION ALL\n" +
                    "select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(CASE WHEN VALUE = 'NI' THEN 0 ELSE VALUE * (EXPOSURE_AMOUNT/ENTRPR_VAL)   END) as Impact, SUM(CASE WHEN VALUE = 'NI' OR VALUE IS NULL THEN 0 ELSE 1 END)  AS Scope_of_Disclosure, TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_1' AND TAXONOMY_ID = 2  GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID,TOTAL_CELLS\n" +
                    "\n" +
                    "UNION ALL\n" +
                    "select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(CASE WHEN VALUE = 'NI' THEN 0 ELSE VALUE * (EXPOSURE_AMOUNT/ENTRPR_VAL)   END) as Impact, SUM(CASE WHEN VALUE = 'NI' OR VALUE IS NULL THEN 0 ELSE 1 END)  AS Scope_of_Disclosure, TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_1' AND TAXONOMY_ID = 3  GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID,TOTAL_CELLS\n" +
                    "\n" +
                    "UNION ALL\n" +
                    "select A.PORTFOLIO_ID, A.INDICATOR, A.TAXONOMY_ID, SUM(A.Impact), B.Scope_of_Disclosure, B.TOTAL_CELLS FROM (select PORTFOLIO_ID, INDICATOR, 4 as TAXONOMY_ID, \n" +
                    "SUM(CASE WHEN   VALUE = 'NI' THEN 0 ELSE VALUE * (EXPOSURE_AMOUNT/ENTRPR_VAL)  END) as Impact \n" +
                    "from all_columns  where INDICATOR = 'SFDR_1' AND  TAXONOMY_ID = 4 GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID ) A, \n" +
                    "(select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(CASE WHEN VALUE = 'NI' OR VALUE IS NULL THEN 0 ELSE 1 END)  AS Scope_of_Disclosure, TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_1' AND TAXONOMY_ID = 4 GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID,TOTAL_CELLS) B WHERE a.PORTFOLIO_ID = b.PORTFOLIO_ID and a.INDICATOR = b.INDICATOR \n" +
                    "   GROUP BY A.PORTFOLIO_ID, A.INDICATOR, A.TAXONOMY_ID, B.TOTAL_CELLS, B.Scope_of_Disclosure\n" +
                    "   \n" +
                    "\n" +
                    "UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(VALUE * EXPOSURE_AMOUNT)/SUM(EXPOSURE_AMOUNT) AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS FROM   (select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_2' AND  TAXONOMY_ID = 27  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS\n" +
                    "\n" +
                    "   UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(VALUE * EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT) AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS FROM   (select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_3' AND  TAXONOMY_ID = 28  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS  \n" +
                    "   \n" +
                    " UNION ALL\n" +
                    "   \n" +
                    "   \n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(CASE WHEN VALUE = 'No' THEN 0 ELSE EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT END) * 100 AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS FROM ( select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_4' AND  TAXONOMY_ID = 23  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS  \n" +
                    "\n" +
                    "\n" +
                    "UNION ALL\n" +
                    "   \n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(VALUE * EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT) * 100 AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS FROM   (select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_5' AND  TAXONOMY_ID = 11  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS\n" +
                    "\n" +
                    "UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(VALUE * EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT) * 100 AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS FROM   (select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_5' AND  TAXONOMY_ID = 12  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS  \n" +
                    "   \n" +
                    "\n" +
                    "UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(CASE WHEN HIGH_IMPACT_CHECK = 'N' THEN 0 ELSE (EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT) * VALUE END) AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS FROM ( \n" +
                    "  select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS, HIGH_IMPACT_CHECK\n" +
                    "from all_columns  where INDICATOR = 'SFDR_6'  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS     \n" +
                    "   \n" +
                    "   \n" +
                    "UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM((EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT) * VALUE) * 100 AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS FROM ( \n" +
                    "  select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_7'  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS  \n" +
                    "   \n" +
                    "   \n" +
                    "UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM((EXPOSURE_AMOUNT/(ENTRPR_VAL/1000000)) * VALUE)/SUM(EXPOSURE_AMOUNT) AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS  FROM (select PORTFOLIO_ID, INDICATOR, ENTRPR_VAL, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_8'  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS  \n" +
                    "   \n" +
                    "  \n" +
                    "   \n" +
                    " UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM((EXPOSURE_AMOUNT/(ENTRPR_VAL/1000000)) * VALUE)/SUM(EXPOSURE_AMOUNT) AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS  FROM (select PORTFOLIO_ID, INDICATOR, ENTRPR_VAL, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_9'  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS    \n" +
                    "\n" +
                    "   \n" +
                    " UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(CASE WHEN VALUE = 'Yes' THEN   EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT  END) * 100 AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS  FROM (select PORTFOLIO_ID, INDICATOR, ENTRPR_VAL, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_10'  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS    \n" +
                    "\n" +
                    "\n" +
                    " UNION ALL \n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(VALUE * EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT) * 100 AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS FROM   (select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_12' AND  TAXONOMY_ID = 15  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS\n" +
                    "\n" +
                    " UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(VALUE * EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT) * 100 AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS FROM   (select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_12' AND  TAXONOMY_ID = 16  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS  \n" +
                    "     \n" +
                    " \n" +
                    "        UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, 20 as TAXONOMY_ID, SUM((VALUE/(1-VALUE)) * EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT) * 100 AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS FROM   (select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_13' AND  TAXONOMY_ID = 19  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS  \n" +
                    "   \n" +
                    "\n" +
                    "    UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, 21 as TAXONOMY_ID, SUM(VALUE * EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT) * 100 AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS FROM   (select PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_13' AND  TAXONOMY_ID = 19  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS  \n" +
                    "   \n" +
                    "   \n" +
                    " UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(CASE WHEN VALUE = 'Yes' THEN   EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT  END) * 100 AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS  FROM (select PORTFOLIO_ID, INDICATOR, ENTRPR_VAL, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_14'  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS    \n" +
                    "   \n" +
                    "UNION ALL\n" +
                    "SELECT PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, SUM(VALUE * EXPOSURE_AMOUNT/TOTAL_EXPOSURE_AMOUNT  ) * 100 AS IMPACT , SUM(Scope_of_Disclosure), TOTAL_CELLS  FROM (select PORTFOLIO_ID, INDICATOR, ENTRPR_VAL, TAXONOMY_ID, Value,  EXPOSURE_AMOUNT , SUM(EXPOSURE_AMOUNT) OVER (partition by portfolio_id) AS TOTAL_EXPOSURE_AMOUNT,  1  AS Scope_of_Disclosure ,TOTAL_CELLS\n" +
                    "from all_columns  where INDICATOR = 'SFDR_17'  AND  (VALUE <> 'NI' OR VALUE IS NULL)) GROUP BY PORTFOLIO_ID, INDICATOR, TAXONOMY_ID, TOTAL_CELLS   \n" +
                    "\n" +
                    "\n" +
                    ") calc  on spt.PORTFOLIO_ID = calc.PORTFOLIO_ID and spt.sfdr_indicator = calc.indicator  and spt.TAXONOMY_ID = calc.TAXONOMY_ID \n" +
                    " order by PORTFOLIO_ID,TO_NUMBER(SUBSTRING(TRIM(spt.SFDR_INDICATOR),  6, 2))  ASC , spt.TAXONOMY_ID asc ),\n" +
                    " \n" +
                    " FINAL_OUTPUT AS\n" +
                    " (SELECT * FROM OUTPUT\n" +
                    " UNION ALL\n" +
                    " SELECT CATEGORY, SUB_CATEGORY, SFDR_CATEGORY, SFDR_SUBCATEGORY, METRIC_TYPE, SFDR_INDICATOR, TAXONOMY_ID, 'Average Impact Q1-Q2-Q3-Q4' AS PORTFOLIO_ID, SUM(IMPACT)/4   , AVG(SCOPE_OF_DISCLOSURE) FROM OUTPUT\n" +
                    " GROUP BY CATEGORY, SUB_CATEGORY, SFDR_CATEGORY, SFDR_SUBCATEGORY, METRIC_TYPE, SFDR_INDICATOR, TAXONOMY_ID, 'Average Impact Q1-Q2-Q3-Q4'  )\n" +
                    " \n" +
                    " \n" +
                    "     SELECT * FROM FINAL_OUTPUT";
        }
        return getQueryResultMap(query);
    }
}
