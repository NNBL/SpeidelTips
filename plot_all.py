import matplotlib.pyplot as pl
import numpy as np

from matplotlib import rcParams
rcParams.update({'figure.autolayout': True})

weightdata = np.genfromtxt("weight_vs_efficiency.csv", delimiter=",", names=True)

wx = weightdata["Grainweight_g"]
wy = weightdata["Efficiency_"]

#fit 2nd order polynomial
wparams = np.polyfit(wx, wy, 2)
wxp = np.linspace(7000, 2500, 20)
wyp = np.polyval(wparams, wxp)

#error band
wsig = np.std(wy - np.polyval(wparams, wx))

attdata = np.genfromtxt("mash_temp_vs_attenuation.csv", delimiter=",", names=True)
ax = attdata["Mash_temperature_C"]
ay =  attdata["Attenuation_"]

#fit 2nd order polynomial
aparams = np.polyfit(ax, ay, 1)
axp = np.linspace(80, 55, 2)
ayp = np.polyval(aparams, axp)

#error band
asig = np.std(ay - np.polyval(aparams, ax))

pl.subplot(2, 1, 1)
pl.title("Braumeister efficiency")

pl.plot(wxp, wyp, 'k')
pl.fill_between(wxp, wyp - wsig, wyp + wsig, color='g', alpha=0.2)
pl.plot(weightdata["Grainweight_g"], weightdata["Efficiency_"], "ro")

pl.xlabel("Grain weight [g]")
pl.ylabel("Efficiency [%]")
pl.grid()

pl.subplot(2, 1, 2)
pl.title("Mash temp vs yeast attenuation")

pl.plot(axp, ayp, 'k')
pl.fill_between(axp, ayp - asig, ayp + asig, color='g', alpha=0.2)
pl.plot(attdata["Mash_temperature_C"], attdata["Attenuation_"], "yo")

pl.xlabel("Temperature [C]")
pl.ylabel("Attenuation [%]")
pl.grid()
pl.show()