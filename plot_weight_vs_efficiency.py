import matplotlib.pyplot as pl
import numpy as np

data = np.genfromtxt("weight_vs_efficiency.csv",delimiter=",", names=True)

pl.scatter(data["Grainweight_g"],data["Efficiency_"])
pl.xlabel("Grain weight [g]")
pl.ylabel("Efficiency [%]")
pl.grid()
pl.title("20L Braumeister efficiency, NNBL 2014")
pl.show()