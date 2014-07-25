/* StellarObject.h
Copyright (c) 2014 by Michael Zahniser

Endless Sky is free software: you can redistribute it and/or modify it under the
terms of the GNU General Public License as published by the Free Software
Foundation, either version 3 of the License, or (at your option) any later version.

Endless Sky is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
PARTICULAR PURPOSE.  See the GNU General Public License for more details.
*/

#ifndef STELLAR_OBJECT_H_
#define STELLAR_OBJECT_H_

#include "Animation.h"
#include "Point.h"

class Planet;



// Class representing a planet, star, moon, or other large object in space.
class StellarObject {
public:
	StellarObject();
	
	// Some objects do not have sprites, because they are just an orbital
	// center for two or more other objects.
	const Animation &GetSprite() const;
	// Get this object's position on the date most recently passed to this
	// system's SetDate() function.
	const Point &Position() const;
	// Get the radius of this planet, i.e. how close you must be to land.
	double Radius() const;
	// If it is possible to land on this planet, this returns the Planet
	// objects that gives more information about it. Otherwise, this
	// function will just return nullptr.
	const Planet *GetPlanet() const;
	// Only planets that you can land on have names.
	const std::string &Name() const;
	// If it is impossible to land on this planet, get the message
	// explaining why (e.g. too hot, too cold, etc.).
	const std::string &LandingMessage() const;
	
	// Check if this is a star.
	bool IsStar() const;
	// Get this object's parent index (in the System's vector of objects).
	int Parent() const;
	// Find out how far this object is from its parent.
	double Distance() const;
	
	
private:
	Animation animation;
	Point position;
	const Planet *planet;
	
	double distance;
	double speed;
	double offset;
	int parent;
	
	const std::string *message;
	bool isStar;
	
	// Let System handle setting all the values of an Object.
	friend class System;
};



#endif