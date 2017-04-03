package com.alma.application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.alma.platform.control.Platform;
import com.alma.platform.data.PluginDescriptor;

public class Board extends JPanel implements ActionListener {

	private Timer timer;
	private long startTime;
	private Roger roger;
	private ArrayList<Alien> aliens;
	private boolean ingame;
	private final int ICRAFT_X = 40;
	private final int ICRAFT_Y = 60;
	private final int B_WIDTH = 1000;
	private final int B_HEIGHT = 500;
	private final int DELAY = 15;
	private final int NBR_FIRE = 50;
	private final int GenerationDelay = 50;
	int generationOffset;
	private Background backgroundImage;
	private Background backgroundImageSwitch;
	private Random rdm;
	private int[][] pos = new int[NBR_FIRE][NBR_FIRE];
	private IHighScore highScore;
	private Integer scoretemp;

	void initPos() {
		rdm = new Random();
		for (int i = 0; i < NBR_FIRE; i++) {
			for (int j = 0; j < NBR_FIRE; j++) {
				this.pos[i][j] = rdm.nextInt(1000);
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
		// Image background = Toolkit.getDefaultToolkit().createImage(
		// "Background.png");
		// this.drawImage(background, 0, 0, null);
		backgroundImage = new Background(0, 0, "background.png");
		backgroundImageSwitch = new Background(1333, 0, "backgroundswitch.png");

		ingame = true;

		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

		roger = new Roger(ICRAFT_X, ICRAFT_Y);

		initAliens();

		timer = new Timer(DELAY, this);
		timer.start();
		generationOffset = 0;
		startTime = System.currentTimeMillis();

		scoretemp=0;
		// to get an instance of IHighScore
		try {
			List<PluginDescriptor> listHighScore = new ArrayList<PluginDescriptor>();
			listHighScore = Platform.getInstance().getListPlugin(IHighScore.class);
			
			
			if (!listHighScore.isEmpty())
				highScore = null;
				highScore = (IHighScore) Platform.getInstance().getPluginInstance(listHighScore.get(0));

		} catch (NoSuchElementException | IllegalArgumentException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initAliens() {
		aliens = new ArrayList<>();

		for (int[] p : pos) {
			if ((p[1] < 450) && (p[1] > 50))
				aliens.add(new Alien(p[0] + 1000, p[1]));
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (ingame) {
			g.drawImage(backgroundImage.getImage(), backgroundImage.getX(),
					backgroundImage.getY(), this);
			g.drawImage(backgroundImageSwitch.getImage(),
					backgroundImageSwitch.getX(), backgroundImageSwitch.getY(),
					this);
			drawObjects(g);
		} else {
			drawGameOver(g);
			highScore.DisplayNameWindow();
			
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
		// g.drawString("Monsters left: " + aliens.size(), 5, 15); inutile pour
		// une generation infini
		g.drawString("Time elapsed: "
				+ (System.currentTimeMillis() - startTime) / 1000, 5, 15);
		g.drawString("score : "+scoretemp, 5, 30); // TODO appeler score
	}

	private void drawGameOver(Graphics g) {

		String msg = "Game Over";
		Font small = new Font("Helvetica", Font.BOLD, 50);
		FontMetrics fm = getFontMetrics(small);

		g.setColor(Color.red);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 2);
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		inGame();
		scrollBackground();
		updateRoger();
		updateFireballs();
		updateAliens();

		checkCollisions();

		repaint();
	}

	private void scrollBackground() {
		backgroundImage.move();
		backgroundImageSwitch.move();

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
		/*
		 * if (aliens.isEmpty()) {//plus besoin vu que la generation des
		 * monstres sera infini
		 * 
		 * ingame = false; return; }
		 */
		for (int i = 0; i < aliens.size(); i++) {

			Alien a = aliens.get(i);
			if (a.isVisible()) {
				a.move();
			} else {
				aliens.remove(i);
			}
		}
		generationOffset++;
		if (generationOffset > GenerationDelay) {
			Random rdm = new Random();
			int p = rdm.nextInt(NBR_FIRE);
			if ((pos[p][1] < 450) && (pos[p][1] > 50)) {
				aliens.add(new Alien(pos[p][0] + 1000, pos[p][1]));
				generationOffset = 0;
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
					this.scoretemp = highScore.incrementScore();
					
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