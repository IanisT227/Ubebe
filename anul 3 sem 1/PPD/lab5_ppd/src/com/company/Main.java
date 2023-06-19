package com.company;

import com.company.model.Polynomial;
import com.company.multiplication.KaratsubaParallelMultiplication;
import com.company.multiplication.KaratsubaSequentialMultiplication;
import com.company.multiplication.SimpleParallelMultiplication;
import com.company.multiplication.SimpleSequentialMultiplication;

public class Main {
    static final int THREADPOOL_SIZE = 8;

    public static void main(String[] args) {
        Polynomial first = new Polynomial(10000);
        Polynomial second = new Polynomial(10000);

        System.out.println("First " + first.toString());
        System.out.println("Second" + second.toString() + "\n");

        long startTime = System.currentTimeMillis();
        new SimpleSequentialMultiplication().polynomialMultiplication(first, second);
        long endTime = System.currentTimeMillis();
        System.out.println("Simple sequential execution time: " + (endTime - startTime) + " ms\n");


        startTime = System.currentTimeMillis();
        new SimpleParallelMultiplication(THREADPOOL_SIZE).polynomialMultiplication(first, second);
        endTime = System.currentTimeMillis();
        System.out.println("Simple parallel execution time: " + (endTime - startTime) + " ms\n");

        startTime = System.currentTimeMillis();
        new KaratsubaSequentialMultiplication().polynomialMultiplication(first, second);
        endTime = System.currentTimeMillis();
        System.out.println("Karatsuba sequential execution time: " + (endTime - startTime) + " ms\n");

        startTime = System.currentTimeMillis();
        new KaratsubaParallelMultiplication(THREADPOOL_SIZE).polynomialMultiplication(first, second,0);
        endTime = System.currentTimeMillis();
        System.out.println("Karatsuba parallel execution time: " + (endTime - startTime) + " ms\n");

    }
}
