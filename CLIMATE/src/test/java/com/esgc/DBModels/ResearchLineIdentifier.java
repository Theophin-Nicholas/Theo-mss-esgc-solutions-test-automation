package com.esgc.DBModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResearchLineIdentifier {

    private String ISIN;
    private int rank;
    private String BBG_Ticker;
    private String SEDOL_CODE_PRIMARY;
    private Double SCORE;
    private String BVD9_NUMBER;
    private String PREVIOUS_PRODUCED_DATE;
    private Double PREVIOUS_SCORE;
    private String PLATFORM_SECTOR;
    private String COMPANY_NAME;
    private String COUNTRY_CODE;
    private String WORLD_REGION;
    private Double investmentPercentage;
    private Integer value;
    private String ResearchLineIdForESGModel;
    private String entityStatus;

    public String getRandomIdentifier(int randomNum) {
        if (randomNum == 1 & getBBG_Ticker() != null) {
            return getBBG_Ticker();
        } else {
            return getISIN();
        }
    }

    public ResearchLineIdentifier(ResearchLineIdentifier identifier) {
        this.ISIN = identifier.getISIN();
        this.value = identifier.getValue();
        this.rank = identifier.getRank();
        this.BBG_Ticker = identifier.getBBG_Ticker();
        this.SEDOL_CODE_PRIMARY = identifier.getSEDOL_CODE_PRIMARY();
        this.SCORE = identifier.getSCORE();
        this.BVD9_NUMBER = identifier.getBVD9_NUMBER();
        this.PREVIOUS_PRODUCED_DATE = identifier.getPREVIOUS_PRODUCED_DATE();
        this.PREVIOUS_SCORE = identifier.getPREVIOUS_SCORE();
        this.PLATFORM_SECTOR = identifier.getPLATFORM_SECTOR();
        this.COMPANY_NAME = identifier.getCOMPANY_NAME();
        this.COUNTRY_CODE = identifier.getCOUNTRY_CODE();
        this.WORLD_REGION = identifier.getWORLD_REGION();
        this.investmentPercentage = identifier.getInvestmentPercentage();
        this.ResearchLineIdForESGModel = identifier.getResearchLineIdForESGModel();
        this.entityStatus = identifier.getEntityStatus();
    }

    public ResearchLineIdentifier(String ISIN, Integer value) {
        this.ISIN = ISIN;
        this.value = value;
        this.rank = 1000000;
        this.BBG_Ticker = null;
        this.SEDOL_CODE_PRIMARY = null;
        this.SCORE = -1d;
        this.BVD9_NUMBER = "0";
        this.PREVIOUS_PRODUCED_DATE = "";
        this.PREVIOUS_SCORE = null;
        this.PLATFORM_SECTOR = null;
        this.COMPANY_NAME = "xxxx";
        this.COUNTRY_CODE = null;
        this.WORLD_REGION = null;
        this.investmentPercentage = null;
        this.ResearchLineIdForESGModel = null;
        this.entityStatus = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResearchLineIdentifier that = (ResearchLineIdentifier) o;
        return Objects.equals(BVD9_NUMBER, that.BVD9_NUMBER) && COMPANY_NAME.equals(that.COMPANY_NAME);
    }

    @Override
    public int hashCode() {
        return Objects.hash(BVD9_NUMBER, COMPANY_NAME);
    }
}
