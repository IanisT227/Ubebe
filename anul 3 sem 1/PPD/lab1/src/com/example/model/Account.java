package com.example.model;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private final List<Operation> sentHistory;
    private final List<Operation> receivedHistory;
    private final Lock mutex;
    private final long startingBalance;
    private final int id;
    private long balance;

    public Account(int id, long balance) {
        this.id = id;
        this.startingBalance = balance;
        this.balance = balance;
        sentHistory = new LinkedList<>();
        receivedHistory = new LinkedList<>();
        mutex = new ReentrantLock();
    }

    public boolean checkIntegrity() {
        long receivedAmount = receivedHistory.stream()
                .mapToLong(Operation::getAmount)
                .sum();
        long sentAmount = sentHistory.stream()
                .mapToLong(Operation::getAmount)
                .sum();
        return balance == startingBalance + receivedAmount - sentAmount;
    }

    public void sendMoney(Account account, long amount, Operation operation) {
        balance -= amount;
        sentHistory.add(operation);
    }

    public void receiveMoney(Account account, long amount, Operation operation) {
        balance += amount;
        receivedHistory.add(operation);
    }

    public boolean hasEnoughMoney(long amount) {
        return balance >= amount;
    }

    public int getId() {
        return id;
    }

    public long getStartingBalance() {
        return startingBalance;
    }

    public Lock getMutex() {
        return mutex;
    }

    public List<Operation> getSentHistory() {
        return sentHistory;
    }

    public List<Operation> getReceivedHistory() {
        return receivedHistory;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
