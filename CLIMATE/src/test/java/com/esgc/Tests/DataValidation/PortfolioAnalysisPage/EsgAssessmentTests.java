package com.esgc.Tests.DataValidation.PortfolioAnalysisPage;

import com.esgc.Pages.ResearchLinePage;
import com.esgc.Tests.TestBases.DataValidationTestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.ESGUtilities;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class EsgAssessmentTests extends DataValidationTestBase {

    @Test(groups = {"regression", "ui", "smoke", "esg"},
            description = "Verify ESG Summary Coverage section")
    @Xray(test = {8373, 8374, 8375, 8376, 8377, 8378, 9968})
    public void verifyEsgAssessmentCoverage() {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToResearchLine("ESG Assessments");
        test.info("Navigated to ESG Assessments Page");

        assertTestCase.assertTrue(researchLinePage.validateIfEsgCardInfoIsDisplayed(), "Verify ESG Card Info with coverage details");

        researchLinePage.selectEsgPortfolioCoverage();
        assertTestCase.assertTrue(researchLinePage.validateCompaniesEsgPopupIsDisplayed(), "Verify ESG Drawer coverage details");

        // Verify ESG Drawer table headers
        ArrayList<String> columnsInDrawer = new ArrayList<>();
        columnsInDrawer.add("Company");
        columnsInDrawer.add("% Investment");
        columnsInDrawer.add("ESG Score");
        columnsInDrawer.add("Model Version");
        assertTestCase.assertTrue(researchLinePage.verifyColumnsInEsgDrawer(columnsInDrawer), "Verify ESG Coverage Drawer columns");

        // Verify ESG Drawer - Methodology Values either 1.0 or 2.0
        assertTestCase.assertTrue(researchLinePage.verifyEsgMethodologyValues(), "Verify ESG Methodology Values either 1.0 or 2.0");

        // Verify ESG Drawer - ESG Score Values are Advanced, Robust, Limited, Weak
        assertTestCase.assertTrue(researchLinePage.verifyEsgScoreValues(), "Verify ESG Score Values are Advanced, Robust, Limited, Weak");

        // Verify ESG Drawer - records order priority ESG Score, %Investment, Company Name
        assertTestCase.assertTrue(researchLinePage.verifyEsgCompaniesOrder(), "Verify ESG Companies are sorted based on ESG values");

        // Verify ESG Drawer - Compare with DB data
        List<List<String>> esgInfoListFromUI = researchLinePage.readEsgInfoFromDrawer();
        List<List<Object>> esgInfoListFromDB = portfolioQueries.getEsgInfoFromDB();
        assertTestCase.assertEquals(esgInfoListFromUI.size(), esgInfoListFromDB.size(), "Verify ESG Records count in UI and DB");

        for (List<String> esgInfoUI : esgInfoListFromUI) {
            boolean found = false;
            for (List<Object> esgInfoDB : esgInfoListFromDB) {
                if (esgInfoUI.get(0).contains(esgInfoDB.get(0).toString()) &&
                        esgInfoUI.get(1).contains(esgInfoDB.get(1).toString()) &&
                        // esgInfoUI.get(2).contains(esgInfoDB.get(2).toString()) &&  //UI vs DB : Advanced : ESG-3
                        esgInfoUI.get(3).contains(esgInfoDB.get(3).toString())
                ) {
                    found = true;
                   break;
                }
            }
            assertTestCase.assertTrue(found, esgInfoUI.get(0) + " Record Verification: " + found);
        }

        researchLinePage.clickHideButtonInDrillDownPanel();

    }

    @Test(groups = {"regression", "ui", "esg"}, description = "Verify ESG Score Data Validation")
    @Xray(test = {9966})
    public void verifyESGWeightedAverageScoreDataValidationTest() {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToResearchLine("ESG Assessments");
        test.info("Navigated to ESG Assessments Page");

        assertTestCase.assertTrue(researchLinePage.validateIfEsgCardInfoIsDisplayed(), "Verify ESG Card Info with coverage details");

        //ESG Score from UI
        assertTestCase.assertTrue(researchLinePage.esgCardInfoBox.isDisplayed(), "Verify ESG Score Widget is displayed");
        BrowserUtils.wait(10);
        String uiEsgScore = researchLinePage.esgCardInfoBoxScore.getText();

        //ESG Score from DB
        String dbEsgScore = ESGUtilities.getESGPillarsCategory("1015", portfolioQueries.getEsgScoreOfPortfolio());

        assertTestCase.assertEquals(uiEsgScore, dbEsgScore, "Verify ESG Score from DB");

    }
}
