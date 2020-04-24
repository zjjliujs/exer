#include "polygon.hpp"
#include "rectangle.hpp"
#include "triangle.hpp"

#include <iostream>

using namespace std;

int main () {
  Rectangle rect;
  Polygon * ppoly1 = &rect;
  ppoly1->set_values (4,5);
  cout << rect.area() << '\n';

  ///*
  Triangle trgl;
  Polygon * ppoly2 = &trgl;
  ppoly2->set_values (4,5);
  cout << trgl.area() << '\n';
  //*/
  return 0;
}
