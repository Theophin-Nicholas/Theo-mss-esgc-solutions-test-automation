package com.esgc.Tests.UI.DashboardPage;

import com.esgc.Pages.ResearchLinePage;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class PortfolioSettings extends UITestBase {


    @Test(groups = {"regression", "ui", "smoke"})
    @Xray(test = {9391,9665,9666,9667,9668, 9669,9670,9673,9674})
    public void validatePortfolioSetting_EditPortfolioName (){
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.clickMenu();
        researchLinePage.portfolioSettings.click();
        String originalPortfolioName =researchLinePage.getPortfolioName();
        System.out.println("originalPortfolioName = " + originalPortfolioName);
        researchLinePage.selectPortfolio(originalPortfolioName);
        BrowserUtils.wait(5);

        assertTestCase.assertTrue(researchLinePage.ValidatePortfolioNameFeildIsEditable(),
                "Validate that portfolio name is editable");

        researchLinePage.validatePortfolioNameNotChangedAfterUpdateAndClickOutside(originalPortfolioName);
        researchLinePage.selectPortfolio(originalPortfolioName);
        researchLinePage.validatePortfolioNameChangedAfterUpdateAndClickInsideDrawer(originalPortfolioName);
        researchLinePage.validatePortfolioNameSavedAutomaticallyAfterTwoSecond(originalPortfolioName);
        researchLinePage.validatePortfolioNameRevertbyCtrlZ(originalPortfolioName);
        researchLinePage.validatePortfolioNameUpdatedInAllLocations(originalPortfolioName);
       // researchLinePage.validateblankPortfolioName(originalPortfolioName);

    }

    @Test(groups = {"regression", "ui", "smoke"})
    @Xray(test = {9664})
    public void validatePortfolioSetting_SamplePortfolioNotEditable (){
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.clickMenu();
        researchLinePage.portfolioSettings.click();
        researchLinePage.selectPortfolio("Sample Portfolio");
        assertTestCase.assertTrue(!researchLinePage.ValidatePortfolioNameFeildIsEditable(),
                "Validate that Sample portfolio should not be editble");
    }
}
