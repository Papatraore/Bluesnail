package com.alma.app;

import javax.swing.JFrame;

public class App extends JFrame {

	public static final String interfacesPath = "com.alma.interfaces";

	private MainPanel mainPanel;

	public App() {
		setTitle("Roger runner");
		setSize(700, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		mainPanel = new MainPanel("src/main/resources/roger_1.png");
		setContentPane(mainPanel);

		run();
	}

	private void run() {
		for (;;) {
			// IMAGE 1
			mainPanel.setRogerPath("src/main/resources/roger_1.png");
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			mainPanel.repaint();

			// IMAGE 2
			mainPanel.setRogerPath("src/main/resources/roger_2.png");
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			mainPanel.repaint();

			// IMAGE 3
			mainPanel.setRogerPath("src/main/resources/roger_3.png");
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			mainPanel.repaint();
		}
	}

	
	// TODO USE THE PLATFORM
	// FIXME c'est dégueulasse
	public static void main(String[] args) {
		new App();
	}
}
