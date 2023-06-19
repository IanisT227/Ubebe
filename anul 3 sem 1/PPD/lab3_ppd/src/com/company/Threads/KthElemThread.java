package com.company.Threads;

import com.company.Matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class KthElemThread implements Callable<List<Integer>> {
    private Matrix firstMatrix;
    private Matrix secondMatrix;
    private Integer itemsIndex;


    public KthElemThread(Matrix firstMatrix, Matrix secondMatrix, Integer itemsIndex) {
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.itemsIndex = itemsIndex;
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
        if (itemsIndex > size)
            System.out.println("Index larger than matrix size");
        else {
            Integer toAdd = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (toAdd.equals(itemsIndex)) {
                        row.add(calculateElement(i, j));
                    }
                    if (toAdd == size - 1)
                        toAdd = 0;
                    toAdd+=1;
                }
            }
        }
        return row;
    }
}
