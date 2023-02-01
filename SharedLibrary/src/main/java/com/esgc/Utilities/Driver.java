package com.esgc.Utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Driver {
  /*
    Singleton Design Pattern: No one can create object of the Driver class.
    Everyone should call static getter method instead.
    We need to ensure that there is only one single Webdriver object.
    It is static, so we can ensure that all tests use the same driver obj. So we can create test suits.
     */

    /*private static final ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();
     In order WebDriver to support parallel testing we use ThreadLocal Java class.
     ThreadLocal allows to create a copy of the object at the runtime for every thread.
     Also we need to make getDriver method synchronized to prevent a crash.
     @DataProvider(parallel =true)-> to execute all tests in parallel data provider tests
   */

    private static final ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();

    public static FirefoxProfile firefoxProfile() {

        //Added for download template settings to change the file location
        FirefoxProfile firefoxProfile = new FirefoxProfile();

        try {
            //download directory path
            firefoxProfile.setPreference("browser.download.folderList", 2);
            firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
            firefoxProfile.setPreference("browser.download.dir", BrowserUtils.downloadPath());
            firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                    "text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
        } catch (Exception e) {

            System.out.println("Profile can't be configured for Firefox.");
        }
        ;
        return firefoxProfile;
    }

    //private constructor prevents creating new object.
    private Driver() {

    }

    /**
     * synchronized keyword makes a method thread safe.
     * It ensures that only one thread can use it at a time.
     * Thread safety reduces performance but it makes everything safe.
     */
    public synchronized static WebDriver getDriver() {

        if (driverPool.get() == null) {
            //if webdriver object doesn't exist //create it
            String browser = ConfigurationReader.getProperty("browser").toLowerCase();
            // -Dbrowser=firefox
            if (System.getProperty("browser") != null) {
                browser = System.getProperty("browser");
            }
            String hubURL = ConfigurationReader.getProperty("hub");
            if (System.getProperty("hub_url")!=null){
                hubURL=System.getProperty("hub_url");
                browser=browser+"-remote";
            }
            switch (browser) {
                case "chrome-remote":
                    try {
                        URL url = new URL(hubURL);
                        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                        desiredCapabilities.setBrowserName(Browser.CHROME.browserName());
                        desiredCapabilities.setPlatform(Platform.ANY);

                        ChromeOptions chromeOptions = new ChromeOptions();
                        //gives us an option to download the file where we want download
                        Map<String, Object> prefs = new HashMap<String, Object>();
                        System.out.println(chromeOptions.getBrowserVersion());
                        prefs.put("download.default_directory", BrowserUtils.downloadPath()); //path to dir
                        chromeOptions.setExperimentalOption("prefs", prefs);
                        chromeOptions.addArguments("--start-maximized");
                        chromeOptions.addArguments("--disable-notifications");
                        chromeOptions.addArguments("--diable-gpu");
                        chromeOptions.addArguments("--disable-extensions");
                        chromeOptions.addArguments("--no-sandbox");
                        chromeOptions.addArguments("--ignore-ssl-errors=yes");
                        chromeOptions.addArguments("--ignore-certificate-errors");
                        desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                        RemoteWebDriver driver = new RemoteWebDriver(url, desiredCapabilities);
                        driver.setFileDetector(new LocalFileDetector());
                        driverPool.set(driver);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case "chrome":

                    /*
                     * To disable pop-up notifications on Chrome we can use this
                     * WebDriverManager.chromedriver().setup();
                     * ChromeOptions options = new ChromeOptions();
                     * options.addArguments("--disable-notifications");
                     * WebDriver driver = new ChromeDriver(options);
                     */

                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    //gives us an option to download the file to the right location
                    Map<String, Object> prefs = new HashMap<String, Object>();

                    prefs.put("download.default_directory", BrowserUtils.downloadPath()); //path to dir
                    chromeOptions.setExperimentalOption("prefs", prefs);

                    chromeOptions.addArguments("--start-maximized");
                    chromeOptions.addArguments("--disable-notifications");
                    driverPool.set(new ChromeDriver(chromeOptions));

                    break;

                //to run chrome without interface (headless mode)
                case "chromeheadless":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.setHeadless(true);
                    //gives us an option to download the file to the right location
                    Map<String, Object> prefs2 = new HashMap<String, Object>();
                    prefs2.put("download.default_directory", BrowserUtils.downloadPath()); //path to dir
                    options.setExperimentalOption("prefs", prefs2);

                    driverPool.set(new ChromeDriver(options));
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.setProfile(firefoxProfile());
                    driverPool.set(new FirefoxDriver(firefoxOptions));

                    break;

                case "firefox-remote":
                    try {
                        URL url = new URL(hubURL);
                        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                        desiredCapabilities.setBrowserName(Browser.FIREFOX.browserName());
                        desiredCapabilities.setPlatform(Platform.ANY);
                        FirefoxOptions firefoxOptionsRemote = new FirefoxOptions();
                        firefoxOptionsRemote.setProfile(firefoxProfile());
                        desiredCapabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptionsRemote);
                        driverPool.set(new RemoteWebDriver(url, desiredCapabilities));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "ie":
                    if (!System.getProperty("os.name").toLowerCase().contains("windows"))
                        throw new WebDriverException("Your OS doesn't support Internet Explorer");
                    WebDriverManager.iedriver().setup();
                    driverPool.set(new InternetExplorerDriver());
                    break;

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();

                    Map<String, Object> prefsEdge = new HashMap<String, Object>();

                    prefsEdge.put("download.default_directory", BrowserUtils.downloadPath()); //path to dir
                    edgeOptions.setExperimentalOption("prefs", prefsEdge);

                    edgeOptions.addArguments("--start-maximized");
                    edgeOptions.addArguments("--disable-notifications");
                    driverPool.set(new EdgeDriver(edgeOptions));
                    break;

                case "edge-remote":
                    try {
                        URL url = new URL(hubURL);
                        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                        desiredCapabilities.setBrowserName(Browser.EDGE.browserName());
                        desiredCapabilities.setPlatform(Platform.ANY);
                        driverPool.set(new RemoteWebDriver(url, desiredCapabilities));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "safari":
                    if (!System.getProperty("os.name").toLowerCase().contains("mac"))
                        throw new WebDriverException("Your OS doesn't support Safari");
                    WebDriverManager.getInstance(SafariDriver.class).setup();
                    driverPool.set(new SafariDriver());
                    break;
                //if browser is wrong, throw exception
                default:
                    throw new RuntimeException("Wrong browser name!");
            }
        }
        return driverPool.get();
    }

    /**
     * synchronized keyword makes a method thread safe.
     * It ensures that only one thread can use it at a time.
     * Thread safety reduces performance but it makes everything safe
     * @param browser you can select browser by providing parameter
     */
    public synchronized static WebDriver getDriver(String browser) {

        if (driverPool.get() == null) {
            //if webdriver object doesn't exist
            //create it

            // -Dbrowser=firefox
            if (System.getProperty("browser") != null) {
                browser = System.getProperty("browser");
            }
            String hubURL = ConfigurationReader.getProperty("hub");
            if (System.getProperty("hub_url")!=null){
                hubURL=System.getProperty("hub_url");
                browser=browser+"-remote";
            }


            switch (browser) {

                case "chrome-remote":
                    try {
                        URL url = new URL(hubURL);
                        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                        desiredCapabilities.setBrowserName(Browser.CHROME.browserName());
                        desiredCapabilities.setPlatform(Platform.ANY);

                        ChromeOptions chromeOptions = new ChromeOptions();
                        //gives us an option to download the file to the right location
                        Map<String, Object> prefs = new HashMap<String, Object>();
                        System.out.println(chromeOptions.getBrowserVersion());
                        prefs.put("download.default_directory", BrowserUtils.downloadPath()); //path to dir
                        chromeOptions.setExperimentalOption("prefs", prefs);
                        chromeOptions.addArguments("--start-maximized");
                        chromeOptions.addArguments("--disable-notifications");
                        desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                        RemoteWebDriver driver = new RemoteWebDriver(url, desiredCapabilities);
                        driver.setFileDetector(new LocalFileDetector());
                        driverPool.set(driver);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case "chrome":

                    /*
                     * To disable pop-up notifications on Chrome we can use this
                     * WebDriverManager.chromedriver().setup();
                     * ChromeOptions options = new ChromeOptions();
                     * options.addArguments("--disable-notifications");
                     * WebDriver driver = new ChromeDriver(options);
                     */

                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    //gives us an option to download the file to the right location
                    Map<String, Object> prefs = new HashMap<String, Object>();

                    prefs.put("download.default_directory", BrowserUtils.downloadPath()); //path to dir
                    chromeOptions.setExperimentalOption("prefs", prefs);

                    chromeOptions.addArguments("--start-maximized");
                    chromeOptions.addArguments("--disable-notifications");
                    //  chromeOptions.addArguments("--disable-dev-shm-usage");

                    driverPool.set(new ChromeDriver(chromeOptions));

                    break;

                //to run chrome without interface (headless mode)
                case "chromeheadless":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.setHeadless(true);
                    //gives us an option to download the file to the right location
                    Map<String, Object> prefs2 = new HashMap<String, Object>();
                    prefs2.put("download.default_directory", BrowserUtils.downloadPath()); //path to dir
                    options.setExperimentalOption("prefs", prefs2);

                    driverPool.set(new ChromeDriver(options));
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.setProfile(firefoxProfile());
                    driverPool.set(new FirefoxDriver(firefoxOptions));

                    break;

                case "firefox-remote":
                    try {
                        URL url = new URL(hubURL);
                        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                        desiredCapabilities.setBrowserName(Browser.FIREFOX.browserName());
                        desiredCapabilities.setPlatform(Platform.ANY);
                        FirefoxOptions firefoxOptionsRemote = new FirefoxOptions();
                        firefoxOptionsRemote.setProfile(firefoxProfile());
                        desiredCapabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptionsRemote);
                        driverPool.set(new RemoteWebDriver(url, desiredCapabilities));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "ie":
                    if (!System.getProperty("os.name").toLowerCase().contains("windows"))
                        throw new WebDriverException("Your OS doesn't support Internet Explorer");
                    WebDriverManager.iedriver().setup();
                    driverPool.set(new InternetExplorerDriver());
                    break;

                case "edge":
                    if (!System.getProperty("os.name").toLowerCase().contains("windows"))
                        throw new WebDriverException("Your OS doesn't support Edge");
                    WebDriverManager.edgedriver().setup();
                    driverPool.set(new EdgeDriver());
                    break;

                case "edge-remote":
                    try {
                        URL url = new URL(hubURL);
                        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                        desiredCapabilities.setBrowserName(Browser.EDGE.browserName());
                        desiredCapabilities.setPlatform(Platform.ANY);
                        driverPool.set(new RemoteWebDriver(url, desiredCapabilities));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "safari":
                    if (!System.getProperty("os.name").toLowerCase().contains("mac"))
                        throw new WebDriverException("Your OS doesn't support Safari");
                    WebDriverManager.getInstance(SafariDriver.class).setup();
                    driverPool.set(new SafariDriver());
                    break;
                //if browser is wrong, throw exception
                default:
                    throw new RuntimeException("Wrong browser name!");
            }
        }
        return driverPool.get();
    }

    public static void closeDriver() {
        if (driverPool.get() != null) {
            driverPool.get().close();
            driverPool.get().quit();
            driverPool.remove();
        }
    }
    public static void close() {
        if (driverPool.get() != null) {
            driverPool.get().close();
            boolean b = driverPool.get() == null;
            driverPool.get().quit();
            //driverPool.remove();
        }
    }

    public static void quit() {
        if (driverPool.get() != null) {
            driverPool.get().quit();
            driverPool.remove();
        }
    }
}

