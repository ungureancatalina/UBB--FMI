import pandas as pd
import numpy as np
import json

NOISE_THRESHOLD = 65
LIGHT_THRESHOLD = 30
MOTION_THRESHOLD = 1
SUS_THRESHOLD = 70

W_ACCESS = 0.4
W_EFF = 0.3
W_SAT = 0.3

def detect_situational_issues(row):
    issues = {}
    if row["Noise_dB"] > NOISE_THRESHOLD:
        issues["noise"] = f"High noise ({row['Noise_dB']} dB)"
    if row["Light_lux"] < LIGHT_THRESHOLD:
        issues["light"] = f"Low light ({row['Light_lux']} lux)"
    if row["Motion"] == MOTION_THRESHOLD:
        issues["motion"] = "User is moving"
    return issues

def detect_permanent_issues(row):
    issues = {}
    if row["Has_Visual_Disability"] == 1:
        issues["visual"] = "Visual disability"
    if row["Has_Motor_Disability"] == 1:
        issues["motor"] = "Motor disability"
    return issues

def classify_user_type(situational, permanent):
    if not situational and not permanent:
        return "No accessibility issues detected"
    if permanent and not situational:
        return "Permanent disability"
    if situational and not permanent:
        return "Situational impairment"
    return "Mixed impairment"

def compute_severity_score(row):
    score = 0
    if row["Noise_dB"] > NOISE_THRESHOLD:
        score += (row["Noise_dB"] - NOISE_THRESHOLD) * 0.6
    if row["Light_lux"] < LIGHT_THRESHOLD:
        score += (LIGHT_THRESHOLD - row["Light_lux"]) * 0.8
    if row["Motion"] == 1:
        score += 20
    if row["Has_Visual_Disability"] == 1:
        score += 40
    if row["Has_Motor_Disability"] == 1:
        score += 35
    return score

def severity_label(score):
    if score < 25:
        return "Low severity"
    if score < 60:
        return "Medium severity"
    return "High severity"

def compute_accessibility_score(row):
    A_access = 1 - (row["Errors_Standard"] / 10)
    A_eff = max(0, 1 - (row["Time_Standard"] - 120) / 100)
    A_sat = row["Satisfaction_Standard"] / 100
    A_total = W_ACCESS*A_access + W_EFF*A_eff + W_SAT*A_sat
    return round(A_total, 4)

def generate_adaptive_recommendations(situational, permanent):
    rec = []
    if "visual" in permanent:
        rec += ["Increase font size", "Enable high contrast", "Activate TTS", "Simplify layout"]
    if "motor" in permanent:
        rec += ["Increase button size", "Enable voice control", "Reduce precision interactions"]
    if "noise" in situational:
        rec += ["Disable voice input", "Enable text input", "Use visual alerts"]
    if "light" in situational:
        rec += ["Enable dark mode", "Increase contrast", "Use soft backgrounds"]
    if "motion" in situational:
        rec += ["Increase touch targets", "Reduce multi-step actions", "Use chunked layout"]
    return rec if rec else ["No adaptation needed"]

def run_amid_engine(input_csv):
    df = pd.read_csv(input_csv)
    results = []
    json_report = {}
    for _, row in df.iterrows():
        situational = detect_situational_issues(row)
        permanent = detect_permanent_issues(row)
        classification = classify_user_type(situational, permanent)
        severity_score = compute_severity_score(row)
        severity = severity_label(severity_score)
        a_total = compute_accessibility_score(row)
        recommendations = generate_adaptive_recommendations(situational, permanent)
        out = {
            "UserID": row["UserID"],
            "Classification": classification,
            "Situational_Issues": list(situational.values()),
            "Permanent_Issues": list(permanent.values()),
            "Severity": severity,
            "Severity_Score": severity_score,
            "A_total": a_total,
            "Recommended_Adaptations": recommendations
        }
        results.append(out)
        json_report[row["UserID"]] = out
    df_out = pd.DataFrame(results)
    df_out.to_csv("AMID_full_report.csv", index=False)
    with open("AMID_full_report.json", "w") as f:
        json.dump(json_report, f, indent=4)
    return df_out

if __name__ == "__main__":
    run_amid_engine("data.csv")
