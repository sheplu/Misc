#ifndef HEREDITE_H_INCLUDED
#define HEREDITE_H_INCLUDED

int nb_voisin (char ** game_array, int pos_line, int post_col);
void duplication (char ** game_array, char ** new_game_array, int nb_line, int nb_colone);
void generation_suivante (char ** game_array, char ** new_game_array, int pos_line, int pos_col);

#endif // HEREDITE_H_INCLUDED
