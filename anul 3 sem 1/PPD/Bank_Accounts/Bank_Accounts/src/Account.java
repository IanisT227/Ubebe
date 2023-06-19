import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Account {
    private final int id;
    private final int initialAmount;
    private int currentAmount;
    private final ArrayList<Log> logs;

    public Account(int id, int initialAmount) {
        this.id = id;
        this.initialAmount = initialAmount;
        this.currentAmount = initialAmount;
        this.logs = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void addLog(int serialNumber, int amount, Account account, String details) {
        var newLog = new Log(new AtomicInteger(serialNumber), account, details, amount);
        this.logs.add(newLog);
    }

    public ArrayList<Log> getLogs() {
        return logs;
    }

    public boolean enoughFundsToTransfer(int amount) {
        return amount <= this.currentAmount;
    }

    public void transferAmount(int amount, int serialNumber, Account receiver) {
        this.currentAmount -= amount;
        this.addLog(serialNumber, amount, receiver, "transferred");
    }

    public void receiveAmount(int amount, int serialNumber, Account sender) {
        this.currentAmount += amount;
        this.addLog(serialNumber, amount, sender, "received");
    }

    @Override
    public String toString() {
        return "Account{" +
                "initialAmount=" + initialAmount +
                ", currentAmount=" + currentAmount +
                '}';
    }
}
