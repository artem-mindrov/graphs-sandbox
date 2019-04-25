package artemmindrov.graphs;

/**
 * Represents a unidirectional graph edge from a single source vertex to a single target.
 * For weighted graphs, also contains the weight.
 * @param <V> user defined vertex type
 */
public class Edge<V> {
    private final V source, sink;
    private final long weight;

    Edge(final V source, final V sink, long weight) {
        this.source = source;
        this.sink = sink;
        this.weight = weight;
    }

    /**
     * Can be used to check if this edge goes out of a particular vertex
     * @param source the vertex to check the edge against
     * @return whether the edge is going out of the specified vertex
     */
    public boolean isFrom(V source) {
        return source.equals(this.source);
    }

    /**
     * @return the vertex this edge goes out of
     */
    public V source() { return source; }

    /**
     * @return the vertex this edge terminates at
     */
    public V sink() { return sink; }

    /**
     * @return the edge's weight
     */
    public long weight() { return weight; }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Edge)) {
            return false;
        }

        Edge e = (Edge)other;
        return (source.equals(e.source) && sink.equals(e.sink) && weight == e.weight);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + ((source == null) ? 0 : source.hashCode());
        result = prime * result + ((sink == null) ? 0 : sink.hashCode());
        result = prime * result + (int)weight;
        return result;
    }
}
