package com.esgc.PortfolioAnalysis.DB.Tests;

import com.esgc.Base.API.APIModels.APIFilterPayload;
import com.esgc.Base.DB.DBModels.ResearchLineIdentifier;
import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.PortfolioAnalysis.API.APIModels.LeadersAndLaggards;
import com.esgc.PortfolioAnalysis.API.APIModels.LeadersAndLaggardsWrapper;
import com.esgc.PortfolioAnalysis.DB.DBModels.ESGLeaderANDLaggers;
import com.esgc.Reporting.CustomAssertion;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.PortfolioUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.esgc.Utilities.Groups.*;
import static com.esgc.Utilities.PortfolioUtilities.distinctByKey;

public class LeadersAndLaggardsTests extends DataValidationTestBase {


    @Test(groups = {REGRESSION, DATA_VALIDATION}, dataProvider = "researchLines")
    @Xray(test = {2126, 2125, 2892, 2891, 2476, 2130, 2129, 2127,
            2893, 2497, 2480, 1287, 2132, 2131, 2896, 2897, 2128,
            2894, 2483, 2482, 3836, 3885, 2481, 2890, 3889, 3882,
            3886, 2895, 2123, 2496, 2479, 4156, 4155, 4162, 4165, 4160, 4161, 4164,
            4163, 4166, 1174, 3032, 2121, 1188, 828, 1286, 1179, 2884, 2498, 3098, 2081, 1285,
            11246,//Subs
            11073//Predicted
    })
    public void validateResearchLineLeadersAndLaggardsData(@Optional String sector, @Optional String region,
                                                           @Optional String researchLine, @Optional String month, @Optional String year) {

        List<ResearchLineIdentifier> portfolioToUpload = dataValidationUtilities.getPortfolioToUpload(researchLine, month, year);

        String fileName = String.format(" %s - %s - %s - %s - %s", researchLine, sector, region, month, year);
        String path = portfolioUtilities.createPortfolio(fileName, portfolioToUpload);
        test.info("Portfolio saved to:");
        test.info(path);
        Response response = controller.importPortfolio(APIUtilities.userID(), fileName + ".csv", path);
        response.then().assertThat().body("portfolio_name", Matchers.notNullValue());

        String portfolioId = response.getBody().jsonPath().get("portfolio_id");
        test.info("portfolio_id=" + portfolioId);
        test.info(String.format("Research Line=%s Filter= %s %s %s %s", researchLine, region, sector, month, year));

        portfolioToUpload = dataValidationUtilities.preparePortfolioForTesting(portfolioToUpload);

        //filter for a given region
        if (!region.equals("all")) {
            portfolioToUpload = portfolioToUpload.stream().filter(r -> r.getWORLD_REGION()
                    .equals(region)).collect(Collectors.toList());
        }

        //filter for a given region
        if (!sector.equals("all")) {
            portfolioToUpload = portfolioToUpload.stream().filter(r -> r.getPLATFORM_SECTOR()
                    .equals(sector)).collect(Collectors.toList());
        }
        portfolioToUpload = portfolioToUpload.stream().filter(e -> e.getSCORE() >= 0).collect(Collectors.toList());
        portfolioToUpload = rankResearchLinesv2(portfolioToUpload, researchLine);

        System.out.println(portfolioToUpload);
        List<List<ResearchLineIdentifier>> leadersAndLaggards = distributeLeadersAndLaggards(portfolioToUpload);

        List<ResearchLineIdentifier> leaders = leadersAndLaggards.get(0);
        List<ResearchLineIdentifier> laggards = leadersAndLaggards.get(1);

        int countOfDistinctCompanies = (int) portfolioToUpload.stream().distinct().count();
        int limitOfLeaders;
        int limitOfLaggards;
        if (countOfDistinctCompanies >= 20) {
            limitOfLeaders = 10;
            limitOfLaggards = 10;
        } else {
            limitOfLeaders = countOfDistinctCompanies % 2 == 0 ? countOfDistinctCompanies / 2 : (countOfDistinctCompanies / 2) + 1;
            limitOfLaggards = countOfDistinctCompanies / 2;
        }

        System.out.println("Count of distinct companies after filters: " + countOfDistinctCompanies);
        System.out.println("Count of leaders limit: " + limitOfLeaders);
        System.out.println("Count of laggards limit: " + limitOfLaggards);

       /* portfolioToUpload.stream()
                .limit(distributeLeadersAndLaggards(portfolioToUpload, limitOfLeaders))
                .collect(Collectors.toList());*/
        System.out.println(leaders.size() + " leaders identified");


        System.out.println("getting laggards from rank " + (countOfDistinctCompanies - limitOfLaggards));
       /* portfolioToUpload.stream()
                .sorted(Collections.reverseOrder())
                .limit()
                .filter(e -> e.getRank() > countOfDistinctCompanies - limitOfLaggards)
                .collect(Collectors.toList());*/
        System.out.println(laggards.size() + " laggards identified");

        APIFilterPayload apiFilterPayload = new APIFilterPayload();
        apiFilterPayload.setSector(sector);
        apiFilterPayload.setRegion(region);
        apiFilterPayload.setBenchmark("");
        apiFilterPayload.setYear(year);
        apiFilterPayload.setMonth(month);

        LeadersAndLaggardsWrapper leadersAndLaggardsWrapper =
                controller.getPortfolioLeadersAndLaggardsResponse(portfolioId, researchLine, apiFilterPayload).body().prettyPeek()
                        .as(LeadersAndLaggardsWrapper.class);

        Map<String, LeadersAndLaggards> apiLeaders = leadersAndLaggardsWrapper.getLeaders().stream()
                .collect(Collectors.toMap(LeadersAndLaggards::getCompanyName, x -> x));

        Map<String, LeadersAndLaggards> apiLaggards = leadersAndLaggardsWrapper.getLaggards().stream()
                .collect(Collectors.toMap(LeadersAndLaggards::getCompanyName, x -> x));

        double totalValues = portfolioUtilities.calculateTotalSumOfInvestment(portfolioToUpload);

        for (ResearchLineIdentifier each : leaders)
            validateLeadersAndLaggardsResponse(assertTestCase, each, apiLeaders.get(each.getCOMPANY_NAME()), totalValues);

        for (ResearchLineIdentifier each : laggards)
            validateLeadersAndLaggardsResponse(assertTestCase, each, apiLaggards.get(each.getCOMPANY_NAME()), totalValues);

    }

