package com.alma.application;

public class FireBall extends Sprite {

	private final int BOARD_WIDTH = 990;
	private final int FIREBALL_SPEED = 9;

	public FireBall(int x, int y) {
		super(x, y);

		initFireBall();
	}

	private void initFireBall() {
		String currentDir = System.getProperty("user.dir");
		currentDir = currentDir
				.replace("platform",
						"applications/roger-runner/src/main/resources/fireball1.png");
		loadImage(currentDir);

		getImageDimensions();
	}

	public void move() {

		x += FIREBALL_SPEED;

		if (x > BOARD_WIDTH) {
			vis = false;
		}
	}
}