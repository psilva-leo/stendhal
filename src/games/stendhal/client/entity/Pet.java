/* $Id$ */
/***************************************************************************
 *                      (C) Copyright 2003 - Marauroa                      *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.client.entity;

import games.stendhal.client.soundreview.SoundMaster;
import games.stendhal.common.Rand;

/** A Pet entity */
public class Pet extends DomesticAnimal {
	//
	// DomesticAnimal
	//

	@Override
	protected void probableChat(final int chance) {
		String[][] soundnames = {
				{ "pet-1.wav", "pet-3.wav" },
				{ "pet-2.wav", "pet-4.wav" } };
		int which = Rand.rand(2);
		if (Rand.rand(100) < chance) {
			String token = getWeight() > 50 ? soundnames[0][which]
					: soundnames[1][which];
			SoundMaster.play(token, x, y); // playSound(token, 20, 35, chance);
		}
	}
}
