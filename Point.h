/* Point.h
Copyright (c) 2014 by Michael Zahniser

Endless Sky is free software: you can redistribute it and/or modify it under the
terms of the GNU General Public License as published by the Free Software
Foundation, either version 3 of the License, or (at your option) any later version.

Endless Sky is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
PARTICULAR PURPOSE.  See the GNU General Public License for more details.
*/

#ifndef POINT_H_
#define POINT_H_

#include <pmmintrin.h>



// Class representing a 2D point with functions for a variety of vector operations.
// Internally the coordinates are stored in a SSE vector and the processor's vector
// extensions are used to optimize all operations.
class Point {
public:
	Point();
	Point(double x, double y);
	Point(const Point &point);
	
	Point &operator=(const Point &point);
	
	// Check if the point is anything but (0, 0).
	explicit operator bool() const;
	bool operator!() const;
	
	// No comparison operators are provided because I never expect to use them
	// and because comparisons with doubles are inherently unsafe due to the
	// possibility of rounding errors and imprecision.
	
	Point operator+(const Point &point) const;
	Point &operator+=(const Point &point);
	Point operator-(const Point &point) const;
	Point &operator-=(const Point &point);
	Point operator-() const;
	
	Point operator*(double scalar) const;
	friend Point operator*(double scalar, const Point &point);
	Point &operator*=(double scalar);
	Point operator/(double scalar) const;
	Point &operator/=(double scalar);
	
	Point operator*(const Point &other) const;
	Point &operator*=(const Point &other);
	
	double &X();
	const double &X() const;
	double &Y();
	const double &Y() const;
	
	void Set(double x, double y);
	
	// Operations that treat this point as a vector from (0, 0):
	double Dot(const Point &point) const;
	double Cross(const Point &point) const;
	
	double Length() const;
	double LengthSquared() const;
	Point Unit() const;
	
	double Distance(const Point &point) const;
	double DistanceSquared(const Point &point) const;
	
	
private:
	// Private constructor, using a vector.
	Point(__m128d v);
	
	
private:
	union {
		__m128d v;
		struct {
			double x;
			double y;
		};
	};
};



#endif
