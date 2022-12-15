package com.esgc.EntityProfile.UI.Tests;

import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.EntityProfile.UI.Pages.EntityClimateProfilePage;
import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class EsgEntitlementsTests extends UITestBase {

    @Test(groups = {"regression", "ui", "entitlements"})
    @Xray(test = {8353})
    public void validateEsgScoreEntitlement(){
        DashboardPage dashboardPage = new DashboardPage();
        LoginPage login = new LoginPage();

        test.info("Login with user is not having ESG Entitlement");
        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK);

        test.info("ESG Score widget in Dashboard Page");
        assertTestCase.assertTrue(!dashboardPage.verifyAverageEsgScoreWidget(),"Verify ESG Score widget in Dashboard Page");
    }

}
