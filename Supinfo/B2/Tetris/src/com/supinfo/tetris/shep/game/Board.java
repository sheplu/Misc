package com.supinfo.tetris.shep.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.supinfo.tetris.shep.game.Bloc.Tetriminos;
import com.supinfo.tetris.shep.launcher.Window;




public class Board extends JPanel implements ActionListener {

    int boardWidth = 16;
    int boardHeight = 22;
    int score = 0;
    int speed = 400;
    int sumCompletedLines = 0;
    int actualX = 0;
    int actualY = 0;
    
    boolean endOfFall = false;
    boolean started = false;
    
    Timer timer;
    JLabel headerbar;
    JOptionPane alert;
    Bloc actualBloc;
    Tetriminos[] board;



    public Board(Window parent) {
    	
    	//create gameboard
    	setBackground(Color.gray);
       setFocusable(true);
       
       //set new bloc, set timer
       actualBloc = new Bloc();
       timer = new Timer(speed, this);
       timer.start(); 
       
       //get headerbar
       headerbar =  parent.getHeaderBar();
       alert = parent.getAlert();
       
       //create the board with size
       board = new Tetriminos[boardWidth * boardHeight];
       
       //create new listener to catch keyboard event
       addKeyListener(new TAdapter());
       clear();  
    }

    //check event
    public void actionPerformed(ActionEvent e) {
        if (endOfFall) {
            endOfFall = false;
            newBloc();
        } 
        else {
            goDown();
        }
    }

    //get width of one square
    int squareWidth() { 
    	int  sWidth = (int) getSize().getWidth() / boardWidth;
    	return sWidth; 
    }
    
    //get height of one square
    int squareHeight() { 
    	int  sHeight= (int) getSize().getHeight() / boardHeight;
    	return sHeight; 
    
    }
    
    //get bloc position
    Tetriminos recupBlocPos(int x, int y) {
    	return board[x + (y * boardWidth)]; 
    }

    //begin game
    public void start()
    {
        started = true;
        endOfFall = false;
        sumCompletedLines = 0;
        clear();

        newBloc();
        timer.start();
    }

    // paint board and bloc
    public void paint(Graphics g)
    { 
        super.paint(g);

        Dimension size = getSize();
        int boardTop = (int) size.getHeight() - boardHeight * squareHeight();


        for (int i = 0; i < boardHeight; ++i) {
            for (int j = 0; j < boardWidth; ++j) {
                Tetriminos form = recupBlocPos(j, boardHeight - i - 1);
                if (form != Tetriminos.Empty) {
                    drawSquare(g, 0 + j * squareWidth(),boardTop + i * squareHeight(), form);
                }
            }
        }

        if (actualBloc.getBloc() != Tetriminos.Empty) {
            for (int i = 0; i < 4; ++i) {
                int x = actualX + actualBloc.x(i);
                int y = actualY - actualBloc.y(i);
                drawSquare(g, 0 + x * squareWidth(),boardTop + (boardHeight - y - 1) * squareHeight(),actualBloc.getBloc());
            }
        }
    }

    //check if u can fall / go bottom
    private void fall()
    {
        int newY = actualY;
        while (newY > 0) {
            if (!tryMove(actualBloc, actualX, newY - 1))
                break;
            --newY;
        }
        pieceDropped();
    }

    //check and go down one line
    private void goDown()
    {
        if (!tryMove(actualBloc, actualX, actualY - 1))
            pieceDropped();
    }

    //clear
    private void clear()
    {
        for (int i = 0; i < boardHeight * boardWidth; ++i)
            board[i] = Tetriminos.Empty;
    }

    //
    private void pieceDropped()
    {
        for (int i = 0; i < 4; ++i) {
            int x = actualX + actualBloc.x(i);
            int y = actualY - actualBloc.y(i);
            board[(y * boardWidth) + x] = actualBloc.getBloc();
        }

        completedLines();

        if (!endOfFall) {
            newBloc();
        }
    }

