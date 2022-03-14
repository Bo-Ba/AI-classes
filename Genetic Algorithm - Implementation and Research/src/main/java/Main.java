import dataClasses.CostFlow;
import helpers.DataLoader;
import helpers.PopulationHelper;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

//        List<Integer[]> population = PopulationHelper.generatePopulation(30, 9, 3, 3);
//        List<CostFlow> testList = DataLoader.findPairs(DataLoader.getCost("easy"), DataLoader.getFlow("easy"));
//        System.out.println(PopulationHelper.calculateCosts(testList, population, 3, 3));

        Integer[] test1 =  {
            1, 2, 3, 4, 5,
        };

        Integer[] test2 =  {
            6, 7, 8, 9, 10
        };

        List<Integer[]> test = PopulationHelper.singlePointCrossover(test1, test2, 3, 3);
        System.out.println(Arrays.toString(test.get(0)));
        System.out.println(Arrays.toString(test.get(1)));

        Integer[] duplicatesList =  {
                6, 7, 8, 9, 10, 5, 5, 5
        };

        System.out.println(Arrays.toString(PopulationHelper.fixGenotypes(duplicatesList, 9)));
    }
}
