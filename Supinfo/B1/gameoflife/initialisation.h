#ifndef INITIALISATION_H_INCLUDED
#define INITIALISATION_H_INCLUDED

void dimensionnement (int *nb_line,int*nb_colone);
void initialisation (char**game_array, int nb_line, int nb_colone);
int nb_generation();
void affiche (char**game_array,int nb_line,int nb_colone);

void dimensionnement_bis(char*nom,int *nb_line, int *nb_colone);
void initialisation_bis(char *nom, char**game_array, int nb_line, int nb_colone);

#endif // INITIALISATION_H_INCLUDED
