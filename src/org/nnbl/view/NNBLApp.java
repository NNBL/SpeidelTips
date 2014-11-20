package org.nnbl.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSplitPane;

import org.nnbl.model.NNBLBrewDatabase;

public class NNBLApp extends JFrame {
	private JLabel statusBar;
	private static final long serialVersionUID = 1L;

	public void setStatusText(String text) {
		statusBar.setText(text);
	}

	public NNBLApp(NNBLBrewDatabase db) {
		setTitle("NNBL Data viewer 1.2\u03B2");
		MeasuredEfficiencyPanel mep = new MeasuredEfficiencyPanel(db, this);
		NameLegendPanel nlp = new NameLegendPanel(db);
		MashTempVsYeastAttenuationPanel map = new MashTempVsYeastAttenuationPanel(
				db, this);
		YeastLegendPanel ylp = new YeastLegendPanel(db);
		MenuBar mb = new MenuBar(db, this);
		statusBar = new JLabel(" ");

		setJMenuBar(mb);

		JSplitPane hsUpper = new JSplitPane(1, mep, nlp);
		JSplitPane hsLower = new JSplitPane(1, map, ylp);
		hsUpper.setResizeWeight(1f);
		hsUpper.setDividerSize(6);
		hsLower.setResizeWeight(1f);
		hsLower.setDividerSize(6);
		JSplitPane vs = new JSplitPane(0, hsUpper, hsLower);
		vs.setResizeWeight(0.5);
		vs.setDividerSize(6);
		add(vs, BorderLayout.CENTER);
		add(statusBar, BorderLayout.SOUTH);

		setMinimumSize(new Dimension(500, 500));
		setSize(700, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		NNBLBrewDatabase brewbase = new NNBLBrewDatabase();
		brewbase.loadBrewsFromFile("./nnbldata.csv");
		System.out.println(brewbase.size() + " brews loaded from file");
		NNBLApp app = new NNBLApp(brewbase);
		app.setVisible(true);
	}
}
