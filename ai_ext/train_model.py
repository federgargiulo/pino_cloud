# model/train_model.py
import numpy as np, pandas as pd, joblib
from sklearn.ensemble import RandomForestClassifier

def simulate_motor_data(n_windows=1000, freq_hz=10, duration_s=2, seed=42):
    np.random.seed(seed)
    data, labels = [], []
    for _ in range(n_windows):
        base_v = np.random.normal(400, 5)
        base_i = np.random.normal(10, 2)
        base_t = np.random.normal(60, 5)
        base_s = np.random.normal(1500, 100)
        base_w = np.random.normal(3, 0.5)
        window = []
        for __ in range(freq_hz * duration_s):
            window.append({
                'voltage_V_L1': base_v + np.random.normal(0,1),
                'voltage_V_L2': base_v + np.random.normal(0,1),
                'voltage_V_L3': base_v + np.random.normal(0,1),
                'current_A_L1': base_i + np.random.normal(0,0.5),
                'current_A_L2': base_i + np.random.normal(0,0.5),
                'current_A_L3': base_i + np.random.normal(0,0.5),
                'vibration_rms_mm_s': base_w + np.random.normal(0,0.2),
                'stator_temp_C': base_t + np.random.normal(0,1),
                'speed_rpm': base_s + np.random.normal(0,20),
            })
        df = pd.DataFrame(window)
        data.append(df.mean().to_dict())
        labels.append(int(base_w >= 3.5))
    return pd.DataFrame(data), labels

if __name__ == "__main__":
    print("Training RandomForest on synthetic motor data…")
    X, y = simulate_motor_data()
    clf = RandomForestClassifier(n_estimators=100, random_state=42)
    clf.fit(X, y)
    joblib.dump(clf, "rf_motor.pkl")
    print("Saved rf_motor.pkl")
