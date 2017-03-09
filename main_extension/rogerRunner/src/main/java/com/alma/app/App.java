package com.alma.app;

import javax.swing.JFrame;

public class App extends JFrame {

	public static final String interfacesPath = "com.alma.interfaces";

	private MainPanel mainPanel;

	public App() {
		setTitle("Roger runner");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
		mainPanel = new MainPanel();
		setContentPane(mainPanel);
		
		run();
	}
	
	private void run() {
		
		/*while(true){
			
		}*/
	}

	public static void main(String[] args) {
		new App();
	}
}
