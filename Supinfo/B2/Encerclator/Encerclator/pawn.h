#ifndef PAWN_H
#define PAWN_H

class Pawn
{
    private:
        int i,j;
    public:
        Pawn(int h=0, int w=0);
        int get_i(void);
        int get_j(void);
        void set_i(int h);
        void set_j(int w);
};




#endif // PAWN_H
