package com.esgc.Utilities.API;

public class ErrorMessages {

    public static String INVALID_CURRENCY_ERROR_MESSAGE = "Import incomplete. Please enter all values as described in the template. Currency can be USD, GBP, or EUR.";
    public static String EMPTY_CURRENCY_ERROR_MESSAGE = "Import incomplete. Please enter all values as described in the template. Empty currency.";
    public static String NO_IDENTIFIER_ERROR_MESSAGE = "Import incomplete. Please enter all values as described in the template.";
    public static String EMPTY_IDENTIFIER_ERROR_MESSAGE = "Import incomplete. Please enter all values as described in the template. Value cannot be empty in line 15,17.";
    public static String INVALID_IDENTIFIER_VALUE_ERROR_MESSAGE = "Import incomplete. Please enter all values as described in the template. Non-numeric/Special characters in Identifier values";
    public static String MISSING_IDENTIFIER_ERROR_MESSAGE = "Import incomplete. Please enter all values as described in the template. Identifier cannot be empty in line 13.";
    public static String INVALID_DATE_ERROR_MESSAGE = "Import incomplete. Please enter all values as described in the template. AsofDate must be in a valid format.";
    public static String NO_HEADER_ERROR_MESSAGE = "Server Error has Occurred with the missing Key --> 'Identifier'";
    public static String INVALID_HEADER_ERROR_MESSAGE = "Import incomplete. Please enter all values as described in the template.";
    public static String VALUE_MISSING_HEADER_ERROR_MESSAGE = "Server Error has Occurred with the missing Key --> 'Value'";
    public static String INVALID_FILE_ERROR_MESSAGE = "Import incomplete. Please upload a .CSV formatted file.";
    public static String EMPTY_FILE_ERROR_MESSAGE = "Import incomplete. This file appears to be empty, please check your file.";
    public static String INVALID_COLUMN_ERROR_MESSAGE = "Server Error has Occurred with the missing Key --> 'Identifier type'";
    public static String INVALID_COLUMN_ERROR_MESSAGE2 = "Server Error has Occurred with the missing Key --> 'Identifier'";
    public static String CHECK_DOCUMENT_ERROR_MESSAGE = "Something has happened. Please try again or upload a new portfolio.";
    public static String SEVERAL_MISSING_ERROR_MESSAGE = "Import incomplete. Please enter all values as described in the template. Currency can be USD, GBP, or EUR. Identifier cannot be empty in line 20. Value cannot be empty in line 13.";
    public static String MISSING_VALUE_ERROR_MESSAGE = "Import incomplete. Please enter all values as described in the template. Value cannot be empty in line 15,17.";
    public static String All_UNMATCHED_IDENTIFIERS_ERROR_MESSAGE =  "0/5 Entities in your portfolio found in our coverage. Please upload a different portfolio or check your file to try again.";
}
