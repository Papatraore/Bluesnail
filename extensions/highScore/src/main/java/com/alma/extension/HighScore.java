package com.alma.extension;

import java.util.HashMap;

import com.alma.application.IHighScore;


/**
 *
 * 
 */
public class HighScore implements IHighScore
{
	
	
	private HashMap<String,Integer> listHighScore;
	private int score;
	
	
	
	public HighScore(){
		this.listHighScore = new HashMap<String,Integer>();
		this.score=0;
	}
	
	
	public void SaveScore(){
		
	}
	
	
	
	public Integer incrementScore(){
		 this.score++;
		 return this.score;
	}
	
	
	public Integer getScore(){
		return this.score;
	}
	
	
	
	
}
