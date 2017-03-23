package com.alma.platform;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Platform {

	private Monitor monitor;
	private Parser parser;
	private List<PluginDescriptor> pluginDescriptor;

	public Platform() throws IOException, NoSuchElementException, IllegalArgumentException {
		monitor = new Monitor();
		parser = new Parser();

		// Parse of extensions file
		pluginDescriptor = parser.parseFile("extensions.txt");

		// Manage monitoring...

	}

	public List<PluginDescriptor> getPluginDescriptor(Class<?> need) {

		List<PluginDescriptor> result = new ArrayList<PluginDescriptor>();

		for (PluginDescriptor plugin : pluginDescriptor) {
			if (plugin.getInterfaceName().equals(need.getName())) {
				result.add(plugin);
			}
		}

		return result;
	}

	public List<PluginDescriptor> getAutorunPlugin() {

		List<PluginDescriptor> result = new ArrayList<PluginDescriptor>();

		for (PluginDescriptor plugin : pluginDescriptor) {
			if (plugin.isAutorun() && plugin.getInterfaceName().equals("IMainPlugin")) {
				result.add(plugin);
			}
		}

		return result;
	}

	public Object getPluginInstance(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		return Class.forName(className).newInstance();
	}

	public static void main(String[] args) {
		Platform platform = null;
		Method run = null;
		
		try {
			platform = new Platform();

			for (PluginDescriptor plugin : platform.getAutorunPlugin()) {
				try {
					Object obj = platform.getPluginInstance(plugin.getClassName());
					run = obj.getClass().getMethod("");
					
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e){
			e.printStackTrace();
		} catch (IllegalArgumentException e){
			e.printStackTrace();
		}
	}

}
