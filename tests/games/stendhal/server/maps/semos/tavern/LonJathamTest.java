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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

/**
 * Test buying with fractional amounts.
 *
 * @author Martin Fuchs
 */
public class LonJathamTest extends ZonePlayerAndNPCTestImpl {

	private static final String ZONE_NAME = "int_semos_tavern_0";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();

		setupZone(ZONE_NAME, new LonJathamNPC());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	public LonJathamTest() {
		super(ZONE_NAME, "Lon Jatham");
	}

	/**
	 * Tests for hiAndBye.
	 */
	@Test
	public void testHiAndBye() {
		final SpeakerNPC npc = getNPC("Lon Jatham");
		final Engine en = npc.getEngine();

		assertTrue(en.step(player, "hello"));
		assertEquals("Good Morning!!! Have you got questions about #java?", getReply(npc));

		assertTrue(en.step(player, "bye"));
		assertEquals("Bye bye!", getReply(npc));
	}

	/**
	 * Tests for Discussion of Java.
	 */
	@Test
	public void testAboutJava() {
		final SpeakerNPC npc = getNPC("Lon Jatham");
		final Engine en = npc.getEngine();

		assertTrue(en.step(player, "hi"));
		assertEquals("Good Morning!!! Have you got questions about #java?", getReply(npc));

		assertTrue(en.step(player, "java"));
		assertEquals("I am the greatest expert of Java, the technology that underpins our world, to ever have lived! Would you like to learn about #objects or #variables, or perhaps how to use a #filebrowser?", getReply(npc));

		assertTrue(en.step(player, "objects"));
		assertEquals("Objects allow #variables relating to a single thing, such as me, you, or the entire city, to be clustered together! Good, mm?", getReply(npc));
		
		assertTrue(en.step(player, "variables"));
		assertEquals("In Java, variables are used to assign values for future reference, and passing between programs. These may occur in many types, including #strings, #integers and #doubles", getReply(npc));
		
		assertTrue(en.step(player, "strings"));
		assertEquals("Strings are collections of characters, such as words or sentences. This dialogue is stored in a string right now!", getReply(npc));
		
		assertTrue(en.step(player, "integers"));
		assertEquals("Integers are whole number values, as opposed to #doubles...", getReply(npc));
		
		assertTrue(en.step(player, "doubles"));
		assertEquals("Doubles may be decimal values, as opposed to #integers...", getReply(npc));
		
		assertTrue(en.step(player, "filebrowser"));
		assertEquals("No! NEVER USE A FILE BROWSER!", getReply(npc));
	}
}
