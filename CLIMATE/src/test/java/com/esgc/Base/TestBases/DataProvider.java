package com.esgc.Base.TestBases;

import static com.esgc.Utilities.ErrorMessages.*;

public class DataProvider {

    @org.testng.annotations.DataProvider(name = "ErrorMessages")
    public Object[][] dpMethod2() {

        return new Object[][]{
                {"InvalidTemplate.csv", INVALID_TEMPLATE_ERROR_MESSAGE, 10606},//498
                {"IncompletePortfolio.csv", INCOMPLETE_PORTFOLIO_ERROR_MESSAGE, 10606},//498
                {"InvalidCurrencyInPortfolio.csv", INVALID_CURRENCY_ERROR_MESSAGE, 498},//498
                {"InvalidCurrencyCodeInPortfolio.csv", INVALID_CURRENCY_ERROR_MESSAGE, 498},//498
                {"InvalidCurrencyCodeInPortfolio2.csv", INVALID_CURRENCY_ERROR_MESSAGE, 3047},
                {"NoIdentifierInPortfolio.csv", NO_IDENTIFIER_ERROR_MESSAGE, 504, 839},//504//839
                {"EmptyIdentifier.csv", EMPTY_IDENTIFIER_ERROR_MESSAGE, 504, 839},//504//839
                {"InvalidIdentifierValue.csv", INVALID_IDENTIFIER_VALUE_ERROR_MESSAGE, 506, 837},//506//837
                {"MissingIdentifier.csv", MISSING_IDENTIFIER_ERROR_MESSAGE, 504, 839},//504//839
//                {"InvalidDate.csv", INVALID_DATE_ERROR_MESSAGE, 512, 836},//512/836
//                {"InvalidDate2.csv", INVALID_DATE_ERROR_MESSAGE, 512, 836},//512/836
//                {"InvalidDate3.csv", INVALID_DATE_ERROR_MESSAGE, 512, 836},//512/836
                {"NoHeader.csv", INVALID_COLUMN_ERROR_MESSAGE, 520, 831},//520//831
                {"InvalidHeader.csv", INVALID_HEADER_ERROR_MESSAGE, 520},//520
                {"ValueMissingHeader.csv", INVALID_COLUMN_ERROR_MESSAGE, 520},//520
                {"ValueMissingHeader2.csv", CHECK_DOCUMENT_ERROR_MESSAGE, 520},//520
                {"InvalidColumn.csv", INVALID_COLUMN_ERROR_MESSAGE, 520},//520
                {"InvalidFile.json", INVALID_FILE_ERROR_MESSAGE, 507, 815, 4154},//507,815, 4154
                {"InvalidFile.txt", INVALID_FILE_ERROR_MESSAGE, 507, 815, 4154},//507,815, 4154
                {"EmptyFile.csv", EMPTY_FILE_ERROR_MESSAGE, 524},//524
                {"SeveralMissingFields.csv", SEVERAL_MISSING_ERROR_MESSAGE, 819},//819 several missing fields
                {"MissingValue.csv", MISSING_VALUE_ERROR_MESSAGE, 840},//840 value missing
                {"AllUnmatchedIdentifiers.csv", All_UNMATCHED_IDENTIFIERS_ERROR_MESSAGE, 984}//all value unmatched

        };
    }
}
