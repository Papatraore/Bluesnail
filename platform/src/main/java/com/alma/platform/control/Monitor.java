package com.alma.platform.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.alma.platform.data.PluginDescriptor;

/**
 * 
 * @author 
 *
 */
public class Monitor{

	
	private Map<PluginDescriptor,Boolean> pluginsMap;
	JFrame frame;
	JPanel panel;
	

	
	
	
	
	/**
	 * 
	 * @param p List of all available plugins given by the platform
	 */
	public Monitor(List<PluginDescriptor> p) {
		pluginsMap = new HashMap<PluginDescriptor,Boolean>();
		for(int i=0; i<p.size();i++){
			pluginsMap.put(p.get(i), false);
		}
		
		frame = new JFrame();
		panel = new JPanel();
		//this.displayPluginsAvailable();
		this.displayHCI();
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see com.alma.platform.control.IMonitor#displayPluginsAvailable()
	 */
	
	public void displayPluginsAvailable(){
		for(Map.Entry<PluginDescriptor, Boolean> entry : pluginsMap.entrySet()) {
			String cle = entry.getKey().getPluginName();
			Boolean valeur = entry.getValue();
			System.out.println("plugin name : "+cle+" | charged :"+valeur);
		}
	}
	
	/**
	 * 
	 */
	public void displayHCI(){
		for(Map.Entry<PluginDescriptor, Boolean> entry : pluginsMap.entrySet()) {
			String cle = entry.getKey().getPluginName();
			Boolean valeur = entry.getValue();
				JLabel l = new JLabel("plugin name : "+cle+" | charged :"+valeur);
				panel.add(l);
				frame.getContentPane().add(panel); //add the panel to the frame
		        frame.pack();
				frame.setVisible(true);
		}
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see com.alma.platform.control.IMonitor#updatePlugins(java.lang.String)
	 */
	
	public void updatePlugins(String classname){
		for(Map.Entry<PluginDescriptor, Boolean> entry : pluginsMap.entrySet()) {
			if(entry.getKey().getClassName()==classname){
				entry.setValue(true);
			}
			break;
		}
		//frame.revalidate();
		//frame.repaint();
	}

	
	
	
	
	
	
}
