import java.io.*;
import java.util.ArrayList;

public class NNBLBrewDatabase {
	public static final int EFFICIENCY = 0;
	public static final int GRAIN_WEIGHT = 1;
	public static final int ORIGINAL_GRAVITY = 2;
	public static final int FINAL_GRAVITY = 3;
	public static final int ATTENUATION = 4;
	public static final int MASH_TEMPERATURE = 5;

	private ArrayList<NNBLBrew> db;

	public NNBLBrewDatabase() {
		db = new ArrayList<NNBLBrew>();
	}

	public ArrayList<Double> getAllEfficiency(boolean addIfZeroes) {
		ArrayList<Double> values = new ArrayList<Double>();
		for (NNBLBrew brew : db) {
			if (addIfZeroes) {
				values.add(brew.getEfficiency());
			} else {
				if (brew.getEfficiency() > 0) {
					values.add(brew.getEfficiency());
				}
			}
		}
		return values;
	}

	public ArrayList<Double> getAllGrainWeight(boolean addIfZeroes) {
		ArrayList<Double> values = new ArrayList<Double>();
		for (NNBLBrew brew : db) {
			if (addIfZeroes) {
				values.add((double) brew.getGrainweight());
			} else {
				if (brew.getGrainweight() > 0) {
					values.add((double) brew.getGrainweight());
				}
			}
		}
		return values;
	}

	public ArrayList<Double> getAllOG(boolean addIfZeroes) {
		ArrayList<Double> values = new ArrayList<Double>();
		for (NNBLBrew brew : db) {
			if (addIfZeroes) {
				values.add((double) brew.getOriginalGravity());

			} else {
				if (brew.getOriginalGravity() > 0) {
					values.add((double) brew.getOriginalGravity());
				}
			}
		}
		return values;
	}

	public ArrayList<Double> getAllFG(boolean addIfZeroes) {
		ArrayList<Double> values = new ArrayList<Double>();
		for (NNBLBrew brew : db) {
			if (addIfZeroes) {
				values.add((double) brew.getFinalGravity());

			} else {
				if (brew.getFinalGravity() > 0) {
					values.add((double) brew.getFinalGravity());
				}
			}
		}
		return values;
	}

	public ArrayList<Double> getAllAttenuation(boolean addIfZeroes) {
		ArrayList<Double> values = new ArrayList<Double>();
		for (NNBLBrew brew : db) {
			if (addIfZeroes) {
				values.add(brew.getAttenuation());

			} else {
				if (brew.getAttenuation() > 0) {
					values.add(brew.getAttenuation());
				}
			}
		}
		return values;
	}

	public ArrayList<Double> getAllMashTemp(boolean addIfZeroes) {
		ArrayList<Double> values = new ArrayList<Double>();
		for (NNBLBrew brew : db) {
			if (addIfZeroes) {
				values.add(brew.getMashTemp());

			} else {
				if (brew.getMashTemp() > 0) {
					values.add(brew.getMashTemp());
				}
			}
		}
		return values;
	}

	public void addBrew(NNBLBrew brew) {
		db.add(brew);
	}

	public ArrayList<NNBLBrew> getBrews() {
		return db;
	}

	public int size() {
		return db.size();
	}

	public String findNearestEfficiency(float grainweight, float efficiency) {

		StringBuilder nearest = new StringBuilder();

		for (NNBLBrew brew : db) {
			if (brew.getGrainweight() >= (grainweight - 50)
					&& brew.getGrainweight() <= (grainweight + 50)
					&& brew.getEfficiency() >= (efficiency - 0.4)
					&& brew.getEfficiency() <= (efficiency + 0.4)) {
				nearest.append(brew.getBrewname() + " by " + brew.getBrewer() + ", ");
			}
		}
		return nearest.toString();
	}

	public String findNearestMashTemp(float temperature, float attenuation) {

		StringBuilder nearest = new StringBuilder();

		for (NNBLBrew brew : db) {
			if (brew.getMashTemp() >= (temperature - 0.5)
					&& brew.getMashTemp() <= (temperature + 0.5)
					&& brew.getAttenuation() >= (attenuation - 0.4)
					&& brew.getAttenuation() <= (attenuation + 0.4)) {
				nearest.append(brew.getYeasttype() + " in " + brew.getBrewname() + ", ");
			}
		}
		return nearest.toString();
	}

	public int getNumberOfBrewsWith(String yeastType) {
		int number = 0;
		for (NNBLBrew brew : db) {
			if (brew.getYeasttype().equals(yeastType)) {
				number++;
			}
		}
		return number;
	}

    public String dbAsCsv(){
        StringBuilder csv = new StringBuilder("Brewer,Name,Grainweight,Efficiency,OG,FG,Mash temperature,Attenuation,Yeast type,Remarks\n");

        for (NNBLBrew nnblBrew : db) {
            csv.append(nnblBrew.getBrewer() + ",");
            csv.append(nnblBrew.getBrewname() + ",");
            csv.append(nnblBrew.getGrainweight() + ",");
            csv.append(nnblBrew.getEfficiency() + ",");
            csv.append(nnblBrew.getOriginalGravity() + ",");
            csv.append(nnblBrew.getFinalGravity() + ",");
            csv.append(nnblBrew.getMashTemp() + ",");
            csv.append(nnblBrew.getAttenuation() + ",");
            csv.append(nnblBrew.getYeasttype() + ",");
            csv.append(nnblBrew.getRemarks() + "\n");

        }
        return csv.toString();
    }

	public void loadBrewsFromFile(String pathname) {
		db.clear();
		try {
			File f = new File(pathname);
			if (f.exists()) {
				BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(pathname), "UTF8"));
				String line = null;
				while ((line = reader.readLine()) != null) {
					if (!line.startsWith("Brewer,Name")) {
						String[] values = line.split(",");
						db.add(new NNBLBrew(values));
					}
				}
				reader.close();
			} else {
				System.err.println("File " + pathname + " does not exist!\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
