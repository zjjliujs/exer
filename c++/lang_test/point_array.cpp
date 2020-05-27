#include <iostream>
#include <string.h>
using namespace std;

class T {
    public:
        T() { 
            cout << "constructor" << endl; 
            x = 1;
        }
        ~T() { cout << "destructor" << endl; }
    public:
        int x;
};

int main()
{
    const int NUM = 3;
    unsigned int i;

    T** p1 = new T*[NUM];
    cout << "new p1" << endl;
    for (i=0; i<NUM; ++i){
        p1[i] = new T();
    }
    //delete[] p1;

    T** p2 = new T*[NUM];
    cout << "new p2" << endl;
    size_t size = NUM * sizeof(T*);
    memset(p2, 0, size);
    cout << "p1 -> p2" << endl;
    memcpy(p2, p1, size);
    cout << "To delete p1" << endl;
    delete p1;
    cout << "out put p2" << endl;
    for (i=0; i<NUM; ++i) {
        cout << p2[i]->x << endl;
        cout << "delete p2[" << i << "]" << endl;
        delete p2[i];
    }
}
