package com.esgc.Test.TestBases;

import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.DataFinder;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IssuerDataProviderClass {

    @DataProvider(name = "credentialsP3")
    public Object[][] credentialsP3() {
        Map<String,String> params = new HashMap<>();
        params.put("Page","P3");
        params.put("Entitlement","credentialsP3");
        List<String> requiredCols= Arrays.asList(new String[]{"UserName","Password","OrbisID"});
        return getData (params,requiredCols);
    }

    @DataProvider(name = "credentialsP2")
    public Object[][] credentialsP2() {

        Map<String,String> params = new HashMap<>();
        params.put("Page","P2");
        params.put("Entitlement","credentialsP2");
        List<String> requiredCols= Arrays.asList(new String[]{"UserName","Password","OrbisID"});
        return getData (params,requiredCols);
    }

    @DataProvider(name = "loginP2")
    public Object[][] loginP2() {
        Map<String,String> params = new HashMap<>();
        params.put("Page","P2");
        params.put("Entitlement","login");
        List<String> requiredCols= Arrays.asList(new String[]{"UserName","Password"});
        return getData (params,requiredCols);
    }
    @DataProvider(name = "loginP3")
    public Object[][] loginP3() {
        Map<String,String> params = new HashMap<>();
        params.put("Page","P3");
        params.put("Entitlement","login");
        List<String> requiredCols= Arrays.asList(new String[]{"UserName","Password"});
        return getData (params,requiredCols);
    }


    @DataProvider(name = "RoutingCheck")
    public Object[][] RoutingCheck() {
        List<String> requiredCols= Arrays.asList(new String[]{"UserName","Password","coverage_status","Page"});
        Map<String,String> params = new HashMap<>();
        params.put("Page","P2");
        params.put("Entitlement","credentialsP2");
        Object[][] a = getData (params,requiredCols);

        Map<String,String> params1 = new HashMap<>();
        params1.put("Page","P3");
        params1.put("Entitlement","credentialsP3");
        Object[][] b =  getData (params1,requiredCols);

        return  BrowserUtils.appendedArrays(a,b);
    }

    //Method for below dataprovider is not in use
    @DataProvider(name = "orbisID")
    public Object[][] JustOrbisID() {

        return new Object[][]{
                {"058618674"}
        };
    }
    @DataProvider(name = "ESGMaterialitycredentials")
    public Object[][] ESGMaterialityCredentials() {

        List<String> requiredCols= Arrays.asList(new String[]{"UserName","Password","coverage_status","Page"});
        Map<String,String> params = new HashMap<>();
       // params.put("Page","P2");
        params.put("Entitlement","ESGMaterialitycredentials");
        return getData (params,requiredCols);

    }
    //Method for below dataprovider is not in use
    @DataProvider(name = "CompaniesWithMESGScore")
    public Object[][] CompaniesWithMESGScore() {

        return new Object[][]
                {
                        /*{"mesg-platform-issuer-qa+test40@outlook.com", "Moodys123", "007091964"}*/

                        // production
                        {"mesg-platform-testing+metrics6@outlook.com", "Moodys123", "000100504"}
                };

    }

    public Object[][] getData(Map<String,String> params, List<String> requiredCols ){
        DataFinder finder = new DataFinder();
        return finder.getData(BrowserUtils.DataSourcePath()+ File.separator +"Accounts.xlsx",params,requiredCols);
    }



}
