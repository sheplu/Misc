#include <iostream>
#include <string>
#include <stdlib.h>

#include <player.h>


using namespace std;


Player::Player(int i, int j, std::string name)
{
    pseudo = name;
    p.set_i(i);
    p.set_j(j);
}

bool Player::can_move(Board plat)
{
    int i = p.get_i();
    int j = p.get_j();
        // test if player can move pawn
    if(plat.is_free(i-1,j-1) || plat.is_free(i-1,j) || plat.is_free(i-1,j+1) ||
       plat.is_free(i,j-1) || plat.is_free(i,j) || plat.is_free(i,j+1) ||
       plat.is_free(i+1,j-1) || plat.is_free(i+1,j) || plat.is_free(i+1,j+1))
        {return true;}
    else
        {return false;}
}

void Player::move_pawn(Board &plat)
{
    int i,j;
    if(can_move(plat))
    {
        do
        {
            cout << "\nMove your pawn : " << get_pseudo() << endl;
            cout << "   Line : ";
            cin >> i;

            cout << "   Column : ";
            cin >> j;
        }while((i > p.get_i()+1 || j > p.get_j()+1 || i < p.get_i()-1 || j < p.get_j()-1 || i > plat.get_height() || j > plat.get_height() || i < 0 || j < 0) || !plat.is_free(i,j));
        // test if the location is valide (up)
        //change the values in board
        plat.set_square( i,j,plat.get_square(p.get_i(), p.get_j()));
        plat.set_square(p.get_i(),p.get_j(),0);


        p.set_i(i);
        p.set_j(j);



    }
}

void Player::put_stone(Board &plat)
{
    int i,j;
    do
    {
        cout << "\nPut a stone : " << get_pseudo() <<endl;
        cout << "   Line : ";
        cin >> i;

        cout << "   Column : ";
        cin >> j;
    }while(!plat.is_free(i,j)); //test if the square is empty

    plat.set_square(i, j, 3);   //change value of the square
}

std::string Player::get_pseudo()
    {return pseudo;}
