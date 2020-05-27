#include <iostream>
#include <stdio.h>
#include <assert.h>

using namespace std;

int main (int argc, char ** argv){
    char buffer[100];

    assert(sizeof(buffer) == 100);
    snprintf (buffer, 100, "Hello, world!");
    cout << buffer << endl;
}
