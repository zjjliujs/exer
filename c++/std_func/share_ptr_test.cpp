#include <iostream>
#include <memory>

using namespace std;

class tester {
	public:
		~tester() {
			std::cout << "析构函数被调用!\n";
		}
};

int main (int argc, char **argv) {
	tester * t = new tester();
	shared_ptr<tester> sp(t);
	return 0;
}
