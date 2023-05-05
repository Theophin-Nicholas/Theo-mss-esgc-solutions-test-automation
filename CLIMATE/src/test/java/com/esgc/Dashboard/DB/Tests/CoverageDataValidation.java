package com.esgc.Dashboard.DB.Tests;

import com.esgc.Base.API.APIModels.APIFilterPayloadWithoutBenchmark;
import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.Dashboard.API.APIModels.DashboardCoverage;
import com.esgc.Dashboard.API.APIModels.Entity;
import com.esgc.Dashboard.API.APIModels.PortfolioSummaryCompanies;
import com.esgc.Dashboard.API.APIModels.RegionSector;
import com.esgc.Dashboard.API.Controllers.DashboardAPIController;
import com.esgc.Dashboard.DB.DBQueries.DashboardQueries;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.Utilities.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.*;

import static com.esgc.Utilities.Groups.*;

public class CoverageDataValidation extends DataValidationTestBase {
    DashboardQueries queries = new DashboardQueries();

    @Test(groups = {REGRESSION, DATA_VALIDATION, DASHBOARD})
    @Xray(test = {3643, 3645, 3646, 3647})
    // Coverage link text  should be Climate Coverage
    public void verifyCoverage() {
        Response portfoliosResponse = APIUtilities.getAvailablePortfoliosForUser();
        JsonPath jsonPathEvaluator = portfoliosResponse.jsonPath();
        List<String> portfolioIds = jsonPathEvaluator.getList("portfolios.portfolio_id");
        String portfolioId = portfolioIds.get(portfolioIds.size() - 1).toString();

        APIFilterPayloadWithoutBenchmark apiFilterPayload = new APIFilterPayloadWithoutBenchmark("all", "all", "03", "2022");

        DashboardAPIController dashboardAPIController = new DashboardAPIController();
        DashboardCoverage coverageAPIResponse = dashboardAPIController
                .getCoverageAPIREsponse(portfolioId, apiFilterPayload).as(DashboardCoverage.class);


        DashboardQueries dashboardQueries = new DashboardQueries();
        Map<String, String> dbResult = dashboardQueries.getCoverage(portfolioId, 2022, 03);
        assertTestCase.assertEquals(coverageAPIResponse.getCoverage(),
                dbResult.get("Climate Coverage") + "/" + dbResult.get("TotalCompanies"), "Validating Total coverage");
        assertTestCase.assertEquals(Math.round(coverageAPIResponse.getPerc_investment()), Integer.parseInt(dbResult.get("CoveragePercent")),
                "Validating Total percentage of coverage");

    }

