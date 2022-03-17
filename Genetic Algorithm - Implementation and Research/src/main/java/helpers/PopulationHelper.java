package helpers;

import dataClasses.CostFlow;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PopulationHelper {
    private static final Random random = new Random();

    public static List<Integer[]> generatePopulation(int populationSize, int machinesNum, int x, int y) {
        List<Integer[]> population = new LinkedList<>();
        Integer[] locationsArray = IntStream.range(0, x * y).boxed().toArray(Integer[]::new);

        for (int i = 0; i < populationSize; i++) {
            Integer[] mesh = shuffle(locationsArray.clone());

            population.add(mesh);
        }
        return population;
    }

    private static Integer[] shuffle(Integer[] array) {
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

    private static void swapRandomIndexes(Integer[] array) {
        Random r = new Random();
        int firstIndex = r.nextInt(array.length);
        int secondIndex = r.nextInt(array.length);
        while (secondIndex == firstIndex) {
            secondIndex = r.nextInt(array.length);
        }
        int temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }

    public static int calculateCost(List<CostFlow> costFlows, List<Integer[]> population, int rowNum, int colNum) {
        int cost = 0;
        for (Integer[] individual : population) {
            cost += calculateCost(costFlows, individual, rowNum, colNum);
        }
        return cost;
    }

    public static List<Integer> calculateCosts(List<CostFlow> costFlows, List<Integer[]> population, int rowNum, int colNum) {
        List<Integer> costs = new LinkedList<>();
        for (Integer[] individual : population) {
            costs.add(calculateCost(costFlows, individual, rowNum, colNum));
        }
        return costs;
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

    public static Integer[] tournamentSelection(List<CostFlow> costFlows,
                                                List<Integer[]> population,
                                                int tournamentSize,
                                                int rowNum,
                                                int colNum) {
        int bestCost = Integer.MAX_VALUE;
        Integer[] bestIndividual = null;
        List<Integer[]> tempPopulation = new ArrayList<>(population);

        for (int i = 0; i < tournamentSize; i++) {
            int individualIndex = random.nextInt(tempPopulation.size());
            int individualCost = calculateCost(costFlows, tempPopulation.get(individualIndex), rowNum, colNum);
            if (individualCost < bestCost) {
                bestIndividual = tempPopulation.get(individualIndex);
                bestCost = individualCost;
            }
            tempPopulation.remove(individualIndex);
        }

        return bestIndividual;
    }

    public static Integer[] rouletteSelection(List<CostFlow> costFlows,
                                              List<Integer[]> population,
                                              int rowNum,
                                              int colNum) {
        List<Integer> costs = calculateCosts(costFlows, population, rowNum, colNum);
        int costsSum = costs.stream().mapToInt(Integer::intValue).sum();

        List<Double> probabilitiesList = costs.stream().map(cost -> (double) costsSum / (double) cost).collect(Collectors.toList());
        double partsSum = probabilitiesList.stream().mapToDouble(Double::doubleValue).sum();
        probabilitiesList = probabilitiesList.stream().map(db -> db / partsSum).collect(Collectors.toList());

        double probabilitiesSum = 0;
        Random r = new Random();
        double randomPick = r.nextDouble();
        for (int i = 0; i < probabilitiesList.size(); i++) {
            probabilitiesSum += probabilitiesList.get(i);
            if (i == costs.size() - 1) probabilitiesSum = 1;
            if (randomPick < probabilitiesSum) return population.get(i);
        }

        return null;
    }

    public static List<Integer[]> singlePointCrossover(Integer[] firstIndividual, Integer[] secondIndividual, int meshRows, int meshColumns) {
        firstIndividual = firstIndividual.clone();
        secondIndividual = secondIndividual.clone();

        int splitPoint = random.nextInt(firstIndividual.length);
        if (splitPoint == 0) splitPoint++;

        Integer[] temp = secondIndividual.clone();
        System.arraycopy(firstIndividual, splitPoint, secondIndividual, splitPoint, firstIndividual.length - splitPoint);
        System.arraycopy(temp, splitPoint, firstIndividual, splitPoint, firstIndividual.length - splitPoint);

        fixGenotypes(firstIndividual, meshRows * meshColumns);
        fixGenotypes(secondIndividual, meshRows * meshColumns);

        return (Arrays.asList(firstIndividual, secondIndividual));
    }

    public static Integer[] fixGenotypes(final Integer[] individual, int possiblePositions) {
        HashSet<Integer> positionsSet = new HashSet<>(Arrays.asList(individual));
        HashSet<Integer> currentSet = new HashSet<>();
        int index = 0;
        if (positionsSet.size() == individual.length) return individual;

        for (int item : individual) {
            if (currentSet.contains(item)) {
                int newPosition = item;
                HashSet<Integer> drawnNumbers = new HashSet<>();
                while (positionsSet.contains(newPosition)) {
                    newPosition = random.nextInt(possiblePositions);
                    drawnNumbers.add(newPosition);
                    if (drawnNumbers.size() == possiblePositions) break;
                }
                positionsSet.add(newPosition);
                individual[index] = newPosition;
            }
            index++;
            currentSet.add(item);
        }

        return individual;
    }

    public static Integer[] mutation(Integer[] individual, float mutationProbability) {
        if (random.nextFloat() < mutationProbability) {
            swapRandomIndexes(individual);
        }
        return individual;
    }
}
