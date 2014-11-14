package org.nnbl.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.nnbl.model.NNBLBrewDatabase;

public class MenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	private JMenu fileMenu;
	private JMenuItem fileMenuOpen;
	private JMenuItem fileMenuExit;
	private JMenu helpMenu;
	private JMenuItem helpMenuAbout;
	private JFileChooser fc;

	public MenuBar(NNBLBrewDatabase db, NNBLApp nnblApp) {
		fileMenu = new JMenu("File");
		helpMenu = new JMenu("Help");

		fc = new JFileChooser(".");
		fileMenuOpen = new JMenuItem("Open");
		fileMenuOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				InputEvent.CTRL_MASK));

		fileMenuOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int retval = fc.showOpenDialog(null);

				if (retval == JFileChooser.APPROVE_OPTION) {
					db.loadBrewsFromFile(fc.getSelectedFile().toString());
					nnblApp.repaint();
				}
			}
		});

		fileMenuExit = new JMenuItem("Exit");
		fileMenuExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				InputEvent.CTRL_MASK));
		fileMenuExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		helpMenuAbout = new JMenuItem("About");
		helpMenuAbout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(
								null,
								"This is a viewer for the data collected during NNBL´s brews.\n"
								+ "The data format for the file is:\n"
								+ "Brewer,Name,Grainweight,Efficiency,OG,FG,Mash temperature,Attenuation,Yeast type,Remarks",
								"NNBL data viewer",
								JOptionPane.INFORMATION_MESSAGE);
			}
		});

		helpMenu.add(helpMenuAbout);
		fileMenu.add(fileMenuOpen);
		fileMenu.addSeparator();
		fileMenu.add(fileMenuExit);
		add(fileMenu);
		add(helpMenu);
	}
}
