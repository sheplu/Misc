#ifndef PERSON_H
#define PERSON_H


class Person
{
    protected:
        std::string name;
        std:: string firstname;
    public:
        person(std::string="Burellier", std::string surname="Jean");
        void display(void);
};

#endif // PERSON_H
