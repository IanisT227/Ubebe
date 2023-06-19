package com.company.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Polynomial {

    private final Integer order;
    private final List<Integer> coefficients;


    public Polynomial(Integer order, List<Integer> coefficients) {
        this.order = order;
        this.coefficients = coefficients;
    }

    public Polynomial(List<Integer> coefficients) {
        this.order = coefficients.size() - 1;
        this.coefficients = coefficients;
    }

    public Polynomial(Integer order) {
        this.order = order;
        coefficients = new ArrayList<>(order + 1);
        generateCoefficients(this.order);
    }

    private void generateCoefficients(Integer order) {
        for (int i = 0; i < order + 1; i++) {
            coefficients.add(new Random().nextInt(11));
        }
    }

    @Override
    public String toString() {
        StringBuilder polynomial = new StringBuilder("Polynomial: ");
        for (int i = 0; i <= order; i++) {
            if (coefficients.get(i) == 0) {
                continue;
            }
            polynomial.append(coefficients.get(i)).append(" X^").append(i);
            if (i != order)
                polynomial.append(" + ");
        }
        return polynomial.toString();
    }

    public Integer getOrder() {
        return order;
    }

    public List<Integer> getCoefficients() {
        return coefficients;
    }

    public Integer getCoefficientSize() {
        return coefficients.size();
    }
}
