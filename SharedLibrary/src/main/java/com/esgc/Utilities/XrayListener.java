package com.esgc.Utilities;

import com.esgc.APIModels.TestCase;
import com.esgc.TestBase.TestBase;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.esgc.Reporting.CustomAssertion.getScreenshot;

public class XrayListener implements IInvokedMethodListener, ITestListener {

    List<Integer> testCaseList;
    boolean isUITest = false;

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

        if (method.isTestMethod() && annotationPresent(method, Xray.class)) {

            testCaseList = Arrays.stream(method
                            .getTestMethod()
                            .getConstructorOrMethod()
                            .getMethod()
                            .getAnnotation(Xray.class)
                            .test())
                    .boxed().collect(Collectors.toList());
            List<String> groups = Arrays.stream(method
                    .getTestMethod()
                    .getConstructorOrMethod()
                    .getMethod()
                    .getAnnotation(Test.class)
                    .groups()).collect(Collectors.toList());

            isUITest = groups.contains("UI") || groups.contains("ui") || groups.contains("Ui");
           /* testResult.setAttribute(
                    "requirement",
                    method
                            .getTestMethod()
                            .getConstructorOrMethod()
                            .getMethod()
                            .getAnnotation(Xray.class)
                            .requirement());
            testResult.setAttribute(
                    "test",
                    method
                            .getTestMethod()
                            .getConstructorOrMethod()
                            .getMethod()
                            .getAnnotation(Xray.class)
                            .test());
            testResult.setAttribute(
                    "labels",
                    method
                            .getTestMethod()
                            .getConstructorOrMethod()
                            .getMethod()
                            .getAnnotation(Xray.class)
                            .labels());*/
        }
    }


    @Override
    public void onTestFailure(ITestResult arg0) {
        if (testCaseList != null) {
            TestCase testCase = new TestCase();
            testCase.setPassOrFail(false);
            List<String> testCases = testCaseList.stream().map(tcNumber -> "ESGCA-" + tcNumber.toString()).collect(Collectors.toList());
            testCase.setTestCaseNumber(testCases);
            if (isUITest) {
                try {
                    testCase.setFailedScreenShot(getScreenshot(RandomStringUtils.randomAlphanumeric(8)));
                } catch (IOException e) {
                    System.out.println("No Screenshot");
                }
            }
            TestBase.testCasesList.add(testCase);

            testCases.forEach(tc -> {
                TestBase.test.fail("Test case FAILED:" + tc);
            });
            testCaseList = null;
        }
    }

    @Override
    public void onTestSkipped(ITestResult arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTestStart(ITestResult arg0) {

    }

    @Override
    public void onTestSuccess(ITestResult arg0) {
        if (testCaseList != null) {
            TestCase testCase = new TestCase();
            testCase.setPassOrFail(true);
            List<String> testCases = testCaseList.stream().map(tcNumber -> "ESGCA-" + tcNumber.toString()).collect(Collectors.toList());
            testCase.setTestCaseNumber(testCases);
            TestBase.testCasesList.add(testCase);

            testCases.forEach(tc -> {
                TestBase.test.pass("Test case PASSED:" + tc);
            });
            testCaseList = null;
        }
    }

    private boolean annotationPresent(IInvokedMethod method, Class<? extends Annotation> clazz) {
        return method.getTestMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(clazz);
    }
}
