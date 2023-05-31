package com.esgc.Dashboard.DB.DBQueries;

import com.esgc.DBModels.DashboardPerformanceChart;
import com.esgc.Utilities.Database.DatabaseDriver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.Database.DatabaseDriver.getQueryResultList;
import static com.esgc.Utilities.Database.DatabaseDriver.getQueryResultMap;

public class DashboardQueries {

    private static final DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");

    public Map<String, String> getCoverage(String portfolioId, int year, int month) {
        System.out.println("portfolioId = " + portfolioId);
        System.out.println("year = " + year);
        System.out.println("month = " + month);
        String query = "WITH p AS (SELECT bvd9_number as \"bvd9_number\"\n" +
                "                           ,sec_id\n" +
                "                          ,region\n" +
                "                          , sector\n" +
                "                          , SUM(value) value\n" +
                "                          ,SUM(SUM(value)) OVER() AS total_value\n" +
                "                          , COUNT(distinct nvl(bvd9_number,sec_id)) Over() total_companies\n" +
                "                    FROM df_portfolio\n" +
                "                    WHERE portfolio_id = '%p'\n" +
                "                    GROUP BY \"bvd9_number\",sec_id,region, sector\n" +
                "                    ) ,\n" +
                "                    distinct_bvd as ( select distinct p2.\"bvd9_number\"   from\n" +
                "                    (\n" +
                "                    SELECT distinct p.\"bvd9_number\"\n" +
                " from p\n" +
                "                      join df_target.entity_score s ON p.\"bvd9_number\" = s.ENTITY_ID_BVD9\n" +
                " WHERE s.release_year || s.release_month <=  '%ym'\n" +
                "                             GROUP BY p.\"bvd9_number\"\n" +
                "                      \n" +
                "                      union\n" +
                "                    \n" +
                "                     select distinct p.\"bvd9_number\" from p\n" +
                "                      join df_target.carbon_footprint cf ON p.\"bvd9_number\"=cf.bvd9_number \n" +
                "                                          AND cf.carbon_footprint_value_total >=0 \n" +
                "  AND cf.year =%y\n" +
                "  AND cf.month =%m\n" +
                "                                                              AND cf.carbon_footprint_value_total is not null\n" +
                "  \n" +
                "                    union\n" +
                "                    select distinct p.\"bvd9_number\" from p \n" +
                "                      join df_target.green_share gs ON p.\"bvd9_number\"=gs.bvd9_number \n" +
                "                                      and gs.gs_overall_assessment_estimate_of_incorporation >=0 \n" +
                "  and gs.year =%y\n" +
                "  AND gs.month =%m\n" +
                "                                                          and gs.gs_overall_assessment_estimate_of_incorporation is not null\n" +
                "                      \n" +
                "                     union\n" +
                "                    select distinct p.\"bvd9_number\" from p \n" +
                "                      join df_target.brown_share bs ON p.\"bvd9_number\"=bs.bvd9_number \n" +
                "                                      and bs.bs_fosf_industry_revenues_accurate >=0 \n" +
                "  and bs.year =%y\n" +
                "  AND bs.month =%m\n" +
                "                                                          and bs.bs_fosf_industry_revenues_accurate is not null\n" +
                "                      ) p2\n" +
                "                                   ) \n" +
                "                                                                                                   \n" +
                "                                                                                                   \n" +
                "SELECT COUNT(DISTINCT distinct_bvd.\"bvd9_number\") as \"covered_companies\",\n" +
                "                      p.total_companies \"Total_Companies\"\n" +
                "                      ,ROUND(100*SUM(NVL(p.value,0))/p.total_value) \"investment\"\n" +
                "                    FROM distinct_bvd inner join p on p.\"bvd9_number\"=distinct_bvd.\"bvd9_number\"\n" +
                "                      GROUP BY p.total_companies, p.total_value\n" +
                "                     \n" +
                "\n" +
                "                     ";

        query = query.replaceAll("%p", portfolioId);
        query = query.replaceAll("%y", String.valueOf(year));
        query = query.replaceAll("%m", String.valueOf(month));
        query = query.replaceAll("%ym", String.valueOf(year) + String.valueOf(month));


        Map<String, String> result = new HashMap<>();
        for (Map<String, Object> rs : getQueryResultMap(query)) {
            result.put("Climate Coverage", rs.get("covered_companies").toString());
            result.put("TotalCompanies", rs.get("Total_Companies").toString());
            result.put("CoveragePercent", rs.get("investment").toString());
        }
        return result;
    }

