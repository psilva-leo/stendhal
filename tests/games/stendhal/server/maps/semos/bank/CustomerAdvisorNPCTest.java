/***************************************************************************
 *                (C) Copyright 2005-2015 - Faiumoni e. V.                 *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.semos.bank;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPObject.ID;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class CustomerAdvisorNPCTest extends ZonePlayerAndNPCTestImpl {

	private static final String ZONE_NAME = "int_semos_bank";
	private static final String NPC_NAME = "Dagobert";


	private static final String REPLY_HELLO = "Welcome to the bank of Semos! I am here to #help you manage your personal chest.";
	private static final String REPLY_HELP = "Follow the corridor to the right, and you will find the magic chests. You can store your belongings in any of them, and nobody else will be able to touch them! A number of spells have been cast on the chest areas to ensure #safety. You can #increase your chest too.";
	private static final String REPLY_INCREASE = "You can #buy more 6 slots for your chest for 20k money.";
	private static final String REPLY_NOT_ENOUGHT_MONEY = "Sorry, you don't have enough money.";
	private static final String REPLY_ENOUGHT_MONEY = "Congratilations! Your chest has now increased!";

	private Player player;
	private SpeakerNPC npc;
	private Engine engine;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();

		setupZone(ZONE_NAME, new CustomerAdvisorNPC());
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		player = createPlayer("player");
		npc = SingletonRepository.getNPCList().get(NPC_NAME);
		engine = npc.getEngine();
	}

	public CustomerAdvisorNPCTest() {
		super(ZONE_NAME, NPC_NAME);
	}

	
	@Test
	public void IncreaseChestTest() {
		// Not Enough Money
		engine.step(player, ConversationPhrases.GREETING_MESSAGES.get(0));
		assertTrue(npc.isTalking());
		assertEquals(REPLY_HELLO, getReply(npc));
		
		engine.step(player, "help");
		assertEquals(REPLY_HELP, getReply(npc));
		
		engine.step(player, "increase");
		assertEquals(REPLY_INCREASE, getReply(npc));
		
		engine.step(player, "help");
		assertEquals(REPLY_HELP, getReply(npc));
		
		engine.step(player, "buy");
		assertEquals(REPLY_NOT_ENOUGHT_MONEY, getReply(npc));
		
		//--------------------------------------------
		// Enough Money
		final StackableItem money = new StackableItem("money", "", "", null);
		money.setQuantity(20000);
		money.setID(new ID(2, ZONE_NAME));
		player.getSlot("bag").add(money);
		assertEquals(20000, player.getNumberOfEquipped("money"));
		
		engine.step(player, ConversationPhrases.GREETING_MESSAGES.get(0));
		assertTrue(npc.isTalking());
		assertEquals(REPLY_HELLO, getReply(npc));
		
		engine.step(player, "help");
		assertEquals(REPLY_HELP, getReply(npc));
		
		engine.step(player, "increase");
		assertEquals(REPLY_INCREASE, getReply(npc));
		
		engine.step(player, "help");
		assertEquals(REPLY_HELP, getReply(npc));
		
		engine.step(player, "buy");
		assertEquals(REPLY_ENOUGHT_MONEY, getReply(npc));
		
	}

	
}
