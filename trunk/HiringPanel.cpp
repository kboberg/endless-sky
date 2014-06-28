/* HiringPanel.cpp
Copyright (c) 2014 by Michael Zahniser

Endless Sky is free software: you can redistribute it and/or modify it under the
terms of the GNU General Public License as published by the Free Software
Foundation, either version 3 of the License, or (at your option) any later version.

Endless Sky is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
PARTICULAR PURPOSE.  See the GNU General Public License for more details.
*/

#include "HiringPanel.h"

#include "GameData.h"
#include "Interface.h"
#include "PlayerInfo.h"

using namespace std;



HiringPanel::HiringPanel(const GameData &data, PlayerInfo &player)
	: data(data), player(player)
{
	SetTrapAllEvents(false);
}



void HiringPanel::Draw() const
{
	if(!player.GetShip())
		return;
	const Ship &flagship = *player.GetShip();
	
	const Interface *interface = data.Interfaces().Get("hiring");
	Information info;
	
	int flagshipBunks = flagship.Attributes().Get("bunks");
	int flagshipRequired = flagship.Attributes().Get("required crew");
	int flagshipExtra = flagship.Crew() - flagshipRequired;
	int flagshipUnused = flagshipBunks - flagship.Crew();
	info.SetString("flagship bunks", to_string(static_cast<int>(flagshipBunks)));
	info.SetString("flagship required", to_string(static_cast<int>(flagshipRequired)));
	info.SetString("flagship extra", to_string(static_cast<int>(flagshipExtra)));
	info.SetString("flagship unused", to_string(static_cast<int>(flagshipUnused)));
	
	// Sum up the statistics for all your ships.
	int fleetBunks = 0;
	int fleetRequired = 0;
	for(const shared_ptr<Ship> &ship : player.Ships())
	{
		const Outfit &attr = ship->Attributes();
		fleetBunks += static_cast<int>(attr.Get("bunks"));
		fleetRequired += static_cast<int>(attr.Get("required crew"));
	}
	int passengers = 0;
	int fleetUnused = fleetBunks - fleetRequired - flagshipExtra;
	info.SetString("fleet bunks", to_string(static_cast<int>(fleetBunks)));
	info.SetString("fleet required", to_string(static_cast<int>(fleetRequired)));
	info.SetString("fleet unused", to_string(static_cast<int>(fleetUnused)));
	info.SetString("passengers", to_string(static_cast<int>(passengers)));
	
	static const int DAILY_SALARY = 100;
	int salary = DAILY_SALARY * (fleetRequired - 1);
	int extraSalary = DAILY_SALARY * flagshipExtra;
	info.SetString("salary required", to_string(static_cast<int>(salary)));
	info.SetString("salary extra", to_string(static_cast<int>(extraSalary)));
	
	if(fleetUnused)
		info.SetCondition("can hire");
	if(flagshipExtra)
		info.SetCondition("can fire");
	
	int modifier = Modifier();
	if(modifier > 1)
		info.SetString("modifier", "x " + to_string(modifier));
	
	interface->Draw(info);
}



bool HiringPanel::KeyDown(SDL_Keycode key, Uint16 mod)
{
	if(!player.GetShip())
		return false;
	
	if(key == 'h' || key == '=' || key == SDLK_RETURN || key == SDLK_SPACE)
		player.GetShip()->AddCrew(Modifier());
	else if(key == 'f' || key == '-' || key == SDLK_BACKSPACE || key == SDLK_DELETE)
		player.GetShip()->AddCrew(-Modifier());
	
	return false;
}



bool HiringPanel::Click(int x, int y)
{
	// Handle clicks on the interface buttons.
	const Interface *interface = data.Interfaces().Get("hiring");
	if(interface)
	{
		char key = interface->OnClick(Point(x, y));
		if(key != '\0')
			return KeyDown(static_cast<SDL_Keycode>(key), KMOD_NONE);
	}
	
	return false;
}



int HiringPanel::Modifier()
{
	SDL_Keymod mod = SDL_GetModState();
	
	int modifier = 1;
	if(mod & KMOD_CTRL)
		modifier *= 20;
	if(mod & KMOD_SHIFT)
		modifier *= 5;
	
	return modifier;
}
