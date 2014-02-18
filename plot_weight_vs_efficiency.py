import matplotlib.pyplot as pl
import numpy as np

from matplotlib import rcParams
rcParams.update({'figure.autolayout': True})

weightdata = np.genfromtxt("weight_vs_efficiency.csv",delimiter=",", names=True)
attdata = np.genfromtxt("mash_temp_vs_attenuation.csv",delimiter=",", names=True)

pl.subplot(2,1,1)
pl.title("Braumeister efficiency")
pl.scatter(weightdata["Grainweight_g"],weightdata["Efficiency_"], facecolor="blue", edgecolor='black', s=75, alpha=0.75, marker=(5,0))
pl.xlabel("Grain weight [g]")
pl.ylabel("Efficiency [%]")
pl.grid()
pl.subplot(2,1,2)
pl.title("Mash temp vs yeast attenuation")
pl.scatter(attdata["Mash_temperature_C"],attdata["Attenuation_"], facecolor="red", edgecolor='black', s=75, alpha=0.75, marker=(5,0))
pl.xlabel("Temperature [C]")
pl.ylabel("Attenuation [%]")
pl.grid()
pl.show()