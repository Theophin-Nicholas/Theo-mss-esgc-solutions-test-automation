package com.esgc.Reporting;

import com.esgc.APIModels.TestCase;
import com.esgc.TestBase.TestBase;
import com.esgc.Utilities.Driver;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CustomAssertion extends SoftAssert {

    public static List<Integer> testCaseNumber;
    public static String message = "";

    /**
     * This method is a custom assertion to mark test cases as FAILED
     */
    @Override
    public void onAssertFailure(IAssert assertCommand, AssertionError ex) {
        try {
            if (testCaseNumber != null) {
                TestCase testCase = new TestCase();
                testCase.setPassOrFail(false);
                List<String> testCases = testCaseNumber.stream().map(tcNumber -> "ESGCA-" + tcNumber.toString()).collect(Collectors.toList());
                testCase.setTestCaseNumber(testCases);
                if (TestBase.isUITest) {
                    String screenShot = getScreenshot(RandomStringUtils.randomAlphanumeric(8));
                    testCase.setFailedScreenShot(screenShot);
                    TestBase.test.addScreenCaptureFromPath(screenShot, "Failed");
                }
                TestBase.testCasesList.add(testCase);

                testCases.forEach(tc -> {
                    TestBase.test.fail("Test case FAILED:" + tc);
                });
                testCaseNumber = null;
            }
            TestBase.test.fail(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is a custom assertion to mark test cases as PASSED
     */
    @Override
    public void onAssertSuccess(IAssert assertCommand) {
        try {
            if (testCaseNumber != null) {
                TestCase testCase = new TestCase();
                testCase.setPassOrFail(true);
                List<String> testCases = testCaseNumber.stream().map(tcNumber -> "ESGCA-" + tcNumber.toString()).collect(Collectors.toList());
                testCase.setTestCaseNumber(testCases);
                TestBase.testCasesList.add(testCase);

                testCases.forEach(tc -> {
                    TestBase.test.pass("Test case PASSED:" + tc);
                });
                testCaseNumber = null;
            }
            TestBase.test.pass(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to perform Assertions (PASS/FAIL)
     */
    @Override
    protected void doAssert(IAssert a) {
        onBeforeAssert(a);
        try {
            a.doAssert();
            onAssertSuccess(a);
        } catch (AssertionError ex) {
            onAssertFailure(a, ex);
            throw ex;
        } finally {
            onAfterAssert(a);
        }
    }

    /**
     * This method checks if an assert statement is true for a given condition with Jira Test Case Ticket Numbers
     *
     * @param condition
     * @param ticketNumbers
     */
    public void assertTrue(boolean condition, String comment, Integer... ticketNumbers) {
        testCaseNumber = Arrays.asList(ticketNumbers);
        message = comment;
        super.assertTrue(condition, message);
    }

    /**
     * This method checks if an assert statement is true for a given condition
     *
     * @param condition
     */
    public void assertTrue(boolean condition, String comment) {
        message = comment;
        super.assertTrue(condition, message);
    }

    /**
     * This method checks if an assert statement is equal for a given condition with Jira Test Case Ticket Numbers
     *
     * @param actual
     * @param expected
     * @param ticketNumbers
     */
    public void assertEquals(String actual, String expected, String comment, Integer... ticketNumbers) {
        testCaseNumber = Arrays.asList(ticketNumbers);
        message = comment;
        super.assertEquals(actual, expected, message);
    }

    /**
     * This method checks if an assert statement is equal for a given condition
     *
     * @param actual
     * @param expected
     */
    public void assertEquals(String actual, String expected, String comment) {
        message = comment;
        super.assertEquals(actual, expected, message);
    }

    /**
     * This method checks if an assert statement is equal for a given condition with Jira Test Case Ticket Numbers
     *
     * @param actual
     * @param expected
     * @param ticketNumbers
     */
    public void assertEquals(Integer actual, Integer expected, String comment, Integer... ticketNumbers) {
        testCaseNumber = Arrays.asList(ticketNumbers);
        message = comment;
        super.assertEquals(actual, expected, message);
    }

    /**
     * This method checks if an assert statement is equal for a given condition with Jira Test Case Ticket Numbers
     *
     * @param actual
     * @param expected
     */
    public void assertEquals(Integer actual, Integer expected, String comment) {
        message = comment;
        super.assertEquals(actual, expected, message);
    }

    public void assertEquals(Double actual, Double expected, String comment) {
        message = comment;
        super.assertEquals(actual, expected, message);
    }

    public void assertEquals(Double actual, Double expected, String comment, Integer... ticketNumbers) {
        testCaseNumber = Arrays.asList(ticketNumbers);
        message = comment;
        super.assertEquals(actual, expected, message);
    }


    /**
     * This method checks if an assert statement is equal for a given condition with Jira Test Case Ticket Numbers
     *
     * @param actual
     * @param expected
     * @param ticketNumbers
     */
    public void assertEquals(List<String> actual, List<String> expected, String comment, Integer... ticketNumbers) {
        testCaseNumber = Arrays.asList(ticketNumbers);
        message = comment;
        super.assertEquals(actual, expected, message);
    }

    /**
     * This method checks if an assert statement is equal for a given condition with Jira Test Case Ticket Numbers
     *
     * @param actual
     * @param expected
     */
    public void assertEquals(List<String> actual, List<String> expected, String comment) {
        message = comment;
        super.assertEquals(actual, expected, message);
    }

    /**
     * This method checks if an assert statement is false for a given condition with Jira Test Case Ticket Number
     *
     * @param condition
     * @param ticketNumbers
     */
    public void assertFalse(boolean condition, String comment, Integer... ticketNumbers) {
        testCaseNumber = Arrays.asList(ticketNumbers);
        message = comment;
        super.assertFalse(condition, message);
    }

    /**
     * This method checks if an assert statement is false for a given condition with Jira Test Case Ticket Number
     *
     * @param condition
     */
    public void assertFalse(boolean condition, String comment) {
        message = comment;
        super.assertFalse(condition, message);
    }

    /*
     * takes screenshot
     * @param name
     * take a name of a test and returns a path to screenshot takes
     */
    public static String getScreenshot(String name) throws IOException {
        try {
            // name the screenshot with the current date time to avoid duplicate name
            String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
            // TakesScreenshot ---> interface from selenium which takes screenshots
//        WebDriver driver = new RemoteWebDriver( ... );
//        driver           = new Augmenter().augment( driver );
//        ( (TakesScreenshot)driver ).getScreenshotAs( ... );
            TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
            File source = ts.getScreenshotAs(OutputType.FILE);
            // full path to the screenshot location
            String target = System.getProperty("user.dir") + File.separator + "test-output"
                    + File.separator + "Screenshots" + File.separator + name + date + ".png";
            File finalDestination = new File(target);
            // save the screenshot to the path given
            FileUtils.copyFile(source, finalDestination);
            return target;
        } catch (UnreachableBrowserException e) {
            System.out.println("BROWSER IS NOT OPENED");
            e.printStackTrace();
        }
        return null;
    }

}
