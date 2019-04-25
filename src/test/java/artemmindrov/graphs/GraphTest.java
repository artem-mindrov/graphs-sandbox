package artemmindrov.graphs;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class GraphTest {
    private Graph<Integer> graph;

    @Before
    public void setup() {
        graph = Graph.<Integer>builder().build();
        graph.addVertex(3);
        graph.addVertex(4);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void returnsImmutableVertexView() {
        Set<Integer> vertices = graph.vertices();
        assertThat(vertices, is(Stream.of(3, 4).collect(Collectors.toSet())));
        vertices.add(5);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void returnsImmutableEdgeView() {
        graph.addEdge(3, 4, 1);
        Set<Edge<Integer>> edges = graph.edges();
        assertEquals(1, edges.size());
        assertThat(edges, contains(new Edge<>(3, 4, 1)));
        edges.add(new Edge<>(4, 3, 1));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void failsToAddEdgesFromNonExistentSources() {
        graph.addEdge(5, 4, 1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void failsToAddEdgesToNonExistentSinks() {
        graph.addEdge(3, 5, 1);
    }

    @Test
    public void findsNoPathForNonexistentArgs() {
        graph.addEdge(3, 4, 1);
        List<Edge<Integer>> path = graph.getPath(8, 9);
        assertTrue(path.isEmpty());
    }

    @Test
    public void returnsEmptyPathIfSourceAndSinkAreSame() {
        graph.addEdge(3,4,1);
        List<Edge<Integer>> path = graph.getPath(4,4);
        assertTrue(path.isEmpty());
    }

    @Test
    public void traversesEveryVertexWithUDF() {
        List<Integer> values = new ArrayList<>();
        graph.traverse(values::add);
        assertEquals(values.size(), 2);
        assertThat(values, containsInAnyOrder(3, 4));
    }
}