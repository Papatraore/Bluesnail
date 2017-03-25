package com.alma.platform.data;

/**
 * Interface that each autorun plugin must implement.
 */
public interface IMainPlugin {
	
	/**
	 * This method is the entry point of an autorun plugin.
	 * BlueSnail framework will automatically call this method following 
	 * the creation of the autorun plugin.
	 */
	void run();
}