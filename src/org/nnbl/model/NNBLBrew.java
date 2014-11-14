package org.nnbl.model;

import java.awt.Color;
import java.util.Random;

public class NNBLBrew {
	private String brewer;
	private String brewName;
	private int grainweight;
	private double efficiency;
	private int originalGravity;
	private int finalGravity;
	private double mashTemp;
	private double attenuation;
	private String yeastType;
	private String remarks;
	private Color color;
	
	public NNBLBrew(String[] values) {
		this.brewer = values[0];
		this.brewName = values[1];
		this.grainweight = Integer.parseInt(values[2].trim());
		this.efficiency = Double.parseDouble(values[3].trim());
		
		if (values[4].equals("NaN")) {
			this.originalGravity = -1;
		} else {
			this.originalGravity = Integer.parseInt(values[4].trim());
		}

		if (values[4].equals("NaN")) {
			this.finalGravity = -1;
			
		} else {
			this.finalGravity = Integer.parseInt(values[5].trim());
		}
		
		this.mashTemp = Double.parseDouble(values[6].trim());
		this.attenuation = Double.parseDouble(values[7].trim());
		this.yeastType = values[8].trim();
		this.remarks = values[9].trim();
		Random r = new Random();
		
		
		
		this.color = Color.getHSBColor(r.nextFloat(),//random hue, color
                1.0f,//full saturation, 1.0 for 'colorful' colors, 0.0 for grey
                1.0f //1.0 for bright, 0.0 for black
                );
	}
	public NNBLBrew() {
		// TODO Auto-generated constructor stub
	}
	public String getBrewer() {
		return brewer;
	}
	public void setBrewer(String brewer) {
		this.brewer = brewer;
	}
	public String getBrewName() {
		return brewName;
	}
	public void setBrewName(String brewName) {
		this.brewName = brewName;
	}
	public int getGrainweight() {
		return grainweight;
	}
	public void setGrainweight(int grainweight) {
		this.grainweight = grainweight;
	}
	public double getEfficiency() {
		return efficiency;
	}
	public void setEfficiency(double efficiency) {
		this.efficiency = efficiency;
	}
	public int getOriginalGravity() {
		return originalGravity;
	}
	public void setOriginalGravity(int originalGravity) {
		this.originalGravity = originalGravity;
	}
	public int getFinalGravity() {
		return finalGravity;
	}
	public void setFinalGravity(int finalGravity) {
		this.finalGravity = finalGravity;
	}
	public double getMashTemp() {
		return mashTemp;
	}
	public void setMashTemp(double mashTemp) {
		this.mashTemp = mashTemp;
	}
	public double getAttenuation() {
		return attenuation;
	}
	public void setAttenuation(double attenuation) {
		this.attenuation = attenuation;
	}
	public String getYeastType() {
		return yeastType;
	}
	public void setYeastType(String yeastType) {
		this.yeastType = yeastType;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
}
