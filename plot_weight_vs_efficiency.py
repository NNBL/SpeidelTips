import matplotlib.pyplot as pl
import numpy as np

from matplotlib import rcParams
rcParams.update({'figure.autolayout': True})

weightdata = np.genfromtxt("weight_vs_efficiency.csv",delimiter=",", names=True)
wfit = np.polyfit(weightdata["Grainweight_g"],weightdata["Efficiency_"],1)
wfit_fn = np.poly1d(wfit)

attdata = np.genfromtxt("mash_temp_vs_attenuation.csv",delimiter=",", names=True)
afit = np.polyfit(attdata["Mash_temperature_C"],attdata["Attenuation_"],1)
afit_fn = np.poly1d(afit)


pl.subplot(2,1,1)
pl.title("Braumeister efficiency")
pl.plot(weightdata["Grainweight_g"],weightdata["Efficiency_"],"yo",weightdata["Grainweight_g"],wfit_fn(weightdata["Grainweight_g"]),"--k")
pl.xlabel("Grain weight [g]")
pl.ylabel("Efficiency [%]")
pl.grid()

pl.subplot(2,1,2)
pl.title("Mash temp vs yeast attenuation")
pl.plot(attdata["Mash_temperature_C"],attdata["Attenuation_"],"yo",attdata["Mash_temperature_C"],afit_fn(attdata["Mash_temperature_C"]),"--k")
pl.scatter(attdata["Mash_temperature_C"],attdata["Attenuation_"], facecolor="red", edgecolor='black', s=75, alpha=0.75)
pl.xlabel("Temperature [C]")
pl.ylabel("Attenuation [%]")
pl.grid()
pl.show()