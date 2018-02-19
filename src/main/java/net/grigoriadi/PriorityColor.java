package net.grigoriadi;

import javax.json.bind.annotation.JsonbTypeAdapter;
import java.awt.*;
import java.util.Objects;

public class PriorityColor {

    @JsonbTypeAdapter(ColorAdapter.class)
    private final Color color;

    private final long priority;

    public PriorityColor(PriorityColor other) {
        this(other.getColor(), other.getPriority());
    }

    public PriorityColor(Color color, long priority) {
        this.color = color;
        this.priority = priority;
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
        PriorityColor that = (PriorityColor) o;
        return priority == that.priority &&
                Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {

        return Objects.hash(color, priority);
    }
}
