package com.alma.app;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

public class MainPanel extends JPanel {

	@Override
	public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setColor(Color.white);
		g2.fillRect(0, 0, getWidth(), getHeight());
        
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke(5));
        Line2D lin = new Line2D.Float(0, (int) (getHeight() * 0.75), getWidth(), (int) (getHeight() * 0.75));
        g2.draw(lin);
	}
}
