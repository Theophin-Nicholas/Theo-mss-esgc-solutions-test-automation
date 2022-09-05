package com.esgc.Utilities;

import com.github.pireba.applescript.AppleScript;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;


/**
 * This is a utility class which contains few miscellenous methods.
 */
public class RobotRunner {
    /**
     * This Method is to select a file (Path given for the file) using Robot class
     */
    public static void selectFileToUpload(String filePath) {
        //setClipboardData(filePath); // path for the file
        String hostSystem = System.getProperty("os.name");
        System.out.println(hostSystem);
        hostSystem = hostSystem.toLowerCase();
        if (hostSystem.contains("windows")) {
            String script = System.getProperty("user.dir") + "\\src\\test\\java\\com\\esgc\\Utilities\\script.vbs";
// search for real path:
//            String executable = "C:\\windows\\...\\vbs.exe";
//            String cmdArr [] = {executable, script};
//            Runtime.getRuntime ().exec (cmdArr);

            try {
                String[] args = {"wscript", script, filePath};
                Runtime.getRuntime().exec(args);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            }
            /*Robot robot = null;
            try {
                System.setProperty("java.awt.headless", "true");
                robot = new Robot();
            } catch (AWTException e) {
                System.out.println("Robot Class cannot be initialized");
            }
            robot.delay(300);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.delay(200);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.delay(200);*/
        } else if (hostSystem.contains("mac")) {

            try {
                String appleScript = "activate application \"###WEBBROWSER###\"\n" +
                        "tell application \"System Events\"\n" +
                        "  delay 3\n" +
                        "  keystroke \"G\" using {command down, shift down}\n" +
                        "  delay 3\n" +
                        "  keystroke \"###PATH###\"\n" +
                        "  delay 2\n" +
                        "  keystroke return\n" +
                        "  delay 2\n" +
                        "  keystroke return\n" +
                        "end tell";
                if (ConfigurationReader.getProperty("browser").contains("chrome")) {
                    appleScript = appleScript.replaceAll("###WEBBROWSER###", "Google Chrome");
                } else if (ConfigurationReader.getProperty("browser").contains("firefox")) {
                    appleScript = appleScript.replaceAll("###WEBBROWSER###", "Firefox");
                } else if (ConfigurationReader.getProperty("browser").contains("safari")) {
                    appleScript = appleScript.replaceAll("###WEBBROWSER###", "Safari");
                } else if (ConfigurationReader.getProperty("browser").contains("firefox")) {
                    appleScript = appleScript.replaceAll("###WEBBROWSER###", "Microsoft Edge");
                } else {
                    System.out.println("Browser Configuration Error");
                    System.out.println("Check configuration.properties file");
                    System.out.println("Check browser");
                }
                String str = "\\";
                filePath = filePath.replace(str, "/");
                appleScript = appleScript.replaceAll("###PATH###", filePath);
                System.out.println(appleScript);
                AppleScript as = new AppleScript(appleScript);
                String result = as.executeAsString();
                System.out.println(result);
            } catch (Exception e) {
                System.out.println("Script could not executed for MAC");
            }
        }
    }

    public static void setClipboardData(String string) {
        StringSelection stringSelection = new StringSelection(string);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }
}