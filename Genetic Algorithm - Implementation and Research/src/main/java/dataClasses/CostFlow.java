package dataClasses;

public class CostFlow {
    private int source;
    private int dest;
    private int cost;
    private int amount;

    public CostFlow(int source, int dest, int cost, int amount) {
        this.source = source;
        this.dest = dest;
        this.cost = cost;
        this.amount = amount;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getDest() {
        return dest;
    }

    public void setDest(int dest) {
        this.dest = dest;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
