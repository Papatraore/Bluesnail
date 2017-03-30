package com.alma.application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

	private Timer timer;
	private Roger roger;
	private ArrayList<Alien> aliens;
	private boolean ingame;
	private final int ICRAFT_X = 40;
	private final int ICRAFT_Y = 60;
	private final int B_WIDTH = 1000;
	private final int B_HEIGHT = 500;
	private final int DELAY = 15;

	private int[][] pos = new int[B_WIDTH][B_WIDTH];

	void initPos() {
		Random rdm = new Random();
		for (int i = 0; i < B_WIDTH; i++) {
			for (int j = 0; j < B_WIDTH; j++) {
				this.pos[i][j] = rdm.nextInt(2500) + 50;
			}
		}
	}

	public Board() {
		initBoard();
	}

	private void initBoard() {
		initPos();

		addKeyListener(new TAdapter());
		setFocusable(true);
		Image background = Toolkit.getDefaultToolkit().createImage(
				"Background.png");
		// this.drawImage(background, 0, 0, null);
		ingame = true;

		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

		roger = new Roger(ICRAFT_X, ICRAFT_Y);

		initAliens();

		timer = new Timer(DELAY, this);
		timer.start();
	}

	public void initAliens() {
		aliens = new ArrayList<>();

		for (int[] p : pos) {
			aliens.add(new Alien(p[0], p[1]));
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (ingame) {
			drawObjects(g);
		} else {
			drawGameOver(g);
		}

		Toolkit.getDefaultToolkit().sync();
	}

	private void drawObjects(Graphics g) {

		if (roger.isVisible()) {
			// System.out.println(roger.getImage().toString());
			g.drawImage(roger.getImage(), roger.getX(), roger.getY(), this);
		}

		ArrayList<FireBall> ms = roger.getFireBalls();

		for (FireBall m : ms) {
			if (m.isVisible()) {
				g.drawImage(m.getImage(), m.getX(), m.getY(), this);
			}
		}

		for (Alien a : aliens) {
			if (a.isVisible()) {
				g.drawImage(a.getImage(), a.getX(), a.getY(), this);
			}
		}

		g.setColor(Color.WHITE);
		g.drawString("Aliens left: " + aliens.size(), 5, 15);
	}

	private void drawGameOver(Graphics g) {

		String msg = "Game Over";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics fm = getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		inGame();

		updateRoger();
		updateFireballs();
		updateAliens();

		checkCollisions();

		repaint();
	}

	private void inGame() {

		if (!ingame) {
			timer.stop();
		}
	}

	private void updateRoger() {

		if (roger.isVisible()) {
			roger.move();

		}
	}

	private void updateFireballs() {

		ArrayList<FireBall> ms = roger.getFireBalls();

		for (int i = 0; i < ms.size(); i++) {

			FireBall m = ms.get(i);

			if (m.isVisible()) {
				m.move();
			} else {
				ms.remove(i);
			}
		}
	}

	private void updateAliens() {

		if (aliens.isEmpty()) {

			ingame = false;
			return;
		}

		for (int i = 0; i < aliens.size(); i++) {

			Alien a = aliens.get(i);
			if (a.isVisible()) {
				a.move();
			} else {
				aliens.remove(i);
			}
		}
	}

	public void checkCollisions() {

		Rectangle r3 = roger.getBounds();

		for (Alien alien : aliens) {
			Rectangle r2 = alien.getBounds();

			if (r3.intersects(r2)) {
				roger.setVisible(false);
				alien.setVisible(false);
				ingame = false;
			}
		}

		ArrayList<FireBall> ms = roger.getFireBalls();

		for (FireBall m : ms) {

			Rectangle r1 = m.getBounds();

			for (Alien alien : aliens) {

				Rectangle r2 = alien.getBounds();

				if (r1.intersects(r2)) {
					m.setVisible(false);
					alien.setVisible(false);
				}
			}
		}
	}

	private class TAdapter extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			roger.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			try {
				roger.keyPressed(e);
			} catch (InterruptedException ex) {
				Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null,
						ex);
			}
		}
	}
}