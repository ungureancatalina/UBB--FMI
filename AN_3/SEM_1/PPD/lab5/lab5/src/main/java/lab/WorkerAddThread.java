package lab;

public class WorkerAddThread extends Thread {

    private final BoundedGradeQueue queue;
    private final FineGrainedGradeList list;

    public WorkerAddThread(BoundedGradeQueue queue, FineGrainedGradeList list) {
        this.queue = queue;
        this.list = list;
    }

    @Override
    public void run() {
        try {
            while (true) {
                GradeRecord rec = queue.take();
                if (rec == null) {
                    // nu mai sunt date si nu mai sunt producatori
                    break;
                }
                list.addOrUpdate(rec.id, rec.grade);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
