#include <iostream>
#include <cstdlib>
#include <string>
#include <stdlib.h>

#include <game.h>


Game :: Game(int h, int w, std::string name_joueur1, std:: string name_joueur2)
{
    board = new Board(h,w);
    winner ="";
    in_game = true;
    player[0] = new Player (h/2,w/2-1,name_joueur1);
    player[1] = new Player (h/2,w/2,name_joueur2);

}

