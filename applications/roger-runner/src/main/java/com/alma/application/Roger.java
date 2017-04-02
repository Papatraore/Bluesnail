package com.alma.application;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Roger extends Sprite {

	private int dx;
	private int dy;
	private ArrayList<FireBall> fireballs;
	protected Image image1;
	protected Image image2;
	protected Image image3;
	private int swapAnimation;
	

	public Roger(int x, int y) {
		super(x, y);
		
		initRoger();
	}

	private void initRoger() {
		String currentDir = System.getProperty("user.dir");
		currentDir = currentDir
				.replace("platform",
						"applications/roger-runner/src/main/resources/running_phase0.png");				
		loadImage(currentDir);
		currentDir = System.getProperty("user.dir");
		currentDir = currentDir
				.replace("platform",
						"applications/roger-runner/src/main/resources/running_phase0.png");				
		loadImage1(currentDir);
		
		currentDir = System.getProperty("user.dir");		
		currentDir = currentDir
				.replace("platform",
						"applications/roger-runner/src/main/resources/running_phase1.png");				
		loadImage2(currentDir);
		currentDir = System.getProperty("user.dir");
		currentDir = currentDir
				.replace("platform",
						"applications/roger-runner/src/main/resources/running_phase2.png");				
		loadImage3(currentDir);
		swapAnimation=0;
		
		fireballs = new ArrayList<>();
		getImageDimensions();
	}

	public void move() {

		x += dx;
		y += dy;

		if (x < 1) {
			x = 1;
		}
				

		if (y <400 && dy==0) {
			if(y<396){
			y=y+4;
			}else{
				y=400;
			}
		}
		
		if(y>400 && dy==0){
			y=y-2;
			
		}
		
		if(y>420){
			y=420;
		}
		
		animate();
		
	}

	private void animate() {
		this.swapAnimation++;
		switch (this.swapAnimation){
		case 8:
			image=image1;			
			break;
			
		case 16:
			image=image2;			
			break;
			
		case 24:
			image=image3;			
			break;
					
		
		}		
		if(swapAnimation>=24){
			swapAnimation=0;			
		}
		
		
	}

	public void move(int bx, int by) {

		x += bx;
		y += by;

		if (x < 1) {
			x = 1;
		}

		if (y < 1) {
			y = 1;
		}
	}

	public ArrayList getFireBalls() {
		return fireballs;
	}

	public void keyPressed(KeyEvent e) throws InterruptedException {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_SPACE) {
			fire();
		}

		if (key == KeyEvent.VK_LEFT) {
			dx = -2;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 2;
		}

		if (key == KeyEvent.VK_UP) {
			dy = -5;
		}

		if (key == KeyEvent.VK_DOWN) {
			dy = 1;
		}
	}

	public void fire() {
		fireballs.add(new FireBall(x + width, y + height / 2));
	}

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_UP) {
			dy = 0;
		}

		if (key == KeyEvent.VK_DOWN) {
			dy = 0;
		}
	}
	
	protected void loadImage1(String imageName) {
		ImageIcon ii = new ImageIcon(imageName);
		image1 = ii.getImage();
	}
	
	protected void loadImage2(String imageName) {
		ImageIcon ii = new ImageIcon(imageName);
		image2 = ii.getImage();
	}
	
	
	protected void loadImage3(String imageName) {
		ImageIcon ii = new ImageIcon(imageName);
		image3 = ii.getImage();
	}
	
	
}