    @Test(groups = {REGRESSION, DATA_VALIDATION, DASHBOARD},
            description = "Data Validation | Dashboard Page | Coverage Drawer | Verify the sector for the entities in the coverage drawer widget are matching with SF data")
    @Xray(test = {14310, 14313, 14328})
    public void verifyCoverageDrawer() {
        DashboardPage dashboardPage = new DashboardPage();
        if(!dashboardPage.selectPortfolioButton.getText().equals("Sample Portfolio")) {
            dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("Sample Portfolio");
        }

        String portfolioId = "00000000-0000-0000-0000-000000000000";

        dashboardPage.openCoverageDrawer();
        dashboardPage.selectViewByRegion();

        Response response = dashboardAPIController.getPortfolioSummaryCompanies(portfolioId, "region");
        PortfolioSummaryCompanies portfolioSummaryCompanies = response.as(PortfolioSummaryCompanies.class);

        List<Map<String, Object>> dbData = queries.getPortfolioDetail(portfolioId);
        System.out.println("DB Keyset = "+dbData.get(0).keySet());
        assertTestCase.assertTrue(dbData.get(0).containsKey("SECTOR"), "SECTOR column found in df_portfolio table");
        List<String> regions = Arrays.asList("AMER", "APAC", "EMEA");
        for(String region:regions){
            List<Entity> entities = new ArrayList<>();
            if(region.equals("AMER")){
                entities = portfolioSummaryCompanies.getAmericas().entities;
            }
            if(region.equals("APAC")){
                entities = portfolioSummaryCompanies.getAsiaPacific().entities;
            }
            if(region.equals("EMEA")){
                entities = portfolioSummaryCompanies.getEmea().entities;
            }

            for(Entity entity:entities){
                boolean check = false;
                //get list on ENTITY_NAME_BVD from db
                for(Map<String, Object> data:dbData){
                    if(data.get("COMPANY_NAME")==null) continue;
                    if(data.get("COMPANY_NAME").equals(entity.getCompany_name())){
                        assertTestCase.assertTrue(data.get("REGION").equals(region));
                        check = true;
                        break;
                    }
                }
                if(!check){
                    System.out.println("Entity "+entity.getCompany_name()+" not found in DB");
                    assertTestCase.fail("Entity "+entity.getCompany_name()+" not found in DB");
                }
            }
        }

        dashboardPage.selectViewBySector();
        response = dashboardAPIController.getPortfolioSummaryCompanies(portfolioId, "sector");
        portfolioSummaryCompanies = response.as(PortfolioSummaryCompanies.class);

        List<String> sectors = Arrays.asList("Basic Materials", "Communication", "Consumer Discretionary","Consumer Staples",
                "Energy", "Financials", "Health Care", "Industry", "Technology", "Utilities");
        for(String sector:sectors){
            List<Entity> entities = new ArrayList<>();
            if(sector.equals("Basic Materials")){
                entities = portfolioSummaryCompanies.getBasicMaterials().entities;
            }
            if(sector.equals("Communication")){
                entities = portfolioSummaryCompanies.getCommunication().entities;
            }
            if(sector.equals("Consumer Discretionary")){
                entities = portfolioSummaryCompanies.getConsumerDiscretionary().entities;
            }
            if(sector.equals("Consumer Staples")){
                entities = portfolioSummaryCompanies.getConsumerStaples().entities;
            }
            if(sector.equals("Energy")){
                entities = portfolioSummaryCompanies.getEnergy().entities;
            }
            if(sector.equals("Financials")){
                entities = portfolioSummaryCompanies.getFinancials().entities;
            }
            if(sector.equals("Health Care")){
                entities = portfolioSummaryCompanies.getHealthCare().entities;
            }
            if(sector.equals("Industry")){
                entities = portfolioSummaryCompanies.getIndustry().entities;
            }
            if(sector.equals("Technology")){
                entities = portfolioSummaryCompanies.getTechnology().entities;
            }
            if(sector.equals("Utilities")){
                entities = portfolioSummaryCompanies.getUtilities().entities;
            }

            for(Entity entity:entities){
                boolean check = false;
                //get list on ENTITY_NAME_BVD from db
                for(Map<String, Object> data:dbData){
                    if(data.get("COMPANY_NAME")==null) continue;
                    if(data.get("COMPANY_NAME").equals(entity.getCompany_name())){
                        if(!data.get("SECTOR").equals(sector)) check = false;
                        else check = true;
                        break;
                    }
                }
                if(!check){
                    System.out.println("Entity "+entity.getCompany_name()+" not found in DB");
                    assertTestCase.fail("Entity "+entity.getCompany_name()+" not found in DB");
                }
            }
        }

        //download coverage report from panel and verify sectors
        assertTestCase.assertTrue(dashboardPage.isExportButtonEnabled(), "Export button is enabled");
        dashboardPage.deleteDownloadFolder();
        dashboardPage.clickExportCompaniesButton();
        dashboardPage.waitForDataLoadCompletion();
        ExcelUtil excel = dashboardPage.getExcelData("Sample Portfolio", "Data - All research lines");
        List<String> excelCompanyNames = excel.getColumnData(0);
        List<String> excelCompanySectors = excel.getColumnData(4);//sectors column
        for (int i = 0; i < excelCompanyNames.size(); i++) {
            System.out.print(i+"\t");
            boolean check = false;
            for (Map<String, Object> data : dbData) {
                if (data.get("COMPANY_NAME") == null) continue;
                if (data.get("COMPANY_NAME").equals(excelCompanyNames.get(i))) {
                    if (data.get("SECTOR").equals(excelCompanySectors.get(i))) {
                        check = true;
                        break;
                    }
                }
            }
            if (!check) {
                System.out.println("Entity " + excelCompanyNames.get(i) + " not found in DB");
                assertTestCase.fail("Entity " + excelCompanyNames.get(i) + " not found in DB");
            }
        }
    }

    @Test(groups = {REGRESSION, DATA_VALIDATION, DASHBOARD},
            description = "Data Validation | Dashboard Page | Portfolio Monitoring Widget | verify the Sector for entity in portfolio-controversies details.")
    @Xray(test = {14311})
    public void verifySectorForPortfolioMonitoringWidget() {
        DashboardPage dashboardPage = new DashboardPage();
        if(!dashboardPage.selectPortfolioButton.getText().equals("Sample Portfolio")) {
            dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("Sample Portfolio");
        }

        String portfolioId = "00000000-0000-0000-0000-000000000000";
        System.out.println(dashboardPage.getPortfolioMonitoringTableTitle());

        List<Map<String, String>> companies = new ArrayList<>();
        for (int i = 0; i < dashboardPage.controversiesTableCompanyNames.size(); i++) {
            Map<String, String> company = new HashMap<>();
            company.put("companyName", dashboardPage.controversiesTableCompanyNames.get(i).getText());
            company.put("sector", dashboardPage.controversiesTableSectors.get(i).getText());
            companies.add(company);
        }
        System.out.println("companies = " + companies);

        List<Map<String, Object>> dbData = queries.getPortfolioSectorDetail(portfolioId);
        System.out.println("DB Keyset = "+dbData.get(0).keySet());

        for(Map<String, String> company:companies){
            System.out.println("company = "+company.get("companyName"));
            boolean check = false;
            //get list on ENTITY_NAME_BVD from db
            for(Map<String, Object> data:dbData){
                if(data.get("ENTITY_NAME")==null) continue;
                if(data.get("ENTITY_NAME").equals(company.get("companyName"))){
                    if(data.get("MESG_SECTOR").equals(company.get("sector"))){
                        check = true;
                        break;
                    }
                }
            }
            if(!check){
                System.out.println("Entity "+company.get("companyName")+" not found in DB");
                assertTestCase.fail("Entity "+company.get("companyName")+" not found in DB");
            }
        }




    }
}