    public String getLatestMonthAndYearWithData(String portfolioId) {
        String strMonth = "";
        String strYear = "";
        LocalDate now = LocalDate.now();
        for (int i = 1; i < 10; i++) {
            LocalDate earlier = now.minusMonths(i);
            strMonth = String.valueOf(earlier.getMonth().getValue());
            if (strMonth.length() == 1) {
                strMonth = "0" + strMonth;
            }
            strYear = String.valueOf(earlier.getYear());
            System.out.println("strMonth = " + strMonth);
            System.out.println("strYear = " + strYear);
            System.out.println("portfolioId = " + portfolioId);
            String query = "select gs.bvd9_number from df_portfolio df" +
                    "    left join temperature_alignment gs on gs.bvd9_number=df.bvd9_number and gs.month='" + strMonth + "' and gs.year='" + strYear + "'" +
                    "    where df.portfolio_id='" + portfolioId + "'" +
                    "    and company_name is not null" +
                    "    and gs.bvd9_number is not null limit 1;";
            if (getQueryResultList(query).get(0).get(0) != null) {
                break;
            }
        }
        return strMonth + ":" + strYear;
    }

    public List<Map<String, Object>> getCompanyGeneralInfo(String portfolioId, String bvd9Number) {
        String query = "select df.*, isin.isin from df_portfolio df" +
                "    left join DF_DERIVED.isin_sorted isin on isin.bvd9_number = df.bvd9_number and isin.primary_isin='Y'" +
                "    where df.portfolio_id='" + portfolioId + "'" +
                "    and df.bvd9_number='" + bvd9Number + "'" +
                "    and company_name is not null";
        List<Map<String, Object>> result = getQueryResultMap(query);
        return result;
    }

    public String getPercentExposedData(String bvd9Number, String rcId, String rtId) {
        String query = "select PERCENT_FACILITIES_EXPOSED from ENTITY_SCORE_THRESHOLDS where\n" +
                " ENTITY_ID_BVD9='" + bvd9Number + "' and\n" +
                " RISK_CATEGORY_ID=" + rcId + " and\n" +
                " RISK_THRESHOLD_ID=" + rtId + " \n" +
                " qualify row_number() over(partition by ENTITY_ID_BVD9 order by release_year||release_month desc)=1";
        return getQueryResultMap(query).get(0).get("PERCENT_FACILITIES_EXPOSED").toString();
    }

    public List<Map<String, Object>> getCompanyCarbonFootprintInfo(String portfolioId, String bvd9Number, String month, String year) {
        String query = "select cf.* from df_portfolio df" +
                "    left join carbon_footprint cf on cf.bvd9_number=df.bvd9_number and cf.month=" + month + " and cf.year=" + year + "" +
                "    where df.portfolio_id='" + portfolioId + "'" +
                "    and df.bvd9_number='" + bvd9Number + "'" +
                "    and company_name is not null" +
                "and AS_OF_DATE is not null";
        List<Map<String, Object>> result = getQueryResultMap(query);
        return result;
    }

    public List<Map<String, Object>> getCompanyGreenShareInfo(String portfolioId, String bvd9Number, String month, String year) {
        String query = "select gs.* from df_portfolio df" +
                "    left join green_share gs on gs.bvd9_number=df.bvd9_number and gs.month=" + month + " and gs.year=" + year + "" +
                "    where df.portfolio_id='" + portfolioId + "'" +
                "    and df.bvd9_number='" + bvd9Number + "'" +
                "    and company_name is not null";
        List<Map<String, Object>> result = getQueryResultMap(query);
        return result;
    }

