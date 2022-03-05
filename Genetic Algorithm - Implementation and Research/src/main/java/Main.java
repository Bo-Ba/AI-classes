import dataClasses.Cost;
import dataClasses.CostFlow;
import dataClasses.Flow;
import helpers.DataLoader;
import helpers.PopulationHelper;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Integer[] generatedData = new Integer[24];
        generatedData[0] = 5;
        generatedData[1] = 7;
        generatedData[2] = 2;
        generatedData[3] = 4;
        generatedData[4] = 3;
        generatedData[5] = 0;
        generatedData[6] = 6;
        generatedData[7] = 1;
        generatedData[8] = 8;
        generatedData[9] = 9;
        generatedData[10] = 10;
        generatedData[11] = 11;
        generatedData[12] = 12;
        generatedData[13] = 13;
        generatedData[14] = 14;
        generatedData[15] = 15;
        generatedData[16] = 16;
        generatedData[17] = 17;
        generatedData[18] = 18;
        generatedData[19] = 19;
        generatedData[20] = 20;
        generatedData[21] = 21;
        generatedData[22] = 22;
        generatedData[23] = 30;

        PopulationHelper.generatePopulation(30, 24, 5 ,6);
        List<CostFlow> testList = DataLoader.findPairs(DataLoader.getCost("easy"), DataLoader.getFlow("easy"));
        System.out.println(PopulationHelper.calculateCost(testList, generatedData, 5, 6));
    }
}
