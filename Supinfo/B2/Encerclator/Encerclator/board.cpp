#include <iostream>
#include <cstdlib>
#include <string>
#include <stdlib.h>

#include <board.h>


using namespace std;

Board::Board(int h, int w)
{
    //test height
    if (h<3 || h%2 == 0)
        {height=5;}
    else
        {height = h;}
    //test width
    if (w<4 || w%2 == 1)
        {width=6;}
    else
        {width = w;}

    tab = new int* [height];

    for (int i=0; i<height; i++)//generate & initialize the board
    {
        tab[i] = new int [width];
        for (int j=0; j<width; j++)
            {set_square(i,j,0);} // set 0 on all square
    }

    set_square(height/2, (width/2)-1, 1);//set pawn1
    set_square(height/2, (width/2), 2);//set pawn2

}



bool Board::is_free(int i, int j)
{
    if (i < 0 || i >= height || j < 0 || j >= width)//if we aren't in board
        {return false;}
    else
    {
        if(get_square(i, j)==0)//if square is empty
            {return true;}
        else
            {return false;}
    }
}


void Board::display()
{
    int k;
    cout << "  ";
    for (k=0; k<get_width(); k++)
        {cout << k;}        //with number it's better
    cout <<endl;
    for (int i=0; i<get_height(); i++)
    {
        cout << i << " ";   //with number it's better
        for (int j=0; j<get_width(); j++)
        {
            switch(get_square(i, j))//display board
            {
                case 0:
                cout << ".";
                break;
                case 1:
                cout << "X";
                break;
                case 2:
                cout << "O";
                break;
                case 3:
                cout << "B";
                break;
                default:
                break;
            }
        }
        cout << endl;
    }
}


int Board::get_square(int i, int j)
    {return tab[i][j];}


void Board::set_square(int i, int j, int contain)
    {tab[i][j]=contain;}


int Board::get_width()
    {return width;}


int Board::get_height()
    {return height;}

Board& Board::operator++ ()
{
    int** first_temporaty_tab = tab;

    int first_width = get_width();
    int first_height = get_height();
    int second_width = first_width+2;
    int second_height = first_height+2;
    int i,j;
    int** second_temporaty_tab = new int* [second_height];

    for (i = 0; i < second_height; i++ )
    {
        second_temporaty_tab[i] = new int[second_width];
        for (j = 0; j < second_width; j++)
            {second_temporaty_tab[i][j] = 0;}
    }

    for (i = 0; i < first_height; i++)
    {
        for (j = 0; j < first_width; j++)
            {second_temporaty_tab[i+1][j+1] = first_temporaty_tab[i][j];}
    }

    height = second_height;
    width = second_width;
    tab = second_temporaty_tab;
    return *this;
}
