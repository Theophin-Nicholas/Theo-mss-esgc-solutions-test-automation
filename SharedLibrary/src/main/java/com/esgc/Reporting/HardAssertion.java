package com.esgc.Reporting;

import com.esgc.TestBase.TestBase;
import com.esgc.Utilities.BrowserUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;



public class HardAssertion extends SoftAssert {
	
	public static String flMessage;
	
	/**
	 * This method is used to FAIL the test case if an assert condition fails and prevents from executing other assert statements in that given testcase
	 * @param condition
	 * @param fMessage
	 */
	public void assertTrue(boolean condition, String fMessage) {
		try {
			flMessage = fMessage;
			if (!condition) {
				TestBase.test.fail(fMessage);
				TestBase.test.addScreenCaptureFromPath(BrowserUtils.getScreenshot(RandomStringUtils.randomAlphanumeric(8)));
			}
			Assert.assertTrue(condition);			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Failed to assertTrue the test condition");
		}
	}
	
	/**
	 * This method is used to FAIL the test case if a condition fails and prevents from executing other assert statements in that given testcase
	 * @param condition
	 * @param fMessage
	 */
	public void assertFalse(boolean condition, String fMessage) {
		try {
			flMessage = fMessage;
			if (condition) {
				TestBase.test.fail(fMessage);
			}
			Assert.assertFalse(condition);			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Failed to assertTrue the test condition");
		}
	}
	
	/**
	 * This method is used to FAIL the test case if an assert condition fails and prevents from executing other assert statements in that given testcase
	 * @param actualResult
	 * @param expectedResult
	 * @param fMessage
	 */
	public void assertEquals(String actualResult, String expectedResult, String fMessage) {
		try {
			flMessage = fMessage;
			if (!actualResult.equals(expectedResult)) {
				TestBase.test.fail(fMessage);
			}
			Assert.assertEquals(actualResult, expectedResult, fMessage);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Failed to assertTrue the test condition");
		}
	}

}
