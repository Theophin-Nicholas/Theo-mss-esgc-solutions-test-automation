package com.esgc.Tests.UI.DashboardPage.Export;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class ExportUtils {

    public List<Map<String,String>> convertExcelToNestedMap(String ExcelFileName) {

        List<Map<String,String>> parentMap = new ArrayList<Map<String,String>>();

        try {
            File file = new File(ExcelFileName);
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            Row headerRow = rowIterator.next();

            while( rowIterator.hasNext() )
            {
                Map<String, String> childMap = new HashMap<String, String>();
                Row row = rowIterator.next();
                for(int i=0; i<headerRow.getLastCellNum(); i++) {
                    String propertyName = headerRow.getCell(i).getStringCellValue();
                    String propertyValue = "";
                    if(row.getCell(i)!=null)
                        propertyValue = new DataFormatter().formatCellValue(row.getCell(i));
                    childMap.put(propertyName, propertyValue);
                }
                parentMap.add(childMap);
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parentMap;
    }

    public boolean compare(Object dbValue, String excelValue){
        String strdbValue = "";
        String strExcelValue = "";
        if(dbValue!=null) {
            strdbValue = dbValue.toString()
                    .replace("_x000D_", "");
            if(strdbValue.equals("-1")) strdbValue="NI";
        }
        if(excelValue!=null) {
            strExcelValue = excelValue.replace("_x000D_", "");
        }
        boolean result = strdbValue.equals(strExcelValue);
        System.out.println("Compare: "+dbValue+" --> "+excelValue+" : "+result);
        return result;
    }

    public boolean compareResearchData(Object dbValue, String excelValue){
        String strdbValue = "";
        String strExcelValue = "";
        if(dbValue==null) {
            strdbValue = "0";
        }else{
            strdbValue = dbValue.toString();
        }
        if(excelValue==null) {
            strExcelValue = "0";
        }else if(excelValue.equals("None")){
            strExcelValue = "0";
        }else{
            strExcelValue = excelValue;
        }
        boolean result = strdbValue.equals(strExcelValue);
        System.out.println("Compare: "+dbValue+" --> "+excelValue+" : "+result);
        return result;
    }

    public boolean compareDoubleValue(String dbValue, String excelValue){
        boolean result = Double.valueOf(dbValue)-Double.valueOf(excelValue)==0;
        System.out.println("Compare: "+dbValue+" --> "+excelValue+" : "+result);
        return result;
    }

    public boolean compareFFI(Object dbThresholdValue, Object dbActualValue, String excelValue){
        String dbValue = "";
        if(dbThresholdValue == null){
            dbValue = "Y";
        }else if(dbThresholdValue != null && String.valueOf(dbThresholdValue).equals("0%")){
            dbValue = "N";
        }else{
            dbValue = "Y";
        }
        boolean result = excelValue.equals(dbValue);
        System.out.println("Compare: "+dbValue+" --> "+excelValue+" : "+result);
        return result;
    }

    public String convertToDecimal(String value){
        //if(value != null && value.length() != 0 && !value.contains(".")) value = value+".00";
        if(value.equals("0")) value = value+".00";
        return value;
    }

}
