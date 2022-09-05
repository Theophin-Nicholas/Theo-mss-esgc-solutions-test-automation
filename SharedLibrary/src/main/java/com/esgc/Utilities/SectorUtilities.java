package com.esgc.Utilities;

import java.util.Arrays;
import java.util.List;

public class SectorUtilities {
    public static List<String> MESGSectors =
            Arrays.asList(
                    "Chemicals",
                    "Forest Products & Paper",
                    "Mining & Metals",
                    "Industrial Materials",
                    "Retailers",
                    "Hotel & Leisure Services",
                    "Diversified Consumer Services",
                    "Automobiles",
                    "Clothes, Footwear & Accessories",
                    "Leisure Goods",
                    "Durable Household Goods",
                    "Telecommunication Services",
                    "Broadcasts, Movies & Entertainment",
                    "Media Agencies",
                    "Publishing",
                    "Internet Media & Services",
                    "Beverage",
                    "Food",
                    "Tobacco",
                    "Personal & Non - durable",
                    "Personal & Non - durable Household Goods",
                    "Supermarkets",
                    "Diversified Banks",
                    "Retail & Specialized Banks",
                    "Finance & Credit",
                    "Capital Markets",
                    "Finance Support",
                    "Services",
                    "Insurance",
                    "Pharmaceuticals & Biotechnology",
                    "Health Care Equipment",
                    "Health Care Services",
                    "Construction & Engineering",
                    "Building Materials",
                    "Industrial Goods & Services",
                    "Aerospace & Defense",
                    "Electric Components & Equipment",
                    "Mechanical Components & Equipment",
                    "Diversified Industrials",
                    "Business Support Services",
                    "Packaging",
                    "Transport & Logistics",
                    "Oil Equipment & Services",
                    "Energy",
                    "Real Estate Owners & Developers",
                    "Real Estate Services",
                    "Home Construction",
                    "Supranationals",
                    "Specific Purpose Banks & Agencies",
                    "Local Authorities",
                    "Software & IT Services",
                    "Technology Hardware",
                    "Electric & Gas Utilities",
                    "Waste & Water");

    public static boolean isMESGSector(String sector){
        return MESGSectors.contains(sector);
    }

}
