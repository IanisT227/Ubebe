package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Graph {
    private Integer nodesNumber;
    private ArrayList<EdgePair> edges;
    private Map<Integer, HashSet<Integer>> adjacencyList;

    public Graph(Integer nodesNumber, ArrayList<EdgePair> edges) {
        this.nodesNumber = nodesNumber;
        this.edges = edges;

        Map<Integer, HashSet<Integer>> newAdjacencyList = new HashMap<>();
        for (EdgePair edgePair : edges) {
            if (!newAdjacencyList.containsKey(edgePair.getFromNode())) {
                HashSet<Integer> neighboursList = new HashSet<>();
                neighboursList.add(edgePair.getToNode());
                newAdjacencyList.put(edgePair.getFromNode(), neighboursList);
            } else {
                HashSet<Integer> neighboursList = newAdjacencyList.get(edgePair.getFromNode());
                neighboursList.add(edgePair.getToNode());
                newAdjacencyList.put(edgePair.getFromNode(), neighboursList);
            }
        }
        adjacencyList = newAdjacencyList;
    }

    public HashSet<Integer> getNeighbours(Integer node) {
        return adjacencyList.get(node);
    }

    public Integer getNodesNumber() {
        return nodesNumber;
    }

    public void setNodesNumber(Integer nodesNumber) {
        this.nodesNumber = nodesNumber;
    }

    public ArrayList<EdgePair> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<EdgePair> edges) {
        this.edges = edges;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "nodesNumber=" + nodesNumber +
                ", edges=" + edges +
                ", adjacencyList=" + adjacencyList +
                '}';
    }
}
