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
    public static final String VIEWER_USER_USERNAME;
    public static final String VIEWER_USER_PASSWORD;
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
    public static final String USER_WITH_ESG_WITHOUT_EXPORT_USERNAME;
    public static final String USER_WITH_ESG_WITHOUT_EXPORT_PASSWORD;
    public static final String USER_WITH_ESG_PS_ENTITLEMENT_USERNAME;
    public static final String USER_WITH_ESG_PS_ENTITLEMENT_PASSWORD;
    public static final String USER_WITH_ESG_ENTITLEMENT_USERNAME;
    public static final String USER_WITH_ESG_ENTITLEMENT_PASSWORD;
    public static final String USER_WITH_EXPORT_ENTITLEMENT_USERNAME;
    public static final String USER_WITH_EXPORT_ENTITLEMENT_PASSWORD;
    public static final String QA_TEST_ACCOUNT_ID;
    public static final String QA_TEST_APPLICATION_ID;
    public static final String ADMIN_ROLE_KEY;
    public static final String VIEWER_ROLE_KEY;
    public static final String FULFILLMENT_ROLE_KEY;
    public static final String USER_WITH_PREDICTEDSCORE_AND_CLIMATE_USERNAME ;
    public static final String USER_WITH_PREDICTEDSCORE_AND_CLIMATE_PASSWORD ;

    public static final String USER_WITH_ONDEMAND_ENTITLEMENT_USERNAME ;
    public static final String USER_WITH_ONDEMAND_ENTITLEMENT_PASSWORD ;

    public static final String FIRSTTIME_USER_WITH_ONDEMAND_ENTITLEMENT_USERNAME ;
    public static final String FIRSTTIME_USER_WITH_ONDEMAND_ENTITLEMENT_PASSWORD ;

    public static final String EUTAXONOMY_SFDR_ESG_ESGPREDICTOR_ONDEMAND_EXPORT_USERNAME ;
    public static final String EUTAXONOMY_SFDR_ESG_ESGPREDICTOR_ONDEMAND_EXPORT_PASSWORD ;


    public static final String EUTAXONOMY_SFDR_USERNAME ;
    public static final String EUTAXONOMY_SFDR_PASSWORD ;

    public static final String PDF_EXPORT_BUNDLE_USERNAME;
    public static final String PDF_EXPORT_BUNDLE_PASSWORD;
    public static final String PDF_EXPORT_ONLY_SOURCEDOCUMENTS_USERNAME;
    public static final String PDF_EXPORT_ONLY_SOURCEDOCUMENTS_PASSWORD;
    public static final String NO_PREVIOUSLY_DOWNLOADED_REGULATORY_REPORTS_USERNAME;
    public static final String NO_PREVIOUSLY_DOWNLOADED_REGULATORY_REPORTS_PASSWORD;
    public static final String PDF_EXPORT_ONLY_PDF_USERNAME;
    public static final String PDF_EXPORT_ONLY_PDF_PASSWORD;
    public static final String USER_WITH_SCORE_PREDICTOR_ENTITLEMENT_USERNAME;
    public static final String USER_WITH_SCORE_PREDICTOR_ENTITLEMENT_PASSWORD;
    public static final String USER_WITH_CORPORATES_ESG_DATA_AND_SCORES_ENTITLEMENT_USERNAME;
    public static final String USER_WITH_CORPORATES_ESG_DATA_AND_SCORES_ENTITLEMENT_PASSWORD;
    public static final String ODA_ESG_PREDICTOR_DATA_ENTITLEMENT_USERNAME;
    public static final String ODA_ESG_PREDICTOR_DATA_ENTITLEMENT_PASSWORD;

    public static final String MESG_APPLICATION_NAME;
    public static  String environment ;


    static String getPropertiesPath() {
        return System.getProperty("user.dir") + File.separator + "src" +
                File.separator + "test" + File.separator + "resources" +
                File.separator + "environments" + File.separator + "%s.properties";
    }

    static {
        Properties properties = null;
        environment = ConfigurationReader.getProperty("environment");
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
        VIEWER_USER_USERNAME = properties.getProperty("viewer_user_username");
        VIEWER_USER_PASSWORD = properties.getProperty("viewer_user_password");
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
        USER_WITH_ESG_WITHOUT_EXPORT_USERNAME = properties.getProperty("user_with_esg_without_export_username");
        USER_WITH_ESG_WITHOUT_EXPORT_PASSWORD = properties.getProperty("user_with_esg_without_export_password");
        USER_WITH_OUT_ESG_ENTITLEMENT_USERNAME = properties.getProperty("tr_bundle_username");
        USER_WITH_OUT_ESG_ENTITLEMENT_PASSWORD = properties.getProperty("tr_bundle_password");
        USER_WITH_EXPORT_ENTITLEMENT_USERNAME = properties.getProperty("export_bundle_username");
        USER_WITH_EXPORT_ENTITLEMENT_PASSWORD = properties.getProperty("export_bundle_password");
        USER_WITH_OUT_EXPORT_ENTITLEMENT_USERNAME = properties.getProperty("no_export_bundle_username");
        USER_WITH_OUT_EXPORT_ENTITLEMENT_PASSWORD = properties.getProperty("no_export_bundle_password");
        USER_WITH_ESG_PS_ENTITLEMENT_USERNAME = properties.getProperty("esg_ps_bundle_username");
        USER_WITH_ESG_PS_ENTITLEMENT_PASSWORD = properties.getProperty("esg_ps_bundle_password");
        USER_WITH_ESG_ENTITLEMENT_USERNAME = properties.getProperty("esg_bundle_username");
        USER_WITH_ESG_ENTITLEMENT_PASSWORD = properties.getProperty("esg_bundle_password");
        OrbisId = properties.getProperty("orbis_id");
        p3OrbisId = properties.getProperty("p3Orbisid");
        QA_TEST_ACCOUNT_ID = properties.getProperty("account_id");
        QA_TEST_APPLICATION_ID = properties.getProperty("application_id");
        MESG_APPLICATION_NAME = properties.getProperty("mesg_application_name");
        VIEWER_ROLE_KEY = properties.getProperty("viewer_role_key");
        ADMIN_ROLE_KEY = properties.getProperty("admin_role_key");
        FULFILLMENT_ROLE_KEY = properties.getProperty("fulfillment_role_key");
        PDF_EXPORT_BUNDLE_USERNAME = properties.getProperty("PDFexport_bundle_username");
        PDF_EXPORT_BUNDLE_PASSWORD = properties.getProperty("PDFexport_bundle_password");
        PDF_EXPORT_ONLY_SOURCEDOCUMENTS_USERNAME = properties.getProperty("PDFexport_OnlySourceDocument_username");
        PDF_EXPORT_ONLY_SOURCEDOCUMENTS_PASSWORD = properties.getProperty("PDFexport_OnlySourceDocument_password");
        PDF_EXPORT_ONLY_PDF_USERNAME = properties.getProperty("PDFexport_OnlyPDF_username");
        PDF_EXPORT_ONLY_PDF_PASSWORD = properties.getProperty("PDFexport_OnlyPDF_password");
        NO_PREVIOUSLY_DOWNLOADED_REGULATORY_REPORTS_USERNAME = properties.getProperty("NoPreviouslyDownloadedRegulatoryReports_username");
        NO_PREVIOUSLY_DOWNLOADED_REGULATORY_REPORTS_PASSWORD = properties.getProperty("NoPreviouslyDownloadedRegulatoryReports_password");
        USER_WITH_ONDEMAND_ENTITLEMENT_USERNAME = properties.getProperty("OnDemand_bundle_username");
        USER_WITH_ONDEMAND_ENTITLEMENT_PASSWORD = properties.getProperty("OnDemand_bundle_password");
        USER_WITH_SCORE_PREDICTOR_ENTITLEMENT_USERNAME = properties.getProperty("score_predictor_username");
        USER_WITH_SCORE_PREDICTOR_ENTITLEMENT_PASSWORD = properties.getProperty("score_predictor_password");
        USER_WITH_CORPORATES_ESG_DATA_AND_SCORES_ENTITLEMENT_USERNAME = properties.getProperty("corporates_esg_data_and_scores_username");
        USER_WITH_CORPORATES_ESG_DATA_AND_SCORES_ENTITLEMENT_PASSWORD = properties.getProperty("corporates_esg_data_and_scores_password");
        ODA_ESG_PREDICTOR_DATA_ENTITLEMENT_USERNAME = properties.getProperty("oda_esg_predictor_data_username");
        ODA_ESG_PREDICTOR_DATA_ENTITLEMENT_PASSWORD = properties.getProperty("oda_esg_predictor_data_password");
        USER_WITH_PREDICTEDSCORE_AND_CLIMATE_USERNAME  = properties.getProperty("UserOnlyWithPredictedAndClimateEntitlement_username");
        USER_WITH_PREDICTEDSCORE_AND_CLIMATE_PASSWORD = properties.getProperty("UserOnlyWithPredictedAndClimateEntitlement_password");

        FIRSTTIME_USER_WITH_ONDEMAND_ENTITLEMENT_USERNAME = properties.getProperty("FirstTimeUserWithOnDemandEntitlement_username");
        FIRSTTIME_USER_WITH_ONDEMAND_ENTITLEMENT_PASSWORD = properties.getProperty("FirstTimeUserWithOnDemandEntitlement_password");

        EUTAXONOMY_SFDR_ESG_ESGPREDICTOR_ONDEMAND_EXPORT_USERNAME = properties.getProperty("EUTaxonomy_SFDR_ESG_ESGPREDICTOR_ONDEMAND_EXPORT_username");
        EUTAXONOMY_SFDR_ESG_ESGPREDICTOR_ONDEMAND_EXPORT_PASSWORD =  properties.getProperty("EUTaxonomy_SFDR_ESG_ESGPREDICTOR_ONDEMAND_EXPORT_password");

        EUTAXONOMY_SFDR_USERNAME= properties.getProperty("EUTaxonomy_SFDR_username");
        EUTAXONOMY_SFDR_PASSWORD = properties.getProperty("EUTaxonomy_SFDR_password");
    }
}
