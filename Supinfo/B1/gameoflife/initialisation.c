#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <windows.h>.

#include "initialisation.h"



////manual

void dimensionnement (int *nb_line,int *nb_colone)   //size of array.
    {
        do
        {
            system("CLS");              //clear screen
            printf("Veuillez saisir votre nombre de lignes:\n");        //nuumber of line
            scanf("%d",nb_line);
            printf("Veuillez saisir votre nombre de colonnes:\n");      //number of colonne
            scanf("%d",nb_colone);
        } while(nb_line<1 || nb_colone<1);            //n & m >0
    *nb_line+=2;                  //line +2 coz cellules_alive
    *nb_colone+=2;                  //colone +2 coz cellules_alive
    }


void initialisation (char**game_array, int nb_line, int nb_colone)         //create array
    {
        int i,j,k,nb_cell_alive,pos_line,pos_colone;
        k=1;

        //printf("%d %d\n",nb_line,nb_colone);

            for (i = 0; i < nb_line; i++)                               //all line
                {for (j = 0; j < nb_colone; j++)                        //all colone
                    {
                    game_array[i][j] = 0;                               //all case is 0
                    //printf("%d",game_array[i][j]);                    //test
                    }
                    //printf("\n");                                     //return array
                }
            i=nb_line-2;
            j=nb_colone-2;
            nb_cell_alive=0;


            do
            {
            printf("Combien de cellules vivantes voulez vous?\t");      //ask nb_cell_alive
            scanf("%d",&nb_cell_alive);
            }while (nb_cell_alive<0 || nb_cell_alive>(i*j));

            do
            {
                printf("Veuillez entrer les coordonnees de la cellule %d\n",k);
                printf("ligne:\t");
                scanf("%d",&pos_line);
                printf("colonne:\t");
                scanf("%d",&pos_colone);
                k=k+1;
                if (game_array[pos_line][pos_colone]==1)        //if case = cell_alive
                    {
                        k=k-1;
                        printf("Choix non accepte!\n");
                    }
                else if (pos_line>i || pos_line<=0 || pos_colone>j || pos_colone<=0)
                        {
                            k=k-1;
                            printf("Choix non accepte!\n");
                        }
                    else
                        {
                            game_array[pos_line][pos_colone]=1;
                        }


            }while(k<nb_cell_alive+1);



    }


int nb_generation()
{
    int nb_gen;
    system("CLS");
    printf("Veuillez entrer le nombre de generation voulues:\t");
    scanf("%d",&nb_gen);
    return nb_gen;
}


void affiche (char**game_array,int nb_line,int nb_colone)
{
    int i,j;
    printf("\n");
    for (i=0; i<nb_line; i++)
        {for (j=0; j<nb_colone; j++)
                {if (game_array[i][j]==0)
                        {printf(".");}
                if (game_array[i][j]==1)
                        {printf("o");}
                }
            printf("\n");
        }


}





////with file

void dimensionnement_bis(char *nom,int *nb_line, int *nb_colone)
{
    int test=0;
    do
    {
    system("cls");
    printf("Entrez le nom de votre fichier :");
    scanf("%s",nom);
    printf("\n\nChargement du fichier...");
    FILE*fichier=NULL;
    char c;
    *nb_line=1;
    *nb_colone=-1;

    fichier=fopen(nom,"r");
    if(fichier!=NULL)
        {
            test=1;
            while(c!='\n' && c!=EOF)            //read 1st line & nb
                {
                    *nb_colone=*nb_colone+1;
                    c = fgetc(fichier);

                }
            *nb_line=*nb_line+1;                //1st line readed

            while(c!=EOF)                       //if not end file
                {
                    c = fgetc(fichier);         //read one per one

                if (c == '\n')                  //if enter
                    {
                        *nb_line=*nb_line+1;     //+1
                    }
                }
                fclose(fichier);
        }
        else
        {
        printf("\n\nProbleme d'ouverture : Fichier introuvable ou en cours d'execution\n\n");
        test=0;
        system("pause");
        }
   }
   while (test!=1);
}




void initialisation_bis(char *nom, char**game_array, int nb_line, int nb_colone)
{

    int i=0,j=0,x=0;
    for (i=0; i<nb_line; i++)               //init 0
    {
        for (j=0; j<nb_colone; j++)
        {
            game_array[i][j]=0;
        }
    }

    FILE*fichier=NULL;
    char c=0;
    fichier=fopen(nom,"r");
    if(fichier!=NULL)
        {
            for (i=0; i<nb_line; i++)                        //all line
                {
                    for (j=0; j<nb_colone; j++)             //all colone
                        {
                            c = fgetc(fichier);
                            if (c=='0'){game_array[i][j]=0;}
                            if (c!='0'){game_array[i][j]=1;}
                        }
                    c = fgetc(fichier);
                }
                fclose(fichier);
        }
        else
        {
        printf("Probleme d'ouverture\n");
        }

        printf("\nGeneration entree :\n");
    for (i=0; i<nb_line; i++)                           //printf array
    {
        x=x+1;
        for (j=0; j<nb_colone; j++)
        {
            if (game_array[i][j]==0)
                {
                printf(".");
                }
            if (game_array[i][j]!=0)
                {
                printf("0");
                }
        }
        printf("\n");


    }
    system("Pause");
    //Sleep(1);
}

