package com.shep.anthill.model;

/*
 * 
 * @author shep
 *
 */


/*
 * larvae are little ants
 */
public class AntLarvae extends Ant {
	
	
	public AntLarvae(int a_identifier){
		
		identifier = a_identifier;
		agemax = 10;
		isLarvae = true;
		
		/*
		 * your destiny is random :)
		 * what will you become?
		 */
		int random = (int)(Math.random() * 100);
		if (random > 0 && random < 10){
			type = 2;
		}
		else if (random < 30 && random > 10){
			type = 1;
		}
		else{
			type = 0;
		}
	}
}
