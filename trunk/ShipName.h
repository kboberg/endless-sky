/* ShipName.h
Copyright (c) 2014 by Michael Zahniser

Endless Sky is free software: you can redistribute it and/or modify it under the
terms of the GNU General Public License as published by the Free Software
Foundation, either version 3 of the License, or (at your option) any later version.

Endless Sky is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
PARTICULAR PURPOSE.  See the GNU General Public License for more details.
*/

#ifndef SHIP_NAME_H_
#define SHIP_NAME_H_

#include "DataFile.h"

#include <string>
#include <vector>



// Class representing a set of rules for generating random ship names.
class ShipName {
public:
	void Load(const DataFile::Node &node);
	
	std::string Get() const;
	
	
private:
	std::vector<std::vector<std::vector<std::string>>> words;
};



#endif