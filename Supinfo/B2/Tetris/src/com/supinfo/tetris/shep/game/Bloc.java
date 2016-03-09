package com.supinfo.tetris.shep.game;

import java.lang.Math;


public class Bloc {

	//list all bloc : 7 + nothing
    enum Tetriminos { 
    	Empty, 
    	SquareBloc, 
    	ZBloc, 
    	SBloc, 
    	LineBloc, 
        TBloc, 
        LBloc, 
        StrangeBloc };

    private Tetriminos pieceBloc;
    private int Pos[][];
    private int[][][] matrice3;


    public Bloc() {

        Pos = new int[4][2];
        setBloc(Tetriminos.Empty);

    }

    
    public int minX()
    {
      int posMinX = Pos[0][0];
      for (int i=0; i < 4; i++) {
    	  posMinX = Math.min(posMinX, Pos[i][0]);
      }
      return posMinX;
    }


    public int minY() 
    {
      int posMinY = Pos[0][1];
      for (int i=0; i < 4; i++) {
    	  posMinY = Math.min(posMinY, Pos[i][1]);
      }
      return posMinY;
    }

    //turn bloc to the right
    public Bloc turnRight()
    {
        Bloc turnR = new Bloc();
        turnR.pieceBloc = pieceBloc;

        for (int i = 0; i < 4; ++i) {
        	turnR.setX(i, -y(i));
        	turnR.setY(i, x(i));
        }
        return turnR;
    }
    
    //turn bloc to the left
    public Bloc turnLeft() 
    {
        Bloc turnL = new Bloc();
        turnL.pieceBloc = pieceBloc;

        for (int i = 0; i < 4; ++i) {
        	turnL.setX(i, y(i));
        	turnL.setY(i, -x(i));
        }
        return turnL;
    }
    
    //bloc matrix, ordinal representation
    public void setBloc(Tetriminos form) {

        matrice3 = new int[][][] {
           { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },
           { { 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 } },
           { { 0, -1 },  { 0, 0 },   { 1, 0 },   { 1, 1 } }, 
           { { 0, -1 },  { 0, 0 },   { -1, 0 },  { -1, 1 } },        
           { { 0, -1 },  { 0, 0 },   { 0, 1 },   { 0, 2 } }, 
           { { -1, -1 }, { 0, -1 },  { 0, 0 },   { 0, 1 } }, 
           { { 1, -1 },  { 0, -1 },  { 0, 0 },   { 0, 1 } }, 
           { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 0, 1 } } 
       };

       for (int i = 0; i < 4 ; i++) {
           for (int j = 0; j < 2; ++j) {
               Pos[i][j] = matrice3[form.ordinal()][i][j];
           }
       }
       pieceBloc = form;

   }
    
    //generate a randombloc
    public void setAleatBloc(){
    	int x = (int) (((Math.random()*100) % 7) +1);
        Tetriminos[] values = Tetriminos.values(); 
        setBloc(values[x]);
    }
    
    
   private void setX(int index, int x) {
   	Pos[index][0] = x; 
   }
   
   
   private void setY(int index, int y) { 
   	Pos[index][1] = y; 
   }
   
   
   public int x(int index) { 
   	return Pos[index][0]; 
   }
   
   
   public int y(int index) { 
   	return Pos[index][1]; 
   }
   
   
   public Tetriminos getBloc()  { 
   	return pieceBloc; 
   }



    
}

