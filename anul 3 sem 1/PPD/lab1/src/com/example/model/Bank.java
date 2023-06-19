package com.example.model;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Math.abs;

public class Bank {
    private static final int MAX_THREADS = 8;
    private static final int NO_TRANSACTIONS = 1000;
    private static final int MAX_ACCOUNTS = 100;
    private static final int MAX_AMOUNT = 1000000;
    private static final int PROBABILITY_TO_CHECK_CONSISTENCY = 10;
    private final Random random;
    private final List<Account> accounts;
    private final List<Transaction> transactions;
    private final ExecutorService executorService;
    private AtomicInteger atomicIdGenerator;
    private ReentrantReadWriteLock consistencyMutex;

    public Bank() {
        random = new Random(LocalDateTime.now().getNano());
        accounts = new LinkedList<>();
        transactions = new LinkedList<>();
        atomicIdGenerator = new AtomicInteger(1);
        consistencyMutex = new ReentrantReadWriteLock();
        for (int i = 0; i < MAX_ACCOUNTS; i++) {
            Account account = new Account(i, MAX_AMOUNT);
            accounts.add(account);
        }
        for (int i = 0; i < NO_TRANSACTIONS; i++) {
            addTransfer();
        }
        executorService = Executors.newFixedThreadPool(MAX_THREADS);
    }

    public void start(){
        System.out.println("Running...");
        long startTime = System.nanoTime();

        transactions.forEach(transaction -> {
            executorService.execute(transaction);
            checkConsistencyWithProbability();
        });

        executorService.shutdown();

        long elapsedTime = System.nanoTime() - startTime;
        System.out.println("Done " + NO_TRANSACTIONS + " transactions in " + elapsedTime / 1000000);
        if (checkConsistency()) System.out.println("Consistency check GOOD");
        else System.out.println("Consistency check BAD");
    }

    private void checkConsistencyWithProbability() {
        if (random.nextInt(1, 100) > PROBABILITY_TO_CHECK_CONSISTENCY) {
            return;
        }
        if (!checkConsistency())System.out.println("Consistency check BAD");
    }

    private boolean checkConsistency() {
        boolean isConsistent = false;
        consistencyMutex.writeLock().lock();
        if (checkIntegrityOfAccounts() && checkConsistencyOfHistories()) {
            isConsistent = true;
        }
        consistencyMutex.writeLock().unlock();
        return isConsistent;
    }

    private boolean checkIntegrityOfAccounts() {
        return accounts.stream()
                .allMatch((Account::checkIntegrity));
    }

    private boolean checkConsistencyOfHistories() {
        for (Account account : accounts) {
            List<Operation> sentHistory = account.getSentHistory();
            for (Operation sentOperation : sentHistory) {
                int accountId = sentOperation.getOtherAccountId();
                Account receiverAccount = accounts.get(accountId);
                List<Operation> receivedHistory = receiverAccount.getReceivedHistory();
                boolean matching = receivedHistory.stream()
                        .anyMatch(receivedOperation -> receivedOperation.getId() == sentOperation.getId());
                if (!matching) {
                    return false;
                }
            }
        }
        return true;
    }

    private void addTransfer() {
        int senderIndex = getRandomAccountIndex();
        int receiverIndex = getRandomAccountIndex();
        while (receiverIndex == senderIndex) {
            receiverIndex = getRandomAccountIndex();
        }
        long transferAmount = getRandomAmount();
        Transaction transaction = new Transaction(accounts.get(senderIndex), accounts.get(receiverIndex),
                transferAmount, atomicIdGenerator, consistencyMutex);
        transactions.add(transaction);
    }

    private int getRandomAccountIndex() {
        return abs(random.nextInt(MAX_ACCOUNTS));
    }

    private int getRandomAmount() {
        return abs(random.nextInt(1, MAX_AMOUNT));
    }
}
