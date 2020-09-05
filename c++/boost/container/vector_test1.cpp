#include <iostream>
#include <vector>

using namespace std;

void output_vector (vector<int> v) {

    vector<int>::const_iterator it = v.begin();
    for (; it != v.end(); ++it){
        cout << *it << ",";
    }
    cout << endl;
}

void test(vector<int> src, int start, int size) {
    vector<int>::const_iterator it_start = src.begin() + start;
    vector<int>::const_iterator it_end = it_start + size;

    vector<int> result(it_start, it_end);
    output_vector(result);
}

int main (int argc, char ** argv) {
    
    vector<int> src = {3, 4, 5, 6};
    output_vector(src);

    test (src, 1, 1);
    test (src, 1, 2);
    test (src, 1, 3);
}
