package org.polymul.service;

import org.polymul.domain.Polynomial;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;




public class KaratsubaConcProduct {
    public Polynomial multiply(Polynomial lhs, Polynomial rhs, int depth) {
        if (depth > 2) {
            return new KaratsubaSeqProduct().multiply(lhs, rhs);
        }

        if (lhs.getOrder() < 2 || rhs.getOrder() < 2) {
            return new SimpleSeqProduct().multiply(lhs, rhs);
        }

        var lhsCoefficients = lhs.getCoefficients();
        var rhsCoefficients = rhs.getCoefficients();
        int len = Math.max(lhs.getOrder(), rhs.getOrder()) / 2;

        Polynomial lowA = new Polynomial(lhsCoefficients.subList(0, len));
        Polynomial highA = new Polynomial(lhsCoefficients.subList(len, lhsCoefficients.size()));
        Polynomial lowB = new Polynomial(rhsCoefficients.subList(0, len));
        Polynomial highB = new Polynomial(rhsCoefficients.subList(len, rhsCoefficients.size()));

        ExecutorService executor = Executors.newFixedThreadPool(3);

        Future<Polynomial> f1 = executor.submit(() -> multiply(lowA, lowB, depth+1));
        Future<Polynomial> f2 = executor.submit(() -> multiply(KaratsubaSeqProduct.add(lowA, highA), KaratsubaSeqProduct.add(lowB, highB), depth+1));
        Future<Polynomial> f3 = executor.submit(() -> multiply(highA, highB, depth+1));

        executor.shutdown();

        Polynomial z1, z2, z3;

        try {
            z1 = f1.get();
            z2 = f2.get();
            z3 = f3.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            executor.awaitTermination(120, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Polynomial r1 = KaratsubaSeqProduct.raiseWith(z3, 2 * len);
        Polynomial r2 = KaratsubaSeqProduct.raiseWith(KaratsubaSeqProduct.subtract(KaratsubaSeqProduct.subtract(z2, z3), z1), len);
        return KaratsubaSeqProduct.add(KaratsubaSeqProduct.add(r1, r2), z1);
    }
    public Polynomial multiply(Polynomial lhs, Polynomial rhs) {
        return multiply(lhs, rhs, 0);
    }
}
