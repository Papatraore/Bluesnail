package com.alma.app;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

public class MainPanel extends JPanel {
	
	private String rogerPath;
	
	public MainPanel(String rogerPath){
		this.rogerPath = rogerPath;
	}

	@Override
	public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setColor(Color.white);
		g2.fillRect(0, 0, getWidth(), getHeight());
		
		/*Image roger = Toolkit.getDefaultToolkit().getImage(rogerPath);
		g2.drawImage(roger, 0, 0, this);
	    g2.finalize();*/
        
		Ellipse2D circle = new Ellipse2D.Double(0,0,50,50);
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke(5));
		g2.draw(circle);
		g2.finalize();
		
		Line2D line = new Line2D.Float(0, (int) (getHeight() * 0.75), getWidth(), (int) (getHeight() * 0.75));
        g2.draw(line);
        g2.finalize();
	}
	
	public String getRogerPath() {
		return rogerPath;
	}

	public void setRogerPath(String rogerPath) {
		this.rogerPath = rogerPath;
	}
}
