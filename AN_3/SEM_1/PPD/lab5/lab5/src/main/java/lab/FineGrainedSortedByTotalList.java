package lab;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

public class FineGrainedSortedByTotalList {

    static class Node {
        int id;
        int total;
        Node next;
        final ReentrantLock lock = new ReentrantLock();

        Node(int id, int total) {
            this.id = id;
            this.total = total;
        }
    }

    private final Node head;
    private final Node tail;

    public FineGrainedSortedByTotalList() {
        // santinele: head cu "nota" maxima, tail cu "nota" minima
        head = new Node(-1, Integer.MAX_VALUE);
        tail = new Node(-1, Integer.MIN_VALUE);
        head.next = tail;
    }

    // insereaza astfel incat lista sa fie descrescatoare dupa total
    public void insertSorted(int id, int total) {
        head.lock.lock();
        Node prev = head;
        Node cur = prev.next;
        cur.lock.lock();

        try {
            // mergem descrescator
            while (cur != tail &&
                    (cur.total > total ||
                            (cur.total == total && cur.id < id))) {
                prev.lock.unlock();
                prev = cur;
                cur = cur.next;
                cur.lock.lock();
            }

            Node n = new Node(id, total);
            n.next = cur;
            prev.next = n;
        } finally {
            cur.lock.unlock();
            prev.lock.unlock();
        }
    }

    public void writeToFile(String filename) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            Node cur = head.next;
            while (cur != tail) {
                bw.write(cur.id + " " + cur.total);
                bw.newLine();
                cur = cur.next;
            }
        }
    }
}
