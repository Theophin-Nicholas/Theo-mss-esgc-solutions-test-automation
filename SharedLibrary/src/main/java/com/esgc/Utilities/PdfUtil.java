package com.esgc.Utilities;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;

/*
 * This is a utility class for reading from pdf files.
 */

public class PdfUtil {

    public static String getPdfContent(String filePath) {

        try {
            File file = new File(filePath);
            PDDocument document = PDDocument.load(file);

            //Instantiate PDFTextStripper class
            PDFTextStripper pdfStripper = new PDFTextStripper();
            //  pdfStripper.setSortByPosition(true);
            pdfStripper.setStartPage(0);
            pdfStripper.setEndPage(document.getNumberOfPages());
            //Retrieving text from PDF document
            String text = pdfStripper.getText(document);

            //Closing the document
            document.close();

            return text;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getPdfContent(String filePath, int pageNumber) {

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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getNumberOfPages(String filePath) {
        try {
            File file = new File(filePath);
            PDDocument document = PDDocument.load(file);
            int pagesCount = document.getNumberOfPages();
            document.close();
            return pagesCount;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String extractPDFText(String pdfFileInText, String strStartIndentifier, String strEndIdentifier) {
        String returnString = "";
        try {
            String strStart = strStartIndentifier;
            String strEnd = strEndIdentifier;
            int startInddex = pdfFileInText.indexOf(strStart);
            int endInddex = pdfFileInText.indexOf(strEnd);
            returnString = pdfFileInText.substring(startInddex, endInddex) + strEnd;

        } catch (Exception e) {
            returnString = "No ParaGraph Found";
        }
        return returnString;
    }

    public static String extractPDFText(String pdfFileInText, String str) {
        String returnString = "";
        try {
            pdfFileInText = pdfFileInText.replaceAll("\n", " ");
            int startInddex = pdfFileInText.indexOf(str);
            returnString = pdfFileInText.substring(startInddex, startInddex + str.length());

        } catch (Exception e) {
            returnString = "No ParaGraph Found";
        }
        return returnString;
    }

    public static int getCountofTextOccurrencesInPdf(String pdfFileInText, String str) {
        String returnString = "";
        int count = 0;
        try {
            pdfFileInText = pdfFileInText.replaceAll("\n", " ");
            int index = 0;
            while (index != -1) {
                index = pdfFileInText.indexOf(str, index + str.length());
                if (index != -1) {
                    count++;
                }

            }
        } catch (Exception e) {
            returnString = "No ParaGraph Found";
        }
        return count;
    }
}