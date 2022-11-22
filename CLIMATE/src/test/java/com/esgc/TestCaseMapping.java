package com.esgc;

import com.esgc.APIModels.TestCase;
import com.esgc.TestBase.TestBase;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

public class TestCaseMapping extends TestBase {

    @Test(priority = 10000, groups = "jira", dataProvider = "testcases")
    public void CanAddNumbers(TestCase tc) {
        ITestResult result = Reporter.getCurrentTestResult();
        result.setAttribute("test", tc.getTestCaseNumber());
        File attachments = null;
        if (tc.getFailedScreenShot() != null) {
            attachments = new File(tc.getFailedScreenShot());
            result.setAttribute("attachments", attachments);
        }

        Assert.assertTrue(tc.getPassOrFail());
    }

    @DataProvider(name = "testcases")
    public Object[][] testProvider() {
        return TestBase.testCasesList.stream()
                .map(testCase -> new Object[]{testCase})
                .toArray(Object[][]::new);
    }

    @DataProvider(name = "rs")
    public Object[][] research() {
        return TestBase.testCasesList.stream()
                .map(testCase -> new Object[]{testCase})
                .toArray(Object[][]::new);
    }
}
