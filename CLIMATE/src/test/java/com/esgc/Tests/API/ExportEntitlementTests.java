package com.esgc.Tests.API;

import com.esgc.Controllers.DashboardAPIController;
import com.esgc.Tests.TestBases.APITestBase;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class ExportEntitlementTests extends APITestBase {

    @Test(groups = {"api", "regression"})
    @Xray(test= {7817, 7843, 9805})
    public void VerifyExportIsAvailable() {
        String portfolioId = "00000000-0000-0000-0000-000000000000";
        DashboardAPIController dashboardAPIController = new DashboardAPIController();
        dashboardAPIController.verifyExport(APITestBase.portfolioID, APITestBase.portfolioName,"all","all", "2022","05").then().assertThat().statusCode(200);
        dashboardAPIController.verifyPortfolioAnalysisExcelExport(portfolioId).then().assertThat().statusCode(200);
        dashboardAPIController.verifyPortfolioAnalysisUploadJsonUrl(portfolioId).then().assertThat().statusCode(200);
    }

    @Test(groups = {"api", "regression"})
    @Xray(test={7817, 7843})
    public void VerifyExportIsNotAvailableBundle() {
        String portfolioId = "00000000-0000-0000-0000-000000000000";
        getNoExportBundleAccessTokenDataValidation();
        DashboardAPIController dashboardAPIController = new DashboardAPIController();
        dashboardAPIController.verifyExport(APITestBase.portfolioID, APITestBase.portfolioName,"all","all", "2022","05").then().assertThat().statusCode(403);
        dashboardAPIController.verifyPortfolioAnalysisExcelExport(portfolioId).then().assertThat().statusCode(403);
        dashboardAPIController.verifyPortfolioAnalysisUploadJsonUrl(portfolioId).then().assertThat().statusCode(403);
    }
}
