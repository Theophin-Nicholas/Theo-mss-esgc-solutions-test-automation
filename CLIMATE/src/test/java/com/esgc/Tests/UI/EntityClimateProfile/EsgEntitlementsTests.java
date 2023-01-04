package com.esgc.Tests.UI.EntityClimateProfile;

import com.esgc.Pages.DashboardPage;
import com.esgc.Pages.LoginPage;
import com.esgc.Tests.TestBases.UITestBase;
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
