package org.polymul;

import org.polymul.domain.Polynomial;
import org.polymul.service.KaratsubaConcProduct;
import org.polymul.service.KaratsubaSeqProduct;
import org.polymul.service.SimpleConcProduct;
import org.polymul.service.SimpleSeqProduct;

public class Main {
    public static void main(String[] args) {
        Polynomial a = new Polynomial(100);
        Polynomial b = new Polynomial(100);

        System.out.println("A: " + a);
        System.out.println("B: " + b);

        long startTime = System.currentTimeMillis();
        Polynomial result1 = new SimpleSeqProduct().multiply(a, b);
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " ms");
        //System.out.println("Result: " + result1);

         startTime = System.currentTimeMillis();
         result1 = new SimpleConcProduct(3).multiply(a, b);
         endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " ms");
        //System.out.println("Result: " + result1);

        startTime = System.currentTimeMillis();
        result1 = new KaratsubaSeqProduct().multiply(a, b);
        endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " ms");
        //System.out.println("Result: " + result1);

        startTime = System.currentTimeMillis();
        result1 = new KaratsubaConcProduct().multiply(a, b);
        endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " ms");
        //System.out.println("Result: " + result1);

        //System.out.println("Execution time: " + (endTime - startTime) + " ms");
    }
}