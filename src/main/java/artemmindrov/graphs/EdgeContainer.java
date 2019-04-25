package artemmindrov.graphs;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class EdgeContainer<V> {
    private final ConcurrentMap<Edge<V>, Boolean> edges = new ConcurrentHashMap<>();

    boolean addEdge(V source, V sink, long weight) {
        return edges.putIfAbsent(new Edge<>(source, sink, weight), true) == null;
    }

    boolean isDirected() { return true; }

    Set<Edge<V>> edges() { return edges.keySet(); }
}
