package com.shep.anthill.model;

/*
 * 
 * @author shep
 *
 */


/*
 * male are ants
 */
public class AntMale extends Ant {
	public AntMale(int a_identifier) {
		identifier = a_identifier;
		isLarvae = false;
		type = 1;
		agemax = 20;
	}
}
