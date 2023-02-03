package com.esgc.TestBase;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.esgc.APIModels.TestCase;
import com.esgc.Reporting.CustomAssertion;
import com.esgc.Utilities.*;
import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.requestSpecification;


public abstract class TestBase {

    protected static ExtentReports report;
    public static ExtentTest test;
    public static List<TestCase> testCasesList = new ArrayList<>();
    private String accessToken;
    public CustomAssertion assertTestCase;
    public static boolean isUITest = false;
    public static boolean isAPITest = false;
    public static boolean isDataValidationTest = false;
    public static boolean isBrowserOpen = false;
    public static StopWatch stopWatch = new StopWatch();
    public static String reportPath = System.getProperty("user.dir") + File.separator + "test-output";
    public static boolean isRemote = false;

    @BeforeTest(alwaysRun = true)
    @Parameters("reportName")
    public void setupTestBeforeExecution(@Optional String reportName) {
        //Check if execution is in remote environment or local?
        if (System.getProperty("browser") != null) {
            isRemote = System.getProperty("browser").contains("remote");
        } else {
            isRemote = ConfigurationReader.getProperty("browser").contains("remote");
        }

        System.out.println("Report name: " + reportName);
        reportName = reportName == null ? "report.html" : reportName + ".html";

        //Create a report path
        String reportPath = System.getProperty("user.dir") + File.separator + "test-output" + File.separator + reportName;

        //initialize the class
        report = new ExtentReports();

        //initialize the HTML reporter with the report path
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

        //attach the html report to report object
        report.attachReporter(spark);

        //Title in report
        spark.config().setReportName("ESG&C UI Test Automation Results");

        // Optional but nice to have - System information for HTML report
        report.setSystemInfo("Environment", Environment.URL);
        report.setSystemInfo("Browser", System.getProperty("browser") == null ? ConfigurationReader.getProperty("browser") : System.getProperty("browser"));
        report.setSystemInfo("OS", System.getProperty("os.name"));
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethodCreateReport(Method method) {
        test = report.createTest(method.getName());
        assertTestCase = new CustomAssertion();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClassAfterExecutionsAreDone() {
        Driver.closeDriver();
        requestSpecification = null;
        isUITest = false;
        isAPITest = false;
        isDataValidationTest = false;
        isBrowserOpen = false;
    }

    @AfterTest(alwaysRun = true)
    public void tearDownAndGenerateReport() {
        report.flush();
    }

    @AfterSuite(alwaysRun = true)
    @Parameters("reportName")
    public void uploadResultsToJira(@Optional String reportName) {
        Driver.closeDriver();
        System.out.println("#########################################3");
        System.out.println("Test Cases:");
        testCasesList.forEach(System.out::println);
        System.out.println("reportName = " + reportName);
        if (reportName == null) return;
        if (reportName.contains("Smoke") || reportName.contains("Regression")) {
            System.out.println("Results should be sent to Jira");
            XrayFileImporter.sendExecutionResultsToXray(reportName, testCasesList);
        }
    }

    public void getScreenshot(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.assignCategory(result.getInstanceName());
            test.log(Status.FAIL, "Classname: " + result.getTestClass());
            test.log(Status.FAIL, MarkupHelper.createLabel("FAILED test case name is: " + result.getName(), ExtentColor.RED));
            Reporter.log("Failed Report ", true);
            test.log(Status.FAIL, MarkupHelper.createLabel("Testcase FAILED due to below issues: ", ExtentColor.RED));
            if (result.getTestClass().getName().contains("UI")) {
                String screenshotPath = BrowserUtils.getScreenshot(result.getName());
                if (screenshotPath != null) test.addScreenCaptureFromPath(screenshotPath, "Failed");
            }
            test.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "TestClass: " + result.getTestClass().getName());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "TestCaseSKIPPED is " + result.getName());
        }
    }

    public void getExistingUsersAccessTokenFromUI() {
        System.out.println("getting token");
        String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.accessToken";
        String accessToken = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
        System.setProperty("token", accessToken);
        //System.out.println("token = " + accessToken);
    }

}
