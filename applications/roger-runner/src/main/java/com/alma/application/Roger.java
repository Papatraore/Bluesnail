package com.alma.application;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Roger extends Sprite {

	private int dx;
	private int dy;
	private ArrayList<FireBall> fireballs;

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
}