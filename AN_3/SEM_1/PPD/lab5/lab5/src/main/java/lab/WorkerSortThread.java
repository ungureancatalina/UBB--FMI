package lab;

import java.util.LinkedList;

public class WorkerSortThread extends Thread {

    private final LinkedList<GradeRecord> workQueue; // noduri din lista nesortata
    private final FineGrainedSortedByTotalList sortedList;

    public WorkerSortThread(LinkedList<GradeRecord> workQueue,
                            FineGrainedSortedByTotalList sortedList) {
        this.workQueue = workQueue;
        this.sortedList = sortedList;
    }

    @Override
    public void run() {
        while (true) {
            GradeRecord rec;
            synchronized (workQueue) {
                if (workQueue.isEmpty()) {
                    break;
                }
                rec = workQueue.removeFirst();
            }
            sortedList.insertSorted(rec.id, rec.grade);
        }
    }
}
