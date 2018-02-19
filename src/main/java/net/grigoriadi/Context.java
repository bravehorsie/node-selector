package net.grigoriadi;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Context {

    private static final Context INSTANCE = new Context();

    private Node root;

    private final Map<NodeId, Node> nodeMapping = new HashMap<>();

    private final PriorityQueue<EventCandidate> eventQueue = new PriorityQueue<>();

    private final Map<NodeId, PriorityColor> lastDataSent = new HashMap<>();

    private Context() {
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public Map<NodeId, Node> getNodeMapping() {
        return nodeMapping;
    }

    public PriorityQueue<EventCandidate> getEventQueue() {
        return eventQueue;
    }

    public static final Context getInstance() {
        return INSTANCE;
    }

    public Map<NodeId, PriorityColor> getLastDataSent() {
        return lastDataSent;
    }
}
