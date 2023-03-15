package com.esgc.Utilities;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class BrowserUtils {


    /**
     * path to download folder - downloads all files here
     * @return path
     */
    public static String downloadPath (){

        String path = System.getProperty("user.dir") + File.separator + "src" +
                File.separator + "test" + File.separator + "resources" + File.separator + "download";

        return path;
    }
    /**
     * path to upload folder - all upload files here
     * @return path
     */
    public static String uploadPath (){

        String path = System.getProperty("user.dir") + File.separator + "src" +
                File.separator + "test" + File.separator + "resources" + File.separator + "upload";

        return path;
    }
    /**
     * path to downloaded template -ESGCA-317
     * @return path
     */
    public static String templatePath (){

        String path = System.getProperty("user.dir") + File.separator + "src" +
                File.separator + "test" + File.separator + "resources" + File.separator + "download"
                + File.separator + ConfigurationReader.getProperty("downloadTemplateName");
        System.out.println("Path "+path);
        return path;
    }

    /**
     * path to expected portfolio template documents
     * @return path
     */
    public static String expectedPortfolioTemplateDocumentPath (){

        String path = System.getProperty("user.dir") + File.separator + "src" +
                File.separator + "test" + File.separator + "resources" + File.separator + "expected_portfolio_template"
                + File.separator + "ESG portfolio import.csv";
        System.out.println("Path "+path);
        return path;
    }

    /**
     * path to export repository folder to get expected export documents
     * @return path
     */
    public static String exportRepositoryPath (){

        String path = System.getProperty("user.dir") + File.separator + "src" +
                File.separator + "test" + File.separator + "resources" + File.separator + "export_document_repository";

        return path;
    }

    /**
     * path to downloaded Export Data -ESGCA-2092
     * @return path
     */
    public static String exportPath (String researchLine){

        File root = new File(downloadPath());
        FilenameFilter beginsWithResearchLine = new FilenameFilter()
        {
            public boolean accept(File directory, String filename) {
                return filename.startsWith(researchLine);
            }
        };

        File[] files = root.listFiles(beginsWithResearchLine);

        return files[files.length-1].getAbsolutePath();
    }

    /**
     * path to expected export document
     * @return path
     */
    public static String expectedExportDocumentPath (String researchLine){

        File root = new File(exportRepositoryPath());
        FilenameFilter beginsWithResearchLine = new FilenameFilter()
        {
            public boolean accept(File directory, String filename) {
                return filename.startsWith(researchLine);
            }
        };

        File[] files = root.listFiles(beginsWithResearchLine);

        return files[files.length-1].getAbsolutePath();
    }


    /**
     * Pauses test for some time
     *
     * @param seconds
     */

    public static void wait(int seconds) {
        try {
            Thread.sleep(1000 * seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * waits for background processes on the browser to complete
     *
     * @param timeOutInSeconds
     */
    public static void waitForPageToLoad(long timeOutInSeconds) {
        Driver Driver = new Driver();
        ExpectedCondition<Boolean> expectation = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeOutInSeconds));
            wait.until(expectation);
        } catch (Throwable error) {
            error.printStackTrace();
        }
    }

    /**
     * Waits for the provided element to be visible on the page
     *
     * @param element
     * @param timeToWaitInSec
     * @return
     */
    public static WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
        Driver Driver = new Driver();
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeToWaitInSec));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static boolean waitForInvisibility(WebElement element, int timeToWaitInSec) {
        Driver Driver = new Driver();
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeToWaitInSec));
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public static boolean waitForInvisibility(List<WebElement> elements, int timeToWaitInSec) {
        Driver Driver = new Driver();
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeToWaitInSec));
        return wait.until(ExpectedConditions.invisibilityOfAllElements(elements));
    }

    public static boolean isElementVisible(WebElement element, int timeToWaitInSec) {
        Driver Driver = new Driver();
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeToWaitInSec));
        try{
            return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Waits for provided element to be clickable
     *
     * @param element
     * @param timeout
     * @return
     */
    public static WebElement waitForClickablility(WebElement element, int timeout) {
        Driver Driver = new Driver();
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Tries to click on the element every second until it is clicked
     *
     * @param element
     * @param seconds
     * @return
     */

    public static void waitAndClick(WebElement element, int seconds) {
        for (int i = 0; i < seconds; i++) {
            try {
                BrowserUtils.waitForClickablility(element, 5).click();
                return;
            } catch (Exception e) {
                BrowserUtils.wait(1);
            }
        }
        System.out.println("Element is not clickable");
    }


    /**
     * checks that an element is present on the DOM of a page. This does not
     * * necessarily mean that the element is visible.
     *
     * @param by
     * @param time
     */
    public static void waitForPresenceOfElement(By by, long time) {
        Driver Driver = new Driver();
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(time)).until(ExpectedConditions.presenceOfElementLocated(by));
    }

    /**
     * Waits for element to be not stale
     *
     * @param element
     */
    public static void waitForStaleElement(WebElement element) {
        int y = 0;
        while (y <= 15) {
            if (y == 1)
                try {
                    element.isDisplayed();
                    break;
                } catch (StaleElementReferenceException st) {
                    y++;
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (WebDriverException we) {
                    y++;
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }
    }


    /**
     * @param elements represents collection of WebElements
     * @return collection of strings
     */
    public static List<String> getElementsText(List<WebElement> elements) {
        List<String> textValues = new ArrayList<>();
        for (WebElement element : elements) {
            if (!element.getText().isEmpty()) {
                textValues.add(element.getText());
            }
        }
        return textValues;
    }

    /**
     * Extracts text from list of elements matching the provided locator into new List<String>
     *
     * @param locator
     * @return list of strings
     */
    public static List<String> getElementsText(By locator) {
        Driver Driver = new Driver();
        List<WebElement> elems = Driver.getDriver().findElements(locator);
        List<String> elemTexts = new ArrayList<>();

        for (WebElement each : elems) {
            elemTexts.add(each.getText());
        }
        return elemTexts;

    }
//==============================================================================

    /**
     * Clicks on an element using JavaScript
     *
     * @param element
     */
    public static void clickWithJS(WebElement element) {
        Driver Driver = new Driver();
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", element);
    }

    /**
     * This method will recover in case of exception after unsuccessful the click,
     * and will try to click on element again.
     *
     * @param by
     * @param attempts
     */
    public static void clickWithWait(By by, int attempts) {
        Driver Driver = new Driver();
        int counter = 0;
        //click on element as many as you specified in attempts parameter
        while (counter < attempts) {
            try {
                //selenium must look for element again
                clickWithJS(Driver.getDriver().findElement(by));
                //if click is successful - then break
                break;
            } catch (WebDriverException e) {
                //if click failed
                //print exception
                //print attempt
                e.printStackTrace();
                ++counter;
                //wait for 1 second, and try to click again
                wait(1);
            }
        }
    }

    /**
     * Performs double click action on an element
     *
     * @param element
     */
    public static void doubleClick(WebElement element) {
        Driver Driver = new Driver();
        new Actions(Driver.getDriver()).doubleClick(element).build().perform();
    }


    /**
     * Scroll to element using JavaScript
     *
     * @param element
     */
    public static WebElement scrollTo(WebElement element) {
        Driver Driver = new Driver();
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView({block: \"center\"});", element);
        return element;
    }

    public static void scrollToByPixel() {
        Driver Driver = new Driver();
        ((JavascriptExecutor) Driver.getDriver()).executeScript("window.scrollBy(0,5)");
    }
    /**
     * Moves the mouse to given element
     *
     * @param element on which to hover
     */
    public static void hover(WebElement element) {
        Driver Driver = new Driver();
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(element).pause(2000).build().perform();
    }

    /**
     * Checks or unchecks given checkbox
     *
     * @param element
     * @param check
     */
    public static void selectCheckBox(WebElement element, boolean check) {
        if (check) {
            if (!element.isSelected()) {
                element.click();
            }
        } else {
            if (element.isSelected()) {
                element.click();
            }
        }
    }

    /**
     * This method will switch webdriver from current window
     * to target window based on page title
     *
     * @param title of the window to switch
     */
    public static void switchWindow(String title) {
        Driver Driver = new Driver();
        Set<String> windowHandles = Driver.getDriver().getWindowHandles();
        for (String window : windowHandles) {
            Driver.getDriver().switchTo().window(window);
            if (Driver.getDriver().getTitle().equals(title)) {
                break;
            }
        }
    }

    /**
     * Verifies whether the element matching the provided locator is displayed on page
     *
     * @param by
     * @throws AssertionError if the element matching the provided locator is not found or not displayed
     */
    public static void verifyElementDisplayed(By by) {
        Driver Driver = new Driver();
        try {
            Assert.assertTrue(Driver.getDriver().findElement(by).isDisplayed(), "Element not visible: " + by);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            Assert.fail("Element not found: " + by);

        }
    }

    /**
     * Verifies whether the element matching the provided locator is NOT displayed on page
     *
     * @param by
     * @throws AssertionError the element matching the provided locator is displayed
     */
    public static void verifyElementNotDisplayed(By by) {
        Driver Driver = new Driver();
        try {
            Assert.assertFalse(Driver.getDriver().findElement(by).isDisplayed(), "Element should not be visible: " + by);
        } catch (NoSuchElementException e) {
            e.printStackTrace();

        }
    }

    /**
     * Verifies whether the element is displayed on page
     *
     * @param element
     * @throws AssertionError if the element is not found or not displayed
     */
    public static void verifyElementDisplayed(WebElement element) {
        try {
            Assert.assertTrue(element.isDisplayed(), "Element not visible: " + element);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            Assert.fail("Element not found: " + element);

        }
    }

    /*
     * takes screenshot
     * @param name
     * take a name of a test and returns a path to screenshot takes
     */
    public static String getScreenshot(String name)  {
        Driver Driver = new Driver();
        // name the screenshot with the current date time to avoid duplicate name
        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        // TakesScreenshot ---> interface from selenium which takes screenshots
        TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);
        // full path to the screenshot location
        String target = System.getProperty("user.dir") + "/test-output/Screenshots/" + name + date + ".png";
        File finalDestination = new File(target);
        // save the screenshot to the path given
        try {
            FileUtils.copyFile(source, finalDestination);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return target;
    }

    public static void waitFor(int seconds) {
        wait(seconds);
    }

    public static void ActionKeyPress(Keys key){
        Driver Driver = new Driver();
        Actions act = new Actions(Driver.getDriver());
        act.sendKeys(key).build().perform();
    }

    public static String getCurrentWindowHandle() {

        Driver Driver = new Driver();
        return Driver.getDriver().getWindowHandle();
    }

    public static Set<String> getWindowHandles() {
        Driver Driver = new Driver();
        Set<String> handles = Driver.getDriver().getWindowHandles();
        return handles;
    }

    public static void switchWindowsTo(String tab) {
        Driver Driver = new Driver();
        Driver.getDriver().switchTo().window(tab);
    }

    public static List<String> splitToSentences(String text) {
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        List<String> sentences = new ArrayList<>();
        iterator.setText(text);
        int start = iterator.first();
        for (int end = iterator.next();
             end != BreakIterator.DONE;
             start = end, end = iterator.next()) {
            if(text.substring(start,end).replaceAll("\\W","").length()>0){
                sentences.add(text.substring(start,end));
            }
        }
        return sentences;
    }
    
    public static String DataSourcePath (){

        String path = System.getProperty("user.dir") + File.separator + "src" +
                File.separator + "test" + File.separator + "resources" + File.separator + "DataSource";

        return path;
    }

    public static Object[][] appendedArrays(Object[][] array1, Object[][] array2) {
        Object[][] ret = new Object[array1.length + array2.length][];
        int i = 0;
        for (;i<array1.length;i++) {
            ret[i] = array1[i];
        }
        for (int j = 0;j<array2.length;j++) {
            ret[i++] = array2[j];
        }
        return ret;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public static <T, U> List<U>
    convertStringListToIntList(List<T> listOfString,
                               Function<T, U> function)
    {
        return Lists.transform(listOfString, function);
    }

    public static List<String> specialSort(List<String> list) {
        //sort list with case-insensitive order and ignore special characters
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.replaceAll("[^a-zA-Z0-9 ]", "~").compareToIgnoreCase(o2.replaceAll("[^a-zA-Z0-9 ]", "~"));
            }
        });
        return list;
    }
}
