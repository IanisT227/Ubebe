package org.polymul.service;

import org.polymul.domain.Polynomial;

import java.util.ArrayList;
import java.util.List;

public class KaratsubaSeqProduct {
    public Polynomial multiply(Polynomial lhs, Polynomial rhs) {
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

        Polynomial z1 = multiply(lowA, lowB);
        Polynomial z2 = multiply(add(lowA, highA), add(lowB, highB));
        Polynomial z3 = multiply(highA, highB);

        Polynomial r1 = raiseWith(z3, 2 * len);
        Polynomial r2 = raiseWith(subtract(subtract(z2, z3), z1), len);
        return add(add(r1, r2), z1);
    }

    public static Polynomial raiseWith(Polynomial lhs, int exponent) {
        List<Integer> coefficients = new ArrayList<>(exponent);
        for (int i=0; i<exponent; i++) coefficients.add(0);
        coefficients.addAll(lhs.getCoefficients());
        return new Polynomial(coefficients);
    }

    public static Polynomial add(Polynomial lhs, Polynomial rhs) {
        var lhsCoefficients = lhs.getCoefficients();
        var rhsCoefficients = rhs.getCoefficients();

        int maxOrder = Math.max(lhs.getOrder(), rhs.getOrder());
        List <Integer> resultCoefficients = new ArrayList<>(maxOrder + 1);

        for (int idx = 0; idx <= maxOrder; idx++) {
            int value = 0;
            if (idx < lhsCoefficients.size()) {
                value += lhsCoefficients.get(idx);
            }
            if (idx < rhsCoefficients.size()) {
                value += rhsCoefficients.get(idx);
            }
            resultCoefficients.add(value);
        }

        return new Polynomial(resultCoefficients);
    }

    public static Polynomial subtract(Polynomial lhs, Polynomial rhs) {
        var lhsCoefficients = lhs.getCoefficients();
        var rhsCoefficients = rhs.getCoefficients();

        int maxOrder = Math.max(lhs.getOrder(), rhs.getOrder());
        ArrayList <Integer> resultCoefficients = new ArrayList<>(maxOrder + 1);

        for (int idx = 0; idx <= maxOrder; idx++) {
            int value = 0;
            if (idx < lhsCoefficients.size()) {
                value += lhsCoefficients.get(idx);
            }
            if (idx < rhsCoefficients.size()) {
                value -= rhsCoefficients.get(idx);
            }
            resultCoefficients.add(value);
        }

        int idx = resultCoefficients.size() - 1;
        while (!resultCoefficients.isEmpty() && resultCoefficients.get(idx) == 0) {
            resultCoefficients.remove(idx);
            idx--;
        }

        return new Polynomial(resultCoefficients);
    }
}
