#include<iostream>
#include<string>

using namespace std;

int main()
{
    string * str = new string();
    //copy
    str->append("123").append(to_string(4));
    (*str) = (*str) + "abc" + to_string(9);
    cout << "str:\t" << *str << endl;
}
