package org.nnbl.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.nnbl.model.NNBLBrew;
import org.nnbl.model.NNBLBrewDatabase;
import org.nnbl.model.YeastTypes;

public class YeastLegendPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private NNBLBrewDatabase db;
	private YeastTypes yt;

	public YeastLegendPanel(NNBLBrewDatabase db) {
		this.db = db;
		yt = new YeastTypes();
		setBackground(Color.WHITE);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		setRenderHints(g2d);
		drawYeastNames(g2d);
	}

	private void drawYeastNames(Graphics2D g2d) {
		ArrayList<String> drawnItems = new ArrayList<String>();
		if (db.size() > 0) {
			int i = 0;
			for (NNBLBrew brew : db.getBrews()) {
				String yeast = brew.getYeastType();
				//if (!drawnItems.contains(yeast)) {
					g2d.setColor(yt.getColorForYeastType(yeast));
					g2d.fillOval(5, 5 + 15 * i, 10, 10);
					g2d.setColor(Color.BLACK);
					g2d.drawString(
							yeast + " (" + db.getNumberOfBrewsWith(yeast) + ")",
							25, 15 + 15 * i);
					g2d.drawOval(5, 5 + 15 * i, 10, 10);
					drawnItems.add(yeast);
					i++;
				//}
			}
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(150, 100);
	}
	private void setRenderHints(Graphics2D g2d) {
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);
	}

}
