package com.esgc.Controllers;

import com.esgc.APIModels.APIFilterPayloadWithoutBenchmark;
import com.esgc.APIModels.Dashboard.APIHeatMapPayload;
import com.esgc.Utilities.API.DashboardEndPoints;
import com.esgc.Utilities.API.EntityClimateProfilePageEndpoints;
import io.restassured.response.Response;

import java.time.LocalDate;

public class DashboardAPIController extends APIController{

    public Response getHeatMapData(String portfolio_id, APIHeatMapPayload apiHeatMapPayload) {
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

    public Response getControversies(String portfolioId, String region, String sector, String year, String month) {
        Response response = null;
        try {
            response = configSpec()
                    .pathParam("portfolio_id", portfolioId).log().all()
                    .body("{\"region\":\""+region+"\",\"sector\":\""+sector+"\",\"month\":\""+month+"\",\"year\":\""+year+"\"}")
                    .when()
                    .post(DashboardEndPoints.POST_CONTROVERSIES);

            System.out.println(response.prettyPrint());

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }



    public Response getEsgCoverageScore(String portfolioId, String benchMark, String region, String sector, String year, String month) {
        Response response = null;
        try {
            response = configSpec()
                    .pathParam("portfolio_id", portfolioId).log().all()
                    .body("{\"region\":\""+region+"\",\"sector\":\""+sector+"\",\"month\":\""+month+"\",\"year\":\""+year+"\",\"benchmark\":\""+benchMark+"\"}")
                    .when()
                    .post(DashboardEndPoints.POST_ESG_SCORES);

            System.out.println(response.prettyPrint());

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }

    public Response getExportSourceDocuments(String entityId) {
        Response response = null;
        try {
            response = configSpec()
                    .pathParam("entity_id", entityId).log().all()
                    .when()
                    .post(EntityClimateProfilePageEndpoints.POST_ExportSourceDocuments);

            System.out.println(response.prettyPrint());

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }

    public Response verifyExport(String portfolioId, String portfolioName, String region, String sector, String year, String month) {
        Response response = null;
        try {
            response = configSpec()
                    .pathParam("portfolio_id", portfolioId)
                    .body("{\"region\":\""+region+"\",\"sector\":\""+sector+"\",\"month\":\""+month+"\",\"year\":\""+year+"\",\"portfolio_name\":\""+portfolioName+"\"}")
                    .when()
                    .post(DashboardEndPoints.POST_EXPORT_CLIMATE_DATA);

            System.out.println(response.prettyPrint());

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }

    public Response verifyPortfolioAnalysisExcelExport() {
        Response response = null;
        try {
            response = configSpec() .when()
                    .get(DashboardEndPoints.GET_PORTFOLIO_ANALYIS_EXCEL_EXPORT);

            System.out.println(response.prettyPrint());

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }

    public Response verifyPortfolioAnalysisUploadJsonUrl() {
        Response response = null;
        try {
            response = configSpec() .when()
                    .get(DashboardEndPoints.GET_PORTFOLIO_ANALYIS_JSON_UPLOAD);

            System.out.println(response.prettyPrint());

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }

    public Response getCoverageAPIREsponse(String portfolio_id, APIFilterPayloadWithoutBenchmark apiFilterPayload) {
        Response response = null;
        try {
            response = configSpec()
                    .pathParam("portfolio_id", portfolio_id)
                    .body(apiFilterPayload)
                    .when()
                    .post(DashboardEndPoints.POST_DashBoard_COVERAGE).prettyPeek();

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }

    public Response getPortfolioSummaryCompanies(String portfolioId) {
        Response response = null;
        try {
            LocalDate now = LocalDate.now();
            LocalDate earlier = now.minusMonths(1);
            String month = String.valueOf(earlier.getMonthValue());
            if(month.length()==1) month = "0"+month;
            String year = String.valueOf(earlier.getYear());
            System.out.println("portfolio_id=" + portfolioId);
            System.out.println("month=" + month);
            System.out.println("year=" + year);
            month="07";
            response = configSpec()
                    .pathParam("portfolio_id", portfolioId)
                    .when()
                    .body("{\"region\":\"all\",\"sector\":\"all\",\"month\":\""+month+"\",\"year\":\""+year+"\",\"view_by\":\"sector\",\"limit\":20}")
                    .post(DashboardEndPoints.POST_PORTFOLIO_SUMMARY_COMPANIES);


        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println(response.prettyPrint());
        return response;
    }
}
