package net.grigoriadi;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private Node parent;

    private final NodeId nodeId;

    private NodeColor nodeColor;

    private List<Node> children = new ArrayList<>();

    public Node(NodeId nodeId) {
        this.nodeId = nodeId;
    }

    public Node(NodeId nodeId, List<Node> children) {
        this.nodeId = nodeId;
        this.children = children;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public NodeId getNodeId() {
        return nodeId;
    }

    public NodeColor getNodeColor() {
        return nodeColor;
    }

    public void setNodeColor(NodeColor nodeColor) {
        this.nodeColor = nodeColor;
    }

    public void addChild(Node node) {
        children.add(node);
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return nodeId + " color: "+nodeColor.getColor();
    }
}
