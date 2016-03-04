#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <windows.h>.

#include "initialisation.h"

#include "heredite.h"

#include "chiffrage.h"



int main()
{
    //choose mod
    int choix,i;
    //array size
    int nb_line,nb_colone;
    //array
    char **game_array;
    //how many generation
    int nb_gen;
    // duplication
    char **new_game_array;
    //end
    int k;
    int v,w;
    //with file
    //list file
    char *nom[50];


    printf("Voulez vous inscrire vos cellules ou les recuperer depuis un fichier?\n");          //choose case
    printf("Choix1: Saisie manuelle.\n");
    printf("Choix2: Saisie via un fichier.\n");
    printf("Choix3: Saisie via un fichier crypte.\n");
    printf("choix4: Regles du jeu.\n");
    scanf("%d",&choix);

    switch(choix)
        {
        case 1:                 //manual
            dimensionnement(&nb_line,&nb_colone);
            printf("Vous avez choisi un tableau equivalent a %dlignes par  %dcolonnes\n",nb_line,nb_colone);

            //size of game_array
            game_array=malloc(nb_line*sizeof(char*));
            for (i=0; i<nb_colone; i++)
            {
                game_array[i] = malloc(nb_line*sizeof(int));
            }

            initialisation (game_array, nb_line, nb_colone);
            //printf("%d %d", nb_line,nb_colone);
            nb_gen=nb_generation();

            //affiche (game_array, nb_line, nb_colone);

            //create new_game_array
            new_game_array=malloc(nb_line*sizeof(char*));
            for (i=0; i<nb_colone; i++)
            {
                new_game_array[i] = malloc(nb_line*sizeof(int));
            }
            duplication (game_array,new_game_array,nb_line,nb_colone);


            //print all generation
            for(k=0;k<nb_gen;k++)
            {
                //copy 2nd array in 1st
                for (v=0; v<nb_line; v++)
                {
                    for (w=0; w<nb_colone; w++)
                    {
                        game_array[v][w]=new_game_array[v][w];
                    }
                }
                system("CLS");
                printf("Vous avez choisi une simulation de %dgenerations",nb_gen);
                printf("\ngeneration %d",k+1);
                affiche (game_array, nb_line, nb_colone);
                system("PAUSE");


                generation_suivante(game_array,new_game_array,nb_line,nb_colone);
            }




        break;
        case 2:         //with file
            system("CLS");
            dimensionnement_bis(nom, &nb_line, &nb_colone);
            printf("Vous avez choisi un tableau equivalent a %dlignes par  %dcolonnes\n",nb_line,nb_colone);


            //size of game_array
            game_array=malloc(nb_line*sizeof(char*));
            for (i=0; i<nb_colone; i++)
            {
                game_array[i] = malloc(nb_line*sizeof(int));
            }


            initialisation_bis (nom,game_array,nb_line,nb_colone);



            new_game_array=malloc(nb_line*sizeof(char*));
            for (i=0; i<nb_line; i++)
            {
                 new_game_array[i] = malloc(nb_colone*sizeof(int));
            }
            //--------------------------------------

            duplication(game_array,new_game_array,nb_line,nb_colone);

            //------------------------------------------------

            nb_gen=nb_generation();


            //create new_game_array
            new_game_array=malloc(nb_line*sizeof(char*));
            for (i=0; i<nb_colone; i++)
            {
                new_game_array[i] = malloc(nb_line*sizeof(int));
            }
            duplication (game_array,new_game_array,nb_line,nb_colone);


            //print all generation
            for(k=0;k<nb_gen;k++)
            {
                //copy 2nd array in 1st
                for (v=0; v<nb_line; v++)
                {
                    for (w=0; w<nb_colone; w++)
                    {
                        game_array[v][w]=new_game_array[v][w];
                    }
                }
                system("CLS");
                printf("Vous avez choisi une simulation de %dgenerations",nb_gen);
                printf("\ngeneration %d",k+1);
                affiche (game_array, nb_line, nb_colone);
                system("PAUSE");
                //Sleep(0.000005);

                generation_suivante(game_array,new_game_array,nb_line,nb_colone);
            }

        break;
        case 3:         //with crypted file
                printf("Non Valide");
        break;
        case 4:         //rules

            system("CLS");
            printf("                    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            printf("                           ###   Regles du jeu     ###          \n");
            printf("                    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            printf("\n\n            Merci de visiter: http://fr.wikipedia.org/wiki/Jeu_de_la_vie\n\n");

        break;
        default:
            printf("Saisie invalide!\n");
        break;
        }

}


