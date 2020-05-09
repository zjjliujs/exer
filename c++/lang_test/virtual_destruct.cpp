#include<iostream>

using namespace std;

class Base
{
    public:
        Base(){}
        //基类的析构函数应该声明为虚析构函数。
        virtual ~Base(){
            cout<<"destruct Base!"<<endl;
        }  
        virtual void  Test(){cout<<"Base Test"<<endl;}
};

class Derived:public Base
{
    public:
        Derived(){}

        ~Derived(){
            cout<<"destruct Derived"<<endl;
        }

        void Test(){cout<<"Derived Test"<<endl;}
};

int main() { 
    //父类的指针指向派生类的对象。
    Base* pBase=new Derived();  
    pBase->Test();

    //如果没有Base基类的析构函数没有声明为virtual，是不会执行到子类的析构函数的。
    //所以将父类的析构函数声明为虚函数，作用是用父类的指针删除一个派生类对象时，派生类对象的析构函数会被调用。
    delete pBase; 
}
