package com.esgc.Base.TestBases;

import static com.esgc.Utilities.ErrorMessages.*;

public class DataProvider {

    @org.testng.annotations.DataProvider(name = "ErrorMessages")
    public Object[][] dpMethod2() {

        return new Object[][]{
                {"InvalidTemplate.csv", INVALID_TEMPLATE_ERROR_MESSAGE, 4399},//4956
                {"IncompletePortfolio.csv", INCOMPLETE_PORTFOLIO_ERROR_MESSAGE, 4399},//4956
                {"InvalidCurrencyInPortfolio.csv", INVALID_CURRENCY_ERROR_MESSAGE, 4956},//4956
                {"InvalidCurrencyCodeInPortfolio.csv", INVALID_CURRENCY_ERROR_MESSAGE, 4956},//4956
                {"InvalidCurrencyCodeInPortfolio2.csv", INVALID_CURRENCY_ERROR_MESSAGE, 4843},
                {"NoIdentifierInPortfolio.csv", NO_IDENTIFIER_ERROR_MESSAGE, 4628, 4856},//4628//4856
                {"EmptyIdentifier.csv", EMPTY_IDENTIFIER_ERROR_MESSAGE, 4628, 4856},//4628//4856
                {"InvalidIdentifierValue.csv", INVALID_IDENTIFIER_VALUE_ERROR_MESSAGE, 4467, 5070},//4467//5070
                {"MissingIdentifier.csv", MISSING_IDENTIFIER_ERROR_MESSAGE, 4628, 4856},//4628//4856
//                {"InvalidDate.csv", INVALID_DATE_ERROR_MESSAGE, 512, 836},//512/836
//                {"InvalidDate2.csv", INVALID_DATE_ERROR_MESSAGE, 512, 836},//512/836
//                {"InvalidDate3.csv", INVALID_DATE_ERROR_MESSAGE, 512, 836},//512/836
                {"NoHeader.csv", INVALID_COLUMN_ERROR_MESSAGE, 5055, 4859},//5055//4859
                {"InvalidHeader.csv", INVALID_HEADER_ERROR_MESSAGE, 5055},//5055
                {"ValueMissingHeader.csv", INVALID_COLUMN_ERROR_MESSAGE, 5055},//5055
                {"ValueMissingHeader2.csv", CHECK_DOCUMENT_ERROR_MESSAGE, 5055},//5055
                {"InvalidColumn.csv", INVALID_COLUMN_ERROR_MESSAGE, 5055},//5055
                {"InvalidFile.json", INVALID_FILE_ERROR_MESSAGE, 5023, 3522, 4876},//5023,3522, 4876
                {"InvalidFile.txt", INVALID_FILE_ERROR_MESSAGE, 5023, 3522, 4876},//5023,3522, 4876
                {"EmptyFile.csv", EMPTY_FILE_ERROR_MESSAGE, 5052},//5052
                {"SeveralMissingFields.csv", SEVERAL_MISSING_ERROR_MESSAGE, 5025},//5025 several missing fields
                {"MissingValue.csv", MISSING_VALUE_ERROR_MESSAGE, 4551},//4551 value missing
                {"AllUnmatchedIdentifiers.csv", All_UNMATCHED_IDENTIFIERS_ERROR_MESSAGE, 1732}//all value unmatched

        };
    }
}
