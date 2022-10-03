package com.esgc.Utilities;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileDownloadUtilities {

    public static void deleteDownloadFolder() {
        try {
            File dir = new File(BrowserUtils.downloadPath());
            File[] dir_contents = dir.listFiles();

            for (int i = 0; i < dir_contents.length; i++) {
                if (dir_contents[i].isDirectory()) {
                    try {
                        FileUtils.deleteDirectory(dir_contents[i]);
                    } catch (IOException e) {
                        System.out.println("Folder is empty");
                        System.out.println("Failed");
                        e.printStackTrace();
                    }
                } else {
                    dir_contents[i].delete();
                }
            }
            dir.delete();
        } catch (Exception e) {
            System.out.println("No existing files in the Folder");
            // e.printStackTrace();
        }
    }

    public static int filesCountInDownloadsFolder() {
        File dir = new File(BrowserUtils.downloadPath());
        File[] dir_contents = dir.listFiles();
        return dir_contents.length;
    }

    public static boolean waitUntilFileIsDownloaded() {
        File dir = new File(BrowserUtils.downloadPath());
        boolean isDownloaded = false;
        for(int i=1; i<30; i++) {
            try{
                File[] dir_contents = dir.listFiles();
                if(dir_contents.length==1){
                    BrowserUtils.wait(10);
                    isDownloaded = true;
                    break;
                }
            }catch(Exception e){

            }
            BrowserUtils.wait(5);
        }
        return isDownloaded;
    }

    public static String getDownloadedFileName() {

        File dir = new File(BrowserUtils.downloadPath());
        File[] dir_contents = dir.listFiles();
        Arrays.asList(dir_contents).stream().filter(e -> e.getName().startsWith("ESG portfolio import"));
        return dir_contents[0].getName(); //0 - only 1 folder
        //template name
    }

}
