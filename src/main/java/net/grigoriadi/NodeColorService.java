package net.grigoriadi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeColorService implements NodeColorChangeObservable, NodeColorChangeObserver {

    private final List<NodeColorChangeObserver> observers = new ArrayList<>();

    private Node root;

    private Map<NodeId, Node> nodeMapping = new HashMap<>();



    public NodeColorService(Map<NodeId, List<NodeId>> initMap, List<NodeColor> initColors) {
        for (Map.Entry<NodeId, List<NodeId>> entry : initMap.entrySet()) {
            //this need more elaboration, to update root according to content, but just for case of the example..
            if (root == null) {
                root = new Node(entry.getKey());
                nodeMapping.put(root.getNodeId(), root);
                addChildren(root, entry.getValue());
                continue;
            }

            addChildren(root, entry.getKey(), entry.getValue());
        }
        for (NodeColor color : initColors) {
            nodeMapping.get(color.getNodeId()).setNodeColor(color);
        }
    }

    @Override
    public void addNodeColorChangeObserver(NodeColorChangeObserver observer) {
        observers.add(observer);
    }

    @Override
    public void colorChanged(NodeColor color) {
        System.out.println("Changing color:");
        Node node = nodeMapping.get(color.getNodeId());

        NodeColor highestPriorityColor = null;

        while (node != null) {
            highestPriorityColor = highestPriorityColor == null ?
                    color : newHighestPriorityColor(node.getNodeColor(), highestPriorityColor);
            observeColor(new NodeColor(node.getNodeId(), highestPriorityColor.getColor(), highestPriorityColor.getPriority()));
            node = node.getParent();
        }
        System.out.println("Color changed. ");
    }

    private void observeColor(NodeColor color) {
        for (NodeColorChangeObserver observer : observers) {
            observer.colorChanged(color);
        }
    }

    private NodeColor newHighestPriorityColor(NodeColor first, NodeColor second) {
        if (first.getPriority() > second.getPriority()) {
            return new NodeColor(first);
        } else {
            return new NodeColor(second);
        }
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
            nodeMapping.put(child, newNode);
        }
    }

}
