package sablon;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.min;

public class Main
{
    public static class Cyclic extends Thread
    {
        private int id, p, n;
        private int[] A, B, C;
        public Cyclic(int id, int p, int n, int[] A, int[] B, int[] C)
        {
            this.id = id;
            this.p = p;
            this.n = n;
            this.A = A;
            this.B = B;
            this.C = C;
        }

        public void run()
        {
            for (int i = id; i < n; i+=p)
            {
                C[i] = A[i] + B[i];
            }
        }
    }

    public static class Block extends Thread
    {
        private int start, end;
        private int[] A, B, C;

        public Block(int start, int end, int[] A, int[] B, int[] C)
        {
            this.start = start;
            this.end = end;
            this.A = A;
            this.B = B;
            this.C = C;
        }
        public void run()
        {
            for (int i = start; i < end; i++)
            {
                C[i] = A[i] + B[i];
            }
        }
    }

    public static int[] generator(int n,int max)
    {
        int[] v = new int[n];
        Random random = new Random();
        for(int i = 0; i < n; i++)
        {
            v[i] = random.nextInt(max);
        }
        return v;
    }

    static long sequential(int[] A, int[] B, int[] C)
    {
        long t0 = System.nanoTime();
        for(int i = 0; i < A.length; i++)
        {
            C[i] = A[i] + B[i];
        }
        long t1 = System.nanoTime();
        return t1 - t0;
    }

    static long runCyclic(int[] A, int[] B, int[] C, int p) throws InterruptedException
    {
        Cyclic[] threads = new Cyclic[p];
        long t0 = System.nanoTime();
        for(int id=0; id < p; id++)
        {
            threads[id] = new Cyclic(id,p,A.length,A,B,C);
            threads[id].start();
        }
        for(int id=0; id < p; id++)
        {
            threads[id].join();
        }
        long t1 = System.nanoTime();
        return t1 - t0;
    }

    static long runBlock(int[] A, int[] B, int[] C, int p) throws InterruptedException
    {
        Block[] threads = new Block[p];
        int n = A.length;
        long t0 = System.nanoTime();
        for(int id=0; id < p; id++)
        {
            int start = id * n / p;
            int end = min((id+1)*n/p , n);
            threads[id] = new Block(start,end,A,B,C);
            threads[id].start();
        }
        for(int id=0; id < p; id++)
        {
            threads[id].join();
        }

        long t1 = System.nanoTime();
        return t1 - t0;
    }

    public static void main(String[] args) throws InterruptedException
    {
        int n = 1_000_000;
        int max = 50_000;
        int p = 3;

        int[] A = generator(n, max);
        int[] B = generator(n, max);

        //Secvential
        int[] C1 = new int[n];
        long tSec = sequential(A,B,C1);
        System.out.println("Secvential: "+tSec);

        //Ciclic
        int[] C2 = new int[n];
        long tCyc = runCyclic(A,B,C2,p);
        System.out.println("Cyclic: "+tCyc);

        //Blocuri
        int[] C3 = new int[n];
        long tBlock = runBlock(A,B,C3,p);
        System.out.println("Block: "+tBlock);

        //Verificat corectitudine
        System.out.println("C1 == C2? "+ Arrays.equals(C1,C2));
        System.out.println("C1 == C3? "+ Arrays.equals(C1,C3));
    }
}