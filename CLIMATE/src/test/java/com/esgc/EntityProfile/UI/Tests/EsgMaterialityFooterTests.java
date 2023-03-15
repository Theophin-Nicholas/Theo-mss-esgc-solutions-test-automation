package com.esgc.EntityProfile.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.EntityProfile.UI.Pages.EntityClimateProfilePage;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.esgc.Utilities.Groups.*;

public class EsgMaterialityFooterTests extends UITestBase {

    @Test(groups = {"esg_materiality", REGRESSION, UI})
    @Xray(test = {8368, 8370})
    public void validateEsgMaterialityFooter() {

        String company = "Sculptor Capital Management, Inc.";
        test.info("Searching and Selecting the company");
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName), companyName + " Header Verification");
        test.info("Searched and selected the company: " + companyName);

        entityProfilePage.selectEsgMaterialityTab();

        // Verify view by filter options are clickable
        assertTestCase.assertTrue(entityProfilePage.isProvidedFilterClickableInMaterialityMatrixFooter("Double Materiality"), "Verify filter is clickable in Materiality Matrix Footer");
        assertTestCase.assertTrue(entityProfilePage.isProvidedFilterClickableInMaterialityMatrixFooter("Business Materiality"), "Verify filter is clickable in Materiality Matrix Footer");
        assertTestCase.assertTrue(entityProfilePage.isProvidedFilterClickableInMaterialityMatrixFooter("Stakeholder Materiality"), "Verify filter is clickable in Materiality Matrix Footer");
        assertTestCase.assertTrue(entityProfilePage.isProvidedFilterClickableInMaterialityMatrixFooter("ESG"), "Verify filter is clickable in Materiality Matrix Footer");
        assertTestCase.assertTrue(entityProfilePage.isProvidedFilterClickableInMaterialityMatrixFooter("Environmental"), "Verify filter is clickable in Materiality Matrix Footer");
        assertTestCase.assertTrue(entityProfilePage.isProvidedFilterClickableInMaterialityMatrixFooter("Social"), "Verify filter is clickable in Materiality Matrix Footer");
        assertTestCase.assertTrue(entityProfilePage.isProvidedFilterClickableInMaterialityMatrixFooter("Governance"), "Verify filter is clickable in Materiality Matrix Footer");

        // Verify default selected view by filter
        assertTestCase.assertTrue(entityProfilePage.isProvidedFilterSelectedByDefaultInMaterialityMatrixFooter("Double Materiality"), "Verify default selected filter in Materiality Matrix Footer");
        assertTestCase.assertTrue(!entityProfilePage.isProvidedFilterSelectedByDefaultInMaterialityMatrixFooter("Business Materiality"), "Verify default selected filter in Materiality Matrix Footer");
        assertTestCase.assertTrue(!entityProfilePage.isProvidedFilterSelectedByDefaultInMaterialityMatrixFooter("Stakeholder Materiality"), "Verify default selected filter in Materiality Matrix Footer");
        assertTestCase.assertTrue(entityProfilePage.isProvidedFilterSelectedByDefaultInMaterialityMatrixFooter("ESG"), "Verify default selected filter in Materiality Matrix Footer");
        assertTestCase.assertTrue(!entityProfilePage.isProvidedFilterSelectedByDefaultInMaterialityMatrixFooter("Environmental"), "Verify default selected filter in Materiality Matrix Footer");
        assertTestCase.assertTrue(!entityProfilePage.isProvidedFilterSelectedByDefaultInMaterialityMatrixFooter("Social"), "Verify default selected filter in Materiality Matrix Footer");
        assertTestCase.assertTrue(!entityProfilePage.isProvidedFilterSelectedByDefaultInMaterialityMatrixFooter("Governance"), "Verify default selected filter in Materiality Matrix Footer");


        entityProfilePage.clickCloseIcon();
    }

    @Test(groups = {"esg_materiality", REGRESSION, UI})
    @Xray(test = {8367})
    public void validateEsgMaterialityFooterLegacyVE() {

        String company = "Apple, Inc.";
        test.info("Searching and Selecting the company");
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName), companyName + " Header Verification");
        test.info("Searched and selected the company: " + companyName);

        entityProfilePage.selectEsgMaterialityTab();

        // Verify view by filter options are clickable
        assertTestCase.assertTrue(entityProfilePage.isProvidedFilterClickableInMaterialityMatrixFooter("Double Materiality"), "Verify filter is clickable in Materiality Matrix Footer");
        assertTestCase.assertTrue(!entityProfilePage.isProvidedFilterClickableInMaterialityMatrixFooter("Business Materiality"), "Verify filter is clickable in Materiality Matrix Footer");
        assertTestCase.assertTrue(!entityProfilePage.isProvidedFilterClickableInMaterialityMatrixFooter("Stakeholder Materiality"), "Verify filter is clickable in Materiality Matrix Footer");
        assertTestCase.assertTrue(entityProfilePage.isProvidedFilterClickableInMaterialityMatrixFooter("ESG"), "Verify filter is clickable in Materiality Matrix Footer");
        assertTestCase.assertTrue(entityProfilePage.isProvidedFilterClickableInMaterialityMatrixFooter("Environmental"), "Verify filter is clickable in Materiality Matrix Footer");
        assertTestCase.assertTrue(entityProfilePage.isProvidedFilterClickableInMaterialityMatrixFooter("Social"), "Verify filter is clickable in Materiality Matrix Footer");
        assertTestCase.assertTrue(entityProfilePage.isProvidedFilterClickableInMaterialityMatrixFooter("Governance"), "Verify filter is clickable in Materiality Matrix Footer");

        entityProfilePage.clickCloseIcon();
    }

    @Test(groups = {"esg_materiality", REGRESSION, UI, ESG})
    @Xray(test = {8369})
    public void validateEsgMaterialityMatrixViewByTests() {

        String company = "Sculptor Capital Management, Inc.";
        test.info("Searching and Selecting the company");
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName), companyName + " Header Verification");
        test.info("Searched and selected the company: " + companyName);

        entityProfilePage.selectEsgMaterialityTab();
        List<String> uiEsgMaterialityCategories = entityProfilePage.readEsgMaterialityCategories();

        // Verify Environmental Categories
        entityProfilePage.selectMaterialityMatrixFilter("Environmental");
        List<String> uiEnvironmentalCategories = entityProfilePage.readEsgMaterialityCategories();
        assertTestCase.assertTrue(validateEnvironmentalCategories(uiEnvironmentalCategories), "Verify Environmental Categories");

        // Verify Governance Categories
        entityProfilePage.selectMaterialityMatrixFilter("Governance");
        List<String> uiGovernanceCategories = entityProfilePage.readEsgMaterialityCategories();
        assertTestCase.assertTrue(validateGovernanceCategories(uiGovernanceCategories), "Verify Governance Categories");

        // Verify Social Categories
        entityProfilePage.selectMaterialityMatrixFilter("Social");
        List<String> uiSocialCategories = entityProfilePage.readEsgMaterialityCategories();
        assertTestCase.assertTrue(validateSocialCategories(uiSocialCategories), "Verify Social Categories");

        // Verify ESG Categories
        entityProfilePage.selectMaterialityMatrixFilter("ESG");
        List<String> uiEsgCategories = entityProfilePage.readEsgMaterialityCategories();
        assertTestCase.assertTrue(compareCategories(uiEsgMaterialityCategories, uiEsgCategories), "Verify ESG Categories");

        entityProfilePage.clickCloseIcon();

        // Verify Categories are reset when selected another company
        String company2 = "Apple, Inc.";
        test.info("Searching and Selecting the company");
        String companyName2 = entityProfilePage.searchAndLoadClimateProfilePage(company2);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName2), companyName2 + " Header Verification");
        test.info("Searched and selected the company: " + companyName2);

        entityProfilePage.selectEsgMaterialityTab();
        List<String> uiEsgMaterialityCategories2 = entityProfilePage.readEsgMaterialityCategories();

        assertTestCase.assertFalse(compareCategories(uiEsgMaterialityCategories, uiEsgMaterialityCategories2), "Compare 2 companies categories");

        entityProfilePage.clickCloseIcon();
    }

    public boolean validateEnvironmentalCategories(List<String> actualCategories){
        ArrayList<String> expectedCategories = new ArrayList<>();
        expectedCategories.add("Biodiversity");
        expectedCategories.add("Transition Risks");
        expectedCategories.add("Physical Risks");
        expectedCategories.add("Environmental Protection");
        expectedCategories.add("Water Management");
        expectedCategories.add("Air Emissions");
        expectedCategories.add("Material Flows");

        for(String category: actualCategories){
            System.out.println("Verify environmental category: "+category);
            if(!expectedCategories.contains(category))
                return false;
        }
        return true;
    }

    public boolean validateGovernanceCategories(List<String> actualCategories){
        ArrayList<String> expectedCategories = new ArrayList<>();
        expectedCategories.add("Internal Controls & Risk Management");
        expectedCategories.add("Business Ethics");
        expectedCategories.add("Lobbying");
        expectedCategories.add("Board");
        expectedCategories.add("Executive Remuneration");
        expectedCategories.add("Stakeholder Relations");
        expectedCategories.add("Cyber & Technological Risks");
        expectedCategories.add("Competition");
        expectedCategories.add("Sustainable Sourcing");
        expectedCategories.add("Supplier Relations");

        for(String category: actualCategories){
            System.out.println("Verify environmental category: "+category);
            if(!expectedCategories.contains(category))
                return false;
        }
        return true;
    }

    public boolean validateSocialCategories(List<String> actualCategories){
        ArrayList<String> expectedCategories = new ArrayList<>();
        expectedCategories.add("Customer Engagement");
        expectedCategories.add("Fundamental Human Rights");
        expectedCategories.add("Diversity & Inclusion");
        expectedCategories.add("Health & Safety");
        expectedCategories.add("Social & Economic Development");
        expectedCategories.add("Career Development");
        expectedCategories.add("Responsible Tax");
        expectedCategories.add("Societal Impacts");
        expectedCategories.add("Reorganizations");
        expectedCategories.add("Wages & Hours");
        expectedCategories.add("Labour Rights & Relations");
        expectedCategories.add("Modern Slavery");
        expectedCategories.add("Product Safety");

        for(String category: actualCategories){
            System.out.println("Verify environmental category: "+category);
            if(!expectedCategories.contains(category))
                return false;
        }
        return true;
    }

    public boolean compareCategories(List<String> categories1, List<String> categories2){

        System.out.println(categories1);
        System.out.println(categories2);

        if(categories1.size()!=categories2.size())
            return false;

        for(int i=0; i<categories1.size(); i++){
            System.out.println("Verification of Category: "+categories1.get(i));
            if(!categories1.get(i).equals(categories2.get(i)))
                return false;
        }
        return true;
    }
}
