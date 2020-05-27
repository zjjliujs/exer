#include <iostream>

using namespace std;

class A {
    public:
        A(){
            cout << "Constructor!" << endl;
            value = 10;
        };

        A(const A & a) {
            cout << "Copy constructor!" << endl;
            value = a.value;
        };

        ~A(){
            cout << "Destructor!" << endl;
            value = 0;
        };

    public:
        int value;
};

int main(int argc, char ** argv){
    A as[3];

    /*
    for (A & a: as){
        cout << &a << endl;
    }
    */

    for (A a: as){
        cout << &a << endl;
    }

    cout << "values:" << as[0].value << "," << as[1].value << "," << as[2].value << endl;
}

