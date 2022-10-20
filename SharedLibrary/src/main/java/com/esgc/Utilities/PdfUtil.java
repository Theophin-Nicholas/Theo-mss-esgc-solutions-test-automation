package com.esgc.Utilities;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

/*
 * This is a utility class for reading from writing to excel files.
 * it works with xls and xlsx files.
 */

public class PdfUtil {

    public static String getPdfContent(String filePath){

        try {
            File file = new File(filePath);
            PDDocument document = PDDocument.load(file);

            //Instantiate PDFTextStripper class
            PDFTextStripper pdfStripper = new PDFTextStripper();

            //Retrieving text from PDF document
            String text = pdfStripper.getText(document);

            //Closing the document
            document.close();

            return text;
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public static String getPdfContent(String filePath, int pageNumber){

        try {
            File file = new File(filePath);
            PDDocument document = PDDocument.load(file);

            //Instantiate PDFTextStripper class
            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfStripper.setStartPage(pageNumber);
            pdfStripper.setEndPage(pageNumber);

            //Retrieving text from PDF document
            String text = pdfStripper.getText(document);
            System.out.println(text);

            //Closing the document
            document.close();

            return text;
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public static int getNumberOfPages(String filePath){
        try {
            File file = new File(filePath);
            PDDocument document = PDDocument.load(file);
            int pagesCount = document.getNumberOfPages();
            document.close();
            return pagesCount;
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }


}