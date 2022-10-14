package com.esgc.Test.UI.P3PageTests;

import com.esgc.APIModels.EntityIssuerPage.ESGCategories;
import com.esgc.APIModels.EntityIssuerPage.SubCategories;
import com.esgc.Controllers.EntityIssuerPageController.EntityIssuerPageAPIController;
import com.esgc.Pages.EntityIssuerPage;
import com.esgc.Pages.LoginPageIssuer;
import com.esgc.Test.TestBases.EntityPageTestBase;

import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EsgMaterialityMatrixTests extends EntityPageTestBase {


    @Test(groups = {"entity_Issuer_profile", "regression", "ui"} ,dataProvider = "ESGMaterialitycredentials11",dataProviderClass = DataProviderClass.class)
    @Xray(test = {9884,9921})
    public void validateESGMaterialityMatrixHeader(String... dataProvider) {
        String userId= dataProvider[0].toString();
        String password=dataProvider[1].toString();
        LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
        LoginPageIssuer.loginWithParams(userId,password);
        assertTestCase.assertTrue(EntityIssuerPage.esgMaterialityTab.isDisplayed(),"Validate ESG Materiality Tab availability");
        EntityIssuerPage entityIssuerPage = new EntityIssuerPage();

        entityIssuerPage.validateEsgMaterialityLegends();


    }

    @Test(groups = {"entity_climate_profile", "regression", "ui"},dataProvider = "ESGMaterialitycredentials11",dataProviderClass = DataProviderClass.class)
    @Xray(test = {9924})
    public void validateEsgMaterialityMatrixColumns(String... dataProvider) {
        String userId= dataProvider[0].toString();
        String password=dataProvider[1].toString();
        LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
        LoginPageIssuer.loginWithParams(userId,password);
        EntityIssuerPage entityIssuerpage = new EntityIssuerPage();


        entityIssuerpage.selectEsgMaterialityTab();
        List<String> expMaterialityMatrixColumns = Arrays.asList(
                "Materiality: Very High",
                "Materiality: High",
                "Materiality: Moderate",
                "Materiality: Low"
        );
        List<String> actualMaterialityMatrixColumns = entityIssuerpage.readEsgMaterialityColumns();
        System.out.println("Expected: "+expMaterialityMatrixColumns);
        System.out.println("Actual  : "+actualMaterialityMatrixColumns);
        assertTestCase.assertTrue(actualMaterialityMatrixColumns.equals(expMaterialityMatrixColumns), "Verification of Materiality Matrix Columns");

        assertTestCase.assertTrue(entityIssuerpage.verifyMaterialityMatrixYaxisLabels(), "Verification of Materiality Matrix Y-Axis Labels");


    }


    @Test(groups = {"esg_materiality", "regression", "ui"},dataProvider = "ESGMaterialitycredentials11",dataProviderClass = DataProviderClass.class)
    @Xray(test = {9925})
    public void validateEsgMaterialityFooter(String... dataProvider) {

        String userId= dataProvider[0].toString();
        String password=dataProvider[1].toString();
        LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
        LoginPageIssuer.loginWithParams(userId,password);
        EntityIssuerPage entityIssuerpage = new EntityIssuerPage();
        BrowserUtils.scrollTo(entityIssuerpage.esgMaterialityTab);
        entityIssuerpage.selectEsgMaterialityTab();

        List<String> methodologies = Arrays.asList(new String[] {"Double Materiality","Business Materiality","Stakeholder Materiality","ESG","Environmental","Social","Governance"});
        for(String e : methodologies){
            assertTestCase.assertTrue(entityIssuerpage.isProvidedFilterClickableInMaterialityMatrixFooter(e), "Verify " + e +"filter is clickable in Materiality Matrix Footer");
            if (e.equals("Double Materiality") || e.equals("ESG")){
                assertTestCase.assertTrue(entityIssuerpage.isProvidedFilterSelectedByDefaultInMaterialityMatrixFooter(e), "Verify" + e +"filter is selected as default in Materiality Matrix Footer");
            }
            else{
                assertTestCase.assertTrue(!entityIssuerpage.isProvidedFilterSelectedByDefaultInMaterialityMatrixFooter(e), "Verify" + e +"filter is not selected in Materiality Matrix Footer");
            }

        }

        //validate Sub Categories based on methodology filters
        EntityIssuerPageAPIController controller = new EntityIssuerPageAPIController();
        List<ESGCategories> ExpectedCategories = new ArrayList<>();
        for(String e : methodologies){
            if(e.equals(e.equals("Environmental") || e.equals("Social") || e.equals("Governance"))){
                entityIssuerpage.selectMaterialityMatrixFilter(e);
                List<String> uiEnvironmentalCategories = entityIssuerpage.readEsgMaterialityCategories();
                List<String> expectedValues = controller.getESGSubCategories(e).get(0).getSubcategories().stream().map(SubCategories::getCategory).collect(Collectors.toList());
                assertTestCase.assertTrue(expectedValues.stream().filter(element -> uiEnvironmentalCategories.contains(element)).findFirst().isPresent(), "Validating Sub category filter for " + e + " Methodology");
            }
        }




    }

    @Test(groups = {"esg_materiality", "regression", "ui"},dataProvider = "ESGMaterialitycredentials11",dataProviderClass = DataProviderClass.class)
    @Xray(test = {9926})
    public void validateEsgMaterialitySubCategoryModel(String... dataProvider) {
        String userId= dataProvider[0].toString();
        String password=dataProvider[1].toString();
        LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
        LoginPageIssuer.loginWithParams(userId,password);
        EntityIssuerPage entityIssuerpage = new EntityIssuerPage();
        BrowserUtils.scrollTo(entityIssuerpage.esgMaterialityTab);
        entityIssuerpage.selectEsgMaterialityTab();
        entityIssuerpage.validateSubCategoryModal();
    }

}
