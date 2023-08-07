package com.esgc.Dashboard.UI.Tests.Export;

import com.esgc.Base.TestBases.DashboardUITestBase;
import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class ExportEntitlementTests extends DashboardUITestBase {


    @Test(groups = {REGRESSION, UI, ENTITLEMENTS})
    @Xray(test = {4533})
    public void verifyExportIsAvailableInDashboard_Bundle() {

        LoginPage login = new LoginPage();
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_EXPORT_ENTITLEMENT);

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Climate Dashboard");
        test.info("Navigated to Dashboard Page");

        dashboardPage.clickViewCompaniesAndInvestments();
        test.info("Navigated to Companies Page");

        dashboardPage.clickExportCompaniesButton();
        test.info("Exported All Companies and Investments Details in excel format");

        assertTestCase.assertTrue(dashboardPage.isCompaniesAndInvestmentsExcelDownloaded(), "Verify Download of Excel file with Companies and Investments details", 4631);

        dashboardPage.deleteDownloadFolder();
    }

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS})
    @Xray(test = {4522})
    public void verifyExportIsNotAvailableInDashboard_Bundle() {

        LoginPage login = new LoginPage();
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_OUT_EXPORT_ENTITLEMENT);

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Climate Dashboard");
        test.info("Navigated to Dashboard Page");

        dashboardPage.clickViewCompaniesAndInvestments();
        test.info("Navigated to Companies Page");

        assertTestCase.assertTrue(!dashboardPage.isExportButtonEnabled(), "Verify Export button is not available");

    }

}
