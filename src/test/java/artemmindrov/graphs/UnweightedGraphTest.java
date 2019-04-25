package artemmindrov.graphs;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnweightedGraphTest {
    private Graph<Integer> graph;

    @Before
    public void setup() {
        graph = Graph.<Integer>builder().weighted(false).build();
        graph.addVertex(3);
        graph.addVertex(5);
    }

    @Test
    public void allowsUnweightedInserts() {
        assertTrue(graph.addEdge(3, 5));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void disallowsWeightedInserts() {
        assertTrue(graph.addEdge(3, 5, 2));
    }
}