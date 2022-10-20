package com.esgc.TestBase;

import com.esgc.Utilities.EntitlementsBundles;
import org.testng.annotations.DataProvider;

public class DataProviderClass {

    @DataProvider(name = "credentials")
    public Object[][] provideFilterParametersWithOrbisId() {

        return new Object[][]
                {
                        /* {"mesg-platform-issuer-qa+test1@outlook.com", "Moodys123", "387077"},
                         {"mesg-platform-issuer-qa+test2@outlook.com", "Moodys123", "409448"},
                         {"mesg-platform-issuer-qa+test3@outlook.com", "Moodys123", "45484"},
                         {"mesg-platform-issuer-qa+test19@outlook.com", "Moodys123", "292337"},
                         {"mesg-platform-issuer-qa+test5@outlook.com", "Moodys123", "59967948"},
                         {"mesg-platform-issuer-qa+test6@outlook.com", "Moodys123", "480840027"},
                         {"mesg-platform-issuer-qa+test9@outlook.com", "Moodys123", "35352536"},
                         {"mesg-platform-issuer-qa+test10@outlook.com", "Moodys123", "50881273"},
                         {"mesg-platform-issuer-qa+test11@outlook.com", "Moodys123", "6741239"},
                         {"mesg-platform-issuer-qa+test13@outlook.com", "Moodys123", "51059459"},
                         {"mesg-platform-issuer-qa+test14@outlook.com", "Moodys123", "309415"},
                         {"mesg-platform-issuer-qa+test15@outlook.com", "Moodys123", "3059"},
                         {"mesg-platform-issuer-qa+test16@outlook.com", "Moodys123", "163796"},
                         {"mesg-platform-issuer-qa+test17@outlook.com", "Moodys123", "250362251"},
                         {"mesg-platform-issuer-qa+test18@outlook.com", "Moodys123", "51056765"},
                         {"mesg-platform-issuer-qa+test4@outlook.com", "Moodys123", "83092379"},
                         {"mesg-platform-issuer-qa+test22@outlook.com", "Moodys123", "289062220"},
                         {"mesg-platform-issuer-qa+test27@outlook.com", "Moodys123", "163927"},
                         {"mesg-platform-issuer-qa+test35@outlook.com", "Moodys123", "61674974"},
                         {"mesg-platform-issuer-qa+test37@outlook.com", "Moodys123", "1645337"},
                         {"mesg-platform-issuer-qa+test39@outlook.com", "Moodys123", "74519180"},
                         {"mesg-platform-issuer-qa+test40@outlook.com", "Moodys123", "3024"},
                         {"mesg-platform-issuer-qa+test41@outlook.com", "Moodys123", "69846122"},
                         {"mesg-platform-issuer-qa+test43@outlook.com", "Moodys123", "299437"},
                         {"mesg-platform-issuer-qa+test44@outlook.com", "Moodys123", "264107"},
                         {"mesg-platform-issuer-qa+test47@outlook.com", "Moodys123", "673357"},
                         {"mesg-platform-issuer-qa+test49@outlook.com", "Moodys123", "41552864"},
                         {"mesg-platform-issuer-qa+test20@outlook.com", "Moodys123", "1842671"},
                         {"mesg-platform-issuer-qa+test21@outlook.com", "Moodys123", "190930579"},
                         {"mesg-platform-issuer-qa+test33@outlook.com", "Moodys123", "50777504"},
                         {"mesg-platform-issuer-qa+test33@outlook.com", "Moodys123", "314938025"},
                         {"mesg-platform-issuer-qa+test34@outlook.com", "Moodys123", "737015"},
                         {"mesg-platform-issuer-qa+test36@outlook.com", "Moodys123", "50015536"},*/
                        {"mesg-platform-issuer-qa+test38@outlook.com", "Moodys123", "91315"},
                        {"mesg-platform-issuer-qa+test45@outlook.com", "Moodys123", "1742838"},
                        {"mesg-platform-issuer-qa+test46@outlook.com", "Moodys123", "51059044"},
                };


    }

