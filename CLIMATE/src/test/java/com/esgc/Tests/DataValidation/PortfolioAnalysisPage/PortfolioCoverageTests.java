package com.esgc.Tests.DataValidation.PortfolioAnalysisPage;

import com.esgc.APIModels.APIFilterPayload;
import com.esgc.APIModels.PortoflioAnalysisModels.PortfolioCoverageWrapper;
import com.esgc.DBModels.ResearchLineIdentifier;
import com.esgc.Tests.TestBases.DataValidationTestBase;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.PortfolioUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PortfolioCoverageTests extends DataValidationTestBase {

    @Test(groups = {"regression", "data_validation"}, dataProvider = "researchLines")
    @Xray(test = {6372, 6726, 7242})
    public void verifyPortfolioCoverageWithMixedIdentifiers(@Optional String sector, @Optional String region,
                                                            @Optional String researchLine, String month, String year) {


        List<ResearchLineIdentifier> portfolioToUpload = dataValidationUtilities.getPortfolioToUpload(researchLine, month, year);
        double totalValues = portfolioUtilities.calculateTotalSumOfInvestment(portfolioToUpload);

        String fileName = String.format("Portfolio Coverage %s - %s - %s", researchLine, sector, region, month, year);
        String path = portfolioUtilities.createPortfolio(fileName, portfolioToUpload);
        System.out.println(path);
        Response response = controller.importPortfolio(APIUtilities.userID(), fileName + ".csv", path);
        response.then().assertThat().body("portfolio_name", Matchers.notNullValue());

        portfolioToUpload = dataValidationUtilities.preparePortfolioForTesting(portfolioToUpload);
        int numberOfCompaniesInPortfolio = portfolioToUpload.size();

        //filter for a given region
        if (!region.equals("all")) {
            portfolioToUpload = portfolioToUpload.stream().filter(r -> r.getWORLD_REGION()
                    .equals(region)).collect(Collectors.toList());
        }

        //filter for a given region
        if (!sector.equals("all")) {
            portfolioToUpload = portfolioToUpload.stream().filter(r -> r.getPLATFORM_SECTOR()
                    .equals(sector)).collect(Collectors.toList());
        }

        int numberOfCoveredCompaniesInResearchLine = (int) portfolioToUpload.stream().filter(r -> r.getSCORE() >= 0).count();

        if (researchLine.equals("Temperature Alignment")) {
            numberOfCoveredCompaniesInResearchLine = (int) portfolioToUpload.stream().filter(r -> r.getSCORE() >= -2).count();
        }

        String portfolioId = response.getBody().jsonPath().get("portfolio_id");
        System.out.println("Portfolio Created, id: " + portfolioId);
        test.info("portfolio_id=" + portfolioId);
        test.info(String.format("Filter= %s %s %s %s", region, sector, month, year));

        APIFilterPayload apiFilterPayload = new APIFilterPayload();
        apiFilterPayload.setSector(sector);
        apiFilterPayload.setRegion(region);
        apiFilterPayload.setBenchmark("");
        apiFilterPayload.setYear(year);
        apiFilterPayload.setMonth(month);

        Response portfolioCoverageResponse = controller.getPortfolioCoverageResponse(
                portfolioId, researchLine, apiFilterPayload);

        List<PortfolioCoverageWrapper> portfolioCoverageWrapper = Arrays.asList(portfolioCoverageResponse
                .as(PortfolioCoverageWrapper[].class));

        String companiesResponse = portfolioCoverageWrapper.get(0).getPortfolioCoverage().getCompanies();
        String investmentResponse = portfolioCoverageWrapper.get(0).getPortfolioCoverage().getInvestment();

        double actualPercentage = PortfolioUtilities.round(Double.parseDouble(investmentResponse),2);
        double expectedPercentage = PortfolioUtilities.round(dataValidationUtilities.getTotalInvestmentPercentage(portfolioToUpload, totalValues),2);

        if (researchLine.equals("Temperature Alignment")) {
            expectedPercentage = PortfolioUtilities.round(dataValidationUtilities.getTotalInvestmentForTemperatureAlignment(portfolioToUpload, totalValues),2);
        }
        assertTestCase.assertEquals(companiesResponse, numberOfCoveredCompaniesInResearchLine + "/" + numberOfCompaniesInPortfolio, "companies in " + researchLine);
        assertTestCase.assertEquals(actualPercentage, expectedPercentage, "total investment in " + researchLine);
    }

    @DataProvider(name = "researchLines")
    public Object[][] provideFilterParameters() {

        return new Object[][]
                {
                        {"all", "AMER", "ESG", "09", "2022"},
                        {"all", "EMEA", "ESG", "09", "2022"},
                        {"all", "APAC", "ESG", "09", "2022"},
                        {"all", "all", "ESG", "09", "2022"},

                        {"all", "AMER", "Physical Risk Management", "12", "2020"},
                        {"all", "EMEA", "Physical Risk Management", "12", "2020"},
                        {"all", "APAC", "Physical Risk Management", "12", "2020"},
                        {"all", "all", "Physical Risk Management", "12", "2020"},

                        {"all", "all", "operationsrisk", "12", "2020"},
                        {"all", "APAC", "operationsrisk", "12", "2020"},
                        {"all", "EMEA", "operationsrisk", "12", "2020"},
                        {"all", "AMER", "operationsrisk", "12", "2020"},

                        {"all", "all", "operationsrisk", "12", "2021"},
                        {"all", "APAC", "operationsrisk", "12", "2021"},
                        {"all", "EMEA", "operationsrisk", "12", "2021"},
                        {"all", "AMER", "operationsrisk", "12", "2021"},


                        {"all", "all", "Temperature Alignment", "03", "2022"},
                        {"all", "APAC", "Temperature Alignment", "03", "2022"},
                        {"all", "EMEA", "Temperature Alignment", "03", "2022"},
                        {"all", "AMER", "Temperature Alignment", "03", "2022"},

                        {"all", "APAC", "Carbon Footprint", "02", "2021"},
                        {"all", "AMER", "Carbon Footprint", "02", "2021"},
                        {"all", "EMEA", "Carbon Footprint", "02", "2021"},
                        {"all", "all", "Carbon Footprint", "02", "2021"},
                        /**  { "all", "APAC", "Carbon Footprint", "03", "2021"},
                        { "all", "AMER", "Carbon Footprint", "03", "2021"},
                        { "all", "EMEA", "Carbon Footprint", "03", "2021"},
                        { "all", "all", "Carbon Footprint", "03", "2021"},
                        {"all", "APAC", "Carbon Footprint", "04", "2021"},
                        {"all", "AMER", "Carbon Footprint", "04", "2021"},
                        {"all", "EMEA", "Carbon Footprint", "04", "2021"},
                        {"all", "all", "Carbon Footprint", "04", "2021"},*/
                        {"all", "APAC", "Carbon Footprint", "12", "2020"},
                        {"all", "AMER", "Carbon Footprint", "12", "2020"},
                        {"all", "EMEA", "Carbon Footprint", "12", "2020"},
                        {"all", "all", "Carbon Footprint", "12", "2020"},

                        {"all", "APAC", "Brown Share", "03", "2021"},
                        {"all", "AMER", "Brown Share", "03", "2021"},
                        {"all", "EMEA", "Brown Share", "03", "2021"},
                        {"all", "all", "Brown Share", "03", "2021"},

                        {"all", "all", "Green Share", "03", "2021"},
                        {"all", "APAC", "Green Share", "03", "2021"},
                        {"all", "EMEA", "Green Share", "03", "2021"},
                        {"all", "AMER", "Green Share", "03", "2021"}
                };
    }

//    @DataProvider(name = "researchLines", parallel = true)
//    public Object[][] provideFilterParameters() {
//
//        return new Object[][]
//                {
//                        {"all", "all", "operationsrisk", "12", "2020"},
//                        {"all", "APAC", "operationsrisk", "12", "2020"},
//                        {"all", "EMEA", "operationsrisk", "12", "2020"},
//                        {"all", "AMER", "operationsrisk", "12", "2020"},
//                        {"all", "AMER", "supplychainrisk", "12", "2020"},
//                        {"all", "EMEA", "supplychainrisk", "12", "2020"},
//                        {"all", "APAC", "supplychainrisk", "12", "2020"},
//                        {"all", "all", "supplychainrisk", "12", "2020"},
//                        {"all", "EMEA", "marketrisk", "12", "2020"},
//                        {"all", "AMER", "marketrisk", "12", "2020"},
//                        {"all", "APAC", "marketrisk", "12", "2020"},
//                        {"all", "all", "marketrisk", "12", "2020"},
//                        {"all", "all", "operationsrisk", "12", "2021"},
//                        {"all", "APAC", "operationsrisk", "12", "2021"},
//                        {"all", "EMEA", "operationsrisk", "12", "2021"},
//                        {"all", "AMER", "operationsrisk", "12", "2021"},
//                        {"all", "AMER", "supplychainrisk", "12", "2021"},
//                        {"all", "EMEA", "supplychainrisk", "12", "2021"},
//                        {"all", "APAC", "supplychainrisk", "12", "2021"},
//                        {"all", "all", "supplychainrisk", "12", "2021"},
//                        {"all", "EMEA", "marketrisk", "12", "2021"},
//                        {"all", "AMER", "marketrisk", "12", "2021"},
//                        {"all", "APAC", "marketrisk", "12", "2021"},
//                        {"all", "all", "marketrisk", "12", "2021"},
//                        {"all", "AMER", "Physical Risk Management", "12", "2020"},
//                        {"all", "EMEA", "Physical Risk Management", "12", "2020"},
//                        {"all", "APAC", "Physical Risk Management", "12", "2020"},
//                        {"all", "all", "Physical Risk Management", "12", "2020"},
//                        {"all", "APAC", "Carbon Footprint", "02", "2021"},
//                        {"all", "AMER", "Carbon Footprint", "02", "2021"},
//                        {"all", "EMEA", "Carbon Footprint", "02", "2021"},
//                        {"all", "all", "Carbon Footprint", "02", "2021"},
//                        /**  { "all", "APAC", "Carbon Footprint", "03", "2021"},
//                        { "all", "AMER", "Carbon Footprint", "03", "2021"},
//                        { "all", "EMEA", "Carbon Footprint", "03", "2021"},
//                        { "all", "all", "Carbon Footprint", "03", "2021"},
//                        {"all", "APAC", "Carbon Footprint", "04", "2021"},
//                        {"all", "AMER", "Carbon Footprint", "04", "2021"},
//                        {"all", "EMEA", "Carbon Footprint", "04", "2021"},
//                        {"all", "all", "Carbon Footprint", "04", "2021"},*/
//                        {"all", "APAC", "Carbon Footprint", "12", "2020"},
//                        {"all", "AMER", "Carbon Footprint", "12", "2020"},
//                        {"all", "EMEA", "Carbon Footprint", "12", "2020"},
//                        {"all", "all", "Carbon Footprint", "12", "2020"},
//                        {"all", "APAC", "Energy Transition Management", "03", "2021"},
//                        {"all", "AMER", "Energy Transition Management", "03", "2021"},
//                        {"all", "EMEA", "Energy Transition Management", "03", "2021"},
//                        {"all", "all", "Energy Transition Management", "03", "2021"},
//                        {"all", "APAC", "Brown Share", "03", "2021"},
//                        {"all", "AMER", "Brown Share", "03", "2021"},
//                        {"all", "EMEA", "Brown Share", "03", "2021"},
//                        {"all", "all", "Brown Share", "03", "2021"},
//                        {"all", "all", "TCFD", "03", "2021"},
//                        {"all", "APAC", "TCFD", "03", "2021"},
//                        {"all", "AMER", "TCFD", "03", "2021"},
//                        {"all", "EMEA", "TCFD", "03", "2021"},
//                        {"all", "all", "Green Share", "03", "2021"},
//                        {"all", "APAC", "Green Share", "03", "2021"},
//                        {"all", "EMEA", "Green Share", "03", "2021"},
//                        {"all", "AMER", "Green Share", "03", "2021"}
//                };
//    }
}
