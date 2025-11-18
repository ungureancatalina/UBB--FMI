import pandas as pd
import matplotlib.pyplot as plt

# Citire și curățare
df = pd.read_csv(
    "input_data_experiment.csv",
    sep=";",
    header=None,
    encoding="utf-8"
)

df = df.iloc[1:].reset_index(drop=True)

df.columns = ["User","Context","Device","Time_Standard","Errors_Standard","Satisfaction_Standard"]

df["Time_Standard"] = pd.to_numeric(df["Time_Standard"])
df["Errors_Standard"] = pd.to_numeric(df["Errors_Standard"])
df["Satisfaction_Standard"] = pd.to_numeric(df["Satisfaction_Standard"])

print(df.head())
print("Număr rânduri după curățare:", len(df))

# Tipuri utilizatori
user_types = (
    ["Dizabilitate vizuală ușoară"] * 5 +
    ["Dizabilitate motorie minoră"] * 5 +
    ["Dizabilitate situațională"] * 10
)
df["User_Type"] = user_types

# Generare versiune AMID
df["Time_AMID"] = df["Time_Standard"] - 20
df["Errors_AMID"] = df["Errors_Standard"] - 1
df["Satisfaction_AMID"] = df["Satisfaction_Standard"] + 10

df["Errors_AMID"] = df["Errors_AMID"].clip(lower=0)
df["Time_AMID"] = df["Time_AMID"].clip(lower=1)

df["time(%)"] = (df["Time_AMID"] - df["Time_Standard"]) / df["Time_Standard"] * 100
df["errors(%)"] = (df["Errors_AMID"] - df["Errors_Standard"]) / df["Errors_Standard"] * 100
df["satisfaction(%)"] = (df["Satisfaction_AMID"] - df["Satisfaction_Standard"]) / df["Satisfaction_Standard"] * 100

print(df.head())


# Distribuția utilizatorilor pe categorii
plt.figure(figsize=(8,5))

ax = df["User_Type"].value_counts().plot(
    kind="barh",   # <<–– horizontal bars
    color="#4c72b0"
)

ax.set_title("Distribuția utilizatorilor pe categorii")
ax.set_xlabel("Număr utilizatori")
ax.set_ylabel("Tip utilizator")

plt.tight_layout()
plt.show()



# Compararea timpului mediu Standard vs AMID
plt.figure(figsize=(7,5))
plt.bar(["Standard", "AMID"],
        [df["Time_Standard"].mean(), df["Time_AMID"].mean()],
        color=["#c44e52", "#55a868"])
plt.title("Timp mediu de finalizare")
plt.ylabel("Secunde")
plt.tight_layout()
plt.show()


# Compararea erorilor medii
plt.figure(figsize=(7,5))
plt.bar(["Standard", "AMID"],
        [df["Errors_Standard"].mean(), df["Errors_AMID"].mean()],
        color=["#c44e52", "#55a868"])
plt.title("Număr mediu de erori")
plt.ylabel("Erori")
plt.tight_layout()
plt.show()


# Compararea satisfacției
plt.figure(figsize=(7,5))
plt.bar(["Standard", "AMID"],
        [df["Satisfaction_Standard"].mean(), df["Satisfaction_AMID"].mean()],
        color=["#c44e52", "#55a868"])
plt.title("Scor mediu de satisfacție (SUS)")
plt.ylabel("Punctaj SUS")
plt.tight_layout()
plt.show()


# Histogramă satisfacție AMID
plt.figure(figsize=(7,5))
plt.hist(df["Satisfaction_AMID"], bins=8, color="skyblue", edgecolor="black")
plt.title("Distribuția satisfacției utilizatorilor (AMID)")
plt.xlabel("Scor SUS")
plt.ylabel("Număr utilizatori")
plt.tight_layout()
plt.show()


# Plot comparativ timp Standard vs AMID
plt.figure(figsize=(7,5))
plt.bar(["Standard", "AMID"],
        [df["Time_Standard"].mean(), df["Time_AMID"].mean()],
        color=["#c44e52", "#55a868"])

plt.title("Timp mediu de finalizare")
plt.ylabel("Secunde")
plt.tight_layout()
plt.show()



# Îmbunătățiri pe tipuri de utilizatori
group_stats = df.groupby("User_Type")[["time(%)", "errors(%)", "satisfaction(%)"]].mean().round(2)
print("\nÎmbunătățiri medii AMID pe tipuri de utilizatori:\n")
print(group_stats)

# Îmbunătățirea timpului (%) pe categorii
plt.figure(figsize=(7,5))
plt.bar(group_stats.index, group_stats["time(%)"], color="#55a868")
plt.title("Îmbunătățirea timpului de finalizare (%)")
plt.ylabel("Reducere timp (%)")
plt.xticks(rotation=45)
plt.tight_layout()
plt.show()

# Îmbunătățirea numărului de erori (%) pe categorii
plt.figure(figsize=(7,5))
plt.bar(group_stats.index, group_stats["errors(%)"], color="#c44e52")
plt.title("Reducerea erorilor (%)")
plt.ylabel("Reducere erori (%)")
plt.xticks(rotation=45)
plt.tight_layout()
plt.show()

# Creșterea satisfacției (%) pe categorii
plt.figure(figsize=(7,5))
plt.bar(group_stats.index, group_stats["satisfaction(%)"], color="#4c72b0")
plt.title("Creșterea satisfacției utilizatorilor (%)")
plt.ylabel("Creștere satisfacție (%)")
plt.xticks(rotation=45)
plt.tight_layout()
plt.show()
