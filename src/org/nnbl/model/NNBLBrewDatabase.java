package org.nnbl.model;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.nnbl.utils.ColorSpan;

public class NNBLBrewDatabase {
	private ArrayList<NNBLBrew> db;

	public NNBLBrewDatabase() {
		db = new ArrayList<NNBLBrew>();
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
				nearest += brew.getBrewName() + " by " + brew.getBrewer() + ", ";
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
					&& brew.getAttenuation() <= (attenuation + 0.4)
					) {
				nearest += brew.getYeastType() + " in " + brew.getBrewName()
						+ ", ";
			}
		}
		return nearest;
	}

	public int getNumberOfBrewsWithYeastType(String type) {
		int number = 0;
		for (NNBLBrew brew : db) {
			if (brew.getYeastType().equals(type)) {
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
				System.err.println("File " + pathname + " does not exist!");
			}
		} catch (IOException e) {
		}
		Color[] colors = ColorSpan.generateColorSpan(db.size());

		for (int i = 0; i < db.size(); i++) {
			db.get(i).setColor(colors[i]);
		}

	}
}
