/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.semos.temple;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;

import java.util.Map;

public class BooksellerNPC implements ZoneConfigurator {

	/**
	 * Configure a zone.
	 *
	 * @param	zone		The zone to be configured.
	 * @param	attributes	Configuration attributes.
	 */
	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildSemosTempleArea(zone);
	}

	private void buildSemosTempleArea(final StendhalRPZone zone) {
		final SpeakerNPC npc = new SpeakerNPC("Fidorea") {

			@Override
			protected void createPath() {
				setPath(null);
			}

			@Override
			protected void createDialog() {
				addGreeting();
				addJob("Sellin' books and takin' names yo");
				addHelp("Nah m8");
				addGoodbye();
			}
		};

		npc.setEntityClass("welcomernpc");
		npc.setDescription("You see Fideroea she screams forever D:");
		npc.setPosition(9, 6);
		npc.initHP(100);
		zone.add(npc);
	}
}