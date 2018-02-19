package net.grigoriadi;

import java.awt.*;
import java.util.Objects;

public class NodeColor {

    private final NodeId nodeId;

    private final Color color;

    private final long priority;

    public NodeColor(NodeId nodeId, Color color, long priority) {
        this.nodeId = nodeId;
        this.color = color;
        this.priority = priority;
    }

    public NodeId getNodeId() {
        return nodeId;
    }

    public NodeColor(NodeColor other) {
        this.nodeId = other.getNodeId();
        this.color = other.getColor();
        this.priority = other.getPriority();
    }

    public Color getColor() {
        return color;
    }

    public long getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeColor nodeColor = (NodeColor) o;
        return priority == nodeColor.priority &&
                Objects.equals(nodeId, nodeColor.nodeId) &&
                Objects.equals(color, nodeColor.color);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nodeId, color, priority);
    }
}
