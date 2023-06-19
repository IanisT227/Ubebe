package com.company;

import com.company.Threads.ColumnThread;
import com.company.Threads.KthElemThread;
import com.company.Threads.RowThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Controller {

    private Matrix first;
    private Matrix second;
    private static final int THREADPOOL_SIZE = 12;
    private static final int ITEM_POOL_SIZE = 20;
    private static final int PARTS_SIZE = 30;
    private static final int RANDOM_UPPER_LIMIT = 50;

    public Controller(Matrix first, Matrix second) {
        this.first = first;
        this.second = second;
    }

    public static void prepareMatrix(Integer size, Matrix secondMatrix) {
        List<Integer> integerListSecond = new ArrayList<>();

        for (int i = 0; i < size * size; i++) {
            integerListSecond.add(new Random().nextInt(RANDOM_UPPER_LIMIT));
        }

        secondMatrix.populateMatrix(integerListSecond);

//        System.out.println(secondMatrix);
    }

    public Integer calculateElement(Integer lineIndex, Integer columnIndex, Matrix first, Matrix second) {
        int sum = 0;
        for (int j = 0; j < first.getLines(); j++) {
            sum += first.matrix.get(lineIndex).get(j) * second.matrix.get(j).get(columnIndex);
        }
        return sum;
    }

    public void calculateMatrixViaRows() throws ExecutionException, InterruptedException {
        int size = first.getLines();
        Matrix resultedMatrix = new Matrix(size);
        int splitInParts = PARTS_SIZE;
        int itemPoolSize = (int) Math.ceil(size * size / splitInParts);

        List<FutureTask> taskLists = new ArrayList<>();
        int indexCounter = 0;
        while (indexCounter < size * size) {
            taskLists.add(new FutureTask(new RowThread(first, second, indexCounter, indexCounter + itemPoolSize - 1)));
            indexCounter += itemPoolSize;
        }

        calculateMatrixViaThread(resultedMatrix, taskLists);
    }

    public void calculateMatrixViaColumns() throws ExecutionException, InterruptedException {
        int size = first.getLines();
        Matrix resultedMatrix = new Matrix(size);
        int splitInParts = PARTS_SIZE;
        int itemPoolSize = (int) Math.ceil(size * size / splitInParts);

        List<FutureTask> taskLists = new ArrayList<>();
        int indexCounter = 0;
        while (indexCounter < size * size) {
            taskLists.add(new FutureTask(new ColumnThread(first, second, indexCounter, indexCounter + itemPoolSize - 1)));
            indexCounter += itemPoolSize;
        }

        calculateMatrixViaThread(resultedMatrix, taskLists);
    }

    public void calculateMatriaViaKElems() throws ExecutionException, InterruptedException {
        int size = first.getLines();
        Matrix resultedMatrix = new Matrix(size);
        int itemPoolSize = ITEM_POOL_SIZE;

        List<FutureTask> taskLists = new ArrayList<>();
        int indexCounter = 0;
        while (indexCounter <= itemPoolSize) {
            taskLists.add(new FutureTask(new KthElemThread(first, second, indexCounter)));
            indexCounter += 1;
        }

        calculateMatrixViaThread(resultedMatrix, taskLists);
    }

    private Matrix calculateMatrixViaThread(Matrix resultedMatrix, List<FutureTask> taskLists) throws InterruptedException, ExecutionException {
        for (FutureTask task : taskLists) {
            Thread thread = new Thread(task);
            thread.start();
        }

        for (FutureTask task : taskLists) {
            resultedMatrix.matrix.add((List<Integer>) task.get());
        }
        return resultedMatrix;
    }


    public void calculateMatrixViaRowsThreadPool() throws ExecutionException, InterruptedException {
        int size = first.getLines();
        Matrix resultedMatrix = new Matrix(size);
        int splitInParts = PARTS_SIZE;
        int itemPoolSize = (int) Math.ceil(size * size / splitInParts);

        List<FutureTask> taskLists = new ArrayList<>();
        int indexCounter = 0;
        while (indexCounter < size * size) {
            taskLists.add(new FutureTask(new RowThread(first, second, indexCounter, indexCounter + itemPoolSize - 1)));
            indexCounter += itemPoolSize;
        }

        calculateMatrixViaThreadPool(resultedMatrix, taskLists);
    }

    public void calculateMatrixViaColumnsThreadPool() throws ExecutionException, InterruptedException {
        int size = first.getLines();
        Matrix resultedMatrix = new Matrix(size);
        int splitInParts = PARTS_SIZE;
        int itemPoolSize = (int) Math.ceil(size * size / splitInParts);

        List<FutureTask> taskLists = new ArrayList<>();
        int indexCounter = 0;
        while (indexCounter < size * size) {
            taskLists.add(new FutureTask(new ColumnThread(first, second, indexCounter, indexCounter + itemPoolSize - 1)));
            indexCounter += itemPoolSize;
        }

        calculateMatrixViaThreadPool(resultedMatrix, taskLists);
    }

    public void calculateMatriaViaKElemsThreadPool() throws ExecutionException, InterruptedException {
        int size = first.getLines();
        Matrix resultedMatrix = new Matrix(size);
        int itemPoolSize = ITEM_POOL_SIZE;

        List<FutureTask> taskLists = new ArrayList<>();
        int indexCounter = 0;
        while (indexCounter <= itemPoolSize) {
            taskLists.add(new FutureTask(new KthElemThread(first, second, indexCounter)));
            indexCounter += 1;
        }

        calculateMatrixViaThreadPool(resultedMatrix, taskLists);
    }
//TODO: de refacut calculul pentru rows si columns si testat pe matrici mai mici

    private Matrix calculateMatrixViaThreadPool(Matrix resultedMatrix, List<FutureTask> taskLists) throws InterruptedException, ExecutionException {
        ExecutorService threadPool = Executors.newFixedThreadPool(THREADPOOL_SIZE);
        for (FutureTask task : taskLists) {
            threadPool.execute(task);
        }
        threadPool.shutdown();

        for (FutureTask task : taskLists) {
            resultedMatrix.matrix.add((List<Integer>) task.get());
        }
        return resultedMatrix;
    }
}
