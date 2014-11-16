package org.nnbl.utils;

import java.awt.Color;

public class ColorSpan {
	public static Color[] generateColorSpan(int numberOfColors) {
		Color[] colors = new Color[numberOfColors];
		
		for (int i = 0; i < numberOfColors - 2; i++) {
			colors[i] = Color
					.getHSBColor((1f / numberOfColors) * i, 1.0f, 1.0f);
		}

		colors[numberOfColors - 2] = Color.GRAY;
		colors[numberOfColors - 1] = Color.WHITE;

		return colors;
	}

	public static Color[] generateColorSpan(float frequency1, float frequency2,
			float frequency3, int phase1, int phase2, int phase3, int center,
			int width, int numberOfColors) {
		Color[] colors = new Color[numberOfColors];

		if (center == 0) {
			center = 128;
		}
		if (width == 0) {
			width = 127;
		}
		if (numberOfColors == 0) {
			numberOfColors = 50;
		}

		for (int i = 0; i < numberOfColors; ++i) {
			double r = Math.sin(frequency1 * i + phase1) * width + center;
			double g = Math.sin(frequency2 * i + phase2) * width + center;
			double b = Math.sin(frequency3 * i + phase3) * width + center;
			colors[i] = new Color((int) r, (int) g, (int) b);
			System.out.println("r:" + r + ", g:" + g + ", b:" + b);
		}
		return colors;
	}
}
