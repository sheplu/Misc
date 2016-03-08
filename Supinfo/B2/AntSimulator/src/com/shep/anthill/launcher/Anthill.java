package com.shep.anthill.launcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import com.shep.anthill.model.AntLarvae;
import com.shep.anthill.model.AntMale;
import com.shep.anthill.model.AntQueen;
import com.shep.anthill.model.AntWorker;
/*
 * 
 * @author shep
 *
 */


/*
 * vector needed to save in file
 */
public class Anthill extends Vector {

	protected ArrayList<AntLarvae> arr_larvae = new ArrayList<AntLarvae>();
	protected ArrayList<AntWorker> arr_workers = new ArrayList<AntWorker>();
	protected ArrayList<AntMale> arr_males = new ArrayList<AntMale>();
	protected ArrayList<AntQueen> arr_queens = new ArrayList<AntQueen>();
	
	protected int anthill_age = 0;
	protected int birth = 0;
	protected int dead = 0;
	
	/*
	 * all got one more year
	 */
	public Anthill(int nb_larva, int nb_worker, int nb_male, int nb_queen){
		for (int i = 0; i < nb_larva; i++) {
        	arr_larvae.add(new AntLarvae(anthill_age));
        	birth++;
		} 
        for (int i = 0; i < nb_worker; i++) {
        	arr_workers.add(new AntWorker(anthill_age));
        	birth++;
		}
        for (int i = 0; i < nb_male; i++) {
        	arr_males.add(new AntMale(anthill_age));
        	birth++;
		}
        for (int i = 0; i < nb_queen; i++) {
        	arr_queens.add(new AntQueen(anthill_age));
        	birth++;
		}
	}
	
	public void growUp(){
		ArrayList<AntLarvae> tmp_arr_larvae = arr_larvae;
		ArrayList<AntWorker> tmp_arr_workers = arr_workers;
		ArrayList<AntMale> tmp_arr_males = arr_males;
		ArrayList<AntQueen> tmp_arr_queens = arr_queens;
		
		arr_queens = new ArrayList<AntQueen>();
		arr_workers = new ArrayList<AntWorker>();
		arr_males = new ArrayList<AntMale>();
		arr_larvae = new ArrayList<AntLarvae>();
		
		anthill_age++;
		birth = 0;
		dead = 0;
		
		int larvae_nb = 0;
		int males_nb = 0;
		int workers_nb = 0;
		int queens_nb = 0;
		
		AntLarvae larva;
		AntWorker worker;
		AntMale male;
		AntQueen queen;
		/*
		 * check all iterator for worker, male, queen & larvae.
		 * growup little thing.
		 * calculate how many dead this day and how many are alive.
		 */
	    Iterator<AntWorker> a_worker = tmp_arr_workers.iterator();
	    while (a_worker.hasNext()) {
	    	worker = (AntWorker) a_worker.next();
	    	worker.growUp();
	    	if(worker.isAlive()){
	    		arr_workers.add(worker);
	    		workers_nb++;
	    	}else{
	    		dead++;
	    	}
	    }
	    Iterator<AntMale> a_male = tmp_arr_males.iterator();
	    while (a_male.hasNext()) {
	    	male = (AntMale) a_male.next();
	    	male.growUp();
	    	if(male.isAlive()){
	    		arr_males.add(male);
	    		males_nb++;
	    	}else{
	    		dead++;
	    	}
	    }
	    Iterator<AntQueen> a_queen = tmp_arr_queens.iterator();
	    while (a_queen.hasNext()) {
	    	queen = (AntQueen) a_queen.next();
	    	queen.growUp();
	    	if(queen.isAlive()){
	    		arr_queens.add(queen);
	    		queens_nb++;
	    		if(males_nb>0){
	    			for (int i = 0; i < 10; i++) {
	    				arr_larvae.add(new AntLarvae(anthill_age));
	    				larvae_nb++;
	    				birth++;
					}
	    		}
	    	}else{
	    		dead++;
	    	}
	    }
	    /*
	     * a larvae can't die, it become adulte
	     * what's your destiny?
	     */
	    Iterator<AntLarvae> a_larvae = tmp_arr_larvae.iterator();
	    while (a_larvae.hasNext()) {
	    	larva = (AntLarvae) a_larvae.next();
	    	larva.growUp();
	    	if(larva.isAlive()){
	    		arr_larvae.add(larva);
	    		larvae_nb++;
	    	}else{
	    		switch (larva.getType()) {
				case 2:
					arr_queens.add(new AntQueen(anthill_age));
					queens_nb++;
					break;
				case 1:
					arr_males.add(new AntMale(anthill_age));
					males_nb++;
					break;
				default:
					arr_workers.add(new AntWorker(anthill_age));
					workers_nb++;
					break;
				}
	    	}
	    }
	    /*
	     * display the results of the day.
	     */
	    System.out.println("------------  Anthill ------------");
	    System.out.println("------------   Day "+anthill_age+"  ------------");
	    System.out.println("Larvae: "+larvae_nb);
	    System.out.println("Workers: "+workers_nb);
	    System.out.println("Males: "+males_nb);
	    System.out.println("Queens: "+queens_nb);
	    
	    System.out.println("\nDead: "+dead);
	    System.out.println("Birth: "+birth);
	    System.out.println("");
	}
}