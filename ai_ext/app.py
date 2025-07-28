from fastapi import FastAPI
import uvicorn

app = FastAPI()

@app.get("/status")
def status():
    return {"message": "Up and running!"}

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=5000)