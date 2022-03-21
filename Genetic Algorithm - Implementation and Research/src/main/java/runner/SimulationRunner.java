package runner;

import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import dataClasses.CostFlow;
import helpers.DataLoader;
import helpers.PopulationHelper;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimulationRunner {
    private int populationSize;
    private int machinesNum;
    private int rowNum;
    private int colNum;
    private int tournamentSize;
    private double selectionPressure;
    private float mutationProbability;
    private float crossoverProbability;
    private int generationNum;
    private String dataFile;
    private Random r = new Random();

    public void random() throws IOException {
        List<CostFlow> data = DataLoader.findPairs(DataLoader.getCost(dataFile), DataLoader.getFlow(dataFile));
        List<Integer> best = new LinkedList<>();
        List<Integer> worst = new LinkedList<>();
        List<Double> avg = new LinkedList<>();

        int genNum = 0;
        while (genNum < generationNum) {
            List<Integer[]> population = PopulationHelper.generatePopulation(populationSize, machinesNum, rowNum, colNum);
            List<Integer> costs = PopulationHelper.calculateCosts(data, population, rowNum, colNum);
            best.add(Collections.min(costs));
            worst.add(Collections.max(costs));
            avg.add(costs.stream().mapToDouble(cost -> cost).average().getAsDouble());
        }
        genNum++;
    }

    public void runSimulationTournament() throws IOException, PythonExecutionException {
        List<CostFlow> data = DataLoader.findPairs(DataLoader.getCost(dataFile), DataLoader.getFlow(dataFile));
        List<Integer[]> population = PopulationHelper.generatePopulation(populationSize, machinesNum, rowNum, colNum);
        List<Integer[]> newGeneration = new LinkedList<>();
        List<Integer> best = new LinkedList<>();
        List<Integer> worst = new LinkedList<>();
        List<Double> avg = new LinkedList<>();
        List<Integer> all = new LinkedList<>();


        int genNum = 0;
        while (genNum < generationNum) {
            List<Integer> costs = PopulationHelper.calculateCosts(data, population, rowNum, colNum);

            best.add(Collections.min(costs));
            worst.add(Collections.max(costs));
            avg.add(costs.stream().mapToDouble(cost -> cost).average().getAsDouble());
            all.addAll(costs);

            if (Collections.min(costs) == 4818) {
                System.out.println(++genNum);
                break;
            }


            while (newGeneration.size() != population.size()) {
                Integer[] firstParent = PopulationHelper.tournamentSelection(data, population, tournamentSize, rowNum, colNum);
                Integer[] secondParent = PopulationHelper.tournamentSelection(data, population, tournamentSize, rowNum, colNum);

                List<Integer[]> newIndividuals = PopulationHelper.singlePointCrossover(firstParent, secondParent, rowNum, colNum, crossoverProbability);

                PopulationHelper.mutate(newIndividuals.get(0), mutationProbability);
                PopulationHelper.mutate(newIndividuals.get(1), mutationProbability);

                newGeneration.add(newIndividuals.get(0));
                if (newGeneration.size() != population.size()) newGeneration.add(newIndividuals.get(1));
                else break;
            }

            population = new LinkedList<>(newGeneration);
            newGeneration.clear();
            genNum++;
        }
        printGraph(best, worst, avg, all, genNum);
    }

    public void runSimulationRoulette() throws IOException, PythonExecutionException {
        List<CostFlow> data = DataLoader.findPairs(DataLoader.getCost(dataFile), DataLoader.getFlow(dataFile));
        List<Integer[]> population = PopulationHelper.generatePopulation(populationSize, machinesNum, rowNum, colNum);
        List<Integer[]> newGeneration = new LinkedList<>();
        List<Integer> best = new LinkedList<>();
        List<Integer> worst = new LinkedList<>();
        List<Double> avg = new LinkedList<>();
        List<Integer> all = new LinkedList<>();

        int genNum = 0;
        while (genNum < generationNum) {
            List<Integer> costs = PopulationHelper.calculateCosts(data, population, rowNum, colNum);

            best.add(Collections.min(costs));
            worst.add(Collections.max(costs));
            avg.add(costs.stream().mapToDouble(cost -> cost).average().getAsDouble());
            all.addAll(costs);

            if (Collections.min(costs) == 4818) {
                System.out.println("Generation number " + ++genNum);
                break;
            }

//            Integer[] elite = PopulationHelper.rouletteSelectionExp(data, population, rowNum, colNum, 200);
//            newGeneration.add(elite);

            while (newGeneration.size() != population.size()) {
                Integer[] firstParent = PopulationHelper.rouletteSelectionExp(data, population, rowNum, colNum, selectionPressure);
                Integer[] secondParent = PopulationHelper.rouletteSelectionExp(data, population, rowNum, colNum, selectionPressure);

                List<Integer[]> newIndividuals = PopulationHelper.singlePointCrossover(firstParent, secondParent, rowNum, colNum, crossoverProbability);
                PopulationHelper.mutate(newIndividuals.get(0), mutationProbability);
                PopulationHelper.mutate(newIndividuals.get(1), mutationProbability);

                newGeneration.add(newIndividuals.get(0));
                if (newGeneration.size() != population.size()) newGeneration.add(newIndividuals.get(1));
                else break;
            }

            population = new LinkedList<>(newGeneration);
            newGeneration.clear();
            genNum++;
        }
        printGraph(best, worst, avg, all, genNum);
    }

    public void printGraph(List<Integer> best, List<Integer> worst, List<Double> avg, List<Integer> all, int generationNum) throws PythonExecutionException, IOException {
        List<Integer> x = IntStream.range(0, generationNum).boxed().collect(Collectors.toList());

        Plot plt = Plot.create();
        plt.plot().add(x, best).linestyle("-").label("best");
        plt.plot().add(x, worst).linestyle("-").label("worst");
        plt.plot().add(x, avg).linestyle("-").label("avg");
        plt.legend().loc("upper right");
        plt.show();

        System.out.println("Best found: " + Collections.min(best));
        System.out.println("Worst found: " + Collections.max(worst));
        System.out.println("Avg found: " + avg.stream().mapToDouble(cost -> cost).average().getAsDouble());
        System.out.println("SD found: " + calculateSD(all));
    }

    public static double calculateSD(List<Integer> data)
    {
        Integer[] numArray = new Integer[data.size()];
        data.toArray(numArray);
        double sum = 0.0, standardDeviation = 0.0;
        int length = numArray.length;

        for(double num : numArray) {
            sum += num;
        }

        double mean = sum/length;

        for(double num: numArray) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation/length);
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public int getMachinesNum() {
        return machinesNum;
    }

    public void setMachinesNum(int machinesNum) {
        this.machinesNum = machinesNum;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getColNum() {
        return colNum;
    }

    public void setColNum(int colNum) {
        this.colNum = colNum;
    }

    public int getTournamentSize() {
        return tournamentSize;
    }

    public void setTournamentSize(int tournamentSize) {
        this.tournamentSize = tournamentSize;
    }

    public float getMutationProbability() {
        return mutationProbability;
    }

    public void setMutationProbability(float mutationProbability) {
        this.mutationProbability = mutationProbability;
    }

    public int getGenerationNum() {
        return generationNum;
    }

    public void setGenerationNum(int generationNum) {
        this.generationNum = generationNum;
    }

    public String getDataFile() {
        return dataFile;
    }

    public void setDataFile(String dataFile) {
        this.dataFile = dataFile;
    }

    public double getSelectionPressure() {
        return selectionPressure;
    }

    public void setSelectionPressure(double selectionPressure) {
        this.selectionPressure = selectionPressure;
    }

    public float getCrossoverProbability() {
        return crossoverProbability;
    }

    public void setCrossoverProbability(float crossoverProbability) {
        this.crossoverProbability = crossoverProbability;
    }

    @Override
    public String toString() {
        return "SimulationRunner{" +
                "populationSize=" + populationSize +
                ", machinesNum=" + machinesNum +
                ", rowNum=" + rowNum +
                ", colNum=" + colNum +
                ", tournamentSize=" + tournamentSize +
                ", mutationProbability=" + mutationProbability +
                ", generationNum=" + generationNum +
                ", dataFile='" + dataFile + '\'' +
                '}';
    }
}