    public List<Map<String, Object>> getSubsidiaryCompany(String subsidiaryCompanyIsin) {
        String query = "select bvd9_number, max(ISIN) from DF_DERIVED.ISIN_SORTED d\n" +
                "join esg_entity_master e on d.bvd9_number=e.orbis_id where MANAGED_TYPE='Subsidiary' and entity_status='Active' and ISIN = '" + subsidiaryCompanyIsin + "'\n" +
                "group by 1;";
        List<Map<String, Object>> result = getQueryResultMap(query);
        return result;
    }

    public Object getResearchData(String bvd9Number, String month, String year, String rdID, String dataPointId) {
        String query = "select rd.DATA_VALUE from RESEARCH_DATA rd\n" +
                " where rd.bvd9_number='" + bvd9Number + "'\n" +
                " and rd.month=" + month + " and rd.year=" + year + " \n" +
                " and rd.RESEARCH_DATA_ID=" + rdID + " \n" +
                " and rd.RESEARCH_STRUCTURE_DATAPOINT_ID=" + dataPointId + "";
        List<Map<String, Object>> result = getQueryResultMap(query);
        Object dataValue = "";
        if (result.size() != 0)
            dataValue = result.get(0).get(0);
        return dataValue;
    }

    public List<Map<String, Object>> getCompanyBrownShareInfo(String portfolioId, String bvd9Number, String month, String year) {
        String query = "select bs.* from df_portfolio df" +
                "    left join brown_share bs on bs.bvd9_number=df.bvd9_number and bs.month=" + month + " and bs.year=" + year + "" +
                "    where df.portfolio_id='" + portfolioId + "'" +
                "    and df.bvd9_number='" + bvd9Number + "'" +
                "    and company_name is not null";
        List<Map<String, Object>> result = getQueryResultMap(query);
        return result;
    }

    public List<Map<String, Object>> getPortfolioBrownShareInfo(String portfolioId, String year, String month) {
        String query = "WITH p AS (SELECT p.bvd9_number,p.company_name,p.region_name,p.region,p.sector,SUM(p.value) OVER(PARTITION BY p.bvd9_number) value,SUM(p.value) OVER() total_value\n" +
                "FROM df_target.df_portfolio p WHERE p.portfolio_id = '" + portfolioId + "'\n" +
                "QUALIFY ROW_NUMBER() OVER(partition by p.BVD9_NUMBER ORDER BY p.REGION_NAME NULLS LAST)=1)\n" +
                "SELECT s.bvd9_number,p.company_name,p.region_name,p.region,p.sector,p.value, p.total_value,s.YEAR,s.MONTH,s.BS_FOSF_INDUSTRY_REVENUES,s.BS_FOSF_INDUSTRY_REVENUES_ACCURATE_SOURCE,\n" +
                "s.BS_FOSF_INDUSTRY_REVENUES_ACCURATE,s.SCORE_RANGE,s.SCORE_CATEGORY,s.PRODUCED_DATE,s.PREVIOUS_PRODUCED_DATE\n" +
                "FROM Brown_Share s JOIN p ON p.bvd9_number=s.BVD9_NUMBER\n" +
                "WHERE s.YEAR || s.MONTH = '" + year + "" + month + "'";
        List<Map<String, Object>> result = getQueryResultMap(query);
        return result;
    }

    public List<Map<String, Object>> getCompanyTempAlignInfo(String portfolioId, String bvd9Number, String month, String year) {
        String query = "select ta.* from df_portfolio df" +
                "    left join temperature_alignment ta on ta.bvd9_number=df.bvd9_number and ta.month=" + month + " and ta.year=" + year + "" +
                "    where df.portfolio_id='" + portfolioId + "'" +
                "    and df.bvd9_number='" + bvd9Number + "'" +
                "    and company_name is not null";
        List<Map<String, Object>> result = getQueryResultMap(query);
        return result;
    }

