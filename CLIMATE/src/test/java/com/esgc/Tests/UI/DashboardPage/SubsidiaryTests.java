package com.esgc.Tests.UI.DashboardPage;

import com.esgc.Pages.DashboardPage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Tests.TestBases.DashboardUITestBase;
import com.esgc.Utilities.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class SubsidiaryTests extends DashboardUITestBase {


    @Test(groups = {"regression", "ui", "smoke", "dashboard"})
    @Xray(test = {11042})
    public void VerifyFileUploadSubsidiaryCompany() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.clickUploadPortfolioButton();
        dashboardPage.clickBrowseFile();
        BrowserUtils.wait(2);

        String inputFile = PortfolioFilePaths.portfolioWithSubsidiaryCompany();
        RobotRunner.selectFileToUpload(inputFile);

        BrowserUtils.wait(4);
    }


}
