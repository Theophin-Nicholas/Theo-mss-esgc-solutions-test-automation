package com.esgc.Dashboard.UI.Tests;

import com.esgc.Base.TestBases.DashboardUITestBase;
import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.Dashboard.API.Controllers.DashboardAPIController;
import com.esgc.Dashboard.DB.DBQueries.DashboardQueries;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    @Test(groups = {REGRESSION, UI, SMOKE})
    @Xray(test = {12278,12279, 12320,12321})
    public void VerifyBrownShareAssessmentCategories() {

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();
        BrowserUtils.wait(5);
        dashboardPage.selectOrDeselectHeatMapSection("Brown Share Assessment");
        BrowserUtils.wait(5);
        dashboardPage.selectOrDeselectHeatMapSection("Overall ESG Score");
        BrowserUtils.wait(5);

        dashboardPage.verifyBrownShareCoverageInHeatMapDescription();

        List<String> actualCategoriesList = new ArrayList<>();
        for(WebElement element: dashboardPage.brownShareCategories){
            actualCategoriesList.add(element.getText());
        }
        Collections.sort(actualCategoriesList);
        System.out.println("Actual Categories: "+actualCategoriesList);
        List<String> expectedCategoriesList = new ArrayList<>();
        expectedCategoriesList.add("No Involvement");
        expectedCategoriesList.add("Minor Involvement");
        expectedCategoriesList.add("Major Involvement");
        Collections.sort(expectedCategoriesList);
        System.out.println("Expected Categories: "+expectedCategoriesList);
        assertTestCase.assertTrue(actualCategoriesList.equals(expectedCategoriesList),"Brown Share Categories are not matched");

        Collections.sort(expectedCategoriesList, Collections.reverseOrder());
        dashboardPage.verifyBrownShareCategoryInHeatMapDrawer(expectedCategoriesList);

    }

    @Test(groups = {REGRESSION, UI, SMOKE})
    @Xray(test = {12323, 12324})
    public void CompareUIandDBHeatMapBrownShareData() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();
        BrowserUtils.wait(5);
        dashboardPage.selectOrDeselectHeatMapSection("Brown Share Assessment");
        BrowserUtils.wait(5);
        dashboardPage.selectOrDeselectHeatMapSection("Overall ESG Score");
        BrowserUtils.wait(5);
        dashboardPage.selectOrDeselectHeatMapSection("Overall ESG Score");
        BrowserUtils.wait(5);

        DashboardQueries dashboardQueries = new DashboardQueries();
        List<Map<String, Object>> dbBrownShareHeatMapData = dashboardQueries.getPortfolioBrownShareInfo("00000000-0000-0000-0000-000000000000","2022","12");
        List<Map<String, String>> uiBrownShareHeatMapData = dashboardPage.getBrownShareDataFromHeatMapDrawer();
        assertTestCase.assertEquals(dbBrownShareHeatMapData.size(),uiBrownShareHeatMapData.size(),"Company Records Count from both DB and UI are not matching");
        for(int i=0;i<dbBrownShareHeatMapData.size();i++){
            String dbCompanyName = dbBrownShareHeatMapData.get(i).get("COMPANY_NAME").toString();
            System.out.println("Verification of Record: "+i+" - "+dbCompanyName);
            for(int j=0;j<uiBrownShareHeatMapData.size();j++){
                String uiCompanyName = uiBrownShareHeatMapData.get(j).get("COMPANY_NAME");
                if(dbCompanyName.equals(uiCompanyName)){
                    Object objAccurateScore = dbBrownShareHeatMapData.get(i).get("BS_FOSF_INDUSTRY_REVENUES_ACCURATE_SOURCE");
                    String dbScoreRange = dbBrownShareHeatMapData.get(i).get("SCORE_RANGE").toString();
                    String uiScoreRange = uiBrownShareHeatMapData.get(j).get("SCORE_RANGE");
                    System.out.println("DB Score Range:"+dbScoreRange+", UI Score Range:"+uiScoreRange);
                    if(objAccurateScore!=null){
                        System.out.println("DB Accurate Score:"+objAccurateScore);
                        if(objAccurateScore.toString().contains(">") || objAccurateScore.toString().contains("<")) {
                            dbScoreRange = objAccurateScore + "%";
                        } else{
                            if(Float.parseFloat(objAccurateScore.toString())>0 && Float.parseFloat(objAccurateScore.toString())<1){
                                dbScoreRange = "<1%";
                            } else {
                                dbScoreRange = Float.parseFloat(objAccurateScore.toString()) + "%";
                                uiScoreRange = Float.parseFloat(uiScoreRange.replace("%", "")) + "%";
                            }
                        }
                        System.out.println("DB Score Range:"+dbScoreRange+", UI Score Range:"+uiScoreRange);
                    }
                    assertTestCase.assertEquals(dbScoreRange,uiScoreRange,dbCompanyName+" Score Ranges are not matching");

                    String dbScoreCategory = dbBrownShareHeatMapData.get(i).get("SCORE_CATEGORY").toString();
                    String uiScoreCategory = uiBrownShareHeatMapData.get(j).get("SCORE_CATEGORY");
                    System.out.println("DB Score Category:"+dbScoreCategory+", UI Score Category:"+uiScoreCategory);
                    assertTestCase.assertEquals(dbScoreCategory,uiScoreCategory,dbCompanyName+" Score Category are not matching");
                    break;
                }
                if(j==uiBrownShareHeatMapData.size())
                    assertTestCase.assertTrue(false,dbCompanyName+" Company Name is not available in UIHeatMap Drawer");
            }
        }
    }

    @Test(groups = {REGRESSION, UI, SMOKE})
    @Xray(test = {12322})
    public void CompareUIandApiHeatMapBrownShareData() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();
        BrowserUtils.wait(5);
        dashboardPage.selectOrDeselectHeatMapSection("Brown Share Assessment");
        BrowserUtils.wait(5);
        dashboardPage.selectOrDeselectHeatMapSection("Overall ESG Score");
        BrowserUtils.wait(5);
        dashboardPage.selectOrDeselectHeatMapSection("Overall ESG Score");
        BrowserUtils.wait(5);

        DashboardAPIController controller = new DashboardAPIController();
        List<Map<String, String>> apiBrownShareHeatMapData = controller.getPortfolioBrownShareData("00000000-0000-0000-0000-000000000000","2022","12");
        List<Map<String, String>> uiBrownShareHeatMapData = dashboardPage.getBrownShareDataFromHeatMapDrawer();
        assertTestCase.assertEquals(apiBrownShareHeatMapData.size(),uiBrownShareHeatMapData.size(),"Company Records Count from both DB and UI are not matching");
        for(int i=0;i<apiBrownShareHeatMapData.size();i++){
            String apiCompanyName = apiBrownShareHeatMapData.get(i).get("COMPANY_NAME");
            System.out.println("Verification of Record: "+i+" - "+apiCompanyName);
            for(int j=0;j<uiBrownShareHeatMapData.size();j++){
                String uiCompanyName = uiBrownShareHeatMapData.get(j).get("COMPANY_NAME");
                if(apiCompanyName.equals(uiCompanyName)){
                    String apiScoreRange = apiBrownShareHeatMapData.get(i).get("SCORE_RANGE");
                    String uiScoreRange = uiBrownShareHeatMapData.get(j).get("SCORE_RANGE");
                    System.out.println("API Score Range:"+apiScoreRange+", UI Score Range:"+uiScoreRange);
                    assertTestCase.assertEquals(apiScoreRange,uiScoreRange,apiCompanyName+" Score Ranges are not matching");

                    String apiScoreCategory = apiBrownShareHeatMapData.get(i).get("SCORE_CATEGORY").toString();
                    String uiScoreCategory = uiBrownShareHeatMapData.get(j).get("SCORE_CATEGORY");
                    System.out.println("API Score Category:"+apiScoreCategory+", UI Score Category:"+uiScoreCategory);
                    assertTestCase.assertEquals(apiScoreCategory,uiScoreCategory,apiCompanyName+" Score Category are not matching");
                    break;
                }
                if(j==uiBrownShareHeatMapData.size())
                    assertTestCase.assertTrue(false,apiCompanyName+" Company Name is not available in UIHeatMap Drawer");
            }
        }
    }
}

