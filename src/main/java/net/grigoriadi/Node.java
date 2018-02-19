package net.grigoriadi;

import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTransient;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@JsonbPropertyOrder({"nodeId", "priority", "nodeColor", "currentColor", "children"})
public class Node {

    @JsonbTransient
    private Node parent;

    private NodeId nodeId;

    private PriorityColor nodeColor;

    private PriorityColor currentColor;

    private List<Node> children = new ArrayList<>();

    public void recalculateBranch() {
        long level = 0;
        Node node = this;

        while (node != null) {
            node = node.getParent();
            level++;
        }
        recalculateBranchColors(level);
    }

    /**
     * Recalculate node values, remember last state of the node.
     * If priority of the one of the transitive children of this node is highest than this node:
     * set color and priority to the values of the node with highest priority.
     * In the other case set color to the value of the original node color.
     *
     * @param level
     * @return
     */
    public PriorityColor recalculateBranchColors(long level) {
        if (currentColor == null) {
            currentColor = new PriorityColor(nodeColor);
        }
        level++;
        if (children.size() == 0) {
            Context.getInstance().getEventQueue().add(new EventCandidate(nodeId, currentColor, level));
            return new PriorityColor(currentColor);
        }
        PriorityColor topPriorityColor = new PriorityColor(nodeColor);
        for (Node child : children) {
            PriorityColor childPriorityColor = child.recalculateBranchColors(level);
            if (childPriorityColor.getPriority() > topPriorityColor.getPriority()) {
                topPriorityColor = new PriorityColor(childPriorityColor);
            }
        }
        currentColor = topPriorityColor;
        Context.getInstance().getEventQueue().add(new EventCandidate(nodeId, currentColor, level));
        return topPriorityColor;
    }

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

    public void setNodeId(NodeId nodeId) {
        this.nodeId = nodeId;
    }

    public PriorityColor getNodeColor() {
        return nodeColor;
    }

    public void setNodeColor(PriorityColor nodeColor) {
        this.nodeColor = nodeColor;
    }

    public PriorityColor getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(PriorityColor currentColor) {
        this.currentColor = currentColor;
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

    public static final class NodeColorChangedConsumer implements Consumer<Node> {

        @Override
        public void accept(Node node) {

        }
    }

}
