package com.alma.platform;

public class PluginDescriptor {

	private String name;
	private String className;
	private String interfaceName;
	private String directoryPath;
	private boolean autorun;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getDirectoryPath() {
		return directoryPath;
	}
	public void setDirectoryPath(String directoryPath) {
		this.directoryPath = directoryPath;
	}
	public boolean isAutorun() {
		return autorun;
	}
	public void setAutorun(boolean autorun) {
		this.autorun = autorun;
	}	
}
