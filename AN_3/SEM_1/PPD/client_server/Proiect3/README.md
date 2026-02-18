# Sistem Client-Server Concurent pentru Rezervări Ateliere


## 1. Descriere

Această aplicație simulează un sistem de rezervări pentru un lanț de
centre culturale. Sistemul este compus dintr-un Server multithreaded
și un Client care simulează comportamentul concurent al utilizatorilor.

**Funcționalități cheie:**
- Procesare asincronă a cererilor (rezervare, plată, anulare) folosind
  Future și CompletableFuture.
- Execuție concurentă pe Server folosind un Thread Pool fix.
- Persistența datelor în fișiere text (append-only).
- Auditor Periodic (Thread separat) care verifică consistența datelor
  și expiră rezervările neplătite, folosind mecanisme stricte de
  sincronizare (ReadWriteLock).

## 2. Structura Proiectului

```
src/
├── model/
│    ├── Reservation.java
│    ├── Transaction.java
│    └── Workshop.java
├── server/            
│    ├── ServerApp.java       
│    ├── DataManager.java     
│    └── PeriodicAuditor.java 
└── client/           
│    └── ClientSimulator.java 
```


## 3. Cerințe & Compilare

- Java JDK: JDK 21
- IDE: IntelliJ IDEA 

Compilare din linia de comandă (din folderul src):
```
javac common/*.java server/*.java client/*.java
```

## 4. Instrucțiuni de Rulare

Aplicația necesită rularea a două procese simultane.

Pasul 1: Pornirea Serverului
--------------------------------------------------

Rulează clasa server.ServerApp.
```
java server.ServerApp
```

Serverul va afișa: "Server pornit pe portul 8888".
Acesta va crea/inițializa fișierele: rezervari.txt, tranzactii.txt.

Pasul 2: Pornirea Clientului
--------------------------------------------------
Într-un terminal nou (sau fereastră separată), rulează client.ClientSimulator.
```
java client.ClientSimulator
```

Clientul va porni 20 de thread-uri care vor trimite cereri timp de
120 de secunde. La final, procesul client se oprește automat.


## 5. Configurare Parametri
Parametrii principali se găsesc în clasa `server.ServerApp`:

1. Interval Verificare Auditor:
   private static final int CHECK_INTERVAL = 5;
   (Se modifică în 10 pentru al doilea scenariu de test).

2. Timp Expirare Plată:
   private static final long PAYMENT_TIMEOUT_MS = 15000; (15 secunde)

3. Dimensiune Thread Pool:
   private static final int THREAD_POOL_SIZE = 8;


## 6. Fișiere de Log

1. rezervari.txt      -> Istoricul tuturor rezervărilor și starea lor.
2. tranzactii.txt     -> Istoricul financiar (încasări/rambursări).
3. raport_auditor.txt -> Raport generat periodic care confirmă:
   - Soldul total așteptat vs. real.
   - Rezervările expirate automat.
   - Erori de suprapunere (dacă există).


## 7. Detalii Implementare Concurentă

- **Thread Pool**: Folosit pentru a decupla acceptarea conexiunii de
  procesarea efectivă.
- **CompletableFuture**: Permite execuția asincronă a logicii de business.
- **ReentrantReadWriteLock**:
    - WriteLock este folosit de Auditor pentru a obține o stare consistentă
      (snapshot isolation) în timpul verificărilor.
    - WriteLock este folosit la modificarea stării (rezervare/plată).
    - Acest mecanism previne "Race Conditions" și inconsistențe financiare.