package net.grigoriadi;

import java.util.Objects;

public class NodeId {
    private final long id;

    public NodeId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeId nodeId = (NodeId) o;
        return id == nodeId.id;
    }

    public long getId() {
        return id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "NodeId:"+id;
    }
}
