#include <iostream>
#include <string.h>

using namespace std;

#define R 2 
#define C 5 

int main() {

    float a[R][C]= {{1,2,3,4,5},{6,7,8,9,10}};
    
    float ** pi = new float * [C];
    size_t s = R * sizeof(float*);
    memset(pi, 0, s);

    unsigned int i, j;
    for (i=0; i<R; ++i){
        *(pi + i) = *(a + i) ;
        cout << pi + i<< endl;
    }

    for (i=0; i<R; ++i){
        //float * r = a[i];
        for (j=0; j<C; ++j){
            cout << a[i][j] << endl;
        }
    }

    for (i=0; i<R; ++i){
        float * r = pi[i];
        for (j=0; j<C; ++j){
            cout << r[j] << endl;
        }
    }
    return 0;
}
