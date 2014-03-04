import matplotlib.pyplot as pl
import numpy as np
import pandas
import matplotlib.cm as cm

from matplotlib import rcParams
rcParams.update({'figure.autolayout': True})

def make_colors(items):
    x = np.arange(items)
    ys = [i+x+(i*x)**2 for i in range(items)]

    return cm.rainbow(np.linspace(0, 1, len(ys)))


def weight_vs_efficiency_figure(subplot):
    weightdata = np.genfromtxt("weight_vs_efficiency.csv", delimiter=",", names=True, dtype=None)
    
    wx = weightdata["Grainweight_g"]
    wy = weightdata["Efficiency_"]
    
    #fit 2nd order polynomial
    wparams = np.polyfit(wx, wy, 2)
    wxp = np.linspace(8500, 2500, 100)
    wyp = np.polyval(wparams, wxp)
    
    #error band
    wsig = np.std(wy - np.polyval(wparams, wx))

    subplot.set_title("Measured efficiency", fontsize=16)
    subplot.plot(wxp, wyp, 'k')
    subplot.fill_between(wxp, wyp - wsig, wyp + wsig, color='green', alpha=0.25)
    i=0
    for x,y in zip(wx,wy):
        pl.plot(x,y,marker="o", markersize=8,label=weightdata["Remarks"][i], alpha=0.75)
        i = i + 1 
     

    subplot.set_xlim(2500,8500)
    subplot.set_ylim(60,100)
    subplot.set_xlabel("Grain weight [g]")
    subplot.set_ylabel("Efficiency [%]")
    subplot.grid()
    subplot.legend(prop={'size':8,}, shadow=True)

def mashtemp_vs_attenuation_figure(subplot):
    ##Experimental and ugly
    data = pandas.read_csv("mash_temp_vs_attenuation.csv", delimiter=",", dtype=None)
    yeasttypes = set(data["Yeast type"])
    ## End
    ax = data["Mash temperature [C]"]
    ay =  data["Attenuation [%]"]
    
    
    #fit 2nd order polynomial
    aparams = np.polyfit(ax, ay, 2)
    axp = np.linspace(70, 60, 100)
    ayp = np.polyval(aparams, axp)
    
    #error band
    asig = np.std(ay - np.polyval(aparams, ax))
    subplot.set_title("Mash temp vs yeast attenuation", fontsize=16)
    subplot.plot(axp, ayp, 'k')
    subplot.fill_between(axp, ayp - asig, ayp + asig, color='green', alpha=0.25)
    
    #Experimental and ugly
    
    colors = make_colors(len(yeasttypes))
    
    for yeast,color in zip(yeasttypes,colors):
            brews = data[data["Yeast type"].str.contains(yeast)]
            pl.scatter(brews["Mash temperature [C]"], brews["Attenuation [%]"], 60, color=color, edgecolors="black", label=yeast, alpha=0.75)
    #End
    
    
    subplot.set_xlim(60,70)
    subplot.set_ylim(50,90)
    subplot.set_xlabel(r"Temperature [$^{\circ}\mathrm{C}$]")
    subplot.set_ylabel("Attenuation [%]")
    subplot.grid()
    subplot.legend(prop={'size':8}, shadow=True)

p = pl.figure()
p1 = p.add_subplot(211)
weight_vs_efficiency_figure(p1)
p2 = p.add_subplot(212)
mashtemp_vs_attenuation_figure(p2)
pl.savefig("all.png")
pl.show()