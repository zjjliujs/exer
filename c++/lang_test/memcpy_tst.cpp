#include <iostream>
#include <string.h>

using namespace std;


int main() {

    float * a = new float[10];
    float * b = new float[5]{1,2,3,4,5};

    memcpy (a, b, 5 * sizeof(float));

    int i;
    for (i=0; i<5; ++i){
        cout << a[i] << endl;
    }
    return 0;
}
