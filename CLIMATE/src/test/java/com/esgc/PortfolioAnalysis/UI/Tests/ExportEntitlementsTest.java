package com.esgc.PortfolioAnalysis.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class ExportEntitlementsTest extends UITestBase {
    //TODO PDF export is not working in QA and not available in UAT
    @Test(groups = {REGRESSION, UI, ENTITLEMENTS},
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {4871})
    public void verifyExportIsAvailableInPortfolioAnalysis_Bundle(String researchLine) {

        if (!(researchLine.equals("Carbon Footprint")
                || researchLine.equals("Brown Share Assessment")
                || researchLine.equals("Green Share Assessment")
        )) {
            throw new SkipException("Export is not ready to test in " + researchLine);
        }

        LoginPage login = new LoginPage();
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_EXPORT_ENTITLEMENT);

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToResearchLine(researchLine);
        BrowserUtils.wait(5);

        dashboardPage.clickExportCompaniesButtonInPortfolioAnalysis();
        test.info("Exported All Companies and Investments Details in pdf format");

        assertTestCase.assertTrue(dashboardPage.isCompaniesAndInvestmentsPdfDownloaded(), "Verify Download of Excel file with Companies and Investments details", 4631);
        dashboardPage.deleteDownloadFolder();

    }

    @Test(groups = {REGRESSION, UI, ENTITLEMENTS},
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {4838})
    public void verifyExportIsNotAvailableInPortfolioAnalysis_Bundle(String researchLine) {

        if (!(researchLine.equals("Carbon Footprint")
                || researchLine.equals("Brown Share Assessment")
                || researchLine.equals("Green Share Assessment"))) {
            throw new SkipException("Export is not ready to test in " + researchLine);
        }

        LoginPage login = new LoginPage();
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_OUT_EXPORT_ENTITLEMENT);

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToResearchLine(researchLine);
        BrowserUtils.wait(5);
        assertTestCase.assertTrue(!dashboardPage.isExportButtonEnabled(), researchLine + "Verify Export button is not available");
    }
}
