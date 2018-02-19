package net.grigoriadi;

/**
 * Data wrapping class, so we can order printing from leaves to root.
 */
public class EventCandidate implements Comparable<EventCandidate> {

    private final NodeId nodeId;
    private final PriorityColor priorityColor;
    private final Long level;

    public EventCandidate(NodeId nodeId, PriorityColor priorityColor, long level) {
        this.nodeId = nodeId;
        this.priorityColor = priorityColor;
        this.level = level;
    }

    public NodeId getNodeId() {
        return nodeId;
    }

    public PriorityColor getPriorityColor() {
        return priorityColor;
    }

    /**
     * Level in tree.
     * @return leve.
     */
    public Long getLevel() {
        return level;
    }

    @Override
    public int compareTo(EventCandidate o) {
        return this.level.compareTo(o.level) * -1;
    }
}
