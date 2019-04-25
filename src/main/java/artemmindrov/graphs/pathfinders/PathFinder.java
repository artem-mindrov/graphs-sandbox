package artemmindrov.graphs.pathfinders;

import artemmindrov.graphs.Edge;
import artemmindrov.graphs.Graph;

import java.util.List;

/**
 * An interface to implement custom path finders for a graph
 * @param <V> user defined vertex type
 */
public interface PathFinder<V> {
    /**
     * Find a path between any two vertices as a list of {@link Edge}s
     * @param graph the graph to search in
     * @param source start vertex
     * @param dest end vertex
     * @return should return the list of edges if a path exists, or an empty list in case any of the provided vertices
     * are non-existent or no path exists between them
     */
    List<Edge<V>> getPath(Graph<V> graph, V source, V dest);
}
