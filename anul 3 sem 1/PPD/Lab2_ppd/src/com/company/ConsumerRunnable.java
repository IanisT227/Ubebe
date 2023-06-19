package com.company;

class ConsumerRunnable implements Runnable {

    private final Main service;

    public ConsumerRunnable(Main service) {
        this.service = service;
    }

    public void run() {
        int size = service.getSize();
        try {
            for (int i = 0; i < size; ++i) {
                service.consumer();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}