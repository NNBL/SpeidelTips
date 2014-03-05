"""docstring"""
import matplotlib.pyplot as pl
import numpy as np
import pandas
import matplotlib.cm as cm
from matplotlib.ticker import MultipleLocator, FormatStrFormatter

from matplotlib import rcParams
rcParams.update({'figure.autolayout': True})



def make_colors(items):
    """returns colors in range items"""
    x = np.arange(items)
    ys = [i+x+(i*x)**2 for i in range(items)]

    return cm.gist_ncar(np.linspace(0, 1, len(ys)))

def weight_vs_og_figure(subplot, data):
    subplot.set_title("Original gravity", fontsize=16)
    subplot.bar([3700, 4300, 5150,  5200, 6100, 6130, 6270, 6500],[40,50,60,70,60,40,35,35], 25, alpha = 0.4, align="center")
    subplot.set_ylabel("OG")
    subplot.set_ylim(30, 100)
    subplot.set_xlabel("Grain weight [g]")
    majorlocator = MultipleLocator(1000)
    majorformatter = FormatStrFormatter('%d')
    minorlocator = MultipleLocator(100)
    subplot.xaxis.set_major_locator(majorlocator)
    subplot.xaxis.set_major_formatter(majorformatter)
    subplot.xaxis.set_minor_locator(minorlocator)
    subplot.yaxis.tick_right()
    subplot.yaxis.set_label_position("right")
    subplot.grid()

def weight_vs_efficiency_figure(subplot, data):
    """subplot for weight vs efficiency"""

    wx = data["Grainweight"]
    wy = data["Efficiency"]

    #fit 3rd order polynomial
    wparams = np.polyfit(wx, wy, 3)
    wxp = np.linspace(8500, 2500, 100)
    wyp = np.polyval(wparams, wxp)

    #error band
    wsig = np.std(wy - np.polyval(wparams, wx))

    subplot.set_title("Measured efficiency", fontsize=16)
    subplot.plot(wxp, wyp, 'k', alpha=0.45)
    subplot.fill_between(wxp, wyp - wsig, wyp + wsig, color='green', alpha=0.25)
    colors = make_colors(len(wx))

    for x, y, color, label in zip(wx, wy, colors, data["Name"]):
        pl.scatter(x, y, s=60, color=color, edgecolors="black", label=label, alpha=0.75)

    subplot.set_xlim(3000, 8500)
    subplot.set_ylim(60, 100)
    majorlocator = MultipleLocator(1000)
    majorformatter = FormatStrFormatter('%d')
    minorlocator = MultipleLocator(100)
    subplot.xaxis.set_major_locator(majorlocator)
    subplot.xaxis.set_major_formatter(majorformatter)
    subplot.xaxis.set_minor_locator(minorlocator)
    subplot.set_xlabel("Grain weight [g]")
    subplot.set_ylabel("Efficiency [%]")
    subplot.grid()
    subplot.legend(prop={'size': 6}, shadow=True)


def mashtemp_vs_attenuation_figure(subplot, data):
    """subplot for mashtemp vs attenuation"""
    #Experimental and ugly
    yeasttypes = set(data["Yeast type"])
    # End
    ax = data["Mash temperature"]
    ay = data["Attenuation"]

    #fit 2nd order polynomial
    aparams = np.polyfit(ax, ay, 2)
    axp = np.linspace(70, 60, 100)
    ayp = np.polyval(aparams, axp)

    #error band
    asig = np.std(ay - np.polyval(aparams, ax))
    subplot.set_title("Mash temp vs yeast attenuation", fontsize=16)
    subplot.plot(axp, ayp, 'k', alpha=0.45)
    subplot.fill_between(axp, ayp - asig, ayp + asig, color='green', alpha=0.25)

    #Experimental and ugly
    colors = make_colors(len(yeasttypes))
    for yeast, color in zip(yeasttypes, colors):
        brews = data[data["Yeast type"].str.contains(yeast)]
        label = yeast+" ("+str(len(brews))+")"
        pl.scatter(brews["Mash temperature"], brews["Attenuation"], s=60, color=color, edgecolors="black", label=label, alpha=0.75)
    #End

    subplot.set_xlim(60, 70)
    subplot.set_ylim(50, 90)
    subplot.set_xlabel(r"Temperature [$^{\circ}\mathrm{C}$]")
    subplot.set_ylabel("Attenuation [%]")
    majorlocator = MultipleLocator(1)
    majorformatter = FormatStrFormatter('%d')
    minorlocator = MultipleLocator(0.5)
    subplot.xaxis.set_major_locator(majorlocator)
    subplot.xaxis.set_major_formatter(majorformatter)
    subplot.xaxis.set_minor_locator(minorlocator)
    subplot.grid()
    subplot.legend(prop={'size': 7}, shadow=True)

    
data = pandas.read_csv("nnbldata.csv", delimiter=",", dtype=None)

p1 = pl.subplot2grid((2,3), (0,0), colspan=2)
weight_vs_efficiency_figure(p1, data)
p2 = pl.subplot2grid((2,3), (0,2))
weight_vs_og_figure(p2, data)
p3 = pl.subplot2grid((2,3), (1,0), colspan=3)
mashtemp_vs_attenuation_figure(p3, data)
#pl.savefig("all.png")
pl.show()
