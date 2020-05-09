#include <iostream>
#include <list>

using namespace std;

int main()
{
    float left = 0;
    float right = 100;
    float bottom = 0;
    float top = 100;

    float * va = new float[24] {
        // Order of coordinates: X, Y, S, T
        // Triangle Fan
        (right + left) / 2, (bottom + top) / 2, 0.5f, 0.5f,
            left, bottom, 0.f, 1.f,
            right, bottom, 1.f, 1.f,
            right, top, 1.f, 0.f,
            left, top, 0.f, 0.f,
            left, bottom, 0.f, 1.f
    };
    cout << "size of array!\t" << sizeof(*va) << endl;

    int i=0;
    while (i < 6){
        int j = 0;
        cout << "Row: " << i << endl;
        while (j < 4){
            cout << " " << va[i * 4 + j];
            j++;
        }
        cout << endl;
        ++i;
    }

    return 0;
}
