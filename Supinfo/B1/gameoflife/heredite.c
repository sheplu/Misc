#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <windows.h>.

#include "heredite.h"


int nb_voisin(char**tab, int pos_line, int pos_col)
{
int compteur_voisin=0;
int i=0; int j=0;
i=pos_line; j=pos_col;

if (tab[i-1][j]==1){compteur_voisin=compteur_voisin+1;}
if (tab[i-1][j-1]==1){compteur_voisin=compteur_voisin+1;}
if (tab[i+1][j]==1){compteur_voisin=compteur_voisin+1;}
if (tab[i+1][j+1]==1){compteur_voisin=compteur_voisin+1;}
if (tab[i+1][j-1]==1){compteur_voisin=compteur_voisin+1;}
if (tab[i-1][j+1]==1){compteur_voisin=compteur_voisin+1;}
if (tab[i][j-1]==1){compteur_voisin=compteur_voisin+1;}
if (tab[i][j+1]==1){compteur_voisin=compteur_voisin+1;}

return(compteur_voisin);
}


//---------------------------------------
void duplication(char**game_array,char**new_game_array,int nb_line,int nb_colone)
{
int i,j;
for (i=0; i<nb_line; i++)
    {
        for (j=0; j<nb_colone; j++)
        {
            new_game_array[i][j]=game_array[i][j];
        }
    }

}
//---------------------------------------
void generation_suivante(char**game_array,char**new_game_array,int nb_line,int nb_colone)
{
    int i,j;
    for (i=1; i<(nb_line-1); i++)                           //how many cell_alive
    {
        for (j=1; j<(nb_colone-1); j++)
        {
             if ((nb_voisin(game_array,i,j))==3)            //if 3
             {
                 new_game_array[i][j]=1;                    //alive
             }
             if (((nb_voisin(game_array,i,j))<=1)||((nb_voisin(game_array,i,j))>=4))        //if 1 or4+
             {
                 new_game_array[i][j]=0;                    //dead
             }
             if((nb_voisin(game_array,i,j))==2)
             {
                 new_game_array[i][j]=game_array[i][j];
             }
        }
    }

}

