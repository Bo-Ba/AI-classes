import dataClasses.Cost;
import dataClasses.CostFlow;
import dataClasses.Flow;
import helpers.DataLoader;
import helpers.PopulationHelper;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        HashMap<Integer, Integer> generatedData = new HashMap<>();
        generatedData.put(0, 5);
        generatedData.put(1, 7);
        generatedData.put(2, 2);
        generatedData.put(3, 4);
        generatedData.put(4, 3);
        generatedData.put(5, 0);
        generatedData.put(6, 6);
        generatedData.put(7, 1);
        generatedData.put(8, 8);
        generatedData.put(9, 9);
        generatedData.put(10, 10);
        generatedData.put(11, 11);
        generatedData.put(12, 12);
        generatedData.put(13, 13);
        generatedData.put(14, 14);
        generatedData.put(15, 15);
        generatedData.put(16, 16);
        generatedData.put(17, 17);
        generatedData.put(18, 18);
        generatedData.put(19, 19);
        generatedData.put(20, 20);
        generatedData.put(21, 21);
        generatedData.put(22, 22);
        generatedData.put(23, 30);

        PopulationHelper.generatePopulation(30, 24, 5 ,6);
        List<CostFlow> testList = DataLoader.findPairs(DataLoader.getCost("easy"), DataLoader.getFlow("easy"));
        System.out.println(PopulationHelper.calculateCost(testList, generatedData, 5, 6));
    }
}
