package artemmindrov.graphs;

import org.junit.Test;

import static org.junit.Assert.*;

public class BuilderTest {
    @Test
    public void buildsDirectedWeightedGraphs() {
        Graph<Integer> g = Graph.<Integer>builder().directed(true).weighted(true).build();

        assertTrue(g.isDirected());
        assertTrue(g.isWeighted());
        assertTrue(g.addVertex(3));
        assertTrue(g.addVertex(5));
        assertTrue(g.addEdge(5, 3, 2));
    }

    @Test
    public void buildsUndirectedWeightedGraphs() {
        Graph<Integer> g = Graph.<Integer>builder().directed(false).weighted(true).build();

        assertFalse(g.isDirected());
        assertTrue(g.isWeighted());
    }

    @Test
    public void buildsUndirectedUnweightedGraphs() {
        Graph<Integer> g = Graph.<Integer>builder().directed(false).weighted(false).build();

        assertFalse(g.isDirected());
        assertFalse(g.isWeighted());
    }

    @Test
    public void buildsDirectedUnweightedGraphs() {
        Graph<Integer> g = Graph.<Integer>builder().directed(true).weighted(false).build();

        assertTrue(g.isDirected());
        assertFalse(g.isWeighted());
    }
    @Test(expected = UnsupportedOperationException.class)
    public void disallowsUnweightedInsertsOnWeightedGraphs() {
        Graph<Integer> g = Graph.<Integer>builder().directed(true).weighted(true).build();
        g.addEdge(5, 3);
    }
}