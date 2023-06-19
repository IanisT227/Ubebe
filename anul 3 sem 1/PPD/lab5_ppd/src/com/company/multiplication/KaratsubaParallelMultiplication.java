package com.company.multiplication;

import com.company.model.Polynomial;

import java.util.List;
import java.util.concurrent.*;

import static com.company.multiplication.KaratsubaSequentialMultiplication.*;

public class KaratsubaParallelMultiplication {
    private final Integer threadPoolSize;

    public KaratsubaParallelMultiplication(Integer threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    public Polynomial polynomialMultiplication(Polynomial first, Polynomial second, int depth) {
        if (depth > 3) {
            return new KaratsubaSequentialMultiplication().polynomialMultiplication(first, second);
        }

        if (first.getOrder() < 32 || second.getOrder() < 32) {
            return new SimpleSequentialMultiplication().polynomialMultiplication(first, second);
        }

        List<Integer> firstCoefficients = first.getCoefficients();
        List<Integer> secondCoefficients = second.getCoefficients();
        int size = Math.max(firstCoefficients.size(), secondCoefficients.size()) / 2;

        Polynomial lowFirst = new Polynomial(firstCoefficients.subList(0, size));
        Polynomial highFirst = new Polynomial(firstCoefficients.subList(size, firstCoefficients.size()));
        Polynomial lowSecond = new Polynomial(secondCoefficients.subList(0, size));
        Polynomial highSecond = new Polynomial(secondCoefficients.subList(size, secondCoefficients.size()));

        ExecutorService executor = Executors.newFixedThreadPool(3);

        Future<Polynomial> resultFirst = executor.submit(() -> polynomialMultiplication(lowFirst, lowSecond, depth + 1));
        Future<Polynomial> resultSecond = executor.submit(() -> polynomialMultiplication(add(lowSecond, highFirst), add(lowSecond, highSecond), depth + 1));
        Future<Polynomial> resultThird = executor.submit(() -> polynomialMultiplication(highFirst, highSecond, depth + 1));

        executor.shutdown();

        Polynomial partThree = null;
        Polynomial partTwo = null;
        Polynomial partOne = null;
        try {
            partOne = resultFirst.get(); // bd
            partTwo = resultSecond.get();
            partThree = resultThird.get();
        } catch (RuntimeException | InterruptedException | ExecutionException exception) {
            System.out.println(exception);
        }

        try {
            executor.awaitTermination(120, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Polynomial raisedThird = raisePolynomial(partThree, 2 * size); // 10^n*ac
        Polynomial raisedSecond = raisePolynomial(subtract(subtract(partTwo, partThree), partOne), size); // 10^(n/2)*(ac+ad+bc+bd-bd-ac)
        return add(add(raisedThird, raisedSecond), partOne);
    }
}
