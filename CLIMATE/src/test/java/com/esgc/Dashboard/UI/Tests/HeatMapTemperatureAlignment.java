package com.esgc.Dashboard.UI.Tests;

import com.esgc.Base.TestBases.DashboardUITestBase;
import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static com.esgc.Utilities.Groups.*;

/**
 * Created by Satish Arukonda on 5/10/2022.
 */

public class HeatMapTemperatureAlignment extends DashboardUITestBase {

    @Test(groups = {ENTITY_PROFILE, REGRESSION, UI, SMOKE, ENTITLEMENTS})
    @Xray(test = {7466})
    public void VerifyHeatMapsTemperatureAlignmentIsAvailableWithBundle(){
        LoginPage login = new LoginPage();
        login.entitlementsLogin(EntitlementsBundles.TRANSITION_RISK);

        DashboardPage dashboardPage = new DashboardPage();

        test.info("Navigating to Dashboard page");
        dashboardPage.navigateToPageFromMenu("Dashboard");

        BrowserUtils.wait(5);
        assertTestCase.assertTrue(dashboardPage.isHeatMapTempratureAlignmentAvailable(), "Verify Heat Map's Temperature Alignment is available");

    }

    @Test(groups = {REGRESSION, UI, SMOKE})
    @Xray(test = 7467)
    public void VerifyHeatMapsTemperatureAlignmentIsNotAvailableWithBundle() {
        LoginPage login = new LoginPage();
        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK);

        DashboardPage dashboardPage = new DashboardPage();

        test.info("Navigating to Dashboard page");
        dashboardPage.navigateToPageFromMenu("Dashboard");

        assertTestCase.assertTrue(!dashboardPage.isHeatMapTempratureAlignmentAvailable(), "Verify Heat Map's Temperature Alignment not is available");
    }

    @Test(groups = {REGRESSION, UI, SMOKE})
    @Xray(test = 7480)
    public void VerifyHeatmapTemperatureAlignmentOnGrid() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.selectOrDeselectHeatMapSection("Temperature Alignment");
        dashboardPage.selectOrDeselectHeatMapSection("Physical Risk: Supply Chain Risk");
        assertTestCase.assertTrue(dashboardPage.verifyHeatMapTableData(), "Verify Heat Map table data format");
    }

    @Test(enabled = false,groups = {REGRESSION, UI, SMOKE})
    @Xray(test = 7468)
    public void VerifyTemperatureAlignmentRLPresentationAndInteraction () {
        DashboardPage dashboardPage = new DashboardPage();
        ArrayList<String> researchLines = new ArrayList<String>();
        // to be de-scoped remove. validate with furkan ???
        researchLines.add("Overall ESG Score");
        researchLines.add("Physical Risk: Operations Risk");
        researchLines.add("Physical Risk: Market Risk");
        researchLines.add("Physical Risk: Supply Chain Risk");
        researchLines.add("Physical Risk Management");
        researchLines.add("Temperature Alignment");
        researchLines.add("Carbon Footprint");
        researchLines.add("Green Share Assessment");
        researchLines.add("Brown Share Assessment");

        BrowserUtils.wait(10);

        for(String rl: researchLines) {
            System.out.println("rl "+rl);
            dashboardPage.selectOrDeselectHeatMapSection("Temperature Alignment");
            BrowserUtils.wait(10);
            dashboardPage.selectOrDeselectHeatMapSection(rl);
            BrowserUtils.wait(10);
            assertTestCase.assertTrue(dashboardPage.verifyHeatMapTableAxis("Temperature Alignment",rl.replace("Physical Risk: ","")), "Verify Heat Map X & Y Axis Values");
            dashboardPage.selectOrDeselectHeatMapSection("Temperature Alignment");
            BrowserUtils.wait(10);
        }
    }
}

