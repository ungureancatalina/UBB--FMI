package lab;

import java.util.LinkedList;

public class BoundedGradeQueue {
    private final LinkedList<GradeRecord> queue = new LinkedList<>();
    private final int capacity;

    private int activeProducers; // cate task-uri de citire mai au de lucrat

    public BoundedGradeQueue(int capacity, int producersCount) {
        this.capacity = capacity;
        this.activeProducers = producersCount;
    }

    public synchronized void put(GradeRecord rec) throws InterruptedException {
        while (queue.size() == capacity) {
            wait(); // asteapta spatiu liber
        }
        queue.addLast(rec);
        notifyAll(); // anuntam consumatorii
    }

    public synchronized GradeRecord take() throws InterruptedException {
        while (queue.isEmpty() && activeProducers > 0) {
            wait(); // asteapta date
        }

        if (!queue.isEmpty()) {
            GradeRecord rec = queue.removeFirst();
            notifyAll(); // anuntam producatorii ca poate s-a eliberat loc
            return rec;
        }

        // coada e goala si nu mai sunt producatori
        return null;
    }

    public synchronized void producerDone() {
        activeProducers--;
        if (activeProducers == 0) {
            // nu mai vin date noi
            notifyAll();
        }
    }
}
