package com.alma.application;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background extends Sprite {

	public Background(int x, int y) {
		super(x, y);
		initBackground();
		
	}
	
	private void initBackground() {														
		String currentDir = System.getProperty("user.dir");
		currentDir = currentDir
				.replace("platform",
						"applications/roger-runner/src/main/resources/background.png");		
		File backgroundImage=new File(currentDir);
		try {
			this.image=ImageIO.read(backgroundImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		getImageDimensions();
	}


}
