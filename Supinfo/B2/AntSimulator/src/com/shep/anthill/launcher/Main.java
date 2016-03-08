package com.shep.anthill.launcher;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.Scanner;


public class Main {

	public static void main(String[] args){
		int go_to_futur = 0;
		boolean and_again = true;
		
		System.out.println("Anthill");
		System.out.println("by Jean BURELLIER");
		Scanner sc = new Scanner(System.in);
		
		
		
		System.out.println("Menu");
		System.out.println("[N] New");
		System.out.println("[C] Continue");
		System.out.println("[Q] Quit");
		
		String menu = sc.nextLine();
		while(!menu.equalsIgnoreCase("N") && !menu.equalsIgnoreCase("C") && !menu.equalsIgnoreCase("Q")){
			System.out.println("Please select a correct option in the menu ");
			System.out.println("[N] New");
			System.out.println("[C] Continue");
			System.out.println("[Q] Quit");
			menu = sc.nextLine();
			
		}

		if(menu.equalsIgnoreCase("N")){		// new game
		
			System.out.print("Larvae: ");
			while(!sc.hasNextInt()){		//ask larvae's number
				System.out.println("Error! Please choose a number of larvae: ");
				System.out.print("Larvae: ");
				sc.nextLine();
			}
			int larvae = sc.nextInt();
			if(larvae < 0)
				larvae = 0;			//no negative ants :)
			sc.nextLine();
			
			System.out.print("Workers: ");
			while(!sc.hasNextInt()){		//ask larvae's number
				System.out.println("Error! Please choose a number of worker: ");
				System.out.print("Workers: ");
				sc.nextLine();
			}
			int workers = sc.nextInt();
			if(workers < 0)
				workers = 0;		//no negative ants :)
			sc.nextLine();
			
			System.out.print("Males: ");
			while(!sc.hasNextInt()){		//ask male's number
				System.out.println("Error! Please choose a number of male: ");
				System.out.print("Males: ");
				sc.nextLine();
			}
			int males = sc.nextInt();		
			if(males < 0)		
				males = 0;		//no negative ants :)
			sc.nextLine();
			
			System.out.print("Queens: ");
			while(!sc.hasNextInt()){		//ask queen's number
				System.out.println("Error! Please choose a number of queen: ");
				System.out.print("Queens: ");
				sc.nextLine();
			}
			int queens = sc.nextInt();		
			if(queens < 0)
				queens = 0;		//no negative ants :)
			sc.nextLine();
			
			
			
	
			/*
			 * display results of the day
			 */
			System.out.println("------------  Anthill ------------");
		    System.out.println("------------   Day 0  ------------");
			System.out.println("Larvae: "+larvae);
			System.out.println("Workers: "+workers);
			System.out.println("Males: "+males);
			System.out.println("Queens: "+queens);
			
			System.out.println("\nToday I created you ! No death. No birth! \n");
	
			Anthill anthill = new Anthill(larvae, workers, males, queens);		//create the anthill
			
			/*
			 * loop for the days
			 */
			while(and_again){
				System.out.print("Go to the future! How many day?: ");
				while(!sc.hasNextInt())
				{
					String choice = sc.next();
					if (choice.equalsIgnoreCase("s")){		// if you save
						System.out.println("SAVE in progress ... ");
						try {		//write in a file
							FileOutputStream fileout = new FileOutputStream("anthill.shep");
							ObjectOutputStream Objectout = new ObjectOutputStream(fileout);
							Objectout.writeObject(anthill);
							Objectout.close();
							System.out.println("SAVED !");
						}
						catch (Exception e) {		// too big file or an error
							System.out.println("Too much ants, they can't all go in a file. They all will die !");
						}
						System.out.print("Go to the future! How many day?: ");
						sc.nextLine();
					}
					else if (choice.equalsIgnoreCase("q")){		// if you quit
						System.out.println("Stopping");
						Runtime.getRuntime().exit(-1);
					}
					else{
					System.out.println("Error! Please choose a number of days: ");
					System.out.print("Go to the future! How many day?: ");
					sc.nextLine();
					}
					
					
				}
				go_to_futur = sc.nextInt();
				if(go_to_futur<1){
					go_to_futur=1;
				}
				try {
					for (int i = 0; i < go_to_futur; i++) {
						anthill.growUp();
					}
				} 
				catch (OutOfMemoryError E) {		//too much ants
					System.out.println("Error, to much people here. A disater will happened soon !");
					System.out.println("All ants has been killed");
					break;
				}
			}
			
		}
		else if (menu.equalsIgnoreCase("C")){		// load a file
			int larvae = 0, workers = 0, males = 0, queens = 0;
			Anthill anthill = new Anthill(larvae, workers, males, queens);		// create the anthill
			
			System.out.println("Loading file anthill.shep...");
			try {		//load the file
				FileInputStream filein = new FileInputStream("anthill.shep");
				ObjectInputStream Objectin = new ObjectInputStream(filein);
				anthill = (Anthill) Objectin.readObject();
				Objectin.close();
				System.out.println("Load succesfull. Your anthill is now here!");
				System.out.println("");
			}
			catch (Exception e) {		//can load the file
				System.out.println("Error in file, all is now lost");
				Runtime.getRuntime().exit(-1);
			}
			
			/*
			 * same as Menu "N" new
			 */
			while(and_again){ 
				System.out.print("Go to the future! How many day?: ");
				while(!sc.hasNextInt())
				{
					String choice = sc.next();
					if (choice.equalsIgnoreCase("s")){
						System.out.println("SAVED in progress");
						try {
							FileOutputStream fileout = new FileOutputStream("anthill.shep");
							ObjectOutputStream Objectout = new ObjectOutputStream(fileout);
							Objectout.writeObject(anthill);
							Objectout.close();
							System.out.println("SAVED !");
						}
						catch (Exception e) {
							System.out.println("Too much ants, they can't all go in a file. They all will die !");
						}
						System.out.print("Go to the future! How many day?: ");
						sc.nextLine();
					}
					else if (choice.equalsIgnoreCase("q")){
						System.out.println("Stopping");
						Runtime.getRuntime().exit(-1);
					}
					else{
					System.out.println("Error! Please choose a number of days: ");
					System.out.print("Go to the future! How many day?: ");
					sc.nextLine();
					}
				}
				go_to_futur = sc.nextInt();					
				if(go_to_futur<=0){
					go_to_futur=1;
				}
				try {
					for (int i = 0; i < go_to_futur; i++) {
						anthill.growUp();
					}
				} 
				catch (OutOfMemoryError E) {
					System.out.println("Error, to much people here. A disater will happened soon !");
					System.out.println("All ants has been killed");
					break;
				}
			}
		}
		else{		//quit
			System.out.println("Stopping");
			Runtime.getRuntime().exit(-1);
		}			
	}
}