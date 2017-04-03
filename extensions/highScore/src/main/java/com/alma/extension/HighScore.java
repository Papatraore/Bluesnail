package com.alma.extension;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.alma.application.IHighScore;


/**
 *Extension to manage the HighScore
 * 
 */
public class HighScore extends JFrame implements IHighScore, ActionListener 
{
	
	
	/**
	 * attributes about frame
	 */
	private JPanel container = new JPanel();
	private JFormattedTextField jtf = new JFormattedTextField();
	private JLabel label = new JLabel("Player's name :");
	private JButton b = new JButton ("OK");

	
	
	
	/**
	 * attributes about HighScore and score
	 */
	private HashMap<String,Integer> listHighScore;
	private int score;
	
	
	
	
	/**
	 * constructor
	 */
	public HighScore(){
		this.listHighScore = new HashMap<String,Integer>();
		this.score=0;
	}
	
	
	
	/**
	 * save the score in the hashmap
	 */
	public void SaveScore(){
		
	}
	
	
	/**
	 * increment score
	 */
	public Integer incrementScore(){
		 this.score++;
		 return this.score;
	}
	
	
	
	/**
	 * getter on score
	 * @return score
	 */
	public Integer getScore(){
		return this.score;
	}
	
	
	
	public HashMap getListHighScore(){
		return this.listHighScore;
	}
	
	
	
	
	/**
	 * Display the window to get the name of the player
	 */
	public void DisplayNameWindow (){
		this.setTitle("Animation");
	    this.setSize(300, 100);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    container.setBackground(Color.white);
	    container.setLayout(new BorderLayout());
	    JPanel top = new JPanel();        
	    Font police = new Font("Arial", Font.BOLD, 14);
	    jtf.setFont(police);
	    jtf.setPreferredSize(new Dimension(150, 30));
	    b.addActionListener(this);
	    top.add(label);
	    top.add(jtf);
	    top.add(b);
	    this.setContentPane(top);
	    this.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.listHighScore.put(jtf.getText(), score);
		dispose();	
	}
	
	
	
	
}
