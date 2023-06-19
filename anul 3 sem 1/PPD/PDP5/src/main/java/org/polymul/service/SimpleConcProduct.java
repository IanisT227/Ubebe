package org.polymul.service;

import org.polymul.domain.Polynomial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SimpleConcProduct implements IProductMethod {
    static class Task implements Runnable {
        private final int start;
        private final int end;
        private final Polynomial lhs;
        private final Polynomial rhs;
        private final List<Integer> coeff;

        public Task(int start, int end, Polynomial lhs, Polynomial rhs, List<Integer> coeff) {
            this.start = start;
            this.end = end;
            this.lhs = lhs;
            this.rhs = rhs;
            this.coeff = coeff;
        }

        @Override
        public void run() {
            for (int index = start; index < end; index++) {
                for (int lhsIdx = 0; lhsIdx <= index && lhsIdx < lhs.getSize(); lhsIdx++) {
                    int rhsIndex = index - lhsIdx;
                    if (rhsIndex < rhs.getSize()) {
                        int value = lhs.getCoefficients().get(lhsIdx) * rhs.getCoefficients().get(index - lhsIdx);
                        coeff.set(index, coeff.get(index) + value);
                    }
                }
            }
        }
    }

    private final int numThreads;

    public SimpleConcProduct(int numThreads) {
        this.numThreads = numThreads;
    }

    @Override
    public Polynomial multiply(Polynomial lhs, Polynomial rhs) {
        int resultSize = lhs.getOrder() + rhs.getOrder() + 1;
        List<Integer> resultCoefficients = new ArrayList<>(Collections.nCopies(resultSize, 0));;

        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(numThreads);
        int start = 0;
        int end = resultSize / numThreads;
        int idxThread = 0;
        while (start < resultSize) {
            Task task = new Task(start, end, lhs, rhs, resultCoefficients);
            executorService.execute(task);
            start = end;
            idxThread += 1;
            end += (resultSize + idxThread) / numThreads;
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(120, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new Polynomial(resultCoefficients);
    }
}
