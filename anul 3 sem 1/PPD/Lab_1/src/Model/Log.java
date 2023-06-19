package Model;

public class Log {
    int serialNumber;
    String operationType;
    Account transferToAccount;
    int operationValue;

    public Log(int serialNumber, String operationType) {
        this.serialNumber = serialNumber;
        this.operationType = operationType;
        this.transferToAccount = null;
        this.operationValue = 0;
    }

    public Log(int serialNumber, String operationType, int operationValue) {
        this.serialNumber = serialNumber;
        this.operationType = operationType;
        this.transferToAccount = null;
        this.operationValue = operationValue;
    }

    public Log(int serialNumber, String operationType, Account transferToAccount, int operationValue) {
        this.serialNumber = serialNumber;
        this.operationType = operationType;
        this.transferToAccount = transferToAccount;
        this.operationValue = operationValue;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public int getOperationValue() {
        return operationValue;
    }

    public void setOperationValue(int operationValue) {
        this.operationValue = operationValue;
    }

    public Account getTransferToAccount() {
        return transferToAccount;
    }

    public void setTransferToAccount(Account transferToAccount) {
        this.transferToAccount = transferToAccount;
    }

    @Override
    public String toString() {
        if(operationValue != 0) {
            return "Log{" +
                    "serialNumber=" + serialNumber +
                    ", operationType='" + operationType + '\'' +
                    ", transferToAccount='" + transferToAccount + '\'' +
                    ", operationValue=" + operationValue +
                    '}';
        }
        return "Log{" +
                "serialNumber=" + serialNumber +
                ", operationType='" + operationType + '\'' +
                '}';
    }
}
