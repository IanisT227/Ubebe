package com.company;

import java.util.ArrayList;
import java.util.List;

public class Matrix {
    private final Integer lines;
    private final Integer columns;
    public List<List<Integer>> matrix;

    public Matrix(Integer items) {
        this.lines = items;
        this.columns = items;
        this.matrix = new ArrayList<List<Integer>>(items);
    }

    void populateMatrix(List<Integer> itemsList) {
        if (itemsList.size() == lines * lines) {
            for (int i = 0; i < lines; i++) {
                matrix.add(new ArrayList<Integer>(lines));
            }

            for (int i = 0; i < lines; i++) {
                for (int j = 0; j < lines; j++) {
                    matrix.get(i).add(itemsList.get(i*j + j));
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Matrix{" +
                "lines=" + lines +
                ", columns=" + columns +
                ", matrix=" + matrix +
                '}';
    }

    public Integer getLines() {
        return lines;
    }

    public Integer getColumns() {
        return columns;
    }

    public List<List<Integer>> getMatrix() {
        return matrix;
    }
}
