package com.company.multiplication;

import com.company.model.Polynomial;

import java.util.ArrayList;

public class SimpleSequentialMultiplication implements Multiplication {
    @Override
    public Polynomial polynomialMultiplication(Polynomial first, Polynomial second) {
        int polynomialSize = first.getOrder() + second.getOrder() + 1;
        ArrayList<Integer> polynomialResultCoefficients = new ArrayList<>();
        for (int i = 0; i < polynomialSize; i++) {
            polynomialResultCoefficients.add(0);
        }

        for (int firstIndex = 0; firstIndex < first.getCoefficientSize(); firstIndex++) {
            for (int secondIndex = 0; secondIndex < second.getCoefficientSize(); secondIndex++) {
                int index = firstIndex + secondIndex;
                int result = first.getCoefficients().get(firstIndex) * second.getCoefficients().get(secondIndex);
                if (index > polynomialSize)
                    System.out.println("Index is"+index+"VS"+polynomialSize);
                polynomialResultCoefficients.set(index, polynomialResultCoefficients.get(index) + result);
            }
        }
        return new Polynomial(polynomialResultCoefficients);
    }
}