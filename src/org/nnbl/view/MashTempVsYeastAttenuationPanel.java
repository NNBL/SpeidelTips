package org.nnbl.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.nnbl.model.NNBLBrew;
import org.nnbl.model.NNBLBrewDatabase;
import org.nnbl.model.YeastTypes;
import org.nnbl.utils.math.PolyTrendLine;
import org.nnbl.utils.math.TrendLine;

public class MashTempVsYeastAttenuationPanel extends JPanel implements
		MouseListener {
	private static final long serialVersionUID = 1L;
	private NNBLBrewDatabase db;
	private NNBLApp nnblApp;
	private String title = "Mash temp vs Yeast attenuation";
	private float minTemperature = 60f;
	private float maxTemperature = 70f;
	private float minAttenuation = 50f;
	private float maxAttenuation = 90f;

	private int borderSize = 0;
	private TrendLine t = new PolyTrendLine(2);
	private YeastTypes yt;

	public MashTempVsYeastAttenuationPanel(NNBLBrewDatabase db, NNBLApp nnblApp) {
		this.db = db;
		this.nnblApp = nnblApp;
		yt = new YeastTypes();
		setBackground(Color.WHITE);
		setMinimumSize(new Dimension(250, 150));
		this.addMouseListener(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		setRenderHints(g2d);		
		drawLines(g2d);
		drawTitle(g2d);
		if (db.size() != 0) {
			drawTrendLine(g2d, getXYvalues(g2d));
			drawBrews(g2d);	
		}	
	}

	private void setRenderHints(Graphics2D g2d) {
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);
		
	}

	private void drawTitle(Graphics2D g2d) {
		g2d.setColor(Color.black);
		g2d.setStroke(new BasicStroke(1));
		Font font = new Font("Arial", Font.ITALIC, 12);
		g2d.setFont(font);
		// Find the width of the string and center it
		FontMetrics fm = g2d.getFontMetrics();
		Rectangle2D rect = fm.getStringBounds(title, g2d);
		g2d.drawString(title, (int) (getWidth() / 2 - rect.getWidth() / 2),
				(int) (10 + rect.getHeight() / 2));
		
	}

	private double[] getXYvalues(Graphics2D g2d) {
		double[] trendLineX = new double[db.size()];
		double[] trendLineY = new double[db.size()];
		int i = 0;
		for (NNBLBrew brew : db.getBrews()) {
			int x = (int) ((brew.getMashTemp() - minTemperature)
					/ (maxTemperature - minTemperature) * getWidth());
			int y = (int) (getHeight() - ((brew.getAttenuation() - minAttenuation)
					/ (maxAttenuation - minAttenuation) * getHeight()));

			trendLineX[i] = x;
			trendLineY[i] = y;
			i++;
		}

		t.setValues(trendLineY, trendLineX);
		return trendLineX;
	}

	private void drawBrews(Graphics2D g2d) {
		g2d.setStroke(new BasicStroke(1));

		for (NNBLBrew brew : db.getBrews()) {
			int x = (int) ((brew.getMashTemp() - minTemperature)
					/ (maxTemperature - minTemperature) * getWidth());
			int y = (int) (getHeight() - ((brew.getAttenuation() - minAttenuation)
					/ (maxAttenuation - minAttenuation) * getHeight()));

			g2d.setColor(yt.getColorForYeastType(brew.getYeastType()));
			g2d.fillOval(x - 5, y - 5, 10, 10);
			g2d.setColor(Color.BLACK);
			g2d.drawOval(x - 5, y - 5, 10, 10);
		}
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
				BasicStroke.JOIN_MITER, 2.0f, dash1, 0.0f));

		int xstrokes = 10;
		int ystrokes = 10;

		for (int i = 1; i < ystrokes; i++) {
			int x0 = borderSize;
			int y0 = i * (getHeight() / ystrokes);
			int x1 = getWidth();
			int y1 = i * (getHeight() / ystrokes);

			g2d.drawLine(x0, y0, x1, y1);

			if (i != 0) {
				g2d.drawString((maxAttenuation - i * 4) + "%", x0, y0 - 2);
			}
		}

		for (int i = 1; i < xstrokes; i++) {
			int x0 = i * (getWidth() / xstrokes);
			int y0 = 0;
			int x1 = i * (getWidth() / xstrokes);
			int y1 = getHeight();
			g2d.drawLine(x0, y0, x1, y1);
			g2d.drawString(((int) (minTemperature + i)) + "C", x1 + 2, y1 - 4);
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		float xfactor = (float) e.getX() / (float) getWidth();
		float yfactor = (float) e.getY() / (float) getHeight();

		float x = xfactor * (maxTemperature - minTemperature) + minTemperature;
		float y = maxAttenuation
				- (yfactor * (maxAttenuation - minAttenuation));
		String statusText = db.findNearestMashTemp(x, y);
		if (statusText.equals("")) {
			nnblApp.setStatusText(" ");			
		} else {
			nnblApp.setStatusText(statusText);
		}
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
		brewbase.loadBrewsFromFile("./src/nnbldata.csv");
		System.out.println(brewbase.size() + " brews loaded from file");

		MashTempVsYeastAttenuationPanel mp = new MashTempVsYeastAttenuationPanel(
				brewbase, null);
		YeastLegendPanel nlp = new YeastLegendPanel(brewbase);

		JSplitPane splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mp,
				nlp);
		splitpane.setResizeWeight(1);
		JFrame frame = new JFrame("Testing MeasuredEfficiency Panel");
		frame.setLayout(new BorderLayout());
		frame.add(splitpane, BorderLayout.CENTER);
		frame.setMinimumSize(mp.getMinimumSize());
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
