import java.util.concurrent.atomic.AtomicInteger;

public class Log {
    private final AtomicInteger serialNumber;
    private final Account linkedAccount;
    private final String operationDetails;
    private final int processedAmount;

    public Log(AtomicInteger serialNumber, Account linkedAccount, String operationDetails, int amount) {
        this.serialNumber = serialNumber;
        this.linkedAccount = linkedAccount;
        this.operationDetails = operationDetails;
        this.processedAmount = amount;
    }

    @Override
    public String toString() {
        return "Log{" +
                "serialNumber=" + serialNumber.get() +
                ", linkedAccount=" + linkedAccount +
                ", operationDetails='" + operationDetails + '\'' +
                ", processedAmount=" + processedAmount +
                '}';
    }
}
