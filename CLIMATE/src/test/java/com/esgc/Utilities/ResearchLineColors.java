package com.esgc.Utilities;

public class ResearchLineColors {


    public String getColorForScoreCategory(String scoreCategory) {
        switch (scoreCategory) {
            case "WEAK":
                return "#DFA124";
            case "LIMITED":
                return "#AF9D3F";
            case "ROBUST":
                return "#5A9772";
            case "ADVANCED":
            case "A2":
                return "#229595";

            case "NO RISK":
            case "0-19":
                return "#4FA3CD";
            case "LOW RISK":
            case "20-39":
                return "#8DA3B7";
            case "MEDIUM RISK":
            case "40-59":
                return "#A9898E";
            case "HIGH RISK":
            case "60-79":
                return "#C06960";
            case "RED FLAG RISK":
            case "80-100":
                return "#DA4930";

            case "MODERATE":
                return "#87C097";
            case "SIGNIFICANT":
                return "#8B9F80";
            case "HIGH":
                return "#A37863";
            case "INTENSE":
                return "#D02C2C";

            case "0%":
                return "#6FA0AD";
            case "0-20%":
                return "#A1C8D3";
            case "20-100%":
                return "#E06E4F";


            case "NONE":
                return "#E06E4F";
            case "MINOR":
                return "#E19E7C";
            //case "SIGNIFICANT":
                //return "#D7DEE0";
            case "MAJOR":
                return "#6FA0AD";
        }
        return null;
    }
}
