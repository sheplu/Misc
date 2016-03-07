#ifndef BOARD_H
#define BOARD_H

class Board {

    private :
        int width,height;
        int** tab;
    public :
        Board(int h=5, int w=6);
        bool is_free(int i, int j);
        void display(void);
        int get_square(int i, int j);
        void set_square(int i, int j, int contain);
        int get_width(void);
        int get_height(void);


        Board& operator++();

};

#endif

