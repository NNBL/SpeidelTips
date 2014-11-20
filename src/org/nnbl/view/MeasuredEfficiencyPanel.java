package org.nnbl.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.nnbl.model.NNBLBrew;
import org.nnbl.model.NNBLBrewDatabase;
import org.nnbl.utils.math.PolyTrendLine;
import org.nnbl.utils.math.TrendLine;

public class MeasuredEfficiencyPanel extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;
	private NNBLBrewDatabase db;
	private NNBLApp nnblApp;
	private float minWeight = 3500f;
	private float maxWeight = 7000f;
	private float minEfficiency = 60f;
	private float maxEfficiency = 100f;
	private int borderSize = 0;
	private TrendLine t = new PolyTrendLine(2);
	protected boolean trendlinestatus = true;

	public MeasuredEfficiencyPanel(NNBLBrewDatabase db, NNBLApp nnblApp) {
		this.db = db;
		this.nnblApp = nnblApp;
		setMinimumSize(new Dimension(250, 150));
		setBackground(Color.WHITE);
		this.addMouseListener(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g.create();
		setRenderingHints(g2d);
		drawLines(g2d);
		drawTitle(g2d);
		if (db.size() > 0) {
			if (trendlinestatus) {
				drawTrendLine(g2d, getXYvalues(g2d));				
			}
			drawBrews(g2d);
		} else {

		}
	}

	private void setRenderingHints(Graphics2D g2d) {
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);
	}

	private void drawBrews(Graphics2D g2d) {
		g2d.setStroke(new BasicStroke(1));

		for (NNBLBrew brew : db.getBrews()) {
			int x = (int) ((brew.getGrainweight() - minWeight)
					/ (maxWeight - minWeight) * getWidth());
			int y = (int) (getHeight() - ((brew.getEfficiency() - minEfficiency)
					/ (maxEfficiency - minEfficiency) * getHeight()));

			g2d.setColor(brew.getColor());
			g2d.fillOval(x - 5, y - 5, 10, 10);
			g2d.setColor(Color.BLACK);
			g2d.drawOval(x - 5, y - 5, 10, 10);
		}
	}

	private double[] getXYvalues(Graphics2D g2d) {
		double[] tlx = new double[db.size()];
		double[] tly = new double[db.size()];

		int i = 0;
		for (NNBLBrew brew : db.getBrews()) {
			int x = (int) ((brew.getGrainweight() - minWeight)
					/ (maxWeight - minWeight) * getWidth());
			int y = (int) (getHeight() - ((brew.getEfficiency() - minEfficiency)
					/ (maxEfficiency - minEfficiency) * getHeight()));

			tlx[i] = x;
			tly[i] = y;
			i++;
		}

		t.setValues(tly, tlx);
		return tlx;
	}

	private void drawTitle(Graphics2D g2d) {
		g2d.setColor(Color.black);
		g2d.setStroke(new BasicStroke(1));
		Font font = new Font("Arial", Font.ITALIC, 12);
		g2d.setFont(font);
		String title = "Measured Efficiency vs grain weight";
		FontMetrics fm = g2d.getFontMetrics();
		Rectangle2D rect = fm.getStringBounds(title, g2d);
		g2d.drawString(title, (int) (getWidth() / 2 - rect.getWidth() / 2),
				(int) (10 + rect.getHeight() / 2));

	}

	private void drawTrendLine(Graphics2D g2d, double[] tlx) {

		int width = getWidth();

		int[] xpoints = new int[width];
		int[] ypoints = new int[width];

		for (int x = 0; x < width; x++) {
			xpoints[x] = x;
			ypoints[x] = (int) t.predict(x);
		}
		// Draw "Error band"
		g2d.setStroke(new BasicStroke(50));
		g2d.setColor(new Color(0, 255, 0, 30));
		g2d.drawPolyline(xpoints, ypoints, xpoints.length);
		// Draw fitted line
		g2d.setStroke(new BasicStroke(1.5f));
		g2d.setColor(Color.BLACK);
		g2d.drawPolyline(xpoints, ypoints, xpoints.length);
	}

	private void drawLines(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);

		g2d.setColor(new Color(0.73f, 0.73f, 0.73f, 1f));
		float dash1[] = { 3.0f, 5.0f };
		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));

		int xstrokes = 7;
		int ystrokes = 8;

		for (int i = 1; i < ystrokes; i++) {
			int x0 = borderSize;
			int y0 = i * (getHeight() / ystrokes);
			int x1 = getWidth();
			int y1 = i * (getHeight() / ystrokes);

			g2d.drawLine(x0, y0, x1, y1);

			if (i != 0) {
				g2d.drawString((maxEfficiency - i * 5) + "%", x0, y0 - 2);
			}
		}

		for (int i = 1; i < xstrokes; i++) {
			int x0 = i * (getWidth() / xstrokes);
			int y0 = 0;
			int x1 = i * (getWidth() / xstrokes);
			int y1 = getHeight();
			g2d.drawLine(x0, y0, x1, y1);
			g2d.drawString((((minWeight + i * 500f) / 1000)) + "Kg", x1 + 2,
					y1 - 4);
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		float xfactor = (float) e.getX() / (float) getWidth();
		float yfactor = (float) e.getY() / (float) getHeight();

		if (e.getButton() == MouseEvent.BUTTON1) {
			float x = xfactor * (maxWeight - minWeight) + minWeight;
			float y = maxEfficiency
					- (yfactor * (maxEfficiency - minEfficiency));
			String statusText = db.findNearestEfficiency(x, y);

			if (statusText.equals("")) {
				nnblApp.setStatusText(" ");
			} else {
				nnblApp.setStatusText(statusText);
			}

		} else if (e.getButton() == MouseEvent.BUTTON3) {
			JPopupMenu menu = new JPopupMenu();
			JMenuItem trendlineitem = new JMenuItem("Toggle trendline");
			trendlineitem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					trendlinestatus = !trendlinestatus;
					repaint();
				}
			});
			menu.add(trendlineitem);
			setComponentPopupMenu(menu);
		}

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(200, 200);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
