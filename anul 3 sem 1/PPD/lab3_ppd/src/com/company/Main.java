package com.company;

import java.util.concurrent.ExecutionException;

public class Main {
    private static final int MATRIX_SIZE = 900;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Integer size = MATRIX_SIZE;
        Matrix matrix = new Matrix(size);
        Matrix secondMatrix = new Matrix(size);

        Controller.prepareMatrix(size, matrix);
        Controller.prepareMatrix(size, secondMatrix);
        Controller controller = new Controller(matrix, secondMatrix);

        System.out.println("Without ThreadPool:");

        float startRows = System.nanoTime() / 1000000;
        controller.calculateMatrixViaRows();
        float endRows = System.nanoTime() / 1000000;
        System.out.println("Rows Time:" + (endRows - startRows));

        float startColumns = System.nanoTime() / 1000000;
        controller.calculateMatrixViaColumns();
        float endColumns = System.nanoTime() / 1000000;
        System.out.println("Columns Time:" + (endColumns - startColumns));

        float startKElems = System.nanoTime() / 1000000;
        controller.calculateMatriaViaKElems();
        float endKElems = System.nanoTime() / 1000000;
        System.out.println("KElems Time:" + (endKElems - startKElems));

        System.out.println("With ThreadPool:");

        float startRowsThreadPool = System.nanoTime() / 1000000;
        controller.calculateMatrixViaRowsThreadPool();
        float endRowsThreadPool = System.nanoTime() / 1000000;
        System.out.println("Rows Time:" + (endRowsThreadPool - startRowsThreadPool));

        float startColumnsThreadPool = System.nanoTime() / 1000000;
        controller.calculateMatrixViaColumnsThreadPool();
        float endColumnsThreadPool = System.nanoTime() / 1000000;
        System.out.println("Columns Time:" + (endColumnsThreadPool - startColumnsThreadPool));

        float startKElemsThreadPool = System.nanoTime() / 1000000;
        controller.calculateMatriaViaKElemsThreadPool();
        float endKElemsThreadPool = System.nanoTime() / 1000000;
        System.out.println("KElems Time:" + (endKElemsThreadPool - startKElemsThreadPool));
    }
}
