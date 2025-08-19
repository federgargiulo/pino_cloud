from sklearn.tree import DecisionTreeClassifier
from sklearn.datasets import load_iris
import joblib

X, y = load_iris(return_X_y=True)
clf = DecisionTreeClassifier()
clf.fit(X, y)

joblib.dump(clf, "rf_motor.pkl")