    public List<Map<String, Object>> getCompanyInvestmentPercentageInThePortfolio(String portfolioId) {
        String query = "                SELECT company_name, bvd9_number as ORBIS_ID, (sum(A.VALUE)/(SELECT SUM(A.VALUE)\n" +
                "                FROM df_portfolio a\n" +
                "                WHERE portfolio_id='" + portfolioId + "')*100) as \"% Investment\"\n" +
                "                FROM df_portfolio a\n" +
                "                WHERE portfolio_id='" + portfolioId + "'\n" +
                "                AND BVD9_NUMBER IS NOT NULL\n" +
                "                group by bvd9_number, company_name;";
        System.out.println(query);
        return getQueryResultMap(query);
    }

    public List<Map<String, Object>> getCompanyTotalControversiesInThePortfolio(String portfolioId) {
        LocalDate localDate = LocalDate.now();
        int thisYear = localDate.getYear();
        int lastYear = thisYear - 1;
        int monthValue = localDate.getMonthValue() - 1;

        String query = "SELECT orbis_id,\n" +
                "       company_name,\n" +
                "       Count(DISTINCT controversy_title) AS count\n" +
                "FROM   df_portfolio a\n" +
                "       JOIN controversy_details b\n" +
                "         ON a.bvd9_number = b.orbis_id\n" +
                "            AND b.controversy_status = 'Active'\n" +
                "            AND b.controversy_steps = 'last'\n" +
                "WHERE  a.portfolio_id = '" + portfolioId + "'\n" +
                "                AND BVD9_NUMBER IS NOT NULL\n" +
                "       AND ( ( To_varchar(Extract(year FROM b.controversy_events)) = '" + thisYear + "'\n" +
                "               AND Extract(month FROM b.controversy_events) <= " + monthValue + " )\n" +
                "              OR ( To_varchar(Extract(year FROM b.controversy_events)) = '" + lastYear + "'\n" +
                "                   AND Extract(month FROM b.controversy_events) > " + monthValue + " ) )\n" +
                "GROUP  BY b.orbis_id,\n" +
                "          company_name;";

        System.out.println(query);
        return getQueryResultMap(query);
    }

    public List<Map<String, Object>> getCompanyTotalCriticalControversiesInThePortfolio(String portfolioId) {
        LocalDate now = LocalDate.now();
        int thisYear = now.getYear();
        int lastYear = thisYear - 1;
        int monthValue = now.getMonthValue() - 1;

        String query = "SELECT orbis_id,\n" +
                "       company_name,\n" +
                "       Count(DISTINCT controversy_title) AS count\n" +
                "FROM   df_portfolio a\n" +
                "       JOIN controversy_details b\n" +
                "         ON a.bvd9_number = b.orbis_id\n" +
                "            AND b.controversy_status = 'Active'\n" +
                "            AND b.controversy_steps = 'last'\n" +
                "            and b.severity = 'Critical'\n" +
                "WHERE  a.portfolio_id = '" + portfolioId + "'\n" +
                "                AND BVD9_NUMBER IS NOT NULL\n" +
                "       AND ( ( To_varchar(Extract(year FROM b.controversy_events)) = '" + thisYear + "'\n" +
                "               AND Extract(month FROM b.controversy_events) <= " + monthValue + " )\n" +
                "              OR ( To_varchar(Extract(year FROM b.controversy_events)) = '" + lastYear + "'\n" +
                "                   AND Extract(month FROM b.controversy_events) > " + monthValue + " ) )\n" +
                "GROUP  BY b.orbis_id,\n" +
                "          company_name";
        System.out.println(query);
        return getQueryResultMap(query);
    }

