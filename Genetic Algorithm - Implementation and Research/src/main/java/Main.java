import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import dataClasses.CostFlow;
import helpers.DataLoader;
import helpers.PopulationHelper;
import runner.SimulationRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, PythonExecutionException {
        SimulationRunner runner = DataLoader.getRunner();
        System.out.println(runner);

//        Integer[] flatTest = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

//        runner.runSimulationTournament();
        runner.runSimulationRoulette();

//        List<Integer[]> population = PopulationHelper.generatePopulation(300, 12, 1, 12);
//        List<CostFlow> testList = DataLoader.findPairs(DataLoader.getCost("flat"), DataLoader.getFlow("flat"));
//
//
//        System.out.println("Tournament selection");
//        System.out.println(Collections.min(PopulationHelper.calculateCosts(testList, population, 3, 3)));
//        Integer[] individual = PopulationHelper.tournamentSelection(testList, population, 30, 3, 3);
//        System.out.println(PopulationHelper.calculateCost(testList, individual, 3, 3));

//        System.out.println("\nRoulette selection");
//        List<Integer[]> roulettePopulation = PopulationHelper.generatePopulation(5, 24, 5, 6);
//        System.out.println(PopulationHelper.calculateCosts(testList, roulettePopulation, 3, 3));
//        PopulationHelper.rouletteSelectionExp(testList, roulettePopulation, 3, 3, 6.5f);
//        PopulationHelper.rouletteSelection(testList, roulettePopulation, 3, 3);
//
//        System.out.println("\nIndividuals for crossover");
//        System.out.println(Arrays.toString(population.get(0)));
//        System.out.println(Arrays.toString(population.get(1)));
//
//        List<Integer[]> test = PopulationHelper.singlePointCrossover(population.get(0), population.get(1), 3, 3);
//
//        System.out.println("Crossover results");
//        System.out.println(Arrays.toString(test.get(0)));
//        System.out.println(Arrays.toString(test.get(1)));
//
//        Integer[] duplicatesList =  {
//                6, 7, 8, 9, 10, 5, 5, 5
//        };
//
//        System.out.println("\nMutation test");
//        System.out.println(Arrays.toString(population.get(0)));
//        System.out.println(Arrays.toString(PopulationHelper.mutation(population.get(0), 0.9f)));
//
//        System.out.println("\nFix genotypes");
//        System.out.println(Arrays.toString(PopulationHelper.fixGenotypes(duplicatesList, 9)));

//        Integer[] cycleFirstParent = {8, 4, 7, 3, 6, 2, 5, 1, 9, 0};
//        Integer[] cycleSecondParent = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
//        List<Integer[]> cycleCrossTest = PopulationHelper.cycleCrossover(cycleFirstParent, cycleSecondParent);
//        System.out.println(Arrays.toString(cycleCrossTest.get(0)));
//        System.out.println(Arrays.toString(cycleCrossTest.get(1)));
    }
}
