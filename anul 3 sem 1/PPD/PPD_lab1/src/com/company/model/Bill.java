package com.company.model;

import java.util.List;

public class Bill {

    private List<BillItem> list;

    public Bill(List<BillItem> list) {
        this.list = list;
    }
}
