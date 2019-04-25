package artemmindrov.graphs.pathfinders;

import artemmindrov.graphs.Edge;
import artemmindrov.graphs.Graph;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BellmanFordTest {
    private Graph<Integer> graph;
    private PathFinder<Integer> bellmanFord;

    private <V> List<V> pathVertices(List<Edge<V>> path) {
        List<V> vertices = path.stream().map(Edge::sink).collect(Collectors.toList());
        vertices.add(0, path.get(0).source());
        return vertices;
    }

    private <V> long pathCost(List<Edge<V>> path) {
        return path.stream().mapToLong(Edge::weight).sum();
    }

    @Before
    public void setup() {
        graph = Graph.<Integer>builder().build();
        bellmanFord = new BellmanFord<>();
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
    }

    @Test
    public void findsShortestPathsInDirectedGraphs() {
        graph.addEdge(3, 4, 1);
        graph.addEdge(3, 5, 2);
        graph.addEdge(4, 6, 2);
        graph.addEdge(5, 6, 2);

        List<Edge<Integer>> path = bellmanFord.getPath(graph, 3,6);
        assertEquals(3, pathCost(path));
        assertThat(pathVertices(path), is(Arrays.asList(3, 4, 6)));
    }

    @Test
    public void findsNoPathInReverseDirection() {
        graph.addEdge(3, 4, 1);
        graph.addEdge(4, 6, 2);
        List<Edge<Integer>> path = bellmanFord.getPath(graph, 6,3);
        assertTrue(path.isEmpty());
    }

    @Test
    public void findsNoPathBetweenIsolatedSubgraphs() {
        graph.addEdge(3, 4, 1);
        graph.addEdge(5, 6, 2);
        List<Edge<Integer>> path = bellmanFord.getPath(graph, 3,6);
        assertTrue(path.isEmpty());
    }

    @Test
    public void findsShortestPathsInGraphsWithLoops() {
        graph.addEdge(3, 4, 1);
        graph.addEdge(3, 3, 2);
        graph.addEdge(3, 5, 2);
        graph.addEdge(4, 6, 2);
        graph.addEdge(4, 4, 2);
        graph.addEdge(5, 6, 2);

        List<Edge<Integer>> path = bellmanFord.getPath(graph, 3,6);
        assertEquals(3, pathCost(path));
        assertThat(pathVertices(path), is(Arrays.asList(3, 4, 6)));
    }

    @Test
    public void findsShortestPathInUndirectedGraphs() {
        graph = Graph.<Integer>builder().directed(false).build();
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addVertex(5);
        graph.addVertex(6);
        graph.addEdge(3, 4, 1);
        graph.addEdge(3, 5, 2);
        graph.addEdge(4, 6, 2);
        graph.addEdge(5, 6, 2);

        findsShortestPathsInDirectedGraphs();
        List<Edge<Integer>> path = bellmanFord.getPath(graph,6,3);
        assertEquals(3, pathCost(path));
        assertThat(pathVertices(path), is(Arrays.asList(6, 4, 3)));
    }

    @Test(expected = IllegalStateException.class)
    public void detectsNegativeCycles() {
        graph.addVertex(7);
        graph.addEdge(3, 4, 1);
        graph.addEdge(4, 5, 1);
        graph.addEdge(5, 6, 1);
        graph.addEdge(5, 7, -4);
        graph.addEdge(7, 4, 1);
        bellmanFord.getPath(graph,3,6);
    }
}