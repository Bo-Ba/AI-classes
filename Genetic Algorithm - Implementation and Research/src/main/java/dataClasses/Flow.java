package dataClasses;

public class Flow {
    private int source;
    private int dest;
    private int amount;

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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "dataClasses.Flow{" +
                "source=" + source +
                ", dest=" + dest +
                ", amount=" + amount +
                '}';
    }
}
