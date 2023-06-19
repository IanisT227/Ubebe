package com.company.multiplication;

import com.company.model.Polynomial;

import java.util.ArrayList;
import java.util.List;

public class KaratsubaSequentialMultiplication implements Multiplication {
    @Override
    public Polynomial polynomialMultiplication(Polynomial first, Polynomial second) {
        if (first.getOrder() < 32 || second.getOrder() < 32) {
            return new SimpleSequentialMultiplication().polynomialMultiplication(first, second);
        }

        List<Integer> firstCoefficients = first.getCoefficients();
        List<Integer> secondCoefficients = second.getCoefficients();
        int size = Math.max(first.getOrder(), second.getOrder()) / 2;

        Polynomial lowFirst = new Polynomial(firstCoefficients.subList(0, size));
        Polynomial highFirst = new Polynomial(firstCoefficients.subList(size, firstCoefficients.size()));
        Polynomial lowSecond = new Polynomial(secondCoefficients.subList(0, size));
        Polynomial highSecond = new Polynomial(secondCoefficients.subList(size, secondCoefficients.size()));

        Polynomial partOne = polynomialMultiplication(lowFirst, lowSecond); //bd
        Polynomial partTwo = polynomialMultiplication(add(lowFirst, highFirst), add(lowSecond, highSecond));
        Polynomial partThree = polynomialMultiplication(highFirst, highSecond);

        Polynomial raisedThird = raisePolynomial(partThree, 2 * size); // 10^n*ac
        Polynomial raisedSecond = raisePolynomial(subtract(subtract(partTwo, partThree), partOne), size); // 10^(n/2)*(ac+ad+bc+bd-bd-ac)
        return add(add(raisedThird, raisedSecond), partOne);
    }

    public static Polynomial raisePolynomial(Polynomial polynomial, Integer exponent) {
        List<Integer> coefficients = new ArrayList<>(exponent);
        for (int i = 0; i < exponent; i++) {
            coefficients.add(0);
        }
        coefficients.addAll(polynomial.getCoefficients());
        return new Polynomial(coefficients);
    }

    public static Polynomial add(Polynomial first, Polynomial second) {
        var firstCoefficients = first.getCoefficients();
        var secondCoefficients = second.getCoefficients();

        int maximumOrder = Math.max(first.getOrder(), second.getOrder());
        List<Integer> resultCoefficients = new ArrayList<>(maximumOrder + 1);

        for (int index = 0; index <= maximumOrder; index++) {
            int value = 0;
            if (index < firstCoefficients.size()) {
                value += firstCoefficients.get(index);
            }
            if (index < secondCoefficients.size()) {
                value += secondCoefficients.get(index);
            }
            resultCoefficients.add(value);
        }

        return new Polynomial(resultCoefficients);
    }

    public static Polynomial subtract(Polynomial first, Polynomial second) {
        var firstCoefficients = first.getCoefficients();
        var secondCoefficients = second.getCoefficients();

        int maximumOrder = Math.max(first.getOrder(), second.getOrder());
        ArrayList<Integer> resultCoefficients = new ArrayList<>(maximumOrder + 1);

        for (int index = 0; index <= maximumOrder; index++) {
            int value = 0;
            if (index < firstCoefficients.size()) {
                value += firstCoefficients.get(index);
            }
            if (index < secondCoefficients.size()) {
                value -= secondCoefficients.get(index);
            }
            resultCoefficients.add(value);
        }

        int index = resultCoefficients.size() - 1;
        while (!resultCoefficients.isEmpty() && resultCoefficients.get(index) == 0) {
            resultCoefficients.remove(index);
            index--;
        }
        return new Polynomial(resultCoefficients);
    }

}
