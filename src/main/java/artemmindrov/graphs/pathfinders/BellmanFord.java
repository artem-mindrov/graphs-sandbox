package artemmindrov.graphs.pathfinders;

import artemmindrov.graphs.Edge;
import artemmindrov.graphs.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BellmanFord<V> implements PathFinder<V> {
    /**
     * This is an implementation of the <a href="https://en.wikipedia.org/wiki/Bellman%E2%80%93Ford_algorithm">Bellman-Ford</a> algorithm.
     * @throws IllegalStateException in case a negative weight cycle is detected
     */
    @Override
    public List<Edge<V>> getPath(Graph<V> graph, V source, V dest) {
        Set<V> vertices = graph.vertices();
        Map<V, Double> distances = vertices.stream().collect(Collectors.toMap(e -> e, e -> Double.POSITIVE_INFINITY));
        Map<V, Edge<V>> predecessors = new HashMap<>();
        distances.put(source, 0d);

        for (int i = 0; i < vertices.size(); i++) {
            for (Edge<V> e : graph.edges()) {
                double distance = distances.get(e.source()) + e.weight();
                V sink = e.sink();

                if (distance < distances.get(sink)) {
                    distances.put(sink, distance);
                    predecessors.put(sink, e);
                }
            }
        }

        for (Edge<V> e : graph.edges()) {
            if (distances.get(e.source()) + e.weight() < distances.get(e.sink())) {
                throw new IllegalStateException("Negative weight cycle detected");
            }
        }

        List<Edge<V>> path = new ArrayList<>();
        Edge<V> e = predecessors.get(dest);

        while (e != null) {
            path.add(0, e);

            if (e.isFrom(source))
                break;

            e = predecessors.get(e.source());
        }

        return path;
    }
}
