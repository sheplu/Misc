package com.supinfo.tetris.shep.launcher;

import com.supinfo.tetris.shep.game.*;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Window extends JFrame{

	JLabel headerbar;
	JLabel scorebar;
	JOptionPane alert;
	
	//create all windows with Jlabel, set size, title ...
	//generate window
	public Window()  {
		
		headerbar = new JLabel("Welcome in TETRIS !");
		scorebar = new JLabel("score bar!");
		
		add(headerbar, BorderLayout.NORTH);
		//add(scorebar, BorderLayout.EAST);
		
		headerbar.setBackground(Color.red);
		headerbar.setOpaque(true);
		
		//scorebar.setBackground(Color.green);
		//scorebar.setOpaque(true);
		
		

        setSize(600, 800);
        setTitle("Tetris de shep");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        Board board = new Board(this);	
        add(board);
        board.start();
	}
	
	public JLabel getHeaderBar() {
	       return headerbar;
	   }
	   
	public JLabel getScoreBar() {
		   return scorebar;
	   }
	
	public JOptionPane getAlert() {
			return alert;
	}
	
}
