package com.esgc.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ESGUtilities {
    public static String getESGPillarsCategory(String researchLine, int score){
        score = (int) Math.floor(score);
        if(researchLine.equals("1008")){
            if (score >= 0 && score <= 29) return "Weak";
            else if (score >= 30 && score <= 49) return "Limited";
            else if (score >= 50 && score <= 59) return "Robust";
            else if (score >= 60 && score <= 100) return "Advanced";
        } else if (researchLine.equals("1015")){
            if (score >= 0 && score <= 24) return "Weak";
            else if (score >= 25 && score <= 44) return "Limited";
            else if (score >= 45 && score <= 64) return "Robust";
            else if (score >= 65 && score <= 100) return "Advanced";
        }
        return "";
    }
    public static int getESGPillarsScale(String researchLine, int score){
        score = (int) Math.floor(score);
        if(researchLine.equals("1008")){
            if (score >= 0 && score <= 29) return 1;
            else if (score >= 30 && score <= 49) return 2;
            else if (score >= 50 && score <= 59) return 3;
            else if (score >= 60 && score <= 100) return 4;
        } else if (researchLine.equals("1015")){
            if (score >= 0 && score <= 24) return 1;
            else if (score >= 25 && score <= 44) return 2;
            else if (score >= 45 && score <= 64) return 3;
            else if (score >= 65 && score <= 100) return 4;
        }
        return 0;
    }
    public String getESGScoreCategory(String researchLine, int score){
        score = (int) Math.floor(score);
        if (researchLine.equals("1008")){
            if (score >= 0 && score <= 29) return "Emerging";
            else if (score >= 30 && score <= 49) return "Progressing";
            else if (score >= 50 && score <= 59) return "Demonstrating";
            else if (score >= 60 && score <= 100) return "Leading";
        }else if (researchLine.equals("1015")){
            if (score >= 0 && score <= 24) return "Emerging";
            else if (score >= 25 && score <= 44) return "Progressing";
            else if (score >= 45 && score <= 64) return "Demonstrating";
            else if (score >= 65 && score <= 100) return "Leading";
        }
        return "";
    }
    public String getESGAlphanumericScoreCategory(String score){
        switch (score) {
            case "a1.esg":
            case "a2.esg":
            case "a3.esg": return "Leading";
            case "b1.esg":
            case "b2.esg":
            case "b3.esg": return "Demonstrating";
            case "c1.esg":
            case "c2.esg":
            case "c3.esg": return "Progressing";
            case "e.esg":return "Emerging";
            default: return "";
        }
    }
    public List<String> getESGPillarScoreRangeCategory(String researchLine){
        if (researchLine.equals("1008")) return new ArrayList<>(Arrays.asList("0-29", "30-49", "50-59","60-100"));
        else if (researchLine.equals("1015")) return new ArrayList<>(Arrays.asList("0-24", "25-44", "45-64","65-100"));
        else return new ArrayList<>();
    }

    public static String getCarbonFootprintCategory(Long score){
        if (score<100000) return "Moderate";
        else if (score<1000000) return "Significant";
        else if (score<10000000) return "High";
        else return "intense";
    }
}
