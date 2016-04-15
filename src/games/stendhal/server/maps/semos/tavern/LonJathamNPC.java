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
package games.stendhal.server.maps.semos.tavern;

import games.stendhal.common.Direction;
import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;

import java.util.Map;

public class LonJathamNPC implements ZoneConfigurator {
	/**
	 * Configure a zone.
	 *
	 * @param	zone		The zone to be configured.
	 * @param	attributes	Configuration attributes.
	 */
	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildNPC(zone);
	}

	private void buildNPC(final StendhalRPZone zone) {
		final SpeakerNPC jatham = new SpeakerNPC("Lon Jatham") {

			@Override
			public void say(final String text) {
				setDirection(Direction.DOWN);
				super.say(text, false);
			}

			@Override
			protected void createPath() {
				setPath(null);
			}

			@Override
			protected void createDialog() {
				addGreeting("Good Morning!!! Have you got questions about #java?");
				
				addReply("java","I am the greatest expert of Java, the technology that underpins our world, to ever have lived! Would you like to learn about #objects or #variables, or perhaps how to use a #filebrowser?");
				
				addReply("objects","Objects allow #variables relating to a single thing, such as me, you, or the entire city, to be clustered together! Good, mm?");

				addReply("variables","In Java, variables are used to assign values for future reference, and passing between programs. These may occur in many types, including #strings, #integers and #doubles");
				
				addReply("filebrowser","No! NEVER USE A FILE BROWSER!");
				
				addReply("integers","Integers are whole number values, as opposed to #doubles...");
				
				addReply("strings","Strings are collections of characters, such as words or sentences. This dialogue is stored in a string right now!");
				
				addReply("doubles","Doubles may be decimal values, as opposed to #integers...");
				
				addGoodbye("Bye bye!");
			}
		};

		jatham.setEntityClass("lonjathamnpc");
		jatham.setDescription("Lon Jatham knows everything about Java!");
		jatham.setPosition(24, 19);
		jatham.setDirection(Direction.DOWN);
		jatham.initHP(100);
		zone.add(jatham);
	}
}
