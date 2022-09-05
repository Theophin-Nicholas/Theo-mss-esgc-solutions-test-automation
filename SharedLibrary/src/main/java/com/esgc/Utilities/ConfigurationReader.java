package com.esgc.Utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {

    //Responsible for loading properties fileand will provide access, based on keys we will read values.
    private static Properties properties = new Properties();

    static {

        try {
            String current = new java.io.File( "../" ).getCanonicalPath();

            //provides access to the file
            FileInputStream fileInputStream = new FileInputStream(current+"/configuration.properties");

            //loads properties file
            properties.load(fileInputStream);
            fileInputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //We call this method to read based on key name from "configuration.properties" file
    public static String getProperty(String key) {

        return properties.getProperty(key);
    }
}

