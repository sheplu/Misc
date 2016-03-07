#include <iostream>
#include <cstdlib>
#include <string>
#include <stdlib.h>
#include <ctype.h>


#include <board.h>
#include <pawn.h>
#include <player.h>
#include <game.h>


using namespace std;

int main()
{
    int width_board, height_board,round;
    string replay;
    string name_joueur1, firstname_joueur1, pseudo_joueur1, name_joueur2, firstname_joueur2, pseudo_joueur2;
    string temporary;

    cout << "           #################################################" << endl;
    cout << "           #################################################" << endl;
    cout << "           ##                                             ##" << endl;
    cout << "           ##                                             ##" << endl;
    cout << "           ##                                             ##" << endl;
    cout << "           ##                                             ##" << endl;
    cout << "           ##                 ENCERCLATOR                 ##" << endl;
    cout << "           ##                                             ##" << endl;
    cout << "           ##                                             ##" << endl;
    cout << "           ##                                             ##" << endl;
    cout << "           ##                           By Jean Burellier ##" << endl;
    cout << "           #################################################" << endl;
    cout << "           #################################################" << endl;
    cout << endl;
    system("PAUSE");
    /*system("CLS");

    cout << "Generation du plateau :" << endl;
    cout << "Hauteur (impair) --> " ;
    cin >> height_board;
    cout << "Largeur (pair) --> " ;
    cin >> width_board;

    system("CLS");
    cout << "Donnees joueurs :" << endl;
    cout << "Nom Prenom et Pseudo du joueur 1 --> " ;
    cin >> name_joueur1 >> firstname_joueur1 >> pseudo_joueur1;
    cout << "\nNom Prenom et Pseudo du joueur 2 --> " ;
    cin >> name_joueur2 >> firstname_joueur2 >> pseudo_joueur2;

    system("CLS");
    Board myboard(height_board, width_board);
    myboard.display();
    ++myboard;
    myboard.display();
    cout << endl;
    Player test(3,3,"shep");

        test.move_pawn(myboard);
        myboard.display();

    Game game_test(height_board,width_board,pseudo_joueur1,pseudo_joueur2);*/

    /*do
    {
        system("CLS");
        game_test.board->display();
        if (game_test.player[0]->can_move(*(game_test.board)))
        {
            game_test.player[0]->move_pawn(*(game_test.board));
            system("CLS");
            game_test.board->display();
            game_test.player[0]->put_stone(*(game_test.board));
        }
        else
        {
            game_test.in_game = false;
            cout << "you lose p1"<< endl;
            break;
        }


        game_test.board->display();
        if(game_test.player[1]->can_move(*(game_test.board)))
        {
            game_test.player[1]->move_pawn(*(game_test.board));
            system("CLS");
            game_test.board->display();
            game_test.player[1]->put_stone(*(game_test.board));
        }
        else
        {
            game_test.in_game = false;
            cout << "you lose p2" << endl;
        }
    }while (game_test.in_game == true);*/

    do
    {
        // ask board's data
        system("CLS");
        cout << "Generate the board :" << endl;
        cout << "   Height (odd) --> " ;
        cin >> height_board;
        cout << "   Width (even) --> " ;
        cin >> width_board;

         //clear then ask player's data
        system("CLS");
        cout << "Players' data' :" << endl;
        cout << "   Player 1's Name Firstname and Pseudo --> " ;
        cin >> name_joueur1 >> firstname_joueur1 >> pseudo_joueur1;
        cout << "\n   Player 2's Name Firstname and Pseudo --> " ;
        cin >> name_joueur2 >> firstname_joueur2 >> pseudo_joueur2;

        system("CLS");

        Game game_test(height_board,width_board,pseudo_joueur1,pseudo_joueur2);

        round=0;

        // play until two players can move
         do
         {
            round++;

             system("CLS");
             game_test.board->display();
             if (game_test.player[0]->can_move(*(game_test.board))) // if he can move
             {
                game_test.player[0]->move_pawn(*(game_test.board)); //move pawn
                system("CLS");
                game_test.board->display();
                game_test.player[0]->put_stone(*(game_test.board)); // put stone
             }
             else
             {
                    //if can't move, game over
                game_test.in_game = false;
                cout << "\nYou lose player 1: "<< game_test.player[0]->get_pseudo() << endl;
                cout << "You win player 2: "<< game_test.player[1]->get_pseudo() << endl;
                break;
             }

             system("CLS");
             game_test.board->display();
             if(game_test.player[1]->can_move(*(game_test.board)))
             {
                game_test.player[1]->move_pawn(*(game_test.board));
                system("CLS");
                game_test.board->display();
                game_test.player[1]->put_stone(*(game_test.board));
             }
             else
             {
                game_test.in_game = false;
                cout << "\nYou lose player 2: "<< game_test.player[1]->get_pseudo() << endl;
                cout << "You win player 1: "<< game_test.player[0]->get_pseudo() << endl;
             }

        }while (game_test.in_game == true);

        cout << "\nGame duration : " << round <<"\n\n"<<endl;
        cout << "Would you regame? \n [1]Yes \n [ ]No" << endl;  //if you want regame
        cin >> replay;
    }while(replay == "1");

    return 0;
}
