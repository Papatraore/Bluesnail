package com.alma.platform.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class PluginParser {

	public List<PluginDescriptor> parseFile(String filename)
			throws IOException, NoSuchElementException, IllegalArgumentException {
		String line = "";
		FileReader fileReader = new FileReader(filename);
		BufferedReader reader = new BufferedReader(fileReader);

		List<PluginDescriptor> result = new ArrayList<PluginDescriptor>();

		while (reader.ready()) {
			PluginDescriptor plugin = new PluginDescriptor();

			line = reader.readLine(); // Read a line
			String[] properties = line.split(";"); // Split the line with ";"
													// and store it in
													// properties array.

			for (int i = 0; i < properties.length; ++i) {
				String[] property = properties[i].split("="); // Split the
																// property with
																// "="
				String propName = property[0];
				String propValue = property[1];

				if (propName.equals("name")) {
					plugin.setName(propValue);
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

	private boolean checkPlugin(PluginDescriptor plugin) {
		return plugin.getName() != null && plugin.getClassName() != null && plugin.getInterfaceName() != null
				&& plugin.getDirectoryPath() != null;
	}
}
