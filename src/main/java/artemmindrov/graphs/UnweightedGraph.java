package artemmindrov.graphs;

/**
 * An unweighted graph implementation
 * @param <V> user defined vertex type
 */
public final class UnweightedGraph<V> extends Graph<V> {
    UnweightedGraph(EdgeContainer<V> ei) {
        super(ei);
    }

    @Override
    public boolean isWeighted() { return false; }

    /**
     * @throws UnsupportedOperationException weighted operations are not supported on unweighted graphs
     */
    @Override
    public boolean addEdge(final V source, final V sink, long weight) {
        throw new UnsupportedOperationException();
    }
}
