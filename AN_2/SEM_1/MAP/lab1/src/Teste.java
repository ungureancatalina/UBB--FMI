public class Teste {

    public static void main(String[] args) {
        testAdunare();
        testScadere();
        testInmultire();
        testImpartire();
        testConjugat();
        testToString();
        testValidare();
    }

    public static void testAdunare() {
        NumarComplex nr1 = new NumarComplex(3, 4);
        NumarComplex nr2 = new NumarComplex(1, 2);
        NumarComplex rezultat = nr1.adunare(nr2);

        if (!(rezultat.getReal() == 4.0 && rezultat.getImag() == 6.0)) {
            System.out.println("Test adunare: FAILED");
        }
    }

    public static void testScadere() {
        NumarComplex nr1 = new NumarComplex(5, 3);
        NumarComplex nr2 = new NumarComplex(2, 1);
        NumarComplex rezultat = nr1.scadere(nr2);

        if (!(rezultat.getReal() == 3.0 && rezultat.getImag() == 2.0)) {
            System.out.println("Test scadere: FAILED");
        }
    }

    public static void testInmultire() {
        NumarComplex nr1 = new NumarComplex(3, 2);
        NumarComplex nr2 = new NumarComplex(4, 5);
        NumarComplex rezultat = nr1.inmultire(nr2);

        if (!(rezultat.getReal() == 2.0 && rezultat.getImag() == 25.0)) {
            System.out.println("Test inmultire: FAILED");
        }
    }

    public static void testImpartire() {
        NumarComplex nr1 = new NumarComplex(4, 2);
        NumarComplex nr2 = new NumarComplex(3, -1);
        NumarComplex rezultat = nr1.impartire(nr2);

        if (!(rezultat.getReal() == 1.0 && rezultat.getImag() == -0.6)) {
            System.out.println("Test impartire: FAILED");
        }
    }

    public static void testConjugat() {
        NumarComplex nr1 = new NumarComplex(3, 4);
        NumarComplex rezultat = nr1.conjugat(nr1);

        if (!(rezultat.getReal() == 3.0 && rezultat.getImag() == -4.0)) {
            System.out.println("Test conjugat: FAILED");
        }
    }

    public static void testToString() {
        NumarComplex nr1 = new NumarComplex(3, 4);
        NumarComplex nr2 = new NumarComplex(5, -3);
        NumarComplex nr3 = new NumarComplex(1, 0);

        if (!nr1.toString().equals("3.0 + 4.0*i")) {
            System.out.println("Test toString nr1: FAILED");
        }

        if (!nr2.toString().equals("5.0 -3.0*i")) {
            System.out.println("Test toString nr2: FAILED");
        }

        if (!nr3.toString().equals("1.0 ")) {
            System.out.println("Test toString nr3: FAILED");
        }
    }

    public static void testValidare() {
        Parser parser = new Parser();
        String[] expresiiValide = {"3+4*i", "5-2*i", "+2*i", "2", "-1+2*i"};
        for (String exp : expresiiValide) {
            String[] tokens = exp.split(" ");
            if (!parser.eValid(tokens)) {
                System.out.println("Test validare: FAILED for " + exp);
            }
        }

        String[] expresiiInvalide = {"3+*i", "5-2", "2i", "+3+*i", "4*", "3+2*"};
        for (String exp : expresiiInvalide) {
            String[] tokens = exp.split(" ");
            if (parser.eValid(tokens)) {
                System.out.println("Test validare: FAILED for " + exp);
            }
        }
    }
}
