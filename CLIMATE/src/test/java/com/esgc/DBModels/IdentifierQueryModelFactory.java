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
                identifierQueryModel.setEntityIdColumnName("ENTITY_ID_BVD9");
                identifierQueryModel.setScoreColumnName("OPERATIONS_RISK_SCORE");
                identifierQueryModel.setTableName("ENTITY_SCORE WHERE RELEASE_YEAR||RELEASE_MONTH<='" + year + month + "' \n" +
                        "       QUALIFY ROW_NUMBER() OVER (PARTITION BY ENTITY_ID_BVD9 ORDER BY RELEASE_YEAR DESC, RELEASE_MONTH DESC) =1");
                return identifierQueryModel;

            case "marketrisk":
                identifierQueryModel.setEntityIdColumnName("ENTITY_ID_BVD9");
                identifierQueryModel.setScoreColumnName("MARKET_RISK_SCORE");
                identifierQueryModel.setTableName("ENTITY_SCORE WHERE RELEASE_YEAR||RELEASE_MONTH<='" + year + month + "' \n" +
                        "       QUALIFY ROW_NUMBER() OVER (PARTITION BY ENTITY_ID_BVD9 ORDER BY RELEASE_YEAR DESC, RELEASE_MONTH DESC) =1");
                return identifierQueryModel;

            case "supplychainrisk":
                identifierQueryModel.setEntityIdColumnName("ENTITY_ID_BVD9");
                identifierQueryModel.setScoreColumnName("SUPPLY_CHAIN_RISK_SCORE");
                identifierQueryModel.setTableName("ENTITY_SCORE WHERE RELEASE_YEAR||RELEASE_MONTH<='" + year + month + "' \n" +
                        "       QUALIFY ROW_NUMBER() OVER (PARTITION BY ENTITY_ID_BVD9 ORDER BY RELEASE_YEAR DESC, RELEASE_MONTH DESC) =1");
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
            case "ESG":
                identifierQueryModel.setEntityIdColumnName("eos.ORBIS_ID");
                identifierQueryModel.setPreviousScoreColumnName("EOS.RESEARCH_LINE_ID, NULL");
                identifierQueryModel.setScoreColumnName("eos.VALUE");
                identifierQueryModel.setTableName(" entity_coverage_tracking ect  \n" +
                        "join ESG_OVERALL_SCORES eos on ect.orbis_id=eos.orbis_id and data_type = 'esg_pillar_score'  " +
                        "and sub_category = 'ESG' and eos.year || eos.month <= '" + year + month + "' \n" +
                        " where  coverage_status = 'Published' and publish = 'yes'\n" +
                        " qualify row_number() OVER (PARTITION BY eos.orbis_id ORDER BY eos.year DESC, eos.month DESC, eos.scored_date DESC) =1");
                return identifierQueryModel;
        }
        throw new IndexOutOfBoundsException("Research line not found");
    }

}
