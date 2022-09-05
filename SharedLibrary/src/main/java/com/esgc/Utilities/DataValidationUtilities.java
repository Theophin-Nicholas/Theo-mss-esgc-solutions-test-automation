package com.esgc.Utilities;

import com.aventstack.extentreports.util.Assert;
import com.esgc.APIModels.PortfolioScore;
import com.esgc.APIModels.PortfolioScoreWrapper;
import com.esgc.DBModels.EntityWithScores;
import com.esgc.DBModels.ResearchLineIdentifier;
import com.esgc.Utilities.Database.PortfolioQueries;
import io.restassured.response.Response;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class DataValidationUtilities {

    public String regionFilter;
    public String sectorFilter;
    private List<ResearchLineIdentifier> unmatchedIdentifiers;
    public double totalInvestment;

    public List<ResearchLineIdentifier> getPortfolioToUpload(String researchLine, String month, String year) {
        PortfolioQueries portfolioQueries = new PortfolioQueries();
        //Get Identifiers from snowflake, create portfolio and upload portfolio
        List<ResearchLineIdentifier> researchLineIdentifiers = portfolioQueries.getIdentifiersWithPositiveScores(researchLine, month, year);

        //query for unscored ids in a given table and add them to the list of ids
        List<ResearchLineIdentifier> unscoredIdentifiers = portfolioQueries.getIdentifiersNotInResearchLine(researchLine, month, year);


        //query for negative score ids if there are any
        List<ResearchLineIdentifier> negativeScoreIdentifiers = portfolioQueries.getIdentifiersWithNegativeScores(researchLine, month, year);


        //query for null score ids if there are any
        List<ResearchLineIdentifier> nullScoreIdentifiers = portfolioQueries.getIdentifiersWithNullScores(researchLine, month, year);
/*
        //unmatched identifiers
        ResearchLineIdentifier x1 = new ResearchLineIdentifier("abc", PortfolioUtilities.randomBetween(1000, 10000000));
        ResearchLineIdentifier x2 = new ResearchLineIdentifier("abc",PortfolioUtilities.randomBetween(1000,10000000));
        ResearchLineIdentifier x3 = new ResearchLineIdentifier("xyz",PortfolioUtilities.randomBetween(1000,10000000));
        ResearchLineIdentifier x4 = new ResearchLineIdentifier("klm",PortfolioUtilities.randomBetween(1000,10000000));
        ResearchLineIdentifier x5 = new ResearchLineIdentifier("pro",PortfolioUtilities.randomBetween(1000,10000000));
        unmatchedIdentifiers = Arrays.asList(x1, x2, x3, x4, x5);
*/

        List<ResearchLineIdentifier> portfolioToUpload =
                Stream.of(researchLineIdentifiers,
                                unscoredIdentifiers,
                                negativeScoreIdentifiers,
                                nullScoreIdentifiers
                                //unmatchedIdentifiers
                        )
                        .filter(Objects::nonNull)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());

        totalInvestment = portfolioToUpload.stream()
                .mapToDouble(ResearchLineIdentifier::getValue).sum();

        //portfolioToUpload.forEach(x -> x.setInvestmentPercentage((x.getValue() / totalInvestment) * 100));

        System.out.printf("%5s %s%n", researchLineIdentifiers.size(), " positive score identifiers added");
        System.out.printf("%5s %s%n", unscoredIdentifiers.size(), " unscored identifiers added");
        System.out.printf("%5s %s%n", negativeScoreIdentifiers.size(), " negative score identifiers added");
        System.out.printf("%5s %s%n", nullScoreIdentifiers.size(), " null score identifiers added");
        //System.out.printf("%5s %s%n", unmatchedIdentifiers.size(), " unmatched identifiers added");
        System.out.println("-----------------------------------------------");
        System.out.printf("%5s %s%n", portfolioToUpload.size(), " identifiers added");

        regionFilter = getRandomRegionInPortfolio(portfolioToUpload);
        sectorFilter = getRandomSectorInPortfolio(portfolioToUpload, regionFilter);

        return portfolioToUpload;
    }


    public List<ResearchLineIdentifier> preparePortfolioForTesting(List<ResearchLineIdentifier> list) {
        double total = list.stream().mapToDouble(ResearchLineIdentifier::getValue).sum();
        List<ResearchLineIdentifier> uniqueList = new ArrayList<>(new HashSet<>(list));
        Assert.notNull(list, "list not null");
        assert list.size() != 0;

        Assert.notNull(uniqueList, "unique list not null");
        assert uniqueList.size() != 0;

        DecimalFormat df = new DecimalFormat("0.000000");
        df.setRoundingMode(RoundingMode.HALF_UP);

        uniqueList.forEach(each -> {


                    each.setValue(
                            list.stream()
                                    .filter(x -> x.getBVD9_NUMBER().equals(each.getBVD9_NUMBER()))
                                    .collect(
                                            groupingBy(ResearchLineIdentifier::getBVD9_NUMBER,
                                                    summingInt(ResearchLineIdentifier::getValue))).values()
                                    .stream().mapToInt(x -> x).sum());

                    each.setInvestmentPercentage(Double.valueOf(df.format(((each.getValue() / total) * 100))));
                }

        );

        return uniqueList;
    }

    public List<String> getRegionsInPortfolio(List<ResearchLineIdentifier> researchLineIdentifiers) {
        return researchLineIdentifiers.stream()
                .filter(PortfolioUtilities.distinctByKey(ResearchLineIdentifier::getWORLD_REGION))
                .map(ResearchLineIdentifier::getWORLD_REGION).collect(Collectors.toList());
    }

    public String getRandomRegionInPortfolio(List<ResearchLineIdentifier> researchLineIdentifiers) {
        List<String> result = researchLineIdentifiers.stream()
                .filter(e -> e.getSCORE() != null)
                .filter(e -> e.getSCORE() >= 0)
                .filter(PortfolioUtilities.distinctByKey(ResearchLineIdentifier::getWORLD_REGION))
                .map(ResearchLineIdentifier::getWORLD_REGION).collect(Collectors.toList());
        Collections.shuffle(result);
        return result.get(0);
    }

    public String getRandomRegionInPortfolio(List<ResearchLineIdentifier> researchLineIdentifiers, String sector) {
        List<String> result = researchLineIdentifiers.stream()
                .filter(e -> e.getSCORE() >= 0)
                .filter(e -> e.getPLATFORM_SECTOR().equals(sector))
                .filter(PortfolioUtilities.distinctByKey(ResearchLineIdentifier::getWORLD_REGION))
                .map(ResearchLineIdentifier::getWORLD_REGION).collect(Collectors.toList());
        Collections.shuffle(result);
        return result.get(0);
    }

    public List<String> getSectorsInPortfolio(List<ResearchLineIdentifier> researchLineIdentifiers) {
        return researchLineIdentifiers.stream()
                .filter(e -> e.getSCORE() >= 0)
                .filter(PortfolioUtilities.distinctByKey(ResearchLineIdentifier::getPLATFORM_SECTOR))
                .map(ResearchLineIdentifier::getPLATFORM_SECTOR).collect(Collectors.toList());
    }

    public String getRandomSectorInPortfolio(List<ResearchLineIdentifier> researchLineIdentifiers) {
        List<String> result = researchLineIdentifiers.stream()
                .filter(e -> e.getSCORE() >= 0)
                .filter(PortfolioUtilities.distinctByKey(ResearchLineIdentifier::getPLATFORM_SECTOR))
                .map(ResearchLineIdentifier::getPLATFORM_SECTOR).collect(Collectors.toList());
        Collections.shuffle(result);
        return result.get(0);
    }

    public String getRandomSectorInPortfolio(List<ResearchLineIdentifier> researchLineIdentifiers, String region) {
        List<String> result = researchLineIdentifiers.stream()
                .filter(e -> e.getSCORE() != null)
                .filter(e -> e.getSCORE() >= 0)
                .filter(e -> e.getWORLD_REGION().equals(region))
                .filter(PortfolioUtilities.distinctByKey(ResearchLineIdentifier::getPLATFORM_SECTOR))
                .map(ResearchLineIdentifier::getPLATFORM_SECTOR).collect(Collectors.toList());
        Collections.shuffle(result);
        return result.get(0);
    }

    public int getCoverageOfPortfolio(List<ResearchLineIdentifier> researchLineIdentifiers) {
        return (int) researchLineIdentifiers.stream().filter(r -> r.getSCORE() >= 0).count();
    }


    public double getTotalPortfolioInvestmentPercentage(List<ResearchLineIdentifier> researchLineIdentifiers) {
        return researchLineIdentifiers.stream()
                .filter(each -> each.getSCORE() >= 0)
                .mapToDouble(ResearchLineIdentifier::getInvestmentPercentage).sum();
    }

    public double getTotalInvestmentPercentage(List<ResearchLineIdentifier> researchLineIdentifiers) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return Double.parseDouble(df.format(((researchLineIdentifiers.stream()
                .mapToDouble(ResearchLineIdentifier::getValue).sum()) / totalInvestment) * 100));
    }

    public double getTotalInvestmentPercentage(List<ResearchLineIdentifier> researchLineIdentifiers, double total) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return Double.parseDouble(df.format(((researchLineIdentifiers.stream()
                .filter(each -> each.getSCORE() >= 0)
                .mapToDouble(ResearchLineIdentifier::getValue).sum()) / total) * 100));
    }

    public double getTotalInvestmentForTemperatureAlignment(List<ResearchLineIdentifier> researchLineIdentifiers, double total) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return Double.parseDouble(df.format(((researchLineIdentifiers.stream()
                .filter(each -> each.getSCORE() != null)
                .filter(each -> each.getSCORE() >-50)
                .mapToDouble(ResearchLineIdentifier::getValue).sum()) / total) * 100));
    }

    public double getTotalInvestmentPercentageForCompany(List<ResearchLineIdentifier> researchLineIdentifiers, String bvd9) {
        return getTotalPortfolioInvestmentPercentage(researchLineIdentifiers.stream()
                .filter(each -> each.getBVD9_NUMBER().equals(bvd9)).collect(Collectors.toList()));
    }

    public double getTotalInvestmentPercentageForCompanyByName(List<ResearchLineIdentifier> researchLineIdentifiers, String companyName) {
        return getTotalInvestmentPercentage(researchLineIdentifiers.stream()
                .filter(each -> each.getCOMPANY_NAME().equals(companyName)).collect(Collectors.toList()));
    }

    public Double getPortfolioScoreFromPortfolioScoreResponse(String researchLine, Response response) {
        try {
            switch (researchLine) {
                case "supplychainrisk":
                case "marketrisk":
                case "operationsrisk":
                    String score = response.as(PortfolioScore[].class)[0].getScore();
                    return Double.parseDouble(score.substring(0, score.indexOf("/")));

                case "Physical Risk Management":
                case "Energy Transition Management":
                case "TCFD":
                    return Double.parseDouble(response.as(PortfolioScoreWrapper[].class)[0]
                            .portfolioScore.stream().filter(e -> e.getName().equals("Total"))
                            .map(PortfolioScore::getScore).findFirst().get());
                default:
                    return Double.parseDouble(response.as(PortfolioScoreWrapper[].class)[0]
                            .portfolioScore.get(0).getScore());
            }
        } catch (Exception e) {
            return null;
        }
    }

    public String getPortfolioScoreCategoryFromPortfolioScoreResponse(String researchLine, Response response) {
        try {
            switch (researchLine) {
                case "supplychainrisk":
                case "marketrisk":
                case "operationsrisk":
                    return response.as(PortfolioScore[].class)[0].getRanking();

                case "Physical Risk Management":
                case "Energy Transition Management":
                case "TCFD":
                    return response.as(PortfolioScoreWrapper[].class)[0]
                            .portfolioScore.stream().filter(e -> e.getName().equals("Total"))
                            .map(PortfolioScore::getRanking).findFirst().get();
                default:
                    return response.as(PortfolioScoreWrapper[].class)[0]
                            .portfolioScore.get(0).getRanking();
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List<ResearchLineIdentifier> updateIdentifierScoresByDateAndAnotherResearchLine(String researchLine,
                                                                                           String month,
                                                                                           String year,
                                                                                           String portfolioId,
                                                                                           List<ResearchLineIdentifier> portfolio) {
        PortfolioQueries portfolioQueries = new PortfolioQueries();

        List<Map<String, Object>> newScores = portfolioQueries.getCompanyScoresInResearchLineWithPortfolioID(researchLine, month, year, portfolioId);

        List<ResearchLineIdentifier> newScoredIdentifierList = clonePortfolio(portfolio);

        newScoredIdentifierList
                .forEach(e -> {
                    //If new list does not have bvd9 that means there is no score in updated research line so set score to null
                    if (!newScores.stream().map(o -> o.get("BVD9_NUMBER")).collect(toList()).contains(e.getBVD9_NUMBER())) {
                        e.setSCORE(-99999d);
                    } else {
                        //If new list has bvd9 that means there a score in that updated research line so set score to new score
                        if (newScores.stream().anyMatch(a -> a.get("BVD9_NUMBER").equals(e.getBVD9_NUMBER()))) {
                            Map<String, Object> newScore = newScores.stream().filter(a -> a.get("BVD9_NUMBER").equals(e.getBVD9_NUMBER()))
                                    .findFirst().get();
                            e.setSCORE(Double.parseDouble(newScore.get("SCORE").toString()));
                        }
                        //If new list has bvd9 but in previous research line does not have data
                        //that means there is a score in updated research line so create missing company object
                        else {
                            System.out.println("COMPANY MISSING!!!!!!!!!!!");
                        }
                    }
                });
        return newScoredIdentifierList;
    }

    public List<ResearchLineIdentifier> updatePreviousScoreForOperationsRisk(String month,
                                                                             String year,
                                                                             String portfolioId,
                                                                             List<ResearchLineIdentifier> portfolio) {
        PortfolioQueries portfolioQueries = new PortfolioQueries();

        List<Map<String, Object>> newScores = portfolioQueries.getCompanyScoresInResearchLineWithPortfolioID("operationsrisk", month, "2020", portfolioId);

        List<ResearchLineIdentifier> newScoredIdentifierList = clonePortfolio(portfolio);

        newScoredIdentifierList
                .forEach(e -> {
                    if (newScores.stream().map(o -> o.get("BVD9_NUMBER")).collect(toList()).contains(e.getBVD9_NUMBER())) {

                        Map<String, Object> newScore = newScores.stream().filter(a -> a.get("BVD9_NUMBER").equals(e.getBVD9_NUMBER()))
                                .findFirst().get();
                        e.setPREVIOUS_SCORE(Double.parseDouble(newScore.get("SCORE").toString()));
                        e.setPREVIOUS_PRODUCED_DATE("2020-12-18");
                    }
                });
        return newScoredIdentifierList;
    }

    public static List<ResearchLineIdentifier> clonePortfolio(List<ResearchLineIdentifier> portfolio) {
        List<ResearchLineIdentifier> clonedPortfolio = new ArrayList<>(portfolio.size());
        for (ResearchLineIdentifier each : portfolio) {
            clonedPortfolio.add(new ResearchLineIdentifier(each));
        }
        return clonedPortfolio;
    }


    public List<EntityWithScores> getIdentifierScoresByDateForAllResearchLines(String month,
                                                                               String year,
                                                                               String portfolioId
    ) {
        PortfolioQueries portfolioQueries = new PortfolioQueries();

        return portfolioQueries.getCompanyScoresInAllResearchLinesWithPortfolioID(month, year, portfolioId);

    }
}
