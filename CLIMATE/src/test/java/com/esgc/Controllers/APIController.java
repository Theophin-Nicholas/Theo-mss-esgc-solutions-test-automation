package com.esgc.Controllers;

import com.esgc.APIModels.APIFilterPayload;
import com.esgc.APIModels.APIFilterPayloadWithImpactFilter;
import com.esgc.APIModels.RangeAndScoreCategory;
import com.esgc.DBModels.ResearchLineIdentifier;
import com.esgc.Utilities.API.Endpoints;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.EndPoints.PortfolioAnalysisEndpoints;
import com.esgc.Utilities.Environment;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static java.util.stream.Collectors.groupingBy;


public class APIController {

    boolean isInvalidTest = false;

    RequestSpecification configSpec() {
        if(System.getProperty("token")==null){
            String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.accessToken";
            String accessToken = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
            System.setProperty("token", accessToken);
        }
        if (isInvalidTest) {
            return given().accept(ContentType.JSON)
                    .baseUri(Environment.URL)
                    .relaxedHTTPSValidation()
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .log().ifValidationFails();
        } else {
            return given().accept(ContentType.JSON)
                    .baseUri(Environment.URL)
                    .relaxedHTTPSValidation()
                    .header("Authorization", "Bearer " + System.getProperty("token"))
                   // .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .log().ifValidationFails();
        }
    }

    public void setInvalid() {
        this.isInvalidTest = true;
    }

    public void resetInvalid() {
        this.isInvalidTest = false;
    }

