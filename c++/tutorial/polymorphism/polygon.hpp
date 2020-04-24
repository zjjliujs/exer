#ifndef POLYGON_HPP
#define POLYGON_HPP

class Polygon {
	protected:
		int width, height;
	public:
		void set_values (int a, int b);
		virtual int area() = 0;
};

#endif //POLYGON_HPP
