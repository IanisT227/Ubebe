import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

public class Transaction extends Thread {
    private final AtomicInteger serialNumber;
    private final Account senderAccount;
    private final Account receiverAccount;
    private final Lock senderMutex;
    private final Lock receiverMutex;
    private final int amountToTransfer;

    public Transaction(AtomicInteger serialNumber, Account senderAccount, Account receiverAccount, int amount, Lock senderMutex, Lock receiverMutex) {
        this.serialNumber = serialNumber;
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.senderMutex = senderMutex;
        this.receiverMutex = receiverMutex;
        this.amountToTransfer = amount;
    }

    public void incrementSerialNumber() {
        this.serialNumber.set(this.serialNumber.get() + 1);
    }

    @Override
    public void run() {
        if (senderAccount.getId() <= receiverAccount.getId()) {
            senderMutex.lock();
            if (!senderAccount.enoughFundsToTransfer(amountToTransfer)) {
                System.out.println("Not enough funds to perform transaction " + serialNumber);
                senderMutex.unlock();
            }
            else {
                senderAccount.transferAmount(amountToTransfer, serialNumber.get(), receiverAccount);
                receiverMutex.lock();
                receiverAccount.receiveAmount(amountToTransfer, serialNumber.get(), senderAccount);
                System.out.println("Transaction " + this.serialNumber.get() + " completed!");
                senderMutex.unlock();
                receiverMutex.unlock();
                this.incrementSerialNumber();
            }
        }
        else {
            receiverMutex.lock();
            receiverAccount.receiveAmount(amountToTransfer, serialNumber.get(), senderAccount);
            senderMutex.lock();
            if (!senderAccount.enoughFundsToTransfer(amountToTransfer)) {
                System.out.println("Not enough funds to perform transaction " + serialNumber);
            }
            else {
                senderAccount.transferAmount(amountToTransfer, serialNumber.get(), receiverAccount);
                System.out.println("Transaction " + this.serialNumber.get() + " completed!");
            }
            receiverMutex.unlock();
            senderMutex.unlock();
            this.incrementSerialNumber();
        }
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "serialNumber=" + serialNumber +
                ", senderAccount=" + senderAccount +
                ", receiverAccount=" + receiverAccount +
                ", senderMutex=" + senderMutex +
                ", receiverMutex=" + receiverMutex +
                ", amountToTransfer=" + amountToTransfer +
                '}';
    }
}
