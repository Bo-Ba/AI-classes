package helpers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataClasses.Cost;
import dataClasses.CostFlow;
import dataClasses.Flow;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DataLoader {

    public static List<Cost> getCost(String fileParam) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File("Genetic Algorithm - Implementation and Research/src/main/resources/data/" + fileParam + "_cost.json");

        return objectMapper.readValue(file, new TypeReference<List<Cost>>(){});
    }

    public static List<Flow> getFlow(String fileParam) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File("Genetic Algorithm - Implementation and Research/src/main/resources/data/" + fileParam + "_flow.json");

        return objectMapper.readValue(file, new TypeReference<List<Flow>>(){});
    }

    public static List<CostFlow> findPairs(List<Cost> costs, List<Flow> flows) {
        List<CostFlow> result = new ArrayList<>();
        for(Cost cost : costs) {
            for (int i = 0; i < flows.size(); i++) {
                Flow flow = flows.get(i);
                if(cost.getSource() == flow.getSource() && cost.getDest() == flow.getDest()) {
                    result.add(new CostFlow(cost.getSource(), cost.getDest(), cost.getCost(), flow.getAmount()));
                    flows.remove(i);
                    break;
                }
            }
        }
        return result;
    }
}
