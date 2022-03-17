import dataClasses.CostFlow;
import helpers.DataLoader;
import helpers.PopulationHelper;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        List<Integer[]> population = PopulationHelper.generatePopulation(30, 9, 3, 3);
        List<CostFlow> testList = DataLoader.findPairs(DataLoader.getCost("easy"), DataLoader.getFlow("easy"));


        System.out.println("Tournament selection");
        System.out.println(Collections.min(PopulationHelper.calculateCosts(testList, population, 3, 3)));
        Integer[] individual = PopulationHelper.tournamentSelection(testList, population, 30, 3, 3);
        System.out.println(PopulationHelper.calculateCost(testList, individual, 3, 3));

        System.out.println("\nRoulette selection");
        List<Integer[]> roulettePopulation = PopulationHelper.generatePopulation(5, 9, 3, 3);
        System.out.println(PopulationHelper.calculateCosts(testList, roulettePopulation, 3, 3));
        System.out.println(Arrays.toString(PopulationHelper.rouletteSelection(testList, roulettePopulation, 3, 3)));

        System.out.println("\nIndividuals for crossover");
        System.out.println(Arrays.toString(population.get(0)));
        System.out.println(Arrays.toString(population.get(1)));

        List<Integer[]> test = PopulationHelper.singlePointCrossover(population.get(0), population.get(1), 3, 3);

        System.out.println("Crossover results");
        System.out.println(Arrays.toString(test.get(0)));
        System.out.println(Arrays.toString(test.get(1)));

        Integer[] duplicatesList =  {
                6, 7, 8, 9, 10, 5, 5, 5
        };

        System.out.println("\nMutation test");
        System.out.println(Arrays.toString(population.get(0)));
        System.out.println(Arrays.toString(PopulationHelper.mutation(population.get(0), 0.9f)));

        System.out.println("\nFix genotypes");
        System.out.println(Arrays.toString(PopulationHelper.fixGenotypes(duplicatesList, 9)));
    }
}
