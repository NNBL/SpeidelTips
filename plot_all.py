import matplotlib.pyplot as pl
import numpy as np
import pandas
import matplotlib.cm as cm

from matplotlib import rcParams
rcParams.update({'figure.autolayout': True})

weightdata = np.genfromtxt("weight_vs_efficiency.csv", delimiter=",", names=True, dtype=None)

wx = weightdata["Grainweight_g"]
wy = weightdata["Efficiency_"]

#fit 2nd order polynomial
wparams = np.polyfit(wx, wy, 2)
wxp = np.linspace(7000, 2500, 100)
wyp = np.polyval(wparams, wxp)

#error band
wsig = np.std(wy - np.polyval(wparams, wx))
attdata = np.genfromtxt("mash_temp_vs_attenuation.csv", delimiter=",", names=True, dtype=None)
##Experimental and ugly
data = pandas.read_csv("mash_temp_vs_attenuation.csv", delimiter=",", dtype=None)
yeasttypes = set(data["Yeast type"])
## End
ax = attdata["Mash_temperature_C"]
ay =  attdata["Attenuation_"]

#fit 2nd order polynomial
aparams = np.polyfit(ax, ay, 2)
axp = np.linspace(80, 60, 100)
ayp = np.polyval(aparams, axp)

#error band
asig = np.std(ay - np.polyval(aparams, ax))

pl.subplot(2, 1, 1)
pl.title("Measured efficiency", fontsize=16)

pl.plot(wxp, wyp, 'k')
pl.fill_between(wxp, wyp - wsig, wyp + wsig, color='green', alpha=0.25)
pl.plot(weightdata["Grainweight_g"], weightdata["Efficiency_"], "ro")
pl.xlim(2500,7000)
pl.ylim(60,100)
pl.xlabel("Grain weight [g]")
pl.ylabel("Efficiency [%]")
pl.grid()

pl.subplot(2, 1, 2)
pl.title("Mash temp vs yeast attenuation", fontsize=16)
pl.plot(axp, ayp, 'k')
pl.fill_between(axp, ayp - asig, ayp + asig, color='green', alpha=0.25)
#Experimental and ugly

Ex = np.arange(len(yeasttypes))
Eys = [i+Ex+(i*Ex)**2 for i in range(len(yeasttypes))]

colors = cm.rainbow(np.linspace(0, 1, len(Eys)))

#colors = ["red","blue","green","orange","purple"]


for yeast,color in zip(yeasttypes,colors):
	brews = data[data["Yeast type"].str.contains(yeast)]
	pl.scatter(brews["Mash temperature [C]"], brews["Attenuation [%]"], color=color, edgecolors="black", label=yeast)
#End


pl.xlim(60,80)
pl.ylim(40,100)
pl.xlabel(r"Temperature [$^{\circ}\mathrm{C}$]")
pl.ylabel("Attenuation [%]")
pl.grid()
pl.legend()
pl.tight_layout()
pl.savefig('all.png')
pl.show()