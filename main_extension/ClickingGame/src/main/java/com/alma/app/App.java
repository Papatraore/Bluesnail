package com.alma.app;

import java.util.Random;

import com.alma.thirdParty.Score;

/**
 * Hello world!
 *
 */
public class App 
{

	public App() {
		start();
	}
    

    public void  start() {
    	Random r = new Random();
    	int round = r.nextInt();
    	IIScore score= new Score("yo",round);
    	System.out.println(score);
    	
    }
    
}
