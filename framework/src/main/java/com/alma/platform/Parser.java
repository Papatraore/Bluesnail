package com.alma.platform;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
	
	
	public List<PluginDescriptor> parseFile(String filename) throws IOException{
        String aLine;

        FileReader fileReader = new FileReader(filename);
		BufferedReader reader = new BufferedReader(fileReader);
		
		List<PluginDescriptor> plug =  new ArrayList<PluginDescriptor>();
		
		aLine= reader.readLine();
		
		while(reader.ready())
		{
			
		}
		
		
		
		return null;
		
	}
	

}
