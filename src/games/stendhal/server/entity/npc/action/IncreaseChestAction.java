/***************************************************************************
 *                   (C) Copyright 2014 - Faiumoni e. V.                   *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.npc.action;

import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.player.Player;



/**
 * creates a slot
 *
 * @author hendrik
 */
public class IncreaseChestAction implements ChatAction {
	

	/**
	 * creates a slot
	 *
	 * @param slotNames list of slots to create
	 */
	public IncreaseChestAction() {
		
	}

	/*
	 * Get 20000 money and increases 6 slots of the player's chest 
	 * Not working
	 * @see games.stendhal.server.entity.npc.ChatAction#fire(games.stendhal.server.entity.player.Player, games.stendhal.common.parser.Sentence, games.stendhal.server.entity.npc.EventRaiser)
	 */
	@Override
	public void fire(Player player, Sentence sentence, EventRaiser npc) {
		
		player.drop("money", 20000);
		//TODO Increase player's chest
		
		
		
	}

}
