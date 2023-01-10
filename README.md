# Introduction

ESG&C is a platform that provides an integrated climate risk solution offering to the broad market of financial services
institutions. For more details: [What is ESG&CA?](https://esconfluence/pages/viewpage.action?pageId=52396160)

Framework provides test automation process for ESG&C Customer UI + API + Data Validation.

# Testing Tools

- IntelliJ IDE
- Java
- TestNG
- Maven
- Selenium Webdriver (For easy interaction with web browser)
- Apache POI to perform operations with excel like read, write and update excel sheet
- aventstack extend report dependency for HTML reporting
- Jira XRAY

# Concepts Included

- Parallel test runs
- Page Object pattern
- Common web page interaction methods
- Singleton Design pattern
- Page Factory Design pattern
- Utilities package for common methods across the framework

# Automation Framework Details

- Test Automation Framework is created as a Maven project to manage dependencies, store information and run the tests.
- TestNG with Selenium is used as a test automation framework which supports DDT (Data Driven Testing).
- Java is used as a programing language.
- POM (Page Object Model) design pattern is used to reduce code duplication and improve test maintenance. Pages packages
  are created to store abstract BasePage class which contains all the common WebElements and functionalities of the
  application. For each web page in the application, there is a corresponding Page Class. This Page class will identify
  the WebElements of that specific web page and contains Page methods which perform operations on these WebElements. All
  Page Classes extends BasePage class to use the elements and methods that are common to all Page Classes of the
  application.
- Page Factory Design Pattern is implemented to simplify the process of creating WebElements which is initialized in
  BasePage class. FindBy() annotation is used to store WebElements in Page Classes.
- A separate class for Driver (In 'utilities' package) is created, and it is designed in 'Singleton' manner to make sure
  that only one instance of WebDriver has been used across the project. “public static WebDriver getDriver()” method
  allows accessing WebDriver in any place of the project.
- The framework generates detailed HTML report which is easy to read and understand for non-technical team members. The
  reports have detailed information regarding test steps, screenshots for any failures that might occur. It can also
  provide a matrix on what percentage is passing, failing, and skipping.
- Smoke and Regression Test execution results are sharing in Jira Xray and #platform-ui-smoke-test-execution Slack
  Channel

# Testing Strategy

- Existing functionalities will be validated as a part of regression tests.
- New functionalities will be validated to make sure that they function as per the requirements.
- Fixed defects will be validated.

# Maven Commands to run the tests on Terminal

Running all the unit test classes command

```
mvn test
```
>(For a shorter test execution duration, please use the command below):

Running a single test with Maven command: (mvn test -Dtest=className)

```
    mvn test -Dtest=GlobalHeaderSidePanel
```

Running multiple test with Maven command: (mvn test -Dtest=className1, className2...)

```
    mvn test -Dtest=GlobalHeaderSidePanel, DashboardSummaryHeader
```

Running a profile which is created on POM.xml file: (mvn test -PprofileName)

```
    mvn test -Pregression  
    mvn test -Psmoke
    mvn clean test -Pregression
```

    (Make sure to set right path on Profile on Pom.xm- For eg.: Path of smoke_tests.xml file (right click + Copy Path + Path from Content Root)

Running test suite with parameters command:

```
mvn test -Denvironment=prod -Dbrowser=safari
```

Clearing target directory:

```
    mvn clean
```

# For Automation Testers: How to Write Test Scripts

### UI Automation Steps:
1. Create Web Elements in Page Classes (like DashboardPage.class) under **Pages** package
2. Create Methods to take action with web elements under page classes( For example clickOnUploadButton, navigateToResearchLine, isPortfolioScoreDisplayed methods)
3. Create Test scripts under **Tests > UI** package and add assertions (for assertion approach please see [Custom Assertion](#custom-assertion-assertTestCase) section)

### API Automation Steps:
1. Create Pojo for API Response under **APIModels** package (use existing ones if available)
2. Add endpoint to corresponding Endpoint class under **Utilities > API** package
3. Create controller method to send request to endpoint and get a response under **Controllers** package
4. Create Test Script under **Tests > API** package to check api response fields

### Data Validation:

>(first 3 steps are same for API so if you already covered them you can go to step 4)

1. Create Pojo for API Response under **APIModels** package (use existing ones if available)
2. Add endpoint to corresponding Endpoint class under **Utilities > API** package
3. Create controller method to send request to endpoint and get a response under **Controllers** package
4. Get query to fetch data from snowflake (will be used in db connection to get data)
5. Create dbModel class to serialize db results to Java Object under **DBModels** class
6. Create method to get data from snowflake with that query and serialize to dbModel (in ***Queries classes) under **Utilities > Database** package
7. Create test scripts and make calculations/validations by assertions

## Test Scripts should be included for all tests:

1. Add related groups into @Test annotation (defined groups are in [Defined Groups](#defined-groups:) section)
2. Pass Data Provider method if needed (for example same flow with different research lines)

For Example(for step 1 and 2):

Test Annotation for test scripts
```
@Test(groups = {SMOKE, REGRESSION, UI, ISSUER}, dataProvider = "researchLines")
```

3. Add **@XRay** annotation to map test cases which are covering in the test flow

For Example:

Xray Annotation to Map Test Cases

```
@Xray(test = { 2704, 2703, 2558, 2557, 2188, 2189, 2559})
```

4. Use custom assertions in test scripts

### Custom Assertion: assertTestCase

* use **assertTestCase** object for assertions
* Add comment to pass that comment as log in test report
* (optional and for very specific use cases) you can pass test cases into custom assertion if needed

For Example:
* without mapped test cases:

custom assertion usage with comment
```
assertTestCase.assertEquals(actualPercentage, expectedPercentage, "Validating investment pct" );
```
 * parameterized comment:

custom assertion usage with parameterized comment

```
assertTestCase.assertEquals(actualPercentage, expectedPercentage, "Validating investment pct for: " + portfolioDistribution.getCategory());
```

* with mapped test cases

custom assertion usage with parametrized comment + mapped test cases
```
assertTestCase.assertEquals(actualPercentage, expectedPercentage, "Validating investment pct for: " + portfolioDistribution.getCategory(), 2704, 2703, 2558, 2557, 2188, 2189, 2559);
```






# Smoke / Regression XML Runner Configurations

### 1- XrayListener

Add XrayListener to get mapped Test Cases with @XRAY annotation

```
<listeners>
        <listener class-name="com.esgc.Utilities.XrayListener"/>
</listeners>
```

### 2- ReportName

Add reportName parameter to get HTML report with given name

- If reportName is not defined then default report name is report.html
- If you report name has "Smoke" or "Regression" then test execution results are going into Jira Xray as well as sharing
  in Slack Channel e.g: (value contains Smoke)

```
<parameter name="reportName" value="April 2022 Point Release Smoke Test Automation Report"/> 
```

### 3- Groups

Add which groups you want to test

#### Defined Groups:

- <b>regression</b> is for all test cases
- <b>smoke</b>  is for smoke test cases
- <b>ui</b>  is for UI related test cases
- <b>api</b>  is for API related test cases. For API tests we get token from UI.
- <b>data_validation</b> is for data validation, calculations, all data related test cases.
- <b>errorMessages</b> is for error message test cases which we see while importing portfolios
- <b>dashboard</b>  is for dashboard related test cases
- <b>entitlements</b>  is for test cases with different entitlements
- <b>robot_dependency</b> is for import portfolio functionality test with browser dialog. We have some scripts to handle
  the dialog so we are depended on Robot Class.
- <b>export</b> is for export functionality test cases
- <b>entity_issuer</b>  is for issuer page test cases
- <b>entity_climate_profile</b>  if for entity climate profile page test cases

eg:Run smoke test cases only for dashboard and do not execute entity_climate_profile test cases and import portfolio
functionality which are depend on Robot Class

```
<groups>
    <run>
        <include name="smoke"/>
        <include name="dashboard"/>
        <exclude name="entity_climate_profile"/>
        <exclude name="robot_dependency"/>
    </run>
</groups>
```

### 4- Package

You need to define which packages you want to use while getting Test Classes. In this case you can have better filtering
for more specific executions. For example if you want to execute smoke in dashboard and if you include smoke group and
dashboard group, automation will run all smoke labeled test cases and all dashboard labelled test cases. However, we
need to find test cases which are labeled both smoke and dashboard. In this case we need to define Dashboard package so
that execution will be work on only dashboard test cases.

e.g.:Execute Dashboard UI and API test cases

```
<packages>
  <package name="com.esgc.Tests.UI.DashboardPage.*"/>
  <package name="com.esgc.Tests.UI.API.*"/>
</packages>
```

### 5- Environment

You need to configure environment in <b>configuration.properties</b> file.

#### Defined Environments:

- <b>qa</b> = https://solutions-qa.mra-esg-nprd.aws.moodys.tld/
- <b>qa2</b> = https://solutions-qa2.mra-esg-nprd.aws.moodys.tld/
- <b>uat</b> = https://solutions-uat.mra-esg-nprd.aws.moodys.tld/
- <b>prod</b> = https://esg.moodys.com/

### 6- Browser

You can add browser as parameter in XML file or you can configure browser in <b>configuration.properties</b> file. e.g.

```
<parameter name="browser" value="chrome"/>
```

#### Defined Browsers:

- <b>chrome</b>
- <b>edge</b>
- <b>safari</b>

# Requirements:

1- Install JDK Terminal command to check the version:
Java -version

2- Download Maven

- Run this command to check whether maven is installed: mvn -version

To download dependencies on POM file:
** For the project all the required dependencies are added on pom.xm file, but for future reference:

- Go to:
  https://mvnrepository.com/
- And search for the dependency that you want to download and get the latest version (don’t download the ‘alpha’
  version).
- Copy the dependency under ‘Maven’, and add to the pom.xml file between: "<dependencies>
  <dependencies>" tags on pom.xml

e.g.:
<dependency>
<groupId>org.seleniumhq.selenium</groupId>
<artifactId>selenium-java</artifactId>
<version>4.6.0</version>
</dependency>

