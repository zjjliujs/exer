#include <iostream>

using namespace std;

class Base {
    public:
    Base() {
        init();
    }

    public:
    virtual void init() {
        cout << "Base init" << endl;
    }
};

class Inher: public Base {
    public:
    Inher():Base() {
    }

    public:
    void init () {
        cout << "Inher init" << endl;
    }
};

int main() {
    Base * inh = new Inher();
    inh->init();
}    
