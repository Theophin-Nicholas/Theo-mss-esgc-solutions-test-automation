package com.esgc.Utilities;


import com.opencsv.CSVReader;
import org.apache.poi.ss.usermodel.*;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/*
 * This is a utility class for reading from writing to excel files.
 * it works with xls and xlsx files.
 */

public class ExcelUtil {

    private Sheet workSheet;
    private Workbook workBook;
    private String path;

    public ExcelUtil(String path, String sheetName) {
        this.path = path;
        try {
            FileInputStream ExcelFile = new FileInputStream(path);
            workBook = WorkbookFactory.create(ExcelFile);
            workSheet = workBook.getSheet(sheetName);
            Assert.assertNotNull(workSheet, String.format("Sheet: '%s' does not exist", sheetName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the data of specific cell
     *
     * @param rowNum starting with 0
     * @param colNum starting with 0
     * @return
     */
    public String getCellData(int rowNum, int colNum) {
        Cell cell;
        try {
            cell = workSheet.getRow(rowNum).getCell(colNum);
            String cellData = cell.toString();
            return cellData;
        } catch (Exception e) {
            //throw new RuntimeException(e);
            return null;
        }
    }

    //This method checking the text from first column only
    public Cell searchFirstColumnCellData(String text) {
        for (Row row : workSheet) {
            Cell cell = row.getCell(0);
            String cellValue = String.valueOf(cell.getCellType());
            if (cellValue.equalsIgnoreCase("NUMERIC")) {
                System.out.println("### " + cell.getNumericCellValue());
                if (text.equals(String.valueOf(cell.getNumericCellValue())))
                    return cell;
            } else {
                System.out.println(cell.getStringCellValue());
                if (text.equals(String.valueOf(cell.getStringCellValue())))
                    return cell;
            }
        }
        return null;
    }

    //This method will search the given text from entire sheet
    public Cell searchCellData(String text) {
        for (Row row : workSheet) {
            for (Cell cell : row) {
                String cellValue = String.valueOf(cell.getCellType());
                if (cellValue.equalsIgnoreCase("NUMERIC")) {
                    if (text.equals(String.valueOf(cell.getNumericCellValue())))
                        return cell;
                } else {
                    if (text.equals(cell.getStringCellValue()))
                        return cell;
                }
            }
        }
        return null;
    }


    public String[][] getDataArray() {

        String[][] data = new String[rowCount()][columnCount()];

        for (int i = 0; i < rowCount(); i++) {
            for (int j = 0; j < columnCount(); j++) {
                String value = getCellData(i, j);
                data[i][j] = value;
            }
        }
        return data;

    }

    /**
     * Get data as a List<Map<<String, String>>, where key name represents column name
     *
     * @return
     */
    public List<Map<String, String>> getDataList() {
        // get all columns
        List<String> columns = getColumnsNames();
        // this will be returned
        List<Map<String, String>> data = new ArrayList<>();

        for (int i = 1; i < rowCount(); i++) {
            // get each row
            Row row = workSheet.getRow(i);
            // create map of the row using the column and value
            // column map key, cell value --> map bvalue
            Map<String, String> rowMap = new HashMap<String, String>();
            for (Cell cell : row) {
                int columnIndex = cell.getColumnIndex();
                rowMap.put(columns.get(columnIndex), cell.toString());
            }

            data.add(rowMap);
        }

        return data;
    }

    /**
     * Get column names
     *
     * @return columns
     */
    public List<String> getColumnsNames() {
        List<String> columns = new ArrayList<>();

        for (Cell cell : workSheet.getRow(0)) {
            columns.add(cell.toString());
        }
        return columns;
    }


    public int columnCount() {
        return workSheet.getRow(0).getLastCellNum();
    }

    public int rowCount() {

        return workSheet.getPhysicalNumberOfRows();
    }

    public void close() {
        try {
            this.workBook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPortfolioNameFromCSVFile(String csvFilename) {

        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(csvFilename));
        } catch (FileNotFoundException e) {
            System.out.println("File is not found");
            e.printStackTrace();
        }
        String[] nextLine;
        String searchWord = "!Name";
        try {
            while ((nextLine = reader.readNext()) != null) {
                for (int i = 0; i < nextLine.length; i++) {
                    if (nextLine[i].matches(searchWord)) {
                        return nextLine[i + 1];
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean checkIf2CSVFilesAreSame(String csvFilePath1, String csvFilePath2) {

        List<String[]> allLines1 = null;
        List<String[]> allLines2 = null;
        try (CSVReader reader1 = new CSVReader(new FileReader(csvFilePath1));
             CSVReader reader2 = new CSVReader(new FileReader(csvFilePath2))) {
            allLines1 = reader1.readAll();
            allLines2 = reader2.readAll();
        } catch (FileNotFoundException e) {
            System.out.println("File is not found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("No Line");
            e.printStackTrace();
        }
        if (allLines1.size() != allLines2.size()) return false;

        for (int i = 0; i < allLines1.size(); i++) {
            if (!Arrays.equals(allLines1.get(i), allLines2.get(i))) {
                System.out.println("Test Fail in Line:" + (i + 1));
                System.out.println("Expected:");
                System.out.println(Arrays.toString(allLines1.get(i)));
                System.out.println("Actual:");
                System.out.println(Arrays.toString(allLines2.get(i)));
                return false;
            }
        }
        return true;
    }

    public List<String> getColumnData(int columnIndex) {
        // get all columns
        List<String> columns = getColumnsNames();
        // this will be returned
        List<String> data = new ArrayList<>();

        for (int i = 1; i < rowCount(); i++) {
            // get each row
            Cell cell = workSheet.getRow(i).getCell(columnIndex);
            data.add(cell.getStringCellValue());
            }
        return data;
    }

    public boolean isSheetExist(String sheetName) {
        for (Sheet sheet : workBook) {
            if (sheet.getSheetName().equals(sheetName)) {
                return true;
            }
        }
        return false;
    }
}