package com.esgc.Tests.UI.EntityClimateProfile;

import com.esgc.Pages.EntityClimateProfilePage;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class EsgMaterialityMatrixTests extends UITestBase {

    @Test(groups = {"entity_climate_profile", "regression", "ui"})
    @Xray(test = {8424})
    public void validateEsgMaterialityMatrixColumns() {
        String company = "Apple, Inc.";
        test.info("Searching and Selecting the company");
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        entityProfilePage.searchAndLoadClimateProfilePage(company);

        entityProfilePage.selectEsgMaterialityTab();
        List<String> expMaterialityMatrixColumns = Arrays.asList(
            "Materiality: Very High",
            "Materiality: High",
            "Materiality: Moderate",
            "Materiality: Low"
        );
        List<String> actualMaterialityMatrixColumns = entityProfilePage.readEsgMaterialityColumns();
        System.out.println("Expected: "+expMaterialityMatrixColumns);
        System.out.println("Actual  : "+actualMaterialityMatrixColumns);
        assertTestCase.assertTrue(actualMaterialityMatrixColumns.equals(expMaterialityMatrixColumns), "Verification of Materiality Matrix Columns");

        assertTestCase.assertTrue(entityProfilePage.verifyMaterialityMatrixYaxisLabels(), "Verification of Materiality Matrix Y-Axis Labels");

        entityProfilePage.clickCloseIcon();
    }

    @Test(groups = {"entity_climate_profile", "regression", "ui"})
    @Xray(test = {8424})
    public void validateLowMaterialityColumnProperties() {
        String company = "Apple, Inc.";
        test.info("Searching and Selecting the company");
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        entityProfilePage.searchAndLoadClimateProfilePage(company);

        entityProfilePage.selectEsgMaterialityTab();
        entityProfilePage.verifyLowMaterialityMatrixColumnProperties();

        entityProfilePage.clickCloseIcon();
    }

    @Test(groups = {"entity_climate_profile", "regression", "ui"})
    @Xray(test = {8427})
    public void validateEsgMaterialityMatrixSubCategoriesProperties() {

        String company = "Pegasystems, Inc.";
        test.info("Searching and Selecting the company");
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName), companyName + " Header Verification");

        entityProfilePage.selectEsgMaterialityTab();
        entityProfilePage.validateSubCategoriesButtonProperties();

        entityProfilePage.clickCloseIcon();
    }

    @Test(groups = {"entity_climate_profile", "regression", "ui"})
    @Xray(test = {8425})
    public void validateEsgMaterialityMatrixOrderAndColors() {

        String company = "Amazon.com, Inc.";
        test.info("Searching and Selecting the company");
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
//        assertTestCase.assertTrue(entityProfilePage.validateGlobalHeader(companyName), companyName + " Header Verification");

        entityProfilePage.selectEsgMaterialityTab();
        entityProfilePage.validateSubCategories();

        entityProfilePage.clickCloseIcon();
    }

    @Test(groups = {"entity_climate_profile", "regression", "ui", "smoke"})
    @Xray(test = {8442})
    public void validateNoneForTheSector() {

        String company = "Sculptor Capital Management, Inc.";
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName), companyName + " Header Verification");

        entityProfilePage.selectEsgMaterialityTab();
        entityProfilePage.validateNoneForTheSectorButton();
        entityProfilePage.clickCloseIcon();
    }

    @Test(groups = {"entity_climate_profile", "regression", "ui"})
    @Xray(test = {8835})
    public void validateAllButtons() {

        String company = "Apple, Inc.";
        test.info("Searching and Selecting the company");
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        entityProfilePage.searchAndLoadClimateProfilePage(company);

        entityProfilePage.selectTransitionRiskTab();
        entityProfilePage.selectPhysicalRiskTab();
        entityProfilePage.selectEsgMaterialityTab();

        entityProfilePage.clickCloseIcon();
    }

    @Test(groups = {"entity_climate_profile", "regression", "ui"})
    @Xray(test = {8841, 8842})
    public void validateMaterialityMatrixLegends() {

        String company = "Apple, Inc.";
        test.info("Searching and Selecting the company");
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        entityProfilePage.searchAndLoadClimateProfilePage(company);

        entityProfilePage.validateEsgMaterialityLegends();

        entityProfilePage.clickCloseIcon();
    }

    @Test(groups = {"entity_climate_profile", "regression", "ui", "smoke"})
    @Xray(test = {8868})
    public void validateEsgMaterialityPopup() {

        String company = "Apple, Inc.";
        test.info("Searching and Selecting the company");
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        entityProfilePage.searchAndLoadClimateProfilePage(company);

        entityProfilePage.selectEsgMaterialityTab();

        assertTestCase.assertTrue(entityProfilePage.validateNoPopupIsDisplayedWhenClickedOnSubCategories(), "Verify popup is not displayed");

        entityProfilePage.clickCloseIcon();
    }
}
