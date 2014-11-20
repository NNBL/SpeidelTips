package org.nnbl.utils;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class ColorTest extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public ColorTest() {
		add(new PaintPanel(), BorderLayout.CENTER);
		setSize(500, 470);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		ColorTest ct = new ColorTest();
		ct.setVisible(true);
	}
}
