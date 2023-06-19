package Repository;

import Model.Account;
import Model.Constants;
import Model.Log;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Repository {
    private List<Account> accounts;
    private Dictionary<Account, Lock> accountMutexDictionary;
    private ReentrantReadWriteLock consistencyCheckMutex;
    private AtomicInteger serialNumber;
    private Random random;
    private int accountsNumber;
    private int maximumAccountAmount;
    private int consistencyCheckInProgressOccurrenceProbability;
    private List<Transaction> transactions;
    private int threadsNumber;

    public Repository() {
        accounts = new LinkedList<>();
        accountMutexDictionary = new Hashtable<>();
        consistencyCheckMutex = new ReentrantReadWriteLock();
        serialNumber = new AtomicInteger(0);
        random = new Random();
        accountsNumber = 100;
        maximumAccountAmount = 1000;
        consistencyCheckInProgressOccurrenceProbability = 2;
        threadsNumber = 10;
        this.PopulateAccounts();
    }

    public void executeTransactions(int transactionsNumber) throws InterruptedException {
        createTransacitons(transactionsNumber);

        float start =  System.nanoTime() / 1000000;

//        executeOneThreadPerTransaction();
        executeTransactionsOnFixedNumberOfThreads();

        float end = System.nanoTime() / 1000000;
        System.out.println("\nFinal consistency check result: " + executeConsistencyCheck());
        System.out.println("\n End work: " + (end - start) / 1000 + " seconds");
    }

    private void PopulateAccounts() {
        for(int i = 0; i < accountsNumber; i++) {
            this.InitializeAccount();
        }
    }

    private void InitializeAccount() {
        var accountSum = random.nextInt(maximumAccountAmount);
        var newAccount = new Account(accountSum);
        accounts.add(newAccount);
        accountMutexDictionary.put(newAccount, new ReentrantLock());
    }

    private boolean checkAmount() {
        for(Account account: accounts) {
            if(!account.verifyAccountConsistency()) {
                return false;
            }
        }
        return true;
    }

    private void createTransacitons(int transactionsNumber) {
        transactions = new ArrayList<>();
        for (int i = 0; i < transactionsNumber; i++) {
            int giverId = random.nextInt(accountsNumber);
            int receiverId = -1;
            while (receiverId != giverId) {
                receiverId = random.nextInt(accountsNumber);
            }
            var giverAccount = accounts.get(giverId);
            var receiverAccount = accounts.get(receiverId);
            var transferAmount = random.nextInt(maximumAccountAmount);
            Transaction t = new Transaction(
                    giverAccount,
                    receiverAccount,
                    accountMutexDictionary.get(giverAccount),
                    accountMutexDictionary.get(receiverAccount),
                    transferAmount,
                    serialNumber,
                    consistencyCheckMutex.readLock()
            );
            transactions.add(t);
        }
    }

    private void executeOneThreadPerTransaction() {
        List<Thread> threads = new ArrayList<>();
        ;
        transactions.stream().forEach(t -> threads.add(new Thread(t)));

        for (Thread thread : threads){
            thread.start();
            executeInProgressConsistencyCheck();
        }

        for (Thread thread : threads){
            try {
                executeInProgressConsistencyCheck();
                thread.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void executeTransactionsOnFixedNumberOfThreads() throws InterruptedException {
        // create thread pool with given size
        ExecutorService service = Executors.newFixedThreadPool(threadsNumber);

        for (Transaction transaction: transactions) {
            service.submit(transaction);
            executeInProgressConsistencyCheck();
        }

        executeInProgressConsistencyCheck();

        // wait for termination
        service.shutdown();
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    }

    private boolean checkThatOperationsAppearInBothAccounts() {
        for(Account account: accounts) {
            var logs = account.getLogs();
            for(Log log: logs) {
                if(Objects.equals(log.getOperationType(), Constants.AddOperation)) {
                    var giverAccount = log.getTransferToAccount();
                    if (!giverAccount.equals(account)) {
                        var giverLog = giverAccount.getLogBySerialNumber(log.getSerialNumber());
                        if(giverLog == null || !giverLog.getOperationType().equals(Constants.RemoveOperation)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean executeConsistencyCheck() {
        this.consistencyCheckMutex.writeLock().lock();
        var result = this.checkAmount() && this.checkThatOperationsAppearInBothAccounts();
        this.consistencyCheckMutex.writeLock().unlock();

        return result;
    }

    private void executeInProgressConsistencyCheck() {
        var chanceOfExecution = random.nextInt(100) + 1;
        if(chanceOfExecution <= consistencyCheckInProgressOccurrenceProbability) {
            if(!executeConsistencyCheck()) {
                System.out.println("In progress consistency check failed");
            }
        }
    }
}

