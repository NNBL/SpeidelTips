package org.nnbl.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class PaintPanel extends JPanel{	
	private static final long serialVersionUID = 1L;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		int numberOfColors = 32;
		Color[] colors = ColorSpan.generateColorSpan(1.666f, 2.666f, 3.666f, 0, 120, 0, 128, 127, numberOfColors);
		int i = 0;
		for (Color color : colors) {
			g2d.setColor(color);
			int xSize = getWidth();
			double ySize = getHeight()/numberOfColors;
			g2d.fillRect(0, (int)(i*ySize), xSize, (int)ySize);
			i++;
			System.out.println(getWidth() + ":" + getHeight());
		}
	}
}
