package com.esgc.Dashboard.API.Controllers;

import com.esgc.Base.API.APIModels.APIFilterPayload;
import com.esgc.Base.API.APIModels.APIFilterPayloadWithoutBenchmark;
import com.esgc.Base.API.Controllers.APIController;
import com.esgc.Dashboard.API.APIModels.*;
import com.esgc.Dashboard.API.DashboardEndPoints;
import com.esgc.EntityProfile.API.APIModels.SectorComparison;
import com.esgc.EntityProfile.API.EntityProfilePageEndpoints;
import com.esgc.PortfolioAnalysis.API.PortfolioAnalysisEndpoints;
import com.esgc.Utilities.Driver;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.openqa.selenium.By;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardAPIController extends APIController {

    public synchronized Response getHeatMapData(String portfolio_id, APIHeatMapPayload apiHeatMapPayload) {
        Response response = null;
        try {
            apiHeatMapPayload.setResearch_line_1(apiResourceMapperWithoutphysicalriskinit(apiHeatMapPayload.getResearch_line_1()));
            apiHeatMapPayload.setResearch_line_2(apiResourceMapperWithoutphysicalriskinit(apiHeatMapPayload.getResearch_line_2()));

            response = configSpec()
                    .pathParam("portfolio_id", portfolio_id)
                    .body(apiHeatMapPayload)
                    .when()
                    .post(DashboardEndPoints.POST_HEAT_MAP).prettyPeek();

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }

    public synchronized Response getControversies(String portfolioId, String region, String sector, String year, String month) {
        Response response = null;
        try {
            response = configSpec()
                    .pathParam("portfolio_id", portfolioId).log().all()
                    .pathParam("research_line", "controversies")
                    .body("{\"region\":\"" + region + "\",\"sector\":\"" + sector + "\",\"month\":\"" + month + "\",\"year\":\"" + year + "\"}")
                    .when()
                    .post(DashboardEndPoints.POST_CONTROVERSIES);

            System.out.println(response.prettyPrint());

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }


    public synchronized Response getEsgCoverageScore(String portfolioId, String benchMark, String region, String sector, String year, String month) {
        Response response = null;
        try {
            response = configSpec()
                    .pathParam("portfolio_id", portfolioId).log().all()
                    .body("{\"region\":\"" + region + "\",\"sector\":\"" + sector + "\",\"month\":\"" + month + "\",\"year\":\"" + year + "\",\"benchmark\":\"" + benchMark + "\"}")
                    .when()
                    .post(DashboardEndPoints.POST_ESG_SCORES);

            System.out.println(response.prettyPrint());

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }

    public synchronized Response getExportSourceDocuments(String entityId) {
        Response response = null;
        try {
            response = configSpec()
                    .pathParam("entity_id", entityId).log().all()
                    .when()
                    .post(EntityProfilePageEndpoints.POST_EXPORT_SOURCE_DOCUMENTS);

            System.out.println(response.prettyPrint());

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }

    public synchronized Response verifyExport(String portfolioId, String portfolioName, String region, String sector, String year, String month) {
        Response response = null;
        try {
            response = configSpec()
                    .pathParam("portfolio_id", portfolioId)
                    .body("{\"region\":\"" + region + "\",\"sector\":\"" + sector + "\",\"month\":\"" + month + "\",\"year\":\"" + year + "\",\"portfolio_name\":\"" + portfolioName + "\"}")
                    .when()
                    .post(DashboardEndPoints.POST_DASHBOARD_EXPORT_CLIMATE_DATA);

            System.out.println(response.prettyPrint());

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }

    public synchronized Response verifyPortfolioAnalysisExcelExport(String portfolioId) {
        Response response = null;
        try {
            response = configSpec().when()
                    .pathParam("portfolio_id",portfolioId)
                    .get(PortfolioAnalysisEndpoints.GET_PORTFOLIO_ANALYSIS_EXCEL_EXPORT);

            System.out.println(response.prettyPrint());

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }

    public synchronized Response verifyPortfolioAnalysisUploadJsonUrl(String portfolioId) {
        Response response = null;
        try {
            response = configSpec().when()
                    .pathParam("portfolio_id",portfolioId)
                    .get(PortfolioAnalysisEndpoints.GET_PORTFOLIO_ANALYSIS_JSON_UPLOAD);

            System.out.println(response.prettyPrint());

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }

    public synchronized Response getCoverageAPIREsponse(String portfolio_id, APIFilterPayloadWithoutBenchmark apiFilterPayload) {
        Response response = null;
        try {
            response = configSpec()
                    .pathParam("portfolio_id", portfolio_id)
                    .body(apiFilterPayload)
                    .when()
                    .post(DashboardEndPoints.POST_DASHBOARD_COVERAGE).prettyPeek();

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }

    public synchronized Response getPortfolioSummaryCompanies(String portfolioId) {
        Response response = null;
        try {
            LocalDate now = LocalDate.now();
            LocalDate earlier = now.minusMonths(1);
            String month = String.valueOf(earlier.getMonthValue());
            if (month.length() == 1) month = "0" + month;
            String year = String.valueOf(earlier.getYear());
            System.out.println("portfolio_id=" + portfolioId);
            System.out.println("month=" + month);
            System.out.println("year=" + year);
            month = "07";
            response = configSpec()
                    .pathParam("portfolio_id", portfolioId)
                    .when()
                    .body("{\"region\":\"all\",\"sector\":\"all\",\"month\":\"" + month + "\",\"year\":\"" + year + "\",\"view_by\":\"sector\",\"limit\":20}")
                    .post(DashboardEndPoints.POST_PORTFOLIO_SUMMARY_COMPANIES);


        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println(response.prettyPrint());
        return response;
    }

    /**
     * This method posts a request and gets api response
     *
     * @param portfolio_id  - portfolio Id
     * @param research_line - research line
     * @return response
     * <p>
     * Response Example:
     */
    public synchronized Response getGeoMapEntityListResponse(String portfolio_id, String research_line, APIEntityListPayload apiEntityListPayload) {
        Response response = null;
        try {

            response = configSpec()
                    .pathParam("portfolio_id", portfolio_id)
                    .pathParam("research_line", apiResourceMapper(research_line))
                    .body(apiEntityListPayload)
                    .when()
                    .post(DashboardEndPoints.POST_GEO_MAP_ENTITY_LIST);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }

    public synchronized Response getHeatMapResponse(String portfolio_id, String research_line, APIHeatMapSinglePayload apiHeatMapPayload) {
        Response response = null;
        try {
            //apiResourceMapperWithoutphysicalriskinit(research_line)
            response = configSpec()
                    // .header("Authorization", "Bearer " + System.getProperty("token"))
                    .pathParam("portfolio_id", portfolio_id)
                    .body(apiHeatMapPayload)
                    .when()
                    .post(DashboardEndPoints.POST_HEAT_MAP);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }

    public synchronized Response getPerformanceChartList(String portfolio_id, String research_line, APIFilterPayload apiFilterPayload, String performanceChart, String size) {
        Response response = null;
        if (performanceChart.equals("largest_holdings"))
            research_line = null;
        APIPerformanceChartPayload apiPerformanceChartPayload = new APIPerformanceChartPayload(apiFilterPayload, research_line, performanceChart, Integer.parseInt(size));

        try {
            RestAssured.defaultParser = Parser.JSON;
            System.out.println("payload = " + apiPerformanceChartPayload);
            response = configSpec()
                    .pathParam("portfolio_id", portfolio_id)
                    .body(apiPerformanceChartPayload)
                    .when()
                    .post(DashboardEndPoints.POST_PERFORMANCE_CHART).prettyPeek();

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }

    public synchronized List<Map<String, String>> getPortfolioBrownShareData(String portfolioId, String year, String month) {
        Response response = null;
        List<Map<String, String>> apiBrownShareHeatMapData = new ArrayList<>();
        try {
            List<String> categoriesList = new ArrayList<>();
            categoriesList.add("No Involvement");
            categoriesList.add("Minor Involvement");
            categoriesList.add("Major Involvement");
            for(String category:categoriesList) {
                response = configSpec()
                        .pathParam("portfolio_id", portfolioId)
                        .when()
                        .body("{\"region\":\"all\",\"sector\":\"all\",\"month\":\"" + month + "\",\"year\":\"" + year + "\",\"research_line_1\":\"brownshareasmt\",\"category_1\":\""+category+"\",\"category_2\":\"\"}")
                        .post(DashboardEndPoints.POST_HEAT_MAP_ENTITY_LIST);

                System.out.println(response.prettyPrint());
                response.as(CompanyBrownShareInfo[].class);

                for(int j=1; j<=250;j++) {
                    Map<String, String> companyDetails = new HashMap<>();
                    String companyName = "";
                    String investmentScore = "";
                    String scoreRange = "";
                    companyDetails.put("COMPANY_NAME", companyName);
                    companyDetails.put("INVESTMENT_SCORE", investmentScore);
                    companyDetails.put("SCORE_RANGE", scoreRange);
                    companyDetails.put("SCORE_CATEGORY", category);
                    apiBrownShareHeatMapData.add(companyDetails);
                }

            }

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return apiBrownShareHeatMapData;
    }
}
