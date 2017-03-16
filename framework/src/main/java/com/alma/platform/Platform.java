package com.alma.platform;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Platform {

	private Monitor monitor;
	private Parser parser;
	private List<PluginDescriptor> pluginDescriptor;

	public Platform() throws IOException {
		monitor = new Monitor();
		parser = new Parser();
		pluginDescriptor = new ArrayList<PluginDescriptor>();

		// Parse of extensions file
		pluginDescriptor = parser.parseFile("extensions.txt");

		// Manage monitoring...

	}

	public List<PluginDescriptor> getPluginDescriptorFor(Class<?> need) {

		List<PluginDescriptor> result = new ArrayList<PluginDescriptor>();

		for (PluginDescriptor plugin : pluginDescriptor) {
			if (plugin.getInterfaceName().equals(need.getName())) {
				result.add(plugin);
			}
		}

		return result;
	}

	public List<PluginDescriptor> getAutorunExtension() {

		List<PluginDescriptor> result = new ArrayList<PluginDescriptor>();

		for (PluginDescriptor plugin : pluginDescriptor) {
			if (plugin.isAutorun()) {
				result.add(plugin);
			}
		}

		return result;
	}

	public Object getExtension(String mainClassName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		return Class.forName(mainClassName).newInstance();
	}

	public static void main(String[] args) {
		Platform platform;
		try {
			platform = new Platform();

			for (PluginDescriptor plugin : platform.getAutorunExtension()) {
				try {
					platform.getExtension(plugin.getClassName());
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
		}
	}

}
