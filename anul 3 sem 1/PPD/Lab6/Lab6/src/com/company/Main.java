package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Graph testGraph = getRandomGraph(200, 15000);
        Graph customGraph = getCustomGraph();
//        System.out.println(testGraph.toString());
        HamiltonianCycleSearcher cycleSearcher = new HamiltonianCycleSearcher(testGraph, 0);
//        HamiltonianCycleSearcher cycleSearcher = new HamiltonianCycleSearcher(customGraph, 0);


        long startTime = System.currentTimeMillis();
        cycleSearcher.getSequentialList();
        long endTime = System.currentTimeMillis();
        System.out.println("Simple sequential execution time: " + (endTime - startTime) + " ms\n");

        startTime = System.currentTimeMillis();
        cycleSearcher.getParallelList();
        endTime = System.currentTimeMillis();
        System.out.println("Simple parallel execution time: " + (endTime - startTime) + " ms\n");


    }

    public static Graph getRandomGraph(Integer nodesNumber, Integer edgesNumber) {
        ArrayList<EdgePair> edges = new ArrayList<>();
        for (int i = 0; i < edgesNumber; i++) {
            Integer fromNode = new Random().nextInt(nodesNumber);
            Integer toNode = new Random().nextInt(nodesNumber);
            while (fromNode.equals(toNode) || edges.contains(new EdgePair(fromNode, toNode))) {
//                System.out.println("am ajuns aici");
                fromNode = new Random().nextInt(nodesNumber);
                toNode = new Random().nextInt(nodesNumber);
            }
//            System.out.println("am ajuns si aici");

            edges.add(new EdgePair(fromNode, toNode));
        }
        return new Graph(nodesNumber, edges);
    }

    public static Graph getCustomGraph() {
        ArrayList<EdgePair> edges = new ArrayList<>();
        edges.add(new EdgePair(0, 1));
        edges.add(new EdgePair(0, 4));
        edges.add(new EdgePair(1, 0));
        edges.add(new EdgePair(1, 5));
        edges.add(new EdgePair(1, 2));
        edges.add(new EdgePair(2, 3));
        edges.add(new EdgePair(2, 5));
        edges.add(new EdgePair(2, 1));
        edges.add(new EdgePair(3, 4));
        edges.add(new EdgePair(3, 5));
        edges.add(new EdgePair(3, 2));
        edges.add(new EdgePair(4, 0));
        edges.add(new EdgePair(4, 5));
        edges.add(new EdgePair(4, 3));
        edges.add(new EdgePair(5, 1));
        edges.add(new EdgePair(5, 2));
        edges.add(new EdgePair(5, 3));
        edges.add(new EdgePair(5, 4));
        return new Graph(6, edges);
    }
}
