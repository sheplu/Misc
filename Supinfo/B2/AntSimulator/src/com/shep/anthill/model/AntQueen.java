package com.shep.anthill.model;

/*
 * 
 * @author shep
 *
 */



/*
 * queens are ants
 */
public class AntQueen extends Ant {
	public AntQueen(int a_identifier) {
		identifier = a_identifier;
		isLarvae = false;
		type = 2;
		agemax = 50;

	}
}
