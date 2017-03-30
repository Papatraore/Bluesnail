package com.alma.application;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.alma.platform.data.IMainPlugin;

public class App extends JFrame implements IMainPlugin {

	private JPanel contentPanel;
	private JPanel mainPanel;

	public App() {
		// on crée le panel container
		contentPanel = new JPanel();
		contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// on crée le panel principal
		mainPanel = new JPanel();

		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		mainPanel.setLayout(new GridLayout(2, 1));
		// on init le panel principal
		initMainPanel();
		contentPanel.add(mainPanel);

		// on ajoute le main pannel et on configure le reste de la frame
		add(new Board());
		pack();
		setTitle("Roger");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void initMainPanel() {

		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

		JMenuItem eMenuItem = new JMenuItem("Exit");
		eMenuItem.setMnemonic(KeyEvent.VK_E);
		eMenuItem.setToolTipText("Exit application");
		eMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});

		file.add(eMenuItem);
		menubar.add(file);
		setJMenuBar(menubar);
		mainPanel.add(new Board());
		setSize(new Dimension(1000, 500));
                setResizable(false);
		pack();

		setTitle("Collision");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
