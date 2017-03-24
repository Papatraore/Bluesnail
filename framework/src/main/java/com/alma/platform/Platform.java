package com.alma.platform;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Platform {

	private Monitor monitor;
	private Parser parser;
	private List<PluginDescriptor> pluginDescriptor;
	private ClassLoader classLoader;

	public Platform() throws IOException, NoSuchElementException, IllegalArgumentException {
		monitor = new Monitor();
		parser = new Parser();
		
		// Parse of extensions file
		pluginDescriptor = parser.parseFile("extensions.txt");
		
		// Get and treats the absolute path of the plugin directory to create the classLoader
		URL[] pluginUrls = new URL[pluginDescriptor.size()];
		String splitUserDirUrl[] = System.getProperty("user.dir").split("/");
		int cpt = 0;
	
		for (PluginDescriptor plugin : pluginDescriptor) {
			
			String pluginUrl = "file:///";
			
			/* Note : We don't take the last fragment of the url which is the directory of the current project.
			 * 		  We assume that the plugin project directory is located next to the current directory project. 
			 */
			for(int i = 1 ; i < splitUserDirUrl.length - 1 ; ++i){
				pluginUrl += splitUserDirUrl[i] + "/";
			}
			
			// Remove the first "/" if there is one
			if(plugin.getDirectoryPath().substring(0, 1).equals("/"))
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

	// PUBLIC METHODS

	public List<PluginDescriptor> getListPlugin(Class<?> need) {

		List<PluginDescriptor> result = new ArrayList<PluginDescriptor>();

		for (PluginDescriptor plugin : pluginDescriptor) {
			
			// check first if the plugin is autorun and then check if it implements the good interface
			if (plugin.isAutorun() && plugin.getInterfaceName().equals(need.getName())) {
				result.add(plugin);
			}
		}

		return result;
	}

	public List<PluginDescriptor> getAutorunPlugin()
			throws ClassNotFoundException {

		List<PluginDescriptor> result = new ArrayList<PluginDescriptor>();

		for (PluginDescriptor plugin : pluginDescriptor) {
			if (checkMainPlugin(plugin))
				result.add(plugin);
		}

		return result;
	}

	public Object getPluginInstance(String className)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		
		return Class.forName(className).newInstance();
	}

	// PRIVATE METHODS

	private boolean checkMainPlugin(PluginDescriptor plugin)
			throws ClassNotFoundException {
		
		boolean isMainPlugin = false;
		int i = 0;

		Class<?>[] clPlugin = Class.forName(plugin.getClassName(), true, classLoader).getInterfaces();

		while (i < clPlugin.length && !isMainPlugin) {
			if (clPlugin[i].getName().equals("IMainPlugin"))
				isMainPlugin = true;

			++i;
		}

		return  plugin.getInterfaceName().equals("IMainPlugin")
				&& isMainPlugin;
	}

	// MAIN

	public static void main(String[] args) {
		Platform platform = null;
		Method run = null;

		try {
			platform = new Platform();

			for (PluginDescriptor plugin : platform.getAutorunPlugin()) {
				try {

					Object obj = platform.getPluginInstance(plugin.getClassName());
					run = obj.getClass().getMethod("run");
					run.invoke(obj);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
