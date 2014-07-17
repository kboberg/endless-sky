/* MapPanel.cpp
Copyright (c) 2014 by Michael Zahniser

Endless Sky is free software: you can redistribute it and/or modify it under the
terms of the GNU General Public License as published by the Free Software
Foundation, either version 3 of the License, or (at your option) any later version.

Endless Sky is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
PARTICULAR PURPOSE.  See the GNU General Public License for more details.
*/

#include "MapPanel.h"

#include "DotShader.h"
#include "Font.h"
#include "FontSet.h"
#include "GameData.h"
#include "LineShader.h"
#include "Mission.h"
#include "PlayerInfo.h"
#include "PointerShader.h"
#include "Screen.h"
#include "SpriteSet.h"
#include "SpriteShader.h"
#include "System.h"
#include "Trade.h"
#include "UI.h"

using namespace std;



MapPanel::MapPanel(const GameData &data, PlayerInfo &player, int commodity)
	: data(data), player(player), distance(player),
	playerSystem(player.GetShip()->GetSystem()),
	selectedSystem(player.GetShip()->GetSystem()),
	commodity(commodity)
{
	SetIsFullScreen(true);
	
	// Special case: any systems which have not been seen but which are the
	// destination of a mission, should be shown in the map.
	for(const Mission &mission : player.AvailableJobs())
		destinations.insert(mission.Destination()->GetSystem());
	for(const Mission &mission : player.Missions())
		destinations.insert(mission.Destination()->GetSystem());
	
	center = Point(0., 0.) - playerSystem->Position();
}



void MapPanel::Draw() const
{
	glClear(GL_COLOR_BUFFER_BIT);
	
	const Sprite *galaxy = SpriteSet::Get("ui/galaxy");
	SpriteShader::Draw(galaxy, center);
	
	DrawTravelPlan();
	
	// Draw the "visible range" circle around your current location.
	Color dimColor(.1, 0.);
	DotShader::Draw(playerSystem->Position() + center, 100.5, 99.5, dimColor);
	Color brightColor(.4, 0.);
	DotShader::Draw(selectedSystem->Position() + center, 11., 9., brightColor);
	
	DrawLinks();
	DrawSystems();
	DrawNames();
	DrawMissions();
}



bool MapPanel::Click(int x, int y)
{
	// Figure out if a system was clicked on.
	Point click = Point(x, y) - center;
	for(const auto &it : data.Systems())
		if(click.Distance(it.second.Position()) < 10.)
		{
			Select(&it.second);
			break;
		}
	
	return true;
}



bool MapPanel::Drag(int dx, int dy)
{
	center += Point(dx, dy);
	return true;
}



void MapPanel::Select(const System *system)
{
	if(!system)
		return;
	selectedSystem = system;
	
	if(distance.HasRoute(system))
	{
		player.ClearTravel();
		while(system != playerSystem)
		{
			player.AddTravel(system);
			system = distance.Route(system);
		}
	}
}



void MapPanel::DrawTravelPlan() const
{
	Color color(.4, .4, 0., 0.);
	
	// Draw your current travel plan.
	const System *previous = playerSystem;
	for(int i = player.TravelPlan().size() - 1; i >= 0; --i)
	{
		const System *next = player.TravelPlan()[i];
		
		Point from = next->Position() + center;
		Point to = previous->Position() + center;
		Point unit = (from - to).Unit() * 7.;
		from -= unit;
		to += unit;
		
		LineShader::Draw(from, to, 3., color);
		
		previous = next;
	}
}



