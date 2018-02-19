package net.grigoriadi;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        NodeId first = new NodeId(1);
        NodeId second = new NodeId(2);
        NodeId third = new NodeId(3);
        NodeId fourth = new NodeId(4);

        Map<NodeId, List<NodeId>> initMap = new HashMap<>();
        initMap.put(first, Arrays.asList(second, third));
        initMap.put(third, Collections.singletonList(fourth));

        NodeColor firstColor = new NodeColor(first, Color.BLACK, 30);
        NodeColor secondColor = new NodeColor(second, Color.RED, 50);
        NodeColor thirdColor = new NodeColor(third, Color.BLUE, 20);
        NodeColor fourthColor = new NodeColor(fourth, Color.GREEN, 30);
        List<NodeColor> initColors = Arrays.asList(firstColor, secondColor, thirdColor, fourthColor);


        NodeColorService service = new NodeColorService(initMap, initColors);
        service.addNodeColorChangeObserver(new PrintingColorObserver());
        service.colorChanged(fourthColor);
        service.colorChanged(new NodeColor(fourth, Color.BLACK, 100));
        service.colorChanged(new NodeColor(fourth, Color.WHITE, 1));

        System.out.println("DONE");
    }

    private final static class PrintingColorObserver implements NodeColorChangeObserver {

        @Override
        public void colorChanged(NodeColor nodeColor) {
            System.out.println("Color changed: "+nodeColor.getNodeId() + " priority:"+nodeColor.getPriority()
                    + " color:" + nodeColor.getColor());
        }
    }
}
