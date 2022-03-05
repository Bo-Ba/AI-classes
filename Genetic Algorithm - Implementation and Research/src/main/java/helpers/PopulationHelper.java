package helpers;

import dataClasses.CostFlow;

import java.util.*;
import java.util.stream.IntStream;

public class PopulationHelper {
    private static Random random;

    public static List<Integer[]> generatePopulation(int populationSize, int machinesNum, int x, int y) {
        List<Integer[]> population = new LinkedList<>();
        Integer[] locationsArray = IntStream.range(0, x * y).boxed().toArray(Integer[]::new);

        for (int i = 0; i < populationSize; i++) {
            Integer[] mesh = shuffle(locationsArray);

            population.add(mesh);
        }
        return population;
    }

    private static Integer[] shuffle(Integer[] array) {
        if (random == null) random = new Random();
        int count = array.length;
        for (int i = count; i > 1; i--) {
            swap(array, i - 1, random.nextInt(i));
        }
        return array;
    }

    private static void swap(Integer[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static int calculateCost(List<CostFlow> costFlows, List<Integer[]> population, int rowNum, int colNum) {
        int cost = 0;
        for (Integer[] individual : population) {
            cost += calculateCost(costFlows, individual, rowNum, colNum);
        }
        return cost;
    }

    public static int calculateCost(List<CostFlow> costFlows, Integer[] individual, int rowNum, int colNum) {
        int cost = 0;
        for (CostFlow costFlow : costFlows) {
            int manhattanDistance = computeManhattanDistance(individual[costFlow.getSource()] % colNum,
                    individual[costFlow.getSource()] / rowNum,
                    individual[costFlow.getDest()] % colNum,
                    individual[costFlow.getDest()] / rowNum);
            cost += manhattanDistance * costFlow.getCost() * costFlow.getAmount();
        }
        return cost;
    }

    private static int computeManhattanDistance(int xi, int yi, int xj, int yj) {
        return Math.abs(xi - xj) + Math.abs(yi - yj);
    }

    public Integer[] tournamentSelection(List<CostFlow> costFlows,
                                         List<Integer[]> population,
                                         int tournamentSize, int rowNum,
                                         int colNum) {

        int bestCost = Integer.MAX_VALUE;
        Integer[] bestIndividual = null;

        for (int i = 0; i < tournamentSize; i++) {
            int individualIndex = random.nextInt(population.size());
            if (calculateCost(costFlows, population.get(individualIndex), rowNum, colNum) < bestCost) {
                bestIndividual = population.get(individualIndex);
            }
            population.remove(individualIndex);
        }

        return bestIndividual;
    }
}
