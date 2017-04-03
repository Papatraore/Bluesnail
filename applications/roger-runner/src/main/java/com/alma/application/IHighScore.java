package com.alma.application;

import java.util.HashMap;

import javax.swing.JTable;

public interface IHighScore {
	
	
	public Integer incrementScore();
	public void SaveScore();
	public void DisplayNameWindow ();
	public HashMap getListHighScore();
	public void getListScoreToJtable();
	
	

}
