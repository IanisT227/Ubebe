package com.example.model;

public class Operation {
    private int id;
    private int otherAccountId;
    private long amount;

    public Operation(int id, int otherAccountId, long amount) {
        this.id = id;
        this.otherAccountId = otherAccountId;
        this.amount = amount;
    }

    public int getOtherAccountId() {
        return otherAccountId;
    }

    public void setOtherAccountId(int otherAccountId) {
        this.otherAccountId = otherAccountId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