    public List<Map<String, Object>> getPhysicalRiskInfo(String portfolioId, String bvd9Number) {
        LocalDate localDate = LocalDate.now();
        String query = " WITH p AS (SELECT DF_1.PORTFOLIO_ID,DF_1.COMPANY_NAME, DF_1.BVD9_NUMBER,SUM(DF_1.VALUE) AS VALUE, DF_1.SECTOR,DF_1.REGION, DF_1.AS_OF_DATE,listagg(SEC_ID,',') WITHIN GROUP (order by BVD9_NUMBER desc) AS ISIN_LIST FROM \"DF_TARGET\".\"DF_PORTFOLIO\" DF_1 WHERE\n" +
                "\t\t   BVD9_NUMBER='" + bvd9Number + "' and portfolio_id='" + portfolioId + "' GROUP BY DF_1.BVD9_NUMBER,DF_1.SECTOR,DF_1.REGION,DF_1.AS_OF_DATE,DF_1.PORTFOLIO_ID,DF_1.COMPANY_NAME)\n" +
                ", CAT1 AS (select DISTINCT RISK_CATEGORY_ID,ENTITY_ID_BVD9,RELEASE_YEAR,RELEASE_MONTH from DF_TARGET.ENTITY_SCORE_CATEGORY CAT1\n" +
                "                                   WHERE RISK_CATEGORY_ID in (14,1,12,4,2,16 ) qualify row_number() over( partition by ENTITY_ID_BVD9,RELEASE_YEAR,RELEASE_MONTH order by ENTITY_CATEGORY_SCORE desc nulls last)=1 ), \n" +
                " S AS (select rc.RISK_CATEGORY_NAME  AS \"Highest Risk Hazard\",sum(PERCENT_FACILITIES_EXPOSED) * 100 as \"% Facilities Exposed to High Risk and Red Flag\",et.ENTITY_ID_BVD9 AS BVD9_NUMBER,et.RELEASE_YEAR,et.RELEASE_MONTH,\n" +
                "       et.RELEASE_YEAR || et.RELEASE_MONTH as RELEASE_YEAR_MONTH\n" +
                "       from DF_TARGET.ENTITY_SCORE_THRESHOLDS et  join DF_TARGET.RISK_CATEGORY rc on et.RISK_CATEGORY_ID=rc.RISK_CATEGORY_ID join CAT1 on et.RISK_CATEGORY_ID = CAT1.RISK_CATEGORY_ID\n" +
                "       AND et.ENTITY_ID_BVD9 =  CAT1.ENTITY_ID_BVD9 and CAT1.RELEASE_YEAR = et.RELEASE_YEAR and CAT1.RELEASE_MONTH = et.RELEASE_MONTH\n" +
                "       where et.ENTITY_ID_BVD9 in (SELECT DISTINCT BVD9_NUMBER FROM p ) and RISK_THRESHOLD_ID in (3,4) \n" +
                "       group by rc.RISK_CATEGORY_NAME,et.ENTITY_ID_BVD9,et.RELEASE_YEAR, et.RELEASE_MONTH )\n" +
                " ,Market as (\tSELECT ENTITY_ID_BVD9,\"'Weather Sensitivity'\" AS \"Physical Market Risk:Weather Sensitivity Score\", \n" +
                "    \"'Country of Sales'\" AS \"Physical Market Risk:Country of Sales Score\",RELEASE_YEAR,RELEASE_MONTH,RELEASE_YEAR||RELEASE_MONTH AS RELEASE_YEAR_MONTH FROM (\n" +
                "      SELECT r2.ENTITY_ID_BVD9,r3.risk_category_name title,r2.ENTITY_CATEGORY_SCORE,r2.release_year,r2.release_month\n" +
                "\t\t\t\t\t\t\t  FROM  df_target.entity_score_category r2 \n" +
                "                              JOIN df_target.risk_category r3 ON r2.risk_category_id = r3.risk_category_id  \n" +
                "                              WHERE r2.risk_category_id  in (8,9) \n" +
                "                              AND r2.ENTITY_ID_BVD9 In (SELECT DISTINCT BVD9_NUMBER FROM  \"DF_TARGET\".\"DF_PORTFOLIO\"  )\n" +
                "                              GROUP BY r2.ENTITY_CATEGORY_SCORE,r2.ENTITY_ID_BVD9,risk_category_name,r2.RELEASE_YEAR,r2.RELEASE_MONTH) MAIN\n" +
                "                              PIVOT (MAX(ENTITY_CATEGORY_SCORE) FOR title IN ('Weather Sensitivity','Country of Sales') ) as Col order by ENTITY_ID_BVD9)\n" +
                "                              \n" +
                " ,Supply  As ( SELECT ENTITY_ID_BVD9,\"'Country of Origin'\" AS  \"Physical Supply Chain Risk:Country of Origin Score\",  \n" +
                "   \"'Resource Demand'\" AS \"Physical Supply Chain Risk:Resource Demand Score\",RELEASE_YEAR,RELEASE_MONTH, RELEASE_YEAR||RELEASE_MONTH AS RELEASE_YEAR_MONTH FROM \n" +
                "             (SELECT r2.ENTITY_ID_BVD9, r2.ENTITY_CATEGORY_SCORE,r3.risk_category_name title,r2.release_year,r2.release_month\n" +
                "\t\t\t\t\t\t\t  FROM df_target.entity_score_category r2\n" +
                "                              JOIN df_target.risk_category r3 ON r2.risk_category_id = r3.risk_category_id  \n" +
                "                              WHERE r2.risk_category_id  in (6,7) \n" +
                "                              AND r2.ENTITY_ID_BVD9 IN  (SELECT DISTINCT BVD9_NUMBER FROM  \"DF_TARGET\".\"DF_PORTFOLIO\")\n" +
                "                              GROUP BY  title,r2.ENTITY_CATEGORY_SCORE,r2.ENTITY_ID_BVD9,r2.RELEASE_YEAR,r2.RELEASE_MONTH) MAIN\n" +
                "                               PIVOT (MAX(ENTITY_CATEGORY_SCORE) FOR title IN ('Country of Origin','Resource Demand')) as Col order by ENTITY_ID_BVD9)        \n" +
                "                select * from p\n" +
                "                \n" +
                "LEFT OUTER JOIN SUPPlY sup ON sup.ENTITY_ID_BVD9 = p.BVD9_NUMBER AND sup.RELEASE_YEAR || sup.RELEASE_MONTH \n" +
                "= (select max(CONCAT(RELEASE_YEAR,RELEASE_MONTH)) as MaxDate  from  Supply sup_1 where  sup_1.RELEASE_YEAR_MONTH  <= 202207 and sup_1.ENTITY_ID_BVD9 = p.BVD9_NUMBER)\n" +
                "\n" +
                "LEFT OUTER JOIN MARKET mar ON mar.ENTITY_ID_BVD9 = p.BVD9_NUMBER AND mar.RELEASE_YEAR || mar.RELEASE_MONTH \n" +
                "= (select max(CONCAT(RELEASE_YEAR,RELEASE_MONTH)) as MaxDate  from  MARKET mar_1 where mar_1.RELEASE_YEAR_MONTH  <= 202207 AND mar_1.ENTITY_ID_BVD9 = p.BVD9_NUMBER )\n" +
                "                               \n" +
                "LEFT OUTER JOIN DF_TARGET.ENTITY_SCORE ES on p.BVD9_NUMBER = ES.ENTITY_ID_BVD9 AND ES.RELEASE_YEAR || ES.RELEASE_MONTH \n" +
                "= (select max(CONCAT(release_year,release_month)) as MaxDate  from  DF_target.entity_score es_1 where es_1.RELEASE_YEAR || es_1.release_month <=  202207 and  es_1.ENTITY_ID_BVD9 = p.BVD9_NUMBER )\n" +
                "                                \n" +
                "LEFT OUTER JOIN S ON p.BVD9_NUMBER = S.BVD9_NUMBER AND  S.RELEASE_YEAR || S.RELEASE_MONTH \n" +
                "= (select max(CONCAT(release_year,release_month)) as MaxDate  from  S es_1 where  es_1.RELEASE_YEAR_MONTH <=  " + localDate.getYear()
                + localDate.format(monthFormatter) + " and es_1.BVD9_NUMBER = p.BVD9_NUMBER )\n";
        System.out.println("query = " + query);
        return getQueryResultMap(query);
    }

