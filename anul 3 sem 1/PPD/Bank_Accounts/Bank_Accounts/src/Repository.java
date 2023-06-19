import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Repository {
    private final ArrayList<Account> accounts;
    private final ArrayList<Transaction> transactions;
    private final HashMap<Account, Lock> accountToMutex;
    private final int numberOfAccounts;
    private final int numberOfTransactions;
    private final int maxAmountForAccount;
    private final int maxAmountForTransfer;
    private final AtomicInteger serialNumber;
    private final Random random;
    private final int numberOfThreads;

    public Repository(int numberOfThreads) {
        this.accounts = new ArrayList<>();
        this.transactions = new ArrayList<>();
        this.numberOfAccounts = 150;
        this.numberOfTransactions = 2000;
        this.maxAmountForAccount = 30000;
        this.maxAmountForTransfer = 5000;
        this.accountToMutex = new HashMap<>();
        this.serialNumber = new AtomicInteger(0);
        this.random = new Random();
        this.numberOfThreads = numberOfThreads;
        this.registerAllAccounts();
        this.createTransactions();
    }

    public void addAccount(int accountId) {
        int amountForAccount = random.nextInt(this.maxAmountForAccount);
        var newAccount = new Account(accountId, amountForAccount);
        this.accounts.add(newAccount);
        this.accountToMutex.put(newAccount, new ReentrantLock());
    }

    public void registerAllAccounts() {
        for (int i = 0; i < this.numberOfAccounts; i++) {
            this.addAccount(i);
        }
    }

    public void addTransaction(Account senderAccount, Account receiverAccount, int transferAmount) {
        Transaction tran = new Transaction(
                serialNumber,
                senderAccount,
                receiverAccount,
                transferAmount,
                accountToMutex.get(senderAccount),
                accountToMutex.get(receiverAccount)
        );
        transactions.add(tran);
    }

    public void createTransactions() {
        for (int i = 0; i < this.numberOfTransactions; i++) {
            int senderId = random.nextInt(numberOfAccounts);
            int receiverId = random.nextInt(numberOfAccounts);
            while (senderId == receiverId) {
                receiverId = random.nextInt(numberOfAccounts);
            }
            var senderAccount = accounts.get(senderId);
            var receiverAccount = accounts.get(receiverId);
            var transferAmount = random.nextInt(maxAmountForTransfer);
            this.addTransaction(senderAccount, receiverAccount, transferAmount);
        }
    }

    public void runTransactionsOnANumberOfThreads() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        for (Transaction transaction : transactions) {
            service.submit(transaction);
        }

        service.shutdown();
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    }
}
