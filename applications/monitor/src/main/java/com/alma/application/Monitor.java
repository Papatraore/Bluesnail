package com.alma.application;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

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

	private JScrollPane stateScrollPanel;
	private JTable stateTable;
	private Object[][] stateData;
	private String[] stateHeader;

	private JScrollPane logScrollPanel;
	private JTextArea logTextArea;
	private List<String> logData;

	public Monitor() {
		super("Monitoring");
	}

	@Override
	public void update() {
		fillDataState();

		if (!logData.isEmpty())
			logTextArea.append(logData.get(logData.size() - 1) + "\n");

		repaint();
		revalidate();
	}

	@Override
	public void run() {
		try {
			platform = Platform.getInstance();
			platform.addMonitor(this);
			pluginsState = platform.getPluginsState();

			stateHeader = new String[2];
			stateData = new Object[pluginsState.size()][2];

			// Init data

			stateHeader[0] = "Plugin";
			stateHeader[1] = "State";
			fillDataState();

			logData = platform.getLog();

			// Construct the GUI

			setSize(400, 600);
			setMinimumSize(new Dimension(400, 600));
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			addWindowListener(new java.awt.event.WindowAdapter() {
				@Override
				public void windowClosing(java.awt.event.WindowEvent windowEvent) {
					removeMonitor();
					dispose();
				}
			});

			mainLayout = new GridLayout(2, 1);
			setLayout(mainLayout);

			stateTable = new JTable(stateData, stateHeader);
			stateScrollPanel = new JScrollPane(stateTable);

			logTextArea = new JTextArea();
			logTextArea.setEditable(false);
			logScrollPanel = new JScrollPane(logTextArea);

			add(stateScrollPanel);
			add(logScrollPanel);
			setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fillDataState() {
		int i = 0;
		for (Map.Entry<PluginDescriptor, PluginState> entry : pluginsState.entrySet()) {

			stateData[i][0] = entry.getKey().getPluginName();
			stateData[i][1] = entry.getValue();
			++i;
		}
	}

	private void removeMonitor() {
		platform.removeMonitor(this);
	}
}
