package com.esgc.Tests.UI.EntityClimateProfile;

import com.esgc.Pages.EntityClimateProfilePage;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntityReferenceAndMethodologiesTest extends UITestBase {

    @Test(groups = {"entity_climate_profile", "regression", "ui", "robot_dependency"})
    @Xray(test = {8348})
    public void validateReferenceAndMethodologyPopup(){

        String company = "Rogers Corp.";
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName),companyName+" Header Verification");

        entityProfilePage.scrollToBottomOfPage();
        assertTestCase.assertTrue(entityProfilePage.verifyMethodologiesTabIsDisplayed(),"Verify Reference & Methodologies tab is in sticky notes");

        entityProfilePage.selectReferenceAndMethodologiesTab();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify References & Methodologies popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle("Methodologies"), "Verify Popup Title");

        entityProfilePage.closePopup();
    }

    @Test(groups = {"entity_climate_profile", "regression", "ui", "robot_dependency"})
    @Xray(test = {8349})
    public void validateReferenceAndMethodologyPopupClose(){

        String company = "Rogers Corp.";
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName),companyName+" Header Verification");

        entityProfilePage.selectReferenceAndMethodologiesTab();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify References & Methodologies popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle("Methodologies"), "Verify Popup Title");

        entityProfilePage.closePopup();
        assertTestCase.assertTrue(!entityProfilePage.verifyPopup(), "Verify popup is closed");

        entityProfilePage.selectReferenceAndMethodologiesTab();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify References & Methodologies popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle("Methodologies"), "Verify Popup Title");

        entityProfilePage.pressESCKey();
        assertTestCase.assertTrue(!entityProfilePage.verifyPopup(), "Verify popup is closed");

        entityProfilePage.selectReferenceAndMethodologiesTab();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify References & Methodologies popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle("Methodologies"), "Verify Popup Title");

        entityProfilePage.clickOutsideOfPopup();
        assertTestCase.assertTrue(!entityProfilePage.verifyPopup(), "Verify popup is closed");

    }

    @Test(groups = {"entity_climate_profile", "regression", "ui"})
    @Xray(test = {8350, 8891,})
    public void validateReferenceAndMethodologyLinks(){

        String company = "Rogers Corp.";
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName),companyName+" Header Verification");

        entityProfilePage.selectReferenceAndMethodologiesTab();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify Reference & Methodologies popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle("Methodologies"), "Verify Popup Title");
        List<String> actualMethodologies = entityProfilePage.getMethodologiesLinks();
        List<String> expectedMethodologies = new ArrayList<>();
        expectedMethodologies.add("ESG Assessment Methodology 1.0");
        expectedMethodologies.add("ESG Assessment Methodology 2.0");
        expectedMethodologies.add("Controversy Risk Assessment Methodology");
        expectedMethodologies.add("ESG Assessment Subcategory Definitions");
        expectedMethodologies.add("ESG Assessment Metrics Definitions");
        Collections.sort(actualMethodologies);
        Collections.sort(expectedMethodologies);
        assertTestCase.assertTrue(actualMethodologies.equals(expectedMethodologies), "Verify Methodology links");
        assertTestCase.assertTrue(entityProfilePage.downloadMethodologyDocument(), "Verify Reference & Methodologies Document is downloaded");
    }
}
