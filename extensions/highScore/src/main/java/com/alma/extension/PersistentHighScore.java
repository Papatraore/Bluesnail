package com.alma.extension;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PersistentHighScore {

	private String scoreFile;

	public PersistentHighScore() {
		String currentDir = System.getProperty("user.dir");
		scoreFile = currentDir.replace("platform", "extensions/highScore/src/main/resources/scores.txt");
	}

	public void saveScore(HashMap<String, Integer> scores) throws IOException {
		FileWriter fileWritter = new FileWriter(scoreFile);
		BufferedWriter writter = new BufferedWriter(fileWritter);
		
		for (Map.Entry<String, Integer> entry : scores.entrySet()) {
			String score = entry.getKey() + ":" + entry.getValue();
			writter.write(score);
			writter.newLine();
		}
		
		writter.close();
	}

	public HashMap<String, Integer> getScore() throws IOException {
		HashMap<String, Integer> result = new HashMap<String, Integer>();		
		
		String line = "";
		FileReader fileReader = new FileReader(scoreFile);
		BufferedReader reader = new BufferedReader(fileReader);
		
		while(reader.ready()){
			line = reader.readLine();
			
			if(!line.equals("")){
				String[] properties = line.split(":");
				result.put(properties[0], Integer.parseInt(properties[1]));
			}			
		}
		
		reader.close();

		return result;
	}

}
