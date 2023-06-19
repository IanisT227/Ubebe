package org.polymul.service;

import org.polymul.domain.Polynomial;

import java.util.ArrayList;
import java.util.List;

public class SimpleSeqProduct implements IProductMethod {
    @Override
    public Polynomial multiply(Polynomial lhs, Polynomial rhs) {
        int resultSize = lhs.getOrder() + rhs.getOrder() + 1;
        List<Integer> resultCoefficients = new ArrayList<>();
        for (int i = 0; i < resultSize; i++) {
            resultCoefficients.add(0);
        }
        for (int lhsIdx = 0; lhsIdx < lhs.getSize(); lhsIdx++) {
            for (int rhsIdx = 0; rhsIdx < rhs.getSize(); rhsIdx++) {
                int index = lhsIdx + rhsIdx;
                int value = lhs.getCoefficients().get(lhsIdx) * rhs.getCoefficients().get(rhsIdx);
                resultCoefficients.set(index, resultCoefficients.get(index) + value);
            }
        }
        return new Polynomial(resultCoefficients);
    }
}
