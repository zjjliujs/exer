#include <iostream>

#define test1 1
#define test2 2
#define macro_str(x) #x

using namespace std;

int main()
{
    cout << macro_str(test1) << "==" << test1 << endl;
    cout << macro_str(test2) << "==" << test2<< endl;
}
