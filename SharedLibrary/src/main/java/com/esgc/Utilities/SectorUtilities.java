package com.esgc.Utilities;

import java.util.Arrays;
import java.util.List;

public class SectorUtilities {
    public static List<String> MESGSectors =
            Arrays.asList(
                    "Chemicals",
                    "Forest Products & Paper",
                    "Mining & Metals",
                    "Hotel, Leisure Goods & Services",
                    "Travel & Tourism",
                    "Automobiles",
                    "Home Construction",
                    "Luxury Goods & Cosmetics",
                    "Specialised Retail",
                    "Telecommunications",
                    "Broadcasting & Advertising",
                    "Publishing",
                    "Beverage",
                    "Food",
                    "Tobacco",
                    "Supermarkets",
                    "Diversified Banks",
                    "Retail & Specialised Banks",
                    "Financial Services - General",
                    "Financial Services - Real Estate",
                    "Insurance",
                    "Pharmaceuticals & Biotechnology",
                    "Health Care Equipment & Services",
                    "Mechanical Components & Equipment",
                    "Business Support Services",
                    "Oil Equipment & Services",
                    "Energy",
                    "Local authorities",
                    "Waste & Water Utilities",
                    "Building Materials",
                    "Industrial Goods & Services",
                    "Electric Components & Equipment",
                    "Software & IT Services",
                    "Electric & Gas Utilities",
                    "Heavy Construction",
                    "Development Banks",
                    "Specific Purpose Banks & Agencies",
                    "Technology-Hardware",
                    "No Industry Alignment",
                    "Aerospace",
                    "Transport & Logistics");

    public static boolean isMESGSector(String sector) {
        return MESGSectors.contains(sector);
    }

}
