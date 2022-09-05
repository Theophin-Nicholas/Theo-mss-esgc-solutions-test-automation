package com.esgc.Tests.API;

import com.esgc.Controllers.DashboardAPIController;
import com.esgc.Tests.TestBases.APITestBase;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class ExportEntitlementTests extends APITestBase {

    @Test(groups = {"api", "regression"})
    @Xray(test= {7817, 7843})
    public void VerifyExportIsAvailable() {
        DashboardAPIController dashboardAPIController = new DashboardAPIController();
        dashboardAPIController.verifyExport(APITestBase.portfolioID, APITestBase.portfolioName,"all","all", "2022","05").then().assertThat().statusCode(200);
        dashboardAPIController.verifyPortfolioAnalysisExcelExport().then().assertThat().statusCode(200);
        dashboardAPIController.verifyPortfolioAnalysisUploadJsonUrl().then().assertThat().statusCode(200);
    }

    @Test(groups = {"api", "regression"})
    @Xray(test={7817, 7843})
    public void VerifyExportIsNotAvailableBundle() {
        getNoExportBundleAccessTokenDataValidation();
        DashboardAPIController dashboardAPIController = new DashboardAPIController();
        dashboardAPIController.verifyExport(APITestBase.portfolioID, APITestBase.portfolioName,"all","all", "2022","05").then().assertThat().statusCode(403);
        dashboardAPIController.verifyPortfolioAnalysisExcelExport().then().assertThat().statusCode(403);
        dashboardAPIController.verifyPortfolioAnalysisUploadJsonUrl().then().assertThat().statusCode(403);
    }
}
