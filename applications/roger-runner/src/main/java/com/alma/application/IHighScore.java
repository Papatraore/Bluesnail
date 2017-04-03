package com.alma.application;

import java.util.HashMap;

public interface IHighScore {

	public Integer incrementScore();

	public void SaveScore();

	public void DisplayNameWindow();

	public HashMap<String, Integer> getListHighScore();

	public void getListScoreToJtable();

}
