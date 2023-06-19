package org.polymul.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Polynomial {
    private static final int MAX_RAND = 10;
    private final int order;
    private final List<Integer> coefficients;


    public Polynomial(List<Integer> coefficients) {
        this.order = coefficients.size() - 1;
        this.coefficients = coefficients;
    }

    public Polynomial(int order) {
        this.order = order;
        coefficients = new ArrayList<>(order + 1);
        generateCoefficients();
    }

    private void generateCoefficients() {
        Random r = new Random();
        for (int i = 0; i < order+1; i++) {
            coefficients.add(r.nextInt(MAX_RAND + 1));
        }
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i <= this.order; i++) {
            if (coefficients.get(i) == 0) {
                continue;
            }
            str.append(coefficients.get(i)).append("x^").append(i);
            if (i != this.order) {
                str.append(" + ");
            }
        }
        return str.toString();
    }
    public List<Integer> getCoefficients() {
        return coefficients;
    }
    public int getOrder() {
        return order;
    }
    public int getSize() {
        return coefficients.size();
    }
}