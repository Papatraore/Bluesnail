package com.alma.plugin;

import java.util.Properties;

/**
 * the plugin class
 */
public class Plugin {

	/**
	 * 
	 */
    private Properties properties;

    public Plugin() {
        properties = new Properties();
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getName() {
        return properties.getProperty("name");
    }

    public String getInterface() {
        return properties.getProperty("interface");
    }

    public String getClassName() {
        return properties.getProperty("class");
    }

    public String getDirectory() {
        return properties.getProperty("directory");
    }

    
    /**
     * 
     * @param option_name
     * @return
     */
    public boolean hasOption(String option_name) {
        return properties.containsKey(option_name);
    }

    
    /**
     * 
     * @param option_name
     * @return
     */
    public String getOption(String option_name) {
        return properties.getProperty(option_name);
    }
}
