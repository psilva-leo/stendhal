/***************************************************************************
 *                   (C) Copyright 2003-2011 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/

package games.stendhal.server.maps.quests;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.semos.mountain.PeterTheGoatherdNPC;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utilities.PlayerTestHelper;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class PieChallengeTest extends ZonePlayerAndNPCTestImpl {

	private Player player = null;
	private SpeakerNPC npc = null;
	private Engine en = null;

	private String questSlot;
	private static final String ZONE_NAME = "0_semos_mountain_n2_w";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}

	public PieChallengeTest() {
		super(ZONE_NAME, "Peter the goatherd");
	}

	@Override
	@Before
	public void setUp() {
		final StendhalRPZone zone = new StendhalRPZone(ZONE_NAME);
		new PeterTheGoatherdNPC().configureZone(zone, null);

		AbstractQuest quest = new PieChallenge();
		quest.addToWorld();

		questSlot = quest.getSlotName();

		player = PlayerTestHelper.createPlayer("bob");
	}

	@Test
	public void testQuest() {
		npc = SingletonRepository.getNPCList().get("Peter the goatherd");
		en = npc.getEngine();


		// -----------------------------------------------


		// -----------------------------------------------

		// [19:04] Welcome to Stendhal. Need help? http://stendhalgame.org/wiki/AskForHelp - please report problems, suggestions and bugs. Remember to keep your password completely secret, never tell to another friend, player, or admin.

		// -----------------------------------------------

		// [19:04] Synchronized

		// -----------------------------------------------

		en.step(player, "hi");

		// -----------------------------------------------

		assertEquals("Hello, I hope you enjoy the cool wind here, you need #help ?",getReply(npc));
		// -----------------------------------------------

		en.step(player, "stomach");

		// -----------------------------------------------

		assertEquals("It's a shame for you to see me starving like this, i really need some hot #pies...", getReply(npc));

		// -----------------------------------------------

		en.step(player, "pies");

		// -----------------------------------------------

		assertEquals("Would you be kind enough to find me some hot pies for my stomach? I'd be ever so grateful!", getReply(npc));

		// -----------------------------------------------

		en.step(player, "no");

		// -----------------------------------------------

		assertEquals("This stomach is hungry you know...", getReply(npc));

		// -----------------------------------------------

		en.step(player, "bye");

		// -----------------------------------------------

		assertEquals("Farewell, return to me whenever you need my help.", getReply(npc));

		// -----------------------------------------------


		// -----------------------------------------------

	    en.step(player, "hi");

		// -----------------------------------------------

		assertEquals("Hello, I hope you enjoy the cool wind here, you need #help ?", getReply(npc));

		// -----------------------------------------------

		en.step(player, "quest");

		// -----------------------------------------------

		assertEquals("Are you willing to find me hot pies for my stomach yet?", getReply(npc));

		// -----------------------------------------------

		en.step(player, "yes");

		// -----------------------------------------------

		assertEquals("That's wonderful! I'd like these hot pies: an #'apple pie', a #'cherry pie', and a #'fish pie'.", getReply(npc));

		// -----------------------------------------------

		en.step(player, "fish pie");

		// -----------------------------------------------

		assertEquals("Delicious fish pie !, You see a fish and leek pie. It's not too stodgy so you can eat it faster than a meat pie.", getReply(npc));

		// -----------------------------------------------

		en.step(player, "bye");

		// -----------------------------------------------

		assertEquals("Farewell, return to me whenever you need my help.", getReply(npc));

		// -----------------------------------------------

		PlayerTestHelper.equipWithStackableItem(player, "fish pie", 1);

		// -----------------------------------------------

		en.step(player, "hi");

		// -----------------------------------------------

		assertEquals("Hello again. If you've brought me some hot pies for my #stomach, I'll happily take them!", getReply(npc));

		// -----------------------------------------------

		en.step(player, "stomach");

		// -----------------------------------------------

		assertEquals("I'd still like an #'apple pie', a #'cherry pie', and a #'fish pie'. Have you brought any?", getReply(npc));

		// -----------------------------------------------

		en.step(player, "fish pie");

		// -----------------------------------------------

		assertEquals("Wonderful! Did you bring anything else with you?", getReply(npc));

		// -----------------------------------------------

		en.step(player, "no");

		// -----------------------------------------------

		assertEquals("Oh, that's a shame, do tell me when you find some. I'd still like an #'apple pie' and a #'cherry pie'.", getReply(npc));

		// -----------------------------------------------

		en.step(player, "bye");

		// -----------------------------------------------

		assertEquals("Farewell, return to me whenever you need my help.", getReply(npc));

		// -----------------------------------------------

		PlayerTestHelper.equipWithStackableItem(player, "cherry pie", 1);

		// -----------------------------------------------

		en.step(player, "hi");

		// -----------------------------------------------

		assertEquals("Hello again. If you've brought me some hot pies for my #stomach, I'll happily take them!", getReply(npc));

		// -----------------------------------------------

		en.step(player, "quest");

		// -----------------------------------------------

		assertEquals("I'd still like an #'apple pie' and a #'cherry pie'. Have you brought any?", getReply(npc));

		// -----------------------------------------------

		en.step(player, "yes");

		// -----------------------------------------------

		assertEquals("Wonderful, what hot delights have you brought?", getReply(npc));

		// -----------------------------------------------

		en.step(player, "cherry pie");
		
		// -----------------------------------------------

		PlayerTestHelper.equipWithStackableItem(player, "fish pie", 1);
		//PlayerTestHelper.equipWithStackableItem(player, "cherry pie", 1);
		PlayerTestHelper.equipWithStackableItem(player, "apple pie", 1);
		
		final int xp = player.getXP();
		final double karma = player.getKarma();
		
		en.step(player, "fish pie");
		//en.step(player, "cherry pie");
		en.step(player, "apple pie ");

		// -----------------------------------------------
		
		assertEquals("Hot pies yum ! Thank you ever so much! Here, take this as a reward.", getReply(npc));

		// -----------------------------------------------
		
		// [19:05] pinch earns 50 experience points.
		assertTrue(player.isEquipped("milk"));
		assertThat(player.getXP(), greaterThan(xp));
		assertThat(player.getKarma(), greaterThan(karma));

		// -----------------------------------------------

		en.step(player, "bye");

		// -----------------------------------------------

		assertEquals("Farewell, return to me whenever you need my help.", getReply(npc));

		// -----------------------------------------------


		// -----------------------------------------------

		en.step(player, "hi");

		// -----------------------------------------------

		assertEquals("Hello, I hope you enjoy the cool wind here, you need #help ?", getReply(npc));

		// -----------------------------------------------

		en.step(player, "stomach");

		// -----------------------------------------------

		assertEquals("I'm sorry to say that the pies you brought for my stomach aren't very warm anymore. Would you be kind enough to find me some more?", getReply(npc));

		// -----------------------------------------------

		en.step(player, "bye");

		// -----------------------------------------------

		// [19:05] Removed contained minor potion item with ID 6 from bag

		// -----------------------------------------------

		assertEquals("Farewell, return to me whenever you need my help.", getReply(npc));

		// -----------------------------------------------


		// -----------------------------------------------

		en.step(player, "hi");

		// -----------------------------------------------

		assertEquals("Hello, I hope you enjoy the cool wind here, you need #help ?", getReply(npc));

		// -----------------------------------------------

		en.step(player, "stomach");

		// -----------------------------------------------

		player.setQuest(questSlot, "done;0");
	}

}

