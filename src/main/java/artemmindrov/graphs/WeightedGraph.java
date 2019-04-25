package artemmindrov.graphs;

/**
 * A weighted graph implementation
 * @param <V> user defined vertex type
 */
public final class WeightedGraph<V> extends Graph<V> {
    WeightedGraph(EdgeContainer<V> ei) {
        super(ei);
    }

    /**
     * @throws UnsupportedOperationException unweighted operations are not supported on weighted graphs
     */
    @Override
    public boolean addEdge(final V source, final V sink) {
        throw new UnsupportedOperationException();
    }
}
