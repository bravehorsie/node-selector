package net.grigoriadi;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import java.awt.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        NodeId first = new NodeId(1);
        NodeId second = new NodeId(2);
        NodeId third = new NodeId(3);
        NodeId fourth = new NodeId(4);
        NodeId fifth = new NodeId(5);
        NodeId sixth = new NodeId(6);
        NodeId seventh = new NodeId(7);
        NodeId eight = new NodeId(8);

        Map<NodeId, List<NodeId>> initMap = new HashMap<>();
        initMap.put(first, Arrays.asList(second, third));
        initMap.put(third, Arrays.asList(fourth, fifth, sixth));
        initMap.put(sixth, Arrays.asList(seventh, eight));


        NodeColor firstColor = new NodeColor(first, Color.BLACK, 30);
        NodeColor secondColor = new NodeColor(second, Color.RED, 50);
        NodeColor thirdColor = new NodeColor(third, Color.BLUE, 20);
        NodeColor fourthColor = new NodeColor(fourth, Color.GREEN, 30);
        NodeColor fifthColor = new NodeColor(fifth, Color.MAGENTA, 75);
        NodeColor sixthColor = new NodeColor(sixth, Color.CYAN, 40);
        NodeColor seventhColor = new NodeColor(seventh, Color.PINK, 15);
        NodeColor eighthColor = new NodeColor(eight, Color.YELLOW, 20);

        List<NodeColor> initColors = Arrays.asList(firstColor, secondColor, thirdColor, fourthColor, fifthColor, sixthColor, seventhColor, eighthColor);

        NodeColorService service = new NodeColorService(initMap, initColors);
        service.addNodeColorChangeObserver(new PrintingColorObserver());
        service.colorChanged(new NodeColor(fourth, Color.BLACK, 100));
        service.colorChanged(new NodeColor(fourth, Color.WHITE, 1));
        service.colorChanged(new NodeColor(seventh, Color.WHITE, 150));
        service.colorChanged(new NodeColor(seventh, Color.WHITE, 5));

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
