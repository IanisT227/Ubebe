package Repository;

import Model.Account;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Transaction implements Runnable {
    private Account giverAccount;
    private Account receiverAccount;
    private Lock giverMutex;
    private Lock receiverMutex;
    private int transferAmount;
    private AtomicInteger serialNumber;
    private Lock consistencyCheckReadLock;

    public Transaction(Account giverAccount, Account receiverAccount, Lock giverMutex, Lock receiverMutex, int transferAmount, AtomicInteger serialNumber, Lock consistencyCheckReadLock) {
        this.giverAccount = giverAccount;
        this.receiverAccount = receiverAccount;
        this.giverMutex = giverMutex;
        this.receiverMutex = receiverMutex;
        this.transferAmount = transferAmount;
        this.serialNumber = serialNumber;
        this.consistencyCheckReadLock = consistencyCheckReadLock;
    }

    @Override
    public void run() {
        consistencyCheckReadLock.lock();
        this.giverMutex.lock();
        var canBeRemoved = this.giverAccount.checkIfMoneyCanBeRemoved(transferAmount, serialNumber.get());
        incrementSerialNumber();
        if(canBeRemoved) {
            this.giverAccount.removeMoneyFromAmount(transferAmount, this.receiverAccount, serialNumber.get());
            this.giverMutex.unlock();
            this.receiverMutex.lock();
            this.receiverAccount.addMoneyToAmount(transferAmount, this.giverAccount, serialNumber.get());
            this.receiverMutex.unlock();
            incrementSerialNumber();
        } else {
            this.giverMutex.unlock();
        }
        consistencyCheckReadLock.unlock();
    }

    public void incrementSerialNumber() {
        this.serialNumber.set(this.serialNumber.get() + 1);
    }
}
