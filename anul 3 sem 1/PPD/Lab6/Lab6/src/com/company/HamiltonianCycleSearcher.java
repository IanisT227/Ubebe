package com.company;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HamiltonianCycleSearcher {
    private static final int THREADPOOL_SIZE = 12;

    private final Graph givenGraph;
    private final Integer startingNode;

    private LinkedHashSet<Integer> visitedNodes;
    private ArrayList<Integer> hamiltonianPath;
    private final Lock lock;
    private ExecutorService executorService;
    private static final int MAX_DEPTH = 2;

    public HamiltonianCycleSearcher(Graph givenGraph, Integer startingNode) {
        this.givenGraph = givenGraph;
        this.startingNode = startingNode;
        visitedNodes = new LinkedHashSet<>();
        hamiltonianPath = new ArrayList<>();
        lock = new ReentrantLock();
    }

    public HamiltonianCycleSearcher(Graph givenGraph, Integer startingNode, LinkedHashSet<Integer> visitedNodes, ArrayList<Integer> hamiltonianPath, Lock lock, ExecutorService executorService) {
        this.givenGraph = givenGraph;
        this.startingNode = startingNode;
        this.visitedNodes = visitedNodes;
        this.hamiltonianPath = hamiltonianPath;
        this.lock = lock;
        this.executorService = executorService;
    }

    private void visitNodeSequential(Integer node) {
        lock.lock();
        if (!hamiltonianPath.isEmpty()) {
            lock.unlock();
            return;
        }

        lock.unlock();
        visitedNodes.add(node);
        if (visitedNodes.size() != givenGraph.getNodesNumber()) {
            givenGraph.getNeighbours(node)
                    .stream()
                    .filter(neighbouringNode -> !visitedNodes.contains(neighbouringNode))
                    .forEach(this::visitNodeSequential);
        } else if (givenGraph.getNeighbours(node).contains(startingNode)) {
            lock.lock();
            hamiltonianPath.addAll(visitedNodes);
            lock.unlock();
        }
        visitedNodes.remove(node);
    }

    public void getSequentialList() {
        hamiltonianPath = new ArrayList<>();
        visitedNodes = new LinkedHashSet<>();
        visitNodeSequential(startingNode);
        System.out.println(hamiltonianPath);
    }

    private void visitNodeParallel(Integer node, Integer depth) throws ExecutionException, InterruptedException {
//       System.out.println("merge " + node +" "+ depth);
        lock.lock();
        if (!hamiltonianPath.isEmpty()) {
            lock.unlock();
            return;
        }
        lock.unlock();
        if (depth < MAX_DEPTH) {
            visitedNodes.add(node);
            if (visitedNodes.size() != givenGraph.getNodesNumber()) {
                ArrayList<Future> futures = new ArrayList<>();
                givenGraph.getNeighbours(node).stream().filter(neighbouringNode -> !visitedNodes
                                .contains(neighbouringNode))
                        .forEach(neighbouringNode -> {
                            futures.add(executorService.submit(() -> {
                                try {
                                    this.copy().visitNodeParallel(neighbouringNode, depth + 1);
                                } catch (ExecutionException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }));
                        });
                for (Future future : futures) {
                    future.get();
                }
            } else {
                lock.lock();
                hamiltonianPath.addAll(visitedNodes);
                lock.unlock();
            }
        } else {
            this.copy().visitNodeSequential(node);
        }
    }

    public void getParallelList() throws ExecutionException, InterruptedException {
        executorService = Executors.newFixedThreadPool(THREADPOOL_SIZE);
        hamiltonianPath = new ArrayList<>();
        visitedNodes = new LinkedHashSet<>();
        visitNodeParallel(0, 0);
        executorService.shutdown();
        executorService.awaitTermination(120, TimeUnit.SECONDS);
        System.out.println(hamiltonianPath);
    }

    private HamiltonianCycleSearcher copy() {
        LinkedHashSet<Integer> newVisited = new LinkedHashSet<>(visitedNodes);
        return new HamiltonianCycleSearcher(givenGraph, startingNode, newVisited, hamiltonianPath, lock, executorService);
    }
}
