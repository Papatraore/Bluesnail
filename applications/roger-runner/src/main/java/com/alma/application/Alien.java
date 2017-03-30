package com.alma.application;

public class Alien extends Sprite {

	private final int INITIAL_X = 400;

	public Alien(int x, int y) {
		super(x, y);

		initAlien();
	}

	private void initAlien() {
		String currentDir = System.getProperty("user.dir");
		currentDir = currentDir
				.replace("platform",
						"applications/roger-runner/src/main/resources/roger1.png");
		loadImage(currentDir);
		getImageDimensions();
	}

	public void move() {

		if (x < 0) {
			x = INITIAL_X;
		}

		x -= 1;
	}
}
