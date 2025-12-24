package lab;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ThreadSafeGradeList {

    private static class Node {
        int id;
        int total;   // suma notelor pentru acest student
        Node next;

        Node(int id, int total) {
            this.id = id;
            this.total = total;
        }
    }

    private Node head = null;

    // se blocheaza pe toata lista
    public synchronized void addOrUpdate(int id, int grade) {
        Node prev = null;
        Node current = head;

        //cauta daca exista studentul
        while (current != null && current.id != id) {
            prev = current;
            current = current.next;
        }

        if (current != null) {
            // exista deja -> adunam nota
            current.total += grade;
        } else {
            // nu exista -> adaugam nod nou la inceput
            Node n = new Node(id, grade);
            n.next = head;
            head = n;
        }
    }

    // scrierea in fisier se face tot sincronizat
    public synchronized void writeToFile(String filename) throws IOException {
        List<Node> items = new ArrayList<>();
        Node current = head;

        // colectam nodurile intr-o lista
        while (current != null) {
            items.add(current);
            current = current.next;
        }

        // sortare dupa ID pentru afisare
        items.sort(Comparator.comparingInt(n -> n.id));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Node n : items) {
                bw.write(n.id + " " + n.total);
                bw.newLine();
            }
        }
    }
}