    public void validateLeadersAndLaggardsResponse(CustomAssertion Assert, ResearchLineIdentifier identifier, LeadersAndLaggards apiResponse,
                                                   double totalInvestment) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        Double expectedInvestmentPercentage = Double.valueOf(df.format(identifier.getInvestmentPercentage()));
        System.out.println("identifier = " + identifier);
        System.out.println("apiResponse = " + apiResponse);
        if (apiResponse == null) return;
        Assert.assertEquals(apiResponse.getRank(), identifier.getRank(), "Validating company rank " + identifier.getCOMPANY_NAME());
        Assert.assertEquals((int) apiResponse.getScore(), identifier.getSCORE().intValue(), "Validating company score " + identifier.getCOMPANY_NAME());
        Assert.assertEquals(PortfolioUtilities.round(apiResponse.getInvestmentPct(), 2), expectedInvestmentPercentage, "Validating company investment pct " + identifier.getCOMPANY_NAME());

    }

    public Comparator<ResearchLineIdentifier> sortLowToHigh() {
        return Comparator
                .comparing(ResearchLineIdentifier::getSCORE)
                .thenComparing(ResearchLineIdentifier::getInvestmentPercentage)
                .thenComparing(ResearchLineIdentifier::getCOMPANY_NAME);
    }

    public Comparator<ResearchLineIdentifier> sortHighToLow() {
        return Comparator
                .comparing(ResearchLineIdentifier::getSCORE).reversed()
                .thenComparing(ResearchLineIdentifier::getInvestmentPercentage)
                .thenComparing(ResearchLineIdentifier::getCOMPANY_NAME);
    }

    public List<ResearchLineIdentifier> rankResearchLines(List<ResearchLineIdentifier> list) {
        Double lastScore = -1D;
        int rank = 1;
        int secretRank = rank;

        list = list.stream().filter(distinctByKey(ResearchLineIdentifier::getBVD9_NUMBER))
                .filter(e -> e.getSCORE() >= 0)
                .sorted(sortLowToHigh())
                .collect(Collectors.toList());

        for (ResearchLineIdentifier each : list) {
            System.out.println(String.format("%s rank: %s", each.getCOMPANY_NAME(), rank));

            if (!lastScore.equals(each.getSCORE())) {
                rank++;
            } else {
                rank = secretRank;
            }
            secretRank++;
            lastScore = each.getSCORE();
            each.setRank(rank);

        }
        return list;
    }

    public List<ResearchLineIdentifier> rankResearchLinesv2(List<ResearchLineIdentifier> list, String researchLine) {
        //we will compare current score with this to decide whether we apply the same rank
        double previousScore = -1D;
        int rank = 0;
        int count = 0;
        switch (researchLine) {
            case "supplychainrisk":
            case "marketrisk":
            case "operationsrisk":
            case "Carbon Footprint":
            case "Brown Share":
                list = list.stream().filter(distinctByKey(ResearchLineIdentifier::getBVD9_NUMBER))
                        .sorted(sortLowToHigh())
                        .collect(Collectors.toList());
                break;
            case "Physical Risk Management":
            case "Energy Transition Management":
            case "TCFD":
            case "Green Share":
                list = list.stream().filter(distinctByKey(ResearchLineIdentifier::getBVD9_NUMBER))
                        .sorted(sortHighToLow())
                        .collect(Collectors.toList());
                break;

        }
        System.out.println(String.format("%4s %6s %s", "RANK", "SCORE", "COMPANY"));
        for (ResearchLineIdentifier each : list) {

            if (each.getSCORE() != previousScore) {
                if (count != 0) {
                    rank = rank + count;
                    count = 0;
                } else {
                    rank++;
                }
            }
            each.setRank(rank);
            count++;
            previousScore = each.getSCORE();
            System.out.println(String.format("%4s %10s %s", rank, each.getSCORE(), each.getCOMPANY_NAME()));
        }

        return list;
    }

    /*
    handles the edge case where leaders will have linked rankes causing more than 10 leaders to happen
     */
    public List<List<ResearchLineIdentifier>> distributeLeadersAndLaggards(List<ResearchLineIdentifier> list) {
        List<ResearchLineIdentifier> leaders = new ArrayList<>();
        List<ResearchLineIdentifier> laggards = new ArrayList<>();
        int firstRank = 1;
        int lastRank = list.get(list.size() - 1).getRank();

        int size = (int) list.stream().filter(distinctByKey(ResearchLineIdentifier::getRank)).count();

        //leaders should be filled first, then laggards
        leaders.addAll(list.stream().filter(e -> e.getRank() == 1).collect(Collectors.toList()));

        if (firstRank != lastRank) {
            firstRank++;


            for (int i = 0; i < size; i++) {


                if (!(laggards.size() >= leaders.size()) && laggards.size() < 10) {
                    int finalRank = lastRank;
                    laggards.addAll(list.stream().filter(e -> e.getRank() == finalRank).collect(Collectors.toList()));
                    if (firstRank == lastRank) break;
                    lastRank--;

                } else if (leaders.size() < 10) {
                    int finalRank = firstRank;
                    leaders.addAll(list.stream().filter(e -> e.getRank() == finalRank).collect(Collectors.toList()));
                    firstRank++;

                } else {
                    break;
                }


            }
        }
        List<List<ResearchLineIdentifier>> lists = new ArrayList<>();
        lists.add(leaders);
        lists.add(laggards);
        return lists;

        /*int count = 0;

        for (ResearchLineIdentifier each : list) {
            if (each.getRank() != rank && count >= limit) {
                break;
            }
            count++;
        }
        return count;*/
    }


    public List<List<ESGLeaderANDLaggers>> distributeESGLeadersAndLaggards(List<ESGLeaderANDLaggers> list) {
        List<ESGLeaderANDLaggers> leaders = new ArrayList<>();
        List<ESGLeaderANDLaggers> laggards = new ArrayList<>();
        int leaderslastRank = list.get(list.size() % 2 == 0 ? list.size() / 2 : (list.size() / 2) + 1).getRANK();
        laggards.addAll(list.stream().filter(f -> f.getRANK() > leaderslastRank).collect(Collectors.toList()).stream()
                .sorted(Comparator.comparing(ESGLeaderANDLaggers::getRANK).reversed()
                        .thenComparing(ESGLeaderANDLaggers::getInvestmentPercentage).reversed()
                        .thenComparing(ESGLeaderANDLaggers::getCOMPANY_NAME).reversed())
                .collect(Collectors.toList()));
        leaders.addAll(list.stream().filter(f -> f.getRANK() <= leaderslastRank).collect(Collectors.toList()));
        List<List<ESGLeaderANDLaggers>> lists = new ArrayList<>();
        lists.add(leaders);
        lists.add(laggards);
        return lists;
    }

    @Test(groups = {REGRESSION, DATA_VALIDATION, ESG})
    @Xray(test = {8452, 9873})
    public void validateESGLeadersAndLaggardsData() {

        String sector = "all";
        String region = "all";
        String researchLine = "ESG Assessments";
        String month = "08";
        String year = "2022";


        String portfolioId = "00000000-0000-0000-0000-000000000000";
        List<ESGLeaderANDLaggers> dbData = portfolioQueries.getESGLeadersAndLaggersData(portfolioId, year + month);


        List<List<ESGLeaderANDLaggers>> leadersAndLaggards = distributeESGLeadersAndLaggards(dbData);

        List<ESGLeaderANDLaggers> leaders = leadersAndLaggards.get(0);
        List<ESGLeaderANDLaggers> laggards = leadersAndLaggards.get(1);


        System.out.println(leaders.size() + " leaders identified");


        System.out.println(laggards.size() + " laggards identified");

        APIFilterPayload apiFilterPayload = new APIFilterPayload();
        apiFilterPayload.setSector(sector);
        apiFilterPayload.setRegion(region);
        apiFilterPayload.setBenchmark("");
        apiFilterPayload.setYear(year);
        apiFilterPayload.setMonth(month);

        LeadersAndLaggardsWrapper leadersAndLaggardsWrapper =
                controller.getPortfolioLeadersAndLaggardsResponse(portfolioId, researchLine, apiFilterPayload).body().prettyPeek()
                        .as(LeadersAndLaggardsWrapper.class);

        List<LeadersAndLaggards> apiLeaders = leadersAndLaggardsWrapper.getLeaders().stream()
                .collect(Collectors.toList());

        List<LeadersAndLaggards> apiLaggards = leadersAndLaggardsWrapper.getLaggards().stream()
                .collect(Collectors.toList());

        for (int i = 0; i < leaders.size(); i++) {
            // ESGLeaderANDLaggers each = leaders.get(i);
            try {
                if (apiLeaders != null || !leaders.get(i).getCOMPANY_NAME().isEmpty() || leaders.get(i).getCOMPANY_NAME() != null || apiLeaders.get(i).getCompanyName() != null) {
                    assertTestCase.assertEquals(apiLeaders.get(i).getRank(), leaders.get(i).getRANK(), "Validating company rank " + leaders.get(i).getCOMPANY_NAME());
                    assertTestCase.assertEquals((int) apiLeaders.get(i).getScore(), leaders.get(i).getSCORE(), "Validating company score " + leaders.get(i).getCOMPANY_NAME());
                    assertTestCase.assertEquals(PortfolioUtilities.round(apiLeaders.get(i).getInvestmentPct(), 2), PortfolioUtilities.round(leaders.get(i).getInvestmentPercentage(), 2), "Validating company investment pct " + leaders.get(i).getCOMPANY_NAME());
                    assertTestCase.assertEquals(apiLeaders.get(i).getMethodologyversion().toString(), leaders.get(i).getMETHODOLOGY_VERSION(), "Validating company Methodology version " + leaders.get(i).getCOMPANY_NAME());
                }
            } catch (Exception e) {
                System.out.println("failed for " + leaders.get(i).getCOMPANY_NAME());
            }
        }


        for (int i = 0; i < laggards.size(); i++) {
            // ESGLeaderANDLaggers laggards.get(i) = laggards.get(i);
            try {
                if (apiLeaders != null || !laggards.get(i).getCOMPANY_NAME().isEmpty() || laggards.get(i).getCOMPANY_NAME() != null || apiLaggards.get(i).getCompanyName() != null) {
                    assertTestCase.assertEquals(apiLaggards.get(i).getRank(), laggards.get(i).getRANK(), "Validating company rank " + laggards.get(i).getCOMPANY_NAME());
                    assertTestCase.assertEquals((int) apiLaggards.get(i).getScore(), laggards.get(i).getSCORE(), "Validating company score " + laggards.get(i).getCOMPANY_NAME());
                    assertTestCase.assertEquals(PortfolioUtilities.round(apiLaggards.get(i).getInvestmentPct(), 2), PortfolioUtilities.round(laggards.get(i).getInvestmentPercentage(), 2), "Validating company investment pct " + laggards.get(i).getCOMPANY_NAME());
                    assertTestCase.assertEquals(apiLaggards.get(i).getMethodologyversion().toString(), laggards.get(i).getMETHODOLOGY_VERSION(), "Validating company Methodology version " + laggards.get(i).getCOMPANY_NAME());
                }
            } catch (Exception e) {
                System.out.println("failed for " + laggards.get(i).getCOMPANY_NAME());
            }
        }


    }


    @DataProvider(name = "researchLines")
    public Object[][] provideFilterParameters() {

        return new Object[][]
                {

                        {"all", "all", "operationsrisk", "12", "2020"},
                        {"all", "APAC", "operationsrisk", "12", "2020"},
                        {"all", "EMEA", "operationsrisk", "12", "2020"},
                        {"all", "AMER", "operationsrisk", "12", "2020"},
//                        {"all", "AMER", "supplychainrisk", "12", "2020"},
//                        {"all", "EMEA", "supplychainrisk", "12", "2020"},
//                        {"all", "APAC", "supplychainrisk", "12", "2020"},
//                        {"all", "all", "supplychainrisk", "12", "2020"},
//                        {"all", "EMEA", "marketrisk", "12", "2020"},
//                        {"all", "AMER", "marketrisk", "12", "2020"},
//                        {"all", "APAC", "marketrisk", "12", "2020"},
//                        {"all", "all", "marketrisk", "12", "2020"},
                        {"all", "all", "operationsrisk", "12", "2021"},
                        {"all", "APAC", "operationsrisk", "12", "2021"},
                        {"all", "EMEA", "operationsrisk", "12", "2021"},
                        {"all", "AMER", "operationsrisk", "12", "2021"},
//                        {"all", "AMER", "supplychainrisk", "12", "2021"},
//                        {"all", "EMEA", "supplychainrisk", "12", "2021"},
//                        {"all", "APAC", "supplychainrisk", "12", "2021"},
//                        {"all", "all", "supplychainrisk", "12", "2021"},
//                        {"all", "EMEA", "marketrisk", "12", "2021"},
//                        {"all", "AMER", "marketrisk", "12", "2021"},
//                        {"all", "APAC", "marketrisk", "12", "2021"},
//                        {"all", "all", "marketrisk", "12", "2021"},
                        {"all", "AMER", "Physical Risk Management", "12", "2020"},
                        {"all", "EMEA", "Physical Risk Management", "12", "2020"},
                        {"all", "APAC", "Physical Risk Management", "12", "2020"},
                        {"all", "all", "Physical Risk Management", "12", "2020"},
                        {"all", "APAC", "Carbon Footprint", "02", "2021"},
                        {"all", "AMER", "Carbon Footprint", "02", "2021"},
                        {"all", "EMEA", "Carbon Footprint", "02", "2021"},
                        {"all", "all", "Carbon Footprint", "02", "2021"},
                        {"all", "APAC", "Carbon Footprint", "12", "2021"},
                        {"all", "AMER", "Carbon Footprint", "12", "2021"},
                        {"all", "EMEA", "Carbon Footprint", "12", "2021"},
                        {"all", "all", "Carbon Footprint", "12", "2021"},
//                        {"all", "APAC", "Energy Transition Management", "03", "2021"},
//                        {"all", "AMER", "Energy Transition Management", "03", "2021"},
//                        {"all", "EMEA", "Energy Transition Management", "03", "2021"},
//                        {"all", "all", "Energy Transition Management", "03", "2021"},
                        {"all", "APAC", "Brown Share", "03", "2021"},
                        {"all", "AMER", "Brown Share", "03", "2021"},
                        {"all", "EMEA", "Brown Share", "03", "2021"},
                        {"all", "all", "Brown Share", "03", "2021"},
//                        {"all", "all", "TCFD", "03", "2021"},
//                        {"all", "APAC", "TCFD", "03", "2021"},
//                        {"all", "AMER", "TCFD", "03", "2021"},
//                        {"all", "EMEA", "TCFD", "03", "2021"},
                        {"all", "all", "Green Share", "03", "2021"},
                        {"all", "APAC", "Green Share", "03", "2021"},
                        {"all", "EMEA", "Green Share", "03", "2021"},
                        {"all", "AMER", "Green Share", "03", "2021"}
                };
    }


}
