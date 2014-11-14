package org.nnbl.utils;

import java.awt.Color;

public class ColorSpan {
	public static Color[] generateColorSpan(int numberOfColors){
		Color[] colors = new Color[numberOfColors];
		for (int i = 0; i < numberOfColors; i++) {
			colors[i] = Color.getHSBColor((1f/numberOfColors)*i,1.0f, 1.0f);
		}
		return colors;
	}
}
