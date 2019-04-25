package artemmindrov.graphs;

import artemmindrov.graphs.pathfinders.BellmanFord;
import artemmindrov.graphs.pathfinders.PathFinder;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

/**
 * An abstract graph representation supporting a few basic operations: adding vertices/edges and path lookup.
 * @param <V> user defined vertex type
 */
public abstract class Graph<V> {
    private static final long DEFAULT_WEIGHT = 1;
    private final ConcurrentMap<V, Boolean> vertices = new ConcurrentHashMap<>();
    private final EdgeContainer<V> edgeContainer;

    Graph(EdgeContainer<V> ei) {
        edgeContainer = ei;
    }

    /**
     * @return an immutable view of the graph's vertices, in no particular order
     */
    public Set<V> vertices() {
        return Collections.unmodifiableSet(vertices.keySet());
    }

    /**
     * @return an immutable view of the graph's edges, in no particular order
     * Refer to {@link artemmindrov.graphs.Edge} for operations supported on edges
     * Also note that edges are unidirectional, so for undirected graphs, vertices are connected using two edges
     */
    public Set<Edge<V>> edges() {
        return Collections.unmodifiableSet(edgeContainer.edges());
    }

    /**
     * @return a boolean telling if this graph is weighted
     */
    public boolean isWeighted() { return true; }

    /**
     * @return a boolean telling if this graph is directed
     */
    public boolean isDirected() { return edgeContainer.isDirected(); }

    public boolean addVertex(final V vertex) {
        return vertices.putIfAbsent(vertex, true) == null;
    }

    private void ensureVertex(V vertex) {
        if (!vertices.containsKey(vertex)) {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Add an edge with the specified weight between two vertices. Vertices should have been added with prior calls
     * to {@link artemmindrov.graphs.Graph#addVertex(Object)}. Only supported on weighted graphs.
     * @param source source vertex for the edge
     * @param sink target vertex for the edge
     * @param weight edge's weight (can be 0 or negative)
     * @return true if the edge was added, false if the edge with the same weight already exists between the specified vertices
     * @throws UnsupportedOperationException when either source or sink are non-existent
     */
    public boolean addEdge(final V source, final V sink, long weight) {
        ensureVertex(source);
        ensureVertex(sink);
        return edgeContainer.addEdge(source, sink, weight);
    }

    /**
     * An unweighted counterpart to {@link artemmindrov.graphs.Graph#addEdge(Object, Object, long)}. Only supported
     * on unweighted graphs.
     * @param source source vertex for the edge
     * @param sink target vertex for the edge
     * @return true if the edge was added, false if the edge with the same weight already exists between the specified vertices
     * @throws UnsupportedOperationException when either source or sink are non-existent
     */
    public boolean addEdge(V source, V sink) {
        ensureVertex(source);
        ensureVertex(sink);
        return edgeContainer.addEdge(source, sink, DEFAULT_WEIGHT);
    }

    /**
     * @param pf a custom {@link artemmindrov.graphs.pathfinders.PathFinder} instance
     * @param source vertex to use as path source
     * @param dest vertex to use as path end
     * @return a list of edges between the vertices as produced by the path finder, an empty list if any of
     * the vertices are non-existent or there is no path between them
     */
    public List<Edge<V>> getPath(PathFinder<V> pf, V source, V dest) {
        Set<V> vertices = vertices();

        if (source.equals(dest) || !vertices.contains(source) || !vertices.contains(dest)) {
            return Collections.emptyList();
        }

        return pf.getPath(this, source, dest);
    }

    /**
     * A convenience flavor for {@link artemmindrov.graphs.Graph#getPath(PathFinder, Object, Object)}
     * using the Bellman-Ford algorithm.
     */
    public List<Edge<V>> getPath(V source, V dest) {
        return getPath(new BellmanFord<>(), source, dest);
    }

    /**
     * Applies {@code udf} to all (even unconnected) vertices in no particular order. It is not guaranteed that the
     * calling thread will see any concurrently added vertices.
     * @param udf user supplied function
     */
    public void traverse(Consumer<V> udf) {
        for (V v : vertices.keySet()) {
            udf.accept(v);
        }
    }

    /**
     * @return a {@link Builder} instance
     */
    public static <V> Builder<V> builder() {
        return new Builder<>();
    }

    /**
     * Helper class to create {@link Graph} instances
     * @param <V> user defined type for the {@link Graph}'s vertices
     */
    public static class Builder<V> {
        private boolean isDirected = true, isWeighted = true;

        public Builder() {}

        /**
         * @param directed set to control whether the graph should be directed (default it true)
         * @return self
         */
        public Builder<V> directed(boolean directed) {
            isDirected = directed;
            return this;
        }

        /**
         * @param weighted set to control whether the graph should be weighted (default it true)
         * @return self
         */
        public Builder<V> weighted(boolean weighted) {
            isWeighted = weighted;
            return this;
        }

        /**
         * @return {@link Graph} instance based on properties set previously
         */
        public Graph<V> build() {
            EdgeContainer<V> ei = isDirected ? new EdgeContainer<>() : new UndirectedEdgeContainer<>();
            return isWeighted ? new WeightedGraph<>(ei) : new UnweightedGraph<>(ei);
        }
    }
}
