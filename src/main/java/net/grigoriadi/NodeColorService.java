package net.grigoriadi;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NodeColorService implements NodeColorChangeObservable, NodeColorChangeObserver {

    private final Context context;

    private final Jsonb jsonb = JsonbBuilder.create(new JsonbConfig());

    private final List<NodeColorChangeObserver> observers = new ArrayList<>();


    public NodeColorService(Map<NodeId, List<NodeId>> initMap, List<NodeColor> initColors) {
        this.context = Context.getInstance();
        for (Map.Entry<NodeId, List<NodeId>> entry : initMap.entrySet()) {
            //this need more elaboration, to update root according to content, but just for case of the example..
            if (context.getRoot() == null) {
                Node root = new Node(entry.getKey());
                context.setRoot(root);
                context.getNodeMapping().put(root.getNodeId(), root);
                addChildren(root, entry.getValue());
                continue;
            }

            addChildren(context.getRoot(), entry.getKey(), entry.getValue());
        }
        for (NodeColor color : initColors) {
            context.getNodeMapping().get(color.getNodeId())
                    .setNodeColor(new PriorityColor(color.getColor(), color.getPriority()));
        }

        System.out.println("Initial tree:");
        printTree();
    }

    @Override
    public void addNodeColorChangeObserver(NodeColorChangeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void colorChanged(NodeColor color) {
        System.out.println("Changing color: "+color.getNodeId() +" "+color.getColor() + " Priority: "+color.getPriority());
        Node node = context.getNodeMapping().get(color.getNodeId());
        node.setCurrentColor(new PriorityColor(color.getColor(), color.getPriority()));
        context.getRoot().recalculateBranch();
        System.out.println("Tree after recalculation:");
        printTree();
        notifyObservers();
    }

    private void printTree() {
        System.out.println(jsonb.toJson(context.getRoot()));
        System.out.println();
    }

    private void notifyObservers() {
        EventCandidate eventCandidate;
        //first priority queue orders events to print by their level in tree, so deepest leaves are print first.
        while ((eventCandidate = context.getEventQueue().poll()) != null) {
            //don't sent events for nodes, which haven't effectively changed their values
            PriorityColor lastSent = context.getLastDataSent().get(eventCandidate.getNodeId());
            if (lastSent != null
                    && eventCandidate.getPriorityColor().equals(lastSent)) {
                continue;
            }
            for (NodeColorChangeObserver observer : observers) {
                observer.colorChanged(new NodeColor(eventCandidate.getNodeId(),
                        eventCandidate.getPriorityColor().getColor(),
                        eventCandidate.getPriorityColor().getPriority()));
                //cache values of last sent events, so we don't send events for the nodes which changed to the same values.
                context.getLastDataSent().put(eventCandidate.getNodeId(), eventCandidate.getPriorityColor());
            }
        }
        System.out.println("==================EVENTS FINISHED====================\n\n");
    }


    private void addChildren(Node node, NodeId id, List<NodeId> children) {
        if (node.getNodeId().equals(id)) {
            addChildren(node, children);
            return;
        }

        for (Node child : node.getChildren()) {
            addChildren(child, id, children);
        }
    }

    private void addChildren(Node node, List<NodeId> children) {
        for (NodeId child : children) {
            Node newNode = new Node(child);
            newNode.setParent(node);
            node.addChild(newNode);
            context.getNodeMapping().put(child, newNode);
        }
    }

}
