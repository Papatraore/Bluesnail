package com.alma.application;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background extends Sprite {

	public Background(int x, int y, String resourcesName) {
		super(x, y);
		initBackground(resourcesName);
		
	}
	
	private void initBackground(String filename) {														
		String currentDir = System.getProperty("user.dir");
		currentDir = currentDir
				.replace("platform",
						"applications/roger-runner/src/main/resources/"+filename);		
		File backgroundImage=new File(currentDir);
		try {
			this.image=ImageIO.read(backgroundImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		getImageDimensions();
	}
	
	
	public void move() {
		x=x-2;
		if(x<=-1331){
			x=1333;
		}
		
		
	}


}
