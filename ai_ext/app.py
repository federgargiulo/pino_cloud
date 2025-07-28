from fastapi import FastAPI
import uvicorn
from pydantic import BaseModel
from typing import List
import joblib
import pandas as pd


app = FastAPI(title="Motor Diagnostic Service")
model = joblib.load("rf_motor.pkl")

class Sample(BaseModel):
    timestamp: str
    voltage_V_L1: float
    voltage_V_L2: float
    voltage_V_L3: float
    current_A_L1: float
    current_A_L2: float
    current_A_L3: float
    vibration_rms_mm_s: float
    stator_temp_C: float
    speed_rpm: float

class DiagnoseRequest(BaseModel):
    samples: List[Sample]

class DiagnoseResponse(BaseModel):
    statusCode: str
    statusDescription: str

@app.get("/status")
def status():
    return {"message": "Up and running!"}


@app.post("/diagnose", response_model=DiagnoseResponse)
def diagnose(request: DiagnoseRequest):
    df = pd.DataFrame([s.dict() for s in request.samples])
    x = df.drop(columns=["timestamp"]).mean().to_frame().T
    pred = model.predict(x)[0]
    return {
        "statusCode": str(pred),
        "statusDescription": "FAULTY" if pred else "HEALTHY"
    }

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=5000)