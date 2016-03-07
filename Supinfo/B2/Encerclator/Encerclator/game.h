#ifndef GAME_H
#define GAME_H

#include <board.h>
#include <player.h>


class Game
{
    public:
        Player* player[2];
        Board *board;
        std:: string winner;
        bool in_game;
        Game(int h, int w, std::string name_joueur1, std:: string name_joueur2);
};


#endif
