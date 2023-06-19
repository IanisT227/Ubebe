package com.company.Threads;

import com.company.Matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ColumnThread implements Callable<List<Integer>> {
    private Matrix firstMatrix;
    private Matrix secondMatrix;
    private Integer startIndex;
    private Integer endIndex;

    public ColumnThread(Matrix firstMatrix, Matrix secondMatrix, Integer startIndex, Integer endIndex) {
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public Integer calculateElement(Integer lineIndex, Integer columnIndex) {
        int sum = 0;
        for (int j = 0; j < firstMatrix.getLines(); j++) {
            sum += firstMatrix.matrix.get(lineIndex).get(j) * secondMatrix.matrix.get(j).get(columnIndex);
        }
        return sum;
    }

    @Override
    public List<Integer> call() {
        int size = firstMatrix.getLines();
        List<Integer> column = new ArrayList<Integer>(size);
        int firstRowIndex = startIndex / size;
        int firstColumnIndex = startIndex % size;

        int lastRowIndex = endIndex / size;
        int lastColumnIndex = endIndex % size;

        if (endIndex > size * size) {
            lastColumnIndex = size - 1;
            lastRowIndex = size - 1;
        }

        for (int i = firstColumnIndex; i <= lastColumnIndex; i++) {
            for (int j = firstRowIndex; j <= lastRowIndex; j++) {
                column.add(calculateElement(j, i));
            }
        }
        return column;
    }
}

