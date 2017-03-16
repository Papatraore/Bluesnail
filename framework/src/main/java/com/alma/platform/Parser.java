package com.alma.platform;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
	
	
	public List<PluginDescriptor> parseFile(String filename) throws IOException{
        
		String aLine;
        FileReader fileReader = new FileReader(filename);
		BufferedReader reader = new BufferedReader(fileReader);
		
		List<PluginDescriptor> plug =  new ArrayList<PluginDescriptor>();		
		
		while(reader.ready())
		{
			PluginDescriptor myplug =  new PluginDescriptor();
			aLine= reader.readLine();
			String[] OneAttributeOneValue = aLine.split(";");
			Boolean autorunTemp=false;
            for(int c = 0; c < OneAttributeOneValue.length; c++){
				String[] property = OneAttributeOneValue[c].split("=");
				if(property[0]=="autorun") autorunTemp=true;
	            addValueToProprety(property[0], property[1],myplug);
            }
            //if the autorun is not specified in the extension description file, we put it on 'false' by default
            if(autorunTemp==false){
            	myplug.setAutorun(false);
            }
            plug.add(myplug);			
		}
		
		reader.close();
		return plug;
		
	}
	
	public void addValueToProprety(String s1, String s2, PluginDescriptor myplug){
		switch (s1) {
		case "name":
			myplug.setName(s2);
		case "class":
			myplug.setClassName(s2);
		case "interface":
			myplug.setInterfaceName(s2);
		case "directory":
			myplug.setDirectoryPath(s2);
		case "autorun":
			if(s2=="true")
				myplug.setAutorun(true);
			else{
				myplug.setAutorun(false);
			}
			
		default:
			break;
		}
	}
	

}