    public Response importPortfolio(String user_id, String fileName, String filepath) {
        try {
            return configSpec()
                    .header("Content-Type", "multipart/form-data")
                    .param("userId", user_id)
                    .multiPart("file", fileName, FileUtils.readFileToByteArray(new File(filepath)), "text/csv")
                    .param("filename", "\"" + fileName + "\"")
                    .when()
                    .put(Endpoints.IMPORT_PORTFOLIO);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method posts a request and gets api response
     *
     * @param portfolio_id - portfolio Id
     * @return response
     * <p>
     * Response Example:
     */
    public Response getFilterOptions(String portfolio_id, String region, String sector) {
        Response response = null;
        try {

            response = configSpec()
                    .pathParam("portfolio_id", portfolio_id)
                    .body("{\n" +
                            "    \"region\": \"" + region + "\",\n" +
                            "    \"sector\": \"" + sector + "\"\n" +
                            "}")
                    .when()
                    .post(Endpoints.POST_FILTER_OPTIONS_IN_PORTFOLIO);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

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
    public Response getPortfolioScoreResponse(String portfolio_id, String research_line, APIFilterPayload apiFilterPayload) {
        Response response = null;
        try {

            response = configSpec()
                    .pathParam("portfolio_id", portfolio_id)
                    .pathParam("research_line", apiResourceMapper(research_line))
                    .body(apiFilterPayload)
                    .when().log().all()
                    .post(PortfolioAnalysisEndpoints.POST_PORTFOLIO_SCORE).prettyPeek();
        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

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
    public Response getPortfolioDistributionResponse(String portfolio_id, String research_line, APIFilterPayload apiFilterPayload) {
        Response response = null;
        try {
            response = configSpec()
                    .pathParam("portfolio_id", portfolio_id)
                    .pathParam("research_line", apiResourceMapper(research_line))
                    .body(apiFilterPayload).log().all()
                    .when()
                    .post(PortfolioAnalysisEndpoints.POST_PORTFOLIO_DISTRIBUTION).prettyPeek();

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }

    public Response getHistoryTablesResponse(String portfolio_id, String research_line, APIFilterPayload apiFilterPayload) {
        Response response = null;
        try {
            response = configSpec()
                    .pathParam("portfolio_id", portfolio_id)
                    .pathParam("research_line", apiResourceMapperWithoutphysicalriskinit(research_line))
                    .body(apiFilterPayload)
                    .when()
                    .post(PortfolioAnalysisEndpoints.POST_PORTFOLIO_HISTORY_TABLE);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }

    /**
     * This method posts a request and gets api response
     *
     * @param portfolio_id  - portfolio id
     * @param research_line - research line
     * @return response
     * <p>
     * Response Example:
     */
    public Response getPortfolioCoverageResponse(String portfolio_id, String research_line, APIFilterPayload apiFilterPayload) {
        Response response = null;
        try {

            response = configSpec()
                    .pathParam("portfolio_id", portfolio_id)
                    .pathParam("research_line", apiResourceMapper(research_line))
                    .body(apiFilterPayload)
                    .when()
                    .post(PortfolioAnalysisEndpoints.POST_PORTFOLIO_COVERAGE).prettyPeek();

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

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
    public Response getPortfolioUpdatesResponse(String portfolio_id, String research_line, APIFilterPayload apiFilterPayload) {
        Response response = null;
        try {

            response = configSpec()
                    .pathParam("portfolio_id", portfolio_id)
                    .pathParam("research_line", apiResourceMapper(research_line))
                    .body(apiFilterPayload)
                    .when()
                    .post(PortfolioAnalysisEndpoints.POST_PORTFOLIO_UPDATES);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

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
    public Response getPortfolioEmissionsResponse(String portfolio_id, String research_line, APIFilterPayload apiFilterPayload) {
        Response response = null;
        try {

            response = configSpec()
                    .pathParam("portfolio_id", portfolio_id)
                    .pathParam("research_line", apiResourceMapper(research_line))
                    .body(apiFilterPayload)
                    .when()
                    .post(PortfolioAnalysisEndpoints.POST_PORTFOLIO_EMISSIONS);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

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
    public Response getPortfolioLeadersAndLaggardsResponse(String portfolio_id, String research_line, APIFilterPayload apiFilterPayload) {
        Response response = null;
        try {

            response = configSpec()
                    .pathParam("portfolio_id", portfolio_id)
                    .pathParam("research_line", apiResourceMapper(research_line))
                    .body(apiFilterPayload)
                    .when()
                    .log().all()
                    .post(PortfolioAnalysisEndpoints.POST_PORTFOLIO_LEADERS_AND_LAGGARDS);


        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

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
    public Response getPortfolioRegionSummaryResponse(String portfolio_id, String research_line, APIFilterPayload apiFilterPayload) {
        Response response = null;
        try {

            response = configSpec()
                    .pathParam("portfolio_id", portfolio_id)
                    .pathParam("research_line", apiResourceMapper(research_line))
                    .body(apiFilterPayload)
                    .when()
                    .post(PortfolioAnalysisEndpoints.POST_PORTFOLIO_REGION_SUMMARY);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

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
    public Response getPortfolioRegionDetailsResponse(String portfolio_id, String research_line, APIFilterPayload apiFilterPayload) {
        Response response = null;
        try {

            response = configSpec()
                    .pathParam("portfolio_id", portfolio_id)
                    .pathParam("research_line", apiResourceMapper(research_line))
                    .body(apiFilterPayload)
                    .when()
                    .post(PortfolioAnalysisEndpoints.POST_PORTFOLIO_REGION_DETAILS);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

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
    public Response getPortfolioSectorSummaryResponse(String portfolio_id, String research_line, APIFilterPayload apiFilterPayload) {
        Response response = null;
        try {

            response = configSpec()
                    .pathParam("portfolio_id", portfolio_id)
                    .pathParam("research_line", apiResourceMapper(research_line))
                    .body(apiFilterPayload)
                    .when()
                    .post(PortfolioAnalysisEndpoints.POST_PORTFOLIO_SECTOR_SUMMARY);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

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
    public Response getPortfolioSectorDetailsResponse(String portfolio_id, String research_line, APIFilterPayload apiFilterPayload) {
        Response response = null;
        try {

            response = configSpec()
                    .pathParam("portfolio_id", portfolio_id)
                    .pathParam("research_line", apiResourceMapper(research_line))
                    .body(apiFilterPayload)
                    .when()
                    .post(PortfolioAnalysisEndpoints.POST_PORTFOLIO_SECTOR_DETAILS);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

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
    public Response getPortfolioRegionMapResponse(String portfolio_id, String research_line, APIFilterPayload apiFilterPayload) {
        Response response = null;
        try {

            response = configSpec()
                    .pathParam("portfolio_id", portfolio_id)
                    .pathParam("research_line", apiResourceMapper(research_line))
                    .body(apiFilterPayload)
                    .when()
                    .post(PortfolioAnalysisEndpoints.POST_PORTFOLIO_REGION_MAP);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

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
    public Response getPortfolioUnderlyingDataMetricsResponse(String portfolio_id, String research_line, APIFilterPayload apiFilterPayload) {
        Response response = null;
        try {

            response = configSpec()
                    .pathParam("portfolio_id", portfolio_id)
                    .pathParam("research_line", apiResourceMapper(research_line))
                    .body(apiFilterPayload)
                    .when()
                    .post(PortfolioAnalysisEndpoints.POST_PORTFOLIO_UNDERLYING_DATA_METRICS);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }

    public Response getPhysicalRiskUnderlyingDataMetricsResponse(String portfolio_id, String physical_risk) {
        Response response = null;
        try {
            response = configSpec()
                    .pathParam("portfolio_id", portfolio_id)
                    .pathParam("physical_risk", physical_risk)
                    .body("{\"region\":\"all\",\"sector\":\"all\",\"month\":\"12\",\"year\":\"2021\"}")
                    .when()
                    .post(PortfolioAnalysisEndpoints.POST_PHYSICAL_RISK_UNDERLYING_DATA_METRICS_DETAILS);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }
    public Response getPhysicalRiskUnderlyingDataMetricsResponse(String portfolio_id, String researchLine, APIFilterPayload apiFilterPayload) {
        Response response = null;
        try {
            response = configSpec()
                    .pathParam("portfolio_id", portfolio_id)
                    .pathParam("physical_risk", researchLine)
                    .body(apiFilterPayload)
                    .when()
                    .post(PortfolioAnalysisEndpoints.POST_PHYSICAL_RISK_UNDERLYING_DATA_METRICS_DETAILS);

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }

    public List<RangeAndScoreCategory> getResearchLineRangesAndScoreCategories(String researchLine) {

        List<RangeAndScoreCategory> rangesAndCategories = new ArrayList<>();

        switch (researchLine) {
            case "operationsrisk":
            case "marketrisk":
            case "supplychainrisk":
                rangesAndCategories.add(new RangeAndScoreCategory("No Risk", 0d, 19d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("Low Risk", 20d, 39d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("Medium Risk", 40d, 59d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("High Risk", 60d, 79d, "negative"));
                rangesAndCategories.add(new RangeAndScoreCategory("Red Flag Risk", 80d, 100d, "negative"));
                return rangesAndCategories;

            case "Physical Risk Management":
            case "Energy Transition Management":
            case "TCFD":
                rangesAndCategories.add(new RangeAndScoreCategory("Advanced", 60d, 100d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("Robust", 50d, 59d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("Limited", 30d, 49d, "negative"));
                rangesAndCategories.add(new RangeAndScoreCategory("Weak", 0d, 29d, "negative"));
                return rangesAndCategories;

            case "Brown Share":
                rangesAndCategories.add(new RangeAndScoreCategory("0%", 0d, 0d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("0-20%", 0.00000000000001d, 20d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("20-100%", 20.0000000001d, 100d, "negative"));
                return rangesAndCategories;

            case "Carbon Footprint":
                rangesAndCategories.add(new RangeAndScoreCategory("Moderate", 0d, 99999d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("Significant", 100000d, 999999d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("High", 1000000d, 9999999d, "negative"));
                rangesAndCategories.add(new RangeAndScoreCategory("Intense", 10000000d, (double) Integer.MAX_VALUE, "negative"));
                return rangesAndCategories;

            case "Green Share":
                rangesAndCategories.add(new RangeAndScoreCategory("Major", 50.0000000001d, 100d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("Significant", 20.00000000001d, 50d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("Minor", 0.0000000000001d, 20d, "negative"));
                rangesAndCategories.add(new RangeAndScoreCategory("None", 0d, 0d, "negative"));
                return rangesAndCategories;
            case "Temperature Alignment":
                rangesAndCategories.add(new RangeAndScoreCategory("Well Below 2°C", 0d, 1.64d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("Below 2°C", 1.65d, 1.94d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("2°C", 1.95d, 2.04d, "negative"));
                rangesAndCategories.add(new RangeAndScoreCategory("Above 2°C", 2.05d, 100d, "negative"));
                rangesAndCategories.add(new RangeAndScoreCategory("No Info", -1000d, -0.00001d, "negative"));
                return rangesAndCategories;
            case "ESG_Pillars":
                rangesAndCategories.add(new RangeAndScoreCategory("Weak", 0d, 24d, "negative"));
                rangesAndCategories.add(new RangeAndScoreCategory("Limited", 25d, 44d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("Robust", 45d, 59d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("Advanced", 60d, 100d, "positive"));
                return rangesAndCategories;

            case "ESG":
                rangesAndCategories.add(new RangeAndScoreCategory("Weak", 0d, 29.99999999d, "negative", 0d, 24.999999d));
                rangesAndCategories.add(new RangeAndScoreCategory("Limited", 30d, 49.9999999d, "negative", 25d, 44.9999999d));
                rangesAndCategories.add(new RangeAndScoreCategory("Robust", 50d, 59.99999d, "positive", 45d, 64.9999999d));
                rangesAndCategories.add(new RangeAndScoreCategory("Advanced", 60d, 100d, "positive", 65d, 100d));
                return rangesAndCategories;
        }
        return null;
    }


    public List<RangeAndScoreCategory> getResearchLineRangesAndScoreCategoriesForUpdates(String researchLine) {

        List<RangeAndScoreCategory> rangesAndCategories = new ArrayList<>();

        switch (researchLine) {
            case "operationsrisk":
            case "marketrisk":
            case "supplychainrisk":
                rangesAndCategories.add(new RangeAndScoreCategory("0-19", 0d, 19d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("20-39", 20d, 39d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("40-59", 40d, 59d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("60-79", 60d, 79d, "negative"));
                rangesAndCategories.add(new RangeAndScoreCategory("80-100", 80d, 100d, "negative"));
                return rangesAndCategories;

            case "Physical Risk Management":
            case "Energy Transition Management":
            case "TCFD":
                rangesAndCategories.add(new RangeAndScoreCategory("Advanced", 60d, 100d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("Robust", 50d, 59d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("Limited", 30d, 49d, "negative"));
                rangesAndCategories.add(new RangeAndScoreCategory("Weak", 0d, 29d, "negative"));
                return rangesAndCategories;

            case "Brown Share":
                rangesAndCategories.add(new RangeAndScoreCategory("0%", 0d, 0d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("0-20%", 0.0000000001d, 19.999999999d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("20-100%", 20d, 100d, "negative"));
                return rangesAndCategories;

            case "Carbon Footprint":
                rangesAndCategories.add(new RangeAndScoreCategory("Moderate", 0d, 99999d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("Significant", 100000d, 999999d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("High", 1000000d, 9999999d, "negative"));
                rangesAndCategories.add(new RangeAndScoreCategory("Intense", 10000000d, (double) Integer.MAX_VALUE, "negative"));
                return rangesAndCategories;

            case "Green Share":
                rangesAndCategories.add(new RangeAndScoreCategory("Major", 50d, 100d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("Significant", 20d, 49.99999999999999d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("Minor", 0.000000000001d, 19.999999999999999d, "negative"));
                rangesAndCategories.add(new RangeAndScoreCategory("None", 0d, 0d, "negative"));
                return rangesAndCategories;
            case "Temperature Alignment":
                rangesAndCategories.add(new RangeAndScoreCategory("Well Below 2°C", 0d, 1.64d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("Below 2°C", 1.65d, 1.94d, "positive"));
                rangesAndCategories.add(new RangeAndScoreCategory("2°C", 1.95d, 2.04d, "negative"));
                rangesAndCategories.add(new RangeAndScoreCategory("Above 2°C", 2.05d, 100d, "negative"));
                rangesAndCategories.add(new RangeAndScoreCategory("No Info", -1000d, -0.00001d, "negative"));
                return rangesAndCategories;
        }
        return null;
    }







    public String apiResourceMapper(String researchLine) {
        switch (researchLine) {
            case "tcfdstrategy":
            case "TCFD":
                return "tcfdstrategy";

            case "Brown Share":
            case "Brown Share Assessment":
            case "brownshareasmt":
                return "brownshareasmt";

            case "Physical Risk Management":
            case "physicalriskmgmt":
                return "physicalriskmgmt";

            case "Operations Risk":
            case "operationsrisk":
                return "operationsrisk";

            case "Market Risk":
            case "marketrisk":
                return "marketrisk";

            case "Supply Chain Risk":
            case "supplychainrisk":
                return "supplychainrisk";

            case "Carbon Footprint":
            case "carbonfootprint":
                return "carbonfootprint";

            case "Energy Transition Management":
            case "energytransmgmt":
                return "energytransmgmt";

            case "Green Share":
            case "Green Share Assessment":
            case "greenshareasmt":
                return "greenshareasmt";

            case "physicalriskhazard":
                return "physicalriskhazard";
            case "Temperature Alignment":
            case "temperaturealgmt":
                return "temperaturealgmt";
            //case "ESG Assessments":
            case "esgasmt":
            case "ESG":
            case "esg":
            case "Esg":
            case "ESG Assessment":
                return "esgasmt";

        }
        return "";
    }

    private String apiDashboardResourceMapper(String researchLine) {
        switch (researchLine) {
            case "tcfdstrategy":
            case "TCFD":
                return "tcfd_strategy";

            case "Brown Share":
            case "Brown Share Assessment":
            case "brownshareasmt":
                return "brown_share";

            case "Physical Risk Management":
            case "physicalriskmgmt":
                return "physical_risk_management";

            case "Operations Risk":
            case "operationsrisk":
                return "operations_risk";

            case "Market Risk":
            case "marketrisk":
                return "market_risk";

            case "Supply Chain Risk":
            case "supplychainrisk":
                return "supply_chain_risk";

            case "Carbon Footprint":
            case "carbonfootprint":
                return "carbon_footprint";

            case "Energy Transition Management":
            case "energytransmgmt":
                return "energy_transition";

            case "Green Share":
            case "Green Share Assessment":
            case "greenshareasmt":
                return "green_share";

        }
        return "";
    }

    public String apiResourceMapperWithoutphysicalriskinit(String researchLine) {
        switch (researchLine) {
            case "tcfdstrategy":
            case "TCFD":
                return "tcfdstrategy";

            case "Brown Share":
            case "Brown Share Assessment":
            case "brownshareasmt":
                return "brownshareasmt";

            case "Physical Risk Management":
            case "physicalriskmgmt":
                return "physicalriskmgmt";

            case "Operations Risk":
            case "operationsrisk":
                return "operationsrisk";

            case "Market Risk":
            case "marketrisk":
                return "marketrisk";

            case "Supply Chain Risk":
            case "supplychainrisk":
                return "supplychainrisk";

            case "Carbon Footprint":
            case "carbonfootprint":
                return "carbonfootprint";

            case "Energy Transition Management":
            case "energytransmgmt":
                return "energytransmgmt";

            case "Green Share":
            case "Green Share Assessment":
            case "greenshareasmt":
                return "greenshareasmt";

            case "Temperature Alignment":
            case "temperaturealgmt":
                return "temperaturealgmt";

            case "ESG":
            case "esg":
            case "Esg":
            case "ESG Assessment":
                return "esgasmt";

        }
        return "";
    }

    /**
     * This method posts a request and gets Impact Distribution api response
     *
     * @param portfolio_id  - portfolio Id
     * @param research_line - research line
     * @return response
     * <p>
     * Response Example:
     */
    public Response getImpactDistributionResponse(String portfolio_id, String research_line, APIFilterPayloadWithImpactFilter apiFilterPayloadWithImpactFilter) {
        Response response = null;
        try {
            response = configSpec()
                    .pathParam("portfolio_id", portfolio_id)
                    .pathParam("research_line", apiResourceMapperWithoutphysicalriskinit(research_line))
                    .body(apiFilterPayloadWithImpactFilter)
                    .when()
                    .log().all()
                    .post(PortfolioAnalysisEndpoints.POST_PORTFOLIO_IMPACT_DISTRIBUTION);
            System.out.println(response.prettyPrint());

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }

    public Response getCarbonFootprintEmissionAPIResponse(String portfolio_id, APIFilterPayload apiFilterPayload) {
        return getPortfolioEmissionsResponse(portfolio_id,"carbonfootprint",apiFilterPayload);
    }

    public List<ResearchLineIdentifier> getfilteredData(List<ResearchLineIdentifier> portfolio, String filter) {
        int limit = 0;
        String orderType = "";
        switch (filter) {
            case "top5":
            case "bottom5":
                limit = 5;

                break;
            case "top10":
            case "bottom10":
                limit = 10;
                break;
        }

        Comparator<ResearchLineIdentifier> compareByValueThenName = null;
        switch (filter) {
            case "top10":
            case "top5":
            case "top10pct":
                compareByValueThenName = Comparator
                        .comparing(ResearchLineIdentifier::getInvestmentPercentage).reversed()
                        .thenComparing(ResearchLineIdentifier::getCOMPANY_NAME);
                break;
            case "bottom10":
            case "bottom5":
                compareByValueThenName = Comparator
                        .comparing(ResearchLineIdentifier::getInvestmentPercentage)
                        .thenComparing(ResearchLineIdentifier::getCOMPANY_NAME);
                break;
            case "bottom10pct":
                compareByValueThenName = Comparator
                        .comparing(ResearchLineIdentifier::getInvestmentPercentage).reversed()
                        .thenComparing(ResearchLineIdentifier::getCOMPANY_NAME).reversed();
                break;
        }


        List<ResearchLineIdentifier> expectedList = portfolio.stream()
                .collect(groupingBy(ResearchLineIdentifier::getBVD9_NUMBER))
                .entrySet().stream()
                .map(e -> e.getValue().stream()
                        .reduce((i1, i2) ->
                                new ResearchLineIdentifier(i1.getISIN(),
                                        i1.getRank(),
                                        i1.getBBG_Ticker(),
                                        i1.getSEDOL_CODE_PRIMARY(),
                                        i1.getSCORE(),
                                        i1.getBVD9_NUMBER(),
                                        i1.getPREVIOUS_PRODUCED_DATE(),
                                        i1.getPREVIOUS_SCORE(),
                                        i1.getPLATFORM_SECTOR(),
                                        i1.getCOMPANY_NAME(),
                                        i1.getCOUNTRY_CODE(),
                                        i1.getWORLD_REGION(),
                                        i1.getInvestmentPercentage() + i2.getInvestmentPercentage(),
                                        i1.getValue() + i2.getValue(),
                                        i1.getResearchLineIdForESGModel())))
                .map(java.util.Optional::get)
                .collect(Collectors.toList()).stream()
                .sorted(compareByValueThenName)
                .filter(e -> e.getBVD9_NUMBER() != null)

                .collect(Collectors.toList());

        double previousValue = 0.0;
        List<ResearchLineIdentifier> finalPortfolio = new ArrayList<>();
        int count = limit;
        double sum = 0;
        for (ResearchLineIdentifier ac : expectedList) {
            if (filter.contains("10pct")) {
                sum += ac.getInvestmentPercentage();
                if (sum <= 10) finalPortfolio.add(ac);
                else break;
            } else {
                if (ac.getInvestmentPercentage() != previousValue) {
                    if (limit > 0 && finalPortfolio.size() < count) limit--;
                    else break;
                }
                finalPortfolio.add(ac);
                previousValue = ac.getInvestmentPercentage();
            }
        }

        return finalPortfolio;

    }

    public String getScoreCategory(int min, int max, String researchline, List<RangeAndScoreCategory> rangeAndCategoryList) {
        String category = "";
        switch (researchline) {
            case "operationsrisk":
            case "marketrisk":
            case "supplychainrisk":
                category = min + "-" + max;
                return category;

            case "Physical Risk Management":
            case "Energy Transition Management":
            case "TCFD":
            case "Brown Share":
            case "Brown Share Assessment":
            case "Carbon Footprint":
            case "Green Share":
            case "Green Share Assessment":
                category = rangeAndCategoryList.stream().filter(e -> e.getMin() == min && e.getMax() == max).findFirst().get().getCategory();
                return category;

        }
        return null;
    }



    public Response getPortfolioNameUpdateAPIResponse(String portfolio_id, String portfolio_name) {
        Response response = null;
        try {

            response = configSpec()
                    .pathParam("portfolio_id", portfolio_id)
                    .body("{\"portfolio_name\":\"" + portfolio_name + "\"}")
                    .when()
                    .put(Endpoints.PUT_PORTFOLIO_NAME_UPDATE);


        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }

    public Response getEntitlementHandlerResponse() {
        Response response = null;
        try {
            response = configSpec()
                    .get(Endpoints.GET_ENTITLEMENT_HANDLER);
        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }


    public Response getPortfolioSettingsAPIResponse(String portfolio_id) {
        Response response = null;
        try {

            response = configSpec()
                    .pathParam("portfolio_id", portfolio_id)
                    .when()
                    .post(Endpoints.POST_PORTFOLIO_SETTINGS);
        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        return response;
    }

    public Response getSearchResults(String searchItem) {
        Response response = null;
        try {

            response = configSpec()

                    .body("{search_term: " + searchItem + "}")
                    .when()
                    .post(Endpoints.SEARCH);


        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }

        return response;
    }

}
