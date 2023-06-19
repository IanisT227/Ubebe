package com.company;

public class ProducerRunnable implements Runnable {

    private final Main service;

    public ProducerRunnable(Main service) {
        this.service = service;
    }

    public void run() {
        int size = service.getSize();
        try {
            for (int i = 0; i < size; ++i) {
                service.producer();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
