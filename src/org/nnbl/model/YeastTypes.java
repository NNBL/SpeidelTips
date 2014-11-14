package org.nnbl.model;

import java.awt.Color;
import java.util.LinkedHashMap;

import org.nnbl.utils.ColorSpan;

public class YeastTypes {
	private LinkedHashMap<String, Color> yeasts;

	public YeastTypes() {
		String[] yeastTypes = {"WLP830","M44","WLP833","WLP001","M10","Nottingham","WLP002","US05","WLP810","T58","Other"};
		Color[] colors = ColorSpan.generateColorSpan(yeastTypes.length);
		yeasts = new LinkedHashMap<String, Color>();
		
		for (int i = 0; i < yeastTypes.length; i++) {
			yeasts.put(yeastTypes[i], colors[i]);
		}
	}
	
	public Color getColorForYeastType(String yeast){
		return yeasts.get(yeast);
	}
}
