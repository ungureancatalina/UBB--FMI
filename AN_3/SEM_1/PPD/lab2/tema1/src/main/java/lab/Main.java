package lab;

import java.io.*;
import java.util.*;

// Scrie simultan in consola si in fisier
class TeePrintStream extends PrintStream {
    private final PrintStream second;

    public TeePrintStream(OutputStream main, OutputStream second) {
        super(main, true);
        this.second = new PrintStream(second, true);
    }

    @Override
    public void write(byte[] buf, int off, int len) {
        super.write(buf, off, len);
        second.write(buf, off, len);
    }

    @Override
    public void flush() {
        super.flush();
        second.flush();
    }
}


public class Main {

    private static int N, M, n, p;
    private static int[][] matrix;
    private static int[][] convolutionMatrix;

    // Citeste datele din fisier
    private static void readValues(Scanner data) {
        N = data.nextInt();
        M = data.nextInt();
        n = data.nextInt();
        p = data.nextInt();
    }

    private static void readOriginalMatrix(Scanner data) {
        matrix = new int[N][M];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                matrix[i][j] = data.nextInt();
    }

    private static void readConvolutionMatrix(Scanner data) {
        convolutionMatrix = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                convolutionMatrix[i][j] = data.nextInt();
    }

    // Scrie matricea rezultat in fisier
    private static void writeToFile(int[][] resultMatrix, String method) {
        try (FileWriter writer = new FileWriter("output_" + method + ".txt")) {
            for (int[] row : resultMatrix) {
                for (int val : row)
                    writer.write(val + " ");
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Genereaza date.txt cu valori random
    private static void generateFile(int N, int M, int n, int p) {
        Random random = new Random();
        try (FileWriter writer = new FileWriter("date.txt")) {
            writer.write(N + " " + M + " " + n + " " + p + "\n");

            // matricea originala
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++)
                    writer.write((random.nextInt(21) + 40) + " ");
                writer.write("\n");
            }

            // matricea de convolutie
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++)
                    writer.write(random.nextInt(2) + " ");
                writer.write("\n");
            }

            System.out.println("Fisierul date.txt a fost generat cu succes!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateFileIfNeeded(int N, int M, int n, int p) {
        File f = new File("date.txt");
        if (f.exists()) {
            try (Scanner sc = new Scanner(f)) {
                int oldN = sc.nextInt();
                int oldM = sc.nextInt();
                int oldn = sc.nextInt();
                sc.close();
                if (oldN == N && oldM == M && oldn == n) {
                    System.out.println("Fisierul date.txt exista deja si dimensiunile coincid, nu se genereaza din nou.");
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        generateFile(N, M, n, p);
    }


    // Adauga bordura pentru convolutie
    private static int[][] borderMatrix() {
        int border = n / 2;
        int[][] bordered = new int[N + 2 * border][M + 2 * border];

        //copiaza matricea originala în centru
        for (int i = 0; i < N; i++)
            for (int j = 0; j < M; j++)
                bordered[i + border][j + border] = matrix[i][j];

        // bordare sus-jos
        for (int i = 0; i < border; i++) {
            bordered[i] = Arrays.copyOf(bordered[border], bordered[border].length);
            bordered[N + border + i] = Arrays.copyOf(bordered[N + border - 1], bordered[N + border - 1].length);
        }

        // bordare stanga-dreapta
        for (int i = 0; i < bordered.length; i++) {
            for (int j = 0; j < border; j++) {
                bordered[i][j] = bordered[i][border];
                bordered[i][M + border + j] = bordered[i][M + border - 1];
            }
        }

        return bordered;
    }

    // Compara doua fisiere (rezultate)
    private static boolean compareFiles(String f1, String f2) throws IOException {
        try (Scanner s1 = new Scanner(new File(f1));
             Scanner s2 = new Scanner(new File(f2))) {
            while (s1.hasNextInt() && s2.hasNextInt()) {
                if (s1.nextInt() != s2.nextInt()) return false;
            }
            return !s1.hasNextInt() && !s2.hasNextInt();
        }
    }

    //metoda de copiere
    private static int[][] deepCopy(int[][] src) {
        int[][] copy = new int[src.length][src[0].length];
        for (int i = 0; i < src.length; i++)
            System.arraycopy(src[i], 0, copy[i], 0, src[i].length);
        return copy;
    }


    private static void testAll(int[][] matrix) throws Exception {

        long[] seqTimes = new long[10];
        long[] horTimes = new long[10];

        for (int run = 1; run <= 10; run++) {
            //System.out.println("\nRulare #" + run);

            // SECVENTIAL
            int[][] copySeq = deepCopy(matrix);
            long start = System.nanoTime();
            Sequential seq = new Sequential(N, M, n, copySeq, convolutionMatrix);
            seq.run();
            long end = System.nanoTime();
            seqTimes[run - 1] = end - start;
            writeToFile(copySeq, "sequential");

            // ORIZONTAL
            int[][] copyHor = deepCopy(matrix);
            start = System.nanoTime();
            HorizontalAllocatedThreads hor = new HorizontalAllocatedThreads(N, M, n, p, copyHor, convolutionMatrix);
            hor.run();
            end = System.nanoTime();
            horTimes[run - 1] = end - start;
            writeToFile(copyHor, "horizontal");

        }

        // Calcul medii
        long seqAvg = Arrays.stream(seqTimes).sum() / 10;
        long horAvg = Arrays.stream(horTimes).sum() / 10;

        System.out.printf("N=%d M=%d n=m=%d%n", N, M, n);
        System.out.printf("Secvential: %d ns%n", seqAvg);
        System.out.printf("%d - orizontal (randuri): %d ns%n", p, horAvg);
    }

    public static void main(String[] args) throws Exception {

        PrintStream fileOut = new PrintStream(new FileOutputStream("rezultate.txt", true));
        System.setOut(new TeePrintStream(fileOut, System.out));

        //Genereaza fisierul doar daca nu exista sau dimensiunile s-au schimbat
        generateFileIfNeeded(10000 , 10000 , 3, 2);

        //Citeste fisierul
        Scanner data = new Scanner(new File("date.txt"));
        readValues(data);
        readOriginalMatrix(data);
        readConvolutionMatrix(data);

        //Testare automata
        int[] threadCounts = {2, 4, 8, 16};
        for (int t : threadCounts) {
            p = t;  // setează nr de thread-uri
            testAll(matrix);
        }

        fileOut.close();
    }
}
