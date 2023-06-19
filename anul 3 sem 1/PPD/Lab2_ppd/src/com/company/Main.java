package com.company;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    private final List<Integer> valuesList1;
    private final List<Integer> valuesList2;
    private int valuesInQueue = 0;
    private int index = 0;
    private final Queue<Integer> toAddQueue = new ArrayDeque<>();
    private int sum = 0;

    private final Lock lock = new ReentrantLock();
    private final Condition hasElement = lock.newCondition();

    public Main(int n) {
        valuesList1 = new ArrayList<>();
        valuesList2 = new ArrayList<>();
        for (int i = 1; i <= n; ++i) {
            valuesList1.add(i);
            valuesList2.add(i);
        }
    }

    public int getSize() {
        return valuesList1.size();
    }

    public void producer() throws InterruptedException {
        lock.lock();
        try {
            while (valuesInQueue == 2) {
                System.out.println("Producer suspended! Buffer full!");
                hasElement.await();
            }
            toAddQueue.add(valuesList1.get(index) * valuesList2.get(index));
            ++valuesInQueue;
            index += 1;
            hasElement.signal();
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            lock.unlock();
        }
    }

    public void consumer() throws InterruptedException {
        lock.lock();
        try {
            while (valuesInQueue == 0) {
                System.out.println("Consumer suspended! Buffer empty");
                hasElement.await();
            }
            sum += toAddQueue.remove();
            --valuesInQueue;
            hasElement.signal();
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Main var = new Main(5);
        Thread producerThread = new Thread(new ProducerRunnable(var));
        Thread consumerThread = new Thread(new ConsumerRunnable(var));

        producerThread.start();
        consumerThread.start();
        producerThread.join();
        consumerThread.join();

        System.out.println("Sum is " + var.sum);
    }
}
