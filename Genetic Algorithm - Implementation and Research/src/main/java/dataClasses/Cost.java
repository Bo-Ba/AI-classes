package dataClasses;

public class Cost {
    private int source;
    private int dest;
    private int cost;

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

    @Override
    public String toString() {
        return "dataClasses.Cost{" +
                "source=" + source +
                ", dest=" + dest +
                ", cost=" + cost +
                '}';
    }
}
