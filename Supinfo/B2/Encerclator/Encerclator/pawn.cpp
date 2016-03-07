#include <iostream>
#include <cstdlib>
#include <string>
#include <stdlib.h>

#include <pawn.h>


Pawn::Pawn(int h, int w)
{
    i = h;
    j = w;
}

int Pawn::get_i(void)
    {return i;}

int Pawn::get_j(void)
    {return j;}


void Pawn::set_i(int h)
    {i = h;}

void Pawn::set_j(int w)
    {j = w;}
