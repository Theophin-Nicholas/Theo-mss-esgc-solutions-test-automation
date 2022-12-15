package com.esgc.Base.DB.DBModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityWithScores {

    private String BVD9_NUMBER;
    private String COMPANY_NAME;
    private Integer VALUE;
    private String WORLD_REGION;
    private String SECTOR;
    private String PORTFOLIO_ID;

    private Double OPERATIONS_RISK_SCORE;
    private Double MARKET_RISK_SCORE;
    private Double SUPPLY_CHAIN_RISK_SCORE;
    private Double TEMPERATURE_ALIGNMENT;
    private Double CARBON_SCORE;
    private Double BROWN_SHARE_SCORE;
    private Double GREEN_SHARE_SCORE;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityWithScores that = (EntityWithScores) o;
        return Objects.equals(BVD9_NUMBER, that.BVD9_NUMBER) && COMPANY_NAME.equals(that.COMPANY_NAME);
    }

    @Override
    public int hashCode() {
        return Objects.hash(BVD9_NUMBER, COMPANY_NAME);
    }
}
