#include <iostream>
#include <string.h>

using namespace std;

#define R 2 
#define C 5 

int main() {

    unsigned int i, j;
    float ** p1 = new float * [R];
    for (i=0; i<R; ++i){
        p1[i] = new float[C];
        for (j=0; j<C; ++j){
            p1[i][j] = i * C + j;
        }
    }
    
    size_t s = (R+1) * sizeof(float*);

    float ** p2 = new float * [C + 1];
    memset(p2, 0, s);
    // OK!
    memcpy(p2, p1, R * sizeof (float *));
    /* OK!
    for (i=0; i<C; ++i) {
        p2[i] = p1[i];
    }
    */
    float f[C] = {100, 101, 102, 103, 104};
    p2[R] = f;

    delete p1;

    for (i=0; i<R + 1; ++i){
        float * r = p2[i];
        for (j=0; j<C; ++j){
            cout << r[j] << ","; 
        }
        cout << endl;
    }
    return 0;
}
