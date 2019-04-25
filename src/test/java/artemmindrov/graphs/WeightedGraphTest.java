package artemmindrov.graphs;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WeightedGraphTest {
    private Graph<Integer> graph;

    @Before
    public void setup() {
        graph = Graph.<Integer>builder().build();
        graph.addVertex(3);
        graph.addVertex(5);
    }

    @Test
    public void allowsWeightedInserts() {
        assertTrue(graph.addEdge(3, 5, 2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void disallowsUnweightedInserts() {
        assertTrue(graph.addEdge(3, 5));
    }
}