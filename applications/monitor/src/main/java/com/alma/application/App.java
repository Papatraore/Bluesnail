package com.alma.application;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.alma.platform.control.IMonitor;
import com.alma.platform.control.Platform;
import com.alma.platform.data.IMainPlugin;
import com.alma.platform.data.PluginDescriptor;

/**
 * Hello world!
 *
 */
public class App implements IMainPlugin, IMonitor, Observer
{

	//private Map<PluginDescriptor,Boolean> pluginsMap;
	JFrame frame;
	JPanel panel;
	Platform platform;
	
	/* (non-Javadoc)
	 * @see com.alma.platform.control.IMonitor#displayPluginsAvailable()
	 */
	@Override
	public void displayPluginsAvailable(){
		for(Map.Entry<PluginDescriptor, Boolean> entry : platform.getPluginsMap().entrySet()) {
			String cle = entry.getKey().getPluginName();
			Boolean valeur = entry.getValue();
			System.out.println("plugin name : "+cle+" | charged :"+valeur);
		}
	}
	

	
	/**
	 * 
	 */
	@Override
	public void displayHCI(){
		for(Map.Entry<PluginDescriptor, Boolean> entry : platform.getPluginsMap().entrySet()) {
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
	/*@Override
	public void updatePlugins(String classname){
		for(Map.Entry<PluginDescriptor, Boolean> entry : pluginsMap.entrySet()) {
			if(entry.getKey().getClassName()==classname){
				entry.setValue(true);
			}
			break;
		}
	}*/

	@Override
	public void run() {
		// FIXME 
		
		
		frame = new JFrame();
		panel = new JPanel();
		this.displayHCI();
		
	}


	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}


	
    
}
