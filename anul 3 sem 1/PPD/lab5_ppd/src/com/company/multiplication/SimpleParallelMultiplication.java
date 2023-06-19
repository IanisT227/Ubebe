package com.company.multiplication;

import com.company.model.Polynomial;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SimpleParallelMultiplication implements Multiplication {
    private final int threadPoolSize;

    static class SimpleTask implements Runnable {
        private final int startIndex;
        private final int endIndex;
        private final Polynomial first;
        private final Polynomial second;
        private final List<Integer> coefficientsList;

        public SimpleTask(int startIndex, int endIndex, Polynomial first, Polynomial second, List<Integer> coefficientsList) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.first = first;
            this.second = second;
            this.coefficientsList = coefficientsList;
        }

        @Override
        public void run() {
            for (int index = startIndex; index < endIndex; index++) {
                for (int firstIndex = 0; firstIndex <= index && firstIndex < first.getCoefficientSize(); firstIndex++) {
                    int secondIndex = index - firstIndex;
                    if (secondIndex < second.getCoefficientSize()) {
                        int value = first.getCoefficients().get(firstIndex) * second.getCoefficients().get(index - firstIndex);
                        coefficientsList.set(index, coefficientsList.get(index) + value);
                    }
                }
            }
        }
    }

    public SimpleParallelMultiplication(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    @Override
    public Polynomial polynomialMultiplication(Polynomial first, Polynomial second) {
        int polynomialSize = first.getOrder() + second.getOrder() + 1;
        ArrayList<Integer> polynomialResultCoefficients = new ArrayList<>();

        for (int i = 0; i < polynomialSize; i++) {
            polynomialResultCoefficients.add(0);
        }
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadPoolSize);

        int startIndex = 0;
        int endIndex = polynomialSize / threadPoolSize;
        while (startIndex < polynomialSize) {
            SimpleTask simpleTask = new SimpleTask(startIndex, endIndex, first, second, polynomialResultCoefficients);
            executorService.execute(simpleTask);
            startIndex = endIndex;
            endIndex += (polynomialSize) / threadPoolSize;
//            System.out.println(startIndex);
//            System.out.println(endIndex);

            if (endIndex > polynomialSize)
                endIndex = polynomialSize;
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new Polynomial(polynomialResultCoefficients);
    }
}


