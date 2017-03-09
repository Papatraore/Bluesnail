package com.alma.monitor;

import com.alma.plugin.Plugin;

public class Monitor {

    private static Monitor instance;
    //private Map<String, PluginInfos> extensions;


    
	public Monitor() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public static Monitor getInstance() {
		return instance;
	}

	public static void setInstance(Monitor instance) {
		Monitor.instance = instance;
	}
	
	public void registerPlugin(Plugin plugin) {
		//TODO
	}


	public void reportNewInstance(String extension_name, String string) {
		// TODO Auto-generated method stub
		
	}

}
