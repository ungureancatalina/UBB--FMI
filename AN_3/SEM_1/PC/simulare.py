import pandas as pd
import matplotlib.pyplot as plt

# Citire fișier
df = pd.read_csv(
    "input_data_experiment.csv",
    sep=";",
    header=None,
    encoding="utf-8"
)

# Sar prima linie dacă e header în fișierul original
df = df.iloc[1:].reset_index(drop=True)

# Nume coloane
df.columns = ["User","Context","Device","Time_Standard","Errors_Standard","Satisfaction_Standard"]

# Conversie la numeric pentru coloanele măsurabile
df["Time_Standard"] = pd.to_numeric(df["Time_Standard"])
df["Errors_Standard"] = pd.to_numeric(df["Errors_Standard"])
df["Satisfaction_Standard"] = pd.to_numeric(df["Satisfaction_Standard"])

print(df.head())
print("Număr rânduri:", len(df))

# Tipuri utilizatori (dizabilități)
user_types = (
    ["Dizabilitate vizuală ușoară"] * 5 +
    ["Dizabilitate motorie minoră"] * 5 +
    ["Dizabilitate situațională"] * 10
)
df["User_Type"] = user_types

# Histogramă pentru câți au fiecare tip de dizabilitate
plt.figure(figsize=(7,5))
df["User_Type"].value_counts().plot(kind="barh")
plt.title("Distribuția utilizatorilor pe tipuri de dizabilitate")
plt.xlabel("Număr utilizatori")
plt.ylabel("Tip dizabilitate")
plt.tight_layout()
plt.show()

# Histogramă pentru tipuri de context 
plt.figure(figsize=(7,5))
df["Context"].value_counts().plot(kind="bar")
plt.title("Distribuția tipurilor de context")
plt.xlabel("Context")
plt.ylabel("Număr utilizatori")
plt.tight_layout()
plt.show()

# Histogramă pentru tipuri de dispozitive folosite
plt.figure(figsize=(7,5))
df["Device"].value_counts().plot(kind="bar")
plt.title("Distribuția dispozitivelor folosite")
plt.xlabel("Dispozitiv")
plt.ylabel("Număr utilizatori")
plt.tight_layout()
plt.show()

# Histogramă pentru erorile standard
plt.figure(figsize=(7,5))
plt.hist(df["Errors_Standard"], bins=8, edgecolor="black")
plt.title("Distribuția erorilor (Standard)")
plt.xlabel("Timp (secunde)")
plt.ylabel("Număr utilizatori")
plt.tight_layout()
plt.show()

# Histogramă pentru satisfactia standard
plt.figure(figsize=(7,5))
plt.hist(df["Satisfaction_Standard"], bins=8, edgecolor="black")
plt.title("Distribuția satisfactiei(Standard)")
plt.xlabel("Timp (secunde)")
plt.ylabel("Număr utilizatori")
plt.tight_layout()
plt.show()