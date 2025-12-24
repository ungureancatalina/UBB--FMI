package lab;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class FineGrainedGradeList {

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

    public FineGrainedGradeList() {
        head = new Node(Integer.MIN_VALUE, 0);  // santinela start
        tail = new Node(Integer.MAX_VALUE, 0);  // santinela final
        head.next = tail;
    }

    // insert/update cu fine-grain locking (lock-coupling)
    public void addOrUpdate(int id, int grade) {
        head.lock.lock();
        Node prev = head;
        Node cur = prev.next;
        cur.lock.lock();

        try {
            // lista sortata dupa ID
            while (cur != tail && cur.id < id) {
                prev.lock.unlock();
                prev = cur;
                cur = cur.next;
                cur.lock.lock();
            }

            if (cur != tail && cur.id == id) {
                // exista deja adaugam nota
                cur.total += grade;
            } else {
                // nu exista inseram nod nou intre prev si cur
                Node n = new Node(id, grade);
                n.next = cur;
                prev.next = n;
            }
        } finally {
            cur.lock.unlock();
            prev.lock.unlock();
        }
    }

    // extrage continutul intr-o lista de GradeRecord
    public List<GradeRecord> toRecords() {
        List<GradeRecord> out = new ArrayList<>();
        Node cur = head.next;
        while (cur != tail) {
            out.add(new GradeRecord(cur.id, cur.total));
            cur = cur.next;
        }
        return out;
    }
}
