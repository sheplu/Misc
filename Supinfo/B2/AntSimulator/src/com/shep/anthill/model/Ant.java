package com.shep.anthill.model;

import java.util.Vector;

/*
 * 
 * @author shep
 *
 */


/*
 * vector needed to save in file
 */
public class Ant extends Vector{
	/*
	 * destiny:
	 * 0: worker
	 * 1: male
	 * 2: queen
	 */
	protected int identifier;
	protected int age = 0;
	protected boolean isLarvae;
	protected int type;
	protected int agemax;
	
	/*
	 * today it's your birthday, growup little thing!
	 */
	public void growUp(int days){
		if(age != -1){
			age++;
		}
	}
	
	public void growUp(){
		this.growUp(1);
	}
	
	/*
	 * check if the ant could live a new day
	 */
	public boolean isAlive(){
		if(agemax>age){
			return true;
		} 
		else {
			return false;
		}
	}
	
	/*
	 * check if the ant is a larva 
	 */
	public boolean isLarvae(){
		return isLarvae;
	}
	
	/*
	 * type and age getter
	 */
	public int getType(){
		return type;
	}
	public int getAge(){
		return age;
	}
}