    @DataProvider(name = "credentialsPrd") //Prd Credentials
    public Object[][] provideFilterParametersWithOrbisIdPrd() {

        return new Object[][]
                {
                        {"mesg360-testing+issuer21@outlook.com", "Test12345"},
                        /*    {"mesg360-testing+issuer26@outlook.com", "Test12345"},*/
                        /*   {"mesg360-testing+issuer31@outlook.com", "Test12345"}*/
                };

    }

    @DataProvider(name = "credentialsP2") //PRD credentials
    public Object[][] provideFilterParametersP2Prd() {

        return new Object[][]
                {
                        {"mesg360-testing+issuer105@outlook.com", "Test12345"},
                        /*   {"mesg360-testing+issuer30@outlook.com", "Test12345"},*/
                        /*    {"mesg360-testing+issuer70@outlook.com", "Test12345"}*/
                };
    }

    @DataProvider(name = "credentialsP2_back")
    public Object[][] provideFilterParameters2() {

        return new Object[][]
                {
                        {"mesg-platform-issuer-qa@outlook.com", "Moodys123"},
                        {" mesg-platform-testing+qa1@outlook.com", "Moodys123"},
                        {"  mesg-platform-testing+qa2@outlook.com", "Moodys123"},
                        {" mesg-platform-testing+qa3@outlook.com", "Moodys123"},
                        /*   {"  mesg-platform-testing+qa4@outlook.com", "Moodys123"},*/ //P3
                        /*  {"  mesg-platform-testing+metrics1@outlook.com", "Moodys123"},*/
                        /*   {"  mesg-platform-testing+metrics2@outlook.com", "Moodys123"},*/
                        {"  mesg-platform-testing+metrics3@outlook.com", "Moodys123"},
                        {"  mesg-platform-testing+metrics4@outlook.com", "Moodys123"},
                        /* {" mesg-platform-testing+metrics5@outlook.com", "Moodys123"},*/
                        /*   {" mesg-platform-testing+metrics6@outlook.com", "Moodys123"},*/
                        /* {" mesg-platform-testing+metrics7@outlook.com", "Moodys123"},*/
                        {" mesg-platform-testing+metrics8@outlook.com", "Moodys123"},
                        /* {" mesg-platform-testing+metrics9@outlook.com", "Moodys123"},*/
                        /* {" mesg-platform-testing+metrics10@outlook.com", "Moodys123"},*/
                        /*  {" mesg-platform-testing+metrics11@outlook.com", "Moodys123"},*/
                        /* {" mesg-platform-issuer-qa+data3@outlook.com", "Moodys123"}*/
                };
    }

    @DataProvider(name = "credentialsP2_UAT")
    public Object[][] provideFilterParameters1() {

        return new Object[][]
                {
                        /*  {"mesg-platform-issuer-qa+automationtest1@outlook.com", "Moodys123"},
                          *//* {"mesg-platform-issuer-qa+automationtest2@outlook.com" ,"Moodys123"},*//*
                        {"mesg-platform-issuer-qa+automationtest3@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest4@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest5@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest6@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest7@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest8@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest9@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest10@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest11@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest12@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest13@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest14@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest15@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest16@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest17@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest18@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest19@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest20@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest21@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest22@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest23@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest24@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest25@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest26@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest27@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest28@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest29@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest30@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest31@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest32@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest33@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest34@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest35@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest36@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest37@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest38@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest39@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest40@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest41@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest42@outlook.com" ,"Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest43@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest44@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest45@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest46@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest47@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest48@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest49@outlook.com", "Moodys123"},*/
                        {"mesg-platform-issuer-qa+automationtest50@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest51@outlook.com", "Moodys123"},
                        {"mesg-platform-issuer-qa+automationtest52@outlook.com", "Moodys123"}
                };
    }

    @DataProvider(name = "Entity")
    public Object[][] dpMethod() {

        return new Object[][]{
                {"Apple, Inc."},
                {"Samsung Securities Co., Ltd."},
                {"Alibaba Health Information Technology Ltd."},
                {"Development Bank of Japan, Inc."},
        };
    }