    //create new bloc and check if you loose
    private void newBloc()
    {
        actualBloc.setAleatBloc();
        actualX = 1 + boardWidth / 2 ;
        actualY = actualBloc.minY() + boardHeight - 1 ;

        if (!tryMove(actualBloc, actualX, actualY)) {
            actualBloc.setBloc(Tetriminos.Empty);
            timer.stop();
            started = false;
            alert.showMessageDialog(null, "You loose with "+score+" points and you build "+sumCompletedLines+" lines" , "Game Over", JOptionPane.ERROR_MESSAGE);
        }
    }

    //check if you can move
    private boolean tryMove(Bloc newBloc, int newX, int newY)
    {
        for (int i = 0; i < 4; ++i) {
            int x = newBloc.x(i) + newX;
            int y = newY - newBloc.y(i);
            if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight)
                return false;
            if (recupBlocPos(x, y) != Tetriminos.Empty) {
                return false;
            }
        }

        actualBloc = newBloc;
        actualX = newX;
        actualY = newY;
        repaint();
        return true;
    }

    //count complet line, count score
    private void completedLines()
    {
        int numberCompletedLines = 0;

        for (int i = boardHeight - 1; i >= 0; --i) {
            boolean fullLine = true;

            for (int j = 0; j < boardWidth; ++j) {
                if (recupBlocPos(j, i) == Tetriminos.Empty) {
                    fullLine = false;
                    break;
                }
            }

            if (fullLine) {
                ++numberCompletedLines;
                
                switch (numberCompletedLines) {
				case 1:
					score += 10;
					break;
				case 2:
					score += 20;
					break;
				case 3:
					score += 20;
					break;
				case 4:
					score += 30;
					break;
                }
                
                for (int k = i; k < boardHeight - 1; ++k) {
                    for (int j = 0; j < boardWidth; ++j)
                         board[(k * boardWidth) + j] = recupBlocPos(j, k + 1);
                }
            }
        }

        
        if (numberCompletedLines > 0) {
            sumCompletedLines += numberCompletedLines;
            speed = 400-(20*sumCompletedLines);
            timer.setDelay(speed);           
            headerbar.setText("lines Completed : " +sumCompletedLines+ "   //   Score : " +score+ "   ///  speed : " +speed);            		
            endOfFall = true;
            actualBloc.setBloc(Tetriminos.Empty);
            repaint();
        }
     }

    //draw blocs
    private void drawSquare(Graphics g, int x, int y, Tetriminos form)
    {
        Color colors[] = { 
        		new Color(255, 255, 255), 
        		new Color(10, 100, 50), 
        		new Color(50, 100, 200), 
        		new Color(50, 200, 100), 
        		new Color(100, 200, 50), 
            	new Color(222, 111, 33), 
            	new Color(55, 0, 255), 
            	new Color(0, 127, 255)
        };


        Color color = colors[form.ordinal()];
        
        //set color 
        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

        //set dark color in border
        g.setColor(color.darker());
        g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1,x + squareWidth() - 1, y + 1);
        g.drawLine(x + 1, y + squareHeight() - 1,x + squareWidth() - 1, y + squareHeight() - 1);
        
        //set brighter color in border
        g.setColor(color.brighter());
        g.drawLine(x, y, x + squareWidth() - 1, y);
        g.drawLine(x, y + squareHeight() - 1, x, y);
        
        
    }

    // class in class, catch keyboard event
    class TAdapter extends KeyAdapter {
         public void keyPressed(KeyEvent e) {

             if (!started || actualBloc.getBloc() == Tetriminos.Empty) {  
                 return;
             }

             int keycode = e.getKeyCode();


             switch (keycode) {
             case KeyEvent.VK_LEFT:
                 tryMove(actualBloc, actualX - 1, actualY);
                 break;
             case KeyEvent.VK_RIGHT:
                 tryMove(actualBloc, actualX + 1, actualY);
                 break;
             case KeyEvent.VK_DOWN:
                 tryMove(actualBloc.turnRight(), actualX, actualY);
                 break;
             case KeyEvent.VK_UP:
                 tryMove(actualBloc.turnLeft(), actualX, actualY);
                 break;
             case KeyEvent.VK_F:
                 fall();
                 break;
             }

         }
     }
}
