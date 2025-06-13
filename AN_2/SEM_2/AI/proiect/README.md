# Estimarea Riscului de Peri-implantită cu Ajutorul Inteligenței Artificiale

## Scop

Acest proiect urmărește dezvoltarea unui sistem bazat pe inteligență artificială (AI) care să evalueze riscul de apariție a peri-implantitei pe baza literaturii științifice disponibile. Scopul final este construirea unui **chestionar clinic validat**, care să sprijine luarea deciziilor în implantologia orală, prin identificarea timpurie a factorilor de risc.

## Ideea de bază

Dându-se o colecție de documente text (articole, studii clinice, manuale, ghiduri etc.), se identifică automat **factorii asociați cu eșecul tratamentului implantar**.

Acești factori sunt apoi evaluați prin:
- **Frecvență**: în câte surse apare factorul.
- **Intensitate**: cât de puternic este asociat cu riscul (dacă e cuantificat statistic sau clinic).

Pe baza acestor scoruri, se:
- Construiește un **chestionar clinic**.
- Se dezvoltă un **model AI** de evaluare automată a riscului.
- Se validează sistemul pe **cazuri clinice reale**.


## Surse de date

### 1. Studiu românesc în două clinici din Cluj-Napoca
- **Titlu**: *Prevalence of Peri-Implant Diseases and Risk Indicators in Two Dental Clinics in Cluj-Napoca, Romania: A Cross-Sectional Study*
- **Publicație**: *Dentistry Journal (MDPI, 2023)*
- ** Informații extrase**:
  - Prevalența peri-implantitei în funcție de vârstă, sex, fumat și diabet
  - Distribuția simptomelor și severitatea bolii

### 2. Studiu internațional cu date comparative
- **Titlu**: *Development of a Machine Learning Model for Predicting Peri-Implantitis Based on Clinical Parameters*
- **Publicație**: *Scientific Reports / Nature (2021)*
- ** Informații extrase**:
  - Factori de risc principali: fumat, diabet, durata implantului
  - Seturi de date structurate pentru antrenarea AI
  - Proporții pacienți afectați vs. sănătoși

### 3. Literatură de specialitate
- **Jan Lindhe** – *Clinical Periodontology and Implant Dentistry*
- **Berglundh et al. (2018)** – *Peri-implant diseases and conditions* (Consensus report, EFP)
- ** Informații extrase**:
  - Definiții clinice standardizate
  - Simptome și clasificări
  - Protocoale terapeutice

### 4. Baze de date publice (opționale pentru AI)
- **European Genome-Phenome Archive (EGA)** – date moleculare ale pacienților
- **PubMed Central (PMC)** – articole full-text pentru extracția de cazuri clinice


## Flux de dezvoltare

### 1. Definirea problemei
- **Input**: Documente text (articole, ghiduri, studii).
- **Output**: Evaluarea riscului de peri-implantită.
- **Motivație AI**: Volumul mare de literatură și complexitatea interpretării clinice.

### 2. Analiza datelor
- Tipuri: text medical (PDF, .txt, .xml extrase)
- Proveniență: studii, ghiduri, baze de date
- Preprocesare: curățare, segmentare, tokenizare, NER (Named Entity Recognition)

### 3. Mini-aplicație software
- Primește date structurate (simptome, factori)
- Returnează scoruri hard-coded (versiunea inițială)
- Pregătită pentru integrarea ulterioară a modelului AI

### 4. Model AI
- **Arhitectură**: BERT sau BioBERT pentru NLP medical
- **Funcții**:
  - Extracția automată a factorilor de risc
  - Calcularea scorului de risc pentru un caz dat
- **Evaluare**: F1-score, accuracy, recall


## TODO List

- Definirea clară a problemei și obiectivelor
- Colectarea și preprocesarea datelor din sursele menționate
- Construirea unei aplicații demo cu scoruri hard-coded
- Antrenarea unui model AI pe date adnotate
- Integrarea modelului în aplicație
- Validarea sistemului pe cazuri reale
- Generarea chestionarului clinic validat


## Link video
https://www.loom.com/share/9fcff7c073444a29a69b4890f35273f9