    @DataProvider(name = "noInfoOrbisID")
    public Object[][] noInfoOrbisID() {

        return new Object[][]{
                {"051056660"},//Automobiles
                {"000530501"},//Airlines
                {"012930645"},//Cement
                {"006739914"},//Oil&Gas
                {"017595801"},//Shipping
                {"114338654"},//Steel
                {"160065949"},//Aluminium
                {"001812590"},//World (Amazon)
                {"000006182"}//Sector: Electric & Gas Utilities
        };
    }

    @DataProvider(name = "entitlementCheck")
    public Object[][] noInfoCarbonFootprintOrbisID() {

        return new Object[][]{
                {"esg-test2@outlook.com", "Helloworld21", "Transition Risk"},//
                {"esg-test1@outlook.com", "Helloworld21", "Physical Risk"},//
                {"esg-test4@outlook.com", "Helloworld24", "Physical Risk and Transition Risk"},//
                /*   {"esg-test7@outlook.com","Helloworld21","Physical Risk, Transition Risk, Corporate ESG and Controversies Entitlements"},//*/
                {"esg-test1+controversy@outlook.com", "Moodys123", "Physical Risk, Transition Risk and Controversies"},//
        };
    }
    @DataProvider(name = "entitlementCheckPrd")
    public Object[][] entitlementCheckPrd() {

        return new Object[][]{
                {"esg-test2@outlook.com","Testing123","Transition Risk"},//
                {"esg-test1@outlook.com","Testing123","Physical Risk"},//
                {"esg-test4@outlook.com","Testing123","Physical Risk and Transition Risk"},//
                /*   {"esg-test7@outlook.com","Helloworld21","Physical Risk, Transition Risk, Corporate ESG and Controversies Entitlements"},//*/
               // {"esg-test1+controversy@outlook.com","Testing123","Physical Risk, Transition Risk and Controversies"},//
        };
    }


    @DataProvider(name = "exportEntitlements")
    public Object[][] exportEntitlements() {

        return new Object[][]{
                {"esg-test5@outlook.com", "Helloworld25", "Physical Risk"},//
                {"esg-test6@outlook.com", "Helloworld26", "Transition Risk"},//
                {"esg-test1+export@outlook.com", "Moodys123", "Physical Risk and Transition Risk"},//
                {"esg-test33@outlook.com", "Helloworld21", "ESG"},//
        };
    }

    @DataProvider(name = "noInfoCarbonFootprintOrbisID")
    public Object[][] noInfoCarbonFootprintOrbisID1() {

        return new Object[][]{
                {"000006182"}//Sector: Electric & Gas Utilities
        };
    }

    @DataProvider(name = "orbisID")
    public Object[][] orbisID() {

        return new Object[][]{
                {"058618674"},//Automobiles
                /*        {"079299609"},//Airlines
                        {"006529465"},//Cement
                        {"000003497"},//Oil&Gas
                        {"187695688"},//Shipping
                        {"007270209"},//Steel
                        {"015984678"},//Aluminium
                        {"000411117"},//World (Apple)*/
//                {"006533494"},//Electric & Gas Utilities

        };
    }

    @DataProvider(name = "orbisIdWithCompanyName")
    public Object[][] orbisIdWithCompanyName() {

        return new Object[][]{
                {"Apple, Inc.", "000411117"},
                {"Rogers Corp.","000001484"},
                {"Ford Motor Co.", "058618674"},//Automobiles
                {"Wizz Air Holdings Plc", "079299609"},//Airlines
                {"Taiwan Cement Corp.","006529465"},//Cement
                {"ConocoPhillips","000003497"},//Oil&Gas
                {"Mitsui O.S.K. Lines, Ltd.","187695688"},//Shipping
        };
    }


    @DataProvider(name = "orbisIDWithoutTempAlignment")
    public Object[][] orbisIDWithoutTempAlignment() {

        return new Object[][]{
                {"012146622"},
                {"058618674"},
                {"037507363"},//
        };
    }

