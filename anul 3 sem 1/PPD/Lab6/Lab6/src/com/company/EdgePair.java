package com.company;

import java.util.Objects;

public class EdgePair {
    private Integer fromNode;
    private Integer toNode;

    public Integer getFromNode() {
        return fromNode;
    }

    public void setFromNode(Integer fromNode) {
        this.fromNode = fromNode;
    }

    public Integer getToNode() {
        return toNode;
    }

    public void setToNode(Integer toNode) {
        this.toNode = toNode;
    }

    public EdgePair(Integer fromNode, Integer toNode) {
        this.fromNode = fromNode;
        this.toNode = toNode;
    }

    @Override
    public String toString() {
        return "{fromNode:" + fromNode +
                ", toNode:" + toNode + "}";
    }

}
