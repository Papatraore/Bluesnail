package com.alma.platform.data;

/**
 * Description of a plugin. This class contains all informations about a plugin.
 *
 */
public class PluginDescriptor {

	/*
	 * Name of the plugin.
	 */
	private String pluginName;

	/*
	 * Name of the class which has to be instantiated first.
	 */
	private String className;

	/*
	 * Name of the interface which have to be implemented by the plugin.
	 */
	private String interfaceName;

	/*
	 * Directory of the maven project where the plugin is.
	 */
	private String directoryPath;

	/*
	 * True if the plugin is in autorun mode, false otherwise.
	 */
	private boolean autorun;

	/**
	 * @brief Default constructor, initialize all attributes to null or their
	 *        default value.
	 */
	public PluginDescriptor() {
		this.pluginName = null;
		this.className = null;
		this.interfaceName = null;
		this.directoryPath = null;
		this.autorun = false;
	}

	/**
	 * Returns the name of the plugin.
	 * 
	 * @return The name of the plugin.
	 */
	public String getPluginName() {
		return pluginName;
	}

	/**
	 * Set the name of the plugin.
	 * 
	 * @param name
	 *            The new name of the plugin.
	 */
	public void setPluginName(String name) {
		this.pluginName = name;
	}

	/**
	 * Returns the main class name of the plugin, ie: the class which has to be
	 * instantiated by the application (or the platform if the plugin is in
	 * autorun mode).
	 * 
	 * @return The name of the main class.
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Set the main class name of the plugin, ie: the class which has to be
	 * instantiated by the application (or the platform if the plugin is in
	 * autorun mode).
	 * 
	 * @param className
	 *            The new name of the main class.
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * Returns the name of the interface which have to be implemented by the
	 * plugin. This interface represents the need of the application.
	 * 
	 * @return The name of the interface implemented by the plugin main class.
	 */
	public String getInterfaceName() {
		return interfaceName;
	}

	/**
	 * Set the name of the interface which have to be implemented by the plugin.
	 * This interface represents the need of the application.
	 * 
	 * @param interfaceName
	 *            The new name of the interface implemented by the plugin main
	 *            class.
	 */
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	/**
	 * Returns the path of the plugin project and more precisely, the path of
	 * the compiled source (.class).
	 * 
	 * @return A string representing the path of the plugin.
	 */
	public String getDirectoryPath() {
		return directoryPath;
	}

	/**
	 * Set the path of the plugin project and more precisely, the path of the
	 * compiled source (.class).
	 * 
	 * @param directoryPath
	 *            The new path of the plugin.
	 */
	public void setDirectoryPath(String directoryPath) {
		this.directoryPath = directoryPath;
	}

	/**
	 * Returns the mode of the plugin, that is, if the plugin has to be
	 * institated by the platform or an application
	 * 
	 * @return True if the plugin is in autorun mode, false otherwise.
	 */
	public boolean isAutorun() {
		return autorun;
	}

	/**
	 * Set the mode of the plugin, that is, if the plugin has to be institated
	 * by the platform or an application
	 * 
	 * @param autorun
	 *            New mode of the plugin.
	 */
	public void setAutorun(boolean autorun) {
		this.autorun = autorun;
	}
}
