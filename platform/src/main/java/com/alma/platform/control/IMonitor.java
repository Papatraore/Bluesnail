package com.alma.platform.control;

public interface IMonitor {

	public abstract void displayPluginsAvailable();
	public void displayHCI();

	/**
	 * 
	 * @param classname classname of the launched plugin 
	 */
	//public abstract void updatePlugins(String classname);

}