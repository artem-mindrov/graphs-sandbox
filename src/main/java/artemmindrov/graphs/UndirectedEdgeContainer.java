package artemmindrov.graphs;

import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class UndirectedEdgeContainer<V> extends EdgeContainer<V> {
    private ReadWriteLock rwl = new ReentrantReadWriteLock();

    UndirectedEdgeContainer() { super(); }

    @Override
    Set<Edge<V>> edges() {
        rwl.readLock().lock();

        try {
            return super.edges();
        } finally {
            rwl.readLock().unlock();
        }
    }

    @Override
    boolean addEdge(final V source, final V sink, long weight) {
        rwl.writeLock().lock();

        try {
            return super.addEdge(source, sink, weight) || super.addEdge(sink, source, weight);
        } finally {
            rwl.writeLock().unlock();
        }
    }

    @Override
    boolean isDirected() { return false; }
}
