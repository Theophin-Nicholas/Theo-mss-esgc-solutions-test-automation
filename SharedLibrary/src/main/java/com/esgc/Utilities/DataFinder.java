package com.esgc.Utilities;

import java.util.List;
import java.util.Map;

public class DataFinder {

    public Object[][] getData(String excelPath,  Map<String, String> params, List<String> requiredColumns) {

        String tabName = Environment.environment;
        ExcelUtil excel = new ExcelUtil(excelPath, tabName);
        int rows = excel.rowCount();
        int cols = requiredColumns.get(0).equals("All")? excel.columnCount():requiredColumns.size();
        int i = 0;
        Object data[][] = new Object[rows][cols];
        String conditions = "";
        for (int rowNum = 1; rowNum < rows; rowNum++) {
            boolean flag = false;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (excel.getCellData(rowNum, excel.getColumnNum(entry.getKey())).equalsIgnoreCase(entry.getValue())) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                i++;
                    for (int colNum = 0; colNum < cols; colNum++) {
                        int coln = requiredColumns.get(0).equals("All")? colNum:excel.getColumnNum(requiredColumns.get(colNum));
                        data[i][colNum] = (excel.getCellData(rowNum, coln));
                        }
                    }
            }
        Object[][] returnObject = new Object[i][cols];
        int rno = i;
        for (int r = 0; r < data.length; r++) {
            if (data[r][0] != null) {
                returnObject[i - rno] = data[r];
                rno--;
            }
        }
        return returnObject;
    }
}
