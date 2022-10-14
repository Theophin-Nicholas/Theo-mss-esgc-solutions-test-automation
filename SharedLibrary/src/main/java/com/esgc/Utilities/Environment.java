package com.esgc.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Environment {
    public static final String URL;
    public static final String EMC_URL;

    public static final String INTERNAL_USER_USERNAME;
    public static final String INTERNAL_USER_PASSWORD;

    //Database
    public static final String DB_HOST;
    public static final String DB_USERNAME;
    public static final String DB_PASSWORD;
    public static final String DB_WAREHOUSE;
    public static final String DB_DATABASE;
    public static final String DB_SCHEMA;
    public static final String DB_ROLE;

    //API
    public static final String USER_ID;
    public static final String UI_USERNAME;
    public static final String UI_PASSWORD;
    public static final String DATA_USERNAME;
    public static final String DATA_PASSWORD;

    //Entity Page
    public static final String ENTITY_URL;
    public static final String ISSUER_USERNAME;
    public static final String ISSUER_USERNAMEP3;
    public static final String ISSUER_PASSWORD;
    public static final String OrbisId ;
    public static final String p3OrbisId;


    //Entitlements
    public static final String PHYSICAL_RISK_USERNAME;
    public static final String PHYSICAL_RISK_PASSWORD;
    public static final String TRANSITION_RISK_USERNAME;
    public static final String TRANSITION_RISK_PASSWORD;
    public static final String CLIMATE_GOVERNANCE_USERNAME;
    public static final String CLIMATE_GOVERNANCE_PASSWORD;
    public static final String PHYSICAL_RISK_TRANSITION_RISK_USERNAME;
    public static final String PHYSICAL_RISK_TRANSITION_RISK_PASSWORD;
    public static final String TRANSITION_RISK_CLIMATE_GOVERNANCE_USERNAME;
    public static final String TRANSITION_RISK_CLIMATE_GOVERNANCE_PASSWORD;
    public static final String PHYSICAL_RISK_CLIMATE_GOVERNANCE_USERNAME;
    public static final String PHYSICAL_RISK_CLIMATE_GOVERNANCE_PASSWORD;
    public static final String USER_WITH_CONTROVERSIES_ENTITLEMENT_USERNAME;
    public static final String USER_WITH_CONTROVERSIES_ENTITLEMENT_PASSWORD;
    public static final String USER_WITH_OUT_CONTROVERSIES_ENTITLEMENT_USERNAME;
    public static final String USER_WITH_OUT_CONTROVERSIES_ENTITLEMENT_PASSWORD;
    public static final String USER_WITH_OUT_ESG_ENTITLEMENT_USERNAME;
    public static final String USER_WITH_OUT_ESG_ENTITLEMENT_PASSWORD;
    public static final String USER_WITH_OUT_EXPORT_ENTITLEMENT_USERNAME;
    public static final String USER_WITH_OUT_EXPORT_ENTITLEMENT_PASSWORD;
    public static final String USER_WITH_EXPORT_ENTITLEMENT_USERNAME;
    public static final String USER_WITH_EXPORT_ENTITLEMENT_PASSWORD;
    public static final String QA_TEST_ACCOUNT_ID;


    static String getPropertiesPath() {
        return System.getProperty("user.dir") + File.separator + "src" +
                File.separator + "test" + File.separator + "resources" +
                File.separator + "environments" + File.separator + "%s.properties";
    }

    static {
        Properties properties = null;
        String environment = ConfigurationReader.getProperty("environment");
        if (System.getProperty("environment")!=null){
            environment=System.getProperty("environment");

        }
        try {
            String path = String.format(getPropertiesPath(), environment);
            FileInputStream input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);
            input.close();
        } catch (IOException e) {
            System.out.println("Environment properties is not found under: ");
            System.out.println(getPropertiesPath());
            e.printStackTrace();
        }
        URL = properties.getProperty("url");
        EMC_URL = properties.getProperty("emc_url");
        INTERNAL_USER_USERNAME = properties.getProperty("internal_user_username");
        INTERNAL_USER_PASSWORD = properties.getProperty("internal_user_password");
        DB_HOST = properties.getProperty("db_host");
        DB_USERNAME = properties.getProperty("db_username");
        DB_PASSWORD = properties.getProperty("db_password");
        DB_WAREHOUSE = properties.getProperty("db_warehouse");
        DB_DATABASE = properties.getProperty("db_database");
        DB_SCHEMA = properties.getProperty("db_schema");
        DB_ROLE = properties.getProperty("db_role");
        USER_ID = properties.getProperty("api_userid");
        UI_USERNAME = properties.getProperty("username");
        UI_PASSWORD = properties.getProperty("password");
        DATA_USERNAME = properties.getProperty("data_username");
        DATA_PASSWORD = properties.getProperty("data_password");
        ENTITY_URL = properties.getProperty("entity_url");
        ISSUER_USERNAME = properties.getProperty("entity_username");
        ISSUER_USERNAMEP3 = properties.getProperty("entity_usernameP3");
        ISSUER_PASSWORD = properties.getProperty("entity_password");
        PHYSICAL_RISK_USERNAME = properties.getProperty("pr_bundle_username");
        PHYSICAL_RISK_PASSWORD = properties.getProperty("pr_bundle_password");
        TRANSITION_RISK_USERNAME = properties.getProperty("tr_bundle_username");
        TRANSITION_RISK_PASSWORD = properties.getProperty("tr_bundle_password");
        CLIMATE_GOVERNANCE_USERNAME = properties.getProperty("cg_bundle_username");
        CLIMATE_GOVERNANCE_PASSWORD = properties.getProperty("cg_bundle_password");
        PHYSICAL_RISK_TRANSITION_RISK_USERNAME = properties.getProperty("ptr_bundle_username");
        PHYSICAL_RISK_TRANSITION_RISK_PASSWORD = properties.getProperty("ptr_bundle_password");
        TRANSITION_RISK_CLIMATE_GOVERNANCE_USERNAME = properties.getProperty("trcg_bundle_username");
        TRANSITION_RISK_CLIMATE_GOVERNANCE_PASSWORD = properties.getProperty("trcg_bundle_password");
        PHYSICAL_RISK_CLIMATE_GOVERNANCE_USERNAME = properties.getProperty("prcg_bundle_username");
        PHYSICAL_RISK_CLIMATE_GOVERNANCE_PASSWORD = properties.getProperty("prcg_bundle_password");
        USER_WITH_CONTROVERSIES_ENTITLEMENT_USERNAME = properties.getProperty("controversies_bundle_username");
        USER_WITH_CONTROVERSIES_ENTITLEMENT_PASSWORD = properties.getProperty("controversies_bundle_password");
        USER_WITH_OUT_CONTROVERSIES_ENTITLEMENT_USERNAME = properties.getProperty("no_controversies_bundle_username");
        USER_WITH_OUT_CONTROVERSIES_ENTITLEMENT_PASSWORD = properties.getProperty("no_controversies_bundle_password");
        USER_WITH_OUT_ESG_ENTITLEMENT_USERNAME = properties.getProperty("tr_bundle_username");
        USER_WITH_OUT_ESG_ENTITLEMENT_PASSWORD = properties.getProperty("tr_bundle_password");
        USER_WITH_EXPORT_ENTITLEMENT_USERNAME = properties.getProperty("export_bundle_username");
        USER_WITH_EXPORT_ENTITLEMENT_PASSWORD = properties.getProperty("export_bundle_password");
        USER_WITH_OUT_EXPORT_ENTITLEMENT_USERNAME = properties.getProperty("no_export_bundle_username");
        USER_WITH_OUT_EXPORT_ENTITLEMENT_PASSWORD = properties.getProperty("no_export_bundle_password");
        OrbisId = properties.getProperty("orbis_id");
        p3OrbisId = properties.getProperty("p3Orbisid");
        QA_TEST_ACCOUNT_ID = properties.getProperty("account_id");
    }
}
