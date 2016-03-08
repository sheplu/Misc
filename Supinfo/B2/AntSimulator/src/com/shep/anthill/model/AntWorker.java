package com.shep.anthill.model;

/*
 * 
 * @author shep
 * 
 */


/*
 * workers are ants
 */
public class AntWorker extends Ant {
	public AntWorker(int a_identifier) {
		identifier = a_identifier;
		isLarvae = false;
		type = 0;
		agemax = 50;
	}
}
