package com.esgc.Utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class RemoteUtils {


    private static final String CHROME_FILE_NAME_SCRIPT = "return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content  #file-link').text";
    private static final String CHROME_FILE_URL_SCRIPT = "return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content  #file-link').href";
    private static final String EDGE_FILE_PATH_SCRIPT = "return window.document.querySelector('[data-win-control='DownloadsControl']').shadowRoot.querySelector('.downloadListItem').shadowRoot.querySelector('.downloadFileLink').textContent.trim();";
    private static final String SAFARI_FILE_PATH_SCRIPT = "return window.document.querySelector('[data-test='DownloadItem']').getAttribute('aria-label').trim().split(' ').pop();";

    private static final WebDriver driver = Driver.getDriver();
    private static final String browserName = ((RemoteWebDriver) driver).getCapabilities().getBrowserName().toLowerCase();

    public static synchronized void openChromeDownloadsTab() {
        WebDriver driver = Driver.getDriver();
        ((JavascriptExecutor) driver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));
        driver.get("chrome://downloads");
    }

    public static synchronized void closeChromeDownloadsTab() {
        WebDriver driver = Driver.getDriver();
        driver.close();
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
    }

    /**
     * name of downloaded document
     *
     * @return fileName
     */
    public static synchronized String getDownloadedDocumentFileName() {

        String fileName = "";
        String sourceURL = "";
        String filePath = "";

        if (browserName.contains("chrome")) {
            openChromeDownloadsTab();

            // Get the latest downloaded file name
            fileName = (String) ((JavascriptExecutor) driver).executeScript(CHROME_FILE_NAME_SCRIPT);
            // Get the latest downloaded file URL
            sourceURL = (String) ((JavascriptExecutor) driver).executeScript(CHROME_FILE_URL_SCRIPT);
            System.out.println("File Name = " + fileName);
            System.out.println("Source URL = " + sourceURL);

            closeChromeDownloadsTab();

        } else if (browserName.contains("edge")) {
            filePath = (String) ((JavascriptExecutor) driver).executeScript(EDGE_FILE_PATH_SCRIPT);
            fileName = new File(filePath).getName();
        } else if (browserName.contains("safari")) {
            filePath = (String) ((JavascriptExecutor) driver).executeScript(SAFARI_FILE_PATH_SCRIPT);
            fileName = new File(filePath).getName();
        }
        return fileName;
    }

    /**
     * url of downloaded document
     *
     * @return url
     */
    public static synchronized String getDownloadedDocumentUrl() {
        WebDriver driver = Driver.getDriver();
        String browserName = ((RemoteWebDriver) driver).getCapabilities().getBrowserName().toLowerCase();

        if (browserName.contains("chrome")) {
            openChromeDownloadsTab();

            // Get the latest downloaded file name
            String fileName = (String) ((JavascriptExecutor) driver).executeScript(CHROME_FILE_NAME_SCRIPT);
            // Get the latest downloaded file URL
            String sourceURL = (String) ((JavascriptExecutor) driver).executeScript(CHROME_FILE_URL_SCRIPT);
            System.out.println("File Name = " + fileName);
            System.out.println("Source URL = " + sourceURL);

            closeChromeDownloadsTab();
            return sourceURL;
        } else if (browserName.contains("edge")) {
            //TODO
        } else if (browserName.contains("safari")) {
            //TODO
        }

        return "";
    }

    /**
     * downloads the document to local if execution is in remote to reach out downloaded files
     */
    public static synchronized String getDocumentPathFromRemote() {
        String filePath = "";
        if (browserName.contains("chrome")) {
            openChromeDownloadsTab();

            // Get the latest downloaded file name
            String fileName = (String) ((JavascriptExecutor) driver).executeScript(CHROME_FILE_NAME_SCRIPT);
            // Get the latest downloaded file URL
            String sourceURL = (String) ((JavascriptExecutor) driver).executeScript(CHROME_FILE_URL_SCRIPT);
            System.out.println("File Name = " + fileName);
            System.out.println("Source URL = " + sourceURL);

            closeChromeDownloadsTab();


        } else if (browserName.contains("edge")) {
            filePath = (String) ((JavascriptExecutor) driver).executeScript(EDGE_FILE_PATH_SCRIPT);
        } else if (browserName.contains("safari")) {
            filePath = (String) ((JavascriptExecutor) driver).executeScript(EDGE_FILE_PATH_SCRIPT);
        }

        return filePath;
    }

    /**
     * downloads the document to local if execution is in remote to reach out downloaded files
     */
    public static synchronized void downloadDocumentFromRemote() {
        try {
            // open the connection to the file URL
            URL url = new URL(getDownloadedDocumentUrl());
            URLConnection connection = url.openConnection();

            // read the file contents into a ByteArrayOutputStream
            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();

            // create a File object from the downloaded contents
            File file = new File(BrowserUtils.templatePath());
            System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
            File parentDir = file.getParentFile();
            System.out.println("parentDir.getName() = " + parentDir.getName());
            System.out.println("parentDir.getCanonicalPath() = " + parentDir.getCanonicalPath());
            if(!parentDir.exists())
                parentDir.mkdirs();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            outputStream.writeTo(fileOutputStream);
            fileOutputStream.close();
            System.out.println("FILE CREATED");
        } catch (Exception e) {
            System.out.println("FILE CREATION FAILED");
            e.printStackTrace();
        }
    }
}
