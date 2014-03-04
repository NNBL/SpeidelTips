import pandas

data = pandas.read_csv("nnbldata.csv", delimiter=",", dtype=None)

print data.sort(["Efficiency"], ascending=False)