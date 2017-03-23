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
	private URL[] pluginUrl;

	public Platform() throws IOException, NoSuchElementException, IllegalArgumentException {
		monitor = new Monitor();
		parser = new Parser();
			
		// Parse of extensions file
		pluginDescriptor = parser.parseFile("extensions.txt");
		pluginUrl = new URL[pluginDescriptor.size()];
		
		for (int i = 0 ; i < pluginUrl.length ; ++i){
			pluginUrl[i] = new URL("file://" + System.getProperty("user.dir") + "/" + pluginDescriptor.get(i).getDirectoryPath());
			System.out.println("file://" + System.getProperty("user.dir") + "/" + pluginDescriptor.get(i).getDirectoryPath());
		}
		
		classLoader = new URLClassLoader(pluginUrl);
 		
		// Manage monitoring
		
		// TODO the monitoring

	}

	// PUBLIC METHODS

	public List<PluginDescriptor> getPluginDescriptor(Class<?> need) {

		List<PluginDescriptor> result = new ArrayList<PluginDescriptor>();

		for (PluginDescriptor plugin : pluginDescriptor) {
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
		// FIXME config check before loading
		System.out.println("CLASSE NAME " + plugin.getClassName());

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

					Object obj = platform.getPluginInstance(plugin
							.getClassName());
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
