package com.alma.platform.control;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.alma.platform.data.PluginDescriptor;
import com.alma.platform.data.PluginParser;
import com.alma.platform.data.PluginState;

/**
 * This class represents the BlueSnail platform and is used to manage the
 * different applications/plugin created by user.
 */
public class Platform {

	/**
	 * Class url of the main plugin interface.
	 */
	public static final String I_MAIN_PLUGIN_PATH = "com.alma.platform.data.IMainPlugin";

	/*
	 * Instance of the platform
	 */
	private static Platform INSTANCE;

	// Monitoring of platform
	private IMonitor monitor;

	// Parser of config file
	private PluginParser parser;

	// List of available plugin
	private List<PluginDescriptor> plugins;

	// Class loader of external class
	private ClassLoader classLoader;

	// list of plugins' states
	private Map<PluginDescriptor, PluginState> pluginsState;

	// --- CONSTRUCTOR

	/**
	 * Creates an instance of a Platform using the singleton pattern.
	 * 
	 * @throws IOException
	 * @throws NoSuchElementException
	 * @throws IllegalArgumentException
	 */
	private Platform() throws IOException, NoSuchElementException, IllegalArgumentException {
		pluginsState = new HashMap<PluginDescriptor, PluginState>();
		parser = new PluginParser();
		plugins = parser.parseFile("config.txt"); // Parse of extensions file

		URL pluginUrls[] = new URL[plugins.size()];
		String userDirUrl[] = System.getProperty("user.dir").split("/");
		int cpt = 0;

		/*
		 * Get and treats the absolute path of the plugin directory to create
		 * the ClassLoader and add each plugin in the plugins state map.
		 */

		for (PluginDescriptor plugin : plugins) {

			String pluginUrl = "file:///";

			/*
			 * Note : We don't take the last fragment of the url which is the
			 * directory of the current project. We assume that the plugin
			 * project directory is located next to the current directory
			 * project.
			 */
			for (int i = 1; i < userDirUrl.length - 1; ++i) {
				pluginUrl += userDirUrl[i] + "/";
			}

			// Removes the first "/" if there is one.

			if (plugin.getDirectoryPath().substring(0, 1).equals("/"))
				pluginUrl += plugin.getDirectoryPath().substring(1, plugin.getDirectoryPath().length());
			else
				pluginUrl += plugin.getDirectoryPath();

			pluginUrls[cpt] = new URL(pluginUrl);

			// Add the plugin in the pluginMap

			pluginsState.put(plugin, PluginState.UNLOADED);

			++cpt;
		}

		classLoader = new URLClassLoader(pluginUrls);
	}

	/*
	 * Checks if the plugin given in parameter implements the IMainPlugin
	 * interface.
	 */
	private boolean checkMainPlugin(PluginDescriptor plugin) throws ClassNotFoundException {

		boolean isMainPlugin = false;
		int i = 0;

		Class<?>[] clPlugin = Class.forName(plugin.getClassName(), true, classLoader).getInterfaces();

		while (i < clPlugin.length && !isMainPlugin) {
			if (clPlugin[i].getName().equals(I_MAIN_PLUGIN_PATH))
				isMainPlugin = true;

			++i;
		}

		return plugin.getInterfaceName().equals(I_MAIN_PLUGIN_PATH) && isMainPlugin;
	}

	// --- PUBLIC METHODS

	/**
	 * Returns the unique instance of the platform.
	 * 
	 * @return The unique platform instance.
	 * @throws NoSuchElementException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public static Platform getInstance() throws NoSuchElementException, IllegalArgumentException, IOException {
		if (INSTANCE == null) {
			synchronized (Platform.class) {
				if (INSTANCE == null) {
					INSTANCE = new Platform();
				}
			}
		}

		return INSTANCE;
	}

	/**
	 * Returns the list of available plugin with their description from a client
	 * need.
	 * 
	 * @param need
	 *            The need of the application. The need is represented by an
	 *            interface that the plugin must implement.
	 * @return A list of available plugin which corresponds to the client need.
	 */
	public List<PluginDescriptor> getListPlugin(Class<?> need) {

		List<PluginDescriptor> result = new ArrayList<PluginDescriptor>();

		for (PluginDescriptor plugin : plugins) {
			if (plugin.getInterfaceName().equals(need.getName()))
				result.add(plugin);
		}

		return result;
	}

	/**
	 * Returns the list of all available plugins with their description.
	 * 
	 * @return A list which contains all available plugins.
	 */
	public List<PluginDescriptor> getListPlugin() {
		return plugins;
	}

	/**
	 * Returns the list of autorun plugin from the available plugin, with their
	 * description.
	 * 
	 * @return A list of autorun plugin.
	 * @throws ClassNotFoundException
	 */
	public List<PluginDescriptor> getAutorunPlugin() throws ClassNotFoundException {

		List<PluginDescriptor> result = new ArrayList<PluginDescriptor>();

		/*
		 * Checks first if the plugin is autorun and then check if it implements
		 * the good interface
		 */
		for (PluginDescriptor plugin : plugins) {
			if (plugin.isAutorun() && checkMainPlugin(plugin))
				result.add(plugin);
		}

		return result;
	}

	/**
	 * Returns an instance of a plugin.
	 * 
	 * @param className
	 *            The name of the plugin.
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public Object getPluginInstance(PluginDescriptor plugin) {

		Object newInstance = null;

		try {
			newInstance = Class.forName(plugin.getClassName(), true, classLoader).newInstance();
			pluginsState.put(plugin, PluginState.LOADED);

		} catch (ClassNotFoundException e) {
			pluginsState.put(plugin, PluginState.ERROR);
		} catch (InstantiationException e) {
			pluginsState.put(plugin, PluginState.ERROR);
		} catch (IllegalAccessException e) {
			pluginsState.put(plugin, PluginState.ERROR);
		}

		notifyMonitor();

		return newInstance;
	}

	/**
	 * Returns the map which contains the state for each plugin. The different
	 * states are defined in the PluginState class.
	 * 
	 * @return A map which contains each plugin (key) and their state (value).
	 */
	public Map<PluginDescriptor, PluginState> getPluginsState() {
		return pluginsState;
	}

	/**
	 * Set the current monitor. The monitor is used by the platform to manage
	 * the state of each plugin.
	 * 
	 * @param monitor
	 *            An available monitor that implement IMonitor.
	 */
	public void setMonitor(IMonitor pMonitor) {
		monitor = pMonitor;
	}

	/**
	 * Update the monitor if there is one.
	 */
	public void notifyMonitor() {
		if (monitor != null)
			monitor.update();
	}

}
