package com.alma.platform.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Parser of config file which contains information about plugin. The config
 * file must respect the following syntax (on one line) : </br>
 * name=<plugin name>;class=<main class name>;interface=<interface
 * name>;directory=<path of the plugin>;autorun=<mode autorun>
 */
public class PluginParser {

	/**
	 * Parse the file given in parameter.
	 * 
	 * @param filename
	 *            The path of the config file.
	 * @return The list of plugin found in the config file with their
	 *         description.
	 * @throws IOException
	 * @throws NoSuchElementException
	 * @throws IllegalArgumentException
	 */
	public List<PluginDescriptor> parseFile(String filename)
			throws IOException, NoSuchElementException, IllegalArgumentException {
		String line = "";
		FileReader fileReader = new FileReader(filename);
		BufferedReader reader = new BufferedReader(fileReader);

		List<PluginDescriptor> result = new ArrayList<PluginDescriptor>();

		while (reader.ready()) {
			PluginDescriptor plugin = new PluginDescriptor();

			// Read a line
			line = reader.readLine();

			// Split the line with ";" and store it in properties array.
			String[] properties = line.split(";");

			for (int i = 0; i < properties.length; ++i) {
				// Split the property with "=" character
				String[] property = properties[i].split("=");

				String propName = property[0];
				String propValue = property[1];

				if (propName.equals("name")) {
					plugin.setPluginName(propValue);
				} else if (propName.equals("class")) {
					plugin.setClassName(propValue);
				} else if (propName.equals("interface")) {
					plugin.setInterfaceName(propValue);
				} else if (propName.equals("directory")) {
					plugin.setDirectoryPath(propValue);
				} else if (propName.equals("autorun")) {
					if (propValue.equals("true"))
						plugin.setAutorun(true);
				} else {
					reader.close();
					throw new NoSuchElementException(
							"Error when parsing the file, the property " + propName + "is not accepted.");
				}
			}

			if (checkPlugin(plugin)) {
				result.add(plugin);
			} else {
				reader.close();
				throw new IllegalArgumentException("Error when parsing the file, a property is missing.");
			}
		}

		reader.close();
		return result;
	}

	/*
	 * Checks if the created plugin has all the necessary informations.
	 */
	private boolean checkPlugin(PluginDescriptor plugin) {
		return plugin.getPluginName() != null && plugin.getClassName() != null && plugin.getInterfaceName() != null
				&& plugin.getDirectoryPath() != null;
	}
}
