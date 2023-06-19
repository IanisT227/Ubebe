package com.company.Threads;

import com.company.Matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RowThread implements Callable<List<Integer>> {
    private Matrix firstMatrix;
    private Matrix secondMatrix;
    private Integer startIndex;
    private Integer endIndex;


    public RowThread(Matrix firstMatrix, Matrix secondMatrix, Integer startIndex, Integer endIndex) {
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
        List<Integer> row = new ArrayList<Integer>(size);
        int firstRowIndex = startIndex / size;
        int firstColumnIndex = startIndex % size;

        int lastRowIndex = endIndex / size;
        int lastColumnIndex = endIndex % size;

        if (endIndex > size * size) {
            lastColumnIndex = size - 1;
            lastRowIndex = size - 1;
        }

        for (int i = firstRowIndex; i <= lastRowIndex; i++) {
            for (int j = firstColumnIndex; j <= lastColumnIndex; j++) {
                row.add(calculateElement(i, j));
            }
        }
        return row;
    }
}
