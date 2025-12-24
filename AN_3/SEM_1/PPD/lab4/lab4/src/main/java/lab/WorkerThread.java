package lab;

public class WorkerThread extends Thread {
    private final GradeQueue queue;
    private final ThreadSafeGradeList list;

    public WorkerThread(GradeQueue queue, ThreadSafeGradeList list) {
        this.queue = queue;
        this.list = list;
    }

    @Override
    public void run() {
        try {
            while (true) {

                //ia un record din coada
                GradeRecord rec = queue.take();
                if (rec == null) {
                    // nu mai sunt date si producatorii au terminat
                    break;
                }

                // pentru fiecare record, actualizeaza lista
                list.addOrUpdate(rec.id, rec.grade);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