void MapPanel::DrawLinks() const
{
	// Draw the links between the systems.
	Color closeColor(.6, .6);
	Color farColor(.3, .3);
	for(const auto &it : data.Systems())
	{
		const System *system = &it.second;
		if(!player.HasSeen(system))
			continue;
		
		for(const System *link : system->Links())
			if(link < system || !player.HasSeen(link))
			{
				// Only draw links between two systems if one of the two is
				// visited. Also, avoid drawiong twice by only drawing in the
				// direction of increasing pointer values.
				if(!player.HasVisited(system) && !player.HasVisited(link))
					continue;
				
				Point from = system->Position() + center;
				Point to = link->Position() + center;
				Point unit = (from - to).Unit() * 7.;
				from -= unit;
				to += unit;
				
				bool isClose = (system == playerSystem || link == playerSystem);
				LineShader::Draw(from, to, 1.2, isClose ? closeColor : farColor);
			}
	}
}



void MapPanel::DrawSystems() const
{
	// Draw the circles for the systems, colored based on the selected criterion,
	// which may be government, services, or commodity prices.
	const Government *playerGovernment = data.Governments().Get("Escort");
	for(const auto &it : data.Systems())
	{
		const System &system = it.second;
		if(!player.HasSeen(&system) && destinations.find(&system) == destinations.end())
			continue;
		
		Color color(.2, .2);
		if(system.IsInhabited() && player.HasVisited(&system))
		{
			if(commodity > -3)
			{
				float value = 0.f;
				if(commodity >= 0)
				{
					const Trade::Commodity &com = data.Commodities()[commodity];
					value = (2.f * (system.Trade(com.name) - com.low))
						/ (com.high - com.low) - 1.f;
				}
				else if(commodity == -1)
					value = system.HasShipyard() * 2 - 1;
				else if(commodity == -2)
					value = system.HasOutfitter() * 2 - 1;
				// Color the systems with a gradient from blue to cyan to gold.
				if(value < 0.f)
					color = Color(
						.12 + .12 * value,
						.48 + .36 * value,
						.48 - .12 * value,
						.4f);
				else
					color = Color(
						.12 + .48 * value,
						.48,
						.48 - .48 * value,
						.4f);
			}
			else if(commodity == -3)
			{
				// Color based on the government's specified color.
				color = Color(
					.6f * system.GetGovernment().GetColor().Get()[0],
					.6f * system.GetGovernment().GetColor().Get()[1],
					.6f * system.GetGovernment().GetColor().Get()[2],
					.4f);
			}
			else
				color = system.GetGovernment().IsEnemy(playerGovernment) ?
					Color(.60f, .10f, .00f, .40f) : Color(.00f, .12f, .60f, .40f);
		}
		
		DotShader::Draw(system.Position() + center, 6., 3.5, color);
	}
}



void MapPanel::DrawNames() const
{
	// Draw names for all systems you have visited.
	const Font &font = FontSet::Get(14);
	Color closeColor(.6, .6);
	Color farColor(.3, .3);
	Point offset(6., -.5 * font.Height());
	for(const auto &it : data.Systems())
	{
		const System &system = it.second;
		if(!player.HasVisited(&system))
			continue;
		
		font.Draw(system.Name(), system.Position() + offset + center,
			(&system == playerSystem) ? closeColor : farColor);
	}
}



void MapPanel::DrawMissions() const
{
	// Draw a pointer for each active or playerSystem mission.
	map<const System *, Angle> angle;
	Color black(0., 1.);
	Color availableColor(.2, 1., 0., 1.);
	Color currentColor(.2, .6, 1., 1.);
	for(const Mission &mission : player.AvailableJobs())
	{
		const System *system = mission.Destination()->GetSystem();
		Angle a = (angle[system] += Angle(30.));
		Point pos = system->Position() + center;
		PointerShader::Draw(pos, a.Unit(), 14., 19., -4., black);
		PointerShader::Draw(pos, a.Unit(), 8., 15., -6., availableColor);
	}
	for(const Mission &mission : player.Missions())
	{
		const System *system = mission.Destination()->GetSystem();
		Angle a = (angle[system] += Angle(30.));
		Point pos = system->Position() + center;
		PointerShader::Draw(pos, a.Unit(), 14., 19., -4., black);
		PointerShader::Draw(pos, a.Unit(), 8., 15., -6., currentColor);
	}
}