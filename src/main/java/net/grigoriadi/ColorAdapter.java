package net.grigoriadi;

import javax.json.bind.adapter.JsonbAdapter;
import java.awt.*;

public class ColorAdapter implements JsonbAdapter<Color, String> {

    @Override
    public String adaptToJson(Color obj) throws Exception {
        return obj.getRed()+","+obj.getGreen()+","+obj.getBlue();
    }

    @Override
    public Color adaptFromJson(String obj) throws Exception {
        String[] colors = obj.split(",");
        return new Color(Integer.parseInt(colors[0]),
                Integer.parseInt(colors[1]),
                Integer.parseInt(colors[2]));
    }
}
