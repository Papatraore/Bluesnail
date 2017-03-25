package com.alma.platform.control;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.alma.platform.data.PluginDescriptor;
import com.alma.platform.data.PluginParser;

/**
 * 
 */
public class Platform {

	public static final String I_MAIN_PLUGIN_PATH = "com.alma.platform.data.IMainPlugin";
	private static Platform INSTANCE;

	// Monitoring of platform
	private Monitor monitor;

	// Parser of config file
	private PluginParser parser;

	// List of available plugin
	private List<PluginDescriptor> pluginDescriptor;

	// Class loader of external class
	private ClassLoader classLoader;

	// --- CONSTRUCTOR

	/**
	 * Creates a unique instance of a Platform using the singleton pattern.
	 * 
	 * @throws IOException
	 * @throws NoSuchElementException
	 * @throws IllegalArgumentException
	 */
	private Platform() throws IOException, NoSuchElementException, IllegalArgumentException {
		monitor = new Monitor();
		parser = new PluginParser();
		pluginDescriptor = parser.parseFile("config.txt"); // Parse of
															// extensions file

		URL pluginUrls[] = new URL[pluginDescriptor.size()];
		String userDirUrl[] = System.getProperty("user.dir").split("/");
		int cpt = 0;

		// Get and treats the absolute path of the plugin directory to create
		// the ClassLoader

		for (PluginDescriptor plugin : pluginDescriptor) {

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
			++cpt;
		}

		classLoader = new URLClassLoader(pluginUrls);

		// Manage monitoring

		// TODO the monitoring

	}

	// --- PRIVATE METHODS

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
	 * @return A list of available plugin that correspond to the client need.
	 */
	public List<PluginDescriptor> getListPlugin(Class<?> need) {

		List<PluginDescriptor> result = new ArrayList<PluginDescriptor>();

		for (PluginDescriptor plugin : pluginDescriptor) {
			if (plugin.getInterfaceName().equals(need.getName()))
				result.add(plugin);
		}

		return result;
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
		for (PluginDescriptor plugin : pluginDescriptor) {
			if (plugin.isAutorun() && checkMainPlugin(plugin))
				result.add(plugin);
		}

		return result;
	}

	/**
	 * Returns an instance of a plugin.
	 * @param className The name of the plugin.
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public Object getPluginInstance(String className)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		return classLoader.loadClass(className).newInstance();
	}

}
