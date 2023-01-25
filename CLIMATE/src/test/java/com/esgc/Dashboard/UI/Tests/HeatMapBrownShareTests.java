package com.esgc.Dashboard.UI.Tests;

import com.esgc.Base.TestBases.DashboardUITestBase;
import com.esgc.Dashboard.API.APIModels.CompanyBrownShareInfo;
import com.esgc.Dashboard.API.Controllers.DashboardAPIController;
import com.esgc.Dashboard.DB.DBQueries.DashboardQueries;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Database.DatabaseDriver;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.*;

import static com.esgc.Utilities.Groups.*;

/**
 * Created by Satish Arukonda on 5/10/2022.
 */

public class HeatMapBrownShareTests extends DashboardUITestBase {

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

    @Test(groups = {UI, DASHBOARD, REGRESSION})
    @Xray(test = {12264})
    public void verifyBrownShareCategoryPercentages() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();
        dashboardPage.waitForDataLoadCompletion();
        test.info("Check Score categories in Performance Charts");

        List<String> filterOptions = Arrays.asList("10", "25", "50", "75", "100");
        for(String option:filterOptions){
            dashboardPage.selectPerformanceChartViewSizeFromDropdown(option);
            int rows = dashboardPage.getPerformanceChartRowCount();
            for(int i=1; i<=rows; i++){
                String xpath = "//tr["+i+"]/td[@heap_id='perfchart']/following-sibling::td[10]";
                WebElement element = Driver.getDriver().findElement(By.xpath(xpath));
                BrowserUtils.scrollTo(element);
                String category = Driver.getDriver().findElement(By.xpath(xpath)).getText();
                System.out.println("Record - "+i+": category - "+category);
                if(!category.isEmpty())
                    assertTestCase.assertTrue(category.contains("%"),"Brown Share Assessment Score Category is not percent");
            }
        }
    }

    @Test(groups = {REGRESSION, UI, DASHBOARD})
    @Xray(test = {12323, 12324})
    public void compareUIandDBHeatMapBrownShareData() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();
        BrowserUtils.wait(5);
        dashboardPage.selectOrDeselectHeatMapSection("Brown Share Assessment");
        BrowserUtils.wait(5);
        dashboardPage.selectOrDeselectHeatMapSection("Overall ESG Score");
        BrowserUtils.wait(5);
        dashboardPage.selectOrDeselectHeatMapSection("Overall ESG Score");
        BrowserUtils.wait(10);

        DashboardQueries dashboardQueries = new DashboardQueries();
        DatabaseDriver.createDBConnection();
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

    @Test(groups = {REGRESSION, UI, DASHBOARD})
    @Xray(test = {12322})
    public void compareUIandApiHeatMapBrownShareData() {
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

        List<Map<String, String>> apiBrownShareHeatMapData = getPortfolioBrownShareData("00000000-0000-0000-0000-000000000000","2022","12");
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

                    String apiScoreCategory = apiBrownShareHeatMapData.get(i).get("SCORE_CATEGORY");
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

    public synchronized List<Map<String, String>> getPortfolioBrownShareData(String portfolioId, String year, String month) {
        Response response = null;
        List<Map<String, String>> apiBrownShareHeatMapData = new ArrayList<>();
        DashboardAPIController controller = new DashboardAPIController();
        try {
            List<String> categoriesList = new ArrayList<>();
            categoriesList.add("No Involvement");
            categoriesList.add("Minor Involvement");
            categoriesList.add("Major Involvement");
            for(String category:categoriesList) {
                response = controller.getHeatMapEntitiesData(portfolioId, year, month, "brownshareasmt", category);
                System.out.println(response.prettyPrint());

                List<CompanyBrownShareInfo> companyList = new ArrayList<>();
                companyList = Arrays.asList(response.as(CompanyBrownShareInfo[].class));
                System.out.println("Category:"+category+" - Companies:"+companyList.size());

                for(int j=0; j<companyList.size(); j++) {
                    Map<String, String> companyDetails = new HashMap<>();
                    String companyName = companyList.get(j).company_name;
                    String scoreRange = companyList.get(j).category_score_r1_1;
                    Double score = companyList.get(j).score_rl_1;
                    if(score != null && score>0){
                        if(score>0 && score<1){
                            scoreRange = "<1%";
                        }else{
                            if(String.valueOf(score).endsWith(".0")){
                                scoreRange = score.intValue()+"%";
                            }else {
                                scoreRange = score.floatValue() + "%";
                            }
                        }
                    }
                    companyDetails.put("COMPANY_NAME", companyName);
                    companyDetails.put("SCORE_RANGE", scoreRange);
                    companyDetails.put("SCORE_CATEGORY", category);
                    apiBrownShareHeatMapData.add(companyDetails);
                }

            }

        } catch (Exception e) {
            System.out.println("Inside exception " + e.getMessage());
        }
        System.out.println("Total Companies Count: " + apiBrownShareHeatMapData.size());

        return apiBrownShareHeatMapData;
    }
}

