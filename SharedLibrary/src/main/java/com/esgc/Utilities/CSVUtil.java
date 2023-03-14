package com.esgc.Utilities;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

public class CSVUtil {
    public static CSVReader reader;
    public static CSVWriter writer;
    public static String path;
    public static String fileName;
    public CSVUtil(String path, String fileName) {
        this.path = path;
        this.fileName = fileName;
        File dir = new File(path);
        File[] dir_contents = dir.listFiles();
        assert dir_contents != null;
        try {
            if(Arrays.stream(dir_contents).anyMatch(e -> (e.getName().contains(fileName)))) {
                reader = new CSVReader(new FileReader(Arrays.stream(dir_contents).filter(e -> (e.getName().contains(fileName))).findFirst().get()));
                //writer = new CSVWriter(new FileWriter(Arrays.stream(dir_contents).filter(e -> (e.getName().contains(fileName))).findFirst().get()));
            }
            else System.out.println("File not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //get column names
    public List<String> getColumnNames() {
        List<String> columnNames = null;
        try {
            columnNames = Arrays.asList(reader.readNext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return columnNames;
    }

    //get all data
    public List<String[]> getAllData() {
        List<String[]> allData = null;
        setReader();
        try {
            allData = reader.readAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allData;
    }

    //get number of rows
    public int getNumberOfRows() {
        List<String[]> allData = null;
        try {
            allData = getAllData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allData.size();
    }

    //get number of columns
    public int getNumberOfColumns() {
        List<String> columnNames = null;
        try {
            columnNames = Arrays.asList(reader.readNext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return columnNames.size();
    }

    //get data by row number
    public String[] getDataByRowNumber(int rowNumber) {
        List<String[]> allData = null;
        try {
            allData = getAllData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allData.get(rowNumber);
    }

    //get cell data by row number and column number
    public String getCell(int rowNumber, int columnNumber) {
        List<String[]> allData = null;
        try {
            allData = getAllData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(allData != null)
            return allData.get(rowNumber)[columnNumber];
        return null;
    }

    //set cell data by row number and column number
    public void setCell(int rowNumber, int columnNumber, String value) {
        List<String[]> allData = null;
        try {
            allData = getAllData();
            //allData.forEach(e -> System.out.println(Arrays.toString(e)));
            allData.get(rowNumber)[columnNumber] = value;
            reader.close();
            setWriter();
            writer.writeAll(allData);
            writer.close();
            setReader();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //set Writer
    public void setWriter(){
        File dir = new File(path);
        File[] dir_contents = dir.listFiles();
        assert dir_contents != null;
        try {
            if(Arrays.stream(dir_contents).anyMatch(e -> (e.getName().contains(fileName)))) {
                //reader = new CSVReader(new FileReader(Arrays.stream(dir_contents).filter(e -> (e.getName().contains(fileName))).findFirst().get()));
                writer = new CSVWriter(new FileWriter(Arrays.stream(dir_contents).filter(e -> (e.getName().contains(fileName))).findFirst().get()));
            }
            else System.out.println("File not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //set Reader
    public void setReader(){
        File dir = new File(path);
        File[] dir_contents = dir.listFiles();
        assert dir_contents != null;
        try {
            if(Arrays.stream(dir_contents).anyMatch(e -> (e.getName().contains(fileName)))) {
                reader = new CSVReader(new FileReader(Arrays.stream(dir_contents).filter(e -> (e.getName().contains(fileName))).findFirst().get()));
//                writer = new CSVWriter(new FileWriter(Arrays.stream(dir_contents).filter(e -> (e.getName().contains(fileName))).findFirst().get()));
            }
            else System.out.println("File not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
