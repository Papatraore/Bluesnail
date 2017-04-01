package com.alma.application;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.alma.platform.control.IMonitor;
import com.alma.platform.control.Platform;
import com.alma.platform.data.IMainPlugin;
import com.alma.platform.data.PluginDescriptor;
import com.alma.platform.data.PluginState;

/**
 * 
 *
 */
public class Monitor extends JFrame implements IMainPlugin, IMonitor {

	private static final long serialVersionUID = 1L;

	private Platform platform;
	private Map<PluginDescriptor, PluginState> pluginsState;

	private GridLayout mainLayout;
	private JScrollPane statePanel;
	private JTable stateTable;
	private Object[][] stateData;
	private String[] stateHeader;

	public Monitor() {
		super("Monitoring");

		setSize(400, 600);
		setMinimumSize(new Dimension(400, 600));

		// setDefaultCloseOperation(EXIT_ON_CLOSE); // FIXME Modify the behavior
		// when closing the window...
	}

	@Override
	public void update() {
		
	}

	@Override
	public void run() {		
		try {
			platform = Platform.getInstance();
			platform.setMonitor(this);
			pluginsState = platform.getPluginsState();

			stateHeader = new String[2];
			stateData = new Object[pluginsState.size()][2];

			// Fill the header

			stateHeader[0] = "Plugin";
			stateHeader[1] = "State";

			// Fill the data

			fillDataState();

			// Construct the GUI

			mainLayout = new GridLayout(1, 1); // TODO see for using proxy...
			setLayout(mainLayout);

			stateTable = new JTable(stateData, stateHeader);
			statePanel = new JScrollPane(stateTable);

			add(statePanel);
			setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fillDataState() {
		int i = 0;
		for (Map.Entry<PluginDescriptor, PluginState> entry : pluginsState.entrySet()) {

			stateData[i][0] = entry.getKey();
			stateData[i][1] = entry.getValue();
			++i;
		}
	}
}
