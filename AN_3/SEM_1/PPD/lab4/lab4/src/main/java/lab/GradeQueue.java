package lab;

import java.util.LinkedList;

public class GradeQueue {
    private final LinkedList<GradeRecord> queue = new LinkedList<>();
    private int activeReaders;  // cate thread-uri Reader mai lucreaza
    private boolean finished = false; // toate datele au fost produse

    public GradeQueue(int numberOfReaders) {
        this.activeReaders = numberOfReaders;
    }

    // producatorii apeleaza put
    public synchronized void put(GradeRecord rec) {
        queue.addLast(rec);
        notifyAll(); // anuntam consumatorii
    }

    // consumatorii apeleaza take
    // intoarce null cand nu mai exista producatori si coada e goala
    public synchronized GradeRecord take() throws InterruptedException {
        while (queue.isEmpty() && !finished) {
            wait();
        }

        if (!queue.isEmpty()) {
            return queue.removeFirst();
        }

        // coada e goala si finished = true
        return null;
    }

    // apelat de fiecare Reader la finalul citirii
    public synchronized void readerDone() {
        activeReaders--;
        if (activeReaders == 0) {
            finished = true;
            notifyAll(); // trezim toti consumatorii care poate asteapta
        }
    }
}