    public List<Map<String, Object>> getPhysicalRiskManagementInfo(String portfolioId) {
        String queryForLatestMonthAndYear = "select * from physical_risk_management order by year desc, month desc limit 1;";
        Map<String, Object> monthAndYear = getQueryResultMap(queryForLatestMonthAndYear).get(0);
        String query = "select * from DF_TARGET.DF_PORTFOLIO df\n" +
                "left JOIN DF_TARGET.physical_risk_management prm on df.BVD9_NUMBER = prm.bvd9_number " +
                "AND prm.year='" + monthAndYear.get("YEAR").toString() + "' and prm.month='" + monthAndYear.get("MONTH").toString() + "' \n" +
                "where df.portfolio_id='" + portfolioId + "' and prm.SCORE_CATEGORY is not null";
        System.out.println("query = " + query);
        return getQueryResultMap(query);
    }

    public List<Map<String, Object>> getEsgAssessmentInfo(String portfolioId, String year, String month) {
        String query = "select * from DF_TARGET.VW_EXPORT_ESG_ASSESSMENTS where \"portfolio_id\" = '" + portfolioId + "' and \"Year\" = '" + year + "' and \"Month\" = '" + month + "'";
        System.out.println("query = " + query);
        return getQueryResultMap(query);
    }

    public List<Map<String, Object>> getPhysicalRiskHazardForPortfolio(String portfolioId) {
        LocalDate localDate = LocalDate.now();
        String query = " WITH p AS (SELECT DF_1.PORTFOLIO_ID,DF_1.COMPANY_NAME, DF_1.BVD9_NUMBER,SUM(DF_1.VALUE) AS VALUE, DF_1.SECTOR,DF_1.REGION, DF_1.AS_OF_DATE,listagg(SEC_ID,',') WITHIN GROUP (order by BVD9_NUMBER desc) AS ISIN_LIST FROM \"DF_TARGET\".\"DF_PORTFOLIO\" DF_1 WHERE\n" +
                "\t\t    portfolio_id='" + portfolioId + "' GROUP BY DF_1.BVD9_NUMBER,DF_1.SECTOR,DF_1.REGION,DF_1.AS_OF_DATE,DF_1.PORTFOLIO_ID,DF_1.COMPANY_NAME)\n" +
                ", CAT1 AS (select DISTINCT RISK_CATEGORY_ID,ENTITY_ID_BVD9,RELEASE_YEAR,RELEASE_MONTH from DF_TARGET.ENTITY_SCORE_CATEGORY CAT1\n" +
                "                                   WHERE RISK_CATEGORY_ID in (14,1,12,4,2,16 ) qualify row_number() over( partition by ENTITY_ID_BVD9,RELEASE_YEAR,RELEASE_MONTH order by ENTITY_CATEGORY_SCORE desc nulls last)=1 ), \n" +
                " S AS (select rc.RISK_CATEGORY_NAME  AS \"Highest Risk Hazard\",sum(PERCENT_FACILITIES_EXPOSED) * 100 as \"% Facilities Exposed to High Risk and Red Flag\",et.ENTITY_ID_BVD9 AS BVD9_NUMBER,et.RELEASE_YEAR,et.RELEASE_MONTH,\n" +
                "       et.RELEASE_YEAR || et.RELEASE_MONTH as RELEASE_YEAR_MONTH\n" +
                "       from DF_TARGET.ENTITY_SCORE_THRESHOLDS et  join DF_TARGET.RISK_CATEGORY rc on et.RISK_CATEGORY_ID=rc.RISK_CATEGORY_ID join CAT1 on et.RISK_CATEGORY_ID = CAT1.RISK_CATEGORY_ID\n" +
                "       AND et.ENTITY_ID_BVD9 =  CAT1.ENTITY_ID_BVD9 and CAT1.RELEASE_YEAR = et.RELEASE_YEAR and CAT1.RELEASE_MONTH = et.RELEASE_MONTH\n" +
                "       where et.ENTITY_ID_BVD9 in (SELECT DISTINCT BVD9_NUMBER FROM p ) and RISK_THRESHOLD_ID in (3,4) \n" +
                "       \n" +
                "       group by rc.RISK_CATEGORY_NAME,et.ENTITY_ID_BVD9,et.RELEASE_YEAR, et.RELEASE_MONTH order by \"Highest Risk Hazard\", \"% Facilities Exposed to High Risk and Red Flag\" desc )\n" +
                "        \n" +
                "                select p.bvd9_number as \"BVD9_NUMBER\" , p.COMPANY_NAME, \"Highest Risk Hazard\" ,  \"% Facilities Exposed to High Risk and Red Flag\" from p\n" +
                "                                            \n" +
                "LEFT OUTER JOIN S ON p.BVD9_NUMBER = S.BVD9_NUMBER AND  S.RELEASE_YEAR || S.RELEASE_MONTH \n" +
                "= (select max(CONCAT(release_year,release_month)) as MaxDate  from  S es_1 where  es_1.RELEASE_YEAR_MONTH <=  " + localDate.getYear()
                + localDate.format(monthFormatter) + " and es_1.BVD9_NUMBER = p.BVD9_NUMBER )\n";
        System.out.println("query = " + query);
        return getQueryResultMap(query);
    }


