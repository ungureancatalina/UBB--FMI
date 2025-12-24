package lab;

import java.io.*;
import java.util.*;

import static lab.Paralel.runParallel;
import static lab.Secvential.runSequential;

public class Main {

    // Generator fisiere random
    private static String[] generateRandomInputFiles(int numStudents,
                                                     int numFiles,
                                                     int minNotesPerFile) throws IOException {
        Random r = new Random();

        String[] files = new String[numFiles];
        for (int f = 0; f < numFiles; f++) {
            String file = "proiect" + (f + 1) + ".txt";
            files[f] = file;

            try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {

                int notesCount = minNotesPerFile + r.nextInt(50); // 80–130 note

                for (int i = 0; i < notesCount; i++) {
                    int id = 1 + r.nextInt(numStudents); // 1..numStudents
                    int grade = r.nextInt(11);          // 0..10

                    pw.println("(" + id + ", " + grade + ")");
                }
            }

            //System.out.println("Creat fisier de intrare: " + file);
        }

        return files;
    }

    // Comparare fisiere rezultat
    static boolean compareFiles(String f1, String f2) throws IOException {
        try (BufferedReader br1 = new BufferedReader(new FileReader(f1));
             BufferedReader br2 = new BufferedReader(new FileReader(f2))) {

            String line1, line2;
            while (true) {
                line1 = br1.readLine();
                line2 = br2.readLine();

                if (line1 == null && line2 == null) return true;   // ambele s-au terminat
                if (line1 == null || line2 == null) return false;  // lungimi diferite

                if (!line1.trim().equals(line2.trim())) return false;
            }
        }
    }

    private static void writeToFile(String filename, String content) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println(content);
        }
    }

    public static void main(String[] args) throws Exception {
        // Generam date random pentru 200 studenti in 10 fisiere
        int numStudents = 200;
        int numFiles = 10;
        int minNotesPerFile = 80;

        System.out.println("=== Generare fisiere de intrare random ===");
        String[] files = generateRandomInputFiles(numStudents, numFiles, minNotesPerFile);
        System.out.println();

        // Rulare secventiala
        System.out.println("=== Rulare secventiala ===");
        long tSeq = runSequential(files, "rezultate_secv.txt"); // fisierul de referinta
        System.out.println("Timp secvential (ns): " + tSeq);
        writeToFile("time_secvential.txt", "" + tSeq);
        System.out.println();

        // Rulari paralele pentru diverse configuratii
        System.out.println("=== Rulari paralele ===");
        int[] ps = {4, 8, 16};
        int[] prs = {1, 2};

        for (int p : ps) {
            for (int pr : prs) {

                long sum = 0;

                System.out.println("\nConfiguratie p=" + p + ", p_r=" + pr);

                for (int rep = 1; rep <= 10; rep++) {
                    String out = "rezultate.txt";
                    String timeFile = "rezultate_timp.txt";
                    String checkFile = "rezultate_check.txt";

                    long tPar = runParallel(files, p, pr, out);
                    sum += tPar;

                    // scriem timpul in fisier separat
                    writeToFile(timeFile, "" + tPar);

                    // verificam corectitudinea
                    boolean ok = compareFiles("rezultate_secv.txt", out);
                    writeToFile(checkFile, ok ? "OK" : "ERROR");

//                    System.out.println("  Rulare " + rep +
//                            " -> Tpar = " + tPar +
//                            " ns | corectitudine: " + (ok ? "OK" : "ERROR"));
                }

                long avg = sum / 10;
                double speedup = (double) tSeq / (double) avg;

                System.out.println("MEDIA timpilor pentru p=" + p + " p_r=" + pr + ": " + avg + " ns");
                System.out.println("Speedup mediu Tseq/Tpar = " + speedup);

                // salvam media si speedup-ul
                writeToFile("rezultate.txt",
                        "AVG=" + avg + "\nSPEEDUP=" + speedup);
            }
        }

        System.out.println();
    }
}
