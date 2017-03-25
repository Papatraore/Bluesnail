package com.alma.platform;

import java.lang.reflect.Method;

import com.alma.platform.control.Platform;
import com.alma.platform.data.PluginDescriptor;

/**
 * Main class of the platform.
 */
public class Launcher {

	public static void main(String[] args) {
		Platform platform = null;
		Method run = null;

		try {
			platform = Platform.getInstance();

			for (PluginDescriptor plugin : platform.getAutorunPlugin()) {
				try {
					// IMainPlugin obj = (IMainPlugin)
					// platform.getPluginInstance(plugin.getClassName());
					// obj.run();

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