    public static List<DashboardPerformanceChart> getBrownShareResultd(String portfolioID, String year, String month) {
        String Query = "select Portfolio_id,df.bvd9_number,BS_FOSF_INDUSTRY_REVENUES_ACCURATE_SOURCE,BS_FOSF_INDUSTRY_REVENUES_ACCURATE, SCORE_RANGE\n" +
                "from df_portfolio df\n" +
                "JOIN BROWN_SHARE bs\n" +
                "on df.bvd9_number = bs.bvd9_number\n" +
                "where portfolio_id = '" + portfolioID + "'" +
                "and year = '" + year + "'" +
                "and month = '" + month + "'";
        List<DashboardPerformanceChart> performanceChartESGScore = new ArrayList<>();

        for (Map<String, Object> each : getQueryResultMap(Query)) {
            DashboardPerformanceChart model = new DashboardPerformanceChart();
            model.setBVD9_NUMBER(each.get("BVD9_NUMBER").toString());
            model.setBS_FOSF_INDUSTRY_REVENUES_ACCURATE_SOURCE(each.get("BS_FOSF_INDUSTRY_REVENUES_ACCURATE_SOURCE") == null ? "NA" : each.get("BS_FOSF_INDUSTRY_REVENUES_ACCURATE_SOURCE").toString());
            model.setBS_FOSF_INDUSTRY_REVENUES_ACCURATE(each.get("BS_FOSF_INDUSTRY_REVENUES_ACCURATE").toString());
            model.setSCORE_RANGE(each.get("SCORE_RANGE").toString());


            performanceChartESGScore.add(model);
        }
        return performanceChartESGScore;
    }

    public List<Map<String, Object>> getPortfolioDetail(String portfolioId) {
        String query = "select * from df_portfolio where portfolio_id='" + portfolioId + "' and bvd9_number is not null";
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
                "              WHERE p.portfolio_id = '" + portfolioId + "'\n" +
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
