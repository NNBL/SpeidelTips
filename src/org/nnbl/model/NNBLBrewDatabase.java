package org.nnbl.model;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.nnbl.utils.ColorSpan;

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

		String nearest = "";

		for (NNBLBrew brew : db) {
			if (brew.getGrainweight() >= (grainweight - 50)
					&& brew.getGrainweight() <= (grainweight + 50)
					&& brew.getEfficiency() >= (efficiency - 0.4)
					&& brew.getEfficiency() <= (efficiency + 0.4)) {
				nearest += brew.getBrewName() + " by " + brew.getBrewer()
						+ ", ";
			}
		}
		return nearest;
	}

	public String findNearestMashTemp(float temperature, float attenuation) {

		String nearest = "";

		for (NNBLBrew brew : db) {
			if (brew.getMashTemp() >= (temperature - 0.5)
					&& brew.getMashTemp() <= (temperature + 0.5)
					&& brew.getAttenuation() >= (attenuation - 0.4)
					&& brew.getAttenuation() <= (attenuation + 0.4)) {
				nearest += brew.getYeastType() + " in " + brew.getBrewName()
						+ ", ";
			}
		}
		return nearest;
	}

	public int getNumberOfBrewsWith(String yeastType) {
		int number = 0;
		for (NNBLBrew brew : db) {
			if (brew.getYeastType().equals(yeastType)) {
				number++;
			}
		}
		return number;
	}

	public void loadBrewsFromFile(String pathname) {
		db.clear();
		try {
			File f = new File(pathname);
			if (f.exists()) {
				BufferedReader reader = new BufferedReader(new FileReader(
						pathname));
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
		}
		
		if (db.size() > 0) {
			Color[] colors = ColorSpan.generateColorSpan(db.size());

			for (int i = 0; i < db.size(); i++) {
				db.get(i).setColor(colors[i]);
			}			
		}
	}
}
