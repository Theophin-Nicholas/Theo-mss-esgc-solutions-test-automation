package com.esgc.DBModels;

public class IdentifierQueryModelFactory {


    public static IdentifierQueryModel getIdentifierQueryModel(String researchLine) {
        return getIdentifierQueryModel(researchLine, "", "");
    }

    ;

    public static IdentifierQueryModel getIdentifierQueryModel(String researchLine, String month, String year) {
        IdentifierQueryModel identifierQueryModel = new IdentifierQueryModel();
        switch (researchLine) {
            case "TCFD":
                identifierQueryModel.setEntityIdColumnName("BVD9_NUMBER");
                identifierQueryModel.setScoreColumnName("GS_TCFD_STGY_TOTAL");
                identifierQueryModel.setPreviousScoreColumnName("PREVIOUS_TOTAL_SCORE");
                identifierQueryModel.setPreviusProducedDateColumnName("PREVIOUS_PRODUCED_DATE");
                identifierQueryModel.setTableName("TCFD_STRATEGY WHERE MONTH='" + month + "' AND YEAR='" + year + "'");
                return identifierQueryModel;

            case "Brown Share":
                identifierQueryModel.setEntityIdColumnName("BVD9_NUMBER");
                identifierQueryModel.setScoreColumnName("BS_FOSF_INDUSTRY_REVENUES_ACCURATE");
                identifierQueryModel.setPreviousScoreColumnName("BS_PREVIOUS_FOSF_INDUSTRY_REVENUES_ACCURATE");
                identifierQueryModel.setPreviusProducedDateColumnName("PREVIOUS_PRODUCED_DATE");
                identifierQueryModel.setTableName("BROWN_SHARE WHERE MONTH='" + month + "' AND YEAR='" + year + "'");
                return identifierQueryModel;

            case "Physical Risk Management":
            case "physicalriskhazard":
                identifierQueryModel.setEntityIdColumnName("BVD9_NUMBER");
                identifierQueryModel.setScoreColumnName("GS_PH_RISK_MGT_TOTAL");
                identifierQueryModel.setPreviousScoreColumnName("PREVIOUS_TOTAL_SCORE");
                identifierQueryModel.setPreviusProducedDateColumnName("PREVIOUS_PRODUCED_DATE");
                identifierQueryModel.setTableName("PHYSICAL_RISK_MANAGEMENT WHERE MONTH='" + month + "' AND YEAR='" + year + "'");
                return identifierQueryModel;

            case "operationsrisk":
                if (!month.equals("12")) {
                    year = (Integer.parseInt(year) - 1) + "";
                }
                identifierQueryModel.setEntityIdColumnName("ENTITY_ID_BVD9");
                identifierQueryModel.setScoreColumnName("OPERATIONS_RISK_SCORE");
                identifierQueryModel.setTableName("ENTITY_SCORE WHERE RELEASE_MONTH='12' AND RELEASE_YEAR='" + year + "'");
                return identifierQueryModel;

            case "marketrisk":
                if (!month.equals("12")) {
                    year = (Integer.parseInt(year) - 1) + "";
                }
                identifierQueryModel.setEntityIdColumnName("ENTITY_ID_BVD9");
                identifierQueryModel.setScoreColumnName("MARKET_RISK_SCORE");
                identifierQueryModel.setTableName("ENTITY_SCORE WHERE RELEASE_MONTH='12' AND RELEASE_YEAR='" + year + "'");
                return identifierQueryModel;

            case "supplychainrisk":
                if (!month.equals("12")) {
                    year = (Integer.parseInt(year) - 1) + "";
                }
                identifierQueryModel.setEntityIdColumnName("ENTITY_ID_BVD9");
                identifierQueryModel.setScoreColumnName("SUPPLY_CHAIN_RISK_SCORE");
                identifierQueryModel.setTableName("ENTITY_SCORE WHERE RELEASE_MONTH='12' AND RELEASE_YEAR='" + year + "'");
                return identifierQueryModel;

            case "Carbon Footprint":
                identifierQueryModel.setEntityIdColumnName("BVD9_NUMBER");
                identifierQueryModel.setScoreColumnName("CARBON_FOOTPRINT_VALUE_TOTAL");
                identifierQueryModel.setPreviousScoreColumnName("PREVIOUS_VALUE_TOTAL");
                identifierQueryModel.setPreviusProducedDateColumnName("PREVIOUS_PRODUCED_DATE");
                identifierQueryModel.setTableName("CARBON_FOOTPRINT   WHERE MONTH='" + month + "' AND YEAR='" + year + "'");
                return identifierQueryModel;

            case "Energy Transition Management":
                identifierQueryModel.setEntityIdColumnName("BVD9_NUMBER");
                identifierQueryModel.setScoreColumnName("GS_ENERGY_TRANSITION_TOTAL");
                identifierQueryModel.setPreviousScoreColumnName("PREVIOUS_TOTAL_SCORE");
                identifierQueryModel.setPreviusProducedDateColumnName("PREVIOUS_PRODUCED_DATE");
                identifierQueryModel.setTableName("ENERGY_TRANSITION WHERE MONTH='" + month + "' AND YEAR='" + year + "'");
                return identifierQueryModel;

            case "Green Share":
                identifierQueryModel.setEntityIdColumnName("BVD9_NUMBER");
                identifierQueryModel.setScoreColumnName("GS_OVERALL_ASSESSMENT_ESTIMATE_OF_INCORPORATION");
                identifierQueryModel.setPreviousScoreColumnName("PREVIOUS_GS_OVERALL_ASSESSMENT_ESTIMATE_OF_INCORPORATION");
                identifierQueryModel.setPreviusProducedDateColumnName("PREVIOUS_PRODUCED_DATE");
                identifierQueryModel.setTableName("GREEN_SHARE WHERE MONTH='" + month + "' AND YEAR='" + year + "'");
                return identifierQueryModel;
            case "Temperature Alignment":
                identifierQueryModel.setEntityIdColumnName("BVD9_NUMBER");
                identifierQueryModel.setScoreColumnName("TEMPERATURE_SCORE_ACTUAL");
                identifierQueryModel.setPreviousScoreColumnName("PREV_TEMPERATURE_SCORE_ACTUAL");
                identifierQueryModel.setPreviusProducedDateColumnName("PREVIOUS_PRODUCED_DATE");
                identifierQueryModel.setTableName("TEMPERATURE_ALIGNMENT WHERE MONTH='" + month + "' AND YEAR='" + year + "'");
                return identifierQueryModel;
        }
        throw new IndexOutOfBoundsException("Research line not found");
    }

}
