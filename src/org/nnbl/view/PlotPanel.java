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
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.nnbl.model.NNBLBrewDatabase;
import org.nnbl.utils.ColorSpan;
import org.nnbl.utils.math.PolyTrendLine;
import org.nnbl.utils.math.TrendLine;

public class PlotPanel extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;

	private String title = "";
	private String xUnit = "";
	private String yUnit = "";

	private double minX = 0.0;
	private double maxX = 100.0;

	private double minY = 0.0;
	private double maxY = 100.0;

	private int borderSize = 0;

	private TrendLine t = new PolyTrendLine(3);
	protected boolean trendlinestatus = true;

	private ArrayList<Double> xvalues;
	private ArrayList<Double> yvalues;

	public PlotPanel() {
		setMinimumSize(new Dimension(250, 150));
		setBackground(Color.WHITE);
		this.addMouseListener(this);
	}
	
	public void setXUnit(String xUnit){
		this.xUnit = xUnit;
	}
	
	public void setYUnit(String yUnit){
		this.yUnit = yUnit;
	}
	public void setXvalues(ArrayList<Double> xvalues) {
		this.xvalues = xvalues;
		minX = Collections.min(xvalues);
		maxX = Collections.max(xvalues);

		if (maxX > 10 && maxX < 100) {
			maxX += 10;
		} else if (maxX > 100 && maxX < 1000) {
			maxX += 100;
		} else if (maxX > 1000 && maxX < 10000) {
			maxX += 1000;
		}

	}

	public void setYvalues(ArrayList<Double> yvalues) {
		this.yvalues = yvalues;
		minY = Collections.min(yvalues);
		maxY = Collections.max(yvalues);

		if (maxY > 10 && maxY < 100) {
			maxY += 10;
		} else if (maxY > 100 && maxY < 1000) {
			maxY += 100;
		} else if (maxY > 1000 && maxY < 10000) {
			maxY += 1000;
		}
	}

	public void setXvalues(ArrayList<Double> xvalues, double minX, double maxX) {
		this.xvalues = xvalues;
		this.minX = minX;
		this.maxX = maxX;
	}

	public void setYvalues(ArrayList<Double> yvalues, double minY, double maxY) {
		this.yvalues = yvalues;
		this.minY = minY;
		this.maxY = maxY;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private void setRenderingHints(Graphics2D g2d) {
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g.create();
		setRenderingHints(g2d);
		drawLines(g2d);
		drawTitle(g2d);
		if (xvalues != null && yvalues != null) {
			if (trendlinestatus) {
				drawTrendLine(g2d, getXYvalues(g2d));
			}
			drawValues(g2d);
		} else {
			System.err.println("No values to draw.");
		}
	}

	private void drawTitle(Graphics2D g2d) {
		g2d.setColor(Color.black);
		g2d.setStroke(new BasicStroke(1));
		Font font = new Font("Arial", Font.ITALIC, 16);
		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();
		Rectangle2D rect = fm.getStringBounds(title, g2d);

		g2d.drawString(title, (int) (getWidth() / 2 - rect.getWidth() / 2),
				(int) (10 + rect.getHeight() / 2));

	}

	private double[] getXYvalues(Graphics2D g2d) {
		double[] tlx = new double[xvalues.size()];
		double[] tly = new double[yvalues.size()];

		for (int i = 0; i < xvalues.size(); i++) {
			tlx[i] = scaleX(xvalues.get(i));
			tly[i] = scaleY(yvalues.get(i));
		}

		t.setValues(tly, tlx);
		return tlx;
	}

	private void drawTrendLine(Graphics2D g2d, double[] tlx) {

		int width = getWidth();

		int[] xpoints = new int[width];
		int[] ypoints = new int[width];
		
		for (int x = 0; x < width; x++) {
			int y = (int) (t.predict(x));
			xpoints[x] = x;
			ypoints[x] = y;
		}
		
		// Draw "Error band"
		g2d.setStroke(new BasicStroke(50));
		g2d.setColor(new Color(0, 255, 0, 30));
		g2d.drawPolyline(xpoints, ypoints, xpoints.length);
		
		// Draw Trend line center
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

		int xstrokes = 10;
		int ystrokes = 10;

		for (int i = 1; i < ystrokes; i++) {
			int x0 = borderSize;
			int y0 = i * (getHeight() / ystrokes);
			int x1 = getWidth();
			int y1 = i * (getHeight() / ystrokes);

			g2d.drawLine(x0, y0, x1, y1);

			if (i != 0) {
				g2d.drawString((int) (maxY - i * (maxY - minY) / ystrokes)
						+ yUnit, x0, y0 - 2);
			}
		}

		for (int i = 1; i < xstrokes; i++) {
			int x0 = i * (getWidth() / xstrokes);
			int y0 = 0;
			int x1 = i * (getWidth() / xstrokes);
			int y1 = getHeight();
			g2d.drawLine(x0, y0, x1, y1);
			g2d.drawString(((int) ((minX + i * (maxX - minX) / xstrokes)))
					+ xUnit, x1 + 2, y1 - 4);
		}

	}

	private void drawValues(Graphics2D g2d) {
		g2d.setStroke(new BasicStroke(1));
		Color[] colors = ColorSpan.generateColorSpan(xvalues.size());
		for (int i = 0; i < xvalues.size(); i++) {
			int x = scaleX(xvalues.get(i));
			int y = scaleY(yvalues.get(i));

			g2d.setColor(colors[i]);
			g2d.fillOval(x - 5, y - 5, 10, 10);
			g2d.setColor(Color.BLACK);
			g2d.drawOval(x - 5, y - 5, 10, 10);

		}
	}

	private int scaleY(double valueToScale) {
		return (int) (getHeight() - ((valueToScale - minY) / (maxY - minY) * getHeight()));
	}

	private int scaleX(double valueToScale) {
		return (int) ((valueToScale - minX) / (maxX - minX) * getWidth());
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		float xfactor = (float) e.getX() / (float) getWidth();
		float yfactor = (float) e.getY() / (float) getHeight();

		if (e.getButton() == MouseEvent.BUTTON1) {
			double x = xfactor * (maxX - minX) + minX;
			double y = maxY - (yfactor * (maxY - minY));
			System.out.println((int) x + ":" + (int) y);
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

	public static void main(String[] args) {
		NNBLBrewDatabase brewbase = new NNBLBrewDatabase();
		brewbase.loadBrewsFromFile("./nnbldata.csv");

		PlotPanel p1 = new PlotPanel();
		p1.setTitle("Efficiency vs grain weight");
		p1.setXUnit("g");
		p1.setYUnit("%");
		p1.setYvalues(brewbase.getAllEfficiency(true), 70, 95);
		p1.setXvalues(brewbase.getAllGrainWeight(true), 3000, 7000);

		PlotPanel p2 = new PlotPanel();
		p2.setTitle("Attenuation vs mash temp");
		p2.setXUnit("°C");
		p2.setYUnit("%");
		p2.setYvalues(brewbase.getAllAttenuation(true), 45, 90);
		p2.setXvalues(brewbase.getAllMashTemp(true), 60, 70);

		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
		top.add(p1);
		top.add(Box.createRigidArea(new Dimension(10, 0)));
		top.add(new NameLegendPanel(brewbase));
		
		JPanel bottom = new JPanel();
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
		bottom.add(p2);
		bottom.add(Box.createRigidArea(new Dimension(10, 0)));
		bottom.add(new YeastLegendPanel(brewbase));
		
		JPanel verticalPane = new JPanel();
		verticalPane.setLayout(new BoxLayout(verticalPane, BoxLayout.Y_AXIS));

		verticalPane.add(top);
		verticalPane.add(Box.createRigidArea(new Dimension(0, 10)));
		verticalPane.add(bottom);

		JFrame app = new JFrame("Test panel");
		app.add(verticalPane);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setLocationRelativeTo(null);
		app.setSize(400, 600);
		app.setVisible(true);
	}
}
