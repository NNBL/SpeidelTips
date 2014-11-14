package org.nnbl.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.nnbl.model.NNBLBrew;
import org.nnbl.model.NNBLBrewDatabase;

public class NameLegendPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private NNBLBrewDatabase db;
	
	public NameLegendPanel(NNBLBrewDatabase db) {
		this.db = db;
		setBackground(Color.WHITE);
		setMinimumSize(new Dimension(200, 400));
		setSize(200, 400);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);
		
		if (db.size() > 0) {
			int i = 0;
			for (NNBLBrew brew : db.getBrews()) {
				g2d.setColor(brew.getColor());
				g2d.fillOval(5, 5 + 15*i, 10, 10);
				g2d.setColor(Color.BLACK);
				g2d.drawString(brew.getBrewName(), 25, 15 + 15*i);
				g2d.drawOval(5, 5 + 15*i, 10, 10);			
				i++;
			}			
		}
	}
	
	public static void main(String[] args) {
		NNBLBrewDatabase brewbase = new NNBLBrewDatabase();
		brewbase.loadBrewsFromFile("./src/nnbldata.csv");
		System.out.println(brewbase.size() + " brews loaded from file");

		NameLegendPanel nlp = new NameLegendPanel(brewbase);
		
		JFrame frame = new JFrame("Testing MeasuredEfficiency Panel");
		frame.add(nlp);
		frame.setMinimumSize(nlp.getMinimumSize());
		frame.setSize(500, 500);
		frame.setVisible(true);
	}
}
