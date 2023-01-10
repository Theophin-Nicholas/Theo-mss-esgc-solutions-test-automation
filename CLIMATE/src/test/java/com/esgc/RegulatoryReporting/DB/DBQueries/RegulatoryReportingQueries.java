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

    public Map<String, Object> getUserInputHistory(String portfolioId, String secId) {
        String query = "with p as (Select * from df_portfolio where portfolio_id ='"+portfolioId+"')\n" +
                "select SEC_ID, COMPANY_NAME, \"VALUE\", round(\"VALUE\"*100/(select sum(\"VALUE\") from p),2) as percentage from p where SEC_ID='"+secId+"';";
        Map<String, Object> result = getRowMap(query);
        //System.out.println("PERCENTAGE = " + result.get("PERCENTAGE"));
        return getRowMap(query);
    }

    public List<Map<String, Object>> getLatestEnterpriseValue(String portfolioId) {
        String query = "with p AS (\n" +
                "\tSELECT PORTFOLIO_ID, BVD9_NUMBER, VALUE, CURRENCY,\n" +
                "   SUM(VALUE) OVER (partition by portfolio_id) TOTAL_VALUE\n" +
                "\tFROM df_target.df_portfolio\n" +
                "\tWHERE portfolio_id IN ('"+portfolioId+"')\n" +
                "\t) --SELECT * FROM p\n" +
                "\t,eem\n" +
                "AS (\n" +
                "\tSELECT portfolio_id\n" +
                "\t\t,eem.orbis_id,\n" +
                "        VALUE,\n" +
                "     TOTAL_VALUE,\n" +
                "        CURRENCY,\n" +
                "     eem.FACTSET_ENTITY_ID,\n" +
                "      fsm.FSYM_company_ID\n" +
                " FROM p\n" +
                "  left outer join DF_TARGET.ESG_ENTITY_MASTER EEM on (p.BVD9_NUMBER = EEM.orbis_id and eem.entity_status ='Active')\n" +
                "  left outer join DF_DERIVED.ISIN_SORTED ISS ON (EEM.FACTSET_ENTITY_ID = ISS.FACTSET_ENTITY_ID ) and ISS.PRIMARY_ISIN = 'Y'\n" +
                "  left outer join FACTSET_MOODYS_DS.ff_v3.ff_sec_map fsm on (iss.fsym_id = fsm.fsym_id)\t) -- select FSYM_COMPANY_ID from eem;\n" +
                "\t,cy\n" +
                "AS (\n" +
                "\tSELECT portfolio_id,\n" +
                "         rrs.*,COUNT(DISTINCT(rrs.BVD9_NUMBER)) OVER (partition by portfolio_id) TOTAL_CELLS,\n" +
                "         EEM.VALUE COMPANY_VALUE,\n" +
                "         EEM.TOTAL_VALUE,\n" +
                "         CURRENCY,\n" +
                "       EEM.FACTSET_ENTITY_ID,\n" +
                "      EEM.FSYM_company_ID\n" +
                "\tFROM df_target.regulatory_report_sfdr rrs\n" +
                "\t\t,eem\n" +
                "\tWHERE rrs.BVD9_NUMBER = eem.orbis_id\n" +
                "//   AND 'No' =  'No' and REPORTING_YEAR = 2021 --(case when 'No' =  'No' then REPORTING_YEAR = 2019 else  REPORTING_YEAR >=  year(current_date) -3 and REPORTING_YEAR <= year(current_date) end )\n" +
                "   QUALIFY ROW_NUMBER() OVER(PARTITION BY portfolio_id, BVD9_NUMBER, INDICATOR, TAXONOMY_ID ORDER BY REPORTING_YEAR DESC) = 1   \t\n" +
                "\t),\n" +
                " ENT_VALUE AS (SELECT CY.*, FBDA.FF_ENTRPR_VAL, FBA.FF_CASH_ST, FBA.FF_SALES  FROM CY  left outer join FACTSET_MOODYS_DS.FF_V3.FF_BASIC_DER_AF FBDA on (CY.FSYM_company_ID  = FBDA.FSYM_ID and FBDA.FF_FYR = CY.REPORTING_YEAR)\n" +
                "                 left outer join  FACTSET_MOODYS_DS.FF_V3.FF_BASIC_AF FBA ON FBA.FSYM_ID =  CY.FSYM_company_ID AND FBA.FF_FYR = CY.REPORTING_YEAR ),\n" +
                "    er as (select PORTFOLIO_ID, VE_ID, BVD9_NUMBER, FACTSET_ID, REPORTING_YEAR, PRODUCED_DATE, PRODUCED_YEAR, PRODUCED_MONTH, INDICATOR, NAME, METRIC_UNIT, RELATED_TO, TAXONOMY_ID, VALUE, TOTAL_CELLS, CURRENCY,\n" +
                "            FR.MIDRATE * COMPANY_VALUE AS EXPOSURE_AMOUNT , FF_ENTRPR_VAL,FF_CASH_ST, FR.MIDRATE,   FR.MIDRATE * TOTAL_VALUE TOTAL_EXPOSURE_AMOUNT,  (FR.MIDRATE * COMPANY_VALUE)/(FR.MIDRATE * TOTAL_VALUE) weight_in_portfolio,\n" +
                "            (CASE WHEN (((FR.MIDRATE * FF_ENTRPR_VAL) + (FR.MIDRATE * FF_CASH_ST)) * 1000000) < 0 THEN NULL ELSE (((FR.MIDRATE * FF_ENTRPR_VAL) + (FR.MIDRATE * FF_CASH_ST)) * 1000000) END)  AS ENTRPR_VAL,\n" +
                "            ((FR.MIDRATE * FF_SALES) * 1000000) AS REVENUE\n" +
                "           from ENT_VALUE EV, DF_LOOKUP.FX_RATE  FR where EV.PRODUCED_DATE = FR.EXRATEDATE AND  FR.FROMCURRCODE = EV.CURRENCY AND FR.TOCURRCODE = 'EUR'\n" +
                "        ) select BVD9_NUMBER,FACTSET_ID, ENTRPR_VAL, REPORTING_YEAR from er qualify row_number() OVER (partition BY FACTSET_ID ORDER BY FACTSET_ID, REPORTING_YEAR DESC) =1";

        return getQueryResultMap(query);
    }

    public Object getLatestDataForCompany(String portfolioId, Object factset_id, String data) {
        String query = "SELECT TOP 1 COMPANY_NAME, IDENTIFIER_TYPE, YEAR, VE_ID,FACTSET_ID,HIGH_IMPACT_CHK,SFDR_1TXNMYID_1,SFDR_1TXNMYID_2,SFDR_1TXNMYID_4,RPTNG_YR_FOR_SFDR_1,SFDR_2TXNMYID_27,\n" +
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
                "AND "+data+"<>'NI'\n" +
                "AND FACTSET_ID='"+factset_id+"'\n" +
                "ORDER BY FACTSET_ID, YEAR DESC";
        Map<String, Object> result = getRowMap(query);
        if (result == null || result.isEmpty() || result.get(data) == null) {
            return 0;
        }
        System.out.println("scope1 = " + result.get(data));
        return result.get(data);
    }

    public List<Double> getSumOfExposure(String portfolioId) {
        List<Map<String, Object>> companyOutput = getLatestCompanyLevelOutput(portfolioId);
        List<Map<String, Object>> userInput = getUserInputHistory(portfolioId);
        System.out.println("userInput keySet() = " + userInput.get(0).keySet());
        List<Map<String, Object>> enterpriseValue = getLatestEnterpriseValue(portfolioId);
        System.out.println("getLatestEnterpriseValue keySet() = " + enterpriseValue.get(0).keySet());
        System.out.println("enterpriseValue.size() = " + enterpriseValue.size());
        
        double Scope1GHGemissions = 0;
        double Scope2GHGemissions = 0;
        double Scope3GHGemissions = 0;
        List<Map<String, String>> values = new ArrayList<>();
        for (int i = 0; i < companyOutput.size(); i++) {
            //System.out.println("companyOutput = " + companyOutput.get(i));
            String factset_id = companyOutput.get(i).get("FACTSET_ID").toString();
            Double scope1 = findData(companyOutput, "FACTSET_ID", factset_id, "SFDR_1TXNMYID_1");
            Double scope2 = findData(companyOutput, "FACTSET_ID", factset_id, "SFDR_1TXNMYID_2");
            Double totalGHGEmissions = findData(companyOutput, "FACTSET_ID", factset_id, "SFDR_1TXNMYID_4");
            Double exposure = findData(userInput, "COMPANY_NAME", companyOutput.get(i).get("COMPANY_NAME").toString(), "VALUE");
            Double entpValue = findData(enterpriseValue, "FACTSET_ID", factset_id, "ENTRPR_VAL");
            //convert entpValue from scientific notation to 2 digit decimal
            System.out.println(factset_id+", "+scope1+", "+scope2+", "+totalGHGEmissions+", "+exposure+", "+entpValue);

            //if exposure or entpValue is 0 then skip
            if (exposure == 0 || entpValue == 0) continue;
            Scope1GHGemissions = Scope1GHGemissions + PortfolioUtilities.round(scope1 * PortfolioUtilities.round(exposure / entpValue, 2),2);
            Scope2GHGemissions = Scope2GHGemissions + PortfolioUtilities.round(scope2 * PortfolioUtilities.round(exposure / entpValue, 3),3);
            Scope3GHGemissions = Scope3GHGemissions + PortfolioUtilities.round(totalGHGEmissions * PortfolioUtilities.round(exposure / entpValue, 4),4);
            System.out.println(Scope1GHGemissions+", "+Scope2GHGemissions+", "+Scope3GHGemissions);
            System.out.println();
        }
        List<Double> scores = new ArrayList<>(Arrays.asList(Scope1GHGemissions, Scope2GHGemissions, Scope3GHGemissions));
        return scores;
    }

    public static Double findData(List<Map<String, Object>>dataMap, String key, String value, String returnKey){
        for (int i = 0; i < dataMap.size(); i++) {
            if(dataMap.get(i).get(key)==null) continue;
            if (dataMap.get(i).get(key).toString().equals(value)){
                if(dataMap.get(i).get(returnKey)==null) continue;
                if(dataMap.get(i).get(returnKey).toString().equals("NI"))return 0.0;
                return Double.parseDouble(dataMap.get(i).get(returnKey).toString());
            }
        }
        return 0.0;
    }
}
