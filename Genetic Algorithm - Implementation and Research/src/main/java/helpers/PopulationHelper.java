package helpers;

import dataClasses.CostFlow;

import java.util.*;
import java.util.stream.IntStream;

public class PopulationHelper {
    private static Random random;

    public static List<HashMap<Integer, Integer>> generatePopulation(int populationSize, int machinesNum, int x, int y) {
        List<HashMap<Integer, Integer>> population = new LinkedList<HashMap<Integer, Integer>>();
        int[] machinesArray = IntStream.range(0, machinesNum).toArray();
        int[] locationsArray = IntStream.range(0, x * y).toArray();

        for (int i = 0; i < populationSize; i++) {
            int[] machinesIds = shuffle(Arrays.copyOf(machinesArray, machinesArray.length));
            int[] machinesIndexes = shuffle(Arrays.copyOf(locationsArray, machinesArray.length));
            HashMap<Integer, Integer> mesh = new HashMap<>();

            for (int j = 0; j < machinesIds.length; j++) {
                mesh.put(machinesIds[j], machinesIndexes[j]);
            }
            population.add(mesh);
        }
        return population;
    }

    private static int[] shuffle(int[] array) {
        if (random == null) random = new Random();
        int count = array.length;
        for (int i = count; i > 1; i--) {
            swap(array, i - 1, random.nextInt(i));
        }
        return array;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static int calculateCost(List<CostFlow> costFlows, List<HashMap<Integer, Integer>> population, int rowNum, int colNum) {
        int cost = 0;
        for (HashMap<Integer, Integer> individual : population) {
            cost += calculateCost(costFlows, individual, rowNum, colNum);
        }
        return cost;
    }

    public static int calculateCost(List<CostFlow> costFlows, HashMap<Integer, Integer> individual, int rowNum, int colNum) {
        int cost = 0;
        for (CostFlow costFlow : costFlows) {
            int manhattanDistance = computeManhattanDistance(individual.get(costFlow.getSource()) % colNum,
                    individual.get(costFlow.getSource()) / rowNum,
                    individual.get(costFlow.getDest()) % colNum,
                    individual.get(costFlow.getDest()) / rowNum);
            cost += manhattanDistance * costFlow.getCost() * costFlow.getAmount();
        }
        return cost;
    }

    private static int computeManhattanDistance(int xi, int yi, int xj, int yj) {
        return Math.abs(xi - xj) + Math.abs(yi - yj);
    }

    public HashMap<Integer, Integer> tournamentSelection(List<CostFlow> costFlows,
                                                         List<HashMap<Integer, Integer>> population,
                                                         int tournamentSize, int rowNum,
                                                         int colNum) {

        int bestCost = Integer.MAX_VALUE;
        HashMap<Integer, Integer> bestIndividual = null;

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
