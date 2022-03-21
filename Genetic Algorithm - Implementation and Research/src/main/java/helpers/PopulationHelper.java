package helpers;

import dataClasses.CostFlow;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PopulationHelper {
    private static final Random random = new Random();
    private static List<Integer> fullGrid;


    public static List<Integer[]> generatePopulation(int populationSize, int machinesNum, int rowNum, int colNum) {
        List<Integer[]> population = new LinkedList<>();
        Integer[] locationsArray = IntStream.range(0, rowNum * colNum).boxed().toArray(Integer[]::new);
        fullGrid = IntStream.range(0, rowNum * colNum).boxed().collect(Collectors.toList());

        for (int i = 0; i < populationSize; i++) {
            Integer[] grid = shuffle(locationsArray);
            grid = Arrays.copyOf(grid, machinesNum);
            population.add(grid);
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
        List<Integer> possiblePlaces = new ArrayList<>(fullGrid);
        possiblePlaces.removeAll(Arrays.asList(array));

        Random r = new Random();
        int firstIndex = r.nextInt(array.length);
        int secondIndex;

        if(possiblePlaces.size() == 0) {
            secondIndex = r.nextInt(array.length);

            while (secondIndex == firstIndex) {
                secondIndex = r.nextInt(array.length);
            }

            int temp = array[firstIndex];
            array[firstIndex] = array[secondIndex];
            array[secondIndex] = temp;
        } else {
            secondIndex = r.nextInt(possiblePlaces.size());
            array[firstIndex] = possiblePlaces.get(secondIndex);
        }
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
        if (rowNum == 1) return calculateCostOneDim(costFlows, individual, rowNum, colNum);
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

    private static int calculateCostOneDim(List<CostFlow> costFlows, Integer[] individual, int rowNum, int colNum) {
        int cost = 0;
        for (CostFlow costFlow : costFlows) {
            int manhattanDistance = computeManhattanDistance(0,
                    individual[costFlow.getSource()] / rowNum,
                    0,
                    individual[costFlow.getDest()] / rowNum);
            cost += manhattanDistance * costFlow.getCost() * costFlow.getAmount();
        }
        return cost;
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
        int sum = costs.stream().mapToInt(Integer::intValue).sum();

        double reversedProbabilitySum = 0;
        Random r = new Random();
        double randomPick = r.nextDouble();
        for (int i = 0; i < costs.size(); i++) {
            double probability = (double) costs.get(i) / (double) sum;
            reversedProbabilitySum += (1 - probability) / (costs.size() - 1);
            if (reversedProbabilitySum > randomPick) return population.get(i);
        }
        return null;
    }

    public static Integer[] rouletteSelectionExp(List<CostFlow> costFlows,
                                                 List<Integer[]> population,
                                                 int rowNum,
                                                 int colNum,
                                                 double selectionPressure) {
        List<Integer> costs = calculateCosts(costFlows, population, rowNum, colNum);

        double max = Collections.max(costs);
        List<Double> mappedValues = costs.stream().mapToDouble(cost -> Math.exp(-selectionPressure * (double) cost / max)).boxed().collect(Collectors.toList());
        double sumMapped = mappedValues.stream().mapToDouble(Double::doubleValue).sum();

        double probability = 0;
        Random r = new Random();
        double randomPick = r.nextDouble();
        for (int i = 0; i < costs.size(); i++) {
            probability += mappedValues.get(i) / sumMapped;
            if (probability > randomPick) return population.get(i);
        }
        return null;
    }

    public static List<Integer[]> singlePointCrossover(Integer[] firstIndividual, Integer[] secondIndividual, int rowsNum, int colNum, float crossoverProbability) {
        firstIndividual = firstIndividual.clone();
        secondIndividual = secondIndividual.clone();

        if(random.nextFloat() > crossoverProbability) return Arrays.asList(firstIndividual, secondIndividual);

        int splitPoint = random.nextInt(firstIndividual.length);
        if (splitPoint == 0) splitPoint++;

        Integer[] temp = secondIndividual.clone();
        System.arraycopy(firstIndividual, splitPoint, secondIndividual, splitPoint, firstIndividual.length - splitPoint);
        System.arraycopy(temp, splitPoint, firstIndividual, splitPoint, firstIndividual.length - splitPoint);

        fixGenotypes(firstIndividual, rowsNum * colNum);
        fixGenotypes(secondIndividual, rowsNum * colNum);

        return Arrays.asList(firstIndividual, secondIndividual);
    }

    public static Integer[] fixGenotypes(final Integer[] individual, int possiblePositions) {
        HashSet<Integer> positionsSet = new HashSet<>(Arrays.asList(individual));
        List<Integer> freePositionsList = IntStream.range(0, possiblePositions).boxed().collect(Collectors.toList());
        freePositionsList.removeAll(positionsSet);
        Collections.shuffle(freePositionsList);

        HashSet<Integer> currentSet = new HashSet<>();
        int index = 0;
        if (positionsSet.size() == individual.length) return individual;

        for (int item : individual) {
            if (currentSet.contains(item)) {
                if (freePositionsList.size() == 0) break;
                int newPosition = freePositionsList.remove(0);
                individual[index] = newPosition;
            }
            index++;
            currentSet.add(item);
        }

        return individual;
    }

    public static Integer[] mutate(Integer[] individual, float mutationProbability) {
        if (random.nextFloat() < mutationProbability) {
            swapRandomIndexes(individual);
        }
        return individual;
    }

    public static List<Integer[]> cycleCrossover(Integer[] firstIndividual, Integer[] secondIndividual, float crossoverProbability) {
        Integer[] firstChild = firstIndividual.clone();
        Integer[] secondChild = secondIndividual.clone();
        if(random.nextFloat() > crossoverProbability) return Arrays.asList(firstChild, secondChild);

        int genotypeSize = firstIndividual.length;
        List<Integer> firstParent = Arrays.asList(firstIndividual);
        List<Integer> secondParent = Arrays.asList(secondIndividual);


        Set<Integer> visitedIndices = new HashSet<Integer>(genotypeSize);
        List<Integer> indices = new ArrayList<Integer>(genotypeSize);

        int numberOfCycles = 1;
        int idx = 0;

        while (visitedIndices.size() < genotypeSize) {
            indices.add(idx);
            int item = secondParent.get(idx);
            idx = firstParent.indexOf(item);

            while (idx != indices.get(0)) {
                indices.add(idx);
                item = secondParent.get(idx);
                idx = firstParent.indexOf(item);
            }

            if (numberOfCycles++ % 2 != 0) {
                for (int i : indices) {
                    int tmp = firstChild[i];
                    firstChild[i] = secondChild[i];
                    secondChild[i] = tmp;
                }
            }

            visitedIndices.addAll(indices);

            idx = (indices.get(0) + 1) % genotypeSize;
            while (visitedIndices.contains(idx) && visitedIndices.size() < genotypeSize) {
                idx++;
                if (idx >= genotypeSize) {
                    idx = 0;
                }
            }
            indices.clear();
        }

        return Arrays.asList(firstChild, secondChild);
    }
}
