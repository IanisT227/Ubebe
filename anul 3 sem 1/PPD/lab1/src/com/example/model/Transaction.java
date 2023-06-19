package com.example.model;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Transaction implements Runnable {
    private final Account senderAccount;
    private final Account receiverAccount;
    private long amount;
    private AtomicInteger atomicInteger;
    private ReentrantReadWriteLock consistencyLock;


    public Transaction(Account senderAccount, Account receiverAccount, long amount, AtomicInteger atomicInteger, ReentrantReadWriteLock consistencyLock) {
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.amount = amount;
        this.atomicInteger = atomicInteger;
        this.consistencyLock = consistencyLock;
    }

    @Override
    public void run() {
        consistencyLock.readLock().lock();
        senderAccount.getMutex().lock();
        if (senderAccount.hasEnoughMoney(amount)) {
            int operationId = atomicInteger.get();
            Operation sendOperation = new Operation(operationId, receiverAccount.getId(), amount);
            senderAccount.sendMoney(receiverAccount, amount, sendOperation);
            senderAccount.getMutex().unlock();
            Operation receiveOperation = new Operation(operationId, senderAccount.getId(), amount);
            receiverAccount.getMutex().lock();
            receiverAccount.receiveMoney(senderAccount, amount, receiveOperation);
            receiverAccount.getMutex().unlock();
            atomicInteger.set(atomicInteger.get() + 1);
        } else {
            senderAccount.getMutex().unlock();
        }
        consistencyLock.readLock().unlock();
    }
}
