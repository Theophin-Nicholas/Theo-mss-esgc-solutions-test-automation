package com.esgc.EntityProfile.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.EntityProfile.UI.Pages.EntityClimateProfilePage;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class EntityReferenceAndMethodologiesTest extends UITestBase {

    @Test(groups = {ENTITY_PROFILE, REGRESSION, UI, ROBOT_DEPENDENCY})
    @Xray(test = {4705, 4660})
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

    @Test(groups = {ENTITY_PROFILE, REGRESSION, UI, ROBOT_DEPENDENCY})
    @Xray(test = {4699})
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

    @Test(groups = {ENTITY_PROFILE, REGRESSION, UI})
    @Xray(test = {4708, 4836,})
    public void validateReferenceAndMethodologyLinks(){

        String company = "Rogers Corp.";
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName),companyName+" Header Verification");

        entityProfilePage.selectReferenceAndMethodologiesTab();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify Reference & Methodologies popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle("Methodologies"), "Verify Popup Title");

    }
}
