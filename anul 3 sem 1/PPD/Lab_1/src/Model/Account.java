package Model;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private int amount;
    private int intialAmount;
    private List<Log> logs;
    private Dictionary<Integer, Log> serialNumberToOperationDictionary;

    public Account(int amount) {
        this.amount = amount;
        this.intialAmount = amount;
        this.logs = new LinkedList<>();
        this.serialNumberToOperationDictionary = new Hashtable<>();
    }

    public void addMoneyToAmount(int addedMoneyValue, Account transferToAccount, int serialNumber) {
        this.amount += addedMoneyValue;
        var log = new Log(serialNumber, Constants.AddOperation, transferToAccount, addedMoneyValue);
        this.addLog(log);
    }

    public void removeMoneyFromAmount(int removedMoneyValue, Account transferToAccount, int serialNumber) {
        this.amount -= removedMoneyValue;
        var log = new Log(serialNumber, Constants.RemoveOperation, transferToAccount, removedMoneyValue);
        this.addLog(log);
    }

    public boolean checkIfMoneyCanBeRemoved(int removeMoneyValue, int serialNumber) {
        var log = new Log(serialNumber, Constants.CheckOperation, removeMoneyValue);
        this.addLog(log);

        return amount >= removeMoneyValue;
    }

    public boolean verifyAccountConsistency() {
        var currentComputedAmount = this.intialAmount;
        for(Log log: this.logs) {
            if(Objects.equals(log.operationType, Constants.AddOperation)) {
                currentComputedAmount += log.operationValue;
            } else if(Objects.equals(log.operationType, Constants.RemoveOperation)) {
                currentComputedAmount -= log.operationValue;
            }
        }

        return currentComputedAmount == amount;
    }

    public List<Log> getLogs() {
        return logs;
    }

    private void addLog(Log log) {
        this.serialNumberToOperationDictionary.put(log.serialNumber, log);
        this.logs.add(log);
    }

    public Log getLogBySerialNumber(int serialNumber) {
        return this.serialNumberToOperationDictionary.get(serialNumber);
    }
}
