#ifndef PLAYER_H
#define PLAYER_H

#include <pawn.h>
#include <board.h>

class Player
{
    private:
        Pawn p;
        std::string pseudo;
    public:
        Player(int i=0, int j=0, std::string name="Laurent");
        bool can_move(Board plat);
        void move_pawn(Board &plat);
        void put_stone(Board &plat);
        std::string get_pseudo(void);
};


#endif // PLAYER_H