    @DataProvider(name = "Research Lines Investments")
    public Object[][] investments() {
        return new Object[][]{
                {"Sample Portfolio", "Physical Risk Hazards", "All Regions", "All Sectors", "May 2022", "Sample Portfolio"},
                {"Sample Portfolio", "Physical Risk Management", "All Regions", "Sovereign", "May 2022", "Sample Portfolio"},
                {"Sample Portfolio", "Temperature Alignment", "All Regions", "All Sectors", "May 2022", "Sample Portfolio"},
                {"Sample Portfolio", "Carbon Footprint", "All Regions", "All Sectors", "May 2022", "Sample Portfolio"},
                {"Sample Portfolio", "Green Share Assessment", "All Regions", "All Sectors", "May 2022", "Sample Portfolio"},
                {"Sample Portfolio", "Brown Share Assessment", "All Regions", "All Sectors", "May 2022", "Sample Portfolio"}
        };
    }

    @DataProvider(name = "Research Lines")
    public Object[][] availableResearchLines() {

        return new Object[][]{
                {"ESG Assessments"},
                {"Carbon Footprint"},
                {"Physical Risk Management"},
                {"Temperature Alignment"},
                {"Brown Share Assessment"},
                {"Green Share Assessment"},
        };
    }

    @DataProvider(name = "CompaniesWithMESGScore")
    public Object[][] CompaniesWithMESGScore() {

        return new Object[][]
                {
                        {"mesg-platform-issuer-qa+test40@outlook.com", "Moodys123", "007091964"}

                };

    }

    @DataProvider(name = "credentials11")
    public Object[][] provideFilterParametersss() {

        return new Object[][]
                {
                        {"mesg-platform-issuer-qa@outlook.com", "Moodys123", "Data Enriched", "P2"},
                        {"mesg-platform-issuer-qa+dat2@outlook.com", "Moodys123", "Researched", "P3"},
                        {"mesg-platform-issuer-qa+data1@outlook.com", "Moodys123", "Researched", "P3"},
                        {"mesg-platform-issuer-qa+data3@outlook.com", "Moodys123", "Researched Under Review", "P3"},
                        {"mesg-platform-issuer-qa+data4@outlook.com", "Moodys123", "Published", "P3"},
                        {"mesg-platform-issuer-qa+data5@outlook.com", "Moodys123", "Published Under Review", "P3"},
                };
    }

    @DataProvider(name = "bundles")
    public Object[][] dpMethod1() {

        return new Object[][]{
                {EntitlementsBundles.PHYSICAL_RISK, 4479},
                {EntitlementsBundles.TRANSITION_RISK, 4480},
                {EntitlementsBundles.PHYSICAL_RISK_TRANSITION_RISK, 4506},
        };
    }


    @DataProvider(name = "CompanyNames")
    public Object[][] provideParameters() {
        return new Object[][]
                {{"Apple, Inc."}, {"Black Hills Corp."}, {"SEEK Ltd."}
                };
    }

    @DataProvider(name = "orbisIDWithDisclosureScore")
    public Object[][] orbisIDWithDisclosureScore() {
        return new Object[][]
                {
                        {"002878599"},
                        {"300411633"},
                        {"051057030"},
                        {"138501539"},
                        {"481390423"},
                        {"233773078"},
                        {"105961081"},
                        /*    {"387796123"},
                            {"380582526"},
                            {"172031911"},
                            {"377084526"},
                            {"133050503"},
                            {"482794570"},
                            {"480796820"},
                            {"486521987"},*/
                };
    }

    @DataProvider(name = "ESGMaterialitycredentials11")
    public Object[][] provideESGCredentials() {

        return new Object[][]
                {
                        {"mesg-platform-issuer-qa+test1@outlook.com", "Moodys123"}
                };
    }

    @DataProvider(name = "Company With Orbis ID")
    public Object[][] companyWithOrbisID() {

        return new Object[][]{

                {"Apple, Inc.", "000411117"},  // VE scored company
                {"FirstCash, Inc.","001668460"} // MESG scored Company

        };
    }
}
