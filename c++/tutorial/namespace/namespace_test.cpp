#include <iostream>
#include <string>

using namespace std;
namespace com {
    namespace ljs {
        void sayHello(string name) {
            cout << "Hello," << name << endl;
        }
    }
}

using namespace com::ljs;

int main(int argc, char** argv) {
    sayHello ("ljs");
}
